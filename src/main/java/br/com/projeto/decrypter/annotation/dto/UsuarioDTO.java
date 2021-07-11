package br.com.projeto.decrypter.annotation.dto;

import br.com.projeto.decrypter.annotation.model.Usuario;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class UsuarioDTO implements Serializable {

    private static final long serialVersionUID = -5479323312077790597L;

    private Long id;

    @NotBlank
    @Length(max = 65)
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(max = 25)
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