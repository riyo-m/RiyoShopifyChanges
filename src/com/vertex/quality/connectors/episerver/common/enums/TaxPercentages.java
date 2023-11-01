package com.vertex.quality.connectors.episerver.common.enums;

/**
 * Enum or Data file that contains different percentages for Episerver's order
 *
 * @author Shivam.Soni
 */
public enum TaxPercentages {
    ZERO_PERCENT(0.0);

    public double taxPercent;

    /**
     * Parameterized constructor of the class/enum
     *
     * @param taxPercent Tax Percentage
     */
    TaxPercentages(double taxPercent) {
        this.taxPercent = taxPercent;
    }
}
