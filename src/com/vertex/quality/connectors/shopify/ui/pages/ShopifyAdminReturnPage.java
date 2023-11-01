package com.vertex.quality.connectors.shopify.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Shopify Admin Return Page - contains all the helper methods & variables
 *
 * @author Shivam.Soni
 */
public class ShopifyAdminReturnPage extends ShopifyPage {

    protected By returnPageLabel = By.xpath(".//h1[text()='Return items']");
    protected String productName = ".//div[contains(@class,'Polaris-LegacyStack__Item')]//a[text()='<<text_replace>>']";
    protected String productLevelRefundQtyBox = "(.//span[text()='<<text_replace>>']//parent::div//parent::div)[1]/parent::div/following-sibling::div//input";
    protected By returnReasonDropdown = By.xpath(".//div[normalize-space(.)='Select a return reason']/following-sibling::div//select");
    protected By noShippingRequiredRadioButton = By.xpath(".//span[normalize-space(.)='No shipping required']/preceding-sibling::span//input");
    protected By createReturnButton = By.xpath(".//button[normalize-space(.)='Create return']");

    /**
     * Parameterized constructor of the class that helps to initialize the object & to access the parents.
     *
     * @param driver Object of WebDriver
     */
    public ShopifyAdminReturnPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Enters quantity for the refund
     *
     * @param product product's name which is to be refunded
     * @param qty     No. of qty which is to be refunded
     */
    public void enterRefundQty(String product, String qty) {
        waitForPageLoad();
        wait.waitForElementPresent(returnPageLabel);
        wait.waitForElementPresent(By.xpath(productName.replace("<<text_replace>>", product)));
        WebElement qtyBox = wait.waitForElementPresent(By.xpath(productLevelRefundQtyBox.replace("<<text_replace>>", product)));
        text.selectAllAndInputText(qtyBox, qty);
        text.pressTab(qtyBox);
        waitForPageLoad();
    }

    /**
     * Selects reason for return,l8b
     *
     * @param reason value of reason
     */
    public void selectReturnReason(String reason) {
        waitForPageLoad();
        wait.waitForElementPresent(returnPageLabel);
        dropdown.selectDropdownByDisplayName(wait.waitForElementPresent(returnReasonDropdown), reason);
        waitForPageLoad();
    }

    /**
     * Selects reason for return
     */
    public void selectReturnReason() {
        selectReturnReason("Unknown");
    }

    /**
     * Selects No Shipping Required radio button
     */
    public void selectNoShippingRequired() {
        waitForPageLoad();
        wait.waitForElementPresent(returnPageLabel);
        click.javascriptClick(wait.waitForElementEnabled(noShippingRequiredRadioButton));
        waitForPageLoad();
    }

    /**
     * Clicks on Create return button
     *
     * @return Order details page
     */
    public ShopifyAdminOrderDetailsPage clickCreateReturn() {
        waitForPageLoad();
        wait.waitForElementPresent(returnPageLabel);
        click.moveToElementAndClick(wait.waitForElementPresent(createReturnButton));
        waitForPageLoad();
        return new ShopifyAdminOrderDetailsPage(driver);
    }
}
