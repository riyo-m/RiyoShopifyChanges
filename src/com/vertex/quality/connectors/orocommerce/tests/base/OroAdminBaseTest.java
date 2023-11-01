package com.vertex.quality.connectors.orocommerce.tests.base;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.orocommerce.pages.admin.OroAdminHomePage;
import com.vertex.quality.connectors.orocommerce.pages.admin.OroAdminLoginPage;

import static com.vertex.quality.common.utils.SQLConnection.getEnvironmentCredentials;

/**
 * @author alewis
 */
public class OroAdminBaseTest extends VertexUIBaseTest<OroAdminLoginPage>
{

	protected String OroURL;
	protected EnvironmentInformation OroCommerceEnvironment;
	protected EnvironmentCredentials OroCommerceCredentials;
	protected String username;
	protected String password;

	/**
	 * Used to be able to set environment descriptor from the child base test
	 *
	 * @return the environment descriptor based on the base test
	 */
	protected DBEnvironmentDescriptors getPortalEnvironmentDescriptor( )
	{
		return DBEnvironmentDescriptors.ORO_ADMIN;
	}

	/**
	 * Gets credentials for the connector from the database
	 */
	@Override
	public OroAdminLoginPage loadInitialTestPage( )
	{
		try
		{
			OroCommerceEnvironment = SQLConnection.getEnvironmentInformation(DBConnectorNames.ORO_COMMERCE,
				DBEnvironmentNames.QA, getPortalEnvironmentDescriptor());
			OroCommerceCredentials = getEnvironmentCredentials(OroCommerceEnvironment);
			OroURL = OroCommerceEnvironment.getEnvironmentUrl();
			username = OroCommerceCredentials.getUsername();
			password = OroCommerceCredentials.getPassword();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		OroAdminLoginPage signOnPage = loadSignOnPage();
		return signOnPage;
	}

	/**
	 * bring up the oro admin login page
	 *
	 * @return a representation of this site's login screen
	 */
	public OroAdminLoginPage loadSignOnPage( )
	{
		OroAdminLoginPage signOnPage;

		String url = this.OroURL;

		driver.get(url);

		VertexLogger.log(String.format("Oro Commerce Environment URL - %s", url), VertexLogLevel.DEBUG, getClass());

		signOnPage = new OroAdminLoginPage(driver);

		return signOnPage;
	}

	/**
	 * do the login process in the login page
	 *
	 * @return new object of the oro admin home page
	 */
	public OroAdminHomePage signInToAdmin( final OroAdminLoginPage signOnPage )
	{

		return signOnPage.loginAsUser(this.username, this.password);
	}
}
