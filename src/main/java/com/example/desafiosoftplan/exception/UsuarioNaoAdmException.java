package com.example.desafiosoftplan.exception;

public class UsuarioNaoAdmException extends Exception {
    public UsuarioNaoAdmException() {
        super("Usuário informado não é adm e por isso não tem acesso a este recurso");
    }
}
