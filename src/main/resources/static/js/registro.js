document.getElementById("btnRegistro").addEventListener("click", () => {
  const nombre = document.getElementById("nombre").value;
  const email = document.getElementById("email").value;
  const clave = document.getElementById("clave").value;

  fetch("http://localhost:8080/api/usuarios/registro", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ nombre, email, clave }),
  })
    .then(res => res.ok ? res.json() : Promise.reject("Error al registrar"))
    .then(() => alert("Cuenta creada correctamente"))
    .catch(err => alert(err));
});
