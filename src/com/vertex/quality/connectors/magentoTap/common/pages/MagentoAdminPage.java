package com.vertex.quality.connectors.magentoTap.common.pages;

import com.vertex.quality.connectors.magentoTap.admin.components.M2AdminNavigationPanel;
import org.openqa.selenium.WebDriver;

/**
 * encapsulates features common to all 'pages' on Magento's Admin site
 * <p>
 * This is necessary primarily because all navigation to different pages/menus
 * on it leaves the current URL of the site unchanged, and, in any case, the
 * header/footer/ navigation menu remain present/unchanged regardless of the
 * page currently being viewed
 *
 * @author alewis
 */
public abstract class MagentoAdminPage extends MagentoPage {
    public M2AdminNavigationPanel navPanel;

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public MagentoAdminPage(WebDriver driver) {
        super(driver);

        navPanel = new M2AdminNavigationPanel(driver);
    }
}
