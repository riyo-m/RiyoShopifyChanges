package com.vertex.quality.connectors.workday.tests.base;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.misc.VertexFileUtils;
import com.vertex.quality.connectors.workday.enums.WorkdayData;
import com.vertex.quality.connectors.workday.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.testng.Assert.assertTrue;

/**
 * this class represents all the common methods used in most of the test cases
 * such as logging in to the admin home page and other helper methods
 *
 * @author dPatel
 */

@Test
public abstract class WorkdayBaseTest extends VertexUIBaseTest<WorkdaySignOnPage> {

    protected String WorkdayUrl;
    protected EnvironmentInformation environmentInformation;
    protected EnvironmentCredentials environmentCredentials;
    protected String username;
    protected String password;
    protected String environmentURL;
    protected String cloudTaxURL = WorkdayData.vertexCloudTaxCalcURL.data;
    protected String cloudTrustedID = WorkdayData.vertexCloudTrustedID.data;
    protected String onDemandTaxURL = WorkdayData.vertexOnDemandPremiseTaxCalcURL.data;
    protected String onDemandTrustedId = WorkdayData.vertexOnDemandPremiseTrustedID.data;
    protected String xmlFile = WorkdayData.xmlFileFullPath.data;
    protected String batchQuoteInt = WorkdayData.batchQuoteIntegration.data;
    protected String batchPostInt = WorkdayData.batchPostIntegration.data;
    protected String invLoad = WorkdayData.vtxinvoiceLoadIntegration.data;
    protected String cleanupInt = WorkdayData.vtxCleanupSimulator.data;
    protected String custInvInt = WorkdayData.vtxCustomerInvoiceIntegration.data;
    protected String supInvInt = WorkdayData.vtxSupplierInvoiceIntegration.data;
    protected String preProcessInt = WorkdayData.vtxpreProcessIntegration.data;
    protected String intfailedMessage = WorkdayData.failedIntegrationForCustomerInvoice.data;
    protected String intFailedAdjMessage = WorkdayData.failedIntegrationForCustomerInvoiceAdj.data;
    protected String failedTaxCalcVal = WorkdayData.failedtaxCalcValidation.data;
    protected String invalidTaxCalcURLErrorMessage = WorkdayData.vertexInvalidTaxCalcURLErrorMessage.data;
    protected String invalidTrustedID = WorkdayData.vertexInvalidTrustedID.data;
    protected String invalidTrustedIDErrorMessage = WorkdayData.vertexInvalidTrustedIDErrorMessage.data;
    protected String invalidSupplierInvoiceTrustedIDErrorMessage = WorkdayData.vertexInvalidSupplierInvoiceTrustedIDErrorMessage.data;
    protected String invalidSupplierInvoiceTaxCalcURLErrorMessage = WorkdayData.vertexInvalidSupplierInvoiceTaxCalcURLErrorMessage.data;
    protected String invalidTaxCalcURL = WorkdayData.vertexInvalidTaxCalcURL.data;
    protected String alcoholSwab = WorkdayData.alcoholSwabs.data;
    protected String chairs = WorkdayData.chairs.data;
    protected String laptopPowerAdaptor = WorkdayData.laptopPowerAdaptor.data;
    protected String publicity = WorkdayData.publicity.data;
    protected String binderClips = WorkdayData.binderClips.data;
    protected String alcoholWipePads = WorkdayData.alcoholWipePads.data;
    protected String iPhone = WorkdayData.iPhone.data;
    protected String hardDrive = WorkdayData.hardDrive.data;
    protected String spectre = WorkdayData.spectre.data;
    protected String americanElectric = WorkdayData.americanElectricPower.data;
    protected String selfAssessedTax = WorkdayData.calculateSelfAssessedTax.data;
    protected String enterTaxDue = WorkdayData.enterTaxDue.data;
    protected String calcTaxDue = WorkdayData.calcTaxDUe.data;
    protected String banners = WorkdayData.bannersAndDisplay.data;
    protected String failedTaxCalcAdjVal = WorkdayData.failedtaxCalcAdjValidation.data;
    protected String reuestXML = WorkdayData.vertexExtractedrequestXMLtxt.data;
    protected String extractedXMLtxt = WorkdayData.vertexExtractedrequestXMLtxt.data;
    protected String expectedRequestXML = WorkdayData.expectedREquestXML.data;
    protected String expectedUpdatedXML = WorkdayData.expectedUpdatedRequestXML.data;
    String[] eibFields = new String[]{"Worktag Split Template", "Locked in Workday", "Payment Practices"};
    String[] eibFieldsValue = new String[]{"DP_Sample", "Yes", "Yes"};
    String[] eibCurrencyFields = new String[]{"Currency Rate Type Override", "Currency Rate Date Override", "Currency Rate Lookup Override"};
    String[] eibCurrencyFieldsValue = new String[]{"Average", "05/18/2020", "0.106365"};
    protected By onProcessPage = By.xpath("(//div[contains(@data-automation-label,'Integration Process: VTX')])[1]");

    /**
     * Used to be able to set environment descriptor from the child base test
     *
     * @return the environment descriptor based on the base test
     */
    protected DBEnvironmentDescriptors getEnvironmentDescriptor() {
        return DBEnvironmentDescriptors.WORKDAY;
    }

