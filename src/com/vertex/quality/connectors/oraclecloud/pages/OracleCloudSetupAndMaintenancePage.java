package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudPageNavigationData;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Navigate to SetUp and Maintenance link
 *
 * @author Shilpi.Verma
 */

public class OracleCloudSetupAndMaintenancePage extends OracleCloudBasePage
{

	public OracleCloudSetupAndMaintenancePage( WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//h1[contains(text(), 'Manage Transaction Types')]")
	WebElement headerManageTransactionType;

	@FindBy(xpath = "//h1[contains(text(), 'Manage Transaction Source')]")
	WebElement headerManageTransactionSource;

	@FindBy(xpath = "//h1[contains(text(), 'Manage Business Unit')]")
	WebElement headerManageBusinessUnit;

	@FindBy(xpath = "//td[contains(text(), 'Legal Structures')]")
	WebElement legalStructureLink;

	@FindBy(xpath = "//tbody/tr[2]/td/span/a[contains(text(), 'Manage Legal Entity')]")
	WebElement manageLELink;

	@FindBy(xpath = "//label[contains(text(), 'Search Legal Entities')]")
	WebElement searchLE;

	@FindBy(xpath = "//button[contains(text(), 'Apply and Go to Task')][1]")
	WebElement applyButton;

	@FindBy(xpath = "//div[@title = 'Tasks']/a")
	public WebElement tasksOption;

	@FindBy(xpath = "//a[@title='Search']")
	public WebElement searchOptionTaskList;

	@FindBy(xpath = "//input[@type = 'text']")
	private WebElement searchTextBoxOnTaskList;

	@FindBy(xpath = "//div[@title='Search']/a")
	private WebElement searchButtonBesidesTextBox;

	@FindBy(xpath = "//table[@summary='Search Results']/tbody/tr/td/span/a")
	private WebElement searchResultRow;


	public WebElement pageHeader( String text )
	{
		By ele = By.xpath("//h1[contains(text(), " + text + ")]");
		WebElement element = wait.waitForElementDisplayed(ele);

		return element;
	}

	/**
	 * Opens the Legal Structure menu by clicking the Legal Structure link
	 * on the right side of the screen
	 */
	public void openTasksTab( )
	{
		wait.waitForElementEnabled(legalStructureLink);
		click.clickElement(legalStructureLink);
		wait.waitForElementDisplayed(manageLELink);
	}

	/**
	 * Opens the Manage Legal Entity page
	 *
	 * @param page
	 * @param <T>
	 *
	 * @return
	 */
	public <T extends OracleCloudBasePage> T clickMenuLink_LegalEntity( OracleCloudPageNavigationData page )
	{

		click.clickElement(manageLELink);
		wait.waitForElementDisplayed(searchLE);

		click.clickElement(searchLE);
		jsWaiter.sleep(2000);

		Actions action = new Actions(driver);
		action.sendKeys(Keys.TAB).build().perform();
		action.sendKeys(Keys.TAB).build().perform();
		action.sendKeys(Keys.ENTER).build().perform();

		jsWaiter.sleep(1000);

		Class pageClass = page.getPageClass();

		return initializePageObject(pageClass);
	}

	/**
	 * Opens the Tasks menu by clicking the Tasks link
	 * on the right side of the screen
	 */
	public void openTasksMenu( )
	{
		wait.waitForElementEnabled(tasksOption);
		click.clickElement(tasksOption);
		wait.waitForElementDisplayed(searchOptionTaskList);
	}

	/**
	 * Opens the Manage Business Unit page by clicking the search button
	 * on the search textbox on search page
	 *
	 * @return pageObject
	 */
	public <T extends OracleCloudBasePage> T clickMenuLink_BusinessUnit( OracleCloudPageNavigationData page )
	{

		click.clickElement(searchOptionTaskList);
		jsWaiter.sleep(2000);
		wait.waitForElementDisplayed(pageHeader("Search"));
		text.enterText(searchTextBoxOnTaskList, "Manage Business Unit");
		click.clickElement(searchButtonBesidesTextBox);

		wait.waitForElementDisplayed(searchResultRow);
		click.clickElement(searchResultRow);

		jsWaiter.sleep(1000);

		Class pageClass = page.getPageClass();
		wait.waitForElementDisplayed(headerManageBusinessUnit);
		return initializePageObject(pageClass);
	}

	/**
	 * Opens the Manage Payables Lookups page
	 *
	 * @param page
	 * @param <T>
	 *
	 * @return
	 */
	public <T extends OracleCloudBasePage> T clickMenuLink_ManagePayablesLookups( OracleCloudPageNavigationData page )
	{
		click.clickElement(searchOptionTaskList);
		jsWaiter.sleep(2000);
		wait.waitForElementDisplayed(pageHeader("Search"));
		click.clickElement(searchTextBoxOnTaskList);
		text.enterText(searchTextBoxOnTaskList, "Manage Payables Lookups");
		click.clickElement(searchButtonBesidesTextBox);

		wait.waitForElementDisplayed(searchResultRow);
		click.clickElement(searchResultRow);

		jsWaiter.sleep(1000);

		Class pageClass = page.getPageClass();

		return initializePageObject(pageClass);
	}

	/**
	 * Opens the Manage Transaction Types page by clicking the search button
	 * on the search textbox on search page
	 *
	 * @return pageObject
	 */
	public <T extends OracleCloudBasePage> T clickMenuLink_TransactionType( OracleCloudPageNavigationData page )
	{
		click.clickElement(searchOptionTaskList);
		jsWaiter.sleep(2000);
		wait.waitForElementDisplayed(pageHeader("Search"));
		text.enterText(searchTextBoxOnTaskList, "Manage Transaction Type");
		click.clickElement(searchButtonBesidesTextBox);

		wait.waitForElementDisplayed(searchResultRow);
		click.clickElement(searchResultRow);

		jsWaiter.sleep(1000);

		Class pageClass = page.getPageClass();
		wait.waitForElementDisplayed(headerManageTransactionType);
		return initializePageObject(pageClass);
	}

	/**
	 * Opens the Manage Transaction Source page by clicking the search button
	 * on the search textbox on search page
	 *
	 * @return pageObject
	 */
	public <T extends OracleCloudBasePage> T clickMenuLink_TransactionSource( OracleCloudPageNavigationData page )
	{
		click.clickElement(searchOptionTaskList);
		jsWaiter.sleep(2000);
		wait.waitForElementDisplayed(pageHeader("Search"));
		text.enterText(searchTextBoxOnTaskList, "Manage Transaction Sources");
		click.clickElement(searchButtonBesidesTextBox);

		wait.waitForElementDisplayed(searchResultRow);
		click.clickElement(searchResultRow);

		jsWaiter.sleep(1000);

		Class pageClass = page.getPageClass();
		wait.waitForElementDisplayed(headerManageTransactionSource);
		return initializePageObject(pageClass);
	}
}
