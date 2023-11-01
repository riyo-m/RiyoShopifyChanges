package com.vertex.quality.connectors.bigcommerce.ui.pages.devcloud;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * this class represent the invite user methods
 *
 * @author rohit.mogane
 */
public class OseriesCloudInviteUserPage extends OseriesCloudBasePage
{

	public OseriesCloudInviteUserPage( final WebDriver driver )
	{
		super(driver);
	}

	protected final By inviteUserLink = By.xpath("//a[@href='/Admin/Users/Invite']");
	protected final By inviteUserButton = By.xpath("//a[@href='#/inviteNew']");

	/**
	 * locates and click on invite user link on dev cloud
	 */
	public void clickInviteUserLink( )
	{
		WebElement inviteUserLinkField = wait.waitForElementDisplayed(inviteUserLink);
		click.clickElement(inviteUserLinkField);
	}

	/**
	 * locates and click on invite user button on dev cloud
	 */
	public void clickInviteUserButton( )
	{
		WebElement inviteUserButtonField = wait.waitForElementDisplayed(inviteUserButton, 60);
		click.clickElement(inviteUserButtonField);
		jsWaiter.sleep(3000);
		click.clickElement(inviteUserButtonField);
	}
}
