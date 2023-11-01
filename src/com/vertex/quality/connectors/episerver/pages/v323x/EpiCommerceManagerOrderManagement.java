package com.vertex.quality.connectors.episerver.pages.v323x;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiCommerceManagerHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * A java class that contains locators & helper methods of Order Management page of Episerver's Commerce Manager
 *
 * @author Shivam.Soni
 */
public class EpiCommerceManagerOrderManagement extends EpiCommerceManagerHomePage {

    /**
     * Parameterized constructor of the class
     *
     * @param driver object of WebDriver
     */
    public EpiCommerceManagerOrderManagement(WebDriver driver) {
        super(driver);
    }

    protected String RECORD_NAME = ".//a[text()='<<text_replace>>']";
    protected By ORDER_PAGE_ALL_TABS = By.xpath(".//ul[@class='tabs']//li");
    protected String ORDER_PAGE_TAB = ".//li[text()='<<text_replace>>']";
    protected String SELECTED_TAB = ".//li[@class='tabSelected'][text()='<<text_replace>>']";
    protected By RELEASE_SHIPMENT_BUTTON = By.xpath(".//button[text()='Release Shipment']");
    protected By DISABLED_RELEASE_SHIPMENT_BUTTON = By.xpath(".//button[text()='Release Shipment'][@disabled='disabled']");
    protected String RECORD_CHECKBOX = ".//td[starts-with(normalize-space(.),'<<text_replace>>')]/preceding-sibling::td//input[@type='checkbox']";
    protected By ADD_TO_PICK_LIST_BUTTON = By.xpath(".//button[text()='Add Shipment to Picklist']");
    protected By ADD_TO_PICK_LIST_POPUP = By.id("McCommandHandlerFramePopupDiv");
    protected By NEW_PICK_LIST = By.xpath(".//input[@value='createNewOption']");
    protected By DEFAULT_NEW_PICK_LIST_NAME = By.xpath(".//tr[normalize-space(.)='Create New Pick List']/following-sibling::tr[1]//input");
    protected By ADD_TO_PICK_LIST_OK_BUTTON = By.xpath(".//button//img[@src='/Apps/MetaDataBase/images/ok-button.gif']");
    protected By GRID_PAGINATION = By.xpath(".//select[@class='GridPaging']");
    protected By PICK_LIST_NAME_COL = By.xpath(".//a[text()='Name']");
    protected By PICK_LIST_NAME_COL_ASCENDING = By.xpath(".//a[text()='Name']/following-sibling::img[@title='Ascending']");
    protected By PICK_LIST_NAME_COL_DESCENDING = By.xpath(".//a[text()='Name']/following-sibling::img[contains(@src,'desc.gif')]");
    protected By COMPLETE_SHIPMENT_BUTTON = By.xpath(".//button[text()='Complete Shipment']");
    protected By COMPLETE_SHIPMENT_POPUP = By.xpath(".//span[text()='Complete Shipment']/parent::div");
    protected By TRACKING_NO_INPUT = By.xpath(".//td[normalize-space(.)='Tracking Number:']/following-sibling::td/input");
    protected By COMPLETE_SHIPMENT_OK_BUTTON = By.xpath(".//input[@value='OK']");
    protected By ORDER_DETAIL_TAB_ORDER_STATUS = By.xpath(".//table[@class='borderType1TableLayoutSection']//td[normalize-space(.)='Status:']/following-sibling::td/span");
    protected By ORDER_DETAIL_TAB_ORDER_TAX = By.xpath(".//table[@class='borderType1TableLayoutSection']//td[normalize-space(.)='Taxes Total:']/following-sibling::td/span");

    /**
     * Opens Record
     *
     * @param recordName record name which is to be opened
     */
    public void openRecord(String recordName) {
        waitForPageLoad();
        switchToRightIframe();
        WebElement order = wait.waitForElementPresent(By.xpath(RECORD_NAME.replace("<<text_replace>>", recordName)));
        click.moveToElementAndClick(order);
        waitForPageLoad();
        window.switchToDefaultContent();
        VertexLogger.log("Opened record: " + recordName);
    }

    /**
     * Selects tab
     *
     * @param tabName Tab name which is to be selected
     */
    public void selectTab(String tabName) {
        waitForPageLoad();
        switchToRightIframe();
        WebElement tab = wait.waitForElementPresent(By.xpath(ORDER_PAGE_TAB.replace("<<text_replace>>", tabName)));
        click.moveToElementAndClick(tab);
        waitForPageLoad();
        wait.waitForElementPresent(By.xpath(SELECTED_TAB.replace("<<text_replace>>", tabName)));
        window.switchToDefaultContent();
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
        waitForPageLoad();
        switchToRightIframe();
        WebElement orderStatus = wait.waitForElementPresent(ORDER_DETAIL_TAB_ORDER_STATUS);
        actualStatus = text.getElementText(orderStatus);
        VertexLogger.log("Actual order status: " + actualStatus);
        return status.equalsIgnoreCase(actualStatus);
    }

    /**
     * Clicks on release shipment button to release order shipment
     */
    public void clickReleaseShipment() {
        waitForPageLoad();
        switchToRightIframe();
        click.moveToElementAndClick(wait.waitForElementPresent(RELEASE_SHIPMENT_BUTTON));
        waitForPageLoad();
        wait.waitForElementPresent(DISABLED_RELEASE_SHIPMENT_BUTTON);
        window.switchToDefaultContent();
        VertexLogger.log("Clicked on Release Shipment button");
    }

