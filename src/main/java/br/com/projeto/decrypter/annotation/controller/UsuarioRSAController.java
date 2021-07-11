package br.com.projeto.decrypter.annotation.controller;

import br.com.projeto.decrypter.annotation.aop.DecryptRSA;
import br.com.projeto.decrypter.annotation.dto.UsuarioDTO;
import br.com.projeto.decrypter.annotation.model.Usuario;
import br.com.projeto.decrypter.annotation.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/usuarios_rsa")
public class UsuarioRSAController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioRSAController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@RequestBody @DecryptRSA(fields = {"email", "senha"}) UsuarioDTO usuarioDTO) {
        final Usuario usuario = this.usuarioRepository.save(usuarioDTO.toModel());

        return ResponseEntity
                .created(URI.create(String.format("/usuarios/%s", usuario.getId())))
                .body(usuario.toDTO());
    }
}