package com.vertex.quality.connectors.ariba.portal.tests.base;

import com.vertex.quality.common.enums.VertexHTTPMethod;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.misc.VertexCurrencyUtils;
import com.vertex.quality.connectors.ariba.api.enums.AribaAPIRequestType;
import com.vertex.quality.connectors.ariba.api.enums.AribaAPIType;
import com.vertex.quality.connectors.ariba.common.utils.AribaAPITestUtilities;
import com.vertex.quality.connectors.ariba.portal.dialogs.requisition.AribaPortalRequisitionLineItemTaxesDialog;
import com.vertex.quality.connectors.ariba.portal.enums.AribaCatalogItemDetailsTextFields;
import com.vertex.quality.connectors.ariba.portal.enums.AribaPlants;
import com.vertex.quality.connectors.ariba.portal.pages.catalog.AribaPortalCatalogHomePage;
import com.vertex.quality.connectors.ariba.portal.pages.catalog.AribaPortalCatalogProductsListPage;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalLoginPage;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import com.vertex.quality.connectors.ariba.portal.pages.requisition.AribaPortalRequisitionCheckoutPage;
import com.vertex.quality.connectors.ariba.portal.pages.requisition.AribaPortalRequisitionLineItemDetailsPage;
import com.vertex.quality.connectors.ariba.portal.pages.requisition.AribaPortalRequisitionSummaryPage;
import com.vertex.quality.connectors.ariba.portal.pojos.AribaLineItem;
import com.vertex.quality.connectors.ariba.portal.pojos.AribaTaxResult;
import io.restassured.response.Response;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.testng.Assert.assertTrue;

public abstract class AribaPortalBaseTest extends VertexUIBaseTest<AribaPortalLoginPage>
{
	protected final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	protected final String designatedConnectorTenant = "vrealm_2174";
	protected final String defaultCompanyCode = "3000 (IDES US INC)";
	protected final CurrencyUnit testCurrency = CurrencyUnit.USD;
	protected final String lancasterShipFromAddress = "TestAutomationS_USPA_Lancaster";
	protected final String californiaBillToAddress = "TestAutomationPL_USCA_UniversityCity";
	protected final String defaultTaxCode = "Tax Code: I1, A/P Sales Tax - Taxable";
	protected final String duck = "Duck-New";
	protected final String chicken = "Chicken-New";
	protected final String waterService = "Water Testing Service";
	protected final String elephant = "Elephant-New";
	protected final String ariba_oneX_Supplier = "Vertex test supplier";
	protected final String ariba_twoX_Supplier = "New Ariba Test Supplier";

	/**
	 * extracts the number of dollars expressed in a text string
	 *
	 * @param currencyVal text that contains a monetary quantity
	 *
	 * @return the number of dollars expressed in a text string
	 *
	 * @author ssalisbury
	 */
	protected double extractCurrencyNum( final String currencyVal )
	{
		double currencyQuantity = -1;
		if ( null != currencyVal )
		{
			// TODO trawl through string, find first char that is . or numeric, keep going
			// until find next char that isn't . or numeric, then parse the string of chars
			// that were . or numeric (warning, check for multiple periods)
			currencyQuantity = 0;

			if ( !"".equals(currencyVal) )
			{
				// TODO check if 1st character is numeric before cutting it
				String cleanedCurrencyVal = currencyVal.substring(1);// get rid of the currency
				// symbol at the beginning
				cleanedCurrencyVal = cleanedCurrencyVal.split(" ")[0];// get rid of the currency name at the end

				currencyQuantity = Double.parseDouble(cleanedCurrencyVal);
			}
		}

		return currencyQuantity;
	}

	/**
	 * checks that some line item in the current PR has the correct amount of tax
	 *
	 * @param checkoutPage        the checkout page where the current PR is being finalized
	 * @param lineItemIndex       which line item's taxes should be checked
	 * @param expectedLineItemTax the amount of tax that the chosen line item should have
	 *
	 * @return whether that line item in the current PR has the correct amount of tax
	 *
	 * @author ssalisbury
	 */
	protected boolean verifyRequisitionLineItemTaxes( final AribaPortalRequisitionCheckoutPage checkoutPage,
		final int lineItemIndex, final Money expectedLineItemTax )
	{
		boolean isItemTaxCorrect;

		String actualTaxText = checkoutPage.itemsTable.getRequisitionLineItemTaxes(lineItemIndex);
		Money actualMoney = VertexCurrencyUtils.parseMoney(actualTaxText);

		isItemTaxCorrect = expectedLineItemTax.equals(actualMoney);

		return isItemTaxCorrect;
	}

	/**
	 * checks that some component of the taxes on a line item in the current PR has the right name
	 *
	 * @param lineItemTaxes            a dialog containing the details of the taxes on a line item
	 *                                 in the
	 *                                 current PR
	 * @param taxComponentIndex        which tax component's name to check
	 * @param expectedTaxComponentName the name that that tax component should have
	 *
	 * @return whether that component of that line item's taxes has the correct name
	 *
	 * @author ssalisbury
	 */
	protected boolean verifyRequisitionLineItemTaxComponentName(
		final AribaPortalRequisitionLineItemTaxesDialog lineItemTaxes, final int taxComponentIndex,
		final String expectedTaxComponentName )
	{
		boolean isItemTaxComponentNameCorrect;

		String actualTaxComponentName = lineItemTaxes.getTaxComponentName(taxComponentIndex);
		isItemTaxComponentNameCorrect = expectedTaxComponentName.equals(actualTaxComponentName);

		if ( !isItemTaxComponentNameCorrect )
		{
			String wrongTaxComponentNameMessage = String.format(
				"The %dth tax component was " + "expected to have the name |[%s]| but instead has the name |[%s]|",
				taxComponentIndex, expectedTaxComponentName, actualTaxComponentName);
			VertexLogger.log(wrongTaxComponentNameMessage, VertexLogLevel.WARN, getClass());
		}

		return isItemTaxComponentNameCorrect;
	}

	/**
	 * checks that some component of the taxes on a line item in the current PR is properly
	 * deductible or not deductible
	 *
	 * @param lineItemTaxes                     a dialog containing the details of the taxes on a
	 *                                          line item in the
	 *                                          current PR
	 * @param taxComponentIndex                 which tax component's deductibility to check
	 * @param expectedTaxComponentDeductibility whether that tax component should be deductible or
	 *                                          not deductible
	 *
	 * @return whether that component of that line item's taxes has the correct deductibility
	 *
	 * @author ssalisbury
	 */
	protected boolean verifyRequisitionLineItemTaxComponentDeductibility(
		final AribaPortalRequisitionLineItemTaxesDialog lineItemTaxes, final int taxComponentIndex,
		final boolean expectedTaxComponentDeductibility )
	{
		boolean isItemTaxComponentDeductibilityCorrect;

		String actualTaxComponentDeductibilityString = lineItemTaxes.getTaxComponentIsDeductible(taxComponentIndex);
		boolean actualTaxComponentDeductibility;
		if ( "Yes".equals(actualTaxComponentDeductibilityString) )
		{
			actualTaxComponentDeductibility = true;
		}
		else if ( "No".equals(actualTaxComponentDeductibilityString) )
		{
			actualTaxComponentDeductibility = false;
		}
		else
		{
			String invalidTaxComponentDeductibilityMessage = String.format(
				"The %dth tax component " + "had an unparsable tax deductibility string |[%s]|\n" + "It was expected " +
				"to%s be tax deductible", taxComponentIndex, actualTaxComponentDeductibilityString,
				expectedTaxComponentDeductibility ? "" : " not");
			VertexLogger.log(invalidTaxComponentDeductibilityMessage, VertexLogLevel.ERROR, getClass());
			actualTaxComponentDeductibility = !expectedTaxComponentDeductibility;
		}

		isItemTaxComponentDeductibilityCorrect = expectedTaxComponentDeductibility == actualTaxComponentDeductibility;

		if ( !isItemTaxComponentDeductibilityCorrect )
		{
			String wrongTaxComponentDeductibilityMessage = String.format(
				"The %dth tax component " + "was expected to%s be deductible but instead is%s deductible",
				taxComponentIndex, expectedTaxComponentDeductibility ? "" : " not",
				actualTaxComponentDeductibility ? "" : " not");
			VertexLogger.log(wrongTaxComponentDeductibilityMessage, VertexLogLevel.WARN, getClass());
		}

		return isItemTaxComponentDeductibilityCorrect;
	}

