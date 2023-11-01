package com.vertex.quality.connectors.commercetools.api.pojos;

import com.vertex.quality.connectors.commercetools.api.interfaces.CommerceToolCartRequestItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * a create cart request which can be sent to the connector's API
 *
 * @author Mayur.Kumbhar
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CommerceToolCartAddress implements CommerceToolCartRequestItem {

    //these have to use underscore because they are used as a key in JSON request
    private String currency_code;
    private String Tax_Mode;
    private String customer_ID;


    @Override
    public void setCurrency(String currency_code)
    {
        this.Tax_Mode=currency_code;
    }

    @Override
    public void setTaxMode(String Tax_Mode)
    {
        this.Tax_Mode=Tax_Mode;
    }

    @Override
    public void setCustomerId(String customer_ID)
    {
        this.customer_ID=customer_ID;
    }

    @Override
    public CommerceToolCartRequestItem copy() {

        CommerceToolCartRequestItem newCart = CommerceToolCartAddress
                .builder()
				.currency_code(this.currency_code)
                .customer_ID(this.customer_ID)
                .Tax_Mode(this.Tax_Mode)
                .build();
        return newCart;
    }
}
