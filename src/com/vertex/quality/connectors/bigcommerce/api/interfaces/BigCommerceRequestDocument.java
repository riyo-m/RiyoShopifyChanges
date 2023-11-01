package com.vertex.quality.connectors.bigcommerce.api.interfaces;

import java.util.List;

/**
 * a section of a quote request, describing all of the items in an order which are being shipped to a particular address
 *
 * WARN- when any field is added to this interface, make sure that every implementing class adds
 * copying of that field to their implementation of the copy method!!!
 *
 * @author osabha ssalisbury
 */
public interface BigCommerceRequestDocument
{
	//these getters use the 'retrieve' prefix because implementing classes want the json serializer to grab the
	// exact values from their private fields (including data type), but if those classes implemented getters from here
	// that start with 'get' then the serializer just grabs what that getter function returns even if that's
	// a different datatype from the field itself
	public BigCommerceAddress retrieveBilling_address( );
	public BigCommerceAddress retrieveDestination_address( );//required
	public BigCommerceRequestItem retrieveHandling( );
	public String retrieveId( ); // unique identifier for this consignment(document request,as part of the quote request).
	public List<BigCommerceRequestItem> retrieveItems( );//required
	public BigCommerceAddress retrieveOrigin_address( );// required field
	public BigCommerceRequestItem retrieveShipping( );

	//these setters have to use underscores because they correspond to keys in the JSON for a quote request,
	// and Lombok's auto-generated setters just capitalize the first letter of the field
	// and add 'set' in front
	public void setBilling_address( final BigCommerceAddress billing_address );
	public void setDestination_address( final BigCommerceAddress destination_address );//required
	public void setHandling( final BigCommerceRequestItem handling );
	public void setId(
		final String id ); // unique identifier for this consignment(document request,as part of the quote request).
	public void setItems( final List<BigCommerceRequestItem> items );//required
	public void setOrigin_address( final BigCommerceAddress origin_address );// required field
	public void setShipping( final BigCommerceRequestItem shipping );

	/**
	 * this creates a deep copy of the BigCommerceRequestDocument object
	 *
	 * @return a deep copy of the BigCommerceRequestDocument object
	 */
	public BigCommerceRequestDocument copy( );
}
