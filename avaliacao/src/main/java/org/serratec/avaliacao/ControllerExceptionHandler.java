package org.serratec.avaliacao;

import org.springframework.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {


        List<String> erros = new ArrayList<>();
        for (FieldError err: ex.getBindingResult().getFieldErrors()){
            erros.add(err.getField() + ": " + err.getDefaultMessage());
        }
                
        ErroResposta erroResposta = new ErroResposta(status.value(),
        		"Existem campos inválidos, Confira!", LocalDateTime.now(),erros);
        		
        return super.handleExceptionInternal(ex, erroResposta, headers, status, request);
    }
	
}
