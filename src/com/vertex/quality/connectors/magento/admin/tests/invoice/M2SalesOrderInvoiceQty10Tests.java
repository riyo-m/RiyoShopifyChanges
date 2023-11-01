package com.vertex.quality.connectors.magento.admin.tests.invoice;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminCreateNewOrderPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminOrderViewInfoPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * Tests tax on Create Sale Order for VAT (US-EU) and Invoice
 * CMAGM2-772
 *
 * @author rohit-mogane
 */
public class M2SalesOrderInvoiceQty10Tests extends MagentoAdminBaseTest
{

	SoftAssert assertEUTax;
	M2AdminCreateNewOrderPage newOrderPage;
	M2AdminOrderViewInfoPage newViewInfoPage;
	Map<String,String> billingAddress, shippingAddress;

	/**
	 * tests tax on bottom right of page in Create New Order Page for US EU tax rate
	 * CMAGM2-808
	 */
	@Test(groups = {"magento_regression"})
	public void checkTaxRateCreateNewOrderQyt10Test( )
	{
		setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);

		newOrderPage = addProductAddressDetails("10");
		billingAddress = createBillingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
				MagentoData.EU_BILLING_LAST_NAME.data, Address.Paris.addressLine1,
				Address.Paris.country.fullName, Address.Paris.city,
				Address.Paris.city, Address.Paris.zip5,
				MagentoData.EU_BILLING_PHONE.data);
		shippingAddress = createShippingAddress(MagentoData.US_SHIPPING6_FIRST_NAME.data,
				MagentoData.US_SHIPPING6_LAST_NAME.data,MagentoData.US_SHIPPING6_STREET0.data,
				MagentoData.US_SHIPPING6_COUNTRY.data, MagentoData.US_SHIPPING6_STATE.data,
				MagentoData.US_SHIPPING6_CITY.data, MagentoData.US_SHIPPING6_ZIP.data,
				MagentoData.US_SHIPPING6_PHONE.data);
		newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

		assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(20));
		newViewInfoPage = submitOrderForVAT();
	}
}
