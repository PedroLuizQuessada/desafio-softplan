package com.example.desafiosoftplan;

import com.example.desafiosoftplan.exception.UsuarioNaoAdmException;
import com.example.desafiosoftplan.exception.UsuarioNaoEncontradoException;
import com.example.desafiosoftplan.model.Usuario;
import com.example.desafiosoftplan.service.UsuarioService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
@ComponentScan(basePackages = {"com.example.desafiosoftplan"})
public class UsuarioTests {
    @Autowired
    private UsuarioService usuarioService;
    private static final String CREDENCIAIS_CRIPTOGRAFADAS_ADM = "YWRtaW46c2VuaGExMjM=";
    private static final String CREDENCIAIS_CRIPTOGRAFADAS_NAO_ADM = "dXNlcjpzZW5oYTEyMw==";

    @Test
    public void getByUsernameESenha() throws UsuarioNaoEncontradoException {
        Usuario usuario = usuarioService.getByUsernameESenha(CREDENCIAIS_CRIPTOGRAFADAS_ADM);
        Assertions.assertThat(usuario.getId()).isNotNull();

        usuario = usuarioService.getByUsernameESenha(CREDENCIAIS_CRIPTOGRAFADAS_NAO_ADM);
        Assertions.assertThat(usuario.getId()).isNotNull();
    }

    @Test
    public void isAdm() throws Exception {
        Usuario usuarioAdm = usuarioService.getByUsernameESenha(CREDENCIAIS_CRIPTOGRAFADAS_ADM);
        Assertions.assertThat(usuarioAdm.getId()).isNotNull();
        usuarioService.isAdm(usuarioAdm);

        Usuario usuarioNaoAdm = usuarioService.getByUsernameESenha(CREDENCIAIS_CRIPTOGRAFADAS_NAO_ADM);
        Assertions.assertThat(usuarioNaoAdm.getId()).isNotNull();
        try {
            usuarioService.isAdm(usuarioNaoAdm);
            throw new Exception("Usuário não deveria ser ADM");
        }
        catch (UsuarioNaoAdmException ignored) {

        }
    }
}
