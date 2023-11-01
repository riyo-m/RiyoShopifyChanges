package com.vertex.quality.connectors.magento.common.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Common Page template for Magento
 *
 * @author alewis
 */
public class MagentoPage extends VertexPage
{
	//TODO Aaron, should the waitForAttributeChange() calls in MagentoPage classes also timeout after
	//TODO only 5 seconds? I don't think they used to. Meanwhile, MagentoPageObject had defined
	//TODO waitForAttributeChange() so that it would timeout after 5 seconds, but most references
	//TODO to waitForAttributeChange() were in MagentoPage classes.

	public MagentoPage( WebDriver driver )
	{
		super(driver);
	}

	protected By spinner = By.xpath(".//*[@class='spinner']//span");
	protected By loader = By.className("loader");
	protected By maskedLoader = By.xpath(".//*[@class='loading-mask']");
	protected By pleaseWaitLoaderText = By.xpath(".//div[text()='Please wait...']");
	protected By loaderIMG = By.xpath(".//img[@alt='Loading...']");

	public void waitForMaskGone( By byLocator )
	{
		WebElement mask = wait.waitForElementPresent(byLocator);
		wait.waitForElementNotDisplayed(mask, 5);
	}

	/**
	 * Waits till spinner or loaders are exists
	 */
	public void waitForSpinnerToBeDisappeared() {
		waitForPageLoad();
		new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(spinner));
		new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(loader));
		new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(maskedLoader));
		new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(pleaseWaitLoaderText));
		new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(loaderIMG));
		waitForPageLoad();
	}
}
