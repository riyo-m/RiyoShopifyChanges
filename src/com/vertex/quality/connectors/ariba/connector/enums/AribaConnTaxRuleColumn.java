package com.vertex.quality.connectors.ariba.connector.enums;

import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnTaxRulesPage;
import lombok.Getter;

/**
 * the different columns in the table of tax mapping rules on {@link AribaConnTaxRulesPage}
 *
 * fields are either dropdowns or text fields
 *
 * @author ssalisbury
 */
@Getter
public enum AribaConnTaxRuleColumn
{
	ARIBA_COMPONENT_TAX_TYPE("Ariba Component Tax Type", AribaConnTableFieldType.DROPDOWN),
	VERTEX_TAX_TYPE("Vertex Tax Type", AribaConnTableFieldType.DROPDOWN),
	VERTEX_JURISDICTION_LEVEL("Vertex Jurisdiction Level", AribaConnTableFieldType.DROPDOWN),
	VERTEX_VAT_TAX_TYPE("Vertex VAT Tax Type", AribaConnTableFieldType.DROPDOWN),
	VERTEX_IMPOSITION_TYPE("Vertex Imposition Type", AribaConnTableFieldType.TEXT_FIELD),
	VERTEX_IMPOSITION_NAME("Vertex Imposition Name", AribaConnTableFieldType.TEXT_FIELD),
	VERTEX_JURISDICTION_NAME("Vertex Jurisdiction Name", AribaConnTableFieldType.TEXT_FIELD);

	private final String name;
	private final AribaConnTableFieldType type;

	AribaConnTaxRuleColumn( final String columnName, final AribaConnTableFieldType fieldType )
	{
		this.name = columnName;
		this.type = fieldType;
	}
}
