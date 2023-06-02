package com.test.testtraining.dao;

import com.test.testtraining.models.Usuario;

import java.util.List;

public interface UsuarioDao {

    List<Usuario> obtenerUsuarios();

    void eliminar(Long id);

    void registrar(Usuario usuario);

    Usuario reviewAngGetObjectOfCredentials(Usuario usuario, boolean pass );

}
