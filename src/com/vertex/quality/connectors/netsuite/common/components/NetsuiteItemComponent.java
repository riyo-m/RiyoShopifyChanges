package com.vertex.quality.connectors.netsuite.common.components;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuitePageTitles;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import com.vertex.quality.connectors.netsuite.common.pages.products.NetsuiteProductClassPopupPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the component on item pages
 *
 * @author hho
 */
public class NetsuiteItemComponent extends NetsuiteComponent
{
	public NetsuiteItemComponent( final WebDriver driver, final NetsuitePage parent )
	{
		super(driver, parent);
	}

	/**
	 * Checks if a Vertex product class is available
	 *
	 * @param vertexProductClassDropdownLocator the dropdown locator
	 * @param productClassName                  the product class name
	 *
	 * @return if a Vertex product class is available
	 */
	public boolean isVertexProductClassAvailable( By vertexProductClassDropdownLocator, String productClassName )
	{
		return isDropdownValueInList(vertexProductClassDropdownLocator, productClassName);
	}

	/**
	 * Creates a new Vertex product class and selects it
	 *
	 * @param newVertexProductClassButton the button
	 * @param className                   the class name
	 */
	public void createNewVertexProductClass( By newVertexProductClassButton, String className )
	{
		String currentWindowHandle = driver.getWindowHandle();
		hover.hoverOverElement(newVertexProductClassButton);
		click.clickElement(newVertexProductClassButton);
		NetsuitePageTitles vertexProductClassPageTitle = NetsuitePageTitles.VERTEX_PRODUCT_CLASS_PAGE;
		NetsuiteProductClassPopupPage classPopupPage = new NetsuiteProductClassPopupPage(driver, currentWindowHandle);
		window.switchToWindowTextInTitle(vertexProductClassPageTitle.getPageTitle());
		classPopupPage.enterClassName(className);
		classPopupPage.save();
	}

	/**
	 * Selects the tab
	 *
	 * @param tabLocator the tab
	 */
	public void selectSalesAndPricingTab( By tabLocator )
	{
		click.clickElement(tabLocator);
	}

	/**
	 * Inputs the description
	 *
	 * @param descriptionTextfieldLocator the locator
	 * @param description                 the description
	 */
	public void enterSalesDescription( By descriptionTextfieldLocator, String description )
	{
		text.enterText(descriptionTextfieldLocator, description);
	}
}
