package br.com.desafio.contaazul.rboleto.json.serializer;

import br.com.desafio.contaazul.rboleto.entity.Boleto;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class BoletoJsonSerializer extends JsonSerializer<Boleto> {
    @Override
    public void serialize(Boleto boleto, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id",boleto.getId().toString());
        jsonGenerator.writeStringField("due_date",boleto.getDueDate());
        jsonGenerator.writeStringField("total_in_cents",boleto.getTotalInCents());
        jsonGenerator.writeStringField("customer",boleto.getCustomer());
        jsonGenerator.writeEndObject();
    }
}
