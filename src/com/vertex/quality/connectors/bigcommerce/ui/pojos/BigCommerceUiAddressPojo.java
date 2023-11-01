package com.vertex.quality.connectors.bigcommerce.ui.pojos;

import com.vertex.quality.connectors.bigcommerce.common.enums.BigCommerceTestDataAddress;
import com.vertex.quality.connectors.bigcommerce.ui.pages.storefront.BigCommerceStoreCheckoutPage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * a shipping or billing address that's used in {@link BigCommerceStoreCheckoutPage}
 *
 * @author ssalisbury
 */
@Getter
@AllArgsConstructor
@Builder
public class BigCommerceUiAddressPojo
{
	private final String firstName;
	private final String lastName;
	private final String addressLine1;
	//this isn't final because that causes compiler errors, I think because of a weird interaction between field
	// initialization/declaration and multiple constructors (the hidden lombok code might be involved?)
	@Builder.Default
	private String addressLine2 = "";
	private final String city;
	private final String country;
	private final String stateProvince;
	private final String postalCode;

	public BigCommerceUiAddressPojo( final String firstName, final String lastName,
		final BigCommerceTestDataAddress address )
	{
		this.firstName = firstName;
		this.lastName = lastName;

		this.addressLine1 = address.getLine1();
		this.city = address.getCity();
		this.country = address.getCountry_name();
		this.stateProvince = address.getRegion_name();
		this.postalCode = address.getPostal_code();
	}
}
