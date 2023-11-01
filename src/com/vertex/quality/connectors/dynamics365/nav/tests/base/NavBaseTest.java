package com.vertex.quality.connectors.dynamics365.nav.tests.base;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.nav.pages.*;
import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavSalesBasePage;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

public abstract class NavBaseTest extends VertexUIBaseTest<NavAdminHomePage> {
    protected EnvironmentInformation environmentInformation;
    protected EnvironmentCredentials environmentCredentials;
    protected String username;
    protected String password;
    protected String environmentURL;
    protected String trustedId;
    protected String companyCode;

    protected DBConnectorNames connectorName = DBConnectorNames.D365_BUSINESS_CENTRAL;

    protected DBEnvironmentNames environmentName = DBEnvironmentNames.QA;
    protected DBEnvironmentDescriptors environmentDescriptor = DBEnvironmentDescriptors.D365_BUSINESS_CENTRAL;

    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
    String transactionDate = sdf.format(date);

    @Override
    protected NavAdminHomePage loadInitialTestPage()
    {
        NavAdminHomePage homePage = null;
        getCredentials();
        homePage = signInToHomePage();
        return homePage;
    }
    /**
     * Helper method to initialize test page and sign on to application
     * includes waiting for loading screen to pass
     * @return the admin homepage
     */
    protected NavAdminHomePage initializeTestPageAndSignOn()
    {
        NavAdminHomePage homePage = testStartPage;
       if (driver.manage().window().getSize().width <= 1294 )
        {
            driver.manage().window().setSize(new Dimension(1616, 876));
        }
        homePage.checkForCautionPopup();
        homePage.waitForLoadingScreen();
        //homePage.clickOnActionBar();
        return homePage;
    }
    /**
     * Gets credentials for the connector from the database
     */
    protected void getCredentials()
    {
        try {
            environmentInformation = SQLConnection.getEnvironmentInformation(connectorName, environmentName,
                    environmentDescriptor);
            environmentCredentials = SQLConnection.getEnvironmentCredentials(environmentInformation);
            username = environmentCredentials.getUsername();
            password = environmentCredentials.getPassword();
            environmentURL = environmentInformation.getEnvironmentUrl();
        } catch (Exception e) {
            VertexLogger.log("unable to load the environment information/credentials for a d365 business central test",
                    VertexLogLevel.ERROR);
            e.printStackTrace();
        }
    }
    /**
     * opens the application's sign on page
     * @return the sign on page
     */
    protected NavSignOnPage initializeSignOnPage() {
        String environmentURL
                = "https://nav-vertex-tst3.eastus.cloudapp.azure.com/NAV/";
        driver.get(environmentURL);
        NavSignOnPage signOnPage = new NavSignOnPage(driver);
        return signOnPage;
    }
    /**
     * this method does the whole sign in process to business central environment
     * does the sign in process over three different pages
     * in case the application asks for account security verification methods, also
     * handles that page to continue signing in
     * @return new instance of the admin home page
     */
    public NavAdminHomePage signInToHomePage()
    {
        String username = "svcvtest@vertexinc.com";
        String password = "C0nnect0r@ut0";
        NavSignOnPage signOnPage = initializeSignOnPage();
        signOnPage.enterEmailAddress(username);
        signOnPage.enterPassword(password);
        NavAdminHomePage homePage = signOnPage.clickDoNotStayLoggedIn();
        String title = homePage.getPageTitle();
        if (title.equals("don't lose access to your account!")) {
            NavSecureAccountPage secureAccountPage = new NavSecureAccountPage(driver);
            signOnPage = secureAccountPage.clickCancel();
            signOnPage.clickDoNotStayLoggedIn();
        }
        return homePage;
    }
    /**
     * Returns the current date in m/d/yyyy format
     * @return transaction date
     */
    protected String getTransactionDate()
    {
        return transactionDate;
    }
    /**
     * Searches for an extension on the extensions page (list is extremely long and gets cut off)
     * and then selects the extension found
     * @param page
     * @param extensionToFind
     */
    protected void searchForAndSelectExtension(NavExtensionsPage page, String extensionToFind) {
        page.searchForExtension(extensionToFind);
        page.clickVertexExtension();
    }
    /**
     * Opens the vertex tax details window on sales edit pages
     * @param page
     */
    protected void openVertexTaxDetailsWindow(NavSalesBasePage page) {
        page.waitForPageLoad();
        page.salesEditNavMenu.selectNavigate();
        page.waitForPageLoad();
        page.salesEditNavMenu.selectVertexTaxDetails();
    }

