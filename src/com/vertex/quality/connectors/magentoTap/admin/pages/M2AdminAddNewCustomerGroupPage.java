package com.vertex.quality.connectors.magentoTap.admin.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Representation of the Add New Customer Page
 *
 * @author alewis
 */
public class M2AdminAddNewCustomerGroupPage extends MagentoAdminPage {
    protected By groupNameFieldId = By.id("customer_group_code");
    protected By taxClassSelectId = By.id("tax_class_id");
    protected By fieldNoteClass = By.className("admin__field-note");

    protected By saveCustomerGroupButtonId = By.id("save");

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public M2AdminAddNewCustomerGroupPage(WebDriver driver) {
        super(driver);
    }

    /**
     * input a group name
     *
     * @param nameString name to input
     */
    public void inputGroupName(String nameString) {
        WebElement field = wait.waitForElementEnabled(groupNameFieldId);
        field.sendKeys(nameString);
    }

    /**
     * select the tax class for the new group
     *
     * @param selectString value of tax class to select
     */
    public void selectTaxClass(String selectString) {
        WebElement field = wait.waitForElementEnabled(taxClassSelectId);
        dropdown.selectDropdownByDisplayName(field, selectString);
    }

    /**
     * click the save customer group button to save
     * the newly created group
     *
     * @return customer groups page
     */
    public M2AdminCustomerGroupsPage clickSaveCustomerGroupButton() {
        WebElement saveButton = wait.waitForElementEnabled(saveCustomerGroupButtonId);
        click.clickElement(saveButton);

        return initializePageObject(M2AdminCustomerGroupsPage.class);
    }

    /**
     * check the tax class currently selected
     * on the dropdown
     *
     * @return displayed text of the currently selected option
     */
    public String checkSelectedTaxClass() {
        wait.waitForElementDisplayed(taxClassSelectId);
        WebElement field = wait.waitForElementEnabled(taxClassSelectId);
        WebElement selected = dropdown.getDropdownSelectedOption(field);
        String selectedString = selected.getText();

        return selectedString;
    }
}
