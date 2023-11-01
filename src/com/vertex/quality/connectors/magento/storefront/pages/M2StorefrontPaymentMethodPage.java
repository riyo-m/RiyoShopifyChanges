package com.vertex.quality.connectors.magento.storefront.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.common.pages.MagentoStorefrontPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

/**
 * Representation of the Payment Method Page
 *
 * @author alewis
 */
public class M2StorefrontPaymentMethodPage extends MagentoStorefrontPage
{
	protected By billingAddressShippingId = By.id("billing-address-same-as-shipping-checkmo");
	protected By addressDropDownName = By.name("billing_address_id");
	protected By editAddressClass = By.className("action-edit-address");
	protected By actionUpdateClass = By.className("action-update");
	protected By billingAddressDetailsClass = By.className("billing-address-details");

	protected By grandClass = By.className("grand");
	protected By subClass = By.className("sub");
	protected By markClass = By.className("mark");
	protected By labelClass = By.className("label");
	protected By amountClass = By.className("amount");
	protected By shippingClass = By.className("shipping");
	protected By subPriceClass = By.className("price");

	protected By paymentMethodContentClass = By.className("payment-method-content");
	protected By checkoutAgreementsClass = By.className("actions-toolbar");
	protected By primaryClass = By.className("primary");
	protected By checkoutClass = By.className("checkout");
	protected By buttonClass = By.tagName("button");

	protected By blindClass = By.className("totals-tax-summary");
	protected By taxTotalsClass = By.className("totals-tax-details");
	protected By taxNoBlindClass = By.className("totals-tax");

	protected String placeOrderButtonText = "Place Order";
	protected String subtotalInclString = "Cart Subtotal Incl. Tax";
	protected String subtotalExclString = "Cart Subtotal Excl. Tax";
	protected String exclShippingString = "Shipping Excl. Tax";
	protected String inclShippingString = "Shipping Incl. Tax";
	protected String salesUseString = "Sales and Use (6%)";
	protected String salesUseStringCali = "Sales and Use (10.25%)";
	protected String salesUseStringNineFive = "Sales and Use (9.5%)";
	protected String shippingTaxString = "Shipping (6%)";
	protected String shippingTaxStringCali = "Shipping (10.25%)";
	protected String inclTaxString = "Order Total Incl. Tax";
	protected String exclTaxString = "Order Total Excl. Tax";
	By maskClass = By.className("loading-mask");
	protected By checkID = By.id("checkmo");
	protected By paymentMethodLabel = By.xpath(".//div[text()='Payment Method']");
	protected By creditCardRadioButton = By.xpath(".//span[text()='Credit Card']");
	protected By sameBillingShippingCreditCardCheckbox = By.xpath("(.//input[contains(@id,'billing-address-same-as-shipping')])[2]");
	protected By sameBillingShippingCheckMoneyCheckbox = By.xpath("(.//input[contains(@id,'billing-address-same-as-shipping')])[1]");
	protected By newBillingAddressDropdown = By.xpath(".//select[@name='billing_address_id']");
	protected By iFrameCreditCardNo = By.xpath(".//iframe[@type='number']");
	protected By creditCardNoBox = By.xpath(".//input[@id='credit-card-number']");
	protected By iFrameExpiration = By.xpath(".//iframe[@type='expirationDate']");
	protected By expirationDateBox = By.xpath(".//input[@id='expiration']");
	protected By iFrameCvv = By.xpath(".//iframe[@type='cvv']");
	protected By cvvBox = By.xpath(".//input[@id='cvv']");
	protected By otpIframe = By.xpath(".//iframe[@id='Cardinal-CCA-IFrame']");
	protected By otpPopup = By.xpath(".//div[@id='Cardinal-Modal']");
	protected By otpCode = By.xpath(".//p[@class='challengeinfotext']");
	protected By otpCodeBox = By.xpath(".//input[@name='challengeDataEntry']");
	protected By otpSubmitButton = By.xpath(".//input[@value='SUBMIT']");
	protected By orderSummaryLabel = By.xpath(".//span[text()='Order Summary']");
	protected By cartSubtotalLabel = By.xpath(".//th[text()='Cart Subtotal']");
	protected By taxLabel = By.xpath(".//th[text()='Tax']");
	protected By shippingLabel = By.xpath(".//th[contains(normalize-space(),'Shipping')]");
	protected By subtotalAmount = By.xpath(".//th[text()='Cart Subtotal']//following-sibling::td//span");
	protected By discountAmount = By.xpath(".//th[contains(normalize-space(.),'Discount')]//following-sibling::td//span");
	protected By shippingAmount = By.xpath(".//th[contains(normalize-space(.),'Shipping')]//following-sibling::td//span");
	protected By retailDeliveryFee = By.xpath(".//th[contains(normalize-space(.),'Retail Delivery Fee')]//following-sibling::td//span");
	protected By taxAmount = By.xpath(".//th[text()='Tax']//following-sibling::td//span");
	protected By orderTotalAmount = By.xpath(".//th[normalize-space()='Order Total']//following-sibling::td//span");
	protected By placeOrderButtonCheckMoney = By.xpath("(.//button[normalize-space(.)='Place Order'])[1]");
	protected By placeOrderButtonCreditCard = By.xpath("(.//button[normalize-space(.)='Place Order'])[2]");
	protected By discountXpath = By.xpath("//*[@id=\"block-discount-heading\"]");
	protected By discountFieldXpath = By.xpath("//*[@id=\"discount-code\"]");
	protected By discountButtonXpath = By.xpath("//*[@id=\"discount-form\"]/div[2]/div/button/span/span");
	protected By shippingAddress = By.xpath(".//div[@class='ship-to']//div[@class='shipping-information-content']");

