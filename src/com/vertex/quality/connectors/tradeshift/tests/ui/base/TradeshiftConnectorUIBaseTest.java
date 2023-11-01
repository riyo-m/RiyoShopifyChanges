package com.vertex.quality.connectors.tradeshift.tests.ui.base;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.connectors.tradeshift.pages.connector.TradeshiftConnectorHomePage;
import com.vertex.quality.connectors.tradeshift.pages.connector.TradeshiftConnectorRequestMappingPage;
import com.vertex.quality.connectors.tradeshift.pages.connector.TradeshiftConnectorSignInPage;
import com.vertex.quality.connectors.tradeshift.pages.connector.TradeshiftConnectorTenantPage;
import org.openqa.selenium.Dimension;
import com.vertex.quality.common.pojos.OSeriesConfiguration;

import static com.vertex.quality.common.utils.SQLConnection.getEnvironmentCredentials;

/**
 * @author alewis
 */
public class TradeshiftConnectorUIBaseTest extends VertexUIBaseTest<TradeshiftConnectorSignInPage> {

    protected String signInUsername;
    protected String signInPassword;
    protected String tradeshiftUrl;
    protected String environmentURL;
    protected EnvironmentInformation TradeshiftEnvironment;
    protected EnvironmentCredentials TradeshiftCredentials;
    protected OSeriesConfiguration oSeriesConfiguration;
    public String TRADESHIFT_TAX_LOOKUP_URL = null;
    public String oseriesURL = null;

    private DBEnvironmentDescriptors getEnvironmentDescriptor() {
        return DBEnvironmentDescriptors.TRADESHIFT_CONNECTOR;
    }

    /**
     * gets sign on information such as username, password, and url from SQL server
     */
    @Override
    public TradeshiftConnectorSignInPage loadInitialTestPage() {
        DBConnectorNames OSERIES_VAR = SQLConnection.getOSeriesDefaults();
        try {
            oSeriesConfiguration = SQLConnection.getOSeriesConfiguration(OSERIES_VAR);

            TradeshiftEnvironment = SQLConnection.getEnvironmentInformation(
                    DBConnectorNames.TRADESHIFT, DBEnvironmentNames.QA, getEnvironmentDescriptor());
            TradeshiftCredentials = getEnvironmentCredentials(TradeshiftEnvironment);
            tradeshiftUrl = TradeshiftEnvironment.getEnvironmentUrl();
            signInUsername = TradeshiftCredentials.getUsername();
            signInPassword = TradeshiftCredentials.getPassword();
        } catch (Exception e) {
            e.printStackTrace();
        }

        TRADESHIFT_TAX_LOOKUP_URL = oSeriesConfiguration.getAddressServiceUrl();
        oseriesURL = TRADESHIFT_TAX_LOOKUP_URL.split("services")[0];

        TradeshiftConnectorSignInPage signInPage = loadSignOnPage();
        return signInPage;
    }

    protected TradeshiftConnectorSignInPage loadSignOnPage( )
    {
        TradeshiftConnectorSignInPage signOnPage;

        String url = this.tradeshiftUrl;

        driver.get(url);

        signOnPage = new TradeshiftConnectorSignInPage(driver);

        return signOnPage;
    }

    protected TradeshiftConnectorHomePage signInToAdmin(final TradeshiftConnectorSignInPage signOnPage )
    {

        return signOnPage.loginAsUser(signInUsername,signInPassword);
    }

    /**
     * Checks the tradeshift connector version number
     *
     * @return The connector version
     * */
    public String getConnectorVersionNumber(){
        TradeshiftConnectorSignInPage signInPage = new TradeshiftConnectorSignInPage(driver);
        signInPage.loginAsUser(signInUsername,signInPassword);
        TradeshiftConnectorHomePage homePage = new TradeshiftConnectorHomePage(driver);
        return homePage.getConnectorVersionNumber();
    }

    /**
     * Checks the tradeshift connector database status
     *
     * @return The database status
     * */
    public String getConnectorDatabaseStatus(){
        TradeshiftConnectorSignInPage signInPage = new TradeshiftConnectorSignInPage(driver);
        signInPage.loginAsUser(signInUsername,signInPassword);
        TradeshiftConnectorHomePage homePage = new TradeshiftConnectorHomePage(driver);
        return homePage.getConnectorDBStatus();
    }

