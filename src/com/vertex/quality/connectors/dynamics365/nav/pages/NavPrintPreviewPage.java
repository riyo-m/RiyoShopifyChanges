package com.vertex.quality.connectors.dynamics365.nav.pages;

import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavSalesBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
/**
 * representation of the print preview page
 *
 * @author bhikshapathi
 */
public class NavPrintPreviewPage extends NavSalesBasePage
{
    protected By textLayerCon = By.className("textLayer");
    protected By previewContainer = By.className("ms-nav-pdf-container");

    public NavPrintPreviewPage( WebDriver driver ) { super(driver); }

    /**
     * Gets all the text on the print preview PDF and returns it
     * (will look for better way to read from pdf, other methods not working)
     *
     * @return pdf text as string
     */
    public String getPdfText( )
    {
        wait.waitForElementPresent(textLayerCon);

        WebElement pdfTextLayer = wait.waitForElementEnabled(textLayerCon);

        String pdfText = pdfTextLayer.getText();

        return pdfText;
    }
}
