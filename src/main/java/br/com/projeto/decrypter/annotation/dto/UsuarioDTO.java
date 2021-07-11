package br.com.projeto.decrypter.annotation.dto;

import br.com.projeto.decrypter.annotation.model.Usuario;

import java.io.Serializable;

public class UsuarioDTO implements Serializable {

    private static final long serialVersionUID = -5479323312077790597L;

    private Long id;
    private String nome;
    private String email;
    private String senha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario toModel() {
        final Usuario usuario = new Usuario();

        usuario.setId(this.id);
        usuario.setEmail(this.email);
        usuario.setNome(this.nome);
        usuario.setSenha(this.senha);

        return usuario;
    }
}