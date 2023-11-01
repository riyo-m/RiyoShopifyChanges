package com.vertex.quality.connectors.commercetools.api.enums;

import lombok.Getter;

@Getter
public enum CommerceToolsProductID
{

	TAXABLE_PRODUCT1("8b79a01a-ee9c-4dbc-8e6e-497ae6784388"),
	TAXABLE_PRODUCT2("31a3c903-2232-4c00-bd8c-b9ba59ab7eaa"),
	EXEMPT_PRODUCT("3d5c9068-c0de-4bf9-b9fc-d012557410d9");

	public String commerceToolsProductID;

	CommerceToolsProductID(String productID)
	{
		this.commerceToolsProductID=productID;
	}

}
