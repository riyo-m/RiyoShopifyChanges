package com.vertex.quality.connectors.magento.common.pages;

import com.vertex.quality.connectors.magento.admin.components.M2AdminNavigationPanel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * encapsulates features common to all 'pages' on Magento's Admin site
 *
 * This is necessary primarily because all navigation to different pages/menus
 * on it leaves the current URL of the site unchanged, and, in any case, the
 * header/footer/ navigation menu remain present/unchanged regardless of the
 * page currently being viewed
 *
 * @author alewis
 */
public abstract class MagentoAdminPage extends MagentoPage
{
	public M2AdminNavigationPanel navPanel;

	protected String nodeSection = ".//a[normalize-space(.)='<<text_replace>>']";
	protected String expandedNode = ".//a[normalize-space(.)='<<text_replace>>'][@class='open']";
	protected By myAccountMenu = By.xpath(".//a[@title='My Account']");
	protected By myAccountExpandedMenu = By.xpath(".//a[@title='My Account']/parent::div[contains(@class,'active')]");
	protected By myAccountDialog = By.xpath(".//a[@title='My Account']/following-sibling::ul");
	protected By myAccountOptions = By.xpath(".//a[@title='My Account']/following-sibling::ul//li");
	protected By signOutOption = By.xpath(".//a[@title='My Account']/following-sibling::ul//li[normalize-space(.)='Sign Out']");

	public MagentoAdminPage( WebDriver driver )
	{
		super(driver);

		navPanel = new M2AdminNavigationPanel(driver);
	}

	/**
	 * Expands the node if it's collapsed
	 *
	 * @param nodeName name of the node which is to be expanded
	 */
	public void expandNode(String nodeName) {
		waitForPageLoad();
		waitForSpinnerToBeDisappeared();
		WebElement node = wait.waitForElementPresent(By.xpath(nodeSection.replace("<<text_replace>>", nodeName)));
		By expNode = By.xpath(expandedNode.replace("<<text_replace>>", nodeName));
		if (!element.isElementDisplayed(expNode)) {
			click.moveToElementAndClick(node);
		}
	}

	/**
	 * Signs out user from the Magento Admin Panel
	 */
	public void signOutFromAdminPanel() {
		waitForPageLoad();
		waitForSpinnerToBeDisappeared();
		WebElement myAccount = wait.waitForElementPresent(myAccountMenu);
		if (!element.isElementDisplayed(myAccountExpandedMenu)) {
			click.moveToElementAndClick(myAccount);
			wait.waitForElementPresent(myAccountExpandedMenu);
			wait.waitForElementPresent(myAccountDialog);
			wait.waitForAllElementsPresent(myAccountOptions);
		}
		click.moveToElementAndClick(wait.waitForElementPresent(signOutOption));
	}
}
