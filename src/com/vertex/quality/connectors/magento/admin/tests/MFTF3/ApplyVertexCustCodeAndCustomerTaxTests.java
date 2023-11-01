package com.vertex.quality.connectors.magento.admin.tests.MFTF3;

import com.vertex.quality.connectors.magento.admin.pages.*;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.storefront.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;


/**
 * BUNDLE-1286
 *
 * @author alewis
 */
public class ApplyVertexCustCodeAndCustomerTaxTests extends MagentoAdminBaseTest
{
	String taxClassName = "CoolCustomerCode";
	protected String productDetailsURL = MagentoData.STOREFRONT_PRODUCT_DETAILS.data;
	protected String adminURL = MagentoData.ADMIN_SIGN_ON_URL.data;

	@Test()
	public void checkTaxAmountDisplayedTest( )
	{
		addNewTaxRule();
		M2StorefrontShoppingCartPage shoppingCartPage = checkFirstZipCode();

		String paExclTax = shoppingCartPage.getTotalExcludingTaxInSummary();
		String paInclTax = shoppingCartPage.getTotalIncludingTaxInSummary();

		shoppingCartPage.selectState("12");  // California
		shoppingCartPage.enterZipCode("91608");
		shoppingCartPage.clickShippingRateSelector();

		String calExclTax = shoppingCartPage.getTotalExcludingTaxInSummary();
		String calInclTax = shoppingCartPage.getTotalIncludingTaxInSummary();

		shoppingCartPage.selectState("57"); //Texas
		shoppingCartPage.enterZipCode("78758-3483");

		String texExclTax = shoppingCartPage.getTotalExcludingTaxInSummary();
		String texInclTax = shoppingCartPage.getTotalIncludingTaxInSummary();

		M2StorefrontShoppingCartPage shoppingCartPage2 = checkSecondZipCode();
		shoppingCartPage2.clickEstimateShippingTax();
		shoppingCartPage2.selectState(PA_Number);  // California
		shoppingCartPage2.enterZipCode("91608");
		shoppingCartPage2.clickShippingRateSelector();

		String paExclTax2 = shoppingCartPage.getTotalExcludingTaxInSummary();
		String paInclTax2 = shoppingCartPage.getTotalIncludingTaxInSummary();

		shoppingCartPage.selectState("12"); //Texas
		shoppingCartPage.enterZipCode("91608");

		String calExclTax2 = shoppingCartPage.getTotalExcludingTaxInSummary();
		String calInclTax2 = shoppingCartPage.getTotalIncludingTaxInSummary();

		shoppingCartPage.selectState("57"); //Texas
		shoppingCartPage.enterZipCode("78758-3483");

		String texExclTax2 = shoppingCartPage.getTotalExcludingTaxInSummary();
		String texInclTax2 = shoppingCartPage.getTotalIncludingTaxInSummary();

		assertEquals(paExclTax, paInclTax);
		assertEquals(calExclTax, calInclTax);
		assertEquals(texExclTax, texInclTax);
		assertEquals(paExclTax2,paInclTax2);
		assertEquals(calExclTax2,calInclTax2);
		assertEquals(texExclTax2, texInclTax2);
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

	protected M2AdminCustomerInformationPage addNewTaxRule( )
	{
		M2AdminTaxRulesPage taxRulesPage = navigateToTaxRules();
		M2AdminNewTaxRulePage newTaxRulePage = taxRulesPage.clickAddNewTaxRule();
		newTaxRulePage.clickAdditionalSettings();
//		newTaxRulePage.clickAddNewCustomerTaxClass();
//		newTaxRulePage.inputTaxClassName(taxClassName);
//
//		newTaxRulePage.confirmNewTaxClass();

		newTaxRulePage.navPanel.clickCustomersButton();
		M2AdminCustomerGroupsPage customerGroupsPage = newTaxRulePage.navPanel.clickCustomerGroupsButton();

//		M2AdminAddNewCustomerGroupPage addNewCustomerGroupPage = customerGroupsPage.clickAddNewCustomerGroupButton();
//		addNewCustomerGroupPage.inputGroupName(taxClassName);
//		addNewCustomerGroupPage.selectTaxClass(taxClassName);
//		M2AdminCustomerGroupsPage groupsPage = addNewCustomerGroupPage.clickSaveCustomerGroupButton();

		customerGroupsPage.navPanel.clickCustomersButton();
		M2AdminCustomersPage customersPage = customerGroupsPage.navPanel.clickAllCustomersButton();

		M2AdminCustomerInformationPage customerInformationPage = customersPage.editCustomerByName("Gruffydd", "Llywelyn");

		customerInformationPage.clickAccountInformationTab();

		customerInformationPage.selectCustomerGroup("VLD GOVT Cust Group");
		customerInformationPage.clickSaveButton();

		return customerInformationPage;
	}

	/* Signs into Magento Storefront
	 *
	 * @return Magento Storefront Homepage
	 */
	protected M2StorefrontHomePage signInToStorefront( )
	{
		M2StorefrontHomePage homePage = openStorefrontHomepage();

		M2StorefrontLoginPage loginPage = homePage.clickSignInButton();

		loginPage.enterUsername("ILoveWales@gmail.com");
		loginPage.enterPassword("Vertex2018");
		M2StorefrontHomePage signedInHomepage = loginPage.clickSignInButton();

		return signedInHomepage;
	}

	/**
	 * Navigation on Magento Storefront to Product Details Page as existing customer
	 *
	 * @return Magento Storefront Product Details Page
	 */
	protected M2StorefrontShoppingCartPage checkFirstZipCode( )
	{
		M2StorefrontHomePage homePage = signInToStorefront();

		M2StorefrontProductDetailsPage detailsPage = new M2StorefrontProductDetailsPage(driver);

		driver.get(hoodieURL);

		detailsPage.selectSizeAndColorSmall();

		detailsPage.clickAddToCartButton();

		clickLogoButton();
		clearShoppingCart();

		M2StorefrontGearPage gearPage = homePage.navPanel.clickGearButton();

		M2StorefrontBagsPage bagsPage = gearPage.clickBagsButton();

		M2StorefrontProductDetailsPage productPage = bagsPage.clickProductButton();

		productPage.clickAddToCartButton();

		productPage.clickCartButton();

		M2StorefrontShoppingCartPage shoppingCartPage = productPage.clickViewAndEditButton();

		shoppingCartPage.clickEstimateShippingTax();

		shoppingCartPage.selectState(PA_Number);

		shoppingCartPage.enterZipCode("19382");

		shoppingCartPage.clickShippingRateSelector();

		return shoppingCartPage;
	}

	protected M2StorefrontShoppingCartPage checkSecondZipCode( )
	{
		driver.get(adminURL);
		M2AdminHomepage homepage = new M2AdminHomepage(driver);
		homepage.navPanel.clickCustomersButton();
		M2AdminCustomersPage customersPage = homepage.navPanel.clickAllCustomersButton();

		M2AdminCustomerInformationPage customerInformationPage = customersPage.editCustomerByName("Gruffydd", "Llywelyn");
		customerInformationPage.clickAccountInformationTab();

		customerInformationPage.inputVertexCustomerCode("something creative");
		customerInformationPage.clickSaveButton();

		driver.get(productDetailsURL);

		M2StorefrontProductDetailsPage detailsPage = new M2StorefrontProductDetailsPage(driver);

		detailsPage.clickCartButton();

		M2StorefrontShoppingCartPage shoppingCartPage = detailsPage.clickViewAndEditButton();

		shoppingCartPage.clickEstimateShippingTax();

		return shoppingCartPage;
	}
}