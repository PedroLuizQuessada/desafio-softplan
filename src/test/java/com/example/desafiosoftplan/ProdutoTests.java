package com.example.desafiosoftplan;

import com.example.desafiosoftplan.exception.ProdutoNaoEncontradoException;
import com.example.desafiosoftplan.model.Produto;
import com.example.desafiosoftplan.service.ProdutoService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
@ComponentScan(basePackages = {"com.example.desafiosoftplan"})
public class ProdutoTests {
    @Autowired
    private ProdutoService produtoService;

    @Test
    public void getAllProdutos() {
        List<Produto> produtos = produtoService.getAllProdutos();
        Assertions.assertThat(produtos).isNotNull();
    }

    @Test
    public void getProdutoById() throws ProdutoNaoEncontradoException {
        Long id = 1L;

        Produto produto = produtoService.getProdutoById(id);
        Assertions.assertThat(produto).isNotNull();
        Assertions.assertThat(produto.getId()).isNotNull();
    }

    @Test
    public void getProdutosByNome() {
        String nome = "panela";

        List<Produto> produtos = produtoService.getProdutosByNome(nome);
        Assertions.assertThat(produtos).isNotNull();
    }

    @Test
    public void addProduto() {
        String nome = "Mesa";
        Long qtdadeEstoque = 7L;
        Double preco = 200.00;

        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setQtdadeEstoque(qtdadeEstoque);
        produto.setPreco(preco);

        Produto produtoSalvo = produtoService.addProduto(produto);
        Assertions.assertThat(produtoSalvo.getId()).isNotNull();
        Assertions.assertThat(produtoSalvo.getId()).isGreaterThan(0L);
    }

    @Test
    public void updateProduto() throws ProdutoNaoEncontradoException {
        Long id = 1L;
        String novoNome = "Mesa de mármore";

        Produto produto = produtoService.getProdutoById(id);
        produto.setNome(novoNome);

        Produto produtoSalvo = produtoService.updateProduto(produto);
        Assertions.assertThat(produtoSalvo.getNome()).isNotNull();
        Assertions.assertThat(produtoSalvo.getNome()).isEqualTo(novoNome);
    }

    @Test
    public void deleteProdutoById() throws Exception {
        Long id = 1L;

        produtoService.getProdutoById(id);
        produtoService.deleteProdutoById(id);
        try {
            produtoService.getProdutoById(id);
            throw new Exception("Falha ao deletar produto");
        }
        catch (ProdutoNaoEncontradoException ignored) {

        }
    }
}
