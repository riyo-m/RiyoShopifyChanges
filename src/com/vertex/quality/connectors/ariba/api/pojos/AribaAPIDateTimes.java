package com.vertex.quality.connectors.ariba.api.pojos;

import lombok.Data;

/**
 * An iso-8601 date pojo to help with making API requests
 *
 * @author hho
 */
@Data
public class AribaAPIDateTimes
{
	private String previousDateTime;
	private String currentDateTime;
	private String nextDateTime;
}
