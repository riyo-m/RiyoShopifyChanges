package com.vertex.quality.connectors.bigcommerce.api.pojos;

import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceQuoteRequest;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceQuoteRequestCustomer;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestDocument;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;
import java.util.stream.Collectors;

/**
 * a quote request which can be sent to the connector's API
 * an implementation with all fields having the data types prescribed by the API's Swagger documentation
 *
 * @author osabha ssalisbury
 */
@Builder
@Getter
@Setter
public class BigCommerceValidQuoteRequest implements BigCommerceQuoteRequest
{
	//this has to use underscores because it corresponds to a key in the JSON for a quote request
	private String currency_code;//required
	private BigCommerceQuoteRequestCustomer customer;//required
	@Singular
	private List<BigCommerceRequestDocument> documents;//required
	//required,unique ID of the taxable document (the quote request)
	private String id;
	//this has to use underscores because it corresponds to a key in the JSON for a quote request
	private String transaction_date;//required

	@Override
	public String retrieveCurrency_code( )
	{
		return currency_code;
	}

	@Override
	public BigCommerceQuoteRequestCustomer retrieveCustomer( )
	{
		return customer;
	}

	@Override
	public List<BigCommerceRequestDocument> retrieveDocuments( )
	{
		return documents;
	}

	@Override
	public String retrieveId( )
	{
		return id;
	}

	@Override
	public String retrieveTransaction_date( )
	{
		return transaction_date;
	}

	@Override
	public BigCommerceQuoteRequest copy( )
	{
		BigCommerceQuoteRequestCustomer newCustomer = this.customer.copy();
		List<BigCommerceRequestDocument> newDocuments = this.documents
			.stream()
			.map(BigCommerceRequestDocument::copy)
			.collect(Collectors.toList());

		BigCommerceQuoteRequest newQuote = BigCommerceValidQuoteRequest
			.builder()
			.currency_code(this.currency_code)
			.customer(newCustomer)
			.documents(newDocuments)
			.id(this.id)
			.transaction_date(this.transaction_date)
			.build();

		return newQuote;
	}
}
