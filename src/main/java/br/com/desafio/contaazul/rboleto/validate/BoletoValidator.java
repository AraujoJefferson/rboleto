package br.com.desafio.contaazul.rboleto.validate;

import br.com.desafio.contaazul.rboleto.configuration.MensagemResource;
import br.com.desafio.contaazul.rboleto.entity.Boleto;
import br.com.desafio.contaazul.rboleto.enumerate.BoletoStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class BoletoValidator implements Validator {
    //private final InventoryService inventoryService;

    @Autowired
    public BoletoValidator(){//InventoryService inventoryService) {
       // this.inventoryService = inventoryService;
    }

    @Override
    public boolean supports(Class clazz) {
        return Boleto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Boleto boleto = (Boleto) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dueDate", "422", MensagemResource.getMensagem("bankslips.save.fields.422"));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "totalInCents", "422",MensagemResource.getMensagem("bankslips.save.fields.422"));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customer", "422",MensagemResource.getMensagem("bankslips.save.fields.422"));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", "422",MensagemResource.getMensagem("bankslips.save.fields.422"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            sdf.parse(boleto.getDueDate());
        } catch (ParseException e) {
            errors.rejectValue("dueDate","422",MensagemResource.getMensagem("bankslips.save.fields.422"));
        }
        try{
            Integer.parseInt(boleto.getTotalInCents());
        }catch (Exception e){
            errors.rejectValue("totalInCents","422",MensagemResource.getMensagem("bankslips.save.fields.422"));
        }

        BoletoStatusEnum boletoStatusEnum = BoletoStatusEnum.get(boleto.getStatus());
        if(boletoStatusEnum == null){
            errors.rejectValue("status","422",MensagemResource.getMensagem("bankslips.save.fields.422"));
        }
    }
}