    /**
     * Checks the tradeshift connector tenant status
     *
     * @return The tenant status
     * */
    public String getConnectorTenantStatus(){
        TradeshiftConnectorSignInPage signInPage = new TradeshiftConnectorSignInPage(driver);
        signInPage.loginAsUser(signInUsername,signInPassword);
        TradeshiftConnectorHomePage homePage = new TradeshiftConnectorHomePage(driver);
        return homePage.getConnectorTenantStatus();
    }

    /**
     * Creates a new tenant in the Tradeshift connector
     * */
    public void createTenant(){
        TradeshiftConnectorSignInPage signInPage = new TradeshiftConnectorSignInPage(driver);
        adjustWindowSize();
        signInPage.loginAsUser(signInUsername,signInPassword);
        TradeshiftConnectorHomePage homePage = new TradeshiftConnectorHomePage(driver);
        homePage.clickConfigurationDropDown();
        homePage.toTenantPage();

        TradeshiftConnectorTenantPage tenantPage = new TradeshiftConnectorTenantPage(driver);
        tenantPage.addTenant(oseriesURL);
        tenantPage.refreshPage();
        tenantPage.deleteTenant("99");
    }

    /**
     * Create a new request mapping in the tradeshift connector
     * */
    public void createRequestMapping(){
        TradeshiftConnectorSignInPage signInPage = new TradeshiftConnectorSignInPage(driver);
        adjustWindowSize();
        signInPage.loginAsUser(signInUsername,signInPassword);
        TradeshiftConnectorHomePage homePage = new TradeshiftConnectorHomePage(driver);
        homePage.clickConfigurationDropDown();
        homePage.toRequestMappingsPage();

        TradeshiftConnectorRequestMappingPage requestMappingPage = new TradeshiftConnectorRequestMappingPage(driver);
        requestMappingPage.addRequestMapping();
        requestMappingPage.deleteRequestMapping("Account Segment 99");
    }

    /**
     * attempt to create two tenants with the same tenant id
     * and validate that an error occurs
     *
     * @return the error message
     * */
    public String duplicateTenant(){
        TradeshiftConnectorSignInPage signInPage = new TradeshiftConnectorSignInPage(driver);
        adjustWindowSize();
        signInPage.loginAsUser(signInUsername,signInPassword);
        TradeshiftConnectorHomePage homePage = new TradeshiftConnectorHomePage(driver);
        homePage.clickConfigurationDropDown();
        homePage.toTenantPage();

        TradeshiftConnectorTenantPage tenantPage = new TradeshiftConnectorTenantPage(driver);
        tenantPage.addTenant(oseriesURL);
        String eMessage = tenantPage.addDuplicateTenant();
        tenantPage.refreshPage();
        tenantPage.deleteTenant("99");
        return eMessage;
    }

    public String duplicateRequestMapping(){
        TradeshiftConnectorSignInPage signInPage = new TradeshiftConnectorSignInPage(driver);
        adjustWindowSize();
        signInPage.loginAsUser(signInUsername,signInPassword);
        TradeshiftConnectorHomePage homePage = new TradeshiftConnectorHomePage(driver);
        homePage.clickConfigurationDropDown();
        homePage.toRequestMappingsPage();

        TradeshiftConnectorRequestMappingPage requestMappingPage = new TradeshiftConnectorRequestMappingPage(driver);
        requestMappingPage.addRequestMapping();
        String eMessage = requestMappingPage.addDuplicateRequestMapping();
        requestMappingPage.deleteRequestMapping("Account Segment 99");
        return eMessage;
    }

