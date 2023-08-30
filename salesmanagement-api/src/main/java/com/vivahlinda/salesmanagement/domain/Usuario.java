package com.vivahlinda.salesmanagement.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NamedQuery(name = "Usuario.findByEmailId", query = "select u from Usuario u where u.email=:email")

@NamedQuery(name = "Usuario.findAllUsuario", query = "select new com.vivahlinda.salesmanagement.domain.dtos.UsuarioDTO(u.id, u.nome, u.numeroContato, u.email, u.isAtivo, u.role, u.endereco, u.cpf, u.dataNascimento, u.dataCriacao) from Usuario u where u.role='usuario'")

@NamedQuery(name = "Usuario.findAllAdmin", query = "select new com.vivahlinda.salesmanagement.domain.dtos.UsuarioDTO(u.id, u.nome, u.numeroContato, u.email, u.isAtivo, u.role, u.endereco, u.cpf, u.dataNascimento, u.dataCriacao) from Usuario u where u.role='admin'")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "Usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "numeroContato")
    private String numeroContato;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

    @Column(name = "isAtivo")
    private String isAtivo;

    @Column(name = "role")
    private String role;

    @Column(name = "endereco")
    private String endereco;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCriacao = LocalDateTime.now();

}
