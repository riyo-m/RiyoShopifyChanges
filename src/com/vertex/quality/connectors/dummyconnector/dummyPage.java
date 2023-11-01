package com.vertex.quality.connectors.dummyconnector;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class dummyPage extends VertexPage
{
	By nflIconClass = By.className("sports menu-nfl");

	public dummyPage( WebDriver driver )
	{
		super(driver);
	}

	public String checkIconText( )
	{
		waitForPageLoad();

		try
		{
			wait.waitForElementDisplayed(nflIconClass, 15);
		}
		catch ( Exception e )
		{
		}

		WebElement nflIcon = wait.waitForElementPresent(nflIconClass);

		String iconText = nflIcon.getText();

		return iconText;
	}
}
