package com.vertex.quality.connectors.bigcommerce.api.enums;

import lombok.Getter;

/**
 * Test Data for Bigcommerce.
 *
 * @author rohit.mogane
 */
@Getter
public enum BigCommerceTestData
{
	AUSTRIA_TAX_RATE("0.2"),
	BELGIUM_TAX_RATE("0.21"),
	US_BELGIUM_TAX_RATE("0.0"),
	US_AUSTRIA_RATE("0.0"),
	TAX_PATH("documents[0].items[0].price.sales_tax_summary[0].rate"),
	TAX_PATH_1("documents[0].items[0].price.sales_tax_summary[0].rate"),
	TAX_PATH_2("documents[1].items[0].price.sales_tax_summary[0].rate"),
	TAX_PATH_3("documents[2].items[0].price.sales_tax_summary[0].rate"),
	WRAPPING("wrapping"),
	HANDLING("handling"),
	SHIPPING("shipping"),
	REFUND("refund"),
	EU_AUSTRIA_TAX_RATE(0.0),
	EU_AUSTRIA_TAX_AMOUNT(0.0),
	EU_AUSTRIA_LOCAL_TAX_RATE(0.06),
	EU_AUSTRIA_LOCAL_TAX_AMOUNT(6.6),
	EU_BELGIUM_TAX_RATE(0.0),
	EU_BELGIUM_TAX_AMOUNT(0.0),
	EU_BELGIUM_LOCAL_TAX_RATE(0.06),
	EU_BELGIUM_LOCAL_TAX_AMOUNT(6.6),
	CZECH_TAX_RATE("0.21"),
	US_CZECH_TAX_RATE("0.0"),
	EXPECTED_TAX_RATE(0.0),
	EXPECTED_TAX_AMOUNT(0.0),
	EXPECTED_LOCAL_TAX_RATE(0.06),
	EXPECTED_LOCAL_TAX_AMOUNT(6.6),
	DENMARK_TAX_RATE("0.25"),
	US_DENMARK_TAX_RATE("0.0"),
	FINLAND_TAX_RATE("0.24"),
	US_FINLAND_TAX_RATE("0.0"),
	TAX_JSON_PATH("documents[0].items[0].price.sales_tax_summary[0].rate"),
	TAX_JSON_PATH_1("documents[0].items[0].price.sales_tax_summary[1].rate"),
	TAX_JSON_PATH_2("documents[0].items[0].price.sales_tax_summary[3].rate"),
	WA_TAX_RATE("0.065"),
	LA_TAX_RATE("0.06"),
	ORLEANS_TAX("0.0445"),
	CO_TAX("0.029"),
	NJ_NO_TAX("0.0"),
	CALIFORNIA_TAX_RATE("0.06"),
	REFUND_ONE_DAY_MONTH(1),
	REFUND_ONE_LESS_DAY_MONTH(-1),
	REFUND_ZERO_DAY_MONTH(0),
	REFUND_THREE_DAY_MONTH(3),
	REFUND_SIX_LESS_DAY_MONTH(-6),
	REFUND_THREE_LESS_DAY_MONTH(-3);

	public String data;
	public double rate;
	public int dayMonth;

	BigCommerceTestData( final double rate )
	{
		this.rate = rate;
	}

	BigCommerceTestData( final String data )
	{
		this.data = data;
	}

	BigCommerceTestData( final int dayMonth )
	{
		this.dayMonth = dayMonth;
	}
}
