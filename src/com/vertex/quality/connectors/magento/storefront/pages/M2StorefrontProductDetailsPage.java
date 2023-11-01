package com.vertex.quality.connectors.magento.storefront.pages;

import com.vertex.quality.connectors.magento.common.pages.MagentoStorefrontPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Representation of the Products Details Page
 *
 * @author alewis
 */
public class M2StorefrontProductDetailsPage extends MagentoStorefrontPage
{
	protected By addToCartButtonID = By.id("product-addtocart-button");
	protected By updateCartButtonId = By.id("product-updatecart-button");
	protected By cartButtonClass = By.className("showcart");

	protected By itemNameClass = By.className("base");

	protected By quantityId = By.id("qty");

	protected By subtotalClass = By.className("subtotal");

	protected By itemPricingClass = By.className("product-item-pricing");
	protected By priceContainerClass = By.className("price-container");
	protected By priceWrapperClass = By.className("price-wrapper");
	protected By includingTaxClass = By.className("price-including-tax");
	protected By excludingTaxClass = By.className("price-excluding-tax");
	protected By minicartPriceClass = By.className("minicart-price");
	protected By priceClass = By.className("price");
	protected By swatchClass = By.className("swatch-option");
	protected By summaryDetails = By.xpath("//div[@class='table-wrapper']");

	protected By viewCartClass = By.className("viewcart");

	protected By bundleSlideID = By.id("bundle-slide");
	protected By toCartClass = By.className("tocart");
	protected By firstElementName = By.name("super_group[33]");
	protected By secondElementName = By.name("super_group[34]");
	protected By thirdElementName = By.name("super_group[35]");
	protected String one = "1";

	protected String innerHTML = "innerHTML";
	protected String xSmall = "XS";
	protected String medium = "M";

	protected By messageSuccessClass = By.className("message-success");
	By maskClass = By.className("loading-mask");

	public M2StorefrontProductDetailsPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Locates the Add to Cart Button
	 *
	 * @return the Add to Cart Button
	 */
	private WebElement findAddToCartButton( )
	{
		waitForPageLoad();
		WebElement addToCartButton = wait.waitForElementPresent(addToCartButtonID);

		return addToCartButton;
	}

	private WebElement findUpdateCartButton( )
	{
		waitForPageLoad();
		WebElement updateCartButton = wait.waitForElementPresent(updateCartButtonId);

		return updateCartButton;
	}

	/**
	 * gets the name of the product
	 *
	 * @return item name
	 */
	public String getItemName( )
	{
		WebElement itemTitle = wait.waitForElementDisplayed(itemNameClass);

		String name = itemTitle.getText();

		return name;
	}

	/**
	 * input the desired quantity for the product
	 *
	 * @param inputString
	 */
	public void inputQuantity( String inputString )
	{
		WebElement field = wait.waitForElementEnabled(quantityId);

		field.clear();

		field.sendKeys(inputString);
	}

	/**
	 * Selects a size and color for the product
	 *
	 * @return the Add to Cart Button
	 */
	public void selectSizeAndColor( )
	{
		List<WebElement> swatchs = wait.waitForAllElementsPresent(swatchClass);

		WebElement swatch = element.selectElementByText(swatchs, medium);
		if ( swatch != null )
		{
			click.clickElement(swatch);
		}

		WebElement firstColor = swatchs.get(5);
		firstColor.click();
	}

	/**
	 * Selects a size and color for the product
	 *
	 * @return the Add to Cart Button
	 */
	public void selectSizeAndColorSmall( )
	{
		List<WebElement> swatchs = wait.waitForAllElementsPresent(swatchClass);

		WebElement swatch = element.selectElementByText(swatchs, xSmall);
		if ( swatch != null )
		{
			click.clickElement(swatch);
		}

		WebElement firstColor = swatchs.get(5);
		firstColor.click();
	}

	/**
	 * Add the 3 items in bundle to cart and clicks "Add to Cart" button
	 */
	public void addStrapsToCart( )
	{
		WebElement firstField = wait.waitForElementEnabled(firstElementName);
		text.enterText(firstField, one);

		WebElement secondField = wait.waitForElementEnabled(secondElementName);
		text.enterText(secondField, one);

		WebElement thirdField = wait.waitForElementEnabled(thirdElementName);
		text.enterText(thirdField, one);

		WebElement toCart = wait.waitForElementEnabled(toCartClass);
		toCart.click();
	}

