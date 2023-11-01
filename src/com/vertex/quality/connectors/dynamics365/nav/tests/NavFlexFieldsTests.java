package com.vertex.quality.connectors.dynamics365.nav.tests;

import com.vertex.quality.connectors.dynamics365.nav.pages.NavAdminHomePage;
import com.vertex.quality.connectors.dynamics365.nav.pages.NavFlexFieldsPage;
import com.vertex.quality.connectors.dynamics365.nav.pages.NavVertexAdminPage;
import com.vertex.quality.connectors.dynamics365.nav.tests.base.NavBaseTest;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class NavFlexFieldsTests extends NavBaseTest
{
    /**
     * CDNAV-415
     * Tests creating new Account (Customer) flex field with the Text type
     * Deletes the flex field after the test
     */
    @Test(groups = { "D365_NAV_Smoke", "D365_NAV_Regression" })
    public void createNewAccountTextTypeTest( )
    {
        String flexSource = "Account (Customer)";
        String flexType = "Text";
        String flexId = "7";
        String flexValue = "CFDI Relation";
        String expectedRowText = String.format("%1$s %2$s %3$s %4$s", flexSource, flexType, flexId, flexValue);

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavVertexAdminPage adminPage = homePage.navigatingToVertexAdminPage();
        adminPage.openFlexFieldsCategory();

        adminPage.flexFieldCreateNew();

        fillInNewFlexFieldInfo(flexSource, flexType, flexId, flexValue);
        adminPage.closeAdminSection();

        WebElement row = adminPage.getFlexFieldRow(flexSource, flexType, flexId, flexValue);

        assertTrue(null != row);

        String rowText = row.getText();
        assertEquals(rowText, expectedRowText);
        adminPage.deleteFlexFieldRow(row);
    }
    /**
     * Tests creating new Account (Customer) flex field with the Numeric type
     * Deletes the flex field after the test
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void createNewAccountNumericTypeTest( )
    {
        String flexSource = "Account (Customer)";
        String flexType = "Numeric";
        String flexId = "2";
        String flexValue = "Last Statement No.";
        String expectedRowText = String.format("%1$s %2$s %3$s %4$s", flexSource, flexType, flexId, flexValue);

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavVertexAdminPage adminPage = homePage.navigatingToVertexAdminPage();
        adminPage.openFlexFieldsCategory();

        adminPage.flexFieldCreateNew();

        fillInNewFlexFieldInfo(flexSource, flexType, flexId, flexValue);
        adminPage.closeAdminSection();

        adminPage.filterFlexFields(flexType);
        WebElement row = adminPage.getFlexFieldRow(flexSource, flexType, flexId, flexValue);
        assertTrue(null != row);

        String rowText = row.getText();
        assertEquals(rowText, expectedRowText);

        adminPage.deleteFlexFieldRow(row);
    }

    /**
     * CDNAV-416
     * Tests creating new Account (Customer) flex field with the Date type
     * Deletes the flex field after the test
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void createNewAccountDateTypeTest( )
    {
        String flexSource = "Account (Customer)";
        String flexType = "Date";
        String flexId = "1";
        String flexValue = "Last Modified Date Time";
        String expectedRowText = String.format("%1$s %2$s %3$s %4$s", flexSource, flexType, flexId, flexValue);

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavVertexAdminPage adminPage = homePage.navigatingToVertexAdminPage();
        adminPage.openFlexFieldsCategory();

        adminPage.flexFieldCreateNew();

        fillInNewFlexFieldInfo(flexSource, flexType, flexId, flexValue);
        adminPage.closeAdminSection();

        adminPage.filterFlexFields(flexType);
        WebElement row = adminPage.getFlexFieldRow(flexSource, flexType, flexId, flexValue);
        assertTrue(null != row);

        String rowText = row.getText();
        assertEquals(rowText, expectedRowText);

        adminPage.deleteFlexFieldRow(row);
    }

    /**
     * CDNAV-414
     * Tests creating new Constant Value flex field with the Date type
     * Deletes the flex field after the test
     */
    @Test(groups = {"D365_NAV_Regression" })
    public void createNewConstantValueDateTypeTest( )
    {
        String flexSource = "Constant Value";
        String flexType = "Date";
        String flexId = "1";
        String flexValue = getTransactionDate();
        System.out.println(flexValue);
        String expectedRowText = String.format("%1$s %2$s %3$s %4$s", flexSource, flexType, flexId, flexValue);

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavVertexAdminPage adminPage = homePage.navigatingToVertexAdminPage();
        adminPage.openFlexFieldsCategory();

        adminPage.flexFieldCreateNew();

        fillInNewFlexFieldInfo(flexSource, flexType, flexId, flexValue);
        adminPage.closeAdminSection();

        adminPage.filterFlexFields(flexType);
        WebElement row = adminPage.getFlexFieldRow(flexSource, flexType, flexId, flexValue);
        assertTrue(null != row);

        String rowText = row.getText();
        assertEquals(rowText, expectedRowText);

        adminPage.deleteFlexFieldRow(row);
    }

    /**
     * CDNAV-412
     * Tests the error that should appear when creating a flex field
     * with an invalid ID
     */
    @Test(groups = {"D365_NAV_Regression" })
    public void flexFieldIdInUseErrorTest( )
    {
        String flexSource = "Quote/Order/Invoice Line";
        String flexType = "Date";
        String flexId = "2";
        String expectedErrorMessage = "ID 2 is already in use. Please choose a different ID";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavVertexAdminPage adminPage = homePage.navigatingToVertexAdminPage();
        adminPage.openFlexFieldsCategory();

        NavFlexFieldsPage flexFieldsPage = adminPage.flexFieldCreateNew();

        flexFieldsPage.selectFlexFieldSource(flexSource);
        flexFieldsPage.selectFlexFieldType(flexType);
        flexFieldsPage.inputFlexFieldId(flexId);

        String actualErrorMessage = flexFieldsPage.getErrorMessage();
        assertEquals(actualErrorMessage, expectedErrorMessage);
    }
}
