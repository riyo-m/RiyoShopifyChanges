package com.vertex.quality.connectors.concur.api.tests;

import com.vertex.quality.connectors.concur.api.tests.base.concurAPIBaseTest;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.hasToString;

/**
 * Concur API Tests
 *
 * @author alewis
 */
public class ConcurAPIInvoiceTests extends concurAPIBaseTest {

    /**
     * PA invoice test
     *
     * CSAPCONC-396
     */
    @Test(groups = "concurAPI")
    public void concurInvoiceTest() {
        String id = concurInvoice("US","USD","100","2020-08-20","PT-INVOICE-1","PT-INVOICE-1",
                "","00AEE75D874A48778F6D6C49AD049295","2033","Bank Fees","2","100","");
        runBatchJob();
        checkForCorrectTax(id,"0.06000000");
    }

    /**
     * PA invoice test with quantity of 10
     *
     * CSAPCONC-395
     */
    @Test(groups = "concurAPI")
    public void concurInvoiceQuantityTenTest() {
        String id = concurInvoice("US","USD","100","2020-08-19","PT-INVOICE-2","PT-INVOICE-2",
                "","00AEE75D874A48778F6D6C49AD049295","2033","Bank Fees","10","100","");
        runBatchJob();
        checkForCorrectTax(id,"0.06000000");
    }

    /**
     * Invoice No Sales Tax State (PA to DE)
     *
     * CSAPCONC-415
     */
    @Test(groups = "concurAPI")
    public void concurNoTaxTest()
    {
        String id = concurInvoice("US","USD","100","2020-08-23","1325467","Test-415",
                "PA-415","B0180909D10246568E2B050ED2EEB306","2033","Bank Fees","1","100","19801");
        runBatchJob();
        checkForCorrectTax(id,"0.00000000");
    }
}