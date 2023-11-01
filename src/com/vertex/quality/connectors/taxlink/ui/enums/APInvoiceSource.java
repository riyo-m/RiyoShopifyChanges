package com.vertex.quality.connectors.taxlink.ui.enums;

/**
 * Enum for AP Invoice Sources
 *
 * @author Shilpi.Verma
 */
public enum APInvoiceSource
{

	FIELDS("AP Invoice Source", "Add AP Invoice Source", "01/01/1900", "Fusion Instance Name: ecog-dev3-us2");

	public String headerAddAPInvoiceSource;
	public String headerSummaryPage;
	public String defaultStartDate;
	public String instanceName;
	public String fileDownloadPath;

	APInvoiceSource( String headerSummaryPage, String headerAddAPInvoiceSource, String defaultStartDate,
		String instanceName )
	{

		this.headerAddAPInvoiceSource = headerAddAPInvoiceSource;
		this.headerSummaryPage = headerSummaryPage;
		this.defaultStartDate = defaultStartDate;
		this.instanceName = instanceName;
	}
}
