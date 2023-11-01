package com.vertex.quality.connectors.bigcommerce.api.pojos;

import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceAddress;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestDocument;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestItem;
import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * a section of a quote request, describing all of the items in an order which are being shipped to a particular address
 * an implementation with all fields having the data types prescribed by the API's Swagger documentation
 *
 * @author osabha ssalisbury
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BigCommerceValidRequestDocument implements BigCommerceRequestDocument
{
	//these have to use underscores because they correspond to keys in the JSON for a quote request
	private BigCommerceAddress billing_address;
	private BigCommerceAddress destination_address;//required
	private BigCommerceRequestItem handling;
	// unique identifier for this consignment(document request,as part of the quote request).
	private String id;
	@Singular
	private List<BigCommerceRequestItem> items;//required
	//this has to use underscores because it corresponds to keys in the JSON for a quote request
	private BigCommerceAddress origin_address;// required
	private BigCommerceRequestItem shipping;

	public BigCommerceValidRequestDocument( final String docId, final BigCommerceRequestItem handling,
		final BigCommerceRequestItem shipping, final BigCommerceAddress billingAddress,
		final BigCommerceAddress shippingAddress, final BigCommerceAddress originAddress,
		final BigCommerceRequestItem... items )
	{
		this.id = docId;
		this.billing_address = billingAddress;
		this.destination_address = shippingAddress;
		this.origin_address = originAddress;
		this.items = Arrays.asList(items);
		this.handling = handling;
		this.shipping = shipping;
	}

	@Override
	public BigCommerceAddress retrieveBilling_address( )
	{
		return billing_address;
	}

	@Override
	public BigCommerceAddress retrieveDestination_address( )
	{
		return destination_address;
	}

	@Override
	public BigCommerceRequestItem retrieveHandling( )
	{
		return handling;
	}

	@Override
	public String retrieveId( )
	{
		return id;
	}

	@Override
	public List<BigCommerceRequestItem> retrieveItems( )
	{
		return items;
	}

	@Override
	public BigCommerceAddress retrieveOrigin_address( )
	{
		return origin_address;
	}

	@Override
	public BigCommerceRequestItem retrieveShipping( )
	{
		return shipping;
	}

	@Override
	public BigCommerceRequestDocument copy( )
	{
		BigCommerceAddress newBillAddress = this.billing_address.copy();
		BigCommerceAddress newDestAddress = this.destination_address.copy();
		BigCommerceRequestItem newHandlingItem = this.handling.copy();
		List<BigCommerceRequestItem> newItems = this.items
			.stream()
			.map(BigCommerceRequestItem::copy)
			.collect(Collectors.toList());
		BigCommerceAddress newOriginAddress = this.origin_address.copy();
		BigCommerceRequestItem newShippingItem = this.shipping.copy();

		BigCommerceRequestDocument newDocument = BigCommerceValidRequestDocument
			.builder()
			.billing_address(newBillAddress)
			.destination_address(newDestAddress)
			.handling(newHandlingItem)
			.items(newItems)
			.origin_address(newOriginAddress)
			.shipping(newShippingItem)
			.build();

		return newDocument;
	}
}
