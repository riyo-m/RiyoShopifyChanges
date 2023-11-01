package com.vertex.quality.connectors.netsuite.common.pages.products;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteItemPage;
import org.openqa.selenium.WebDriver;

/**
 * Represents the gift certificate page
 *
 * @author hho
 */
public class NetsuiteGiftCertificatePage extends NetsuiteItemPage
{
	public NetsuiteGiftCertificatePage( final WebDriver driver )
	{
		super(driver);
	}

	@Override
	public <T extends NetsuiteItemPage> T save( )
	{
		click.clickElement(saveButtonLocator);
		return initializePageObject(NetsuiteGiftCertificatePage.class);
	}

	@Override
	public <T extends NetsuiteItemPage> T edit( )
	{
		click.clickElement(editButtonLocator);
		return initializePageObject(NetsuiteGiftCertificatePage.class);
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
}
