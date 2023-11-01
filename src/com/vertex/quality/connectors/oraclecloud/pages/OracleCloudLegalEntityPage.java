package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * This class contains all the methods to verify Oracle ERP part of E2E scenario for legal Entity
 *
 * @author Shilpi.Verma
 */

public class OracleCloudLegalEntityPage extends OracleCloudBasePage
{

	public OracleCloudLegalEntityPage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@title = 'Expand Search']")
	private WebElement searchLink;

	@FindBy(xpath = "//label[contains(text(), 'Name')]/preceding-sibling::input")
	private WebElement nameSearchField;

	@FindBy(xpath = "//table[@summary = 'Search Results']/tbody/tr/td[2]/div/table/tbody/tr/td/span/a")
	private WebElement searchResultRow;

	@FindBy(xpath = "//button[contains(text(), 'Search')]")
	private WebElement searchButton;

	/**
	 * Method to search Legal Entity in OracleERP
	 * @return
	 */
	public String searchLegalEntity( )
	{

		wait.waitForElementEnabled(searchLink);
		click.clickElement(searchLink);
		wait.waitForElementEnabled(nameSearchField);

		text.enterText(nameSearchField, "RI IFF");
		click.clickElement(searchButton);
		jsWaiter.sleep(3000);

		String leTest = searchResultRow.getText();

		return leTest;
	}
}
