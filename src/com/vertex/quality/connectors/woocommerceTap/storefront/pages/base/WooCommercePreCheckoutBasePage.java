package com.vertex.quality.connectors.woocommerceTap.storefront.pages.base;

import com.vertex.quality.connectors.woocommerce.components.WooCommerceStoreHeaderPane;
import org.openqa.selenium.WebDriver;

/**
 * generic representation of WooCommerce store front pre checkout base page
 *
 * @author rohit.mogane
 */
public class WooCommercePreCheckoutBasePage extends WooCommerceStoreBasePage
{
    public WooCommerceStoreHeaderPane wooCommerceHeader;

    public WooCommercePreCheckoutBasePage(WebDriver driver)
    {
        super(driver);
        this.wooCommerceHeader = initializePageObject(WooCommerceStoreHeaderPane.class,this);
    }
}