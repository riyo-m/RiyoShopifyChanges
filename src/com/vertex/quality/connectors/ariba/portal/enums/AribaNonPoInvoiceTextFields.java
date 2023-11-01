package com.vertex.quality.connectors.ariba.portal.enums;

import com.vertex.quality.connectors.ariba.portal.interfaces.AribaPortalTextField;

/**
 * contains the names and types of all the non po invoice page fields
 *
 * @author osabha
 */
public enum AribaNonPoInvoiceTextFields implements AribaPortalTextField
{
	SUPPLIER("Supplier:", true),
	SUPPLIER_INVOICE("Supplier Invoice #:", false),
	SHIP_FROM("Ship From:", true),
	PLANT("Plant:", true),
	DELIVER_TO("Deliver To:", false),
	REMIT_TO_ADDRESS("Remit To Address:", true),
	COMMODITY_CODE("Commodity Code:", true),
	FULL_DESCRIPTION("Full Description:", false);

	public String labelText;
	public boolean isCombobox;

	AribaNonPoInvoiceTextFields( final String text, final boolean isCombo )
	{
		this.labelText = text;
		this.isCombobox = isCombo;
	}

	public String getLabel( )
	{
		return this.labelText;
	}

	public boolean isCombobox( )
	{
		return this.isCombobox;
	}
}
