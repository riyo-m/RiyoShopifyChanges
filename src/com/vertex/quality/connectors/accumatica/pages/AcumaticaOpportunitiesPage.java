package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Opportunities page actions / methods.
 *
 * @author Saidulu Kodadala
 */
public class AcumaticaOpportunitiesPage extends AcumaticaBasePage
{
	protected By NEW_RECORD_PLUS_ICON = By.cssSelector("[icon='AddNew']");
	protected By OPPORTUNITIES_ID = By.id("ctl00_phF_form_edOpportunityID_text");
	protected By STATUS = By.xpath("//input//following-sibling::span[text()='New']");
	protected By REASON = By.xpath("//input//following-sibling::span[contains(text(),'Assign')]");
	protected By STAGE = By.xpath("//input//following-sibling::span[contains(text(),'Prospect')]");
	protected By SUBJECT = By.id("ctl00_phF_form_edOpportunityName");
	protected By CLASS_ID = By.id("ctl00_phF_form_edCROpportunityClassID_text");
	protected By BRANCH = By.id("ctl00_phG_tab_t0_edBranchID_text");
	protected By TAX_ZONE_ID = By.id("ctl00_phG_tab_t0_TaxZoneID_text");
	protected By BUSINESS_ACCOUNT = By.id("ctl00_phF_form_edBAccountID_text");
	protected By ADDRESS_LINE1 = By.id("ctl00_phG_tab_t1_edOpportunity_Address_edAddressLine1");
	protected By ADDRESS_LINE2 = By.id("ctl00_phG_tab_t1_edOpportunity_Address_edAddressLine2");
	protected By CITY = By.id("ctl00_phG_tab_t1_edOpportunity_Address_edCity");
	protected By COUNTRY = By.id("ctl00_phG_tab_t1_edOpportunity_Address_edCountryID_text");
	protected By STATE = By.id("ctl00_phG_tab_t1_edOpportunity_Address_edState_text");
	protected By ZIPCODE = By.id("ctl00_phG_tab_t1_edOpportunity_Address_edPostalCode");
	protected By INVENTORY = By.id("_ctl00_phG_tab_t5_ProductsGrid_lv0_edInventoryID_text");
	protected By QUANTITY = By.id("ctl00_phG_tab_t5_ProductsGrid_en");
	protected By UNIT_PRICE = By.id("ctl00_phG_tab_t5_ProductsGrid_en");
	protected By AMOUNT = By.id("ctl00_phF_form_edCuryAmount");
	protected By TOTAL = By.id("ctl00_phF_form_edCuryProductsAmount");
	protected By DISCOUNT = By.id("ctl00_phF_form_edCuryDiscTot");

	public AcumaticaOpportunitiesPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * click on 'New Record Icon'
	 */
	public void clickOnNewRecordIcon( )
	{
		wait.waitForElementDisplayed(NEW_RECORD_PLUS_ICON);
		click.clickElement(NEW_RECORD_PLUS_ICON);
		waitForPageLoad();
	}

	/**
	 * Get opportunities id
	 *
	 * @return
	 */
	public String getOpportunitiesId( )
	{
		wait.waitForElementDisplayed(OPPORTUNITIES_ID);
		String opportunitiesId = attribute.getElementAttribute(OPPORTUNITIES_ID, "value");
		waitForPageLoad();
		return opportunitiesId;
	}

	/**
	 * Get Status
	 *
	 * @return
	 */
	public String getStatus( )
	{
		wait.waitForElementDisplayed(STATUS);
		String status = attribute.getElementAttribute(STATUS, "value");
		waitForPageLoad();
		return status;
	}

	/**
	 * Get Reason
	 *
	 * @return
	 */
	public String getReason( )
	{
		wait.waitForElementDisplayed(REASON);
		String reason = attribute.getElementAttribute(REASON, "value");
		waitForPageLoad();
		return reason;
	}

	/**
	 * Get Stage
	 *
	 * @return
	 */
	public String getStage( )
	{
		wait.waitForElementDisplayed(STAGE);
		String stage = attribute.getElementAttribute(STAGE, "value");
		waitForPageLoad();
		return stage;
	}

