package com.vertex.quality.connectors.sitecorexc.common.tests;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.connectors.sitecorexc.admin.pages.SiteCoreXCAdminHomePage;
import com.vertex.quality.connectors.sitecorexc.admin.pages.SiteCoreXCAdminLoginPage;

import static com.vertex.quality.common.utils.SQLConnection.getEnvironmentCredentials;

/**
 * Class for common methods for SiteCoreXC Admin UI
 *
 * @author Vivek.Kumar
 */
public class SiteCoreXCAdminBaseTest extends VertexUIBaseTest<SiteCoreXCAdminLoginPage> {

    protected String signInUsername;
    protected String signInPassword;
    protected String siteCoreXCUrl;
    protected String environmentURL;
    protected EnvironmentInformation siteCoreXCEnvironment;
    protected EnvironmentCredentials siteCoreXCCredentials;

    private DBEnvironmentDescriptors getEnvironmentDescriptor() {
        return DBEnvironmentDescriptors.SITECOREXC_ADMIN;
    }

    /**
     * gets sign on information such as username, password, and url from SQL server
     */
    @Override
    public SiteCoreXCAdminLoginPage loadInitialTestPage() {
        try {

            siteCoreXCEnvironment = SQLConnection.getEnvironmentInformation(DBConnectorNames.SITECORE_XC, DBEnvironmentNames.QA,
                    getEnvironmentDescriptor());
            siteCoreXCCredentials = getEnvironmentCredentials(siteCoreXCEnvironment);
            siteCoreXCUrl = siteCoreXCEnvironment.getEnvironmentUrl();
            signInUsername = siteCoreXCCredentials.getUsername();
            signInPassword = siteCoreXCCredentials.getPassword();
        } catch (Exception e) {
            e.printStackTrace();
        }


        SiteCoreXCAdminLoginPage signInPage = loadSignOnPage();
        return signInPage;
    }

    /**
     * method for getting url and loading signOn Page.
     *
     * @return signOnPage after getting connector url.
     */
    protected SiteCoreXCAdminLoginPage loadSignOnPage() {
        SiteCoreXCAdminLoginPage signOnPage;

        String url = this.siteCoreXCUrl;

        driver.get(url);

        signOnPage = new SiteCoreXCAdminLoginPage(driver);

        return signOnPage;
    }

    /**
     * method for signing into connector url.
     *
     * @param signOnPage for SiteCoreXCAdminLoginPage
     * @return signOnPage SiteCoreXCAdminHomePage after logging into connector url.
     */
    protected SiteCoreXCAdminHomePage signInToAdmin(final SiteCoreXCAdminLoginPage signOnPage) {
        return signOnPage.loginAsUser(signInUsername, signInPassword);
    }
}
