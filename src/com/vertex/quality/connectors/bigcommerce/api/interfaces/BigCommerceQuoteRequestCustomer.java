package com.vertex.quality.connectors.bigcommerce.api.interfaces;

/**
 * the customer which a quote request is for
 *
 * WARN- when any field is added to this interface, make sure that every implementing class adds
 * copying of that field to their implementation of the copy method!!!
 *
 * @author osabha ssalisbury
 */
public interface BigCommerceQuoteRequestCustomer
{
	//these getters use the 'retrieve' prefix because implementing classes want the json serializer to grab the
	// exact values from their private fields (including data type), but if those classes implemented getters from here
	// that start with 'get' then the serializer just grabs what that getter function returns even if that's
	// a different datatype from the field itself
	public String retrieveCustomer_group_id( );
	public String retrieveCustomer_id( );//required
	public String retrieveTaxability_code( );

	//these setters have to use underscores because they correspond to keys in the JSON for a quote request,
	// and Lombok's auto-generated setters just capitalize the first letter of the field
	// and add 'set' in front
	public void setCustomer_group_id( final String customer_group_id );
	public void setCustomer_id( final String customer_id );//required
	public void setTaxability_code( final String taxability_code );

	/**
	 * this creates a deep copy of the BigCommerceQuoteRequestCustomer object
	 *
	 * @return a deep copy of the BigCommerceQuoteRequestCustomer object
	 */
	public BigCommerceQuoteRequestCustomer copy( );
}
