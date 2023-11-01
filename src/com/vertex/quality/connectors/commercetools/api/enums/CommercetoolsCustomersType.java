package com.vertex.quality.connectors.commercetools.api.enums;


import lombok.Getter;

/**
 * different Customer types used in commercetool create cart request
 *
 * @author Mayur.Kumbhar
 */
@Getter
public enum CommercetoolsCustomersType {


    CUSTOMER_ID("d0df44c5-d66c-41e0-874e-0e83d2802e26");

    public  String commerceToolsCustomer_ID;

    CommercetoolsCustomersType(final String customer_ID)
    {
        this.commerceToolsCustomer_ID=customer_ID;
    }

}
