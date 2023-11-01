package com.vertex.quality.connectors.dynamics365.nav.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.List;

/**
 * Represents the navigation options shared between all different pages editing
 * sales transactions
 *
 * @author bhikshapathi
 */
public class NavSalesEditPagesNavMenu extends VertexComponent
{

    protected By navButtonLoc = By.className("ms-Button-textContainer");
    protected By navButtonConLoc = By.className("ms-Button-flexContainer");

    protected By buttonConLoc = By.className("ms-ResizeGroup");
    protected By buttonLoc = By.className("ms-Button-flexContainer");
    protected By makeInvoiceButtonLoc = By.xpath("//span[@class='ms-cui-ctl-largelabel'][contains(.,'Invoice')]");
    protected By postButtonLoc = By.xpath("//span[@class='ms-cui-ctl-largelabel'][contains(.,'Post...')]");
    protected By postTab = By.xpath("//span[text()='Post...']");
    protected By posting = By.xpath("//a[@title='Posting']");
    protected By postButtonForCreditMemo = By.xpath("(//span[@class='ms-cui-ctl-largelabel'][contains(.,'Post')])[2]");
    protected By postButtonFromInvoice =By.xpath("//span[@class='ms-cui-ctl-largelabel'][contains(.,'Post')]");
    protected By copyDocumentButtonLoc = By.xpath("//span[@class='ms-cui-ctl-mediumlabel'][contains(.,'Copy Document...')]");
    protected By moreOptionsButtonConLoc = By.cssSelector("a[title='Reveal secondary actions']");
    protected By moreOptionsActionsButtonLoc = By.cssSelector("span[aria-label='Actions']");
    protected By recalculateTaxButtonLoc = By.cssSelector("span[aria-label='Recalculate Tax']");
    protected By vertexTaxDetailButtonLoc = By.xpath("(//span[contains(.,'Vertex Tax Details')])[6]");
    protected By vertexTaxDetailFromInvoice = By.xpath("//span[@class='ms-cui-ctl-largelabel'][contains(.,'Details')]");
    protected By VertexTaxDetailsHomePage=By.xpath("//span[text()='Vertex Tax']");
    protected By printButtonLoc = By.xpath("//a/span[text()='Print...']");
    protected By printConfirmationButtonLoc = By.cssSelector("span[aria-label='Print Confirmation...']");
    protected By postingButtonLoc = By.cssSelector("a[aria-label='Posting']");
    protected By postingTabLoc = By.xpath("(//span[contains(.,'Posting')])[6]");
    protected By navigateTabLoc = By.xpath("(//a/span[contains(.,'Navigate')])");
    protected By actionsTab = By.xpath("(//a/span[contains(.,'Actions')])[3]");
    protected By navigateTabLoc1 = By.xpath("(//span[contains(.,'Navigate')])[20]");
    protected By processTab = By.xpath("(//span[contains(.,'Process')])[6]");
    protected By navigateTabInReturn = By.xpath("//span[text()='Navigate' and (not(@aria-hidden))]");
    protected By manage = By.xpath("//span[text()='Manage' and (not(@aria-hidden))]");
    protected By draftInvoiceButtonLoc = By.cssSelector("a[aria-label='Draft Invoice...']");
    protected By testReportButtonLoc = By.cssSelector("a[aria-label='Test Report...']");
    protected By statistics = By.xpath("//span[text()='Statistics']");
    protected By recalculateTax=By.xpath("//span[contains(text(),'Recalculate')]");
    protected By tabActions=By.xpath("//a/span[contains(.,'Actions')and (not(@tabindex))]");
    protected By tabHome=By.xpath("//a/span[contains(.,'Home')and (not(@tabindex))]");

    public NavSalesEditPagesNavMenu( WebDriver driver, VertexPage parent ) {super(driver, parent);}

