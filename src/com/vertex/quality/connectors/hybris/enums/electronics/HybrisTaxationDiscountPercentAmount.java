package com.vertex.quality.connectors.hybris.enums.electronics;

/**
 * Describes all taxation percentage - used to validate percentage based tax applicable on orders
 *
 * @author Shivam.Soni
 */
public enum HybrisTaxationDiscountPercentAmount {
    // Hybris Tax percentage
    PA_CA_TAX(9.5),
    PA_WA_TAX(10.1),
    PA_LA_TAX(9.45),
    PA_CO_TAX(8.2),
    PA_UT_TAX(7.45),
    PA_WI_TAX(5.5),
    US_CANBC_TAX(12.0),
    PA_TAX(6.0),
    CANNB_CANNB_TAX(15.0),
    CANQC_CANBC_TAX(12.0),
    CANBC_CANON_TAX(13.0),
    CANBC_CANQC_TAX(14.975),
    DE_AT_TAX(19.0),
    DE_FR_TAX(20.0),
    US_FR_TAX(20.0),
    FR_DE_TAX(19.0),
    DE_DE_TAX(19.0),
    FR_DE_SUP_TAX(19.0),
    FR_DE_DDP_TAX(19.0),
    SG_JP_SUP_TAX(10.0),
    GREECE_TAX(24.0),
    ZERO_TAX(0.0),

    // Colorado Retail Delivery Fee
    CO_RDF_FLAT(0.27),

    // Hybris discount amount & percentage
    TEN_DISCOUNT_AMOUNT_OR_PERCENT(10.0),
    FIVE_DISCOUNT_AMOUNT_OR_PERCENT(5.0);

    double percentOrAmount;

    HybrisTaxationDiscountPercentAmount(double taxPercent) {
        this.percentOrAmount = taxPercent;
    }

    public double getPercentOrAmount() {
        return percentOrAmount;
    }
}
