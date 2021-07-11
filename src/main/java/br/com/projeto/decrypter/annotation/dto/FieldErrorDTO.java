package br.com.projeto.decrypter.annotation.dto;

import java.io.Serializable;

public class FieldErrorDTO implements Serializable {

    private static final long serialVersionUID = 70883332162189909L;
    private final String campo;
    private final String mensagem;

    public FieldErrorDTO(String campo, String mensagem) {
        this.campo = campo;

        this.mensagem = mensagem;
    }

    public String getCampo() {
        return campo;
    }

    public String getMensagem() {
        return mensagem;
    }
}
