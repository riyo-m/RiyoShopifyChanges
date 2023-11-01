package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaPostSignOnPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author saidulu kodadala
 * Customer classes page actions/methods
 */
public class AcumaticaCustomerClassesPage extends AcumaticaPostSignOnPage
{
	protected By NEW_RECORD_PLUS_ICON = By.cssSelector("[icon='AddNew']");
	protected By CUSTOMER_CLASS_ID = By.id("ctl00_phF_form_edCustomerClassID_text");
	protected By CUSTOMER_CLASS_DESCRIPTION = By.id("ctl00_phF_form_edDescr");
	protected By COUNTRY = By.id("ctl00_phG_tab_t0_edCountryID_text");
	protected By TAX_ZONE_ID = By.id("ctl00_phG_tab_t0_edTaxZoneID_text");
	protected By SALESPERSON_ID = By.id("ctl00_phG_tab_t0_edSalesPersonID_text");
	protected By SHIP_VIA = By.id("ctl00_phG_tab_t0_edShipVia_text");
	protected By CREDIT_VERIFICATION_DROPDOWN = By.id("ctl00_phG_tab_t0_edCreditRule");
	protected static final String CREDIT_VERIFICATION_OPTION = "//td[text()='%s']";
	protected By CUSTOMER_ID_SEARCH_ICON = By.cssSelector("[id*='phF_form_edCustomerClassID'] [icon='SelectorN']");
	protected By CUSTOMER_ID_SEARCH = By.xpath(
		"//*[@id='ctl00_phF_form_edCustomerClassID_pnl_gr_scrollDiv']//td[contains(text(),'AUTOMATIONCLASSNEW')]");
	protected By CUSTOMER_SEARCH_BAR = By.id("ctl00_phF_form_edCustomerClassID_pnl_tlb_fb");

	public AcumaticaCustomerClassesPage( WebDriver driver )
	{
		super(driver);
	}

	AcumaticaCommonPage common = new AcumaticaCommonPage(driver);

	/***
	 * click on 'Add New Record' icon
	 */
	public void clickAddNewRecord( )
	{
		wait.waitForElementEnabled(CUSTOMER_CLASS_ID);
		click.clickElement(NEW_RECORD_PLUS_ICON);
		waitForPageLoad();
		wait.waitForElementDisplayed(CUSTOMER_CLASS_ID);
	}

	/***
	 * Enter customer class id
	 * @param classId
	 */
	public void enterClassId( final String classId )
	{
		text.setTextFieldCarefully(CUSTOMER_CLASS_ID, classId, false);
		//fixme is this checking that the element's value attribute & displayed text are the same? why?
		//	shouldn't this be a different function, or at least used in a return value?
		String class_Id = attribute.getElementAttribute(CUSTOMER_CLASS_ID, "value");
		text.verifyText(CUSTOMER_CLASS_ID, class_Id, "Class ID");
	}

	/***
	 * Enter customer class description
	 * @param description
	 */
	public void enterDescription( final String description )
	{
		text.setTextFieldCarefully(CUSTOMER_CLASS_DESCRIPTION, description);
	}

	/***
	 * Enter country
	 * @param country
	 */
	public void enterCountry( final String country )
	{
		text.setTextFieldCarefully(COUNTRY, country);
	}

	/***
	 * Enter tax zone id
	 * @param taxZoneId
	 */
	public void taxZoneId( final String taxZoneId )
	{
		text.setTextFieldCarefully(TAX_ZONE_ID, taxZoneId);
	}

	/***
	 * Clear sales person id text
	 */
	public void clearSalesPersonId( )
	{
		wait.waitForElementEnabled(SALESPERSON_ID);
		text.clearText(SALESPERSON_ID);
		text.pressTab(SALESPERSON_ID);
		waitForPageLoad();
	}

	/***
	 * Enter ship via
	 * @param shipVia
	 */
	public void enterShipVia( final String shipVia )
	{
		text.setTextFieldCarefully(SHIP_VIA, shipVia);
	}

