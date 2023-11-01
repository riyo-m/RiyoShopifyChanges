package com.vertex.quality.connectors.dynamics365.business.pages.accountpayable;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessSalesQuotesPage;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessSalesBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BusinessPurchaseQuotePage extends BusinessSalesBasePage {
    public BusinessPurchaseQuotePage(WebDriver driver) {
        super(driver);
    }


    /**
     * handles the popup message by clicking on yes button to open the order be converted
     * @return new instance of the edit purchase order page
     * @author Shruti
     */
    public BusinessPurchaseOrderPage goToOrder() {
        WebElement dialogPopup = wait.waitForElementDisplayed(dialogBoxLoc);
        List<WebElement> buttonsList = wait.waitForAllElementsPresent(buttonLoc, dialogPopup);
        WebElement yesButton = element.selectElementByText(buttonsList, "Yes");
        click.clickElement(yesButton);
        return initializePageObject(BusinessPurchaseOrderPage.class);
    }
    /**
     * Converts the purchase quote to an order
     * @return the converted purchase order
     * @author Shruti
     */
    public BusinessPurchaseOrderPage convertQuoteToOrder(){
        BusinessSalesQuotesPage quotesPage=new BusinessSalesQuotesPage(driver);
        salesEditNavMenu.clickProcessButton();
        quotesPage.clickMakeOrder();
        quotesPage.clickYesToConvertQuoteToOrder();
        BusinessPurchaseOrderPage convertedPurchaseOrder = goToOrder();
        return convertedPurchaseOrder;
    }
}
