package com.vertex.quality.connectors.magentoTap.admin.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Representation of the Create New Order in Default Store View Page
 *
 * @author alewis
 */
public class M2AdminOrderCustomerPage extends MagentoAdminPage {

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public M2AdminOrderCustomerPage(WebDriver driver) {
        super(driver);
    }

    protected By customerButtonID = By.className("col-name");
    By maskClass = By.className("loading-mask");

    /**
     * finds a customer
     *
     * @return a WebElement of a customer
     */
    private WebElement findCustomer(String customerID) {
        WebElement customerButton = element.selectElementByText(customerButtonID, customerID);
        return customerButton;
    }

    /**
     * clicks a customer
     *
     * @return the Create New Order Page
     */
    public M2AdminCreateNewOrderPage clickCustomer(String customer) {
        waitForSpinnerToBeDisappeared();
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement customerButton = findCustomer(customer);

        if (customerButton != null) {
            wait.waitForElementDisplayed(customerButton, 7);
            click.clickElement(customerButton);
        } else {
            String errorMsg = "Customer button not found";
            throw new RuntimeException(errorMsg);
        }
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();

        return initializePageObject(M2AdminCreateNewOrderPage.class);
    }
}
