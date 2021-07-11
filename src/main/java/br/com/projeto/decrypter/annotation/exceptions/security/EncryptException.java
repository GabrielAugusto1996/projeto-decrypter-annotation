package br.com.projeto.decrypter.annotation.exceptions.security;

public class EncryptException extends RuntimeException {

    private static final long serialVersionUID = 2835529357577694050L;

    public EncryptException(final Throwable throwable) {
        super("Failed to encrypt value.", throwable);
    }

    public EncryptException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}