	/**
	 * checks that some component of the taxes on a line item in the current PR has the right rate
	 *
	 * @param lineItemTaxes            a dialog containing the details of the taxes on a line item
	 *                                 in the
	 *                                 current PR
	 * @param taxComponentIndex        which tax component's rate to check
	 * @param expectedTaxComponentRate the rate which that tax component should have
	 *
	 * @return whether that component of that line item's taxes has the correct rate
	 *
	 * @author ssalisbury
	 */
	protected boolean verifyRequisitionLineItemTaxComponentRate(
		final AribaPortalRequisitionLineItemTaxesDialog lineItemTaxes, final int taxComponentIndex,
		final String expectedTaxComponentRate )
	{
		boolean isItemTaxComponentRateCorrect;

		String actualTaxComponentRate = lineItemTaxes.get_tax_component_rate(taxComponentIndex);
		//TODO just strip the '%' off the end then parse it into a double

		isItemTaxComponentRateCorrect = expectedTaxComponentRate.equals(actualTaxComponentRate);

		if ( !isItemTaxComponentRateCorrect )
		{
			String wrongTaxComponentRateMessage = String.format(
				"The %dth tax component was " + "expected to have the rate |[%s]| but instead has the rate |[%s]|",
				taxComponentIndex, expectedTaxComponentRate, actualTaxComponentRate);
			VertexLogger.log(wrongTaxComponentRateMessage, VertexLogLevel.WARN, getClass());
		}

		return isItemTaxComponentRateCorrect;
	}

	/**
	 * checks that some component of the taxes on a line item in the current PR has the right
	 * taxable price
	 *
	 * @param lineItemTaxes                    a dialog containing the details of the taxes on a
	 *                                         line item in the
	 *                                         current PR
	 * @param taxComponentIndex                which tax component's taxable price to check
	 * @param expectedTaxComponentTaxablePrice the taxable price which that tax component should
	 *                                         have
	 *
	 * @return whether that component of that line item's taxes has the correct taxable price
	 *
	 * @author ssalisbury
	 */
	protected boolean verifyRequisitionLineItemTaxComponentTaxablePrice(
		final AribaPortalRequisitionLineItemTaxesDialog lineItemTaxes, final int taxComponentIndex,
		final Money expectedTaxComponentTaxablePrice )
	{
		boolean isItemTaxComponentTaxablePriceCorrect;

		String actualTaxComponentTaxablePriceText = lineItemTaxes.getTaxComponentAppliedOn(taxComponentIndex);
		Money actualTaxComponentTaxablePrice = VertexCurrencyUtils.parseMoney(actualTaxComponentTaxablePriceText);

		isItemTaxComponentTaxablePriceCorrect = expectedTaxComponentTaxablePrice.equals(actualTaxComponentTaxablePrice);

		if ( !isItemTaxComponentTaxablePriceCorrect )
		{
			String wrongTaxComponentTaxablePriceMessage = String.format(
				"The %dth tax component was " + "expected to have the taxable price %f but instead has the " +
				"taxable price %f", taxComponentIndex, expectedTaxComponentTaxablePrice
					.getAmount()
					.doubleValue(), actualTaxComponentTaxablePrice
					.getAmount()
					.doubleValue());
			VertexLogger.log(wrongTaxComponentTaxablePriceMessage, VertexLogLevel.WARN, getClass());
		}

		return isItemTaxComponentTaxablePriceCorrect;
	}

	/**
	 * checks that the amount of some component of the taxes on a line item in the current PR is
	 * correct
	 *
	 * @param lineItemTaxes              a dialog containing the details of the taxes on a line item
	 *                                   in the
	 *                                   current PR
	 * @param taxComponentIndex          which tax component's amount to check
	 * @param expectedTaxComponentAmount the correct amount of that tax component
	 *
	 * @return whether the amount of that component of that line item's taxes is correct
	 *
	 * @author ssalisbury
	 */
	protected boolean verifyRequisitionLineItemTaxComponentAmount(
		final AribaPortalRequisitionLineItemTaxesDialog lineItemTaxes, final int taxComponentIndex,
		final Money expectedTaxComponentAmount )
	{
		boolean isItemTaxComponentAmountCorrect;

		String actualTaxComponentAmountText = lineItemTaxes.getTaxComponentAmount(taxComponentIndex);
		Money actualTaxComponentAmount = VertexCurrencyUtils.parseMoney(actualTaxComponentAmountText);

		isItemTaxComponentAmountCorrect = expectedTaxComponentAmount.equals(actualTaxComponentAmount);

		if ( !isItemTaxComponentAmountCorrect )
		{
			String wrongTaxComponentAmountMessage = String.format(
				"The %dth tax component was " + "expected to have the total tax amount %f but instead has the " +
				"total tax amount %f", taxComponentIndex, expectedTaxComponentAmount
					.getAmount()
					.doubleValue(), actualTaxComponentAmount
					.getAmount()
					.doubleValue());
			VertexLogger.log(wrongTaxComponentAmountMessage, VertexLogLevel.WARN, getClass());
		}

		return isItemTaxComponentAmountCorrect;
	}

	/**
	 * checks that the total amount of taxes on some line item in the current PR is correct
	 *
	 * @param lineItemTaxes              a dialog containing the details of the taxes on a line item
	 *                                   in the
	 *                                   current PR
	 * @param expectedTotalItemTaxAmount how large the total taxes on this line item should be
	 *
	 * @return whether the total amount of taxes on this line item is correct
	 *
	 * @author ssalisbury
	 */
	protected boolean verifyRequisitionLineItemTotalTax( final AribaPortalRequisitionLineItemTaxesDialog lineItemTaxes,
		final Money expectedTotalItemTaxAmount )
	{
		boolean isItemTotalTaxCorrect;

		String actualTaxText = lineItemTaxes.getItemTotalTax();
		Money actualTotalItemTaxAmount = VertexCurrencyUtils.parseMoney(actualTaxText);

		isItemTotalTaxCorrect = expectedTotalItemTaxAmount.equals(actualTotalItemTaxAmount);

		return isItemTotalTaxCorrect;
	}

	/**
	 * checks that one component of the taxes on some line item in the current PR has entirely
	 * correct values
	 *
	 * @param lineItemTaxes                     a dialog containing the details of the taxes on a
	 *                                          line item in the
	 *                                          current PR
	 * @param taxComponentIndex                 which tax component to check the values of
	 * @param expectedTaxComponentName          the name that that tax component should have
	 * @param expectedTaxComponentDeductibility whether that tax component should be deductible or
	 *                                          not deductible
	 * @param expectedTaxComponentRate          the rate which that tax component should have
	 * @param expectedTaxComponentTaxablePrice  the taxable price which that tax component should
	 *                                          have
	 * @param expectedTaxComponentAmount        the correct amount of that tax component
	 *
	 * @return whether all of the values of the given component of that line item's taxes are
	 * correct
	 *
	 * @author ssalisbury
	 */
	protected boolean verifyRequisitionLineItemTaxComponent(
		final AribaPortalRequisitionLineItemTaxesDialog lineItemTaxes, final int taxComponentIndex,
		final String expectedTaxComponentName, final boolean expectedTaxComponentDeductibility,
		final String expectedTaxComponentRate, final Money expectedTaxComponentTaxablePrice,
		final Money expectedTaxComponentAmount )
	{
		boolean isItemTaxComponentCorrect;

		boolean isItemTaxComponentNameCorrect = verifyRequisitionLineItemTaxComponentName(lineItemTaxes,
			taxComponentIndex, expectedTaxComponentName);
		boolean isItemTaxComponentDeductibilityCorrect = verifyRequisitionLineItemTaxComponentDeductibility(
			lineItemTaxes, taxComponentIndex, expectedTaxComponentDeductibility);
		boolean isItemTaxComponentRateCorrect = verifyRequisitionLineItemTaxComponentRate(lineItemTaxes,
			taxComponentIndex, expectedTaxComponentRate);
		boolean isItemTaxComponentTaxablePriceCorrect = verifyRequisitionLineItemTaxComponentTaxablePrice(lineItemTaxes,
			taxComponentIndex, expectedTaxComponentTaxablePrice);
		boolean isItemTaxComponentAmountCorrect = verifyRequisitionLineItemTaxComponentAmount(lineItemTaxes,
			taxComponentIndex, expectedTaxComponentAmount);

		isItemTaxComponentCorrect = isItemTaxComponentNameCorrect && isItemTaxComponentDeductibilityCorrect &&
									isItemTaxComponentRateCorrect && isItemTaxComponentTaxablePriceCorrect &&
									isItemTaxComponentAmountCorrect;

		return isItemTaxComponentCorrect;
	}

