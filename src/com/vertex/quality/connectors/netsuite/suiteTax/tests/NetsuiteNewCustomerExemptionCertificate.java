package com.vertex.quality.connectors.netsuite.suiteTax.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.enums.State;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.customers.NetsuiteAPICustomerPage;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPISalesOrderPage;
import com.vertex.quality.connectors.netsuite.suiteTax.tests.base.NetsuiteBaseVatTest;
import org.testng.annotations.Test;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class NetsuiteNewCustomerExemptionCertificate extends NetsuiteBaseVatTest {

    /**
     * Create a New Customer, Exemption certificate & validate the auto population of Vertex Customer ID, if left blank
	 * Validate that any transaction (Sales order) with this new customer will return a zero Vertex tax
     * @author ravunuri
     * CNSAPI-371
     */
    @Test(groups = {"netsuite_suite_smoke"})
    protected void ValidateExemptionCertificateTest ( )
	{
		NetsuiteAddress validAddress = NetsuiteAddress
			.builder("19406")
			.addressLine1("2301 Renaissance Blvd")
			.city("King Of Prussia")
			.state(State.PA)
			.country(Country.USA)
			.zip9("19406-2772")
			.build();

		String customerId = "Exemption Customer";
		String subsidiary = "Honeycomb Holdings Inc.";
		String certificateNum = "12345";
		String expectedCertResult = "SUCCESS";
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String certificateStartDate = dateFormat.format(date);

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteNavigationMenus customersMenu = getCustomerMenu();
		NetsuiteAPICustomerPage customerPage = setupManagerPage.navigationPane.navigateThrough(customersMenu);
		assertTrue(customerPage.isVertexCustomerIdFieldAvailable());

		//Create a new CUSTOMER with address, save the record, validate that Vertex customer id auto-populates
		customerPage.createNewCustomer(customerId, subsidiary, validAddress);

		//Create new Vertex 'EXEMPTION CERTIFICATE' (under CUSTOM section)
		customerPage.createExemptionCertificate(certificateNum, certificateStartDate);

		//Verify the Exemption Certificate result has a 'SUCCESS' status
		String actualCertResult = customerPage.getExemptionCertificateResult();
		assertEquals(actualCertResult, expectedCertResult);

		NetsuiteCustomer customer = NetsuiteCustomer.TEST_EXEMPTION_CUSTOMER;
		String total = "<Total>100.0</Total>";
		String taxRate = "<EffectiveRate>0.0</EffectiveRate>";
		String calculatedTax = "<CalculatedTax>0.0</CalculatedTax>";
		NetsuiteItem item = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).quantity("1").amount("100.00").build();

		//Navigate to Enter Sales Order menu
		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
		NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
		setupBasicOrder(salesOrderPage, customer, item);

		//Wait for Exemption Certificate to Process
		salesOrderPage.waitUntilHasRecordProcessed();
		NetsuiteAPISalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();
		checkDocumentLogs(savedSalesOrderPage, taxRate, calculatedTax, total);

		//Delete Sales order
		deleteDocument(savedSalesOrderPage);
		//Delete CUSTOMER with Exemption Certificate
		customerPage.searchForRecord(customerId);
	}

	/**
	 * Try to delete an Exemption certificate
	 * CNSAPI-1384
	 */
	@Test(groups = {"suite_tax_regression"})
	protected void DeleteExemptionCertificateTest ( )
	{
		NetsuiteAddress validAddress = NetsuiteAddress
			.builder("19406")
			.addressLine1("2301 Renaissance Blvd")
			.city("King Of Prussia")
			.state(State.PA)
			.country(Country.USA)
			.zip9("19406-2772")
			.build();

		String customerId = "Another Exemption Customer";
		String subsidiary = "Honeycomb Holdings Inc.";
		String certificateNum = "12345";
		String certificateId = "";
		String expectedCertResult = "SUCCESS";
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String certificateStartDate = dateFormat.format(date);

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteNavigationMenus customersMenu = getCustomerMenu();
		NetsuiteAPICustomerPage customerPage = setupManagerPage.navigationPane.navigateThrough(customersMenu);
		assertTrue(customerPage.isVertexCustomerIdFieldAvailable());

		//Create a new CUSTOMER with address, save the record, validate that Vertex customer id auto-populates
		customerPage.createNewCustomer(customerId, subsidiary, validAddress);

		//Create new Vertex 'EXEMPTION CERTIFICATE' (under CUSTOM section)
		customerPage.createExemptionCertificate(certificateNum, certificateStartDate);

		//Verify the Exemption Certificate result has a 'SUCCESS' status
		String actualCertResult = customerPage.getExemptionCertificateResult();
		assertEquals(actualCertResult, expectedCertResult);
		certificateId = customerPage.getExemptionCertId();

		//Try to delete an Exemption Certificate and validate the display of a Warning message

		customerPage.deleteCert(certificateId); //Navigate to page

		//Delete CUSTOMER with Exemption Certificate
		customerPage.searchForRecord(customerId);
	}
}