package com.vertex.quality.connectors.magento.admin.tests.ProductCodeClass;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminConfigPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminCreateNewOrderPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminOrderViewInfoPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminSalesTaxConfigPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * CMAGM2-938 : Sales - Support Product Code / Class
 * Create a Basic Sales order with Product Class exemption invoice
 * and validate Sales order with Product Class Exemption
 *
 * @author vivek-kumar
 */
public class M2CreateSalesOrderProductClassExemptionTests extends MagentoAdminBaseTest {

    M2AdminConfigPage configPage;
    M2AdminSalesTaxConfigPage newTaxConfigPage;
    M2AdminCreateNewOrderPage newOrderPage;
    M2AdminOrderViewInfoPage newViewInfoPage;
    Map<String, String> billingAddress, shippingAddress;

    /**
     * CMAGM2-934
     * Create a Basic Sales order with Product Class exemption invoice
     */
    @Test(groups = "magento_regression")
    public void checkTaxAmountInProductClassExemptionTest() {
        configPage = setShippingOrigin(Address.Gettysburg.country.fullName, Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.city, Address.Gettysburg.addressLine1);
        newOrderPage = addProductsAddressDetails(MagentoData.QA_EXEMPTED_PHYSICAL_PRODUCT.data, MagentoData.quantity_one.data);
        billingAddress = createBillingAddress(MagentoData.US_SHIPPING2_FIRST_NAME.data,
                MagentoData.US_SHIPPING2_LAST_NAME.data, MagentoData.US_SHIPPING2_STREET0.data,
                MagentoData.US_SHIPPING2_COUNTRY.data, MagentoData.US_SHIPPING2_STATE.data,
                MagentoData.US_SHIPPING2_CITY.data, MagentoData.US_SHIPPING2_ZIP.data,
                MagentoData.US_SHIPPING2_PHONE.data);
        shippingAddress = createShippingAddress(MagentoData.US_SHIPPING2_FIRST_NAME.data,
                MagentoData.US_SHIPPING2_LAST_NAME.data, MagentoData.US_SHIPPING2_STREET0.data,
                MagentoData.US_SHIPPING2_COUNTRY.data, MagentoData.US_SHIPPING2_STATE.data,
                MagentoData.US_SHIPPING2_CITY.data, MagentoData.US_SHIPPING2_ZIP.data,
                MagentoData.US_SHIPPING2_PHONE.data);
        newOrderPage = addBillingShippingAddress(billingAddress, shippingAddress);

        assertEquals(newOrderPage.getTaxFromUI(), newOrderPage.calculateIndividualPercentBasedTax(false, true, 6));
        newViewInfoPage = submitOrderForVAT();
    }
}
