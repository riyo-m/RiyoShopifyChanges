package com.vertex.quality.connectors.magento.admin.pages;

import com.vertex.quality.connectors.magento.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author alewis
 */
public class M2AdminCustomerInformationPage extends MagentoAdminPage {
    protected By customerInformationSideTabClass = By.className("admin__page-nav-item");
    protected By customerGroupSelectName = By.cssSelector("select[name='customer[group_id]']");
    protected By vertexCustomerCodeName = By.cssSelector(
            "input[name='customer[extension_attributes][vertex_customer_code]']");

    protected By saveButtonId = By.id("save");
    By maskClass = By.className("loading-mask");

    public M2AdminCustomerInformationPage(WebDriver driver) {
        super(driver);
    }

    /**
     * click on account information tab
     */
    public void clickAccountInformationTab() {
        waitForPageLoad();

        wait.waitForElementNotDisplayed(maskClass);

        List<WebElement> tabsList = wait.waitForAllElementsEnabled(customerInformationSideTabClass);

        WebElement tab = element.selectElementByText(tabsList, "Account Information");

        waitForPageLoad();

        click.clickElementCarefully(tab);
    }

    /**
     * click on save customer button
     *
     * @return customersPage  returns object of M2AdminCustomersPage
     */
    public M2AdminCustomersPage clickSaveButton() {
        waitForPageLoad();

        wait.waitForElementNotDisplayed(maskClass);

        WebElement saveButton = wait.waitForElementEnabled(saveButtonId);

        saveButton.click();

        wait.waitForElementNotDisplayedOrStale(saveButton, 5);

        M2AdminCustomersPage customersPage = initializePageObject(M2AdminCustomersPage.class);

        return customersPage;
    }

    /**
     * enter customer code or class
     *
     * @param selectString customer group string to select
     */
    public void selectCustomerGroup(String selectString) {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();

        wait.waitForElementNotDisplayed(maskClass);

        WebElement select = wait.waitForElementEnabled(customerGroupSelectName);

        dropdown.selectDropdownByDisplayName(select, selectString);
    }

    /**
     * enter customer code or class
     *
     * @param inputString Vertex customer code or class
     */
    public void inputVertexCustomerCode(String inputString) {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        wait.waitForElementNotDisplayed(maskClass);

        WebElement field = wait.waitForElementEnabled(vertexCustomerCodeName);

        field.clear();

        field.sendKeys(inputString);
    }

    /**
     * Removes the applied customer code
     */
    public void removeCustomerCode() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement field = wait.waitForElementEnabled(vertexCustomerCodeName);
        field.clear();
    }
}
