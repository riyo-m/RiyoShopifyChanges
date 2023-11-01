package com.vertex.quality.connectors.magentoTap.admin.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class M2AdminNewAddRulePage extends MagentoAdminPage {

    By requiredClass = By.className("_required");
    By labelClass = By.className("admin__field-label");
    By adminFieldNoteClass = By.className("admin__field-note");
    By textFieldClass = By.className("admin__control-text");
    By selectClass = By.className("admin__control-select");
    By collapsibleClass = By.className("admin__collapsible-title");
    By saveId = By.id("save");
    By xpath = By.xpath("//*[@id=\"container\"]/div/div[2]/div[4]/div[2]/fieldset/div[5]/div[2]/div[1]/label");
    By websiteXpath = By.xpath("//*[@id='AIK3QL3']/option");
    By customerGroupXpath = By.xpath("//*[@id='FMWRO0G']/option[2]");

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public M2AdminNewAddRulePage(final WebDriver driver) {
        super(driver);
    }

    /**
     * Enters the required fields
     */
    public void enterRequiredFields() {
        waitForPageLoad();
        List<WebElement> collapsable = wait.waitForAllElementsDisplayed(collapsibleClass);

        for (WebElement collapsible : collapsable) {
            String collapsibleText = collapsible.getText();

            if (collapsibleText.equals("Actions")) {
                click.clickElementCarefully(collapsible);
                waitForPageLoad();
            }
        }

        List<WebElement> requires = wait.waitForAllElementsDisplayed(requiredClass);

        for (WebElement required : requires) {
            try {
                WebElement label = wait.waitForElementDisplayed(labelClass, required);
                String requiredText = label.getText();

                if (requiredText.equals("Rule Name")) {
                    WebElement textField = wait.waitForElementDisplayed(textFieldClass, required);
                    text.enterText(textField, "discount");
                }

                if (requiredText.equals("Coupon")) {
                    WebElement select = wait.waitForElementDisplayed(selectClass, required);
                    dropdown.selectDropdownByIndex(select, 1);
                }

                if (requiredText.equals("Coupon Code")) {
                    WebElement textField = wait.waitForElementDisplayed(textFieldClass, required);
                    text.enterText(textField, "discount");
                }

                if (requiredText.equals("Discount Amount")) {
                    WebElement textField = wait.waitForElementDisplayed(textFieldClass, required);
                    text.enterText(textField, "100%");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        WebElement adminFieldNote = wait.waitForElementDisplayed(xpath);
        click.clickElementCarefully(adminFieldNote);

        WebElement website = wait.waitForElementDisplayed(websiteXpath);
        click.clickElementCarefully(website);

        WebElement customerGroup = wait.waitForElementDisplayed(customerGroupXpath);
        click.clickElementCarefully(customerGroup);
    }

    /**
     * Clicks the save button
     */
    public void clickSaveButton() {
        WebElement save = wait.waitForElementDisplayed(saveId);
        click.clickElementCarefully(save);
    }
}
