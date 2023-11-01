package com.vertex.quality.connectors.taxlink.ui_e2e.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui_e2e.base.TaxLinkBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Optional;

/**
 * This class contains all the methods to test the pages in AP Tax Calculation Exclusions tab in TaxLink UI
 *
 * @author mgaikwad
 */

public class TaxLinkApTaxCalcExclPage extends TaxLinkBasePage
{
	public TaxLinkApTaxCalcExclPage( WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath = "//label[contains(text(), 'Business Unit')]/following-sibling::select")
	private WebElement businessUnit;

	@FindBy(xpath = "//label[contains(text(), 'AP Invoice Source')]/following-sibling::select")
	private WebElement apInvSrc;

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id = '0']")
	private List<WebElement> summaryTableAPTaxCalcExcl;

	@FindBy(xpath = "//div[@ref='eOverlayWrapper']/span")
	public WebElement noRowsToShow;

	@FindBy(xpath = "//input[@name='enableFlag']")
	protected WebElement enabledFlag;

	private By column_BU = By.xpath(".//following-sibling::div");
	private By column_AP = By.xpath(".//following-sibling::div[2]");
	private By column_Enabled = By.xpath(".//following-sibling::div[4]");
	private By editButtonApTaxCalcExcl = By.xpath(
		".//ancestor::div/following-sibling::div/div/div/div/div/div/div/div/button");


	/**
	 * This method is to search data in Summary table based on the parameters passed
	 *
	 * @param businessUnit
	 * @param apInvoice
	 *
	 * @return boolean
	 */
	public boolean searchData( String businessUnit, String apInvoice )
	{
		boolean finalFlag = false;

		click.clickElement(saveButton);
		expWait.until(ExpectedConditions.visibilityOfAllElements(summaryTableAPTaxCalcExcl));

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = summaryTableAPTaxCalcExcl
				.stream()
				.filter(ele -> ele
								   .findElement(column_BU)
								   .getText()
								   .equals(businessUnit) && ele
								   .findElement(column_AP)
								   .getText()
								   .equals(apInvoice) && ele
								   .findElement(column_Enabled)
								   .getText()
								   .equals("Y"))
				.findFirst();

			if ( data.isPresent() )
			{
				VertexLogger.log(
					"Data present with Business Unit: " + businessUnit + " AP Invoice Source: " + apInvoice +
					" Column_Enabled: Y");

				finalFlag = true;

				break;
			}
			else
			{
				click.clickElement(nextArrow);
			}
		}
		return finalFlag;
	}

	/**
	 * This method is to add the BU and AP invoice Source to create a record for AP Tax calc exclusion,
	 * and verify it in the Summary Table
	 *
	 * @return boolean
	 */
	public boolean addAPTaxCalcExcl( )
	{
		boolean finalFlag = false, businessUnit_flag = false, apInvoice_flag = false, summary_flag = false;

		navigateToAPTaxCalcExcl();
		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		click.clickElement(addButton);
		jsWaiter.sleep(5000);

		dropdown.selectDropdownByDisplayName(businessUnit, "VTX_US_BU");
		if ( dropdown
			.getDropdownSelectedOption(businessUnit)
			.getText()
			.equals("VTX_US_BU") )
		{
			businessUnit_flag = true;
		}

		jsWaiter.sleep(5000);
		dropdown.selectDropdownByDisplayName(apInvSrc, "Manual Invoice Entry");
		if ( dropdown
			.getDropdownSelectedOption(apInvSrc)
			.getText()
			.equals("Manual Invoice Entry") )
		{
			apInvoice_flag = true;
		}

		if ( searchData("VTX_US_BU", "Manual Invoice Entry") )
		{
			summary_flag = true;
		}

		if ( businessUnit_flag && apInvoice_flag && summary_flag )
		{
			finalFlag = true;
		}
		return finalFlag;
	}

	/**
	 * Method to disable all Manual Invoices for "VTX_US_BU"
	 * for AP Tax Calculation Exclusions tab in TaxLink UI
	 * reason being rules won't get applied to a transaction until and unless
	 * these records are disabled
	 *
	 * @return boolean
	 */
	public boolean disableApTaxCalcExcl( )
	{
		boolean finalFlag = false;

		navigateToAPTaxCalcExcl();
		wait.waitForElementDisplayed(summaryPageTitle);
		try
		{
			if ( noRowsToShow.isDisplayed() )
			{
				VertexLogger.log("No rows present!!");
				finalFlag = true;
			}
		}
		catch ( Exception e )
		{
			expWait.until(ExpectedConditions.visibilityOfAllElements(summaryTableAPTaxCalcExcl));

			int count = Integer.parseInt(totalPageCountSummaryTable.getText());
			for ( int i = 1 ; i <= count ; i++ )
			{
				for ( int j = 1 ; j <= summaryTableAPTaxCalcExcl.size() ; j++ )
				{
					if ( summaryTableAPTaxCalcExcl
						.stream()
						.anyMatch(ele -> ele
											 .getText()
											 .equals("RI IFF Farms Ltd.") || ele
											 .findElement(column_BU)
											 .getText()
											 .equals("VTX_US_BU") || ele
											 .findElement(column_AP)
											 .getText()
											 .equals("Manual invoice entry") || ele
											 .findElement(column_Enabled)
											 .getText()
											 .equals("Y") || ele
											 .getText()
											 .equals("All Legal Entities") || ele
											 .findElement(column_BU)
											 .getText()
											 .equals("All Business Units") || ele
											 .findElement(column_AP)
											 .getText()
											 .equals("All AP Invoice Sources")) )
					{
						Optional<WebElement> data = summaryTableAPTaxCalcExcl
							.stream()
							.filter(ele -> ele
											   .getText()
											   .equals("RI IFF Farms Ltd.") || ele
											   .findElement(column_BU)
											   .getText()
											   .equals("VTX_US_BU") || ele
											   .findElement(column_AP)
											   .getText()
											   .equals("Manual invoice entry") || ele
											   .findElement(column_Enabled)
											   .getText()
											   .equals("Y") || ele
											   .getText()
											   .equals("All Legal Entities") || ele
											   .findElement(column_BU)
											   .getText()
											   .equals("All Business Units") || ele
											   .findElement(column_AP)
											   .getText()
											   .equals("All AP Invoice Sources"))
							.findAny();
						{
							if ( data.isPresent() )
							{
								data
									.get()
									.findElement(editButtonApTaxCalcExcl)
									.click();
								VertexLogger.log("Clicked on Edit button");
								if ( enabledFlag.isSelected() )
								{
									click.clickElement(enabledFlag);
									click.clickElement(saveButton);
									VertexLogger.log("Disabled the AP Tax Calc exclusion record!! ");
									finalFlag = true;
									jsWaiter.sleep(2000);
								}
							}
							else
							{
								click.clickElement(nextArrow);
							}
						}
					}
				}
			}
		}
		return finalFlag;
	}
}
