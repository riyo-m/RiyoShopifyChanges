package com.vertex.quality.connectors.magentoTap.admin.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Representation of Order Information Page
 *
 * @author alewis
 */
public class M2AdminOrderViewInfoPage extends MagentoAdminPage {
    protected By evenClass = By.className("even");
    protected By colSubtotalClass = By.className("col-subtotal");
    protected By exclTaxClass = By.className("price-excl-tax");
    protected By inclTaxClass = By.className("price-incl-tax");
    protected By colTaxPercent = By.className("col-tax-percent");
    protected By colPriceOriginal = By.className("col-price-original");
    protected By colTaxAmount = By.className("col-tax-amount");
    protected By columnZeroClass = By.className("col-0");
    protected By columnOneClass = By.className("col-1");
    protected By columnTwoClass = By.className("col-2");
    protected By columnThreeClass = By.className("col-3");
    protected By columnFourClass = By.className("col-4");
    protected By columnFiveClass = By.className("summary-total");
    protected By salesUseClass = By.className("summary-details-first");
    protected By shippingTotalClass = By.className("summary-details");
    protected By adminTotalMarkClass = By.className("admin__total-mark");
    protected By columnSixClass = By.className("col-6");
    protected By labelClass = By.className("label");
    protected By priceClass = By.className("price");

    protected String exclLabelString = "Subtotal (Excl.Tax)";
    protected String inclLabelString = "Subtotal (Incl.Tax)";
    protected String refundedString = "Total Refunded";
    protected String inclShippingHandling = "Shipping & Handling (Incl.Tax)";
    protected String shippingTaxString = "Shipping";

    protected By statusId = By.id("history_status");
    protected By submitCommentButtonLoc = By.cssSelector("button[title='Submit Comment']");

    protected By invoiceID = By.xpath("(//*[contains(text(),'Hold')])[1]//following::button[1]");
    protected By creditMemoID = By.id("order_creditmemo");
    protected By backButtonId = By.id("back");
    By maskClass = By.className("loading-mask");

    protected By messageSuccessClass = By.className("message-success");

    protected By totalRefunded = By.xpath(".//td[normalize-space(.)='Total Refunded']/following-sibling::td//span");
    protected By taxAmount = By.xpath(".//td[normalize-space(.)='Tax']/following-sibling::td//span");
    protected By subTotalAmount = By.xpath("(.//td[normalize-space(.)='Subtotal']/following-sibling::td//span)[2]");
    protected By shippingHandlingAmount = By.xpath("(.//td[contains(normalize-space(.),'Shipping & Handling')]/following-sibling::td//span)[2]");

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public M2AdminOrderViewInfoPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets original price of order before tax from Items Ordered section
     *
     * @return String of the original price
     */
    public String getOriginalPrice() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(colPriceOriginal);
        WebElement even = wait.waitForElementPresent(evenClass);
        WebElement originalPrice = wait.waitForElementPresent(colPriceOriginal, even);
        String price = originalPrice.getText();

