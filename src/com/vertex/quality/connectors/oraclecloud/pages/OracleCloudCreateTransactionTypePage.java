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
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * This class contains all the methods to verify Oracle ERP part
 * of E2E scenario for Transaction Type
 *
 * @author mgaikwad
 */

public class OracleCloudCreateTransactionTypePage extends OracleCloudBasePage
{
	TaxLinkUIUtilities utils = new TaxLinkUIUtilities();

	public OracleCloudCreateTransactionTypePage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(tagName = "html")
	public WebElement htmlElement;

	@FindBy(xpath = "//h1[contains(text(), 'Create Transaction Type')]")
	WebElement headerCreateTransactionType;

	@FindBy(xpath = "//div[contains(text(), 'Create Reference Accounts')]")
	WebElement headerCreateRefAccounts;

	@FindBy(xpath = "//div[contains(text(), 'Search and Select: Business Unit')]")
	WebElement headerSearchBuCombobox;

	@FindBy(xpath = "//label[contains(text(),'Transaction Type Set')]/ancestor::tr/td[2]/span/input")
	private WebElement transTypeSetTextBox;

	@FindBy(xpath = "//label[contains(text(),'Legal Entity')]/ancestor::tr/td[2]/span/input")
	private WebElement legalEntityTextBox;

	@FindBy(xpath = "//label[contains(text(),'Name')]/ancestor::tr/td[2]/input")
	private WebElement transNameTextBox;

	@FindBy(xpath = "//label[contains(text(),'Transaction Class')]/ancestor::tr/td[2]/select")
	private WebElement transClassDropDown;

	@FindBy(xpath = "//label[contains(text(),'Transaction Status')]/ancestor::tr/td[2]/select")
	private WebElement transStatusDropDown;

	@FindBy(xpath = "//label[contains(text(),'From Date')]/ancestor::tr/td[2]/input")
	private WebElement fromDateTextBox;

	@FindBy(xpath = "//label[contains(text(),'Creation')]/ancestor::tr/td[2]/select")
	private WebElement creationSignDropdown;

	@FindBy(xpath = "//label[contains(text(),'Generate Bill')]/ancestor::tr/td[2]/select")
	private WebElement generateBillDropdown;

	@FindBy(xpath = "//label[contains(text(),'Credit Memo')]/ancestor::tr/td[2]/span/input")
	private WebElement creditMemoTypeDropdown;

	@FindBy(xpath = "(//span[contains(text(),'ave and Close')])[last()]")
	private WebElement saveTransTypeButton;

	@FindBy(xpath = "//a[@title='Create']")
	private WebElement addBusinessUnitIcon;

	@FindBy(xpath = "//a[@title='Search: Business Unit']")
	private WebElement businessUnitCombobox;

	@FindBy(xpath = "(//a[contains(text(),'Search')])[last()]")
	private WebElement buSearchOptionInCombobox;

	@FindBy(xpath = "(//label[contains(text(),'Name')]/ancestor::tr/td[2]/table/tbody/tr/td/span/input)[last()]")
	private WebElement buSearchNameTextbox;

	@FindBy(xpath = "//button[contains(text(),'Search')]")
	private WebElement buSearchButton;

	@FindBy(xpath = "(//span[contains(text(),'VTX_US_BU')])[last()]")
	private WebElement selectBuFromResults;

	@FindBy(xpath = "//button[contains(text(),'OK')]")
	private WebElement okButtonOnSearchBuPopUp;

	@FindBy(xpath = "//button[contains(text(),'Cancel')]")
	private WebElement cancelButtonOnSearchBuPopUp;

	@FindBy(xpath = "//button[contains(text(),'Cancel')]")
	private WebElement	cancelButtonOnBuPopUp;

	@FindBy(xpath = "(//label[contains(text(),'Revenue')]/ancestor::tr/td[2]/table/tbody/tr/td/span/input)[last()-1]")
	private WebElement revenueBuTextBox;

	@FindBy(xpath = "(//label[contains(text(),'Receivable')]/ancestor::tr/td[2]/table/tbody/tr/td/span/input)[last()-1]")
	private WebElement receivableBuTextBox;

	@FindBy(xpath = "//label[contains(text(),'Tax')]/ancestor::tr/td[2]/table/tbody/tr/td/span/input")
	private WebElement taxBuTextBox;

	@FindBy(xpath = "//label[contains(text(),'Freight')]/ancestor::tr/td[2]/table/tbody/tr/td/span/input")
	private WebElement freightBuTextBox;

	@FindBy(xpath = "//label[contains(text(),'Unearned')]/ancestor::tr/td[2]/table/tbody/tr/td/span/input")
	private WebElement unearnedBuTextBox;

	@FindBy(xpath = "//label[contains(text(),'Unbilled')]/ancestor::tr/td[2]/table/tbody/tr/td/span/input")
	private WebElement unbilledBuTextBox;

	@FindBy(xpath = "//label[contains(text(),'AutoInvoice')]/ancestor::tr/td[2]/table/tbody/tr/td/span/input")
	private WebElement autoInvoiceBuTextBox;

	@FindBy(xpath = "//button[@accesskey='K']")
	private WebElement okBuButton;

	WebDriverWait expWait = new WebDriverWait(driver, 150);
	FluentWait<WebDriver> fluentWait = new FluentWait<>(driver);

