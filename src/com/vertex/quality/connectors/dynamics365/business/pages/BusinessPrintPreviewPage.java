package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * representation of the print preview page
 *
 * @author cgajes
 */
public class BusinessPrintPreviewPage extends BusinessBasePage
{
	protected By textLayerCon = By.className("textLayer");
	protected By previewContainer = By.className("ms-nav-pdf-container");

	public BusinessPrintPreviewPage( WebDriver driver ) { super(driver); }

	/**
	 * Gets all the text on the print preview PDF and returns it
	 * (will look for better way to read from pdf, other methods not working)
	 *
	 * @return pdf text as string
	 */
	public String getPdfText( )
	{
		waitForPageLoad();
		wait.waitForElementPresent(textLayerCon);

		WebElement pdfTextLayer = wait.waitForElementEnabled(textLayerCon);

		String pdfText = pdfTextLayer.getText();

		return pdfText;
	}
}
