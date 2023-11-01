package com.vertex.quality.connectors.taxlink.ui_e2e.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * this class represents the locators and methods for Oracle ERP
 * and Business Unit page
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkBusinessUnitPage extends TaxLinkBasePage
{

	public TaxLinkBusinessUnitPage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//*[@col-id='businessUnitName']")
	private List<WebElement> businessUnitNamePresentation;

	@FindBy(xpath = "//div[@class='ag-cell-wrapper']")
	private List<WebElement> importBUIDList;

	By buIdentifier = By.xpath(".//parent::div/parent::div/following-sibling::div[@col-id='businessUnitName']");

	/**
	 * click on Dashboard Dropdown in Taxlink application
	 */
	public void clickOnDashboardDropdown( )
	{
		if ( !businessUnitButton.isDisplayed() )
		{
			wait.waitForElementEnabled(dashboardMenuLoc);
			click.clickElement(dashboardMenuLoc);
		}
	}

	/**
	 * Verify title of Business Unit Page in Taxlink application
	 *
	 * @return
	 */
	public boolean verifyTitleBuPage( )
	{
		boolean flagVerifyTitleBuPage;
		String bUPageTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		boolean verifyFlag = bUPageTitle.equalsIgnoreCase("Business Units");

		if ( verifyFlag )
		{
			flagVerifyTitleBuPage = true;
		}
		else
		{
			flagVerifyTitleBuPage = false;
		}
		return flagVerifyTitleBuPage;
	}

	/**
	 * Import ERP's selected BU in Taxlink
	 *
	 * @return boolean
	 */
	public boolean importBusinessUnitTaxlink( String oracleERP_Text )
	{
		boolean flagImportBUTaxlink = false;
		boolean flagImport = false, flagSearch = false, flagAlreadyImported = false;

		navigateToBusinessUnit();
		verifyTitleBuPage();

		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		int totalPageCount = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int j = 1 ; j <= totalPageCount ; j++ )
		{
			Optional<WebElement> data = dataPresent(oracleERP_Text, businessUnitNamePresentation);

			if ( data.isPresent() )
			{
				VertexLogger.log(oracleERP_Text + " is already imported to Taxlink.. Verified in Summary table!");
				flagAlreadyImported = true;
				break;
			}
			else
			{
				if(nextArrowOnSummaryTable.findElement(By.xpath(".//parent::div")).getAttribute("class").contains("disabled"))
				{
					break;
				}
				else
					js.executeScript("arguments[0].scrollIntoView();", nextArrowOnSummaryTable);
					click.clickElement(nextArrowOnSummaryTable);
			}
		}
		if ( flagAlreadyImported )
		{
			flagImportBUTaxlink = true;
		}
		else
		{
			click.clickElement(importButtonOnSummaryPage);

			expWait = new WebDriverWait(driver, 120);
			expWait.until(ExpectedConditions.elementToBeClickable(importSelectAllCheckBox));

			int importPageCount = Integer.parseInt(importTotalPageCount.getText());
			for ( int i = 0 ; i <= importPageCount ; i++ )
			{
				Optional<WebElement> ele = importListCheckBox
					.stream()
					.filter(e -> e
						.findElement(buIdentifier)
						.getText()
						.contains(oracleERP_Text))
					.findFirst();

				if ( ele.isPresent() )
				{
					flagSearch = true;
					VertexLogger.log(oracleERP_Text + " is present in Import pop up");

					ele
						.get()
						.click();

					click.clickElement(selectImportButton);

					expWait.until(ExpectedConditions.textToBePresentInElement(popUpForImport,
						"Are you sure you want to save these records?"));

					click.clickElement(savePopUpImportButton);
					expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

					break;
				}
				else
				{
					wait.waitForElementDisplayed(importNextArrow);
					click.clickElement(importNextArrow);
				}
			}

			totalPageCount = Integer.parseInt(totalPageCountSummaryTable.getText());
			for ( int j = 1 ; j <= totalPageCount ; j++ )
			{
				Optional<WebElement> data = dataPresent(oracleERP_Text, businessUnitNamePresentation);

				if ( data.isPresent() )
				{
					flagImport = true;
					break;
				}
				else
				{
					click.clickElement(nextArrowOnSummaryTable);
				}
			}
			if ( getFileReadPath().size() != 0 )
			{
				try
				{
					deleteFile();
				}
				catch ( IOException e )
				{
					e.printStackTrace();
				}
			}
			else
			{
				flagImport = true;
			}
		}

		if ( flagImport && flagSearch )
		{
			flagImportBUTaxlink = true;
		}
		return flagImportBUTaxlink;
	}
}

