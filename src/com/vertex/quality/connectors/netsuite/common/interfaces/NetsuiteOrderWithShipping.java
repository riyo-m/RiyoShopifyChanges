package com.vertex.quality.connectors.netsuite.common.interfaces;

import org.openqa.selenium.By;

/**
 * Represents an order that has the shipping and handling amount field
 *
 * @author hho
 */
public interface NetsuiteOrderWithShipping
{
	String summaryShippingCostHeader = "SHIPPING COST";
	String summaryHandlingCostHeader = "HANDLING COST";
	String toBeCalculated = "To Be Calculated";
	By shippingCostTextField = By.id("shippingcost_formattedValue");
	By calculateShippingCostButton = By.xpath("//a[@title='Calculate']");

	/**
	 * Gets the order shipping amount
	 *
	 * @return the order shipping amount
	 */
	String getOrderShippingAmount( );

	/**
	 * Gets the order tax amount
	 *
	 * @return the order tax amount
	 */
	String getOrderHandlingAmount( );

	/**
	 * Calculates the shipping cost
	 */
	void calculateShippingCost( );
}
