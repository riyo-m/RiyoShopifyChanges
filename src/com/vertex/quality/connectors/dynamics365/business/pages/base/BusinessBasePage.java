package com.vertex.quality.connectors.dynamics365.business.pages.base;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.dynamics365.business.components.BusinessHomePageMainNavMenu;
import com.vertex.quality.connectors.dynamics365.business.components.BusinessPagesNavMenu;
import com.vertex.quality.connectors.dynamics365.business.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents the base page for the business central environment pages
 *
 * @author osabha
 */
public class BusinessBasePage extends VertexPage

{
	public BusinessHomePageMainNavMenu mainNavMenu;
	public BusinessPagesNavMenu pageNavMenu;

	protected By loadingClass = By.className("splash");
	protected By mainFrameLoc = By.className("designer-client-frame");
	protected By dialogBoxLoc = By.className("ms-nav-content-box");

	protected By topBarCon = By.id("product-menu-bar");
	protected By searchBoxCon = By.className("ms-dyn365-fin");
	protected By dialogButtonCon = By.className("ms-nav-actionbar-container");

	protected By searchButtonFieldLoc = By.xpath("//button[@title='Search']");
	protected By settingsButtonField=By.xpath("//button[@title='Settings']");
	protected By helpButtonField = By.xpath("//button[@title='Help']");
	protected By closeButtonField = By.xpath("//button[@title='Close']");
	protected By helpAndSupportButtonField = By.xpath("//button[text()='Help & Support']");
	protected By connectorVersionLoc = By.xpath("//span[contains(text(), 'Version')]"); ////span[text()='Troubleshooting']/../..
	protected By allAvailableRolesList=By.xpath("//div[contains(@class,'ms-nav-scrollable')]//td[@data-prev-tabindex=-1]/a");
	protected By roleEllipsis=By.xpath("//div[@controlname='UserRoleCenter']//a[contains(@class,'MoreEllipsis')]");
	protected By allButtonsList=By.xpath("//span[contains(@class,'ms-ContextualMenu-itemText')]");
	protected By searchBarFieldLoc =By.xpath("//div[@class='ms-nav-group']//input[@type='text' and (not(@tabindex))]");
	protected By searchResultArrow = By.className("icon-RightCaret");
	protected By searchResult = By.xpath("//div[text()='Vertex Admin']");
	protected By manualSetup = By.xpath("//div[text()='Manual Setup']");
	protected By searchResultOfTaxGroups = By.xpath("(//div[contains(.,'Tax Groups')])[34]");
	protected By searchResutCon = By.cssSelector("div[class*='ms-itemContent--']");
	protected By docNo = By.xpath("(//a[contains(@title,'Open record')][not(@aria-hidden)])[1]");

	protected By buttonLoc = By.tagName("button");

	public BusinessBasePage( WebDriver driver )
	{
		super(driver);
		this.mainNavMenu = new BusinessHomePageMainNavMenu(driver, this);
		this.pageNavMenu = new BusinessPagesNavMenu(driver, this);
	}

	/**
	 * Waits for the loading screen, which covers the entire webpage,
	 * to disappear
	 * waitForPageLoad does not wait long enough for the cover to disappear
	 */
	public void waitForLoadingScreen( )
	{
		wait.waitForElementNotPresent(loadingClass);
	}

	public void refreshAndWaitForLoad( )
	{
		refreshPage();
		waitForLoadingScreen();
	}

	public void switchFrame( )
	{
		try
		{
			driver
				.switchTo()
				.frame(wait.waitForElementPresent(mainFrameLoc, 30));
		}
		catch ( TimeoutException e )
		{

		}
	}
	/**
	 * select the Settings on page by clicking on settings icon at top of page
	 */
	public void selectSettings(){
		String label="My Settings";
		driver.switchTo().parentFrame();
		WebElement topCon = wait.waitForElementDisplayed(topBarCon);
		WebElement settingsButton=wait.waitForElementDisplayed(settingsButtonField,topCon);
		click.clickElementCarefully(settingsButton);
		//select My Settings
		List<WebElement> buttonsList= wait.waitForAllElementsPresent(allButtonsList);
		WebElement settingSelect=element.selectElementByText(buttonsList,label);

		click.clickElementIgnoreExceptionAndRetry(settingSelect);

		driver.switchTo().frame(wait.waitForElementPresent(mainFrameLoc, 45));
		waitForPageLoad();
	}

