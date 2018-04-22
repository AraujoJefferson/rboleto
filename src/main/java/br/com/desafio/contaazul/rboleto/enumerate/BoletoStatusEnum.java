package br.com.desafio.contaazul.rboleto.enumerate;

public enum BoletoStatusEnum {
    PENDING("PENDING"), PAID("PAID"),
    CANCELED("CANCELED");

    private String codigo;

    BoletoStatusEnum(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public static BoletoStatusEnum get(String codigo){
        for (BoletoStatusEnum enumValue: BoletoStatusEnum.values()) {
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
