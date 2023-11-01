package com.vertex.quality.connectors.episerver.pages.v324x;

import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Episerver's Orders page where orders can be managed (Release order, Add to Pick list, etc...)
 *
 * @author Shivam.Soni
 */
public class EpiOrdersPage extends EpiOrderManagementHomePage {

    protected String ORDER_NO_LINK = ".//a[normalize-space(.)='<<text_replace>>']";
    protected String ORDER_DETAILS_TABS = ".//div[normalize-space(.)='<<text_replace>>']";
    protected String SELECTED_TAB = ".//div[normalize-space(.)='<<text_replace>>']/following-sibling::span[contains(@class,'active')]";
    protected By ORDER_DETAIL_TAB_ORDER_STATUS = By.xpath(".//p[text()='Status']/following-sibling::p");
    protected By ORDER_DETAIL_TAB_ORDER_TAX = By.xpath(".//p[text()='Taxes Total']/following-sibling::span");
    protected By ORDER_MORE_ACTION = By.xpath(".//h6[text()='Shipments']/parent::div//div[@class='action-dropdown__container']");
    protected By ORDER_MORE_ACTION_DIALOG = By.xpath("(.//ul[@class='epi-uif-list mdc-list'])[1]");
    protected By RELEASE_OPTION = By.xpath(".//ul//li[text()='Release']");
    protected By RELEASE_SHIPMENT_CONFIRMATION = By.xpath(".//p[text()='Are you sure you want to release this shipment?']");
    protected By RELEASE_SHIPMENT_BUTTON = By.xpath(".//button[normalize-space(.)='Release Shipment']");
    protected By ADD_TO_PICKLIST_OPTION = By.xpath(".//ul//li[text()='Add to Picklist']");
    protected By ADD_TO_PICKLIST_CONFIRMATION = By.xpath(".//p[text()='Are you sure you want to add this shipment to a new picklist?']");
    protected By ADD_TO_PICK_LIST_BUTTON = By.xpath(".//button[normalize-space(.)='Add to PickList']");
    protected By COMPLETE_OPTION = By.xpath(".//ul//li[text()='Complete']");
    protected By COMPLETE_SHIPMENT_CONFIRMATION = By.xpath(".//p[text()='Are you sure you want to complete this shipment?']");
    protected By COMPLETE_SHIPMENT_BUTTON = By.xpath(".//button[normalize-space(.)='Complete Shipment']");

    /**
     * Parameterized constructor of the class
     *
     * @param driver object of WebDriver
     */
    public EpiOrdersPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Opens Order
     *
     * @param orderNo Order Number which is to be opened
     */
    public void openRecord(String orderNo) {
        waitForSpinnerToBeDisappeared();
        WebElement order = wait.waitForElementPresent(By.xpath(ORDER_NO_LINK.replace("<<text_replace>>", orderNo)));
        click.moveToElementAndClick(order);
        waitForSpinnerToBeDisappeared();
        VertexLogger.log("Opened order: " + orderNo);
    }

    /**
     * Selects tab
     *
     * @param tabName Tab name which is to be selected
     */
    public void selectTab(String tabName) {
        waitForSpinnerToBeDisappeared();
        WebElement tab = wait.waitForElementPresent(By.xpath(ORDER_DETAILS_TABS.replace("<<text_replace>>", tabName)));
        click.moveToElementAndClick(tab);
        waitForSpinnerToBeDisappeared();
        VertexLogger.log("Selected tab: " + tabName + " from Order Management");
    }

    /**
     * Clicks on order to open order and selects tab
     *
     * @param orderNo Order Number which is to be opened
     * @param tabName Tab name which is to be selected
     */
    public void openOrderAndSelectTab(String orderNo, String tabName) {
        openRecord(orderNo);
        selectTab(tabName);
    }

    /**
     * Verify actual and expected order status under Order Page -> Details tab
     *
     * @param status expected order status
     * @return true or false based on condition
     */
    public boolean verifyOrderStatus(String status) {
        String actualStatus;
        VertexLogger.log("Expected order status: " + status);
        waitForSpinnerToBeDisappeared();
        WebElement orderStatus = wait.waitForElementPresent(ORDER_DETAIL_TAB_ORDER_STATUS);
        actualStatus = text.getElementText(orderStatus);
        VertexLogger.log("Actual order status: " + actualStatus);
        return status.equalsIgnoreCase(actualStatus);
    }

    /**
     * Clicks on release shipment button to release order shipment
     */
    public void clickReleaseShipment() {
        openOrderMoreActionDropdown();
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(RELEASE_OPTION));
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(RELEASE_SHIPMENT_CONFIRMATION);
        click.moveToElementAndClick(wait.waitForElementPresent(RELEASE_SHIPMENT_BUTTON));
        waitForSpinnerToBeDisappeared();
        VertexLogger.log("Clicked on Release Shipment button");
    }

    /**
     * Opens order's more action dropdown
     */
    public void openOrderMoreActionDropdown() {
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(ORDER_MORE_ACTION));
        wait.waitForElementPresent(ORDER_MORE_ACTION_DIALOG);
    }

    /**
     * Clicks on Add To Pick List button
     */
    public void clickAddToPickList() {
        openOrderMoreActionDropdown();
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(ADD_TO_PICKLIST_OPTION));
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(ADD_TO_PICKLIST_CONFIRMATION);
        click.moveToElementAndClick(wait.waitForElementPresent(ADD_TO_PICK_LIST_BUTTON));
        waitForSpinnerToBeDisappeared();
        VertexLogger.log("Clicked on Add To Picklist button");
    }

    /**
     * Clicks on Complete Shipment button
     */
    public void clickCompleteShipment() {
        openOrderMoreActionDropdown();
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(COMPLETE_OPTION));
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(COMPLETE_SHIPMENT_CONFIRMATION);
        click.moveToElementAndClick(wait.waitForElementPresent(COMPLETE_SHIPMENT_BUTTON));
        waitForSpinnerToBeDisappeared();
        VertexLogger.log("Clicked Complete Shipment");
    }

    /**
     * Takes all necessary steps to complete the order
     */
    public void releaseOrderAddToPickListAndCompleteOrder() {
        clickReleaseShipment();
        clickAddToPickList();
        clickCompleteShipment();
    }

    /**
     * Get order's total tax from Commerce -> Order Management -> details tab
     *
     * @return order total tax
     */
    public double getOrderTotalTaxesFromUI() {
        String tax;
        waitForSpinnerToBeDisappeared();
        WebElement orderTax = wait.waitForElementPresent(ORDER_DETAIL_TAB_ORDER_TAX);
        tax = text.getElementText(orderTax);
        VertexLogger.log("Order tax from Commerce -> Order Management -> Form Details tab: " + tax);
        return Double.parseDouble(tax.replace("USD", ""));
    }
}
