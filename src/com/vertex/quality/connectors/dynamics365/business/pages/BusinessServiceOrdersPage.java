package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessSalesBasePage;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessServiceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents the service order page
 */

public class BusinessServiceOrdersPage extends BusinessServiceBasePage {
    protected By orderLinkForLineItem=By.xpath("(//span[text()='Order'])[3]");
    protected By serviceLines=By.xpath("//span[text()='Service Lines']");
    protected By tableHeadersService=By.xpath("//div[@controlname='Service Item Worksheet Subform'][not(@summary)]//th");
    protected By serviceMgmtBackButton=By.xpath("//form[@controlname='VER_Service Line Tax Details']//i[@data-icon-name='Back']");
    protected By serviceItemWorksheetButtonLoc = By.xpath("//span[@aria-label='Service Item Worksheet']");
    protected By expandServiceItemTable=By.xpath("(//form[@controlname='Service Item Worksheet']//button[@title='Enter focus mode for this part'])[1]");
    protected By exitExpandServiceItemTable=By.xpath("//form[@controlname='Service Item Worksheet']//button[@title='Exit focus mode']");
    protected By expandServiceItemSheet=By.xpath("//form[@controlname='Service Item Worksheet']//i[@data-icon-name='FullScreen']");
    public BusinessServiceOrdersPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Clicks the Order and Service Lines for the line items
     */
    public void clickOrderAndServiceLines(){
        wait.waitForElementDisplayed(orderLinkForLineItem);
        click.javascriptClick(orderLinkForLineItem);

        wait.waitForElementDisplayed(serviceLines);
        click.javascriptClick(serviceLines);
    }

    /**
     * locates the item number field and enters the item number in it.
     *
     * @param itemNumber desired item number as a string
     * @param rowIndex   which row to write to
     */
    public void enterItemNumber(String itemNumber, int rowIndex) {
        WebElement itemNumberField = getTableCell(rowIndex, 4);
        click.clickElementIgnoreExceptionAndRetry(itemNumberField);
        waitForPageLoad();
        itemNumberField.sendKeys(itemNumber);
        text.pressTab(By.cssSelector("body"));
    }

    /**
     * Navigates to service lines from service order
     * @return serviceItem
     */
    public BusinessServiceItemPage navigateToServiceItemLines(){
        if(element.isElementDisplayed(moreOptionsInServiceLines)){
            clickOnMoreOptions();
            waitForPageLoad();
        }
        clickOrderAndServiceLines();
        waitForPageLoad();
        BusinessServiceItemPage serviceItem = new BusinessServiceItemPage(driver);
        return serviceItem;
    }

}
