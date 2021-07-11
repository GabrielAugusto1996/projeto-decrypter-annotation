package br.com.projeto.decrypter.annotation.exceptions.security;

public final class EncryptAESException extends EncryptException {

    private static final long serialVersionUID = -4952975530264583095L;

    public EncryptAESException(final Throwable throwable) {
        super("Failed to encrypt value using AES.", throwable);
    }
}