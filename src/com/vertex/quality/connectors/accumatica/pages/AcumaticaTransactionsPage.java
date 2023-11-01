package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author Saidulu Kodadala
 * Transactions page actions/ methods
 */
public class AcumaticaTransactionsPage extends AcumaticaBasePage
{
	protected By NEW_RECORD_PLUS_ICON = By.cssSelector("[icon='AddNew']");
	protected By TRANS_TYPE = By.id("ctl00_phF_form_edAdjTranType_text");
	protected By STATUS = By.xpath("//input//following-sibling::span[text()='Balanced']");
	protected By REFERENCE_NBR = By.xpath("ctl00_phF_form_edAdjRefNbr_text");
	protected By DISCRITION = By.id("ctl00_phF_form_edTranDesc");
	protected By CASH_ACCOUNT = By.id("ctl00_phF_form_edCashAccountID_text");
	protected By ENTRY_TYPE = By.id("ctl00_phF_form_edEntryTypeID_text");
	protected By DOCUMENT_REF = By.id("ctl00_phF_form_edExtRefNbr");
	protected By BRANCH = By.id("_ctl00_phG_tab_t0_grid_lv0_edBranchID_text");
	protected By INVENTORY = By.id("_ctl00_phG_tab_t0_grid_lv0_edInventoryID_text");
	protected By PRICE = By.id("_ctl00_phG_tab_t0_grid_lv0_edCuryUnitPrice");
	protected By TAX_ZONE = By.id("ctl00_phG_tab_t2_edTaxZoneID_text");
	protected By AMOUNT = By.id("ctl00_phF_form_edCuryProductsAmount");
	protected By TAX_TOTAL = By.id("ctl00_phF_form_edCuryTaxTotal");

	public AcumaticaTransactionsPage( WebDriver driver )
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
	 * Get Trans Type
	 *
	 * @return
	 */
	public String getTansType( )
	{
		wait.waitForElementDisplayed(TRANS_TYPE);
		String transType = attribute.getElementAttribute(TRANS_TYPE, "value");
		waitForPageLoad();
		return transType;
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
	 * Get Reference Nbr.
	 *
	 * @return
	 */
	public String getReferenceNbr( )
	{
		wait.waitForElementDisplayed(REFERENCE_NBR);
		String referenceNbr = attribute.getElementAttribute(REFERENCE_NBR, "value");
		waitForPageLoad();
		return referenceNbr;
	}

	/**
	 * Set discritption
	 */
	public void setDiscritption( String discritption )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(DISCRITION);
		text.enterText(DISCRITION, discritption);
		waitForPageLoad();
	}

	/**
	 * Set cashAccount
	 */
	public void setCashAccount( String cashAccount )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(CASH_ACCOUNT);
		text.enterText(CASH_ACCOUNT, cashAccount);
		waitForPageLoad();
	}

	/**
	 * Get cashAccount
	 */
	public String getCashAccount( )
	{
		waitForPageLoad();
		String cashAccount = attribute.getElementAttribute(CASH_ACCOUNT, "value");
		waitForPageLoad();
		return cashAccount;
	}

	/**
	 * set document ref
	 */
	public void setDocumentRef( String documentRef )
	{
		waitForPageLoad();
		text.enterText(DOCUMENT_REF, documentRef);
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
	 * Get Tax Zone
	 */
	public String getTaxZone( )
	{
		waitForPageLoad();
		String taxZone = attribute.getElementAttribute(TAX_ZONE, "value");
		waitForPageLoad();
		return taxZone;
	}

	/**
	 * Set Entry Type
	 */
	public void setEntryType( String entryType )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(ENTRY_TYPE);
		text.enterText(ENTRY_TYPE, entryType);
		waitForPageLoad();
	}

	/**
	 * Set Entry Type
	 */
	public String getEntryType( )
	{
		waitForPageLoad();
		String entryType = attribute.getElementAttribute(ENTRY_TYPE, "value");
		waitForPageLoad();
		return entryType;
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
	 * Get  Tax Total
	 */
	public String getTotal( )
	{
		waitForPageLoad();
		String total = attribute.getElementAttribute(TAX_TOTAL, "value");
		waitForPageLoad();
		return total;
	}

	/**
	 * Set inventory
	 *
	 * @param inventory
	 */
	public void setInventoryId( String inventory )
	{
		text.enterText(INVENTORY, inventory);
		text.pressTab(INVENTORY);
	}

	/**
	 * Set Price in products tab
	 *
	 * @param price
	 */
	public void setPrice( String price )
	{
		text.enterText(PRICE, price);
		text.pressTab(PRICE);
	}
}
