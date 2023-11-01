package com.vertex.quality.connectors.magentoTap.admin.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class M2AdminNewProductPage extends MagentoAdminPage {
    By requiredFieldClass = By.className("admin__field");
    By labelClass = By.className("admin__field-label");
    By realFieldClass = By.className("admin__field-control");
    By innerFieldClass = By.className("admin__control-text");
    By selectFieldClass = By.className("admin__control-select");
    By multiSelectFieldClass = By.className("admin__action-multiselect-text");
    By multiSelectLabelClass = By.className("admin__action-multiselect-label");
    By actionBasicClass = By.className("action-basic");
    By filtersClass = By.className("data-grid-filters-actions-wrap");
    By formFieldClass = By.className("admin__form-field");
    By formFieldBoxClass = By.className("admin__form-field-control");
    By checkBoxClass = By.className("data-grid-checkbox-cell-inner");
    By actionPrimaryClass = By.className("action-primary");
    By pageTitleClass = By.className("page-title");
    By dataRowClass = By.className("data-row");
    By saveButtonID = By.id("save-button");
    By maskClass = By.className("loading-mask");
    By saveProductMessage = By.xpath("//div[@id='messages']");

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public M2AdminNewProductPage(final WebDriver driver) {
        super(driver);
    }

    /**
     * Edit SKU by SKU No
     *
     * @param newSku SKU No
     */
    public void editSKU(String newSku) {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        List<WebElement> addProductButtons = wait.waitForAllElementsDisplayed(requiredFieldClass);

        for (WebElement addProductButton : addProductButtons) {
            try {
                String fieldText = text.getElementText(addProductButton);

                if (fieldText.equals("SKU")) {
                    WebElement innerField = wait.waitForElementDisplayed(realFieldClass, addProductButton);
                    WebElement textField = wait.waitForElementDisplayed(innerFieldClass, innerField);
                    textField.clear();
                    click.clickElementCarefully(textField);
                    text.enterText(textField, newSku);
                    click.clickElementCarefully(pageTitleClass);
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Fills in required fields
     *
     * @param prodName       product name
     * @param sku            SKU No
     * @param setTax         Tax value
     * @param taxClassString Tax class value
     * @return M2AdminNewProductPage
     */
    public M2AdminNewProductPage fillOutRequiredField(String prodName, String sku, boolean setTax,
                                                      String taxClassString) {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        waitForPageLoad();
        List<WebElement> addProductButtons = wait.waitForAllElementsDisplayed(requiredFieldClass);

        for (WebElement addProductButton : addProductButtons) {
            try {
                String fieldText = text.getElementText(addProductButton);

                if (fieldText.equals("Product Name")) {
                    WebElement innerField = wait.waitForElementDisplayed(realFieldClass, addProductButton);
                    WebElement textField = wait.waitForElementDisplayed(innerFieldClass, innerField);
                    click.clickElementCarefully(textField);
                    text.enterText(textField, prodName);
                }

                if (fieldText.equals("SKU")) {
                    WebElement innerField = wait.waitForElementDisplayed(realFieldClass, addProductButton);
                    WebElement textField = wait.waitForElementDisplayed(innerFieldClass, innerField);
                    click.clickElementCarefully(textField);
                    text.enterText(textField, sku);
                    click.clickElementCarefully(pageTitleClass);
                }

                if (fieldText.equals("Categories")) {
                    WebElement innerField = wait.waitForElementDisplayed(realFieldClass, addProductButton);
                }

                if (fieldText.contains("Price")) {
                    WebElement textField = wait.waitForElementDisplayed(innerFieldClass, addProductButton);
                    click.clickElementCarefully(textField);
                    text.setTextFieldCarefully(textField, "100");
                }

                if (fieldText.contains("Quantity")) {
                    WebElement textField = wait.waitForElementDisplayed(innerFieldClass, addProductButton);
                    click.clickElementCarefully(textField);
                    text.setTextFieldCarefully(textField, "1000");
                }

                if (setTax == true && fieldText.contains("Tax Class")) {
                    WebElement textField = wait.waitForElementDisplayed(selectFieldClass, addProductButton);
                    dropdown.selectDropdownByDisplayName(textField, taxClassString);
                }
            } catch (Exception e) {

            }
        }

        return initializePageObject(M2AdminNewProductPage.class);
    }

    /**
     * Checks the default value of class
     *
     * @return default value
     */
    public String checkTestClassIsDefault() {
        String displayedText = null;
        waitForPageLoad();
        List<WebElement> addProductButtons = wait.waitForAllElementsDisplayed(requiredFieldClass);

        for (WebElement addProductButton : addProductButtons) {
            try {
                WebElement label = wait.waitForElementDisplayed(labelClass, addProductButton);
                String fieldText = text.getElementText(label);

                if (fieldText.equals("Tax Class")) {
                    WebElement innerField = wait.waitForElementDisplayed(realFieldClass, addProductButton);
                    WebElement textField = wait.waitForElementDisplayed(selectFieldClass, innerField);
                    displayedText = text.getElementText(textField);
                    break;
                }
            } catch (Exception e) {

            }
        }
        return displayedText;
    }

    /**
     * Clicks on the categories button
     */
    public void clickCategoriesButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        List<WebElement> addProductButtons = wait.waitForAllElementsDisplayed(requiredFieldClass);

        for (WebElement addProductButton : addProductButtons) {
            try {
                WebElement label = wait.waitForElementDisplayed(labelClass, addProductButton);
                String fieldText = text.getElementText(label);

                if (fieldText.equals("Categories")) {
                    WebElement innerField = wait.waitForElementDisplayed(realFieldClass, addProductButton);
                    WebElement textField = wait.waitForElementDisplayed(multiSelectFieldClass, innerField);
                    click.clickElementCarefully(textField);
                    WebElement selectDefaultCategory = wait.waitForElementDisplayed(multiSelectLabelClass, innerField);
                    click.clickElementCarefully(selectDefaultCategory);
                    click.clickElementCarefully(pageTitleClass);
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Clicks add product to group button
     */
    public void clickAddProductToGroup() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        List<WebElement> actionBasicButtons = wait.waitForAllElementsDisplayed(actionBasicClass);

        for (WebElement actionBasicButton : actionBasicButtons) {
            String actionBasicText = text.getElementText(actionBasicButton);

            if (actionBasicText.equals("Add Products to Group")) {
                wait.waitForElementDisplayed(actionBasicButton);
                click.clickElementCarefully(actionBasicButton);
                waitForPageLoad();
            }
        }

        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement filter = wait.waitForElementDisplayed(filtersClass);
        click.clickElementCarefully(filter);

        List<WebElement> formFields = wait.waitForAllElementsDisplayed(formFieldClass);

        for (WebElement formField : formFields) {
            String formFieldText = text.getElementText(formField);

            if (formFieldText.equals("Name")) {
                WebElement fieldBox = wait.waitForElementDisplayed(formFieldBoxClass, formField);
                WebElement innerFieldBox = wait.waitForElementDisplayed(innerFieldClass, fieldBox);
                click.clickElementCarefully(fieldBox);
                text.enterText(innerFieldBox, "Virtual", false);
                text.pressEnter(innerFieldBox);
                waitForPageLoad();

                WebElement checkBox = wait.waitForElementDisplayed(checkBoxClass);
                click.clickElementCarefully(checkBox);

                text.enterText(innerFieldBox, "Joust", true);
                text.pressEnter(innerFieldBox);
                waitForPageLoad();
                click.clickElementCarefully(checkBox);

                List<WebElement> actionPrimarys = wait.waitForAllElementsPresent(actionPrimaryClass);

                for (WebElement actionPrimary : actionPrimarys) {
                    String actionPrimaryText = text.getElementText(actionPrimary);

                    if (actionPrimaryText.equals("Add Selected Products")) {
                        click.clickElementCarefully(actionPrimary);
                    }
                }
            }
        }

        List<WebElement> dataRows = wait.waitForAllElementsDisplayed(dataRowClass);

        for (WebElement dataRow : dataRows) {
            WebElement quantityTextField = wait.waitForElementDisplayed(innerFieldClass, dataRow);
            text.setTextFieldCarefully(quantityTextField, "1");
        }
    }

    /**
     * Clicks on the save button
     */
    public void clickSaveButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement saveButton = wait.waitForElementDisplayed(saveButtonID);
        click.clickElementCarefully(saveButton);
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(saveProductMessage);
    }
}