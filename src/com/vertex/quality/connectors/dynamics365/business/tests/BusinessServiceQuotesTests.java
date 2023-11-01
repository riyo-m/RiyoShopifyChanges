package com.vertex.quality.connectors.dynamics365.business.tests;

import com.vertex.quality.connectors.dynamics365.business.enums.BusinessMainMenuNavTabs;
import com.vertex.quality.connectors.dynamics365.business.pages.*;
import com.vertex.quality.connectors.dynamics365.business.tests.base.BusinessBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
@Listeners(TestRerunListener.class)
public class BusinessServiceQuotesTests extends BusinessBaseTest {
    BusinessAdminHomePage homePage;
    BusinessServiceHomePage servicePage;

    /**
     * CDBC-1170
     * Create Service Quote and verify taxes
     * @author Shruti
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_ServiceMgmt", "D365_Business_Central_Service_Regression","D365_Business_Central_Smoke"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxForServiceQuoteTest(){
        String customerNo="TestingService_PA";
        String serviceItemNo="1009";
        String itemNumber = "1908-S";
        String quantity = "1";
        String expectedTax="20.68";
        String expectedAmountTax="0.59";
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();

        //enable service management toggle
        adminPage.updateServiceManagementOn();
        adminPage.clickBackAndSaveArrow();

        //select Service Quote in service management menu
        BusinessServiceQuotesListPage serviceQuotes=servicePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SERVICE_MANAGEMENT.value,"Service Quotes" );
        BusinessServiceQuotesPage serviceQuote = serviceQuotes.pageNavMenu.clickNew();
        serviceQuote.fillInNumberForService();
        fillInServiceQuotesGeneralInfo(serviceQuote,customerNo);
        serviceQuote.expandTable();
        serviceQuote.fillInServiceTableInfo("1008",1);
        serviceQuote.fillInServiceTableInfo(serviceItemNo,2);
        serviceQuote.exitExpandTable();
        String quoteNumber = serviceQuote.getCurrentSalesNumber();
        BusinessServiceItemPage serviceItemSheet=serviceQuote.navigateToServiceItemWorksheet();
        serviceItemSheet.expandServiceItemWorksheet();
        serviceItemSheet.fillInItemsTableInfoForService("Resource","KATHERINE", quantity,null,1);
        serviceQuote.activateRowForServiceOrder(2, 2);
        serviceItemSheet.fillInItemsTableInfoForService("Item",itemNumber, quantity,null,2);
        serviceQuote.activateRowForServiceOrder(3, 2);
        serviceItemSheet.fillInItemsTableInfoForService("G/L Account","10300", quantity,null,3);
        serviceQuote.activateRowForServiceOrder(4, 1);
        serviceItemSheet.fillInItemsTableInfoForService("Cost","MILEAGE", quantity,null,4);

        //Verify Total tax on Service Item Worksheet
        String totalTaxOnServiceItemSheet=serviceItemSheet.getTotalTaxAmount();
        assertEquals(totalTaxOnServiceItemSheet,expectedTax);
        serviceItemSheet.activateRow(1);
        String amountIncludingTax=serviceItemSheet.amountTax();
        assertEquals(amountIncludingTax,expectedAmountTax);

        //open vertex tax details
        serviceMgmtVertexTaxDetails(serviceItemSheet);
        List<String> taxAmountValues = new ArrayList<String>();
        taxAmountValues.add("9.24");
        taxAmountValues.add("11.40");
        taxAmountValues.add("0.00");
        taxAmountValues.add("0.04");
        List<String> taxAmounts = serviceQuote.getTaxAmountForEachServiceLine("9.24", "11.40", "0.00", "0.04");
        assertEquals(taxAmounts, taxAmountValues);
        serviceItemSheet.serviceMgmtItemSheetCloseButton();
        serviceItemSheet.closeItemSheet();

        //Click item 1008 and go to service item worksheet
        serviceItemSheet = serviceQuote.navigateToServiceItemWorksheet();
        serviceItemSheet.fillInItemsTableInfoForService("Resource","KATHERINE", quantity,null,1);
        serviceQuote.activateRowForServiceOrder(2, 2);
        serviceItemSheet.fillInItemsTableInfoForService("Item","1900-S", quantity,null,2);
        serviceQuote.activateRowForServiceOrder(3, 2);
        serviceItemSheet.fillInItemsTableInfoForService("G/L Account","10600", "2.5",null,3);
        serviceQuote.activateRowForServiceOrder(4, 1);
        serviceItemSheet.fillInItemsTableInfoForService("Cost","MILEAGE", quantity,null,4);

        //Verify Total tax on Service Item Worksheet
        totalTaxOnServiceItemSheet=serviceItemSheet.getTotalTaxAmount();
        assertEquals(totalTaxOnServiceItemSheet,"20.85");
        serviceItemSheet.activateRow(1);
        amountIncludingTax=serviceItemSheet.amountTax();
        assertEquals(amountIncludingTax,expectedAmountTax);

        //open vertex tax details
        serviceMgmtVertexTaxDetails(serviceItemSheet);
        List<String> taxAmountValues2 = new ArrayList<String>();
        taxAmountValues2.add("9.24");
        taxAmountValues2.add("11.57");
        taxAmountValues2.add("0.00");
        taxAmountValues2.add("0.04");
        List<String> taxAmounts2 = serviceQuote.getTaxAmountForEachServiceLine("9.24", "11.57", "0.00", "0.04");
        assertEquals(taxAmounts2, taxAmountValues2);
        serviceItemSheet.serviceMgmtItemSheetCloseButton();
        serviceItemSheet.closeItemSheet();

        //Navigate to Quote then Service Lines and select filter as All and verify Sales Tax
        serviceQuote.clickQuoteAndServiceLines();
        serviceQuote.selectAndFilterLinesFilter("All", "All");
        totalTaxOnServiceItemSheet=serviceItemSheet.getTotalTaxAmount();
        assertEquals(totalTaxOnServiceItemSheet,"41.52");

        //Verify Vertex Tax Details
        serviceMgmtVertexTaxDetails(serviceItemSheet);
        List<String> taxAmountValues3 = new ArrayList<String>();
        taxAmountValues3.add("9.23");
        taxAmountValues3.add("11.40");
        taxAmountValues3.add("0.00");
        taxAmountValues3.add("0.04");
        List <String> taxAmounts3 = serviceQuote.getTaxAmountForEachServiceLine("9.23", "11.40", "0.00", "0.04");
        assertEquals(taxAmounts3, taxAmountValues3);
        serviceItemSheet.clickBackArrow();

        //Then select Per Selected Service Item Line and verify tax
        serviceQuote.selectAndFilterLinesFilter("All", "Per Selected Service Item Line");
        totalTaxOnServiceItemSheet=serviceItemSheet.getTotalTaxAmount();
        assertEquals(totalTaxOnServiceItemSheet,"20.85");
        serviceItemSheet.closeItemSheet();

        //Select 2nd item and navigate back to Service Lines, select Per Selected Service Item Line and verify tax
        serviceQuote.selectLineWithServiceItemNumber("1009");
        serviceQuote.clickQuoteAndServiceLines();
        serviceQuote.selectAndFilterLinesFilter("All","Per Selected Service Item Line");
        totalTaxOnServiceItemSheet=serviceItemSheet.getTotalTaxAmount();
        assertEquals(totalTaxOnServiceItemSheet,"20.67");
        serviceItemSheet.closeItemSheet();

        //Navigate to XML and validate total tax
        adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(quoteNumber);
        adminPage.filterxml("Tax Calc Response");
        String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
        assertTrue(xmlStr.contains("<TotalTax>41.52</TotalTax>"), "Total Tax  Validation Failed");
    }

    /**
     * @TestCase CDBC-1304
     * @Description - Create a Service Quote, Recalculate Tax and Verify The Request/Response
     * @Author Mario Saint-Fleur
     */
    @Test(alwaysRun = true, groups = {"D365_Business_Central_ServiceMgmt", "D365_Business_Central_Service_Regression","D365_Business_Central_Smoke"}, retryAnalyzer = TestRerun.class)
    public void recalculateTaxForServiceQuoteAndVerifyResponseAndRequestTest(){
        String customerNo="TestingService_PA";
        String serviceItemNo="1009";
        String itemNumber = "1896-S";
        String quantity = "1";
        String expectedTax="60.05";
        int expectedCount = 6;
        BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();

        //enable service management toggle
        adminPage.updateServiceManagementOn();
        adminPage.clickBackAndSaveArrow();

        //select Service Quote in service management menu
        BusinessServiceQuotesListPage serviceQuotes=servicePage.mainNavMenu.goToSubNavTab(BusinessMainMenuNavTabs.SERVICE_MANAGEMENT.value,"Service Quotes" );
        BusinessServiceQuotesPage serviceQuote = serviceQuotes.pageNavMenu.clickNew();
        serviceQuote.fillInNumberForService();
        fillInServiceQuotesGeneralInfo(serviceQuote,customerNo);
        serviceQuote.expandTable();
        serviceQuote.fillInServiceTableInfo(serviceItemNo,1);
        serviceQuote.exitExpandTable();
        String quoteNumber = serviceQuote.getCurrentSalesNumber();
        BusinessServiceItemPage serviceItemSheet=serviceQuote.navigateToServiceItemWorksheet();
        serviceItemSheet.expandServiceItemWorksheet();
        serviceItemSheet.fillInItemsTableInfoForService("Item",itemNumber, quantity,null,1);

        //Recalculate Tax
        recalculateTax(serviceQuote);

        //Verify Total tax on Service Item Worksheet
        String totalTaxOnServiceItemSheet=serviceItemSheet.getTotalTaxAmount();
        assertEquals(totalTaxOnServiceItemSheet,expectedTax);

        //Navigate to XML page and validate Response/Request amount
        adminPage = homePage.searchForAndNavigateToVertexAdminPage();
        adminPage.openXmlLogCategory();
        adminPage.filterDocuments(quoteNumber);
        int count = adminPage.getResponseCount(quoteNumber);
        assertEquals(count,expectedCount,"Row validation failed");
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpServiceMgmt(){
        String role="Service Manager";
        homePage = new BusinessAdminHomePage(driver);
        servicePage=new BusinessServiceHomePage(driver);
        String verifyPage=homePage.verifyHomepageHeader();
        if(!verifyPage.contains(role)){

            //navigate to select role as Service Manager role
            homePage.selectSettings();
            homePage.navigateToManagerInSettings(role);
        }
    }

}
