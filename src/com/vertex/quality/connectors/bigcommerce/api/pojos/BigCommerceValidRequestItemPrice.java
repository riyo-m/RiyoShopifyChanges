package com.vertex.quality.connectors.bigcommerce.api.pojos;

import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestItemPrice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * the price of an item in a quote request.
 * an implementation with all fields having the data types prescribed by the API's Swagger documentation
 *
 * @author osabha ssalisbury
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BigCommerceValidRequestItemPrice implements BigCommerceRequestItemPrice
{
	//this has to use underscores because it corresponds to keys in the JSON for a quote request
	private boolean tax_inclusive;//required
	private double amount;//required

	@Override
	public boolean retrieveTax_inclusive( )
	{
		return tax_inclusive;
	}

	@Override
	public double retrieveAmount( )
	{
		return amount;
	}

	@Override
	public BigCommerceRequestItemPrice copy( )
	{
		BigCommerceRequestItemPrice newPrice = BigCommerceValidRequestItemPrice
			.builder()
			.tax_inclusive(this.tax_inclusive)
			.amount(this.amount)
			.build();

		return newPrice;
	}
}
