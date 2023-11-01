package com.vertex.quality.connectors.workday.pages;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.*;
import java.io.IOException;

/**
 * This class represents Launch/Schedule Integration Page
 * it contains all the methods necessary to interact with the page for navigation
 *
 * @author dPatel
 */
public class WorkdayLaunchIntegrationPage extends VertexPage {

    public WorkdayLaunchIntegrationPage(WebDriver driver) { super(driver); }

    protected By integInput = By.xpath("(//input[contains(@class,'ejruka1')])[1]");
    protected By customerInvoiceLoad = By.cssSelector("p[title='VtxCustomerInvoiceLoad']");
    protected By batchQuote = By.cssSelector("p[title='VTX-BatchQuoteCustomerInvoiceBP']");
    protected By batchPost = By.cssSelector("p[title='VTX-BatchPostCustomerInvoiceBP']");
    protected By ok = By.cssSelector("span[title='OK']");
    protected By scheduleTitle = By.cssSelector("span[title='Schedule an Integration']");
    protected By refresh = By.xpath("(//div[@data-automation-id='promptOption'])[1]/span");
    protected By status = By.xpath("//div[text()='SUCCESS : All records loaded successfully!!']");
    protected By integCompleted = By.xpath("//div[text()='Integration Completed.']");
    protected By integFailed = By.xpath("//div[text()='Integration Failed.  ']");
    protected By processTime = By.xpath("(//div[@data-automation-id='textView'])[2]");
    protected By viewBackgroundProcess = By.xpath("//span[text()='View Background Process']");
    protected By uploadIcon =By.cssSelector("svg.wd-icon-prompts.wd-icon:last-of-type");
    protected By integAttach = By.cssSelector("div[data-automation-label='Create Integration Attachment']");
    protected By titleintegAttach = By.cssSelector("span[title='Create Integration Attachment']");
    protected By selectFile = By.cssSelector("button[title='Select files']");
    protected By company = By.xpath("(//input[@placeholder='Search'])[2]");
    protected By spectre = By.cssSelector("p[title='Spectre, Inc.']");
    protected By beginInv = By.xpath("(//div[@data-automation-id='numericWidget'])[1]");
    protected By beginInvInput = By.xpath("(//input[@data-automation-id='numericInput'])[1]");
    protected By endInv = By.xpath("(//div[@data-automation-id='numericWidget'])[2]");
    protected By endInvInput = By.xpath("(//input[@data-automation-id='numericInput'])[2]");
    protected By error = By.xpath("//label[text()='Number of Errors']");
    protected By cleanupSimulator =By.cssSelector("p[title='VTX-BatchQuoteCleanupSimulator']");
    protected By supplierInvoiceLoad =By.cssSelector("p[title='VtxSupplierInvoiceLoad']");
    protected By currencyXML = By.xpath("//div[@data-automation-label='Import_Supplier_Inv_Currency_dhruv.xml']");
    protected By lineXML = By.xpath("//div[@data-automation-label='Import_Supplier_Inv_LineLevelCustomObject_dhruv.xml']");
    protected By prorateDiffAdd = By.xpath("//div[@data-automation-label='Import_Supplier_Inv_ProrateDiffAddImport_dhruv.xml']");
    protected By prorate = By.xpath("//div[@data-automation-label='Import_Supplier_Inv_Prorate_dhruv.xml']");
    protected By schedule = By.xpath("//span[@title='Schedule an Integration']");


    /**
     * This method enters the specified Integration to be launched in the text field.
     * It dynamically wait till the text is loaded in the field
     *
     * @param nameOfInteg Name of the Integration
     */
    public void enterIntegration(String nameOfInteg)
    {
        WebElement input = wait.waitForElementEnabled(integInput);
        text.clickElementAndEnterText(input,nameOfInteg);
        input.sendKeys(Keys.TAB);
        try {
            switch (nameOfInteg) {
                case "VtxCustomerInvoiceLoad":
                    wait.waitForElementDisplayed(customerInvoiceLoad);
                    break;
                case "VTX-BatchQuoteCustomerInvoiceBP":
                    wait.waitForElementDisplayed(batchQuote);
                    break;
                case "VTX-BatchPostCustomerInvoiceBP":
                    wait.waitForElementDisplayed(batchPost);
                    break;
                case "VTX-BatchQuoteCleanupSimulator":
                    wait.waitForElementDisplayed(cleanupSimulator);
                    break;
                case "VtxSupplierInvoiceLoad":
                    wait.waitForElementDisplayed(supplierInvoiceLoad);
                    break;
            }
        }
        catch (TimeoutException e)
        {
            VertexLogger.log("*******Integration doesn't exist***********");
        }
    }

