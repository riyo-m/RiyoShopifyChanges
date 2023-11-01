package com.vertex.quality.connectors.dynamics365.business.tests;

import com.vertex.quality.connectors.dynamics365.business.pages.BusinessAdminHomePage;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessCustomersListPage;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessVertexCustomerClassPage;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessSalesBasePage;
import com.vertex.quality.connectors.dynamics365.business.tests.base.BusinessBaseTest;
import org.testng.annotations.BeforeMethod;

/**
 * Creating Customers and Tax groups Under Prerequisites
 * @author bhikshapathi
 */
public class BusinessPrerequisiteTests extends BusinessBaseTest
{
    BusinessAdminHomePage homePage;
    /**
     * Creating Customers Under Prerequisites
     * @author bhikshapathi
     */
    public void createCustomersTest()
    {
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        homePage.navigateToCustomersListPage();
        createNewCustomer("Test Philad3","2955 Market St. apt 2", null, "Philadelphia", "PA", "19104", "US","VERTEX","C00080");
        createNewCustomer("Naomi Nagata","2955 Market St. apt 2", null, "Philadelphia", "PA", "19104", "US","VERTEX","C00280");
        createNewCustomer("Test_DE","100 N Orange St", null, "Wilmington", "DE", "19801", "US","VERTEX","C00520");
        createNewCustomer("Cust Sam WA","22833 NE 8th St", null, "Sammamish", "WA", "98074", "US","VERTEX","C00750");
        createNewCustomer("Test PR","100 Calle Brumbaugh Apt 3", null, "San Juan", "PR", "00901-2620", "US","VERTEX","C00730");
        createNewCustomer("Test C365BC 224","6525 Glacier Hwy", null, "Juneau", "AK", "99801-7905", "US","VERTEX","C00510");
        createNewCustomer("ModifiedOriginState","100 Universal City Plz", null, "Universal City", "CA", "91608-1002", "US","VERTEX","C00570");
        createNewCustomer("VERTEX TAX AREA ISSUE","2400 Route 9", null, "Fishkill", "NY", "12524-2200", "US","VERTEX","C00300");
        createNewCustomer("Test Vertex Customer","10599 Skyreach Rd", null, "Highlands Ranch", "CO", "80126-5635", "US","VERTEX","C00010");
        createNewCustomer("NoTaxpayer","1440 Monroe St", null, "Madison", "PA", "53711-2051", "US","VERTEX","C00530");
        createNewCustomer("Ticket42TEST","378 Mineral Springs Rd", null, "Cobleskill", "NY", "12043", "US","VERTEX","C00310");
        createNewCustomer("Canada Customer","400 Simpson Dr", null, "Chester Springs", "PA", "19425-9546", "US","VERTEX","C00450");
        createNewCustomer("Test Phila","2955 Market St. apt 2", null, "Philadelphia", "PA", "19104-2817", "US","VERTEX","C00470");
        createNewCustomer("test1234","400 Simpson Dr", null, "Chester Springs", "PA", "19425-9546", "US","VERTEX","C00210");
        createNewCustomer("Upgrade16 cust","101 Granite Ln", null, "Chester Springs", "PA", "19425-3823", "US","VERTEX","C00020");
        createNewCustomer("cust_NOVERTEX","400 Simpson Dr", null, "Chester Springs", "PA", "19425", "US","ATLANTA, GA","C00410");
        createNewCustomer("Geo Mason","9525 Braddock Rd", null, "Fairfax", "VA", "22032-2539", "US","VERTEX","C00040");
        createNewCustomer("Change Discount","36 Railroad St", null, "Royersford", "PA", "19468-3518", "US","VERTEX","C00670");
    }

    /**
     * Creating Tax groups Under Prerequisites
     * @author bhikshapathi
     */
    public void createTaxGroupCodesTest()
    {
        BusinessSalesBasePage basePage= new BusinessSalesBasePage(driver);
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        homePage.searchForAndNavigateToTaxGroupsPage();
        createTaxGroup("DEFAULT","DEFAULT");
        createTaxGroup("FURNITURETEST","testing");
        createTaxGroup("PRODUCTCLASSEXEMPT1","cloud test");
        basePage.clickBackAndSaveArrow();
    }

    /**
     * Creating New Customer Class
     */
    public void createCustomerClassTest()
    {
        BusinessSalesBasePage basePage= new BusinessSalesBasePage(driver);
        BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
        BusinessVertexCustomerClassPage customerClassPage = homePage.searchForAndNavigateToVertexCustomerClassPage();
        customerClassPage.createCustomerClass("CLASS_TEST","class for automation");
        customerClassPage.createCustomerClass("CUST_CLAS1","testclass");
        customerClassPage.createCustomerClass("CUST_TEST","o-series mapped for 100 % exempt");
        customerClassPage.createCustomerClass("TEST_AUTOM","Create New Customer Class Test");
        basePage.clickBackAndSaveArrow();
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
