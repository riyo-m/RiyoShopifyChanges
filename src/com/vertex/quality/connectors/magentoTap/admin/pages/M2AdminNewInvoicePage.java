package com.vertex.quality.connectors.magentoTap.admin.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Representation of the New Invoice Page
 *
 * @author alewis
 */
public class M2AdminNewInvoicePage extends MagentoAdminPage {
    protected By parentClass = By.className("admin__table-primary");
    protected By evenClass = By.className("even");
    protected By oddClass = By.className("odd");
    protected By productClass = By.className("col-product");
    protected By productTitleClass = By.className("product-title");
    protected By inputClass = By.className("qty-input");
    protected By updateButtonClass = By.className("update-button");
    protected By colTaxClass = By.className("col-tax");
    protected By colPriceClass = By.className("col-price");
    protected By adminTotalMark = By.className("admin__total-mark");
    protected By priceExclTaxClass = By.className("price-excl-tax");
    protected By priceInclTaxClass = By.className("price-incl-tax");
    protected By labelClass = By.className("label");
    protected String exclTaxSubtotalString = "Subtotal (Excl.Tax)";
    protected String exclTaxString = "Excl. Tax:";
    protected String inclTaxString = "Incl. Tax:";
    protected String shippingString = "Shipping (14.975%)";
    protected By priceClass = By.className("price");
    protected By grandTotalExclTax = By.xpath("//table[@class='data-table admin__table-secondary order-subtotal-table']//strong[contains(text(),'Grand Total (Excl.Tax)')]/../..//span[@class='price']");
    protected By grandTotalInclTax = By.xpath("//table[@class='data-table admin__table-secondary order-subtotal-table']//strong[contains(text(),'Grand Total (Incl.Tax)')]/../..//span[@class='price']");
    protected By shippingTaxField = By.xpath("//td[@class='admin__total-mark'][contains(text(),'Shipping')]/..//td//span[@class='price']");

    protected By columnZeroClass = By.className("col-0");
    protected By columnThreeClass = By.className("col-3");
    protected By columnFourClass = By.className("col-4");
    protected By columnFiveClass = By.className("col-5");
    protected By summaryTotalClass = By.className("summary-total");
    protected By summaryDetailsClass = By.className("summary-details");
    protected By summaryDetailsFirstClass = By.className("summary-details-first");
    protected By columnSixClass = By.className("col-6");
    protected By columnSevenClass = By.className("col-7");
    protected By columnNineClass = By.className("col-9");

    protected By pricesTable = By.className("data-table admin__table-secondary order-subtotal-table");

    protected By orderShipID = By.id("order_ship");

    By maskClass = By.className("loading-mask");

    protected By submitInvoiceButton = By.className("submit-button");

    protected By orderCreditMemoID = By.id("order_creditmemo");

    protected By messageSuccessClass = By.className("message-success");

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public M2AdminNewInvoicePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Refunds some but not all of items
     *
     * @param refundItem Item to be refunded
     * @param qty        quantity of refund items
     */
    public void doPartialRefund(String refundItem, String qty) {
        waitForPageLoad();

        WebElement parent = wait.waitForElementEnabled(parentClass);

        WebElement even = wait.waitForElementPresent(evenClass, parent);

        List<WebElement> products = wait.waitForAllElementsDisplayed(productClass, even);

        for (WebElement product : products) {
            WebElement productTitle = wait.waitForElementDisplayed(productTitleClass, product);
            String productString = productTitle.getText();

            if (refundItem.equals(productString)) {
                WebElement input = wait.waitForElementEnabled(inputClass, even);
                input.clear();
                input.sendKeys(qty);
            }

            WebElement updateButton = wait.waitForElementEnabled(updateButtonClass);
            click.clickElement(updateButton);
            waitForPageLoad();
        }
    }

