package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * All Projects page common methods and object declaration page
 *
 * @author Mario Saint-Fleur
 */


public class DFinanceAllProjectsPage extends DFinanceBasePage {

    protected By PROJECT_TYPE = By.xpath("//div[@data-dyn-controlname='projTable_Type']//div[@title='Open']");
    protected By PROJECT_CONTACT_ID = By.name("ProjInvoiceId");
    protected By CUSTOMER_ACCOUNT = By.name("CustAccount");
    protected By CREATE_PROJECT = By.name("OKCommandButton");
    protected By PROJECT_STAGES = By.xpath("//button[@name='CtrlStages' and contains(@aria-describedby, 'ProjTable')]");
    protected By PROJECT_STAGES_2 = By.xpath("(//button[@name='CtrlStages'])[2]");
    protected By LINES_OPTION = By.xpath("//button[@name='Lines']");
    protected By HOURS_AMOUNT = By.xpath("(//input[@aria-label='Hours'])[2]");
    protected By COST_PRICE = By.xpath("(//input[@aria-label='Cost price'])");
    protected By INVOICE_PROPOSAL = By.xpath("(//button[contains(@name,'PSAProjProposalSelection')])[2]");
    protected By INVOICE_PROPOSAL_1 = By.xpath("(//button[contains(@name,'PSAProjProposalSelection')])");
    protected By PROJECT_TRANSACTION_CHECKBOX = By.xpath("//input[@type='checkbox']//..//div//span");
    protected By PROJECT_INVOICE_PROPOSAL = By.xpath("(//button[@name='ProjInvoiceProposalListPage'])[2]");
    protected By PROJECT_INVOICE_NUMBER = By.xpath("//input[contains(@id, 'ProjInvoiceId')]");
    protected By PROJECT_ID = By.xpath("//input[contains(@name, 'ProjId')]");
    protected By ITEM_NUMBER = By.xpath("//input[@aria-label='Item number']");
    protected By PRODUCT_DIMENSIONS = By.xpath("//li[@title='Product dimensions']");
    protected By SITE = By.name("InventoryDimensions_InventSiteId");
    protected By WAREHOUSE = By.name("InventoryDimensions_InventLocationId");
    protected By SALES_TAX_BUTTON = By.name("SalesTax_Empl");
    protected By FUNCTIONS_BUTTON = By.xpath("(//button[contains(@id, 'Functions_button')])[2]");
    protected By CATEGORY_FIELD = By.xpath("//input[@aria-label='Category']");
    protected By SALES_PRICE_FIELD = By.xpath("//input[@aria-label='Sales price']");
    protected By OK_BUTTON = By.name("Ok");
    protected By ELLIPSE_BUTTON = By.xpath("(//div[@data-dyn-role='OverflowButton'])[8]");
    protected By HOURS = By.xpath("//input[contains(@id,'JournalNameId')]");
    Actions actions = new Actions(driver);

    public DFinanceAllProjectsPage( WebDriver driver )
    {
        super(driver);
    }

    /**
     * Selects the project type
     * @param projectType
     */
    public void selectProjectType(String projectType){
        wait.waitForElementDisplayed(PROJECT_TYPE);
        click.clickElementCarefully(PROJECT_TYPE);

        WebElement projectTypes = wait.waitForElementDisplayed(By.xpath("//li[text()='"+projectType+"']"));
        click.clickElementCarefully(projectTypes);
    }

    /**
     * Selects and enters the project Id
     * @param projectContractId
     */
    public void enterProjectContractId(String projectContractId){
        wait.waitForElementDisplayed(PROJECT_CONTACT_ID);
        text.clickElementAndEnterText(PROJECT_CONTACT_ID, projectContractId);
    }

    /**
     * Selects and enters the customer account
     * @param customerAccount
     */
    public void enterCustomerAccount(String customerAccount){
        wait.waitForElementDisplayed(CUSTOMER_ACCOUNT);
        text.clickElementAndEnterText(CUSTOMER_ACCOUNT, customerAccount);
    }

    /**
     * Clicks the Create Project button
     */
    public void clickCreateProjectButton(){
        wait.waitForElementDisplayed(CREATE_PROJECT);
        click.clickElementCarefully(CREATE_PROJECT);
    }

    /**
     * Clicks the Tab
     * @param clickTab
     */
    public void clickTab(String clickTab){
        WebElement clickSelectTab = wait.waitForElementDisplayed(By.xpath("//button[contains(@id,'"+clickTab+"')]"));
        click.clickElementCarefully(clickSelectTab);
    }

