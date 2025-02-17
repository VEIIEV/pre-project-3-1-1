package org.example.preproject231.controller.advices;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@ControllerAdvice
public class GlobalValidationExceptionHandler extends DefaultHandlerExceptionResolver {


    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> handleValidationExceptions(EmptyResultDataAccessException ex) {

        String errors = ex.getMessage();
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


}
