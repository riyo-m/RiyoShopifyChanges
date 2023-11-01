package com.vertex.quality.connectors.ariba.connector.tests.misc;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavOption;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnHomePage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnSignOnPage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnSystemStatusPage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnVertexServicesPage;
import com.vertex.quality.connectors.ariba.connector.tests.base.AribaConnBaseTest;
import org.testng.annotations.Test;
import java.util.Set;
import static org.testng.Assert.assertTrue;

/**
 * Tests two connector applications of different versions of the software being run on the same server
 * by trying to access the UI's of both connector applications at almost the same time
 *
 * @author ssalisbury
 */
@SuppressWarnings("Duplicates")
public class AribaConnConcurrentApplicationVersionTests extends AribaConnBaseTest
{
	protected final DBEnvironmentDescriptors config1Descriptor = DBEnvironmentDescriptors.ARIBA_CONFIG;
	protected final DBEnvironmentDescriptors config2Descriptor = DBEnvironmentDescriptors.ARIBA2_0_CONFIG;

	protected boolean wasVersion1Chosen = false;

	protected String config1SignOnUrl = null;
	protected String config2SignOnUrl = null;

	protected String config1Username = null;
	protected String config1Password = null;

	protected String config2Username = null;
	protected String config2Password = null;

	protected String secondApplicationUrl = null;
	protected String secondApplicationUsername = null;
	protected String secondApplicationPassword = null;

	//checks that the version has the main release number (i.e. '1' for 1.0 vs '2' for 2.0) 
	// followed three times by a period and then a 1 or 2 digit number
	protected final String config1VersionFormat = "1\\.\\d\\d?\\.\\d\\d?\\.\\d\\d?";
	protected final String config2VersionFormat = "2\\.\\d\\d?\\.\\d\\d?\\.\\d\\d?";

	@Override
	@Test(groups = { "ariba_ui","ariba_regression" })
	public AribaConnSignOnPage loadInitialTestPage( )
	{
		AribaConnSignOnPage startPage = super.loadInitialTestPage();

		populateApplicationsInformation();

		final String environmentChoiceLogMessage = String.format("Starting environment had descriptor: %s",
			environmentChoice.getDescriptor());
		VertexLogger.log(environmentChoiceLogMessage);

		return startPage;
	}

	/**
	 * fetches information from the database about the connector application that wasn't initially chosen/loaded by
	 * AribaUiBaseTest
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	protected void populateApplicationsInformation( )
	{
		DBEnvironmentDescriptors otherEnvironment = null;
		if ( environmentChoice.equals(config1Descriptor) )
		{
			wasVersion1Chosen = true;

			config1SignOnUrl = currConfigSignOnUrl;
			config1Username = configUsername;
			config1Password = configPassword;

			otherEnvironment = config2Descriptor;
		}
		else if ( environmentChoice.equals(config2Descriptor) )
		{
			wasVersion1Chosen = false;

			config2SignOnUrl = currConfigSignOnUrl;
			config2Username = configUsername;
			config2Password = configPassword;

			otherEnvironment = config1Descriptor;
		}
		else
		{
			String badEnvironmentMessage = String.format("invalid environment loaded by test setup: %s",
				environmentChoice.getDescriptor());
			throw new RuntimeException(badEnvironmentMessage);
		}

		try
		{
			EnvironmentInformation environmentInfo = SQLConnection.getEnvironmentInformation(DBConnectorNames.ARIBA,
				DBEnvironmentNames.QA, otherEnvironment);
			EnvironmentCredentials credentials = SQLConnection.getEnvironmentCredentials(environmentInfo);

			if ( wasVersion1Chosen )
			{
				config2SignOnUrl = environmentInfo.getEnvironmentUrl();
				config2Username = credentials.getUsername();
				config2Password = credentials.getPassword();

				secondApplicationUrl = config2SignOnUrl;
				secondApplicationUsername = config2Username;
				secondApplicationPassword = config2Password;
			}
			else
			{
				config1SignOnUrl = environmentInfo.getEnvironmentUrl();
				config1Username = credentials.getUsername();
				config1Password = credentials.getPassword();

				secondApplicationUrl = config1SignOnUrl;
				secondApplicationUsername = config1Username;
				secondApplicationPassword = config1Password;
			}
		}
		catch ( Exception e )
		{
			VertexLogger.log(
				"failed to set up a test of simultaneously accessing multiple instances of the Ariba connector UI",
				VertexLogLevel.ERROR);
			e.printStackTrace();
		}
	}
}
