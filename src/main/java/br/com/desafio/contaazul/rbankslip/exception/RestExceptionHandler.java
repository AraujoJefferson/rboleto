package br.com.desafio.contaazul.rbankslip.exception;

import br.com.desafio.contaazul.rbankslip.configuration.MensageResource;
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

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger loggerREH = LogManager.getLogger(RestExceptionHandler.class);
    public static final String BANKSLIPS_SAVE_EMPTY_400 = "bankslips.save.empty.400";
    public static final String BANKSLIPS_SAVE_FIELDS_422 = "bankslips.save.fields.422";
    public static final String BANKSLIPS_NOT_EXIST_404 = "bankslips.not.exist.404";
    public static final String BANKSLIPS_INVALID_400 = "bankslips.invalid.400";
    public static final String BANKSLIPS_PAY_401 = "bankslips.pay.401";
    public static final String BANKSLIPS_CANCEL_401 = "bankslips.cancel.401";

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        loggerREH.error(MensageResource.getMensagem(BANKSLIPS_SAVE_EMPTY_400));
        return new ResponseEntity<>(MensageResource.getMensagem(BANKSLIPS_SAVE_EMPTY_400), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        BindingResult bindingResult = ex.getBindingResult();

        if (bindingResult.hasErrors()) {
            loggerREH.error(MensageResource.getMensagem(BANKSLIPS_SAVE_FIELDS_422));
            return new ResponseEntity<>(MensageResource.getMensagem(BANKSLIPS_SAVE_FIELDS_422), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        loggerREH.error(MensageResource.getMensagem(BANKSLIPS_SAVE_EMPTY_400));
        return new ResponseEntity<>(MensageResource.getMensagem(BANKSLIPS_SAVE_EMPTY_400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BankslipDoesNotExistException.class})
    protected ResponseEntity<String> handleDoesNotExist() {
        loggerREH.error(MensageResource.getMensagem(BANKSLIPS_NOT_EXIST_404));
        return new ResponseEntity<>(MensageResource.getMensagem(BANKSLIPS_NOT_EXIST_404), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {BankslipInvalidException.class})
    protected ResponseEntity<String> handleInvalid() {
        loggerREH.error(MensageResource.getMensagem(BANKSLIPS_INVALID_400));
        return new ResponseEntity<>(MensageResource.getMensagem(BANKSLIPS_INVALID_400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BankslipInvalidFieldsException.class})
    protected ResponseEntity<String> handleInvalidFields() {
        loggerREH.error(MensageResource.getMensagem(BANKSLIPS_SAVE_FIELDS_422));
        return new ResponseEntity<>(MensageResource.getMensagem(BANKSLIPS_SAVE_FIELDS_422), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {BankslipUnauthorizedPayException.class})
    protected ResponseEntity<String> handleUnauthorizedPayment() {
        loggerREH.error(MensageResource.getMensagem(BANKSLIPS_PAY_401));
        return new ResponseEntity<>(MensageResource.getMensagem(BANKSLIPS_PAY_401), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {BankslipUnauthorizedCancelException.class})
    protected ResponseEntity<Object> handleUnauthorizedCancel() {
        loggerREH.error(MensageResource.getMensagem(BANKSLIPS_CANCEL_401));
        return new ResponseEntity<>(MensageResource.getMensagem(BANKSLIPS_CANCEL_401), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            if (violation.getPropertyPath().toString().contains(ConstantApplication.FIELD_NAME_UUID)) {
                loggerREH.error(MensageResource.getMensagem(BANKSLIPS_INVALID_400));
                return new ResponseEntity<>(MensageResource.getMensagem(BANKSLIPS_INVALID_400), HttpStatus.BAD_REQUEST);
            }
            loggerREH.error(MensageResource.getMensagem(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage()));
        }
        loggerREH.error(MensageResource.getMensagem(BANKSLIPS_SAVE_FIELDS_422));
        return new ResponseEntity<>(MensageResource.getMensagem(BANKSLIPS_SAVE_FIELDS_422), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}