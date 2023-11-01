package com.vertex.quality.connectors.concur.pages.panelPages;

import com.vertex.quality.connectors.concur.pages.base.ConcurBasePage;
import com.vertex.quality.connectors.concur.pojos.ConcurInvoiceRow;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * represents the invoice page for SAP Concur
 *
 * @author alewis
 */
public class ConcurInvoicePage extends ConcurBasePage
{

	protected final By createNewInvoice = By.xpath("//li/a[contains(text(),'Create New Invoice')]");
	protected final By requestNewVendor = By.className("btn_new");

	private final By vendorName = By.id("VendorForm_VendorInfo_VendorName");
	private final By address1 = By.id("VendorForm_VendorInfo_Address1");
	private final By address2 = By.id("VendorForm_VendorInfo_Address2");
	private final By city = By.id("VendorForm_VendorInfo_City");
	private final By state = By.id("VendorForm_VendorInfo_State");
	private final By zipCode = By.id("VendorForm_VendorInfo_PostalCode");
	private final By country = By.className("x-form-text");
	private final By countryCell = By.id("VendorForm_VendorInfo_CtryCode_cell");
	private final By currency = By.className("x-form-text");
	private final By currencyCell = By.id("VendorForm_VendorInfo_CrnKey_cell");
	private final By telephoneNumber = By.id("VendorForm_VendorInfo_PhoneNumber");
	private final By contactFirstName = By.id("VendorForm_VendorInfo_ContactFirstName");
	private final By windowFooter = By.className("x-window-footer");
	private final By okButton = By.className("x-btn-text");

	private final By dropDown = By.id("subViewDropdown");
	private final By searchInvoiceNumber = By.id("searchBy");
	private final By allMyInvoice = By.className("notActive");
	private final By searchBar = By.id("searchValue");
	private final By searchButton = By.xpath("//button/span/span[contains(text(),'Search')]");
	private final By checkBox = By.className("check");
	private final By moreActionsButton = By.id("moreactions");
	private final By buttonClass = By.className("btn");
	private final By recall = By.className("btn-link");
	private final By yesLoc = By.className("btn-lg");
	private final By delete = By.className("pull-left");
	private final By tableLocator = By.className("sapcnqr-data-grid__sticky-container");
	private final By titleLocator = By.tagName("thead");
	private final By invoiceRowLocator = By.tagName("tbody");
	private final By checkBoxLoc = By.tagName("input");
	private final By checkBoxLoc1=By.xpath("//th/span/label[contains(.,'Select all rows')]");
	private final By SearchcheckBoxID=By.xpath("(//input[@type='checkbox'])[2]");
	private final By companyLocations=By.xpath("(//a[contains(.,'Company Locations')])[2]");
	private final By newShipToLocation=By.xpath("//button[contains(.,'New')]");
	private final By newBillToLocation=By.xpath("(//button[contains(.,'New')])[2]");

	protected final By clickInvoice = By.linkText("Invoice");

	private final By locationName=By.xpath("(//input[@name='Name'])[1]");
	private final By locationAddressCode=By.xpath("(//input[@name='ExternalId'])[1]");
	private final By locationAddress1=By.xpath("(//input[@name='Address1'])[1]");
	private final By locationPostalCode=By.xpath("(//input[@name='PostalCode'])[1]");
	private final By shipToCity=By.xpath("(//input[@name='City'])[1]");
	private final By countryCell1 = By.id("x-form-el-ext-comp-1017");
	private final By shipingToLink = By.id("Select_Ship_To_Location_Link");
	private final By country1 = By.xpath("//input[@id='ext-comp-1017']");
	private final By shipToField = By.xpath("(//input[@type='text'])[2]");
	private final By shipToAddressSaveButton = By.xpath("(//button[@class=' x-btn-text'])[4]");
	private final By shipToDefaultCheckBox = By.xpath("(//input[@class=' x-form-checkbox x-form-field'])[2]");

	private final By stateCell = By.xpath("//div[@id='x-form-el-ext-comp-1011']");
	private final By state1 = By.xpath("//input[@id='ext-comp-1011']");
	private final By stateDropDownIcon = By.xpath("(//a[@class='x-form-trigger x-form-arrow-trigger'])[2]");

