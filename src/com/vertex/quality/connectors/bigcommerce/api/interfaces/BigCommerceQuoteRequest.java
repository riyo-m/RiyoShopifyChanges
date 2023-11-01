package com.vertex.quality.connectors.bigcommerce.api.interfaces;

import java.util.List;

/**
 * a quote request which can be sent to the connector's API
 *
 * WARN- when any field is added to this interface, make sure that every implementing class adds
 * copying of that field to their implementation of the copy method!!!
 *
 * @author osabha ssalisbury
 */
public interface BigCommerceQuoteRequest
{
	//these getters use the 'retrieve' prefix because implementing classes want the json serializer to grab the
	// exact values from their private fields (including data type), but if those classes implemented getters from here
	// that start with 'get' then the serializer just grabs what that getter function returns even if that's
	// a different datatype from the field itself
	public String retrieveCurrency_code( );//required
	public BigCommerceQuoteRequestCustomer retrieveCustomer( );//required
	public List<BigCommerceRequestDocument> retrieveDocuments( );//required
	public String retrieveId( ); //required,unique ID of the taxable document (the quote request)
	public String retrieveTransaction_date( );//required

	//these setters have to use underscores because they correspond to keys in the JSON for a quote request,
	// and Lombok's auto-generated setters just capitalize the first letter of the field
	// and add 'set' in front
	public void setCurrency_code( final String currency_code );//required
	public void setCustomer( final BigCommerceQuoteRequestCustomer customer );//required
	public void setDocuments( final List<BigCommerceRequestDocument> documents );//required
	public void setId( final String id ); //required,unique ID of the taxable document (the quote request)
	public void setTransaction_date( final String transaction_date );//required

	/**
	 * this creates a deep copy of the BigCommerceQuoteRequest object
	 *
	 * @return a deep copy of the BigCommerceQuoteRequest object
	 */
	public BigCommerceQuoteRequest copy( );
}
