package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * This class contains method to click on Add transaction type in
 * Oracle ERP part of E2E scenario for AR Transaction Type
 *
 * @author mgaikwad
 */

public class OracleCloudManageTransactionTypePage extends OracleCloudBasePage
{

	public OracleCloudManageTransactionTypePage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@title='Create']/a")
	private WebElement addTransactionTypeButton;

	/**
	 * Method to click on add button in Manage transaction type page
	 * in OracleERP
	 *
	 */
	public void clickOnAddTransactionType( )
	{
		wait.waitForElementDisplayed(addTransactionTypeButton);
		click.clickElement(addTransactionTypeButton);
	}
}
