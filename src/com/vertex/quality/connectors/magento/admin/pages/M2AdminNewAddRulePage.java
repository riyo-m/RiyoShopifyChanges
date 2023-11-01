package com.vertex.quality.connectors.magento.admin.pages;

import com.vertex.quality.connectors.magento.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class M2AdminNewAddRulePage extends MagentoAdminPage
{

	By requiredClass = By.className("_required");
	By labelClass = By.className("admin__field-label");
	By adminFieldNoteClass = By.className("admin__field-note");
	By textFieldClass = By.className("admin__control-text");
	By selectClass = By.className("admin__control-select");
	By collapsibleClass = By.className("admin__collapsible-title");
	By saveId = By.id("save");
	By xpath = By.xpath("//*[@id=\"container\"]/div/div[2]/div[4]/div[2]/fieldset/div[5]/div[2]/div[1]/label");
	By websiteXpath = By.xpath("//*[@id='AIK3QL3']/option");
	By customerGroupXpath = By.xpath("//*[@id='FMWRO0G']/option[2]");

	public M2AdminNewAddRulePage( final WebDriver driver )
	{
		super(driver);
	}

	public void enterRequiredFields( )
	{
		waitForPageLoad();
		List<WebElement> collapsibles = wait.waitForAllElementsDisplayed(collapsibleClass);

		for ( WebElement collapsible : collapsibles )
		{
			String collapsibleText = collapsible.getText();

			if ( collapsibleText.equals("Actions") )
			{
				click.clickElementCarefully(collapsible);
				waitForPageLoad();
			}
		}

		List<WebElement> requireds = wait.waitForAllElementsDisplayed(requiredClass);

		for ( WebElement required : requireds )
		{
			try
			{
				WebElement label = wait.waitForElementDisplayed(labelClass, required);
				String requiredText = label.getText();

				if ( requiredText.equals("Rule Name") )
				{
					WebElement textField = wait.waitForElementDisplayed(textFieldClass, required);
					text.enterText(textField, "discount");
				}

				if ( requiredText.equals("Coupon") )
				{
					WebElement select = wait.waitForElementDisplayed(selectClass, required);
					dropdown.selectDropdownByIndex(select, 1);
				}

				if ( requiredText.equals("Coupon Code") )
				{
					WebElement textField = wait.waitForElementDisplayed(textFieldClass, required);
					text.enterText(textField, "discount");
				}

				if ( requiredText.equals("Discount Amount") )
				{
					WebElement textField = wait.waitForElementDisplayed(textFieldClass, required);
					text.enterText(textField, "100%");
				}
			}
			catch ( Exception e )
			{

			}
		}
		WebElement adminFieldNote = wait.waitForElementDisplayed(xpath);
		click.clickElementCarefully(adminFieldNote);

		WebElement website = wait.waitForElementDisplayed(websiteXpath);
		click.clickElementCarefully(website);

		WebElement customerGroup = wait.waitForElementDisplayed(customerGroupXpath);
		click.clickElementCarefully(customerGroup);
	}

	public void clickSaveButton( )
	{
		WebElement save = wait.waitForElementDisplayed(saveId);
		click.clickElementCarefully(save);
	}
}