    /**
     * this method locates the process button
     * from nav panel on the dialog page open and then clicks on it
     */
    public void clickProcess( )
    {
        WebElement processButton = null;
        String expectedText = "Process";
        List<WebElement> listContainers = wait.waitForAllElementsPresent(buttonConLoc);
        for ( WebElement container : listContainers )
        {
            List<WebElement> processButtons = element.getWebElements(buttonLoc, container);
            processButton = element.selectElementByText(processButtons, expectedText);
            if ( processButton != null )
            {
                click.clickElement(processButton);
                break;
            }
        }
    }
    /**
     * Locates the Process button in the process submenu and clicks on it
     * @author bhikshapathi
     */
    public void clickProcessButton( )
    {
        WebElement processButton = wait.waitForElementEnabled(processTab);
        click.clickElement(processButton);
    }
    /**
     * Locates the Make Invoice button in the process submenu and clicks on it
     */
    public void selectMakeInvoiceButton( )
    {
        WebElement makeInvoiceButton = wait.waitForElementEnabled(makeInvoiceButtonLoc);
        click.clickElement(makeInvoiceButton);
    }

    /**
     * Locates Posting button and clicks on it
     */
    public void clickPostingButton( )
    {
        WebElement navigateButton = null;
        String expectedText = "Posting";
        List<WebElement> listContainers = wait.waitForAllElementsPresent(navButtonConLoc);
        for ( WebElement container : listContainers )
        {
            List<WebElement> navigateButtons = container.findElements(navButtonLoc);
            navigateButton = element.selectElementByText(navigateButtons, expectedText);
            if ( navigateButton != null && navigateButton.isDisplayed() )
            {
                try
                {
                    click.clickElement(navigateButton);
                    break;
                }
                catch ( ElementNotInteractableException e )
                {
                    // clicking navigateButton element consistently fails without this, but works fine with the empty catch
                }
            }
        }
    }

    /**
     * Locates the Post button in the posting submenu and clicks on it
     */
    public void selectPostButton( )
    {   waitForPageLoad();
        WebElement postButton = wait.waitForElementEnabled(postButtonLoc);
        click.clickElement(postButton);
    }
    /**
     * Locates the Post button in the posting submenu and clicks on it
     */
    public void selectPost( )
    {   waitForPageLoad();
        if(!element.isElementDisplayed(postTab))
        {
            click.clickElementCarefully(posting);
        }
        WebElement postButton = wait.waitForElementEnabled(postTab);
        click.clickElementCarefully(postButton);
    }
    /**
     * Locates the Post button in the posting submenu and clicks on it
     */
    public void selectPostButtonForCreditMemo( )
    {   waitForPageLoad();
        WebElement postButton = wait.waitForElementEnabled(postButtonForCreditMemo);
        click.clickElement(postButton);
    }
    /**
     * Locates the Post button on Invoice Home Page
     */
    public void selectPostButtonOnInvoicePage(){
        waitForPageLoad();
        WebElement invoicePostButton = wait.waitForElementEnabled(postButtonFromInvoice);
        click.clickElement(invoicePostButton);
    }

    /**
     * Locates the Posting button in the posting submenu and clicks on it
     */
    public void clickPostingTab( )
    {
        WebElement postButton = wait.waitForElementEnabled(postingTabLoc);
        click.clickElement(postButton);
    }
    /**
     * Locates the Posting button in the posting submenu and clicks on it
     */
    public void clickActionsTab( )
    {
        WebElement actionsButton = wait.waitForElementEnabled(actionsTab);
        click.clickElement(actionsButton);
    }

    /**
     * Locates the Prepare button and clicks on it
     */
    public void clickPrepareButton( )
    {
        String expectedText = "Prepare";
        if(element.isElementDisplayed(navButtonConLoc)) {
            List<WebElement> listContainers = wait.waitForAllElementsPresent(navButtonConLoc);

            for ( WebElement container : listContainers )
            {
                List<WebElement> navigateButtons = container.findElements(navButtonLoc);
                WebElement navigateButton = element.selectElementByText(navigateButtons, expectedText);
                if ( navigateButton != null && navigateButton.isDisplayed() )
                {
                    click.clickElement(navigateButton);
                    break;
                }
            }
        }
    }

    /**
     * locates the copy document button and clicks on it in the prepare submenu and
     * clicks on it
     */
    public void selectCopyDocument( )
    {
        WebElement copyDocument = wait.waitForElementEnabled(copyDocumentButtonLoc);
        click.clickElement(copyDocument);
    }