	private final By shipToAndBillTo = By.xpath("//input[@name='IsCopyAddressToOtherType']");
	private final By shipToSave = By.xpath("//button[contains(.,'Save')]");
	private final By billToSave = By.xpath("(//button[contains(.,'Save')])[2]");

	private final By billToButton = By.xpath("//span[contains(@class,'x-tab-strip-text')][contains(text(),'Bill To')]");

	private final By billingName=By.xpath("(//input[@name='Name'])[2]");
	private final By billingAddressCode=By.xpath("(//input[@name='ExternalId'])[2]");
	private final By billingAddress1=By.xpath("(//input[@name='Address1'])[2]");
	private final By billingPostalCode=By.xpath("(//input[@name='PostalCode'])[2]");
	private final By billToCity=By.xpath("(//input[@name='City'])[2]");
	private final By countryCell2 = By.id("x-form-el-ext-comp-1110");
	private final By country2 = By.xpath("//input[@id='ext-comp-1110']");
	private final By shipToLocationEmail = By.xpath("//input[@id='ext-comp-1015']");

	private final By addNewShipToLocation = By.className("x-panel-body x-panel-body-noheader x-panel-body-noborder x-form");

	public ConcurInvoicePage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * creates new invoice by clicking create new invoice tab
	 */
	public void createNewInvoice( )
	{
		wait.waitForElementEnabled(createNewInvoice);
		click.clickElement(createNewInvoice);

		waitForPageLoad();
	}

	public ConcurEnterInvoiceDetailsPage selectVendor( String vendorName )
	{
		List<WebElement> elements = wait.waitForAllElementsPresent(By.className("x-grid3-cell-inner"));

		for ( WebElement correctVendorElement : elements )
		{

			String vendorString = correctVendorElement.getText();

			if ( vendorString.equals(vendorName) )
			{
				click.clickElementCarefully(correctVendorElement);
				break;
			}
		}
		ConcurEnterInvoiceDetailsPage returnPage = initializePageObject(ConcurEnterInvoiceDetailsPage.class);
		return returnPage;
	}

	/**
	 * inputs vendor information into new invoice
	 *
	 * @param vendorNameInput   name of the vendor
	 * @param address1Input     first line of the address
	 * @param address2Input     second line of the address
	 * @param cityInput         city of the address
	 * @param stateInput        state of the address
	 * @param zipCodeInput      zip code of the address
	 * @param countryInput      country of the address
	 * @param currencyTextInput currency to use
	 *
	 * @return returns an initialized instance of the ConcurEnterInvoiceDetailsPage class
	 */
	public ConcurEnterInvoiceDetailsPage requestNewVendor( final String vendorNameInput, final String address1Input,
		final String address2Input, final String cityInput, final String stateInput, final String zipCodeInput, final String countryInput, final String currencyTextInput, final String telephoneNumberInput, final String contactFirstNameInput )
	{

		//click request new vendor
		WebElement window1 = wait.waitForElementEnabled(requestNewVendor);
		click.clickElement(window1);

		//enter vendor name
		wait.waitForElementEnabled(vendorName);
		text.enterText(vendorName, vendorNameInput);

		//enter address1
		wait.waitForElementEnabled(address1);
		text.enterText(address1, address1Input);

		//enter address2
		wait.waitForElementEnabled(address2);
		text.enterText(address2, address2Input);

		//enter city
		wait.waitForElementEnabled(city);
		text.enterText(city, cityInput);

		//enter state
		wait.waitForElementEnabled(state);
		text.enterText(state, stateInput);

		//enter zipCode
		wait.waitForElementEnabled(zipCode);
		text.enterText(zipCode, zipCodeInput);

		//Set country  - fixed kind of
		WebElement countryBox = element.getWebElement(countryCell);
		WebElement countryLocator = element.getWebElement(country, countryBox);
		StringBuffer countryString = new StringBuffer(countryInput);
		wait.waitForElementEnabled(countryLocator);
		text.enterText(countryLocator, "", true);
		enterTextSeveralTimes(countryLocator, countryString, 10);

		//enter telephone number
		wait.waitForElementEnabled(telephoneNumber);
		text.enterText(telephoneNumber, telephoneNumberInput);

		//Set currency  - fixed kind of
		WebElement currencyBox = element.getWebElement(currencyCell);
		WebElement currencyLocator = element.getWebElement(currency, currencyBox);
		StringBuffer currencyString = new StringBuffer(currencyTextInput);
		wait.waitForElementEnabled(currencyLocator);
		text.enterText(currencyLocator, "", true);
		enterTextSeveralTimes(currencyLocator, currencyString, 10);

		//enter contact first name
		wait.waitForElementEnabled(contactFirstName);
		text.enterText(contactFirstName, contactFirstNameInput);

		//Click ok
		waitForPageLoad();
		final String okBtnText = "OK";
		WebElement window = wait.waitForElementDisplayed(windowFooter);
		List<WebElement> btns = element.getWebElements(okButton, window);
		WebElement okBtn = element.selectElementByText(btns, okBtnText);
		click.clickElementCarefully(okBtn);
		ConcurEnterInvoiceDetailsPage returnPage = initializePageObject(ConcurEnterInvoiceDetailsPage.class);
		return returnPage;
	}