	/**
	 * Set subject
	 */
	public void setSubject( String subject )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(SUBJECT);
		text.enterText(SUBJECT, subject);
		waitForPageLoad();
	}

	/**
	 * Set class id
	 */
	public void setClassId( String classId )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(CLASS_ID);
		text.enterText(CLASS_ID, classId);
		waitForPageLoad();
	}

	/**
	 * Get class id
	 */
	public String getClassId( )
	{
		waitForPageLoad();
		String classId = attribute.getElementAttribute(CLASS_ID, "value");
		waitForPageLoad();
		return classId;
	}

	/**
	 * set branch
	 */
	public void setBranch( String branch )
	{
		waitForPageLoad();
		text.enterText(BRANCH, branch);
		waitForPageLoad();
	}

	/**
	 * Get branch
	 */
	public String getBranch( )
	{
		waitForPageLoad();
		String branch = attribute.getElementAttribute(BRANCH, "value");
		waitForPageLoad();
		return branch;
	}

	/**
	 * Get Tax Zone Id
	 */
	public String getTaxZoneId( )
	{
		waitForPageLoad();
		String taxZoneId = attribute.getElementAttribute(TAX_ZONE_ID, "value");
		waitForPageLoad();
		return taxZoneId;
	}

	/**
	 * Get Tax Zone Id
	 */
	public void setTaxZoneId( String taxZoneId )
	{
		waitForPageLoad();
		text.enterText(TAX_ZONE_ID, taxZoneId);
		waitForPageLoad();
	}

	/**
	 * Set business account
	 */
	public void setBusinessAccount( String businessAccount )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(BUSINESS_ACCOUNT);
		text.enterText(BUSINESS_ACCOUNT, businessAccount);
		waitForPageLoad();
	}

	/**
	 * Get Address Line1
	 */
	public String getAddressLine1( )
	{
		waitForPageLoad();
		String addressLine1 = attribute.getElementAttribute(ADDRESS_LINE1, "value");
		waitForPageLoad();
		return addressLine1;
	}

	/**
	 * Get Address Line2
	 */
	public String getAddressLine2( )
	{
		waitForPageLoad();
		String addressLine2 = attribute.getElementAttribute(ADDRESS_LINE2, "value");
		waitForPageLoad();
		return addressLine2;
	}

	/**
	 * Get city
	 */
	public String getCity( )
	{
		waitForPageLoad();
		String city = attribute.getElementAttribute(CITY, "value");
		waitForPageLoad();
		return city;
	}

	/**
	 * Get country
	 */
	public String getCountry( )
	{
		waitForPageLoad();
		String country = attribute.getElementAttribute(COUNTRY, "value");
		waitForPageLoad();
		return country;
	}

	/**
	 * Get state
	 */
	public String getState( )
	{
		waitForPageLoad();
		String state = attribute.getElementAttribute(STATE, "value");
		waitForPageLoad();
		return state;
	}

	/**
	 * Get zipCode
	 */
	public String getZipCode( )
	{
		waitForPageLoad();
		String zipCode = attribute.getElementAttribute(ZIPCODE, "value");
		waitForPageLoad();
		return zipCode;
	}

	/**
	 * Get Amount
	 */
	public String getAmount( )
	{
		waitForPageLoad();
		String amount = attribute.getElementAttribute(AMOUNT, "value");
		waitForPageLoad();
		return amount;
	}

	/**
	 * Get Total
	 */
	public String getTotal( )
	{
		waitForPageLoad();
		String total = attribute.getElementAttribute(TOTAL, "value");
		waitForPageLoad();
		return total;
	}

	/**
	 * Get Discount
	 */
	public String getDiscount( )
	{
		waitForPageLoad();
		String discount = attribute.getElementAttribute(DISCOUNT, "value");
		waitForPageLoad();
		return discount;
	}

	/**
	 * Set Inventory Id
	 */
	public void setInventoryId( String inventory )
	{
		text.enterText(INVENTORY, inventory);
		text.pressTab(INVENTORY);
	}

	/**
	 * Set Quantity in products tab
	 *
	 * @param quantity
	 */
	public void setQuantity( String quantity )
	{
		text.enterText(QUANTITY, quantity);
		text.pressTab(QUANTITY);
	}

	/**
	 * Set Unit Price in products tab
	 *
	 * @param unitPrice
	 */
	public void setUnitPrice( String unitPrice )
	{
		text.enterText(UNIT_PRICE, unitPrice);
		text.pressTab(UNIT_PRICE);
	}
}
