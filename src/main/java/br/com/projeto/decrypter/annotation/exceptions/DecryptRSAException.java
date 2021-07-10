package br.com.projeto.decrypter.annotation.exceptions;

public final class DecryptRSAException extends RuntimeException {

    private static final long serialVersionUID = 5337510698677242631L;

    public DecryptRSAException(final Throwable throwable) {
        super("Failed to decrypt value.", throwable);
    }
}