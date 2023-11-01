package com.vertex.quality.connectors.netsuite.common.interfaces;

/**
 * Represents the basic order
 *
 * @author hho
 */
public interface NetsuiteBasicOrder
{
	/**
	 * Gets the order subtotal
	 *
	 * @return the order subtotal
	 */
	String getOrderSubtotal( );

	/**
	 * Gets the order total
	 *
	 * @return the order total
	 */
	String getOrderTotal( );
}