     /*
     * click on company location method from Administration-->invoice
     */
	public void createCompanyLocations( )
	{
		wait.waitForElementEnabled(companyLocations);
		click.clickElement(companyLocations);
		waitForPageLoad();
	}

	/*
	 * click on newbutton for adding new ship to location from Administration-->invoice-->company locations
	 */
	public void newButton( )
	{
		wait.waitForElementEnabled(newShipToLocation);
		click.clickElement(newShipToLocation);
		waitForPageLoad();
	}
	/*
	 * click on new button for bill to location from Administration-->invoice-->company locations
	 */
	public void billToButton( )
	{
		wait.waitForElementEnabled(billToButton);
		click.clickElement(billToButton);
		waitForPageLoad();
	}
	/*
	 * click on new button for adding new bill to location from Administration-->invoice-->company locations-->billto-->new
	 */
	public void billToNewButton( )
	{
		wait.waitForElementEnabled(newBillToLocation);
		click.clickElement(newBillToLocation);
		waitForPageLoad();
	}
	/*
	 * click on invoicelink method from homepage
	 */
	public void invoiceLink( )
	{
		wait.waitForElementEnabled(clickInvoice);
		click.clickElement(clickInvoice);
		waitForPageLoad();
	}
	/*
	 * click on ship to address same as bill to checkbox method from homepage
	 */
	public void sameShipToAndBillToCheckBox( )
	{
		wait.waitForElementEnabled(shipToAndBillTo);
		click.clickElement(shipToAndBillTo);
		waitForPageLoad();
	}
	/*
	 * click on ship to save button method from homepage
	 */
	public void shipToSaveButton( )
	{
		wait.waitForElementEnabled(shipToSave);
		click.clickElement(shipToSave);
		waitForPageLoad();
	}
	/*
	 * click on bill to save button method from homepage
	 */
	public void billToSaveButton( )
	{
		wait.waitForElementEnabled(billToSave);
		click.clickElement(billToSave);
		waitForPageLoad();
	}
	/*
	 * click on bill to save button method from homepage
	 */
	public void defaultShipToCheckBox( )
	{
		wait.waitForElementEnabled(shipToDefaultCheckBox);
		click.clickElement(shipToDefaultCheckBox);
		waitForPageLoad();
	}


