package com.vertex.quality.connectors.taxlink.ui.enums;

/**
 * Enums for PO Tax calc excl tab
 *
 * @author mgaikwad
 */

public enum PoTaxCalcExcl
{
	PoTaxCalcExclDetails("PO Tax Calculation Exclusions", "Add PO Tax Exclusion", "View PO Tax Exclusion",
		"Edit PO Tax Exclusion");

	public String headerPoTaxCalcExclPage;
	public String headerAddPoTaxCalcExclPage;
	public String headerViewPoTaxCalcExclPage;
	public String headerEditPoTaxCalcExclPage;

	PoTaxCalcExcl( String headerPoTaxCalcExclPage, String headerAddPoTaxCalcExclPage,
		String headerViewPoTaxCalcExclPage, String headerEditPoTaxCalcExclPage )
	{
		this.headerPoTaxCalcExclPage = headerPoTaxCalcExclPage;
		this.headerAddPoTaxCalcExclPage = headerAddPoTaxCalcExclPage;
		this.headerViewPoTaxCalcExclPage = headerViewPoTaxCalcExclPage;
		this.headerEditPoTaxCalcExclPage = headerEditPoTaxCalcExclPage;
	}
}
