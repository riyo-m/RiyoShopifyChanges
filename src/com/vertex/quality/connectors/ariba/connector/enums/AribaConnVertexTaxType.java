package com.vertex.quality.connectors.ariba.connector.enums;

import lombok.Getter;

/**
 * the different categories of tax that O-Series recognizes
 *
 * @author ssalisbury
 */
@Getter
public enum AribaConnVertexTaxType
{
	SALES("SALES"),
	SELLER_USE("SELLER_USE"),
	CONSUMERS_USE("CONSUMERS_USE"),
	VAT("VAT"),
	IMPORT_VAT("IMPORT_VAT"),
	NONE("NONE");

	private String val;

	AribaConnVertexTaxType( final String taxType )
	{
		this.val = taxType;
	}
}
