package com.srm.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Test
    void handleMoedaNaoEncontradaException_DeveRetornarNotFound() {
        MoedaNaoEncontradaException ex = new MoedaNaoEncontradaException("USD");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleMoedaNaoEncontradaException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Moeda não encontrada", responseBody.getError());
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

        ResponseEntity<ValidationErrorResponse> response = exceptionHandler.handleValidationExceptions(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ValidationErrorResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Erro de validação", responseBody.getError());
        assertEquals("Um ou mais campos estão inválidos", responseBody.getMessage());
        assertTrue(responseBody.getErrors().containsKey("field"));
    }

    @Test
    void handleHttpRequestMethodNotSupported_DeveRetornarMethodNotAllowed() {
        HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("POST");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleMethodNotSupported(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        ErrorResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Método não permitido", responseBody.getError());
    }

    @Test
    void handleHttpMediaTypeNotSupported_DeveRetornarUnsupportedMediaType() {
        HttpMediaTypeNotSupportedException ex = new HttpMediaTypeNotSupportedException("text/plain");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleMediaTypeNotSupported(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
        ErrorResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Tipo de mídia não suportado", responseBody.getError());
    }

    @Test
    void handleHttpMessageNotReadable_DeveRetornarBadRequest() {
        HttpMessageConversionException ex = new HttpMessageConversionException("Invalid JSON");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleMessageNotReadable(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("JSON inválido", responseBody.getError());
        assertEquals("O corpo da requisição contém um JSON mal formatado ou inválido", responseBody.getMessage());
    }

    @Test
    void handleGlobalException_DeveRetornarInternalServerError() {
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleTransacaoException(new TransacaoException("Test exception"));

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Erro na transação", responseBody.getError());
        assertEquals("Test exception", responseBody.getMessage());
    }
} 