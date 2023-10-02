package com.example.desafiosoftplan.exception;

public class ProdutoNaoEncontradoException extends Exception {
    public ProdutoNaoEncontradoException(Long id) {
        super(String.format("Produto de ID %d não encontrado", id));
    }
}
