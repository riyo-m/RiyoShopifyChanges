package com.vertex.quality.connectors.dynamics365.nav.tests;

import com.vertex.quality.connectors.dynamics365.nav.components.NavCompanyInfoDialog;
import com.vertex.quality.connectors.dynamics365.nav.enumes.NavPopMessages;
import com.vertex.quality.connectors.dynamics365.nav.pages.*;
import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavSalesBasePage;
import com.vertex.quality.connectors.dynamics365.nav.tests.base.NavBaseTest;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * This Test Class represents Address Cleansing Tests for NAV
 */
public class NavAddressCleansingTests extends NavBaseTest
{

    public static final String taxCalculationAddress = "https://oseries9-final.vertexconnectors.com/vertex-ws/services/LookupTaxAreas80";
    public static final String addressValidation = "https://oseries9-final.vertexconnectors.com/vertex-ws/services/CalculateTax80";
    //Above URL's are swapped to check the error's during service call

    /**
     * CDNAV-436
     * Validate addresses with continue calc with Continue to Calc with invalid address cleansing OFF
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void invalidAddressCleansingOFFTest() {
        String addressLineOne = "Krishe Sapphire";
        String city = "Hyderabad";
        String state = "PA";
        String shortZip = "99999";
        String country = "US";
        String customerCode = "Test PA";
        String expectedZipCode="99999";

        NavSalesBasePage customerCard = new NavSalesBasePage(driver);

        //Login
        NavAdminHomePage homePage = initializeTestPageAndSignOn();

        //Navigating to Vertex Admin
        NavVertexAdminPage adminPage = homePage.navigatingToVertexAdminPage();

        //Updating to address cleansing OFF
        adminPage.updateAddressCleansingOff();
        adminPage.SavecloseAdminSection();

        //Navigate to Customer list page
        NavCustomersListPage customersListPage = homePage.navigateToCustomersListPage();
        customersListPage.filterCustomers(customerCode);

        //Adding Incorrect Customer Address to verify functionality
        fillInCustomerAddressInfo(addressLineOne, null, city, state, shortZip, country);
        NavCustomerCardPage cardPage=new NavCustomerCardPage(driver);
        cardPage.saveAndClose();
        customersListPage.filterCustomers(customerCode);

        String actualZipCode= cardPage.getZipCode();
        cardPage.saveAndClose();
        assertEquals(expectedZipCode,actualZipCode);

        //Navigate to vertex admin and enable Address cleansing
        homePage.searchAndNavigateToVertexAdminPage();
        adminPage.updateAddressCleansingOn();
        adminPage.SavecloseAdminSection();

        //Update customer to correct address
        fillInCustomerAddressInfo("2955 Market St. apt 2", null, "Philadelphia", "PA", "19104", "US");
        cardPage.saveAndClose();
    }
    /**
     * CDNAV-623
     * Validate address cleansing by enabling it on admin page, adding US address & verify XML
       @author Shruti Jituri
     */
    @Test(groups = { "D365_NAV_Smoke", "D365_NAV_Regression" })
      public void enablingAddressCleaningTest(){
        String customerCode = "Test PA";
        String expectedZipCode="19104-2817";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();

        //Navigating to Vertex Admin Page
        NavVertexAdminPage adminPage = homePage.navigatingToVertexAdminPage();

        //Enable Address Cleansing
        adminPage.updateAddressCleansingOn();
        adminPage.SavecloseAdminSection();

        //Navigate to Customer list page and validate functionality
        NavCustomersListPage customersListPage = homePage.navigateToCustomersListPage();
        customersListPage.filterCustomers(customerCode);
        fillInCustomerAddressInfo("2955 Market St. apt 2", null, "Philadelphia", "PA", "11111", "US");
        NavCustomerCardPage cardPage=new NavCustomerCardPage(driver);
        cardPage.saveAndClose();

        customersListPage.filterCustomers(customerCode);
        String actualZipCode= cardPage.getZipCode();
        cardPage.saveAndClose();
        assertEquals(expectedZipCode,actualZipCode);

        //Navigate to vertex admin and validate XML
        homePage.searchAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(customerCode);
        adminPage.filterxml("Address Cleanse Response");

        String xmlContents = adminPage.clickOnFirstAddressCleansingLinkAndGetTheXml("Address Cleanse Response");
        assertTrue(xmlContents.contains("<PostalCode>19104-2817</PostalCode>"), "Postal code Address Validation Failed");
    }
    /**
     * CDNAV-418
     * Min. Address Cleansing Confidence Factor
     * select confidence factor < 50,
     * select confidence factor > 50,
     * return multiple options and select the lowest
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void minimumAddressCleansingConfidenceFactorTest() {
        String customerCode = "Test PA";
        NavSalesBasePage customerCard = new NavSalesBasePage(driver);
        NavAdminHomePage homePage = initializeTestPageAndSignOn();

        //Navigating to Vertex Admin Page and update confidence factor > 50
        NavVertexAdminPage adminPage = homePage.navigatingToVertexAdminPage();
        adminPage.updateConfidenceFactor("75");
        adminPage.updateAddressCleansingOn();
        adminPage.SavecloseAdminSection();

        NavCustomersListPage customersListPage = homePage.navigateToCustomersListPage();
        customersListPage.filterCustomers(customerCode);

        //Provide incorrect address to verify
        fillInCustomerAddressInfo("400 Simpson Dr", null, "Linfield", "PA", "19720", "US");
        customerCard.saveAndClose();
        assertEquals(customerCard.getAddressValidationPopUpMessage(), NavPopMessages.ADDRESS_CLEANSING_POPUP_MESSAGE.value);
        customerCard.dialogBoxClickYes();

        //Navigate to admin Page and validate XML
        homePage.searchAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(customerCode);
        adminPage.filterxml("Address Cleanse Response");
        String xmlStr = adminPage.clickOnFirstAddressCleansingLinkAndGetTheXml("Address Cleanse Response");
        assertTrue(xmlStr.contains("<AddressCleansingResultMessage type=\"FAULT\" code=\"E412\">Primary name not found in directory.</AddressCleansingResultMessage>"), "Address Cleansing result Validation Failed");
        adminPage.SavecloseAdminSection();

        //Update customer with original address
        customersListPage.filterCustomers(customerCode);
        fillInCustomerAddressInfo("2955 Market St. apt 2", null, "Philadelphia", "PA", "19101", "US");
        adminPage.SavecloseAdminSection();

        //update confidence factor < 50
        homePage.navigatingToVertexAdminPage();
        adminPage.updateConfidenceFactor("25");
        adminPage.SavecloseAdminSection();

        //Provide incorrect address to verify
        customersListPage.filterCustomers(customerCode);
        fillInCustomerAddressInfo("400 Simpson Dr", null, "Chester Springs", "PA", "19720", "US");
        customerCard.saveAndClose();

        //Putting Back the confidence Factor
        homePage.navigatingToVertexAdminPage();
        adminPage.updateConfidenceFactor("75");
        adminPage.SavecloseAdminSection();

        //Filter customer and Update expected address
        customersListPage.filterCustomers(customerCode);
        fillInCustomerAddressInfo("2955 Market St. apt 2", null, "Philadelphia", "PA", "19101", "US");
        adminPage.SavecloseAdminSection();
    }

    /**
     * CDNAV-689
     * Verify exceptions notification for Company Information Page
     *
     * @author Shruti Jituri
     */
    @Test(groups = { "D365_NAV_Regression" })
    public void invalidAddressErrorTest() {
        String incorrectZip="19015";
        String correctZip="31772";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavVertexAdminPage adminPage = homePage.navigatingToVertexAdminPage();
        NavSalesBasePage basePage=new NavSalesBasePage(driver);
        //swap the URL of tax calculation and address validation
        adminPage.fillInURLAddress(taxCalculationAddress,addressValidation);
        adminPage.updateAddressCleansingOn();
        adminPage.SavecloseAdminSection();

        //Navigate to company information page and modify zip
        NavCompanyInfoDialog companyInfo=homePage.navigateToCompanyInfo();
        companyInfo.enterWrongZip(incorrectZip);
        adminPage.SavecloseAdminSection();
        //Verify the error
        String actualDialogMessage=companyInfo.getDialogBoxText();
        assertTrue(actualDialogMessage.contains("Could not call request"), "Message validation failed");
        basePage.dialogBoxClickOk();
        homePage.navigateToCompanyInfo();
        //Update back the correct zip
        companyInfo.enterWrongZip(correctZip);
        adminPage.SavecloseAdminSection();
        basePage.dialogBoxClickOk();
        homePage.navigatingToVertexAdminPage();

        //Update back the correct URL
        adminPage.fillInURLAddress(addressValidation, taxCalculationAddress);
        adminPage.SavecloseAdminSection();
    }

