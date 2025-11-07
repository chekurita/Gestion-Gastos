

const API = "http://localhost:8080/api";
const messageEl = document.getElementById("message");

// obtenemos el token del login
const token = localStorage.getItem("token");

// si no hay token, redirigimos al login
if (!token) {
  window.location.href = "login.html";
}

// headers con autenticación
function authHeaders() {
  return {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`
  };
}

// objeto user que se llenará al inicio
let user = null;

// función para mostrar mensajes
function showMessage(text, time = 3000) {
  messageEl.textContent = text;
  messageEl.style.opacity = "1";
  setTimeout(() => { messageEl.style.opacity = "0"; }, time);
}

// obtener info del usuario logueado usando el token
async function fetchUserInfo() {
  try {
    const res = await fetch(`${API}/usuarios/me`, { headers: authHeaders() });
    if (!res.ok) throw new Error("No se pudo obtener info del usuario");
    user = await res.json();
    document.getElementById("userEmail").textContent = user.email;
    console.log("Usuario cargado:", user);
  } catch (e) {
    console.error(e);
    showMessage("Error obteniendo información del usuario");
    window.location.href = "login.html";
  }
}

// logout
document.getElementById("logoutBtn").addEventListener("click", () => {
  localStorage.removeItem("token");
  window.location.href = "login.html";
});



// UI nav
document.querySelectorAll(".sidebar li[data-section]").forEach(li => {
  li.addEventListener("click", () => {
    document.querySelectorAll(".sidebar li").forEach(n => n.classList.remove("active"));
    li.classList.add("active");
    const section = li.getAttribute("data-section");
    document.querySelectorAll(".page").forEach(p => p.classList.remove("visible"));
    document.getElementById(section).classList.add("visible");
    document.getElementById("pageTitle").textContent = li.textContent;
  });
});


/* Fetch categories and gastos */
async function fetchCategorias() {
  try {
    const res = await fetch(`${API}/categorias`, { headers: authHeaders() });
    if (!res.ok) throw new Error("No se pudieron cargar las categorías");
    const data = await res.json();
    renderCategorias(data);
    return data;
  } catch (e) {
    console.error(e);
    showMessage("Error cargando categorías");
    return [];
  }
}

async function fetchGastos() {
  try {
    const res = await fetch(`${API}/gastos/usuario/${user.id}`, { headers: authHeaders() });
    if (!res.ok) throw new Error("No se pudieron cargar gastos");
    const data = await res.json();
    renderGastos(data);
    updateOverview(data);
    return data;
  } catch (e) {
    console.error(e);
    showMessage("Error cargando gastos");
    return [];
  }
}

/* Renderers */
function renderCategorias(list) {
  const sel = document.getElementById("gastoCategoria");
  const ul = document.getElementById("listaCategorias");
  sel.innerHTML = `<option value="">-- Sin categoría --</option>`;
  ul.innerHTML = "";
  list.forEach(c => {
    const opt = document.createElement("option");
    opt.value = c.id;
    opt.textContent = c.nombre;
    sel.appendChild(opt);

    const li = document.createElement("li");
    li.textContent = c.nombre;
    ul.appendChild(li);
  });
  document.getElementById("countCategorias").textContent = list.length;
}

function renderGastos(list) {
  const tbody = document.querySelector("#tablaGastos tbody");
  const recent = document.querySelector("#tablaRecientes tbody");
  tbody.innerHTML = "";
  recent.innerHTML = "";

  list.forEach(g => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${g.fecha}</td>
      <td>${g.descripcion || "-"}</td>
      <td>${g.categoriaNombre || "-"}</td>
      <td>$${Number(g.monto).toFixed(2)}</td>
      <td><button class="btn-delete" data-id="${g.id}">Eliminar</button></td>
    `;
    tbody.appendChild(tr);

    // recientes: los primeros 5
    if (recent.children.length < 5) {
      const tr2 = document.createElement("tr");
      tr2.innerHTML = `
        <td>${g.fecha}</td>
        <td>${g.descripcion || "-"}</td>
        <td>${g.categoriaNombre || "-"}</td>
        <td>$${Number(g.monto).toFixed(2)}</td>
      `;
      recent.appendChild(tr2);
    }
  });

  // attach delete handlers
  document.querySelectorAll(".btn-delete").forEach(btn => {
    btn.addEventListener("click", async (e) => {
      const id = e.target.dataset.id;
      if (!confirm("Eliminar gasto?")) return;
      try {
        const res = await fetch(`${API}/gastos/${id}`, {
          method: "DELETE",
          headers: authHeaders()
        });
        if (!res.ok) throw new Error("No se pudo eliminar");
        showMessage("Gasto eliminado");
        await refreshAll();
      } catch (err) {
        console.error(err);
        showMessage("Error eliminando gasto");
      }
    });
  });

  document.getElementById("countGastos").textContent = list.length;
}

