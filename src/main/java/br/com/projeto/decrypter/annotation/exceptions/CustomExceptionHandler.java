package br.com.projeto.decrypter.annotation.exceptions;

import br.com.projeto.decrypter.annotation.dto.ErroDTO;
import br.com.projeto.decrypter.annotation.dto.FieldErrorDTO;
import br.com.projeto.decrypter.annotation.exceptions.security.DecryptException;
import br.com.projeto.decrypter.annotation.exceptions.security.EncryptException;
import static java.util.stream.Collectors.toSet;
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
                .body(new ErroDTO("Ocorreu algum erro durante a validação.", badRequestException.getFieldErrors().stream().map(fieldError -> new FieldErrorDTO(fieldError.getField(), fieldError.getDefaultMessage())).collect(toSet())));
    }

    @ResponseBody
    @ExceptionHandler(DecryptException.class)
    protected ResponseEntity<ErroDTO> getDecryptExceptionHandler(final DecryptException decryptException) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErroDTO(decryptException.getMessage(), null));
    }

    @ResponseBody
    @ExceptionHandler(EncryptException.class)
    protected ResponseEntity<ErroDTO> getEncryptExceptionHandler(final EncryptException encryptException) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErroDTO(encryptException.getMessage(), null));
    }
}