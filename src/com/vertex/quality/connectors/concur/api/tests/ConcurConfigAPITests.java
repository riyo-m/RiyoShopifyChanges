package com.vertex.quality.connectors.concur.api.tests;

import com.vertex.quality.connectors.concur.api.tests.base.concurAPIBaseTest;
import org.testng.annotations.Test;

public class ConcurConfigAPITests extends concurAPIBaseTest {


    /**
     * Test to see if trustedIDs containing a period pass healthcheck
     *
     * CSAPCONC-451
     */
    @Test(groups = "concurAPI")
    public void testTrustedIDWithPeriodTest() {
        createConnectorAccessToken();
        getConcurTokenPeriodTrustedID();
    }

    /**
     * Test to if connector correctly handles bad data in que. Puts in 3 invalid invoices then runs batch job
     * The connector should not be stuck in processing
     *
     * CSAPCONC-450
     */
    @Test(groups = "concurAPI")
    public void badInvoiceProcessTest() {
        String token = createConnectorAccessToken();
        String id = concurInvoice("CA","USD","100","2020-09-20","PT-INVOICE-1","PT-INVOICE-1",
                "CA-BAD","00AEE75D874A48778F6D6C49AD049295","2033","Bank Fees","2","100","");
        String idTwo = concurInvoice("CA","USD","100","2020-09-20","PT-INVOICE-2","PT-INVOICE-2",
                "CA-BAD","00AEE75D874A48778F6D6C49AD049295","2033","Bank Fees","2","100","");
        String idThree = concurInvoice("CA","USD","100","2020-09-20","PT-INVOICE-3","PT-INVOICE-3",
                "CA-BAD","00AEE75D874A48778F6D6C49AD049295","2033","Bank Fees","2","100","");

        String batchId = runBatchJob(token);

        getJobStatus(batchId,token);
    }
}