    /**
     * Gets credentials for the connector from the database
     */
    @Override
    public WorkdaySignOnPage loadInitialTestPage() {
        try {
            environmentInformation = SQLConnection.getEnvironmentInformation(DBConnectorNames.WORKDAY,
                    DBEnvironmentNames.QA, getEnvironmentDescriptor());
            environmentCredentials = SQLConnection.getEnvironmentCredentials(environmentInformation);
            WorkdayUrl = environmentInformation.getEnvironmentUrl();
            username = environmentCredentials.getUsername();
            password = environmentCredentials.getPassword();
            environmentURL = environmentInformation.getEnvironmentUrl();
            setWindowSize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loadAdminSite();
    }

    /**
     * Set test run's window dimensions. This prevents test failure whenever
     * the test runs in headless mode
     */
    protected void setWindowSize() {
        Dimension d = new Dimension(1600, 900);
        driver.manage().window().setSize(d);
        return;
    }

    /**
     * loads the login page of workday's portal site
     *
     * @return a representation of this site's login screen
     */
    protected WorkdaySignOnPage loadAdminSite() {

        WorkdaySignOnPage signOnPage;
        String url = this.WorkdayUrl;
        driver.get(url);
        VertexLogger.log(String.format("Workday URL - %s", url), VertexLogLevel.DEBUG);
        signOnPage = new WorkdaySignOnPage(driver);
        return signOnPage;
    }

    /**
     * does the sign in to the test environment
     *
     * @return new instance of the admin home page class
     */
    protected WorkdayHomePage signInToHomePage(final WorkdaySignOnPage signOnPage) {
        VertexLogger.log("Signing in to workday Site...");
        return signOnPage.loginAsUser(username, password);
    }

    /**
     * It fills out customer invoice form
     *
     * @return workdayCustomerInvoicePage object
     */
    protected WorkdayCustomerInvoicePage fillCustomerInvoiceForm(String company, String billToCust, String product, int qty) {
        WorkdayCustomerInvoicePage customerInvoice = signInToHomePage(testStartPage).getCustomerInvoiceForm();
        customerInvoice.enterCompanyInfo(company);
        customerInvoice.enterBillToCustomer(billToCust);
        customerInvoice.enterSalesItem(product);
        customerInvoice.changeInQuantity(qty);
        customerInvoice.uncheckDeferredRevenueIfChecked();
        return customerInvoice;
    }

    /**
     * It fills out customer invoice form
     *
     * @return workdayCustomerInvoicePage object
     */
    protected WorkdayCustomerInvoicePage fillCustomerInvoiceFormSingleLineWithAddress(String company, String billToCust, String[] product, int qty, String address) {
        WorkdayHomePage page = new WorkdayHomePage(driver);
        WorkdayCustomerInvoicePage customerInvoice = page.getCustomerInvoiceForm();
        customerInvoice.enterCompanyInfo(company);
        customerInvoice.enterBillToCustomer(billToCust);
        customerInvoice.enterSalesItem(product[0]);
        customerInvoice.changeInQuantity(qty);
        customerInvoice.uncheckDeferredRevenueIfChecked();
        if (address != null) {
            customerInvoice.changeShippingAddress(address);
        }
        return customerInvoice;
    }

    /**
     * It fills out customer invoice form
     *
     * @return workdayCustomerInvoicePage object
     */
    protected WorkdayCustomerInvoicePage fillCustomerInvoiceFormForUpdatedAttributes(String company, String billToCust, String product, int qty) {
        loadInitialTestPage();
        WorkdayHomePage page = new WorkdayHomePage(driver);
        WorkdayCustomerInvoicePage customerInvoice = page.getCustomerInvoiceForm();
        customerInvoice.enterCompanyInfo(company);
        customerInvoice.enterBillToCustomer(billToCust);
        customerInvoice.enterSalesItem(product);
        customerInvoice.updateQuantity(qty);
        customerInvoice.uncheckDeferredRevenueIfChecked();
        return customerInvoice;
    }

    /**
     * It fills out customer invoice form
     *
     * @return workdayCustomerInvoicePage object
     */
    protected WorkdayCustomerInvoicePage fillCustomerInvoiceFormForUpdatedAddressAttributes(String company, String billToCust, String product, int qty, String address) {
        loadInitialTestPage();
        WorkdayHomePage page = new WorkdayHomePage(driver);
        WorkdayCustomerInvoicePage customerInvoice = page.getCustomerInvoiceForm();
        customerInvoice.enterCompanyInfo(company);
        customerInvoice.enterBillToCustomer(billToCust);
        customerInvoice.enterSalesItem(product);
        customerInvoice.changeInQuantity(qty);
        customerInvoice.uncheckDeferredRevenueIfChecked();
        if (address != null) {
            customerInvoice.changeShippingAddress(address);
        }
        return customerInvoice;
    }

    /**
     * It fills out customer invoice form
     *
     * @return workdayCustomerInvoicePage object
     * @author Vishwa
     */
    protected WorkdayCustomerInvoicePage fillCustomerInvoiceFormMultipleProducts(String company, String billToCust, String[] product, int qty, String address) {
        loadInitialTestPage();
        WorkdayHomePage page = new WorkdayHomePage(driver);
        WorkdayCustomerInvoicePage customerInvoice = page.getCustomerInvoiceForm();
        customerInvoice.enterCompanyInfo(company);
        customerInvoice.enterBillToCustomer(billToCust);
        customerInvoice.enterSalesItem(product[0]);
        customerInvoice.changeInQuantity(qty);
        customerInvoice.uncheckDeferredRevenueIfChecked();
        if (address != null) {
            customerInvoice.changeShippingAddress(address);
        }
        customerInvoice.clickOnMaximizeSalesScreen();
        for (int i = 1; i < product.length; i++) {
            customerInvoice.addMoreProducts(product[i], i + 1);
        }
        page.waitForPageLoad();
        customerInvoice.clickOnMinimizeSalesScreen();
        return customerInvoice;
    }

    /**
     * It fills out Supplier invoice form
     *
     * @return WorkdaySupplierInvoicePage page object
     */
    protected WorkdaySupplierInvoicePage fillSupplierInvoiceForm(String company, String supplier, String defaultTaxOption, String item, String headerTax) {
        WorkdaySupplierInvoicePage supplierInvoice = signInToHomePage(testStartPage).getSupplierInvoiceForm();
        supplierInvoice.enterCompanyInfo(company);
        supplierInvoice.enterSupplier(supplier);
        if (!headerTax.equals("0")) {
            supplierInvoice.enterHeaderTaxAmount(headerTax);
        }
        supplierInvoice.selectDefaultTaxOption(defaultTaxOption);
        supplierInvoice.enterSalesItem(item, false);
        return supplierInvoice;
    }

    /**
     * It fills out the Supplier Invoice Form with no header level Ship-to Address
     *
     * @return WorkdaySupplierInvoicePage page object
     */
    protected WorkdaySupplierInvoicePage fillSupplierInvoiceFormNoHeaderShipTo(String company, String supplier, String defaultTaxOption, String item, String headerTax, int qty, String address) {
        // need to add a parameter to hold addresses used in items
        WorkdaySupplierInvoicePage supplierInvoice = signInToHomePage(testStartPage).getSupplierInvoiceForm();
        supplierInvoice.enterCompanyInfo(company);
        supplierInvoice.enterSupplier(supplier);
        supplierInvoice.clearHeaderLevelShippingAddress();
        if (!headerTax.equals("0")) {
            supplierInvoice.enterHeaderTaxAmount(headerTax);
        }
        supplierInvoice.selectDefaultTaxOption(defaultTaxOption);
        supplierInvoice.enterSalesItem(item, false);
        return supplierInvoice;
    }

    /**
     * It fills out the Supplier Invoice Form with no header level Ship-to Address
     *
     * @return WorkdaySupplierInvoicePage page object
     */
    protected WorkdaySupplierInvoicePage fillSupplierInvoiceFormChangeHeaderShipTo(String company, String supplier, String defaultTaxOption, String item, String headerTax, String address) {
        // need to add a parameter to hold addresses used in items
        WorkdayHomePage page = new WorkdayHomePage(driver);
        WorkdaySupplierInvoicePage supplierInvoice = page.getSupplierInvoiceForm();
        supplierInvoice.enterCompanyInfo(company);
        supplierInvoice.enterSupplier(supplier);
        supplierInvoice.clearHeaderLevelAndChangeShippingAddress(address);
        if (!headerTax.equals("0")) {
            supplierInvoice.enterHeaderTaxAmount(headerTax);
        }
        supplierInvoice.selectDefaultTaxOption(defaultTaxOption);
        supplierInvoice.enterSalesItem(item, false);
        return supplierInvoice;
    }

    /**
     * It fills out the Supplier Invoice Form with no header level Ship-to Address
     *
     * @return WorkdaySupplierInvoicePage page object
     */
    protected WorkdaySupplierInvoicePage fillSupplierInvoiceFormWithControlTotal(String company, String supplier, String defaultTaxOption, String item, String headerTax, int qty, String controlTotal) {
        // need to add a parameter to hold addresses used in items
        WorkdaySupplierInvoicePage supplierInvoice = signInToHomePage(testStartPage).getSupplierInvoiceForm();
        supplierInvoice.enterCompanyInfo(company);
        supplierInvoice.enterSupplier(supplier);
        if (!headerTax.equals("0")) {
            supplierInvoice.enterHeaderTaxAmount(headerTax);
        }
        supplierInvoice.selectDefaultTaxOption(defaultTaxOption);
        supplierInvoice.enterControlTotal(controlTotal);
        supplierInvoice.enterSalesItem(item, false);
        supplierInvoice.changeInQuantity(qty);
        return supplierInvoice;
    }

    /**
     * It fills out the Supplier Invoice Form and adds additional fields
     *
     * @return WorkdaySupplierInvoicePage page object
     */
    protected WorkdaySupplierInvoicePage fillSupplierInvoiceFormWithAdditionalFields(String company, String supplier, String defaultTaxOption, String item, String headerTax, int qty, String[] additionalFields) {
        // need to add a parameter to hold addresses used in items
        WorkdaySupplierInvoicePage supplierInvoice = signInToHomePage(testStartPage).getSupplierInvoiceForm();
        supplierInvoice.enterCompanyInfo(company);
        supplierInvoice.enterSupplier(supplier);
        if (!headerTax.equals("0")) {
            supplierInvoice.enterHeaderTaxAmount(headerTax);
        }
        supplierInvoice.selectDefaultTaxOption(defaultTaxOption);
        supplierInvoice.enterSalesItem(item, false);
        supplierInvoice.changeInQuantity(qty);
        supplierInvoice.clickAdditionalFieldsTab();
        supplierInvoice.enterAdditionalFields(additionalFields);
        return supplierInvoice;
    }

    /**
     * It fills out Supplier invoice form for updated attributes
     *
     * @return WorkdaySupplierInvoicePage page object
     */
    protected WorkdaySupplierInvoicePage fillSupplierInvoiceFormForUpdatedAttributes(String company, String supplier, String defaultTaxOption, String item, int qty, String headerTax) {
        loadInitialTestPage();
        WorkdayHomePage page = new WorkdayHomePage(driver);
        WorkdaySupplierInvoicePage supplierInvoice = page.getSupplierInvoiceForm();
        supplierInvoice.enterCompanyInfo(company);
        supplierInvoice.enterSupplier(supplier);
        supplierInvoice.selectDefaultTaxOption(defaultTaxOption);
        if (!headerTax.equals("0")) {
            supplierInvoice.enterHeaderTaxAmount(headerTax);
        }
        supplierInvoice.enterSalesItem(item, false);
        supplierInvoice.changeInQuantity(qty);
        return supplierInvoice;
    }

    /**
     * It fills out Supplier invoice form for updated Address
     *
     * @return WorkdaySupplierInvoicePage page object
     */
    protected WorkdaySupplierInvoicePage fillSupplierInvoiceFormForUpdatedAttributes(String company, String supplier, String defaultTaxOption, String[] item, int qty, String headerTax, String address) {
        loadInitialTestPage();
        WorkdayHomePage workDayHomePage = new WorkdayHomePage(driver);

        WorkdaySupplierInvoicePage supplierInvoice = workDayHomePage.getSupplierInvoiceForm();
        supplierInvoice.enterCompanyInfo(company);
        supplierInvoice.enterSupplier(supplier);
        supplierInvoice.selectDefaultTaxOption(defaultTaxOption);
        if (!headerTax.equals("0.00")) {
            supplierInvoice.enterHeaderTaxAmount(headerTax);
        }
        supplierInvoice.enterSalesItem(item[0], false);
        if (address != null) {
            supplierInvoice.changeShippingAddress(address);
        }
        supplierInvoice.changeInQuantity(qty);
        for (int i = 1; i < item.length; i++) {
            supplierInvoice.addMoreInvoiceLineforCanada(item[i], i + 1);
        }
        return supplierInvoice;
    }

    /**
     * It fills out Supplier invoice adjustment form
     *
     * @return WorkdaySupplierInvoicePage page object
     */
    protected WorkdaySupplierInvoicePage workdaySupplierAdjustmentForm(String supplier, String defaultTaxOption, String item, String invNo, int updatedUnitCost) {
        loadInitialTestPage();
        WorkdayHomePage page = new WorkdayHomePage(driver);
        WorkdaySupplierInvoicePage supplierInvoice = page.getSupplierInvoiceAdjustmentForm();
        supplierInvoice.enterCompanyInfo(spectre);
        supplierInvoice.enterSupplier(supplier);
        supplierInvoice.selectDefaultTaxOption(defaultTaxOption);
        supplierInvoice.enterValuesToTextBox("Reference Invoice", invNo, true);
        supplierInvoice.enterValuesToTextBox("Adjustment Reason", "Price Adjustment", true);
        supplierInvoice.enterSalesItem(item, true);
        supplierInvoice.enterUnitCost(updatedUnitCost);
        return supplierInvoice;
    }

    /**
     * It fills the Customer Invoice Adjustment form
     *
     * @param typesofInvoiceAction Invoice action
     * @param updatedQty           Updated Quantity
     * @return WorkdayCustomerInvoicePage object
     */
    protected WorkdayCustomerInvoicePage fillCustomerInvoiceAdjForm(String typesofInvoiceAction, int updatedQty, boolean isFullAdjustment) {
        WorkdayInvoiceReviewPage afterSubmit = new WorkdayInvoiceReviewPage(driver);
        afterSubmit.clickOnNewlyCreatedCustInvAdj();
        afterSubmit.clickonCustomerInvoiceAction(typesofInvoiceAction);
        afterSubmit.enterAdjReason();
        WorkdayCustomerInvoicePage updateCustomerInvoice = afterSubmit.clickonOK();
        if (!isFullAdjustment) {
            updateCustomerInvoice.updateQuantity(updatedQty);
        }
        return updateCustomerInvoice;
    }

    /**
     * It fills the Customer Invoice Adjustment form for updated Price
     *
     * @param typesofInvoiceAction Invoice action
     * @param updatedPrice         Updated Price
     * @return WorkdayCustomerInvoicePage object
     */
    protected WorkdayCustomerInvoicePage fillCustomerInvoiceAdjFormForUpdatedPrice(String typesofInvoiceAction, String updatedPrice) {
        WorkdayInvoiceReviewPage afterSubmit = new WorkdayInvoiceReviewPage(driver);
        afterSubmit.clickOnNewlyCreatedCustInvAdj();
        afterSubmit.clickonCustomerInvoiceAction(typesofInvoiceAction);
        afterSubmit.enterAdjReason();
        WorkdayCustomerInvoicePage updateCustomerInvoice = afterSubmit.clickonOK();
        updateCustomerInvoice.changeInUnitPriceForPartialAdj(Integer.parseInt(updatedPrice));
        return updateCustomerInvoice;
    }

    /**
     * It fills the Customer Invoice Debit Adjustment form
     *
     * @param typesOfInvoiceAction Invoice action
     * @param updatedQty           Updated Quantity
     * @return WorkdayCustomerInvoicePage object
     */
    protected WorkdayCustomerInvoicePage fillCustomerInvoiceDebitAdjForm(String typesOfInvoiceAction, int updatedQty) {
        WorkdayInvoiceReviewPage afterSubmit = new WorkdayInvoiceReviewPage(driver);
        afterSubmit.clickOnNewlyCreatedCustInvAdj();
        afterSubmit.clickonCustomerInvoiceAction(typesOfInvoiceAction);
        afterSubmit.clickOnCreditOrDebit(true);
        afterSubmit.enterAdjReason();
        WorkdayCustomerInvoicePage updateCustomerInvoice = afterSubmit.clickonOK();
        updateCustomerInvoice.updateQuantity(updatedQty);
        return updateCustomerInvoice;
    }

    /**
     * It fills the Customer Invoice Change form
     *
     * @param updatedQty Updated Quantity
     * @return WorkdayCustomerInvoicePage object
     */
    protected WorkdayCustomerInvoicePage fillCustomerInvoiceChangeForm(int updatedQty) {
        WorkdayInvoiceReviewPage afterSubmit = new WorkdayInvoiceReviewPage(driver);
        afterSubmit.clickonCustomerInvoiceAction("change");
        WorkdayCustomerInvoicePage updateCustomerInvoice = new WorkdayCustomerInvoicePage(driver);
        updateCustomerInvoice.updateQuantity(updatedQty);
        updateCustomerInvoice.clickOnMaximizeSalesScreen();
        updateCustomerInvoice.clickOnMinimizeSalesScreen();
        updateCustomerInvoice.uncheckDeferredRevenueIfChecked();
        return updateCustomerInvoice;
    }

    /**
     * It fills the Customer Invoice Change form
     *
     * @param updatedQty Updated Quantity
     * @return WorkdayCustomerInvoicePage object
     */
    protected WorkdaySupplierInvoicePage fillSupplierInvoiceChangeForm(int updatedQty) {
        WorkdayInvoiceReviewPage afterSubmit = new WorkdayInvoiceReviewPage(driver);
        afterSubmit.clickonSupplierInvoiceAction("change");
        WorkdaySupplierInvoicePage updateSupplierInvoice = new WorkdaySupplierInvoicePage(driver);
        updateSupplierInvoice.changeInQuantity(updatedQty);
        return updateSupplierInvoice;
    }

    /**
     * It fills the Supplier Invoice Change Form and takes in a String parameter that will be the new tax option
     *
     * @param updatedTaxOption Updated Tax Option
     * @return WorkdaySupplerInvoicePage object
     */
    protected WorkdaySupplierInvoicePage fillSupplierInvoiceChangeFormForUpdatedTaxOption(String updatedTaxOption) {
        WorkdayInvoiceReviewPage afterSubmit = new WorkdayInvoiceReviewPage(driver);
        afterSubmit.clickonSupplierInvoiceAction("change");
        WorkdaySupplierInvoicePage updateSupplierInvoice = new WorkdaySupplierInvoicePage(driver);
        updateSupplierInvoice.selectDefaultTaxOption(updatedTaxOption);
        return updateSupplierInvoice;
    }

    /**
     * It fills the Customer Invoice Change form
     *
     * @param updatedQty Updated Quantity
     * @return WorkdayCustomerInvoicePage object
     */
    protected WorkdaySupplierInvoicePage fillSupplierInvoiceAdjustmentForm(int updatedQty, boolean isAdjDown, boolean isSameDate) {
        WorkdayInvoiceReviewPage afterSubmit = new WorkdayInvoiceReviewPage(driver);
        afterSubmit.clickonSupplierInvoiceAction("adjustment");
        WorkdaySupplierInvoicePage updateSupplierInvoice = new WorkdaySupplierInvoicePage(driver);
        updateSupplierInvoice.enterAdjReason();
        if (!isSameDate) {
            updateSupplierInvoice.changeAdjDate();
        }
        if (!isAdjDown) {
            updateSupplierInvoice.clickOnIncreaseLiability();
        }
        updateSupplierInvoice.selectLineItemFromAdjustmentPage();
        updateSupplierInvoice.clickSaveAndContinue();
        updateSupplierInvoice.changeInQuantity(updatedQty);
        return updateSupplierInvoice;
    }

    /**
     * It verifies that integration is completed by verifying successful Submit, third party tax calculation status
     * and "Successful" Overall status
     *
     * @return true if completed
     */
    protected Boolean verifyIntegrationCompleted(String typesOfInvoice) {
        boolean integCompleted = false;
        WorkdayInvoiceReviewPage invoiceReview = new WorkdayInvoiceReviewPage(driver);
        WorkdayCustomerInvoicePage custInv = new WorkdayCustomerInvoicePage(driver);
        if (typesOfInvoice.equals("Customer")) {
            custInv.navigateToCustomerInvoicePage();
        }
        if (invoiceReview.verifySubmit() && invoiceReview.integrationCompletedVerification(typesOfInvoice) && invoiceReview.verifyThirdPartyTaxCalculationStatus()) {
            integCompleted = true;
        }
        return integCompleted;
    }

    /**
     * It verifies that Supplier Invoice integration is completed by verifying successful Submit, third party tax calculation status
     * and "Successful" Overall status
     *
     * @return true if completed
     */
    protected Boolean verifySupplierInvoiceIntegrationCompleted() {
        boolean integCompleted = false;
        WorkdayInvoiceReviewPage invoiceReview = new WorkdayInvoiceReviewPage(driver);
        WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);
        WorkdaySupplierInvoicePage supplierInvoice = new WorkdaySupplierInvoicePage(driver);
        supplierInvoice.navigateToSupplierInvoicePage();
        if (invoiceReview.verifySubmit() && invoiceReview.integrationCompletedVerification("Supplier")) {
            invoiceReview.clickonNewlyCreatedSupplierInvoice();
            supplierInvoiceReviewPage.clickOnTaxTab();
            if (supplierInvoiceReviewPage.verifyThirdPartyIntegrationStatus()) {
                integCompleted = true;
            }
            supplierInvoiceReviewPage.clickOnInvoiceAdjustmentLinesTab();
        }
        return integCompleted;
    }

