package com.vertex.quality.connectors.dynamics365.business.pages.base;

import com.vertex.quality.connectors.dynamics365.business.pages.BusinessServiceItemPage;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BusinessServiceBasePage extends BusinessSalesBasePage {
    protected By moreOptionsInServiceLines=By.xpath("//div[contains(@class, 'ms-nav-band-header')]//span[@aria-label='More options']");
    protected By orderLinkForLineItem=By.xpath("(//span[text()='Order'])[3]");
    protected By serviceLines=By.xpath("//span[text()='Service Lines']");
    protected By tableHeadersService=By.xpath("//div[@controlname='Service Item Worksheet Subform'][not(@summary)]//th");
    protected By serviceMgmtBackButton=By.xpath("//form[@controlname='VER_Service Line Tax Details']//i[@data-icon-name='Back']");
    protected By serviceItemWorksheetButtonLoc = By.xpath("//span[@aria-label='Service Item Worksheet']");
    protected By expandServiceItemTable=By.xpath("(//form[@controlname='Service Item Worksheet']//button[@title='Enter focus mode for this part'])[1]");
    protected By exitExpandServiceItemTable=By.xpath("//form[@controlname='Service Item Worksheet']//button[@title='Exit focus mode']");
    protected By expandServiceItemSheet=By.xpath("//form[@controlname='Service Item Worksheet']//i[@data-icon-name='FullScreen']");

    public BusinessServiceBasePage(WebDriver driver) {
        super(driver);
    }


    /**
     * locates the service Item No field on the item table and enters the service Item no. in
     *
     * @param serviceItemNo
     * @param rowIndex
     */

    public void setServiceItemNo(String serviceItemNo, int rowIndex){
        WebElement serviceItemNoField = getTableCell(rowIndex, 2);
        wait.waitForElementEnabled(serviceItemNoField);
        click.clickElementIgnoreExceptionAndRetry(serviceItemNoField);
        click.clickElementIgnoreExceptionAndRetry(By.xpath("//*[contains(@title,'Choose a value for Service Item No.')]"));
        waitForPageLoad();
        String serviceItemNumber = String.format("//a[text()='%s']", serviceItemNo, serviceItemNo);
        click.clickElementIgnoreExceptionAndRetry(By.xpath(serviceItemNumber));
    }

    /**
     * Click on more options
     */
    public void clickOnMoreOptions(){
        WebElement moreOptionsButton = wait.waitForElementEnabled(moreOptionsInServiceLines);
        click.clickElementIgnoreExceptionAndRetry(moreOptionsButton);
    }

    /**
     * Navigates to service item worksheet from service quote
     * @return serviceItem
     */
    public BusinessServiceItemPage navigateToServiceItemWorksheet(){
        if(element.isElementDisplayed(moreOptionsInServiceLines)){
            clickOnMoreOptions();
            waitForPageLoad();
        }
        click.clickElementIgnoreExceptionAndRetry(lineButtonLoc);
        waitForPageLoad();
        click.clickElementIgnoreExceptionAndRetry(serviceItemWorksheetButtonLoc);
        waitForPageLoad();
        BusinessServiceItemPage serviceItem = new BusinessServiceItemPage(driver);
        return serviceItem;
    }
    /**
     * Generalized method, expand table for Service Item lines
     */
    public void expandServiceItemTable() {
        wait.waitForElementPresent(expandServiceItemTable);
        click.clickElementCarefully(expandServiceItemTable);
        jsWaiter.waitForLoadAll();

    }
    /**
     *  exit expand table for Service Item lines
     */
    public void exitExpandServiceItemTable() {
        wait.waitForElementPresent(exitExpandServiceItemTable);
        click.clickElementCarefully(exitExpandServiceItemTable);
        jsWaiter.waitForLoadAll();

    }
    /**
     * Generalized method, for expand service Item Worksheet
     */
    public void expandServiceItemWorksheet() {
        wait.waitForElementPresent(expandServiceItemSheet);
        click.javascriptClick(expandServiceItemSheet);
        jsWaiter.waitForLoadAll();

    }


    /**
     * locates the quantity field on the item table and enters the quantity in
     *
     * @param quantity
     * @param rowIndex*/

    public void enterQuantityForService( String quantity, int rowIndex )
    {
        WebElement quantityField = getTableCell(rowIndex, 8);
        scroll.scrollElementIntoView(quantityField);
        wait.waitForElementEnabled(quantityField);
        try
        {
            if (!element.isElementDisplayed(quantityField)) {
                scroll.scrollElementIntoView(quantityField);
            }
            quantityField.click();
        }
        catch ( ElementNotInteractableException e )
        {
            click.javascriptClick(quantityField);
        }
        text.enterText(quantityField, quantity);
        text.pressTab(quantityField);
    }
    /**
     * locates the price field on the item table and enters the price in
     *
     * @param price
     * @param rowIndex*/
    public void enterUnitPriceForServiceItem(String price, int rowIndex) {
        WebElement priceField = getTableCell(rowIndex, 18);
        scroll.scrollElementIntoView(priceField);
        wait.waitForElementEnabled(priceField);
        try
        {
            priceField.click();
        }
        catch ( ElementNotInteractableException e )
        {
            click.javascriptClick(priceField);
        }
        text.setTextFieldCarefully(priceField, price, false);
        text.pressTab(priceField);
    }

    /**
     * Modify item type then fill in the items table when creating a service order and quote
     * @param itemType
     * @param itemCode
     * @param itemQuantity
     * @param price
     * @param itemIndex*/

    public void fillInItemsTableInfoForService( String itemType, String itemCode, String itemQuantity,
                                                   String price, int itemIndex ) {
        int itemNumber = itemIndex - 1;
        enterItemTypeForService(itemType,itemNumber);
        enterItemNumber(itemCode, itemNumber);
        enterQuantityForService(itemQuantity, itemNumber);
        if(price!=null) {
            enterUnitPriceForServiceItem(price, itemNumber);
        }

    }

    /**
     * Modify service item number then fill in the items table when creating a service quote/order
     * @param serviceItem
     * @param itemIndex*/

    public void fillInServiceTableInfo(String serviceItem, int itemIndex){
        int itemNumber = itemIndex - 1;
        setServiceItemNo(serviceItem,itemNumber);

    }
    /**
     * locates and clicks on the back button on service mgmt tax details dialog
     */
    public void serviceMgmtNavigateBackFromVertexTaxDetails(){
        WebElement backButton = wait.waitForElementDisplayed(serviceMgmtBackButton);
        click.clickElementCarefully(backButton);
        wait.waitForElementNotDisplayedOrStale(backButton, 5);
    }
}
