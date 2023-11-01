package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Discount Codes page actions/methods
 *
 * @author Saidulu Kodadala
 */
public class AcumaticaDiscountCodesPage extends AcumaticaBasePage
{
	protected By ADD_NEW_RECORD = By.cssSelector("[id*='ctl00_phDS_ds_ToolBar_ul'] [icon='RecordAdd']");
	protected By DISCOUNT_CODE = By.id("ctl00_phL_grid_em");
	protected By DESCRIPTION = By.id("ctl00_phL_grid_ei");

	public AcumaticaDiscountCodesPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * click on add new record
	 */
	public void clickAddNewRecord( )
	{
		wait.waitForElementDisplayed(ADD_NEW_RECORD);
		click.clickElement(ADD_NEW_RECORD);
		waitForPageLoad();
	}

	/**
	 * Enter discount code
	 */
	public void setDiscountCode( String discountCode )
	{
		wait.waitForElementDisplayed(DISCOUNT_CODE);
		text.enterText(DISCOUNT_CODE, discountCode);
		waitForPageLoad();
		text.pressTab(DISCOUNT_CODE);
	}

	/**
	 * Enter description
	 */
	public void setDescription( String description )
	{
		wait.waitForElementDisplayed(DESCRIPTION);
		text.enterText(DESCRIPTION, description);
		waitForPageLoad();
		text.pressTab(DESCRIPTION);
	}
}
