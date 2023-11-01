package com.vertex.quality.connectors.ariba.connector.enums;

import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnAccountingFieldMappingPage;
import lombok.Getter;
import org.openqa.selenium.By;

/**
 * the field selector dropdowns which are used to select specific
 * pieces of calculated-tax-information in an O Series response message and map
 * them to fields in Ariba's data format (ie the names of the selectors below)
 *
 * Used on the {@link AribaConnAccountingFieldMappingPage}
 *
 * @author ssalisbury
 */
@Getter
public enum AribaConnAccountFieldMappingSourceSelector
{
	// @formatter:off	locator			ariba field 'level'			default test option
	TAX_CODE(			By.id("taxCodeSel"),	"Line",	AribaConnTaxResponseField.FILING_CATEGORY_CODE.displayName),
	ACCOUNT_INSTRUCTION(By.id("acctInstSel"),	"Tax",	AribaConnTaxResponseField.FLEX_CODE_17.displayName),
	DEDUCTIBLE_ACCOUNT(	By.id("dedAcctSel"),	"Tax",	AribaConnTaxResponseField.CLEAR_FIELD.displayName),
	INVOICE_CITATION( By.id("invCitSel"),	"Tax",	AribaConnTaxResponseField.VERTEX_TAX_CODE.displayName);
	// @formatter:on

	public By loc;
	public String aribaFieldLevel;

	// the option that the current selector would be set to in a test where the
	// selector just had to be set to some value
	public String defaultSourceFieldName;

	AribaConnAccountFieldMappingSourceSelector( final By loc, final String fieldLevel, final String defaultSource )
	{
		this.loc = loc;
		this.aribaFieldLevel = fieldLevel;
		this.defaultSourceFieldName = defaultSource;
	}
}
