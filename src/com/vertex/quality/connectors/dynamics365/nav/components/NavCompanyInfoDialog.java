package com.vertex.quality.connectors.dynamics365.nav.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessManualSetupPage;
import com.vertex.quality.connectors.dynamics365.nav.pages.NavManualSetupPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents the company info dialog from the manual setup page
 * contains all the necessary methods to interact with the dialog
 *
 * @author bhikshapathi
 */
public class NavCompanyInfoDialog extends VertexComponent
{
    public NavCompanyInfoDialog(WebDriver driver, VertexPage parent )
    {
        super(driver, parent);
    }
    protected By zipCodeFieldLoc = By.xpath("//div/a[contains(.,'ZIP Code')]/../div/input");
    protected By backAndSaveArrowButtonLoc = By.cssSelector("i[data-icon-name='Back']");
    protected By dialogBoxLoc = By.className("ms-nav-content");
    protected By dialogTextLoc = By.className("dialog-title");
    /**
     * locates the company zip code field and enters zip code into it
     */
    public void enterWrongZip(String zip)
    {
        WebElement zipCodeField = wait.waitForElementDisplayed(zipCodeFieldLoc);
        click.clickElement(zipCodeField);
        text.enterText(zipCodeField,zip);
    }

    /**
     * locates the clicks on the back and save arrow button
     *
     * @return instance of the manual setup page
     */
    public NavManualSetupPage closeDialog( )
    {
        WebElement backArrow = wait.waitForElementDisplayed(backAndSaveArrowButtonLoc);
        click.clickElement(backArrow);
        return new NavManualSetupPage(driver);
    }

    /**
     * locates the zip code field and extracts the test from it
     *
     * @return text in the zip code field
     */
    public String getZipCodeFromField( )
    {
        WebElement zipCodeField = wait.waitForElementDisplayed(zipCodeFieldLoc);
        String zipCode = zipCodeField.getAttribute("value");

        return zipCode;
    }

    /**
     * locates the dialog box and extracts the text from it
     * @return text in dialog box
     */
    public String getDialogBoxText( )
    {
        WebElement dialogBox = wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement dialogText = wait.waitForElementDisplayed(dialogTextLoc, dialogBox);
        String dialogtxt = dialogText.getText();
        return dialogtxt;
    }
}
