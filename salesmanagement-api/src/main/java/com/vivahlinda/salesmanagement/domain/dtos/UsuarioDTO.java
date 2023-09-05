package com.vivahlinda.salesmanagement.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UsuarioDTO {

    private Integer id;
    private String nome;
    private String numeroContato;
    private String email;
    private String isAtivo;
    private String role;
    private String endereco;
    private String cpf;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCriacao;

    public UsuarioDTO(Integer id, String nome, String numeroContato, String email, String isAtivo, String role, String endereco, String cpf, LocalDate dataNascimento, LocalDateTime dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.numeroContato = numeroContato;
        this.email = email;
        this.isAtivo = isAtivo;
        this.role = role;
        this.endereco = endereco;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.dataCriacao = dataCriacao;
    }

    public UsuarioDTO(String nome, String email, String role) {
        this.nome = nome;
        this.email = email;
        this.role = role;
    }
}
