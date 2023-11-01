package com.vertex.quality.connectors.bigcommerce.api.pojos.invalid;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestItem;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestItemPrice;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceTaxClass;
import com.vertex.quality.connectors.bigcommerce.api.pojos.BigCommerceValidRequestItem;
import com.vertex.quality.connectors.bigcommerce.api.pojos.BigCommerceValidRequestItemPrice;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * an item which is part of a quote request
 * an implementation where the wrapping field is just a double instead of a RequestItem
 *
 * @author osabha ssalisbury
 */
@Builder
@Getter
@Setter
public class BigCommerceRequestItemWithDoubleWrapping implements BigCommerceRequestItem
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
	private double wrapping;

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

	/**
	 * creates a RequestItem object which contains this object's wrapping decimal-value in order to satisfy the type
	 * expectations of the RequestItem interface
	 *
	 * @return a RequestItem object which contains this object's wrapping decimal-value
	 */
	@Override
	public BigCommerceRequestItem retrieveWrapping( )
	{
		final boolean defaultWrappingTaxInclusivity = true;
		VertexLogger.log("invoking dummy getter", VertexLogLevel.WARN);

		BigCommerceValidRequestItemPrice wrappingPrice = new BigCommerceValidRequestItemPrice(
			defaultWrappingTaxInclusivity, this.wrapping);
		BigCommerceValidRequestItem wrappingItem = BigCommerceValidRequestItem
			.builder()
			.price(wrappingPrice)
			.build();
		return wrappingItem;
	}

	/**
	 * sets this object's 'wrapping' decimal value based on the price amount in the given 'item' object
	 *
	 * @param wrapping the 'item' object whose contents will determine the state of this object's 'wrapping' field
	 */
	public void setWrapping( final BigCommerceRequestItem wrapping )
	{
		BigCommerceRequestItemPrice wrappingPrice = wrapping.retrievePrice();
		this.wrapping = wrappingPrice.retrieveAmount();
	}

	@Override
	public BigCommerceRequestItem copy( )
	{
		BigCommerceRequestItemPrice newPrice = this.price.copy();
		BigCommerceTaxClass newTaxClass = this.tax_class.copy();

		BigCommerceRequestItem newItem = BigCommerceRequestItemWithDoubleWrapping
			.builder()
			.id(this.id)
			.item_code(this.item_code)
			.name(this.name)
			.price(newPrice)
			.quantity(this.quantity)
			.tax_class(newTaxClass)
			.tax_exempt(this.tax_exempt)
			.type(this.type)
			.wrapping(this.wrapping)
			.build();

		return newItem;
	}
}
