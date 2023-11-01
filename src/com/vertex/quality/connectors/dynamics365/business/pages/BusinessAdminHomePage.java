package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.business.components.BusinessHomePageMainNavMenu;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.BusinessVendorListsPage;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents the home page of business central environment after the login process
 *
 * @author osabha, cgajes,K.Bhikshapathi
 */
public class BusinessAdminHomePage extends BusinessBasePage
{
	public BusinessHomePageMainNavMenu mainNavMenu;

	protected By headerTitle = By.tagName("title");
	protected By mainFrameLoc = By.className("designer-client-frame");
	protected By dialogBoxLoc = By.className("ms-nav-content-box");
	protected By actionsCon = By.cssSelector("div[class*='stacked-command-bar-container']");

	protected By dialogBoxTitleLoc = By.className("dialog-title");
	protected By dialogBoxButtonCon = By.className("dialog-action-bar");
	protected By vertexAdminButtonLoc = By.cssSelector("button[aria-label='Vertex Admin'] > span");
	protected By customersButtonLoc = By.cssSelector("[aria-label='Customers']");
	protected By salesButtonLoc = By.xpath("(//span[@aria-label='Sales'])[1]");
	protected By vendorsButtonLoc = By.cssSelector("[aria-label='Vendors']");

	public BusinessAdminHomePage( WebDriver driver )
	{
		super(driver);
		this.mainNavMenu = new BusinessHomePageMainNavMenu(driver, this);
	}

	/**
	 * Returns the page's title for verification
	 *
	 * @return page title
	 */
	public String verifyHomepageHeader( )
	{
		String titleTxt = getPageTitle();

		return titleTxt;
	}

	/**
	 * If a popup appears warning the user of a license expiration, click OK to bypass
	 */
	public void checkForCautionPopup( )
	{
		try
		{
			driver
				.switchTo()
				.frame(wait.waitForElementPresent(mainFrameLoc, 30));

			WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc, 15);
			WebElement textContent = wait.waitForElementDisplayed(dialogBoxTitleLoc, dialog);

			String text = textContent.getText();

			if ( text.contains("Caution: Your program license expires in ") )
			{
				dialogBoxClickOk();
			}
		}
		catch ( TimeoutException e )
		{
			// if popup doesn't appear, no need to do anything
		}
	}

	/**
	 * Clicks the link to the customers list page
	 *
	 * @return customers list page
	 */
	public BusinessCustomersListPage navigateToCustomersListPage( )
	{
		WebElement button = wait.waitForElementDisplayed(customersButtonLoc);
		click.javascriptClick(button);
		waitForPageLoad();

		return initializePageObject(BusinessCustomersListPage.class);
	}

	/**
	 * Clicks the link to the Vendors list page
	 *
	 * @return vendors list page
	 */
	public BusinessVendorListsPage navigateToVendorsListPage( )
	{
		WebElement button = wait.waitForElementEnabled(vendorsButtonLoc);
		click.clickElementCarefully(button);
		waitForPageLoad();

		return initializePageObject(BusinessVendorListsPage.class);
	}
	/**
	 * Navigates to the vertex admin configurations page by clicking the link on the home page
	 *
	 * @return vertex admin page
	 */
	public BusinessVertexAdminPage navigateToVertexAdminPage( )
	{
        window.switchToFrame(mainFrameLoc,30);
		WebElement vertexAdminButton = wait.waitForElementEnabled(vertexAdminButtonLoc);
		try{
			click.javascriptClick(vertexAdminButton);
		}catch (Exception e){
			VertexLogger.log(e.toString());
			click.clickElementCarefully(vertexAdminButton);
		}

		return initializePageObject(BusinessVertexAdminPage.class);
	}
}
