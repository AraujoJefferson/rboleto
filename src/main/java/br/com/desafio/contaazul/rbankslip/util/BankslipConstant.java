package br.com.desafio.contaazul.rbankslip.util;

/**
 * Classe que recebe todas as constantes que se referem ao Bankslip
 */
public class BankslipConstant {
    //Bankslip
    public static final String TABLE_NAME_BANKSLIP = "BANKSLIP";
    public static final String FIELD_NAME_UUID = "id";
    public static final String FIELD_NAME_DUE_DATE = "due_date";
    public static final String FIELD_NAME_TOTAL_IN_CENT = "total_in_cents";
    public static final String FIELD_NAME_CUSTOMER = "customer";
    public static final String FIELD_NAME_STATUS = "status";
    public static final String FIELD_NAME_FINE = "fine";

    //RestExceptionHandler
    public static final String BANKSLIPS_SAVE_EMPTY_400 = "bankslips.save.empty.400";
    public static final String BANKSLIPS_SAVE_FIELDS_422 = "bankslips.save.fields.422";
    public static final String BANKSLIPS_NOT_EXIST_404 = "bankslips.not.exist.404";
    public static final String BANKSLIPS_INVALID_400 = "bankslips.invalid.400";
    public static final String BANKSLIPS_PAY_401 = "bankslips.pay.401";
    public static final String BANKSLIPS_CANCEL_401 = "bankslips.cancel.401";

    //BankslipController
    public static final String BANKSLIPS_SAVE_OK_201 = "bankslips.save.ok.201";
    public static final String BANKSLIPS_LIST_OK_200 = "bankslips.list.ok.200";
    public static final String BANKSLIPS_DETAIL_OK_200 = "bankslips.detail.ok.200";
    public static final String BANKSLIPS_PAY_OK_200 = "bankslips.pay.ok.200";
    public static final String BANKSLIPS_CANCEL_OK_200 = "bankslips.cancel.ok.200";

    //BankslipCalculate
    public static final String GT10 = "0.1";
    public static final String DEFAULT = "0,0";
    public static final String LT10 = "0.05";

    private BankslipConstant(){}
}
