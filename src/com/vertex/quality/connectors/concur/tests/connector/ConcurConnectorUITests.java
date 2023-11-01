package com.vertex.quality.connectors.concur.tests.connector;

import com.vertex.quality.connectors.concur.pages.misc.VertexConfigConcurHomePage;
import com.vertex.quality.connectors.concur.tests.base.VertexConcurBaseTest;
import org.testng.annotations.Test;

/**
 * tests for the concur connector ui
 *
 * @author jciccone
 */
public class ConcurConnectorUITests  extends VertexConcurBaseTest {
    //No Ticket
    /**
     * Test to ensure a user can make custom field mappings in concur
     * */
    @Test
    public void customFieldMappingTest() {
        VertexConfigConcurHomePage homePage = new VertexConfigConcurHomePage(driver);

        //SignIn to vertex Concur Home Page
        vertexConcurSignIn();
        homePage.clickConfiguration();
        homePage.clickCustomFieldMapping();
        homePage.addFieldMapping();
        homePage.deleteFieldMapping();
    }


}
