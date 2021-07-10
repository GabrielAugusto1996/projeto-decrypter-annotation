package br.com.projeto.decrypter.annotation.exceptions;

import org.springframework.validation.FieldError;

import java.util.List;

public final class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = -4481910670357739824L;

    private final List<FieldError> fieldErrors;

    public BadRequestException(List<FieldError> fieldErrors) {
        super();
        this.fieldErrors = fieldErrors;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}