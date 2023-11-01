package com.vertex.quality.connectors.commercetools.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * a generic representation of a CommerceTools Vertex Logo Page.
 *
 * @author vivek-kumar
 */
public class CommerceToolsVertexLogoPage extends CommerceToolsBasePage
{

	public CommerceToolsVertexLogoPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * normal xpath not working iin headless browser mode, so we are using relative xpath.
	 */
	protected final By vertexIconButton = By.xpath("//*[contains(text(),'Settings')]//following::div[5]");
	protected final By addressCleansing = By.xpath("//*[@name='addressCleansingEnabled']//parent::label");

	/**
	 * click on the vertex icon present in the commercetools.
	 */
	public void clickVertexIcon( )
	{
		WebElement vertexiconfield = wait.waitForElementEnabled(vertexIconButton);
		click.moveToElementAndClick(vertexiconfield);
	}

	/**
	 * switch on address cleansing on vertex config services.
	 */
	public void switchOnAddressCleansing( )
	{
		WebElement addressCleansingField = wait.waitForElementPresent(addressCleansing);
		click.clickElement(addressCleansingField);
		wait.waitForElementEnabled(addressCleansingField);
	}

	/**
	 * switch off address cleansing on vertex config services.
	 */
	public void switchOffAddressCleansing( )
	{

		WebElement addressCleansingField = wait.waitForElementPresent(addressCleansing);
		click.moveToElementAndClick(addressCleansingField);
		wait.waitForElementEnabled(addressCleansingField);
		click.moveToElementAndClick(addressCleansingField);
	}
}