    /**Clicks recalculate tax button via Navigate tab
    */
    protected void clickRecalculateTax(NavSalesBasePage page){
        page.waitForPageLoad();
        page.salesEditNavMenu.selectNavigate();
        page.waitForPageLoad();
        page.salesEditNavMenu.selectRecalculate();
    }

    /**
     * Navigate to Stat window
     */
    protected void navigateToStatWindow()
    {
        NavSalesBasePage page = new NavSalesBasePage(driver);
        page.salesEditNavMenu.clickOnStatistics();
    }

    /**
     * On Home page locates recalculate tax and clicks it
     * @param page
     */
    protected void openRecalculateTaxFromHomePage(NavSalesBasePage page){
        page.waitForPageLoad();
        page.salesEditNavMenu.selectRecalculate();
    }

    /**
     * Opens the vertex tax details window on sales invoice pages
     * @param page
     */
    protected  void openVertexTaxDetailsWindowFromInvoice(NavSalesBasePage page)
    {
        page.waitForPageLoad();
        page.salesEditNavMenu.selectVertexTaxDetailsFromInvoice();
    }
    /**
     * Opens the vertex tax details window on sales invoice pages
     * @param page
     * @author bhikshapathi
     */
    protected  void openVertexTaxDetailsWindowFromReturnOrder(NavSalesBasePage page)
    {
        page.waitForPageLoad();
        page.salesEditNavMenu.selectNavigateFromReturnOrder();
        page.salesEditNavMenu.selectVertexTaxDetails();
    }

    /**
     * Navigates to Vertex Tax details from Quote Home page
     */
    protected void openVertexTaxDetailsFromQuote(NavSalesBasePage page){
        page.waitForPageLoad();
        page.salesEditNavMenu.clickHomePageVertexTaxDetails();
    }

    /**
     * Navigates to the statistics window from the navigate tab
     * @param page
     * @author Shruti Jituri
     */
    protected void openStatisticsWindowFromReturnOrder(NavSalesBasePage page){
        page.waitForPageLoad();
        page.salesEditNavMenu.selectNavigateFromReturnOrder();
        page.salesEditNavMenu.clickOnStatistics();
    }

    /**
     * clicks the recalculate tax option
     * @param page
     */
    protected void recalculateTax(NavSalesBasePage page) {
        try {
            page.salesEditNavMenu.clickMoreOptions();
        } catch (NoSuchElementException | TimeoutException e) {
            // if More Options has already been opened
        }
        page.salesEditNavMenu.clickMoreOptionsActionsButton();
        page.salesEditNavMenu.selectRecalculateTaxButton();
    }


    /**
     * Fills in the general info when creating a sales memo
     * @param memoPage
     * @param customerCode
     */
    protected void fillInSalesMemoGeneralInfo(NavSalesCreditMemoPage memoPage, String customerCode) {
        memoPage.enterCustomerCode(customerCode);
    }
    /**
     * Fills in the general info when creating a sales invoice
     * @param invoicePage
     * @param customerCode
     */
    protected void fillInSalesInvoiceGeneralInfo(NavSalesInvoicePage invoicePage, String customerCode) {
        invoicePage.enterCustomerCode(customerCode);
    }
    /**
     * Fills in the general info when creating a sales order
     *  @param orderPage
     * @param customerCode
     */
    protected void fillInSalesOrderGeneralInfo(NavSalesOrderPage orderPage, String customerCode) {
        orderPage.enterCustomerCode(customerCode);
    }
    /**
     * Fills in the general info when creating a sales order
     *  @param returnOrderPage
     * @param customerCode
     */
    protected void fillInSalesOrderGeneralInfo(NavSalesReturnOrderPage returnOrderPage, String customerCode) {
        returnOrderPage.enterCustomerCode(customerCode);
    }
    /**
     * Fills in the general info when creating a sales quote
     *  @param quotePage
     * @param customerCode
     */
    protected void fillInSalesQuoteGeneralInfo(NavSalesQuotesPage quotePage, String customerCode) {
        quotePage.enterCustomerCode(customerCode);
    }
    /**
     * Fills in the items table when creating a sales order/quote/memo/invoice
     * @param name
     * @param address
     * @param city
     * @param zipCode
     */
    protected void fillInCustomAddressInShippingAndBilling(String name,String address, String city, String state, String zipCode) {
        NavSalesBasePage salesPage = new NavSalesBasePage(driver);
        salesPage.enterCustomerName(name);
        salesPage.enterAddress(address);
        salesPage.enterCityName(city);
        salesPage.enterState(state);
        salesPage.enterZipCodeValue(zipCode);
    }
    /**
     * Post the Sales Invoice
     * @param page
     */
    protected NavSalesInvoicePage postTheInvoice(NavSalesInvoicePage page){
        page.salesEditNavMenu.selectPostButtonOnInvoicePage();
        page.dialogBoxClickYes();
        NavSalesInvoicePage postedInvoice = page.goToInvoice();
        return postedInvoice;
    }