	protected String innerHTMLString = MagentoData.innerHTML.data;

	public M2StorefrontPaymentMethodPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * switches the address for the order for customers with multiple addresses
	 */
	public void switchAddress( String address )
	{
		dropdown.selectDropdownByIndex(addressDropDownName, 1);
	}

	/**
	 * changes the address of shipping
	 */
	public void clickEditAddress( String address )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		WebElement editAddress = wait.waitForElementPresent(editAddressClass);
		editAddress.click();
		switchAddress(address);
		WebElement actionUpdate = wait.waitForElementPresent(actionUpdateClass);
		actionUpdate.click();
		waitForPageLoad();
		try
		{
			attribute.waitForElementAttributeChange(billingAddressDetailsClass, innerHTMLString);
		}
		catch ( Exception e )
		{
		}
	}

	/**
	 * selects payment method
	 */
	public void selectPaymentMethod() {
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		WebElement checkField = wait.waitForElementDisplayed(checkID);
		click.clickElementCarefully(checkField);
	}

	/**
	 * Changes the address for a physical product
	 */
	public void editAddressPhysicalProduct( String address )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		WebElement billingAddressShipping = wait.waitForElementPresent(billingAddressShippingId);
		billingAddressShipping.click();
		switchAddress(address);
		WebElement actionUpdate = wait.waitForElementPresent(actionUpdateClass);
		actionUpdate.click();
		waitForPageLoad();
		attribute.waitForElementAttributeChange(billingAddressDetailsClass, innerHTMLString);
	}

	/**
	 * gets the subtotal price excluding tax from the Order Summary section
	 *
	 * @return double of subtotal excluding tax
	 */
	public double getSubtotalExclTax( )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		String priceString = null;
		List<WebElement> subs = wait.waitForAllElementsPresent(subClass);

		WebElement sub = element.selectElementByNestedLabel(subs, markClass, subtotalExclString);
		if ( sub != null )
		{
			WebElement price = wait.waitForElementPresent(subPriceClass, sub);
			priceString = price.getText();
		}

		String parsedTaxValue = priceString.substring(1);
		double priceDouble = Double.parseDouble(parsedTaxValue);

		return priceDouble;
	}

	/**
	 * gets the subtotal price including tax from the Order Summary section
	 *
	 * @return double of shipping including tax
	 */
	public double getSubtotalInclTax( )
	{
		this.refreshPage();
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);

		//		try
		//		{
		//			wait.waitForElementPresent(subClass, 3);
		//		}
		//		catch ( Exception e )
		//		{
		//
		//		}

		String priceString = null;
		List<WebElement> subs = wait.waitForAllElementsPresent(subClass);

		WebElement sub = element.selectElementByNestedLabel(subs, markClass, subtotalInclString);
		if ( sub != null )
		{
			WebElement price = wait.waitForElementPresent(subPriceClass, sub);
			priceString = text.getElementText(price);
		}

		String parsedTaxValue = priceString.substring(1);
		double priceDouble = Double.parseDouble(parsedTaxValue);

		return priceDouble;
	}

	/**
	 * gets the shipping price excluding tax from the Order Summary section
	 *
	 * @return double of shipping excluding tax
	 */
	public double getShippingExclTax( )
	{
		String priceString = null;
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		List<WebElement> shippings = wait.waitForAllElementsPresent(shippingClass);

		for ( WebElement shipping : shippings )
		{
			WebElement mark = wait.waitForElementPresent(markClass, shipping);
			WebElement label = wait.waitForElementPresent(labelClass, mark);
			String labelText = label.getText();

			if ( labelText.equals(exclShippingString) )
			{
				WebElement price = wait.waitForElementPresent(subPriceClass, shipping);
				priceString = price.getText();
			}
		}

		String parsedTaxValue = priceString.substring(1);
		double priceDouble = Double.parseDouble(parsedTaxValue);

		return priceDouble;
	}

	/**
	 * gets the shipping price including tax from the Order Summary section
	 *
	 * @return double of shipping including tax
	 */
	public double getShippingInclTax( )
	{
		String priceString = null;
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		List<WebElement> shippings = wait.waitForAllElementsPresent(shippingClass);

		for ( WebElement shipping : shippings )
		{
			WebElement mark = wait.waitForElementPresent(markClass, shipping);
			WebElement label = wait.waitForElementPresent(labelClass, mark);
			String labelText = label.getText();

			if ( labelText.equals(inclShippingString) )
			{
				WebElement price = wait.waitForElementPresent(subPriceClass, shipping);
				priceString = price.getText();
			}
		}

		String parsedTaxValue = priceString.substring(1);
		double priceDouble = Double.parseDouble(parsedTaxValue);

		return priceDouble;
	}

	/**
	 * Opens tax blind
	 */
	public void openTaxBlind( )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
