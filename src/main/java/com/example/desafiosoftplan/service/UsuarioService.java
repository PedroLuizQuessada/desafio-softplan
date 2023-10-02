package com.example.desafiosoftplan.service;

import com.example.desafiosoftplan.exception.UsuarioNaoAdmException;
import com.example.desafiosoftplan.exception.UsuarioNaoEncontradoException;
import com.example.desafiosoftplan.model.Usuario;
import com.example.desafiosoftplan.repo.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final static String USERNAME_KEY = "username";
    private final static String SENHA_KEY = "senha";

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario getByUsernameESenha(String credenciaisCriptografadas) throws UsuarioNaoEncontradoException {
        Map<String, String> credenciais;
        try {
            credenciais = decodificarCredenciais(credenciaisCriptografadas);
        }
        catch (Exception e) {
            throw new UsuarioNaoEncontradoException();
        }

        Optional<Usuario> usuarioOptional = usuarioRepository.getByUsernameAndSenha(credenciais.get(USERNAME_KEY), credenciais.get(SENHA_KEY));
        if (usuarioOptional.isPresent()) {
            return usuarioOptional.get();
        }
        else {
            throw new UsuarioNaoEncontradoException();
        }
    }

    public void isAdm(Usuario usuario) throws UsuarioNaoAdmException {
        if (!usuario.isAdm()) {
            throw new UsuarioNaoAdmException();
        }
    }

    private Map<String, String> decodificarCredenciais(String credenciaisCriptografadas) {
        Map<String, String> credenciais = new HashMap<>();

        credenciaisCriptografadas = credenciaisCriptografadas.replace("Basic ", "");
        byte[] decodedBytes = Base64.getDecoder().decode(credenciaisCriptografadas);
        String credenciaisDescriptografadas = new String(decodedBytes);
        String[] credenciaisArray = credenciaisDescriptografadas.split(":");

        credenciais.put(USERNAME_KEY, credenciaisArray[0]);
        credenciais.put(SENHA_KEY, credenciaisArray[1]);
        return credenciais;
    }
}
