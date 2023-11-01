package com.vertex.quality.connectors.salesforce.pages.cpq;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import com.vertex.quality.connectors.salesforce.pages.SalesForceLogInPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Common functions for anything related to Salesforce Basic UI after Login Page.
 *
 * @author
 */
public class SalesForceCPQPostLogInPage extends SalesForceBasePage
{
	protected By LEX_DIALOG = By.id("tryLexDialog");
	protected By LEX_DIALOG_CLOSE = By.id("tryLexDialogX");

	protected By SWITCH_TO_LIGHTING = By.className("switch-to-lightning");

	protected By PROFILE_IMAGE = By.cssSelector(".profileTrigger .uiImage");
	protected By PROFILE_CARD = By.className("oneUserProfileCard");
	protected By SWITCH_TO_CLASSIC = By.xpath("//a[contains(text(),'Switch to Salesforce Classic')]");

	protected By APP_MENU_DROPDOWN = By.id("tsidLabel");
	protected By APP_MENU_ITEMS_BLOCK = By.id("tsid-menuItems");
	protected By SELECTED_APP_MENU = By.id("tsidLabel");

	protected By SET_UP_LINK = By.id("setupLink");
	protected By DELETE_BUTTON = By.xpath("//td[@id = 'topButtonRow']//input[@title= 'Delete']");

	protected By ALL_PRODUCT_GO = By.xpath("//*/div[@class='lbBody']//*/input");
	protected By PRICE_BOOK_ENTRY_VIEW = By.xpath("//*/div[@class='listRelatedObject pricebookentryBlock']//*/a[text()='View']");
	protected By PRICE_BOOK_VIEW = By.xpath("//*/div[@class='listRelatedObject pricebookentryBlock']//*/th/a");

	protected By BACK_TO_ENTRIES= By.xpath("//*/a[text()='Back to List: Price Book Entries']");

	protected By NEW_SCHEDULER_BUTTON = By.cssSelector("input[name = 'new']");
	protected By SCHEDULER_NAME_INPUT = By.cssSelector("input[name = 'Name']");
	protected By SCHEDULER_TYPE_SELECT = By.xpath("//*/div/span/select");
	protected By SCHEDULER_BATCH_1 = By.xpath("//*/optgroup/option[1]");
	protected By SCHEDULER_DATE_INPUT = By.xpath("//*/span[@class=\"dateInput\"]/input");
	protected By SCHEDULER_SAVE_BUTTON = By.xpath("//*/td[@class='pbButton']/input[@name=\"save\"]");
	protected By SCHEDULER_TAB_BUTTON = By.className("wt-InvoiceScheduler");

	public SalesForceCPQPostLogInPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Click on Tab in Vertex
	 */
	public void clickVertexPageTab( String tabName )
	{
		waitForPageLoad();
		String tabLocator = String.format(".//*[@title='%s' or starts-with(@id, '%s')]", tabName,
			tabName.replace(" ", ""));
		wait.waitForElementDisplayed(By.xpath(tabLocator));
		click.javascriptClick(By.xpath(tabLocator));
		waitForSalesForceLoaded();
	}

	/**
	 * Click on Tab in Vertex
	 */
	public void clickVertexPageTabMenu( String tabName )
	{
		waitForPageLoad();
		String tabLocator = String.format(".//a[starts-with(@title,'%s')]", tabName);
		wait.waitForElementDisplayed(By.xpath(tabLocator));
		click.clickElement(By.xpath(tabLocator));
		waitForSalesForceLoaded();
	}

	/**
	 * Check if Sales force CRM loaded in Lighting view , If Yes switch to Classic
	 * view
	 */
	public void checkAndSwitchClassicView( )
	{
		waitForPageLoad();
		if ( element.isElementDisplayed(SWITCH_TO_LIGHTING) )
		{
			VertexLogger.log("Salesforce CRM is in Classic View.", SalesForceLogInPage.class);
		}
		else if ( element.isElementDisplayed(PROFILE_IMAGE) )
		{
			click.clickElement(PROFILE_IMAGE);
			wait.waitForElementDisplayed(PROFILE_CARD);
			click.clickElement(SWITCH_TO_CLASSIC);
			waitForSalesForceLoaded();
		}
	}

	/**
	 * Close LEX dialog, if appears after user login and while switching with app
	 * menu [Top Right corner of every page]
	 */
	public void closeLightningExperienceDialog( )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(SELECTED_APP_MENU);