	/**
	 * checks that the given line item in the current PR has the correct tax code
	 *
	 * @param lineItemTaxes   a dialog containing the details of the taxes on a line item in the
	 *                        current PR
	 * @param expectedTaxCode the tax code that that line item should have
	 *
	 * @return whether that line item has the correct tax code
	 *
	 * @author ssalisbury
	 */
	protected boolean verifyRequisitionLineItemTaxCode( final AribaPortalRequisitionLineItemTaxesDialog lineItemTaxes,
		final String expectedTaxCode )
	{
		boolean isItemTaxCodeCorrect = false;

		String actualTaxCode = lineItemTaxes.getItemTaxCode();

		isItemTaxCodeCorrect = expectedTaxCode.equals(actualTaxCode);

		return isItemTaxCodeCorrect;
	}

	/**
	 * checks that the given line item in the current PR has the correct number of tax components
	 *
	 * @param lineItemTaxes                     a dialog containing the details of the taxes on a
	 *                                          line item in the
	 *                                          current PR
	 * @param expectedLineItemTaxComponentCount the number of components that this line item's
	 *                                          taxes should be composed of
	 *
	 * @return whether this line item's taxes are composed of the correct number of components
	 *
	 * @author ssalisbury
	 */
	protected boolean verifyRequisitionLineItemTaxCount( final AribaPortalRequisitionLineItemTaxesDialog lineItemTaxes,
		final int expectedLineItemTaxComponentCount )
	{
		boolean isItemTaxComponentCountCorrect;

		int actualTaxComponentCount = lineItemTaxes.getItemTaxComponentsCount();

		isItemTaxComponentCountCorrect = expectedLineItemTaxComponentCount == actualTaxComponentCount;

		if ( !isItemTaxComponentCountCorrect )
		{
			String wrongTaxComponentCountMessage = String.format(
				"This item was expected to have %d tax " + "components but instead it has %d tax components",
				expectedLineItemTaxComponentCount, actualTaxComponentCount);
			VertexLogger.log(wrongTaxComponentCountMessage, VertexLogLevel.WARN, getClass());
		}

		return isItemTaxComponentCountCorrect;
	}

	/**
	 * Helper method to search for the list of items and add them to cart
	 *
	 * @param items list of items
	 *
	 * @return new instance of the checkout page
	 */
	protected AribaPortalRequisitionCheckoutPage searchAllItemsAndAddToCart( AribaPortalCatalogHomePage catalogHomePage,
		String supplierName, AribaLineItem... items )
	{
		int numberOfItems = items.length;
		AribaPortalRequisitionCheckoutPage checkoutPage = null;
		AribaPortalCatalogProductsListPage searchResultsPage;
		for ( int i = 0 ; i < numberOfItems ; i++ )
		{
			AribaLineItem thisItem = items[i];
			if ( i == numberOfItems - 1 )
			{
				searchResultsPage = catalogHomePage.topMenuBar.searchCatalog(thisItem.productName);
				searchResultsPage.addProductToCartBySupplierName(supplierName, thisItem.productName, thisItem.quantity);
				searchResultsPage.topMenuBar.waitForCartSummaryPopup();
				checkoutPage = searchResultsPage.topMenuBar.proceedToCheckout();
			}
			else
			{
				searchResultsPage = catalogHomePage.topMenuBar.searchCatalog(thisItem.productName);
				searchResultsPage.addProductToCartBySupplierName(supplierName, thisItem.productName, thisItem.quantity);
				searchResultsPage.topMenuBar.waitForCartSummaryPopup();
				searchResultsPage.topMenuBar.closeCartSummaryPopup();
			}
		}
		return checkoutPage;
	}

	/**
	 * Edits all the line items details individually
	 *
	 * @param items list of items to edit
	 *
	 * @return new instance of the checkout page
	 */
	protected AribaPortalRequisitionCheckoutPage editLineItemsDetails( AribaPortalRequisitionCheckoutPage checkoutPage,
		AribaLineItem... items )
	{
		int numberOfItems = items.length;
		AribaPortalRequisitionLineItemDetailsPage itemDetailsPage;

		for ( int i = 0 ; i < numberOfItems ; i++ )
		{
			AribaLineItem thisItem = items[i];
//			if ( !thisItem.shippingCost.isEmpty() )
//			{
//				checkoutPage.chargesDialog.editShippingCharges(thisItem.shippingCost);
//			}
			itemDetailsPage = checkoutPage.itemsTable.editLineItemDetails(thisItem.itemIndex);
			itemDetailsPage.clickContactSelectButton();
			itemDetailsPage.contactDialog.selectContactValue(thisItem.shipFromAddress);
			itemDetailsPage.writeTextField(thisItem.billToAddress, AribaCatalogItemDetailsTextFields.BILL_TO);
			itemDetailsPage.writeTextField(thisItem.plant_shipToAddress.plantDisplayName,
				AribaCatalogItemDetailsTextFields.PLANT);
			checkoutPage = itemDetailsPage.clickOkButton();
		}

		return checkoutPage;
	}

	/**
	 * Edits all the line items details individually to verify Tax is getting updated as per updated items
	 *
	 * @param items list of items to edit
	 *
	 * @return new instance of the checkout page
	 */
	protected AribaPortalRequisitionCheckoutPage editLineItemsDetailsToVerifyUpdatedTax( AribaPortalRequisitionCheckoutPage checkoutPage,
																	   AribaLineItem... items )
	{
		int numberOfItems = items.length;
		AribaPortalRequisitionLineItemDetailsPage itemDetailsPage;

		for ( int i = 0 ; i < numberOfItems ; i++ )
		{
			AribaLineItem thisItem = items[i];

			itemDetailsPage = checkoutPage.itemsTable.editLineItemDetails(thisItem.itemIndex);
			itemDetailsPage.writeTextField(thisItem.updatedPrice, AribaCatalogItemDetailsTextFields.PRICE);
			itemDetailsPage.clickContactSelectButton();
			itemDetailsPage.contactDialog.selectContactValue(thisItem.shipFromAddress);
			itemDetailsPage.writeTextField(thisItem.billToAddress, AribaCatalogItemDetailsTextFields.BILL_TO);
			itemDetailsPage.writeTextField(thisItem.plant_shipToAddress.plantDisplayName,
					AribaCatalogItemDetailsTextFields.PLANT);
			checkoutPage = itemDetailsPage.clickOkButton();
		}
		return checkoutPage;
	}


	/**
	 * Helper method to perform the multiple line items test
	 *
	 * @param loggedInDashboardPage portal home page with the navigation dashboard
	 * @param requisitionTitle      requisition title
	 * @param items                 list of items to create requisition for
	 *
	 * @return check out page after the requisition is made
	 */
	protected AribaPortalRequisitionCheckoutPage createCatalogLineItemsRequisition(
		AribaPortalPostLoginBasePage loggedInDashboardPage, String supplierName, String requisitionTitle,
		AribaLineItem... items )
	{
		AribaPortalCatalogHomePage catalogHomePage = startNewRequisitionAndEmptyCart(loggedInDashboardPage);

		AribaPortalRequisitionCheckoutPage checkoutPage = searchAllItemsAndAddToCart(catalogHomePage, supplierName,
			items);
		checkoutPage.setRequisitionTitle(requisitionTitle);
		checkoutPage.setRequisitionCompanyCode(defaultCompanyCode);
		editLineItemsDetails(checkoutPage, items);
		checkoutPage.updateTaxes();
		return checkoutPage;
	}

	protected AribaPortalRequisitionCheckoutPage createCatalogLineItemsRequisition(
		AribaPortalPostLoginBasePage loggedInDashboardPage, String supplierName, String requisitionTitle,
		List<AribaLineItem> lineItems )
	{
		AribaLineItem[] lineItemsArray = lineItems.toArray(AribaLineItem[]::new);
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			supplierName, requisitionTitle, lineItemsArray);

