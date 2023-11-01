package com.vertex.quality.connectors.accumatica.enums;

import lombok.Getter;
import org.openqa.selenium.By;

/**
 * this describes comboboxes in Acumatica
 * Combobox refers to text field input which drop down a list of suggested values
 * after some amount of text has been entered.
 * They also have a magnifiying glass button which opens a popup for searching
 * the legal values.
 */
@Getter
public enum AcumaticaCombobox
{
	//on Customers page
	CUSTOMER_COUNTRY("_DefAddress_edCountryID"),
	CUSTOMER_STATE("_DefAddress_edState"),
	CUSTOMER_DEFAULT_TAX_ZONE("_DefLocation_edCTaxZoneID");

	private By textField;
	private String distinctiveIdContents;
	//the portion of its id attribute which can actually be used to identify it (i.e. the part which isn't specifying what tab the combobox is on)

	AcumaticaCombobox( String idContents )
	{
		this.distinctiveIdContents = idContents;
		final String textFieldsEndOfIdSelector = String.format("[id$='%s_text']", distinctiveIdContents);
		this.textField = By.cssSelector(textFieldsEndOfIdSelector);
	}
}