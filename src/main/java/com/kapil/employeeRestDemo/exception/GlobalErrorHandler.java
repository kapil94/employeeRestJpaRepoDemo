package com.kapil.employeeRestDemo.exception;

import com.kapil.employeeRestDemo.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalErrorHandler {


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(BaseException ex,
                                                         HttpServletRequest request){

        ErrorResponse errorResponse= new ErrorResponse(
                LocalDateTime.now(),
                ex.getErrorStatus().value(),
                ex.getErrorStatus().getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()

        );

        return ResponseEntity.status(ex.getErrorStatus())
                .body(errorResponse);
    }


}
