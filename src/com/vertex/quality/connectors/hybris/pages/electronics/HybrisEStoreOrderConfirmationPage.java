package com.vertex.quality.connectors.hybris.pages.electronics;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.misc.VertexCurrencyUtils;
import com.vertex.quality.connectors.hybris.pages.HybrisBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the functionality of Electronics Store Order Confirmation page and get the Order
 * Number
 *
 * @author Nagaraju Gampa
 */
public class HybrisEStoreOrderConfirmationPage extends HybrisBasePage {
    public HybrisEStoreOrderConfirmationPage(WebDriver driver) {
        super(driver);
    }

    protected By ORDER_NUMBER = By.cssSelector("[class='checkout-success__body']> div + p > b");
    protected By ORDER_CONFIRM_ITEM_PRICE = By.cssSelector("div[class='item__price']");
    protected By ORDER_CONFIRM_TOTAL = By.cssSelector("div[class*='item__total hidden-xs']");
    //Xpath is used for few of below locators, because Writing CSS is like hardcoding(Example: .orderTotal *:nth-child(6) div] due to implementation of DOM Structure.
    // Even writing methods by iterating elements is also not possible due to implementation of DOM Structure.
    protected By ORDER_CONFIRM_SUBTOTAL = By.xpath(
            "//div[contains(text(),'Subtotal:')]//following-sibling::div[1]/div");
    protected By ORDER_CONFIRM_ORDER_DISCOUNT = By.cssSelector(
            "div[class='text-right subtotals__item--state-discount']");
    protected By ORDER_CONFIRM_SHIPPING = By.xpath(
            "//div[contains(text(),'Shipping:')]//following-sibling::div[1]/div");
    protected By ORDER_CONFIRM_TAX = By.xpath("//div[contains(text(),'Tax:')]//following-sibling::div[1]/div");
    protected By ORDER_CONFIRM_ORDER_TOTAL = By.cssSelector("div[class='text-right totals']");
    protected By ORDER_CONFIRM_SAVED_AMT_TEXT = By.cssSelector("div[class='order-savings__info text-right']");
    protected By ORDER_CONFIRM_RECEIVED_PROMOTION_TEXT = By.className("order-savings__info");

    /***
     * Method to get Order Number from Order Confirmation Page
     *
     * @return order number
     */
    public String getOrderNumber() {
        wait.waitForElementDisplayed(ORDER_NUMBER);
        final String orderNumber = text.getElementText(ORDER_NUMBER);
        return orderNumber;
    }

    /***
     * Method to get Item Price from Order Confirmation Page
     *
     * @return itemPriceflt - This amount will be returned in Float Datatype
     */
    public float getItemPrice() {
        wait.waitForElementDisplayed(ORDER_CONFIRM_ITEM_PRICE);
        String itemPriceStr = attribute.getElementAttribute(ORDER_CONFIRM_ITEM_PRICE, "textContent");
        double itemPrice = VertexCurrencyUtils.cleanseCurrencyString(itemPriceStr);
        float itemPriceflt = Float.parseFloat(String.valueOf(itemPrice));
        return itemPriceflt;
    }

    /***
     * Method to get Total Amount from Order Confirmation page
     *
     * @return Total Amount in Float
     */
    public float getTotal() {
        float result = 0;
        if (element.isElementDisplayed(ORDER_CONFIRM_TOTAL)) {
            final String totalStr = attribute
                    .getElementAttribute(ORDER_CONFIRM_TOTAL, "textContent")
                    .trim();
            double totalAmt = VertexCurrencyUtils.cleanseCurrencyString(totalStr);
            VertexLogger.log("Total Amount: " + totalStr);
            result = Float.parseFloat(String.valueOf(totalAmt));
        }
        return result;
    }

    /***
     * Method to get SubTotal Amount from Order Confirmation page
     *
     * @return SubTotal Amount in Float
     */
    public float getSubTotal() {
        float result = 0;
        if (element.isElementDisplayed(ORDER_CONFIRM_SUBTOTAL)) {
            final String subTotalStr = attribute
                    .getElementAttribute(ORDER_CONFIRM_SUBTOTAL, "textContent")
                    .trim();
            double subTotalAmt = VertexCurrencyUtils.cleanseCurrencyString(subTotalStr);
            VertexLogger.log("Sub Total Amount: " + subTotalStr);
            result = Float.parseFloat(String.valueOf(subTotalAmt));
        }
        return result;
    }

    /***
     * Method to get OrderDiscount from Order Confirmation page
     *
     * @return OrderDiscount in Float
     */
    public float getOrderDiscount() {
        float result = 0;
        if (element.isElementDisplayed(ORDER_CONFIRM_ORDER_DISCOUNT)) {
            final String orderDiscountStr = attribute
                    .getElementAttribute(ORDER_CONFIRM_ORDER_DISCOUNT, "textContent")
                    .trim();
            double orderDiscountAmt = VertexCurrencyUtils.cleanseCurrencyString(orderDiscountStr);
            VertexLogger.log("Order Discount Amount: " + orderDiscountStr);
            result = Float.parseFloat(String.valueOf(orderDiscountAmt));
        }
        return result;
    }

