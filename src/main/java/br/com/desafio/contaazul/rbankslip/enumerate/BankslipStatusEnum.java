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

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public static BankslipStatusEnum get(String codigo){
        for (BankslipStatusEnum enumValue: BankslipStatusEnum.values()) {
            if(enumValue.getCodigo().equalsIgnoreCase(codigo)){
                return enumValue;
            }
        }
        return null;
    }

    public String toString(){
        return this.getCodigo();
    }
}
