package com.vertex.quality.connectors.magento.admin.pages;

import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Representation of the Credit Memo page
 *
 * @author alewis
 */
public class M2AdminCreditMemoPage extends MagentoAdminPage
{
	protected By parentClass = By.className("admin__table-primary");
	protected By evenClass = By.className("even");
	protected By colSubtotalClass = By.className("col-subtotal");
	protected By colTaxAmountClass = By.className("col-tax-amount");
	protected By summaryTotal = By.className("summary-total");
	protected By priceExclTaxClass = By.className("price-excl-tax");
	protected By priceInclTaxClass = By.className("price-incl-tax");
	protected By markClass = By.className("admin__total-mark");
	protected By labelClass = By.className("label");
	protected By priceClass = By.className("price");

	protected By columnZero = By.className("col-0");
	protected By columnTwo = By.className("col-2");
	protected By salesUseClass = By.className("summary-details-first");
	protected By summaryDetailsClass = By.className("summary-details");
	protected By columnFourClass = By.className("col-4");
	protected By columnEightClass = By.className("col-8");
	protected By inputClass = By.className("qty-input");
	protected By productTitleClass = By.className("product-title");
	protected By productClass = By.className("col-product");
	protected By colRefund = By.className("col-refund");
	protected By grandTotalExclTax = By.xpath("//table[@class='data-table admin__table-secondary order-subtotal-table']//strong[contains(text(),'Grand Total (Excl.Tax)')]/../..//span[@class='price']");
	protected By grandTotalInclTax = By.xpath("//table[@class='data-table admin__table-secondary order-subtotal-table']//strong[contains(text(),'Grand Total (Incl.Tax)')]/../..//span[@class='price']");

	protected String shippingString = "Shipping (6%)";
	protected String subtotalString = "Subtotal (Excl.Tax)";
	protected By updateButtonClass = By.className("update-button");
	protected By submitButtonClass = By.className("submit-button");
	protected By updateQty1 = By.xpath("(//*[@class='input-text admin__control-text qty-input'])[1]");
	protected By updateQty2 = By.xpath("(//*[@class='input-text admin__control-text qty-input'])[2]");
	protected By updateQty3 = By.xpath("(//*[@class='input-text admin__control-text qty-input'])[3]");
	protected By adjustmentRefundBox = By.name("creditmemo[adjustment_positive]");
	protected By adjustmentFeeBox = By.id("adjustment_negative");
	protected By refundShippingBox = By.id("shipping_amount");
	protected By updateTotals = By.xpath("//*[contains(text(),'Update Totals')]//parent::button");
	protected By shippingTax = By.xpath("(//*[contains(text(),'Shipping & Handling')])[2]//following::span[2]");
	protected By totalRefunded = By.xpath(".//td[normalize-space(.)='Total Refunded']/following-sibling::td//span");

	protected By taxAmount = By.xpath(".//td[normalize-space(.)='Tax']/following-sibling::td//span");
	protected By subTotalAmount = By.xpath("(.//td[normalize-space(.)='Subtotal']/following-sibling::td//span)[2]");
	protected By shippingHandlingAmount = By.xpath("(.//td[contains(normalize-space(.),'Shipping & Handling')]/following-sibling::td//span)[2]");
	protected By discountAmount = By.xpath("(.//td[normalize-space(.)='Discount']/following-sibling::td//span)[2]");

	By maskClass = By.className("loading-mask");
	By spinnerClass = By.className("admin__data-grid-loading-mask");

	public M2AdminCreditMemoPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Refunds some but not all of items
	 */
	public void doPartialRefund( String refundItem, String qty )
	{
		waitForPageLoad();

		WebElement parent = wait.waitForElementPresent(parentClass);

		WebElement even = wait.waitForElementPresent(evenClass, parent);

		List<WebElement> products = wait.waitForAllElementsDisplayed(productClass, even);

		for ( WebElement product : products )
		{
			WebElement productTitle = wait.waitForElementDisplayed(productTitleClass, product);
			String productString = productTitle.getText();

			if ( refundItem.equals(productString) )
			{
				WebElement input = wait.waitForElementEnabled(inputClass, even);
				input.clear();
				input.sendKeys(qty);
			}
		}

		WebElement updateButton = wait.waitForElementEnabled(updateButtonClass);
		click.clickElement(updateButton);
	}

	/**
	 * Takes out of refund top item in order
	 */
	public void doPartialRefundPR2( String refundItem, String qty )
	{
		waitForPageLoad();

		WebElement parent = wait.waitForElementPresent(parentClass);

		List<WebElement> colRefunds = wait.waitForAllElementsPresent(colRefund);

		WebElement colRefund = colRefunds.get(2);
		WebElement input = wait.waitForElementEnabled(inputClass, colRefund);
		input.clear();
		input.sendKeys(qty);

		WebElement updateButton = wait.waitForElementEnabled(updateButtonClass);

		click.clickElement(updateButton);
	}

