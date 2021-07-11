package br.com.projeto.decrypter.annotation.exceptions.security;

public final class DecryptAESException extends DecryptException {

    private static final long serialVersionUID = -6681664852092876004L;

    public DecryptAESException(final Throwable throwable) {
        super("Failed to decrypt value using AES.", throwable);
    }
}