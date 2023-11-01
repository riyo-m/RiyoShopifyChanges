package com.vertex.quality.connectors.dynamics365.sales.tests.base;
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
import com.vertex.quality.connectors.dynamics365.sales.pages.SalesAdminHomePage;
import com.vertex.quality.connectors.dynamics365.sales.pages.SalesSignInPage;
import com.vertex.quality.connectors.dynamics365.sales.pages.SalesVertexAdminPage;
import org.openqa.selenium.Dimension;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
/**
 * base test class for sales environment
 *
 * @author Shruti
 */
@Test(groups = { "D365_CRM_Sales" })
public abstract class SalesBaseTest extends VertexUIBaseTest<SalesAdminHomePage> {
    private final String CONFIG_DETAILS_FILE_PATH = CommonDataProperties.CONFIG_DETAILS_FILE_PATH;

    protected String username;
    protected String password;
    protected String environmentURL;
    protected String trustedId;
    protected String companyCode;

    public String CRM_TRUSTED_ID = null;
    public String CRM_TAX_CALCULATION_URL = null;
    public String CRM_ADDRESS_VALIDATION_URL = null;

    /**
     * Configure Vertex Admin with trusted ID, tax calculation URL, and address validation URL from getCredentials()
     */
    protected void configureVertexConnection() {
        SalesVertexAdminPage adminPage = new SalesVertexAdminPage(driver);
        adminPage.navigateToVertexSettings();
        adminPage.setTrustedID(CRM_TRUSTED_ID);
        adminPage.setTaxCalculationURL(CRM_TAX_CALCULATION_URL);
        adminPage.setAddressValidationURL(CRM_ADDRESS_VALIDATION_URL);
        adminPage.clickSave();

        adminPage.navigateToSalesHomePage();
    }

    /**
     * this signs in to application, adjusts window size
     * @return  admin home page
     * */
    @Override
    protected SalesAdminHomePage loadInitialTestPage(){
        getCredentials();

        SalesAdminHomePage homePage = signInToHomePage();
        VertexLogger.log("Current window size: "+ driver.manage().window().getSize());
        if (driver.manage().window().getSize().width <= 1294 )
        {
            driver.manage().window().setSize(new Dimension(1616, 876));
        }

        return homePage;
    }
    /**
     * this method does the whole sign in process to CRM environment
     * does the sign in process over three different pages
     * @return new instance of the admin home page
     * */
    public SalesAdminHomePage signInToHomePage(){
        SalesSignInPage salesLogin=navigateToSignInPage();
        salesLogin.enterEmailAddress(username);
        salesLogin.enterPassword(password);
        SalesAdminHomePage homePage=salesLogin.clickDoNotStayLoggedIn();
        return homePage;
    }

    /**
     * Configure Vertex Admin with trusted ID, tax calculation URL, and address validation URL from getCredentials()
     * @param homePage
     */
    protected void configureVertexConnection(SalesAdminHomePage homePage) {
        SalesVertexAdminPage adminPage = homePage.navigateToSalesVertexAdminPage();
        adminPage.navigateToVertexSettings();
        adminPage.setTrustedID(CRM_TRUSTED_ID);
        adminPage.setTaxCalculationURL(CRM_TAX_CALCULATION_URL);
        adminPage.setAddressValidationURL(CRM_ADDRESS_VALIDATION_URL);
        adminPage.clickSave();

        adminPage.navigateToSalesHomePage();
    }


    /**
     * opens the application's sign in page
     * @return the sign in page
     */
    protected SalesSignInPage navigateToSignInPage(){
        driver.get(environmentURL);
        SalesSignInPage signInPage=new SalesSignInPage(driver);
        return  signInPage;
    }

    /**
     * Gets credentials for the connector from the database
     */
    protected void getCredentials()
    {
        try {
            ReadProperties readConfigDetails = new ReadProperties(CONFIG_DETAILS_FILE_PATH);

            username = readConfigDetails.getProperty("TEST.VERTEX.D365.USERNAME");
            password = readConfigDetails.getProperty("TEST.VERTEX.D365.PASSWORD");
            
            environmentURL = "https://vtx-qa.crm.dynamics.com/";

            String oseriesEnv = System.getProperty("oseries");
            if (oseriesEnv == null) {
                oseriesEnv = "classic";
            }

            switch(oseriesEnv) {
                case "classic":
                    CRM_TRUSTED_ID = "VTXTST123";
                    CRM_TAX_CALCULATION_URL = "https://oseries9-final.vertexconnectors.com/vertex-ws/services/CalculateTax70";
                    CRM_ADDRESS_VALIDATION_URL = "https://oseries9-final.vertexconnectors.com/vertex-ws/services/LookupTaxAreas70";
                    break;

                case "cloud":
                    CRM_TRUSTED_ID = "3368513061430108";
                    CRM_TAX_CALCULATION_URL = "https://qaconnect.vertexsmb.com/vertex-ws/services/CalculateTax70";
                    CRM_ADDRESS_VALIDATION_URL = "https://qaconnect.vertexsmb.com/vertex-ws/services/LookupTaxAreas70";
                    break;
            }
        } catch (Exception e) {
            VertexLogger.log("unable to load the environment information/credentials for a d365 sales test",
                    VertexLogLevel.ERROR);
            e.printStackTrace();
        }
    }

}
