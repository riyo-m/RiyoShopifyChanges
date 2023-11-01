package com.vertex.quality.connectors.concur.tests.connector;

import com.vertex.quality.connectors.concur.pages.misc.VertexConfigConcurHomePage;
import com.vertex.quality.connectors.concur.tests.base.VertexConcurBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * tests the Latest Invoice Batch Job Status
 *
 * @author alewis
 */
@Test(groups = { "concur" })
public class ConcurJobStatusTests extends VertexConcurBaseTest {

    /**
     * check Invoice Batch Job Status
     *
     * CSAPCONC-448
     */
    @Test
    public void checkInvoiceBatchJobStatusTest() {
        VertexConfigConcurHomePage homePage = new VertexConfigConcurHomePage(driver);

        //SignIn to vertex Concur Home Page
        vertexConcurSignIn();

        //verifies the status the invoice batch job
        assertEquals(verifyStatusInvoiceBatchJob(), "COMPLETED");

        //verifies the start time of the batch job
        assertTrue(homePage.verifyJobStartTime());

        //verifies the end time of the batch job
        assertTrue(homePage.verifyJobStartTime());

        //SignOut from vertex Concur Page
        vertexConcurSignOut();
    }

    /**
     * check Invoice Batch Job Status
     *
     * CSAPCONC-449
     */
    @Test
    public void checkTokenButtonTest() {
        VertexConfigConcurHomePage homePage = new VertexConfigConcurHomePage(driver);
        vertexConcurSignIn();

        String tokenStatus = verifyTokenStatusJob();

        assertEquals(tokenStatus,"AVAILABLE");
    }

    /**
     * check schedule information on connector test
     *
     * CSAPCONC-453
     */
    @Test
    public void checkConnectorScheduleInfoTest() {
        VertexConfigConcurHomePage homePage = new VertexConfigConcurHomePage(driver);
        vertexConcurSignIn();

        String invoiceSchedule = verifyScheduleInvoiceJob();
        String tokenSchedule = verifyScheduleTokenJob();

        assertEquals(invoiceSchedule,"Cron Expression - 0 0/10 * ? * *");
        assertEquals(tokenSchedule,"Every 50 min; Free tokens that are processing for longer than 12 min");

    }
}