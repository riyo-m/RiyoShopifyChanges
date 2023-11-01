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
public class M2SalesOrderInvoiceChangeLocTests extends MagentoAdminBaseTest
{

	SoftAssert assertEUTax;
	M2AdminCreateNewOrderPage newOrderPage;
	M2AdminOrderViewInfoPage newViewInfoPage;
	Map<String,String> billingAddress, shippingAddress;

	/**
	 * tests tax on bottom right of page in Create New Order Page for US EU tax rate
	 * CMAGM2-805
	 */
	@Test(groups = {"magento_regression"})
	public void checkTaxRateCreateNewOrderChangeLocTest( )
	{
		setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);

		newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
		billingAddress = createBillingAddress(MagentoData.EU_BILLING_FIRST_NAME.data,
			MagentoData.EU_BILLING_LAST_NAME.data, Address.Paris.addressLine1,
				Address.Paris.country.fullName, Address.Paris.city,
			Address.Paris.city, Address.Paris.zip5,
			MagentoData.EU_BILLING_PHONE.data);
		shippingAddress = createShippingAddress(MagentoData.US_SHIPPING3_FIRST_NAME.data,
			MagentoData.US_SHIPPING3_LAST_NAME.data,MagentoData.US_SHIPPING3_STREET0.data,
			MagentoData.US_SHIPPING3_COUNTRY.data, MagentoData.US_SHIPPING3_STATE.data,
			MagentoData.US_SHIPPING3_CITY.data, MagentoData.US_SHIPPING3_ZIP.data,
			MagentoData.US_SHIPPING3_PHONE.data);
		newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

		assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(20));
		newViewInfoPage = submitOrderForVAT();

		newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
		billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
				MagentoData.EU_SHIPPING_LAST_NAME.data,MagentoData.EU_SHIPPING_STREET0.data,
				MagentoData.EU_SHIPPING_COUNTRY.data, MagentoData.EU_SHIPPING_STATE.data,
				MagentoData.EU_SHIPPING_CITY.data, MagentoData.EU_SHIPPING_ZIP.data,
				MagentoData.EU_SHIPPING_PHONE.data);
		shippingAddress = createShippingAddress(MagentoData.US_SHIPPING4_FIRST_NAME.data,
				MagentoData.US_SHIPPING4_LAST_NAME.data,MagentoData.US_SHIPPING4_STREET0.data,
				MagentoData.US_SHIPPING4_COUNTRY.data, MagentoData.US_SHIPPING4_STATE.data,
				MagentoData.US_SHIPPING4_CITY.data, MagentoData.US_SHIPPING4_ZIP.data,
				MagentoData.US_SHIPPING4_PHONE.data);
		newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

		assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(20));
		newViewInfoPage = submitOrderForVAT();

		newOrderPage = addProductAddressDetails(MagentoData.quantity_five.data);
		billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
				MagentoData.EU_SHIPPING_LAST_NAME.data,MagentoData.EU_SHIPPING_STREET0.data,
				MagentoData.EU_SHIPPING_COUNTRY.data, MagentoData.EU_SHIPPING_STATE.data,
				MagentoData.EU_SHIPPING_CITY.data, MagentoData.EU_SHIPPING_ZIP.data,
				MagentoData.EU_SHIPPING_PHONE.data);
		shippingAddress = createShippingAddress(MagentoData.US_SHIPPING4_FIRST_NAME.data,
				MagentoData.US_SHIPPING4_LAST_NAME.data,MagentoData.US_SHIPPING4_STREET0.data,
				MagentoData.US_SHIPPING4_COUNTRY.data, MagentoData.US_SHIPPING4_STATE.data,
				MagentoData.US_SHIPPING4_CITY.data, MagentoData.US_SHIPPING4_ZIP.data,
				MagentoData.US_SHIPPING4_PHONE.data);
		newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

		assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(20));
		newViewInfoPage = submitOrderForVAT();

		newOrderPage = addProductAddressDetails(MagentoData.quantity_one.data);
		billingAddress = createBillingAddress(MagentoData.EU_SHIPPING_FIRST_NAME.data,
				MagentoData.EU_SHIPPING_LAST_NAME.data,MagentoData.EU_SHIPPING_STREET0.data,
				MagentoData.EU_SHIPPING_COUNTRY.data, MagentoData.EU_SHIPPING_STATE.data,
				MagentoData.EU_SHIPPING_CITY.data, MagentoData.EU_SHIPPING_ZIP.data,
				MagentoData.EU_SHIPPING_PHONE.data);
		shippingAddress = createShippingAddress(MagentoData.US_SHIPPING5_FIRST_NAME.data,
				MagentoData.US_SHIPPING5_LAST_NAME.data,MagentoData.US_SHIPPING5_STREET0.data,
				MagentoData.US_SHIPPING5_COUNTRY.data, MagentoData.US_SHIPPING5_STATE.data,
				MagentoData.US_SHIPPING5_CITY.data, MagentoData.US_SHIPPING5_ZIP.data,
				MagentoData.US_SHIPPING5_PHONE.data);
		newOrderPage = addBillingShippingAddress(billingAddress,shippingAddress);

		assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculatePercentBasedTax(20));
		newViewInfoPage = submitOrderForVAT();
	}
}