	/**
	 * clicks the the customize button and then the 'Add to Cart' button
	 */
	public void clickCustomizeAddToCartButton( )
	{
		waitForPageLoad();
		WebElement customizeButton = wait.waitForElementEnabled(bundleSlideID);
		customizeButton.click();

		WebElement toCart = wait.waitForElementEnabled(toCartClass);
		toCart.click();
	}

	/**
	 * clicks the Add to Cart Button
	 *
	 * @return the Storefront Orders Page
	 */
	public M2StorefrontProductDetailsPage clickAddToCartButton( )
	{
		waitForPageLoad();
		wait.waitForElementEnabled(findAddToCartButton());
		WebElement addToCartButton = findAddToCartButton();
		wait.waitForElementEnabled(findCartButton());
		WebElement cartButton = findCartButton();
		wait.waitForElementDisplayed(addToCartButton);
		if ( addToCartButton != null )
		{
			waitForPageLoad();
			wait.waitForElementDisplayed(cartButton);
			addToCartButton.click();
		}
		else
		{
			String errorMsg = "Add to Order button not found";
			throw new RuntimeException(errorMsg);
		}

		return initializePageObject(M2StorefrontProductDetailsPage.class);
	}

	/**
	 * click Update Cart button
	 *
	 * @return shopping cart page
	 */
	public M2StorefrontShoppingCartPage clickUpdateCartButton( )
	{
		WebElement updateCartButton = findUpdateCartButton();
		WebElement cartButton = findCartButton();

		if ( updateCartButton != null )
		{
			wait.waitForElementEnabled(cartButton);
			updateCartButton.click();
		}
		else
		{
			String errorMsg = "Update Cart button not found";
			throw new RuntimeException(errorMsg);
		}

		M2StorefrontShoppingCartPage shoppingCartPage = initializePageObject(M2StorefrontShoppingCartPage.class);

		return shoppingCartPage;
	}

	/**
	 * Locates the Cart Button
	 *
	 * @return the Cart Button
	 */
	private WebElement findCartButton( )
	{
		waitForPageLoad();
		WebElement cartButton = wait.waitForElementPresent(cartButtonClass);

		return cartButton;
	}

	/**
	 * clicks the Cart Button
	 *
	 * @return the Storefront Product Page
	 */
	public M2StorefrontProductDetailsPage clickCartButton( )
	{
		waitForPageLoad();
		WebElement vertexOrdersButton = findCartButton();

		if ( vertexOrdersButton != null )
		{
			waitForPageLoad();
			wait.waitForElementEnabled(vertexOrdersButton);
			scroll.scrollElementIntoView(vertexOrdersButton);
			vertexOrdersButton.click();
		}
		else
		{
			String errorMsg = "Cart button not found";
			throw new RuntimeException(errorMsg);
		}

		return initializePageObject(M2StorefrontProductDetailsPage.class);
	}

	/**
	 * checks to see if the including and excluding tax amounts on the minicart are
	 * the same
	 *
	 * @return whether the excluding and including tax amounts are the same
	 */
	public boolean isCorrectTaxDisplayed( )
	{
		boolean areAmountsSame = false;

		WebElement itemPricing = driver.findElement(itemPricingClass);
		WebElement priceContainer = itemPricing.findElement(priceContainerClass);
		WebElement priceWrapper = priceContainer.findElement(priceWrapperClass);

		WebElement includingTax = priceWrapper.findElement(includingTaxClass);
		WebElement excludingTax = priceWrapper.findElement(excludingTaxClass);

		WebElement includingTaxPrice = includingTax.findElement(minicartPriceClass);
		WebElement excludingTaxPrice = excludingTax.findElement(minicartPriceClass);

		WebElement includingPrice = includingTaxPrice.findElement(priceClass);
		WebElement excludingPrice = excludingTaxPrice.findElement(priceClass);

		String totalPrice = includingPrice.getText();
		String noTaxPrice = excludingPrice.getText();

		if ( totalPrice != null && noTaxPrice != null && totalPrice.equals(noTaxPrice) )
		{
			areAmountsSame = true;
			return areAmountsSame;
		}
		else
		{
			areAmountsSame = false;
			return areAmountsSame;
		}
	}

