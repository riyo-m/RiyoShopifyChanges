package com.vertex.quality.connectors.kibo.pages;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.kibo.components.KiboCheckoutPageOrderSummary;
import com.vertex.quality.connectors.kibo.components.KiboCheckoutPagePayment;
import com.vertex.quality.connectors.kibo.components.KiboCheckoutPageShipping;
import com.vertex.quality.connectors.kibo.components.KiboCheckoutPageShippingMethod;
import org.openqa.selenium.WebDriver;

/**
 * This class represents the checkout page on the maxine live store.
 * contains all the methods necessary to interact with the page elements
 * the constructor within calls instances of different classes that represent different sections of
 * the page
 *
 * @author osabha
 */
public class KiboStoreFrontCheckoutPage extends VertexPage
{
	public KiboCheckoutPageShipping shipping;
	public KiboCheckoutPagePayment payment;
	public KiboCheckoutPageOrderSummary orderSummary;
	public KiboCheckoutPageShippingMethod shippingMethod;

	public KiboStoreFrontCheckoutPage( WebDriver driver )
	{
		super(driver);
		this.shippingMethod = new KiboCheckoutPageShippingMethod(driver, this);
		this.shipping = new KiboCheckoutPageShipping(driver, this);
		this.payment = new KiboCheckoutPagePayment(driver, this);
		this.orderSummary = new KiboCheckoutPageOrderSummary(driver, this);
	}
}


