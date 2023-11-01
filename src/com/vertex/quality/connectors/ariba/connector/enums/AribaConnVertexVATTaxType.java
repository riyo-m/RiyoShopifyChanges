package com.vertex.quality.connectors.ariba.connector.enums;

import lombok.Getter;

/**
 * the different categories of VAT that O-Series recognizes
 *
 * @author ssalisbury
 */
@Getter
public enum AribaConnVertexVATTaxType
{
	INPUT("INPUT"),
	IMPORT("IMPORT"),
	OUTPUT("OUTPUT"),
	INPUT_OUTPUT("INPUT_OUTPUT"),
	BLANK("");

	private String val;

	AribaConnVertexVATTaxType( final String vatType )
	{
		this.val = vatType;
	}
}
