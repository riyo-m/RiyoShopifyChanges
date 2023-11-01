package com.vertex.quality.common.enums;

import lombok.Getter;

/**
 * Vertex Oseries valid Tax Types
 *
 * @author dgorecki
 */
@Getter
public enum VertexTaxType
{
	SALES("SALES"),
	SELLER_USE("SELLER_USE"),
	CONSUMERS_USE("CONSUMERS_USE"),
	VAT("VAT"),
	IMPORT_VAT("IMPORT_VAT"),
	NONE("NONE");

	private String value;

	VertexTaxType( final String value )
	{
		this.value = value;
	}
}
