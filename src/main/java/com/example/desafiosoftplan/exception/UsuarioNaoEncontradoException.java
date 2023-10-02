package com.example.desafiosoftplan.exception;

public class UsuarioNaoEncontradoException extends Exception {
    public UsuarioNaoEncontradoException() {
        super("Usuário não encontrado");
    }
}
