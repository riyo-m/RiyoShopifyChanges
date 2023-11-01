package com.vertex.quality.connectors.kibo.pages;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.kibo.components.KiboMainMenuNavPanel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This class represents the homepage from which user can navigate to most of the other pages.
 * it contains all the methods necessary to interact with the page for navigation
 *
 * @author osabha
 */
public class KiboAdminHomePage extends VertexPage
{
	public KiboMainMenuNavPanel navPanel;

	public KiboAdminHomePage( WebDriver driver )
	{
		super(driver);
		this.navPanel = new KiboMainMenuNavPanel(driver, this);
	}

	protected By mainMenu = By.className("taco-primary-menu-trigger");

	/**
	 * locates and clicks on the main menu button
	 */
	public void clickMainMenu( )
	{
		waitForPageLoad();
		WebElement menu = wait.waitForElementPresent(mainMenu);

		menu.click();
	}
}