        return price;
    }

    /**
     * Gets subtotal price of order before tax from Items Ordered section
     *
     * @return String of the subtotal excluding tax
     */
    public String getSubtotalExclTax() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement even = wait.waitForElementDisplayed(evenClass);
        WebElement colSubtotal = wait.waitForElementDisplayed(colSubtotalClass, even);
        WebElement priceExclSubtotal = wait.waitForElementDisplayed(exclTaxClass, colSubtotal);
        WebElement price = wait.waitForElementDisplayed(priceClass, priceExclSubtotal);
        String priceString = price.getText();

        return priceString;
    }

    /**
     * Gets subtotal price of order after tax from Items Ordered section
     *
     * @return String of the subtotal including tax
     */
    public String getSubtotalInclTax() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement even = wait.waitForElementDisplayed(evenClass);
        WebElement colSubtotal = wait.waitForElementDisplayed(colSubtotalClass, even);
        WebElement priceInclSubtotal = wait.waitForElementDisplayed(inclTaxClass, colSubtotal);
        WebElement price = wait.waitForElementDisplayed(priceClass, priceInclSubtotal);
        String priceString = price.getText();

        return priceString;
    }

    /**
     * Gets tax amount from Items Ordered section
     *
     * @return String of tax amount
     */
    public String getTaxAmount() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement even = wait.waitForElementPresent(evenClass);
        WebElement taxAmount = wait.waitForElementPresent(colTaxAmount, even);
        String price = taxAmount.getText();

        return price;
    }

    /**
     * Gets tax from Items Ordered section
     *
     * @return String of tax percent
     */
    public String getTaxPercent() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(colTaxPercent);
        WebElement even = wait.waitForElementPresent(evenClass);
        WebElement taxPercent = wait.waitForElementPresent(colTaxPercent, even);
        String taxPerc = taxPercent.getText();
        return taxPerc;
    }

    /**
     * Gets subtotal excluding tax from Order Totals at bottom right corner of page
     *
     * @return a string of subtotal price excluding tax
     */
    public String getSubtotalExclTaxOrderTotals() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        String priceText = null;
        List<WebElement> rowTotals = wait.waitForAllElementsPresent(columnZeroClass);
        WebElement rowTotal = element.selectElementByNestedLabel(rowTotals, labelClass, exclLabelString);
        if (rowTotal != null) {
            WebElement exclTaxPrice = wait.waitForElementPresent(priceClass, rowTotal);
            priceText = exclTaxPrice.getText();
        }
        return priceText;
    }

    /**
     * Gets subtotal including tax from Order Totals at bottom right corner of page
     *
     * @return a string of subtotal price including tax
     */
    public String getSubtotalInclTaxOrderTotals() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        String priceText = null;
        List<WebElement> rowTotals = wait.waitForAllElementsPresent(columnOneClass);
        WebElement rowTotal = element.selectElementByNestedLabel(rowTotals, labelClass, inclLabelString);
        if (rowTotal != null) {
            WebElement inclTaxPrice = wait.waitForElementPresent(priceClass, rowTotal);
            priceText = inclTaxPrice.getText();
        }
        return priceText;
    }

    /**
     * Gets Shipping & Handling excluding tax from Order Totals at bottom right corner of page
     *
     * @return a string of Shipping & Handling price excluding tax
     */
    public String getShippingExclTaxOrderTotals() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement rowTotals = wait.waitForElementPresent(columnTwoClass);
        WebElement exclTaxPrice = wait.waitForElementPresent(priceClass, rowTotals);
        String priceText = exclTaxPrice.getText();

        return priceText;
    }

    /**
     * Gets Shipping & Handling including tax from Order Totals at bottom right corner of page
     *
     * @return a string of Shipping & Handling price including tax
     */
    public String getShippingInclTaxOrderTotals() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        String priceText = null;
        List<WebElement> rowTotals = wait.waitForAllElementsPresent(columnThreeClass);
        WebElement rowTotal = element.selectElementByNestedLabel(rowTotals, labelClass, inclShippingHandling);
        if (rowTotal != null) {
            WebElement exclTaxPrice = wait.waitForElementDisplayed(priceClass, rowTotal);
            priceText = exclTaxPrice.getText();
        }
        return priceText;
    }

    /**
     * Gets price excluding tax from Order total at bottom right corner of page
     *
     * @return a string of grand total price excluding tax
     */
    public String getPriceTotalExclTax() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(priceClass);
        WebElement rowTotals = wait.waitForElementPresent(columnFourClass);
        WebElement exclTaxPrice = wait.waitForElementPresent(priceClass, rowTotals);
        String priceText = exclTaxPrice.getText();

        return priceText;
    }

    /**
     * Gets price excluding tax from Order total at bottom right corner of page, of an order with no shipping
     *
     * @return a string of grand total price excluding tax
     */
    public String getPriceTotalExclTaxNoShopping() {
        WebElement rowTotals = wait.waitForElementPresent(columnTwoClass);
        WebElement exclTaxPrice = wait.waitForElementPresent(priceClass, rowTotals);
        String priceText = exclTaxPrice.getText();

        return priceText;
    }

    /**
     * clicks tax blind and opens it
     */
    public void clickTaxBlind() {
        WebElement taxBlind = wait.waitForElementPresent(columnFiveClass);
        taxBlind.click();
    }

    /**
     * Gets Sales and use tax from Order total at bottom right corner of page
     *
     * @return a string of the Sales and Use tax amount
     */
    public String getSalesUseTax() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement salesUse = wait.waitForElementPresent(salesUseClass);
        WebElement price = wait.waitForElementPresent(priceClass, salesUse);
        String priceText = price.getText();

        return priceText;
    }

    /**
     * Gets shipping tax from Order total at bottom right corner of page
     *
     * @return a string of the shipping tax amount
     */
    public String getShippingTax() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        String priceText = null;
        waitForPageLoad();
        List<WebElement> shippingTaxContainers = wait.waitForAllElementsPresent(shippingTotalClass);

        for (WebElement shippingTaxContainer : shippingTaxContainers) {
            WebElement mark = wait.waitForElementPresent(adminTotalMarkClass, shippingTaxContainer);
            String markString = mark.getText();

            if (markString.contains(shippingTaxString)) {
                WebElement price = wait.waitForElementPresent(priceClass, shippingTaxContainer);
                priceText = price.getText();
            }
        }
        return priceText;
    }

    /**
     * Gets price including tax from Order total at bottom right corner of page
     *
     * @return a string of grand total price including tax
     */
    public String getPriceTotalInclTax() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement rowTotals = wait.waitForElementPresent(columnSixClass);
        WebElement inclTaxPrice = wait.waitForElementPresent(priceClass, rowTotals);
        String priceText = inclTaxPrice.getText();

        return priceText;
    }

    /**
     * Gets price including tax from Order total at bottom right corner of page, on order with no shipping
     *
     * @return a string of grand total price including tax
     */
    public String getPriceTotalInclTaxNoShopping() {
        WebElement rowTotals = wait.waitForElementPresent(columnFourClass);
        WebElement inclTaxPrice = wait.waitForElementPresent(priceClass, rowTotals);
        String priceText = inclTaxPrice.getText();

        return priceText;
    }

    /**
     * Gets refund total from Order Totals at bottom right corner of page
     *
     * @return a string of total amount of the refund
     */
    public String getTotalRefundAmount() {
        String priceText = null;

        List<WebElement> rowTotals = wait.waitForAllElementsPresent(columnOneClass);

        WebElement rowTotal = element.selectElementByNestedLabel(rowTotals, labelClass, refundedString);
        if (rowTotal != null) {
            WebElement exclTaxPrice = wait.waitForElementPresent(priceClass, rowTotal);
            priceText = exclTaxPrice.getText();
        }
        return priceText;
    }

    /**
     * clicks the back button
     *
     * @return the orders page
     */
    public M2AdminOrdersPage clickBackButton() {
        WebElement backButton = wait.waitForElementEnabled(backButtonId);

        click.clickElementCarefully(backButton);

        M2AdminOrdersPage ordersPage = initializePageObject(M2AdminOrdersPage.class);

        return ordersPage;
    }

    /**
     * clicks the invoice button
     *
     * @return the New Invoice Page
     */
    public M2AdminNewInvoicePage clickInvoiceButton() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(invoiceID);
        WebElement invoiceButton = wait.waitForElementEnabled(invoiceID);
        click.clickElement(invoiceButton);

        return initializePageObject(M2AdminNewInvoicePage.class);
    }

    /**
     * clicks the credit memo button
     *
     * @return the New Memo Page
     */
    public M2AdminCreditMemoPage clickCreditMemoButton() {
        WebElement creditMemoButton = wait.waitForElementEnabled(creditMemoID);

        creditMemoButton.click();

        return initializePageObject(M2AdminCreditMemoPage.class);
    }

    /**
     * checks to see if the messages on top of page match the correct string
     *
     * @param correctMessage
     * @return a boolean if the string matches
     */
    public boolean checkMessage(String correctMessage) {
        wait.waitForElementDisplayed(messageSuccessClass);
        boolean match = false;
        WebElement messageSuccess = element.selectElementByText(messageSuccessClass, correctMessage);
        if (messageSuccess != null) {
            match = true;
        }
        return match;
    }

    /**
     * Selects an order status from the dropdown
     *
     * @param statusString value of status to select
     */
    public void selectOrderStatus(String statusString) {
        scroll.scrollElementIntoView(statusId);
        WebElement orderStatusSetting = wait.waitForElementDisplayed(statusId);
        dropdown.selectDropdownByValue(orderStatusSetting, statusString);
    }

    /**
     * Clicks on the submit comment button
     */
    public void clickSubmitCommentButton() {
        WebElement submitCommentButton = wait.waitForElementEnabled(submitCommentButtonLoc);
        click.clickElement(submitCommentButton);
    }

    /**
     * Gets total refunded tax amount from the UI
     *
     * @return Total Refunded Tax value
     */
    public double getRefundedTaxFromUI() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(subTotalAmount);
        wait.waitForElementPresent(shippingHandlingAmount);
        return Double.parseDouble(text.getElementText(wait.waitForElementPresent(taxAmount)).replace("€", "").replace(",", ""));
    }

    /**
     * Gets total refunded amount from the UI
     *
     * @return Total Refunded amount
     */
    public double getRefundedAmountFromUI() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(subTotalAmount);
        wait.waitForElementPresent(shippingHandlingAmount);
        return Double.parseDouble(text.getElementText(wait.waitForElementPresent(totalRefunded)).replace("€", "").replace(",", ""));
    }
}
