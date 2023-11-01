package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents the customers list page
 *
 * @author cgajes
 */
public class BusinessCustomersListPage extends BusinessBasePage
{
	protected By dialogBoxLoc = By.className("ms-nav-content-box");

	protected By newButtonLoc = By.cssSelector("span[aria-label='New']");
	protected By okButtonLoc = By.xpath("//button/span[text()='OK']");
	protected By businessToBusinessLoc = By.xpath(".//tr[1]//*[contains(@title,'Business-to-Business Customer (Bank)')]");
	protected By retailCustomerLoc = By.cssSelector("a[aria-label*='Cash-Payment / Retail Customer']");
	protected By customerGridLoc = By.cssSelector("table[id*='_BusinessGrid']");
	protected By customerRowsLoc = By.cssSelector("table tbody tr");
	protected By customerRowSegmentLoc = By.cssSelector("td");
	protected By openLink = By.xpath("//td[@controlname='No.'][not(@data-is-focusable)]");
	protected By customersSearch = By.cssSelector("input[aria-label='Search Customers']");
	protected By custSearch = By.cssSelector("input[aria-label*='Search Customers']");
	protected By customersSearch1 = By.xpath("//div/i[@data-icon-name='Search']");
	protected By customersSearch2 = By.xpath("(//div/i[@data-icon-name='Search'])[2]");
	protected By customersButtonLoc = By.xpath("(//button[contains(@aria-label, 'Customers')]//span[@aria-label='Customers'])[2]");
	protected By salesButtonLoc = By.xpath("(//i[@class='icon-DownCaret-after'])[18]");
	protected By customerNameField = By.xpath("input[aria-label='Name, ']");
	protected By customerList = By.xpath("(//td/a[@aria-haspopup='menu'])[3]");
	protected By editCustomer = By.xpath("//div/span[contains(.,'Edit')]");
	protected By clearCustomerfield = By.xpath("//span/i[@data-icon-name='Clear']");
	protected By firstSearch = By.xpath("//td[@controlname='No.']/a[contains(@title,'Open record')]");
	protected By firstElement = By.xpath("//td[@controlname='No.']/a[contains(@title,'Open record') and not(@tabindex='-1')]");



	public BusinessCustomersListPage( WebDriver driver ) { super(driver); }

	/**
	 * clicks on the +New button and selects business-to-business customer
	 *
	 * @return the customer card page
	 */
	public BusinessCustomerCardPage clickNewBusinessToBusinessCustomer( )
	{
		waitForPageLoad();
		List<WebElement> buttonList = wait.waitForAllElementsPresent(newButtonLoc);
		WebElement newButton = buttonList.get(buttonList.size() - 1);
		click.clickElement(newButton);

		WebElement dialogBox = wait.waitForElementDisplayed(dialogBoxLoc);
		WebElement busToBusType = wait.waitForElementDisplayed(businessToBusinessLoc, dialogBox);

		click.clickElementCarefully(busToBusType);
		click.clickElement(okButtonLoc);
		waitForPageLoad();
		return initializePageObject(BusinessCustomerCardPage.class);
	}

	/**
	 * Gets a customer from the list based on row
	 *
	 * @param rowNum
	 *
	 * @return element of the row that contains clickable link and number
	 */
	public WebElement getCustomerFromList( int rowNum )
	{
		WebElement selectedCustomer = wait.waitForAllElementsPresent(openLink).get(rowNum-1);
		return selectedCustomer;
	}

	/**
	 * Gets the number of the customer in the specified row
	 *
	 *
	 * @return the customer's document number
	 */
	public String getCustomerNumberByRowIndex( )
	{
		WebElement customer = wait.waitForElementDisplayed(openLink);

		String number = customer.getText();

		return number;
	}

	/**
	 * Opens a customer displayed on the list page, based on specified row
	 *
	 * @return customer card page
	 */
	public BusinessCustomerCardPage openCustomerByRowIndex( int rowIndex )
	{
		WebElement select = getCustomerFromList(rowIndex);
		click.clickElementCarefully(select);
		return initializePageObject(BusinessCustomerCardPage.class);
	}
	/**
	 * Opens a customer displayed on the list page, based on customer Code
	 * @author bhikshapathi
	 *
	 */
	public void  searchAndOpenCustomer( String customerCode)
	{
		waitForPageLoad();
		WebElement search = wait.waitForElementEnabled(customersSearch1);
		click.clickElementCarefully(search);
		WebElement search1 = wait.waitForElementEnabled(customersSearch);
		text.clearText(search1);
		text.enterText(search1,customerCode);
		text.pressEnter(search1);
		WebElement menu=wait.waitForElementDisplayed(firstSearch);
		click.clickElementCarefully(menu);
		waitForPageLoad();

	}
	/**
	 * clears a customer displayed on the list page
	 * @author bhikshapathi
	 *
	 */
	public void  clearCustomerField()
	{
		waitForPageLoad();
		WebElement clear = wait.waitForElementEnabled(clearCustomerfield);
		click.javascriptClick(clear);
		waitForPageLoad();

	}
	/**
	 * Clicks sales  link and navigate to the customers list page
	 *@bhikshapathi
	 */
	public void navigateToCustomers( )
	{
		waitForPageLoad();
		WebElement customerbutton = wait.waitForElementDisplayed(customersButtonLoc);
		click.clickElementIgnoreExceptionAndRetry(customerbutton);
		waitForPageLoad();
	}
	/**
	 * Opens a customer displayed on the list page, based on customer Code
	 * @author bhikshapathi
	 *
	 */
	public void  searchForCustomer( String customerCode)
	{
		waitForPageLoad();
		WebElement search = wait.waitForElementEnabled(customersSearch2);
		click.clickElementCarefully(search);
		WebElement search1 = wait.waitForElementDisplayed(custSearch);
		text.enterText(search1,customerCode);
		text.pressEnter(search1);
		WebElement menu=wait.waitForElementDisplayed(firstElement);
		click.clickElementCarefully(menu);
		waitForPageLoad();

	}

}
