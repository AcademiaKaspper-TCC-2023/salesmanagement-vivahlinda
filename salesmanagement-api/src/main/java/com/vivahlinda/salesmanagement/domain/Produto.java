package com.vivahlinda.salesmanagement.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@NamedQuery(name = "Produto.getAllProduto", query = "select new com.vivahlinda.salesmanagement.domain.dtos.ProdutoDTO(p.id,p.nome,p.descricao,p.preco,p.status,p.categoria.id,p.categoria.nome) from Produto p")

@NamedQuery(name = "Produto.updateProdutoStatus", query = "update Produto p set p.status=:status where p.id=:id")

@NamedQuery(name = "Produto.getProdutoByCategoria", query = "select new com.vivahlinda.salesmanagement.domain.dtos.ProdutoDTO(p.id, p.nome) from Produto p where p.categoria.id=:id and p.status='true'")

@NamedQuery(name = "Produto.getProdutoById", query = "select new com.vivahlinda.salesmanagement.domain.dtos.ProdutoDTO(p.id, p.nome, p.descricao, p.preco) from Produto p where p.id=:id")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "produto")
public class Produto implements Serializable {

    public static final Long serialVersionUid = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "categoria_fk", nullable = false)
    private Categoria categoria;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco", precision = 19, scale = 2)
    private BigDecimal preco;

    @Column(name = "status")
    private String status;
}
