package com.vertex.quality.connectors.bigcommerce.api.interfaces;

/**
 * an address which is part of an api quote request
 *
 * WARN- when any field is added to this interface, make sure that every implementing class adds
 * copying of that field to their implementation of the copy method!!!
 *
 * @author osabha ssalisbury
 */
public interface BigCommerceAddress
{
	//these getters use the 'retrieve' prefix because implementing classes want the json serializer to grab the
	// exact values from their private fields (including data type), but if those classes implemented getters from here
	// that start with 'get' then the serializer just grabs what that getter function returns even if that's
	// a different datatype from the field itself
	public String retrieveLine1( );
	public String retrieveLine2( );
	public String retrieveCity( );
	public String retrieveCountry_name( );
	public String retrieveRegion_name( );
	public String retrieveRegion_code( );
	public String retrieveCompany_name( );
	public String retrieveCountry_code( );
	public String retrievePostal_code( );
	public String retrieveType( );

	//these setters have to use underscores because they correspond to keys in the JSON for a quote request,
	// and Lombok's auto-generated setters just capitalize the first letter of the field
	// and add 'set' in front
	public void setLine1( final String line1 );
	public void setLine2( final String line2 );
	public void setCity( final String city );
	public void setCountry_name( final String country_name );
	public void setRegion_name( final String region_name );
	public void setRegion_code( final String region_code );
	public void setCompany_name( final String company_name );
	public void setCountry_code( final String country_code );
	public void setPostal_code( final String postal_code );
	public void setType( final String type );

	/**
	 * this creates a deep copy of the BigCommerceAddress object
	 *
	 * @return a deep copy of the BigCommerceAddress object
	 */
	public BigCommerceAddress copy( );
}
