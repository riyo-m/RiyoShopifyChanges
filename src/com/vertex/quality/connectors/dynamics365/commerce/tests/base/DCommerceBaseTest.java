package com.vertex.quality.connectors.dynamics365.commerce.tests.base;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.common.utils.properties.ReadProperties;
import com.vertex.quality.connectors.dynamics365.commerce.pages.DCommerceHomePage;
import com.vertex.quality.connectors.dynamics365.commerce.pages.DCommerceSignInPage;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuModule;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelCategory;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelLink;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailHomePage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailLoginPage;


/**
 * collects setup actions, constants, utility functions, etc which are shared by the test classes for D365 Commerce
 *
 * @author Vivek Olumbe
 */

public abstract class DCommerceBaseTest extends VertexUIBaseTest<DCommerceHomePage>
{
    private final String CONFIG_DETAILS_FILE_PATH = CommonDataProperties.CONFIG_DETAILS_FILE_PATH;

    public String DCOMMERCE_URL = null;
    public String DCOMMERCE_EMAIL = null;
    public String DCOMMERCE_PASSWORD = null;
    public String DFINANCE_URL = null;
    public String DFINANCE_USERNAME = null;
    public String DFINANCE_PASSWORD = null;
    public String DRETAIL_URL = null;
    public String DRETAIL_OPERATOR = null;
    public String DRETAIL_PASSWORD = null;

    protected final int defaultTableRow = 1;

    @Override
    public DCommerceHomePage loadInitialTestPage( )
    {
        initSetup();
        DCommerceHomePage homePage = launchHomePage();
        DCommerceSignInPage signinPage = homePage.navigateToSignInPage();
        logInAsUser(signinPage, DCOMMERCE_EMAIL, DCOMMERCE_PASSWORD);
        return homePage;
    }

    /**
     * Get the data from resources folder -> property file
     */
    protected void initSetup( )
    {
        try
        {
            ReadProperties readConfigDetails = new ReadProperties(CONFIG_DETAILS_FILE_PATH);


            DCOMMERCE_URL = "https://vertexonline.commerce.dynamics.com/";
            DCOMMERCE_EMAIL = readConfigDetails.getProperty("TEST.VERTEX.D365.USERNAME");
            DCOMMERCE_PASSWORD = readConfigDetails.getProperty("TEST.VERTEX.D365.PASSWORD");

            DFINANCE_URL = "https://vtxecomm.sandbox.operations.dynamics.com/";
            DFINANCE_USERNAME = readConfigDetails.getProperty("TEST.VERTEX.D365.USERNAME");
            DFINANCE_PASSWORD = readConfigDetails.getProperty("TEST.VERTEX.D365.PASSWORD");

            DRETAIL_URL = "https://scu5d72npzx80790477-cpos.su.retail.dynamics.com/";
            DFINANCE_USERNAME = readConfigDetails.getProperty("TEST.VERTEX.D365.USERNAME");
            DFINANCE_PASSWORD = readConfigDetails.getProperty("TEST.VERTEX.D365.PASSWORD");

            DRETAIL_OPERATOR = "000123";
            DRETAIL_PASSWORD = "123";
        }
        catch ( Exception e )
        {
            VertexLogger.log("Failure initializing test data");
            e.printStackTrace();
        }
    }

    /**
     * Launch Dynamics365 Commerce home page
     */
    protected DCommerceHomePage launchHomePage( )
    {
        DCommerceHomePage homePage = null;
        driver.manage().window().maximize();
        VertexLogger.log(String.format("Launching Dynamics365 E-Commerce URL - %s", DCOMMERCE_URL),
                VertexLogLevel.TRACE);
        driver.get(DCOMMERCE_URL);

        homePage = new DCommerceHomePage(driver);
        homePage.clickAcceptButton();
        return homePage;
    }

    /**
     * Enter user name , password and click on 'login' button
     *
     * @param signInPage    the page where the test signs into the site
     * @param email         the test account's email
     * @param password      the test account's password
     *
     * @return the home page of the d365 commerce site after signing in
     */
    protected void logInAsUser(final DCommerceSignInPage signInPage, final String email,
                               final String password )
    {
        signInPage.enterEmailAddress(email);
        signInPage.enterPassword(password);
        signInPage.clickSignIn();
    }