	/**
	 * adding new shipping to locaton information into new invoice(Administration-->invoice-->company locations-->new
	 *
	 * @param shipToLocationName   name of the customer
	 * @param shipToLocationAddressCode  code of the address
	 * @param shipToLocationAddress1     first line of the address
	 * @param shipToLocationCity         city of the address
	 * @param shipToLocationPostalCode   postalcode of the address
	 * @param countryInput      country of the address
	 */
	public void enterShipToLocationDetails( String shipToLocationName, String shipToLocationAddressCode,
											 String shipToLocationAddress1, String shipToLocationCity, String shipToLocationPostalCode,
											 String countryInput,String stateInput,String shipToEmail)
	{
		waitForPageLoad();

		//enter shipping location name
		wait.waitForElementEnabled(locationName);
		text.enterText(locationName, shipToLocationName);

		//enter shipping location address code
		wait.waitForElementEnabled(locationAddressCode);
		text.enterText(locationAddressCode, shipToLocationAddressCode);

		//enter shipping location address1
		wait.waitForElementEnabled(locationAddress1);
		text.enterText(locationAddress1, shipToLocationAddress1);

		//enter shipping location city
		wait.waitForElementEnabled(shipToCity);
		text.enterText(shipToCity, shipToLocationCity);

		//enter shipping location postal code
		wait.waitForElementEnabled(locationPostalCode);
		text.enterText(locationPostalCode, shipToLocationPostalCode);

		//Set country  - fixed kind of
		WebElement countryBox = element.getWebElement(countryCell1);
		WebElement countryLocator = element.getWebElement(country1, countryBox);
		StringBuffer countryString = new StringBuffer(countryInput);
		wait.waitForElementEnabled(countryLocator);
		text.enterText(countryLocator, "", true);
		enterTextSeveralTimes(countryLocator, countryString, 10);

		/*//Set state  - fixed kind of
		wait.waitForElementEnabled(stateDropDownIcon);
		//WebElement stateBox = element.getWebElement(stateCell);
		WebElement stateLocator = wait.waitForElementEnabled(state1);
		//StringBuffer stateString = new StringBuffer(stateInput);
		wait.waitForElementEnabled(stateLocator);
		text.enterText(stateLocator, stateInput);
		//text.enterText(stateLocator, stateString, false);
		//enterTextSeveralTimes(stateLocator, "", 10);*/

		//enter email address
		wait.waitForElementEnabled(shipToLocationEmail);
		text.enterText(shipToLocationEmail, shipToEmail);


	}
	/**
	 * adding new billing to location information into new invoice(Administration-->invoice-->company locations-->new
	 *
	 * @param billToLocationName   name of the customer
	 * @param billToLocationAddressCode  code of the address
	 * @param billToLocationAddress1     first line of the address
	 * @param billToLocationCity         city of the address
	 * @param billToLocationPostalCode   postalcode of the address
	 * @param billToCountryInput      country of the address
	 */
	public void enterBillToLocationDetails( String billToLocationName, String billToLocationAddressCode,
											String billToLocationAddress1, String billToLocationCity, String billToLocationPostalCode,
											String billToCountryInput)
	{
		waitForPageLoad();

		//enter shipping location name
		wait.waitForElementEnabled(billingName);
		text.enterText(billingName, billToLocationName);

		//enter shipping location address code
		wait.waitForElementEnabled(billingAddressCode);
		text.enterText(billingAddressCode, billToLocationAddressCode);

		//enter shipping location address1
		wait.waitForElementEnabled(billingAddress1);
		text.enterText(billingAddress1, billToLocationAddress1);

		//enter shipping location city
		wait.waitForElementEnabled(billToCity);
		text.enterText(billToCity, billToLocationCity);

		//enter shipping location postal code
		wait.waitForElementEnabled(billingPostalCode);
		text.enterText(billingPostalCode, billToLocationPostalCode);

		//Set country  - fixed kind of
		WebElement countryBox = element.getWebElement(countryCell2);
		WebElement countryLocator = element.getWebElement(country2, countryBox);
		StringBuffer countryString = new StringBuffer(billToCountryInput);
		wait.waitForElementEnabled(countryLocator);
		text.enterText(countryLocator, "", true);
		enterTextSeveralTimes(countryLocator, countryString, 10);

	}

