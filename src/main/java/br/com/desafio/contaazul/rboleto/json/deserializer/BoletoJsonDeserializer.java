package br.com.desafio.contaazul.rboleto.json.deserializer;

import br.com.desafio.contaazul.rboleto.entity.Boleto;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import org.h2.util.StringUtils;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.UUID;

@JsonComponent
public class BoletoJsonDeserializer extends JsonDeserializer<Boleto> {
    @Override
    public Boleto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
        Boleto boleto = new Boleto();
        String id = getNode(treeNode, "id");
        if(!StringUtils.isNullOrEmpty(id)){
            try{
                boleto.setId(UUID.fromString(id));
            }catch (IllegalArgumentException e){
                //TODO Preparar um tratamento melhor
            }
        }
        boleto.setStatus(getNode(treeNode,"status"));
        boleto.setCustomer(getNode(treeNode,"customer"));
        boleto.setDueDate(getNode(treeNode,"due_date"));
        boleto.setTotalInCents(getNode(treeNode,"total_in_cents"));
        return boleto;
    }

    private String getNode(TreeNode treeNode, String node){
        TextNode jsonNodes = (TextNode) treeNode.get(node);
        if(jsonNodes != null){
            return jsonNodes.asText();
        }

        return "";
    }
}
