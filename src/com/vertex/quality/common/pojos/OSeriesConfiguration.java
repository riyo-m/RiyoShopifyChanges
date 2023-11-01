package com.vertex.quality.common.pojos;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents an entry in the OSeriesConfiguration table
 *
 * @author hho
 */
@Getter
@Builder
public class OSeriesConfiguration
{
	private String oseriesConfigurationId;
	private String connectorId;
	private String oseriesUsername;
	private String oseriesPassword;
	private String trustedId;
	private String companyCode;
	private String taxServiceUrl;
	private String addressServiceUrl;
}
