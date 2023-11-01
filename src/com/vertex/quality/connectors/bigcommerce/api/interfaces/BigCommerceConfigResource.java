package com.vertex.quality.connectors.bigcommerce.api.interfaces;

/**
 * a configuration for one user who accesses the connector
 *
 * WARN- when any field is added to this interface, make sure that every implementing class adds
 * copying of that field to their implementation of the copy method!!!
 *
 * @author ssalisbury
 */
public interface BigCommerceConfigResource
{
	//these getters use the 'retrieve' prefix because implementing classes want the json serializer to grab the
	// exact values from their private fields (including data type), but if those classes implemented getters from here
	// that start with 'get' then the serializer just grabs what that getter function returns even if that's
	// a different datatype from the field itself
	public String retrieveCompanyCode( );
	public String retrieveDateAdded( );
	public int retrieveId( );
	public String retrievePassword( );
	public String retrieveStoreApiUrl( );
	public String retrieveStoreClientId( );
	public String retrieveStoreClientSecret( );
	public String retrieveStoreId( );
	public String retrieveStoreName( );
	public String retrieveStoreToken( );
	public String retrieveTrustedId( );
	public String retrieveUsername( );

	public void setCompanyCode( final String companyCode );
	public void setDateAdded( final String dateAdded );
	public void setId( final int id );
	public void setPassword( final String password );
	public void setStoreApiUrl( final String storeApiUrl );
	public void setStoreClientId( final String storeClientId );
	public void setStoreClientSecret( final String storeClientSecret );
	public void setStoreId( final String storeId );
	public void setStoreName( final String storeName );
	public void setStoreToken( final String storeToken );
	public void setTrustedId( final String trustedId );
	public void setUsername( final String username );

	/**
	 * this creates a deep copy of the BigCommerceConfigResource object
	 *
	 * @return a deep copy of the BigCommerceConfigResource object
	 */
	public BigCommerceConfigResource copy( );
}