	/**
	 * Click help icon at top of page and select Help & Support
	 */
	public void navigateToHelpAndSupport(){
		String label="Help";
		driver.switchTo().parentFrame();
		WebElement topCon = wait.waitForElementDisplayed(topBarCon);
		WebElement helpButton = wait.waitForElementDisplayed(helpButtonField,topCon);
		click.clickElementIgnoreExceptionAndRetry(helpButton);

		//select Help & Support
		wait.waitForElementDisplayed(helpAndSupportButtonField);
		click.clickElementIgnoreExceptionAndRetry(helpAndSupportButtonField);

		waitForPageLoad();
	}

	/**
	 * Click close icon for Help section
	 */
	public void clickCloseButton() {
		wait.waitForElementDisplayed(closeButtonField);
		click.clickElementIgnoreExceptionAndRetry(closeButtonField);

		waitForPageLoad();
	}

	/**
	 * Get connector version
	 *
	 * @return connectorVersion
	 */
	public String getConnectorVersion() {

		WebElement connectorVersionField = wait.waitForElementPresent(connectorVersionLoc);
		return connectorVersionField.getText();
	}


	/**
	 * Navigates to the settings and select role as Service Manager or Business Manager
	 * @param role
	 */
	public void navigateToManagerInSettings(String role) {
		WebElement roleClick = wait.waitForElementDisplayed(roleEllipsis);
		click.clickElementCarefully(roleClick);
		List<WebElement> availableRoles = wait.waitForAllElementsPresent(allAvailableRolesList);
		WebElement selectRole = element.selectElementByText(availableRoles, role);
		click.clickElementCarefully(selectRole);
		dialogBoxClickOk();
		waitForPageLoad();

	}

	/**
	 * Navigates to the Vertex Admin Configurations page by searching for it
	 *
	 */
	public void searchForAndNavigateToPage(String page ) {
		driver.switchTo().parentFrame();
		WebElement topCon = wait.waitForElementDisplayed(topBarCon);
		WebElement searchButton = wait.waitForElementDisplayed(searchButtonFieldLoc, topCon);
		click.clickElement(searchButton);

		driver
				.switchTo()
				.frame(wait.waitForElementPresent(mainFrameLoc, 45));
		WebElement dialogBox = wait.waitForElementDisplayed(dialogBoxLoc);

		WebElement searchBarField = wait.waitForElementPresent(searchBarFieldLoc, dialogBox);

		click.clickElementIgnoreExceptionAndRetry(searchBarField);

		text.setTextFieldCarefully(searchBarField, page, false);
		By searchResultString = By.xpath(String.format("//div[text()='%s']", page));
		WebElement vertexAdminButton = wait.waitForElementPresent(searchResultString);
		wait.waitForElementEnabled(vertexAdminButton);

		click.clickElementIgnoreExceptionAndRetry(vertexAdminButton);

		waitForPageLoad();
	}

	/**
	 * Navigates to the Vertex Admin Configurations page by searching for it
	 *
	 * @return Vertex Admin page
	 */
	public BusinessVertexAdminPage searchForAndNavigateToVertexAdminPage( )
	{
		searchForAndNavigateToPage( "Vertex Admin");
		BusinessVertexAdminPage vertexAdminPage = new BusinessVertexAdminPage(driver);

		return vertexAdminPage;
	}

	/**
	 * Navigates to the Manual Setup page by searching for it
	 *
	 * @return Manual Setup Page
	 */
	public BusinessManualSetupPage searchForAndNavigateToManualSetupPage( )
	{
		driver.switchTo().parentFrame();
		WebElement topCon = wait.waitForElementDisplayed(topBarCon);
		WebElement searchButton = wait.waitForElementDisplayed(searchButtonFieldLoc, topCon);
		click.clickElement(searchButton);

		driver
				.switchTo()
				.frame(wait.waitForElementPresent(mainFrameLoc, 45));
		WebElement dialogBox = wait.waitForElementDisplayed(dialogBoxLoc);

		WebElement searchBarField = wait.waitForElementPresent(searchBarFieldLoc, dialogBox);
		click.clickElement(searchBarField);

		text.enterText(searchBarField, "manual setup");
		WebElement manualSetupButton = wait.waitForElementPresent(manualSetup);
		wait.waitForElementEnabled(manualSetupButton);
		click.clickElementCarefully(manualSetupButton);

		waitForPageLoad();
		BusinessManualSetupPage manualSetupPage = new BusinessManualSetupPage(driver);

		return manualSetupPage;
	}