	/**
	 * validates that an invoice was successfully created by searching with the invoice number
	 *
	 * @param expectedInvoiceNumber expected value in invoice number field
	 *
	 * @return arrayList of values received from the created invoice
	 */
	public ConcurInvoiceRow validateInvoiceCreation( final String expectedInvoiceNumber )
	{

		//click dropdown and select all my invoices
		final String allMyinvoices = "All My Invoices";
		wait.waitForElementEnabled(dropDown);
		click.clickElement(dropDown);
		WebElement dropDownResults = element.selectElementByText(allMyInvoice, allMyinvoices);
		click.clickElement(dropDownResults);

		waitForPageLoad();

		//click search by invoice number
		//search for 'i' and sending the enter key also the driver to select the invoice number tab from the pop down
		final String invoiceNumSearchTerm = "i";
		wait.waitForElementEnabled(searchInvoiceNumber);
		click.clickElement(searchInvoiceNumber);
		StringBuffer searchTerm = new StringBuffer(invoiceNumSearchTerm);
		searchTerm.append(Keys.ENTER);
		wait.waitForElementEnabled(searchInvoiceNumber);
		text.enterText(searchInvoiceNumber, searchTerm, false);

		waitForPageLoad();

		//enter invoice number in seerch bar- NOTE: invoice is randomly generated 9 digit number, its possible invoice isn't unique but very implausible
		WebElement searchBox = wait.waitForElementEnabled(searchBar);
		text.enterText(searchBox, expectedInvoiceNumber);

		//click search button
		WebElement searchButtonClick = element.selectElementByText(searchButton, "Search");
		wait.waitForElementEnabled(searchButtonClick);
		click.clickElement(searchButtonClick);

		waitForPageLoad();

		//container for the table includes title row and invoice rows
		WebElement tableContainer = wait.waitForElementEnabled(tableLocator);

		//get container from which we'll get title list
		WebElement titleRowContainer = element.getWebElement(titleLocator, tableContainer);

		//get list of titles
		List<WebElement> listOfColumnTitles = element.getWebElements(By.tagName("th"), titleRowContainer);

		int invoiceNameIndex = -1;
		int vendorNameIndex = -1;
		int invoiceNumberIndex = -1;
		int invoiceDateIndex = -1;
		int approvalStatusIndex = -1;
		int paymentStatusIndex = -1;
		int totalPayIndex = -1;

		for ( int k = 0 ; k < listOfColumnTitles.size() ; k++ )
		{
			if ( text
				.getElementText(listOfColumnTitles.get(k))
				.equals("Invoice Name") )
			{
				invoiceNameIndex = k;
			}
			else if ( text
				.getElementText(listOfColumnTitles.get(k))
				.equals("Vendor Name") )
			{
				vendorNameIndex = k;
			}
			else if ( text
				.getElementText(listOfColumnTitles.get(k))
				.equals("Invoice Number") )
			{
				invoiceNumberIndex = k;
			}
			else if ( text
				.getElementText(listOfColumnTitles.get(k))
				.equals("Invoice Date") )
			{
				invoiceDateIndex = k;
			}
			else if ( text
				.getElementText(listOfColumnTitles.get(k))
				.equals("Approval Status") )
			{
				approvalStatusIndex = k;
			}
			else if ( text
				.getElementText(listOfColumnTitles.get(k))
				.equals("Payment Status") )
			{
				paymentStatusIndex = k;
			}
			else if ( text
				.getElementText(listOfColumnTitles.get(k))
				.equals("Total") )
			{
				totalPayIndex = k;
			}
		}

		//gets attributes of invoice that is found from search

		WebElement invoiceRowContainer = element.getWebElement(invoiceRowLocator, tableContainer);

		List<WebElement> listOfInvoiceRows = element.getWebElements(By.tagName("td"), invoiceRowContainer);

		WebElement invoiceNameElement = listOfInvoiceRows.get(invoiceNameIndex);
		WebElement vendorNameElement = listOfInvoiceRows.get(vendorNameIndex);
		WebElement invoiceNumberElement = listOfInvoiceRows.get(invoiceNumberIndex);
		WebElement invoiceDateElement = listOfInvoiceRows.get(invoiceDateIndex);
		WebElement approvalStatusElement = listOfInvoiceRows.get(approvalStatusIndex);
		WebElement paymentStatusElement = listOfInvoiceRows.get(paymentStatusIndex);
		WebElement totalPayElement = listOfInvoiceRows.get(totalPayIndex);

		//values found in row
		String actualInvoiceName = text.getElementText(invoiceNameElement);
		String actualVendorName = text.getElementText(vendorNameElement);
		String actualInvoiceNumber = text.getElementText(invoiceNumberElement);
		String actualInvoiceDate = text.getElementText(invoiceDateElement);
		String actualApprovalStatus = text.getElementText(approvalStatusElement);
		String actualPaymentStatus = text.getElementText(paymentStatusElement);
		String actualTotalPay = text.getElementText(totalPayElement);

		ConcurInvoiceRow invoiceRow = ConcurInvoiceRow
			.builder()
			.invoiceName(actualInvoiceName)
			.vendorName(actualVendorName)
			.invoiceNumber(actualInvoiceNumber)
			.invoiceDate(actualInvoiceDate)
			.approvalStatus(actualApprovalStatus)
			.paymentStatus(actualPaymentStatus)
			.totalPay(actualTotalPay)
			.build();

		return invoiceRow;
	}

