package br.com.projeto.decrypter.annotation.processor;

import br.com.projeto.decrypter.annotation.exceptions.BadRequestException;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

class ValidatorProcessorTest {

    private final Validator validator;

    ValidatorProcessorTest() {
        this.validator = mock(Validator.class);
    }

    @Test
    void execute_ThrowsBadRequest_QuandoEncontrarErros() {
        assertThrows(BadRequestException.class, () -> {
            final BindingResult bindingResult = mock(BindingResult.class);
            when(bindingResult.hasErrors()).thenReturn(TRUE);

            final Object obj = mock(Object.class);

            doNothing().when(validator).validate(obj, bindingResult);

            new ValidatorProcessor(validator).execute(obj, bindingResult);
        });
    }
}