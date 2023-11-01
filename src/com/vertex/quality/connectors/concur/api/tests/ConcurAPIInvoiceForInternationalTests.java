package com.vertex.quality.connectors.concur.api.tests;
import com.vertex.quality.connectors.concur.api.tests.base.concurAPIBaseTest;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.hasToString;

/**
 * Concur API International Tests
 *
 * @author alews
 */
public class ConcurAPIInvoiceForInternationalTests extends concurAPIBaseTest{

    /**
     * US to CAN Invoice test
     *
     * CSAPCONC-424
     */
    @Test
    public void concurAPIInvoiceUSToCANTest(){
        String id = concurInvoice("US","USD","100","2020-08-24","546978132","INV-424","CAN-424",
                "B0180909D10246568E2B050ED2EEB306","2033","Bank Fees","1","100","V8T 4R4");
        runBatchJob();
        checkForCorrectTax(id,"0.07000000");
    }

    /**
     * CAN to USA Invoice test
     *
     * CSAPCONC-425
     */
    @Test
    public void concurAPIInvoiceCANToUSATest(){
        String id = concurInvoice("CAN","CAD","100","2020-08-24","546978546","INV-425","USA-425",
                "B1B36CE8EAA94AB38BD78B688E67B6F5","2033","Bank Fees","1","100","17325");
        runBatchJob();
        checkForCorrectTax(id,"0.06000000");
    }
}