    /**
     * It gets the file name according to the current date for example on 3/24/2020 it will get
     * "Import_Customer_Invoice_100_5lines_Jenkins_3242020.xml
     *
     * @return File name that are already uploaded in Jenkins execution VM according to date
     */
    public String getTodaysFileName() {
        DateFormat dateFormat = new SimpleDateFormat("Mddyyyy");
        Date date = new Date();
        return "Import_Customer_Invoice_100_5lines_Jenkins_" + dateFormat.format(date);
    }

    /**
     * It launch "VtxCustomerInvoiceLoad" and uploads the file from "C:\\upload" folder
     * Files are already uploaded in Test Execution VM according to Date
     * and wait till the integration finish (Successful or fail)
     *
     * @return true if completed
     */
    public boolean launchCustomerInvoiceLoad(boolean isCloud) {

        WorkdayLaunchIntegrationPage launchIntegPage;
        if (isCloud) {
            launchIntegPage = signInToHomePage(testStartPage).getLaunchIntegrationPage();
        } else {
            loadInitialTestPage();
            WorkdayHomePage page = new WorkdayHomePage(driver);
            launchIntegPage = page.getLaunchIntegrationPage();
        }
        launchIntegPage.enterIntegration(invLoad);
        launchIntegPage.clickOK(false);
        launchIntegPage.uploadEIBFile("batch");
        launchIntegPage.clickOK(false);
        launchIntegPage.clickOK(true);
        boolean isIntegCompleted = launchIntegPage.waitTillBackGroundProcessIsDone(true);
        VertexLogger.log("Total Processing time for " + invLoad + " is:" + launchIntegPage.getProcessingTime());
        return isIntegCompleted;
    }

