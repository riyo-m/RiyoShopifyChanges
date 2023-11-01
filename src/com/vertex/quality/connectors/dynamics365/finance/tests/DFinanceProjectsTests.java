package com.vertex.quality.connectors.dynamics365.finance.tests;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Mario Saint-Fleur
 * This class is all tests related to Project Invoice
 */
@Listeners(TestRerunListener.class)
public class DFinanceProjectsTests extends DFinanceBaseTest {
    final String setup = DFinanceLeftMenuNames.SETUP.getData();
    final String procurementAndSourcingParameters = DFinanceLeftMenuNames.PROCUREMENT_AND_SOURCING_PARAMETERS.getData();
    Boolean advTaxGroup = false;

    @BeforeMethod(alwaysRun = true)
    public void setupTest( )
    {
        advTaxGroup = false;
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        settingsPage.selectCompany(germanCompanyDEMF);
        homePage.refreshPage();
        toggleONOFFAdvanceTaxGroup(false);
    }

    @AfterMethod(alwaysRun = true)
    public void teardownTest( )
    {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        if (advTaxGroup) {
            homePage.refreshPage();
            toggleONOFFAdvanceTaxGroup(false);
        }
    }
    /**
     * @TestCase CD365-1978
     * @Description - Create a project invoice using hours expense
     * @Author Mario Saint-Fleur
     */
    @Test(groups = {"FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void createProjectInvoiceUsingHoursExpenseTest(){
        final String hourAmount = "8.00";

        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = new DFinanceAllPurchaseOrdersPage(driver);
        DFinanceAllProjectsPage allProjectsPage = new DFinanceAllProjectsPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

        settingsPage.selectCompany(usmfCompany);

        //Navigate to All Projects
        homePage.navigateToPage(
                DFinanceLeftMenuModule.PROJECT_MANAGEMENT_AND_ACCOUNTING, DFinanceModulePanelCategory.PROJECTS,
                DFinanceModulePanelLink.ALL_PROJECTS);

        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        allProjectsPage.enterProjectContractId("000008");
        allProjectsPage.enterCustomerAccount("004021");
        allProjectsPage.clickCreateProjectButton();

        //Begin Project Process
        allProjectsPage.clickTab("HeaderHomeTab_button");
        allProjectsPage.selectProjectStageOption("ctrlStartUp");
        allProjectsPage.clickOkButton();

        //Create New Hour Expense
        allProjectsPage.selectJournalType("HourEntry", "1");
        allProjectsPage.clickNewButton("CreateNewHour", "1");
        allProjectsPage.clickLinesOption("Lines");
        allProjectsPage.clickNewButton("SystemDefinedNewButton", "2");
        allProjectsPage.enterHoursAmount(hourAmount);
        allProjectsPage.clickPostButton("2");
        allSalesOrdersSecondPage.clickOnOK();
        allProjectsPage.clickBackButton("4");
        allProjectsPage.clickBackButton("3");

        //Verify Sales Tax and Post
        allProjectsPage.clickTab("MaintainTab_button");
        allProjectsPage.clickInvoiceProposalAndSelectTransaction();
        allSalesOrdersSecondPage.clickOnOK();
        allProjectsPage.clickSalesTaxButton("SalesTax_Empl");
        String actualSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("11.04"),
                "'Total actual sales tax amount' value is not expected");
        allSalesOrdersPage.clickOk();
        allProjectsPage.clickPostButton("1");
        allSalesOrdersPage.clickOnOKBtn();
        allProjectsPage.clickOkButton();

        String projectNumber = allProjectsPage.getDocumentNumber("2");

        //Navigate to Vertex XML Inquiry and validate TotalTax
        homePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        xmlInquiryPage.getDocumentID(projectNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>11.04</TotalTax>"));
    }

    /**
     * @TestCase CD365-2043
     * @Description - Create Project Invoice Using All Journal Types And Validate Lines Are Posted
     * @Author Mario Saint-Fleur
     */
    @Ignore
    @Test(groups = {"FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void createProjectInvoiceUsingAllJournalTypesAndValidateLinesArePostedTest(){
        final String hourAmount = "8.00";

        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = new DFinanceAllPurchaseOrdersPage(driver);
        DFinanceAllProjectsPage allProjectsPage = new DFinanceAllProjectsPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

        settingsPage.selectCompany(usmfCompany);

        //Navigate to All Projects
        homePage.navigateToPage(
                DFinanceLeftMenuModule.PROJECT_MANAGEMENT_AND_ACCOUNTING, DFinanceModulePanelCategory.PROJECTS,
                DFinanceModulePanelLink.ALL_PROJECTS);

        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        allProjectsPage.enterProjectContractId("000008");
        allProjectsPage.enterCustomerAccount("004021");
        allProjectsPage.clickCreateProjectButton();

        //Begin Project Process
        allProjectsPage.clickTab("HeaderHomeTab_button");
        allProjectsPage.selectProjectStageOption("ctrlStartUp");
        allProjectsPage.clickOkButton();

        //Create New Hour Expense
        allProjectsPage.selectJournalType("HourEntry");
        allProjectsPage.clickNewButton("CreateNewHour", "1");
        allProjectsPage.clickLinesOption("Lines");
        allProjectsPage.clickNewButton("SystemDefinedNewButton", "2");
        allProjectsPage.enterHoursAmount(hourAmount);
        allProjectsPage.clickPostButton("2");
        allSalesOrdersSecondPage.clickOnOK();
        allProjectsPage.clickCloseButton("4");
        allProjectsPage.clickCloseButton("3");

        //Create New Expense
        allProjectsPage.selectJournalType("ExpenseEntry");
        allProjectsPage.clickNewButton("SystemDefinedNewButton", "2");
        allProjectsPage.clickLinesOption("JournalLines");
        allProjectsPage.enterCostPrice("60.00");
        allProjectsPage.clickPostButton("2");
        allProjectsPage.clickPostingType("PostJournal", "2");
        allProjectsPage.clickCloseButton("4");
        allProjectsPage.clickCloseButton("3");

        //Create New Item
        allProjectsPage.selectJournalType("ItemJournalEntry");
        allProjectsPage.clickNewButton("SystemDefinedNewButton", "2");
        allProjectsPage.clickLinesOption("lines");
        allProjectsPage.enterItemNumber("1000");
        allProjectsPage.clickProductDimensions();
        allProjectsPage.enterSiteAndWarehouse("1", "11");
        allProjectsPage.clickFunctions();
        allProjectsPage.clickFunctions();
        allProjectsPage.clickOnEllipse();
        allProjectsPage.clickPostButton("2");
        allSalesOrdersSecondPage.clickOnOK();
        allProjectsPage.clickCloseButton("4");
        allProjectsPage.clickCloseButton("3");

        //Create New Fee
        allProjectsPage.selectJournalType("FeeEntry");
        allProjectsPage.clickNewButton("CreateNewFee", "1");
        allProjectsPage.clickLinesOption("Lines");
        allProjectsPage.clickNewButton("SystemDefinedNewButton", "2");
        allProjectsPage.enterFeeAmount("1000");
        allProjectsPage.clickPostButton("2");
        allSalesOrdersSecondPage.clickOnOK();
        allProjectsPage.clickCloseButton("4");
        allProjectsPage.clickCloseButton("3");

        //Create a invoice proposal and select all items
        allProjectsPage.clickTab("MaintainTab_button");
        allProjectsPage.clickInvoiceProposalAndSelectTransaction();
        allSalesOrdersSecondPage.clickOnOK();
        allProjectsPage.clickSalesTaxButton("TaxTmpWorkProjectInvoiceProposal");
        String actualSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("96.56"),
                "'Total actual sales tax amount' value is not expected");
        allSalesOrdersPage.clickOk();
        allProjectsPage.clickPostButton("1");
        allSalesOrdersPage.clickOnOKBtn();
        allProjectsPage.clickOkButton();

        //Create project invoice proposal and post
        allProjectsPage.clickCloseButton("3");
        allProjectsPage.clickProjectInvoiceProposal();

        String getInvoiceNumber = allProjectsPage.getInvoiceNumber();

        //Navigate to Vertex XML Inquiry and validate product class lines for all journals
        homePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        xmlInquiryPage.getDocumentID(getInvoiceNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<Product productClass=\"ALL\">Car Audio</Product>\n" +
                "\t\t\t<Quantity unitOfMeasure=\"Ea\">8.0</Quantity>\n" +
                "\t\t\t<FairMarketValue>138.0</FairMarketValue>\n" +
                "\t\t\t<ExtendedPrice>138.0</ExtendedPrice>"));
        assertTrue(response.contains("<Product productClass=\"ALL\">Travel</Product>\n" +
                "\t\t\t<Quantity unitOfMeasure=\"Ea\">1.0</Quantity>\n" +
                "\t\t\t<FairMarketValue>69.0</FairMarketValue>\n" +
                "\t\t\t<ExtendedPrice>69.0</ExtendedPrice>"));
        assertTrue(response.contains("<Product productClass=\"ALL\">1000</Product>\n" +
                "\t\t\t<Quantity unitOfMeasure=\"Ea\">1.0</Quantity>\n" +
                "\t\t\t<ExtendedPrice>0.0</ExtendedPrice>"));
        assertTrue(response.contains("<Product productClass=\"ALL\">Setup</Product>\n" +
                "\t\t\t<Quantity unitOfMeasure=\"Ea\">1.0</Quantity>\n" +
                "\t\t\t<FairMarketValue>1000.0</FairMarketValue>\n" +
                "\t\t\t<ExtendedPrice>1000.0</ExtendedPrice>"));
    }

