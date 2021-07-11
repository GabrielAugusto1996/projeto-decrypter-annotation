package br.com.projeto.decrypter.annotation.exceptions;

import br.com.projeto.decrypter.annotation.dto.ErroDTO;
import br.com.projeto.decrypter.annotation.dto.FieldErrorDTO;
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
    @ExceptionHandler(DecryptRSAException.class)
    protected ResponseEntity<ErroDTO> getDecryptRSAExceptionHandler(final DecryptRSAException decryptRSAException) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErroDTO(decryptRSAException.getMessage(), null));
    }

    @ResponseBody
    @ExceptionHandler(EncryptRSAException.class)
    protected ResponseEntity<ErroDTO> getEncryptRSAExceptionHandler(final EncryptRSAException encryptRSAException) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErroDTO(encryptRSAException.getMessage(), null));
    }
}