// Call the dataTables jQuery plugin
$(document).ready(function() {
// on ready.
});

async function registrarUsuarios(){

let datos= {};
datos.nombre= document.getElementById("NameField").value
datos.apellido= document.getElementById("LastNameField").value
datos.email= document.getElementById("EmailField").value
datos.password= document.getElementById("PasswordField").value

let repeatPass= document.getElementById("RepeatPasswordField").value

    if(repeatPass != datos.password){
        alert("The 2nd password is not equal")
        return;
    }

// codigo para ejecutar un fetch al servidor.
    const request = await fetch('api/usuarios',{
    method: 'POST',
    headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
    },
    body: JSON.stringify(datos)
    });

    alert("Account Created")
    window.location.href = "login.html"
}