package com.vertex.quality.connectors.workday.pages;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * this class represents the workday Supplier Invoice Review Page
 * contains all the methods necessary to interact with the page
 * @author dpatel
 */
public class WorkdaySupplierInvoiceReviewPage extends VertexPage {

    public WorkdaySupplierInvoiceReviewPage(WebDriver driver) {
        super(driver);
    }

    protected By SupplierInvoiceTaxCalcError = By.xpath("(//div[@data-automation-id='textView'])[16]");
    protected By incomplete = By.xpath("//div[@title='Incomplete']");
    protected By complete = By.xpath("//div[@title='Complete']");
    protected By integrations = By.xpath("//div[text()='Dhruv Patel: Integration Completed.']");
    protected By processTab = By.xpath("(//div[text()='Process History'])[1]");
    protected By prepaidTab = By.xpath("(//div[text()='Prepaid Details'])[1]");
    protected By taxTab = By.xpath("(//div[text()='Tax'])[1]");
    protected By additionalFieldsTab = By.xpath("(//div[text()='Additional Fields'])[1]");
    protected By currencyRateTab = By.xpath("(//div[text()='Currency Rate'])[1]");
    protected By transactionTax = By.xpath("//label[text()='Third Party Tax Calculation Status']");
    protected By lineLevelSplitTempl = By.xpath("(//div[@data-automation-label='DP_Sample'])[2]");
    protected By additionalData = By.xpath("//div[@data-automation-label='Additional Data']");
    protected By title = By.cssSelector("span[data-automation-id='pageHeaderTitleText']");
    protected By supplierInvoice = By.xpath("//span[text()='VertexSupplierInvoice']");
    protected By supplierInvoiceLine = By.xpath("//span[text()='VertexSupplierInvoiceLine']");
    protected By supplierInvoiceLineTitle = By.xpath("//span[@title='View Supplier Invoice']");
    protected By ellipsis = By.xpath("(//div[@data-automation-id='menuItem']//li)[2]");
    protected By billable = By.xpath("(//div[@data-automation-id='textView'])[15]");
    protected By firstSearch = By.xpath("//img[@data-automation-id='SEARCH_ICON_charm']");
    protected By suplInvTitle = By.xpath("//span[@title='View Supplier Invoice']");
    protected By invLink = By.xpath("//div[@role='link']");
    protected By inProgress = By.xpath("//div[@title='In Progress']");
    protected By suplInvLink = By.xpath("//div[contains(@title,'Supplier Invoice:')]");
    protected By closeButton = By.xpath("//div[@data-automation-id='closeButton']");
    protected By approved = By.xpath("//div[@title='Approved']");
    protected By vertexTaxPaidLines = By.xpath("//div[@title='VertexTaxPaid: Vertex']//ancestor::tr//div[contains(@title,'maximum')]");
    protected By drillDownTax = By.xpath("//button[@data-automation-id='drillDownNumberLabel']");
    protected By taxPopup = By.xpath("//div[@data-uxi-element-id='popupInfo']");
    protected By taxlineItems = By.xpath("//div[@data-uxi-element-id='popupInfo']//tr[@data-automation-level='0']");
    protected By taxLineSelfAssessed = By.xpath("//div[text()='Tax Self-Assessed']//ancestor::tr//div[contains(@title,'payment')]");
    protected By taxPaid = By.xpath("(//div[text()='Tax Paid']//ancestor::tr//div[contains(@title,'payment')])[1]");
    protected By nonRecovTax = By.xpath("(//caption[text()='Transaction Tax']/..//tr//div[@data-automation-id='numericText'])[7]");
    protected By invoiceAdjustmentLinesTab = By.xpath("(//div[text()='Invoice Lines' or text()='Adjustment Lines'])[1]");
    protected By linesWithItemTaxAmount = By.xpath("((//tr[@data-automation-level='0'])/td[3]/div/div)");
    String lineWithItemName = "((//tr[@data-automation-level='0'])[%s]//div[@data-automation-id='numericText'])[9]";
    String lineWithoutItemName ="((//tr[@data-automation-level='0'])[%s]//div[@data-automation-id='numericText'])[7]";
    String lineWithTaxAmount = "((//tr[@data-automation-level='0'])/td[3]/div/div)[%s]";
    String additionalFieldLoc = "//div[text()='%s']";
    String [] constantTextFields  = {"Supplier",  "Payment Terms", "Override Payment Type", "Default Withholding Tax Code", "Handling Code" , "Document Link" , "Approver",
            "Freight Amount", "Other Charges","On Hold","Memo","Reference Type","Reference Number","External PO Number"};