    /**
     * CDNAV-690
     * Verify exceptions notification for Customers in making call to service
     *
     * @author Shruti Jituri
     */
    @Test(groups = {"D365_NAV_Regression"})
    public void invalidAddressErrorForCustomersTest() {
        String customerCode = "Test_NJ";
        String incompleteZipCode = "08817";

        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        NavVertexAdminPage adminPage = homePage.navigatingToVertexAdminPage();
        NavCustomerCardPage cardPage = new NavCustomerCardPage(driver);

        //swap the URL of tax calculation and address validation on admin page
        adminPage.fillInURLAddress(taxCalculationAddress, addressValidation);
        adminPage.updateAddressCleansingOn();
        adminPage.SavecloseAdminSection();
        //Navigate to Customer card page and update zip
        NavCustomersListPage customersListPage = homePage.navigateToCustomersListPage();
        customersListPage.filterCustomers(customerCode);
        cardPage.enterAddressZip(incompleteZipCode);
        cardPage.saveAndClose();
        //Validate the error messages and original zip
        String alertMessage = cardPage.getErrorMessage();
        assertTrue(alertMessage.contains("Could not call request"), "Error Validation Failed");
        cardPage.revertChanges();
        String actualZipCode = cardPage.getZipCode();
        assertTrue(actualZipCode.contains("08817-4677"), "Expected Zip Code Validation Failed for Test_NJ");
        cardPage.saveAndClose();
        //Update back the correct URL
        homePage.navigatingToVertexAdminPage();
        adminPage.fillInURLAddress(addressValidation, taxCalculationAddress);
        adminPage.SavecloseAdminSection();
    }
}