		if ( element.isElementDisplayed(LEX_DIALOG) )
		{
			VertexLogger.log("Lightning Experience dialog appeared", SalesForceLogInPage.class);
			click.clickElement(LEX_DIALOG_CLOSE);
			waitForSalesForceLoaded();
		}
	}

	/**
	 * Method to switch between different applications
	 *
	 * @param appName
	 */
	public void switchToCRMAppMenu( String appName )
	{
		refreshPage();
		waitForPageLoad();
		wait.waitForElementDisplayed(SELECTED_APP_MENU);
		String selectedMenuItem = attribute.getElementAttribute(SELECTED_APP_MENU, "textContent");
		if ( selectedMenuItem.equals(appName) )
		{
			VertexLogger.log(selectedMenuItem + " App Menu item is already selected", SalesForceLogInPage.class);
		}
		else
		{
			wait.waitForElementDisplayed(APP_MENU_DROPDOWN);
			click.clickElement(APP_MENU_DROPDOWN);
			wait.waitForElementDisplayed(APP_MENU_ITEMS_BLOCK);
			By apMenuItem = By.xpath(String.format("//a[text()='%s']", appName));
			click.javascriptClick(apMenuItem);
			waitForPageLoad();
		}
	}

	/**
	 * To Click on SetUp link
	 */
	public void clickSetupLink( )
	{
		refreshPage();
		waitForSalesForceLoaded();
		click.clickElement(SET_UP_LINK);
		waitForPageLoad();
	}

	/**
	 * Click on Tab in Vertex
	 *
	 * @param tabName
	 */
	public void clickSalesPageTab( String tabName )
	{
		waitForPageLoad();
		String tabLocator = String.format(tabName + "_Tab");
		wait.waitForElementDisplayed(By.id(tabLocator));
		click.clickElement(By.id(tabLocator));
		waitForPageLoad();
	}

	/**
	 * To Select and delete the items from Recent Items table
	 *
	 * @param itemName
	 */
	public void removeItemsFromRecentItems( String itemName )
	{
		if ( itemName.length() > 15 )
		{
			itemName = itemName.substring(0, 15);
		}
		String itemRow = String.format("//span[contains(text(),'%s')]", itemName);
		click.clickElement(By.xpath(itemRow));
		waitForPageLoad();
		click.clickElement(DELETE_BUTTON);
		alert.acceptAlert(DEFAULT_TIMEOUT);
		waitForPageLoad();
		// wait.waitForElementDisplayed(By.xpath(itemRow));
	}

	/**
	 * To Select items from Recent Items table
	 */
	public void selectItemsFromRecentItems( String itemName )
	{
		jsWaiter.sleep(5000);
		String itemRow = String.format("//span[contains(text(),'%s')]", itemName);
		click.clickElement(By.xpath(itemRow));
		waitForPageLoad();
	}

	/**
	 * Get ID from url
	 */
	public String getIDFromUrl()
	{
		String url = driver.getCurrentUrl();
		int idStart = 0;
		for(int i=url.length()-1; i>=0;i--){
			if(url.charAt(i)=='/'){
				idStart = i+1;
				break;
			}
		}
		return url.substring(idStart);

	}

	/**
	 * click go on the products page
	 */
	public void clickProductGo()
	{
		List<WebElement> productGoBy = driver.findElements(ALL_PRODUCT_GO);
		click.clickElementCarefully(productGoBy.get(0));
	}

	/**
	* Clicks a product referenced by prodId on the products page
	*
	* @param prodId
	* */
	public void clickProductByID(String prodId){
		String xpath = String.format("//*/a[contains(text(),'%s')]",prodId);
		click.clickElementCarefully(By.xpath(xpath));
	}

	/**
	 * Clicks the "view" button to navigate to the price book entry page for a product
	 * */
	public void clickPriceBookEntryView(){
		List<WebElement> priceBookEntries = driver.findElements(PRICE_BOOK_ENTRY_VIEW);
		click.clickElementCarefully(priceBookEntries.get(0));
	}

	/**
	 * Clicks the "view" button to navigate to the price book page for a product
	 * */
	public void clickPriceBookView(){
		click.clickElementCarefully(PRICE_BOOK_VIEW);
	}

	/**
	 * Clicks the "Back to entries" button on the price book entry page
	 * */
	public void clickPriceBackToEntriesView(){
		click.clickElementCarefully(BACK_TO_ENTRIES);
	}

	/**
	 * Creates a new invoice scheduler
	 * */
	public void createInvoiceScheduler(){
		click.clickElementCarefully(SCHEDULER_TAB_BUTTON);

		wait.waitForElementEnabled(NEW_SCHEDULER_BUTTON);
		click.clickElementCarefully(NEW_SCHEDULER_BUTTON);

		WebElement schedNameInput = wait.waitForElementEnabled(SCHEDULER_NAME_INPUT);
		click.clickElementCarefully(schedNameInput);
		text.enterText(schedNameInput, "TST_Scheduler");
		schedNameInput.sendKeys(Keys.ENTER);

		click.clickElementCarefully(SCHEDULER_BATCH_1);

		WebElement schedulerType = wait.waitForElementDisplayed(SCHEDULER_TYPE_SELECT);
		schedulerType.sendKeys(Keys.ARROW_DOWN);
		schedulerType.sendKeys(Keys.ENTER);

		SimpleDateFormat dtf = new SimpleDateFormat("MM/dd/yyyy Hh:mm aa");
		LocalDateTime now = LocalDateTime.now().plus(Duration.of(2, ChronoUnit.MINUTES));
		WebElement dateInput = wait.waitForElementDisplayed(SCHEDULER_DATE_INPUT);
		text.enterText(dateInput,dtf.format(now));
		click.clickElementCarefully(SCHEDULER_SAVE_BUTTON);

	}

}
