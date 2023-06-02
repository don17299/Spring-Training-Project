// Call the dataTables jQuery plugin
$(document).ready(function() {
    cargarUsuarios();
  $('#usuarios').DataTable();
  updateNickname();
});

function updateNickname(){
//outerhtml is to change the html and not only the content
    document.getElementById("nickname-field-user").outerHTML= localStorage.nick;
}

function getHeaders(){
    return {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.token
        };
}

async function cargarUsuarios(){
// codigo para ejecutar un fetch al servidor.
    const request = await fetch('api/usuarios',{
    method: 'GET',
    headers: getHeaders()
   });

    const usuarios = await request.json();

    let listadoHtml='';

    for(let usuario of usuarios){
        let deleteButton= '<a href="#" onclick="eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>'

        let telefono= usuario==null? '-' : usuario.telefono;
        let usuarioHtml=    '<tr><td>'+usuario.id+'</td><td>'+usuario.nombre+usuario.apellido+'</td><td>'
                            +usuario.email+'</td><td>'+telefono
                            +'</td><td>'+deleteButton+'</td></tr>'
        listadoHtml += usuarioHtml;
    }
    document.querySelector('#usuarios tbody').outerHTML= listadoHtml;
}

async function eliminarUsuario(id){

    if(!confirm('¿Desea eliminar este usuario?')){
        return;
    }

    const request = await fetch('api/usuarios/'+id,{
        method: 'DELETE',
        headers: getHeaders()
    });

    location.reload();
}

async function buscarUsuario(){

    let datos= {};
    datos.email= document.getElementById("searchInput").value;
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

    let userData = userInfo.split(",");

        let deleteButton= '<a href="#" onclick="eliminarUsuario('+userData[0]+')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>'
        let usuarioHtml=    '<tr><td>'+userData[0]+'</td><td>'+userData[1]+userData[2]+'</td><td>'
                            +userData[3]+'</td><td>'+userData[4]
                            +'</td><td>'+deleteButton+'</td></tr>'

    document.querySelector('#usuarios tbody').outerHTML= usuarioHtml;

    //location.reload();
}