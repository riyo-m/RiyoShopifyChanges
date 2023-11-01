package com.vertex.quality.connectors.salesforce.pages.lom;

import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SalesForceLOMCreditMemoPage extends SalesForceBasePage {

    SalesForceLOMPostLogInPage postLogInPage;

    protected By DETAILS_TAB = By.xpath(".//h1/div[text()='Credit Memo']/../../../../../../../../../../../../../..//*[text()='Details']");
    protected By RECENTLY_VIEWED = By.xpath(
            ".//div/div/div/div[2]/div/div[1]/div[2]/div[1]/span[@title='Recently Viewed']");
    protected By RELATED_TAB = By.xpath("//*[@id=\"relatedListsTab__item\"][@aria-controls='tab-21']");

    protected By FIRST_CREDIT_MEMO_RECORD = By.xpath(".//div[contains(@class, 'slds-split-view')]//div[1]/div/div/ul/li[1]/a[contains(@class, 'splitViewListRecordLink')]");
    protected By ORDER_PRODUCT_TAX_CODE = By.xpath("//div[contains(@class, 'slds-form-element')]/div[contains(@class, 'test-id__field-label-container') and span[text()='Tax_Codes']]//following-sibling::div//span//slot//lightning-formatted-text[@slot='outputField']");
    protected By ORDER_PRODUCT_INVOICE_TEXT_CODE = By.xpath("//div[contains(@class, 'slds-form-element')]/div[contains(@class, 'test-id__field-label-container') and span[text()='Invoice_Text_Codes']]//following-sibling::div//span//slot//lightning-formatted-text[@slot='outputField']");
    protected By ORDER_PRODUCT_VERTEX_TAX_CODE = By.xpath("//div[contains(@class, 'slds-form-element')]/div[contains(@class, 'test-id__field-label-container') and span[text()='Vertex_Tax_Codes']]//following-sibling::div//span//slot//lightning-formatted-text[@slot='outputField']");

    public SalesForceLOMCreditMemoPage(WebDriver driver)
    {
        super(driver);
        postLogInPage = new SalesForceLOMPostLogInPage(driver);
    }

    /**
     * Navigate to first order in All Orders
     */
    public void navigateToFirstCreditMemo( )
    {
        waitForSalesForceLoaded();
        wait.waitForElementDisplayed(FIRST_CREDIT_MEMO_RECORD);
        click.javascriptClick(FIRST_CREDIT_MEMO_RECORD);
        waitForSalesForceLoaded();
    }

    /**
     * Navigate to Details of order
     */
    public void navigateToDetails( )
    {
        waitForSalesForceLoaded();
        wait.waitForElementDisplayed(RECENTLY_VIEWED);
        wait.waitForElementDisplayed(DETAILS_TAB);
        click.clickElement(DETAILS_TAB);
        waitForSalesForceLoaded();
    }

    /**
     * Get estimated tax field with currency hardcoded to 'USD'
     *
     * @return estimated tax
     */
    public String getTotalTax(){
        return getTotalTax("USD");
    }

    /**
     * get Estimated Tax field based on specified currency code
     * @param isoCurrencyCode currency used for Order
     *
     * @return estimated Tax
     */
    public String getTotalTax(String isoCurrencyCode)
    {
        String totalTaxLocator = String.format(".//span[text()= 'Credit Memo Amount Information']/../../../..//span[text()='Vertex Tax Total']/../..//lightning-formatted-text[@data-output-element-id='output-field'][contains(text(),'%s')]", isoCurrencyCode);
        By TOTAL_TAX = By.xpath(totalTaxLocator);
        int i = 0;
        while (!element.isElementPresent(TOTAL_TAX) && i < 5)
        {
            refreshPage();
            navigateToDetails();
            waitForSalesForceLoaded();
            i++;
        }
        if (i==0)
        {
            navigateToDetails();
            waitForSalesForceLoaded();
        }
        wait.waitForElementEnabled(TOTAL_TAX);
        String sectionText = text.getElementText(TOTAL_TAX);
        i =0;
        while(sectionText == "$0.00" && i < 5)
        {
            refreshPage();
            navigateToDetails();
            sectionText = text.getElementText(TOTAL_TAX);
            i++;
        }
        return sectionText;
    }

    /**
     * get Total with Tax field with currency hardcoded to 'USD'
     *
     * @return estimated Total Tax
     */
    public String getTotalWithTax()
    {
        return getTotalWithTax("USD");
    }

    /**
     * get Total with Tax field based on specified currency
     * @param isoCurrencyCode currency used for order
     *
     * @return estimated Total Tax
     */
    public String getTotalWithTax(String isoCurrencyCode)
    {
        String totalWithTaxLocator = String.format(".//span[text()= 'Credit Memo Amount Information']/../../../..//span[text()='Total with Tax']/../..//lightning-formatted-text[contains(text(), '%s')]", isoCurrencyCode);
        By TOTAL_WITH_TAX = By.xpath(totalWithTaxLocator);
        wait.waitForElementDisplayed(TOTAL_WITH_TAX);
        wait.waitForElementEnabled(TOTAL_WITH_TAX);
        return text.getElementText(TOTAL_WITH_TAX);
    }

    /**
     * Navigate to Related of order
     */
    public void navigateToRelated( )
    {
        wait.waitForElementDisplayed(RECENTLY_VIEWED);
        wait.waitForElementDisplayed(RELATED_TAB);
        click.clickElement(RELATED_TAB);
        waitForSalesForceLoaded();
    }

    /**
     * Navigate back to Order Summary page
     */
    public void navigateToProductSummary( String productName )
    {
        String productBy = String.format(".//*/slot/slot/span[text()='%s' and @id='window']",productName);

        wait.waitForElementDisplayed(By.xpath(productBy));
        click.clickElement(By.xpath(productBy));
    }

    /**
     * Gets the value for the product Invoice Text Code on the Credit memo papge
     * */
    public String getProductInvoiceTextCode() {
        List<WebElement> textCode = driver.findElements(ORDER_PRODUCT_INVOICE_TEXT_CODE);
        return textCode.get(1).getText();
    }

    /**
     * Gets the value for the product Vertex Tax Code on the Credit memo page
     * */
    public String getProductVertexTaxCode() {
        List<WebElement> taxCode = driver.findElements(ORDER_PRODUCT_VERTEX_TAX_CODE);
        return taxCode.get(1).getText();
    }

    /**
     * Gets the value for the product Tax Code on the Credit memo page
     * */
    public String getProductTaxCode() {
        List<WebElement> taxCode = driver.findElements(ORDER_PRODUCT_TAX_CODE);
        return taxCode.get(1).getText();
    }
}
