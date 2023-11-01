package com.vertex.quality.common.pojos;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * Represents an entry in the EnvironmentCredentials table
 *
 * @author hho
 */
@Getter
@Builder
public class EnvironmentCredentials
{
	@NonNull
	private String environmentCredentialId;
	@NonNull
	private String connectorId;
	@NonNull
	private String environmentId;
	@NonNull
	private String environmentInformationId;
	private String username;
	private String password;
	private String authenticationAnswer1;
	private String authenticationAnswer2;
	private String authenticationAnswer3;
}
