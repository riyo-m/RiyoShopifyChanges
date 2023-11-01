package com.vertex.quality.connectors.magento.common.pages;

import com.vertex.quality.common.pages.VertexAutomationObject;
import org.openqa.selenium.WebDriver;

public class MagentoPageObject extends VertexAutomationObject
{
	protected final long attributeChangeTimeout = 5;

	public MagentoPageObject( WebDriver driver )
	{
		super(driver);
	}
}
