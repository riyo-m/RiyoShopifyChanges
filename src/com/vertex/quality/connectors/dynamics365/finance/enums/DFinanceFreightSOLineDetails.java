package com.vertex.quality.connectors.dynamics365.finance.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Vertex Settings common data
 *
 * @author Shiva Mothkula
 */
public enum DFinanceFreightSOLineDetails
{
	Line1("VertexAR", "4.00000", "6.00", "Sales tax payable", "6244 - TAXABLE - STATE - GEORGIA - Sales and Use Tax",
		"Freight"),
	Line2("VertexAR", "4.00000", "6.00", "Sales tax payable", "7308 - TAXABLE - COUNTY - WILCOX - Local Sales and Use Tax",
			"Freight"),
	Line3("VertexAR", "4.00000", "40.00", "Sales tax payable", "6244 - TAXABLE - STATE - GEORGIA - Sales and Use Tax",
			"1000"),
	Line4("VertexAR", "4.00000", "40.00", "Sales tax payable", "7308 - TAXABLE - COUNTY - WILCOX - Local Sales and Use Tax",
			"1000"),
	Line5("VertexAR", "4.00000", "6.00", "Sales tax payable", "6244 - TAXABLE - STATE - GEORGIA - Sales and Use Tax",
			"Miscellanous charges"),
	Line6("VertexAR", "4.00000", "6.00", "Sales tax payable", "7308 - TAXABLE - COUNTY - WILCOX - Local Sales and Use Tax",
			"Miscellanous charges"),

	Line7("VertexAR", "4.00000", "1.60", "Sales tax payable", "6244 - TAXABLE - STATE - GEORGIA - Sales and Use Tax",
			"Miscellanous charges"),
	Line8("VertexAR", "4.00000", "1.60", "Sales tax payable", "7308 - TAXABLE - COUNTY - WILCOX - Local Sales and Use Tax",
			"Miscellanous charges"),
	Line9("VertexAR", "4.00000", "80.00", "Sales tax payable", "6244 - TAXABLE - STATE - GEORGIA - Sales and Use Tax",
			"DP_001"),
	Line10("VertexAR", "4.00000", "80.00", "Sales tax payable", "7308 - TAXABLE - COUNTY - WILCOX - Local Sales and Use Tax",
			"DP_001"),
	Line11("VertexAR", "4.00000", "96.00", "Sales tax payable", "6244 - TAXABLE - STATE - GEORGIA - Sales and Use Tax",
			"DP_001"),
	Line12("VertexAR", "4.00000", "96.00", "Sales tax payable", "7308 - TAXABLE - COUNTY - WILCOX - Local Sales and Use Tax",
			"DP_001"),
	Line13("VertexAR", "0.21540", "0.28", "Sales tax payable", "4220 - TAXABLE - STATE - COLORADO - Retail Delivery Fee", "Freight"),
	Line14("VertexAR", "2.90000", "3.77", "Sales tax payable", "4220 - TAXABLE - STATE - COLORADO - Sales and Use Tax", "Freight"),
	Line15("VertexAR", "1.00000", "1.30", "Sales tax payable", "4377 - TAXABLE - COUNTY - DOUGLAS - Local Sales and Use Tax", "Freight"),
	Line16("VertexAR", "1.00000", "1.30", "Sales tax payable", "78135 - TAXABLE - DISTRICT - REGIONAL TRANSPORTATION DISTRICT - Local Sales and Use Tax", "Freight"),
	Line17("VertexAR", "0.10000", "0.13", "Sales tax payable", "78399 - TAXABLE - DISTRICT - SCIENTIFIC AND CULTURAL FACILITIES DISTRICT - Local Sales and Use Tax", "Freight"),
	Line18("VertexAR", "2.90000", "29.00", "Sales tax payable", "4220 - TAXABLE - STATE - COLORADO - Sales and Use Tax", "1000"),
	Line19("VertexAR", "1.00000", "10.00", "Sales tax payable", "4377 - TAXABLE - COUNTY - DOUGLAS - Local Sales and Use Tax", "1000"),
	Line20("VertexAR", "1.00000", "10.00", "Sales tax payable", "78135 - TAXABLE - DISTRICT - REGIONAL TRANSPORTATION DISTRICT - Local Sales and Use Tax", "1000"),
	Line21("VertexAR", "0.10000", "1.00", "Sales tax payable", "78399 - TAXABLE - DISTRICT - SCIENTIFIC AND CULTURAL FACILITIES DISTRICT - Local Sales and Use Tax", "1000");

	String salesTaxCode;
	String percent;
	String actualSalesTaxAmount;
	String salesTaxDirection;
	String vertexTaxDescription;
	String description;

	DFinanceFreightSOLineDetails( String salesTaxCode, String percent, String actualSalesTaxAmount,
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
