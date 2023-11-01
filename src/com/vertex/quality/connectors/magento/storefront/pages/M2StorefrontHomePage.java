package com.vertex.quality.connectors.magento.storefront.pages;

import com.vertex.quality.connectors.magento.common.pages.MagentoStorefrontPage;
import com.vertex.quality.connectors.magento.storefront.components.StorefrontNavigationPanel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Representation of this storefront site before a particular page/menu has been
 * navigated to
 *
 * @author alewis
 */
public class M2StorefrontHomePage extends MagentoStorefrontPage
{
	protected By signInClass = By.className("authorization-link");

	protected By customerNameDropdownClass = By.className("customer-name");

	protected By customerMenuClass = By.className("customer-menu");

	protected By dropdownOptions = By.cssSelector("ul li");
	protected By headerLinksTag = By.tagName("li");

	public StorefrontNavigationPanel navPanel;
	By maskClass = By.className("loading-mask");

	public M2StorefrontHomePage( WebDriver driver )
	{
		super(driver);
		navPanel = new StorefrontNavigationPanel(driver, this);
	}

	/**
	 * clicks the sign in button
	 *
	 * @return the Storefront Login Page
	 */
	public M2StorefrontLoginPage clickSignInButton( )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		wait.waitForElementDisplayed(signInClass);
		WebElement field = driver.findElement(signInClass);
		click.clickElement(field);

		M2StorefrontLoginPage loginPage = initializePageObject(M2StorefrontLoginPage.class);

		return loginPage;
	}

	/**
	 * clicks create account button
	 *
	 * @return storefront create account page
	 */
	public M2StorefrontCreateAccountPage clickCreateAnAccountButton( )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		List<WebElement> headerLinkList = driver.findElements(headerLinksTag);

		WebElement field = element.selectElementByText(headerLinkList, "Create an Account");

		field.click();

		M2StorefrontCreateAccountPage createAccountPage = initializePageObject(M2StorefrontCreateAccountPage.class);

		return createAccountPage;
	}

	/**
	 * navigate to the My Account page by
	 * clicking on the dropdown under user's name
	 *
	 * @return storefront my account page
	 */
	public M2StorefrontMyAccountPage navigateToMyAccount( )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		WebElement accountDropdown = wait.waitForElementEnabled(customerNameDropdownClass);

		accountDropdown.click();

		WebElement menu = wait.waitForElementDisplayed(customerMenuClass);

		List<WebElement> linkList = wait.waitForAllElementsDisplayed(dropdownOptions, menu);

		WebElement link = element.selectElementByText(linkList, "My Account");

		link.click();

		M2StorefrontMyAccountPage myAccountPage = initializePageObject(M2StorefrontMyAccountPage.class);

		return myAccountPage;
	}

	/**
	 * logs out of storefront account
	 */
	public void logOutOfAccount( )
	{
		wait.waitForElementNotDisplayed(maskClass);
		waitForPageLoad();
		WebElement accountDropdown = wait.waitForElementEnabled(customerNameDropdownClass);

		accountDropdown.click();

		WebElement menu = wait.waitForElementDisplayed(customerMenuClass);

		List<WebElement> linkList = wait.waitForAllElementsDisplayed(dropdownOptions, menu);

		WebElement link = element.selectElementByText(linkList, "Sign Out");

		link.click();
	}
}
