package com.vertex.quality.connectors.ariba.supplier.components;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.ariba.supplier.components.base.AribaSupplierBaseComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AribaSupplierCreateInvoiceShippingDetails extends AribaSupplierBaseComponent
{

	public AribaSupplierCreateInvoiceShippingDetails( final WebDriver driver, final VertexPage parent )
	{
		super(driver, parent);
	}

	protected final By shippingFieldContLoc = By.cssSelector(".base-ncd-label.ANXLabel");

	/**
	 * @param amount
	 */
	public void enterHeaderLevelShippingAmount( String amount )
	{
		WebElement parentCont = wait.waitForElementPresent(By.className("awtWrapperTable"));
		WebElement shippingAmountField = null;
		WebElement shippingAmountFieldCont = getFieldCont("Shipping Amount:", shippingFieldContLoc, parentCont);
		shippingAmountField = wait.waitForElementEnabled(By.tagName("input"), shippingAmountFieldCont);
		text.enterText(shippingAmountField, amount);
	}
}
