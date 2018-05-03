package br.com.desafio.contaazul.rbankslip.json.serializer;

import br.com.desafio.contaazul.rbankslip.entity.Bankslip;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.SimpleDateFormat;

import static br.com.desafio.contaazul.rbankslip.util.BankslipConstant.*;

@JsonComponent
public class BankslipJsonSerializer extends JsonSerializer<Bankslip> {
    @Override
    public void serialize(Bankslip bankslip, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField(FIELD_NAME_UUID, bankslip.getId().toString());
        jsonGenerator.writeStringField(FIELD_NAME_DUE_DATE, new SimpleDateFormat("yyyy-MM-dd").format(bankslip.getDueDate()));
        jsonGenerator.writeStringField(FIELD_NAME_TOTAL_IN_CENT, bankslip.getTotalInCents().toString());
        jsonGenerator.writeStringField(FIELD_NAME_CUSTOMER, bankslip.getCustomer());
        jsonGenerator.writeEndObject();
    }
}
