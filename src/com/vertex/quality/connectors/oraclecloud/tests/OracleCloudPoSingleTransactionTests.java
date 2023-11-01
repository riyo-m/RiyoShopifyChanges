package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudCreateInvoicePage;
import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudCreatePurchaseOrderPage;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

/**
 * Container for all PO related single transaction
 * regression tests for Oracle Cloud.
 *
 * @author msalomone
 */
public class OracleCloudPoSingleTransactionTests extends OracleCloudBaseTest {

    final String supplier = "MCC Calif";
    final String validateTextSelect = "Validate Ctrl+Alt+V";
    final String invoiceValidated = "Validated";

    /**
     * Test whether a purchase order can successfully be created with
     * a description containing 240 characters that includes a combination
     * of numbers, letters, and special characters.
     *
     * Jira test case: COERPC-3133
     */
    @Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_po_single" })
    public void createPo240CharDescriptionTest() {

        final String defaultShipToLocation = "VTX_PA";
        String type = "Fixed Price Services";
        String firstDesc = "xL8q1mxcuD9VZZ9nI7sq4jqbPGzLHow2K2V3BuF8g6s1JK*!n4+Xb4yk6aa1FnZ3gfAHJqh4zP4QR9Gb?_H4s3xcFXb98Ofa895ZSWCNA9kkq39I8YD0gnriGqOKa3GF918Xh5GkUH6QXI4zAnh92b2nn8GYY074z37SkSxj28dwnv5s64G69sMVCy4rB8RG79R06Ob5L8h2T2pd5W9w8O767IdH9q135KgGjB1Y3Vm32y7FMhW4u96w7fHt8Sn6q0WlupImlKnqNIcsbgUAWE9EswfmfMsN6i5eKr8J4lErxBa7A666pNcF5L8rH3KZ38I0UZ51eTd87dQu7axF9N3A)(";
        String categoryName = "Phone Support";

        String price = "100.00";
        String total = "100.00";
        String intendedUse = "test1";
        String poChargeAcct = "3211-20-60041-110-0000-0000";
        String transBusCat = "Purchase Transaction";
        String prodType = "Services";

        loadInitialTestPage();

        signInToHomePage();

        OracleCloudCreatePurchaseOrderPage createPoPage = navigateToCreatePurchaseOrdersPage();

        inputCreatePoInfo(createPoPage, null, null, null, supplier,
                defaultShipToLocation);

        createPoPage.addLine();
        createPoPage.inputLineData(0, type, firstDesc, categoryName, null, null, price,
                total, poChargeAcct, transBusCat, prodType, intendedUse);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        createPoPage.clickSaveOrder();
        createPoPage.clickSubmitOrder();

        boolean submissionConfirmed = createPoPage.verifySubConfirmation();

        assertTrue(submissionConfirmed);
    }

    /**
     * Test creates a PO and an invoice matching to the PO before
     * verifying that Oracle successfully saves it
     *
     * Jira test case: COERPC-3134
     */
    @Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_po_single" })
    public void createApInvoiceMatchedWith240CharPoTest() {
        String orderNum = "";
        String invoiceNumber = "";
        String savedHeaderNumber = "";
        String testId = "APMatchPO";
        String amountInput = "100.00";

        final String defaultShipToLocation = "VTX_PA";
        String type = "Fixed Price Services";
        String firstDesc = "xL8q1abcuD9VZZ9nI7sq4jqbPGzLWow2K2V3BuF8g6s1JK*!n4+Xb4yk6aa1FnZ3gfAHJqh4zP4QR9Gb?_H4s3xcFXb98Ofa895ZSWCNA9kkq39I8YD0gnriGqOKa3GF918Xh5GkUH6QXI4zAnh92b2nn8GYY074z37SkSxj28dwnv5s64G69sMVCy4rB8RG79R06Ob5L8h2T2pd5W9w8O767IdH9q135KgGjB1Y3Vm32y7FMhW4u96w7fHt8Sn6q0WlupImlKnqNIcsbgUAWE9EswfmfMsN6i5eKr8J4lErxBa7A666pNcF5L8rH3KZ38I0UZ51eTd87dQu7axF9N3A)(";
        String categoryName = "Phone Support";

        String price = "100.00";
        String total = "100.00";
        String intendedUse = "test1";
        String poChargeAcct = "3211-20-60041-110-0000-0000";
        String transBusCat = "Purchase Transaction";
        String prodType = "Services";

        loadInitialTestPage();

        signInToHomePage();

        OracleCloudCreatePurchaseOrderPage createPoPage = navigateToCreatePurchaseOrdersPage();

        inputCreatePoInfo(createPoPage, null, null, null, supplier,
                defaultShipToLocation);

        createPoPage.addLine();
        createPoPage.inputLineData(0, type, firstDesc, categoryName, null, null, price,
                total, poChargeAcct, transBusCat, prodType, intendedUse);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        orderNum = createPoPage.getPurchaseOrderNumber();
        createPoPage.clickSaveOrder();
        createPoPage.clickSubmitOrder();

        boolean submissionConfirmed = createPoPage.verifySubConfirmation();

        assertTrue(submissionConfirmed);

        signOffPage();

        loadInitialTestPage();

        signInToHomePage();

        OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

        createInvoicePage.editIdentifyingPO(orderNum);
        createInvoicePage.editAmount(amountInput);

        invoiceNumber = generateAndWriteInvoiceNumber(createInvoicePage, testId);
        createInvoicePage.matchInvoiceLines();
        createInvoicePage.editAmount(amountInput);
        createInvoicePage.clickSaveButton();

        savedHeaderNumber = createInvoicePage.getSavedHeader();
        boolean headerConfirmed = savedHeaderNumber.contains(invoiceNumber);
        assertTrue(headerConfirmed);

        String status = validateInvoice(createInvoicePage);
        assertTrue(invoiceValidated.equals(status));
    }

