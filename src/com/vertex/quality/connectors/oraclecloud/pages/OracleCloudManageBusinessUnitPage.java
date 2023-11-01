package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class contains all the methods to verify Oracle ERP part of E2E scenario for business unit
 *
 * @author mgaikwad
 */

public class OracleCloudManageBusinessUnitPage extends OracleCloudBasePage
{

	public OracleCloudManageBusinessUnitPage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@title = 'Expand Search']")
	private WebElement searchLink;

	@FindBy(xpath = "//label[contains(text(), 'Name')]/preceding-sibling::input")
	private WebElement nameSearchField;

	@FindBy(xpath = "//tr/td[2]/div/table/tbody/tr/td[1]/span/a")
	private WebElement filteredResultsBu;

	@FindBy(xpath = "//button[contains(text(),'Search')]")
	private WebElement searchButtonOnBu;

	WebDriverWait expWait = new WebDriverWait(driver, 150);

	/**
	 * Method to search BU in OracleERP
	 *
	 * @return
	 */
	public String searchBusinessUnit( )
	{
		expWait.until(ExpectedConditions.visibilityOfAllElements(nameSearchField));
		text.enterText(nameSearchField, "VTX_US_BU");
		click.clickElement(searchButtonOnBu);
		wait.waitForElementDisplayed(filteredResultsBu);

		jsWaiter.sleep(10000);
		String buTest = filteredResultsBu.getText();
		if ( buTest.equals("VTX_US_BU") )
		{
			VertexLogger.log("Selected Business Unit Name for import from Oracle ERP is : " + buTest);
		}
		return buTest;
	}
}
