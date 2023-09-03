package com.vivahlinda.salesmanagement.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NamedQuery(name = "Venda.getAllVenda", query = "select c from Venda c order by c.id desc")

@NamedQuery(name = "Venda.getVendaByUsername", query = "select c from Venda c where c.loginVendedor=:username order by c.id desc")


@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "venda")
public class Venda implements Serializable {
    public static final Long serialVersionUid = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "nomeCliente")
    private String nomeCliente;

    @Column(name = "cpfCliente")
    private String cpfCliente;

    @Column(name = "emailCliente")
    private String emailCliente;

    @Column(name = "noContatoCliente")
    private String noContatoCliente;

    @Column(name = "formaPagamento")
    private String formaPagamento;

    @Column(name = "totalCompra", precision = 19, scale = 2)
    private BigDecimal totalCompra;

    @Column(name = "detalheProdutosVendidos", columnDefinition = "json")
    private String detalheProdutosVendidos;

    @Column(name = "loginVendedor")
    private String loginVendedor;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataVenda;
}
