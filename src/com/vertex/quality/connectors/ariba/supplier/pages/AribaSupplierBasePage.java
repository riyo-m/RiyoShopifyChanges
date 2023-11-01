package com.vertex.quality.connectors.ariba.supplier.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.WebDriver;

/**
 * this class represents the base page for all the supplier site pages
 *
 * @author osabha
 */
public abstract class AribaSupplierBasePage extends VertexPage
{
	public AribaSupplierBasePage( WebDriver driver ) {super(driver);}
}
