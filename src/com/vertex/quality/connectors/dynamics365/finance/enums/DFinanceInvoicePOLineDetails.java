package com.vertex.quality.connectors.dynamics365.finance.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Vertex Settings common data
 *
 * @author Shiva Mothkula
 */
public enum DFinanceInvoicePOLineDetails
{
	Line1("VertexAP", "6.50000", "0.78", "Sales tax receivable",
		"40256 - TAXABLE - STATE - WASHINGTON - Retail Sales and Use Tax", "A0001"),
	Line2("VertexAP", "1.30000", "0.16", "Sales tax receivable",
		"40587 - TAXABLE - COUNTY - LEWIS - Local Sales and Use Tax", "A0001"),
	Line3("VertexAP", "6.50000", "54.21", "Sales tax receivable",
				  "40256 - TAXABLE - STATE - WASHINGTON - Retail Sales and Use Tax", "AP Invoice Approval"),
	Line4("VertexAP", "1.30000", "10.84", "Sales tax receivable",
				  "40587 - TAXABLE - COUNTY - LEWIS - Local Sales and Use Tax", "AP Invoice Approval"),
	Line5("VertexAP", "0.00000", "0.00", "Sales tax receivable",
			"78283 - EXEMPT - COUNTRY - GERMANY - VAT", "D0003"),
	Line6("VertexAP", "0.00000", "0.00", "Sales tax receivable",
			"78283 - EXEMPT - COUNTRY - GERMANY - VAT", "D0001"),
	Line7("VertexAP", "6.50000", "0.78", "Sales tax receivable",
			"40256 - TAXABLE - STATE - WASHINGTON - Retail Sales and Use Tax", "Invoice"),
	Line8("VertexAP", "1.30000", "0.16", "Sales tax receivable",
			"40587 - TAXABLE - COUNTY - LEWIS - Local Sales and Use Tax", "Invoice"),
	Line9("VertexAP", "0.00000", "0.00", "Sales tax payable",
			"78283 - TAXABLE - COUNTRY - GERMANY - VAT", "AP Invoice Register"),
	Line10("V_RC_G", "19.00000", "34.16", "Sales tax receivable",
			"78283 - TAXABLE - COUNTRY - GERMANY - VAT", "AP Invoice Register"),
	Line11("V_RC_G", "19.00000", "136.65", "Sales tax receivable",
			"78283 - TAXABLE - COUNTRY - GERMANY - VAT", "AP Invoice Register"),
	Line12("VAT_CODE", "19.00000", "170.81", "Sales tax payable",
			"78283 - TAXABLE - COUNTRY - GERMANY - VAT", "AP Invoice Register"),
	Line13("VertexAP", "0.00000", "0.00", "Sales tax receivable",
			"78283 - TAXABLE - COUNTRY - GERMANY - VAT", "AP Invoice Approval"),
	Line14("V_RC_G", "19.00000", "34.16", "Sales tax receivable",
			"78283 - TAXABLE - COUNTRY - GERMANY - VAT", "AP Invoice Approval"),
	Line15("V_RC_G", "19.00000", "136.65", "Sales tax receivable",
			"78283 - TAXABLE - COUNTRY - GERMANY - VAT", "AP Invoice Approval"),
	Line16("VAT_CODE", "19.00000", "-170.81", "Sales tax payable",
			"78283 - TAXABLE - COUNTRY - GERMANY - VAT", "AP Invoice Approval");

	String salesTaxCode;
	String percent;
	String actualSalesTaxAmount;
	String salesTaxDirection;
	String vertexTaxDescription;
	String description;

	DFinanceInvoicePOLineDetails( String salesTaxCode, String percent, String actualSalesTaxAmount,
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
