package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class DFinanceItemRequirements extends DFinanceBasePage {

    protected By CREATE_PROJECT = By.name("OKCommandButton");
    protected By SITE = By.name("InventoryDimensions_InventSiteId");
    protected By WAREHOUSE = By.name("InventoryDimensions_InventLocationId");
    protected By OK_BUTTON = By.name("Ok");
    protected By UNIT_PRICE = By.name("ProjectSalesPrice_SalesPrice");
    Actions actions = new Actions(driver);

    public DFinanceItemRequirements( WebDriver driver )
    {
        super(driver);
    }

    /**
     * Clicks the Create Project button
     */
    public void clickCreateProjectButton(){
        wait.waitForElementDisplayed(CREATE_PROJECT);
        click.clickElementCarefully(CREATE_PROJECT);
    }

    /**
     * Clicks the ok button in the popup
     */
    public void clickOkButton(){
        wait.waitForElementDisplayed(OK_BUTTON);
        click.clickElementCarefully(OK_BUTTON);
    }

    /**
     * Adds a new line item in the item requirements
     * @param projectId
     * @param itemNumber
     * @param site
     * @param wareHouse
     * @param quantity
     */
    public void addNewLineItemRequirements(String projectId, String itemNumber, String site, String wareHouse,
                                           String quantity, int itemIndex){
        int itemNumberVal = itemIndex + 2;
        itemIndex = itemIndex+1;
        DFinanceBasePage createItemRequirements = new DFinanceBasePage(driver);

        createItemRequirements.enterProjectId(projectId, itemIndex);
        createItemRequirements.enterItem(itemNumber, itemIndex);
        createItemRequirements.enterSite(site, itemNumberVal);
        createItemRequirements.enterWarehouse(wareHouse, itemNumberVal);
        createItemRequirements.enterQuantity(quantity, itemNumberVal);
    }

    /**
     * Clicks the projects tab in the Item Requirements page
     * @param tabType
     */
    public void clickOnTabItemRequirements(String tabType){
        WebElement tabSelected = wait.waitForElementDisplayed(By.xpath("//li[@data-dyn-controlname='"+tabType+"_header']"));
        click.clickElementCarefully(tabSelected);
    }

    /**
     * Enters the Unit Price for an line item
     * @param unitPrice
     */
    public void enterUnitPrice(String unitPrice){
        wait.waitForElementDisplayed(UNIT_PRICE);
        text.setTextFieldCarefully(UNIT_PRICE, unitPrice, false);
    }
}
