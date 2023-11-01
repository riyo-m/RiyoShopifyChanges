package com.vertex.quality.connectors.taxlink.ui.enums;

/**
 * Enums for Legal Entity
 *
 * @author Shilpi.Verma
 */
public enum LegalEntity
{
	FIELDS("Legal Entity", "Add Legal Entity", "01/01/1900", "Fusion Instance Name: ecog-dev3-us2");

	public String headerAddLegalEntity;
	public String headerSummaryPage;
	public String defaultStartDate;
	public String instanceName;

	LegalEntity( String headerSummaryPage, String headerAddLegalEntity, String defaultStartDate, String instanceName )
	{

		this.headerAddLegalEntity = headerAddLegalEntity;
		this.headerSummaryPage = headerSummaryPage;
		this.defaultStartDate = defaultStartDate;
		this.instanceName = instanceName;
	}
}
