package com.vertex.quality.connectors.netsuite.common.enums;

import lombok.Getter;

/**
 * Expense Category names in Netsuite
 *
 * @author ravunuri
 */
@Getter
public enum NetsuiteExpenseCategory
{
	CATEGORY_1("Cellular Phone"),
	CATEGORY_2("Meals & Entertainment"),
	CATEGORY_3("Mileage");

	private String categoryName;

	NetsuiteExpenseCategory(String categoryName )
	{
		this.categoryName = categoryName;
	}
}
