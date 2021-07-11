package br.com.projeto.decrypter.annotation.exceptions.security;

public class DecryptException extends RuntimeException {

    private static final long serialVersionUID = 5337510698677242631L;

    public DecryptException(final Throwable throwable) {
        super("Failed to decrypt value.", throwable);
    }

    public DecryptException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}