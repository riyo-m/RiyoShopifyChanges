package com.vertex.quality.connectors.woocommerceTap.storefront.pages;

import com.vertex.quality.connectors.woocommerceTap.storefront.pages.base.WooCommercePreCheckoutBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * representation of the customer page of WooCommerce store front site
 * which displays the details of customer information.
 *
 * @author vivek.kumar
 */
public class WooCommerceCustomersPage extends WooCommercePreCheckoutBasePage {
    public WooCommerceCustomersPage(WebDriver driver) {
        super(driver);
    }


    protected final By product = By.xpath("//input[@name='Product']");
    protected final By save = By.xpath("//input[@name='save']");
    protected final By code = By.xpath("(//*[contains(text(),'code')])[2]");
    protected final By customerCds = By.xpath("//span[contains(text(),'code')]");
    protected final By classButton = By.xpath("//button[contains(text(),'customerClass')]");
    protected final By classField = By.xpath("//input[@name='customerClass']");


    /**
     * click on save button.
     */
    public void clickSaveButton() {
        WebElement saveButton = wait.waitForElementDisplayed(save);
        click.moveToElementAndClick(saveButton);
    }


    /**
     * click on Customer Code.
     */
    public void clickCustomerCode() {
        WebElement customerCd = wait.waitForElementDisplayed(code);
        click.moveToElementAndClick(customerCd);
    }

    /**
     * enter Product Class.
     *
     * @param customerCode for exempted customer.
     */
    public void enterCustomerCode(String customerCode) {
        WebElement customerCd = wait.waitForElementDisplayed(customerCds);
        text.enterText(customerCd, customerCode);
    }

    /**
     * navigates to the Customer Code in the back office from the Customer page
     *
     * @param customerCode for exempted customer.
     */
    public void navigateToCustomerCode(String customerCode) {
        clickCustomerCode();
        enterCustomerCode(customerCode);
        clickSaveButton();
    }

    /**
     * click on Customer class.
     */
    public void clickCustomerClass() {
        WebElement customerClass = wait.waitForElementDisplayed(classButton);
        click.moveToElementAndClick(customerClass);
    }

    /**
     * enter Customer Class.
     *
     * @param customerClass for exempted customer.
     */
    public void enterCustomerClass(String customerClass) {
        WebElement customerCls = wait.waitForElementDisplayed(classField);
        text.enterText(customerCls, customerClass);
    }

    /**
     * navigates to the Customer Class from the Products page
     *
     * @param customerClass for exempted customers.
     */
    public void navigateToCustomerClass(String customerClass) {
        clickCustomerClass();
        enterCustomerClass(customerClass);
        clickSaveButton();
    }
}