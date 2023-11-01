package com.vertex.quality.connectors.bigcommerce.api.pojos;

import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceConfigResource;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * a configuration for one user who accesses the connector
 * an implementation with all fields having the data types prescribed by the API's Swagger documentation
 *
 * @author ssalisbury
 */
@Getter
@Setter
@Builder
public class BigCommerceValidConfigResource implements BigCommerceConfigResource
{
	private String companyCode;
	private String dateAdded;
	private int id;
	private String password;
	private String storeApiUrl;
	private String storeClientId;
	private String storeClientSecret;
	private String storeId;
	private String storeName;
	private String storeToken;
	private String trustedId;
	private String username;

	@Override
	public String retrieveCompanyCode( )
	{
		return companyCode;
	}

	@Override
	public String retrieveDateAdded( )
	{
		return dateAdded;
	}

	@Override
	public int retrieveId( )
	{
		return id;
	}

	@Override
	public String retrievePassword( )
	{
		return password;
	}

	@Override
	public String retrieveStoreApiUrl( )
	{
		return storeApiUrl;
	}

	@Override
	public String retrieveStoreClientId( )
	{
		return storeClientId;
	}

	@Override
	public String retrieveStoreClientSecret( )
	{
		return storeClientSecret;
	}

	@Override
	public String retrieveStoreId( )
	{
		return storeId;
	}

	@Override
	public String retrieveStoreName( )
	{
		return storeName;
	}

	@Override
	public String retrieveStoreToken( )
	{
		return storeToken;
	}

	@Override
	public String retrieveTrustedId( )
	{
		return trustedId;
	}

	@Override
	public String retrieveUsername( )
	{
		return username;
	}

	@Override
	public BigCommerceConfigResource copy( )
	{
		BigCommerceConfigResource newConfig = BigCommerceValidConfigResource
			.builder()
			.companyCode(this.companyCode)
			.dateAdded(this.dateAdded)
			.id(this.id)
			.password(this.password)
			.storeApiUrl(this.storeApiUrl)
			.storeClientId(this.storeClientId)
			.storeClientSecret(this.storeClientSecret)
			.storeId(this.storeId)
			.storeName(this.storeName)
			.storeToken(this.storeToken)
			.trustedId(this.trustedId)
			.username(this.username)
			.build();

		return newConfig;
	}
}
