package com.vertex.quality.connectors.dynamics365.finance.dialogs;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.dynamics365.finance.dialogs.base.DFinanceBaseDialog;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceSynchronizeOrdersPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Contains methods for interacting with dialog on {@link DFinanceSynchronizeOrdersPage}
 */
public class DFinanceSynchronizeOrdersDialog extends DFinanceBaseDialog {

    protected Actions action = new Actions(driver);
    protected By ADD_BUTTON = By.xpath("//button[@data-dyn-controlname='Add']");
    protected By OK_BUTTON = By.xpath("//button[@data-dyn-controlname='CommandButtonOK']");
    public DFinanceSynchronizeOrdersDialog(final WebDriver driver, final VertexPage parent )
    {
        super(driver, parent);
    }

    /**
     * Select an organization in node list
     * @param organization
     */
    public void selectOrganizationNode(String organization) {
        By organizationNodeLoc = By.xpath(String.format("//span[@title='%s']", organization));
        WebElement listElement = wait.waitForElementEnabled(organizationNodeLoc);

        action.moveToElement(listElement).click().perform();
    }

    /**
     * Click add button
     */
    public void clickAddButton() {
        WebElement addButton = wait.waitForElementDisplayed(ADD_BUTTON);
        action.moveToElement(addButton).click().perform();
    }

    /**
     * Click OK button
     */
    public void clickOKButton() {
        WebElement okButton = wait.waitForElementDisplayed(OK_BUTTON);
        action.moveToElement(okButton).click().perform();
    }

}
