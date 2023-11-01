package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class contains all the methods to verify Oracle ERP part
 * of E2E scenario for Transaction Source
 *
 * @author mgaikwad
 */

public class OracleCloudCreateTransactionSourcePage extends OracleCloudBasePage
{
	TaxLinkUIUtilities utils = new TaxLinkUIUtilities();

	public OracleCloudCreateTransactionSourcePage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(tagName = "html")
	public WebElement htmlElement;

	@FindBy(xpath = "//h1[contains(text(), 'Create Transaction Source')]")
	WebElement headerCreateTransactionSource;

	@FindBy(xpath = "//label[contains(text(),'Transaction Source Set')]/ancestor::tr/td[2]/span/input")
	private WebElement transSrcSetTextBox;

	@FindBy(xpath = "//label[contains(text(),'Name')]/ancestor::tr/td[2]/input")
	private WebElement transSrcNameTextBox;

	@FindBy(xpath = "//label[contains(text(),'Description')]/ancestor::tr/td[2]/input")
	private WebElement transSrcDescTextBox;

	@FindBy(xpath = "//label[contains(text(),'Type')]/ancestor::tr/td[2]/select")
	private WebElement transSrcTypeDropDown;

	@FindBy(xpath = "//label[contains(text(),'From Date')]/ancestor::tr/td[2]/input")
	private WebElement fromDateTextBox;

	@FindBy(xpath = "//label[contains(text(),'Active')]/ancestor::span/input")
	private WebElement activeSrcCheckbox;

	@FindBy(xpath = "//label[contains(text(),'Automatic transaction')]/ancestor::span/input")
	private WebElement automaticTransNumberCheckbox;

	@FindBy(xpath = "//label[contains(text(),'Last Transaction')]/ancestor::tr/td[2]/input")
	private WebElement lastTransNumberTextbox;

	@FindBy(xpath = "(//span[contains(text(),'ave and Close')])[last()]")
	private WebElement saveTransSrcButton;

	WebDriverWait expWait = new WebDriverWait(driver, 150);

	/**
	 * Method to add data in Create Transaction Source page in OracleERP
	 *
	 * @return transactionSrcName
	 */
	public String createTransactionSource( )
	{
		wait.waitForElementDisplayed(headerCreateTransactionSource);

		text.enterText(transSrcSetTextBox, "VTX BU SET");

		wait.waitForElementDisplayed(transSrcNameTextBox);
		text.enterText(transSrcNameTextBox, utils.randomText());

		wait.waitForElementDisplayed(transSrcDescTextBox);
		text.enterText(transSrcDescTextBox, utils.randomAlphaNumericText());

		expWait.until(ExpectedConditions.visibilityOfAllElements(transSrcTypeDropDown));
		dropdown.selectDropdownByIndex(transSrcTypeDropDown, 1);

		wait.waitForElementDisplayed(fromDateTextBox);
		text.enterText(fromDateTextBox, utils.getYesterdaysDateERP());

		checkbox.isCheckboxChecked(activeSrcCheckbox);
		checkbox.isCheckboxChecked(automaticTransNumberCheckbox);

		wait.waitForElementDisplayed(lastTransNumberTextbox);
		text.enterText(lastTransNumberTextbox, "1");

		htmlElement.sendKeys(Keys.HOME);
		wait.waitForElementDisplayed(headerCreateTransactionSource);

		String transactionSrcName = transSrcNameTextBox.getAttribute("title");
		VertexLogger.log("Transaction Source Name: " + transactionSrcName);

		click.clickElement(saveTransSrcButton);
		jsWaiter.sleep(10000);

		return transactionSrcName;
	}
}
