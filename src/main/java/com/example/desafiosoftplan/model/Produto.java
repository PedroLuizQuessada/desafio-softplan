package com.example.desafiosoftplan.model;

import jakarta.persistence.*;

@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nome;
    @Column(nullable = false, name = "quantidade_estoque")
    private Long qtdadeEstoque;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getQtdadeEstoque() {
        return qtdadeEstoque;
    }

    public void setQtdadeEstoque(Long qtdadeEstoque) {
        this.qtdadeEstoque = qtdadeEstoque;
    }
}
