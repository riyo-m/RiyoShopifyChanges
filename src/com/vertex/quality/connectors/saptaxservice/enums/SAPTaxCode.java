package com.vertex.quality.connectors.saptaxservice.enums;

/**
 * Tax codes available for direct payload
 *
 * @author hho
 */
public enum SAPTaxCode
{
	TAX_CODE_501("501"),
	TAX_CODE_503("503"),
	TAX_CODE_507("507"),
	TAX_CODE_511("511"),
	TAX_CODE_700("700"),
	TAX_CODE_701("701"),
	TAX_CODE_702("702"),
	TAX_CODE_703("703"),
	TAX_CODE_2("2"),
	TAX_CODE_5("5"),
	TAX_CODE_11("11"),
	TAX_CODE_20("20"),
	TAX_CODE_508("508"),
	TAX_CODE_509("509"),
	TAX_CODE_510("510"),
	TAX_CODE_512("512"),
	TAX_CODE_513("513"),
	TAX_CODE_514("514"),
	TAX_CODE_516("516"),
	TAX_CODE_517("517"),
	TAX_CODE_518("518"),
	TAX_CODE_519("519"),
	TAX_CODE_520("520"),
	TAX_CODE_522("522"),
	TAX_CODE_523("523"),
	TAX_CODE_524("524"),
	TAX_CODE_525("525"),
	TAX_CODE_10("10"),
	TAX_CODE_12("11"),
	TAX_CODE_13("13"),
	TAX_CODE_14("14"),
	TAX_CODE_15("15"),
	TAX_CODE_22("22");

	private String taxCode;

	SAPTaxCode( String taxCode )
	{
		this.taxCode = taxCode;
	}

	/**
	 * Gets the tax code
	 *
	 * @return the tax code
	 */
	public String getTaxCode( )
	{
		return this.taxCode;
	}
}
