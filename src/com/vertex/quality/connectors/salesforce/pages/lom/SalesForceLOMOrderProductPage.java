package com.vertex.quality.connectors.salesforce.pages.lom;

import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SalesForceLOMOrderProductPage extends SalesForceBasePage {

    protected By ORDER_PRODUCT_DROPDOWN = By.xpath("//a[@title='New Case']/../..//div[@class='uiPopupTrigger']");
    protected By DROPDOWN_EDIT_BUTTON = By.xpath("//div[contains(@class, 'DropDownMenuList')]//a[@title='Edit']");

    protected By DISCOUNT_PERCENT_INPUT = By.xpath(
            "//div/label/span[contains(text(), 'DiscountPercent')]/../following-sibling::input");
    protected By DISCOUNT_AMOUNT_INPUT = By.xpath(
            "//div/label/span[contains(text(), 'DiscountAmount')]/../following-sibling::input");
    protected By ORDER_PRODUCT_TYPE_INPUT = By.xpath(
            ".//div/span/span[text()='Type']/../..//a[@class='select']");

    protected By ORDER_PRODUCT_SAVE_BUTTON = By.xpath("//button[@title='Save']");
    protected By ORDER_PRODUCT_EDIT_BUTTON = By.xpath("//a[@title='Edit' and @class='forceActionLink']");
    protected By ORDER_LINK = By.xpath("//span[text()='Order']/../following-sibling::div/span/div/a");

    SalesForceLOMPostLogInPage postLogInPage;
    SalesForceLOMOrderPage orderPage;
    public SalesForceLOMOrderProductPage( WebDriver driver )
    {
        super(driver);
        postLogInPage = new SalesForceLOMPostLogInPage(driver);
        orderPage = new SalesForceLOMOrderPage(driver);
    }

    /**
     * Navigate back to order
     */
    public void navigateBackToOrder()
    {
        refreshPage();
        waitForSalesForceLoaded();
        wait.waitForElementDisplayed(ORDER_LINK);
        click.javascriptClick(ORDER_LINK);
        waitForPageLoad();
    }

    /**
     * Click edit button in order dropdown menu
     */
    public void clickOrderDropdownButton()
    {
        scroll.scrollElementIntoView(ORDER_PRODUCT_DROPDOWN);
        wait.waitForElementDisplayed(ORDER_PRODUCT_DROPDOWN);
        click.clickElement(ORDER_PRODUCT_DROPDOWN);
        waitForPageLoad();
        waitForSalesForceLoaded();
    }

    /**
     * Click edit button on order dropdown
     */
    public void clickDropdownEditButton( )
    {
        waitForSalesForceLoaded();
        clickOrderDropdownButton();
        wait.waitForElementDisplayed(DROPDOWN_EDIT_BUTTON);
        click.javascriptClick(DROPDOWN_EDIT_BUTTON);
        waitForSalesForceLoaded();
    }

    /**
     * Click Save button
     */
    public void clickSaveButton()
    {
        wait.waitForElementDisplayed(ORDER_PRODUCT_SAVE_BUTTON);
        click.clickElement(ORDER_PRODUCT_SAVE_BUTTON);
        waitForPageLoad();
        waitForSalesForceLoaded();
    }

    /**
     * Set Order Discount Amount
     *
     * @param discountAmount
     */
    public void setDiscountAmount(String discountAmount)
    {
        if (!discountAmount.equals(""))
        {
            wait.waitForElementDisplayed(DISCOUNT_AMOUNT_INPUT);
            text.enterText(DISCOUNT_AMOUNT_INPUT, discountAmount);
        }
    }

    /**
     * Set Order Discount Percent
     *
     * @param discountPercent
     */
    public void setDiscountPercent(String discountPercent)
    {
        if (!discountPercent.equals(""))
        {
            wait.waitForElementDisplayed(DISCOUNT_PERCENT_INPUT);
            text.enterText(DISCOUNT_PERCENT_INPUT, discountPercent);
        }
    }

    /**
     * Click Edit button
     */
    public void clickEditButton(){
        waitForPageLoad();
        wait.waitForElementDisplayed(ORDER_PRODUCT_EDIT_BUTTON);
        click.clickElement(ORDER_PRODUCT_EDIT_BUTTON);
    }

    /**
     * Update product type
     * @param productName
     * @param type
     */
    public void updateProductType(String productName, String type) {
        orderPage.navigateToOrderProduct(productName);
        clickEditButton();
        wait.waitForElementDisplayed(ORDER_PRODUCT_TYPE_INPUT);
        click.clickElement(ORDER_PRODUCT_TYPE_INPUT);

        String locator = String.format(".//li/a[@title='%s']", type);
        By path = By.xpath(locator);
        wait.waitForElementDisplayed(path);
        click.clickElement(path);
        clickSaveButton();
    }

}
