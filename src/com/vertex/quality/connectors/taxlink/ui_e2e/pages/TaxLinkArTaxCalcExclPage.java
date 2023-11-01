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
 * This class contains method to add a Manual Invoice for "VTX_US_BU"
 * for AR Tax Calculation Exclusions tab in TaxLink UI
 *
 * @author mgaikwad
 */

public class TaxLinkArTaxCalcExclPage extends TaxLinkBasePage
{
	public TaxLinkArTaxCalcExclPage( WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// AR Tax Exclusions Page locs
	@FindBy(xpath = "//*[@col-id = 'legalEntityName']")
	private List<WebElement> summaryTableArExclusions;

	@FindBy(xpath = "//h1[contains(text(), 'AR Tax Calculation Exclusions')]")
	private WebElement arTaxExclusionsPageHeaderLoc;

	@FindBy(xpath = "//button[contains(text(), 'ADD')]")
	private WebElement addArExclusionButton;

	@FindBy(xpath = "//div[@ref='eCenterContainer']/div/div[@col-id='legalEntityName']")
	private List<WebElement> legalEntityColLoc;

	@FindBy(xpath = "//div[@ref='eCenterContainer']/div/div[@col-id='businessUnitName']")
	private List<WebElement> busUnitColLoc;

	@FindBy(xpath = "//div[@ref='eCenterContainer']/div/div[@col-id='batchSourceName']")
	private List<WebElement> arTrxSrcColLoc;

	@FindBy(xpath = "//div[@ref='eCenterContainer']/div/div[@col-id='trxTypeName']")
	private List<WebElement> arTrxTypeColLoc;

	// Add AR Tax Exclusion Page locs
	@FindBy(xpath = "//h3[contains(text(), 'Add AR Tax Exclusion')]")
	private WebElement addArTaxExclusionPageHeaderLoc;

	@FindBy(xpath = "//label[contains(text(), 'Legal Entity')]/following-sibling::select")
	private WebElement legalEntityDropdownLoc;

	@FindBy(xpath = "//label[contains(text(), 'Business Unit')]/following-sibling::select")
	private WebElement busUnitDropdownLoc;

	@FindBy(xpath = "//label[contains(text(), 'AR Transaction Source')]/following-sibling::select")
	private WebElement arTrxSourceDropdownLoc;

	@FindBy(xpath = "//label[contains(text(), 'AR Transaction Type')]/following-sibling::select")
	private WebElement arTrxTypeDropdownLoc;

	@FindBy(xpath = "//*[@col-id='lookupCodeDesc']")
	private List<WebElement> summaryTableArTransactionSrc;

	private By transIdentifier = By.xpath(
		".//parent::div/parent::div/following-sibling::div[@col-id='lookupCodeDesc']");
	private By column_BU = By.xpath(".//following-sibling::div");
	private By column_ArTransSrc = By.xpath(".//following-sibling::div[2]");
	private By column_ArTransType = By.xpath(".//following-sibling::div[3]");
	private By column_Enabled = By.xpath(".//following-sibling::div[4]");


	/**
	 * This method is to add the BU and Ar transaction source & type to create a record for AR Tax calc exclusion,
	 * and verify it in the Summary Table
	 *
	 * @return boolean
	 */
	public boolean addARTaxCalcExcl( String businessUnit, String arTransSrc, String arTransType )
	{
		boolean finalFlag = false, businessUnit_flag = false, arTrxSource_flag = false, arTrxType_flag = false,
			summary_flag = false;

		navigateToArTaxExclusionsPage();
		expWait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		click.clickElement(addButton);
		wait.waitForElementDisplayed(busUnitDropdownLoc);
		dropdown.selectDropdownByDisplayName(busUnitDropdownLoc, businessUnit);
		if ( dropdown
			.getDropdownSelectedOption(busUnitDropdownLoc)
			.getText()
			.equals(businessUnit) )
		{
			businessUnit_flag = true;
		}

		jsWaiter.sleep(2000);
		dropdown.selectDropdownByDisplayName(arTrxSourceDropdownLoc, arTransSrc);
		if ( dropdown
			.getDropdownSelectedOption(arTrxSourceDropdownLoc)
			.getText()
			.equals(arTransSrc) )
		{
			arTrxSource_flag = true;
		}
		wait.waitForElementDisplayed(arTrxTypeDropdownLoc);
		dropdown.selectDropdownByDisplayName(arTrxTypeDropdownLoc, arTransType);
		if ( dropdown
			.getDropdownSelectedOption(arTrxTypeDropdownLoc)
			.getText()
			.equals(arTransType) )
		{
			arTrxType_flag = true;
		}

		if ( searchData(businessUnit, arTransSrc, arTransType) )
		{
			summary_flag = true;
		}

		if ( businessUnit_flag && arTrxSource_flag && arTrxType_flag && summary_flag )
		{
			finalFlag = true;
		}
		return finalFlag;
	}

	/**
	 * This method is to search data in Summary table based on the parameters passed
	 *
	 * @param businessUnit
	 * @param arTransSrc
	 * @param arTransType
	 *
	 * @return boolean
	 */
	public boolean searchData( String businessUnit, String arTransSrc, String arTransType )
	{
		boolean finalFlag = false;

		click.clickElement(saveButton);
		expWait.until(ExpectedConditions.visibilityOfAllElements(summaryTableArExclusions));

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int i = 1 ; i <= count ; i++ )
		{
			Optional<WebElement> data = summaryTableArExclusions
				.stream()
				.filter(ele -> ele
								   .findElement(column_BU)
								   .getText()
								   .equals(businessUnit) && ele
								   .findElement(column_ArTransSrc)
								   .getText()
								   .equals(arTransSrc) && ele
								   .findElement(column_ArTransType)
								   .getText()
								   .equals(arTransType) && ele
								   .findElement(column_Enabled)
								   .getText()
								   .equals("Y"))
				.findFirst();

			if ( data.isPresent() )
			{
				VertexLogger.log(
					"Data present with Business Unit: " + businessUnit + " AR Transaction Source: " + arTransSrc +
					" AR Transaction Type: " + arTransType + " Column_Enabled: Y");

				finalFlag = true;

				break;
			}
			else
			{
				click.clickElement(nextArrowOnSummaryTable);
			}
		}
		return finalFlag;
	}
}
