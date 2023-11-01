package com.vertex.quality.connectors.workday.tests.ui.USA.CustomerInvoice;

import com.vertex.quality.connectors.workday.tests.base.WorkdayBaseTest;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

/**
 * this test class covers tests for end to end Batch Processing Functionality by using "Vertex Cloud" Instance
 *
 * @author dpatel
 */

public class WorkdayBatchProcessingTests extends WorkdayBaseTest {

    /**
     * this test covers end to end batch processing from Invoice Load to Verify Custom Object
     * It creates new XML file every time it runs for unique customer Invoice ID
     * CWD-439
     */
    @Test(groups = { "workday_Batch" })
    public void workdayBatchProcessingEndToEndHappyPathTest() {

        int expectedInv=100;
        String [] expectedAmount = {"171.00", "17,100.00", "8,721.00" };

        // Updates XML file before loading
        updateXMLFile();

        // Loading XML file with 100 Invoices and Verifying the Successful load
        assertTrue(launchCustomerInvoiceLoad(true),"Invoice Load Integration Failed");
        assertTrue(verifyInvoiceLoad(expectedInv),"InvoiceLoad verification Failed");
        String [] invoices = getRangeOfInvoices(expectedInv);

        //Launch and verify BatchQuote Integration
        assertTrue(launchBatchQuoteIntegration(),"BatchQuote Integration Failed");
        assertTrue(verifyBatchQuoteIntegration(expectedInv),"BatchQuote Integration verification Failed");

        //launch BatchPost Integration and verifying Custom Object status
        assertTrue(launchBatchPostIntegration(),"BatchPost Integration failed");
        for (int i=0; i<invoices.length; i++){
            assertTrue(verifyPostedFlagStatusAndTaxAMountOfInvoice(invoices[i],expectedAmount[i]),"Custom Object was not updated");
        }
    }

    /**
     * this test covers end to end batch processing when Custom Object object was not updated after "BatchQuote"
     * It creates new XML file every time it runs for unique customer Invoice ID
     * This exception is created manually by using "cleanupsimulator" Integration
     * CWD-430
     */
    @Test(groups = { "workday_Batch"})
    public void workdayBatchProcessingEndToEndCustomObjectCleanupPathTest()  {

        int expectedInv=100;
        int noOfCleanedInv;
        String [] expectedAmount = {"171.00", "17,100.00", "8,721.00" };

        // Updates XML file before loading
        updateXMLFile();

        // Loading XML file with 100 Invoices and Verifying the Successful load
        assertTrue(launchCustomerInvoiceLoad(true),"Invoice Load Integration Failed");
        assertTrue(verifyInvoiceLoad(expectedInv),"InvoiceLoad verification Failed");
        String [] invoices = getRangeOfInvoices(expectedInv);

        //Launch and verify BatchQuote Integration
        assertTrue(launchBatchQuoteIntegration(),"BatchQuote Integration Failed");
        assertTrue(verifyBatchQuoteIntegration(expectedInv),"BatchQuote Integration verification Failed");

        //Launch Cleanup BatchQuote Simulator and verifying it by running BatchQuote report
        assertTrue(launchCleanupIntegration(invoices[0],invoices[2]),"Cleanup BatchQuote Integration Failed");
        noOfCleanedInv = (Integer.parseInt(invoices[2]))-(Integer.parseInt(invoices[0]))+1;
        System.out.println(noOfCleanedInv);
        System.out.println(expectedInv-noOfCleanedInv);
        assertTrue(verifyBatchQuoteIntegration(expectedInv-noOfCleanedInv),"Custom object was not wiped");

        //launch BatchPost Integration and verifying Custom Object status
        assertTrue(launchBatchPostIntegration(),"BatchPost Integration failed");
        for (int i=0; i<invoices.length; i++){
            assertTrue(verifyPostedFlagStatusAndTaxAMountOfInvoice(invoices[i],expectedAmount[i]),"Custom Object was not updated");
        }
    }