    /**
     * It verifies invoiceLoad by running "BatchQuote" report and getting the row count (Invoices Count)
     *
     * @param expectedInv expected number of Invoices
     * @return true if verified
     */
    public boolean verifyInvoiceLoad(int expectedInv) {

        loadInitialTestPage();
        WorkdayHomePage page = new WorkdayHomePage(driver);
        WorkdayInvoiceReportPage invoiceReportPage = page.getBatchQuoteReportPage();
        invoiceReportPage.enterCompanyName("Spectre, Inc.");
        invoiceReportPage.clickOk();
        boolean isVerified = invoiceReportPage.verifyRowCount(Integer.toString(expectedInv));
        String firstInv = invoiceReportPage.getFirstInvoiceNumber(true);
        String lastInv = invoiceReportPage.getLastInvoiceNumber(firstInv, expectedInv);
        VertexLogger.log("Loaded Invoice Range :" + firstInv + " - " + lastInv);
        return isVerified;
    }

    /**
     * It launch "VTX:BatchQuoteCustomerInvoiceBP_20" and wait till the integration finish (Successful or fail)
     *
     * @return true if completed without any errors
     */
    public boolean launchBatchQuoteIntegration() {

        loadInitialTestPage();
        WorkdayHomePage page = new WorkdayHomePage(driver);
        WorkdayLaunchIntegrationPage launchIntegPage = page.getLaunchIntegrationPage();
        launchIntegPage.enterIntegration(batchQuoteInt);
        launchIntegPage.clickOK(false);
        launchIntegPage.enterCompanyInfo("Spectre, Inc.");
        launchIntegPage.clickOK(false);
        boolean isIntegCompleted = launchIntegPage.waitTillBackGroundProcessIsDone(false);
        VertexLogger.log("Total Processing time for " + batchQuoteInt + " is:" + launchIntegPage.getProcessingTime());
        return isIntegCompleted;
    }

