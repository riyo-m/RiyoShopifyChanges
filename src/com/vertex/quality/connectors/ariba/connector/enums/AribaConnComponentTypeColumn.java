package com.vertex.quality.connectors.ariba.connector.enums;

import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnComponentTaxTypesPage;
import lombok.Getter;

/**
 * the different columns in the table of configurations of component tax types on
 * {@link AribaConnComponentTaxTypesPage}
 *
 * fields are checkboxes, text fields, and one link to a dialog
 * some fields are posting keys, and all of those are (currently?) text fields
 *
 * @author ssalisbury
 */
@Getter
public enum AribaConnComponentTypeColumn
{
	COMPONENT_TAX_TYPE("Component Tax Type", AribaConnTableFieldType.TEXT_FIELD),
	EXTERNAL_TAX_TYPES("External Tax Types", AribaConnTableFieldType.DIALOG_LINK),
	SELF_ASSESSES_TAX_ACCOUNT_INSTRUCTION("Self-Assessed Tax Account Instruction", AribaConnTableFieldType.TEXT_FIELD);

	private final String name;
	private final AribaConnTableFieldType type;

	AribaConnComponentTypeColumn( final String columnName, final AribaConnTableFieldType fieldType )
	{
		this.name = columnName;
		this.type = fieldType;
	}
}