	/***
	 * Select credit verification option
	 * @param option
	 */
	public void selectCreditVerification( final String option )
	{
		By CREDIT = By.xpath(String.format(CREDIT_VERIFICATION_OPTION, option));
		click.clickElementCarefully(CREDIT_VERIFICATION_DROPDOWN);
		click.clickElementCarefully(CREDIT);
	}

	/***
	 * Switch to tab
	 * @param tabName
	 */
	public void switchToTab( final String tabName )
	{
		By TAB_LOCATOR = By.xpath(String.format(CREDIT_VERIFICATION_OPTION, tabName));

		wait.waitForElementEnabled(TAB_LOCATOR);
		click.javascriptClick(element.getWebElement(TAB_LOCATOR));
		waitForPageLoad();

		click.clickElementCarefully(TAB_LOCATOR);
	}

	/***
	 * Enter customer class id and verify customer id
	 * @param customerId
	 */
	public void searchCustomerClassId( final String customerId )
	{
		text.setTextFieldCarefully(CUSTOMER_SEARCH_BAR, customerId);

		//fixme doesn' the wait just crash the test if it isn't displayed? what's the point here?
		wait.waitForElementDisplayed(CUSTOMER_ID_SEARCH);
		if ( element.isElementDisplayed(CUSTOMER_ID_SEARCH) )
		{
			VertexLogger.log(String.format("No result found for the role/text: \"%s\"", customerId));
		}
		else
		{
			VertexLogger.log(String.format("Search failed, Result found for the role/text: \"%s\"", customerId),
				VertexLogLevel.ERROR);
		}
	}

	/***
	 * Table validation (based on column names /row names)
	 * @return
	 */
	public int tableValidation( )
	{
		WebElement table = wait.waitForElementPresent(By.id("ctl00_phG_tab_t3_sp1_gridNS_dataT0"));
		List<WebElement> rows = wait.waitForAllElementsPresent(By.tagName("tr"), table);
		int i = 0;
		int j = 0;
		for ( WebElement row : rows )
		{
			List<WebElement> cells = row.findElements(By.tagName("td"));
			for ( WebElement cell : cells )
			{
				String expectedText = cell.getText();
				if ( expectedText.equalsIgnoreCase("INVOICE") )
				{
					j = i;
					break;
				}
			}
			i = i + 1;
		}
		return j;
	}

	/***
	 * Select invoice from mailing table
	 */
	public void selectInvoiceFromMailingTable( )
	{
		int count = this.tableValidation();
		for ( int i = 1 ; i <= 7 ; i++ )
		{
			if ( i == count )
			{
				By activeCheckBox = By.xpath("//table[contains(@id,'gridNS_dataT0')]/tbody/tr[" + i +
											 "]/td/*[@class='sprite-icon control-icon']");
				String ischecked = attribute.getElementAttribute(activeCheckBox, "icon");
				if ( ischecked.contains("Uncheck") )
				{
					wait.waitForElementEnabled(activeCheckBox);
					click.javascriptClick(element.getWebElement(activeCheckBox));
					waitForPageLoad();
				}
			}
			else
			{
				By activeCheckBox = By.xpath("//table[contains(@id,'gridNS_dataT0')]/tbody/tr[" + i +
											 "]/td/*[@class='sprite-icon control-icon']");
				wait.waitForElementDisplayed(activeCheckBox);
				String ischecked = attribute.getElementAttribute(activeCheckBox, "icon");
				if ( ischecked.contains("Check") )
				{
					wait.waitForElementEnabled(activeCheckBox);
					click.javascriptClick(element.getWebElement(activeCheckBox));
					waitForPageLoad();
				}
			}
		}
	}

	/***
	 * Making all methods into one
	 */ //fixme function name
	public void customerClassPageActions( final String classId, final String description, final String country,
		final String taxZoneId, final String shipVia, final String creditVerification, final String tabName )
	{
		this.enterClassId(classId);
		this.enterDescription(description);
		this.enterCountry(country);
		this.taxZoneId(taxZoneId);
		this.clearSalesPersonId();
		this.enterShipVia(shipVia);
		this.selectCreditVerification(creditVerification);
		this.switchToTab(tabName);
		this.selectInvoiceFromMailingTable();
		common.clickSaveButton();
		common.clickDeleteButton();
		window.switchToDefaultContent();
	}
}