    /**
     * Post the Sales Return Order
     * @param page
     */
    protected NavSalesCreditMemoPage postReturnOrder(NavSalesReturnOrderPage page){
        page.salesEditNavMenu.clickActionsTab();
        page.salesEditNavMenu.selectPost();
        page.dialogBoxClickOk();
        NavSalesCreditMemoPage postedCreditMemo= page.goToPostedMemo();
        return postedCreditMemo;
    }

    /**
     * When posting a sales order, selects the Ship and Invoice option
     * then posts the order as an invoice
     * @param page
     * @return the posted invoice
     */
    protected NavSalesInvoicePage salesOrderSelectShipAndInvoiceThenPost( NavSalesOrderPage page )
    {
        page.salesEditNavMenu.clickActionsTab();
        page.salesEditNavMenu.selectPost();
        page.selectShipAndInvoicePosting();

        NavSalesInvoicePage postedInvoice = page.goToInvoice();

        return postedInvoice;
    }
    /**
     * When posting a sales order, selects the Ship and Invoice option
     * then posts the order as an invoice
     * @param page
     * @return the posted invoice
     */
    protected NavSalesReturnOrderPage salesOrderSelectReceiveAndInvoiceThenPost( NavSalesOrderPage page )
    {   page.waitForPageLoad();
        page.salesEditNavMenu.selectPostButton();
        page.selectReceiveAndInvoicePosting();

        NavSalesReturnOrderPage postedCreditMemo = page.goToPostedMemos();

        return postedCreditMemo;
    }
    /**
     * Fills in the address when creating a customer
     *
     * @param addrLineOne
     * @param addrLineTwo
     * @param city
     * @param state
     * @param zipCode
     * @param country
     */
    protected void  fillInCustomerAddressInfo( String addrLineOne, String addrLineTwo, String city, String state,
                                               String zipCode, String country )
    {
        NavCustomerCardPage customerCardPage = new NavCustomerCardPage(driver);
        customerCardPage.enterAddressLineOne(addrLineOne);
        if ( null != addrLineTwo )
        {
            customerCardPage.enterAddressLineTwo(addrLineTwo);
        }
        customerCardPage.enterAddressCountry(country);
        customerCardPage.enterAddressCity(city);
        customerCardPage.enterAddressState(state);
        if(null!=zipCode) {
            customerCardPage.enterAddressZip(zipCode);
        }
    }
    /**
     * Fills line leval location code in the items table when creating a sales order/quote/memo/invoice
     * @param locationCode
     * @param itemIndex
     */
    protected void addLineLevelLocationCode(String locationCode, int itemIndex)
    {
        int itemNumber = itemIndex - 1;
        NavSalesBasePage salesPage = new NavSalesBasePage(driver);
        salesPage.waitForPageLoad();
        salesPage.enterLocationCode(locationCode, itemNumber);
    }
    /**
     * Fills line leval location code in the items table when creating a sales order/quote/memo/invoice
     * @param quantity
     * @param itemIndex
     */
    protected void updateQuantity(String quantity, int itemIndex)
    {
        int itemNumber = itemIndex - 1;
        NavSalesBasePage salesPage = new NavSalesBasePage(driver);
        salesPage.enterQuantity(quantity, itemNumber);
    }
    /**
     * Updated Line Amount the items table when creating a sales order/quote/memo/invoice
     * @param amount
     * @param itemIndex
     */
    protected void changedLineAmount(String amount, int itemIndex)
    {
        NavSalesBasePage salesPage = new NavSalesBasePage(driver);
        salesPage.enterLineAmount(amount,itemIndex);
    }

