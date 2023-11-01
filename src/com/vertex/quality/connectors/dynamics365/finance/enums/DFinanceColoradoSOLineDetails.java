package com.vertex.quality.connectors.dynamics365.finance.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Vertex Settings common data
 *
 * @author Shiva Mothkula
 */
public enum DFinanceColoradoSOLineDetails
{
	Line1("VertexAR", "2.90000", "29.00", "Sales tax payable", "4220 - TAXABLE - STATE - COLORADO - Sales and Use Tax",
		null),
	Line2("VertexAR", "1.00000", "10.00", "Sales tax payable",
		"78135 - TAXABLE - DISTRICT - REGIONAL TRANSPORTATION DISTRICT - Local Sales and Use Tax", null),
	Line3("VertexAR", "0.10000", "1.00", "Sales tax payable",
		"78399 - TAXABLE - DISTRICT - SCIENTIFIC AND CULTURAL FACILITIES DISTRICT - Local Sales and Use Tax", null),
	Line4("VertexAR", "2.90000", "4.35", "Sales tax payable", "4220 - TAXABLE - STATE - COLORADO - Sales and Use Tax",
		"Freight"),
	Line5("VertexAR", "1.00000", "1.50", "Sales tax payable",
		"78135 - TAXABLE - DISTRICT - REGIONAL TRANSPORTATION DISTRICT - Local Sales and Use Tax", "Freight"),
	Line6("VertexAR", "0.10000", "0.15", "Sales tax payable",
		"78399 - TAXABLE - DISTRICT - SCIENTIFIC AND CULTURAL FACILITIES DISTRICT - Local Sales and Use Tax",
		"Freight"),
	Line7("VertexAR", "0.00000", "0.00", "Sales tax payable",
		"7308 - EXEMPT - COUNTY - WILCOX - Local Sales and Use Tax", "Prd_110719114478"),
	Line8("VertexAR", "0.00000", "0.00", "Sales tax payable", "78315 - TAXABLE - COUNTRY - INDIA - VAT",
		"Prd_110719114478"),
	Line9("VertexAR", "0.00000", "0.00", "Sales tax payable", "87759 - TAXABLE - STATE - KARNATAKA - State VAT",
		"Prd_110719114478"),
	Line10("VertexAR", "0.00000", "0.00", "Sales tax payable", "6244 - EXEMPT - STATE - GEORGIA - Sales and Use Tax",
		"Prd_110719114478"),
	Line11("VertexAR", "4.00000", "6.00", "Sales tax payable", "6244 - TAXABLE - STATE - GEORGIA - Sales and Use Tax",
			"Freight"),
	Line12("VertexAR", "4.00000", "6.00", "Sales tax payable", "7308 - TAXABLE - COUNTY - WILCOX - Local Sales and Use Tax",
			"Freight"),
	Line13("VertexAR", "4.00000", "40.00", "Sales tax payable", "6244 - TAXABLE - STATE - GEORGIA - Sales and Use Tax",
			null),
	Line14("VertexAR", "4.00000", "40.00", "Sales tax payable", "7308 - TAXABLE - COUNTY - WILCOX - Local Sales and Use Tax",
			null);

	String salesTaxCode;
	String percent;
	String actualSalesTaxAmount;
	String salesTaxDirection;
	String vertexTaxDescription;
	String description;

	DFinanceColoradoSOLineDetails( String salesTaxCode, String percent, String actualSalesTaxAmount,
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
