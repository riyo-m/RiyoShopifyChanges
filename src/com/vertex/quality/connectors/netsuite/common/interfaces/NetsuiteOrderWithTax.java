package com.vertex.quality.connectors.netsuite.common.interfaces;

import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;

/**
 * Represents an order that contains tax information
 *
 * @author hho
 */
public interface NetsuiteOrderWithTax
{
	/**
	 * Gets the item's tax code
	 *
	 * @param item the Netsuite item
	 *
	 * @return the item's tax code
	 */
	String getItemTaxCode( NetsuiteItem item );

	/**
	 * Gets the item's tax rate
	 *
	 * @param item the Netsuite item
	 *
	 * @return the item's tax rate
	 */
	String getItemTaxRate( NetsuiteItem item );

	/**
	 * Gets the order tax amount
	 *
	 * @return the order tax amount
	 */
	String getOrderTaxAmount( );
}
