package com.vertex.quality.connectors.magento.admin.pages;

import com.vertex.quality.connectors.magento.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class M2AdminShipPage extends MagentoAdminPage
{
	By submitButtonClass = By.className("submit-button");

	public M2AdminShipPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * submits the shipment for the order
	 */
	public void submitShipment( )
	{
		WebElement submitButton = wait.waitForElementEnabled(submitButtonClass);
		click.clickElement(submitButton);
	}
}
