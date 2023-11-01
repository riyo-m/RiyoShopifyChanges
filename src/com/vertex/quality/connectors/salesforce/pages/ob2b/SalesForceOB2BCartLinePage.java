package com.vertex.quality.connectors.salesforce.pages.ob2b;

import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SalesForceOB2BCartLinePage extends SalesForceBasePage {

    protected By CART_NUMBER_LINK = By.xpath("//td[text()='Cart']/following-sibling::td/div/a");
    protected By DISCOUNT_PERCENT_INPUT = By.xpath(
            "//label[contains(text(),'DiscountPercent')]//parent::td/following-sibling::td/input");
    protected By DISCOUNT_AMOUNT_INPUT = By.xpath(
            "//label[contains(text(),'DiscountAmount')]//parent::td/following-sibling::td/input");

    protected By EDIT_BUTTON = By.xpath("//td[@id = 'topButtonRow']//input[@title= 'Edit']");
    protected By SAVE_BUTTON = By.xpath("//td[@id = 'topButtonRow']//input[@title= 'Save']");
    protected By VERTEX_TAX_CODE = By.xpath("//*/td[text()='Vertex Tax Code']//following-sibling::td//div");
    protected By TAX_CODE = By.xpath("//*/td[text()='Tax Code']//following-sibling::td//div");
    protected By INVOICE_TEXT_CODE = By.xpath("//*/td[text()='Invoice Text Code']//following-sibling::td//div");

    public SalesForceOB2BCartLinePage( WebDriver driver )
    {
        super(driver);
    }

    /**
     * Click Edit button
     */
    public void clickEditButton( )
    {
        wait.waitForElementDisplayed(EDIT_BUTTON);
        click.clickElement(EDIT_BUTTON);
    }

    /**
     * Click save button on cart page
     */
    public void clickSaveButton( )
    {
        wait.waitForElementDisplayed(SAVE_BUTTON);
        click.clickElement(SAVE_BUTTON);
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
     * Navigates back to cart page
     */
    public void navigateBackToCart()
    {
        wait.waitForElementDisplayed(CART_NUMBER_LINK);
        click.clickElement(CART_NUMBER_LINK);
        waitForPageLoad();
        waitForSalesForceLoaded();
    }

    /**
     * Gets the Value for VAT Tax Code
     */
    public String getTaxCode()
    {
        WebElement taxCode = wait.waitForElementDisplayed(TAX_CODE);
        return taxCode.getText();
    }

    /**
     * Gets the Value for VAT Vertex Tax Code
     */
    public String getVertexTaxCode()
    {
        WebElement taxCode = wait.waitForElementDisplayed(VERTEX_TAX_CODE);
        return taxCode.getText();
    }

    /**
     * Gets the Value for VAT Invoice Text Code
     */
    public String getInvoiceTextCode()
    {
        WebElement textCode = wait.waitForElementDisplayed(INVOICE_TEXT_CODE);
        return textCode.getText();
    }
}
