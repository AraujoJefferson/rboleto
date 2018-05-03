package br.com.desafio.contaazul.rbankslip.controller;

import br.com.desafio.contaazul.rbankslip.business.BankslipRepository;
import br.com.desafio.contaazul.rbankslip.configuration.MensageResource;
import br.com.desafio.contaazul.rbankslip.entity.Bankslip;
import br.com.desafio.contaazul.rbankslip.entity.BankslipCalculate;
import br.com.desafio.contaazul.rbankslip.enumerate.BankslipStatusEnum;
import br.com.desafio.contaazul.rbankslip.exception.BankslipDoesNotExistException;
import br.com.desafio.contaazul.rbankslip.exception.BankslipUnauthorizedCancelException;
import br.com.desafio.contaazul.rbankslip.exception.BankslipUnauthorizedPayException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

import static br.com.desafio.contaazul.rbankslip.util.BankslipConstant.*;
import static br.com.desafio.contaazul.rbankslip.util.ConstantApplication.UUID_PATTERN;

@RestController
@RequestMapping("/rest")
@Validated
public class BankslipController {
    private static final Logger logger = LogManager.getLogger(BankslipController.class);
    private final BankslipRepository repositorio;

    @Autowired
    public BankslipController(BankslipRepository repositorio) {
        this.repositorio = repositorio;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/bankslips")
    public ResponseEntity<String> createBankslip(@RequestBody Bankslip bankslip) {
        repositorio.save(bankslip);
        logger.info(MensageResource.getMensagem(BANKSLIPS_SAVE_OK_201));
        return new ResponseEntity<>(MensageResource.getMensagem(BANKSLIPS_SAVE_OK_201),
                HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/bankslips")
    public ResponseEntity<Iterable> listBankslip() {
        Iterable<Bankslip> all = repositorio.findAll();
        logger.info(MensageResource.getMensagem(BANKSLIPS_LIST_OK_200));
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/bankslips/{" + FIELD_NAME_UUID + "}")
    public ResponseEntity<BankslipCalculate> detailBankslip(@PathVariable(FIELD_NAME_UUID) @Pattern(regexp = UUID_PATTERN) @NotNull String uuid) {
        Bankslip byId = repositorio.findById(UUID.fromString(uuid)).orElseThrow(BankslipDoesNotExistException::new);
        BankslipCalculate bankslipCalculate = new BankslipCalculate(byId);
        logger.info(MensageResource.getMensagem(BANKSLIPS_DETAIL_OK_200));
        return new ResponseEntity<>(bankslipCalculate, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/bankslips/{id}/pay")
    public ResponseEntity<String> payBankslip(@PathVariable(FIELD_NAME_UUID) @Pattern(regexp = UUID_PATTERN) @NotNull String uuid) {
        Bankslip byId = repositorio.findById(UUID.fromString(uuid)).orElseThrow(BankslipDoesNotExistException::new);
        if (BankslipStatusEnum.PENDING.getCodigo().equalsIgnoreCase(byId.getStatus())) {
            byId.setStatus(BankslipStatusEnum.PAID.toString());
            repositorio.save(byId);
            logger.info(MensageResource.getMensagem(BANKSLIPS_PAY_OK_200));
            return new ResponseEntity<>(MensageResource.getMensagem(BANKSLIPS_PAY_OK_200), HttpStatus.OK);
        }
        throw new BankslipUnauthorizedPayException();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/bankslips/{" + FIELD_NAME_UUID + "}/cancel")
    public ResponseEntity<String> cancelBankslip(@PathVariable(FIELD_NAME_UUID) @Pattern(regexp = UUID_PATTERN) @NotNull String uuid) {
        Bankslip byId = repositorio.findById(UUID.fromString(uuid)).orElseThrow(BankslipDoesNotExistException::new);
        if (BankslipStatusEnum.PENDING.getCodigo().equalsIgnoreCase(byId.getStatus())) {
            byId.setStatus(BankslipStatusEnum.CANCELED.toString());
            repositorio.save(byId);
            logger.info(MensageResource.getMensagem(BANKSLIPS_CANCEL_OK_200));
            return new ResponseEntity<>(MensageResource.getMensagem(BANKSLIPS_CANCEL_OK_200), HttpStatus.OK);
        }
        throw new BankslipUnauthorizedCancelException();
    }
}
