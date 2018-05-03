package br.com.desafio.contaazul.rbankslip.json.deserializer;

import br.com.desafio.contaazul.rbankslip.entity.Bankslip;
import br.com.desafio.contaazul.rbankslip.exception.BankslipInvalidFieldsException;
import br.com.desafio.contaazul.rbankslip.util.ConstantApplication;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import org.h2.util.StringUtils;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import static br.com.desafio.contaazul.rbankslip.util.BankslipConstant.*;

@JsonComponent
public class BankslipJsonDeserializer extends JsonDeserializer<Bankslip> {
    @Override
    public Bankslip deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
        Bankslip bankslip = new Bankslip();
        String id = getNode(treeNode, FIELD_NAME_UUID);
        if (!StringUtils.isNullOrEmpty(id)) {
            try {
                bankslip.setId(UUID.fromString(id));
            } catch (IllegalArgumentException e) {
                throw new BankslipInvalidFieldsException(e);
            }
        }
        bankslip.setStatus(getNode(treeNode, FIELD_NAME_STATUS));
        bankslip.setCustomer(getNode(treeNode, FIELD_NAME_CUSTOMER));
        try {
            bankslip.setDueDate(new SimpleDateFormat(ConstantApplication.YYYY_MM_DD).parse(getNode(treeNode, FIELD_NAME_DUE_DATE)));
        } catch (ParseException e) {
            throw new BankslipInvalidFieldsException(e);
        }
        try {
            bankslip.setTotalInCents(Long.parseLong(getNode(treeNode, FIELD_NAME_TOTAL_IN_CENT)));
        } catch (NumberFormatException e) {
            throw new BankslipInvalidFieldsException(e);
        }
        return bankslip;
    }

    private String getNode(TreeNode treeNode, String node) {
        TextNode jsonNodes = (TextNode) treeNode.get(node);
        if (jsonNodes != null) {
            return jsonNodes.asText();
        }

        return "";
    }
}