    /**
     * locates navigate button and clicks on it
     */
    public void clickBaseNavigateButton( )
    {
        WebElement navigateButton = null;
        String expectedText = "Navigate";
        List<WebElement> listContainers = wait.waitForAllElementsPresent(navButtonConLoc);
        for ( WebElement container : listContainers )
        {
            List<WebElement> navigateButtons = container.findElements(navButtonLoc);
            navigateButton = element.selectElementByText(navigateButtons, expectedText);
            if ( navigateButton != null && navigateButton.isDisplayed() )
            {
                click.clickElement(navigateButton);
                break;
            }
        }
    }

    /**
     * locates the more options button and clicks on it
     */
    public void clickMoreOptions( )
    {
        String expectedText = "More options";
        List<WebElement> buttonsList = wait.waitForAllElementsPresent(moreOptionsButtonConLoc, 30);
        for ( WebElement button : buttonsList )
        {
            String buttonText = button.getText();
            if ( button != null && button.isDisplayed() && expectedText.equals(buttonText) )
            {
                try
                {
                    click.clickElement(button);
                    break;
                }
                catch ( ElementNotInteractableException e )
                {
                    // clicking navigateButton element consistently fails without this, but works fine with the empty catch
                }
            }
        }
    }

    /**
     * locate and click on the actions button that appears after clicking more options
     */
    public void clickMoreOptionsActionsButton( )
    {
        String expectedText = "Actions";
        List<WebElement> buttonsList = wait.waitForAllElementsPresent(moreOptionsActionsButtonLoc);
        for ( WebElement button : buttonsList )
        {
            String buttonText = button.getText();
            if ( button != null && button.isDisplayed() && expectedText.equals(buttonText) )
            {
                try
                {
                    click.clickElement(button);
                    break;
                }
                catch ( ElementNotInteractableException e )
                {
                    // clicking navigateButton element consistently fails without this, but works fine with the empty catch
                }
            }
        }
    }
    /**
     * locates and clicks on the Actions tab on posted credit memo
     */
    public void selectActionsTab( ){
        WebElement actions = wait.waitForElementEnabled(tabActions);
        click.clickElement(actions);
    }
    /**
     * locate and click on the posting button in the Actions submenu
     */
    public void selectActionsPostingButton( )
    {
        List<WebElement> buttonList = wait.waitForAllElementsDisplayed(postingButtonLoc);
        WebElement postingButton = buttonList.get(buttonList.size() - 1);

        click.clickElement(postingButton);
    }
    /**
     * locates and clicks on the Home tab
     */
    public void selectHomeTab( ){
        WebElement actions = wait.waitForElementEnabled(tabHome);
        click.clickElement(actions);
    }

    /**
     * locate and click on the Draft Invoice button in the Actions/Posting submenu
     * On the invoice page
     */
    public void selectDraftInvoiceButton( )
    {
        WebElement draftButton = wait.waitForElementEnabled(draftInvoiceButtonLoc);
        click.clickElement(draftButton);
    }

    /**
     * locate and click on the Test Report button in the Actions/Posting submenu
     * On the credit memo page
     */
    public void selectTestReportButton( )
    {
        WebElement testReportButton = wait.waitForElementEnabled(testReportButtonLoc);
        click.clickElement(testReportButton);
    }

    /**
     * Locates the Recalculate Tax button in the Actions submenu and clicks on it
     */
    public void selectRecalculateTaxButton( )
    {
        WebElement recalcTaxButton = wait.waitForElementDisplayed(recalculateTaxButtonLoc);
        click.clickElement(recalcTaxButton);
    }

