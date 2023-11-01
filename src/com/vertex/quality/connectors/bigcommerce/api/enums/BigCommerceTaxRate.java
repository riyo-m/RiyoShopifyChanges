package com.vertex.quality.connectors.bigcommerce.api.enums;

/**
 * Tax Rates for different countries
 *
 * @author vivek.kumar
 */
public enum BigCommerceTaxRate {
    CAN_NEW_BRUNSWICK("0.15"),
    CAN_BC_QC("0.05"),
    QUEBEC("0.09975"),
    CAN_BC_ON("0.13"),
    ONTARIO("0.0"),
    CAN_QC_BC("0.05"),
    QC_BC("0.07"),
    PA_RATE("0.06"),
    BC("0.07"),
    NEW_BRUNSWICK("0.0");

    public String value;

    BigCommerceTaxRate(String val) {
        this.value = val;
    }
}