    String [] constantExpectedValues = {"American Electric Power", "2% 10, net 30", "Check", "USA 30% NRWT", "Messenger", "http://www.hp.com", "Justin Dillman",
            "100.00", "150.00", "Yes","dp_memo", "Other", "123456","123456"};

    String [] lineLevelExpectedValues = {"USA 28% No Tax ID","Amsterdam","AA - Central Africa","Cash Flow Code: Sale","dp_1"};

    String [] gpsInvoices = new String[]{"17878", "17879", "17800"};
    String [] spectreInvoices = new String[]{"17814", "17815", "17816"};

    ArrayList<String> ext = new ArrayList<>(Arrays.asList("Freight Amount","Other Charges","On Hold","Memo","Reference Number","External PO Number",
            "Tax Amount","VendorChargedTax","Currency Rate Date Override","Currency Rate Lookup Override","Payment Practices","Locked in Workday",
            "VertexCalculatedTax","Total Adjustment Amount","VertexCallType","VertexCallStatus","VertexTransactionId","VertexPostedFlag",
            "VertexControlTotalAmount","GST/HST %","PST %"));

    ArrayList<String> promptOption = new ArrayList<>(Arrays.asList("Reference Type","Statutory Invoice Type","Prepaid Amortization Type",
            "Default Tax Option","Worktag Split Template","Currency Rate Type Override"));

    /**
     * It verifies Values for Referenced Invoices after supplier Supplier Invoice is submitted
     *
     * @param company company name
     *
     * @return true if Verified
     */
    public boolean verifyReferencedInvoicesValue(String company)
    {
        boolean isVerified = true;
        String [] invoices;
        if (company.equalsIgnoreCase("GPS"))
        {
            invoices = gpsInvoices;
        }
        else
        {
            invoices = spectreInvoices;
        }
        List<WebElement> elements = wait.waitForAllElementsDisplayed(getbyobjOfFieldsValue("Referenced Invoices",false));
        for (int i = 0; i<elements.size();i++)
            if (!(elements.get(i).getText().contains("178"))) {
                isVerified = false;
                VertexLogger.log("Reference Invoice "+invoices[i]+ "is not expected");
                break;
            }
        return isVerified;
    }

    /**
     * this method verify Tax Calculation error message
     *
     * @param expectedSupplierInvoiceErrorMessage Expected Error Message
     *
     * @return boolean 'true' if successful verification else 'false'
     */

    public boolean verifySupplierInvoiceTaxCalcErrorMessage(String expectedSupplierInvoiceErrorMessage)
    {
        scroll.scrollElementIntoView(SupplierInvoiceTaxCalcError);
        return text.getElementText(SupplierInvoiceTaxCalcError).equalsIgnoreCase(expectedSupplierInvoiceErrorMessage);
    }


    /**
     * It verifies Fixed Text fields value
     *
     * @param textFields TextField
     * @param expectedValues Expected Values
     * @param isLineLevel true if line level details
     *
     * @return true if verified
     */
    public boolean verifyFixedTextFieldsValue (String[] textFields, String [] expectedValues, boolean isLineLevel)
    {
        String value;
        boolean isVerfied = true;

        for (int i = 0 ; i<textFields.length; i++)
        {
            value = text.getElementText(getbyobjOfFieldsValue(textFields[i],isLineLevel));
            if (!(value.equalsIgnoreCase(expectedValues[i])))
            {
                isVerfied= false;
                VertexLogger.log("Verification failed for: "+textFields[i]+ " Actual is: "+ value);
                break;
            }
        }
        return isVerfied;
    }

    /**
     * It verifies Updated Tax Option
     *
     * @param expectedTax Expected Tax Option
     *
     * @return True if verified
     */
    public boolean verifyUpdatedTaxOption(String expectedTax)
    {
        boolean isVerified =  text.getElementText(getbyobjOfFieldsValue("Default Tax Option",false)).equalsIgnoreCase(expectedTax);
        if (!isVerified)
        {
            VertexLogger.log("Updated Tax Option Validation failed");
        }
        return isVerified;
    }

    /**
     * It verifies Total Adjusted Amount
     *
     * @param expectedAmount Expected Tax Option
     *
     * @return True if verified
     */
    public boolean verifyTotalAdjustedAmount(String expectedAmount)
    {
        boolean isVerified =  text.getElementText(getbyobjOfFieldsValue("Total Adjustment Amount",false)).equalsIgnoreCase(expectedAmount);
        if (!isVerified)
        {
            VertexLogger.log("Total Adjustment Amount Validation failed");
        }
        return isVerified;
    }

