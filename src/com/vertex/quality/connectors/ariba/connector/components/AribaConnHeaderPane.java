package com.vertex.quality.connectors.ariba.connector.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnSignOnPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * handles interaction with items in the header element of Ariba's Vertex Configuration page
 *
 * @author ssalisbury
 */
public class AribaConnHeaderPane extends VertexComponent
{
	protected final By logoutButtonLoc = By.xpath("//button[@id='logout-button']");
	protected final By accountIconLoc = By.xpath("//button[@class='utility-control__btn']");

	//warning- this is currently the 'displayed text' of a 1pixel x 1pixel element inside the button
	protected final String accountIconName = "Logout";

	public AribaConnHeaderPane( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * this should click the account icon on the right-hand end of the page's header,
	 * causing that icon's flyout panel, which contains the site's logout button, to
	 * appear
	 *
	 * @author ssalisbury
	 */
	public void clickAccountIcon( )
	{
		List<WebElement> icons = wait.waitForAnyElementsDisplayed(accountIconLoc);
		WebElement icon = element.selectElementByNestedLabel(icons, By.tagName("span"), accountIconName);
		wait.waitForElementEnabled(icon);
		click.clickElement(icon);

		waitForPageLoad();
	}

	/**
	 * checks whether the logout button is possibly clickable
	 *
	 * @return whether the logout button is displayed
	 *
	 * @author ssalisbury
	 */
	public boolean isLogoutButtonDisplayed( )
	{
		WebElement logoutButton = wait.waitForElementPresent(logoutButtonLoc);
		boolean isLogoutDisplayedEnabled = element.isElementDisplayed(logoutButton);

		return isLogoutDisplayedEnabled;
	}

	/**
	 * logs out of Ariba's vertex configuration site after logging out of Ariba's
	 * vertex configuration site
	 *
	 * @return the sign-on page that you are redirected to
	 *
	 * @author ssalisbury
	 */
	public AribaConnSignOnPage clickLogout( )
	{
		clickAccountIcon();
		WebElement logoutButton = wait.waitForElementPresent(logoutButtonLoc);
		click.javascriptClick(logoutButton);
		AribaConnSignOnPage signOn = initializePageObject(AribaConnSignOnPage.class);

		return signOn;
	}
}
