package com.vertex.quality.connectors.accumatica.interfaces;

import org.openqa.selenium.By;

/**
 * this describes an enum of the columns in a table in some Acumatica page
 */
public interface AcumaticaTableColumn
{
	/**
	 * gets the label text in this column's header
	 *
	 * @return the label text in this column's header
	 */
	String getLabel( );

	/**
	 * when a text field is being edited, this overlay hovers over the field & is the only way to get to the table cell
	 * itself; this gets the locator for finding the overlay based on the identifying string at the end of the
	 * element's id
	 *
	 * @return a locator for the overlay which is displayed above a cell in this column if that cell is currently being
	 * edited
	 */
	By getEditingOverlay( );
}