    /**
     * locate and click on the navigate button that appears after clicking more options
     */
    public void clickMoreOptionsNavigateButton( )
    {
        WebElement navigateButton = null;
        String expectedText = "Navigate";
        List<WebElement> listContainers = wait.waitForAllElementsPresent(navButtonConLoc);
        for ( WebElement container : listContainers )
        {
            List<WebElement> navigateButtons = container.findElements(navButtonLoc);
            Collections.reverse(navigateButtons);
            navigateButton = element.selectElementByText(navigateButtons, expectedText);
            if ( navigateButton != null && navigateButton.isDisplayed() )
            {
                try
                {
                    click.clickElement(navigateButton);
                    break;
                }
                catch ( ElementNotInteractableException e )
                {
                    // clicking navigateButton element consistently fails without this, but works fine with the empty catch
                }
            }
        }
    }
    /**
     * locates the vertex tax details button in the
     * more options navigate submenu and clicks on it
     * @author bhikshapathi
     */
    public void selectNavigate( )
    {   WebElement navigateButton = wait.waitForElementPresent(navigateTabInReturn);
        click.clickElement(navigateButton);
    }

    /**
     * Select Manage Tab
     */
    public void selectManage()
    {
        WebElement manageButton = wait.waitForElementPresent(manage);
        click.clickElement(manageButton);
    }

    /**
     * locates the recalculate tax and clicks on it
     */
    public void selectRecalculate(){
        WebElement recalcTax = wait.waitForElementDisplayed(recalculateTax);
        click.clickElement(recalcTax);
    }
    /**
     * select Navigate From Invoice
     * @author bhikshapathi
     */
    public void selectNavigateFromInvoice( )
    {
        WebElement vertexTaxDetailsButton = wait.waitForElementPresent(navigateTabLoc1);

        click.clickElement(vertexTaxDetailsButton);
    }
    /**
     * select Navigate From Invoice
     * @author bhikshapathi
     */
    public void selectNavigateFromReturnOrder( )
    {
        WebElement vertexTaxDetailsButton = wait.waitForElementPresent(navigateTabInReturn);

        click.clickElement(vertexTaxDetailsButton);
    }
    /**
     * locates the vertex tax details button in the
     * more options navigate submenu and clicks on it
     */
    public void selectVertexTaxDetails( )
    {
        WebElement vertexTaxDetailsButton = wait.waitForElementPresent(vertexTaxDetailButtonLoc);
        click.clickElement(vertexTaxDetailsButton);
    }


    /**
     * click on Stat
     */
    public void clickOnStatistics()
    {
        WebElement statisticsButton = wait.waitForElementPresent(statistics);
        click.clickElementCarefully(statisticsButton);
    }

   /**
   * Locates and clicks on Vertex Tax Details from Quote Home Page
   */
    public void clickHomePageVertexTaxDetails(){
        WebElement vertexTaxDetailsHomePage=wait.waitForElementPresent(VertexTaxDetailsHomePage);
        click.clickElement(vertexTaxDetailsHomePage);
    }

    /**
     * locates the vertex tax details button in the
     * more options navigate submenu and clicks on it
     */
    public void selectVertexTaxDetailsFromInvoice( )
    {
        waitForPageLoad();
        WebElement vertexTaxDetailsButton = wait.waitForElementPresent(vertexTaxDetailFromInvoice);
        click.clickElementCarefully(vertexTaxDetailsButton);
    }
    /**
     * locates the print/send button and clicks on it
     */
    public void clickPrintSendButton( )
    {
        WebElement navigateButton = null;
        String expectedText = "Print/Send";
        List<WebElement> listContainers = wait.waitForAllElementsPresent(navButtonConLoc);
        for ( WebElement container : listContainers )
        {
            List<WebElement> navigateButtons = container.findElements(navButtonLoc);
            navigateButton = element.selectElementByText(navigateButtons, expectedText);
            if ( navigateButton != null && navigateButton.isDisplayed() )
            {
                try
                {
                    click.clickElement(navigateButton);
                    break;
                }
                catch ( ElementNotInteractableException e )
                {
                    // clicking navigateButton element consistently fails without this, but works fine with the empty catch
                }
            }
        }
    }

    /**
     * locates the print button in the print/send submenu and clicks on it
     */
    public void selectPrint( )
    {
        WebElement printButton = wait.waitForElementEnabled(printButtonLoc);

        click.clickElement(printButton);
    }

    /**
     * locates the print confirmation button in the print/send submenu and clicks on it
     */
    public void selectPrintConfirmation( )
    {
        WebElement printConfButton = wait.waitForElementEnabled(printConfirmationButtonLoc);

        click.clickElement(printConfButton);
    }
}
