package com.example.desafiosoftplan.repo;

import com.example.desafiosoftplan.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.username = ?1 AND u.senha = ?2")
    Optional<Usuario> getByUsernameAndSenha(String username, String senha);
}
