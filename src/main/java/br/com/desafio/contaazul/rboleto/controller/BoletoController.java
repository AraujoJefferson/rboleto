package br.com.desafio.contaazul.rboleto.controller;

import br.com.desafio.contaazul.rboleto.business.BoletoRepository;
import br.com.desafio.contaazul.rboleto.configuration.MensagemResource;
import br.com.desafio.contaazul.rboleto.entity.Boleto;
import br.com.desafio.contaazul.rboleto.entity.BoletoCalculado;
import br.com.desafio.contaazul.rboleto.enumerate.BoletoStatusEnum;
import br.com.desafio.contaazul.rboleto.validate.BoletoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/rest")
public class BoletoController {
    private ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    private final BoletoRepository repositorio;
    private final BoletoValidator validator;

    @Autowired
    public BoletoController(BoletoRepository repositorio, BoletoValidator validator) {
        this.repositorio = repositorio;
        this.validator = validator;
    }

    @InitBinder("boleto")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/bankslips")
    public ResponseEntity<Object> salvar(@RequestBody @Valid Boleto boleto) {
        repositorio.save(boleto);
        return new ResponseEntity<Object>(MensagemResource.getMensagem("bankslips.save.ok.201"),
                HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/bankslips")
    public ResponseEntity<Object> listar() {
        return new ResponseEntity<Object>(repositorio.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/bankslips/{id}")
    public ResponseEntity<Object> buscar(@PathVariable @NotNull String id) {
        if (!idValido(id)) {
            return new ResponseEntity<Object>(MensagemResource.getMensagem("bankslips.search.invalid.400"), HttpStatus.BAD_REQUEST);
        }
        Optional<Boleto> byId = repositorio.findById(UUID.fromString(id));

        if (byId.isPresent()) {
            return new ResponseEntity<Object>(MensagemResource.getMensagem("bankslips.search.not.exist.404"), HttpStatus.NOT_FOUND);
        }

        BoletoCalculado boletoCalculado = new BoletoCalculado(byId.get());
        return new ResponseEntity<Object>(boletoCalculado, HttpStatus.OK);
    }

    private boolean idValido(@NotNull String id) {
        final Pattern pattern = Pattern.compile("^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$");
        return pattern.matcher(id).matches();
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/bankslips/{id}/pay")
    public ResponseEntity<Object> pagarBoleto(@PathVariable @NotNull String id) {
        if (!idValido(id)) {
            return new ResponseEntity<Object>(MensagemResource.getMensagem("bankslips.search.invalid.400"), HttpStatus.BAD_REQUEST);
        }
        Optional<Boleto> byId = repositorio.findById(UUID.fromString(id));

        if (byId.isPresent()) {
            return new ResponseEntity<Object>(MensagemResource.getMensagem("bankslips.pay.not.exist.404"), HttpStatus.NOT_FOUND);
        }
        byId.get().setStatus(BoletoStatusEnum.PAID.toString());
        repositorio.save(byId.get());
        return new ResponseEntity<Object>(MensagemResource.getMensagem("bankslips.pay.ok.200"), HttpStatus.OK);
    }
}
