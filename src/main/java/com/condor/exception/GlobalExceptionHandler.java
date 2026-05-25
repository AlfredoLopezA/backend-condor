package com.condor.exception;

import com.condor.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(ResourceNotFoundException ex) {
        ApiResponse<Object> response = new ApiResponse<>( false, "RESOURCE_NOT_FOUND", ex.getMessage(), null );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(ConstraintViolationException ex) {
        ApiResponse<Object> response = new ApiResponse<>( false, "VALIDATION_ERROR", ex.getMessage(), null );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid value for parameter: " + ex.getName();
        ApiResponse<Object> response = new ApiResponse<>( false, "TYPE_MISMATCH", message, null );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Object>> handleMissingParams(
            MissingServletRequestParameterException ex) {
        ApiResponse<Object> response = new ApiResponse<>( false, "MISSING_PARAMETER", ex.getParameterName() + " IS_REQUIRED", null );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(
        IllegalArgumentException ex) {
            ApiResponse<Object> response = new ApiResponse<>( false, "ILLEGAL_ARGUMENT", ex.getMessage(), null );
            return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception ex) {
        ex.printStackTrace();
        ApiResponse<Object> response = new ApiResponse<>( false, "INTERNAL_SERVER_ERROR", ex.getMessage(), null );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}