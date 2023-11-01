package com.vertex.quality.connectors.kibo.tests.flexFields;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.kibo.enums.*;
import com.vertex.quality.connectors.kibo.pages.KiboBackOfficeStorePage;
import com.vertex.quality.connectors.kibo.pages.KiboVertexConnectorPage;
import com.vertex.quality.connectors.kibo.pages.KiboWarehouseCaPage;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.testng.Assert.*;

/**
 * Test cases related to set flex-fields & validate sales order
 *
 * @author Shivam.Soni
 */
public class KiboFlexFieldsTests extends KiboTaxCalculationBaseTest {

    KiboVertexConnectorPage connectorPage;
    KiboWarehouseCaPage warehousePage;
    KiboBackOfficeStorePage maxinePage;

    String[] entity;
    String[] field;

    /**
     * CKIBO-744 Test Case - create Sales order with 1 Flex field and invoice
     */
    @Test(groups = {"kibo_regression"})
    public void salesOrderWithOneFlexFieldTest() {
        try {
            // Go to vertex connector config page
            gotoVertexConfiguration();

            // Enable invoice from vertex connector
            enableOrDisableInvoiceFromKibo(true);

            // Go to flex-fields' menu
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.configurationDialog.gotoFlexFields();

            // Set code-field(s)
            entity = new String[]{OSeriesData.ENTITY_FULFILLMENT_INFO.data};
            field = new String[]{OSeriesData.FIELD_SHIPPING_METHOD_NAME.data};
            connectorPage.configurationDialog.setCodeFields(entity, field);

            // Set numeric-field(s)
            entity = new String[]{OSeriesData.ENTITY_ORDER_DATA.data};
            field = new String[]{OSeriesData.FIELD_CUSTOMER_ACCOUNT_ID.data};
            connectorPage.configurationDialog.setNumericFields(entity, field);

            // Set date-field(s)
            field = new String[]{OSeriesData.FIELD_SUBMITTED_DATE.data};
            connectorPage.configurationDialog.setDateFields(entity, field);

            // Save setting & close config pop-up
            connectorPage.configurationDialog.clickSaveButton();
            connectorPage.configurationDialog.closeConfigAppPopup();

            // Set Ship From Address
            warehousePage = new KiboWarehouseCaPage(driver);
            warehousePage.setWarehouseAddress(KiboWarehouses.WH_WRH_OO1.value, Address.Gettysburg.addressLine1, Address.Gettysburg.city,
                    Address.Gettysburg.state.fullName, Address.Gettysburg.zip5, Address.Gettysburg.country.fullName);

            // Process Order
            maxinePage = navigateToBackOfficeStores();
            selectCustomerAndOpenOrdersDetails(KiboCustomers.Customer1, Address.LosAngeles.addressLine1, Address.LosAngeles.city,
                    Address.LosAngeles.state.fullName, Address.LosAngeles.zip5, Address.LosAngeles.country.fullName);
            maxinePage.orderDetailsDialog.selectProductAndQuantity(KiboProductNames.PRODUCT_TEST_SHOES, KiboTaxRates.KIBO_QUANTITY1.value);
            maxinePage.clickSaveAndSubmitOrderButton();

            // Assertion of Tax on order's amount.
            assertEquals(maxinePage.calculatePercentageBasedTax(9.5), maxinePage.getTaxAmount());

            // Do check payment for the order
            payForTheOrder(String.valueOf(ThreadLocalRandom.current().nextInt(9999)));
            assertTrue(maxinePage.payment.checkIfPaid());

            // Fulfill the order & mark as shipped
            maxinePage.fulfillOrderAndGotoReturn();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Go to vertex connector setting & remove all flex fields
            connectorPage = new KiboVertexConnectorPage(driver);
            connectorPage.refreshPage();
            navigateToVertexConfiguration();
            connectorPage.configurationDialog.gotoFlexFields();
            connectorPage.configurationDialog.removeAllCodeFields();
            connectorPage.configurationDialog.removeAllNumericFields();
            connectorPage.configurationDialog.removeAllDateFields();
            connectorPage.configurationDialog.clickSaveButton();
            connectorPage.configurationDialog.closeConfigAppPopup();
        }
    }
}
