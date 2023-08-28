package com.vivahlinda.salesmanagement.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vivahlinda.salesmanagement.domain.enums.Perfil;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "Usuario")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
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

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

    @Column(name = "status")
    private Boolean isAtivo;

    @Column(name = "role")
    private String role;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PERFIS")
    private Set<Integer> perfis = new HashSet<>();

    @Column(name = "dataNascimento")
    private LocalDate dataNascimento;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(name = "endereco")
    private String endereco;

    public Usuario(Integer id, String nome, String numeroContato, String email, String senha, Boolean isAtivo, String role, LocalDate dataNascimento, String endereco) {
        this.id = id;
        this.nome = nome;
        this.numeroContato = numeroContato;
        this.email = email;
        this.senha = senha;
        this.isAtivo = isAtivo;
        this.role = role;
        this.dataNascimento = dataNascimento;
        this.dataCriacao = LocalDateTime.now();
        this.endereco = endereco;
        addPerfil(Perfil.CLIENTE);  // adiciona perfil CLIENTE automaticamente
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }
}