	/**
	 * clicks the 'View and Edit' Button
	 *
	 * @return the Shopping Cart Page in Magento Storefront
	 */
	public M2StorefrontShoppingCartPage clickViewAndEditButton( )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		WebElement viewCart = wait.waitForElementDisplayed(viewCartClass);
		scroll.scrollElementIntoView(viewCart);
		click.clickElement(viewCart);
		wait.waitForElementNotDisplayed(maskClass);
		wait.waitForElementDisplayed(summaryDetails);
		return initializePageObject(M2StorefrontShoppingCartPage.class);
	}

	/**
	 * Gets the subtotal price of the products displayed in the Minicart including tax
	 *
	 * @return a string of the price including tax in the Cart Subtotal in the top right
	 */
	public String getIncludeTaxCartSubtotal( )
	{
		WebElement subtotal = wait.waitForElementPresent(subtotalClass);
		WebElement inclPrice = wait.waitForElementPresent(includingTaxClass, subtotal);
		String inclPriceString = inclPrice.getText();

		return inclPriceString;
	}

	/**
	 * Gets the subtotal price of the products displayed in the Minicart excluding tax
	 *
	 * @return a string of the price excluding tax in the Cart Subtotal in the top right
	 */
	public String getExcludeTaxCartSubtotal( )
	{
		WebElement subtotal = wait.waitForElementPresent(subtotalClass);
		WebElement exclPrice = wait.waitForElementPresent(excludingTaxClass, subtotal);
		String exclPriceString = exclPrice.getText();

		return exclPriceString;
	}

	/**
	 * Gets the price of the product displayed in the Minicart excluding tax
	 *
	 * @return a string of the price excluding tax
	 */
	public String getExcludeTaxInMinicart( )
	{
		WebElement itemPricing = driver.findElement(itemPricingClass);
		WebElement priceContainer = itemPricing.findElement(priceContainerClass);
		WebElement priceWrapper = priceContainer.findElement(priceWrapperClass);

		WebElement excludingTax = priceWrapper.findElement(excludingTaxClass);
		WebElement excludingTaxPrice = excludingTax.findElement(minicartPriceClass);
		WebElement excludingPrice = excludingTaxPrice.findElement(priceClass);

		String price = excludingPrice.getText();

		return price;
	}

	/**
	 * Gets the price of the product displayed in the Minicart including tax
	 *
	 * @return a string of the price including tax
	 */
	public String getIncludeTaxInMinicart( )
	{
		WebElement itemPricing = driver.findElement(itemPricingClass);
		WebElement priceContainer = itemPricing.findElement(priceContainerClass);
		WebElement priceWrapper = priceContainer.findElement(priceWrapperClass);

		WebElement includingTax = priceWrapper.findElement(includingTaxClass);
		WebElement includingTaxPrice = includingTax.findElement(minicartPriceClass);
		WebElement includingPrice = includingTaxPrice.findElement(priceClass);

		String price = includingPrice.getText();

		return price;
	}

	/**
	 * Gets the price of the first product displayed in the Minicart excluding tax
	 *
	 * @return a string of the price excluding tax
	 */
	public String getExcludeTaxInMinicartFirstItem( )
	{
		List<WebElement> itemsPricing = wait.waitForAllElementsPresent(itemPricingClass);

		WebElement itemPricing = itemsPricing.get(0);
		WebElement priceContainer = itemPricing.findElement(priceContainerClass);
		WebElement priceWrapper = priceContainer.findElement(priceWrapperClass);

		WebElement excludingTax = priceWrapper.findElement(excludingTaxClass);
		WebElement excludingTaxPrice = excludingTax.findElement(minicartPriceClass);
		WebElement excludingPrice = excludingTaxPrice.findElement(priceClass);

		String price = excludingPrice.getText();

		return price;
	}

	/**
	 * Gets the price of the first product displayed in the Minicart including tax
	 *
	 * @return a string of the price including tax
	 */
	public String getIncludeTaxInMinicartFirstItem( )
	{
		List<WebElement> itemsPricing = wait.waitForAllElementsPresent(itemPricingClass);

		WebElement itemPricing = itemsPricing.get(0);
		WebElement priceContainer = itemPricing.findElement(priceContainerClass);
		WebElement priceWrapper = priceContainer.findElement(priceWrapperClass);

		WebElement includingTax = priceWrapper.findElement(includingTaxClass);
		WebElement includingTaxPrice = includingTax.findElement(minicartPriceClass);
		WebElement includingPrice = includingTaxPrice.findElement(priceClass);

		String price = includingPrice.getText();

		return price;
	}

	/**
	 * Gets the price of the second product displayed in the Minicart excluding tax
	 *
	 * @return a string of the price excluding tax
	 */
	public String getExcludeTaxInMinicartSecondItem( )
	{
		List<WebElement> itemsPricing = wait.waitForAllElementsPresent(itemPricingClass);

		WebElement itemPricing = itemsPricing.get(1);
		WebElement priceContainer = itemPricing.findElement(priceContainerClass);
		WebElement priceWrapper = priceContainer.findElement(priceWrapperClass);

		WebElement excludingTax = priceWrapper.findElement(excludingTaxClass);
		WebElement excludingTaxPrice = excludingTax.findElement(minicartPriceClass);
		WebElement excludingPrice = excludingTaxPrice.findElement(priceClass);

		String price = excludingPrice.getText();

		return price;
	}

	/**
	 * Gets the price of the second product displayed in the Minicart including tax
	 *
	 * @return a string of the price including tax
	 */
	public String getIncludeTaxInMinicartSecondItem( )
	{
		List<WebElement> itemsPricing = wait.waitForAllElementsPresent(itemPricingClass);

		WebElement itemPricing = itemsPricing.get(1);
		WebElement priceContainer = itemPricing.findElement(priceContainerClass);
		WebElement priceWrapper = priceContainer.findElement(priceWrapperClass);

		WebElement includingTax = priceWrapper.findElement(includingTaxClass);
		WebElement includingTaxPrice = includingTax.findElement(minicartPriceClass);
		WebElement includingPrice = includingTaxPrice.findElement(priceClass);

		String price = includingPrice.getText();

		return price;
	}

	/**
	 * Gets the price of the third product displayed in the Minicart not including tax
	 *
	 * @return a string of the price not including tax
	 */
	public String getExcludeTaxInMinicartThirdItem( )
	{
		List<WebElement> itemsPricing = wait.waitForAllElementsPresent(itemPricingClass);

		WebElement itemPricing = itemsPricing.get(2);
		WebElement priceContainer = itemPricing.findElement(priceContainerClass);
		WebElement priceWrapper = priceContainer.findElement(priceWrapperClass);

		WebElement excludingTax = priceWrapper.findElement(excludingTaxClass);
		WebElement excludingTaxPrice = excludingTax.findElement(minicartPriceClass);
		WebElement excludingPrice = excludingTaxPrice.findElement(priceClass);

		String price = excludingPrice.getText();

		return price;
	}

	/**
	 * Gets the price of the third product displayed in the Minicart including tax
	 *
	 * @return a string of the price including tax
	 */
	public String getIncludeTaxInMinicartThirdItem( )
	{
		List<WebElement> itemsPricing = wait.waitForAllElementsPresent(itemPricingClass);

		WebElement itemPricing = itemsPricing.get(2);
		WebElement priceContainer = itemPricing.findElement(priceContainerClass);
		WebElement priceWrapper = priceContainer.findElement(priceWrapperClass);

		WebElement includingTax = priceWrapper.findElement(includingTaxClass);
		WebElement includingTaxPrice = includingTax.findElement(minicartPriceClass);
		WebElement includingPrice = includingTaxPrice.findElement(priceClass);

		String price = includingPrice.getText();

		return price;
	}

	/**
	 * checks to see if the messages on top of page match the correct string
	 *
	 * @param correctMessage
	 *
	 * @return a boolean if the string matches
	 */
	public boolean checkMessage( String correctMessage )
	{
		boolean match = false;
		WebElement messageSuccess = element.selectElementByText(messageSuccessClass, correctMessage);
		if ( messageSuccess != null )
		{
			match = true;
		}
		return match;
	}
}