    /**
     * This method clicks on "Ok" button
     *
     * @param isFinal true if it is "OK" button of the final page before Integration is launched
     */
    public void clickOK(Boolean isFinal)
    {
        if (isFinal)
        {
            wait.waitForElementDisplayed(schedule);
            click.clickElementCarefully(ok);
            wait.waitForElementDisplayed(viewBackgroundProcess);
        }
        else
        {
            click.clickElementCarefully(ok);
            wait.waitForElementDisplayed(scheduleTitle);
        }

    }

    /**
     * This method uploads EIB XML file from "C:\\upload" folder by executing AutoIt Script
     */
    public void uploadEIBFile(String eibName) {

        wait.waitForElementDisplayed(uploadIcon).click();
        wait.waitForElementEnabled(integAttach).click();
        wait.waitForElementDisplayed(titleintegAttach);
        scroll.scrollElementIntoView(selectFile);
        wait.waitForElementEnabled(selectFile).click();
        try
        {
            Thread.sleep(2000); //Since next step is not using any selemium component I couldn't use dynamic wait
            if (eibName.equalsIgnoreCase("batch"))
            {
                Runtime.getRuntime().exec("C:\\upload\\AutoIT\\fileUpload.exe");
            }
            else if (eibName.equalsIgnoreCase("currency"))
            {
                Runtime.getRuntime().exec("C:\\upload\\AutoIT\\supplierInvoicefileUpload.exe");
                wait.waitForElementDisplayed(currencyXML);
            }
            else if (eibName.equalsIgnoreCase("linelevel"))
            {
                Runtime.getRuntime().exec("C:\\upload\\AutoIT\\supplierInvoiceLineLevelfileUpload.exe");
                wait.waitForElementDisplayed(lineXML);
            }
            else if (eibName.equalsIgnoreCase("prorateDiffAddImport")) {
                Runtime.getRuntime().exec("C:\\upload\\AutoIT\\prorateDiffAddImport.exe");
                wait.waitForElementDisplayed(prorateDiffAdd);
            }
            else
            {
                Runtime.getRuntime().exec("C:\\upload\\AutoIT\\supplierInvoiceProratefileUpload.exe");
                wait.waitForElementDisplayed(prorate);
            }
            click.clickElementCarefully(ok);
        } catch (InterruptedException | IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method waits dynamically till the background process is completed
     *
     * @param isInvoiceLoad true if it is "InvoiceLoad" integration
     *
     * @return true if it completed without any error
     */
    public boolean waitTillBackGroundProcessIsDone(boolean isInvoiceLoad)
    {
        boolean isProcessCompleted = true;
        wait.waitForElementDisplayed(refresh).click();
        waitForPageLoad();
        if (!isInvoiceLoad)
        {
            status = integCompleted;
        }
        boolean isRefreshDisplayed = element.isElementDisplayed(status);


        while (!isRefreshDisplayed)
        {
            try {
                wait.waitForElementDisplayed(refresh).click();
            }
            catch(StaleElementReferenceException e)
            {
                wait.waitForElementDisplayed(refresh).click();
            }
            isRefreshDisplayed = element.isElementDisplayed(status);
            if (element.isElementDisplayed(integFailed))
            {
                VertexLogger.log("Integration Failed");
                break;
            }
        }
        if (element.isElementDisplayed(error))
        {
            VertexLogger.log("Integration was completed with Errors");
            isProcessCompleted = false;
        }
        return isProcessCompleted;
    }

    /**
     * This method gets the processing time after Integration is completed
     *
     * @return processing time in hh:mm:ss
     */
    public String getProcessingTime()
    {
        return text.getElementText(processTime);
    }

    /**
     * This method enters the company Info and dynamically waits till text is successfully loaded
     *
     * @param companyName name of the company to be entered
     */
    public void enterCompanyInfo(String companyName)
    {
        WebElement companyTextBox = wait.waitForElementDisplayed(company);
        click.clickElementCarefully(companyTextBox);
        text.enterText(companyTextBox,companyName);
        companyTextBox.sendKeys(Keys.TAB);
        if (companyName.equals("Spectre, Inc."))
        {
            wait.waitForElementDisplayed(spectre);
        }
    }

    /**
     * This method enters the beginning Invoice number
     *
     * @param invNo Beginning invoice number
     */
    public void enterBeginningInv(String invNo)
    {
        click.clickElementCarefully(beginInv);
        WebElement beginTextBox = wait.waitForElementDisplayed(beginInvInput);
        text.enterText(beginTextBox,invNo);
        beginTextBox.sendKeys(Keys.TAB);
    }

    /**
     * This method enters the Ending Invoice number
     *
     * @param invNo Ending invoice number
     */
    public void enterEndingInv(String invNo)
    {
        click.clickElementCarefully(endInv);
        WebElement endTextBox = wait.waitForElementDisplayed(endInvInput);
        text.enterText(endTextBox,invNo);
        endTextBox.sendKeys(Keys.TAB);
    }

}
