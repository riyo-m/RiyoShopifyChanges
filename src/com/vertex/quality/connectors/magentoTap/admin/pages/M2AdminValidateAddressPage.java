package com.vertex.quality.connectors.magentoTap.admin.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represent the page for validate address for address cleansing.
 *
 * @author Mayur.Kumbhar
 */
public class M2AdminValidateAddressPage extends VertexPage {

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public M2AdminValidateAddressPage(final WebDriver driver) {
        super(driver);
    }

    protected By validateAddressButton = By.xpath("//button[@title='Validate address']");
    protected By sameBillingCheckBox = By.xpath("//input[@name='shipping_same_as_billing']");
    protected By validateShippingAddressButton = By.xpath("//button[@data-ui-id='widget-button-1']");


    /**
     * click on validate address button for address cleansing.
     */
    public void clickOnValidateAddressButton() {
        WebElement validateAddressButtonField = wait.waitForElementDisplayed(validateAddressButton);
        click.moveToElementAndClick(validateAddressButtonField);

    }

    /**
     * click on same billing and ship address.
     */
    public void clickOnSameBillingCheckBox() {
        WebElement sameBillCheckBoxField = wait.waitForElementDisplayed(sameBillingCheckBox);
        click.moveToElementAndClick(sameBillCheckBoxField);
    }

    /**
     * click on validate shipping address button
     */
    public void clickOnValidateShippingAddressButton() {
        WebElement validateShippingAddressButtonField = wait.waitForElementEnabled(validateShippingAddressButton);
        click.moveToElementAndClick(validateShippingAddressButtonField);
    }
}
