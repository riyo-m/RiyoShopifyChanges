package com.vertex.quality.connectors.bigcommerce.api.pojos;

import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestItem;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestItemPrice;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceTaxClass;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * an item which is part of a quote request
 * an implementation with all fields having the data types prescribed by the API's Swagger documentation
 *
 * @author osabha ssalisbury
 */
@Builder
@Getter
@Setter
public class BigCommerceValidRequestItem implements BigCommerceRequestItem
{
	private String id;//required
	//this has to use underscores because it corresponds to keys in the JSON for a quote request
	private String item_code;
	private String name;
	private BigCommerceRequestItemPrice price;//required
	private int quantity;//required
	//these have to use underscores because they correspond to keys in the JSON for a quote request
	private BigCommerceTaxClass tax_class;
	private boolean tax_exempt;
	//required, The type of line item this request represents. takes values from the enum BigCommerceRequestItemType
	private String type;
	private BigCommerceRequestItem wrapping;

	@Override
	public String retrieveId( )
	{
		return id;
	}

	@Override
	public String retrieveItem_code( )
	{
		return item_code;
	}

	@Override
	public String retrieveName( )
	{
		return name;
	}

	@Override
	public BigCommerceRequestItemPrice retrievePrice( )
	{
		return price;
	}

	@Override
	public int retrieveQuantity( )
	{
		return quantity;
	}

	@Override
	public BigCommerceTaxClass retrieveTax_class( )
	{
		return tax_class;
	}

	@Override
	public boolean retrieveTax_exempt( )
	{
		return tax_exempt;
	}

	@Override
	public String retrieveType( )
	{
		return type;
	}

	@Override
	public BigCommerceRequestItem retrieveWrapping( )
	{
		return wrapping;
	}

	@Override
	public BigCommerceRequestItem copy( )
	{
		BigCommerceRequestItemPrice newPrice = this.price.copy();
		BigCommerceTaxClass newTaxClass = this.tax_class.copy();
		BigCommerceRequestItem newWrapping = this.wrapping.copy();

		BigCommerceRequestItem newItem = BigCommerceValidRequestItem
			.builder()
			.id(this.id)
			.item_code(this.item_code)
			.name(this.name)
			.price(newPrice)
			.quantity(this.quantity)
			.tax_class(newTaxClass)
			.tax_exempt(this.tax_exempt)
			.type(this.type)
			.wrapping(newWrapping)
			.build();

		return newItem;
	}
}