    /**
     * Adjusts the window size for tests to assure
     * they will pass in Jenkins
     * */
    public void adjustWindowSize(){
        if (driver.manage().window().getSize().width <= 1294 ) {
            driver.manage().window().setSize(new Dimension(1616, 876));
        }
    }
    /**
     * Creates a new tenant and adds a UN/ECE code to it
     *
     * @return codesSet
     * */
    public boolean createUnEceTenant(){
        boolean codesSet;

        TradeshiftConnectorSignInPage signInPage = new TradeshiftConnectorSignInPage(driver);
        adjustWindowSize();
        signInPage.loginAsUser(signInUsername,signInPassword);

        TradeshiftConnectorHomePage homePage = new TradeshiftConnectorHomePage(driver);
        homePage.clickConfigurationDropDown();
        homePage.toTenantPage();

        TradeshiftConnectorTenantPage tenantPage = new TradeshiftConnectorTenantPage(driver);
        tenantPage.addTenant(oseriesURL);
        tenantPage.editTenant("99");
        tenantPage.addUnEceTenantCodes();
        tenantPage.editTenant("99");
        String categoryCode = tenantPage.getUnEceCategoryCodeDropDownValue();
        String schemeCode = tenantPage.getUnEceSchemeCodeDropDownValue();
        tenantPage.clickSaveTenant();
        tenantPage.refreshPage();
        tenantPage.deleteTenant("99");
        codesSet = (categoryCode.equals("Flex Code 1") && schemeCode.equals("Flex Code 2"));


        return codesSet;
    }

    /**
     * Adds UN/ECE codes to a tenant and saves them
     * Assures that the codes can be cleared from the tenant
     *
     * @return codesDeleted
     * */
    public boolean clearTenantCodes(){
        boolean codesDeleted;

        TradeshiftConnectorSignInPage signInPage = new TradeshiftConnectorSignInPage(driver);
        adjustWindowSize();
        signInPage.loginAsUser(signInUsername,signInPassword);
        TradeshiftConnectorHomePage homePage = new TradeshiftConnectorHomePage(driver);
        homePage.clickConfigurationDropDown();
        homePage.toTenantPage();

        TradeshiftConnectorTenantPage tenantPage = new TradeshiftConnectorTenantPage(driver);
        tenantPage.editTenant("a0cddd0f-aac0-4abe-ad2d-289c1aa862bb");
        tenantPage.addUnEceTenantCodes();
        tenantPage.editTenant("a0cddd0f-aac0-4abe-ad2d-289c1aa862bb");
        tenantPage.clearTenantCodes();
        tenantPage.editTenant("a0cddd0f-aac0-4abe-ad2d-289c1aa862bb");
        String categoryCode = tenantPage.getUnEceCategoryCodeDropDownValue();
        String schemeCode = tenantPage.getUnEceSchemeCodeDropDownValue();

        codesDeleted = (categoryCode.equals("Select...") && schemeCode.equals("Select..."));

        return codesDeleted;

    }

    /**
     * Adds default UN/ECE codes to a tenant and saves them
     * Assures that the codes can be cleared from the tenant
     *
     * @return codesSet
     * */
    public boolean createCustomTenantCodes(){
        boolean codesSet;

        TradeshiftConnectorSignInPage signInPage = new TradeshiftConnectorSignInPage(driver);
        adjustWindowSize();
        signInPage.loginAsUser(signInUsername,signInPassword);
        TradeshiftConnectorHomePage homePage = new TradeshiftConnectorHomePage(driver);
        homePage.clickConfigurationDropDown();
        homePage.toTenantPage();

        TradeshiftConnectorTenantPage tenantPage = new TradeshiftConnectorTenantPage(driver);
        tenantPage.addTenant(oseriesURL);
        tenantPage.editTenant("99");
        tenantPage.clearDefaultTenantCodes();
        tenantPage.addDefaultTenantCodes("dc2","dc1");
        String defaultCategory = tenantPage.getUnEceCategoryCodeDefaultValue();
        String defaultScheme = tenantPage.getUnEceSchemeCodeDefaultValue();
        tenantPage.clickSaveTenant();
        tenantPage.refreshPage();
        tenantPage.deleteTenant("99");

        codesSet = (defaultCategory.equals("dc2") && defaultScheme.equals("dc1"));

        return codesSet;
    }