    /**
     * Test creates a PO, adds two lines to it: one line with
     * a large decimal price, and another with a large number of
     * decimals for the qty. Verifies that Oracle successfully
     * saves and submits a PO with these two lines.
     *
     * Jira test case: COERPC-3149
     */
    @Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_po_single" })
    public void createPoWith2LinesTest() {
        final String defaultShipToLocation = "VTX_PA";
        String type = "Goods";
        String firstDesc = "Qty 7 decimals";
        String categoryName = "Phone Support";
        String firstQuantity = "5.6321597";
        String unit = "Ea";
        String firstPrice = "100.00";
        String firstTotal = "563.22";
        String secondDesc = "Unit price 7 decimals";
        String secondQuantity = "100";
        String secondPrice = "5.7489635";
        String secondTotal = "574.90";
        String poChargeAcct = "3211-20-60041-110-0000-0000";
        String transBusCat = "Purchase Transaction";
        String prodType = "Goods";

        loadInitialTestPage();

        signInToHomePage();

        OracleCloudCreatePurchaseOrderPage createPoPage = navigateToCreatePurchaseOrdersPage();

        inputCreatePoInfo(createPoPage, null, null, null, supplier,
                defaultShipToLocation);

        createPoPage.addLine();
        createPoPage.inputLineData(0, type, firstDesc, categoryName, firstQuantity, unit, firstPrice,
                firstTotal, poChargeAcct, transBusCat, prodType, null);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        createPoPage.addLine();
        createPoPage.inputLineData(1, type, secondDesc, categoryName, secondQuantity, unit, secondPrice,
                secondTotal, poChargeAcct, transBusCat, prodType, null);

        createPoPage.clickSaveOrder();
        createPoPage.clickSubmitOrder();

        boolean submissionConfirmed = createPoPage.verifySubConfirmation();

        assertTrue(submissionConfirmed);
    }

    /**
     * Test creates a PO and an invoice matching to the PO before
     * verifying that Oracle successfully saves it
     *
     * Jira test case: COERPC-3150
     */
    @Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_po_single" })
    public void createApInvoiceMatchedWithPoTest() {
        String orderNum = "";
        String invoiceNumber = "";
        String savedHeaderNumber = "";
        String testId = "APMatchPO";
        String amountInput = "1,138.12";

        final String defaultShipToLocation = "VTX_PA";
        String type = "Goods";
        String firstDesc = "Qty 7 decimals";
        String categoryName = "Phone Support";
        String firstQuantity = "5.6321597";
        String unit = "Ea";
        String firstPrice = "100.00";
        String firstTotal = "563.22";
        String secondDesc = "Unit price 7 decimals";
        String secondQuantity = "100";
        String secondPrice = "5.7489635";
        String secondTotal = "574.90";
        String intendedUse = "test1";
        String poChargeAcct = "3211-20-60041-110-0000-0000";
        String transBusCat = "Purchase Transaction";
        String prodType = "Goods";

        loadInitialTestPage();

        signInToHomePage();

        OracleCloudCreatePurchaseOrderPage createPoPage = navigateToCreatePurchaseOrdersPage();

        inputCreatePoInfo(createPoPage, null, null, null, supplier,
                defaultShipToLocation);

        createPoPage.addLine();
        createPoPage.inputLineData(0, type, firstDesc, categoryName, firstQuantity, unit, firstPrice,
                firstTotal, poChargeAcct, transBusCat, prodType, intendedUse);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        createPoPage.addLine();
        createPoPage.inputLineData(1, type, secondDesc, categoryName, secondQuantity, unit, secondPrice,
                secondTotal, poChargeAcct, transBusCat, prodType, intendedUse);

        orderNum = createPoPage.getPurchaseOrderNumber();
        createPoPage.clickSaveOrder();
        createPoPage.clickSubmitOrder();

        boolean submissionConfirmed = createPoPage.verifySubConfirmation();

        assertTrue(submissionConfirmed);

        signOffPage();

        loadInitialTestPage();

        signInToHomePage();

        OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

        createInvoicePage.editIdentifyingPO(orderNum);
        createInvoicePage.editAmount(amountInput);

        invoiceNumber = generateAndWriteInvoiceNumber(createInvoicePage, testId);
        createInvoicePage.matchInvoiceLines();
        createInvoicePage.editAmount(amountInput);
        createInvoicePage.clickSaveButton();

        savedHeaderNumber = createInvoicePage.getSavedHeader();
        boolean headerConfirmed = savedHeaderNumber.contains(invoiceNumber);
        assertTrue(headerConfirmed);
    }

