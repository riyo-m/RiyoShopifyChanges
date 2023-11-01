package com.vertex.quality.connectors.dynamics365.finance.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Vertex Settings common data
 *
 * @author Shiva Mothkula
 */
public enum DFinanceSammamishSOLineDetails
{
	Line1("VertexAR", "0.00000", "0.00", "Sales tax payable", "87759 - TAXABLE - STATE - KARNATAKA - State VAT",
		"A0001"),
	Line3("VertexAR", "1.40000", "14.00", "Sales tax payable",
		"102655 - TAXABLE - DISTRICT - REGIONAL TRANSIT AUTHORITY - Local Sales and Use Tax", "A0001"),
	Line2("VertexAR", "6.50000", "65.00", "Sales tax payable",
		"40256 - TAXABLE - STATE - WASHINGTON - Retail Sales and Use Tax", "A0001"),
	Line4("VertexAR", "0.00000", "0.00", "Sales tax payable",
			"78315 - TAXABLE - COUNTRY - INDIA - VAT", "A0001"),
	Line5("VertexAR", "0.00000", "0.00", "Sales tax payable",
		"40453 - NONTAXABLE - COUNTY - KING - Local Sales and Use Tax", "A0001"),
	Line6("VertexAR", "2.20000", "22.00", "Sales tax payable",
		"60576 - TAXABLE - CITY - SAMMAMISH - Local Sales and Use Tax", "A0001");

	String salesTaxCode;
	String percent;
	String actualSalesTaxAmount;
	String salesTaxDirection;
	String vertexTaxDescription;
	String description;

	DFinanceSammamishSOLineDetails( String salesTaxCode, String percent, String actualSalesTaxAmount,
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
