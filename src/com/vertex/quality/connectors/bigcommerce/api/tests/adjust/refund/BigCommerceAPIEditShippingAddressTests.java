package com.vertex.quality.connectors.bigcommerce.api.tests.adjust.refund;

import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceCurrency;
import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceTestData;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceQuoteRequest;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestDocument;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestItem;
import com.vertex.quality.connectors.bigcommerce.api.tests.base.BigCommerceAPIBaseTest;
import com.vertex.quality.connectors.bigcommerce.common.enums.BigCommerceTestDataAddress;
import com.vertex.quality.connectors.bigcommerce.ui.tests.refund.oseries.OSeriesTaxJournalExportTests;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class BigCommerceAPIEditShippingAddressTests extends BigCommerceAPIBaseTest
{
	/**
	 * CBC-177
	 * this test case sends a commit request and then get's the invoice id from the response.
	 * then sends an adjustment request for the same invoice id and check the reversal transaction.
	 * Reversal transaction should appear in the log.
	 * Return Sales order with edit shipping address.
	 * 1. with in same month
	 * 2. 1 day less than 3 months
	 * 3. 3 month
	 * 4. 3 months(+1 day)
	 * 5. 6 months out(Tax is paid to jurisdiction)
	 *
	 * @author rohit-mogane
	 */

	OSeriesTaxJournalExportTests taxJournalExportTest;

	/**
	 * Return Sales order with edit shipping address with in same month.
	 */
	@Test(groups = { "bigCommerce_refund", "bigCommerce_smoke", "bigCommerce_regression" })
	public void editShippingAddressInSameMonthTest( )
	{
		BigCommerceTestDataAddress paAddress1 = BigCommerceTestDataAddress.US_PA_ADDRESS_1;
		BigCommerceTestDataAddress paAddress2 = BigCommerceTestDataAddress.US_PA_ADDRESS_2;

		BigCommerceTestDataAddress paAddress3 = BigCommerceTestDataAddress.US_PA_ADDRESS_3;
		BigCommerceTestDataAddress paAddress4 = BigCommerceTestDataAddress.US_PA_ADDRESS_4;

		BigCommerceRequestItem originAddressShirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity,
			item1Id);
		BigCommerceRequestItem changedAddressShirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity,
			item2Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, paAddress1, paAddress2, false,
			originAddressShirt);
		BigCommerceRequestDocument doc2 = apiUtil.buildDocument(document2Id, paAddress3, paAddress4, false,
			changedAddressShirt);

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc1);

		String fromDate = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

		BigCommerceQuoteRequest adjustRequest = apiUtil.buildQuoteRequest(invoiceId, BigCommerceCurrency.USD,
			customer1Id, doc2);

		Response adjustResponse = apiUtil.sendAdjustRequest(adjustRequest, invoiceId, standardLowPriceAmount);

		Response messageLogResponse = apiUtil.sendReversalGetRequest(fromDate, invoiceId);
		apiUtil.verifyMessageLogKeyword(messageLogResponse, invoiceId);
	}

	/**
	 * Return Sales order with edit shipping address with 1 day less than 3 months
	 */
	@Test(groups = { "bigCommerce_refund", "bigCommerce_regression" })
	public void editShippingAddressOneDayLessThreeMonthsTest( )
	{
		BigCommerceTestDataAddress paAddress1 = BigCommerceTestDataAddress.US_PA_ADDRESS_1;
		BigCommerceTestDataAddress paAddress2 = BigCommerceTestDataAddress.US_PA_ADDRESS_2;

		BigCommerceTestDataAddress paAddress3 = BigCommerceTestDataAddress.US_PA_ADDRESS_3;
		BigCommerceTestDataAddress paAddress4 = BigCommerceTestDataAddress.US_PA_ADDRESS_4;

		BigCommerceRequestItem originAddressShirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity,
			item1Id);
		BigCommerceRequestItem changedAddressShirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity,
			item2Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, paAddress1, paAddress2, false,
			originAddressShirt);
		BigCommerceRequestDocument doc2 = apiUtil.buildDocument(document2Id, paAddress3, paAddress4, false,
			changedAddressShirt);

		String oneDayLessThanThreeMonths = apiUtil.currentDateTime(
			BigCommerceTestData.REFUND_THREE_LESS_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequestWithDate(quote1Id, BigCommerceCurrency.USD,
			customer1Id, oneDayLessThanThreeMonths, doc1);
		String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

		oneDayLessThanThreeMonths = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ONE_LESS_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		BigCommerceQuoteRequest adjustRequest = apiUtil.buildQuoteRequestWithDate(invoiceId, BigCommerceCurrency.USD,
			customer1Id, oneDayLessThanThreeMonths, doc2);
		Response adjustResponse = apiUtil.sendAdjustRequest(adjustRequest, invoiceId, standardLowPriceAmount);

		String fromDate = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ONE_LESS_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());
		Response messageLogResponse = apiUtil.sendReversalGetRequest(fromDate, invoiceId);
		apiUtil.verifyMessageLogKeyword(messageLogResponse, invoiceId);
	}

	/**
	 * Return Sales order with edit shipping address on last day of 3rd month
	 */
	@Test(groups = { "bigCommerce_refund", "bigCommerce_regression" })
	public void editShippingAddressOnLastDayOfThirdMonthTest( )
	{
		BigCommerceTestDataAddress paAddress1 = BigCommerceTestDataAddress.US_PA_ADDRESS_1;
		BigCommerceTestDataAddress paAddress2 = BigCommerceTestDataAddress.US_PA_ADDRESS_2;

		BigCommerceTestDataAddress paAddress3 = BigCommerceTestDataAddress.US_PA_ADDRESS_3;
		BigCommerceTestDataAddress paAddress4 = BigCommerceTestDataAddress.US_PA_ADDRESS_4;

		BigCommerceRequestItem originAddressShirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity,
			item1Id);
		BigCommerceRequestItem changedAddressShirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity,
			item2Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, paAddress1, paAddress2, false,
			originAddressShirt);
		BigCommerceRequestDocument doc2 = apiUtil.buildDocument(document2Id, paAddress3, paAddress4, false,
			changedAddressShirt);

		String onLastDayOfThirdMonth = apiUtil.currentDateTime(
			BigCommerceTestData.REFUND_THREE_LESS_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequestWithDate(quote1Id, BigCommerceCurrency.USD,
			customer1Id, onLastDayOfThirdMonth, doc1);

		String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

		onLastDayOfThirdMonth = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		BigCommerceQuoteRequest adjustRequest = apiUtil.buildQuoteRequestWithDate(invoiceId, BigCommerceCurrency.USD,
			customer1Id, onLastDayOfThirdMonth, doc2);

		Response adjustResponse = apiUtil.sendAdjustRequest(adjustRequest, invoiceId, standardLowPriceAmount);

		String fromDate = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());
		Response messageLogResponse = apiUtil.sendReversalGetRequest(fromDate, invoiceId);
		apiUtil.verifyMessageLogKeyword(messageLogResponse, invoiceId);
	}

	/**
	 * Return Sales order with edit shipping address with 3 months and one day
	 */
	@Test(groups = { "bigCommerce_refund", "bigCommerce_regression" })
	public void editShippingAddressThreeMonthsPlusOneDayTest( )
	{
		int months = -3, oneDay = 0, lastDay = 0;

		BigCommerceTestDataAddress paAddress1 = BigCommerceTestDataAddress.US_PA_ADDRESS_1;
		BigCommerceTestDataAddress paAddress2 = BigCommerceTestDataAddress.US_PA_ADDRESS_2;

		BigCommerceTestDataAddress paAddress3 = BigCommerceTestDataAddress.US_PA_ADDRESS_3;
		BigCommerceTestDataAddress paAddress4 = BigCommerceTestDataAddress.US_PA_ADDRESS_4;

		BigCommerceRequestItem originAddressShirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity,
			item1Id);
		BigCommerceRequestItem changedAddressShirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity,
			item2Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, paAddress1, paAddress2, false,
			originAddressShirt);
		BigCommerceRequestDocument doc2 = apiUtil.buildDocument(document2Id, paAddress3, paAddress4, false,
			changedAddressShirt);

		String threeMonthsPlusOneDay = apiUtil.currentDateTime(
			BigCommerceTestData.REFUND_THREE_LESS_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequestWithDate(quote1Id, BigCommerceCurrency.USD,
			customer1Id, threeMonthsPlusOneDay, doc1);

		String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

		String threeMonthsPurge = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		createDriver();
		taxJournalExportTest = new OSeriesTaxJournalExportTests();
		taxJournalExportTest.taxJournalExport(driver, threeMonthsPurge);

		threeMonthsPlusOneDay = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ONE_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		BigCommerceQuoteRequest adjustRequest = apiUtil.buildQuoteRequestWithDate(invoiceId, BigCommerceCurrency.USD,
			customer1Id, threeMonthsPlusOneDay, doc2);
		Response adjustResponse = apiUtil.sendAdjustRequest(adjustRequest, invoiceId, standardLowPriceAmount);

		String fromDate = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ONE_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		Response messageLogResponse = apiUtil.sendReversalGetRequest(fromDate, invoiceId);
		apiUtil.verifyMessageLogForPurge(messageLogResponse, invoiceId);
	}

	/**
	 * Return Sales order with edit shipping address with 6 months
	 */
	@Test(groups = { "bigCommerce_refund", "bigCommerce_regression" })
	public void editShippingAddressSixMonthsOutTest( )
	{
		int months = -6, oneDay = 0, lastDay = 0;

		BigCommerceTestDataAddress paAddress1 = BigCommerceTestDataAddress.US_PA_ADDRESS_1;
		BigCommerceTestDataAddress paAddress2 = BigCommerceTestDataAddress.US_PA_ADDRESS_2;

		BigCommerceTestDataAddress paAddress3 = BigCommerceTestDataAddress.US_PA_ADDRESS_3;
		BigCommerceTestDataAddress paAddress4 = BigCommerceTestDataAddress.US_PA_ADDRESS_4;

		BigCommerceRequestItem originAddressShirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity,
			item1Id);
		BigCommerceRequestItem changedAddressShirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity,
			item2Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, paAddress1, paAddress2, false,
			originAddressShirt);
		BigCommerceRequestDocument doc2 = apiUtil.buildDocument(document2Id, paAddress3, paAddress4, false,
			changedAddressShirt);

		String sixMonthsOut = apiUtil.currentDateTime(BigCommerceTestData.REFUND_SIX_LESS_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequestWithDate(quote1Id, BigCommerceCurrency.USD,
			customer1Id, sixMonthsOut, doc1);

		String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

		String threeMonthsPurge = apiUtil.currentDateTime(BigCommerceTestData.REFUND_THREE_LESS_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		createDriver();
		taxJournalExportTest = new OSeriesTaxJournalExportTests();
		taxJournalExportTest.taxJournalExport(driver, threeMonthsPurge);

		sixMonthsOut = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ONE_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		BigCommerceQuoteRequest adjustRequest = apiUtil.buildQuoteRequestWithDate(invoiceId, BigCommerceCurrency.USD,
			customer1Id, sixMonthsOut, doc2);
		Response adjustResponse = apiUtil.sendAdjustRequest(adjustRequest, invoiceId, standardLowPriceAmount);

		String fromDate = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ONE_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		Response messageLogResponse = apiUtil.sendReversalGetRequest(fromDate, invoiceId);
		apiUtil.verifyMessageLogForPurge(messageLogResponse, invoiceId);
	}
}
