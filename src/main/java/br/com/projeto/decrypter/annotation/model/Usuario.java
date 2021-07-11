package br.com.projeto.decrypter.annotation.model;

import br.com.projeto.decrypter.annotation.dto.UsuarioDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 8156338661930785891L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, length = 65)
    private String nome;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "senha", nullable = false, length = 25)
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

    public UsuarioDTO toDTO() {
        final UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setId(this.id);
        usuarioDTO.setEmail(this.email);
        usuarioDTO.setNome(this.nome);
        usuarioDTO.setSenha(this.senha);

        return usuarioDTO;
    }
}