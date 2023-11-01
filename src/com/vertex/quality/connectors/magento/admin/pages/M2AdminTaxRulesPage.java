package com.vertex.quality.connectors.magento.admin.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Representation of the Tax Rules page
 *
 * @author alewis
 */
public class M2AdminTaxRulesPage extends VertexPage
{
	protected By addId = By.id("add");

	public M2AdminTaxRulesPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Clicks the Add New Tax Rule Button
	 *
	 * @return the Add New Tax Rule Page
	 */
	public M2AdminNewTaxRulePage clickAddNewTaxRule( )
	{
		WebElement addButton = wait.waitForElementDisplayed(addId);
		click.clickElementCarefully(addButton);

		return initializePageObject(M2AdminNewTaxRulePage.class);
	}
}
