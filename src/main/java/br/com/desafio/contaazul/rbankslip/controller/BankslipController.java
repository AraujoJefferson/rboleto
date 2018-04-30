package br.com.desafio.contaazul.rbankslip.controller;

import br.com.desafio.contaazul.rbankslip.business.BankslipRepository;
import br.com.desafio.contaazul.rbankslip.configuration.MensageResource;
import br.com.desafio.contaazul.rbankslip.entity.Bankslip;
import br.com.desafio.contaazul.rbankslip.entity.BankslipCalculate;
import br.com.desafio.contaazul.rbankslip.enumerate.BankslipStatusEnum;
import br.com.desafio.contaazul.rbankslip.exception.BankslipDoesNotExistException;
import br.com.desafio.contaazul.rbankslip.exception.BankslipUnauthorizedCancelException;
import br.com.desafio.contaazul.rbankslip.exception.BankslipUnauthorizedPayException;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

import static br.com.desafio.contaazul.rbankslip.util.ConstantApplication.*;

@RestController
@RequestMapping("/rest")
@Validated
public class BankslipController {
    private final BankslipRepository repositorio;

    @Autowired
    public BankslipController(BankslipRepository repositorio) {
        this.repositorio = repositorio;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/bankslips")
    public ResponseEntity<Object> createBankslip(@RequestBody Bankslip bankslip) {

        repositorio.save(bankslip);

        return new ResponseEntity<Object>(MensageResource.getMensagem("bankslips.save.ok.201"),
                HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/bankslips")
    public ResponseEntity<Object> listBankslip() {
        return new ResponseEntity<Object>(repositorio.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/bankslips/{" + FIELD_NAME_UUID + "}")
    public ResponseEntity<Object> calculateBankslip(@PathVariable @JsonProperty(FIELD_NAME_UUID) @Pattern(regexp = UUID_PATTERN) @NotNull String uuid) {
        Bankslip byId = repositorio.findById(UUID.fromString(uuid)).orElseThrow(BankslipDoesNotExistException::new);
        BankslipCalculate bankslipCalculate = new BankslipCalculate(byId);

        return new ResponseEntity<Object>(bankslipCalculate, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/bankslips/{" + FIELD_NAME_UUID + "}/pay")
    public ResponseEntity<Object> payBankslip(@PathVariable @JsonProperty(FIELD_NAME_UUID) @Pattern(regexp = UUID_PATTERN) @NotNull String uuid) {
        Bankslip byId = repositorio.findById(UUID.fromString(uuid)).orElseThrow(BankslipDoesNotExistException::new);
        if(BankslipStatusEnum.PENDING.getCodigo().equalsIgnoreCase(byId.getStatus())){
            byId.setStatus(BankslipStatusEnum.PAID.toString());
            repositorio.save(byId);
            return new ResponseEntity<Object>(MensageResource.getMensagem("bankslips.pay.ok.200"), HttpStatus.OK);
        }
        throw new BankslipUnauthorizedPayException();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/bankslips/{" + FIELD_NAME_UUID + "}/cancel")
    public ResponseEntity<Object> cancelBankslip(@PathVariable @JsonProperty(FIELD_NAME_UUID) @Pattern(regexp = UUID_PATTERN) @NotNull String uuid) {
        Bankslip byId = repositorio.findById(UUID.fromString(uuid)).orElseThrow(BankslipDoesNotExistException::new);
        if(BankslipStatusEnum.PENDING.getCodigo().equalsIgnoreCase(byId.getStatus())){
            byId.setStatus(BankslipStatusEnum.CANCELED.toString());
            repositorio.save(byId);
            return new ResponseEntity<Object>(MensageResource.getMensagem("bankslips.cancel.ok.200"), HttpStatus.OK);
        }
        throw new BankslipUnauthorizedCancelException();
    }
}
