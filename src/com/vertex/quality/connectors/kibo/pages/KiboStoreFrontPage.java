package com.vertex.quality.connectors.kibo.pages;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.kibo.components.KiboStoreFrontProductsMenu;
import com.vertex.quality.connectors.kibo.enums.KiboProductCategory;
import com.vertex.quality.connectors.kibo.enums.KiboProductNames;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * this class represents the maxine front store in the live view
 * contains all the methods necessary to interact with this page
 *
 * @author osabha
 */
public class KiboStoreFrontPage extends VertexPage
{
	public KiboStoreFrontProductsMenu productsMenu;

	public KiboStoreFrontPage( WebDriver driver )
	{
		super(driver);

		this.productsMenu = new KiboStoreFrontProductsMenu(driver, this);
	}

	protected By addToCartButtonLoc = By.id("add-to-cart");
	protected String productsCategoriesTab = ".//ul[@class='mz-sitenav-list']/li/a[normalize-space(.)='<<text_replace>>']";
	protected String productNameLocator = ".//a[text()='<<text_replace>>']";
	protected By pageSizeDropdown = By.xpath("(.//span[text()='Show:'])[1]/following-sibling::select");
	protected By cartIcon = By.xpath(".//a[@href='/cart']");
	protected By cartForm = By.id("cartform");

	/**
	 * locates and clicks the add to cart button
	 *
	 * @return new instance of Maxine Live cart page class
	 */
	public KiboStoreFrontCartPage clickAddToCart( )
	{
		WebDriverWait shortWait = new WebDriverWait(driver, FIVE_SECOND_TIMEOUT);
		shortWait
			.ignoring(StaleElementReferenceException.class)
			.until(ExpectedConditions.elementToBeClickable(addToCartButtonLoc));
		WebElement addToCartButton = wait.waitForElementPresent(addToCartButtonLoc);
		click.clickElement(addToCartButton);
		shortWait
			.ignoring(StaleElementReferenceException.class)
			.until(ExpectedConditions.invisibilityOfAllElements(addToCartButton));
		waitForPageLoad();

		return new KiboStoreFrontCartPage(driver);
	}

	/**
	 * to select product and add it to the cart
	 *
	 * @param category product category tab to click on
	 * @param name     name of the product to select
	 *
	 * @return new instance of the store front cart page
	 */
	public KiboStoreFrontCartPage addProductToCart( KiboProductCategory category, KiboProductNames name )
	{
		productsMenu.selectProductTab(category);
		productsMenu.selectProduct(name);
		clickAddToCart();
		return new KiboStoreFrontCartPage(driver);
	}

	/**
	 * Go to cart from the header icon of Cart.
	 */
	public void goToCartFromHeader() {
		waitForPageLoad();
		click.moveToElementAndClick(wait.waitForElementPresent(cartIcon));
		waitForPageLoad();
		wait.waitForElementPresent(cartForm);
	}

	/**
	 * Selects product's category
	 *
	 * @param categoryName category name which is to be selected.
	 */
	public void selectProductCategory(String categoryName) {
		waitForPageLoad();
		click.moveToElementAndClick(wait.waitForElementEnabled(By.xpath(productsCategoriesTab.replace("<<text_replace>>", categoryName))));
		waitForPageLoad();
	}

	/**
	 * Loads maximum records on the page.
	 */
	public void loadMaximumRecords() {
		waitForPageLoad();
		wait.waitForElementPresent(pageSizeDropdown);
		dropdown.selectDropdownByDisplayName(pageSizeDropdown, "300");
		waitForPageLoad();
	}

	/**
	 * Selects the product based on Product's Name
	 *
	 * @param product name of product which is to be selected
	 */
	public void selectProduct(String product) {
		waitForPageLoad();
		click.moveToElementAndClick(wait.waitForElementEnabled(By.xpath(productNameLocator.replace("<<text_replace>>", product))));
		waitForPageLoad();
	}
}

