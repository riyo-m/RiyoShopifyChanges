package com.vertex.quality.common.pojos;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * Represents an entry in the EnvironmentInformation table
 *
 * @author hho
 */
@Getter
@Builder
public class EnvironmentInformation
{
	@NonNull
	private String environmentInformationId;
	@NonNull
	private String connectorId;
	@NonNull
	private String environmentId;
	@NonNull
	private String environmentDescriptor;
	@NonNull
	private String environmentUrl;
}

