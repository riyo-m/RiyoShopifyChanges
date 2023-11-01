package com.vertex.quality.connectors.shopify.ui.pages.ShopifyQuotationPage;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class QuotationTaxPage extends ShopifyPage
{
	static Map<String, String> beforePayValues = new HashMap<>();
	static Map<String, String> AfterPayValues = new HashMap<>();

	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public QuotationTaxPage( final WebDriver driver )
	{
		super(driver);
	}

	protected By showTaxRates = By.xpath("//span[text()='Show tax rates']");
	protected By closeTaxRate = By.xpath("//span[text()='Close']");

	protected By verifyTax = By.xpath(
		"(//span[text()='Sales and Use Tax (2.9%)']//parent::div//following-sibling::div//span)[2]");

	protected By verifyLocalSales = By.xpath(
		"(//span[text()='Local Sales and Use Tax (4.81%)']//parent::div//following-sibling::div//span)[2]");

	protected By verifyLocalSalesAndUseTax = By.xpath(
		"(//span[text()='Local Sales and Use Tax (1%)']//parent::div//following-sibling::div//span)[2]");

	protected By verifyLocalSalesAndUseTaxDec = By.xpath(
		"(//span[text()='Local Sales and Use Tax (0.1%)']//parent::div//following-sibling::div//span)[2]");

	protected By verifyTotalTax = By.xpath("//span[text()='Total tax']//parent::div//following-sibling::div//span");

	protected By clickInvoiceTax = By.xpath("//span[text()='Show tax rates']");

	public void clickShowTaxRates( ) throws InterruptedException
	{
		waitForPageLoad();
		sleep(2000);
		click.clickElement(showTaxRates);
	}

	public void beforePayment( )
	{
		beforePayValues.put("Sales and Use Tax (2.9%)", text.getElementText(verifyTax));
		beforePayValues.put("Local Sales and Use Tax (4.81%)", text.getElementText(verifyLocalSales));
		beforePayValues.put("Local Sales and Use Tax (1%)", text.getElementText(verifyLocalSalesAndUseTax));
		beforePayValues.put("Local Sales and Use Tax (0.1%)", text.getElementText(verifyLocalSalesAndUseTaxDec));
		beforePayValues.put("Total tax", text.getElementText(verifyTotalTax));
		System.out.println(beforePayValues);
	}

	public void afterPayment( )
	{
		AfterPayValues.put("Sales and Use Tax (2.9%)", text.getElementText(verifyTax));
		AfterPayValues.put("Local Sales and Use Tax (4.81%)", text.getElementText(verifyLocalSales));
		AfterPayValues.put("Local Sales and Use Tax (1%)", text.getElementText(verifyLocalSalesAndUseTax));
		AfterPayValues.put("Local Sales and Use Tax (0.1%)", text.getElementText(verifyLocalSalesAndUseTaxDec));
		AfterPayValues.put("Total tax", text.getElementText(verifyTotalTax));
		System.out.println(AfterPayValues);
	}

	public void verifyBothFields( )
	{

		Assert.assertEquals(AfterPayValues.get("Sales and Use Tax (2.9%)"),
			beforePayValues.get("Sales and Use Tax (2.9%)"));
		Assert.assertEquals(AfterPayValues.get("Local Sales and Use Tax (4.81%)"),
			beforePayValues.get("Local Sales and Use Tax (4.81%)"));
		Assert.assertEquals(AfterPayValues.get("Local Sales and Use Tax (1%)"),
			beforePayValues.get("Local Sales and Use Tax (1%)"));
		Assert.assertEquals(AfterPayValues.get("Local Sales and Use Tax (0.1%)"),
			beforePayValues.get("Local Sales and Use Tax (0.1%)"));
		Assert.assertEquals(AfterPayValues.get("Total tax"), beforePayValues.get("Total tax"));
	}

	public void clickClose( ) throws InterruptedException
	{
		waitForPageLoad();
		sleep(2000);
		click.clickElement(closeTaxRate);
	}

	public void quotationPage( ) throws InterruptedException
	{
		clickShowTaxRates();
		beforePayment();
		clickClose();
	}

	public void enterClickInvoiceTax( )
	{
		waitForPageLoad();
		click.clickElement(clickInvoiceTax);
	}

	public void invoiceTax( )
	{
		enterClickInvoiceTax();
		afterPayment();
		verifyBothFields();
	}
}
