package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * This page class contains methods to test update of Customer Name in VCS after clicking on Reload icon
 *
 * @author Shilpi.Verma
 */
public class TaxlinkSessionReload_VCSPage extends TaxLinkBasePage {
    /**
     * Constructor of TaxLinkBasePage class
     * inheriting properties of VertexPage
     *
     * @param driver
     */
    public TaxlinkSessionReload_VCSPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//select [@id='SelectedTenantId']")
    private WebElement availableClients;

    @FindBy(xpath = "//label [contains (@class , 'header_customerName')]")
    private WebElement customerName;

    @FindBy(xpath = "//label[contains(@class, 'header_reloadLabel')]/strong")
    private WebElement reloadIcon;

    /**
     * Method to verify update of Customer name after clicking on Reload icon
     *
     * @return
     */
    public boolean reloadSession() {
        boolean flag = false;

        expWait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(availableClients, By.tagName("option")));
        String currentClient = dropdown
                .getDropdownSelectedOption(availableClients)
                .getText();

        new TaxLinkSignOnPage(driver).launchStageAccelerator();
        wait.waitForElementDisplayed(customerName);

        String custName = customerName.getText();
        String[] name = custName.split(":");

        boolean client_flag = false;
        if (name[1].trim().equals(currentClient)) {
            client_flag = true;
        }

        if (client_flag) {

            Object[] a = driver.getWindowHandles().toArray();
            driver.switchTo().window(a[0].toString());

            dropdown.selectDropdownByIndex(availableClients, 1);
            String changedClient = dropdown
                    .getDropdownSelectedOption(availableClients)
                    .getText();

            driver.switchTo().window(a[1].toString());
            click.clickElement(reloadIcon);
            wait.waitForElementDisplayed(reloadIcon);

            String changedCustName = customerName.getText();
            String[] changedName = changedCustName.split(":");

            if (changedName[1].trim().equals(changedClient)) {
                flag = true;
                VertexLogger.log("Clients change is reflecting correctly after clicking on Reload icon");
            } else {
                VertexLogger.log("Clients change is NOT reflecting correctly after clicking on Reload icon");
            }

        }
        return flag;
    }
}