    /**
     * It verifies "BatchQuote" by running "BatchPost" report and getting the row count (Invoices Count)
     *
     * @param expectedInv expected number of Invoices
     * @return true if verified
     */
    public boolean verifyBatchQuoteIntegration(int expectedInv) {

        loadInitialTestPage();
        WorkdayHomePage page = new WorkdayHomePage(driver);
        WorkdayInvoiceReportPage invoiceReportPage = page.getBatchPostReportPage();
        invoiceReportPage.enterCompanyName("Spectre, Inc.");
        invoiceReportPage.enterAfterBeforeDate();
        invoiceReportPage.clickOk();
        boolean isVerified = invoiceReportPage.verifyRowCount(Integer.toString(expectedInv));
        String firstInv = invoiceReportPage.getFirstInvoiceNumber(false);
        String lastInv = invoiceReportPage.getLastInvoiceNumber(firstInv, expectedInv);
        VertexLogger.log("Quoted Invoice Range :" + firstInv + " - " + lastInv);
        return isVerified;
    }

    /**
     * It launch "VTX:BatchPostCustomerInvoiceBP_20" and wait till the integration finish (Successful or fail)
     *
     * @return true if completed without any errors
     */
    public boolean launchBatchPostIntegration() {

        loadInitialTestPage();
        WorkdayHomePage page = new WorkdayHomePage(driver);
        WorkdayLaunchIntegrationPage launchIntegPage = page.getLaunchIntegrationPage();
        launchIntegPage.enterIntegration(batchPostInt);
        launchIntegPage.clickOK(false);
        launchIntegPage.enterCompanyInfo("Spectre, Inc.");
        launchIntegPage.clickOK(false);
        boolean isCompleted = launchIntegPage.waitTillBackGroundProcessIsDone(false);
        VertexLogger.log("Total Processing time for " + batchPostInt + " is:" + launchIntegPage.getProcessingTime());
        return isCompleted;
    }

    /**
     * It verifies "BatchPost" by finding the invoices and checking their custom object for Posted flag
     * It also verifies the tax amount for that invoice
     *
     * @param invNo     invoice number that will be verified
     * @param taxAmount expected tax amount
     * @return true if both posted flag and tax amount are verified
     */
    public boolean verifyPostedFlagStatusAndTaxAMountOfInvoice(String invNo, String taxAmount) {
        loadInitialTestPage();
        boolean isVerified = false;
        String taxAmountActual;
        WorkdayHomePage page = new WorkdayHomePage(driver);
        WorkdayFindInvoicePage findInvPage = page.getFindInvoicePage();
        findInvPage.enterInvoiceNumber(invNo);
        WorkdayInvoiceReviewPage invoiceReviewPage = findInvPage.clickOk(invNo);
        invoiceReviewPage.clickOnCustomerInvoiceLink();
        taxAmountActual = invoiceReviewPage.getValuesFromTaxAmount();
        if (taxAmountActual.equals(taxAmount) && invoiceReviewPage.verifycustomObjectStatus()) {
            isVerified = true;
        } else {
            VertexLogger.log("Validation Failed for Processed Invoice :" + invNo + ", Actual Tax Amount:" + taxAmountActual);
        }
        return isVerified;

    }

    /**
     * It gets the three invoices in a array from the report : First Invoice, Last Invoice and In-between Invoice
     *
     * @param expectedInv Number of Invoice Expected
     * @return Three Invoices array
     */
    public String[] getRangeOfInvoices(int expectedInv) {
        WorkdayInvoiceReportPage report = new WorkdayInvoiceReportPage(driver);
        String firstInv = report.getFirstInvoiceNumber(true);
        String lastInv = report.getLastInvoiceNumber(firstInv, expectedInv);
        String inBetweenInv = report.getInbetweenInvoice(Integer.parseInt(firstInv), expectedInv);
        return new String[]{firstInv, lastInv, inBetweenInv};
    }

    /**
     * It launches "CleanupBatchQuoteSimulator" which clears the custom object and wait till it finishes
     * this is the only for testing purpose, It will not be used in production
     *
     * @param startInv Starting Invoice
     * @param endInv   Ending Invoice
     * @return true if completed successfully
     */
    public boolean launchCleanupIntegration(String startInv, String endInv) {
        loadInitialTestPage();
        WorkdayHomePage page = new WorkdayHomePage(driver);
        WorkdayLaunchIntegrationPage launchIntegPage = page.getLaunchIntegrationPage();
        launchIntegPage.enterIntegration(cleanupInt);
        launchIntegPage.clickOK(false);
        launchIntegPage.enterCompanyInfo("Spectre, Inc.");
        launchIntegPage.enterBeginningInv(startInv);
        launchIntegPage.enterEndingInv(endInv);
        launchIntegPage.clickOK(true);
        return launchIntegPage.waitTillBackGroundProcessIsDone(false);

    }

