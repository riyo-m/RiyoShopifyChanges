package com.vertex.quality.connectors.dynamics365.nav.pages;

import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavBasepage;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class NavVertexDiagnosticsPage extends NavBasepage
{
    protected By dialogBoxLoc = By.className("ms-nav-content");

    protected By backAndSaveArrowButtonLoc = By.cssSelector("i[data-icon-name='Back']");
    protected By typeSelectLoc = By.className("enumerationcontrol-edit");
    protected By accountOrDocumentNumberLoc = By.xpath("//div/a[contains(.,'Account/Document #')]/../div/input");
    protected By sendToEmailAddressLoc = By.xpath("//div/a[contains(.,'Send to Email Address')]/../div/input");
    protected By actionsButtonLoc = By.xpath("(//li/a/span[contains(.,'Actions')])[2]");
    protected By runDiagnosticsButtonLoc = By.xpath("(//span[contains(.,'Diagnostics')])[6]");
    protected By dialogTextLoc = By.className("dialog-title");

    public NavVertexDiagnosticsPage( WebDriver driver ) { super(driver); }

    /**
     * locates the back arrow and clicks on it to save the changes on the page and close it
     */
    public void clickBackAndSaveArrow( )
    {
        List<WebElement> arrowsList = wait.waitForAllElementsPresent(backAndSaveArrowButtonLoc);
        WebElement backArrow = arrowsList.get(arrowsList.size() - 1);
        wait.waitForElementEnabled(backArrow);
        try
        {
            click.clickElement(backArrow);
        }
        catch ( ElementNotInteractableException e )
        {

        }
        wait.waitForElementNotDisplayedOrStale(backArrow, 5);
    }

    public String getDialogBoxText( )
    {
        WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement dialogText = wait.waitForElementDisplayed(dialogTextLoc, dialog);

        String txt = dialogText.getText();

        return txt;
    }

    /**
     * Selects the document or customer type from the dropdown lits
     *
     * @param type
     */
    public void selectType( String type )
    {
        WebElement typeSelectField = wait.waitForElementDisplayed(typeSelectLoc);
        dropdown.selectDropdownByDisplayName(typeSelectField, type);
    }

    /**
     * Inputs the account or document number into the field
     *
     * @param num
     */
    public void inputAccountOrDocumentNumber( String num )
    {
        WebElement field = wait.waitForElementEnabled(accountOrDocumentNumberLoc);
        text.enterText(field, num);
    }

    /**
     * Inputs the email address where results will be sent into the field
     *
     * @param email
     */
    public void inputEmailAddress( String email )
    {
        WebElement field = wait.waitForElementEnabled(sendToEmailAddressLoc);
        text.enterText(field, email);
    }
    /**
     * Inputs the email address where results will be sent into the field
     *
     */
    public void runDiagnostic( )
    {
        WebElement actionsButton = wait.waitForElementEnabled(actionsButtonLoc);
        click.clickElement(actionsButton);

        WebElement runDiagnosticButton = wait.waitForElementDisplayed(runDiagnosticsButtonLoc);
        click.clickElement(runDiagnosticButton);
    }
}