    /**
     * It verifies Prepaid Details
     *
     * @return true if Verified
     */
    public boolean verifyPrepaidDetails()
    {
        boolean isVerified = false;
        click.clickElementCarefully(prepaidTab);
        WebElement ele = wait.waitForElementDisplayed(getbyobjOfFieldsValue("Prepaid Amortization Type",false));
        if (text.getElementText(ele).equalsIgnoreCase("Schedule"))
        {
            isVerified = true;
        }
        else
        {
            VertexLogger.log("Prepaid Amortization Type is incorrect");
        }
        return isVerified;
    }

    /**
     * It verifies Supplier Contract Information
     *
     * @param company company Name
     *
     * @return true if Verified
     */
    public boolean verifySupplierContractInformation(String company)
    {
        boolean isVerified = true;
        if (company.equalsIgnoreCase("GPS"))
        {
            if (!(text.getElementText(getbyobjOfFieldsValue("Supplier Contract",false)).equalsIgnoreCase("CON-1083: GPSAEP Contract")))
            {
                isVerified = false;
                VertexLogger.log("Supplier Contract Information Validation Failed");
            }
        }
        return isVerified;
    }

    /**
     * It verifies all the fixed text field values at invoice level
     *
     * @param company company
     * @param taxOption tax Option
     *
     * @return true if verified
     */
    public boolean verifyFixedTextFieldsValues(String company,String taxOption)
    {
       boolean isVerified = verifyFixedTextFieldsValue(constantTextFields, constantExpectedValues, false)
               && verifyUpdatedTaxOption(taxOption);
//               && verifyReferencedInvoicesValue(company)
//               && verifySupplierContractInformation(company);
        return isVerified;
    }

    /**
     * It verifies third party intergation status according to "Dafault tax option"
     *
     * @return true if Verified
     */
    public boolean verifyThirdPartyIntegrationStatus()
    {
        boolean isVerified;
        isVerified= element.isElementDisplayed(complete);
        if (!isVerified)
        {
            VertexLogger.log("Third Party Tax Calculation verification failed");
        }
        else
        {
            VertexLogger.log("Third Party Tax Calculation verification is successful");
        }
        return isVerified;
    }

    /**
     * It verified Line level Item Details
     *
     * @param salesItemArray String Array of Line level Sales Item
     *
     * @return true if verified
     */
    public boolean verifyLinelvelDetails(String [] salesItemArray)
    {
        boolean verified = false;
        boolean overallVerified = false;
        String salesItem;
        for (int i = 0; i<salesItemArray.length; i++)
        {
            salesItem = text.getElementText(getByObjOfItem(i+1));
            if (salesItem.equalsIgnoreCase(salesItemArray[i]))
            {
             verified = true;

            }
            else
            {
                System.out.println(text.getElementText(getByObjOfItem(i+1)));
                VertexLogger.log("item:" + salesItemArray[i] +" is not verified, Actual is: "+salesItem);
                verified = false;
                break;
            }
        }
        if (verifyVariableLineItems() && verified)
        {
            overallVerified= true;
        }
        return overallVerified;
    }

    /**
     * It Verifies Calculated Tax
     *
     * @param expectedTax Expected Tax
     *
     * @return True if verified
     */
    public boolean verifyTaxDetails(String expectedTax)
    {
        boolean isVerified = true;
        String amount;
        WebDriverWait fastWait = new WebDriverWait(driver, FIVE_SECOND_TIMEOUT);
        if (!expectedTax.equals("0"))
        {
            fastWait.until(ExpectedConditions.presenceOfElementLocated(getbyobjOfFieldsValue("Tax Amount",false)));
            amount = text.getElementText(getbyobjOfFieldsValue("Tax Amount",false));
            if (!amount.equalsIgnoreCase(expectedTax))
            {
                isVerified = false;
                VertexLogger.log("Verification of Tax Amount Failed : \nExpected : " + expectedTax +"\nActual : "+ amount);
            }
        }
        return isVerified;
    }

    /**
     * It verifies Number of successful Integrations
     *
     * @param company company
     *
     * @return true if verified
     */
    public boolean verifyNumberOfSuccessfulIntergation(String company,String originalTaxOption)
    {
        boolean isVerified = false;
        click.clickElementCarefully(processTab);
        List <WebElement> ele = wait.waitForAllElementsDisplayed(integrations);
        int size = ele.size();
        if ((company.equalsIgnoreCase("GPS")&& size == 2) || (company.equalsIgnoreCase("Spectre, Inc.")&& size == 3))
        {
            isVerified = true;
        }
        else if (!originalTaxOption.equalsIgnoreCase("Enter Tax Due to Supplier"))
        {
            if ((company.equalsIgnoreCase("GPS")&& size == 1) || (company.equalsIgnoreCase("Spectre, Inc.")&& size == 2))
            {
                isVerified = true;
            }
        }
        else
        {
            VertexLogger.log("Number Of Integration doesn't match up");
        }
        return isVerified;
    }