//		WebElement blind = wait.waitForElementEnabled(blindClass);
//
//		blind.click();

		WebElement taxBlindDisplayed;
		WebElement amount;
		try
		{
			taxBlindDisplayed = wait.waitForElementDisplayed(blindClass, 3);
			amount = wait.waitForElementEnabled(amountClass, taxBlindDisplayed, 3);
		}
		catch ( Exception e )
		{
			taxBlindDisplayed = wait.waitForElementDisplayed(blindClass);
			amount = wait.waitForElementEnabled(amountClass, taxBlindDisplayed);
		}

		try
		{
			attribute.waitForElementAttributeChange(amount, "innerHTML", 10);
		}
		catch ( Exception e )
		{
			System.out.println("Couldn't do");
		}


		waitForPageLoad();

		scroll.scrollElementIntoView(amount);
		click.clickElementCarefully(amount);
	}

	/**
	 * gets the tax from the Order Summary section when there is no tax blind,
	 * so there is only one category
	 *
	 * @return a double of tax
	 */
	public double getTaxNoBlind( )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		WebElement taxTotal = wait.waitForElementDisplayed(taxNoBlindClass);

		WebElement amount = wait.waitForElementPresent(amountClass, taxTotal);
		String price = amount.getText();

		String parsedTaxValue = price.substring(1);
		double priceDouble = Double.parseDouble(parsedTaxValue);

		return priceDouble;
	}

	/**
	 * gets the Sales and Use tax from the Order Summary section
	 *
	 * @return a double of Sales and Use tax
	 */
	public double getSalesUseTax( )
	{
		String price = null;
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		List<WebElement> taxTotals = wait.waitForAllElementsPresent(taxTotalsClass);

		for ( WebElement taxTotal : taxTotals )
		{
			WebElement mark = wait.waitForElementPresent(markClass, taxTotal);
			String markText = mark.getText();

			if ( markText.equals(salesUseString) || markText.equals(salesUseStringCali) || markText.equals(
					salesUseStringNineFive) )
			{
				WebElement amount = wait.waitForElementPresent(amountClass, taxTotal);
				price = amount.getText();
			}
		}
		String parsedTaxValue = price.substring(1);
		double priceDouble = Double.parseDouble(parsedTaxValue);

		return priceDouble;
	}

	/**
	 * gets the Shipping tax from the Order Summary section
	 *
	 * @return a double of shipping tax
	 */
	public double getShippingTaxInBlind( )
	{
		String price = null;
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		List<WebElement> taxTotals = wait.waitForAllElementsPresent(taxTotalsClass);

		for ( WebElement taxTotal : taxTotals )
		{
			WebElement mark = wait.waitForElementPresent(markClass, taxTotal);
			String markText = mark.getText();

			if ( markText.equals(shippingTaxString) || markText.equals(shippingTaxStringCali) )
			{
				WebElement amount = wait.waitForElementPresent(amountClass, taxTotal);
				price = amount.getText();
			}
		}
		String parsedTaxValue = price.substring(1);
		double priceDouble = Double.parseDouble(parsedTaxValue);

		return priceDouble;
	}

	/**
	 * gets the total price excluding tax from the Order Summary section
	 *
	 * @return a double of total price excluding tax of the item
	 */
	public Double getTotalExclTax( )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		List<WebElement> grandTotals = wait.waitForAllElementsPresent(grandClass);
		String price = null;

		WebElement grandTotal = element.selectElementByNestedLabel(grandTotals, markClass, exclTaxString);
		if ( grandTotal != null )
		{
			WebElement amount = grandTotal.findElement(amountClass);
			WebElement grandPrice = amount.findElement(subPriceClass);
			price = grandPrice.getText();
		}
		String parsedTaxValue = price.substring(1);
		double priceDouble = Double.parseDouble(parsedTaxValue);

		return priceDouble;
	}

	/**
	 * gets the total price including tax from the Order Summary section
	 *
	 * @return a double of total price of the item
	 */
	public Double getTotalInclTax( )
	{
		this.refreshPage();
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);

		//		try
		//		{
		//			wait.waitForElementPresent(subClass, 3);
		//		}
		//		catch ( Exception e )
		//		{
		//
		//		}

		List<WebElement> grandTotals = wait.waitForAllElementsPresent(grandClass);
		String price = null;

		WebElement grandTotal = element.selectElementByNestedLabel(grandTotals, markClass, inclTaxString);
		if ( grandTotal != null )
		{
			WebElement amount = grandTotal.findElement(amountClass);
			WebElement grandPrice = amount.findElement(subPriceClass);
			price = grandPrice.getText();
		}
		String parseTaxValue = price.substring(1);
		double priceDouble = Double.parseDouble(parseTaxValue);

		return priceDouble;
	}

	public void applyDiscount( String discountString )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		WebElement discount = wait.waitForElementDisplayed(discountXpath);
		click.clickElementCarefully(discount);

		WebElement discountField = wait.waitForElementDisplayed(discountFieldXpath);
		click.clickElementCarefully(discountField);
		text.enterText(discountField, discountString);

		WebElement discountButton = wait.waitForElementDisplayed(discountButtonXpath);
		click.clickElementCarefully(discountButton);
	}

	/**
	 * clicks the Place Order Button on the Payment Method page. Inputting the order
	 *
	 * @return the Thank You Page
	 */
	public M2StorefrontThankYouPage clickPlaceOrderButton( )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		String buttonText = null;
		wait.waitForElementDisplayed(paymentMethodContentClass);
		WebElement paymentMethodContent = wait.waitForElementPresent(paymentMethodContentClass);
		waitForPageLoad();
		List<WebElement> orderButtons = paymentMethodContent.findElements(checkoutAgreementsClass);

		for ( WebElement orderButton : orderButtons )
		{
			waitForPageLoad();
			WebElement placeButtonPrimary = orderButton.findElement(primaryClass);
			WebElement button = placeButtonPrimary.findElement(buttonClass);
			buttonText = button.getAttribute("title");
			waitForPageLoad();
			if ( buttonText != null && buttonText.equals(placeOrderButtonText) )
			{
				waitForPageLoad();
				orderButton = button;
				wait.waitForElementDisplayed(orderButton,5);
				click.clickElement(orderButton);
			}
		}
		waitForPageLoad();
		return new M2StorefrontThankYouPage(driver);
	}

	public M2StorefrontThankYouPage clickPlaceOrderButtonDiscount( )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		String buttonText = null;
		WebElement paymentMethodContent = wait.waitForElementPresent(paymentMethodContentClass);
		List<WebElement> orderButtons = paymentMethodContent.findElements(checkoutAgreementsClass);

		for ( WebElement orderButton : orderButtons )
		{
			WebElement placeButtonPrimary = orderButton.findElement(primaryClass);
			WebElement button = placeButtonPrimary.findElement(buttonClass);
			buttonText = button.getAttribute("title");

			if ( buttonText != null && buttonText.equals(placeOrderButtonText) )
			{
				orderButton = button;
				System.out.println(orderButton.getText());
				WebElement checkoutButton = wait.waitForElementPresent(checkoutClass, orderButton);
				click.clickElement(checkoutButton);
			}
		}
		return new M2StorefrontThankYouPage(driver);
	}

	/**
	 * Select the checkbox for Same billing & shipping address
	 */
	public void setSameBillingShippingAddress() {
		waitForPageLoad();
		waitForSpinnerToBeDisappeared();
		wait.waitForElementNotDisplayed(maskClass);
		checkbox.setCheckbox(wait.waitForElementPresent(sameBillingShippingCheckMoneyCheckbox), true);
	}

	/**
	 * Reads Customer's shipping address
	 *
	 * @return Customer's shipping address
	 */
	public String getShippingAddress() {
		waitForPageLoad();
		waitForSpinnerToBeDisappeared();
		String address = text.getElementText(wait.waitForElementPresent(shippingAddress));
		VertexLogger.log(address);
		return address;
	}

	/**
	 * Select or De-select the checkbox for Same billing & shipping address
	 *
	 * @param isSame true to set Same bill & ship And false to set different bill & ship addresses
	 */
	public void setSameBillingShippingAddress(boolean isSame) {
		waitForPageLoad();
		waitForSpinnerToBeDisappeared();
		wait.waitForElementNotDisplayed(maskClass);
		wait.waitForElementPresent(sameBillingShippingCheckMoneyCheckbox);
		checkbox.setCheckbox(sameBillingShippingCheckMoneyCheckbox, isSame);
	}

	/**
	 * selects Check/ Money Order payment method
	 */
	public void selectCheckMoneyOrderPaymentMethod() {
		waitForPageLoad();
		waitForSpinnerToBeDisappeared();
		wait.waitForElementNotDisplayed(maskClass);
		WebElement checkField = wait.waitForElementDisplayed(checkID);
		click.clickElementCarefully(checkField);
		waitForPageLoad();
		waitForSpinnerToBeDisappeared();
		if (element.isElementDisplayed(sameBillingShippingCheckMoneyCheckbox)) {
			if (!checkbox.isCheckboxChecked(wait.waitForElementPresent(sameBillingShippingCheckMoneyCheckbox))) {
				click.moveToElementAndClick(sameBillingShippingCheckMoneyCheckbox);
			}
		}
		waitForPageLoad();
		waitForSpinnerToBeDisappeared();
	}

	/**
	 * Get subtotal amount from the UI
	 *
	 * @return subtotal amount
	 */
	public double getSubTotalFromUI() {
		waitForPageLoad();
		waitForSpinnerToBeDisappeared();
		wait.waitForElementPresent(orderSummaryLabel);
		wait.waitForElementPresent(cartSubtotalLabel);
		wait.waitForElementPresent(subtotalAmount);
		String subTotal = text.getElementText(subtotalAmount).replace("$", "").replace(",", "");
		return Double.parseDouble(subTotal);
	}

	/**
	 * Get shipping & handling amount from the UI
	 *
	 * @return subtotal amount
	 */
	public double getShippingFromUI() {
		waitForPageLoad();
		waitForSpinnerToBeDisappeared();
		wait.waitForElementPresent(orderSummaryLabel);
		wait.waitForElementPresent(cartSubtotalLabel);
		wait.waitForElementPresent(subtotalAmount);
		String subTotal = text.getElementText(shippingAmount).replace("$", "").replace(",", "");
		return Double.parseDouble(subTotal);
	}

	/**
	 * Gets tax amount from the UI
	 *
	 * @return tax amount from UI.
	 */
	public double getTaxFromUI() {
		waitForPageLoad();
		waitForSpinnerToBeDisappeared();
		wait.waitForElementPresent(orderSummaryLabel);
		wait.waitForElementPresent(cartSubtotalLabel);
		wait.waitForElementPresent(shippingLabel);
		wait.waitForElementPresent(taxLabel);
		return Double.parseDouble(text.getElementText(wait.waitForElementPresent(taxAmount)).replace("$", "").replace(",", ""));
	}

	/**
	 * Gets order total (including tax, shipping and discount) amount from the UI
	 *
	 * @return order total amount from UI.
	 */
	public double getGrandTotalFromUI() {
		waitForPageLoad();
		waitForSpinnerToBeDisappeared();
		wait.waitForElementPresent(orderSummaryLabel);
		wait.waitForElementPresent(cartSubtotalLabel);
		return Double.parseDouble(text.getElementText(wait.waitForElementPresent(orderTotalAmount)).replace("$", "").replace(",", ""));
	}

	/**
	 * Calculates percentage based or expected tax
	 *
	 * @param taxAmount percent of tax
	 * @return calculated percent based tax
	 */
	public double calculatePercentBasedTax(double taxAmount) {
		double subtotal = 0;
		double discount = 0;
		double shipHandle = 0;
		double expectedTax = 0;
		waitForSpinnerToBeDisappeared();
		wait.waitForElementPresent(orderSummaryLabel);
		wait.waitForElementPresent(cartSubtotalLabel);
		wait.waitForElementPresent(shippingLabel);
		subtotal = Double.parseDouble(text.getElementText(wait.waitForElementPresent(subtotalAmount)).replace("$", "").replace(",", ""));
		shipHandle = Double.parseDouble(text.getElementText(wait.waitForElementPresent(shippingAmount)).replace("$", "").replace(",", ""));
		if (element.isElementDisplayed(discountAmount)) {
			discount = Double.parseDouble(text.getElementText(wait.waitForElementPresent(discountAmount)).replace("-$", "").replace(",", ""));
		}
		expectedTax = (subtotal + shipHandle - discount) * (taxAmount / 100);
		return Double.parseDouble(String.format("%.2f", expectedTax));
	}

	/**
	 * Calculates percentage based or expected tax
	 *
	 * @param isOnlyProduct  true to calculate tax only on product & false to ignore tax on product
	 * @param isOnlyShipping true to calculate tax only on shipping & false to ignore tax on shipping
	 * @param taxAmount      percent of tax
	 * @return calculated percent based tax
	 */
	public double calculateIndividualPercentBasedTax(boolean isOnlyProduct, boolean isOnlyShipping, double taxAmount) {
		double subtotal = 0;
		double shipHandle = 0;
		double discount = 0;
		double expectedTax = 0;
		waitForPageLoad();
		wait.waitForElementPresent(orderSummaryLabel);
		wait.waitForElementPresent(cartSubtotalLabel);
		wait.waitForElementPresent(shippingLabel);
		if (isOnlyProduct) {
			subtotal = Double.parseDouble(text.getElementText(wait.waitForElementPresent(subtotalAmount)).replace("$", "").replace(",", ""));
		}
		if (isOnlyShipping) {
			shipHandle = Double.parseDouble(text.getElementText(wait.waitForElementPresent(shippingAmount)).replace("$", "").replace(",", ""));
		}
		if (element.isElementPresent(discountAmount)) {
			discount = Double.parseDouble(text.getElementText(discountAmount).replace("-", "").replace("$", "").replace(",", ""));
		}
		expectedTax = (subtotal + shipHandle - discount) * (taxAmount / 100);
		return Double.parseDouble(String.format("%.2f", expectedTax));
	}

	/**
	 * Calculates percentage based or expected tax for Colorado addresses as it has additional retail delivery fee
	 * Separating this method tax calculation from regular tax calculation method to avoid confusion in future
	 * Retail Delivery Fee is only applicable to Colorado and not for any other addresses
	 *
	 * @param taxAmount percent of tax
	 * @return calculated percent based tax, Add fixed Delivery Fee & return combined amount
	 */
	public double calculatePercentBasedTaxForColorado(double taxAmount, double coAdditionalTax) {
		double coTax = Double.parseDouble(String.format("%.2f", MagentoData.COLORADO_RETAIL_DELIVERY_FEE.value * (coAdditionalTax / 100)));
		return Double.parseDouble(String.format("%.2f", calculatePercentBasedTax(taxAmount) + MagentoData.COLORADO_RETAIL_DELIVERY_FEE.value + coTax));
	}

	/**
	 * Validates fixed retail delivery fee for Colorado addresses
	 *
	 * @return true or false based on condition match
	 */
	public boolean verifyRetailDeliveryFeeForColorado() {
		waitForSpinnerToBeDisappeared();
		wait.waitForElementPresent(orderSummaryLabel);
		wait.waitForElementPresent(cartSubtotalLabel);
		wait.waitForElementPresent(shippingLabel);
		String valueFromUI = text.getElementText(retailDeliveryFee).replace("$", "");
		return Double.parseDouble(valueFromUI) == MagentoData.COLORADO_RETAIL_DELIVERY_FEE.value;
	}

	/**
	 * Verify whether tax label & tax is present or not?
	 * Used in Customer Code/ Class & Product Code/ Class scenarios
	 *
	 * @return true or false based on condition
	 */
	public boolean isTaxLabelPresent() {
		waitForPageLoad();
		waitForSpinnerToBeDisappeared();
		wait.waitForElementPresent(orderSummaryLabel);
		wait.waitForElementPresent(cartSubtotalLabel);
		wait.waitForElementPresent(shippingLabel);
		return element.isElementPresent(taxAmount);
	}
}