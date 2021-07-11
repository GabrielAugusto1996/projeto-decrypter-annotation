package br.com.projeto.decrypter.annotation.controller;

import br.com.projeto.decrypter.annotation.dto.UsuarioDTO;
import br.com.projeto.decrypter.annotation.model.Usuario;
import br.com.projeto.decrypter.annotation.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<Collection<UsuarioDTO>> findAll() {
        return ResponseEntity
                .ok(this.usuarioRepository.findAll().stream().map(Usuario::toDTO).collect(Collectors.toSet()));
    }
}