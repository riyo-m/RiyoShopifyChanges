package com.vertex.quality.connectors.oraclecloud.tests;

import com.vertex.quality.connectors.oraclecloud.pages.OracleCloudManageReqsPage;
import com.vertex.quality.connectors.oraclecloud.tests.base.OracleCloudBaseTest;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * Container for all Requisition related single transaction
 * regression tests for Oracle Cloud.
 *
 * @author msalomone
 */
public class OracleCloudRequisitionSingleTransactionTests extends OracleCloudBaseTest {

    /**
     * Create a purchase requisition in the ERP.
     *
     * COERPC-3132
     */
    @Test(groups = { "oerpc_regression", "oerpc_ui", "oerpc_req_single" })
    public void reqCreateRequisitionTest() {
        String reqNumber = "1421";

        loadInitialTestPage();

        signInToHomePage();

        OracleCloudManageReqsPage manageReqsPage = navigateToManageReqsPage();

        inputManageReqSearchInfo(manageReqsPage, reqNumber);

        manageReqsPage.clickSearchButton();
        manageReqsPage.duplicateRequisition();
        manageReqsPage.clickSaveRequisition();
        manageReqsPage.clickSubmitRequisition();

        boolean submissionConfirmed = manageReqsPage.verifySubConfirmation();

        assertTrue(submissionConfirmed);
    }
}
