package com.vertex.quality.connectors.bigcommerce.ui.pages.storefront.base;

import com.vertex.quality.connectors.bigcommerce.ui.components.BigCommerceStoreHeaderPane;
import org.openqa.selenium.WebDriver;

/**
 * a generic representation of a page on big commerce's storefront site other than the checkout page
 *
 * @author ssalisbury
 */
public class BigCommerceStorePreCheckoutBasePage extends BigCommerceStoreBasePage
{
	public BigCommerceStoreHeaderPane header;

	public BigCommerceStorePreCheckoutBasePage( final WebDriver driver )
	{
		super(driver);
		this.header = initializePageObject(BigCommerceStoreHeaderPane.class, this);
	}
}
