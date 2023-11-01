package com.vertex.quality.connectors.netsuite.scis.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NetsuiteSCISStorePage extends VertexPage
{
	public NetsuiteSCISStorePage( final WebDriver driver )
	{
		super(driver);
	}

	public void addItem( String itemName )
	{
		String addItemXpath = String.format("//li[a[@title='%s']]/div/button", itemName);
		click.clickElement(By.xpath(addItemXpath));
	}

	public String getSubtotal( )
	{
		By subtotalXpath = By.xpath("//button[@class='sidebar-list-values-button-subtotal']");
		wait.waitForElementDisplayed(subtotalXpath);
		return element
			.getWebElement(subtotalXpath)
			.getText();
	}

	public String getTax( )
	{
		By taxXpath = By.xpath("//li/span[@class='sidebar-list-values-number-tax']");
		wait.waitForElementDisplayed(taxXpath);
		return element
			.getWebElement(taxXpath)
			.getText();
	}

	public String getTotal( )
	{
		By totalXpath = By.xpath("//li/span[@class='sidebar-list-values-number-total']");
		wait.waitForElementDisplayed(totalXpath);
		return element
			.getWebElement(totalXpath)
			.getText();
	}

	public String getAmountDue( )
	{
		By amountDueXpath = By.xpath("//li/span[@class='sidebar-list-total-number-amountdue']");
		wait.waitForElementDisplayed(amountDueXpath);
		return element
			.getWebElement(amountDueXpath)
			.getText();
	}

	public void cash( )
	{
		By cashXpath = By.xpath("//button[@class='sidebar-action-button-cash']");
		wait.waitForElementDisplayed(cashXpath);
		click.clickElement(cashXpath);
	}

	public void tenderUp( )
	{
		By tenderUpXpath = By.xpath("//button[@class='payment-cash-button-tender-up']");

		WebDriverWait block = new WebDriverWait(driver, 10);
		WebElement modal = block.until(ExpectedConditions.visibilityOfElementLocated(By.id("modal-body")));
		WebElement form = modal.findElement(By.tagName("form"));
		WebElement button = form.findElement(By.tagName("button"));
		click.clickElement(button);
	}

	public void applyPayment( )
	{
		By applyPaymentXpath = By.xpath("//button[@class='payment-cash-actions-button-apply']");
		wait.waitForElementDisplayed(applyPaymentXpath);
		click.clickElement(applyPaymentXpath);
	}

	public void waitForReceipt( )
	{
		By confirmationMessageXpath = By.xpath("//div[@class='receipt-transaction-success-message']");
		wait.waitForElementDisplayed(confirmationMessageXpath);
	}
}
