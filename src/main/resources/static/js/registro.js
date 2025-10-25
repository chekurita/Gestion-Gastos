document.getElementById("btnRegistro").addEventListener("click", () => {
  const nombre = document.getElementById("nombre").value;
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  fetch("http://localhost:8080/api/usuarios/register", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ nombre, email, password }),
  })
    .then(res => res.ok ? res.json() : Promise.reject("Error al registrar"))
    .then(() => alert("Cuenta creada correctamente"))
    .catch(err => alert(err));
});


