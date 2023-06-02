// Call the dataTables jQuery plugin
$(document).ready(function() {
// on ready.
});

async function iniciarSesion(){

let datos= {};
datos.email= document.getElementById("EmailField").value
datos.password= document.getElementById("PasswordField").value
//this needs the search function
//datos.nick = document.getElementById("NameField").value

// codigo para ejecutar un fetch al servidor.
    const request = await fetch('api/login',{
    method: 'POST',
    headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
    },
    body: JSON.stringify(datos)
    });


    //here we can use .json() or .text() to obtain the result in that formats.
    const response= await request.text();



    if(response != "FAIL" ){
    //this save the jwt token.
    localStorage.token = response;
    localStorage.email = datos.email;

       const requestNick = await fetch('api/usuarios/search',{
            method: 'POST',
            headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': localStorage.token
            },
            body: JSON.stringify(datos)
            });
        const userInfo = await requestNick.text();

    localStorage.nick = userInfo.split(",")[1];
    window.location.href='usuarios.html'
    }else{
    alert("WRONG credentials")
    }

}