package com.vertex.quality.connectors.dynamics365.nav.pages;

import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavBasepage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * represents Vertex Admin Page
 *
 * @author bhikshapathi
 */
public class NavVertexAdminPage extends NavBasepage
{
    protected By categoryHeadersLocs = By.className("columns-caption-container");
    protected By dialogBoxLoc = By.className("ms-nav-content");
    protected By transparentOverlayLoc = By.className("overlay-transparent");
    protected By backAndSaveArrowButtonLoc = By.cssSelector("i[data-icon-name='Back']");
    protected By flexFieldActionDropdownLoc = By.cssSelector("a[title='Actions for Flex Fields List']");
    protected By flexFieldOptionsLocs = By.className("ms-cui-ctl-mediumlabel");
    protected By commonTableLoc = By.cssSelector("table[id*='BusinessGrid']");
    protected By tableRowLoc = By.cssSelector("tbody tr");
    protected By showMoreLoc = By.cssSelector("a[title='Show more options']");
    protected By dialogButtonCon = By.className("ms-nav-actionbar-container");
    protected By documentNoOpenMenu = By.xpath("(//th[@title='Document/Record No.']/div/a)[2]");
    protected By manageTab = By.xpath("//li/a[@title='Manage']");
    protected By docNoOpenMenu = By.xpath("(//th[@abbr='Document/Record No.'])[2]");
    protected By xmlOpenMenu = By.xpath("(//th[@abbr='XML Type'])[2]");
    protected By flexFieldsOpenMenu = By.xpath("(//th[@abbr='Flex Field Type'])[2]");
    protected By nothingToShow = By.xpath("//table[@summary='VER_XML Log Page']/../div/span[contains(.,'(There is nothing to show in this view)')]");
    protected By documentFilter = By.xpath("(//span[@class='ms-nav-ctxmenu-title'])[3]");
    protected By documentSearch = By.xpath("//div/p[contains(.,'Only show lines where \"Document/Record No.\" is')]/../../../../div/div/div/input");
    protected By xmlSearch = By.xpath("//div/p[contains(.,'Only show lines where \"XML Type\" is')]/../../../../div/div/div/input");
    protected By flexSearch = By.xpath("//div/p[contains(.,'Only show lines where \"Flex Field Type\" is')]/../../../div/div/select");
    protected By submitingButton = By.xpath("//button/span[contains(.,'OK')]");
    protected By submitingButton1 = By.xpath("//div[@class='ms-nav-actionbar-container has-actions']/button[contains(.,'OK')]");
    protected By log_Response = By.xpath("(//table[@summary='VER_XML Log Page']//a)[1]");
    protected By xmlFrame = By.xpath("//div[@class='ms-nav-content']//iframe");
    protected By docLink = By.linkText("Document/Record No.");
    protected By buttonLoc = By.tagName("button");
    protected By responceText1 = By.xpath("//div[@id='controlAddIn']/textarea");
    protected By closeXml = By.xpath("(//span[text()='Close'])[1]/..");
    protected By closeSection = By.xpath("(//div[@title='Save and close the page'])[2]");
    protected By close = By.xpath("//div[@title='Save and close the page'][not(@aria-hidden)]");
    protected By addressCleasingButtonOn= By.xpath("//div/a[contains(.,'Address Cleansing Enabled')]/../div/input");
    protected By invoiceRequestEnabledButton= By.xpath("//div/a[contains(.,'Invoice Request Enabled')]/../div/input");
    protected By addressCleasingField = By.xpath("//div/a[contains(.,'Min. Address Cleansing Confidence Factor')]/../div/input");
    protected By deleteRecord = By.xpath("//li[@class='ms-nav-ctxmenu-item']/a[@title='Delete Line']");
    protected By taxCalculationInputField=By.xpath("//div/a[contains(.,'Tax Calculation Server Address')]/../div/input");
    protected By addValidationInputField=By.xpath("//div/a[contains(.,'Address Validation Server Address')]/../div/input");
    public NavVertexAdminPage( WebDriver driver )
    {
        super(driver);
    }

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

