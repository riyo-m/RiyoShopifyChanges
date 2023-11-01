package com.vertex.quality.connectors.netsuite.common.pages;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteNavigationPane;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * The Netsuite homepage
 *
 * @author hho
 */
public class NetsuiteHomepage extends NetsuiteAuthenticationPage
{
	public NetsuiteNavigationPane navigationPane;

	public NetsuiteHomepage( final WebDriver driver )
	{
		super(driver);
		navigationPane = new NetsuiteNavigationPane(driver, this);
	}

	public void moveToAndClick( By elementLocator){
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(elementLocator)).moveByOffset(0, 0).click().build().perform();
	}

	protected void moveToAndClick( WebElement element ){
		//moveToAndClick();
	}

	//get elements row id
	public void toggleInactiveFlag( )
	{
		WebElement inactiveCheckbox = wait.waitForElementPresent(By.xpath("//input[@id='isinactive_fs_inp']"));
		click.javascriptClick(inactiveCheckbox);
		click.javascriptClick(By.xpath("//input[@id='btn_multibutton_submitter']"));
		waitForPageLoad();

	}
}
