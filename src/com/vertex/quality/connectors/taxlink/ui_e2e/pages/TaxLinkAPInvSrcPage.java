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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * This class contains all the methods to verify the E2E scenario of AP Invoice Source tab
 *
 * @author Shilpi.Verma
 */
public class TaxLinkAPInvSrcPage extends TaxLinkBasePage
{
	public TaxLinkAPInvSrcPage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/APInvoceSource']")
	private WebElement apInvoiceSourceTab;

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id = 'lookupCodeDesc']")
	private List<WebElement> summaryTableAPInvoiceSource;


	By leIdentifier = By.xpath(".//parent::div/parent::div/following-sibling::div[@col-id='lookupCodeDesc']");

	/**
	 * Method for navigation to Legal Entity tab
	 */
	public void navigateToAPInvoiceSource( )
	{
		clickOnDashboardDropdown();
		wait.waitForElementDisplayed(apInvoiceSourceTab);
		click.clickElement(apInvoiceSourceTab);
	}

	/**
	 * Method to verify Import in E2E scenario
	 *
	 * @return
	 */
	public boolean importAPInvSrc( )
	{
		boolean flag = false;
		boolean textFlag = false;
		boolean summaryData_flag = false;

		String oracleERP_Text = String.valueOf(getFileReadPath().get(0));

		navigateToAPInvoiceSource();
		wait.waitForElementDisplayed(summaryPageTitle);

		click.clickElement(importButtonOnSummaryPage);

		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.elementToBeClickable(importSelectAllCheckBox));

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

				wait.until(ExpectedConditions.textToBePresentInElement(popUpForImport,
					"Are you sure you want to save these records?"));

				click.clickElement(savePopUpImportButton);
				wait.until(ExpectedConditions.invisibilityOf(savePopUpImportButton));
				wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

				break;
			}
			else
			{
				click.clickElement(importNextArrow);
			}
		}

		int totalRec_count = Integer.parseInt(totalPageCountSummaryTable.getText());
		for ( int j = 1 ; j <= totalRec_count ; j++ )
		{
			wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
			wait.until(ExpectedConditions.elementToBeClickable(editButton));
			Optional<WebElement> data = dataPresent(oracleERP_Text, summaryTableAPInvoiceSource);

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
			Files.delete(Paths.get(String.valueOf(getFileReadPath().get(1))));
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}

		if ( summaryData_flag && textFlag )
		{
			flag = true;
		}

		return flag;
	}
}
