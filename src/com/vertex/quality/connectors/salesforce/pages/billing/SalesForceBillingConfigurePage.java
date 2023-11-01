package com.vertex.quality.connectors.salesforce.pages.billing;

import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import com.vertex.quality.connectors.salesforce.pages.cpq.SalesForceCPQPostLogInPage;
import com.vertex.quality.connectors.salesforce.pages.cpq.SalesForceCPQSetupPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SalesForceBillingConfigurePage extends SalesForceBasePage {

    protected By DESTINATION_ADDRESS_DROPDOWN = By.xpath("//th/div[contains(text(), 'Tax calculation')]/../.././div[@class='Add for Picklist']/td/div/select");

    protected By SAVE_BUTTON = By.xpath("//button[text()='Save']");
    protected By BACK_TO_HOME_BUTTON = By.xpath("//button[text()='Back To Home']");
    protected By YES_BUTTON = By.xpath("//button[text()='Yes']");

    public SalesForceBillingConfigurePage( WebDriver driver )
    {
        super(driver);
    }

    /**
     * Selects destination address from dropdown menu
     * @param addressSelection
     */
    public void setDestinationAddressDropdown(String addressSelection)
    {
        wait.waitForElementDisplayed(DESTINATION_ADDRESS_DROPDOWN, 120);
        dropdown.selectDropdownByValue(DESTINATION_ADDRESS_DROPDOWN, addressSelection);
        waitForPageLoad();
    }

    /**
     * Checks address is set back to default - Order: Account Billing Address
     */
    public void checkAddressReset()
    {
        String expectedAddress = "Order: Account Billing Address";
        wait.waitForElementDisplayed(DESTINATION_ADDRESS_DROPDOWN);
        String address = dropdown.getDropdownSelectedOption(DESTINATION_ADDRESS_DROPDOWN).getText();
        int i = 0;
        while(!address.equals(expectedAddress) && i < 15)
        {
            i++;
            setDestinationAddressDropdown(expectedAddress);
            clickSaveButton();
            clickBackToHomeButton();
            navigateToBillingConfigurePage();
            wait.waitForElementDisplayed(DESTINATION_ADDRESS_DROPDOWN);
            address = dropdown.getDropdownSelectedOption(DESTINATION_ADDRESS_DROPDOWN).getText();
        }
    }

    /**
     * Click save button
     */
    public void clickSaveButton()
    {
        wait.waitForElementDisplayed(SAVE_BUTTON);
        click.clickElement(SAVE_BUTTON);
        waitForSalesForceLoaded();
    }

    /**
     * Click back to home button on pop up
     */
    public void clickBackToHomeButton()
    {
        wait.waitForElementDisplayed(BACK_TO_HOME_BUTTON);
        click.clickElement(BACK_TO_HOME_BUTTON);
        waitForSalesForceLoaded();
    }

    /**
     * Click yes button on pop up
     */
    public void clickYesButton()
    {
        wait.waitForElementDisplayed(YES_BUTTON);
        click.clickElement(YES_BUTTON);
        waitForSalesForceLoaded();
    }

    /**
     * Navigate to billing configure page via url
     */
    public void navigateToBillingConfigurePage()
    {
        String url = driver.getCurrentUrl();
        url = url.substring(0, url.indexOf(".com/"));
        url = url.replace("https://", "https://blng.");
        url = url.replace("salesforce","visual.force");
        System.out.println(url);
        driver.get(url +".com/apex/BillingConfigure");
    }
}