    /**
     * Clicks the header for the XML Logs category
     * to open the section
     */
    public void openXmlLogCategory( )
    {
        List<WebElement> categoriesList = wait.waitForAllElementsPresent(categoryHeadersLocs);
        WebElement xmlLog = element.selectElementByText(categoriesList, "XML Log");
        click.clickElement(xmlLog);
    }
    /**
     * Clicks on filter Documents
     * to open the section
     * @author bhikshapathi
     */
    public void filterDocuments(String docNo) {
        WebElement documentOpenMenu = wait.waitForElementDisplayed(docNoOpenMenu);
        click.moveToElementAndClick(documentOpenMenu);
        WebElement documentOpenMenuArrow = wait.waitForElementDisplayed(documentNoOpenMenu);
        click.moveToElementAndClick(documentOpenMenuArrow);
        click.clickElementCarefully(documentFilter);
        WebElement docSearch =wait.waitForElementDisplayed(documentSearch);
        text.enterText(docSearch,docNo);
        click.clickElementCarefully(submitingButton);
        waitForPageLoad();
    }

    /**
     * Gets the date of the filtered document
     * @return dateValue
     * @author Shruti
     */
    public String checkingDate( ){
        WebElement dateElement=wait.waitForElementDisplayed(log_Response);
        String dateValue=attribute.getElementAttribute(dateElement,"textContent");
        return dateValue;
    }
    /**
     * Clicks on filter Documents
     * to open the section
     * @author bhikshapathi
     */
    public void filterxml(String xmlType ){
        WebElement documentOpenMenu = wait.waitForElementDisplayed(xmlOpenMenu);
        click.moveToElementAndClick(documentOpenMenu);
        click.clickElementCarefully(documentFilter);
        WebElement docSearch =wait.waitForElementDisplayed(xmlSearch);
        text.enterText(docSearch,xmlType);
        click.clickElementCarefully(submitingButton1);
        waitForPageLoad();
    }
    /**
     * Clicks on filter Documents
     * to open the section
     * @author bhikshapathi
     */
    public void filterFlexFields(String xmlType ){
        WebElement documentOpenMenu = wait.waitForElementDisplayed(flexFieldsOpenMenu);
        click.moveToElementAndClick(documentOpenMenu);
        click.clickElementCarefully(documentFilter);
        WebElement docSearch =wait.waitForElementDisplayed(flexSearch);
        click.clickElementCarefully(docSearch);
        dropdown.selectDropdownByDisplayName(docSearch,xmlType);
        waitForPageLoad();
        click.clickElementCarefully(submitingButton1);
        waitForPageLoad();
    }
    /**
     * Clicks the header for the Flex Fields category
     * to open the section
     */
    public void openFlexFieldsCategory( )
    {
        List<WebElement> categoriesList = wait.waitForAllElementsPresent(categoryHeadersLocs);

        WebElement flexFields = element.selectElementByText(categoriesList, "Flex Fields");

        click.clickElement(flexFields);

        //wait.waitForElementDisplayed(flexFieldActionDropdownLoc);
    }

    /**
     * Opens the flex field actions menu and selects the option to create
     * a new flex field
     *
     * @return flex field page
     */
    public NavFlexFieldsPage flexFieldCreateNew( )
    {
        WebElement actions = wait.waitForElementDisplayed(manageTab);
        click.clickElement(actions);

        List<WebElement> actionList = wait.waitForAllElementsDisplayed(flexFieldOptionsLocs);
        WebElement newAction = element.selectElementByText(actionList, "New");
        click.clickElement(newAction);

        return initializePageObject(NavFlexFieldsPage.class);
    }

    /**
     * Finds a flex field in the flex field table based on the information displayed in the row
     *
     * @param source
     * @param type
     * @param id
     * @param value
     *
     * @return row containing the flex field
     */
    public WebElement getFlexFieldRow( String source, String type, String id, String value )
    {
        WebElement getRow = null;
        String expectedText = String.format("%1$s %2$s %3$s %4$s", source, type, id, value);

        List<WebElement> tableList = wait.waitForAllElementsPresent(commonTableLoc);
        WebElement table = tableList.get(tableList.size() - 1);


        List<WebElement> tableRows = wait.waitForAllElementsPresent(tableRowLoc, table);

        for ( WebElement row : tableRows )
        {
            String text = row.getText();
            if ( text.startsWith(expectedText) )
            {
                getRow = row;
                break;
            }
        }

        return getRow;
    }

