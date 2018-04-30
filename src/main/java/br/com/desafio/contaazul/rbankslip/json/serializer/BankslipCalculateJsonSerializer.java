package br.com.desafio.contaazul.rbankslip.json.serializer;

import br.com.desafio.contaazul.rbankslip.entity.Bankslip;
import br.com.desafio.contaazul.rbankslip.entity.BankslipCalculate;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.SimpleDateFormat;

@JsonComponent
public class BankslipCalculateJsonSerializer extends JsonSerializer<BankslipCalculate> {
    @Override
    public void serialize(BankslipCalculate bankslip, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", bankslip.getId().toString());
        jsonGenerator.writeStringField("due_date",new SimpleDateFormat("yyyy-MM-dd").format(bankslip.getDueDate()));
        jsonGenerator.writeStringField("total_in_cents", bankslip.getTotalInCents().toString());
        jsonGenerator.writeStringField("customer", bankslip.getCustomer());
        jsonGenerator.writeStringField("fine", bankslip.getFine());
        jsonGenerator.writeStringField("status", bankslip.getStatus());
        jsonGenerator.writeEndObject();
    }
}