    /**
     * This Method verifies Vendor Charged Tax From custom Object
     *
     * @param expectedTax Expected Tax
     *
     * @return true if Verified
     */
    public boolean verifyVendorChrgedTaxFromCustomObject(String expectedTax)
    {
        boolean isVerified = false;
        Actions performAct = new Actions(driver);
        WebElement element = wait.waitForElementDisplayed(ellipsis);
        performAct.moveToElement(element).click().build().perform();
        wait.waitForElementDisplayed(additionalData);
        hover.hoverOverElement(additionalData);
        Actions actions = new Actions(driver);
        actions.moveToElement(wait.waitForElementDisplayed(additionalData)).build().perform();
        click.javascriptClick(additionalData);
        try
        {
            wait.waitForTextInElement(title, "View All Additional Data");
        }
        catch(StaleElementReferenceException e)
        {
            wait.waitForElementDisplayed(supplierInvoice);
        }
        String vendorChargedTax = text.getElementText(getbyobjOfFieldsValue("VendorChargedTax",false));
        if (expectedTax.equalsIgnoreCase(vendorChargedTax))
        {
            isVerified = true;
        }
        else
        {
            VertexLogger.log("Vendor Charged Tax doesn't match with expected value, Actual value is: " + vendorChargedTax);
        }
        return  isVerified;
    }

    /**
     * It verify VertexCallType value
     * @param expectedTaxType expectedFlag
     * @return true if verified
     */
    public boolean verifyVertexCallType(String expectedTaxType)
    {
        boolean isVerified = false;
        String taxType = text.getElementText(getbyobjOfFieldsValue("VertexCallType",false));
        if (expectedTaxType.equalsIgnoreCase(taxType))
        {
            isVerified = true;
        }
        else
        {
            VertexLogger.log("VertexCallType doesn't match with expected value, Actual value is: " + taxType);
        }
        return isVerified;
    }

    /**
     * It verify VertexCallStatus value
     * @param expectedStatus expectedFlag
     * @return true if verified
     */
    public boolean verifyVertexCallStatus(String expectedStatus)
    {
        boolean isVerified = false;
        String taxStatus = text.getElementText(getbyobjOfFieldsValue("VertexCallStatus",false));
        if (expectedStatus.equalsIgnoreCase(taxStatus))
        {
            isVerified = true;
        }
        else
        {
            VertexLogger.log("VertexCallStatus doesn't match with expected value, Actual value is: " + taxStatus);
        }
        return isVerified;
    }

    /**
     * It verify VertexTransactionId value
     * @return true if verified
     */
    public boolean verifyVertexTransactionId()
    {
        boolean isVerified = false;
        String taxStatus = text.getElementText(getbyobjOfFieldsValue("VertexTransactionId",false));
        if(taxStatus != null && !taxStatus.isEmpty() && taxStatus.startsWith("VID_"))
        {
            isVerified = true;
        }
        else
        {
            VertexLogger.log("VertexTransactionId doesn't match with expected value, Actual value is: " + taxStatus);
        }
        return isVerified;
    }

    /**
     * It verify VertexTransactionId value
     * @return true if verified
     */
    public boolean verifyVertexControlTotalAmount(String expectedAmount)
    {
        boolean isVerified = false;
        String controlTotal = text.getElementText(getbyobjOfFieldsValue("VertexControlTotalAmount",false));
        if (expectedAmount.equalsIgnoreCase(controlTotal))
        {
            isVerified = true;
        }
        else
        {
            VertexLogger.log("VertexControlTotalAmount doesn't match with expected value, Actual value is: " + controlTotal);
        }
        return isVerified;
    }