    /**
     * Change item type to Comment then fill in items table for the comment row
     * @param itemCode
     * @param description
     * @param itemIndex
     */
    protected void fillInItemsTableComment( String itemCode, String description, int itemIndex )
    {
        int itemNumber = itemIndex - 1;
        NavSalesBasePage salesPage = new NavSalesBasePage(driver);

        salesPage.setItemType("Comment", itemNumber);
        if ( null != itemCode )
        {
            salesPage.setItemNumberInfo(itemCode, itemNumber);
        }
        if ( null != description )
        {
            salesPage.enterItemDescription(description, itemNumber);
        }
    }
    /**
     * Fills in the required info when creating a new flex field
     * @param flexSource
     * @param flexType
     * @param flexId
     * @param flexValue
     */
    protected void fillInNewFlexFieldInfo( String flexSource, String flexType, String flexId, String flexValue )
    {
        NavFlexFieldsPage flexFieldsPage = new NavFlexFieldsPage(driver);

        flexFieldsPage.selectFlexFieldSource(flexSource);
        flexFieldsPage.selectFlexFieldType(flexType);
        flexFieldsPage.inputFlexFieldId(flexId);
        flexFieldsPage.inputFlexFieldValue(flexValue);
    }
    /**
     * Copies a sales document to a credit memo, then posts the copied memo
     * @param page
     * @param documentType
     * @param documentNumber
     */
    protected void copySalesDocumentToMemo( NavSalesBasePage page, String documentType, String documentNumber)
    {
        page.activateRow(1);
        page.salesEditNavMenu.clickPrepareButton();
        page.salesEditNavMenu.selectCopyDocument();

        page.fillOutCopyDocumentInformation(documentType, documentNumber);
    }


    /**
     * Enable the checkboxes in Copy document functionality of credit memo
     * @param page
     */
    protected void enableIncludeHeaderAndRecalculateLines(NavSalesBasePage page) {
        page.enableIncludeHeaderRecalculateLines();
    }

    /**
     * Disable the checkboxes in Copy document functionality of credit memo
     * @param page
     */
    protected void disableBothIncludeHeaderAndRecalculateLines(NavSalesBasePage page) {
        page.disableBothIncludeHeaderRecalculateLines();
    }
    /**
     * Copies a sales document to a return order
     * @param page
     * @param documentType
     * @param documentNumber
     */
    protected void copySalesDocumentToReturnOrder(NavSalesBasePage page, String documentType, String documentNumber){
        page.activateRow(1);
        page.salesEditNavMenu.selectActionsTab();
        page.salesEditNavMenu.selectCopyDocument();
        page.fillOutCopyDocumentInformation(documentType, documentNumber);
    }
    /**
     * Posts any sales document as an invoice
     * @param page
     * @return posted sales document
     */
    protected NavSalesInvoicePage salesDocumentPostInvoice( NavSalesBasePage page )
    {
        page.salesEditNavMenu.clickPostingButton();
        page.salesEditNavMenu.selectPostButton();
        page.dialogBoxClickYes();

        NavSalesInvoicePage postedInvoice = page.goToInvoice();

        return postedInvoice;
    }
    /**
     * Posts a credit memo as a memo
     * @param page
     * @return the posted memo
     */
    protected NavSalesCreditMemoPage postCreditMemo( NavSalesCreditMemoPage page )
    {
        page.salesEditNavMenu.clickActionsTab();
        page.salesEditNavMenu.selectPostButtonForCreditMemo();
        page.dialogBoxClickYes();
        NavSalesCreditMemoPage postedMemo = page.goToPostedMemo();
        return postedMemo;
    }

