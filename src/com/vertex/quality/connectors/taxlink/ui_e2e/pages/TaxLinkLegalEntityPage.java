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
 * This class contains all the methods to verify the E2E scenario of Legal Entity tab
 *
 * @author Shilpi.Verma
 */
public class TaxLinkLegalEntityPage extends TaxLinkBasePage
{

	public TaxLinkLegalEntityPage( WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id = 'lookupCodeDesc']")
	private List<WebElement> summaryTableLegalEntity;

	By leIdentifier = By.xpath(".//parent::div/parent::div/following-sibling::div[@col-id='lookupCodeDesc']");

	/**
	 * Method to verify Import in E2E scenario
	 *
	 * @return
	 */
	public boolean importLegalEntity( )
	{
		boolean textFlag = false;
		boolean summaryData_flag = false;
		boolean flagAlreadyImported = false;
		boolean finalFlag = false;
		String oracleERP_Text = String.valueOf(getFileReadPath().get(0));

		navigateToLegalEntity();
		wait.waitForElementDisplayed(summaryPageTitle);

		int totalPageCount = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int j = 1 ; j <= totalPageCount ; j++ )
		{
			Optional<WebElement> data = dataPresent(oracleERP_Text, summaryTableLegalEntity);

			if ( data.isPresent() )
			{
				VertexLogger.log(oracleERP_Text + " is already imported to Taxlink.. Verified in Summary table!");
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
					js.executeScript("arguments[0].scrollIntoView();", nextArrowOnSummaryTable);
				}
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		if ( flagAlreadyImported )
		{
			summaryData_flag = true;
		}
		else
		{
			click.clickElement(importButtonOnSummaryPage);

			expWait.until(ExpectedConditions.elementToBeClickable(importSelectAllCheckBox));

			int importRec_count = Integer.parseInt(importTotalPageCount.getText());
			for ( int i = 0 ; i < importRec_count ; i++ )
			{
				Optional<WebElement> ele = importListCheckBox
					.stream()
					.filter(e -> e
						.findElement(leIdentifier)
						.getText()
						.contains(oracleERP_Text))
					.findFirst();

				if ( ele.isPresent() )
				{
					textFlag = true;
					VertexLogger.log(oracleERP_Text + " :is present in Import pop up");

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
		}

		int totalRec_count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int j = 1 ; j <= totalRec_count ; j++ )
		{
			expWait.until(ExpectedConditions.elementToBeClickable(editButton));
			Optional<WebElement> data = dataPresent(oracleERP_Text, summaryTableLegalEntity);

			if ( data.isPresent() )
			{
				summaryData_flag = true;
				VertexLogger.log(data
									 .get()
									 .getText() + " :Data is present in Summary Table");

				break;
			}
			else
			{
				click.clickElement(nextArrow);
			}
		}

		try
		{
			deleteFile();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}

		if ( summaryData_flag && textFlag )
		{
			finalFlag = true;
		}

		return finalFlag;
	}
}
