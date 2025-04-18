package com.srm.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.Method;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        webRequest = mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("uri=/api/test");
    }

    @Test
    void handleMoedaNaoEncontradaException_DeveRetornarNotFound() {
        MoedaNaoEncontradaException ex = new MoedaNaoEncontradaException("USD");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleMoedaNaoEncontradaException(ex, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Not Found", responseBody.getError());
        assertEquals("Moeda não encontrada: USD", responseBody.getMessage());
    }

    public void dummyMethod() {
        // This method exists only to provide a valid method for reflection in tests
    }

    @Test
    void handleMethodArgumentNotValid_DeveRetornarBadRequest() throws NoSuchMethodException {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "field", "error message");
        when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(fieldError));
        
        Method method = this.getClass().getMethod("dummyMethod");
        MethodParameter parameter = new MethodParameter(method, -1);
        
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter, bindingResult);

        ResponseEntity<Object> response = exceptionHandler.handleValidationExceptions(ex, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Object responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody instanceof ValidationErrorResponse);
        ValidationErrorResponse errorResponse = (ValidationErrorResponse) responseBody;
        assertEquals("Validation Error", errorResponse.getError());
        assertEquals("Erro de validação nos campos", errorResponse.getMessage());
        assertTrue(errorResponse.getErrors().containsKey("field"));
    }

    @Test
    void handleHttpRequestMethodNotSupported_DeveRetornarMethodNotAllowed() {
        HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("POST");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleMethodNotSupported(ex, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        ErrorResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Method Not Allowed", responseBody.getError());
    }

    @Test
    void handleHttpMediaTypeNotSupported_DeveRetornarUnsupportedMediaType() {
        HttpMediaTypeNotSupportedException ex = new HttpMediaTypeNotSupportedException("text/plain");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleMediaTypeNotSupported(ex, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
        ErrorResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Unsupported Media Type", responseBody.getError());
    }

    @Test
    void handleHttpMessageNotReadable_DeveRetornarBadRequest() {
        HttpInputMessage inputMessage = mock(HttpInputMessage.class);
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Invalid JSON", inputMessage);

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleMessageNotReadable(ex, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Bad Request", responseBody.getError());
        assertEquals("Erro ao processar o JSON enviado", responseBody.getMessage());
    }

    @Test
    void handleGlobalException_DeveRetornarInternalServerError() {
        Exception ex = new Exception("Test exception");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleGlobalException(ex, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ErrorResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Internal Server Error", responseBody.getError());
        assertEquals("Ocorreu um erro interno no servidor", responseBody.getMessage());
    }
} 