package com.vertex.quality.connectors.taxlink.ui_e2e.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * this class represents all the locators and methods for AR Transaction Source page
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkArTransactionSourcePage extends TaxLinkBasePage
{
	public TaxLinkArTransactionSourcePage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='lookupCodeDesc']")
	private List<WebElement> summaryTableArTransactionSrc;

	By transIdentifier = By.xpath(".//parent::div/parent::div/following-sibling::div[@col-id='lookupCodeDesc']");

	/**
	 * click on Dashboard Dropdown in Taxlink application
	 */
	public void clickOnDashboardDropdown( )
	{
		if ( !arTransactionSourceButton.isDisplayed() )
		{
			click.clickElement(dashboardMenuLoc);
		}
	}

	/**
	 * Verify title of AR Transaction Source Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitleArTransSourcePage( )
	{
		boolean flag;
		String arTransSrcTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		boolean verifyFlag = arTransSrcTitle.equalsIgnoreCase("AR Transaction Source");

		if ( verifyFlag )
		{
			flag = true;
		}
		else
		{
			flag = false;
		}
		return flag;
	}

	/*
	 * Verify import option of AR Transaction Source
	 *
	 * @return boolean
	 */

	public boolean importArTransSourceToTaxlink( String oracleERP_Text )
	{
		boolean flagFinal = false;
		boolean flagImport = false, flagSearch = false, flagAlreadyImported = false;

		navigateToArTransSource();
		verifyTitleArTransSourcePage();

		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		int totalPageCount = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int j = 1 ; j <= totalPageCount ; j++ )
		{
			Optional<WebElement> data = dataPresent(oracleERP_Text, summaryTableArTransactionSrc);

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
					click.clickElement(nextArrowOnSummaryTable);
			}
		}
		if ( flagAlreadyImported )
		{
			flagFinal = true;
		}
		else
		{
			expWait.until(ExpectedConditions.elementToBeClickable(importButtonOnSummaryPage));
			click.clickElement(importButtonOnSummaryPage);

			expWait.until(ExpectedConditions.elementToBeClickable(importSelectAllCheckBox));

			int importPageCount = Integer.parseInt(importTotalPageCount.getText());
			for ( int i = 0 ; i <= importPageCount ; i++ )
			{
				Optional<WebElement> ele = importListCheckBox
					.stream()
					.filter(e -> e
						.findElement(transIdentifier)
						.getText()
						.contains(oracleERP_Text))
					.findFirst();

				jsWaiter.sleep(5000);

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
					wait.waitForElementDisplayed(externalFilter,50);
					VertexLogger.log(oracleERP_Text + " is Imported!!");
					break;
				}
				else
				{
					click.clickElement(importNextArrow);
				}
			}
			jsWaiter.sleep(5000);

			wait.waitForElementDisplayed(externalFilter);
			VertexLogger.log("Searching for "+oracleERP_Text +" in summary table!!");

			int count = Integer.parseInt(totalPageCountSummaryTable.getText());
			for ( int i = 1 ; i <= count ; i++ )
			{
				jsWaiter.sleep(5000);
				Optional<WebElement> data = dataPresent(oracleERP_Text, summaryTableArTransactionSrc);
				if ( data.isPresent() )
				{
					VertexLogger.log(oracleERP_Text + " is imported to Taxlink.. Verified in Summary table!");
					flagImport = true;
					break;
				}
				else
				{
					if(nextArrowOnSummaryTable.findElement(By.xpath(".//parent::div")).getAttribute("class").contains("disabled"))
					{
						VertexLogger.log("End of pages!");
						break;
					}
					else
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
			flagFinal = true;
		}
		return flagFinal;
	}
}

