package com.vertex.quality.connectors.mirakl.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.mirakl.api.enums.MiraklOperatorsData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;

/**
 * Taxpayers page actions of O-Series
 *
 * @author rohit.mogane
 */
public class OSeriesTaxpayers extends MiraklHomePage
{

	public OSeriesTaxpayers( final WebDriver driver )
	{
		super(driver);
	}

	protected By myEnterprise = By.xpath("//*[contains(text(),'My Enterprise')]//parent::div");
	protected By taxPayers = By.xpath("//*[contains(text(),'Taxpayers')]//parent::li");
	protected By showActions = By.xpath("(//*[contains(text(),'Show Actions')]//parent::button)");
	protected By deleteTaxPayer = By.xpath("//*[contains(text(),'Delete')]");
	protected By deleteConfirm = By.xpath("//*[contains(text(),'Delete Taxpayer')]//following::button[1]");
	protected By taxPayersNameCol = By.xpath("(//*[@col-id='taxpayerName'])[2]");
	protected By allTaxpayersCount = By.xpath("//*[contains(text(),'Last')]//following::strong");

	/**
	 * Pass As of date to O-Series URL
	 */
	public void selectAsOfDate( )
	{
		driver
			.navigate()
			.to(MiraklOperatorsData.OSERIES_URL_NEW.data + "ui/calc-config/supplies/" +
				MiraklOperatorsData.OSERIES_AS_OF_DATE.data + "/system/home");
	}

	/**
	 * Select My Enterprise tab from home page side menu
	 */
	public void selectMyEnterpriseTab( )
	{
		waitForPageLoad();
		WebElement myEnterpriseField = wait.waitForElementDisplayed(myEnterprise, 200);
		click.moveToElementAndClick(myEnterpriseField);
	}

	/**
	 * Select TaxPayer's tab from home page side menu
	 */
	public void selectTaxPayersTab( )
	{
		WebElement taxPayersField = wait.waitForElementEnabled(taxPayers);
		click.moveToElementAndClick(taxPayersField);
	}

	/**
	 * Delete the particular taxPayer
	 *
	 * @param taxPayersToIgnore this array will be used ignore the taxPayers
	 */
	public void deleteTaxPayersList( String[] taxPayersToIgnore )
	{
		waitForPageLoad();
		String taxPayersCount = wait
			.waitForElementDisplayed(allTaxpayersCount, 120)
			.getText();
		String taxPayersName = wait
			.waitForElementDisplayed(taxPayersNameCol)
			.getText();
		for ( int i = 1 ; i < Integer.parseInt(taxPayersCount) ; i++ )
		{
			waitForPageLoad();
			if ( Arrays
				.asList(taxPayersToIgnore)
				.contains(taxPayersName) )
			{
				VertexLogger.log("Taxpayer to ignore - " + taxPayersName);
			}
			else
			{
				WebElement showActionsElement = wait.waitForElementDisplayed(showActions, 200);
				click.moveToElementAndClick(showActionsElement);
				WebElement deleteTaxPayerAction = wait.waitForElementDisplayed(deleteTaxPayer);
				click.moveToElementAndClick(deleteTaxPayerAction);
				WebElement deleteTaxPayerConfirm = wait.waitForElementDisplayed(deleteConfirm);
				click.moveToElementAndClick(deleteTaxPayerConfirm);
				VertexLogger.log(i + " - deleted");
			}
		}
	}
}
