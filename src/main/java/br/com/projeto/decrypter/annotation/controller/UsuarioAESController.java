package br.com.projeto.decrypter.annotation.controller;

import br.com.projeto.decrypter.annotation.aop.aes.DecryptAES;
import br.com.projeto.decrypter.annotation.dto.UsuarioDTO;
import br.com.projeto.decrypter.annotation.model.Usuario;
import br.com.projeto.decrypter.annotation.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/usuarios_aes")
public class UsuarioAESController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioAESController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@RequestBody @DecryptAES(fields = {"email", "senha"}) UsuarioDTO usuarioDTO, BindingResult bindingResult) {
        final Usuario usuario = this.usuarioRepository.save(usuarioDTO.toModel());

        return ResponseEntity
                .created(URI.create(String.format("/usuarios/%s", usuario.getId())))
                .body(usuario.toDTO());
    }
}