	/**
	 * Method to add data in Create Transaction Type page in OracleERP
	 *
	 * @return transactionName
	 */
	public String createTransactionType( )
	{
		wait.waitForElementDisplayed(headerCreateTransactionType);

		text.enterText(transTypeSetTextBox, "Common Set");

		wait.waitForElementDisplayed(legalEntityTextBox);
		text.enterText(legalEntityTextBox, "RI IFF Farms Ltd.");

		wait.waitForElementDisplayed(transNameTextBox);
		text.enterText(transNameTextBox, utils.randomAlphaNumericText());

		expWait.until(ExpectedConditions.visibilityOfAllElements(transClassDropDown));
		dropdown.selectDropdownByDisplayName(transClassDropDown, "Invoice");

		expWait.until(ExpectedConditions.visibilityOfAllElements(transStatusDropDown));
		dropdown.selectDropdownByIndex(transStatusDropDown, 2);

		wait.waitForElementDisplayed(fromDateTextBox);
		text.enterText(fromDateTextBox, utils.getYesterdaysDateERP());

		wait.waitForElementDisplayed(creationSignDropdown);
		dropdown.selectDropdownByDisplayName(creationSignDropdown, "Positive Sign");

		wait.waitForElementDisplayed(generateBillDropdown);
		dropdown.selectDropdownByDisplayName(generateBillDropdown, "Yes");

		jsWaiter.sleep(2000);
		text.enterText(creditMemoTypeDropdown, "Credit Memo");

		if ( createBuRefID() )
		{
			VertexLogger.log("Created Business Unit reference account!!");
		}
		else
		{
			VertexLogger.log("Failed to create Business Unit reference account!!");
		}

		jsWaiter.sleep(10000);

		htmlElement.sendKeys(Keys.HOME);

		fluentWait
			.pollingEvery(Duration.ofSeconds(1))
			.
				withTimeout(Duration.ofSeconds(15))
			.
				until(x -> headerCreateTransactionType.isDisplayed());

		String transactionName = transNameTextBox.getAttribute("title");
		VertexLogger.log("Transaction Name: " + transactionName);

		wait.waitForElementDisplayed(legalEntityTextBox);
		text.enterText(legalEntityTextBox, "RI IFF Farms Ltd.");

		expWait.until(ExpectedConditions.visibilityOfAllElements(transStatusDropDown));
		dropdown.selectDropdownByIndex(transStatusDropDown, 2);

		click.clickElement(saveTransTypeButton);

		return transactionName;
	}

	/**
	 * Method to add data in Create Reference Accounts BU Pop up
	 * in Create transaction type page in OracleERP
	 *
	 * @return buRefFlag
	 */
	public boolean createBuRefID( )
	{
		boolean buRefFlag;

		expWait.until(ExpectedConditions.elementToBeClickable(addBusinessUnitIcon));

		VertexLogger.log("Creating a BU Reference Account..");
		jsWaiter.sleep(5000);
		click.clickElement(addBusinessUnitIcon);

		wait.waitForElementDisplayed(headerCreateRefAccounts);

		if ( searchBu() )
		{
			wait.waitForElementNotDisplayed(cancelButtonOnSearchBuPopUp);
			text.enterText(revenueBuTextBox, "3111-00-21140-000-0000-0000");
			text.enterText(receivableBuTextBox, "3111-00-13005-000-0000-0000");
			text.enterText(taxBuTextBox, "3111-00-21530-000-0000-0000");
			text.enterText(freightBuTextBox, "3111-10-41160-410-2111-0000");
			text.enterText(unearnedBuTextBox, "3111-00-21140-000-0000-0000");
			text.enterText(unbilledBuTextBox, "3111-00-13005-000-0000-0000");
			text.enterText(autoInvoiceBuTextBox, "3111-00-13071-000-0000-0000");
			click.clickElement(okBuButton);
		}

		if ( headerCreateTransactionType.isDisplayed() )
		{
			buRefFlag = true;
		}
		else
		{
			buRefFlag = false;
		}

		return buRefFlag;
	}

	/**
	 * Method to search for Business unit in search pop up for Create Reference Accounts
	 * in Create transaction type page in OracleERP
	 *
	 * @return buSearchFlag
	 */
	public boolean searchBu( )
	{
		boolean buSearchFlag;

		click.clickElement(businessUnitCombobox);
		fluentWait
			.pollingEvery(Duration.ofSeconds(1))
			.
				withTimeout(Duration.ofSeconds(15))
			.
				until(x -> buSearchOptionInCombobox.isDisplayed());

		click.clickElement(buSearchOptionInCombobox);
		wait.waitForElementDisplayed(headerSearchBuCombobox);

		text.enterText(buSearchNameTextbox, "VTX_US_BU");
		click.clickElement(buSearchButton);

		wait.waitForElementDisplayed(selectBuFromResults);

		if ( selectBuFromResults
			.getText()
			.equals("VTX_US_BU") )
		{
			buSearchFlag = true;
			click.clickElement(selectBuFromResults);
			click.clickElement(okButtonOnSearchBuPopUp);
			VertexLogger.log("Selected Business Unit : VTX_US_BU");
		}
		else
		{
			buSearchFlag = false;
			VertexLogger.log("Unable to select Business Unit : VTX_US_BU");
		}
		return buSearchFlag;
	}
}
