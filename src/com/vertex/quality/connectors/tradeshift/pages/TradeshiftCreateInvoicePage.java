package com.vertex.quality.connectors.tradeshift.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.vertex.quality.connectors.tradeshift.utils.TradeshiftUtils;

import java.util.List;

public class TradeshiftCreateInvoicePage extends VertexPage {
    protected By sendInvoiceTo = By.id("query");
    protected By invoiceNumberInput = By.id("documentId");
    protected By itemIdInput = By.id("lines_0__itemIdentification");
    protected By itemDescriptionInput = By.id("lines_0__description");
    protected By itemQuantityInput = By.id("lines_0__amount");
    protected By itemPriceInput = By.id("lines_0__price");
    protected By itemTaxInput = By.id("lines_0__vatLabel");
    protected By itemIdInput2 = By.id("lines_2__itemIdentification");
    protected By itemDescriptionInput2 = By.id("lines_2__description");
    protected By itemQuantityInput2 = By.id("lines_2__amount");
    protected By itemPriceInput2 = By.id("lines_2__price");
    protected By itemTaxInput2 = By.id("lines_2__vatLabel");
    protected By itemTaxSelection = By.xpath("//*[@id=\"select-tax-panel\"]/div/div[1]/menu[2]/li[2]/button/span");
    protected By sendButton = By.id("savedispatch");
    protected By clearDocDate = By.xpath("/html/body/div[1]/div/div/div/div[2]/div[1]/div[2]/div/div[1]/span[1]/span[2]/figure/del");
    protected By purchaseOrderCard = By.xpath("/html/body/div[1]/div/div/div/div[2]/div[2]/div/div[1]/div/div/div/div[2]/a");
    protected By useAsDraftLink = By.className("tst-use-template-button");
    protected By sendTo = By.className("tst-receiverdropdown");
    protected By mainAppFrame = By.id("main-app-iframe");
    protected By legacyFrame = By.id("legacy-frame");
    protected By purchaseOrderList = By.className("receipt-icon");
    protected By searchResult = By.className("searchresult");
    protected By savePaymentmeans = By.id("savePaymentmeans");
    protected By addNewLineItemButton = By.xpath("//div/div/a[contains(text(),'Add new line')]");
    protected By invoiceTaxText = By.xpath("//*/li/p[@class='val money']");
    protected By mainIframe = By.xpath("//*[@id=\"frame-container-main\"]/iframe");

    public TradeshiftCreateInvoicePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Enter the shipping address for the
     * recipient of the invoice when creating it
     * from a purchase order
     */
    public void fillOutToFromPO(){
        WebElement toButton = wait.waitForElementDisplayed(sendTo);
        click.clickElementCarefully(toButton);
        WebElement search = wait.waitForElementDisplayed(searchResult);
        click.clickElementCarefully(search);
        jsWaiter.sleep(3000);
    }

    /**
     * Fills out the recipient of the
     * invoice for all forms that are
     * not an invoice from a PO
     * as a buyer
     * */
    public void sendInvoiceTo(){
        WebElement to = wait.waitForElementDisplayed(sendInvoiceTo);
        click.clickElementCarefully(to);
        text.enterText(to,"Services Supplier, Inc");
        jsWaiter.sleep(3000);
        to.sendKeys(Keys.DOWN);
        to.sendKeys(Keys.ENTER);
    }

    /**
     * Enter the shipping address for the
     * recipient of the invoice as a supplier
     */
    public void fillOutToSupplier(){
        WebElement to = wait.waitForElementDisplayed(sendInvoiceTo);
        click.clickElementCarefully(to);
        text.enterText(to,"Vertex Sndbx Branch 1");
        jsWaiter.sleep(3000);
        to.sendKeys(Keys.DOWN);
        to.sendKeys(Keys.ENTER);
    }

    /**
     * Enters an invoice number in the invoice number
     * input box for all forms
     * */
    public void fillInvoiceNumber(){
        WebElement invoiceNumber = wait.waitForElementDisplayed(invoiceNumberInput);
        click.clickElementCarefully(invoiceNumber);
        text.enterText(invoiceNumber,TradeshiftUtils.randomInvoiceNumber());
    }

