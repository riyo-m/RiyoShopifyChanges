package com.vertex.quality.connectors.bigcommerce.api.interfaces;

/**
 * an item which is part of a quote request
 *
 * WARN- when any field is added to this interface, make sure that every implementing class adds
 * copying of that field to their implementation of the copy method!!!
 *
 * @author osabha ssalisbury
 */
public interface BigCommerceRequestItem
{
	//these getters use the 'retrieve' prefix because implementing classes want the json serializer to grab the
	// exact values from their private fields (including data type), but if those classes implemented getters from here
	// that start with 'get' then the serializer just grabs what that getter function returns even if that's
	// a different datatype from the field itself
	public String retrieveId( );
	public String retrieveItem_code( );
	public String retrieveName( );
	public BigCommerceRequestItemPrice retrievePrice( );//required
	public int retrieveQuantity( );//required
	public BigCommerceTaxClass retrieveTax_class( );
	public boolean retrieveTax_exempt( );
	public String retrieveType( );//required, The type of line item this request represents.
	public BigCommerceRequestItem retrieveWrapping( );

	//these setters have to use underscores because they correspond to keys in the JSON for a quote request,
	// and Lombok's auto-generated setters just capitalize the first letter of the field
	// and add 'set' in front
	public void setId( final String id );//required
	public void setItem_code( final String item_code );
	public void setName( final String name );
	public void setPrice( final BigCommerceRequestItemPrice price );//required
	public void setQuantity( final int quantity );//required
	public void setTax_class( final BigCommerceTaxClass taxClass );
	public void setTax_exempt( final boolean tax_exempt );
	public void setType( final String type );//required, The type of line item this request represents.
	public void setWrapping( final BigCommerceRequestItem wrapping );

	/**
	 * this creates a deep copy of the BigCommerceRequestItem object
	 *
	 * @return a deep copy of the BigCommerceRequestItem object
	 */
	public BigCommerceRequestItem copy( );
}
