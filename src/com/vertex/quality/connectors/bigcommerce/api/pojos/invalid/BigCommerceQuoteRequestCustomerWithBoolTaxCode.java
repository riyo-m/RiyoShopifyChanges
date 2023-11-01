package com.vertex.quality.connectors.bigcommerce.api.pojos.invalid;

import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceQuoteRequestCustomer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * the customer which a quote request is for
 * an implementation which stores the taxability_code field's information as a boolean
 *
 * @author osabha ssalisbury
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class BigCommerceQuoteRequestCustomerWithBoolTaxCode implements BigCommerceQuoteRequestCustomer
{
	//these have to use underscores because they correspond to keys in the JSON for a quote request
	private String customer_group_id;
	private String customer_id;//required
	private boolean taxability_code;

	public BigCommerceQuoteRequestCustomerWithBoolTaxCode( final String customer_id, final boolean taxability_code )
	{
		this.customer_id = customer_id;
		this.taxability_code = taxability_code;
	}

	public String retrieveCustomer_group_id( )
	{
		return customer_group_id;
	}

	public String retrieveCustomer_id( )
	{
		return customer_id;
	}

	/**
	 * converts this object's boolean taxability_code value into a string to satisfy the type expectations of the quote
	 * request customer interface
	 *
	 * @return a string representation of this object's boolean taxability_code value
	 */
	public String retrieveTaxability_code( )
	{
		final String taxabilityCodeString = String.valueOf(taxability_code);
		return taxabilityCodeString;
	}

	/**
	 * sets the taxability code by converting the given string into a boolean value
	 *
	 * @param taxability_code a string describing the new taxability code
	 */
	public void setTaxability_code( final String taxability_code )
	{
		this.taxability_code = Boolean.parseBoolean(taxability_code);
	}

	@Override
	public BigCommerceQuoteRequestCustomer copy( )
	{
		BigCommerceQuoteRequestCustomer newCustomer = BigCommerceQuoteRequestCustomerWithBoolTaxCode
			.builder()
			.customer_group_id(this.customer_group_id)
			.customer_id(this.customer_id)
			.taxability_code(this.taxability_code)
			.build();

		return newCustomer;
	}
}