    /**
     * Fills in the fields for the item being
     * ordered in all forms
     * */
    public void fillItem(String item, String description, String quantity, String price){
        scroll.scrollBottom();
        WebElement itemInput = wait.waitForElementDisplayed(itemIdInput);
        click.clickElementCarefully(itemInput);
        text.enterText(itemInput,item);
        WebElement itemDescription = wait.waitForElementDisplayed(itemDescriptionInput);
        click.clickElementCarefully(itemDescription);
        text.enterText(itemDescription,description);
        WebElement itemQuantity = wait.waitForElementDisplayed(itemQuantityInput);
        click.clickElementCarefully(itemQuantity);
        text.enterText(itemQuantity, quantity);
        WebElement itemPrice = wait.waitForElementDisplayed(itemPriceInput);
        click.clickElementCarefully(itemPrice);
        text.enterText(itemPrice,price);
        WebElement itemTax = wait.waitForElementDisplayed(itemTaxInput);
        click.clickElementCarefully(itemTax);
        WebElement taxSelection = wait.waitForElementDisplayed(itemTaxSelection);
        click.clickElementCarefully(taxSelection);
    }

    /**
     * adds a new line item to an invoice
     * */
    public void fillSecondLineItem(String item, String description, String quantity, String price){
        WebElement itemInput = wait.waitForElementDisplayed(itemIdInput2);
        click.clickElementCarefully(itemInput);
        text.enterText(itemInput,item);
        WebElement itemDescription = wait.waitForElementDisplayed(itemDescriptionInput2);
        click.clickElementCarefully(itemDescription);
        text.enterText(itemDescription,description);
        WebElement itemQuantity = wait.waitForElementDisplayed(itemQuantityInput2);
        click.clickElementCarefully(itemQuantity);
        text.enterText(itemQuantity, quantity);
        WebElement itemPrice = wait.waitForElementDisplayed(itemPriceInput2);
        click.clickElementCarefully(itemPrice);
        text.enterText(itemPrice,price);
        WebElement itemTax = wait.waitForElementDisplayed(itemTaxInput2);
        click.clickElementCarefully(itemTax);
        WebElement taxSelection = wait.waitForElementDisplayed(itemTaxSelection);
        click.clickElementCarefully(taxSelection);
    }

    public void addNewLineItem(){
        WebElement newLineButton = wait.waitForElementDisplayed(addNewLineItemButton);
        click.clickElementCarefully(newLineButton);
    }

    /**
     * Click the send button
     * */
    public void submitInvoice(){
        WebElement send = wait.waitForElementDisplayed(sendButton);
        click.clickElementCarefully(send);

    }

    /**
     * Clears the purchase order date created
     * filter in order to click a completed PO
     * */
    public void clearDocDate(){
        WebElement clear = wait.waitForElementDisplayed(clearDocDate);
        click.clickElementCarefully(clear);
    }

    /**
     * Clicks the PO we need to use to create
     * an invoice
     * */
    public void clickPO(){
        List<WebElement> poList = wait.waitForAllElementsPresent(purchaseOrderList);
        WebElement poFirst = poList.get(1);
        window.switchToFrame(mainAppFrame);
        click.clickElementCarefully(poFirst);
    }

    /**
     * Clicks the "use as draft" button
     * to create a new invoice
     * */
    public void clickUseAsDraft(){
        WebElement useAsDraft = wait.waitForElementDisplayed(useAsDraftLink);
        click.clickElementCarefully(useAsDraft);
    }

    /**
     * Used to access ui elements on the buyer portal
     * */
    public void switchToMainIframeBuyer(){
    	driver.switchTo().defaultContent();
    	driver.switchTo().frame("main-app-iframe");
    }

	/**
	 * Used to access ui elements on the supplier portal
	 * */
	public void switchToMainIframeSupplier(){
		driver.switchTo().defaultContent();
		WebElement e = wait.waitForElementDisplayed(mainIframe);
		driver.switchTo().frame(e);
	}

	/**
	 * This is used to access nested iframes in the
	 * buyer and supplier portals
	 * */
	public void switchToLegacyIframe(){
		driver.switchTo().defaultContent();
		driver.switchTo().frame("main-app-iframe");
		driver.switchTo().frame("legacy-frame");
	}

    /**
     * gets the tax calculation for an invoice item
     *
     * @return the tax calculation
     * */
    public String getInvoiceTax(){
        List<WebElement> tax = wait.waitForAllElementsDisplayed(invoiceTaxText);
        return tax.get(1).getText();
    }
}
