package com.viksingh.catalogservice.exception;

import com.viksingh.catalogservice.dto.response.ApiResponse;
import com.viksingh.catalogservice.dto.response.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler  {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        final Map<String,String> errors = new HashMap<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put("message",error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(ApiResponse.response(HttpStatus.BAD_REQUEST,errors));
    }

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<ResponseDTO> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex){
        return ResponseEntity.badRequest().body(ApiResponse.response(HttpStatus.BAD_REQUEST,"Duplicate sku number"));
    }

    @ExceptionHandler(value = {
            CategoryNotFoundException.class,
            ProductNotFoundException.class,
    })
    public <T extends RuntimeException> ResponseEntity<ResponseDTO> handleApiRequestException(final T e) {
        log.info("**ApiExceptionHandler controller, handle API request*\n");
        final var badRequest = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(ApiResponse.response(badRequest,e.getMessage()),badRequest);
    }
}
