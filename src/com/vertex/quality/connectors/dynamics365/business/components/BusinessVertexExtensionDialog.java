package com.vertex.quality.connectors.dynamics365.business.components;

import com.vertex.quality.common.dialogs.VertexDialog;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents the vertex extension dialog
 *
 * @author osabha
 */
public class BusinessVertexExtensionDialog extends VertexDialog
{
	public BusinessVertexExtensionDialog( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	protected By versionFieldLoc = By.xpath("//td[@controlname='Version']/span");
	protected By layoutSelectionLoc = By.xpath("//div[@data-control-id='ListLayoutChooser']");
	protected By listLayoutLoc = By.xpath("//button[@title='Show as list']");
	/**
	 * checks if the version field is present
	 * (actually checks if field displayed; when using versionFieldLoc, it is present
	 * but does not return true as displayed)
	 *
	 * @return boolean indicating if the version field is present
	 */
	public boolean isVersionPresent(String versionName )
	{
		return text.getElementText(versionFieldLoc).equalsIgnoreCase(versionName);
	}

	public String getVersion() {
		WebElement version = wait.waitForElementPresent(versionFieldLoc);
		return text.getHiddenText(version);
	}

	public void selectListLayout() {
		wait.waitForElementDisplayed(layoutSelectionLoc);
		click.clickElementIgnoreExceptionAndRetry(layoutSelectionLoc);

		wait.waitForElementDisplayed(listLayoutLoc);
		click.clickElementIgnoreExceptionAndRetry(listLayoutLoc);
	}
}
