package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * This page contains all the methods to add Payables Lookup
 *
 * @author Shilpi.Verma
 */

public class OracleCloudPayablesLookupsPage extends OracleCloudBasePage
{
	TaxLinkUIUtilities util = new TaxLinkUIUtilities();

	public OracleCloudPayablesLookupsPage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//label[contains(text(), ' Lookup Type')]/preceding-sibling::input[@type = 'text']")
	WebElement lookupTypeField;

	@FindBy(xpath = "//button[contains(text(), 'Search')]")
	WebElement searchButton;

	@FindBy(xpath = "//a[@href='#'][@role= 'button']/img[@title='Create']")
	WebElement addSign;

	@FindBy(xpath = "//label[contains(text(), 'Lookup Code')]/preceding-sibling::input")
	WebElement lookupCodeField;

	@FindBy(xpath = "(//label[contains(text(), 'Meaning')]/preceding-sibling::input)[3]")
	WebElement meaningField;

	@FindBy(xpath = "//label[contains(text(), 'Reference Data Set')]/preceding-sibling::select")
	WebElement refDataDrpDown;

	@FindBy(xpath = "(//label[contains(text(), 'Start Date')]/preceding-sibling::input[@placeholder = 'm/d/yy'])[1]")
	WebElement startDateField;

	@FindBy(xpath = "//span[contains(text(), 'Save')]")
	WebElement saveButton;

	@FindBy(xpath = "//span[contains(text(), 'ave and Close')]")
	WebElement saveCloseButton;

	/**
	 * Method to add Payables Look Up
	 *
	 * @return LookUp Code
	 */
	public String addLookup( )
	{
		wait.waitForElementEnabled(lookupTypeField);
		text.enterText(lookupTypeField, "Source");

		click.clickElement(searchButton);
		wait.waitForElementEnabled(addSign, 4);

		click.clickElement(addSign);
		wait.waitForElementEnabled(lookupCodeField);

		String lookupCode = util.randomNumber("5");
		String meaning = lookupCode + "TEST";

		text.enterText(lookupCodeField, lookupCode);
		text.enterText(meaningField, meaning);
		dropdown.selectDropdownByDisplayName(refDataDrpDown, "Common Set");
		text.clickElementAndEnterText(startDateField, util.getCurrDate("M/d/yy"));

		click.clickElement(saveButton);
		jsWaiter.sleep(1000);
		click.clickElement(saveCloseButton);

		return meaning;
	}
}
