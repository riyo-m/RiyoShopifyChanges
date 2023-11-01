package com.vertex.quality.connectors.dynamics365.finance.tests.canada;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuModule;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelCategory;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelLink;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.testng.Assert.assertTrue;
@Listeners(TestRerunListener.class)
public class DFinanceCanadaAPVatTests extends DFinanceBaseTest {

    /**
     * @TestCase CD365-175
     * @Description - create a purchase order for US to CAN with Invoice
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_AP_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void usToCanadaPurchaseOrderWithInvoiceTest() {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);

        final String itemNumber = "1001";
        final String site = "1";
        final String warehouse = "11";
        final String unitPrice = "12";

        final double expectedTaxAmount = 43.43;

        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

        settingsPage.selectCompany(usmfCompany);

        //Create new Purchase Order with Canadian destination address
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber("Autotest Canada");
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        createPurchaseOrderPage.enterDeliveryAddress("Autotest Canada");
        String purchaseOrderNumber = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrderNumber));
        createPurchaseOrderPage.setSalesOrderTaxGroup(salesTaxGroupVertexAP);
        createPurchaseOrderPage.clickOnLines();
        allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, "5", unitPrice, 0);
        allSalesOrdersSecondPage.fillItemsInfo("L0001","1", "13", "1", "500", 1);

        //Validate Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        //Confirm Purchase order
        createPurchaseOrderPage.navigateToConfirmationPage();
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Add Product Receipt
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Verify sales tax
        createPurchaseOrderPage.clickOnTab("Invoice");
        createPurchaseOrderPage.navigateToInvoicePage();
        createPurchaseOrderPage.clickOnInvoiceTab("Financials");
        createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
        actualSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        Double expectedActualSalesTaxAmount = 43.43;
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedActualSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        allSalesOrdersPage.clickOk();

        //Set invoice number
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);

        //Update Match Status
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Apply Prepayments
        createPurchaseOrderPage.clickOnInvoiceTab("Overview");
        DFinanceApplyPrepaymentsPage applyPrepaymentsPage = createPurchaseOrderPage.applyPrepayments();
        String displayedInvoiceNumber = applyPrepaymentsPage.getInvoiceNumber();
        assertTrue(displayedInvoiceNumber.equalsIgnoreCase(invoiceNumber),
                String.format(expectedInvoiceNumber, invoiceNumber, displayedInvoiceNumber));
        applyPrepaymentsPage.closeApplyPrepayment();

        //Post the invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully("AutoTest Canada", invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        //Verify sales tax amount
        createPurchaseOrderPage.navigateToSalesTaxPage();
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify destination and Total tax amount
        xmlInquiryPage.getDocumentID(purchaseOrderNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<Destination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>1820 Store Street</StreetAddress1>\n" +
                "\t\t\t\t<City>Victoria</City>\n" +
                "\t\t\t\t<MainDivision>BC</MainDivision>\n" +
                "\t\t\t\t<PostalCode>V8T 4R4</PostalCode>\n" +
                "\t\t\t\t<Country>CAN</Country>\n" +
                "\t\t\t</Destination>"));
        assertTrue(response.contains("<TotalTax>4.68</TotalTax>"));
    }

    /**
     * @TestCase CD365-174
     * @Description - create a purchase order for CAN to CAN with Invoice
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_AP_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void canadaToCanadaPurchaseOrderWithInvoiceTest() {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);

        final String itemNumber = "1001";
        final String site = "9";
        final String warehouse = "50";
        final String unitPrice = "12";

        final double expectedTaxAmount = 7.80;

        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

        settingsPage.selectCompany(usmfCompany);

        //Create new Purchase Order with Canadian origin and destination address
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber("Autotest Canada");
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        createPurchaseOrderPage.enterDeliveryAddress("Autotest Canada");
        String purchaseOrderNumber = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrderNumber));
        createPurchaseOrderPage.setSalesOrderTaxGroup(salesTaxGroupVertexAP);
        createPurchaseOrderPage.clickOnLines();
        allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, "5", unitPrice, 0);

        //Validate Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        //Confirm Purchase order
        createPurchaseOrderPage.navigateToConfirmationPage();
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Add Product Receipt
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Verify sales tax
        createPurchaseOrderPage.clickOnTab("Invoice");
        createPurchaseOrderPage.navigateToInvoicePage();
        createPurchaseOrderPage.clickOnInvoiceTab("Financials");
        createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
        actualSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        Double expectedActualSalesTaxAmount = 7.80;
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedActualSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        allSalesOrdersPage.clickOk();

        //Set invoice number
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);

        //Update Match Status
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Apply Prepayments
        createPurchaseOrderPage.clickOnInvoiceTab("Overview");
        DFinanceApplyPrepaymentsPage applyPrepaymentsPage = createPurchaseOrderPage.applyPrepayments();
        String displayedInvoiceNumber = applyPrepaymentsPage.getInvoiceNumber();
        assertTrue(displayedInvoiceNumber.equalsIgnoreCase(invoiceNumber),
                String.format(expectedInvoiceNumber, invoiceNumber, displayedInvoiceNumber));
        applyPrepaymentsPage.closeApplyPrepayment();

        //Post the invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully("AutoTest Canada", invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        //Verify sales tax amount
        createPurchaseOrderPage.navigateToSalesTaxPage();
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify destination and total tax amount
        xmlInquiryPage.getDocumentID(purchaseOrderNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<Destination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>1820 Store Street</StreetAddress1>\n" +
                "\t\t\t\t<City>Victoria</City>\n" +
                "\t\t\t\t<MainDivision>BC</MainDivision>\n" +
                "\t\t\t\t<PostalCode>V8T 4R4</PostalCode>\n" +
                "\t\t\t\t<Country>CAN</Country>\n" +
                "\t\t\t</Destination>"));
        assertTrue(response.contains("<TotalTax>7.8</TotalTax>"));
    }
}
