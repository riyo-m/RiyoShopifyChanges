package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * This class contains method to check the function's functionality
 * according to the record created in taxlink for invoices in
 * Oracle ERP part of E2E scenario for AP TAX CALC EXCLUSION
 *
 * @author mgaikwad
 */

public class OracleCloudApTaxCalcExclusionPage extends OracleCloudBasePage
{
	public OracleCloudApTaxCalcExclusionPage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='xel xkg']/div[2]")
	private WebElement transTaxTable;

	@FindBy(xpath = "//a[@title='Expand Taxes']")
	private WebElement taxesExpandLoc;

	@FindBy(xpath = "//a[text()='Transaction Taxes']")
	private WebElement transTaxesView;

	By blockingPane = By.className("AFBlockingGlassPane");

	/**
	 * Method to verify tax exclusion for an invoice
	 * in OracleERP
	 */
	public boolean verifyTaxRecord( )
	{
		boolean flagEmptyRecord = false;
		wait.waitForElementNotEnabled(blockingPane);

		try
		{
			click.javascriptClick(taxesExpandLoc);
		}
		catch ( NoSuchElementException | TimeoutException ex )
		{
			VertexLogger.log("Taxes summary section already expanded. Proceeding.", VertexLogLevel.INFO);
		}

		wait.waitForElementDisplayed(transTaxesView);
		click.javascriptClick(transTaxesView);

		jsWaiter.sleep(5000);

		if ( transTaxTable
			.getText()
			.contains("No data to display") )
		{
			flagEmptyRecord = true;
		}
		return flagEmptyRecord;
	}
}
