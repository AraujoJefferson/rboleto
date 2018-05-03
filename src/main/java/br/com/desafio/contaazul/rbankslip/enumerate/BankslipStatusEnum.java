package br.com.desafio.contaazul.rbankslip.enumerate;

public enum BankslipStatusEnum {
    PENDING("PENDING"), PAID("PAID"),
    CANCELED("CANCELED");

    private String codigo;

    BankslipStatusEnum(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public static BankslipStatusEnum get(String codigo){
        for (BankslipStatusEnum enumValue: BankslipStatusEnum.values()) {
            if(enumValue.getCodigo().equalsIgnoreCase(codigo)){
                return enumValue;
            }
        }
        return null;
    }

    @Override
    public String toString(){
        return this.getCodigo();
    }
}
