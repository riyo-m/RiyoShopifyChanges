package com.vertex.quality.connectors.bigcommerce.api.interfaces;

/**
 * the price of an item in a quote request.
 *
 * WARN- when any field is added to this interface, make sure that every implementing class adds
 * copying of that field to their implementation of the copy method!!!
 *
 * @author osabha ssalisbury
 */
public interface BigCommerceRequestItemPrice
{
	//these getters use the 'retrieve' prefix because implementing classes want the json serializer to grab the
	// exact values from their private fields (including data type), but if those classes implemented getters from here
	// that start with 'get' then the serializer just grabs what that getter function returns even if that's
	// a different datatype from the field itself
	public boolean retrieveTax_inclusive( );//required
	public double retrieveAmount( );//required

	// these setters have to use underscores because they correspond to keys in the JSON for a quote request,
	// and Lombok's auto-generated setters just capitalize the first letter of the field
	// and add 'set' in front
	public void setTax_inclusive( final boolean tax_inclusive );//required
	public void setAmount( final double amount );//required

	/**
	 * this creates a deep copy of the BigCommerceRequestItemPrice object
	 *
	 * @return a deep copy of the BigCommerceRequestItemPrice object
	 */
	public BigCommerceRequestItemPrice copy( );
}