	/**
	 * Navigates to the Vertex Admin Configurations page by searching for it
	 *
	 * @return taxGroupsPage
	 */
	public BusinessVertexAdminPage searchForAndNavigateToTaxGroupsPage( )
	{
		driver.switchTo().parentFrame();
		WebElement topCon = wait.waitForElementDisplayed(topBarCon);
		WebElement searchButton = wait.waitForElementDisplayed(searchButtonFieldLoc, topCon);
		click.clickElement(searchButton);

		driver
				.switchTo()
				.frame(wait.waitForElementPresent(mainFrameLoc, 45));
		WebElement dialogBox = wait.waitForElementDisplayed(dialogBoxLoc);

		WebElement searchBarField = wait.waitForElementPresent(searchBarFieldLoc, dialogBox);
		click.clickElement(searchBarField);

		text.enterText(searchBarField, "Tax Groups");
		WebElement taxGroups = wait.waitForElementPresent(searchResultOfTaxGroups);
		wait.waitForElementEnabled(taxGroups);
		click.clickElementCarefully(taxGroups);

		waitForPageLoad();
		BusinessVertexAdminPage taxGroupsPage = new BusinessVertexAdminPage(driver);

		return taxGroupsPage;
	}

	/**
	 * Navigates to the Vertex Diagnostics page by searching for it
	 *
	 * @return the Vertex Diagnostics page
	 */
	public BusinessVertexDiagnosticsPage searchForAndNavigateToVertexDiagnosticsPage( )
	{
		searchForAndNavigateToPage( "Vertex Diagnostics");

		BusinessVertexDiagnosticsPage diagnosticsPage = new BusinessVertexDiagnosticsPage(driver);

		return diagnosticsPage;
	}

	public BusinessVertexCustomerClassPage searchForAndNavigateToVertexCustomerClassPage( )
	{
		driver.switchTo().parentFrame();
		WebElement searchContainer = wait.waitForElementDisplayed(topBarCon);
		WebElement searchButton = wait.waitForElementDisplayed(searchButtonFieldLoc, searchContainer);
		click.clickElement(searchButton);

		driver
			.switchTo()
			.frame(wait.waitForElementPresent(mainFrameLoc, 30));
		WebElement dialogBox = wait.waitForElementDisplayed(dialogBoxLoc);

		WebElement searchBarField = wait.waitForElementPresent(searchBarFieldLoc, dialogBox);
		click.clickElement(searchBarField);

		text.enterText(searchBarField, "Customer Class");
		WebElement customerClassButton = wait.waitForElementPresent(searchResutCon);
		wait.waitForElementEnabled(customerClassButton);
		click.clickElement(customerClassButton);

		waitForPageLoad();
		BusinessVertexCustomerClassPage customerClassPage = new BusinessVertexCustomerClassPage(driver);

		return customerClassPage;
	}

	/**
	 * Generalized method, should work on any popup box
	 * Selects "OK"
	 */
	public void dialogBoxClickOk( )
	{
		WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
		WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialog);
		List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonLoc, buttonCon);
		WebElement button = element.selectElementByText(buttonList, "OK");
		click.clickElement(button);

		wait.waitForElementNotDisplayedOrStale(button, 15);
	}

	/**
	 * Generalized method, should work on any popup box
	 * Selects "Yes"
	 */
	public void dialogBoxClickYes( )
	{
		WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
		WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialog);
		List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonLoc, buttonCon);
		WebElement button = element.selectElementByText(buttonList, "Yes");
		click.clickElement(button);

		wait.waitForElementNotDisplayedOrStale(button, 15);
	}

	/**
	 * Generalized method, should work on any popup box
	 * Selects "Close"
	 */
	public void dialogBoxClickClose( )
	{
		WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
		WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialog);
		List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonLoc, buttonCon);
		WebElement button = element.selectElementByText(buttonList, "Close");
		click.clickElement(button);

		wait.waitForElementNotDisplayedOrStale(button, 15);
	}

	/**
	 * Gets the document number
	 * @return the document number
	 */
	public String getDocNumberByRowIndex()
	{
		waitForPageLoad();
		WebElement customer = wait.waitForElementDisplayed(docNo);
		String number = customer.getText();
		return number;
	}
}