    /**
     * Refunds some but not all of items
     *
     * @param refundItem Item to be refunded
     * @param qty        quantity of refund items
     */
    public void doPartialRefundTwoItems(String refundItem, String qty) {
        waitForPageLoad();

        WebElement parent = wait.waitForElementEnabled(parentClass);

        List<WebElement> evens = wait.waitForAllElementsPresent(evenClass, parent);

        for (WebElement even : evens) {
            List<WebElement> products = wait.waitForAllElementsDisplayed(productClass, even);

            for (WebElement product : products) {
                WebElement productTitle = wait.waitForElementDisplayed(productTitleClass, product);
                String productString = productTitle.getText();

                if (refundItem.equals(productString)) {
                    WebElement input = wait.waitForElementEnabled(inputClass, even);
                    input.clear();
                    input.sendKeys(qty);
                }
            }
        }
    }

    /**
     * Clicks on update button
     */
    public void clickUpdateButton() {
        WebElement updateButton = wait.waitForElementEnabled(updateButtonClass);
        click.clickElement(updateButton);
        waitForPageLoad();
        waitForMaskGone(maskClass);
    }

    /**
     * Gets the excluding tax price of the order in the Items to Invoice section
     *
     * @return String of excluding tax price
     */
    public String getExclTaxPrice() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        String price = null;
        WebElement even = wait.waitForElementPresent(evenClass);
        WebElement taxAmount = wait.waitForElementPresent(colPriceClass, even);
        WebElement priceExclTax = wait.waitForElementPresent(priceExclTaxClass, taxAmount);
        WebElement label = wait.waitForElementPresent(labelClass, priceExclTax);
        String labelText = label.getText();

        if (exclTaxString.equals(labelText)) {
            WebElement priceContainer = wait.waitForElementPresent(priceClass, priceExclTax);
            price = priceContainer.getText();
        }

