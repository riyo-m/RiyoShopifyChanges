package com.vertex.quality.connectors.netsuite.common.enums;

import lombok.Getter;

/**
 * Contains Netsuite employee
 *
 * @author ravunuri
 */
@Getter
public enum NetsuiteEmployee
{
	EMPLOYEE_TEST("Test Automation");

	private String employeeName;

	NetsuiteEmployee( String employeeName )
	{
		this.employeeName = employeeName;
	}
}
