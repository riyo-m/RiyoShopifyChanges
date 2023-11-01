package com.vertex.quality.connectors.magento.admin.pages;

import com.vertex.quality.connectors.magento.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class M2AdminCartPriceRulesPage extends MagentoAdminPage
{

	By addID = By.id("add");
	By selectXpath = By.xpath("//*[@id='promo_quote_grid_table']/tbody/tr[4]/td[2]");

	public M2AdminCartPriceRulesPage( final WebDriver driver )
	{
		super(driver);
	}

	public M2AdminNewAddRulePage clickAddRuleButton( )
	{
		WebElement add = wait.waitForElementDisplayed(addID);
		click.clickElementCarefully(add);

		return initializePageObject(M2AdminNewAddRulePage.class);
	}

	public M2AdminNewAddRulePage clickRule( )
	{
		WebElement select = wait.waitForElementDisplayed(selectXpath);
		click.clickElementCarefully(select);

		return initializePageObject(M2AdminNewAddRulePage.class);
	}
}
