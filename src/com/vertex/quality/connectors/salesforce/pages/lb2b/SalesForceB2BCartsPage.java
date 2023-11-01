package com.vertex.quality.connectors.salesforce.pages.lb2b;

import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SalesForceB2BCartsPage extends SalesForceBasePage {

    protected By LIST_VIEW = By.xpath("//span[contains(@class, 'selectedListView')]");

    protected By PURCHASE_ORDER_NUMBER_INPUT = By.xpath("//div/label/span[text()='Purchase Order Number']/../following-sibling::input");
    protected By TAX_EXEMPT_CHECKBOX = By.xpath("//div/label/span[text()='VTX_TaxExempt']/../following-sibling::input");
    protected By PENCIL_EDIT_BUTTON = By.xpath("//div/span[text()='VTX_TaxExempt']/../following-sibling::div/button");
    protected By TAX_REGISTRATION_NUMBER = By.xpath("//div/label/span[contains(text(), 'RegistrationNumber')]/../following-sibling::input");
    protected By DISCOUNT_PERCENT_INPUT = By.xpath(
            "//div/label/span[contains(text(), 'DiscountPercent')]/../following-sibling::input");
    protected By DISCOUNT_AMOUNT_INPUT = By.xpath(
            "//div/label/span[contains(text(), 'DiscountAmount')]/../following-sibling::input");
    protected By EXEMPTION_CERTIFICATE_INPUT = By.xpath(
            "//div/label/span[contains(text(), 'ExemptionCertificate')]/../following-sibling::input");

    protected By CART_CLOSE_BUTTON = By.xpath("//a[@aria-selected='true']/span[text()='Cart']/../following-sibling::div[contains(@class, 'close')]/button");
    protected By CART_SAVE_BUTTON = By.xpath("//button[contains(@class, 'saveBtn')]");
    protected By VIEW_ALL_BUTTON = By.className("view-all-label");
    protected By CART_ITEMS_LINK = By.xpath("//span[text()='Cart Items' and @title='Cart Items']/parent::a");

    protected By RELATED_TAB = By.xpath(
            ".//h1/div[text()='Cart']/../../../../../../../../../.././/div/div/div/div/ul/li/a/span[text()='Related']");

    protected By INVOICE_TEXT_CODE = By.xpath("//div[contains(@class, 'slds-form-element')]/div[contains(@class, 'test-id__field-label-container') and span[text()='Invoice Text Codes']]//following-sibling::div//span//slot[@name='outputField']//lightning-formatted-text");
    protected By VERTEX_TAX_CODE = By.xpath("//div[contains(@class, 'slds-form-element')]/div[contains(@class, 'test-id__field-label-container') and span[text()='Vertex Tax Code']]//following-sibling::div//span//slot[@name='outputField']//lightning-formatted-text");
    protected By TAX_CODE = By.xpath("//div[contains(@class, 'slds-form-element')]/div[contains(@class, 'test-id__field-label-container') and span[text()='Tax Codes']]//following-sibling::div//span//slot[@name='outputField']//lightning-formatted-text");


    public SalesForceB2BCartsPage( WebDriver driver )
    {
        super(driver);
    }

    public SalesForceLB2BPostLogInPage postLogInPage = new SalesForceLB2BPostLogInPage(driver);

    /**
     * Selects list view for Carts tab
     * @param listView
     */
    public void selectCartListView(String listView)
    {
        By listViewSelection;
        clickListViewDropdown();
        String selection = String.format("//a[@role='option']/span[text()='%s']", listView);
        listViewSelection = By.xpath(selection);
        wait.waitForElementDisplayed(listViewSelection);
        click.clickElement(listViewSelection);
        waitForSalesForceLoaded();
    }

    /**
     * Click the list view dropdown menu
     */
    public void clickListViewDropdown( )
    {
        wait.waitForElementDisplayed(LIST_VIEW);
        click.clickElement(LIST_VIEW);
        waitForSalesForceLoaded();
    }

    /**
     * Selects current active cart in storefront based on Contact/Owner
     * @param ownerName
     */
    public void selectCurrentActiveCart(String ownerName)
    {
        By activeCart;
        String cartSelection = String.format("//th//a[@title='%s']/../../following-sibling::td//a[@title='Cart']", ownerName);
        activeCart = By.xpath(cartSelection);
        wait.waitForElementDisplayed(activeCart);
        click.clickElement(activeCart);
    }

    /**
     * Click pencil icon to edit active cart
     */
    public void clickPencilIcon( )
    {
        waitForSalesForceLoaded(3000);
        scroll.scrollElementIntoView(PENCIL_EDIT_BUTTON);
        wait.waitForElementDisplayed(PENCIL_EDIT_BUTTON);
        click.clickElement(PENCIL_EDIT_BUTTON);
        waitForSalesForceLoaded();
    }

    /**
     * Click Cart Items view all link
     */
    public void clickViewAllLink()
    {
        wait.waitForElementDisplayed(VIEW_ALL_BUTTON);
        click.clickElement(VIEW_ALL_BUTTON);
        waitForSalesForceLoaded();
    }

    /**
     * Click Cart Items link to view all cart items
     */
    public void clickCartItemsLink()
    {
        wait.waitForElementDisplayed(CART_ITEMS_LINK);
        click.clickElement(CART_ITEMS_LINK);
        waitForSalesForceLoaded();
    }

    /**
     * Set tax exempt checkbox
     * @param taxExempt
     */
    public void setTaxExemptCheckbox(Boolean taxExempt)
    {
        waitForPageLoad();
        wait.waitForElementDisplayed(TAX_EXEMPT_CHECKBOX);
        checkbox.setCheckbox(TAX_EXEMPT_CHECKBOX, taxExempt);
    }

    /**
     * Set Customer Exemption Certificate
     * @param exemptionCertificate
     */
    public void setExemptionCertificate(String exemptionCertificate)
    {
        waitForPageLoad();
        wait.waitForElementDisplayed(EXEMPTION_CERTIFICATE_INPUT);
        text.enterText(EXEMPTION_CERTIFICATE_INPUT, exemptionCertificate);
    }

    /**
     * Set purchase order number
     */
    public void setPurchaseOrderNumber(String purchaseOrderNumber)
    {
        wait.waitForElementDisplayed(PURCHASE_ORDER_NUMBER_INPUT);
        text.enterText(PURCHASE_ORDER_NUMBER_INPUT, purchaseOrderNumber);
    }

    /**
     * Set discount percent
     * @param discountPercent
     */
    public void setDiscountPercent(String discountPercent)
    {
        if(!discountPercent.equals(""))
        {
            wait.waitForElementDisplayed(DISCOUNT_PERCENT_INPUT);
            text.enterText(DISCOUNT_PERCENT_INPUT, discountPercent);
        }
    }

    /**
     * Set discount amount
     * @param discountAmount
     */
    public void setDiscountAmount(String discountAmount)
    {
        if(!discountAmount.equals(""))
        {
            wait.waitForElementDisplayed(DISCOUNT_AMOUNT_INPUT);
            text.enterText(DISCOUNT_AMOUNT_INPUT, discountAmount);
        }
    }

    /**
     * Save cart after editing
     */
    public void saveCart( )
    {
        closeCartTab();
        wait.waitForElementDisplayed(CART_SAVE_BUTTON);
        click.clickElement(CART_SAVE_BUTTON);
        waitForSalesForceLoaded();
    }

    /**
     * Closes currently open cart tab to trigger save prompt
     */
    public void closeCartTab( )
    {
        wait.waitForElementDisplayed(CART_CLOSE_BUTTON);
        click.clickElement(CART_CLOSE_BUTTON);
        waitForSalesForceLoaded();
    }

    /**
     * Set tax registration number
     * @param registrationNum
     */
    public void setTaxRegistrationNumber( String registrationNum )
    {
        wait.waitForElementDisplayed(TAX_REGISTRATION_NUMBER);
        text.enterText(TAX_REGISTRATION_NUMBER, registrationNum);
    }

    /**
     * Navigate to Related tab for Cart record
     */
    public void navigateToRelated()
    {
        waitForSalesForceLoaded();
        wait.waitForElementDisplayed(RELATED_TAB);
        click.clickElement(RELATED_TAB);
        waitForPageLoad();
    }

    /**
     * Navigate to Related tab for specified record
     *
     * @param recordType
     */
    public void navigateToRelated(String recordType)
    {
        String element = String.format(".//h1/div[text()='%s']/../../../../../../../../../../../../../..//ul/li/a[text()='Related']", recordType);
        By relatedTab = By.xpath(element);
        waitForSalesForceLoaded();
        wait.waitForElementDisplayed(relatedTab);
        waitForSalesForceLoaded();
        click.clickElement(relatedTab);
        waitForSalesForceLoaded();
    }

    /**
     * Navigate to Cart Line Item
     * @param productName
     */
    public void navigateToCartItem( String productName )
    {
        clickCartItemsLink();
        waitForPageLoad();
        String cartItem = String.format("//tr/th[@data-label='Cart Item Name']/*/span/div//a/slot/slot/span[text()='%s']", productName);
        By cartItemLink = By.xpath(cartItem);
        wait.waitForElementDisplayed(cartItemLink);
        click.clickElement(cartItemLink);
        waitForSalesForceLoaded();
    }

    /**
     * Select cart record based on cart total
     * @param cartTotal
     */
    public void navigateToSpecificCart( String cartTotal)
    {
        String locator = String.format("//tr//span[contains(@class, 'OutputCurrency') and contains(text(), '%s')]/../../..//a[@title='Cart']", cartTotal);
        By cartLink = By.xpath(locator);
        wait.waitForElementDisplayed(cartLink);
        click.clickElement(cartLink);
        waitForPageLoad();
    }

    /**
     * Gets the VAT Tax code
     * */
    public String getTaxCode(){
        WebElement taxCode = wait.waitForElementDisplayed(TAX_CODE);
        try{
            return taxCode.getText();
        }catch(org.openqa.selenium.TimeoutException e){
            return "No code";
        }
    }

    /**
     * Gets the VAT Vertex tax code
     * */
    public String getVertexTaxCode(){
        WebElement taxCode = wait.waitForElementDisplayed(VERTEX_TAX_CODE);
        try{
            return taxCode.getText();
        }catch(org.openqa.selenium.TimeoutException e){
            return "No code";
        }
    }

    /**
     * Gets the VAT invoice text code
     * */
    public String getInvoiceTextCode(){
        WebElement textCode = wait.waitForElementDisplayed(INVOICE_TEXT_CODE);
        try{
            return textCode.getText();
        }catch(org.openqa.selenium.TimeoutException e){
            return "No code";
        }
    }
}
