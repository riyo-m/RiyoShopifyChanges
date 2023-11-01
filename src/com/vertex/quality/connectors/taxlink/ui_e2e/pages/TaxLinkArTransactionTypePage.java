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
 * this class represents all the locators and methods for AR Transaction Type page
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkArTransactionTypePage extends TaxLinkBasePage
{
	public TaxLinkArTransactionTypePage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='trxTypeName']")
	private List<WebElement> summaryTableArTransactionType;

	@FindBy(xpath = "//div[@class='ag-cell ag-cell-not-inline-editing ag-cell-with-height ag-cell-value'][@col-id='trxTypeName']")
	private List<WebElement> arTransTypeNamePresentation;

	@FindBy(xpath = "(//span[contains(@ref,'lbTotal')])[last()-1]")
	protected WebElement totalPageCountSummaryTableArType;

	By transIdentifier = By.xpath(".//parent::div/parent::div/following-sibling::div[@col-id='trxTypeName']");
	By enabledCol = By.xpath(".//following-sibling::div[2]");
	By editButtonFollowingTransName = By.xpath(".//following-sibling::div/div//button[contains(text(),'Edit')]");

	/**
	 * click on Dashboard Dropdown in Taxlink application
	 */
	public void clickOnDashboardDropdown( )
	{
		if ( !arTransactionTypeButton.isDisplayed() )
		{
			click.clickElement(dashboardMenuLoc);
		}
	}

	/**
	 * Verify title of AR Transaction Type Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitleArTransTypePage( )
	{
		boolean flag;
		String arTransTypeTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		boolean verifyFlag = arTransTypeTitle.equalsIgnoreCase("AR Transaction Type");

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
	 * Verify import option of AR Transaction Type
	 *
	 * @return boolean
	 */

	public boolean importArTransTypeToTaxlink( String oracleERP_Text )
	{
		boolean flagFinal = false;
		boolean flagImport = false, flagSearch = false, flagAlreadyImported = false, flagEnabled = false;

		navigateToArTransType();
		verifyTitleArTransTypePage();
		expWait.until(ExpectedConditions.visibilityOf(externalFilter));
		click.clickElement(externalFilter);
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		int totalPageCount = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int j = 1 ; j <= totalPageCount ; j++ )
		{
			Optional<WebElement> data = dataPresent(oracleERP_Text, arTransTypeNamePresentation);
			jsWaiter.sleep(5000);
			if ( data.isPresent() )
			{
				VertexLogger.log(oracleERP_Text + " is already imported to Taxlink.. Verified in Summary table!");
				if ( data
					.get()
					.findElement(enabledCol)
					.getText()
					.contains("N") )
				{
					data
						.get()
						.findElement(editButtonFollowingTransName)
						.click();
					enableRecord();
					flagEnabled = true;
				}
				else
				{
					flagEnabled = true;
				}
				flagAlreadyImported = true;
				break;
			}
			else
			{
				if ( nextArrowOnSummaryTable
					.findElement(By.xpath(".//parent::div"))
					.getAttribute("class")
					.contains("disabled") )
				{
					break;
				}
				else
				{
					click.clickElement(nextArrowOnSummaryTable);
				}
			}
		}
		if ( flagEnabled && flagAlreadyImported )
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
					click.clickElement(importNextArrow);
				}
			}

			expWait.until(ExpectedConditions.visibilityOf(externalFilter));
			click.clickElement(externalFilter);
			dropdown.selectDropdownByDisplayName(externalFilter, "Both");

			int count = Integer.parseInt(totalPageCountSummaryTable.getText());
			for ( int i = 1 ; i <= count ; i++ )
			{
				Optional<WebElement> data = dataPresent(oracleERP_Text, arTransTypeNamePresentation);

				if ( data.isPresent() )
				{
					VertexLogger.log(oracleERP_Text + " is imported to Taxlink.. Verified in Summary table!");
					flagImport = true;
					break;
				}
				else
				{
					expWait.until(ExpectedConditions.elementToBeClickable(nextArrowOnSummaryTable));
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

	/**
	 * Enable the disabled record on Edit page of transaction type in Taxlink application
	 */
	public void enableRecord( )
	{
		click.clickElement(enabledFlag);
		wait.waitForElementEnabled(saveButton);
		click.clickElement(saveButton);
	}
}

