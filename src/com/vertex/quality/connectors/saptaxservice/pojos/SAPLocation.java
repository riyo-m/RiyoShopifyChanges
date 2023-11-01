package com.vertex.quality.connectors.saptaxservice.pojos;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a Location item in the "Locations" JSON array property in the API
 *
 * @author hho
 */
@Builder
@EqualsAndHashCode
@ToString
@Getter
public class SAPLocation
{
	private String addressId;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String city;
	private String country;
	private String county;
	private String isBusinessPartnerTaxRegistered;
	private String isCompanyTaxRegistered;
	private String state;
	private String type;
	private String zipCode;
}
