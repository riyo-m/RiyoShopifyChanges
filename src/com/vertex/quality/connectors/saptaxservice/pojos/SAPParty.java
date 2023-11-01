package com.vertex.quality.connectors.saptaxservice.pojos;

import lombok.*;

import java.util.List;

/**
 * Represents a Party item in the "Party" JSON array property in the API
 *
 * @author hho
 */
@Builder
@EqualsAndHashCode
@ToString
@Getter
public class SAPParty
{
	private String id;
	private String role;
	@Singular("taxRegistration")
	private List<TaxRegistration> taxRegistration;

	@Builder
	@EqualsAndHashCode
	@ToString
	@Getter
	public static class TaxRegistration
	{
		private String locationType;
		private String taxNumber;
		private String taxNumberTypeCode;
	}
}
