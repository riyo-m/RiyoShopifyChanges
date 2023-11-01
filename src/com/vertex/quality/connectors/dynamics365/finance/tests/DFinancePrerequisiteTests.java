package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.testng.Assert.assertTrue;


/**
 * @author Vishwa
 * These are all Prerequisite tests, only one time need to run in any environment
 */
@Listeners(TestRerunListener.class)
public class DFinancePrerequisiteTests extends DFinanceBaseTest {

    /**
     * @TestCase
     * @Author Vishwa
     * CD365-697
     * @Description - Creating Cusotmer only one time need to run in any environment
     */

    @Test(groups = {"FO_prerequisite", "FO_General_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
    public void createCustomerTest() {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage;
        DFinanceAllSalesOrdersPage salesOrderPage;

        //================Data Declaration ===========================

        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHsS");
        Calendar cal = Calendar.getInstance();
        final String customerAccount = "D_US_PA1";
        final String customerType = "Organization";
        final String customerName = "PACS ";
        final String customerGroup = "10";

        String streetAddress = Address.Pittsburgh.addressLine1;
        String city = Address.Pittsburgh.city;
        String state = Address.Pittsburgh.state.abbreviation;
        String zipCode = Address.Pittsburgh.zip5;
        String country = Address.Pittsburgh.country.iso3code;

        //================script implementation=======================

        // Navigate to All Customers Page
        DFinanceAllCustomersPage allCustomersPage = homePage.navigateToAllCustomersPage();

        // Create a new Customer
        DFinanceCreateCustomerPage createCustomerPage = allCustomersPage.clickNewCustomerButton();

        // set Customer details
        createCustomerPage.setCustomerDetails(customerAccount, customerType, customerName, customerGroup);

        // verify default country value
        String actualCountry = createCustomerPage.getCountry();
        assertTrue(actualCountry.contains(country),
                String.format("Expected Country: %s, but actual Country: %s", country, actualCountry));

        // set Address details
        createCustomerPage.setCustomerAddressDetails(streetAddress, city, state, country, zipCode);

        // click Save button
        createCustomerPage.clickSaveButton();
    }
}
