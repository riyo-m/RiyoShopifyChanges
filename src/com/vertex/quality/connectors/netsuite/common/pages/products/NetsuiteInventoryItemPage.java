package com.vertex.quality.connectors.netsuite.common.pages.products;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteItemPage;
import org.openqa.selenium.WebDriver;

/**
 * Represents the inventory item page in Netsuite
 *
 * @author hho
 */
public class NetsuiteInventoryItemPage extends NetsuiteItemPage
{
	public NetsuiteInventoryItemPage( final WebDriver driver )
	{
		super(driver);
	}

	@Override
	public <T extends NetsuiteItemPage> T save( )
	{
		click.clickElement(saveButtonLocator);
		return initializePageObject(NetsuiteInventoryItemPage.class);
	}

	@Override
	public <T extends NetsuiteItemPage> T edit( )
	{
		click.clickElement(editButtonLocator);
		return initializePageObject(NetsuiteInventoryItemPage.class);
	}

	/**
	 * Checks if a Vertex product class is available
	 *
	 * @param productClassName the product class name
	 *
	 * @return if a Vertex product class is available
	 */
	public boolean isVertexProductClassAvailable( String productClassName )
	{
		return itemComponent.isVertexProductClassAvailable(vertexProductClassDropdownLocator, productClassName);
	}

	/**
	 * Creates a new Vertex product class and selects it
	 *
	 * @param className the class name
	 */
	public void createNewVertexProductClass( String className )
	{
		itemComponent.createNewVertexProductClass(newVertexProductClassButton, className);
	}

	/**
	 * Selects the tab
	 */
	public void selectSalesAndPricingTab( )
	{
		itemComponent.selectSalesAndPricingTab(salesPricingTabLocator);
	}

	/**
	 * Inputs the description
	 *
	 * @param description the description
	 */
	public void enterSalesDescription( String description )
	{
		selectSalesAndPricingTab();
		itemComponent.enterSalesDescription(salesDescriptionTextfieldLocator, description);
	}

	/**
	 * Checks if the Vertex Product Class field is available
	 *
	 * @return if the Vertex Product Class field is available
	 */
	public boolean isVertexProductClassFieldAvailable( )
	{
		return element.isElementDisplayed(vertexProductClassDropdownLocator);
	}
}
