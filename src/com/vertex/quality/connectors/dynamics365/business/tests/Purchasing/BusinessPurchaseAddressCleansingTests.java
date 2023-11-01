package com.vertex.quality.connectors.dynamics365.business.tests.Purchasing;

import com.vertex.quality.connectors.dynamics365.business.pages.BusinessAdminHomePage;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessVertexAdminPage;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.BusinessVendorListsPage;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.BusinessVendorPage;
import com.vertex.quality.connectors.dynamics365.business.tests.base.BusinessBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.Assert;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Listeners(TestRerunListener.class)
public class BusinessPurchaseAddressCleansingTests extends BusinessBaseTest {
    BusinessAdminHomePage homePage;
    boolean addressCleanse = true;

    @BeforeMethod(alwaysRun = true)
    public void setupTest( )
    {
        BusinessVertexAdminPage adminPage = homePage.navigateToVertexAdminPage();
        //Post Admin Setup
        adminPage.updateAddressCleansingOn();
        adminPage.clickBackAndSaveArrow();
        addressCleanse = true;
    }

    @AfterMethod(alwaysRun = true)
    public void teardownTest( )
    {
        if (!addressCleanse) {
            //Enable Accounts Receivable
            BusinessVertexAdminPage adminPage = homePage.navigateToVertexAdminPage();
            //Post Admin Setup
            homePage.searchForAndNavigateToVertexAdminPage();
            adminPage.updateAddressCleansingOn();
            adminPage.clickBackAndSaveArrow();
        }
    }

    /**
     *  CDBC-925
     *  Creates a vendor with a zip code missing the final 4 digits,
     *  verify the last 4 digits are appended when customer saved
     *  Deletes customer after address is verified
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_AP"}, retryAnalyzer = TestRerun.class)
    public void createVendorAddressCleansingForZipCodeTest() {
        String vendorName="TestAddressCleanse";
        String vendorClass="VENDOR";
        String addressLineOne = "2955 Market St Ste 2";
        String city = "Philadelphia";
        String state = "PA";
        String shortZip = "19104";
        String country = "US";
        String taxAreaCode = "VERTEX";
        String expectedZipCode = "19104-2817";

        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessVertexAdminPage adminPage = homePage.navigateToVertexAdminPage();
        adminPage.updateAddressCleansingOn();
        adminPage.clickBackAndSaveArrow();
        addressCleanse = true;

        //Navigate to Vendor Page and fill in Address
        BusinessVendorListsPage vendorsListPage=homePage.navigateToVendorsListPage();
        BusinessVendorPage vendorPage=new BusinessVendorPage(driver);
        vendorsListPage.clickNew();
        vendorsListPage.clickBusinessToBusinessVendor();
        vendorsListPage.dialogBoxClickOk();
        vendorPage.enterVendorNameOnVendorPage(vendorName);
        vendorPage.enterVertexVendorClass(vendorClass);
        fillInVendorAddressInfo(addressLineOne, null, city, state, shortZip, country);

        //Navigate to Invoice category and update tax area code
        vendorPage.openInvoicingCategory();
        vendorPage.enterTaxAreaCode(taxAreaCode);
        vendorPage.openInvoicingCategory();
        vendorPage.openReadOnlyMode();

        //Verify if address was cleansed and if extra 4 digits were added to zipcode
        String actualZipCode=vendorPage.geZipCode();
        assertEquals(actualZipCode, expectedZipCode);
        homePage.searchForAndNavigateToVertexAdminPage();

        //Open XML and verify the tax area and zip code
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(vendorName);
        adminPage.filterxml("Address Cleanse Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains(" <PostalCode>19104-2817</PostalCode>"), "Address cleansing failed with Zip");
        vendorPage.clickBackAndSaveArrow();
        vendorPage.deleteDocument();
    }
    @BeforeMethod(alwaysRun = true)
    public void setUpBusinessMgmt(){
        String role="Business Manager";
        homePage = new BusinessAdminHomePage(driver);
        String verifyPage=homePage.verifyHomepageHeader();
        if(!verifyPage.contains(role)){

            //navigate to select role as Business Manager
            homePage.selectSettings();
            homePage.navigateToManagerInSettings(role);
        }
    }

}
