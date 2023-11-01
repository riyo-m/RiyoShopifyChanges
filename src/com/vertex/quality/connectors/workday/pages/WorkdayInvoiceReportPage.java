package com.vertex.quality.connectors.workday.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class represents Invoice Report page for "BatchQuote" and "BatchPost" report
 * it contains all the methods necessary to interact with the page for navigation
 *
 * @author dPatel
 */
public class WorkdayInvoiceReportPage extends VertexPage {

    public WorkdayInvoiceReportPage(WebDriver driver) { super(driver); }

    protected By companyInput = By.xpath("(//input[@placeholder='Search'])[2]");
    protected By spectre = By.cssSelector("p[title='Spectre, Inc.']");
    protected By ok = By.cssSelector("span[title='OK']");
    protected By rowCount = By.cssSelector("label[data-automation-id='rowCountLabel']");
    protected By firstInv = By.xpath("(//div[@data-automation-id='textView'])[1]");
    protected By firstInvPost = By.xpath("(//div[@data-automation-id='textView'])[3]");
    protected By afterDate = By.xpath("(//div[@data-automation-id='dateWidgetContainer'])[1]/div/input");
    protected By beforeDate = By.xpath("(//div[@data-automation-id='dateWidgetContainer'])[2]/div/input");

    /**
     * this method enters the company name in the text box and wait till it's loaded
     *
     * @param company name of the company
     */
    public void enterCompanyName(String company)
    {
        WebElement companyEle = wait.waitForElementDisplayed(companyInput);
        text.enterText(companyEle,company);
        companyEle.sendKeys(Keys.TAB);
        wait.waitForElementDisplayed(spectre);
    }

    /**
     * this method clicks on "Ok" button and dynamically wait till it's loaded
     */
    public void clickOk()
    {
        click.clickElementCarefully(ok);
        wait.waitForElementDisplayed(rowCount);
    }

    /**
     * this method verifies the row counts for "BatchQuote" and "BatchPost" report
     *
     * @param numberOfInv Expected Number of Invoices
     *
     * @return true if verified
     */
    public boolean verifyRowCount(String numberOfInv)
    {
       String value = wait.waitForElementDisplayed(rowCount).getText();
        String[] split = value.split("\\s+");

        return split[0].equals(numberOfInv);
    }

    /**
     * this method gets the first Invoice Number
     *
     * @param isQuote true if "BatchQuote" report
     *
     * @return First Invoice
     */
    public String getFirstInvoiceNumber(boolean isQuote)
    {
        if (isQuote)
        {
            return text.getElementText(firstInv);
        }
        else {
            return text.getElementText(firstInvPost);
        }
    }

    /**
     * this method gets the last Invoice Number from first Invoice number and Total Invoice Number
     *
     * @param firstInv First Invoice Number
     * @param numberOfInvoices Total Invoices
     *
     * @return Last Invoice
     */
    public String getLastInvoiceNumber(String firstInv, int numberOfInvoices)
    {
       int intFirstInv = Integer.parseInt(firstInv);
       return (Integer.toString(intFirstInv + numberOfInvoices - 1));
    }

    /**
     * this method enters after and before date in the respective text fields in the report form
     */
    public void enterAfterBeforeDate()
    {
        String dateFormatted= getTodaysDate();
        text.enterText(afterDate,"04/01/2020");
        text.enterText(beforeDate,dateFormatted);
    }

    /**
     * this method gets the Inbetween of first and last Invoice
     *
     * @param firstInv first Invoice
     * @param range Invoice Range
     *
     * @return Inbetween Invoice
     */
    public String getInbetweenInvoice(int firstInv, int range)
    {
       return Integer.toString(firstInv+(range/2));
    }

    /**
     * this helper method gets today's date in mm/dd/yyyy format
     *
     * @return Date in String format
     */
    public String getTodaysDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * this helper method gets today's date in mm/dd/yyyy format
     *
     * @return Date in String format
     */
    public String getYesterdaysDateMMddyyyy()
    {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        DateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
        System.out.println(dateFormat.format(cal.getTime()));
        return dateFormat.format(cal.getTime());
    }

    /**
     * this helper method gets today's date in mm/dd/yyyy format
     *
     * @return Date in String format
     */
    public String getYesterdaysDateyyyymmdd()
    {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(dateFormat.format(cal.getTime()));
        return dateFormat.format(cal.getTime());
    }


}
