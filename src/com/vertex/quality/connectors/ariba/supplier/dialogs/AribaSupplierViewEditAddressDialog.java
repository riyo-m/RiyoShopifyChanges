package com.vertex.quality.connectors.ariba.supplier.dialogs;

import com.vertex.quality.common.dialogs.VertexDialog;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.WebDriver;

/**
 * represents the view edit address dialog for a given item, where you can change the ship from address
 * this dialog is possessed by the invoice creation page class
 *
 * @author osabha
 */
public class AribaSupplierViewEditAddressDialog extends VertexDialog
{
	public AribaSupplierViewEditAddressDialog( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}
}
