package com.vertex.quality.connectors.magento.admin.tests.MFTF1;

import com.vertex.quality.connectors.magento.admin.pages.M2AdminCreateNewOrderPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminCreditMemoPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminNewInvoicePage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminOrderViewInfoPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * tests inputting a physical order, of which billing and shipping address are different
 *
 * @author alewis
 */
public class M2AOICMBillNotShipPhysicalTests extends MagentoAdminBaseTest
{
	/**
	 * tests tax on top of page in Create New Order Page for PA State tax rate
	 */
	@Test()
	public void checkTaxInCreateNewOrderItemsOrderTest( )
	{
		M2AdminCreateNewOrderPage newOrderPage = addProductToSalesOrders();

		String excludingPrice = newOrderPage.getPriceSubtotalExclTax();
		String includingPrice = newOrderPage.getPriceSubtotalInclTax();

		String parseTaxValueExcl = excludingPrice.substring(1);
		double priceDoubleExcl = Double.parseDouble(parseTaxValueExcl);

		String parseTaxValueIncl = includingPrice.substring(1);
		double priceDoubleIncl = Double.parseDouble(parseTaxValueIncl);

		double total = priceDoubleIncl / priceDoubleExcl;
		String totalString = Double.toString(total);

		assertTrue(totalString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests tax on bottom right of page in Create New Order Page for PA State tax rate
	 */
	@Test()
	public void checkTaxAmountInCreateNewOrderOrderTotalsTest( )
	{
		M2AdminCreateNewOrderPage newOrderPage = addProductToSalesOrders();

		String excludingPrice = newOrderPage.getPriceTotalExclTax();
		String includingPrice = newOrderPage.getPriceTotalInclTax();

		String parseTaxValueExcl = excludingPrice.substring(1);
		double priceDoubleExcl = Double.parseDouble(parseTaxValueExcl);

		String parseTaxValueIncl = includingPrice.substring(1);
		double priceDoubleIncl = Double.parseDouble(parseTaxValueIncl);

		double total = priceDoubleIncl / priceDoubleExcl;
		String totalString = Double.toString(total);

		assertTrue(totalString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests Tax Amount is correct percent of Original Price in Items Ordered section of Order View Info page
	 * for PA state sales tax
	 */
	@Test()
	public void checkSubtotalTaxInOrderInfoPageItemsOrderedTest( )
	{
		M2AdminOrderViewInfoPage infoPage = navigateOrderViewInfoPage();

		String exclTaxPrice = infoPage.getSubtotalExclTax();
		String inclTaxPrice = infoPage.getSubtotalInclTax();

		String parseExclPrice = exclTaxPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclAmount = inclTaxPrice.substring(1);
		double doubleInclAmount = Double.parseDouble(parseInclAmount);

		double difference = doubleInclAmount / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests tax in Items Ordered section of Order View Info page for PA state sales tax
	 */
	@Test()
	public void checkTaxInOrderInfoPageItemsOrderedTest( )
	{
		M2AdminOrderViewInfoPage infoPage = navigateOrderViewInfoPage();

		String originalPrice = infoPage.getOriginalPrice();
		String taxAmount = infoPage.getTaxAmount();

		String parseOriginalPrice = originalPrice.substring(1);
		double doubleOriginalPrice = Double.parseDouble(parseOriginalPrice);

		String parseTaxAmount = taxAmount.substring(1);
		double doubleTaxAmount = Double.parseDouble(parseTaxAmount);

		double percentTaxPaid = doubleTaxAmount / doubleOriginalPrice;
		String percentTaxPaidString = Double.toString(percentTaxPaid);

		assertTrue(percentTaxPaidString.equals(MagentoData.PA_TAX_NUMBER.data));
	}

	/**
	 * tests the tax percent in Items Ordered section of Order View Info page for PA state sales tax is correct
	 */
	@Test()
	public void checkTaxPercentInOrderInfoPageItemsOrderedTest( )
	{
		M2AdminOrderViewInfoPage infoPage = navigateOrderViewInfoPage();

		String taxPercent = infoPage.getTaxPercent();

		assertTrue(taxPercent.equals(MagentoData.PA_TAX_PERCENT.data));
	}

	/**
	 * tests tax in Order Totals section of Order View Info page for PA state sales tax
	 */
	@Test()
	public void checkSubtotalTaxInOrderInfoPageOrderTotalsTest( )
	{
		M2AdminOrderViewInfoPage infoPage = navigateOrderViewInfoPage();

		String exclPrice = infoPage.getSubtotalExclTaxOrderTotals();
		String inclPrice = infoPage.getSubtotalInclTaxOrderTotals();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests tax in Order Totals section of Order View Info page for PA state sales tax
	 */
	@Test()
	public void checkShippingHandlingTaxInOrderInfoPageOrderTotalsTest( )
	{
		M2AdminOrderViewInfoPage infoPage = navigateOrderViewInfoPage();

		String exclPrice = infoPage.getShippingExclTaxOrderTotals();
		String inclPrice = infoPage.getShippingInclTaxOrderTotals();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests taxes in tax blind in Order Totals section of Order View Info page for PA state sales tax
	 */
	@Test()
	public void checkBlindTaxesInOrderInfoPageOrderTotalsTest( )
	{
		M2AdminOrderViewInfoPage infoPage = navigateOrderViewInfoPage();

		String salesUsePrice = infoPage.getSalesUseTax();
		String shippingTaxPrice = infoPage.getShippingTax();

		String exclSubtotal = infoPage.getSubtotalExclTaxOrderTotals();
		String exclShippingHandling = infoPage.getShippingExclTaxOrderTotals();

		String parseSalesUsePrice = salesUsePrice.substring(1);
		double doubleSalesUsePrice = Double.parseDouble(parseSalesUsePrice);
		String parseShippingTaxPrice = shippingTaxPrice.substring(1);
		double doubleShippingTaxPrice = Double.parseDouble(parseShippingTaxPrice);

		String parseSubtotalPrice = exclSubtotal.substring(1);
		double doubleSubtotalPrice = Double.parseDouble(parseSubtotalPrice);
		String parseShippingHandlingPrice = exclShippingHandling.substring(1);
		double doubleShippingHandlingPrice = Double.parseDouble(parseShippingHandlingPrice);

		double correctSalesUsePercent = doubleSalesUsePrice / doubleSubtotalPrice;
		String differenceString = Double.toString(correctSalesUsePercent);

		double correctShippingPercent = doubleShippingTaxPrice / doubleShippingHandlingPrice;
		String differenceShippingString = Double.toString(correctShippingPercent);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_NUMBER.data));
		assertTrue(differenceShippingString.equals(MagentoData.PA_TAX_NUMBER.data));
	}

	/**
	 * tests tax in Order Totals section of Order View Info page for PA state sales tax
	 */
	@Test()
	public void checkTaxInOrderInfoPageOrderTotalsTest( )
	{
		M2AdminOrderViewInfoPage infoPage = navigateOrderViewInfoPage();

		String exclPrice = infoPage.getPriceTotalExclTax();
		String inclPrice = infoPage.getPriceTotalInclTax();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

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
	 * tests Shipping & Handling tax in Invoice Totals section of New Invoice page for PA state sales tax
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
	public void colPriceOriginal( )
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
	 * checks for correct tax amount in Items to Refund section of New Memo page for PA state sales tax
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
		M2AdminCreditMemoPage creditMemoPage = navigateToNewMemoPage();

		String salesUsePrice = creditMemoPage.getSalesUseTax();
		String subtotalPrice = creditMemoPage.getSubtotalExclTax();

		String parseSalesUsePrice = salesUsePrice.substring(1);
		double doubleSalesUsePrice = Double.parseDouble(parseSalesUsePrice);
		String parseSubtotalPrice = subtotalPrice.substring(1);
		double doubleSubtotalPrice = Double.parseDouble(parseSubtotalPrice);

		double difference = doubleSalesUsePrice / doubleSubtotalPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_NUMBER.data));
	}

	/**
	 * test tax in Refund Totals section of New Memo page for PA state sales tax
	 */
	@Test()
	public void checkTaxInMemoPageRefundTotalsSectionTest( )
	{
		M2AdminCreditMemoPage creditMemoPage = navigateToNewMemoPage();

		String exclPrice = creditMemoPage.getRefundTotalExclTax();
		String inclPrice = creditMemoPage.getRefundTotalInclTax();

		String parseExclPrice = exclPrice.substring(1);
		double doubleExclPrice = Double.parseDouble(parseExclPrice);

		String parseInclPrice = inclPrice.substring(1);
		double doubleInclPrice = Double.parseDouble(parseInclPrice);

		double difference = doubleInclPrice / doubleExclPrice;
		String differenceString = Double.toString(difference);

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}
}