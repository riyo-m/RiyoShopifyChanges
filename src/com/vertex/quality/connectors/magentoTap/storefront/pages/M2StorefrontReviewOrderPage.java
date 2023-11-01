package com.vertex.quality.connectors.magentoTap.storefront.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoStorefrontPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of the Review Order Page
 *
 * @author alewis
 */
public class M2StorefrontReviewOrderPage extends MagentoStorefrontPage {
    protected By taxSummaryClass = By.className("totals-tax-summary");
    protected By taxDetailsClass = By.className("totals-tax-details");

    protected By totalsCategoriesClass = By.className("totals");

    protected By placeOrderButtonId = By.id("review-button");

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver
     */
    public M2StorefrontReviewOrderPage(WebDriver driver) {
        super(driver);
    }

    /**
     * gets the name of the category, excluding the total price
     *
     * @param ele element value
     * @return the name (Sales Tax, Subtotal Excl Tax, etc)
     */
    private String getCategoryName(WebElement ele) {
        String fullText = ele.getText();

        int index = fullText.indexOf("$");

        String name = fullText
                .substring(0, index)
                .trim();

        return name;
    }

    /**
     * gets the name of the category, excluding the total price and
     * specific shipping type chosen
     *
     * @param ele element
     * @return the name (Shipping Excl Tax, Shipping Incl Tax)
     */
    private String getCategoryNameShipping(WebElement ele) {
        String fullText = ele.getText();

        int index = fullText.indexOf("(");
        if (index == -1) {
            index = fullText.indexOf("$");
        }

        String name = fullText
                .substring(0, index)
                .trim();

        return name;
    }

    /**
     * get the price of the category
     *
     * @param ele element
     * @return price
     */
    private String getTotalPrice(WebElement ele) {
        String fullText = ele.getText();

        int index = fullText.indexOf("$");

        String price = fullText
                .substring(index)
                .trim();

        return price;
    }

    /**
     * find a specific category, such as subtotal, by the category name
     * (for when there are multiple categories of the same name)
     *
     * @param categoryName name of category
     * @return list of categories with that name
     */
    private List<WebElement> findCategoryByText(String categoryName) {
        List<WebElement> foundTotals = new ArrayList<>();
        List<WebElement> totalsList = wait.waitForAllElementsDisplayed(totalsCategoriesClass);

        for (WebElement item : totalsList) {
            String name = getCategoryName(item);

            if (categoryName.equals(name)) {
                foundTotals.add(item);
            }
        }

        return foundTotals;
    }

    /**
     * find a specific shipping category by the category name
     * (for when there are multiple categories of the same name)
     *
     * @param categoryName name of category
     * @return list of categories with that name
     */
    private List<WebElement> findShippingCategoryByText(String categoryName) {
        List<WebElement> foundTotals = new ArrayList<>();
        List<WebElement> totalsList = wait.waitForAllElementsDisplayed(totalsCategoriesClass);

        for (WebElement item : totalsList) {
            String name = getCategoryNameShipping(item);

            if (categoryName.equals(name)) {
                foundTotals.add(item);
            }
        }

        return foundTotals;
    }

    /**
     * get the subtotal excluding tax
     *
     * @param index value of index
     * @return subtotal
     */
    public String getSubtotalExclTax(int index) {
        List<WebElement> allTotals = findCategoryByText("Subtotal (Excl. Tax)");

        WebElement category = allTotals.get(index);

        String total = getTotalPrice(category);

        return total;
    }

    /**
     * get the subtotal including tax
     *
     * @param index value of index
     * @return total price
     */
    public String getSubtotalInclTax(int index) {
        List<WebElement> allTotals = findCategoryByText("Subtotal (Incl. Tax)");

        WebElement category = allTotals.get(index);

        String total = getTotalPrice(category);

        return total;
    }

    /**
     * get the shipping total excluding tax
     *
     * @param index value of index
     * @return shipping cost
     */
    public String getShippingExclTax(int index) {
        List<WebElement> allTotals = findShippingCategoryByText("Shipping Excl. Tax");

        WebElement category = allTotals.get(index);

        String total = getTotalPrice(category);

        return total;
    }

    /**
     * get the shipping total including tax
     *
     * @param index value of index
     * @return shipping cost
     */
    public String getShippingInclTax(int index) {
        List<WebElement> allTotals = findShippingCategoryByText("Shipping Incl. Tax");

        WebElement category = allTotals.get(index);

        String total = getTotalPrice(category);

        return total;
    }

    /**
     * get the grand total for the item excluding tax
     *
     * @param index value of index
     * @return grand total
     */
    public String getGrandTotalExclTax(int index) {
        List<WebElement> allTotals = findCategoryByText("Grand Total Excl. Tax");

        WebElement category = allTotals.get(index);

        String total = getTotalPrice(category);

        return total;
    }

    /**
     * get the grand total for the item including tax
     *
     * @param index value of index
     * @return grand total
     */
    public String getGrandTotalInclTax(int index) {
        List<WebElement> allTotals = findCategoryByText("Grand Total Incl. Tax");

        WebElement category = allTotals.get(index);

        String total = getTotalPrice(category);

        return total;
    }

    /**
     * get the tax amount for the item
     *
     * @param index value of index
     * @return tax
     */
    public String getTax(int index) {
        List<WebElement> allTaxes = wait.waitForAllElementsDisplayed(taxSummaryClass);

        WebElement tax = allTaxes.get(index);

        String total = getTotalPrice(tax);

        return total;
    }

    /**
     * get the sales and use tax for the item
     *
     * @param index value of index
     * @return sales use tax
     */
    public String getSalesUseTax(int index) {
        List<WebElement> allTaxes = wait.waitForAllElementsDisplayed(taxDetailsClass);

        WebElement tax = allTaxes.get(index);

        String total = getTotalPrice(tax);

        return total;
    }

    /**
     * clicks the Place Order button
     *
     * @return thank you page
     */
    public M2StorefrontThankYouPage clickPlaceOrderButton() {
        WebElement button = wait.waitForElementEnabled(placeOrderButtonId);

        click.clickElementCarefully(button);

        M2StorefrontThankYouPage thankYouPage = initializePageObject(M2StorefrontThankYouPage.class);

        return thankYouPage;
    }
}
