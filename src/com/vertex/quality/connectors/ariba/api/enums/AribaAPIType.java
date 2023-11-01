package com.vertex.quality.connectors.ariba.api.enums;

import lombok.Getter;

/**
 * Ariba API Type field values
 *
 * @author dgorecki osabha
 */
@Getter
public enum AribaAPIType
{
	REQUISITION("ariba.purchasing.core.Requisition"),
	INVOICE_RECONCILIATION("ariba.invoicing.core.InvoiceReconciliation"),
	NOT_APPLICABLE("");

	private String value;

	AribaAPIType( final String value )
	{
		this.value = value;
	}
}
