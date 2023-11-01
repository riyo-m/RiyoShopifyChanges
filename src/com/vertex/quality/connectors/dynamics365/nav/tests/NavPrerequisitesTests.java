package com.vertex.quality.connectors.dynamics365.nav.tests;

import com.vertex.quality.connectors.dynamics365.nav.pages.NavAdminHomePage;
import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavSalesBasePage;
import com.vertex.quality.connectors.dynamics365.nav.tests.base.NavBaseTest;

/**
 * Creating Customers and Tax groups Under Prerequisites
 * @author bhikshapathi
 */
public class NavPrerequisitesTests extends NavBaseTest
{ /**
 * Creating Customers Under Prerequisites
 * @author bhikshapathi
 */
  public void createCustomersTest()
{
    NavSalesBasePage customerCard= new NavSalesBasePage(driver);
    NavAdminHomePage homePage = initializeTestPageAndSignOn();
    homePage.navigateToCustomersListPage();
    createNewCustomer("Test Phila","2955 Market St. apt 2", null, "Philadelphia", "PA", "19104", "US","VERTEX","MAIN");
    customerCard.clickBackAndSaveArrow();
    createNewCustomerForSecondTime("Test Philad","2955 Market St. apt 2", null, "Philadelphia", "PA", "19104", "US","VERTEX","MAIN");
    customerCard.clickBackAndSaveArrow();
    createNewCustomerForSecondTime("Test_DE","1200 N. Dupont Highway", null, "Dover", "DE", "19901", "US","VERTEX","MAIN");
    customerCard.clickBackAndSaveArrow();
    createNewCustomerForSecondTime("Cust Sam WA","22833 NE 8th St", null, "Sammamish", "WA", "98074", "US","VERTEX","MAIN");
    customerCard.clickBackAndSaveArrow();
    createNewCustomerForSecondTime("Test PR","100 Calle Brumbaugh Apt 3", null, "San Juan", "PR", "00901-2620", "US","VERTEX","MAIN");
    customerCard.clickBackAndSaveArrow();
    createNewCustomerForSecondTime("Test C365BC 224","6525 Glacier Hwy", null, "Juneau", "AK", "99801-7905", "US","VERTEX","MAIN");
    customerCard.clickBackAndSaveArrow();
    createNewCustomerForSecondTime("ModifiedOriginState","100 Universal City Plz", null, "Universal City", "CA", "91608-1002", "US","VERTEX","MAIN");
    customerCard.clickBackAndSaveArrow();
    createNewCustomerForSecondTime("VERTEX TAX AREA ISSUE","2400 Route 9", null, "Fishkill", "NY", "12524-2200", "US","VERTEX","MAIN");
    customerCard.clickBackAndSaveArrow();
    createNewCustomerForSecondTime("Naomi Nagata","2955 Market St. apt 2", null, "Philadelphia", "PA", "19104", "US","VERTEX","MAIN");
    customerCard.clickBackAndSaveArrow();
    createNewCustomerForSecondTime("VERTEX TAX AREA ISSUE","2400 Route 9", null, "Fishkill", "NY", "12524-2200", "US","VERTEX","MAIN");
    customerCard.clickBackAndSaveArrow();
    createNewCustomerForSecondTime("Test Vertex Customer","10599 Skyreach Rd", null, "Highlands Ranch", "CO", "80126-5635", "US","VERTEX","MAIN");
    customerCard.clickBackAndSaveArrow();
    createNewCustomerForSecondTime("NoTaxpayer","1440 Monroe St", null, "Madison", "PA", "53711-2051", "US","VERTEX","MAIN");
    customerCard.clickBackAndSaveArrow();
    createNewCustomerForSecondTime("Ticket42TEST","378 Mineral Springs Rd", null, "Cobleskill", "NY", "12043", "US","VERTEX","MAIN");
    customerCard.clickBackAndSaveArrow();
    createNewCustomerForSecondTime("Canada Customer","400 Simpson Dr", null, "Chester Springs", "PA", "19425-9546", "US","VERTEX","MAIN");
    customerCard.clickBackAndSaveArrow();
    createNewCustomerForSecondTime("Test Phila","2955 Market St. apt 2", null, "Philadelphia", "PA", "19104-2817", "US","VERTEX","MAIN");
    customerCard.clickBackAndSaveArrow();
    createNewCustomerForSecondTime("test1234","400 Simpson Dr", null, "Chester Springs", "PA", "19425-9546", "US","VERTEX","MAIN");
    customerCard.clickBackAndSaveArrow();
    createNewCustomerForSecondTime("Upgrade16 cust","101 Granite Ln", null, "Chester Springs", "PA", "19425-3823", "US","VERTEX","MAIN");
    customerCard.clickBackAndSaveArrow();
    createNewCustomerForSecondTime("cust_NOVERTEX","400 Simpson Dr", null, "Chester Springs", "PA", "19425", "US","ATLANTA, GA","MAIN");
    customerCard.clickBackAndSaveArrow();
    createNewCustomerForSecondTime("Geo Mason","9525 Braddock Rd", null, "Fairfax", "VA", "22032-2539", "US","VERTEX","MAIN");
    customerCard.clickBackAndSaveArrow();
   }

    /**
     * Creating Tax groups Under Prerequisites
     * @author bhikshapathi
     */
    public void createTaxGroupCodesTest()
    {
        NavSalesBasePage customerCard= new NavSalesBasePage(driver);
        NavAdminHomePage homePage = initializeTestPageAndSignOn();
        homePage.searchAndNavigateToTaxGroupsPage();
        createTaxGroup("DEFAULT","DEFAULT");
        createTaxGroup("FREIGHT","FREIGHT");
        createTaxGroup("FURNITURE","Taxable Olympic Furniture");
        createTaxGroup("FURNITURETEST","testing");
        createTaxGroup("LABOR","Labor on Job");
        createTaxGroup("MATERIALS","Taxable Raw Materials");
        createTaxGroup("PRODUCTCLASSEXEMPT","cloud test");
        createTaxGroup("SUPPLIES","Taxable Olympic Materials");
        customerCard.saveAndClose();
    }
}
