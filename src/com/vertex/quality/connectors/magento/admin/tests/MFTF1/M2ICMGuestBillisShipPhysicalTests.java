package com.vertex.quality.connectors.magento.admin.tests.MFTF1;

import com.vertex.quality.connectors.magento.admin.pages.M2AdminCreditMemoPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminNewInvoicePage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminOrderViewInfoPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminOrdersPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.storefront.pages.M2StorefrontThankYouPage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * tests that tax is correct in each state of putting in a invoice and credit memo
 * for an existing guest physical order
 * where billing and shipping addresses are the same
 *
 * @author alewis
 */
public class M2ICMGuestBillisShipPhysicalTests extends MagentoAdminBaseTest
{

	/**
	 * tests tax in Items to Invoice section of New Invoice page for PA state sales tax
	 */
	@Test()
	public void checkTaxInInvoicePageItemsToInvoiceTest( )
	{
		M2AdminNewInvoicePage invoicePage = navigateToNewInvoicePage();

		String exclPrice = invoicePage.getExclTaxPrice();
		String inclPrice = invoicePage.getInclTaxPrice();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests tax amount is correct percent of excluding tax price
	 * in Items to Invoice section of New Invoice page for PA state sales tax
	 */
	@Test()
	public void checkTaxAmountInItemsToInvoiceTest( )
	{
		M2AdminNewInvoicePage invoicePage = navigateToNewInvoicePage();

		String taxAmount = invoicePage.getTaxAmount();
		String exclPrice = invoicePage.getExclTaxPrice();

		String parseTaxAmount = taxAmount.substring(1);
		String exclPriceString = exclPrice.substring(1);

		double doubleTaxAmount = Double.parseDouble(parseTaxAmount);
		double doubleExclPrice = Double.parseDouble(exclPriceString);

		double difference = doubleTaxAmount / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_NUMBER.data));
	}

	/**
	 * tests Subtotal tax in Invoice Totals section of New Invoice page for PA state sales tax
	 */
	@Test()
	public void checkSubtotalTaxInInvoicePageInvoiceTotalsTest( )
	{
		M2AdminNewInvoicePage invoicePage = navigateToNewInvoicePage();

		String exclPrice = invoicePage.getInvoiceSubtotalExclTax();
		String inclPrice = invoicePage.getInvoiceSubtotalInclTax();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests Shipping & Handling tax in Invoice Totals section of New Invoice page for PA state
	 * sales tax
	 */
	@Test()
	public void checkShippingHandlingTaxInInvoicePageInvoiceTotalsTest( )
	{
		M2AdminNewInvoicePage invoicePage = navigateToNewInvoicePage();

		String exclPrice = invoicePage.getInvoiceShippingHandlingExclTax();
		String inclPrice = invoicePage.getInvoiceShippingHandlingInclTax();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests tax in tax blind Invoice Totals section of New Invoice page for PA state sales tax
	 */
	@Test()
	public void checkTaxInInvoicePageInvoiceTotalsTest( )
	{
		M2AdminNewInvoicePage invoicePage = navigateToNewInvoicePage();

		String exclPrice = invoicePage.getInvoiceTotalExclTax();
		String inclPrice = invoicePage.getInvoiceTotalInclTax();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests tax in Invoice Totals section of New Invoice page for PA state sales tax
	 */
	@Test()
	public void checkTaxInBlindInvoicePageInvoiceTotalsTest( )
	{
		M2AdminNewInvoicePage invoicePage = navigateToNewInvoicePage();

		String salesUsePrice = invoicePage.getSalesUseTax();
		String shippingTaxPrice = invoicePage.getShippingTax();

		String subtotalExcl = invoicePage.getInvoiceSubtotalExclTax();
		String shippingExcl = invoicePage.getInvoiceShippingHandlingExclTax();

		String parseSalesUsePrice = salesUsePrice.substring(1);
		double doubleSalesUsePrice = Double.parseDouble(parseSalesUsePrice);
		String parseSubtotalPrice = subtotalExcl.substring(1);
		double doubleSubtotalPrice = Double.parseDouble(parseSubtotalPrice);

		String parseShippingTaxPrice = shippingTaxPrice.substring(1);
		double doubleShippingTaxPrice = Double.parseDouble(parseShippingTaxPrice);
		String parseShippingPrice = shippingExcl.substring(1);
		double doubleShippingPrice = Double.parseDouble(parseShippingPrice);

		double difference = doubleSalesUsePrice / doubleSubtotalPrice;
		String differenceString = Double.toString(difference);

		double shippingDifference = doubleShippingTaxPrice / doubleShippingPrice;
		String shippingString = Double.toString(shippingDifference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_NUMBER.data));
		assertTrue(shippingString.equals(MagentoData.PA_TAX_NUMBER.data));
	}

	/**
	 * Checks Subtotal of tax in Items to Refund section of New Memo page for PA state sales tax
	 */
	@Test()
	public void checkSubtotalTaxInMemoPageRefundItemsSectionTest( )
	{
		M2AdminCreditMemoPage creditMemoPage = navigateToNewMemoPage();

		String exclPrice = creditMemoPage.getSubtotalExclTax();
		String inclPrice = creditMemoPage.getSubtotalInclTax();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * checks for correct tax amount in Items to Refund section of New Memo page for PA state sales
	 * tax
	 */
	@Test()
	public void checkTaxAmountInMemoPageRefundItemsSectionTest( )
	{
		M2AdminCreditMemoPage creditMemoPage = navigateToNewMemoPage();

		String taxAmount = creditMemoPage.getTaxAmount();
		String subtotalPrice = creditMemoPage.getSubtotalExclTax();

		String parseTaxAmount = taxAmount.substring(1);
		double doubleTaxAmount = Double.parseDouble(parseTaxAmount);

		String parseSubtotalPrice = subtotalPrice.substring(1);
		double doubleSubtotalPrice = Double.parseDouble(parseSubtotalPrice);

		double difference = doubleTaxAmount / doubleSubtotalPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_NUMBER.data));
	}

	/**
	 * test subtotal tax in Refund Totals section of New Memo page for PA state sales tax
	 */
	@Test()
	public void checkSubtotalTaxInMemoPageRefundTotalsSectionTest( )
	{
		M2AdminCreditMemoPage creditMemoPage = navigateToNewMemoPage();

		String exclPrice = creditMemoPage.getRefundSubtotalExclTax();
		String inclPrice = creditMemoPage.getRefundSubtotalInclTax();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * test tax in Blind in Refund Totals section of New Memo page for PA state sales tax
	 */
	@Test()
	public void checkBlindTaxInMemoPageRefundTotalsSectionTest( )
	{
		navigateToCreditMemoPageForExistOrder();

		M2AdminCreditMemoPage creditMemoPage = new M2AdminCreditMemoPage(driver);

		String salesUsePrice = creditMemoPage.getSalesUseTax();
		String subtotalPrice = creditMemoPage.getSubtotalExclTax();

		String parseSalesUsePrice = salesUsePrice.substring(1);
		double doubleSalesUsePrice = Double.parseDouble(parseSalesUsePrice);
		String parseSubtotalPrice = subtotalPrice.substring(1);
		double doubleSubtotalPrice = Double.parseDouble(parseSubtotalPrice);

		double difference = doubleSalesUsePrice / doubleSubtotalPrice;
		String slicedDifferenceString = String.format("%.2f", difference);

		assertTrue(slicedDifferenceString.equals(MagentoData.PA_TAX_NUMBER.data));
	}

	/**
	 * test tax in Refund Totals section of New Memo page for PA state sales tax
	 */
	@Test()
	public void checkTaxInMemoPageRefundTotalsSectionTest( )
	{
		navigateToCreditMemoPageForExistOrder();

		M2AdminCreditMemoPage creditMemoPage = new M2AdminCreditMemoPage(driver);

		String exclPrice = creditMemoPage.getRefundTotalExclTax();
		String inclPrice = creditMemoPage.getRefundTotalInclTax();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String slicedDifferenceString = String.format("%.2f", difference);

		assertTrue(slicedDifferenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * gets the order number from the thank you page after putting in order from storefront
	 *
	 * @return a String of the order number
	 */
	protected String getOrderFromStorefront( )
	{
		M2StorefrontThankYouPage thankYouPage = putInOrderForAdmin();
		String orderNumber = thankYouPage.getOrderNumber();
		clickLogoButton();
		clearShoppingCart();

		return orderNumber;
	}

	/**
	 * navigates to the new invoice page for a guest order
	 */
	protected void navigateToInvoicePageForExistOrder( )
	{
		String orderNumber = getOrderFromStorefront();
		M2AdminOrdersPage ordersPage = navigateToOrders();

		ordersPage.clickOrder(orderNumber);
	}

	/**
	 * navigates to the new credit memo page for a guest order
	 */
	protected void navigateToCreditMemoPageForExistOrder( )
	{
		String orderNumber = getOrderFromStorefront();
		M2AdminOrdersPage ordersPage = navigateToOrders();
		M2AdminOrderViewInfoPage orderViewInfoPage = ordersPage.clickOrder(orderNumber);

		M2AdminNewInvoicePage invoicePage = orderViewInfoPage.clickInvoiceButton();

		invoicePage.clickSubmitInvoiceButton();
		M2AdminCreditMemoPage creditMemoPage = invoicePage.clickCreditMemoButton();
		creditMemoPage.clicksTaxBlind();
	}
}