    /**
     * Creates a new tenant and enables Accruals
     * for that tenant
     * */
    public boolean enableTenantAccrual(){
        boolean accrualEnabled;

        TradeshiftConnectorSignInPage signInPage = new TradeshiftConnectorSignInPage(driver);
        adjustWindowSize();
        signInPage.loginAsUser(signInUsername,signInPassword);
        TradeshiftConnectorHomePage homePage = new TradeshiftConnectorHomePage(driver);
        homePage.clickConfigurationDropDown();
        homePage.toTenantPage();

        TradeshiftConnectorTenantPage tenantPage = new TradeshiftConnectorTenantPage(driver);
        tenantPage.addTenant(oseriesURL);
        tenantPage.editTenant("99");
        tenantPage.enableAccruals();
        tenantPage.clickSaveTenant();

        tenantPage.editTenant("99");
        tenantPage.toAccrualTab();
        accrualEnabled = tenantPage.getAccrualsValue().equals("1");

        tenantPage.refreshPage();
        tenantPage.deleteTenant("99");

        return accrualEnabled;
    }

    /**
     * Creates a new tenant and enables the tax engine request/response
     * logging option for that tenant
     *
     * @return the value of the tax engine dropdown
     * */
    public String enableTenantTaxEngineResponse(){
        String taxEngineDropdownValue;

        TradeshiftConnectorSignInPage signInPage = new TradeshiftConnectorSignInPage(driver);
        adjustWindowSize();
        signInPage.loginAsUser(signInUsername,signInPassword);
        TradeshiftConnectorHomePage homePage = new TradeshiftConnectorHomePage(driver);
        homePage.clickConfigurationDropDown();
        homePage.toTenantPage();

        TradeshiftConnectorTenantPage tenantPage = new TradeshiftConnectorTenantPage(driver);
        tenantPage.addTenant(oseriesURL);
        tenantPage.editTenant("99");
        tenantPage.toLoggingTab();
        tenantPage.disableTaxEngineResponseValue();

        tenantPage.editTenant("99");
        tenantPage.toLoggingTab();
        tenantPage.enableTaxEngineResponseValue();

        taxEngineDropdownValue = tenantPage.getTaxEngineResponseValue();

        tenantPage.refreshPage();
        tenantPage.deleteTenant("99");


        return taxEngineDropdownValue;
    }

    /**
     * Creates a new tenant and disables the tax engine request/response
     * logging option for that tenant
     *
     * @return the value of the tax engine dropdown
     * */
    public String disableTenantTaxEngineResponse(){
        String taxEngineDropdownValue;

        TradeshiftConnectorSignInPage signInPage = new TradeshiftConnectorSignInPage(driver);
        adjustWindowSize();
        signInPage.loginAsUser(signInUsername,signInPassword);
        TradeshiftConnectorHomePage homePage = new TradeshiftConnectorHomePage(driver);
        homePage.clickConfigurationDropDown();
        homePage.toTenantPage();

        TradeshiftConnectorTenantPage tenantPage = new TradeshiftConnectorTenantPage(driver);
        tenantPage.addTenant(oseriesURL);
        tenantPage.editTenant("99");
        tenantPage.toLoggingTab();
        tenantPage.disableTaxEngineResponseValue();

        taxEngineDropdownValue = tenantPage.getTaxEngineResponseValue();

        tenantPage.refreshPage();
        tenantPage.deleteTenant("99");


        return taxEngineDropdownValue;
    }

	/**
	 * Creates a new tenant with a tax code configuration
	 *
	 * @return true if the values were properly set
	 * */
	public boolean createTenantWithTaxConfiguration(){
		boolean taxCodesSet;

		TradeshiftConnectorSignInPage signInPage = new TradeshiftConnectorSignInPage(driver);
		signInPage.loginAsUser(signInUsername,signInPassword);
		TradeshiftConnectorHomePage homePage = new TradeshiftConnectorHomePage(driver);
		homePage.clickConfigurationDropDown();
		homePage.toTenantPage();

		TradeshiftConnectorTenantPage tenantPage = new TradeshiftConnectorTenantPage(driver);
		tenantPage.addTenant(oseriesURL);

		tenantPage.editTenant("99");
		tenantPage.toTaxCongfigurationTab();

		taxCodesSet = tenantPage.getVendorTaxCode().equals("VENDOR_CODE")
					  && tenantPage.getConsumerTaxCode().equals("CONSUMER_CODE")
					  && tenantPage.getPartialTaxCode().equals("PARTIAL_CODE");

		tenantPage.refreshPage();
		tenantPage.deleteTenant("99");


		return taxCodesSet;
	}
}