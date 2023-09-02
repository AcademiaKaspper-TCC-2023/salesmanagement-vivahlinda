package com.vivahlinda.salesmanagement.domain.dtos;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProdutoDTO {

    Integer id;
    String nome;
    String descricao;
    BigDecimal preco;
    String status;
    Integer categoriaId;
    String nomeCategoria;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Integer id, String nome, String descricao, BigDecimal preco, String status, Integer categoriaId, String nomeCategoria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.status = status;
        this.categoriaId = categoriaId;
        this.nomeCategoria = nomeCategoria;
    }

    public ProdutoDTO(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public ProdutoDTO(Integer id, String nome, String descricao, BigDecimal preco) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }
}
