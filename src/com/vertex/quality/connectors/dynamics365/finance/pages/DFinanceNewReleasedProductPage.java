package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

/**
 * New Released Product page common methods and object declaration page
 *
 * @author Shiva Mothkula
 */
public class DFinanceNewReleasedProductPage extends DFinanceBasePage
{
	protected By PRODUCT_TYPE = By.name("Types_ProductType");
	protected By PRODUCT_NUMBER = By.name("Identification_ProductNumber");
	protected By PRODUCT_NAME = By.name("Identification_Name");

	protected By ITEM_MODEL_GROUP = By.name("ModelGroupId");
	protected By ITEM_GROUP = By.name("ItemGroupId");
	protected By STORAGE_DIMENSION_GROUP = By.name("StorageDimensionGroup_Name");
	protected By TRACKING_DIMENSION_GROUP = By.name("TrackingDimensionGroup_Name");

	protected By INVENTORY_UNIT = By.name("InventUnitId");
	protected By PURCHASE_UNIT = By.name("PurchUnitId");
	protected By SALES_UNIT = By.name("SalesUnitId");
	protected By BOM_UNIT = By.name("BOMUnitId");

	protected By SALES_TAX_ITEM_GROUP_ID = By.name("SalesTaxItemGroupId");
	protected By PURCHASE_TAX_ITEM_GROUP_ID = By.name("PurchTaxItemGroupId");

	protected By OK_BUTTON = By.name("OKButton");

	public DFinanceNewReleasedProductPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Get Product type
	 *
	 * @return
	 */
	public String getProductType( )
	{
		wait.waitForElementEnabled(PRODUCT_TYPE);
		String productType = attribute.getElementAttribute(PRODUCT_TYPE, "value");

		if ( productType != null )
		{
			productType = productType.trim();
		}
		return productType;
	}

	/**
	 * Set Product Number
	 *
	 * @param productNumber
	 */
	public void setProductNumber( String productNumber )
	{
		wait.waitForElementEnabled(PRODUCT_NUMBER);
		text.enterText(PRODUCT_NUMBER, productNumber);
		waitForPageLoad();
	}

	/**
	 * Set Product Name
	 *
	 * @param productName
	 */
	public void setProductName( String productName )
	{
		wait.waitForElementEnabled(PRODUCT_NAME);
		text.enterText(PRODUCT_NAME, productName);
		waitForPageLoad();
	}

	/**
	 * Set Item Model Group
	 *
	 * @param itemModelGroup
	 */
	public void setItemModelGroup( String itemModelGroup )
	{
		wait.waitForElementEnabled(ITEM_MODEL_GROUP);
		text.enterText(ITEM_MODEL_GROUP, itemModelGroup + Keys.TAB);
		waitForPageLoad();
	}

	/**
	 * Set Item Group
	 *
	 * @param itemGroup
	 */
	public void setItemGroup( String itemGroup )
	{
		wait.waitForElementEnabled(ITEM_GROUP);
		text.enterText(ITEM_GROUP, itemGroup + Keys.TAB);
		waitForPageLoad();
	}

	/**
	 * Set STORAGE_DIMENSION_GROUP
	 *
	 * @param storageDimensionGroup
	 */
	public void setStorageDimensionGroup( String storageDimensionGroup )
	{
		wait.waitForElementEnabled(STORAGE_DIMENSION_GROUP);
		text.enterText(STORAGE_DIMENSION_GROUP, storageDimensionGroup + Keys.TAB);
		waitForPageLoad();
	}

	/**
	 * Set TRACKING_DIMENSION_GROUP
	 *
	 * @param trackingDimensionGroup
	 */
	public void setTrackingDimensionGroup( String trackingDimensionGroup )
	{
		wait.waitForElementEnabled(TRACKING_DIMENSION_GROUP);
		text.enterText(TRACKING_DIMENSION_GROUP, trackingDimensionGroup + Keys.TAB);
		waitForPageLoad();
	}

	/**
	 * Get Inventory Unit
	 *
	 * @return
	 */
	public String getInventoryUnit( )
	{
		wait.waitForElementEnabled(INVENTORY_UNIT);
		String inventoryUnit = attribute.getElementAttribute(INVENTORY_UNIT, "value");

		if ( inventoryUnit != null )
		{
			inventoryUnit = inventoryUnit.trim();
		}
		return inventoryUnit;
	}

	/**
	 * Get Purchase Unit
	 *
	 * @return
	 */
	public String getPurchaseUnit( )
	{
		wait.waitForElementEnabled(PURCHASE_UNIT);
		String purchaseUnit = attribute.getElementAttribute(PURCHASE_UNIT, "value");

		if ( purchaseUnit != null )
		{
			purchaseUnit = purchaseUnit.trim();
		}
		return purchaseUnit;
	}

	/**
	 * Get Sales Unit
	 *
	 * @return
	 */
	public String getSalesUnit( )
	{
		wait.waitForElementEnabled(SALES_UNIT);
		String salesUnit = attribute.getElementAttribute(SALES_UNIT, "value");

		if ( salesUnit != null )
		{
			salesUnit = salesUnit.trim();
		}
		return salesUnit;
	}

	/**
	 * Get BOM Unit
	 *
	 * @return
	 */
	public String getBOMUnit( )
	{
		wait.waitForElementEnabled(BOM_UNIT);
		String bomUnit = attribute.getElementAttribute(BOM_UNIT, "value");

		if ( bomUnit != null )
		{
			bomUnit = bomUnit.trim();
		}
		return bomUnit;
	}

	/**
	 * Set "Item sales tax group" in "SALES TAXATION"
	 *
	 * @param salesTaxItemGroupId
	 */
	public void setSalesTaxItemGroupId( String salesTaxItemGroupId )
	{
		wait.waitForElementEnabled(SALES_TAX_ITEM_GROUP_ID);
		text.enterText(SALES_TAX_ITEM_GROUP_ID, salesTaxItemGroupId + Keys.TAB);
		waitForPageLoad();
	}

	/**
	 * Set "Item sales tax group" in "PURCHASE TAXATION"
	 *
	 * @param purchaseTaxItemGroupId
	 */
	public void setPurchaseTaxItemGroupId( String purchaseTaxItemGroupId )
	{
		wait.waitForElementEnabled(PURCHASE_TAX_ITEM_GROUP_ID);
		text.enterText(PURCHASE_TAX_ITEM_GROUP_ID, purchaseTaxItemGroupId + Keys.TAB);
		waitForPageLoad();
	}

	public DFinanceReleasedProductsPage clickOkButton( )
	{
		wait.waitForElementEnabled(OK_BUTTON);
		click.clickElement(OK_BUTTON);
		waitForPageLoad();

		DFinanceReleasedProductsPage releasedProductsPage = initializePageObject(DFinanceReleasedProductsPage.class);

		return releasedProductsPage;
	}
}