    /**
     * Fills in the address when creating a customer
     * @param custName
     * @param addrLineTwo
     * @param city
     * @param state
     * @param zipCode
     * @param country
     */
    protected void createNewCustomer( String custName,String addrLineOne, String addrLineTwo, String city, String state,
                                      String zipCode, String country,String taxCode, String locCode )
    {
        NavCustomerCardPage customerCardPage = new NavCustomerCardPage(driver);
        customerCardPage.clickNew();
        customerCardPage.selectDomesticCustomer();
        customerCardPage.enterCustName(custName);
        customerCardPage.enterAddressLineOne(addrLineOne);
        if ( null != addrLineTwo )
        {
            customerCardPage.enterAddressLineTwo(addrLineTwo);
        }
        customerCardPage.enterAddressCity(city);
        customerCardPage.enterAddressState(state);
        customerCardPage.enterAddressZip(zipCode);
        customerCardPage.enterAddressCountry(country);
        customerCardPage.openInvoicingCategory();
        customerCardPage.enterTaxAreaCode(taxCode);
        customerCardPage.openShippingCategory();
        customerCardPage.enterLocationCode(locCode);
    }
    /**
     * Fills in the address when creating a customer
     * @param custName
     * @param addrLineTwo
     * @param city
     * @param state
     * @param zipCode
     * @param country
     */
    protected void createNewCustomerForSecondTime( String custName,String addrLineOne, String addrLineTwo, String city, String state,
                                                   String zipCode, String country,String taxCode, String locCode )
    {
        NavCustomerCardPage customerCardPage = new NavCustomerCardPage(driver);
        customerCardPage.clickNew();
        customerCardPage.selectDomesticCustomer();
        customerCardPage.enterCustName(custName);
        customerCardPage.enterAddressLineOne(addrLineOne);
        if ( null != addrLineTwo )
        {
            customerCardPage.enterAddressLineTwo(addrLineTwo);
        }
        customerCardPage.enterAddressCity(city);
        customerCardPage.enterAddressState(state);
        customerCardPage.enterAddressZip(zipCode);
        customerCardPage.enterAddressCountry(country);
        customerCardPage.enterTaxAreaCode(taxCode);
        customerCardPage.enterLocationCode(locCode);
    }
    /**
     * Create new Tax group code
     * @param code
     * @param description
     */
    protected void createTaxGroup(String code, String description)
    {
        NavCustomerCardPage customerCardPage = new NavCustomerCardPage(driver);
        customerCardPage.clickNew();
        customerCardPage.enterTaxGCode(code);
        customerCardPage.enterDescription(description);
    }
    /**
     * Converts any sales document into an invoice
     * @param page
     * @return the converted invoice
     */
    protected NavSalesInvoicePage salesDocumentMakeInvoice( NavSalesBasePage page )
    {   page.waitForPageLoad();
        page.salesEditNavMenu.selectMakeInvoiceButton();
        page.dialogBoxClickYes();
        NavSalesInvoicePage invoicePage = page.goToInvoice();
        return invoicePage;
    }
    /**
     * Makes sure none of the flex field inputs are enabled on an existing flex field
     * @param page
     * @return true if all inputs disabled, false if not
     */
    protected boolean checkAllFlexFieldInputsDisabled( NavFlexFieldsPage page )
    {
        boolean flexFieldInputsDisabled = true;
        try
        {
            page.selectFlexFieldSource("Constant Value");
            flexFieldInputsDisabled = false;
        }
        catch ( TimeoutException e )
        {}
        try
        {
            page.selectFlexFieldType("Text");
            flexFieldInputsDisabled = false;
        }
        catch ( TimeoutException e )
        {}
        try
        {
            page.inputFlexFieldId("10");
            flexFieldInputsDisabled = false;
        }
        catch ( TimeoutException e )
        { }
        try
        {
            page.inputFlexFieldValue("testing");
            flexFieldInputsDisabled = false;
        }
        catch ( TimeoutException e )
        {}
        return flexFieldInputsDisabled;
    }

    /**
     * Modify item type then fill in the items table when creating a sales order/quote/memo/invoice
     * @param itemType
     * @param itemCode
     * @param itemDescription
     * @param locCode
     * @param itemQuantity
     * @param lineAmount
     * @param itemIndex
     */
    protected void fillInItemsTableInfo( String itemType, String itemCode, String itemDescription,String locCode, String itemQuantity,
                                         String lineAmount, int itemIndex )
    {
        int itemNumber = itemIndex - 1;
        NavSalesBasePage salesPage = new NavSalesBasePage(driver);

        salesPage.setItemType(itemType, itemNumber);
        salesPage.enterItemNumber(itemCode, itemNumber);
        if ( null != itemDescription )
        {
            salesPage.enterItemDescription(itemDescription, itemNumber);
        }
        if ( null != locCode )
        {
            salesPage.enterLocationCode(locCode,itemNumber);
        }
        salesPage.waitForPageLoad();
        if ( null != itemQuantity )
        {
            salesPage.enterQuantity(itemQuantity, itemNumber);
        }

        if ( null != lineAmount )
        {
            salesPage.enterLineAmount(lineAmount,itemIndex);
        }

    }


}