    /**
     * It gets VertexTransactionId value
     * @return String containing the unique Vertex Transaction ID
     */
    public String getVertexTransactionId()
    {
        Actions performAct = new Actions(driver);
        WebElement element = wait.waitForElementDisplayed(ellipsis);
        performAct.moveToElement(element).click().build().perform();
        wait.waitForElementDisplayed(additionalData);
        hover.hoverOverElement(additionalData);
        Actions actions = new Actions(driver);
        actions.moveToElement(wait.waitForElementDisplayed(additionalData)).build().perform();
        click.clickElementCarefully(additionalData);
        try
        {
            wait.waitForTextInElement(title, "View All Additional Data");
        }
        catch(StaleElementReferenceException e)
        {
            wait.waitForElementDisplayed(supplierInvoice);
        }
        String taxStatus = text.getElementText(getbyobjOfFieldsValue("VertexTransactionId",false));
        if (taxStatus == null || taxStatus.isEmpty() || !taxStatus.startsWith("VID_"))
        {
            VertexLogger.log("VertexTransactionId doesn't match with expected value, Actual value is: " + taxStatus);
        }
        return taxStatus;
    }

    /**
     * It verify Vertex posted flag value
     * @param expectedFlag expectedFlag
     * @return true if verified
     */
    public boolean verifyVertexPostedFlag(String expectedFlag)
    {
        boolean isVerified = false;
        String flagValue = text.getElementText(getbyobjOfFieldsValue("VertexPostedFlag",false));
        if (flagValue.equalsIgnoreCase(expectedFlag))
        {
            isVerified = true;
        }
        else
        {
            VertexLogger.log("VertexPostedFlag doesn't match with expected value, Actual value is: " + flagValue);
        }
        return isVerified;
    }
    /**
     * It gets "By" object of the fields value
     *
     * @param fieldName Name of the Field
     * @param isLineLevel true if it Line level items
     *
     * @return By object
     */
    public By getbyobjOfFieldsValue(String fieldName, boolean isLineLevel)
    {
        if (ext.contains(fieldName))
        {
            return By.xpath(String.format("//label[text()='%s']/../..//div[contains(@data-automation-id,'ext')]",fieldName));
        }

        else if (promptOption.contains(fieldName))
        {
            return By.xpath(String.format("//label[text()='%s']/../..//div[contains(@data-automation-id,'promptOption')]",fieldName));
        }

        else if (!isLineLevel)
        {
            return By.xpath(String.format("//label[text()='%s']/../..//div[@role='link']",fieldName));
        }

        else if (fieldName.equals("Prepaid"))
        {
            return By.xpath("(//tbody//tr[@tabindex='0'][1]//div[@data-automation-id='textView'])[5]");
        }

        else if (fieldName.equals("LineLevelMemo"))
        {
            return By.xpath("(//tbody//tr[@tabindex='0'][1]//div[@data-automation-id='multilineTextView'])//div[@dir='ltr']");
        }

        else
        {
            HashMap<String, String> hmap = new HashMap<>();
            hmap.put("Additional Worktags","17");
            hmap.put("Region","16");
            hmap.put("Location","15");
            hmap.put ("Withholding Tax Code","9");
            hmap.put("Ship-To Contact","6");
            return By.xpath(String.format("(//tbody//tr[@tabindex='0'][1]//div[@data-automation-id='promptOption'])[%s]",hmap.get(fieldName)));
        }

    }

    /**
     * It verify all the Variable line items are the expected one
     *
     * @return true if verified
     */
    public boolean verifyVariableLineItems()
    {
        boolean isVerified = false;
        for (String lineLevelExpectedValue : lineLevelExpectedValues) {
            By obj = By.xpath(String.format("(//tbody//tr[@tabindex='0'][1]//div[text()='%s'])", lineLevelExpectedValue));
            WebElement ele = wait.waitForElementDisplayed(obj);
            if (element.isElementDisplayed(ele)) {
                isVerified = true;
            } else {
                VertexLogger.log("Values :" + lineLevelExpectedValue + "is not present in submitted supplier Invoice");
                break;
            }
        }
        return isVerified;
    }

    /**
     * This method gets "By" object for Sales Item
     *
     * @param lineNumber Line number
     *
     * @return By object
     */
    public By getByObjOfItem(int lineNumber)
    {
        return By.xpath(String.format("(//tbody//tr[@tabindex='0'][%s]//div[@data-automation-id='promptOption'])[3]",lineNumber));
    }


    /**
     * This method gets "By" object for Line Level custom object
     *
     * @param lineNumber Line number
     *
     * @return By object
     */
    public By getByObjOfLineLevelCustomObject(String lineNumber)
    {
        return By.xpath(String.format("(//img[@data-automation-id='RELATED_TASK_charm'])",lineNumber));
    }
    /**
     * This method verifies that Billable chaeck box is checked
     *
     * @return boolean
     */
    public boolean verifyBillableCheckbox()
    {
        boolean isVerified;
        isVerified = text.getElementText(billable).equals("Yes");
        if (!isVerified)
        {
            VertexLogger.log("Billable field is not updated");
        }
        return isVerified;
    }

