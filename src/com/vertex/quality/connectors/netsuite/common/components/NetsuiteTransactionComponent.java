package com.vertex.quality.connectors.netsuite.common.components;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteExpenseCategory;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteExpense;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.*;

/**
 * Contains method implementations for NetsuiteTransactions and its children
 *
 * @author hho
 */
public class NetsuiteTransactionComponent extends NetsuiteComponent
{
	protected final By items = By.xpath("//div[@id='inner_popup_div']//tbody/tr");
	protected final By taxDetailRef = By.xpath("//input[contains(@id,'inpt_taxdetailsreference') and @name='inpt_taxdetailsreference']");
	protected final By taxType = By.xpath("//input[contains(@id,'inpt_taxtype') and @name='inpt_taxtype']");
	protected final By taxCodeDropDownBtn = By.id("parent_actionbuttons_taxdetails_taxcode_fs");
	protected final By taxCodeDDListBtn = By.id("taxcode_popup_list");
	protected final By searchBox = By.id("st");
	protected final By searchBtn = By.id("Search");
	protected final By taxBasisInpt = By.id("taxbasis_formattedValue");
	protected final By taxRateInpt = By.id("taxrate_formattedValue");
	protected final By taxAmt = By.xpath("//table[@id='taxdetails_splits']//*[@id='taxamount_formattedValue']");
	protected final By expenseAmt = By.xpath("//*[@id=\"expense_splits\"]/tbody/tr[2]/td[8]");
	protected final String sectionLocatorText = "taxdetails_splits";
	protected final String expenseSectionLocatorText = "expense_splits";

	public int currentItemIndex = 0;
	public int currentExpenseCategoryIndex = 0;
	public int headerIndex = 0;
	Actions actor = new Actions((driver) );

	public NetsuiteTransactionComponent( final WebDriver driver, final NetsuitePage parent )
	{
		super(driver, parent);
	}

	/**
	 * Selects a customer for the transaction
	 *
	 * @param customerDropdownLocator     the customer dropdown arrow locator
	 * @param customerListLocator         the customer list button locator
	 * @param customerSearchFieldLocator  the customer search text field locator
	 * @param customerSearchButtonLocator the customer search button locator
	 * @param customerName                the customer's name
	 */
	public void selectCustomer( By customerDropdownLocator, By customerListLocator, By customerSearchFieldLocator,
		By customerSearchButtonLocator, String customerName )
	{
		click.clickElement(customerDropdownLocator);
		click.clickElement(customerListLocator);
		text.enterText(customerSearchFieldLocator, customerName);
		click.clickElement(customerSearchButtonLocator);
		clickDropdownListMenuItem(customerName);
	}

	/**
	 * Selects an employee for the transaction
	 *
	 * @param employeeDropdownLocator     the employee dropdown arrow locator
	 * @param employeeName                the employee's name
	 */
	public void selectEmployee( By employeeDropdownLocator, String employeeName )
	{
		click.clickElement(employeeDropdownLocator);
		clickDropdownListMenuItem(employeeName);
	}

	/**
	 * Selects a vendor for the transaction
	 *
	 * @param vendorLocator     		the vendor dropdown arrow locator
	 * @param vendorName                the vendor's name
	 */
	public void selectVendor( By vendorLocator, String vendorName )
	{
		click.clickElement(vendorLocator);
		text.enterText(vendorLocator, vendorName);
	}

	/**
	 * Adds an item to the transaction
	 *
	 * @param itemDropdownLocator     the item dropdown arrow locator
	 * @param itemListLocator         the item list button locator
	 * @param itemSearchFieldLocator  the item search text field locator
	 * @param itemSearchButtonLocator the item search button locator
	 * @param item                    the Netsuite item
	 * @param index					  the index of the current item
	 */
	public void addItemToTransaction( By itemDropdownLocator, By itemListLocator, By itemSearchFieldLocator, By itemSearchButtonLocator, NetsuiteItem item, int index )
	{
		Actions actor = new Actions((driver) );
		String itemName = item
				.getNetsuiteItemName()
				.getItemName();
		//Skip over the header row
		currentItemIndex = index+1;
		// Expand item search box before attempting to search for item

		//** Get Item Column
		int count = element.getWebElements(By.xpath("//table[@id='item_splits']/tbody/tr[1]/td") ).size();

		String header = "ITEM";
		int headerIndex = 0;
		WebElement els;
		//Find correct Column by Text
		for(int col = 1;col != count;col++) {
			headerIndex++;
			els = element.getWebElement(By.xpath("//table[@id='item_splits']/tbody/tr[1]/td["+col+"]/div") );

			if(els.getText().compareToIgnoreCase(header) == 0) {
				break;
			}
		}

		WebElement itemNameInput = driver.findElement(By.xpath("//table[@id='item_splits']/tbody/tr["+currentItemIndex+"]/td["+headerIndex+"]") );
		actor.moveToElement(itemNameInput)
				.click()
				.perform();

		// Wait for box expansion to finish before proceeding
		if(element.isElementPresent(itemDropdownLocator) )
		{
			click.clickElement(itemDropdownLocator);
			click.clickElement(itemListLocator);
			text.enterText(itemSearchFieldLocator, itemName);
			click.clickElement(itemSearchButtonLocator);
			//wait till drop down reloads list
			boolean wait = true;
			long time = 0;
			List<WebElement> list;
			while (wait && time <= 2000) {
				long start = System.currentTimeMillis();
				list = element.getWebElements(items);
				if (list.size() == 2) {
					wait = false;
				}
				time = System.currentTimeMillis() - start;
			}
		}
		clickDropdownListMenuItem(itemName);

		// Wait 3 seconds for Alert Popup
		if ( alert.waitForAlertPresent(3) )
		{
			alert.acceptAlert();
		}
		waitForPageLoad();
	}

