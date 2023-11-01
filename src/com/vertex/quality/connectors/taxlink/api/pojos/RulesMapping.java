package com.vertex.quality.connectors.taxlink.api.pojos;

import lombok.Getter;
import lombok.Setter;

/**
 * This is a POJO class for creating payload for POST request of Rules Mapping
 *
 * @author Manjiri.Gaikwad
 */

@Getter
@Setter
public class RulesMapping
{
	private int conditionSetId;
	private String conditionString;
	private String constantValue;
	private boolean enabledFlag;
	private String endDate;
	private String functionCode;
	private String phaseType;
	private String ruleName;
	private String ruleOrder;
	private int sourceAttributeId;
	private String sourceAttributeId1;
	private String startDate;
	private int targetAttributeId;
}
