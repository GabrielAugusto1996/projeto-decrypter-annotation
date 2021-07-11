package br.com.projeto.decrypter.annotation.dto;

import java.io.Serializable;
import java.util.Collection;

public class ErroDTO implements Serializable {

    private static final long serialVersionUID = -8797309226582033951L;

    private final String mensagem;
    private final Collection<FieldErrorDTO> fieldErrors;

    public ErroDTO(String mensagem, Collection<FieldErrorDTO> fieldErrors) {
        this.mensagem = mensagem;
        this.fieldErrors = fieldErrors;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Collection<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }
}