    /**
     * Clicks Project Stage and selects the option
     * @param projectStageOption
     */
    public void selectProjectStageOption(String projectStageOption){
        if(element.isElementPresent(PROJECT_STAGES)) {
            wait.waitForElementDisplayed(PROJECT_STAGES);
            click.clickElementCarefully(PROJECT_STAGES);
        }else if(element.isElementPresent(PROJECT_STAGES_2)){
            wait.waitForElementDisplayed(PROJECT_STAGES_2);
            click.clickElementCarefully(PROJECT_STAGES_2);
        }

        WebElement selectProjectStage = wait.waitForElementDisplayed(By.xpath("//button[@name='"+projectStageOption+"']"));
        click.clickElementCarefully(selectProjectStage);
        jsWaiter.sleep(10000);
    }

    /**
     * Clicks the selected Journal button
     * @param journalType
     */
    public void selectJournalType(String journalType, String elementLocation){
        String elementXpath = "(//button[@name='"+journalType+"'])["+elementLocation+"]";
        WebElement selectedJournalType = wait.waitForElementDisplayed(By.xpath(elementXpath));
        click.clickElementCarefully(selectedJournalType);
    }

    /**
     * Clicks the selected Journal button
     * @param journalType
     */
    public void selectJournalType(String journalType){
        By journalLoc = By.xpath(String.format("//button[@name='%s' and contains(@id, 'ProjTable')]", journalType));
        WebElement selectedJournalType = wait.waitForElementDisplayed(journalLoc);
        click.clickElementIgnoreExceptionAndRetry(selectedJournalType);
    }

    /**
     * Enters hours as the Journal ID
     */
    public void enterHours(){
        WebElement enterHoursValue = wait.waitForElementDisplayed(HOURS);
        text.setTextFieldCarefully(enterHoursValue, "Hour", false);
    }

    /**
     * Click Lines Option
     * @param lineType
     */
    public void clickLinesOption(String lineType){
        WebElement journalType = wait.waitForElementDisplayed(By.xpath("//button[@name='"+lineType+"']"));
        click.clickElementCarefully(journalType);
    }

    /**
     * Clicks and enters the hours value
     * @param hoursAmount
     */
    public void enterHoursAmount(String hoursAmount){
        WebElement enteredHours = wait.waitForElementPresent(HOURS_AMOUNT);
        actions.moveToElement(enteredHours).click(enteredHours).perform();
        text.setTextFieldCarefully(HOURS_AMOUNT, hoursAmount, false);
    }

    /**
     * Clicks and enters the cost price
     * @param costPrice
     */
    public void enterCostPrice(String costPrice){
        WebElement costPriced = wait.waitForElementPresent(COST_PRICE);
        actions.moveToElement(costPriced).click(costPriced).perform();
        text.setTextFieldCarefully(COST_PRICE, costPrice, false);
    }

    /**
     * Enters the item number
     * @param itemNum
     */
    public void enterItemNumber(String itemNum){
        WebElement costPriced = wait.waitForElementPresent(ITEM_NUMBER);
        actions.moveToElement(costPriced).click(costPriced).perform();
        text.setTextFieldCarefully(ITEM_NUMBER, itemNum, false);
    }

    /**
     * Clicks Product Dimensions tab
     */
    public void clickProductDimensions(){
        wait.waitForElementDisplayed(PRODUCT_DIMENSIONS);
        click.clickElementCarefully(PRODUCT_DIMENSIONS);
    }

    /**
     * Enters the site and warehouse
     * @param site
     * @param wareHouse
     */
    public void enterSiteAndWarehouse(String site, String wareHouse){
        WebElement siteElement = wait.waitForElementPresent(SITE);
        actions.moveToElement(siteElement).click(siteElement).perform();
        text.setTextFieldCarefully(siteElement, site, false);

        WebElement warehouseElement = wait.waitForElementPresent(WAREHOUSE);
        actions.moveToElement(warehouseElement).click(warehouseElement).perform();
        text.setTextFieldCarefully(warehouseElement, wareHouse, false);

    }

    /**
     * Enter free amount
     * @param feeAmount
     */
    public void enterFeeAmount(String feeAmount){
        WebElement categoryField = wait.waitForElementDisplayed(CATEGORY_FIELD);
        text.setTextFieldCarefully(categoryField, "Setup", false);

        WebElement salesPrice = wait.waitForElementPresent(SALES_PRICE_FIELD);
        actions.moveToElement(salesPrice).click().perform();
        text.setTextFieldCarefully(salesPrice, feeAmount, false);

    }