    /***
     * Method to get Shipping Amount from Order Confirmation page
     *
     * @return Shipping Amount in Float
     */
    public float getShippingAmount() {
        float result = 0;
        if (element.isElementDisplayed(ORDER_CONFIRM_SHIPPING)) {
            final String shippingStr = attribute
                    .getElementAttribute(ORDER_CONFIRM_SHIPPING, "textContent")
                    .trim();
            double shippingAmount = VertexCurrencyUtils.cleanseCurrencyString(shippingStr);
            VertexLogger.log("Shipping Amount: " + shippingStr);
            result = Float.parseFloat(String.valueOf(shippingAmount));
        }
        return result;
    }

    /**
     * Takes discount amount of order from the UI
     *
     * @return discounted amount
     */
    public double getDiscountAmount() {
        double discountAmount = getOrderDiscount();
        return Double.parseDouble(String.format("%.2f", discountAmount));
    }

    /***
     * Method to get Tax Amount from Order Confirmation page
     *
     * @return Tax Amount in Float
     */
    public float getTax() {
        float result = 0;
        if (element.isElementDisplayed(ORDER_CONFIRM_TAX)) {
            final String taxStr = attribute
                    .getElementAttribute(ORDER_CONFIRM_TAX, "textContent")
                    .trim();
            double taxAmt = VertexCurrencyUtils.cleanseCurrencyString(taxStr);
            VertexLogger.log("Tax Amount: " + taxStr);
            result = Float.parseFloat(String.valueOf(taxAmt));
        }
        return result;
    }

    /***
     * Method to get Order Total Amount from Order Confirmation page
     *
     * @return Order Total Amount in Float
     */
    public float getOrderTotal() {
        float result = 0;
        if (element.isElementDisplayed(ORDER_CONFIRM_ORDER_TOTAL)) {
            final String orderTotalStr = attribute
                    .getElementAttribute(ORDER_CONFIRM_ORDER_TOTAL, "textContent")
                    .trim();
            double orderTotalAmt = VertexCurrencyUtils.cleanseCurrencyString(orderTotalStr);
            VertexLogger.log("Order Total Amount: " + orderTotalStr);
            result = Float.parseFloat(String.valueOf(orderTotalAmt));
        }
        return result;
    }

    /***
     * Method to get Saved Amount On Order Confirmation page
     *
     * @return SavedAmountText
     */
    public String getSavedAmountText() {
        String savedAmountText = "";
        wait.waitForElementDisplayed(ORDER_CONFIRM_SAVED_AMT_TEXT);
        savedAmountText = attribute
                .getElementAttribute(ORDER_CONFIRM_SAVED_AMT_TEXT, "text")
                .trim();
        return savedAmountText;
    }

    /***
     * Method to get Received Promotion Text On Order Confirmation page
     *
     * @return ReceivedPromotionText
     */
    public String getReceivedPromotionText() {
        String receivedPromotionText = "";
        wait.waitForElementDisplayed(ORDER_CONFIRM_RECEIVED_PROMOTION_TEXT);
        receivedPromotionText = attribute
                .getElementAttribute(ORDER_CONFIRM_RECEIVED_PROMOTION_TEXT, "text")
                .trim();
        return receivedPromotionText;
    }

    /**
     * Counts discounted amount of percentage based coupons
     *
     * @param taxPercent need to pass percentage of percentage based tax
     * @return calculated tax amount based on percentage
     */
    public float calculatePercentageBasedTax(double taxPercent) {
        double tax = (getSubTotal() + getShippingAmount()) * (taxPercent / 100);
        return Float.parseFloat(String.format("%.2f", tax));
    }

    /**
     * Counts discounted amount of percentage based coupons
     *
     * @param taxPercent         need to pass percentage of percentage based tax
     * @param isProductIncluded  true to calculate tax on product & false to not calculate tax on product
     * @param isShippingIncluded true to calculate tax on shipping & false to not calculate tax on shipping
     * @return calculated tax amount based on percentage
     */
    public float calculatePercentageBasedTax(boolean isProductIncluded, boolean isShippingIncluded, double taxPercent) {
        float subtotal = 0;
        float shipping = 0;
        if (isProductIncluded) {
            subtotal = getSubTotal();
        }
        getSubTotal();
        if (isShippingIncluded) {
            getShippingAmount();
        }
        double tax = (subtotal + shipping) * (taxPercent / 100);
        return Float.parseFloat(String.format("%.2f", tax));
    }
}
