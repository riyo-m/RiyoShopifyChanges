package com.vertex.quality.connectors.concur.tests.base;

import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.connectors.concur.pages.misc.VertexConfigConcurHomePage;
import com.vertex.quality.connectors.concur.pages.misc.VertexConfigConcurSignInPage;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * class contains sign page methods of vertex concur page
 *
 * @author alewis
 */
public class VertexConcurBaseTest extends VertexUIBaseTest<VertexConfigConcurSignInPage> {

    public String vertexConcurURL = "https://concur.dev.vertexconnectors.com/#/";
    public String Username = "concurAutomationUser";
    public String Password = "welcome1";

    /**
     * Method to login for Vertex Concur login
     */
    protected void vertexConcurSignIn() {
        VertexConfigConcurSignInPage signInPage = new VertexConfigConcurSignInPage(driver);
        driver.get(vertexConcurURL);
        signInPage.enterUsername(Username);
        signInPage.enterPassword(Password);
        signInPage.clickLogin();
        signInPage.waitForPageLoad();
        assertTrue(signInPage.verifyVertexConcurLogin());
    }

    /**
     * get the status of invoice batch job
     *
     * @return status returns the status text of the batch job
     */
    public String verifyStatusInvoiceBatchJob() {
        VertexConfigConcurHomePage homePage = new VertexConfigConcurHomePage(driver);
        String status;
        homePage.clickStartInvoiceJobButton();
        homePage.refreshPage();
        homePage.jsWaiter.sleep(50000);
        status = homePage.getStatusInvoiceBatchJob();
        for (int n = 0; !status.equals("COMPLETED"); n++) {
            homePage.refreshPage();
            homePage.waitForPageLoad();
            if (!homePage.verifyVertexConcurText()) {
                vertexConcurSignIn();
            }
            status = homePage.getStatusInvoiceBatchJob();
            if (status.equals("FAILED")) {
                fail("The Status of Invoice Batch Job is Getting Failed.");
            }
        }
        status = homePage.getStatusInvoiceBatchJob();
        if (status.equals("COMPLETED")) {
            assertTrue(true, "The Status of Invoice Batch Job is Completed.");
        }
        return status;
    }

    /**
     * Method to Logout for Vertex Concur Page
     */
    protected void vertexConcurSignOut() {
        VertexConfigConcurSignInPage signInPage = new VertexConfigConcurSignInPage(driver);
        signInPage.clickLogoutButton();
    }

    public String verifyTokenStatusJob() {
        VertexConfigConcurHomePage homePage = new VertexConfigConcurHomePage(driver);

        String tokenStatus = homePage.getTokenStatus();

        return tokenStatus;
    }

    public String verifyScheduleInvoiceJob() {
        VertexConfigConcurHomePage homePage = new VertexConfigConcurHomePage(driver);

        String invoiceJobText = homePage.checkScheduleInvoiceJob();

        return invoiceJobText;
    }

    public String verifyScheduleTokenJob() {
        VertexConfigConcurHomePage homePage = new VertexConfigConcurHomePage(driver);

        String tokenJobText = homePage.checkScheduleTokenJob();

        return tokenJobText;
    }
}