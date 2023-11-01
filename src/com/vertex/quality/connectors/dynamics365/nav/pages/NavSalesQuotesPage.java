package com.vertex.quality.connectors.dynamics365.nav.pages;

import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavSalesBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents the new sales quote page
 *
 * @author bhikshapathi,dpatel
 */
public class NavSalesQuotesPage extends NavSalesBasePage
{
    protected By buttonLoc = By.tagName("button");
    protected By yesButtonConLoc = By.className("ms-nav-actionbar-container");
    protected By popupMessageLoc = By.className("ms-nav-editcontrol-nocaption");
    protected By makeOrderButtonLoc = By.xpath("//span[text()='Order']");

    public NavSalesQuotesPage( WebDriver driver )
    {
        super(driver);
    }

    /**
     * locate and click make order button, under process tab
     */
    public void clickMakeOrder( )
    {
        WebElement makeOrderButton = wait.waitForElementPresent(makeOrderButtonLoc);
        click.clickElementCarefully(makeOrderButton);

    }

    /**
     * locates and clicks on the Yes button from the popup message
     * asking to convert the current quote to an order
     */
    public void clickYesToConvertQuoteToOrder( )
    {
        WebElement messageElement = null;
        String message = "Do you want to convert the quote to an order?";
        WebElement yesButtonContainer = wait.waitForElementPresent(yesButtonConLoc);
        List<WebElement> buttonsList = wait.waitForAllElementsPresent(buttonLoc, yesButtonContainer);
        WebElement yesButton = element.selectElementByText(buttonsList, "Yes");
        messageElement = element.selectElementByText(popupMessageLoc, message);

        if ( messageElement != null )
        {
            click.clickElement(yesButton);
            wait.waitForElementNotDisplayedOrStale(messageElement, shortTimeout);
        }
    }

    /**
     * handles the popup message by clicking on yes button to open the just converted order
     *
     * @return new instance of the edit sales order page
     */
    public NavSalesOrderPage clickYesToOpenTheOrder( )
    {
        WebElement messageElement = null;
        String message = "Do you want to open the new order?";
        WebElement yesButtonContainer = wait.waitForElementPresent(yesButtonConLoc);
        List<WebElement> buttonsList = wait.waitForAllElementsPresent(buttonLoc, yesButtonContainer);
        WebElement yesButton = element.selectElementByText(buttonsList, "Yes");

        List<WebElement> popupMessageContainers = wait.waitForAllElementsPresent(popupMessageLoc);
        for ( WebElement container : popupMessageContainers )
        {
            String containerText = container.getText();
            if ( containerText == null || containerText.isEmpty() )
            {
                System.out.println("unable to find the popup message text");
            }

            else
            {
                if ( containerText.contains(message) )
                {
                    messageElement = container;
                    break;
                }
            }
        }

        click.clickElement(yesButton);
        wait.waitForElementNotDisplayedOrStale(messageElement, shortTimeout);
        return new NavSalesOrderPage(driver);

    }

    /**
     * Converts the sales quote to an order
     *
     * @return the converted sales order
     */
    public NavSalesOrderPage convertQuoteToOrder( )
    {
        clickMakeOrder();
        clickYesToConvertQuoteToOrder();
        NavSalesOrderPage convertedSalesOrder = clickYesToOpenTheOrder();
        return convertedSalesOrder;
    }
}
