package br.com.desafio.contaazul.rbankslip.exception;

import br.com.desafio.contaazul.rbankslip.configuration.MensageResource;
import br.com.desafio.contaazul.rbankslip.util.ConstantApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(MensageResource.getMensagem("bankslips.save.empty.400"), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        BindingResult bindingResult = ex.getBindingResult();

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(MensageResource.getMensagem("bankslips.save.fields.422"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(MensageResource.getMensagem("bankslips.save.empty.400"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BankslipDoesNotExistException.class})
    protected ResponseEntity<Object> handleDoesNotExist() {
        return new ResponseEntity<Object>(MensageResource.getMensagem("bankslips.not.exist.404"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {BankslipInvalidException.class})
    protected ResponseEntity<Object> handleInvalid() {
        return new ResponseEntity<Object>(MensageResource.getMensagem("bankslips.invalid.400"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BankslipInvalidFieldsException.class})
    protected ResponseEntity<Object> handleInvalidFields() {
        return new ResponseEntity<Object>(MensageResource.getMensagem("bankslips.save.fields.422"), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            if (violation.getPropertyPath().toString().contains(ConstantApplication.FIELD_NAME_UUID)) {
                return new ResponseEntity<Object>(MensageResource.getMensagem("bankslips.invalid.400"), HttpStatus.BAD_REQUEST);
            }
            /*errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());*/
        }
        return new ResponseEntity<Object>(MensageResource.getMensagem("bankslips.save.fields.422"), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}