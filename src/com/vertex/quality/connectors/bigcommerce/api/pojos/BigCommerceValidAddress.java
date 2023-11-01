package com.vertex.quality.connectors.bigcommerce.api.pojos;

import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceAddress;
import com.vertex.quality.connectors.bigcommerce.common.enums.BigCommerceTestDataAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * an address which is part of an api quote request
 * an implementation with all fields having the data types prescribed by the API's Swagger documentation
 *
 * @author osabha ssalisbury
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BigCommerceValidAddress implements BigCommerceAddress
{
	private String line1;
	private String line2;
	private String city;
	//these have to use underscores because they correspond to keys in the JSON for a quote request
	private String country_name;
	private String region_name;
	private String region_code;
	private String company_name;
	private String country_code;
	private String postal_code;
	private String type;

	public BigCommerceValidAddress( final BigCommerceTestDataAddress selectedAddress )
	{
		line1 = selectedAddress.getLine1();
		city = selectedAddress.getCity();
		country_name = selectedAddress.getCountry_name();
		region_name = selectedAddress.getRegion_name();
		postal_code = selectedAddress.getPostal_code();
	}

	@Override
	public String retrieveLine1( )
	{
		return line1;
	}

	@Override
	public String retrieveLine2( )
	{
		return line2;
	}

	@Override
	public String retrieveCity( )
	{
		return city;
	}

	@Override
	public String retrieveCountry_name( )
	{
		return country_name;
	}

	@Override
	public String retrieveRegion_name( )
	{
		return region_name;
	}

	@Override
	public String retrieveRegion_code( )
	{
		return region_code;
	}

	@Override
	public String retrieveCompany_name( )
	{
		return company_name;
	}

	@Override
	public String retrieveCountry_code( )
	{
		return country_code;
	}

	@Override
	public String retrievePostal_code( )
	{
		return postal_code;
	}

	@Override
	public String retrieveType( )
	{
		return type;
	}

	@Override
	public BigCommerceAddress copy( )
	{
		BigCommerceAddress newAddress = BigCommerceValidAddress
			.builder()
			.line1(this.line1)
			.line2(this.line2)
			.city(this.city)
			.country_name(this.country_name)
			.region_name(this.region_name)
			.region_code(this.region_code)
			.company_name(this.company_name)
			.country_code(this.country_code)
			.postal_code(this.postal_code)
			.type(this.type)
			.build();

		return newAddress;
	}
}