	/**
	 * Changes the quantity for a partial refund
	 */
	public void changeRefundQTY( String qty )
	{
		waitForPageLoad();
		List<WebElement> inputs = wait.waitForAllElementsDisplayed(inputClass);

		for ( WebElement input : inputs )
		{
			input.clear();
			input.sendKeys(qty);
		}

		WebElement updateButton = wait.waitForElementEnabled(updateButtonClass);
		click.clickElement(updateButton);
	}

	/**
	 * Gets the excluding tax price of the order in the 'Items to Refund' section
	 *
	 * @return String of excluding tax subtotal
	 */
	public String getSubtotalExclTax( )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		WebElement even = wait.waitForElementPresent(evenClass);
		WebElement colSubtotal = wait.waitForElementPresent(colSubtotalClass, even);
		WebElement priceExclTax = wait.waitForElementPresent(priceExclTaxClass, colSubtotal);
		WebElement price = wait.waitForElementPresent(priceClass, priceExclTax);
		String priceText = price.getText();

		return priceText;
	}

	/**
	 * Gets the excluding tax price of the first item in the order in the 'Items to Refund' section
	 *
	 * @return String of excluding tax subtotal
	 */
	public String getSubtotalExclTaxFirstInGroup( )
	{

		waitForPageLoad();
		WebElement even = wait.waitForElementEnabled(evenClass);
		List<WebElement> colSubtotals = wait.waitForAllElementsPresent(colSubtotalClass, even);
		WebElement colSubtotal = colSubtotals.get(1);
		WebElement priceExclTax = wait.waitForElementDisplayed(priceExclTaxClass, colSubtotal);
		WebElement price = wait.waitForElementDisplayed(priceClass, priceExclTax);
		//String priceText = text.retrieveTextFieldContents(price);
		String priceText = price.getText();

		return priceText;
	}

	/**
	 * Gets the including tax price of the first item in the order in the 'Items to Refund' section
	 *
	 * @return String of including tax subtotal
	 */
	public String getSubtotalInclTax( )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		WebElement even = wait.waitForElementPresent(evenClass);
		WebElement colSubtotal = wait.waitForElementPresent(colSubtotalClass, even);
		WebElement priceInclTax = wait.waitForElementPresent(priceInclTaxClass, colSubtotal);
		WebElement price = wait.waitForElementPresent(priceClass, priceInclTax);
		String priceText = price.getText();

		return priceText;
	}

	/**
	 * Gets the including tax price of the order in the 'Items to Refund' section
	 *
	 * @return String of including tax subtotal
	 */
	public String getSubtotalInclTaxFirstInGroup( )
	{
		WebElement even = wait.waitForElementEnabled(evenClass);
		List<WebElement> colSubtotals = wait.waitForAllElementsPresent(colSubtotalClass, even);
		WebElement colSubtotal = colSubtotals.get(1);
		WebElement priceInclTax = wait.waitForElementDisplayed(priceInclTaxClass, colSubtotal);
		WebElement price = wait.waitForElementDisplayed(priceClass, priceInclTax);
		//String priceText = text.retrieveTextFieldContents(price);
		String priceText = price.getText();

		return priceText;
	}

	/**
	 * Gets the tax amount of the order in the 'Items to Refund' section
	 *
	 * @return String of tax amount
	 */
	public String getTaxAmount( )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		WebElement even = wait.waitForElementPresent(evenClass);
		List<WebElement> taxAmounts = wait.waitForAllElementsPresent(colTaxAmountClass, even);
		//WebElement taxAmount = taxAmounts.get(1);
		WebElement taxAmount = taxAmounts.get(0);
		String taxAmountText = taxAmount.getText();

		return taxAmountText;
	}

	/**
	 * Gets the tax amount of the first item in the order in the 'Items to Refund' section
	 *
	 * @return String of tax amount
	 */
	public String getTaxAmountFirstInGroup( )
	{
		WebElement even = wait.waitForElementPresent(evenClass);
		//WebElement taxAmount = wait.waitForElementPresent(colTaxAmountClass, even);
		List<WebElement> taxAmounts = wait.waitForAllElementsPresent(colTaxAmountClass, even);
		WebElement taxAmount = taxAmounts.get(1);
		String taxAmountText = taxAmount.getText();

		return taxAmountText;
	}

	/**
	 * Gets subtotal price excluding tax from Refund Totals at bottom right corner of page
	 * *
	 *
	 * @return a string of subtotal excluding tax
	 */
	public String getRefundSubtotalExclTax( )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		wait.waitForElementNotDisplayed(spinnerClass);
		String priceText = null;
		List<WebElement> rowTotals = wait.waitForAllElementsDisplayed(columnZero);

		WebElement rowTotal = element.selectElementByNestedLabel(rowTotals, labelClass, subtotalString);
		if ( rowTotal != null )
		{
			WebElement exclTaxPrice = wait.waitForElementPresent(priceClass, rowTotal);
			priceText = text.getElementText(exclTaxPrice);
		}

		return priceText;
	}

	/**
	 * Gets subtotal price including tax from Refund Totals at bottom right corner of page
	 *
	 * @return a string of subtotal including tax
	 */
	public String getRefundSubtotalInclTax( )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		WebElement rowTotal = wait.waitForElementPresent(columnTwo);
		WebElement inclTaxPrice = wait.waitForElementPresent(priceClass, rowTotal);
		String priceText = inclTaxPrice.getText();

		return priceText;
	}

	/**
	 * Opens tax blind
	 */
	public void clicksTaxBlind( )
	{
		wait.waitForElementNotDisplayed(maskClass);
		wait.waitForElementNotDisplayed(spinnerClass);
		WebElement summary = wait.waitForElementPresent(summaryTotal);
		summary.click();
		waitForPageLoad();
	}

	/**
	 * Gets Sales and Use tax from Refund Totals at bottom right corner of page
	 *
	 * @return a string of Sales and Use tax total
	 */
	public String getSalesUseTax( )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		wait.waitForElementNotDisplayed(spinnerClass);
		WebElement salesUse = wait.waitForElementPresent(salesUseClass);
		WebElement price = wait.waitForElementPresent(priceClass, salesUse);
		String priceText = price.getText();

		return priceText;
	}

	/**
	 * Gets Shipping tax from Refund Totals at bottom right corner of page
	 *
	 * @return a string of Shipping tax total
	 */
	public String getShippingTax( )
	{
		String priceText = null;
		List<WebElement> salesUses = wait.waitForAllElementsPresent(summaryDetailsClass);

		WebElement salesUse = element.selectElementByNestedLabel(salesUses, markClass, shippingString);
		if ( salesUse != null )
		{
			WebElement price = wait.waitForElementPresent(priceClass, salesUse);
			priceText = text.getElementText(price);
		}
		return priceText;
	}

	/**
	 * Gets price excluding tax from Refund Totals at bottom right corner of page
	 *
	 * @return a string of grand total excluding tax
	 */
	public String getRefundTotalExclTax( )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		scroll.scrollElementIntoView(grandTotalInclTax);
		WebElement exclTaxPrice = wait.waitForElementPresent(grandTotalExclTax);
		String priceText = exclTaxPrice.getText();

		return priceText;
	}

	/**
	 * Gets price including tax from Refund Totals at bottom right corner of page
	 *
	 * @return a string of grand total including tax
	 */
	public String getRefundTotalInclTax( )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		WebElement inclTaxPrice = wait.waitForElementPresent(grandTotalInclTax);
		String priceText = inclTaxPrice.getText();

		return priceText;
	}

	/**
	 * clicks the Refund Offline button on button right corner of page
	 *
	 * @return the order view info page
	 */
	public M2AdminOrderViewInfoPage clickRefundOfflineButton( )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		WebElement submitButton = wait.waitForElementEnabled(submitButtonClass);
		click.clickElement(submitButton);
		waitForPageLoad();

		return initializePageObject(M2AdminOrderViewInfoPage.class);
	}

	/**
	 * clicks the Refund Offline button on button right corner of page
	 *
	 * @return the order view info page
	 */
	public M2AdminOrderViewInfoPage addAdjustmentRefund(String qty1, String qty2, String qty3, String adjustRefund)
	{
		waitForPageLoad();

		wait.waitForElementNotDisplayed(maskClass);
		if(qty1 != null)
		{
			WebElement updateQtyText1 = wait.waitForElementEnabled(updateQty1);
			text.enterText(updateQtyText1,qty1);
		}
		if(qty2 != null)
		{
			WebElement updateQtyText2 = wait.waitForElementEnabled(updateQty2);
			text.enterText(updateQtyText2,qty2);
		}
		if(qty3 != null)
		{
			WebElement updateQtyText3 = wait.waitForElementEnabled(updateQty3);
			text.enterText(updateQtyText3,qty3);
		}
		WebElement adjustFeeText = wait.waitForElementEnabled(adjustmentRefundBox);
		text.enterText(adjustFeeText,adjustRefund);
		clickUpdateTotals();
		return initializePageObject(M2AdminOrderViewInfoPage.class);
	}

	/**
	 * Removes the Shipping Amount from the return
	 */
	public void removeShippingFromRefund() {
		waitForSpinnerToBeDisappeared();
		text.enterText(wait.waitForElementPresent(refundShippingBox), "0.00");
	}

	/**
	 * Enters adjusted amount & click Update Total button
	 *
	 * @param amount Adjusted amount
	 * @return the order view info page
	 */
	public M2AdminOrderViewInfoPage enterAdjustedFee(String amount) {
		waitForSpinnerToBeDisappeared();
		WebElement adjustFeeText = wait.waitForElementEnabled(adjustmentFeeBox);
		text.enterText(adjustFeeText,amount);
		clickUpdateTotals();
		return new M2AdminOrderViewInfoPage(driver);
	}

	/**
	 * Enters refund shipping amount
	 *
	 * @param amount refund amount
	 */
	public void enterRefundShipping(String amount) {
		waitForSpinnerToBeDisappeared();
		WebElement refundFee = wait.waitForElementEnabled(refundShippingBox);
		text.enterText(refundFee,amount);
		text.pressTab(refundFee);
	}

	/**
	 * Gets tax amount from the UI
	 *
	 * @return tax amount from UI.
	 */
	public double getTaxFromUI() {
		waitForPageLoad();
		waitForSpinnerToBeDisappeared();
		wait.waitForElementPresent(subTotalAmount);
		wait.waitForElementPresent(refundShippingBox);
		return Double.parseDouble(text.getElementText(wait.waitForElementPresent(taxAmount)).replace("$", "").replace(",", ""));
	}

	/**
	 * Calculates percentage based or expected tax
	 *
	 * @param taxAmount percent of tax
	 * @return calculated percent based tax
	 */
	public double calculatePercentBasedTax(double taxAmount) {
		double subtotal = 0;
		double refundShip = 0;
		double discount = 0;
		double adjustmentFee = 0;
		double adjustmentRefund = 0;
		double expectedTax = 0;
		waitForPageLoad();
		waitForSpinnerToBeDisappeared();
		subtotal = Double.parseDouble(text.getElementText(wait.waitForElementPresent(subTotalAmount)).replace("$", "").replace(",", ""));
		refundShip = Double.parseDouble(attribute.getElementAttribute(wait.waitForElementPresent(refundShippingBox), "value").replace("$", "").replace(",", ""));
		adjustmentFee = Double.parseDouble(attribute.getElementAttribute(wait.waitForElementPresent(adjustmentFeeBox), "value").replace("$", "").replace(",", ""));
		adjustmentRefund = Double.parseDouble(attribute.getElementAttribute(wait.waitForElementPresent(adjustmentRefundBox), "value").replace("$", "").replace(",", ""));
		if (element.isElementPresent(discountAmount)) {
			discount = Double.parseDouble(text.getElementText(discountAmount).replace("-", "").replace("$", "").replace(",", ""));
		}
		expectedTax = (subtotal + refundShip + adjustmentRefund - adjustmentFee - discount) * (taxAmount / 100);
		return Double.parseDouble(String.format("%.2f", expectedTax));
	}

	/**
	 * clicks the Refund Offline button on button right corner of page
	 *
	 * @return the order view info page
	 */
	public M2AdminOrderViewInfoPage clickUpdateTotals()
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		WebElement updateTotalsButton = wait.waitForElementPresent(updateTotals);
		click.clickElement(updateTotalsButton);
		waitForPageLoad();
		return initializePageObject(M2AdminOrderViewInfoPage.class);
	}

	/**
	 * selects a shipping rate for the order
	 *
	 * @return tax rate
	 */
	public double getMagentoRefundTaxRate()
	{
		waitForPageLoad();

		WebElement shippingTaxText = wait.waitForElementPresent(shippingTax);
		String taxPrice = text.getElementText(shippingTaxText).replaceAll("[^0-9]", "");;
		double priceDoubleTax = Double.parseDouble(taxPrice);
		double taxRate = priceDoubleTax + Double.parseDouble(MagentoData.PARTIAL_RETURN_AMOUNT.data);
		return taxRate;
	}

	/**
	 * selects a shipping rate for the order
	 *
	 * @return tax rate
	 */
	public double getMagentoTotalRefunded()
	{
		waitForPageLoad();
		WebElement TotalRefundText = wait.waitForElementPresent(totalRefunded);
		String totalPrice = text.getElementText(TotalRefundText).replaceAll("[^0-9]", "");;
		double priceDoubleTax = Double.parseDouble(totalPrice);

		return priceDoubleTax;
	}

}
