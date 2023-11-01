package com.vertex.quality.connectors.ariba.supplier.pages;

import com.vertex.quality.connectors.ariba.supplier.components.AribaSupplierHeaderPane;
import org.openqa.selenium.WebDriver;

/**
 * Homepage for the supplier network site
 */
public class AribaSupplierHomePage extends AribaSupplierBasePage
{
	public AribaSupplierHeaderPane headerPane;

	public AribaSupplierHomePage( WebDriver driver )
	{
		super(driver);

		headerPane = new AribaSupplierHeaderPane(driver, this);
	}
}