    /**
     * Clicks the invoice proposal button in the Manage tab
     */
    public void clickInvoiceProposalAndSelectTransaction(){
        if(element.isElementPresent(INVOICE_PROPOSAL)) {
            wait.waitForElementDisplayed(INVOICE_PROPOSAL);
            click.clickElementCarefully(INVOICE_PROPOSAL);
        }
        else {
            wait.waitForElementDisplayed(INVOICE_PROPOSAL_1);
            click.clickElementCarefully(INVOICE_PROPOSAL_1);
        }
        List<WebElement> projectTransactions = wait.waitForAllElementsPresent(PROJECT_TRANSACTION_CHECKBOX);

        for(int i = 0; i <= projectTransactions.size() - 1; i++){
            if(projectTransactions.size() == 1){
                click.clickElementCarefully(projectTransactions.get(0));
            }if(projectTransactions.size() > 1){
                click.clickElementCarefully(projectTransactions.get(i));
            }
        }
    }

    /**
     * Click Sales Tax Button
     * @param taxName
     */
    public void clickSalesTaxButton(String taxName){
        //wait.waitForElementDisplayed(SALES_TAX_BUTTON);
        WebElement taxElement = wait.waitForElementDisplayed(By.xpath("//button[@name='"+taxName+"']"));
        click.clickElementCarefully(taxElement);
    }

    /**
     * Clicks the ok button in the popup
     */
    public void clickOkButton(){
        wait.waitForElementDisplayed(OK_BUTTON);
        click.clickElementCarefully(OK_BUTTON);
    }

    /**
     * Clicks the new button
     * @param newButtonType
     * @param buttonLocation
     */
    public void clickNewButton(String newButtonType, String buttonLocation){
        WebElement newButton = wait.waitForElementDisplayed(By.xpath("(//button[@name='"+newButtonType+"'])["+buttonLocation+"]"));
        click.clickElementCarefully(newButton);
    }


    /**
     * Clicks Close Button
     * @param buttonLocation
     */
    public void clickCloseButton(String buttonLocation){
        waitForPageLoad();
        jsWaiter.sleep(30000);
        String elementPath = "(//button[@aria-label='Close'])["+buttonLocation+"]";
        try {
            WebElement closeButton = wait.waitForElementDisplayed(By.xpath(elementPath));
            click.clickElementCarefully(closeButton);
        }catch(Exception ex){}
    }

    /**
     * Clicks Back Button
     * @param buttonLocation
     */
    public void clickBackButton(String buttonLocation){
        waitForPageLoad();
        jsWaiter.sleep(30000);
        String elementPath = "(//button[@aria-label='Close']//div//span[@class='button-commandRing Back-symbol'])["+buttonLocation+"]";
        WebElement closeButton = wait.waitForElementDisplayed(By.xpath(elementPath));
        click.clickElementCarefully(closeButton);
    }
    /**
     * Clicks the Project Invoice Proposal
     */
    public void clickProjectInvoiceProposal(){
        wait.waitForElementDisplayed(PROJECT_INVOICE_PROPOSAL);
        click.clickElementCarefully(PROJECT_INVOICE_PROPOSAL);
    }

    /**
     * Gets the projects invoice number
     * @return - project invoice number
     */
    public String getProjectID(){
        wait.waitForElementDisplayed(PROJECT_ID);
        String projectID = attribute.getElementAttribute(PROJECT_ID, "value");
        return projectID;
    }

    /**
     * Gets the projects invoice number
     * @return - project invoice number
     */
    public String getInvoiceNumber(){
        wait.waitForElementPresent(PROJECT_INVOICE_NUMBER);
        String actualSalesTaxAmount = attribute.getElementAttribute(PROJECT_INVOICE_NUMBER, "value");
        return actualSalesTaxAmount;
    }

    /**
     * Clicks the post option in the dropdown
     * @param postingType
     * @param elementLocation
     */
    public void clickPostingType(String postingType, String elementLocation){
       WebElement postType = wait.waitForElementDisplayed(By.xpath("(//button[@name='"+postingType+"'])["+elementLocation+"]"));
       click.clickElementCarefully(postType);
    }

    /**
     * Clicks the inventory dropdown
     */
    public void clickFunctions(){
        wait.waitForElementDisplayed(FUNCTIONS_BUTTON);
        click.clickElementCarefully(FUNCTIONS_BUTTON);
    }

    /**
     * Click on ELlipse button
     */
    public void clickOnEllipse(){
        wait.waitForElementDisplayed(ELLIPSE_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(ELLIPSE_BUTTON);
    }
}
