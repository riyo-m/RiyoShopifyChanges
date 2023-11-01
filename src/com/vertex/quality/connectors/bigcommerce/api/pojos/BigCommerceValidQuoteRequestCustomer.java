package com.vertex.quality.connectors.bigcommerce.api.pojos;

import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceQuoteRequestCustomer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * the customer which a quote request is for
 * an implementation with all fields having the data types prescribed by the API's Swagger documentation
 *
 * @author osabha ssalisbury
 */
@Getter
@Setter
@Builder
public class BigCommerceValidQuoteRequestCustomer implements BigCommerceQuoteRequestCustomer
{
	//these have to use underscores because they correspond to keys in the JSON for a quote request
	private String customer_group_id;
	private String customer_id;//required
	private String taxability_code;

	@Override
	public String retrieveCustomer_group_id( )
	{
		return customer_group_id;
	}

	@Override
	public String retrieveCustomer_id( )
	{
		return customer_id;
	}

	@Override
	public String retrieveTaxability_code( )
	{
		return taxability_code;
	}

	@Override
	public BigCommerceQuoteRequestCustomer copy( )
	{
		BigCommerceQuoteRequestCustomer newCustomer = BigCommerceValidQuoteRequestCustomer
			.builder()
			.customer_group_id(this.customer_group_id)
			.customer_id(this.customer_id)
			.taxability_code(this.taxability_code)
			.build();

		return newCustomer;
	}
}