    /**
     * It updates Integration attributes for "VTX-BatchQuoteCustomerInvoiceBP" and "VTX-BatchPostCustomerInvoiceBP"
     * to either Vertex cloud or OnDemand/Premise instance of o-series
     *
     * @param updateToCloud true for cloud and false for OnDemand/Premise
     */
    @Test(enabled = false)
    public void updateIntegrationAttributes(boolean updateToCloud) {
        WorkdayIntegrationAttributesPage intAttPage;
        if (updateToCloud) {
            loadInitialTestPage();
            WorkdayHomePage page = new WorkdayHomePage(driver);
            intAttPage = page.getViewIntegrationPage();
        } else {
            intAttPage = signInToHomePage(testStartPage).getViewIntegrationPage();
        }
        intAttPage.enterIntegrationSystem(batchQuoteInt);
        enterAttributes(updateToCloud, intAttPage);
        loadInitialTestPage();
        WorkdayHomePage page = new WorkdayHomePage(driver);
        intAttPage = page.getViewIntegrationPage();
        intAttPage.enterIntegrationSystem(batchPostInt);
        enterAttributes(updateToCloud, intAttPage);
    }

    /**
     * It is helper method that enters attributes in textBox and clicks "Ok"
     *
     * @param updateToCloud true for cloud and false for OnDemand/Premise
     * @param intAttPage    WorkdayIntegrationAttributesPage object
     */
    @Test(enabled = false)
    public void enterAttributes(boolean updateToCloud, WorkdayIntegrationAttributesPage intAttPage) {

        intAttPage.getEditAttributePage();
        if (updateToCloud) {
            intAttPage.enterTrustedID(cloudTrustedID);
            intAttPage.enterTaxCalURL(cloudTaxURL);
        } else {
            intAttPage.enterTrustedID(onDemandTrustedId);
            intAttPage.enterTaxCalURL(onDemandTaxURL);
        }
        intAttPage.clickOk();
    }

    /**
     * It is helper method that enters Invalid attributes in textBox and clicks "Ok"
     *
     * @param attribute   name of the Attribute
     * @param integration vertex Integration
     */
    @Test(enabled = false)
    public void enterInvalidAttribute(String attribute, String integration) {
        WorkdayIntegrationAttributesPage intAttPage = signInToHomePage(testStartPage).getViewIntegrationPage();
        intAttPage.enterIntegrationSystem(integration);
        intAttPage.getEditAttributePage();

        if (attribute.equals("trustedID")) {
            intAttPage.enterTrustedID(invalidTrustedID);
        } else {
            intAttPage.enterTaxCalURL(invalidTaxCalcURL);
        }
        intAttPage.clickOk();

    }

    /**
     * It is helper method that enters valid attributes in textBox and clicks "Ok"
     *
     * @param attribute   name of the Attribute
     * @param integration vertex Integration
     */
    @Test(enabled = false)
    public void enterValidAttribute(String attribute, String integration) {
        loadInitialTestPage();
        WorkdayHomePage page = new WorkdayHomePage(driver);
        WorkdayIntegrationAttributesPage intAttPage = page.getViewIntegrationPage();
        intAttPage.enterIntegrationSystem(integration);
        intAttPage.getEditAttributePage();
        if (attribute.equals("trustedID")) {
            intAttPage.enterTrustedID(cloudTrustedID);
        } else {
            intAttPage.enterTaxCalURL(cloudTaxURL);
        }
        intAttPage.clickOk();
    }

    /**
     * It is helper method that enters valid attributes for onDemand/Premise instances in textBox and clicks "Ok"
     *
     * @param attribute   name of the Attribute
     * @param integration vertex Integration
     */
    @Test(enabled = false)
    public void enterValidAttributeForOnDemandPremise(String attribute, String integration) {
        loadInitialTestPage();
        WorkdayHomePage page = new WorkdayHomePage(driver);
        WorkdayIntegrationAttributesPage intAttPage = page.getViewIntegrationPage();
        intAttPage.enterIntegrationSystem(integration);
        intAttPage.getEditAttributePage();
        if (attribute.equals("trustedID")) {
            intAttPage.enterTrustedID(onDemandTrustedId);
        } else {
            intAttPage.enterTaxCalURL(onDemandTaxURL);
        }
        intAttPage.clickOk();
    }

