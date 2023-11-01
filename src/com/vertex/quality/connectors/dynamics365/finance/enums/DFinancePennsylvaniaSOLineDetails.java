package com.vertex.quality.connectors.dynamics365.finance.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Vertex Settings common data
 *
 * @author Shiva Mothkula
 */
public enum DFinancePennsylvaniaSOLineDetails
{
	Line1("VertexAR", "6.00000", "300.00", "Sales tax payable",
		"31152 - TAXABLE - STATE - PENNSYLVANIA - Sales and Use Tax", "A0001");

	String salesTaxCode;
	String percent;
	String actualSalesTaxAmount;
	String salesTaxDirection;
	String vertexTaxDescription;
	String description;

	DFinancePennsylvaniaSOLineDetails( String salesTaxCode, String percent, String actualSalesTaxAmount,
		String salesTaxDirection, String vertexTaxDescription, String description )
	{
		this.salesTaxCode = salesTaxCode;
		this.percent = percent;
		this.actualSalesTaxAmount = actualSalesTaxAmount;
		this.salesTaxDirection = salesTaxDirection;
		this.vertexTaxDescription = vertexTaxDescription;
		this.description = description;
	}

	/**
	 * Get the data from enum
	 *
	 * @return
	 */
	public Map<String, String> getLineDataMap( )
	{
		Map<String, String> lineDataMap = new HashMap<String, String>();

		lineDataMap.put("Sales tax code", salesTaxCode);
		lineDataMap.put("Percent", percent);
		lineDataMap.put("Actual sales tax amount", actualSalesTaxAmount);
		lineDataMap.put("Sales tax direction", salesTaxDirection);
		lineDataMap.put("Vertex tax description", vertexTaxDescription);
		lineDataMap.put("Description", description);

		return lineDataMap;
	}
}
