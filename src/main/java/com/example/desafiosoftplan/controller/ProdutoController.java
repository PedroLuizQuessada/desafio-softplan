package com.example.desafiosoftplan.controller;

import com.example.desafiosoftplan.exception.ProdutoNaoEncontradoException;
import com.example.desafiosoftplan.model.Produto;
import com.example.desafiosoftplan.service.ProdutoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProdutoController {
    private final ProdutoService produtoService;
    private final static String MENSAGEM_ERRO_HEADER = "MensagemErro";

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/getAllProdutos")
    public ResponseEntity<List<Produto>> getAllProdutos() {
        try {
            List<Produto> produtos = produtoService.getAllProdutos();
            return new ResponseEntity<>(produtos, HttpStatus.OK);
        }
        catch (Exception e) {
            return ResponseEntity.status(identificarCodigoResposta(e))
                    .headers(gerarCabecalhoResposta(e))
                    .body(null);
        }
    }

    @GetMapping("/getProdutoById/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        try {
            Produto produto = produtoService.getProdutoById(id);
            return new ResponseEntity<>(produto, HttpStatus.OK);
        }
        catch (Exception e) {
            return ResponseEntity.status(identificarCodigoResposta(e))
                    .headers(gerarCabecalhoResposta(e))
                    .body(null);
        }
    }

    @PostMapping("/addProduto")
    public ResponseEntity<Produto> addProduto(@RequestBody Produto produto) {
        try {
            Produto produtoSalvo = produtoService.addProduto(produto);
            return new ResponseEntity<>(produtoSalvo, HttpStatus.OK);
        }
        catch (Exception e) {
            return ResponseEntity.status(identificarCodigoResposta(e))
                    .headers(gerarCabecalhoResposta(e))
                    .body(null);
        }
    }

    @PostMapping("/updateProduto")
    public ResponseEntity<Produto> updateProduto(@RequestBody Produto produto) {
        try {
            Produto produtoAtualizado = produtoService.updateProduto(produto);
            return new ResponseEntity<>(produtoAtualizado, HttpStatus.OK);
        }
        catch (Exception e) {
            return ResponseEntity.status(identificarCodigoResposta(e))
                    .headers(gerarCabecalhoResposta(e))
                    .body(null);
        }
    }

    @DeleteMapping("/deleteProdutoById/{id}")
    public ResponseEntity<HttpStatus> deleteProdutoById(@PathVariable Long id) {
        try {
            produtoService.deleteProdutoById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return ResponseEntity.status(identificarCodigoResposta(e))
                    .headers(gerarCabecalhoResposta(e))
                    .body(null);
        }
    }

    private HttpStatus identificarCodigoResposta(Exception e) {
        if (e instanceof ProdutoNaoEncontradoException) {
            return HttpStatus.NOT_FOUND;
        }
        else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private HttpHeaders gerarCabecalhoResposta(Exception e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(MENSAGEM_ERRO_HEADER,
                e.getMessage());
        return responseHeaders;
    }
}
