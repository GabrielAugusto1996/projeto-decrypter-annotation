package br.com.projeto.decrypter.annotation.exceptions;

public final class EncryptRSAException extends RuntimeException {

    private static final long serialVersionUID = 2835529357577694050L;

    public EncryptRSAException(final Throwable throwable) {
        super("Failed to encrypt value.", throwable);
    }
}