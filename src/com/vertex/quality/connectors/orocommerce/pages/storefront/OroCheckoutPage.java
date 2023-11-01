package com.vertex.quality.connectors.orocommerce.pages.storefront;

import com.vertex.quality.connectors.orocommerce.components.storefront.checkoutBillingInfo;
import com.vertex.quality.connectors.orocommerce.components.storefront.checkoutOrderSummary;
import com.vertex.quality.connectors.orocommerce.components.storefront.checkoutShippingInfo;
import com.vertex.quality.connectors.orocommerce.pages.base.OroStoreFrontBasePage;
import org.openqa.selenium.WebDriver;

/**
 * @author alewis
 */
public class OroCheckoutPage extends OroStoreFrontBasePage
{

	public checkoutBillingInfo billingInfo;
	public checkoutShippingInfo shippingInfo;
	public checkoutOrderSummary orderSummary;

	public OroCheckoutPage( WebDriver driver )
	{
		super(driver);
		this.billingInfo = new checkoutBillingInfo(driver, this);
		this.shippingInfo = new checkoutShippingInfo(driver, this);
		this.orderSummary = new checkoutOrderSummary(driver, this);
	}
}