    /**
     * Launches Dynamics365 Finance home page and logs in if needed
     * @return DFinanceHomePage
     */
    protected DFinanceHomePage navigateToDFinanceHomePage() {
        VertexLogger.log(String.format("Launching Dynamics365 Finance & Operations URL - %s", DFINANCE_URL),
                VertexLogLevel.TRACE);
        driver.get(DFINANCE_URL);
        DFinanceHomePage homePage = null;

        DFinanceLoginPage loginPage = new DFinanceLoginPage(driver);

        if (loginPage.isLoggedIn()) {
            homePage = new DFinanceHomePage(driver);
        } else {
            loginPage.setUsername(DFINANCE_USERNAME);
            loginPage.clickNextButton();
            loginPage.setPassword(DFINANCE_PASSWORD);
            loginPage.clickLoginButton();
            homePage = loginPage.clickYesButton();
        }

        return homePage;
    }

    /**
     * Launches Dynamics365 Retail home page and logs in
     * @return DRetailHomePage
     */
    protected DRetailHomePage navigateToDRetailHomePage() {
        VertexLogger.log(String.format("Launching Dynamics365 Retail URL - %s", DRETAIL_URL),
                VertexLogLevel.TRACE);
        driver.get(DRETAIL_URL);

        DRetailLoginPage loginPage = new DRetailLoginPage(driver);
        loginPage.activateStore();
        loginPage.setUsername(DRETAIL_OPERATOR);
        loginPage.setPassword(DRETAIL_PASSWORD);
        loginPage.clickLoginButton();
        loginPage.closeTutorial();

        DRetailHomePage homePage = new DRetailHomePage(driver);

        return new DRetailHomePage(driver);
    }

    /**
     * Navigates to Distribution Schedules and initiates P-0001 job
     * @return DFinanceDistributionSchedulePage
     */
    protected DFinanceDistributionSchedulePage runBatchJob() {
        final String jobName = "P-0001";
        final int batchJobWaitInMS = 30000;

        DFinanceHomePage homePage = new DFinanceHomePage(driver);

        DFinanceDistributionSchedulePage distributionSchedulePage = homePage.navigateToPage(DFinanceLeftMenuModule.RETAIL_AND_COMMERCE, DFinanceModulePanelCategory.IT,
                DFinanceModulePanelLink.DISTRIBUTION_SCHEDULE);
        distributionSchedulePage.initiateTheJob(jobName);
        distributionSchedulePage.clickOK();
        distributionSchedulePage.waitStaticallyForBatchJobToRun(batchJobWaitInMS);

        return distributionSchedulePage;
    }

    /**
     * Navigates to Synchronize Orders and initiates synchronization job for Fabrikam extended online store
     */
    protected void synchronizeOrders() {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        final int batchJobWaitInMS = 120000;

        DFinanceSynchronizeOrdersPage syncOrdersPage = homePage.navigateToPage(DFinanceLeftMenuModule.RETAIL_AND_COMMERCE, DFinanceModulePanelCategory.IT,
                DFinanceModulePanelLink.SYNCHRONIZE_ORDERS);
        syncOrdersPage.runSynchronizeOrdersJob("Fabrikam extended online store");
        syncOrdersPage.waitStaticallyForBatchJobToRun(batchJobWaitInMS);
    }

    /**
     * Navigates to All Sales Orders and opens most recent open order for given Customer
     * @param customerName
     * @return DFinanceAllSalesOrdersPage
     */
    protected DFinanceAllSalesOrdersPage navigateToLatestOpenSalesOrder(String customerName) {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);

        DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);
        DFinanceAllSalesOrdersSecondPage secondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        allSalesOrdersPage.searchSalesOrderByCustomerName(customerName);
        allSalesOrdersSecondPage.applyFilterOnStatus("Open order");
        secondPage.clickOnLatestOrderCreated();

        return allSalesOrdersPage;
    }

}