    /**
     * this test covers end to end batch processing from Invoice Load to Verify Custom Object
     * It creates new XML file every time it runs for unique customer Invoice ID
     * CWD-424
     */
    @Test(groups = { "workday_Batch"})
    public void workdayBatchProcessingEndToEndHappyPathOnDemandPremiseTest() {

        int expectedInv=100;
        String [] expectedAmount = {"171.00", "17,100.00", "8,721.00" };

        // updates XML file before loading
        updateXMLFile();

        // Updates Intergation Attributes to OnDemand/Prem
        updateIntegrationAttributes(false);

        // Loading XML file with 100 Invoices and Verifying the Successful load
        assertTrue(launchCustomerInvoiceLoad(false),"Invoice Load Integration Failed");
        assertTrue(verifyInvoiceLoad(expectedInv),"InvoiceLoad verification Failed");
        String [] invoices = getRangeOfInvoices(expectedInv);

        //Launch and verify BatchQuote Integration
        assertTrue(launchBatchQuoteIntegration(),"BatchQuote Integration Failed");
        assertTrue(verifyBatchQuoteIntegration(expectedInv),"BatchQuote Integration verification Failed");

        //launch BatchPost Integration and verifying Custom Object status
        assertTrue(launchBatchPostIntegration(),"BatchPost Integration failed");
        for (int i=0; i<invoices.length; i++){
            assertTrue(verifyPostedFlagStatusAndTaxAMountOfInvoice(invoices[i],expectedAmount[i]),"Custom Object was not updated");
        }
        updateIntegrationAttributes(true);
    }

    /**
     * this test covers end to end batch processing from Invoice Load to Verify Custom Object
     * It creates new XML file every time it runs for unique customer Invoice ID
     * It artificially create exception in which custom object will not update even after successful quote
     * It first changes Integration attributes to OnDemand/Premise one and at last it changes it back
     * CWD-722
     */
    @Test(groups = { "workday_Batch"})
    public void workdayBatchProcessingEndToEndCustomObjectCleanupPathOnDemandPremiseTest() {
        int expectedInv = 100;
        int noOfCleanedInv;
        String[] expectedAmount = {"171.00", "17,100.00", "8,721.00" };

        // Updates XML file before loading
        updateXMLFile();

        // Updates Intergation Attributes to OnDemand/Prem
        updateIntegrationAttributes(false);

        // Loading XML file with 100 Invoices and Verifying the Successful load
        assertTrue(launchCustomerInvoiceLoad(false), "Invoice Load Integration Failed");
        assertTrue(verifyInvoiceLoad(expectedInv), "InvoiceLoad verification Failed");
        String[] invoices = getRangeOfInvoices(expectedInv);

        //Launch and verify BatchQuote Integration
        assertTrue(launchBatchQuoteIntegration(), "BatchQuote Integration Failed");
        assertTrue(verifyBatchQuoteIntegration(expectedInv), "BatchQuote Integration verification Failed");

        //Launch Cleanup BatchQuote Simulator and verifying it by running BatchQuote report
        assertTrue(launchCleanupIntegration(invoices[0], invoices[2]), "Cleanup BatchQuote Integration Failed");
        noOfCleanedInv = (Integer.parseInt(invoices[2])) - (Integer.parseInt(invoices[0])) + 1;
        System.out.println(noOfCleanedInv);
        System.out.println(expectedInv - noOfCleanedInv);
        assertTrue(verifyBatchQuoteIntegration(expectedInv - noOfCleanedInv), "Custom object was not wiped");

        //launch BatchPost Integration and verifying Custom Object status
        assertTrue(launchBatchPostIntegration(), "BatchPost Integration failed");
        for (int i = 0; i < invoices.length; i++) {
            assertTrue(verifyPostedFlagStatusAndTaxAMountOfInvoice(invoices[i], expectedAmount[i]), "Custom Object was not updated");
        }
        updateIntegrationAttributes(true);
    }
}
