package com.vertex.quality.connectors.shopify.ui.pages.ShopifyinvoicePage;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class VerifyTaxPage extends ShopifyPage
{
	//	static Map<String, String> beforePayValues = new HashMap<>();
	//	static Map<String, String> AfterPayValues = new HashMap<>();
	protected By invoiceTaxRates = By.xpath("(//span[text()='Show tax rates']//ancestor::div/child::span)[4]");
	protected By markFulfilledDialog = By.xpath(
		".//span[text()='Paid']/parent::span/following-sibling::span/descendant::span[text()='Fulfilled']");

	protected By closeTaxRate = By.xpath("//span[text()='Close']");
	protected By closeInvoiceTaxRate = By.xpath("//span[text()='Close']");

	protected By quotationTax = By.xpath("(//span[text()='Show tax rates']//ancestor::div/child::span)/parent::div/following-sibling::div");
	protected By customerQuotationExempt = By.xpath("(//span[text()='Customer exempt']//ancestor::div/child::span)/parent::div/following-sibling::div");

	protected By productTaxExempt = By.xpath("(//span[text()='Not calculated']//ancestor::div/child::span)/parent::div/following-sibling::div");
	protected By giftCardVerifyTax = By.xpath("(//span[text()='Not calculated']//ancestor::div/child::span)/parent::div/following-sibling::div");
	protected By taxNotCalculated = By.xpath("(//span[text()='Not calculated']//ancestor::div/child::span)/parent::div/following-sibling::div");

	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public VerifyTaxPage( final WebDriver driver )
	{
		super(driver);
	}

	//
	//	protected By verifySalesAndCompensatingUseTax = By.xpath(
	//		"(//span[text()='Sales and Compensating Use Tax (4%)']/parent::div/following-sibling::div//span)[2]");
	//
	//	protected By verifyLocalSales = By.xpath(
	//		"(//span[text()='Local Sales and Use Tax (4.5%)']//parent::div//following-sibling::div//span)[2]");
	//
	//	protected By verifyMetropolitanCommuterTransportationDistrict = By.xpath(
	//		"(//span[text()='Metropolitan Commuter Transportation District (0.375%)']//parent::div//following-sibling::div//span)[2]");
	//	protected By verifyTotalTax = By.xpath("//span[text()='Total tax']//parent::div//following-sibling::div//span");
	//
	//	protected By clickInvoiceTax = By.xpath("//span[text()='Show tax rates']");


	public double verifyInvoiceTaxRates(){
		waitForPageLoad();
		//		DecimalFormat format = new DecimalFormat("0.00");
		scroll.scrollElementIntoView(invoiceTaxRates);
		text.getElementText(invoiceTaxRates);

		return Double.parseDouble(text
			.getElementText(wait.waitForElementPresent(invoiceTaxRates))
			.replace("$", "")
			.replace(",", ""));
	}
	public double verifyQuotationTaxRates(){
		waitForPageLoad();
		//		DecimalFormat format = new DecimalFormat("0.00");
		scroll.scrollElementIntoView(quotationTax);
		text.getElementText(quotationTax);

		return Double.parseDouble(text
			.getElementText(wait.waitForElementPresent(quotationTax))
			.replace("$", "")
			.replace(",", ""));
	}
	public double verifyCustomerQuotationExempt(){
		waitForPageLoad();
		//		DecimalFormat format = new DecimalFormat("0.00");
		scroll.scrollElementIntoView(customerQuotationExempt);
		text.getElementText(customerQuotationExempt);

		return Double.parseDouble(text
			.getElementText(wait.waitForElementPresent(customerQuotationExempt))
			.replace("$", "")
			.replace(",", ""));
	}
	public double verifyProductTaxExempt(){
		waitForPageLoad();
		//		DecimalFormat format = new DecimalFormat("0.00");
		scroll.scrollElementIntoView(productTaxExempt);
		text.getElementText(productTaxExempt);

		return Double.parseDouble(text
			.getElementText(wait.waitForElementPresent(productTaxExempt))
			.replace("$", "")
			.replace(",", ""));
	}
	public double verifyGiftCardTax(){
		waitForPageLoad();
		//		DecimalFormat format = new DecimalFormat("0.00");
		scroll.scrollElementIntoView(giftCardVerifyTax);
		text.getElementText(giftCardVerifyTax);

		return Double.parseDouble(text
			.getElementText(wait.waitForElementPresent(giftCardVerifyTax))
			.replace("$", "")
			.replace(",", ""));
	}


	//	public void beforePayment( )
	//	{
	//		beforePayValues.put("Sales and Use Tax (4%)", text.getElementText(verifySalesAndCompensatingUseTax));
	//		beforePayValues.put("Local Sales and Use Tax (4.5%)", text.getElementText(verifyLocalSales));
	//		beforePayValues.put("Metropolitan Commuter Transportation District (0.375%)",
	//			text.getElementText(verifyMetropolitanCommuterTransportationDistrict));
	//		beforePayValues.put("Total tax", text.getElementText(verifyTotalTax));
	//		System.out.println(beforePayValues);
	//	}
	//
	//	public void afterPayment( )
	//	{
	//		AfterPayValues.put("Sales and Use Tax (4%)", text.getElementText(verifySalesAndCompensatingUseTax));
	//		AfterPayValues.put("Local Sales and Use Tax (4.5%)", text.getElementText(verifyLocalSales));
	//		AfterPayValues.put("Metropolitan Commuter Transportation District (0.375%)",
	//			text.getElementText(verifyMetropolitanCommuterTransportationDistrict));
	//		AfterPayValues.put("Total tax", text.getElementText(verifyTotalTax));
	//		System.out.println(AfterPayValues);
	//	}
	//
	//	public void verifyBothFields( )
	//	{
	//
	//		Assert.assertEquals(AfterPayValues.get("Sales and Use Tax (4%)"),
	//			beforePayValues.get("Sales and Use Tax (4%)"));
	//		Assert.assertEquals(AfterPayValues.get("Local Sales and Use Tax (4.5%)"),
	//			beforePayValues.get("Local Sales and Use Tax (4.5%)"));
	//		Assert.assertEquals(AfterPayValues.get("Metropolitan Commuter Transportation District (0.375%)"),
	//			beforePayValues.get("Metropolitan Commuter Transportation District (0.375%)"));
	//		Assert.assertEquals(AfterPayValues.get("Total tax"), beforePayValues.get("Total tax"));
	//	}
}