		return checkoutPage;
	}

	/**
	 * Helper method to load the portal page,
	 * start new requisition and empty the cart
	 *
	 * @param loggedInDashboardPage portal home page with the main navigation tabs
	 *
	 * @return the home page with the catalog tab accessed
	 */
	protected AribaPortalCatalogHomePage startNewRequisitionAndEmptyCart(
		AribaPortalPostLoginBasePage loggedInDashboardPage )
	{
		AribaPortalCatalogHomePage catalogHomePage1 = loggedInDashboardPage.openCatalog();
		AribaPortalCatalogHomePage catalogHomePage2 = catalogHomePage1.startNewRequisition();
		return catalogHomePage2;
	}

	/**
	 * verifies all the calculated tax amounts against the passed expected values.
	 *
	 * @param checkoutPage    the page on which the tax results are shown
	 * @param expectedTaxCode returned tax code to assert for
	 * @param items           list of items to verify tax values for
	 */
	protected void validateAllRequisitionTaxes( AribaPortalRequisitionCheckoutPage checkoutPage,
		final String expectedTaxCode, AribaLineItem... items )
	{
		int numberOfItems = items.length;

		for ( int i = 0 ; i < numberOfItems ; i++ )
		{
			AribaLineItem thisItem = items[i];
			final Money expectedAmountWithCurrency = Money.of(testCurrency, thisItem.expectedTaxAmount);
			final Money expectedLineItemTax = expectedAmountWithCurrency;
			final Money expectedTotalItemTaxAmount = expectedAmountWithCurrency;
			boolean correctLineItemTaxes = verifyRequisitionLineItemTaxes(checkoutPage, thisItem.itemIndex,
				expectedLineItemTax);
			assertTrue(correctLineItemTaxes);

			AribaPortalRequisitionLineItemTaxesDialog itemTaxes = checkoutPage.itemsTable.openRequisitionLineItemTaxes(
				thisItem.itemIndex);
			boolean correctLineItemTaxCount = verifyRequisitionLineItemTaxCount(itemTaxes, thisItem.taxResults.size());
			assertTrue(correctLineItemTaxCount);
			boolean correctLineItemTaxCode = verifyRequisitionLineItemTaxCode(itemTaxes, expectedTaxCode);
			assertTrue(correctLineItemTaxCode);

			boolean correctLineItemTaxComponents = true;
			int componentIndex = 1;
			for ( AribaTaxResult expectedTaxComponent : thisItem.taxResults )
			{
				boolean correctLineItemTaxComponent = verifyRequisitionLineItemTaxComponent(itemTaxes, componentIndex,
					expectedTaxComponent.name, expectedTaxComponent.isDeductible, expectedTaxComponent.rate,
					expectedTaxComponent.taxablePrice, expectedTaxComponent.taxAmount);

				if ( !correctLineItemTaxComponent )
				{
					correctLineItemTaxComponents = false;
					break;
				}
				componentIndex++;
			}
			assertTrue(correctLineItemTaxComponents);

			boolean correctLineItemTotalTax = verifyRequisitionLineItemTotalTax(itemTaxes, expectedTotalItemTaxAmount);
			assertTrue(correctLineItemTotalTax);
			itemTaxes.closeRequisitionLineItemTaxes();
		}
	}

	protected void validateAllRequisitionTaxes( AribaPortalRequisitionCheckoutPage checkoutPage,
		final String expectedTaxCode, List<AribaLineItem> lineItems )
	{
		AribaLineItem[] lineItemsArray = lineItems.toArray(AribaLineItem[]::new);
		validateAllRequisitionTaxes(checkoutPage, expectedTaxCode, lineItemsArray);
	}

	/**
	 * runs the whole process of creating the requisition and validate all the tax calculations
	 * also saves the purchased order number in the data base with time stamp.
	 *
	 * @param requisitionTitle      title describing the requisition
	 * @param testCaseName          name of the test case
	 * @param testData              the line items and their expected tax components
	 * @param loggedInDashboardPage portal home page
	 * @param expectedTaxCode       tax code to assert for in the result tax components
	 */
	protected void executeTestCase( AribaPortalPostLoginBasePage loggedInDashboardPage, String supplierName,
		final String requisitionTitle, final String testCaseName, final String expectedTaxCode,
		final List<AribaLineItem> testData )
	{
		AribaPortalRequisitionCheckoutPage checkoutPage = createCatalogLineItemsRequisition(loggedInDashboardPage,
			supplierName, requisitionTitle, testData);
		validateAllRequisitionTaxes(checkoutPage, expectedTaxCode, testData);
		AribaPortalCatalogHomePage catalogPage = checkoutPage.toolbar.submitRequisition();

		AribaPortalRequisitionSummaryPage summaryPage = catalogPage
			.getSubmittedNotification()
			.openRequisition();

		String poNumber = summaryPage.getOrderNumber();
		String orderTimeStamp = getOrderTimeStamp();
		tryStoreOrderNumber(poNumber, orderTimeStamp, testCaseName);
	}

	/**
	 * gets the current time stamp
	 *
	 * @return time stamp in a string
	 */
	private String getOrderTimeStamp( )
	{
		LocalDateTime ldt = LocalDateTime.now();
		String orderTimeStamp = formatter.format(ldt);
		return orderTimeStamp;
	}

	/**
	 * tries to store the requisition order number in the database
	 *
	 * @param poNumber       purchase order number
	 * @param orderTimeStamp time stamp of the order
	 * @param testCaseName   name of the test case
	 */
	protected void tryStoreOrderNumber( final String poNumber, final String orderTimeStamp, final String testCaseName )
	{
		try
		{
			SQLConnection.storeOrderNumber(poNumber, orderTimeStamp, testCaseName);
		}
		catch ( SQLException e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * builds the test data, all the line items, and their tax components
	 * shipping from Lancaster, Pa to Delaware plant.
	 *
	 * @return list of the line items with all their components.
	 */
	protected AribaLineItem buildTestDataItem( String itemName, String shipFromAddress, AribaPlants shipToPlant,
		String billingAddress, String quantity, int itemIndex, double expectedAmount,
		AribaTaxResult... expectedTaxResults )
	{
		List<AribaTaxResult> itemTaxResults = List.of(expectedTaxResults);
		AribaLineItem item = AribaLineItem
			.builder()
			.productName(itemName)
			.quantity(quantity)
			.itemIndex(itemIndex)
			.expectedTaxAmount(expectedAmount)
			.shipFromAddress(shipFromAddress)
			.billToAddress(billingAddress)
			.taxResults(itemTaxResults)
			.plant_shipToAddress(shipToPlant)
			.build();
		return item;
	}

	//	@Override
	//	public void setupTestRun( )
	//	{
	//		super.setupTestRun();
	//
	//		try
	//		{
	//			createDriver();
	//		}
	//		catch ( Exception e )
	//		{
	//			e.printStackTrace();
	//		}
	//		AribaConnUiUtilities connUiUtilities = new AribaConnUiUtilities(driver);
	//		AribaConnSignOnPage signOnPage = connUiUtilities.loadConfig();
	//		AribaConnHomePage homePage = connUiUtilities.signInToConfig(signOnPage);
	//		connUiUtilities.wipeTenantDataRows(designatedConnectorTenant, homePage);
	//		quitDriver();
	//		setUpBasicConfigsThroughAPI();
	//	}

	protected void setUpBasicConfigsThroughAPI( )
	{
		AribaAPITestUtilities apiUtil = new AribaAPITestUtilities(AribaAPIType.NOT_APPLICABLE,
			AribaAPIRequestType.NOT_APPLICABLE);
		String configCustomFieldMappingRequestBody
			= "\"{\\\"tenantID\\\":2,\\\"mapID\\\":1,\\\"name\\\":\\\"CustomFieldMapping\\\",\\\"valueEntries\\\":[{\\\"tenantID\\\":\\\"vrealm_2174\\\",\\\"valueID\\\":0,\\\"mapKey\\\":\\\"TestBooleanVertex\\\",\\\"mapValue\\\":\\\"Boolean:TestBooleanVertex=1\\\",\\\"lastHash\\\":0},{\\\"tenantID\\\":\\\"vrealm_2174\\\",\\\"valueID\\\":0,\\\"mapKey\\\":\\\"TestStringvertex\\\",\\\"mapValue\\\":\\\"String:TestStringvertex=2\\\",\\\"lastHash\\\":0},{\\\"tenantID\\\":\\\"vrealm_2174\\\",\\\"valueID\\\":0,\\\"mapKey\\\":\\\"TestDateVertex\\\",\\\"mapValue\\\":\\\"Date:TestDateVertex=1\\\",\\\"lastHash\\\":0},{\\\"tenantID\\\":\\\"vrealm_2174\\\",\\\"valueID\\\":0,\\\"mapKey\\\":\\\"TestMoneyFieldVertex\\\",\\\"mapValue\\\":\\\"Money:TestMoneyFieldVertex=1\\\",\\\"lastHash\\\":0},{\\\"tenantID\\\":\\\"vrealm_2174\\\",\\\"valueID\\\":0,\\\"mapKey\\\":\\\"TestIntegerVertex\\\",\\\"mapValue\\\":\\\"Integer:TestIntegerVertex=2\\\",\\\"lastHash\\\":0}]}\\n\"";
		String configAccountFieldMappingRequestBody
			= "\"{\\\"tenantID\\\":2,\\\"mapID\\\":6,\\\"name\\\":\\\"AcctFieldMapping\\\",\\\"valueEntries\\\":[{\\\"valueID\\\":940,\\\"mapKey\\\":\\\"TaxCode\\\",\\\"mapValue\\\":\\\"taxCode\\\",\\\"externalID\\\":null,\\\"lastHash\\\":0}]}\"";
		String configAribaExternalTaxTypesRequestBody
			= "[{\"typeID\":0,\"taxType\":\"GSTTax\",\"isVAT\":\"true\",\"isSelfAssessed\":\"false\",\"accountInstruction\":\"VS1\",\"lastHash\":0,\"tenant\":0,\"rank\":0},{\"typeID\":0,\"taxType\":\"HSTTax\",\"isVAT\":\"true\",\"isSelfAssessed\":\"false\",\"accountInstruction\":\"VS1\",\"lastHash\":0,\"tenant\":0,\"rank\":0},{\"typeID\":0,\"taxType\":\"PSTTax\",\"isVAT\":\"false\",\"isSelfAssessed\":\"false\",\"accountInstruction\":\"NVV\",\"lastHash\":0,\"tenant\":0,\"rank\":0},{\"typeID\":0,\"taxType\":\"QSTTax\",\"isVAT\":\"true\",\"isSelfAssessed\":\"false\",\"accountInstruction\":\"VS3\",\"lastHash\":0,\"tenant\":0,\"rank\":0},{\"typeID\":0,\"taxType\":\"VATRCTax\",\"isVAT\":\"true\",\"isSelfAssessed\":\"true\",\"accountInstruction\":\"\",\"lastHash\":0,\"tenant\":0,\"rank\":0},{\"typeID\":0,\"taxType\":\"VATTax\",\"isVAT\":\"true\",\"isSelfAssessed\":\"false\",\"accountInstruction\":\"VST\",\"lastHash\":0,\"tenant\":0,\"rank\":0},{\"typeID\":0,\"taxType\":\"SalesTax\",\"isVAT\":\"false\",\"isSelfAssessed\":\"false\",\"accountInstruction\":\"NVV\",\"lastHash\":0,\"tenant\":0,\"rank\":0},{\"typeID\":0,\"taxType\":\"UseTax\",\"isVAT\":\"false\",\"isSelfAssessed\":\"true\",\"accountInstruction\":\"\",\"lastHash\":0,\"tenant\":0,\"rank\":0},{\"typeID\":0,\"taxType\":\"SalesTaxState\",\"isVAT\":\"false\",\"isSelfAssessed\":\"false\",\"accountInstruction\":\"NVV\",\"lastHash\":0,\"tenant\":0,\"rank\":0},{\"typeID\":0,\"taxType\":\"SalesTaxDistrict\",\"isVAT\":\"false\",\"isSelfAssessed\":\"false\",\"accountInstruction\":\"NVV\",\"lastHash\":0,\"tenant\":0,\"rank\":0},{\"typeID\":0,\"taxType\":\"IGST\",\"isVAT\":\"true\",\"isSelfAssessed\":\"false\",\"accountInstruction\":\"FIX\",\"lastHash\":0,\"tenant\":0,\"rank\":0},{\"typeID\":0,\"taxType\":\"SGST\",\"isVAT\":\"true\",\"isSelfAssessed\":\"false\",\"accountInstruction\":\"FIX\",\"lastHash\":0,\"tenant\":0,\"rank\":0},{\"typeID\":0,\"taxType\":\"SalesTaxCounty\",\"isVAT\":\"false\",\"isSelfAssessed\":\"false\",\"accountInstruction\":\"NVV\",\"lastHash\":0,\"tenant\":0,\"rank\":0},{\"typeID\":0,\"taxType\":\"SalesTaxCity\",\"isVAT\":\"false\",\"isSelfAssessed\":\"false\",\"accountInstruction\":\"NVV\",\"lastHash\":0,\"tenant\":0,\"rank\":0}]";
		String configAribaComponentTaxTypesRequestBody = "[\n" + "    {\n" + "        \"typeID\": null,\n" +
														 "        \"tenant\": 0,\n" +
														 "        \"taxType\": \"IGST\",\n" +
														 "        \"summaryTaxTypes\": [\n" + "            {\n" +
														 "                \"typeID\": 19193,\n" +
														 "                \"taxType\": \"IGST\",\n" +
														 "                \"isSelfAssessed\": 0\n" + "            }\n" +
														 "        ],\n" + "        \"accountInstruction\": \"\",\n" +
														 "        \"rank\": 0,\n" + "        \"lastHash\": 0\n" +
														 "    },\n" + "    {\n" + "        \"typeID\": null,\n" +
														 "        \"tenant\": 0,\n" +
														 "        \"taxType\": \"SGST\",\n" +
														 "        \"summaryTaxTypes\": [\n" + "            {\n" +
														 "                \"typeID\": 19194,\n" +
														 "                \"taxType\": \"SGST\",\n" +
														 "                \"isSelfAssessed\": 0\n" + "            }\n" +
														 "        ],\n" + "        \"accountInstruction\": \"\",\n" +
														 "        \"rank\": 1,\n" + "        \"lastHash\": 0\n" +
														 "    },\n" + "    {\n" + "        \"typeID\": null,\n" +
														 "        \"tenant\": 0,\n" +
														 "        \"taxType\": \"GSTHST\",\n" +
														 "        \"summaryTaxTypes\": [\n" + "            {\n" +
														 "                \"typeID\": 19183,\n" +
														 "                \"taxType\": \"GSTTax\",\n" +
														 "                \"isSelfAssessed\": 0\n" +
														 "            },\n" + "            {\n" +
														 "                \"typeID\": 19184,\n" +
														 "                \"taxType\": \"HSTTax\",\n" +
														 "                \"isSelfAssessed\": 0\n" + "            }\n" +
														 "        ],\n" + "        \"accountInstruction\": \"\",\n" +
														 "        \"rank\": 2,\n" + "        \"lastHash\": 0\n" +
														 "    },\n" + "    {\n" + "        \"typeID\": null,\n" +
														 "        \"tenant\": 0,\n" +
														 "        \"taxType\": \"PSTTax\",\n" +
														 "        \"summaryTaxTypes\": [\n" + "            {\n" +
														 "                \"typeID\": 19185,\n" +
														 "                \"taxType\": \"PSTTax\",\n" +
														 "                \"isSelfAssessed\": 0\n" + "            }\n" +
														 "        ],\n" + "        \"accountInstruction\": \"\",\n" +
														 "        \"rank\": 3,\n" + "        \"lastHash\": 0\n" +
														 "    },\n" + "    {\n" + "        \"typeID\": null,\n" +
														 "        \"tenant\": 0,\n" +
														 "        \"taxType\": \"PSTCUT\",\n" +
														 "        \"summaryTaxTypes\": [\n" + "            {\n" +
														 "                \"typeID\": 19185,\n" +
														 "                \"taxType\": \"PSTTax\",\n" +
														 "                \"isSelfAssessed\": 0\n" + "            }\n" +
														 "        ],\n" + "        \"accountInstruction\": \"MW2\",\n" +
														 "        \"rank\": 4,\n" + "        \"lastHash\": 0\n" +
														 "    },\n" + "    {\n" + "        \"typeID\": null,\n" +
														 "        \"tenant\": 0,\n" +
														 "        \"taxType\": \"QSTTax\",\n" +
														 "        \"summaryTaxTypes\": [\n" + "            {\n" +
														 "                \"typeID\": 19186,\n" +
														 "                \"taxType\": \"QSTTax\",\n" +
														 "                \"isSelfAssessed\": 0\n" + "            }\n" +
														 "        ],\n" + "        \"accountInstruction\": \"\",\n" +
														 "        \"rank\": 5,\n" + "        \"lastHash\": 0\n" +
														 "    },\n" + "    {\n" + "        \"typeID\": null,\n" +
														 "        \"tenant\": 0,\n" +
														 "        \"taxType\": \"VATRCTax\",\n" +
														 "        \"summaryTaxTypes\": [\n" + "            {\n" +
														 "                \"typeID\": 19188,\n" +
														 "                \"taxType\": \"VATTax\",\n" +
														 "                \"isSelfAssessed\": 0\n" + "            }\n" +
														 "        ],\n" + "        \"accountInstruction\": \"ESA\",\n" +
														 "        \"rank\": 6,\n" + "        \"lastHash\": 0\n" +
														 "    },\n" + "    {\n" + "        \"typeID\": null,\n" +
														 "        \"tenant\": 0,\n" +
														 "        \"taxType\": \"VATTax\",\n" +
														 "        \"summaryTaxTypes\": [\n" + "            {\n" +
														 "                \"typeID\": 19188,\n" +
														 "                \"taxType\": \"VATTax\",\n" +
														 "                \"isSelfAssessed\": 0\n" + "            }\n" +
														 "        ],\n" + "        \"accountInstruction\": \"\",\n" +
														 "        \"rank\": 7,\n" + "        \"lastHash\": 0\n" +
														 "    },\n" + "    {\n" + "        \"typeID\": null,\n" +
														 "        \"tenant\": 0,\n" +
														 "        \"taxType\": \"SalesTaxState\",\n" +
														 "        \"summaryTaxTypes\": [\n" + "            {\n" +
														 "                \"typeID\": 19189,\n" +
														 "                \"taxType\": \"SalesTax\",\n" +
														 "                \"isSelfAssessed\": 0\n" +
														 "            },\n" + "            {\n" +
														 "                \"typeID\": 19191,\n" +
														 "                \"taxType\": \"SalesTaxState\",\n" +
														 "                \"isSelfAssessed\": 0\n" + "            }\n" +
														 "        ],\n" + "        \"accountInstruction\": \"\",\n" +
														 "        \"rank\": 8,\n" + "        \"lastHash\": 0\n" +
														 "    },\n" + "    {\n" + "        \"typeID\": null,\n" +
														 "        \"tenant\": 0,\n" +
														 "        \"taxType\": \"SalesTaxCounty\",\n" +
														 "        \"summaryTaxTypes\": [\n" + "            {\n" +
														 "                \"typeID\": 19189,\n" +
														 "                \"taxType\": \"SalesTax\",\n" +
														 "                \"isSelfAssessed\": 0\n" +
														 "            },\n" + "            {\n" +
														 "                \"typeID\": 19195,\n" +
														 "                \"taxType\": \"SalesTaxCounty\",\n" +
														 "                \"isSelfAssessed\": 0\n" + "            }\n" +
														 "        ],\n" + "        \"accountInstruction\": \"\",\n" +
														 "        \"rank\": 9,\n" + "        \"lastHash\": 0\n" +
														 "    },\n" + "    {\n" + "        \"typeID\": null,\n" +
														 "        \"tenant\": 0,\n" +
														 "        \"taxType\": \"SalesTaxCity\",\n" +
														 "        \"summaryTaxTypes\": [\n" + "            {\n" +
														 "                \"typeID\": 19189,\n" +
														 "                \"taxType\": \"SalesTax\",\n" +
														 "                \"isSelfAssessed\": 0\n" +
														 "            },\n" + "            {\n" +
														 "                \"typeID\": 19196,\n" +
														 "                \"taxType\": \"SalesTaxCity\",\n" +
														 "                \"isSelfAssessed\": 0\n" + "            }\n" +
														 "        ],\n" + "        \"accountInstruction\": \"\",\n" +
														 "        \"rank\": 10,\n" + "        \"lastHash\": 0\n" +
														 "    },\n" + "    {\n" + "        \"typeID\": null,\n" +
														 "        \"tenant\": 0,\n" +
														 "        \"taxType\": \"SalesTaxDistrict\",\n" +
														 "        \"summaryTaxTypes\": [\n" + "            {\n" +
														 "                \"typeID\": 19189,\n" +
														 "                \"taxType\": \"SalesTax\",\n" +
														 "                \"isSelfAssessed\": 0\n" +
														 "            },\n" + "            {\n" +
														 "                \"typeID\": 19192,\n" +
														 "                \"taxType\": \"SalesTaxDistrict\",\n" +
														 "                \"isSelfAssessed\": 0\n" + "            }\n" +
														 "        ],\n" + "        \"accountInstruction\": \"\",\n" +
														 "        \"rank\": 11,\n" + "        \"lastHash\": 0\n" +
														 "    },\n" + "    {\n" + "        \"typeID\": null,\n" +
														 "        \"tenant\": 0,\n" +
														 "        \"taxType\": \"UseTaxState\",\n" +
														 "        \"summaryTaxTypes\": [\n" + "            {\n" +
														 "                \"typeID\": 19190,\n" +
														 "                \"taxType\": \"UseTax\",\n" +
														 "                \"isSelfAssessed\": 1\n" +
														 "            },\n" + "            {\n" +
														 "                \"typeID\": 19189,\n" +
														 "                \"taxType\": \"SalesTax\",\n" +
														 "                \"isSelfAssessed\": 0\n" +
														 "            },\n" + "            {\n" +
														 "                \"typeID\": 19191,\n" +
														 "                \"taxType\": \"SalesTaxState\",\n" +
														 "                \"isSelfAssessed\": 0\n" + "            }\n" +
														 "        ],\n" + "        \"accountInstruction\": \"MW1\",\n" +
														 "        \"rank\": 12,\n" + "        \"lastHash\": 0\n" +
														 "    },\n" + "    {\n" + "        \"typeID\": null,\n" +
														 "        \"tenant\": 0,\n" +
														 "        \"taxType\": \"UseTaxCounty\",\n" +
														 "        \"summaryTaxTypes\": [\n" + "            {\n" +
														 "                \"typeID\": 19190,\n" +
														 "                \"taxType\": \"UseTax\",\n" +
														 "                \"isSelfAssessed\": 1\n" +
														 "            },\n" + "            {\n" +
														 "                \"typeID\": 19189,\n" +
														 "                \"taxType\": \"SalesTax\",\n" +
														 "                \"isSelfAssessed\": 0\n" +
														 "            },\n" + "            {\n" +
														 "                \"typeID\": 19195,\n" +
														 "                \"taxType\": \"SalesTaxCounty\",\n" +
														 "                \"isSelfAssessed\": 0\n" + "            }\n" +
														 "        ],\n" + "        \"accountInstruction\": \"MW2\",\n" +
														 "        \"rank\": 13,\n" + "        \"lastHash\": 0\n" +
														 "    },\n" + "    {\n" + "        \"typeID\": null,\n" +
														 "        \"tenant\": 0,\n" +
														 "        \"taxType\": \"UseTaxCity\",\n" +
														 "        \"summaryTaxTypes\": [\n" + "            {\n" +
														 "                \"typeID\": 19190,\n" +
														 "                \"taxType\": \"UseTax\",\n" +
														 "                \"isSelfAssessed\": 1\n" +
														 "            },\n" + "            {\n" +
														 "                \"typeID\": 19189,\n" +
														 "                \"taxType\": \"SalesTax\",\n" +
														 "                \"isSelfAssessed\": 0\n" +
														 "            },\n" + "            {\n" +
														 "                \"typeID\": 19196,\n" +
														 "                \"taxType\": \"SalesTaxCity\",\n" +
														 "                \"isSelfAssessed\": 0\n" + "            }\n" +
														 "        ],\n" + "        \"accountInstruction\": \"MW3\",\n" +
														 "        \"rank\": 14,\n" + "        \"lastHash\": 0\n" +
														 "    },\n" + "    {\n" + "        \"typeID\": null,\n" +
														 "        \"tenant\": 0,\n" +
														 "        \"taxType\": \"UseTaxDistrict\",\n" +
														 "        \"summaryTaxTypes\": [\n" + "            {\n" +
														 "                \"typeID\": 19190,\n" +
														 "                \"taxType\": \"UseTax\",\n" +
														 "                \"isSelfAssessed\": 1\n" +
														 "            },\n" + "            {\n" +
														 "                \"typeID\": 19189,\n" +
														 "                \"taxType\": \"SalesTax\",\n" +
														 "                \"isSelfAssessed\": 0\n" +
														 "            },\n" + "            {\n" +
														 "                \"typeID\": 19192,\n" +
														 "                \"taxType\": \"SalesTaxDistrict\",\n" +
														 "                \"isSelfAssessed\": 0\n" + "            }\n" +
														 "        ],\n" + "        \"accountInstruction\": \"MW4\",\n" +
														 "        \"rank\": 15,\n" + "        \"lastHash\": 0\n" +
														 "    },\n" + "    {\n" + "        \"typeID\": null,\n" +
														 "        \"tenant\": 0,\n" +
														 "        \"taxType\": \"SalesTax\",\n" +
														 "        \"summaryTaxTypes\": [\n" + "            {\n" +
														 "                \"typeID\": 19189,\n" +
														 "                \"taxType\": \"SalesTax\",\n" +
														 "                \"isSelfAssessed\": 0\n" + "            }\n" +
														 "        ],\n" + "        \"accountInstruction\": \"\",\n" +
														 "        \"rank\": 16,\n" + "        \"lastHash\": 0\n" +
														 "    },\n" + "    {\n" + "        \"typeID\": null,\n" +
														 "        \"tenant\": 0,\n" +
														 "        \"taxType\": \"UseTax\",\n" +
														 "        \"summaryTaxTypes\": [\n" + "            {\n" +
														 "                \"typeID\": 19190,\n" +
														 "                \"taxType\": \"UseTax\",\n" +
														 "                \"isSelfAssessed\": 1\n" +
														 "            },\n" + "            {\n" +
														 "                \"typeID\": 19189,\n" +
														 "                \"taxType\": \"SalesTax\",\n" +
														 "                \"isSelfAssessed\": 0\n" + "            }\n" +
														 "        ],\n" + "        \"accountInstruction\": \"MW1\",\n" +
														 "        \"rank\": 17,\n" + "        \"lastHash\": 0\n" +
														 "    },\n" + "    {\n" + "        \"typeID\": null,\n" +
														 "        \"tenant\": 0,\n" +
														 "        \"taxType\": \"CGST\",\n" +
														 "        \"summaryTaxTypes\": [\n" + "            {\n" +
														 "                \"typeID\": 19194,\n" +
														 "                \"taxType\": \"SGST\",\n" +
														 "                \"isSelfAssessed\": 0\n" + "            }\n" +
														 "        ],\n" + "        \"accountInstruction\": \"\",\n" +
														 "        \"rank\": 18,\n" + "        \"lastHash\": 0\n" +
														 "    }\n" + "]";
		String configAribaTaxRulesRequestBody = "[\n" + "    {\n" + "        \"ruleId\": null,\n" +
												"        \"externalId\": \"1568037720267-2745\",\n" +
												"        \"compTypeId\": 18165,\n" + "        \"taxTypes\": [\n" +
												"            \"VAT\"\n" + "        ],\n" +
												"        \"jurisdictionLevels\": [\n" + "            \"COUNTRY\"\n" +
												"        ],\n" + "        \"vatTaxType\": null,\n" +
												"        \"impositionType\": null,\n" +
												"        \"impositionName\": \"GST/HST\",\n" +
												"        \"jurisdictionName\": null,\n" + "        \"priority\": 1,\n" +
												"        \"lastHash\": 0\n" + "    },\n" + "    {\n" +
												"        \"ruleId\": null,\n" +
												"        \"externalId\": \"1568037871784-854\",\n" +
												"        \"compTypeId\": 18171,\n" + "        \"taxTypes\": [\n" +
												"            \"SALES\"\n" + "        ],\n" +
												"        \"jurisdictionLevels\": [\n" + "            \n" +
												"        ],\n" + "        \"vatTaxType\": null,\n" +
												"        \"impositionType\": null,\n" +
												"        \"impositionName\": \"Provincial Sales Tax(PST)\",\n" +
												"        \"jurisdictionName\": null,\n" + "        \"priority\": 2,\n" +
												"        \"lastHash\": 0\n" + "    },\n" + "    {\n" +
												"        \"ruleId\": null,\n" +
												"        \"externalId\": \"1568037923265-9535\",\n" +
												"        \"compTypeId\": 18172,\n" + "        \"taxTypes\": [\n" +
												"            \"SELLER_USE\"\n" + "        ],\n" +
												"        \"jurisdictionLevels\": [\n" + "            \n" +
												"        ],\n" + "        \"vatTaxType\": null,\n" +
												"        \"impositionType\": null,\n" +
												"        \"impositionName\": \"Provincial Sales Tax(PST)\",\n" +
												"        \"jurisdictionName\": null,\n" + "        \"priority\": 3,\n" +
												"        \"lastHash\": 0\n" + "    },\n" + "    {\n" +
												"        \"ruleId\": null,\n" +
												"        \"externalId\": \"1568037960914-2628\",\n" +
												"        \"compTypeId\": 18165,\n" + "        \"taxTypes\": [\n" +
												"            \"VAT\"\n" + "        ],\n" +
												"        \"jurisdictionLevels\": [\n" + "            \n" +
												"        ],\n" + "        \"vatTaxType\": null,\n" +
												"        \"impositionType\": null,\n" +
												"        \"impositionName\": \"Quebec Sales Tax (VAT)\",\n" +
												"        \"jurisdictionName\": null,\n" + "        \"priority\": 4,\n" +
												"        \"lastHash\": 0\n" + "    },\n" + "    {\n" +
												"        \"ruleId\": null,\n" +
												"        \"externalId\": \"1568038000363-3041\",\n" +
												"        \"compTypeId\": 18165,\n" + "        \"taxTypes\": [\n" +
												"            \"VAT\"\n" + "        ],\n" +
												"        \"jurisdictionLevels\": [\n" + "            \n" +
												"        ],\n" + "        \"vatTaxType\": \"INPUT_OUTPUT\",\n" +
												"        \"impositionType\": null,\n" +
												"        \"impositionName\": null,\n" +
												"        \"jurisdictionName\": null,\n" + "        \"priority\": 5,\n" +
												"        \"lastHash\": 0\n" + "    },\n" + "    {\n" +
												"        \"ruleId\": null,\n" +
												"        \"externalId\": \"1568038044276-4021\",\n" +
												"        \"compTypeId\": 18165,\n" + "        \"taxTypes\": [\n" +
												"            \"VAT\"\n" + "        ],\n" +
												"        \"jurisdictionLevels\": [\n" + "            \n" +
												"        ],\n" + "        \"vatTaxType\": \"INPUT\",\n" +
												"        \"impositionType\": null,\n" +
												"        \"impositionName\": null,\n" +
												"        \"jurisdictionName\": null,\n" + "        \"priority\": 6,\n" +
												"        \"lastHash\": 0\n" + "    },\n" + "    {\n" +
												"        \"ruleId\": null,\n" +
												"        \"externalId\": \"1568038064459-2212\",\n" +
												"        \"compTypeId\": 18165,\n" + "        \"taxTypes\": [\n" +
												"            \"VAT\"\n" + "        ],\n" +
												"        \"jurisdictionLevels\": [\n" + "            \n" +
												"        ],\n" + "        \"vatTaxType\": \"OUTPUT\",\n" +
												"        \"impositionType\": null,\n" +
												"        \"impositionName\": null,\n" +
												"        \"jurisdictionName\": null,\n" + "        \"priority\": 7,\n" +
												"        \"lastHash\": 0\n" + "    },\n" + "    {\n" +
												"        \"ruleId\": null,\n" +
												"        \"externalId\": \"1568038073115-206\",\n" +
												"        \"compTypeId\": 18171,\n" + "        \"taxTypes\": [\n" +
												"            \"SALES\",\n" + "            \"SELLER_USE\"\n" +
												"        ],\n" + "        \"jurisdictionLevels\": [\n" +
												"            \"STATE\",\n" + "            \"PROVINCE\",\n" +
												"            \"TERRITORY\"\n" + "        ],\n" +
												"        \"vatTaxType\": null,\n" +
												"        \"impositionType\": null,\n" +
												"        \"impositionName\": null,\n" +
												"        \"jurisdictionName\": null,\n" + "        \"priority\": 8,\n" +
												"        \"lastHash\": 0\n" + "    },\n" + "    {\n" +
												"        \"ruleId\": null,\n" +
												"        \"externalId\": \"1568038122460-4610\",\n" +
												"        \"compTypeId\": 18171,\n" + "        \"taxTypes\": [\n" +
												"            \"SALES\",\n" + "            \"SELLER_USE\"\n" +
												"        ],\n" + "        \"jurisdictionLevels\": [\n" +
												"            \"COUNTY\",\n" + "            \"PARISH\"\n" +
												"        ],\n" + "        \"vatTaxType\": null,\n" +
												"        \"impositionType\": null,\n" +
												"        \"impositionName\": null,\n" +
												"        \"jurisdictionName\": null,\n" + "        \"priority\": 9,\n" +
												"        \"lastHash\": 0\n" + "    },\n" + "    {\n" +
												"        \"ruleId\": null,\n" +
												"        \"externalId\": \"1568038152862-2267\",\n" +
												"        \"compTypeId\": 18171,\n" + "        \"taxTypes\": [\n" +
												"            \"SALES\",\n" + "            \"SELLER_USE\"\n" +
												"        ],\n" + "        \"jurisdictionLevels\": [\n" +
												"            \"CITY\",\n" + "            \"TOWNSHIP\",\n" +
												"            \"BOROUGH\",\n" + "            \"APO\",\n" +
												"            \"FPO\"\n" + "        ],\n" +
												"        \"vatTaxType\": null,\n" +
												"        \"impositionType\": null,\n" +
												"        \"impositionName\": null,\n" +
												"        \"jurisdictionName\": null,\n" +
												"        \"priority\": 10,\n" + "        \"lastHash\": 0\n" +
												"    },\n" + "    {\n" + "        \"ruleId\": null,\n" +
												"        \"externalId\": \"1568038196942-6940\",\n" +
												"        \"compTypeId\": 18171,\n" + "        \"taxTypes\": [\n" +
												"            \"SALES\",\n" + "            \"SELLER_USE\"\n" +
												"        ],\n" + "        \"jurisdictionLevels\": [\n" +
												"            \"DISTRICT\",\n" +
												"            \"LOCAL_IMPROVEMENT_DISTRICT\",\n" +
												"            \"SPECIAL_PURPOSE_DISTRICT\"\n" + "        ],\n" +
												"        \"vatTaxType\": null,\n" +
												"        \"impositionType\": null,\n" +
												"        \"impositionName\": null,\n" +
												"        \"jurisdictionName\": null,\n" +
												"        \"priority\": 11,\n" + "        \"lastHash\": 0\n" +
												"    },\n" + "    {\n" + "        \"ruleId\": null,\n" +
												"        \"externalId\": \"1568038251029-1866\",\n" +
												"        \"compTypeId\": 18167,\n" + "        \"taxTypes\": [\n" +
												"            \"CONSUMERS_USE\"\n" + "        ],\n" +
												"        \"jurisdictionLevels\": [\n" + "            \"STATE\",\n" +
												"            \"PROVINCE\",\n" + "            \"TERRITORY\"\n" +
												"        ],\n" + "        \"vatTaxType\": null,\n" +
												"        \"impositionType\": null,\n" +
												"        \"impositionName\": null,\n" +
												"        \"jurisdictionName\": null,\n" +
												"        \"priority\": 12,\n" + "        \"lastHash\": 0\n" +
												"    },\n" + "    {\n" + "        \"ruleId\": null,\n" +
												"        \"externalId\": \"1568038284478-9737\",\n" +
												"        \"compTypeId\": 18167,\n" + "        \"taxTypes\": [\n" +
												"            \"CONSUMERS_USE\"\n" + "        ],\n" +
												"        \"jurisdictionLevels\": [\n" + "            \"COUNTY\",\n" +
												"            \"PARISH\"\n" + "        ],\n" +
												"        \"vatTaxType\": null,\n" +
												"        \"impositionType\": null,\n" +
												"        \"impositionName\": null,\n" +
												"        \"jurisdictionName\": null,\n" +
												"        \"priority\": 13,\n" + "        \"lastHash\": 0\n" +
												"    },\n" + "    {\n" + "        \"ruleId\": null,\n" +
												"        \"externalId\": \"1568038314870-7602\",\n" +
												"        \"compTypeId\": 18167,\n" + "        \"taxTypes\": [\n" +
												"            \"CONSUMERS_USE\"\n" + "        ],\n" +
												"        \"jurisdictionLevels\": [\n" + "            \"CITY\",\n" +
												"            \"TOWNSHIP\",\n" + "            \"BOROUGH\",\n" +
												"            \"APO\",\n" + "            \"FPO\"\n" + "        ],\n" +
												"        \"vatTaxType\": null,\n" +
												"        \"impositionType\": null,\n" +
												"        \"impositionName\": null,\n" +
												"        \"jurisdictionName\": null,\n" +
												"        \"priority\": 14,\n" + "        \"lastHash\": 0\n" +
												"    },\n" + "    {\n" + "        \"ruleId\": null,\n" +
												"        \"externalId\": \"1568038352519-1194\",\n" +
												"        \"compTypeId\": 18167,\n" + "        \"taxTypes\": [\n" +
												"            \"CONSUMERS_USE\"\n" + "        ],\n" +
												"        \"jurisdictionLevels\": [\n" + "            \"DISTRICT\",\n" +
												"            \"LOCAL_IMPROVEMENT_DISTRICT\",\n" +
												"            \"SPECIAL_PURPOSE_DISTRICT\"\n" + "        ],\n" +
												"        \"vatTaxType\": null,\n" +
												"        \"impositionType\": null,\n" +
												"        \"impositionName\": null,\n" +
												"        \"jurisdictionName\": null,\n" +
												"        \"priority\": 15,\n" + "        \"lastHash\": 0\n" +
												"    },\n" + "    {\n" + "        \"ruleId\": null,\n" +
												"        \"externalId\": \"1568038382687-8609\",\n" +
												"        \"compTypeId\": 18171,\n" + "        \"taxTypes\": [\n" +
												"            \"SALES\"\n" + "        ],\n" +
												"        \"jurisdictionLevels\": [\n" + "            \n" +
												"        ],\n" + "        \"vatTaxType\": null,\n" +
												"        \"impositionType\": null,\n" +
												"        \"impositionName\": null,\n" +
												"        \"jurisdictionName\": null,\n" +
												"        \"priority\": 16,\n" + "        \"lastHash\": 0\n" +
												"    },\n" + "    {\n" + "        \"ruleId\": null,\n" +
												"        \"externalId\": \"1568038395143-9634\",\n" +
												"        \"compTypeId\": 18171,\n" + "        \"taxTypes\": [\n" +
												"            \"SELLER_USE\"\n" + "        ],\n" +
												"        \"jurisdictionLevels\": [\n" + "            \n" +
												"        ],\n" + "        \"vatTaxType\": null,\n" +
												"        \"impositionType\": null,\n" +
												"        \"impositionName\": null,\n" +
												"        \"jurisdictionName\": null,\n" +
												"        \"priority\": 17,\n" + "        \"lastHash\": 0\n" +
												"    },\n" + "    {\n" + "        \"ruleId\": null,\n" +
												"        \"externalId\": \"1568038404594-8767\",\n" +
												"        \"compTypeId\": 18167,\n" + "        \"taxTypes\": [\n" +
												"            \"CONSUMERS_USE\"\n" + "        ],\n" +
												"        \"jurisdictionLevels\": [\n" + "            \n" +
												"        ],\n" + "        \"vatTaxType\": null,\n" +
												"        \"impositionType\": null,\n" +
												"        \"impositionName\": null,\n" +
												"        \"jurisdictionName\": null,\n" +
												"        \"priority\": 18,\n" + "        \"lastHash\": 0\n" +
												"    }\n" + "]";
		Response response = apiUtil.sendRequestWithCookie(VertexHTTPMethod.POST, "application/hal+json",
			"http://connector-dev.vertexconnectors.com:8100/vertex-ariba-2.0/data/maps/vrealm_2174/CustomFieldMapping",
			null, configCustomFieldMappingRequestBody, apiUtil.cookie);

		Response response2 = apiUtil.sendRequestWithCookie(VertexHTTPMethod.POST, "application/hal+json",
			"http://connector-dev.vertexconnectors.com:8100/vertex-ariba-2.0/data/maps/vrealm_2174/AcctFieldMapping",
			null, configAccountFieldMappingRequestBody, apiUtil.cookie);
		Response response3 = apiUtil.sendRequestWithCookie(VertexHTTPMethod.POST, "application/hal+json",
			"http://connector-dev.vertexconnectors.com:8100/vertex-ariba-2.0/data/summaryTypes/vrealm_2174", null,
			configAribaExternalTaxTypesRequestBody, apiUtil.cookie);
		Response response4 = apiUtil.sendRequestWithCookie(VertexHTTPMethod.POST, "application/hal+json",
			"http://connector-dev.vertexconnectors.com:8100/vertex-ariba-2.0/data/componentTypes/vrealm_2174", null,
			configAribaComponentTaxTypesRequestBody, apiUtil.cookie);
		Response response5 = apiUtil.sendRequestWithCookie(VertexHTTPMethod.POST, "application/hal+json",
			"http://connector-dev.vertexconnectors.com:8100/vertex-ariba-2.0/data/rules/vrealm_2174", null,
			configAribaTaxRulesRequestBody, apiUtil.cookie);
	}

	/**
	 * tried to retrieve an invoice ID for a PO.
	 *
	 * @param testCaseName name of the test case that generated that Purchase order.
	 *
	 * @return invoice ID
	 */
	protected String tryGetInvoiceId( final String testCaseName )
	{
		String invoiceId = null;
		try
		{
			invoiceId = SQLConnection.retrieveInvoiceId(testCaseName);
		}
		catch ( SQLException e )
		{
			e.printStackTrace();
		}

		String message = String.format("no invoice id found for %s test case", testCaseName);
		assertTrue(invoiceId != null, message);

		return invoiceId;
	}

	/**
	 * tries to delete a specific order record from the database.
	 *
	 * @param testCaseName the test case name which generated that order record.
	 */
	protected void tryDeleteOrderRecordFromDatabase( final String testCaseName )
	{
		try
		{
			SQLConnection.deleteOrderFromTable(testCaseName);
		}
		catch ( SQLException e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * Helper method to edit Catalog Line Items
	 *
	 * @param checkoutPage    the page on which the line items details are updated
	 * @param supplierName      supplier Name
	 * @param requisitionTitle      requisition title
	 * @param items                 list of items to create requisition for
	 *
	 * @return check out page after the details are updated
	 */
	protected AribaPortalRequisitionCheckoutPage editCatalogLineItemsRequisition(
			AribaPortalRequisitionCheckoutPage checkoutPage, String supplierName, String requisitionTitle,
			AribaLineItem... items )
	{
		editLineItemsDetailsToVerifyUpdatedTax(checkoutPage,items);
		checkoutPage.updateTaxes();
		return checkoutPage;
	}
}
