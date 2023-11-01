package com.vertex.quality.connectors.magentoTap.storefront.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoStorefrontPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Representation of the Ship to Multiple Addresses Page
 *
 * @author alewis
 */
public class M2StorefrontShipToMultipleAddressesPage extends MagentoStorefrontPage {
    protected By firstSendToSelectId = By.cssSelector("select[id^='ship_0']");
    protected By secodnSendToSelectId = By.cssSelector("select[id^='ship_1']");

    protected By goToShippingInfoButtonClass = By.className("continue");

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver
     */
    public M2StorefrontShipToMultipleAddressesPage(WebDriver driver) {
        super(driver);
    }

    /**
     * select the address for the first item
     *
     * @param addressValue
     */
    public void selectFirstItemAddress(String addressValue) {
        WebElement sendToSelect = wait.waitForElementEnabled(firstSendToSelectId);

        dropdown.selectDropdownByValue(sendToSelect, addressValue);
    }

    /**
     * select the address for the second item
     *
     * @param addressValue
     */
    public void selectSecondItemAddress(String addressValue) {
        WebElement sendToSelect = wait.waitForElementEnabled(secodnSendToSelectId);

        dropdown.selectDropdownByValue(sendToSelect, addressValue);
    }

    /**
     * click the Goto Shipping Information button
     *
     * @return shipping information page
     */
    public M2StorefrontShippingInfoPage clickGoToShippingInformationButton() {
        WebElement button = wait.waitForElementEnabled(goToShippingInfoButtonClass);

        button.click();

        wait.waitForElementNotDisplayedOrStale(button, 10);

        M2StorefrontShippingInfoPage shippingInfoPage = initializePageObject(M2StorefrontShippingInfoPage.class);

        return shippingInfoPage;
    }
}
