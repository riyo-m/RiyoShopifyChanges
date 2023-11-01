package com.vertex.quality.connectors.commercetools.api.enums;

import lombok.Getter;

/**
 * different tax mode types used in commerce tool api
 *
 * @author Mayur.Kumbhar
 */
@Getter
public enum CommerceToolTaxMode {

    TAX_MODE("External");

    private String taxMode;

    CommerceToolTaxMode(final String taxMode)
    {
        this.taxMode=taxMode;
    }
}
