package com.vertex.quality.common.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * represents the the different access variables for QA environments
 *
 * @author osabha
 */
@Getter
@AllArgsConstructor
public class VertexEnvironmentAccessInfo
{
	private String url;
	private List<EnvironmentCredentials> userCredentials;
}
