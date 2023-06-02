package com.test.testtraining.controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.test.testtraining.dao.UsuarioDao;
import com.test.testtraining.models.Usuario;
import com.test.testtraining.utils.JwtUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JwtUtil jwtUtil;

    //by default. the http method is get
    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.GET)
    public Usuario obtenerUsuario(@PathVariable Long id){
        Usuario usuario= new Usuario();
        usuario.setId(id);
        usuario.setNombre("Carlos");
        usuario.setApellido("Duque");
        usuario.setEmail("mail123@mail.com");
        usuario.setTelefono("1231231233");

        return usuario;
    }

    @RequestMapping(value= "api/usuarios")
    public List<Usuario> obtenerUsuarios(@RequestHeader(value = "Authorization") String token){
        if(validatingToken(token)){
            return null;
        }
        return usuarioDao.obtenerUsuarios();
    }

    private boolean validatingToken(String token){
        String usuarioId = jwtUtil.getKey(token);
        return usuarioId == null;
    }

    @RequestMapping(value= "api/usuarios", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario){
        Argon2 argon2= Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        //the first number is related with security, more it more security more or less.
        String hash= argon2.hash(1, 1024,1,usuario.getPassword().toCharArray());

        usuario.setPassword(hash);

        usuarioDao.registrar(usuario);
    }

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)
    public void eliminarUsuario(@RequestHeader(value = "Authorization") String token,
                                @PathVariable Long id){
        if(validatingToken(token)){
            return;
        }

        usuarioDao.eliminar(id);
    }

    //QUest implement this method, cause the man didnt want to do that.
    @RequestMapping(value= "api/usuarios/search", method = RequestMethod.POST)
    public String buscarUsuario(@RequestBody Usuario usuario,
                                 @RequestHeader(value = "Authorization") String token){

        if(validatingToken(token)){
            return "FAIL";
        }

        Usuario user = usuarioDao.reviewAngGetObjectOfCredentials(usuario, false);

        if(user==null){
            return "FAIL";
        }

        return user.getId()+","+user.getNombre()+","+user.getApellido()+","+user.getEmail()+","+(user.getTelefono()==null?"-":user.getTelefono());
    }


}