        return price;
    }

    /**
     * Gets the including tax price of the order in the Items to Invoice section
     *
     * @return String of including tax price
     */
    public String getInclTaxPrice() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        String price = null;
        WebElement even = wait.waitForElementPresent(evenClass);
        WebElement taxAmount = wait.waitForElementPresent(colPriceClass, even);
        WebElement priceInclTax = wait.waitForElementPresent(priceInclTaxClass, taxAmount);
        WebElement label = wait.waitForElementPresent(labelClass, priceInclTax);
        String labelText = label.getText();

        if (inclTaxString.equals(labelText)) {
            WebElement priceContainer = wait.waitForElementPresent(priceClass, priceInclTax);
            price = priceContainer.getText();
        }

        return price;
    }

    /**
     * Gets the tax amount of the order in the Items to Invoice section
     *
     * @return String of tax amount
     */
    public String getTaxAmount() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement even = wait.waitForElementPresent(evenClass);
        WebElement colTax = wait.waitForElementPresent(colTaxClass, even);
        String price = colTax.getText();

        return price;
    }

    /**
     * Gets subtotal price excluding tax from Invoice Totals at bottom right corner of page
     *
     * @return a string of subtotal price excluding tax
     */
    public String getInvoiceSubtotalExclTax() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        String priceString = null;
        List<WebElement> colZeros = wait.waitForAllElementsPresent(columnZeroClass);

        WebElement colZero = element.selectElementByNestedLabel(colZeros, labelClass, exclTaxSubtotalString);
        if (colZero != null) {
            WebElement price = wait.waitForElementDisplayed(priceClass, colZero);
            priceString = text.getElementText(price);
        }

        return priceString;
    }

    /**
     * Gets subtotal price including tax from Invoice Totals at bottom right corner of page
     *
     * @return a string of subtotal price including tax
     */
    public String getInvoiceSubtotalInclTax() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement colThree = wait.waitForElementPresent(columnThreeClass);
        WebElement price = wait.waitForElementPresent(priceClass, colThree);
        String priceString = price.getText();
        return priceString;
    }

    /**
     * Gets Shipping & Handling price excluding tax from Invoice Totals at bottom right corner of
     * page
     *
     * @return a string of Shipping & Handling price excluding tax
     */
    public String getInvoiceShippingHandlingExclTax() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement colFour = wait.waitForElementPresent(columnFourClass);
        WebElement price = wait.waitForElementPresent(priceClass, colFour);
        String priceString = price.getText();
        return priceString;
    }

    /**
     * Gets Shipping & Handling price including tax from Invoice Totals at bottom right corner of
     * page
     *
     * @return a string of Shipping & Handling price including tax
     */
    public String getInvoiceShippingHandlingInclTax() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement colFive = wait.waitForElementPresent(columnFiveClass);
        WebElement price = wait.waitForElementPresent(priceClass, colFive);
        String priceString = price.getText();
        return priceString;
    }

    /**
     * Opens tax blind
     */
    public void openTaxBlind() {
        waitForPageLoad();
        WebElement taxBlind = wait.waitForElementEnabled(summaryTotalClass);
        taxBlind.click();
    }

    /**
     * Gets the Sales and Use tax price from Invoice Totals at bottom right corner of page
     *
     * @return a string of the Sales and use Tax total
     */
    public String getSalesUseTax() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement rowTotal = wait.waitForElementPresent(summaryDetailsFirstClass);
        WebElement salesUsePrice = wait.waitForElementPresent(priceClass, rowTotal);
        String priceText = salesUsePrice.getText();

        return priceText;
    }

    /**
     * Gets price of the shipping tax on the order
     *
     * @return a string of the shipping tax
     */
    public String getShippingTax() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        scroll.scrollElementIntoView(grandTotalInclTax);
        WebElement exclTaxPrice = wait.waitForElementPresent(shippingTaxField);
        String priceText = exclTaxPrice.getText();
        return priceText;
    }

    /**
     * Gets price excluding tax from Invoice Totals at bottom right corner of page
     *
     * @return a string of grand total price excluding tax
     */
    public String getInvoiceTotalExclTax() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        scroll.scrollElementIntoView(grandTotalInclTax);
        WebElement exclTaxPrice = wait.waitForElementPresent(grandTotalExclTax);
        String priceText = exclTaxPrice.getText();

        return priceText;
    }

    /**
     * Gets price including tax from Invoice Totals at bottom right corner of page
     *
     * @return a string of grand total price including tax
     */
    public String getInvoiceTotalInclTax() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement inclTaxPrice = wait.waitForElementPresent(grandTotalInclTax);
        String priceText = inclTaxPrice.getText();

        return priceText;
    }

    /**
     * Navigation to Ship page
     *
     * @return Ship Page
     */
    public M2AdminShipPage navigateToShipPage() {
        waitForPageLoad();
        WebElement orderShip = wait.waitForElementDisplayed(orderShipID);
        click.clickElement(orderShip);

        return initializePageObject(M2AdminShipPage.class);
    }

    /**
     * clicks the 'Submit Invoice' button
     *
     * @return the order info Page
     */
    public M2AdminOrderViewInfoPage clickSubmitInvoiceButton() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        wait.waitForElementDisplayed(submitInvoiceButton);
        WebElement submitInvoice = wait.waitForElementPresent(submitInvoiceButton);
        click.clickElement(submitInvoice);
        wait.waitForElementNotDisplayedOrStale(submitInvoice, 10);

        M2AdminOrderViewInfoPage orderViewInfoPage = initializePageObject(M2AdminOrderViewInfoPage.class);

        return orderViewInfoPage;
    }

    /**
     * clicks the 'Credit Memo' button
     *
     * @return the Credit Memo Page
     */
    public M2AdminCreditMemoPage clickCreditMemoButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(orderCreditMemoID);
        WebElement orderCreditMemoButton = wait.waitForElementEnabled(orderCreditMemoID);
        click.clickElement(orderCreditMemoButton);

        return initializePageObject(M2AdminCreditMemoPage.class);
    }

    /**
     * checks to see if the messages on top of page match the correct string
     *
     * @param correctMessage correction message
     * @return a boolean if the string matches
     */
    public boolean checkMessage(String correctMessage) {
        boolean match = false;
        WebElement messageSuccess = element.selectElementByText(messageSuccessClass, correctMessage);
        if (messageSuccess != null) {
            match = true;
        }
        return match;
    }
}
