package br.com.desafio.contaazul.rbankslip.exception;

import br.com.desafio.contaazul.rbankslip.configuration.MensageResource;
import br.com.desafio.contaazul.rbankslip.entity.BankslipCalculate;
import br.com.desafio.contaazul.rbankslip.util.ConstantApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LogManager.getLogger(RestExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(MensageResource.getMensagem("bankslips.save.empty.400"));
        return new ResponseEntity<Object>(MensageResource.getMensagem("bankslips.save.empty.400"), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        BindingResult bindingResult = ex.getBindingResult();

        if (bindingResult.hasErrors()) {
            logger.error(MensageResource.getMensagem("bankslips.save.fields.422"));
            return new ResponseEntity<>(MensageResource.getMensagem("bankslips.save.fields.422"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        logger.error(MensageResource.getMensagem("bankslips.save.empty.400"));
        return new ResponseEntity<>(MensageResource.getMensagem("bankslips.save.empty.400"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BankslipDoesNotExistException.class})
    protected ResponseEntity<Object> handleDoesNotExist() {
        logger.error(MensageResource.getMensagem("bankslips.not.exist.404"));
        return new ResponseEntity<Object>(MensageResource.getMensagem("bankslips.not.exist.404"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {BankslipInvalidException.class})
    protected ResponseEntity<Object> handleInvalid() {
        logger.error(MensageResource.getMensagem("bankslips.invalid.400"));
        return new ResponseEntity<Object>(MensageResource.getMensagem("bankslips.invalid.400"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BankslipInvalidFieldsException.class})
    protected ResponseEntity<Object> handleInvalidFields() {
        logger.error(MensageResource.getMensagem("bankslips.save.fields.422"));
        return new ResponseEntity<Object>(MensageResource.getMensagem("bankslips.save.fields.422"), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {BankslipUnauthorizedPayException.class})
    protected ResponseEntity<Object> handleUnauthorizedPayment() {
        logger.error(MensageResource.getMensagem("bankslips.pay.401"));
        return new ResponseEntity<Object>(MensageResource.getMensagem("bankslips.pay.401"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {BankslipUnauthorizedCancelException.class})
    protected ResponseEntity<Object> handleUnauthorizedCancel() {
        logger.error(MensageResource.getMensagem("bankslips.cancel.401"));
        return new ResponseEntity<Object>(MensageResource.getMensagem("bankslips.cancel.401"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            if (violation.getPropertyPath().toString().contains(ConstantApplication.FIELD_NAME_UUID)) {
                logger.error(MensageResource.getMensagem("bankslips.invalid.400"));
                return new ResponseEntity<Object>(MensageResource.getMensagem("bankslips.invalid.400"), HttpStatus.BAD_REQUEST);
            }
            logger.error(MensageResource.getMensagem(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage()));
        }
        logger.error(MensageResource.getMensagem("bankslips.save.fields.422"));
        return new ResponseEntity<Object>(MensageResource.getMensagem("bankslips.save.fields.422"), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}