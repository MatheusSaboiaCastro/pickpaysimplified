package com.pickpaysimplified.infra;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pickpaysimplified.dtos.ExceptionDTO;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDTO> threatDuplicateEntry(DataIntegrityViolationException ex) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("This account is already taken", "400");
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDTO> threat404(EntityNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> threatGeneralException(Exception ex) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(ex.getMessage(), "500");
        return ResponseEntity.badRequest().body(exceptionDTO);
    }
}