	/**
	 * Adds an item to the transaction
	 *
	 * @param itemDropdownLocator     the item dropdown arrow locator
	 * @param itemListLocator         the item list button locator
	 * @param itemSearchFieldLocator  the item search text field locator
	 * @param itemSearchButtonLocator the item search button locator
	 * @param item                    the Netsuite item
	 */
	public void addItemToTransaction( By itemDropdownLocator, By itemListLocator, By itemSearchFieldLocator,
		By itemSearchButtonLocator, NetsuiteItem item )
	{
		addItemToTransaction(itemDropdownLocator, itemListLocator, itemSearchFieldLocator, itemSearchButtonLocator, item, 1);
	}

	/**
	 * Adds a Tax Detail to the transaction
	 *
	 * @param item                    the Netsuite item
	 * @param index					  the index of the current item
	 */
	public void addTaxDetailToTransaction( NetsuiteItem item, int index )
	{
		//Skip over the header row
		currentItemIndex = index+2;

		//Get Tax Details Cell
		headerIndex = getColumn(sectionLocatorText,"Tax Details Reference");
		setDropDown(taxDetailRef,"SHIPPING");

		//Get Tax Type
		headerIndex = getColumn(sectionLocatorText,"Tax Type");
		setDropDown(taxType, "US Sales");

		//Get Tax Code
		headerIndex = getColumn(sectionLocatorText,"Tax Code");
		setListDropDown(item.getTaxDetailCode());

		//Get Tax Basis
		headerIndex = getColumn(sectionLocatorText,"Tax Basis");
		enterTaxDetailText(taxBasisInpt, item.getTaxBasis());

		//Get Tax Rate
		headerIndex = getColumn(sectionLocatorText,"Tax Rate");
		enterTaxDetailText(taxRateInpt, item.getTaxRate());

		//Get Tax Amount
		headerIndex = getColumn(sectionLocatorText,"Tax Amount");
		enterTaxDetailText(taxAmt, item.getTaxAmt());

		currentItemIndex = 0;
	}

