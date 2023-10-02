package com.example.desafiosoftplan.repo;

import com.example.desafiosoftplan.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query("SELECT p FROM Produto p WHERE p.nome ILIKE %?1%")
    List<Produto> getByNome(String nome);
}
