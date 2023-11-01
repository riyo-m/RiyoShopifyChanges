package com.vertex.quality.connectors.episerver.pages.oseries;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Taxpayers page actions of O-Series
 *
 * @author Shivam.Soni
 */
public class OSeriesTaxpayers extends VertexPage {

    public OSeriesTaxpayers(final WebDriver driver) {
        super(driver);
    }

    protected By myEnterprise = By.xpath("//*[contains(text(),'My Enterprise')]");
    protected By taxPayers = By.xpath("//a[text()='Taxpayers']");
    protected By showActions = By.xpath("(//*[contains(text(),'Show Actions')]//parent::button)");
    protected By showActionsEdit = By.xpath(".//a[@data-id='edit']");
    protected By shippingTermsDropdown = By.xpath(".//label[normalize-space(.)='Shipping Terms']/following-sibling::div");
    protected By shippingTermTextBox = By.xpath(".//label[normalize-space(.)='Shipping Terms']/following-sibling::div//input");
    protected By clearShippingTerms = By.xpath(".//*[@href='#i-cross']");
    protected By saveButton = By.xpath(".//button[normalize-space(.)='SAVE']");
    protected By cancelButton = By.xpath(".//button[normalize-space(.)='CANCEL']");

    /**
     * Select My Enterprise tab from home page side menu
     * example: oSeriesTaxPayers.selectMyEnterpriseTab();
     */
    public void selectMyEnterpriseTab() {
        WebElement myEnterpriseField = wait.waitForElementPresent(myEnterprise, 200);
        click.moveToElementAndClick(myEnterpriseField);
        waitForPageLoad();
    }

    /**
     * Select TaxPayer's tab from home page side menu
     * example: oSeriesTaxPayers.selectTaxPayersTab();
     */
    public void selectTaxPayersTab() {
        waitForPageLoad();
        WebElement taxPayersField = wait.waitForElementPresent(taxPayers);
        click.moveToElementAndClick(taxPayersField);
        waitForPageLoad();
    }

    /**
     * Click on Show Actions and click on Edit button of Tax Payers
     * example: oSeriesTaxPayers.editTaxPayer();
     */
    public void editTaxPayer() {
        WebElement showAction = wait.waitForElementPresent(showActions);
        click.moveToElementAndClick(showAction);
        WebElement editTaxPayer = wait.waitForElementPresent(showActionsEdit);
        click.moveToElementAndClick(editTaxPayer);
        waitForPageLoad();
    }

    /**
     * Search shipping term and selects appropriate Shipping Term from the list
     * example: oSeriesTaxPayers.selectShippingTerm("SUP");
     *
     * @param shipTerm any value of term will set particular shipping term
     */
    public void selectShippingTerm(String shipTerm) {
        WebElement shipTermDropdown = wait.waitForElementPresent(shippingTermsDropdown);
        click.moveToElementAndClick(shipTermDropdown);
        WebElement shipTermBox = wait.waitForElementPresent(shippingTermTextBox);
        text.enterText(shipTermBox, shipTerm);
        text.pressEnter(shipTermBox);
    }

    /**
     * Clear selected shipping terms
     * example: oSeriesTaxPayers.clearShippingTerm();
     */
    public void clearShippingTerm() {
        waitForPageLoad();
        wait.waitForElementPresent(shippingTermTextBox);
        if (element.isElementDisplayed(clearShippingTerms)) {
            WebElement clearShip = wait.waitForElementPresent(clearShippingTerms);
            click.moveToElementAndClick(clearShip);
        }
    }

    /**
     * Save all modified settings
     * example: oSeriesTaxPayers.saveSettings();
     */
    public void saveSettings() {
        WebElement save = wait.waitForElementPresent(saveButton);
        click.moveToElementAndClick(save);
        waitForPageLoad();
        // Intentional hard-wait for 3 minutes because it takes certain time to update the settings in DB.
        jsWaiter.sleep(180000);
    }

    /**
     * Go to My Enterprise, edit tax-payer & update shipping term or clear selected shipping term
     * To select any shipping term pass isSelect=true & appropriate shipping term value
     * To clear shipping term pass isSelect=false & rather than passing any value pass blank value.
     * example 1: oSeriesTax.updateShippingTerms(true, "SUP");
     * example 2: oSeriesTax.updateShippingTerms(false, "");
     *
     * @param isSelect isSelect value will be either set or clears the shipping terms value
     * @param term     term value will set particular shipping term & blank value will clear shipping term
     */
    public void updateShippingTerms(boolean isSelect, String term) {
        selectMyEnterpriseTab();
        selectTaxPayersTab();
        editTaxPayer();
        if (isSelect) {
            selectShippingTerm(term);
        } else {
            clearShippingTerm();
        }
        saveSettings();
    }
}
