package com.vertex.quality.connectors.ariba.connector.enums;

import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnAccountingFieldMappingPage;

/**
 * the different flex fields which custom ariba fields
 * can be mapped to on the {@link AribaConnAccountingFieldMappingPage}
 *
 * @author ssalisbury
 */
public enum AribaConnTaxResponseField
{
	CLEAR_FIELD(""),
	TAX_CODE("Tax Code"),
	VERTEX_TAX_CODE("Vertex Tax Code"),
	INVOICE_SUMMARYTEXT("Invoice Summary Text"),
	FILING_CATEGORY_CODE("Filing Category Code"),
	FLEX_CODE_1("Flex Code Field 1"),
	FLEX_CODE_2("Flex Code Field 2"),
	FLEX_CODE_3("Flex Code Field 3"),
	FLEX_CODE_4("Flex Code Field 4"),
	FLEX_CODE_5("Flex Code Field 5"),
	FLEX_CODE_6("Flex Code Field 6"),
	FLEX_CODE_7("Flex Code Field 7"),
	FLEX_CODE_8("Flex Code Field 8"),
	FLEX_CODE_9("Flex Code Field 9"),
	FLEX_CODE_10("Flex Code Field 10"),
	FLEX_CODE_11("Flex Code Field 11"),
	FLEX_CODE_12("Flex Code Field 12"),
	FLEX_CODE_13("Flex Code Field 13"),
	FLEX_CODE_14("Flex Code Field 14"),
	FLEX_CODE_15("Flex Code Field 15"),
	FLEX_CODE_16("Flex Code Field 16"),
	FLEX_CODE_17("Flex Code Field 17"),
	FLEX_CODE_18("Flex Code Field 18"),
	FLEX_CODE_19("Flex Code Field 19"),
	FLEX_CODE_20("Flex Code Field 20"),
	FLEX_CODE_21("Flex Code Field 21"),
	FLEX_CODE_22("Flex Code Field 22"),
	FLEX_CODE_23("Flex Code Field 23"),
	FLEX_CODE_24("Flex Code Field 24"),
	FLEX_CODE_25("Flex Code Field 25"),
	FLEX_NUM_1("Flex Numeric Field 1"),
	FLEX_NUM_2("Flex Numeric Field 2"),
	FLEX_NUM_3("Flex Numeric Field 3"),
	FLEX_NUM_4("Flex Numeric Field 4"),
	FLEX_NUM_5("Flex Numeric Field 5"),
	FLEX_NUM_6("Flex Numeric Field 6"),
	FLEX_NUM_7("Flex Numeric Field 7"),
	FLEX_NUM_8("Flex Numeric Field 8"),
	FLEX_NUM_9("Flex Numeric Field 9"),
	FLEX_NUM_10("Flex Numeric Field 10");

	public final String displayName;

	AribaConnTaxResponseField( final String displayedName )
	{
		this.displayName = displayedName;
	}
}
