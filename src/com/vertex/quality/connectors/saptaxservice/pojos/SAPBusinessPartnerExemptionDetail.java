package com.vertex.quality.connectors.saptaxservice.pojos;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a BusinessPartnerExemptionDetail item in the "BusinessPartnerExemptionDetails" JSON
 * array property in the API
 *
 * @author hho
 */
@Builder
@EqualsAndHashCode
@ToString
@Getter
public class SAPBusinessPartnerExemptionDetail
{
	private String exemptionReasonCode;
	private String locationType;
}
