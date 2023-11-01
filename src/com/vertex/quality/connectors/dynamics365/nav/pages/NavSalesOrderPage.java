package com.vertex.quality.connectors.dynamics365.nav.pages;

import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavSalesBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * represents the sales order page (dialog looking page)
 *
 * @author bhikshapathi
 */
public class NavSalesOrderPage extends NavSalesBasePage
{   protected By orderDateFieldLoc = By.cssSelector("input[aria-label*='Order Date,']");
    protected By postingDateFieldLoc = By.cssSelector("input[aria-label*='Posting Date,']");
    protected By salesOrderNumberLoc1 = By.xpath("//div[@class='ms-nav-scrollable']/h1");
    protected By okayButton = By.xpath("//button/span[contains(.,'OK')]");
    protected By orderNoFromVertexDetails=By.xpath("//h2[@class='native-container-title' and (not(@tabindex))]");

    protected By messageConLoc = By.className("ms-nav-content");
    protected By dialogButtonCon = By.className("ms-nav-actionbar-container");
    protected By radioButtonContainer = By.className("radio-button-edit-container");

    protected By buttonLoc = By.tagName("button");
    protected By radioLoc = By.tagName("li");
    protected By inputLoc = By.tagName("input");

    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
    String transactionDate = sdf.format(date);

    public NavSalesOrderPage( WebDriver driver )
    {
        super(driver);
    }

    /**
     * locates the order date field and enters today's date in it.
     */
    public void setOrderDate( )
    {
        WebElement orderDateField = wait.waitForElementPresent(orderDateFieldLoc);
        text.enterText(orderDateField, transactionDate);
        text.enterText(orderDateField, transactionDate);
    }

    /**
     * locates the order posting date field and enters today's date in it.
     */
    public void setPostingDate( )
    {
        WebElement postDateField = wait.waitForElementPresent(postingDateFieldLoc);
        text.enterText(postDateField, transactionDate);
        text.enterText(postDateField, transactionDate);
    }

    /**
     * gets the order number from the top of the page
     * will only be present after at least clicking on the items table
     *
     * @return the order number
     */
    public String getOrderNumber( )
    {   waitForPageLoad();
        WebElement title = wait.waitForElementEnabled(salesOrderNumberLoc1);
        String fullText = text.getElementText(title);
        System.out.println(fullText);
        String orderNum = fullText.substring(0, fullText.indexOf(" "));
        return orderNum;
    }

    /**
     * gets the order number from the Vertex Tax details popup
     *
     * @return the orderNumber
     */
    public String getVertexDetailsOrderNumber(){
        waitForPageLoad();
        WebElement heading=wait.waitForElementDisplayed(orderNoFromVertexDetails);
        String title=text.getElementText(heading);
        String docOrderNumber=title.substring(title.indexOf("∙")+1);
        String orderNumber=docOrderNumber.substring(0,docOrderNumber.indexOf("∙")-1).trim();
        return orderNumber;
    }

    /**
     * handles the alerting message about accepting changes on the page.
     */
    public void clickYesToAcceptChangesAndRecalculateTax( )
    {
        WebElement messageCon = wait.waitForElementPresent(messageConLoc);
        List<WebElement> buttonsList = wait.waitForAllElementsPresent(buttonLoc, messageCon);
        WebElement yesButton = element.selectElementByText(buttonsList, "Yes");
        click.clickElement(yesButton);
        wait.waitForElementNotDisplayedOrStale(yesButton, shortTimeout);
    }

    /**
     * When posting an order, select the Ship and Invoice option
     */
    public void selectShipAndInvoicePosting( )
    {
        WebElement radioButton = null;

        WebElement dialogBox = wait.waitForElementDisplayed(messageConLoc);
        WebElement radioButtonCon = wait.waitForElementDisplayed(radioButtonContainer, dialogBox);

        List<WebElement> radioButtonList = wait.waitForAllElementsPresent(radioLoc, radioButtonCon);

        for ( WebElement radio : radioButtonList )
        {
            String label = text.getElementText(radio);
            if ( "Ship and Invoice".equals(label) )
            {
                radioButton = radio.findElement(inputLoc);
                break;
            }
        }

        click.clickElement(radioButton);

        WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialogBox);
        List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonLoc, buttonCon);
        WebElement button = element.selectElementByText(buttonList, "OK");
        click.clickElement(button);

        wait.waitForElementNotDisplayedOrStale(radioButton, 30);
    }
    /**
     * When posting an order, select the Ship and Invoice option
     */
    public void selectReceiveAndInvoicePosting( )
    {
        WebElement radioButton = null;

        WebElement dialogBox = wait.waitForElementDisplayed(messageConLoc);
        WebElement radioButtonCon = wait.waitForElementDisplayed(radioButtonContainer, dialogBox);

        List<WebElement> radioButtonList = wait.waitForAllElementsPresent(radioLoc, radioButtonCon);

        for ( WebElement radio : radioButtonList )
        {
            String label = text.getElementText(radio);
            if ( "Receive and Invoice".equals(label) )
            {
                radioButton = radio.findElement(inputLoc);
                break;
            }
        }

        click.clickElement(radioButton);

        WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialogBox);
        List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonLoc, buttonCon);
        WebElement button = element.selectElementByText(buttonList, "OK");
        click.clickElement(button);

        wait.waitForElementNotDisplayedOrStale(radioButton, 30);
    }
    /**
     * gets the system Returns Error Text
     * @return errorText
     * @author bhikshapathi
     */
    public void systemReturnsErrorText()
    {
        waitForPageLoad();
        WebElement element=wait.waitForElementEnabled(okayButton);
        click.clickElementCarefully(element);
    }
}