    /**
     * Verifies the creation of a PO with a line item containing
     * decimals for quantity, price, and total
     *
     * Jira test case: COERPC-3136
     */
    @Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_po_single" })
    public void createPoDecimalLineAmtUnitPriceQtyTest() {
        final String defaultShipToLocation = "VTX_PA";
        String type = "Goods";
        String desc = "decimal";
        String categoryName = "Phone Support";
        String quantity = "0.956324";
        String unit = "Ea";
        String price = "1.6353247";
        String total = "1.56";
        String intendedUse = "test1";
        String poChargeAcct = "3211-20-60041-110-0000-0000";
        String transBusCat = "Purchase Transaction";
        String prodType = "Goods";

        loadInitialTestPage();

        signInToHomePage();

        OracleCloudCreatePurchaseOrderPage createPoPage = navigateToCreatePurchaseOrdersPage();

        inputCreatePoInfo(createPoPage, null, null, null, supplier,
                defaultShipToLocation);

        createPoPage.addLine();
        createPoPage.inputLineData(0, type, desc, categoryName, quantity, unit, price,
                total, poChargeAcct, transBusCat, prodType, intendedUse);

        createPoPage.clickSaveOrder();
        createPoPage.clickSubmitOrder();

        boolean submissionConfirmed = createPoPage.verifySubConfirmation();

        assertTrue(submissionConfirmed);
    }

    /**
     * Verifies that a PO with a line item containing decimals for quantity,
     * price, and total can successfully be used to create an AP invoice.
     *
     * Jira test case: COERPC-3147
     */
    @Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_po_single" })
    public void createApInvoiceMatchedPoDecimalLineAmtUnitPriceQtyTest() {
        String orderNum = "";
        String invoiceNumber = "";
        String savedHeaderNumber = "";
        String testId = "APMatchPO";
        String amountInput = "1.56";

        final String defaultShipToLocation = "VTX_PA";
        String type = "Goods";
        String desc = "decimal";
        String categoryName = "Phone Support";
        String quantity = "0.956324";
        String unit = "Ea";
        String price = "1.6353247";
        String total = "1.56";
        String intendedUse = "test1";
        String poChargeAcct = "3211-20-60041-110-0000-0000";
        String transBusCat = "Purchase Transaction";
        String prodType = "Goods";

        loadInitialTestPage();

        signInToHomePage();

        OracleCloudCreatePurchaseOrderPage createPoPage = navigateToCreatePurchaseOrdersPage();

        inputCreatePoInfo(createPoPage, null, null, null, supplier,
                defaultShipToLocation);

        createPoPage.addLine();
        createPoPage.inputLineData(0, type, desc, categoryName, quantity, unit, price,
                total, poChargeAcct, transBusCat, prodType, intendedUse);

        orderNum = createPoPage.getPurchaseOrderNumber();
        createPoPage.clickSaveOrder();
        createPoPage.clickSubmitOrder();

        boolean submissionConfirmed = createPoPage.verifySubConfirmation();

        assertTrue(submissionConfirmed);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        signOffPage();

        loadInitialTestPage();

        signInToHomePage();

        OracleCloudCreateInvoicePage createInvoicePage = navigateToCreateInvoicePage();

        createInvoicePage.editIdentifyingPO(orderNum);
        createInvoicePage.editAmount(amountInput);

        invoiceNumber = generateAndWriteInvoiceNumber(createInvoicePage, testId);
        createInvoicePage.matchInvoiceLines();
        createInvoicePage.editAmount(amountInput);
        createInvoicePage.clickSaveButton();

        savedHeaderNumber = createInvoicePage.getSavedHeader();
        boolean headerConfirmed = savedHeaderNumber.contains(invoiceNumber);
        assertTrue(headerConfirmed);

        String status = validateInvoice(createInvoicePage);
        assertTrue(invoiceValidated.equals(status));
    }

    /**
     * Helper method
     * Validate an unvalidated invoice
     *
     * @param page the create invoice page
     *
     * @return the validation status
     */
    protected String validateInvoice( OracleCloudCreateInvoicePage page )
    {
        WebElement menu = page.clickInvoiceActionsButton();
        try
        {
            page.clickInvoiceActionFromMenu(menu, validateTextSelect);
        }
        catch ( StaleElementReferenceException e )
        {
            menu = page.clickInvoiceActionsButton();
            page.clickInvoiceActionFromMenu(menu, validateTextSelect);
        }

        try {
            page.waitForValidationByText();
        }
        catch (Exception ex) {
            page.waitForValidationByText();
        }

        String validStatus = page.checkValidationStatus();

        return validStatus;
    }
}
