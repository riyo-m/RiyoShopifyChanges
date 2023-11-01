package com.vertex.quality.connectors.salesforce.pages.lb2b;

import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SalesForceB2BCartItemPage extends SalesForceBasePage {

    protected By PENCIL_EDIT_BUTTON = By.xpath("//div/span[contains(text(),'DiscountAmount')]/../following-sibling::div/button");
    protected By DISCOUNT_PERCENT_INPUT = By.xpath(
            "//div/label/span[contains(text(), 'DiscountPercent')]/../following-sibling::input");
    protected By DISCOUNT_AMOUNT_INPUT = By.xpath(
            "//div/label/span[contains(text(), 'DiscountAmount')]/../following-sibling::input");
    protected By ADJUSTMENT_TAX_AMOUNT_INPUT = By.xpath("" +
            "//div/label/span[contains(text(), 'Adjustment Tax Amount')]/../following-sibling::input");

    protected By CART_SAVE_BUTTON = By.xpath("//button[@title='Save']");

    public SalesForceB2BCartItemPage (WebDriver driver){ super(driver); }

    /**
     * Click pencil icon to edit active cart
     */
    public void clickPencilIcon( )
    {
        wait.waitForElementDisplayed(PENCIL_EDIT_BUTTON);
        click.clickElement(PENCIL_EDIT_BUTTON);
        waitForSalesForceLoaded();
    }

    /**
     * Save cart after editing
     */
    public void clickSaveButton( )
    {
        wait.waitForElementDisplayed(CART_SAVE_BUTTON);
        click.clickElement(CART_SAVE_BUTTON);
        waitForSalesForceLoaded();
    }

    /**
     * Set discount percent
     * @param discountPercent
     */
    public void setDiscountPercent(String discountPercent)
    {
        if(!discountPercent.equals(""))
        {
            wait.waitForElementDisplayed(DISCOUNT_PERCENT_INPUT);
            text.enterText(DISCOUNT_PERCENT_INPUT, discountPercent);
        }
    }

    /**
     * Set discount amount
     * @param discountAmount
     */
    public void setDiscountAmount(String discountAmount)
    {
        if(!discountAmount.equals(""))
        {
            wait.waitForElementDisplayed(DISCOUNT_AMOUNT_INPUT);
            text.enterText(DISCOUNT_AMOUNT_INPUT, discountAmount);
        }
    }

    /**
     * Set Adjustment Tax Amount
     * @param adjustmentTaxAmount
     */
    public void setAdjustmentTaxAmount(String adjustmentTaxAmount)
    {
        wait.waitForElementDisplayed(ADJUSTMENT_TAX_AMOUNT_INPUT);
        text.enterText(ADJUSTMENT_TAX_AMOUNT_INPUT, adjustmentTaxAmount);
    }

}
