package com.vertex.quality.connectors.ariba.portal.pages.catalog;

import com.vertex.quality.connectors.ariba.portal.components.catalog.AribaPortalCatalogTopMenuBar;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import com.vertex.quality.connectors.ariba.portal.pages.invoice.AribaPortalInvoiceCreationPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * representation of the search results page in the catalog
 *
 * @author dgorecki ssalisbury osabha
 */
public class AribaPortalCatalogProductsListPage extends AribaPortalPostLoginBasePage
{

	protected final By searchResultsRowLoc = By.cssSelector("div[class='a-cat-srch-result']");
	protected final By supplierLabelLoc = By.tagName("label");
	protected final By searchResultsContLoc = By.cssSelector("a-cat-row-first");
	protected final By catalogSearchResultLabel = By.cssSelector("span[class='catlinks rowitemtitletext a-arv-cat-item-title']");
	protected final By quanFieldConLoc = By.className("a-cat-item-row-quantity");
	protected final By addProductToCartButtonLoc = By.className("w-btn-primary");
	public AribaPortalCatalogTopMenuBar topMenuBar;

	public AribaPortalCatalogProductsListPage( WebDriver driver )
	{
		super(driver);

		this.topMenuBar = initializePageObject(AribaPortalCatalogTopMenuBar.class, this);
	}

	/**
	 * @param supplierName
	 * @param quantity
	 */
	public void addProductToCartBySupplierName( final String supplierName, final String itemName,
		final String quantity )
	{
		WebElement row = getProductElementBySupplierName(supplierName, itemName);
		enterQuantity(quantity, row);
		WebElement addToCartButton = wait.waitForElementPresent(addProductToCartButtonLoc, row);
		click.clickElement(addToCartButton);
		waitForPageLoad();
	}

	/**
	 * filters the current search results list of items to another list of results with identical item name
	 *
	 * @param allSearchResults all the primitive search results
	 * @param itemName         name of the item we searched for
	 *
	 * @return list of search results matching searched item name
	 */
	public List<WebElement> filterSearchResultsByItemName( List<WebElement> allSearchResults, String itemName )
	{
		List<WebElement> resultsMatchingItemName = new ArrayList<>();
		for ( WebElement thisRow : allSearchResults )
		{
			WebElement itemNameElement = wait.waitForElementDisplayed(catalogSearchResultLabel, thisRow);
			String elementText = text.getElementText(itemNameElement);
			if ( itemName.equals(elementText) )
			{
				resultsMatchingItemName.add(thisRow);
			}
		}

		return resultsMatchingItemName;
	}

	/**
	 * go through the catalog search results  and select items that have specific supplier name
	 * @param supplierName name of the target supplier
	 *
	 * @return webElement of the target search results
	 */
	public WebElement getProductElementBySupplierName( final String supplierName, String itemName )
	{
		WebElement targetProduct = null;
		List<WebElement> resultsRows = wait.waitForAllElementsDisplayed(searchResultsRowLoc);
		List<WebElement> filteredResults = filterSearchResultsByItemName(resultsRows, itemName);

		for ( WebElement itemRow : filteredResults )
		{
			List<WebElement> trTags = wait.waitForAllElementsPresent(By.tagName("tr"), itemRow);
			for ( WebElement tag : trTags )
			{

				String labelText = tag.getText();
				if ( labelText != null )
				{
					if ( labelText.contains(supplierName) )
					{
						targetProduct = itemRow;
						break;
					}
				}
			}
		}
		return targetProduct;
	}

	/**
	 * locates the quantity field for a given search result product and enters the quantity for it
	 *
	 * @param quantity
	 * @param parentCont the row that contains the search product
	 */
	public void enterQuantity( String quantity, WebElement parentCont )
	{
		WebElement quanFieldCont = wait.waitForElementPresent(quanFieldConLoc, parentCont);
		WebElement quanField = wait.waitForElementPresent(By.tagName("input"), quanFieldCont);
		click.clickElement(quanField);
		wait.waitForElementEnabled(quanField);
		text.enterText(quanField, quantity);
	}

	/**
	 * clicks on the add item button after an item is searched
	 *
	 * @param supplierName name of the product to search and add to invoice
	 * @param quantity     quantity of the item selected
	 *
	 * @return new instance of the invoice creation page
	 */
	public AribaPortalInvoiceCreationPage addItemToInvoice( final String supplierName, final String itemName,
		final String quantity )
	{
		addProductToCartBySupplierName(supplierName, itemName, quantity);
		// when you have gotten to the catalog item page from the invoice creation page
		// this method causes  the invoice creation page to load when you click add item button.

		return initializePageObject(AribaPortalInvoiceCreationPage.class);
	}
}