	/**
	 * Adds a Tax Detail to the transaction
	 *
	 * @param expenseCategory         the Netsuite expenseCategory
	 * @param index					  the index of the current expenseCategory
	 */
	public void addExpensesToTransaction(By expenseCategoryDropdownLocator, NetsuiteExpense expenseCategory, int index,
										 String customer)
	{
		Actions actor = new Actions((driver) );
		String category = expenseCategory
				.getCategory().getCategoryName();
		String currency = expenseCategory.getCurrency();
		String amount = expenseCategory.getAmount();

		//Skip over the header row
		currentItemIndex = index+1;
		// Expand item search box before attempting to search for item

		//** Get CATEGORY,CURRENCY,CUSTOMER Columns and select values
		int count = element.getWebElements(By.xpath("//table[@id='expense_splits']/tbody/tr[1]/td") ).size();

		String categoryHeader = "CATEGORY";
		String currencyHeader = "CURRENCY";
		String customerHeader = "CUSTOMER";
		String amountHeader = "AMOUNT";
		int categoryHeaderIndex = 0;
		int currencyHeaderIndex = 0;
		int customerHeaderIndex = 0;
		int amountHeaderIndex = 0;
		WebElement els;
		//Find correct Column by Text
		for(int col = 1;col != count;col++) {
			els = element.getWebElement(By.xpath("//table[@id='expense_splits']/tbody/tr[1]/td["+col+"]/div") );

			if(els.getText().compareToIgnoreCase(categoryHeader) == 0) {
				categoryHeaderIndex = col;
			}
			if(els.getText().compareToIgnoreCase(currencyHeader) == 0) {
				currencyHeaderIndex = col;
			}
			if(els.getText().compareToIgnoreCase(customerHeader) == 0) {
				customerHeaderIndex = col;
			}
			if(els.getText().compareToIgnoreCase(amountHeader) == 0) {
				amountHeaderIndex = col;
			}
		}
		// Select Expense CATEGORY Dropdown text
		WebElement itemNameInput = driver.findElement(By.xpath("//table[@id='expense_splits']/tbody/tr["+currentItemIndex+"]/td["+categoryHeaderIndex+"]") );
		actor.moveToElement(itemNameInput)
				.click()
				.perform();
		click.clickElement(expenseCategoryDropdownLocator);
		clickDropdownListMenuItem(category.toString());

		// Select Expense CURRENCY Dropdown
		itemNameInput = driver.findElement(By.xpath("//table[@id='expense_splits']/tbody/tr["+currentItemIndex+"]/td["+currencyHeaderIndex+"]") );
		actor.moveToElement(itemNameInput)
				.click()
				.perform();
		By expenseCurrencyDropdownLocator = By.xpath("//input[@id='inpt_currency6']");
		click.clickElement(expenseCurrencyDropdownLocator);
		clickDropdownListMenuItem(currency);

		// Enter Expense AMOUNT text
		itemNameInput = driver.findElement(By.xpath("//table[@id='expense_splits']/tbody/tr["+currentItemIndex+"]/td["+amountHeaderIndex+"]") );
		actor.moveToElement(itemNameInput)
				.click()
				.perform();
		actor.moveToElement(itemNameInput)
				.click()
				.perform();
		By amountFieldLocator = By.xpath("//input[@id='amount_formattedValue']");
		text.enterText(amountFieldLocator, amount);

		// Enter Expense CUSTOMER text
		itemNameInput = driver.findElement(By.xpath("//table[@id='expense_splits']/tbody/tr["+currentItemIndex+"]/td["+customerHeaderIndex+"]") );
		actor.moveToElement(itemNameInput)
				.click()
				.perform();
		actor.moveToElement(itemNameInput)
				.click()
				.perform();
		By customerFieldLocator = By.xpath("//input[@id='expense_customer_display']");
		text.enterText(customerFieldLocator, customer);

		// Set SUPERVISOR APPROVAL Checkbox
		By supervisorApprovalLocator = By.xpath("//*[@id=\"supervisorapproval_fs\"]");
		click.javascriptClick(supervisorApprovalLocator);

		// Set ACCOUNTING APPROVAL Checkbox
		By accountingApprovalLocator = By.xpath("//*[@id=\"accountingapproval_fs\"]");
		click.javascriptClick(accountingApprovalLocator);

		// Wait 3 seconds for Alert Popup
		if ( alert.waitForAlertPresent(3) )
		{
			alert.acceptAlert();
		}
		waitForPageLoad();
	}

	/**
	 * Gets the Column of the table with matching Header
	 * @param sectionLocatorText i.e. item section, tax detail section
	 * @param columnHeader text in column header
	 * @return column index
	 */
	public int getColumn(String sectionLocatorText, String columnHeader)
	{
		//** Get Item Column
		int headerIndex = 0;
		WebElement els;
		int count = element.getWebElements(By.xpath("//table[@id='" +sectionLocatorText+ "']/tbody/tr[1]/td") ).size();
		//Find correct Column by Text
		for(int col = 1;col != count;col++) {
			headerIndex++;
			els = element.getWebElement(By.xpath("//table[@id='" +sectionLocatorText+ "']/tbody/tr[1]/td["+col+"]/div") );
			if(els.getText().compareToIgnoreCase(columnHeader) == 0) {
				break;
			}
		}
		return headerIndex;
	}

	/**
	 * setTaxDetailDropdowns
	 * @param DropdownLocator dropdown element
	 * @param value set to this value
	 */
	public void setDropDown(By DropdownLocator, String value){

		WebElement inputLocation = driver.findElement(By.xpath("//table[@id='" +sectionLocatorText+ "']/tbody/tr["+currentItemIndex+"]/td["+headerIndex+"]") );
		actor.moveToElement(inputLocation)
				.click()
				.perform();
		setDropdownToValue(element.getWebElement(DropdownLocator), value);
	}