function updateOverview(list) {
  const total = list.reduce((s, g) => s + Number(g.monto || 0), 0);
  document.getElementById("totalGastado").textContent = `$${total.toFixed(2)}`;
}

/* Create category */
document.getElementById("btnCrearCategoria").addEventListener("click", async () => {
  const nombre = document.getElementById("categoriaNombre").value.trim();
  if (!nombre) { showMessage("Ingresá un nombre"); return; }
  try {
    const res = await fetch(`${API}/categorias`, {
      method: "POST",
      headers: authHeaders(),
      body: JSON.stringify({ nombre })
    });
    if (!res.ok) throw new Error("Error creando categoría");
    document.getElementById("categoriaNombre").value = "";
    showMessage("Categoría creada");
    await refreshAll();
  } catch (e) {
    console.error(e);
    showMessage("Error al crear categoría");
  }
});

/* Add gasto */
document.getElementById("formGasto").addEventListener("submit", async (ev) => {
  ev.preventDefault();
  const descripcion = document.getElementById("gastoDescripcion").value.trim();
  const monto = parseFloat(document.getElementById("gastoMonto").value);
  const fecha = document.getElementById("gastoFecha").value;
  const categoriaId = document.getElementById("gastoCategoria").value || null;

  if (!monto || !fecha) { showMessage("Completá monto y fecha"); return; }

  try {
    const res = await fetch(`${API}/gastos`, {
      method: "POST",
      headers: authHeaders(),
      body: JSON.stringify({
        descripcion,
        monto,
        fecha,
        usuarioId: user.id,
        categoriaId: categoriaId ? Number(categoriaId) : null
      })
    });
    if (!res.ok) {
      const txt = await res.text();
      throw new Error(txt || "Error creando gasto");
    }
    showMessage("Gasto creado");
    document.getElementById("formGasto").reset();
    await refreshAll();
    // switch to gastos list
    document.querySelector('.sidebar li[data-section="gastos"]').click();
  } catch (e) {
    console.error(e);
    showMessage("Error creando gasto");
  }
});

/* refresh all data */
async function refreshAll() {
  await fetchCategorias();
  await fetchGastos();
}

/* initial load */
(async function init() {
  // set today as default for new gasto
  document.getElementById("gastoFecha").value = new Date().toISOString().slice(0,10);
  await fetchUserInfo();
  await refreshAll();
})();

const modal = document.getElementById('modalGasto');
const btnAgregar = document.querySelector('[data-section="nuevo"]');
const btnCerrar = document.getElementById('closeModal');

btnAgregar.addEventListener('click', () => {
  modal.style.display = 'flex';
});

btnCerrar.addEventListener('click', () => {
  modal.style.display = 'none';
});

// Opcional: cerrar al hacer clic fuera del contenido
window.addEventListener('click', (e) => {
  if (e.target === modal) {
    modal.style.display = 'none';
  }
});

const modalCategorias = document.getElementById('modalCategorias');
const btnCategorias = document.querySelector('[data-section="categorias"]');
const btnCerrarCategorias = document.getElementById('closeCategorias');

btnCategorias.addEventListener('click', () => {
  modalCategorias.style.display = 'flex';
});

btnCerrarCategorias.addEventListener('click', () => {
  modalCategorias.style.display = 'none';
});

window.addEventListener('click', (e) => {
  if (e.target === modalCategorias) {
    modalCategorias.style.display = 'none';
  }
});


