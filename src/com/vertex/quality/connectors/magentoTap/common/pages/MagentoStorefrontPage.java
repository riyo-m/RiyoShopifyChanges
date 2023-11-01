package com.vertex.quality.connectors.magentoTap.common.pages;

import com.vertex.quality.connectors.magento.storefront.components.StorefrontCreateUserPopup;
import com.vertex.quality.connectors.magento.storefront.components.StorefrontNavigationPanel;
import org.openqa.selenium.WebDriver;

/**
 * encapsulates features common to all 'pages' on Magento's Storefront site
 * This is necessary primarily because all navigation to different pages/menus
 * on it leaves the current URL of the site unchanged, and, in any case, the
 * header/footer/ navigation menu remain present/unchanged regardless of the
 * page currently being * viewed
 *
 * @author alewis
 */
public class MagentoStorefrontPage extends MagentoPage {
    public StorefrontNavigationPanel navPanel;
    public StorefrontCreateUserPopup popup;

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public MagentoStorefrontPage(WebDriver driver) {
        super(driver);

        navPanel = new StorefrontNavigationPanel(driver, this);
        popup = new StorefrontCreateUserPopup(driver, this);
    }
}
