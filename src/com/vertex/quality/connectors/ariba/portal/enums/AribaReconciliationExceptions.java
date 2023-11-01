package com.vertex.quality.connectors.ariba.portal.enums;

import lombok.Getter;

/**
 * this class represents the header names of all the reconciliation exceptions
 *
 * @author osabha
 */
@Getter
public enum AribaReconciliationExceptions
{
	OVER_TAX_EXCEPTION("Over Tax Variance"),
	UNDER_TAX_EXCEPTION("Under Tax Variance"),
	AMOUNT_EXCEPTION("Amount Variance"),
	QUANTITY_EXCEPTION("Received Quantity Variance"),
	LINE_TAX_AMOUNT_EXCEPTION("Line Tax Amount Variance"),
	GENERAL_TAX_EXCEPTION("Over Tax General"),
	TAX_AMOUNT_EXCEPTION("Tax Amount Variance");

	private String exception;

	AribaReconciliationExceptions( final String exception ) { this.exception = exception; }
}
