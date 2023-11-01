package com.vertex.quality.connectors.ariba.connector.pages.configuration;

import com.vertex.quality.connectors.ariba.connector.enums.AribaConnAccountFieldMappingSourceSelector;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnMenuBasePage;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * configures the mapping, in the calculated tax data of an O Series response,
 * of Vertex's fields in the O Series response's format to Ariba's fields in
 * their system so that the Ariba response message can be created
 * The enums AribaUiTaxResponseField and AribaUiAccountFieldMappingSourceSelector
 * are handled by this class
 *
 * @author ssalisbury
 */
public class AribaConnAccountingFieldMappingPage extends AribaConnMenuBasePage
{
	/* the default displayed text for the field (when it's empty) is stored in a hidden option element.
	 that element doesn't show up in the dropdown of field options, yet it returns true for WebElement.isDisplayed()
	 */
	protected final String hiddenDefaultFieldOptionName = "Specify source field";

	public AribaConnAccountingFieldMappingPage( WebDriver driver )
	{
		super(driver, "Accounting Field Mappings");
	}

	/**
	 * gets a list of the displayed names of the ariba mapper's options
	 *
	 * @return a list of the displayed names of the ariba mapper's options
	 * Fail- returns an empty list if the ariba mapper has no options
	 *
	 * @author ssalisbury
	 */
	public List<String> getSourceFieldOptionNames( final AribaConnAccountFieldMappingSourceSelector aribaMapper )
	{
		List<String> fieldOptions = dropdown.getDropdownDisplayOptions(aribaMapper.getLoc());
		//this only returns the names of the options that actually show up in the dropdown
		fieldOptions.removeIf(optionName -> optionName.equals(this.hiddenDefaultFieldOptionName));
		return fieldOptions;
	}
}
