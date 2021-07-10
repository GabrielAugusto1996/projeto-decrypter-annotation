package br.com.projeto.decrypter.annotation.processor;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

@Component
public class ValidatorProcessor {

    private final Validator validator;

    public ValidatorProcessor(Validator validator) {
        this.validator = validator;
    }

    public void execute(Object object, BindingResult result) {
        this.validator.validate(object, result);


    }
}