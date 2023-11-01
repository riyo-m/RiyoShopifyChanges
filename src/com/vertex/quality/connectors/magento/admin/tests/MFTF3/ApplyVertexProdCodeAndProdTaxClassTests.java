package com.vertex.quality.connectors.magento.admin.tests.MFTF3;

import com.vertex.quality.connectors.magento.admin.pages.*;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.storefront.pages.M2StorefrontBagsPage;
import com.vertex.quality.connectors.magento.storefront.pages.M2StorefrontHomePage;
import com.vertex.quality.connectors.magento.storefront.pages.M2StorefrontProductDetailsPage;
import com.vertex.quality.connectors.magento.storefront.pages.M2StorefrontShoppingCartPage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * BUNDLE-1291
 *
 * @author alewis
 */
public class ApplyVertexProdCodeAndProdTaxClassTests extends MagentoAdminBaseTest
{
	final String taxClassName = "Clothing Test";

	/**
	 *
	 */
	@Test()
	public void applyVertexCustomerCodeTest( )
	{
		verifyDeleteCreatedProduct();

		M2StorefrontShoppingCartPage shoppingCartPage = navigateToCatalogChangeProduct();

		String exclTax = shoppingCartPage.getSubtotalExcludingTaxInSummary();
		shoppingCartPage.refreshPage();
		String inclTax = shoppingCartPage.getSubtotalIncludingTaxInSummary();

		navigateToProductsPage();

		String exclTaxTwo = shoppingCartPage.getSubtotalExcludingTaxInSummary();
		shoppingCartPage.refreshPage();
		String inclTaxTwo = shoppingCartPage.getSubtotalIncludingTaxInSummary();

		verifyDeleteCreatedProduct();

		assertTrue(inclTax.equals(exclTax));

		assertTrue(inclTaxTwo.equals(exclTaxTwo));
	}

	/**
	 * navigates to the tax rules page
	 *
	 * @return a representation of the tax rules page
	 */
	protected M2AdminTaxRulesPage navigateToTaxRules( )
	{
		M2AdminHomepage homepage = signInToAdminHomepage();
		homepage.navPanel.clickStoresButton();

		M2AdminTaxRulesPage taxRulesPage = homepage.navPanel.clickTaxRulesButton();

		return taxRulesPage;
	}

	/**
	 * navigate to the add new tax rules page
	 * and add a new tax rule
	 *
	 * @return a representation of the new tax rules page
	 */
	protected M2AdminNewTaxRulePage addNewTaxRule( )
	{
		M2AdminTaxRulesPage taxRulesPage = navigateToTaxRules();
		M2AdminNewTaxRulePage newTaxRulePage = taxRulesPage.clickAddNewTaxRule();

		//newTaxRulePage.clickAdditionalSettings();

		//newTaxRulePage.clickAddNewCustomerTaxClass();

		//newTaxRulePage.clickAddNewTaxClass(taxClassName);

		return newTaxRulePage;
	}

	protected M2StorefrontShoppingCartPage navigateToCatalogChangeProduct( )
	{
		M2AdminNewTaxRulePage newTaxRulePage = addNewTaxRule();
		newTaxRulePage.navPanel.clickCatalogButton();
		M2AdminProductsPage productsPage = newTaxRulePage.navPanel.clickProductsButton();

		M2AdminNewProductPage newProductPage = productsPage.clickAddProductButton();

		newProductPage.fillOutRequiredField("ClothingTest3", "ct16", true, "Clothing");

		newProductPage.clickSaveButton();

		M2StorefrontHomePage homePage = openStorefrontHomepage();

		M2StorefrontBagsPage bagsPage = homePage.navPanel.searchStorefrontForProd("ct16");
		M2StorefrontProductDetailsPage productPage = bagsPage.clickProductButtonSelectItem("ClothingTest3");

		productPage.clickAddToCartButton();

		productPage.clickCartButton();

		M2StorefrontShoppingCartPage shoppingCartPage = productPage.clickViewAndEditButton();

		shoppingCartPage.clickEstimateShippingTax();
		shoppingCartPage.selectState("12");
		shoppingCartPage.enterZipCode("19382");
		shoppingCartPage.clickShippingRateSelector();

		return shoppingCartPage;
	}

	protected M2StorefrontShoppingCartPage navigateToProductsPage( )
	{
		driver.get(url);

		M2AdminHomepage homePage = new M2AdminHomepage(driver);

		homePage.navPanel.clickCatalogButton();

		M2AdminProductsPage productsPage = homePage.navPanel.clickProductsButton();
		M2AdminNewProductPage newProductPage = productsPage.editSelectProduct("ClothingTest3");
		newProductPage.editSKU("Wallets");
		newProductPage.clickSaveButton();
		homePage.clickSignOutAdminPage();

		M2StorefrontHomePage storefrontHomePage = openStorefrontHomepage();

		M2StorefrontBagsPage bagsPage = storefrontHomePage.navPanel.searchStorefrontForProd("Wallets");

		M2StorefrontProductDetailsPage productPage = bagsPage.clickProductButtonSelectItem("ClothingTest3");

		productPage.clickAddToCartButton();

		productPage.clickCartButton();

		M2StorefrontShoppingCartPage shoppingCartPage = productPage.clickViewAndEditButton();

		return shoppingCartPage;
	}

	/**
	 * delete the created product
	 *
	 */
	protected void verifyDeleteCreatedProduct( )
	{
		M2AdminCustomersPage customersPage = new M2AdminCustomersPage(driver);
		M2AdminHomepage homepage = signInToAdminHomepage();

		homepage.navPanel.clickCatalogButton();

		M2AdminProductsPage productsPage = homepage.navPanel.clickProductsButton();

		String message = "A total of 1 record(s) were deleted.";

		productsPage.searchProduct("ClothingTest3");
		if (productsPage.selectProductCheckboxByName("ClothingTest3")){
			productsPage.selectDeleteAction();
			customersPage.checkMessage(message);
		}
		productsPage.clickClearAllButton();
		homepage.clickSignOutAdminPage();
	}
}