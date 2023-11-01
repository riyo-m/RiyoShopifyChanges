package com.vertex.quality.connectors.workday.tests.ui.Configuration;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.workday.pages.*;
import com.vertex.quality.connectors.workday.tests.base.WorkdayBaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

/**
 * tests that Validates Integration Attributes for all available Integrations
 *
 * @author Dpatel
 */

public class WorkdayValidateIntegrationAttributesTests extends WorkdayBaseTest {

    String noTax = "0";
    String zeroHeaderTax = "0";
    String calcSelfAssessed = "Calculate Self-Assessed Tax";
    String post = "post";
    String gps = "GPS";
    String [] item = {chairs,alcoholSwab, alcoholWipePads,binderClips,laptopPowerAdaptor};
    WorkdaySupplierInvoiceReviewPage reviewPage;
    WorkdayBusinessProcessPage bpPage;
    /**
     * this test validates that trustedID for all the integrations are masked
     * CWD-458
     */
    @Test(groups = { "workday_regression"} )
    public void workdayValidateTrustedIDisMaskedTest()
    {
        String expectedTrustedID = "*******";
        WorkdayIntegrationAttributesPage intAttPage;
        intAttPage = signInToHomePage(testStartPage).getViewIntegrationPage();
        intAttPage.enterIntegrationSystem(custInvInt);
        assertTrue(intAttPage.validateTrustedID(expectedTrustedID));
        VertexLogger.log("Integration: "+ custInvInt +" has masked Trusted ID");
        String [] integrations = {supInvInt,batchQuoteInt,batchPostInt};
        for (String integration : integrations) {
            loadInitialTestPage();
            WorkdayHomePage page = new WorkdayHomePage(driver);
            intAttPage = page.getViewIntegrationPage();
            intAttPage.enterIntegrationSystem(integration);
            assertTrue(intAttPage.validateTrustedID(expectedTrustedID));
            VertexLogger.log("Integration: " + integration + " has masked Trusted ID");
        }
    }

    /**
     * this test validates that correct release version is available in Launch Parameter for all four Integrations
     * CWD-461
     */
    @Test(groups = { "workday_regression"} )
    public void workdayValidateReleaseVersionInLaunchParameterTest()
    {
        String expectedVersion = "Workday2020R1v1.1.0";
        WorkdayIntegrationAttributesPage intAttPage;
        intAttPage = signInToHomePage(testStartPage).getViewIntegrationPage();
        intAttPage.enterIntegrationSystem(custInvInt);
        intAttPage.switchTab("Launch Parameters");
        assertTrue(intAttPage.isVersionParamExist());
        assertTrue(intAttPage.validateVersionParameter(expectedVersion));
        VertexLogger.log("Integration: " + custInvInt + " has correct version in Launch Parameter");
        String [] integrations = {supInvInt,batchQuoteInt,batchPostInt};
        for (String integration : integrations) {
            loadInitialTestPage();
            WorkdayHomePage page = new WorkdayHomePage(driver);
            intAttPage = page.getViewIntegrationPage();
            intAttPage.enterIntegrationSystem(integration);
            intAttPage.switchTab("Launch Parameters");
            assertTrue(intAttPage.isVersionParamExist());
            assertTrue(intAttPage.validateVersionParameter(expectedVersion));
            VertexLogger.log("Integration: " + integration + " has correct version in Launch Parameter");
        }
    }

