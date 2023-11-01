package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.RulesMapping;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * this class represents all the locators and methods for Default Mapping Tab contained
 * in Rules Mapping module in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkDefaultMappingPage extends TaxLinkBasePage
{
	public TaxLinkDefaultMappingPage( final WebDriver driver ) throws IOException
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "(//div[@class='rs-dropdown-menu-toggle'])[last()-3]")
	private WebElement rulesMappingTab;

	@FindBy(xpath = "//div[@col-id='defaultMapping']")
	private List<WebElement> defaultMappingPresentation;

	@FindBy(xpath = "//button[contains(text(), ' Download')]")
	private WebElement downloadButtonDefaultMappingPage;

	private By downloadButtonDefaultMapping = By.xpath(
		".//following-sibling::div/div/button[contains(text(), ' Download')]");

	/**
	 * Method to verify Title of Default Mapping Page
	 *
	 * @return boolean
	 */
	public boolean verifyTitleDefaultMappingPage( )
	{
		boolean flagTitleDefaultMappingPage = false;
		String viewDefaultMappingTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		if ( viewDefaultMappingTitle.equalsIgnoreCase(RulesMapping.RuleMappingDetails.headerDefaultMappingPage) )
		{
			flagTitleDefaultMappingPage = true;
		}
		return flagTitleDefaultMappingPage;
	}

	/**
	 * Method to verify View Default Mapping Page
	 *
	 * @return boolean
	 */
	public boolean viewDefaultMapping( )
	{
		boolean flagViewDefaultMapping = false;
		navigateToDefaultMapping();
		if ( verifyTitleDefaultMappingPage() )
		{
			int count = defaultMappingPresentation.size();
			for ( int i = 1 ; i < count ; i++ )
			{
				if ( defaultMappingPresentation
						 .get(i)
						 .getText() != "Default Mapping Reports" )
				{
					VertexLogger.log("Default Mapping Report : " + defaultMappingPresentation
						.get(i)
						.getText());
					jsWaiter.sleep(1500);
					defaultMappingPresentation
						.get(i)
						.findElement(downloadButtonDefaultMapping)
						.click();
					ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
					jsWaiter.sleep(100);
					driver
						.switchTo()
						.window(tabs.get(0)); // switch back to main screen
					flagViewDefaultMapping = true;
				}
			}
		}
		return flagViewDefaultMapping;
	}
}
