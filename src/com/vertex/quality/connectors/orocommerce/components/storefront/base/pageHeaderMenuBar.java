package com.vertex.quality.connectors.orocommerce.components.storefront.base;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * represents the menu bar of the base page header section
 * contains all the methods necessary to interact with it
 *
 * @author alewis
 */
public class pageHeaderMenuBar extends VertexComponent
{
	public pageHeaderMenuBar( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	By mainMenuContLoc = By.cssSelector("ul[class='main-menu']");
	By productTabLoc = By.xpath("//span[contains(text(),'Products')]");
}