    /**
     * this test validates that with Incorrect URL Customer Invoice Integration(Single) is throwing proper error
     * CWD-247
     */
    @Test(groups = { "workday_DO_NOT_RUN"} )
    public void workdayValidateCustomerInvoiceWithIncorrectURLTest()
    {
        enterInvalidAttribute("taxCalcURL",custInvInt);
        String expectedResults = "0.00";
        WorkdayInvoiceReviewPage afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes("Spectre","LexCorp","Services 13",2)
                .clickOnSubmit();
        assertFalse(verifyIntegrationCompleted("Customer"));
        assertTrue(afterSubmit.verifyTaxCalcErrorMessage(invalidTaxCalcURLErrorMessage));
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedResults,failedTaxCalcVal);
    }

    /**
     * this test validates that with Incorrect TrustedID Customer Invoice Integration(Single) is throwing proper error
     * CWD-462
     */
    @Test(groups = { "workday_DO_NOT_RUN"} )
    public void workdayValidateCustomerInvoiceWithIncorrectTrustedIDTest()
    {
        enterInvalidAttribute("trustedID",custInvInt);
        String expectedResults = "0.00";
        WorkdayInvoiceReviewPage afterSubmit = fillCustomerInvoiceFormForUpdatedAttributes("Spectre","LexCorp","Services 13",2)
                .clickOnSubmit();
        assertFalse(verifyIntegrationCompleted("Customer"));
        assertTrue(afterSubmit.verifyTaxCalcErrorMessage(invalidTrustedIDErrorMessage));
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedResults,failedTaxCalcVal);
    }


    /**
     * this test validates that with Incorrect TrustedID supplier Invoice Integration(Single) is throwing proper error
     * CWD-763
     */
    @Test(groups = { "workday_DO_NOT_RUN"} )
    public void workdayValidateSupplierInvoiceWithIncorrectTrustedIDTest()
    {
        String expectedTaxCalc = "0";
        enterInvalidAttribute("trustedID",supInvInt);
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes(spectre, americanElectric, selfAssessedTax, banners,1,noTax)
                .clickOnSubmit();
        assertFalse(verifyIntegrationCompleted("Supplier"),intfailedMessage);
        assertTrue(reviewPage.verifySupplierInvoiceTaxCalcErrorMessage(invalidSupplierInvoiceTrustedIDErrorMessage));
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc, failedTaxCalcVal);
    }

    /**
     * this test validates that with Incorrect TrustedID Supplier Invoice Integration(Single) is throwing proper error
     * CWD-225
     */
    @Test(groups = { "workday_DO_NOT_RUN"} )
    public void workdayValidateSupplierInvoiceWithIncorrectURLTest()
    {
        String expectedTaxCalc = "0";
        enterInvalidAttribute("taxCalcURL",supInvInt);
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes(spectre, americanElectric, selfAssessedTax, banners,1,noTax)
                .clickOnSubmit();
        assertFalse(verifyIntegrationCompleted("Supplier"),intfailedMessage);
        assertTrue(reviewPage.verifySupplierInvoiceTaxCalcErrorMessage(invalidSupplierInvoiceTaxCalcURLErrorMessage));
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc, failedTaxCalcVal);
    }

    /**
     * This test Verifes that "VtxCalcDelay" Configuration parameter in Integration "Vtx-SupplierInvoiceBP" is functioning correctly
     * Basically Time Delay should be more than 100 ms from the server logs
     * CWD-590
     */
    @Test(groups = { "workday_regression"} )
    public void workdaySupplierInvoiceVtxCalcDelayConfigurationTest()
    {
        String expectedTaxCalc = "3.92";
        String originalTaxOption = calcSelfAssessed;
        String updatedTaxOption = calcSelfAssessed;
        String company = gps;
        String headerTaxValue = zeroHeaderTax;
        String expectedString1 = "Applying VtxCalcDelay of 200 ms";
        String expectedString2 = "Finished applying VtxCalcDelay.";
        String line1 ;
        String line2 ;

        //signing in the home page
        signInToHomePage(testStartPage);


        //Fill and verify Supplier Invoice Form
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForRedesignedValues(company, americanElectric,originalTaxOption, item, headerTaxValue)
                .clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);
        reviewPage = afterSubmit.clickonNewlyCreatedSupplierInvoice();
        verifyUpdatedSupplierInvoiceDetails(reviewPage, company, item, updatedTaxOption, expectedTaxCalc, headerTaxValue,originalTaxOption);

        //Download server logs
        reviewPage.navigateToSupplierInvoiceDetails();
        afterSubmit.switchToTab("Process History");
        afterSubmit.clickOnBusinessProcessLink();
        String fileName= downloadServerLogs(true);

        //Get Lines with the expected Strings
        line1 = afterSubmit.getFullLineFromTextPresentInServerLogs(fileName, expectedString1);
        line2 = afterSubmit.getFullLineFromTextPresentInServerLogs(fileName, expectedString2);
        String time1 =  afterSubmit.getBeforeAfterTimeForDelay(line1,line2,true);
        String time2 = afterSubmit.getBeforeAfterTimeForDelay(line1,line2,false);

        //Verify that delay is over 100 Seconds
        assertTrue(afterSubmit.verifyDelay(time1,time2));
    }

    /**
     * This Test Validate that Integration has only debugflag and WWSVersion parameters
     * CWD-816
     */
    @Test(groups = { "workday_regression"} )
    public void validatePreprocessIntegrationAttributesFromUITest()
    {
        String expectedVersion = "Workday2020R1v1.1.0";
        WorkdayIntegrationAttributesPage intAttPage;

        //Getting "View Integration Page" and entering Integration
        intAttPage = signInToHomePage(testStartPage).getViewIntegrationPage();
        intAttPage.enterIntegrationSystem(preProcessInt);

        //Validation Points for Integration Attributes
        assertTrue(intAttPage.validateIntAttributePresent("DebugFlag"));
        assertTrue(intAttPage.validateIntAttributePresent("WWSVersion"));
        assertFalse(intAttPage.validateIntAttributePresent("TrustedId"));
        assertFalse(intAttPage.validateIntAttributePresent("VertexWSTaxCalcURL"));

        //Validation for version exist or not
        intAttPage.switchTab("Launch Parameters");
        assertTrue(intAttPage.isVersionParamExist());
        assertTrue(intAttPage.validateVersionParameter(expectedVersion));
    }

    /**
     * This Test Validates Preprocess Integration Attributes from server Logs
     * CWD-817
     */
    public void validatePreProcessIntAttributesFromServerLogsTest()
    {
        //Test Data
        String expectedTaxCalc = "15.00";
        String tax = "10";
        String originalTaxoption = enterTaxDue;
        String [] item = {alcoholSwab};
        String [] expectedStrings = {"TrustedId: null", "VertexWSTaxCalcURL: null","pDebugFlag: true", "pWWSVersion: v34.0"};

        WorkdaySupplierInvoiceReviewPage supplierInvoiceReviewPage = new WorkdaySupplierInvoiceReviewPage(driver);

        signInToHomePage(testStartPage);
        //Class Initialization

        //Filling Supplier Invoice form and verify it's completed
        WorkdayInvoiceReviewPage afterSubmit = fillSupplierInvoiceFormForUpdatedAttributes("GPS USA", "American",
                originalTaxoption, item,100,tax,null).clickOnSubmit();
        assertTrue(verifySupplierInvoiceIntegrationCompleted(),intfailedMessage);

        //Validate tax option, tax amount, Line object Tax amount and Tax rates
        assertTrue(supplierInvoiceReviewPage.verifyTaxDetails(expectedTaxCalc));
        assertEquals(afterSubmit.getValuesFromTaxAmount(),expectedTaxCalc, failedTaxCalcVal);
        afterSubmit.switchToTab("Process");
        String fileName= downloadPreprocessServerLogs();
        for (String expectedString : expectedStrings) {
            assertTrue(afterSubmit.verifyTextIsPresentInServerLogs(fileName, expectedString));
        }

    }
    @AfterMethod
    public void afterMethod(Method nameOfTest)
    {
        if (nameOfTest.getName().contains("CustomerInvoiceWithIncorrectURL")) {
            enterValidAttribute("taxCalcURL",custInvInt);
        }
        else if (nameOfTest.getName().contains("CustomerInvoiceWithIncorrectTrustedID")){
            enterValidAttribute("trustedID",custInvInt);
        }
        else if (nameOfTest.getName().contains("SupplierInvoiceWithIncorrectTrustedID")){
            enterValidAttribute("trustedID",supInvInt);
        }
        else if(nameOfTest.getName().contains("SupplierInvoiceWithIncorrectURL"))
        {
            enterValidAttribute("taxCalcURL",supInvInt);
        }
    }
}
