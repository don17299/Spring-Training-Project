package com.test.testtraining.dao;

import com.test.testtraining.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;//this need jpa to exist.

import java.util.List;

@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Usuario> obtenerUsuarios() {
        String query = "FROM Usuario";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = entityManager.find(Usuario.class,id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);

    }

    @Override
    public Usuario reviewAngGetObjectOfCredentials(Usuario usuario, boolean pass) {
        String query = "FROM Usuario WHERE email= :email";
        List<Usuario> lista= entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();
        //if this list is null means that nobody has this email and pass.

        if(lista.isEmpty()){
           return null;
        }
        if(pass) {
            String passHashed = lista.get(0).getPassword();

            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            return argon2.verify(passHashed, usuario.getPassword().toCharArray())? lista.get(0):null;

        }else{
            return lista.get(0);
        }

    }
}
