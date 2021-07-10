package br.com.projeto.decrypter.annotation.exceptions;

import br.com.projeto.decrypter.annotation.dto.ErroDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomExceptionHandler {

    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErroDTO> getBadRequestExceptionHandler(final BadRequestException badRequestException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErroDTO("Ocorreu algum erro durante a validação.", badRequestException.getFieldErrors()));
    }
}