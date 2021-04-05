package com.loanstreet.loansservice.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {

    private enum ErrorMessages {
        /** Missing Arguments from Request **/
        MISSING_ARGUMENTS("An argument must be provided: %s"),

        /** Entity not found in the repository **/
        ENTITY_NOT_FOUND("Could not find entity with id %s"),

        /** Bad request JSON body input. */
        ERROR_READING_REQUEST_BODY("Failed to parse request body, please make sure all attributes are of the correct type and JSON format is valid."),

        /** Bad request JSON body input. */
        BAD_REQUEST_JSON_BODY_INPUT("Error parsing %s. Expected: %s, actual value: %s.");

        private final String message;

        ErrorMessages(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception ex) {
        return new ResponseEntity<>(ex.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { MandatoryArgumentsMissingException.class })
    public ResponseEntity<Object> handleMandatoryArgumentsMissingException(MandatoryArgumentsMissingException ex) {
        String errorMessage = String.format(ErrorMessages.MISSING_ARGUMENTS.getMessage(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { EntityNotFoundException.class })
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        String errorMessage = String.format(ErrorMessages.ENTITY_NOT_FOUND.getMessage(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        String errorMessage = ErrorMessages.ERROR_READING_REQUEST_BODY.getMessage();

        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException cause = (InvalidFormatException) ex.getCause();
            String fieldName = cause.getPath().get(0).getFieldName();
            String fieldType = cause.getTargetType().toString();
            String fieldValue = cause.getValue().toString();

            errorMessage = String.format(ErrorMessages.BAD_REQUEST_JSON_BODY_INPUT.getMessage(), fieldName, fieldType, fieldValue);
        }

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
