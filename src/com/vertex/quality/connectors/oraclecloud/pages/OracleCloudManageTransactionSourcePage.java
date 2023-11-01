package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * This class contains method to click on Add transaction source in
 * Oracle ERP part of E2E scenario for AR Transaction source
 *
 * @author mgaikwad
 */

public class OracleCloudManageTransactionSourcePage extends OracleCloudBasePage
{

	public OracleCloudManageTransactionSourcePage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@title='Create']/a")
	private WebElement addTransactionSourceButton;

	/**
	 * Method to click on add button in Manage transaction source page
	 * in OracleERP
	 *
	 */
	public void clickOnAddTransactionSource( )
	{
		wait.waitForElementDisplayed(addTransactionSourceButton);
		click.clickElement(addTransactionSourceButton);
	}
}