    /**
     * @TestCase CD365-2051
     * @Description - Create Project Invoice with userDefinedPartyIdCode And Validate Lines Are Posted
     * @Author Brenda Johnson
     */
    @Test(groups = {"FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void createProjectInvoiceUserDefinedPartyIdCodeValidateLinesArePostedTest() {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = new DFinanceAllPurchaseOrdersPage(driver);
        DFinanceAllProjectsPage allProjectsPage = new DFinanceAllProjectsPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

        settingsPage.selectCompany(usmfCompany);

        //Navigate to All Projects
        homePage.navigateToPage(
                DFinanceLeftMenuModule.PROJECT_MANAGEMENT_AND_ACCOUNTING, DFinanceModulePanelCategory.PROJECTS,
                DFinanceModulePanelLink.ALL_PROJECTS);

        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        allProjectsPage.enterProjectContractId("000008");
        allProjectsPage.enterCustomerAccount("004021");
        allProjectsPage.clickCreateProjectButton();
        String projectID = allProjectsPage.getProjectID();

        //Begin Project Process
        allProjectsPage.clickTab("HeaderHomeTab_button");
        allProjectsPage.selectProjectStageOption("ctrlStartUp");
        allProjectsPage.clickOkButton();

        //Navigate to All Projects
        homePage.navigateToPage(
                DFinanceLeftMenuModule.PROJECT_MANAGEMENT_AND_ACCOUNTING, DFinanceModulePanelCategory.ITEM_TASKS,
                DFinanceModulePanelLink.ITEM_REQUIREMENTS);

        // Add New Item Requirements
        allProjectsPage.clickNewButton("SystemDefinedNewButton", "1");
        DFinanceItemRequirements createItemRequirementsPage = new DFinanceItemRequirements(driver);
        createItemRequirementsPage.addNewLineItemRequirements(projectID,"1000", "1", "11", "2", 0);
        createItemRequirementsPage.clickOnTabItemRequirements("Project");
        createItemRequirementsPage.enterUnitPrice("150.00");

        allProjectsPage.clickTab("ActionPaneTab");
        allProjectsPage.clickPostingType("ButtonLineUpdate","1");
        allProjectsPage.clickPostingType("buttonUpdatePackingSlipProject", "1");
        allSalesOrdersPage.clickOnOKBtn();
        allSalesOrdersPage.clickOnOKPopUp();

        //Navigate to All Projects
        homePage.navigateToPage(
                DFinanceLeftMenuModule.PROJECT_MANAGEMENT_AND_ACCOUNTING, DFinanceModulePanelCategory.PROJECTS,
                DFinanceModulePanelLink.ALL_PROJECTS);

        allSalesOrdersPage.searchCreatedOrder("Project ID", projectID);
        allSalesOrdersPage.clickOnDisplayedOrderNumber(projectID);

        //Create a invoice proposal and select all items
        allProjectsPage.clickTab("MaintainTab_button");
        allProjectsPage.clickInvoiceProposalAndSelectTransaction();
        allSalesOrdersSecondPage.clickOnOK();
        allProjectsPage.clickSalesTaxButton("TaxTmpWorkProjectInvoiceProposal");
        String actualSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("24.00"),
                "'Total actual sales tax amount' value is not expected");
        allSalesOrdersPage.clickOk();
        allProjectsPage.clickPostButton("1");
        allSalesOrdersPage.clickOnOKBtn();
        allProjectsPage.clickOkButton();

        String getInvoiceNumber = allProjectsPage.getInvoiceNumber();

        //Navigate to Vertex XML Inquiry and validate product class lines for all journals
        homePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        xmlInquiryPage.getDocumentID(getInvoiceNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<CustomerCode classCode=\"10\" isBusinessIndicator=\"true\">004021</CustomerCode>"));
    }

    /**
     * @TestCase CD365-2105
     * @Description - Create Project Invoice Proposal For DEMF Company
     * @Author Mario Saint-Fleur
     */
    @Ignore
    @Test(groups = {"FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void createProjectInvoiceUsingHoursExpenseVATTest(){
        final String hourAmount = "8.00";

        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = new DFinanceAllPurchaseOrdersPage(driver);
        DFinanceAllProjectsPage allProjectsPage = new DFinanceAllProjectsPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
        advTaxGroup = true;

        //Navigate to All Projects
        homePage.navigateToPage(
                DFinanceLeftMenuModule.PROJECT_MANAGEMENT_AND_ACCOUNTING, DFinanceModulePanelCategory.PROJECTS,
                DFinanceModulePanelLink.ALL_PROJECTS);

        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        allProjectsPage.selectProjectType("Time and material");
        allProjectsPage.enterProjectContractId("000052");
        allProjectsPage.enterCustomerAccount("DE-011");
        allProjectsPage.clickCreateProjectButton();

        //Create New Hour Expense
        allProjectsPage.clickTab("HeaderHomeTab_button");
        allProjectsPage.selectJournalType("HourEntry", "1");
        allProjectsPage.clickNewButton("CreateNewHour", "1");
        allProjectsPage.enterHours();
        allProjectsPage.clickLinesOption("Lines");
        allProjectsPage.clickNewButton("SystemDefinedNewButton", "2");
        allProjectsPage.enterHoursAmount(hourAmount);
        allProjectsPage.clickPostButton("2");
        allSalesOrdersSecondPage.clickOnOK();
        allProjectsPage.clickCloseButton("4");
        allProjectsPage.clickCloseButton("3");

        //Verify Sales Tax and Post
        allProjectsPage.clickTab("MaintainTab_button");
        allProjectsPage.clickInvoiceProposalAndSelectTransaction();
        allSalesOrdersSecondPage.clickOnOK();
        allProjectsPage.clickSalesTaxButton("SalesTax_Empl");
        String actualSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("0.00"),
                "'Total actual sales tax amount' value is not expected");
        allSalesOrdersPage.clickOk();
        allProjectsPage.clickPostButton("1");
        allSalesOrdersPage.clickOnOKBtn();
        allProjectsPage.clickOkButton();

        String projectNumber = allProjectsPage.getDocumentNumber("2");

        String getInvoiceNumber = allProjectsPage.getInvoiceNumber();

        //Navigate to Vertex XML Inquiry and validate TotalTax
        homePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        xmlInquiryPage.getDocumentID(projectNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>0.0</TotalTax>"));

        xmlInquiryPage.getDocumentID(getInvoiceNumber);
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>0.0</TotalTax>"));
    }
}