    /**
     * Selects checkbox of record
     *
     * @param recordName record name which is to be selected
     */
    public void selectRecord(String recordName) {
        waitForPageLoad();
        switchToRightIframe();
        WebElement order = wait.waitForElementPresent(By.xpath(RECORD_CHECKBOX.replace("<<text_replace>>", recordName)));
        click.moveToElementAndClick(order);
        window.switchToDefaultContent();
        VertexLogger.log("Selected record: " + recordName);
    }

    /**
     * Clicks on Add To Pick List button
     */
    public void clickAddToPickList() {
        waitForPageLoad();
        switchToRightIframe();
        click.moveToElementAndClick(wait.waitForElementPresent(ADD_TO_PICK_LIST_BUTTON));
        waitForPageLoad();
        window.switchToDefaultContent();
        VertexLogger.log("Clicked on Add To Picklist button");
    }

    /**
     * Used to add shipment to release
     *
     * @param orderNo Order number which should be added to release shipment list
     * @return pick list name
     */
    public String addShipmentToRelease(String orderNo) {
        String pickListName;
        selectRecord(orderNo);
        clickAddToPickList();
        switchToRightIframe();
        wait.waitForElementPresent(ADD_TO_PICK_LIST_POPUP);
        switchToPopupIframe();
        click.moveToElementAndClick(wait.waitForElementPresent(NEW_PICK_LIST));
        pickListName = attribute.getElementAttribute(DEFAULT_NEW_PICK_LIST_NAME, "value");
        VertexLogger.log("New pick list name: " + pickListName);
        click.moveToElementAndClick(wait.waitForElementPresent(ADD_TO_PICK_LIST_OK_BUTTON));
        waitForPageLoad();
        window.switchToDefaultContent();
        return pickListName;
    }

    /**
     * Loads all the records in the single page
     */
    public void loadAllRecords() {
        waitForPageLoad();
        switchToRightIframe();
        dropdown.selectDropdownByDisplayName(GRID_PAGINATION, "All");
        waitForPageLoad();
        window.switchToDefaultContent();
        VertexLogger.log("Loaded all the records");
    }

    /**
     * Sorts name column of pick list
     */
    public void sortPickListByAscending() {
        int count = 0;
        waitForPageLoad();
        switchToRightIframe();
        while (!element.isElementDisplayed(PICK_LIST_NAME_COL_DESCENDING)) {
            click.clickElementIgnoreExceptionAndRetry(wait.waitForElementPresent(PICK_LIST_NAME_COL));
            waitForPageLoad();
            // Intentional timeout because ascending/descending image locators takes certain time to appear on UI
            jsWaiter.sleep(3500);
            count++;
            if (count == 5) {
                VertexLogger.log("Unable to properly sort column in Ascending Order");
                break;
            } else {
                VertexLogger.log("Clicked to sort column");
            }
        }
        window.switchToDefaultContent();
    }

    /**
     * Clicks on Complete Shipment button
     */
    public void clickCompleteShipment() {
        waitForPageLoad();
        switchToRightIframe();
        click.moveToElementAndClick(wait.waitForElementPresent(COMPLETE_SHIPMENT_BUTTON));
        waitForPageLoad();
        window.switchToDefaultContent();
        VertexLogger.log("Clicked Complete Shipment");
    }

    /**
     * Enters tracking number while completing the order
     *
     * @param trackingNo pass Tracking Number
     */
    public void enterTracking(String trackingNo) {
        waitForPageLoad();
        switchToRightIframe();
        wait.waitForElementPresent(COMPLETE_SHIPMENT_POPUP);
        switchToPopupIframe();
        text.enterText(wait.waitForElementPresent(TRACKING_NO_INPUT), trackingNo);
        window.switchToDefaultContent();
        VertexLogger.log("Entered tracking number: " + trackingNo);
    }

    /**
     * Clicks OK on complete shipment popup
     */
    public void clickOkCompleteShipment() {
        waitForPageLoad();
        switchToRightIframe();
        wait.waitForElementPresent(COMPLETE_SHIPMENT_POPUP);
        switchToPopupIframe();
        click.moveToElementAndClick(wait.waitForElementPresent(COMPLETE_SHIPMENT_OK_BUTTON));
        switchToRightIframe();
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(COMPLETE_SHIPMENT_POPUP));
        waitForPageLoad();
        window.switchToDefaultContent();
        VertexLogger.log("Clicked OK on Complete Shipment Popup");
    }

    /**
     * Selects pick list and completes the order
     *
     * @param pickListName pick list name which is to be processed
     * @param orderNo      order number which is to be completed
     */
    public void selectRecordAndCompleteOrder(String pickListName, String orderNo, String trackingNo) {
        waitForPageLoad();
        loadAllRecords();
        sortPickListByAscending();
        openRecord(pickListName);
        selectRecord(orderNo);
        clickCompleteShipment();
        waitForPageLoad();
        enterTracking(trackingNo);
        clickOkCompleteShipment();
    }

    /**
     * Get order's total tax from commerce manager -> Order Management -> details tab
     *
     * @return order total tax
     */
    public double getOrderTotalTaxesFromUI() {
        String tax;
        waitForPageLoad();
        switchToRightIframe();
        WebElement orderTax = wait.waitForElementPresent(ORDER_DETAIL_TAB_ORDER_TAX);
        tax = text.getElementText(orderTax);
        VertexLogger.log("Order tax from Commerce Manager -> Order Management -> Details tab: " + tax);
        return Double.parseDouble(tax.replace("$", ""));
    }
}
