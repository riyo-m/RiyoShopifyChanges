package com.vertex.quality.connectors.bigcommerce.api.pojos;

import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceTaxClass;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * the tax class of an item in a quote request
 * an implementation with all fields having the data types prescribed by the API's Swagger documentation
 *
 * @author osabha ssalisbury
 */
@Builder
@Getter
@Setter
public class BigCommerceValidTaxClass implements BigCommerceTaxClass
{
	//this has to use underscores because it corresponds to keys in the JSON for a quote request
	private String class_id;
	private String code;
	private String name;

	@Override
	public String retrieveClass_id( )
	{
		return class_id;
	}

	@Override
	public String retrieveCode( )
	{
		return code;
	}

	@Override
	public String retrieveName( )
	{
		return name;
	}

	@Override
	public BigCommerceTaxClass copy( )
	{
		BigCommerceTaxClass newTaxClass = BigCommerceValidTaxClass
			.builder()
			.class_id(this.class_id)
			.code(this.code)
			.name(this.name)
			.build();

		return newTaxClass;
	}
}
