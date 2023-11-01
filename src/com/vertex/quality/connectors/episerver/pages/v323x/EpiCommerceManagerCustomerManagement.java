package com.vertex.quality.connectors.episerver.pages.v323x;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiCommerceManagerHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * A java class that contains locators & helper methods of Customer Management page of Episerver's Commerce Manager
 *
 * @author Shivam.Soni
 */
public class EpiCommerceManagerCustomerManagement extends EpiCommerceManagerHomePage {

    /**
     * Parameterized constructor of the class
     *
     * @param driver object of WebDriver
     */
    public EpiCommerceManagerCustomerManagement(WebDriver driver) {
        super(driver);
    }

    protected String CUSTOMER_FULL_NAME = ".//td[normalize-space(.)='<<text_replace>>']//preceding-sibling::td//input[@type='image']";
    protected By EDIT_CUSTOMER_BUTTON = By.xpath(".//input[@value='Edit']");
    protected By EDIT_CUSTOMER_ALL_LABELS = By.className("labelSmartTableLayoutItem");
    protected By EDIT_CUSTOMER_OK_BUTTON = By.xpath(".//input[@value='OK']");
    protected By CUSTOMER_CODE_LABEL = By.xpath(".//td[normalize-space(.)='Customer code:']");
    protected By CUSTOMER_CODE_BOX = By.xpath(".//td[normalize-space(.)='Customer code:']//following-sibling::td//input");
    protected By CUSTOMER_CLASS_LABEL = By.xpath(".//td[normalize-space(.)='Customer class code:']");
    protected By CUSTOMER_CLASS_BOX = By.xpath(".//td[normalize-space(.)='Customer class code:']//following-sibling::td//input");

    /**
     * Clicks on Edit customer & navigates to edit customer page.
     *
     * @param customerName Customer's Full Name which is to be edited
     */
    public void editCustomer(String customerName) {
        waitForPageLoad();
        switchToRightIframe();
        WebElement editCustomer = wait.waitForElementPresent(By.xpath(CUSTOMER_FULL_NAME.replace("<<text_replace>>", customerName)));
        click.moveToElementAndClick(editCustomer);
        waitForPageLoad();
        wait.waitForAllElementsPresent(EDIT_CUSTOMER_ALL_LABELS);
        window.switchToDefaultContent();
        VertexLogger.log("Editing customer details: " + customerName);
    }

    /**
     * Clicks on OK button on Edit customer page.
     */
    public void clickOkOnEditCustomer() {
        waitForPageLoad();
        switchToRightIframe();
        click.moveToElementAndClick(wait.waitForElementPresent(EDIT_CUSTOMER_OK_BUTTON));
        waitForPageLoad();
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(EDIT_CUSTOMER_OK_BUTTON));
        window.switchToDefaultContent();
        VertexLogger.log("Saved customer details");
    }

    /**
     * Enter or Remove Customer Code
     *
     * @param wantToSet pass true to enter code & false to remove the code
     * @param code      value of customer code
     */
    public void enterOrRemoveCustomerCode(boolean wantToSet, String code) {
        waitForPageLoad();
        switchToRightIframe();
        wait.waitForElementPresent(CUSTOMER_CODE_LABEL);
        WebElement cCode = wait.waitForElementPresent(CUSTOMER_CODE_BOX);
        if (wantToSet) {
            text.enterText(cCode, code);
            VertexLogger.log("Entered Customer Code: " + code);
        } else {
            text.clearText(cCode);
            VertexLogger.log("Removed Customer Code: " + code);
        }
    }

    /**
     * Enter or Remove Customer Class Code
     *
     * @param wantToSet pass true to enter code & false to remove the code
     * @param classCode value of customer code
     */
    public void enterOrRemoveCustomerClass(boolean wantToSet, String classCode) {
        waitForPageLoad();
        switchToRightIframe();
        wait.waitForElementPresent(CUSTOMER_CLASS_LABEL);
        WebElement cCode = wait.waitForElementPresent(CUSTOMER_CLASS_BOX);
        if (wantToSet) {
            text.enterText(cCode, classCode);
            VertexLogger.log("Entered Customer Class Code: " + classCode);
        } else {
            text.clearText(cCode);
            VertexLogger.log("Removed Customer Class Code: " + classCode);
        }
        window.switchToDefaultContent();
    }
}
