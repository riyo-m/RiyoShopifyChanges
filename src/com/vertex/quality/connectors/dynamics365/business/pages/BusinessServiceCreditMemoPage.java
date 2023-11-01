package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessServiceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents the service credit memo page
 */

public class BusinessServiceCreditMemoPage extends BusinessServiceBasePage {

    By serviceInvoiceNoInput = By.xpath("//div[@controlname='VER_Original Serv. Inv. No.']//input");
    public BusinessServiceCreditMemoPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Enter the Original Service Invoice No. under Invoicing section
     * @param invoiceNo
     */
    public void enterServiceInvoiceNo(String invoiceNo) {
        WebElement serviceInvoiceNoField = wait.waitForElementDisplayed(serviceInvoiceNoInput);
        scroll.scrollElementIntoView(serviceInvoiceNoField);
        click.clickElementCarefully(serviceInvoiceNoField);
        text.selectAllAndInputText(serviceInvoiceNoField, invoiceNo);
        text.pressTab(serviceInvoiceNoField);
    }
}