    /**
     * This method locate and click on first Invoice of selected Name
     */
    public void clickOnFirstGivenInvoice()
    {
        click.clickElementCarefully(firstSearch);
        wait.waitForElementDisplayed(suplInvTitle);
    }

    /**
     * This method wait for 60 seconds (6 of 10 seconds interval) till Invoice is approved
     */
    public void waitTillEIBLoadedInvoiceIsApproved()
    {
        boolean isPresent;
        Actions performAct;
        WebElement ellipsisEle;
        for (int i=0; i<6; i++)
        {
            performAct = new Actions(driver);
            ellipsisEle = wait.waitForElementDisplayed(ellipsis);
            performAct.moveToElement(ellipsisEle).click().build().perform();
            wait.waitForElementDisplayed(suplInvLink);
            click.clickElementCarefully(suplInvLink);
            waitForPageLoad();
            wait.waitForElementNotDisplayed(closeButton);
            isPresent = element.isElementDisplayed(inProgress);
            if (isPresent)
            {
                try
                {
                    wait.waitForElementDisplayed(approved,10);
                }
                catch (TimeoutException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                break;
            }
        }
        if (element.isElementDisplayed(closeButton))
        {
            click.clickElementCarefully(closeButton);
        }
    }

    /**
     * This method locate and click on Tax Tab
     */
    public void clickOnTaxTab()
    {
        click.clickElementCarefully(taxTab);
        wait.waitForElementDisplayed(transactionTax);
    }

    /**
     * This method locates and clicks on the Additional Fields tab
     */
    public void clickOnAdditionalFieldsTab()
    {
        click.clickElementCarefully(additionalFieldsTab);
    }

    /**
     * This method locates and clicks on the invoice line tab
     */
    public void clickOnInvoiceAdjustmentLinesTab()
    {
        click.clickElementCarefully(invoiceAdjustmentLinesTab);
    }

    /**
     * This method locate and click on Currency Rate Tab
     */
    public void clickOnCurrencyRateTab()
    {
        click.clickElementCarefully(currencyRateTab);
    }

    /**
     * This method Verify Line Level Worktag Split Template
     */
    public boolean verifyLineLevelSplitTemplate()
    {
        boolean isVerified;
        isVerified = element.isElementDisplayed(lineLevelSplitTempl);
        if (!isVerified)
        {
            VertexLogger.log("Line Level Worktag Split Template is not verified");
        }
        return isVerified;
    }

    /**
     * It verifies line level Custom Object Vertex Calculated Tax
     * @param lineNo Line Number
     * @param expectedTax expected Tax
     * @return true if verified
     */
    public boolean verifyLineLevelCustomObjectVertexCalculatedTax(String lineNo,String expectedTax)
    {
        boolean isVerified = false;
        Actions performAct = new Actions(driver);
        WebElement element = wait.waitForElementDisplayed(ellipsis);
        scroll.scrollElementIntoView(element);
        performAct.moveToElement(element).click().build().perform();
        wait.waitForElementDisplayed(additionalData);
        hover.hoverOverElement(additionalData);
        Actions actions = new Actions(driver);
        actions.moveToElement(wait.waitForElementDisplayed(additionalData)).build().perform();
        click.clickElementCarefully(additionalData);
        try
        {
            wait.waitForTextInElement(title, "View All Additional Data");
        }
        catch(StaleElementReferenceException e)
        {
            wait.waitForElementDisplayed(supplierInvoiceLine);
        }
        String vertexCalculatedTax = text.getElementText(getbyobjOfFieldsValue("VendorChargedTax",false));
        if (expectedTax.equalsIgnoreCase(vertexCalculatedTax))
        {
            isVerified = true;
        }
        else
        {
            VertexLogger.log("Vertex Calculated Tax doesn't match with expected value, Actual value is: " + vertexCalculatedTax);
        }
        return  isVerified;
    }

    /**
     * This Method verifies Canada tax rates present in custom object from Line Items
     * @param expectedGST
     * @param expectedPST
     * @return boolean true if verified
     */
    public boolean verifyCanadaLineItemsTaxRate(String expectedGST, String expectedPST)
    {
        boolean isVerified= false;
        String actualGSTTaxRate = text.getElementText(getbyobjOfFieldsValue("GST/HST %",false));
        String actualPSTTaxRate = text.getElementText(getbyobjOfFieldsValue("PST %",false));
        if (actualGSTTaxRate.equals(expectedGST)&&actualPSTTaxRate.equals(expectedPST))
        {
            isVerified = true;
        }
        else if (actualGSTTaxRate.equals(expectedGST))
        {
            VertexLogger.log("Line Item Actual PST" + actualPSTTaxRate + "% does not match with expected PST "+ expectedPST + "%");
        }
        else
        {
            VertexLogger.log("Line Item Actual GST/HST" + actualGSTTaxRate + "% does not match with expected PST "+ expectedPST + "%");
        }
        return isVerified;
    }

    /**
     * This Method helps to navigate from line level custom object to Supplier Invoice Page
     */
    public void navigateToSupplierInvoiceFromCustomObject()
    {
        click.clickElementCarefully(invLink);
        wait.waitForElementDisplayed(supplierInvoiceLineTitle);
    }

    /**
     * This Method helps to navigate from line level custom object to Supplier Invoice Page
     */
    public void navigateToSupplierInvoiceDetails()
    {
        click.clickElementCarefully(invLink);
        wait.waitForElementDisplayed(suplInvTitle);
    }

    /**
     * This method helps to navigate to the line level custom object from the Supplier Invoice Page
     */
    public void navigateToCustomObject()
    {
        Actions performAct = new Actions(driver);
        WebElement element = wait.waitForElementDisplayed(ellipsis);
        performAct.moveToElement(element).click().build().perform();
        wait.waitForElementDisplayed(additionalData);
        hover.hoverOverElement(additionalData);
        Actions actions = new Actions(driver);
        actions.moveToElement(wait.waitForElementDisplayed(additionalData)).build().perform();
        click.clickElementCarefully(additionalData);
        try
        {
            wait.waitForTextInElement(title, "View All Additional Data");
        }
        catch(StaleElementReferenceException e)
        {
            wait.waitForElementDisplayed(supplierInvoice);
        }
    }

    /**
     * It gets line level Custom Object Vertex Calculated Tax
     * @param lineNo Line Number
     * @return String of line level tax
     */
    public String getLineLevelCustomObjectVertexCalculatedTax(String lineNo)
    {
        Actions performAct = new Actions(driver);
        WebElement element = wait.waitForElementDisplayed(getByObjOfLineLevelCustomObject(lineNo));
        scroll.scrollElementIntoView(element);
        performAct.moveToElement(element).click().build().perform();
        wait.waitForElementDisplayed(additionalData);
        hover.hoverOverElement(additionalData);
        Actions actions = new Actions(driver);
        actions.moveToElement(wait.waitForElementDisplayed(additionalData)).build().perform();
        click.clickElementCarefully(additionalData);
        try
        {
            wait.waitForTextInElement(title, "View All Additional Data");
        }
        catch(StaleElementReferenceException e)
        {
            wait.waitForElementDisplayed(supplierInvoiceLine);
        }
        return  text.getElementText(getbyobjOfFieldsValue("VertexCalculatedTax",false));
    }

    /**
     * It gets array of line level Custom Object Vertex Calculated Tax
     * @return Array of line level tax
     */
    public String[] getArrayOfLineLevelTaxFromCustomObject()
    {
        List<String> lineLevelTaxes = new ArrayList<>();
        for (int i=1;i<6;i++)
        {
            lineLevelTaxes.add(getLineLevelCustomObjectVertexCalculatedTax(Integer.toString(i)));
            navigateToSupplierInvoiceFromCustomObject();
        }
        String[] arr = lineLevelTaxes.toArray(new String[0]);
        for (String x : arr)
            System.out.print(x + " ");
        return arr;
    }

    /**
     * This method gets all the line level taxes and return String array
     * @return String ArrayofLIneLevelTaxes
     */
    public String[] getAllLineLevelTaxesFromTaxTab()
    {
        List<String> lineLevelVertexTaxPaidList = new ArrayList<>();
        String [] lineLevelVertexTaxPaid;
        int numberOfLine;
        String lineTax;
        WebElement line;
        List<WebElement> lineList;

        click.clickElementCarefully(drillDownTax);
        wait.waitForElementDisplayed(taxPopup);
        lineList = wait.waitForAllElementsPresent(taxlineItems);
        numberOfLine = lineList.size();
        for (int i=1;i<=numberOfLine;i++)
        {
            try
            {
                line = wait.waitForElementPresent(By.xpath(String.format(lineWithItemName,i)),1);

            }
            catch (TimeoutException e)
            {
                line = wait.waitForElementPresent(By.xpath(String.format(lineWithoutItemName,i)));
            }
            scroll.scrollElementIntoView(line);
            lineTax = text.getElementText(line);
            lineLevelVertexTaxPaidList.add(lineTax);
        }
        lineLevelVertexTaxPaid = new String [lineLevelVertexTaxPaidList.size()];
        lineLevelVertexTaxPaidList.toArray(lineLevelVertexTaxPaid);
        return lineLevelVertexTaxPaid;
    }

    /**
     * Verifies tax breakdown of individual items
     *
     * @param tax Array containing Strings of the tax of the items
     *
     *            FUNCTION IS A PLACEHOLDER AND IS NOT FINISHED
     */
    public boolean verifyTaxBreakdown(String[] tax)
    {
        List<String> actualLineLevelTax = new ArrayList<>();
        String [] lineLevelTax;
        int numberOfLine;
        String lineTax;
        WebElement line;
        List<WebElement> lineList;
        boolean isVerified = true;

        click.clickElementCarefully(drillDownTax);
        wait.waitForElementDisplayed(taxPopup);
        lineList = wait.waitForAllElementsPresent(linesWithItemTaxAmount);
        numberOfLine = lineList.size();
        for (int i=1;i<=numberOfLine;i++)
        {
            try
            {
                line = wait.waitForElementPresent(By.xpath(String.format(lineWithTaxAmount,i)),1);


            }
            catch (TimeoutException e)
            {
                line = wait.waitForElementPresent(By.xpath(String.format(lineWithoutItemName,i)));
            }
            scroll.scrollElementIntoView(line);
            lineTax = text.getElementText(line);
            System.out.println(lineTax);
            actualLineLevelTax.add(lineTax);
        }

        lineLevelTax = new String [actualLineLevelTax.size()];
        actualLineLevelTax.toArray(lineLevelTax);
        for (int i=0;i < actualLineLevelTax.size(); i++) {
            if (!actualLineLevelTax.get(i).equals(tax[i])) {
                isVerified = false;
            }
        }
        WebElement close = wait.waitForElementEnabled(closeButton);
        click.moveToElementAndClick(close);

        return isVerified;
    }


    /**
     * This Method will get sum of all Vertex Paid Line Items for Tax Tab and return it as a String
     * @return SumOfVertexTaxPaid
     */
    public String getSumOfAllVertexTaxPaidLineItems()
    {

        List<WebElement> lineEles = wait.waitForAllElementsPresent(vertexTaxPaidLines);
        List<Double> lineLevelTaxes = new ArrayList<>();
        double sum = 0.00;
        Double[] arrayOfVertexTaxes;
        for ( WebElement line : lineEles )
        {
            scroll.scrollElementIntoView(line);
            String tax = text.getElementText(line);
            lineLevelTaxes.add(Double.parseDouble(tax));
        }
        arrayOfVertexTaxes = lineLevelTaxes.toArray(new Double[0]);
        for (Double x : arrayOfVertexTaxes)
        {
            sum+=x;
        }
        return Double.toString(sum);
    }

    /**
     * This Method verified Line Level Tax from Tax type
     * @param taxAmount
     * @return
     */
    public boolean verifyLineLevelSelfAssessedTax(String taxAmount)
    {
        boolean isVerified= false;
        if (text.getElementText(taxLineSelfAssessed).equals(taxAmount))
        {
            isVerified = true;
        }
        else
        {
            VertexLogger.log("Self Assessed Tax Validation Failed");
        }
        return  isVerified;
    }

    /**
     * This Method verified Line Level Tax from Tax type
     * @param taxAmount
     * @return
     */
    public boolean verifyLineLevelTaxPaidTax(String taxAmount)
    {
        boolean isVerified= false;

        if (text.getElementText(taxPaid).equals(taxAmount))
        {
            isVerified = true;
        }
        else
        {
            VertexLogger.log("Vertex Tax- Paid Validation Failed");
        }
        return  isVerified;
    }

    /**
     * This Method verified Line Level Tax from Tax type
     * @param taxAmount
     * @return
     */
    public boolean verifyNonRecoverableTax(String taxAmount)
    {
        boolean isVerified= false;
        if (text.getElementText(nonRecovTax).equals(taxAmount))
        {
            isVerified = true;
        }
        else
        {
            VertexLogger.log("Vertex Non Recoverable Validation Failed");
        }
        return  isVerified;
    }

    public boolean verifyAdditionalFields(String [] additionalFields)
    {
        boolean isVerified = true;
        for (int i = 0; i < additionalFields.length; i++) {
            By fieldLoc = By.xpath(String.format(additionalFieldLoc, additionalFields[i]));
            if (!element.isElementPresent(fieldLoc)) {
                isVerified = false;
            }
        }
        return isVerified;
    }

}
