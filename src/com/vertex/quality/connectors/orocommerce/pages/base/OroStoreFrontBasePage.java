package com.vertex.quality.connectors.orocommerce.pages.base;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.orocommerce.components.storefront.base.pageHeaderMenuBar;
import com.vertex.quality.connectors.orocommerce.components.storefront.base.pageHeaderMiddleBar;
import org.openqa.selenium.WebDriver;

/**
 * contains all the common functionality across all the Oro  store Front Pages
 *
 * @author alewis
 */
public class OroStoreFrontBasePage extends VertexPage
{
	public pageHeaderMiddleBar headerMiddleBar;
	public pageHeaderMenuBar headerMenuBar;

	public OroStoreFrontBasePage( WebDriver driver )
	{
		super(driver);
		this.headerMiddleBar = new pageHeaderMiddleBar(driver, this);
		this.headerMenuBar = new pageHeaderMenuBar(driver, this);
	}
}
