package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author saidulu kodadala
 * Stack item page actions/methods
 */
public class AcumaticaStockItemsPage extends AcumaticaBasePage
{
	protected By INVENTORY_ID = By.id("ctl00_phF_form_edInventoryCD_text");
	protected By DESCRIPTION = By.id("ctl00_phF_form_edDescr");
	protected By ITEM_CLASS = By.id("ctl00_phG_tab_t0_edItemClassID_text");
	protected By WARE_HOUSE_ID = By.id("ctl00_phG_tab_t0_edDfltSiteID_text");
	protected By DEFAULT_ISSUE_FROM = By.id("ctl00_phG_tab_t0_edDfltShipLocationID_text");
	protected By DEFAULT_RECEIPT_TO = By.id("ctl00_phG_tab_t0_edDfltReceiptLocationID_text");
	protected By PRICE_CLASS = By.id("ctl00_phG_tab_t2_edPriceClassID_text");
	protected By TYPE = By.xpath("//input[@id='ctl00_phG_tab_t0_edItemType_text']/../..");
	protected By TYPE_OPTION = By.xpath("//span[text()='%s']");
	protected By VALUATION_METHOD = By.xpath("//input[@id='ctl00_phG_tab_t0_edValMethod_text']/../..");
	protected By TABLE_TAB = By.xpath("//td[text()='%s']");

	public AcumaticaStockItemsPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Enter inventory id
	 */
	public void setInventoryId( String inventoryId )
	{
		wait.waitForElementDisplayed(INVENTORY_ID);
		text.selectAllAndInputText(INVENTORY_ID, inventoryId);
		text.pressTab(INVENTORY_ID);
	}

	/**
	 * Enter description
	 */
	public void setDescription( String description )
	{
		wait.waitForElementDisplayed(DESCRIPTION);
		text.selectAllAndInputText(DESCRIPTION, description);
		text.pressTab(DESCRIPTION);
	}

	/**
	 * Enter set item class
	 *
	 * @param itemClass
	 */
	public void setItemClass( String itemClass )
	{
		wait.waitForElementDisplayed(ITEM_CLASS);
		text.selectAllAndInputText(ITEM_CLASS, itemClass);
		text.pressTab(ITEM_CLASS);
	}

	/**
	 * Enter type
	 *
	 * @param type
	 */
	public void setType( String type )
	{
		wait.waitForElementDisplayed(TYPE);
		click.clickElement(ITEM_CLASS);
		wait.waitForElementDisplayed(TYPE_OPTION);
		click.clickElement(TYPE_OPTION);
	}

	/**
	 * Enter valuation method
	 *
	 * @param valuationOption
	 */
	public void setValuationMethod( String valuationOption )
	{
		wait.waitForElementDisplayed(VALUATION_METHOD);
		click.clickElement(VALUATION_METHOD);
		wait.waitForElementDisplayed(TYPE_OPTION);
		click.clickElement(TYPE_OPTION);
	}

	/**
	 * Enter ware house id
	 *
	 * @param wareHouseId
	 */
	public void setWareHouseId( String wareHouseId )
	{
		wait.waitForElementDisplayed(WARE_HOUSE_ID);
		text.enterText(WARE_HOUSE_ID, wareHouseId);
		text.pressTab(WARE_HOUSE_ID);
	}

	/**
	 * Enter default Issue from
	 *
	 * @param defaultIssueFrom
	 */
	public void setDefaultIssueFrom( String defaultIssueFrom )
	{
		wait.waitForElementDisplayed(DEFAULT_ISSUE_FROM);
		text.enterText(DEFAULT_ISSUE_FROM, defaultIssueFrom);
		text.pressTab(DEFAULT_ISSUE_FROM);
	}

	/**
	 * Enter default receipt to
	 *
	 * @param defaultReceiptTo
	 */
	public void setDefaultReceiptTo( String defaultReceiptTo )
	{
		wait.waitForElementDisplayed(DEFAULT_RECEIPT_TO);
		text.enterText(DEFAULT_RECEIPT_TO, defaultReceiptTo);
		text.pressTab(DEFAULT_RECEIPT_TO);
	}

	/**
	 * select table tab
	 *
	 * @param tab
	 */
	public void selectTableTab( String tab )
	{
		wait.waitForElementDisplayed(TABLE_TAB);
		click.clickElement(TABLE_TAB);
		waitForPageLoad();
	}

	/**
	 * clear price class
	 */
	public void clearPriceClass( )
	{
		wait.waitForElementDisplayed(PRICE_CLASS);
		text.clearText(PRICE_CLASS);
		waitForPageLoad();
	}
}