	/**
	 * deletes invoice once invoice has already been searched for in the invoice page
	 */
	public void deleteInvoice( )
	{
		//find element containing checkbox
		final String selectRowText = "Select row";
		WebElement checkBoxElement = element.selectElementByText(checkBox, selectRowText);

		//find checkbox
		WebElement checkId = element.getWebElement(checkBoxLoc, checkBoxElement);

		//click checkbox
		wait.waitForElementEnabled(checkId);
		checkbox.setCheckbox(checkId, true);

		//click more actions button
		wait.waitForElementEnabled(moreActionsButton);
		click.clickElement(moreActionsButton);

		WebElement recallElement = element.selectElementByText(recall, "Recall");
		WebElement yesButton = null;

		//click recall button if possible then when page reloads reclick checkbox
		if ( element.isElementEnabled(recallElement) )
		{

			click.clickElement(recallElement);

			waitForPageLoad();
			final String yesBtnText = "Yes";
			yesButton = element.selectElementByText(yesLoc, yesBtnText);
			wait.waitForElementEnabled(yesButton);
			click.clickElement(yesButton);
			waitForPageLoad();
			WebElement checkBoxElement1 = element.selectElementByText(checkBox, selectRowText);//TODO

			checkId = element.getWebElement(checkBoxLoc, checkBoxElement1);

			checkbox.setCheckbox(checkId, true);
		}
		else
		{
			wait.waitForElementEnabled(moreActionsButton);
			click.clickElement(moreActionsButton);
		}

		waitForPageLoad();
		String deleteString = "Delete";
		WebElement deleteButton = element.selectElementByText(delete, deleteString);

		wait.waitForElementEnabled(deleteButton);
		click.clickElement(deleteButton);

		final String yes = "Yes";
		yesButton = element.selectElementByText(yesLoc, yes);
		wait.waitForElementEnabled(yesButton);
		click.clickElement(yesButton);
	}

	/**
	 * click 'All My Invoices' button on invoice manager page
	 */
	public void navigateToAllMyInvoices( )
	{
		final String allMyInvoicesString = "All My Invoices";
		WebElement allMyInvoicesBtn = element.selectElementByText(allMyInvoice, allMyInvoicesString);
		wait.waitForElementEnabled(allMyInvoicesBtn);
		click.clickElement(allMyInvoicesBtn);
		waitForPageLoad();
	}
	public void invoiceDetailsShipTo(String address){
		WebElement shipToLink=wait.waitForElementDisplayed(shipingToLink);
		click.javascriptClick(shipToLink);
		jsWaiter.sleep(12000);
		WebElement search = wait.waitForElementEnabled(shipToField);
		search.sendKeys(Keys.ARROW_DOWN);
		search.sendKeys((Keys.DELETE));
		text.enterText(search,address);
		jsWaiter.sleep(5000);
		search.sendKeys(Keys.TAB);
		defaultShipToCheckBox();
		click.javascriptClick(shipToAddressSaveButton);
	}


	public ConcurViewInvoiceDetailsPage selectInvoice( final String expectedInvoiceNumber )
	{
		List<WebElement> invoiceButtons = wait.waitForAllElementsDisplayed(buttonClass);

		for ( WebElement invoiceButton : invoiceButtons )
		{
			try
			{
				String invoiceText = invoiceButton.getText();
				if ( expectedInvoiceNumber.equals(invoiceText) )
				{
					click.clickElementCarefully(invoiceButton);
				}
			}
			catch ( Exception e )
			{

			}
		}

		waitForPageLoad();

		return initializePageObject(ConcurViewInvoiceDetailsPage.class);
	}
}