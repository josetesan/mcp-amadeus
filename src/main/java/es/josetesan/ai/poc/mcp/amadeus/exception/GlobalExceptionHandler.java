package es.josetesan.ai.poc.mcp.amadeus.exception;

import com.amadeus.exceptions.ResponseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<Map<String, Object>> handleAmadeusException(ResponseException e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Amadeus API Error");
        errorResponse.put("code", e.getCode());
        errorResponse.put("message", e.getDescription());
        
        return ResponseEntity.status(e.getResponse().getStatusCode()).body(errorResponse);
    }
    
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, Object>> handleMissingParams(MissingServletRequestParameterException e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Missing Required Parameter");
        errorResponse.put("parameter", e.getParameterName());
        errorResponse.put("message", e.getMessage());
        
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Internal Server Error");
        errorResponse.put("message", e.getMessage());
        
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}