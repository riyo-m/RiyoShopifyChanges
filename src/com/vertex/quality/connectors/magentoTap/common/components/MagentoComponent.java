package com.vertex.quality.connectors.magentoTap.common.components;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoPageObject;
import org.openqa.selenium.WebDriver;

/**
 * A component in Magento
 *
 * @author alewis
 */
public class MagentoComponent extends MagentoPageObject {

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public MagentoComponent(WebDriver driver) {
        super(driver);
    }
}
