package com.vertex.quality.connectors.workday.pages;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * this class represents the workday Customer Invoice Review Page
 * contains all the methods necessary to interact with the page
 * @author jdillman
 */

public class WorkdayCustomerInvoiceReviewPage extends VertexPage {

    public WorkdayCustomerInvoiceReviewPage(WebDriver driver) {
        super(driver);
    }

    protected By ellipsis = By.xpath("(//div[@data-automation-id='menuItem']//li)[2]");
    protected By additionalData = By.xpath("//div[@data-automation-label='Additional Data']");
    protected By title = By.cssSelector("span[data-automation-id='pageHeaderTitleText']");
    protected By customerInvoice = By.xpath("//span[text()='VertexCustomerInvoice']");
    protected By invLink = By.xpath("//div[@role='link']");
    protected By custInvTitle = By.xpath("//span[@title='View Customer Invoice']");

    ArrayList<String> ext = new ArrayList<>(Arrays.asList("Freight Amount","Other Charges","On Hold","Memo","Reference Number","External PO Number",
            "Tax Amount","VendorChargedTax","Currency Rate Date Override","Currency Rate Lookup Override","Payment Practices","Locked in Workday",
            "VertexCalculatedTax","Total Adjustment Amount","VertexCallType","VertexCallStatus","VertexTransactionId","VertexPostedFlag","GST/HST %","PST %"));
    ArrayList<String> promptOption = new ArrayList<>(Arrays.asList("Reference Type","Statutory Invoice Type","Prepaid Amortization Type",
            "Default Tax Option","Worktag Split Template","Currency Rate Type Override"));

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
     * This Method helps to navigate from line level custom object to Supplier Invoice Page
     */
    public void navigateToCustomerInvoiceDetails()
    {
        click.clickElementCarefully(invLink);
        wait.waitForElementDisplayed(custInvTitle);
    }

    /**
     * Method verifies VertexTransactionId value
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
     * It gets and verifies VertexTransactionId value
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
            wait.waitForElementDisplayed(customerInvoice);
        }
        String taxStatus = text.getElementText(getbyobjOfFieldsValue("VertexTransactionId",false));
        if (taxStatus == null || taxStatus.isEmpty() || !taxStatus.startsWith("VID_"))
        {
            VertexLogger.log("VertexTransactionId doesn't match with expected value, Actual value is: " + taxStatus);
        }
        return taxStatus;
    }
}
