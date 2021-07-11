package br.com.projeto.decrypter.annotation.exceptions.security;

public final class EncryptRSAException extends EncryptException {

    private static final long serialVersionUID = -4952975530264583095L;

    public EncryptRSAException(final Throwable throwable) {
        super("Failed to encrypt value using RSA.", throwable);
    }
}