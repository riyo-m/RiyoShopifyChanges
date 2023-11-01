package com.vertex.quality.connectors.coupa.tests.ui;

import com.vertex.quality.connectors.coupa.pages.CoupaCreateInvoicePage;
import com.vertex.quality.connectors.coupa.pages.CoupaHomePage;
import com.vertex.quality.connectors.coupa.pages.CoupaSignInPage;
import com.vertex.quality.connectors.coupa.pages.VertexCoupaSignInPage;
import com.vertex.quality.connectors.coupa.tests.ui.base.CoupaUIBaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Basic configuration ui tests on the coupa connector
 *
 * @author alewis
 */
public class CoupaConfigUITests extends CoupaUIBaseTest {

    /**
     * Tests the vertex connector version
     *
     * CCOUPA-1488
     */
    @Test(groups = {"coupa","coupa_ui","coupa_regression"})
    public void coupaConnectorVersionTest() {
        VertexCoupaSignInPage signInPage = new VertexCoupaSignInPage(driver);
        //login to vertex coupa
        signInPage.vertexCoupaQALogin();
        //verify the coupa version
        assertEquals(signInPage.getCoupaVersion(),"1.0.0.13");
    }

    /**
     * Login test using O-series 9 auth
     *
     * CCOUPA-1711
     */
    @Test(groups = {"coupa","coupa_ui","coupa_regression"})
    public void loginTest() {
        VertexCoupaSignInPage signInPage = new VertexCoupaSignInPage(driver);
        signInPage.vertexCoupaLogin();
        String pageTitle = signInPage.getPageTitle();
        assertEquals(pageTitle,"Vertex Connector for Coupa");
    }

    @Test(groups = {"coupa","coupa_ui","coupa_regression"})
    public void switchVertexInvoiceTest() {
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);
        signInPage.verifyCoupaLogin();
        CoupaHomePage homePage = new CoupaHomePage(driver);
        homePage.clickSetup();
        homePage.clickSetupPageLink("Call Outs");
        homePage.clickVertexCoupaCallOut();
        homePage.clickEditCallOutButton();
        homePage.selectQAEndPoint("9"); // "9" represents the html value of the QA environment in the dropdown
        homePage.clickEditEndpointSaveButton();
        Boolean QAEPEndpoint = homePage.confirmEndpointValueChange("Vertex Coupa QA EP - Endpoint::HttpInstance");
        assertTrue(QAEPEndpoint);
    }
}