	/**
	 * Sets Dropdowns with the list option
	 * @param value set to this value
	 */
	public void setListDropDown(String value) {

		WebElement inputLocation = driver.findElement(By.xpath("//table[@id='" +sectionLocatorText+ "']/tbody/tr["+currentItemIndex+"]/td["+headerIndex+"]") );
		actor.moveToElement(inputLocation)
				.click()
				.perform();

		if(element.isElementPresent(taxCodeDropDownBtn ) ) {
			click.clickElement(taxCodeDropDownBtn );

			if (driver.findElement(taxCodeDDListBtn) != null) {
				click.clickElement(taxCodeDDListBtn );
				text.enterText(searchBox, value);
				click.clickElement(searchBtn);

				//wait till drop down reloads list
				boolean wait = true;
				long time = 0;
				List<WebElement> list;
				while (wait && time <= 2000) {
					long start = System.currentTimeMillis();
					list = element.getWebElements(items);
					if (list.size() <= 2) {
						wait = false;
					}
					time = System.currentTimeMillis() - start;
				}
				clickDropdownListMenuItem(value);
			}
		}
	}

	/**
	 * Enter Text into tax Detail table
	 * @param inptLocator Text input element locator
	 * @param value set to this text value
	 */
	public void enterTaxDetailText(By inptLocator, String value){
		WebElement inputLocation = driver.findElement(By.xpath("//table[@id='" +sectionLocatorText+ "']/tbody/tr["+currentItemIndex+"]/td["+headerIndex+"]") );

		actor.moveToElement(inputLocation)
				.click()
				.perform();
		actor.moveToElement(inputLocation)
				.click()
				.perform();
		text.enterText(inptLocator , value);
	}

	/**
	 * Gets the summary category amount
	 *
	 * @param summaryCategory the summary category
	 *
	 * @return the amount
	 */
	public String getSummaryCategoryAmount( String summaryCategory )
	{
		String amountClass = "inputreadonly";
		By summaryTableLocator = By.className("totallingtable");
		WebElement summaryTable = wait.waitForElementDisplayed(summaryTableLocator);
		List<WebElement> summaryTableRows = wait.waitForAllElementsDisplayed(By.tagName("tr"), summaryTable);
		WebElement categoryAmountElement = null;

		search:
		for ( WebElement summaryTableRow : summaryTableRows )
		{
			List<WebElement> summaryRowDivs = summaryTableRow.findElements(By.tagName("div"));

			for ( WebElement summaryRowDiv : summaryRowDivs )
			{
				List<WebElement> summarySpans = summaryRowDiv.findElements(By.tagName("span"));

				for ( int i = 0 ; i < summarySpans.size() ; i++ )
				{
					WebElement summarySpan = summarySpans.get(i);
					String summarySpanText = summarySpan.getText();

					if ( summarySpanText != null && summarySpanText.equals(summaryCategory) &&
						 (i + 1) < summarySpans.size() )
					{
						WebElement checkElement = summarySpans.get(i + 1);
						String checkClass = attribute.getElementAttribute(checkElement, "class");
						if ( checkClass != null && checkClass.contains(amountClass) )
						{
							categoryAmountElement = checkElement;
							break search;
						}
					}
				}
			}
		}

		String amount = null;
		if ( categoryAmountElement != null )
		{
			amount = categoryAmountElement.getText();
		}
		return amount;
	}

	/**
	 * Selects the posting period
	 *
	 * @param postingPeriod the posting period
	 */
	public void selectPostingPeriod( String postingPeriod )
	{
		By postingPeriodDropdown = By.name("inpt_postingperiod");
		setDropdownToValue(postingPeriodDropdown, postingPeriod);
	}

	/**
	 * Selects the location
	 *
	 * @param location the location
	 */
	public void selectLocation( String location )
	{
		By locationDropdown = By.name("inpt_location");
		if(element.isElementDisplayed(locationDropdown)) {
			setDropdownToValue(locationDropdown, location);
		}

	}

	/**
	 * Inputs the given coupon code
	 *
	 * @param code the coupon code
	 */
	public void enterCouponCode( String code )
	{
		By couponCodeTextField = By.id("couponcode_display");
		text.enterText(couponCodeTextField, code);
	}

	/**
	 * Selects the given promotion
	 *
	 * @param promotion the promotion
	 */
	public void selectPromotion( String promotion )
	{
		By promoDropdown = By.name("inpt_promocode");
		setDropdownToValue(promoDropdown, promotion);
	}

	/**
	 * Selects the given currency type for the transaction
	 *
	 * @param currency the currency
	 */
	public void selectCurrency( String currency )
	{
		By currencyDropdown = By.name("inpt_currency");
		setDropdownToValue(currencyDropdown, currency);
	}

	/**
	 * Enables line item shipping if not enabled
	 */
	public void enableLineItemShipping( )
	{
		By lineItemShippingCheckbox = By.id("ismultishipto_fs_inp");
		if ( !checkbox.isCheckboxChecked(lineItemShippingCheckbox) )
		{
			click.javascriptClick(lineItemShippingCheckbox);
		}
	}

	/**
	 * Inputs the given date
	 *
	 * @param date the date
	 */
	public void enterDate( String date )
	{
		By dateTextField = By.id("trandate");
		text.enterText(dateTextField, date);
	}
}
