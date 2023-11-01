package com.vertex.quality.connectors.tradeshift.tests.ui.buyer;

import com.vertex.quality.connectors.tradeshift.pages.TradeshiftBuyerSignInPage;

import com.vertex.quality.connectors.tradeshift.tests.ui.base.TradeshiftUIBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
public class TradeshiftConfigUITests extends TradeshiftUIBaseTest {

    /**
     * Test if we can login to the Tradeshift Dev environment
     *
     * CTRADESHI-360
     * */
    @Test(groups = {"tradeshift"})
    public void tradeshiftLoginTest(){
        TradeshiftBuyerSignInPage signInPage = new TradeshiftBuyerSignInPage(driver);
        signInPage.tradeshiftSignIn();
    }
}