    /**
     * Opens the flex field
     *
     * @param rowToOpen
     *
     * @return the flex field page
     */
    public NavFlexFieldsPage openFlexFieldRow( WebElement rowToOpen )
    {
        WebElement moreOptionsButton = wait.waitForElementEnabled(showMoreLoc, rowToOpen);
        click.clickElement(moreOptionsButton);

        List<WebElement> actionList = wait.waitForAllElementsDisplayed(flexFieldOptionsLocs);
        WebElement viewAction = element.selectElementByText(actionList, "View");

        click.clickElement(viewAction);

        return initializePageObject(NavFlexFieldsPage.class);
    }

    /**
     * Deletes the flex field row
     *
     * @param rowToDelete
     */
    public void deleteFlexFieldRow( WebElement rowToDelete )
    {
        WebElement moreOptionsButton = wait.waitForElementEnabled(showMoreLoc, rowToDelete);
        click.clickElement(moreOptionsButton);

        WebElement deleteAction=wait.waitForElementDisplayed(deleteRecord);
        click.clickElementCarefully(deleteAction);

        WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialog);
        List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonLoc, buttonCon);
        WebElement button = element.selectElementByText(buttonList, "Yes");
        click.clickElementCarefully(button);

        wait.waitForElementNotDisplayedOrStale(button, 15);
    }

    /**
     * Checks the action options for an existing flex field to ensure there is no option
     * to edit the field
     *
     * @param rowToCheck
     *
     * @return true if no edit option is found, false if one is
     */
    public boolean ensureNoEditOptionOnExistingFlexField( WebElement rowToCheck )
    {
        boolean noEdit = true;

        WebElement moreOptionsButton = wait.waitForElementEnabled(showMoreLoc, rowToCheck);
        click.clickElement(moreOptionsButton);

        List<WebElement> actionList = wait.waitForAllElementsDisplayed(flexFieldOptionsLocs);

        for ( WebElement actionOption : actionList )
        {
            String text = actionOption.getText();
            if ( "Edit".equalsIgnoreCase(text) )
            {
                noEdit = false;
            }
        }

        WebElement overlay = wait.waitForElementPresent(transparentOverlayLoc);
        click.clickElement(overlay);

        return noEdit;
    }

    /**
     * Checks each row of the XML log table to see if the given document number
     * exists in the table (so, if there is an XML log for that document)
     *
     * @param docNumber
     *
     * @return whether the document number is present, as a boolean
     */
    public boolean checkXmlTableForDocumentNumber( String docNumber )
    {
        boolean numPresent = false;

        List<WebElement> tableList = wait.waitForAllElementsPresent(commonTableLoc);
        WebElement xmlTable = tableList.get(tableList.size() - 1);
        List<WebElement> tableRows = wait.waitForAllElementsPresent(tableRowLoc, xmlTable);

        for ( WebElement row : tableRows )
        {
            String rowText = row.getText();
            if ( rowText.contains(docNumber) )
            {
                numPresent = true;
                break;
            }
        }

        return numPresent;
    }
    /**
     * Open xml file based on the  type  from table
     * @return xml content text
     * @author dpatel, bhikshapathi
     */
    public String clickOnFirstLinkAndGetTheXml()
    {
        String response_Request_Details;
        click.clickElementCarefully(docLink);
        scroll.scrollElementIntoView(log_Response);
        click.javascriptClick(log_Response);
        waitForPageLoad();
        WebElement xmlframe = wait.waitForElementPresent(xmlFrame);
        driver.switchTo().frame(xmlframe);
        WebDriverWait shortWait = new WebDriverWait(driver, SIX_SECOND_TIMEOUT);
        WebElement ele = wait.waitForElementDisplayed(responceText1);
        response_Request_Details= attribute.getElementAttribute(ele,"value");
        driver.switchTo().parentFrame();
        shortWait
                .ignoring(TimeoutException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(closeXml));
        click.moveToElementAndClick(closeXml);
        return response_Request_Details;
    }
    /**
     * Open xml file based on the  type  from table
     * @return xml content text
     * @author dpatel, bhikshapathi
     */
    public String clickOnFirstAddressCleansingLinkAndGetTheXml(String xmlType)
    {
        String response_Request_Details;
        hover.hoverOverElement(docLink);
        click.clickElementCarefully(docLink);
        String dateTime=getTime();
        String itemRow = String.format("//tbody/tr[1]/td[3]/a[contains(text(),'%s')]", dateTime);
        click.clickElement(By.xpath(itemRow));
        waitForPageLoad();
        WebElement xmlframe = wait.waitForElementPresent(xmlFrame);
        driver.switchTo().frame(xmlframe);
        WebDriverWait shortWait = new WebDriverWait(driver, SIX_SECOND_TIMEOUT);
        WebElement ele = wait.waitForElementDisplayed(responceText1);
        response_Request_Details= attribute.getElementAttribute(ele,"value");
        driver.switchTo().parentFrame();
        shortWait
                .ignoring(TimeoutException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(closeXml));
        click.moveToElementAndClick(closeXml);
        return response_Request_Details;
    }
    /**
     * Generalized method, get Latest Xml
     */
    public String getLatestXml()
    {
        WebElement element=wait.waitForElementDisplayed(log_Response);
        String date=element.getText();
        return  date;
    }
    /**
     * Generalized method, to get Current System time
     */
    public String getTime()
    {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
        String transactionDate = sdf.format(date);
        return transactionDate;
    }
    /**
     * Generalized method, for update Confidence Factor
     * @author bhikshapathi
     */
    public void updateConfidenceFactor(String AddresCleansing)
    {
        WebElement element=wait.waitForElementDisplayed(addressCleasingField);
        text.clearText(element);
        text.enterText(element,AddresCleansing);
        element.sendKeys(Keys.TAB);
        waitForPageLoad();
    }
    /**
     * Generalized method, for invoice Request Enabled On
     * @author bhikshapathi
     */
    public void invoiceRequestEnabledOn()
    {   waitForPageLoad();
        WebElement element=wait.waitForElementDisplayed(invoiceRequestEnabledButton);
        click.clickElementCarefully(element);
        waitForPageLoad();
    }
    /**
     * Generalized method, for update Address Cleansing Off
     * @author bhikshapathi
     */
    public void updateAddressCleansingOn()
    {   waitForPageLoad();
        WebElement element=wait.waitForElementDisplayed(addressCleasingButtonOn);
        if(!element.isSelected()) {
             click.clickElementCarefully(element);
        }
        waitForPageLoad();
    }
    /**
     * Generalized method, for close Admin Section
     * @author bhikshapathi
     */
    public void closeAdminSection()
    {   waitForPageLoad();
        WebElement element=wait.waitForElementDisplayed(closeSection);
        click.clickElementCarefully(element);
        waitForPageLoad();
    }
    /**
     * Generalized method, for close Admin Section
     * @author bhikshapathi
     */
    public void SavecloseAdminSection()
    {   waitForPageLoad();
        WebElement element=wait.waitForElementDisplayed(close);
        click.clickElementCarefully(element);
        waitForPageLoad();
    }
    /**
     * Verify No xml after updating address
     * to open the section
     */
    public String checkNoXmlLog( )
    {
        WebElement xmlLog = wait.waitForElementDisplayed(nothingToShow);
        String response=xmlLog.getText();
        return response;
    }
    /**
     * Generalized method, for update Address Cleansing Off
     * @author bhikshapathi
     */
    public void updateAddressCleansingOff()
    {   waitForPageLoad();
        WebElement element=wait.waitForElementDisplayed(addressCleasingButtonOn);
        if(element.isSelected()) {
             click.clickElementCarefully(element);
        }
        waitForPageLoad();
    }
    /**
     * to update URL of Tax Calculation and Address Validation
     * @author Shruti Jituri
     */
    public void fillInURLAddress(String taxCalculation, String addValidation){
        waitForPageLoad();
        WebElement taxCalField=wait.waitForElementDisplayed(taxCalculationInputField);
        click.clickElementCarefully(taxCalField);
        text.clearText(taxCalField);
        text.enterText(taxCalField,taxCalculation);
        taxCalField.sendKeys(Keys.TAB);
        WebElement addValidationField= wait.waitForElementDisplayed(addValidationInputField);
        click.clickElementCarefully(addValidationField);
        text.clearText(addValidationField);
        text.enterText(addValidationField,addValidation);
    }
}
