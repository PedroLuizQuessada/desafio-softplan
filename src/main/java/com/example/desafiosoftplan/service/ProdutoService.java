package com.example.desafiosoftplan.service;

import com.example.desafiosoftplan.exception.ProdutoNaoEncontradoException;
import com.example.desafiosoftplan.model.Produto;
import com.example.desafiosoftplan.repo.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> getAllProdutos() {
        return produtoRepository.findAll();
    }

    public Produto getProdutoById(Long id) throws ProdutoNaoEncontradoException {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            return produtoOptional.get();
        }
        else {
            throw new ProdutoNaoEncontradoException(id);
        }
    }

    public List<Produto> getProdutosByNome(String nome) {
        return produtoRepository.getByNome(nome);
    }

    public Produto addProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto updateProduto(Produto produto) throws ProdutoNaoEncontradoException {
        getProdutoById(produto.getId());
        return produtoRepository.save(produto);
    }

    public void deleteProdutoById(Long id) throws ProdutoNaoEncontradoException {
        getProdutoById(id);
        produtoRepository.deleteById(id);
    }
}
