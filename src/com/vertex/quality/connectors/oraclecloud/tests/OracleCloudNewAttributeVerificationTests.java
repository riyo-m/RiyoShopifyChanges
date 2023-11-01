package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudCreateOrderFieldData;
import com.vertex.quality.connectors.oraclecloud.keywords.api.DataKeywords;
import com.vertex.quality.connectors.oraclecloud.keywords.api.DatabaseKeywords;
import com.vertex.quality.connectors.oraclecloud.pages.*;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;
import com.vertex.quality.connectors.oraclecloud.common.utils.OracleProperties;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;


import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeApiTest;
import static org.testng.Assert.assertTrue;
import static com.vertex.quality.connectors.oraclecloud.tests.Initializer.initializeDbSettings;

public class OracleCloudNewAttributeVerificationTests extends OracleCloudBaseTest {

    private DatabaseKeywords database = new DatabaseKeywords();
    private DataKeywords data = new DataKeywords();
    private static OracleProperties properties = OracleProperties.getOraclePropertiesInstance();

    private final String billToAccount = "57004";

    /*
     * New Attribute verification test for AP Single Transactions
     */
    @Test(groups = { "oerpc_ui", "oerpc_ap_single", "oerpc_new_attrs" })
    public void apNewAttributeVerificationTest( )
    {

        final String testId = "NewAttr";
        final String amountInput = "100.00";
        final String taxRegimeInput = "VERTEX US TAX";
        final String taxNameInput = "STATE";
        final String taxJurisdictionInput = "IDAHO";
        final String busUnitInput = "VTX_US_BU";
        final String supplierInputMccCali = "MCC Calif";
        final String rateNameInputStd = "STD";

        initializeDbSettings();
        loadInitialTestPage();

        signInToHomePage();

        OracleCloudApSingleTransactionTests apAttrVerification = new OracleCloudApSingleTransactionTests();
        OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

        invoiceHeaderRequiredInfo(createInvoicePage, busUnitInput, supplierInputMccCali, null, amountInput,
                null, null, null);

        String invoiceNumber = generateAndWriteInvoiceNumber(createInvoicePage, testId);

        WebElement taxPopup = apAttrVerification.openTransactionTaxPopup(createInvoicePage);
        addTaxRow(createInvoicePage, taxPopup, true);
        editTaxRowInformation(createInvoicePage, taxPopup, rateNameInputStd, null, taxRegimeInput,
                taxNameInput, null, taxJurisdictionInput, amountInput);
        apAttrVerification.closeTransactionTaxPopup(createInvoicePage, taxPopup);

        String status = apAttrVerification.validateInvoiceByText(createInvoicePage);

        assertTrue(apAttrVerification.invoiceValidated.equals(status));

        database.apNewAttributeVerification(invoiceNumber);
    }

    /**
     * New Attribute verification test for AR Single Transactions
     */
    @Test(groups = { "oerpc_ui", "oerpc_ar_single", "oerpc_new_attrs" })
    public void arInvoiceNewAttributeVerificationTest( )
    {
        initializeDbSettings();
        String transactionNumber = "493098";
        String buttonText = "Complete and Review";

        loadInitialTestPage();

        signInToHomePage();

        OracleCloudManageTransactionsPage manageTransactionsPage = navigateToManageTransactionsPage();
        manageTransactionsPage.writeTransNumOperator(transactionNumber);
        manageTransactionsPage.clickSearchButton();
        manageTransactionsPage.findSearchResult(transactionNumber);
        manageTransactionsPage.clickDuplicateIcon();
        manageTransactionsPage.waitForLoadedPage("Create Transaction: Invoice");
        manageTransactionsPage.clickSaveButton();
        String newTransactionNumber = manageTransactionsPage.getTransactionNumber();
        OracleCloudReviewTransactionPage reviewTransactionPage = manageTransactionsPage.clickCompleteButtonOption(
                buttonText);

        database.arNewAttributeVerification(newTransactionNumber);

    }


    /**
     * Create an Order in UI with Line 1 = $1000, ship-to-PA
     *
     * Jira Test Case: COERPC-3223
     */
    @Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_om_single" })
    public void omNewAttributeVerificationTest( )
    {
        initializeApiTest();
        initializeDbSettings();
        final String busUnitInput = "VTX_US_BU";
        final String customer = "MCC Test Customer";
        final String poNumber = "OmCustomerPoTest";
        final String item = "Item1000";
        final String expectedTotal = "1,060.00";

        loadInitialTestPage_ecogdev1();

        signInToHomePage();

        OracleCloudCreateOrderPage createOrderPage = navigateToCreateOrderPage();

        orderHeaderRequiredInfo(createOrderPage,customer,busUnitInput, billToAccount);
        createOrderPage.inputAndCheck(OracleCloudCreateOrderFieldData.PURCHASE_ORDER, poNumber);
        createOrderPage.addItem(item);
        createOrderPage.clickSaveButton();

        String calculatedTotal = createOrderPage.getTotalTransTax();
        String orderNumber = createOrderPage.getOrderNumber();
        properties.setOmOrderNum(orderNumber);
        assertTrue(expectedTotal.equals(calculatedTotal));

        VertexLogger.log("Sales Order Number: "+orderNumber, VertexLogLevel.INFO);
        String transaction_id = data.getOmDataFromTables("OmSingle_results", orderNumber);
        database.omNewAttributeVerification(transaction_id);
    }


    @Test(groups = { "oerpc_ui", "oerpc_po_single", "oerpc_new_attrs" })
    public void poNewAttributeVerificationTest() {
        initializeDbSettings();
        String orderNum = "9186";

        loadInitialTestPage();

        signInToHomePage();

        OracleCloudManagePurchaseOrdersPage managePoPage = navigateToManagePurchaseOrdersPage();

        inputManagePoHeaderInfo(managePoPage, orderNum);

        managePoPage.clickSearchButton();
        managePoPage.duplicateOrder();
        managePoPage.clickSaveOrder();
        String pOrderNumber = managePoPage.getPurchaseOrderNumber();
        managePoPage.clickSubmitOrder();

        boolean submissionConfirmed = managePoPage.verifySubConfirmation();

        assertTrue(submissionConfirmed);

        database.poNewAttributeVerification(pOrderNumber);
    }

    /**
     * Create a purchase requisition in the ERP.
     *
     * COERPC-3132
     */
    @Test(groups = { "oerpc_ui", "oerpc_req_single", "oerpc_new_attrs" })
    public void reqNewAttributeVerificationTest() {
        initializeDbSettings();
        String reqNumber = "1421";

        loadInitialTestPage();

        signInToHomePage();

        OracleCloudManageReqsPage manageReqsPage = navigateToManageReqsPage();

        inputManageReqSearchInfo(manageReqsPage, reqNumber);

        manageReqsPage.clickSearchButton();
        manageReqsPage.duplicateRequisition();
        manageReqsPage.clickSaveRequisition();
        String newreqNumber = manageReqsPage.getRequisitiontionNumber();
        manageReqsPage.clickSubmitRequisition();

        boolean submissionConfirmed = manageReqsPage.verifySubConfirmation();

        assertTrue(submissionConfirmed);

        database.reqNewAttributeVerification(newreqNumber);
    }
}