    /**
     * It updates XML file containing 100 Customer Invoices right before uploading so it has
     * unique customer Invoice ID every time it loads
     */
    @Test(enabled = false)
    public void updateXMLFile() {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFile));
            document.getDocumentElement().normalize();
            NodeList nList = document.getElementsByTagName("Row");
            for (int temp = 5; temp < 105; temp++) {
                Node node = nList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("Row")) {
                    node = node.getChildNodes().item(3).getChildNodes().item(0);
                    String value = node.getTextContent();
                    String numberStr = value.split("_")[2];
                    String numPlusOne = Integer.toString(Integer.parseInt(numberStr) + 1);
                    String oldId = "JEN_DPInvoice_" + numberStr;
                    String newId = "JEN_DPInvoice_" + numPlusOne;
                    node.setTextContent(node.getTextContent().replaceAll(oldId, newId));
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            OutputStream stream = new FileOutputStream(xmlFile);
            StreamResult result = new StreamResult(stream);
            transformer.transform(source, result);
            System.out.println("Done");
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * It fills out all the fields in Supplier invoice form including 5 line items
     *
     * @param company          name of the company
     * @param supplier         name of the supplier
     * @param defaultTaxOption Default Tax option
     * @param item             String array of items
     * @param headerTax        Header tax
     * @return WorkdaySupplierInvoicePage page object
     */
    public WorkdaySupplierInvoicePage fillSupplierInvoiceFormForRedesignedValues(String company, String supplier, String defaultTaxOption, String[] item, String headerTax) {
        loadInitialTestPage();
        WorkdayHomePage page = new WorkdayHomePage(driver);
        WorkdaySupplierInvoicePage supplierInvoice = page.getSupplierInvoiceForm();
        supplierInvoice.enterCompanyInfo(company);
        supplierInvoice.enterSupplier(supplier);
        supplierInvoice.enterPaymentTerms();
        supplierInvoice.enterdueDateOverRide();
        supplierInvoice.enterValuesToTextBox("overridepayment", "check", false);
        supplierInvoice.enterReferenceType();
        supplierInvoice.enterReferenceNumber();
        supplierInvoice.selectDefaultTaxOption(defaultTaxOption);
        supplierInvoice.enterDefaultWithHoldingTaxCode();
        supplierInvoice.enterHeaderTaxAmount(headerTax);
        supplierInvoice.enterValuesToTextBox("handlingcode", "messenger", false);
        supplierInvoice.clickOnHold();
        supplierInvoice.enterExternalPONumber();
        supplierInvoice.enterDocumentLink();
        supplierInvoice.enterMemo();
        supplierInvoice.enterApprover();
        supplierInvoice.enterLineLevelInvoiceDetails(item[0], defaultTaxOption, false);
        for (int i = 1; i < item.length; i++) {
            supplierInvoice.addMoreInvoiceLine(item[i], i + 1, defaultTaxOption);
        }
        return supplierInvoice;
    }

    /**
     * It verifies all supplier invoice details after submitting it
     *
     * @param reviewPage       SupplierInvoiceReview Page object
     * @param company          name of the company
     * @param item             sales item
     * @param updatedTaxOption Updated Tax option
     * @param expectedTaxCalc  expected Tax calculation
     * @param headerTax        header Tax Value
     */
    @Test(enabled = false)
    public void verifyUpdatedSupplierInvoiceDetails(WorkdaySupplierInvoiceReviewPage reviewPage, String company, String[] item, String updatedTaxOption,
                                                    String expectedTaxCalc, String headerTax, String originalTaxOption) {
        assertTrue(reviewPage.verifyFixedTextFieldsValues(company, updatedTaxOption), "Verification Failed For Fixed Invoice level TextFields");
        assertTrue(reviewPage.verifyLinelvelDetails(item), "Verification Failed For Line level details");
        assertTrue(reviewPage.verifyTaxDetails(expectedTaxCalc), "Verification Failed For Tax Amount");
        //assertTrue(reviewPage.verifyPrepaidDetails(), "Verification Failed For Prepaid details");
        assertTrue(reviewPage.verifyVendorChrgedTaxFromCustomObject(headerTax), "Verification Failed For Vendor Charged Tax in Custom Object");
    }

    /**
     * It downloads the server logs depending upon the input
     *
     * @param isFirst true if first of two download
     * @return name of the file
     */
    public String downloadServerLogs(boolean isFirst) {
        String fileName = null;
        WorkdayInvoiceReviewPage afterSubmit = new WorkdayInvoiceReviewPage(driver);
        if (isFirst) {
            afterSubmit.switchToProcessTab();
            afterSubmit.clickOnBusinessProcessLink();
            afterSubmit.navigateToServerLogFile();
            fileName = afterSubmit.clickOnFirstServerLog();
        } else {
            afterSubmit.navigateBackToInvoicePage();
            afterSubmit.switchToProcessTab();
            afterSubmit.clickOnSecondBusinessProcessLink();
            afterSubmit.navigateToServerLogFile();
            fileName = afterSubmit.clickOnSecondServerLog();
        }
        VertexFileUtils.waitForFileToExist(VertexPage.DOWNLOAD_DIRECTORY_PATH + "//" + fileName, 10);
        return fileName;
    }

    /**
     * It downloads the server logs for changed invoices depending upon the input
     *
     * @param isFirst true if first of two download
     * @return name of the file
     */
    public String downloadChangeInvoiceServerLogs(boolean isFirst) {
        String fileName = null;
        WorkdayInvoiceReviewPage afterSubmit = new WorkdayInvoiceReviewPage(driver);
        if (isFirst) {
            afterSubmit.switchToProcessTab();
            afterSubmit.clickOnBusinessProcessLink();
            afterSubmit.navigateToServerLogFile();
            fileName = afterSubmit.clickOnFirstServerLog();
        } else {
            afterSubmit.navigateBackToInvoicePage();
            afterSubmit.switchToProcessTab();
            afterSubmit.clickOnSecondBusinessProcessLink();
            afterSubmit.navigateToServerLogFile();
            fileName = afterSubmit.clickOnFirstServerLog();
        }
        VertexFileUtils.waitForFileToExist(VertexPage.DOWNLOAD_DIRECTORY_PATH + "//" + fileName, 10);
        return fileName;
    }

    /**
     * It fills out all the fields in Supplier invoice form with billable checkbox
     *
     * @param company          name of the company
     * @param supplier         name of the supplier
     * @param defaultTaxOption Default Tax option
     * @param item             String array of items
     * @return WorkdaySupplierInvoicePage page object
     */
    public WorkdaySupplierInvoicePage fillSupplierInvoiceFormForRedesignedValuesBillable(String company, String supplier, String defaultTaxOption, String item) {
        loadInitialTestPage();
        WorkdayHomePage homePage = new WorkdayHomePage(driver);
        WorkdaySupplierInvoicePage supplierInvoice = homePage.getSupplierInvoiceForm();
        supplierInvoice.enterCompanyInfo(company);
        supplierInvoice.enterSupplier(supplier);
        supplierInvoice.enterFreightAmount("100");
        supplierInvoice.enterOtherChargesAmount("150");
        supplierInvoice.enterPaymentTerms();
        supplierInvoice.enterdueDateOverRide();
        supplierInvoice.enterValuesToTextBox("overridepayment", "check", false);
        supplierInvoice.enterReferenceType();
        supplierInvoice.enterReferenceNumber();
        supplierInvoice.selectDefaultTaxOption(defaultTaxOption);
        supplierInvoice.enterDefaultWithHoldingTaxCode();
        supplierInvoice.enterValuesToTextBox("handlingcode", "messenger", false);
        supplierInvoice.clickOnHold();
        supplierInvoice.enterExternalPONumber();
        supplierInvoice.enterReferencedInvoices(company);
        supplierInvoice.enterStatutoryInvoiceType();
        supplierInvoice.enterSupplierContract(company);
        supplierInvoice.enterDocumentLink();
        supplierInvoice.enterMemo();
        supplierInvoice.enterApprover();
        supplierInvoice.enterLineLevelInvoiceDetails(item, defaultTaxOption, true);
        supplierInvoice.clicksOnBillableCheckBOx();
        supplierInvoice.enterPrepaidAuthorization();
        return supplierInvoice;
    }

    /**
     * It launch "VtxSupplierInvoiceLoad" and uploads the file from "C:\\upload" folder
     * Files are already uploaded in Test Execution VM according to Date
     * and wait till the integration finish (Successful or fail)
     *
     * @return true if completed
     */
    public boolean launchSupplierInvoiceLoad(String eibName) {

        loadInitialTestPage();
        WorkdayHomePage homePage = new WorkdayHomePage(driver);
        WorkdayLaunchIntegrationPage launchIntegPage;
        launchIntegPage = homePage.getLaunchIntegrationPage();
        launchIntegPage.enterIntegration("VtxSupplierInvoiceLoad");
        launchIntegPage.clickOK(false);
        launchIntegPage.uploadEIBFile(eibName);
        launchIntegPage.clickOK(true);
        boolean isIntegCompleted = launchIntegPage.waitTillBackGroundProcessIsDone(true);
        VertexLogger.log("Total Processing time for " + invLoad + " is:" + launchIntegPage.getProcessingTime());
        return isIntegCompleted;
    }

    /**
     * It gets newly created supplier Invoice by EIB load
     *
     * @param invNo invoice Number
     */
    @Test(enabled = false)
    public void getEIBLoadedSupplierInvoice(String invNo) {
        loadInitialTestPage();
        WorkdayHomePage page = new WorkdayHomePage(driver);
        WorkdayFindInvoicePage findInvPage = page.getFindSupplierInvoicePage();
        findInvPage.enterInvoiceNumber(invNo);
        findInvPage.enterDateAfter();
        WorkdaySupplierInvoiceReviewPage invReview = findInvPage.clickOkAndNavigateToSupplierInvoice(invNo);
        invReview.clickOnFirstGivenInvoice();
        invReview.waitTillEIBLoadedInvoiceIsApproved();
    }

    /**
     * It gets a previously created supplier invoice by invoice number
     *
     * @return WorkdaySupplierInvoicePage page object
     */

    protected WorkdaySupplierInvoiceReviewPage getSupplierInvoicePage(String invNo, String date) {
        loadInitialTestPage();
        WorkdayHomePage page = new WorkdayHomePage(driver);
        WorkdayFindInvoicePage findInvPage = page.getFindSupplierInvoicePage();
        findInvPage.enterInvoiceNumber(invNo);
        findInvPage.enterDateBefore(date);
        WorkdaySupplierInvoiceReviewPage invReview = findInvPage.clickOkAndNavigateToSupplierInvoice(invNo);
        invReview.clickOnFirstGivenInvoice();
        return invReview;
    }

    /**
     * It verifies Currency fields value,third party integration,Locked in workday,payment practices after Updating
     * supplier Invoice through EIB
     *
     * @param updatedTaxOption taxoption
     */
    @Test(enabled = false)
    public void verifyEIBLoadedSupplierInvoiceFieldsValue(String updatedTaxOption) {
        WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);
        assertTrue(supplierInvoiceReviewPage.verifyLineLevelSplitTemplate());
        supplierInvoiceReviewPage.clickOnTaxTab();
        assertTrue(supplierInvoiceReviewPage.verifyThirdPartyIntegrationStatus());
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption));
        assertTrue(supplierInvoiceReviewPage.verifyFixedTextFieldsValue(eibFields, eibFieldsValue, false));
        supplierInvoiceReviewPage.clickOnCurrencyRateTab();
        assertTrue(supplierInvoiceReviewPage.verifyFixedTextFieldsValue(eibCurrencyFields, eibCurrencyFieldsValue, false));
    }

    /**
     * This Method verifies Transaction data in Custom object
     *
     * @param callStatus call status
     * @param callType   Call type
     * @param postedFlag PostedFlag
     */
    @Test(enabled = false)
    public void verifyHeaderLevelCustomObjectTransactionData(String callType, String callStatus, String postedFlag) {
        WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);
        assertTrue(supplierInvoiceReviewPage.verifyVertexCallType(callType));
        assertTrue(supplierInvoiceReviewPage.verifyVertexCallStatus(callStatus));
        assertTrue(supplierInvoiceReviewPage.verifyVertexTransactionId());
        assertTrue(supplierInvoiceReviewPage.verifyVertexPostedFlag(postedFlag));
    }

    /**
     * It verifies Currency fields value,third party integration,Locked in workday,payment practices after Updating
     * supplier Invoice through EIB
     *
     * @param updatedTaxOption taxoption
     */
    @Test(enabled = false)
    public void verifyEIBLoadedSupplierInvoiceProratedFieldsValue(String updatedTaxOption, String expectedTaxCalc, String[] expectedProratedTax) {
        WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);
        supplierInvoiceReviewPage.clickOnTaxTab();
        assertTrue(supplierInvoiceReviewPage.verifyThirdPartyIntegrationStatus(), "ThirdParty Tax Integration Failed");
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc), "Verification Failed For Tax Amount");
        assertTrue(supplierInvoiceReviewPage.verifyUpdatedTaxOption(updatedTaxOption), "Tax option verification failed");
        WorkdayInvoiceReviewPage afterSubmit = new WorkdayInvoiceReviewPage(driver);
        afterSubmit.switchToTab("Process History");
        afterSubmit.clickOnBusinessProcessLink();
        afterSubmit.navigateToServerLogFile();
        String fileName = afterSubmit.clickOnFirstServerLog();
        VertexFileUtils.waitForFileToExist(VertexPage.DOWNLOAD_DIRECTORY_PATH + "//" + fileName, 10);
        for (String expectedString : expectedProratedTax) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }

    /**
     * This Method is filling Supplier invoice Form fields except Sales Items
     *
     * @param company    Company
     * @param billToCust Bill-to Customers
     */
    @Test(enabled = false)
    public void fillOutCustomerInvoiceFormExceptSalesItems(String company, String billToCust) {
        loadInitialTestPage();
        WorkdayHomePage page = new WorkdayHomePage(driver);
        WorkdayCustomerInvoicePage customerInvoice = page.getCustomerInvoiceForm();
        customerInvoice.enterCompanyInfo(company);
        customerInvoice.enterBillToCustomer(billToCust);
    }

    /**
     * This Method Fills Customer Invoice Sales Items' Fields
     *
     * @param salesItem
     * @param qty
     * @param unitPrice
     * @param address
     * @return WorkdayCustomerInvoicePage object
     */
    public WorkdayCustomerInvoicePage fillOutCustomerInvoiceSalesItemFields(String salesItem, String qty, String unitPrice, String address) {
        WorkdayCustomerInvoicePage customerInvoicePage = new WorkdayCustomerInvoicePage(driver);
        customerInvoicePage.clickOnAddLineButtonIfNeeded();
        customerInvoicePage.enterSalesItem(salesItem);
        if (!qty.equals("1")) {
            customerInvoicePage.changeInQuantity(Integer.parseInt(qty));
        }
        if (unitPrice != null) {
            customerInvoicePage.changeInUnitPrice(Integer.parseInt(unitPrice));
        }
        if (address != null) {
            customerInvoicePage.changeShippingAddress(address);
        }
        return customerInvoicePage;
    }

    /**
     * It downloads the server logs depending upon the input
     *
     * @param isFirst true if first of two download
     * @return name of the file
     */
    public String downloadServerLogsForSupplierInv(boolean isFirst) {
        String fileName = null;
        WorkdayInvoiceReviewPage afterSubmit = new WorkdayInvoiceReviewPage(driver);
        if (isFirst) {
            afterSubmit.clickOnBusinessProcessLink();
            afterSubmit.navigateToServerLogFile();
            fileName = afterSubmit.clickOnFirstServerLog();
        } else {
            if (driver.findElements(onProcessPage).size() <= 0) {
                afterSubmit.navigateBackToInvoicePage();
                afterSubmit.switchToTab("Business Process");
            }
            afterSubmit.clickOnSecondBusinessProcessLink();
            afterSubmit.navigateToServerLogFile();
            fileName = afterSubmit.clickOnSecondServerLog();
        }
        VertexFileUtils.waitForFileToExist(VertexPage.DOWNLOAD_DIRECTORY_PATH + "//" + fileName, 10);
        return fileName;
    }

    /**
     * It downloads the Post server logs
     *
     * @return name of the file
     */
    public String downloadServerLogsForPostSupplierInv() {
        String fileName = null;
        WorkdayInvoiceReviewPage afterSubmit = new WorkdayInvoiceReviewPage(driver);
        afterSubmit.clickOnPostBusinessProcessLink();
        afterSubmit.navigateToServerLogFile();
        fileName = afterSubmit.clickOnFirstServerLog();
        VertexFileUtils.waitForFileToExist(VertexPage.DOWNLOAD_DIRECTORY_PATH + "//" + fileName, 10);
        return fileName;
    }

    /**
     * Downloads Preprocess Server Logs
     */
    public String downloadPreprocessServerLogs() {
        String fileName = null;
        WorkdayInvoiceReviewPage afterSubmit = new WorkdayInvoiceReviewPage(driver);
        afterSubmit.clickOnPreProcessBPLink();
        afterSubmit.navigateToServerLogFile();
        fileName = afterSubmit.clickOnFirstServerLog();
        VertexFileUtils.waitForFileToExist(VertexPage.DOWNLOAD_DIRECTORY_PATH + "//" + fileName, 10);
        return fileName;
    }

    /**
     * This method validate Strings from the Server logs for Supplier invoice
     *
     * @param afterSubmit                   WorkdayInvoiceReviewPage object
     * @param expectedStringsForAdjustedInv Array of strings that needs to be validated
     */
    @Test(enabled = false)
    public void validateServerLogStringsSupInv(WorkdayInvoiceReviewPage afterSubmit, String[] expectedStringsForAdjustedInv) {
        afterSubmit.clickOnClose();
        afterSubmit.switchToTab("Process");
        String fileName = downloadServerLogsForPostSupplierInv();
        for (String expectedString : expectedStringsForAdjustedInv) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }
    }
}
