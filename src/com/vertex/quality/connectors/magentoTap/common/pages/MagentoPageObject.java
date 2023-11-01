package com.vertex.quality.connectors.magentoTap.common.pages;

import com.vertex.quality.common.pages.VertexAutomationObject;
import org.openqa.selenium.WebDriver;

public class MagentoPageObject extends VertexAutomationObject {
    protected final long attributeChangeTimeout = 5;

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public MagentoPageObject(WebDriver driver) {
        super(driver);
    }
}
