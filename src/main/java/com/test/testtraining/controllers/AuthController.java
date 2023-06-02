package com.test.testtraining.controllers;

import com.test.testtraining.dao.UsuarioDao;
import com.test.testtraining.models.Usuario;
import com.test.testtraining.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value= "api/login", method = RequestMethod.POST)
    public String login(@RequestBody Usuario usuario){
        Usuario user= usuarioDao.reviewAngGetObjectOfCredentials(usuario, true);

        if(user!=null){

            return jwtUtil.create(String.valueOf(user.getId()),user.getEmail());

        }else{
            return "FAIL";
        }
    }
}
