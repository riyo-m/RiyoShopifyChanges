package com.vertex.quality.connectors.ariba.supplier.tests;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.ariba.api.enums.AribaAPIRequestType;
import com.vertex.quality.connectors.ariba.api.enums.AribaAPIType;
import com.vertex.quality.connectors.ariba.common.utils.AribaAPITestUtilities;
import com.vertex.quality.connectors.ariba.supplier.pages.AribaSupplierHomePage;
import com.vertex.quality.connectors.ariba.supplier.pages.AribaSupplierInboxPage;
import com.vertex.quality.connectors.ariba.supplier.pages.AribaSupplierSignOnPage;

import java.sql.SQLException;

/**
 * currently just stores constants & utility functions that are used by many or
 * all tests of ariba's configuration site
 *
 * @author dgorecki , osabha
 */
public abstract class AribaSupplierBaseTest extends VertexUIBaseTest<AribaSupplierSignOnPage>
{
	protected String aribaSupplierURL;
	protected AribaAPITestUtilities apiUtil;
	protected EnvironmentInformation environmentInformation;
	protected EnvironmentCredentials environmentCredentials;
	protected String username;
	protected String password;
	protected String environmentURL;
	protected final String duck = "Duck-New";
	protected final String chicken = "Chicken-New";
	protected final String waterService = "Water Testing Service";
	protected final String elephant = "Elephant-New";

	/**
	 * Used to be able to set environment descriptor from the child base test
	 *
	 * @return the environment descriptor based on the base test
	 */
	protected DBEnvironmentDescriptors getEnvironmentDescriptor( )
	{
		return DBEnvironmentDescriptors.ARIBA2_0_SUPPLIER;
	}

	/**
	 * Gets credentials for the connector from the database
	 */
	@Override
	public AribaSupplierSignOnPage loadInitialTestPage( )
	{
		try
		{
			apiUtil = new AribaAPITestUtilities(AribaAPIType.INVOICE_RECONCILIATION,
				AribaAPIRequestType.TAX_CALCULATION);
			environmentInformation = SQLConnection.getEnvironmentInformation(DBConnectorNames.ARIBA,
				DBEnvironmentNames.QA, getEnvironmentDescriptor());
			//environmentCredentials = SQLConnection.getEnvironmentCredentials(environmentInformation);
//			aribaSupplierURL = environmentInformation.getEnvironmentUrl();   temp fix
			aribaSupplierURL = "https://service.ariba.com/Supplier.aw";
			username = "test-dahra.williamspartnersharedprod020@sap.com";
			password = "Vertex2021!";
//			username = environmentCredentials.getUsername(); temp fix. Method needs to be redone
//			password = environmentCredentials.getPassword();
//			environmentURL = environmentInformation.getEnvironmentUrl();

		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		AribaSupplierSignOnPage signOnPage = loadSupplierSite();
		return signOnPage;
	}

	/**
	 * logs into ariba's Supplier site
	 *
	 * @return the home page of ariba's Supplier site
	 */
	protected AribaSupplierHomePage signInToSupplier( final AribaSupplierSignOnPage signOnPage )
	{
		VertexLogger.log("Signing in to Ariba Supplier Site...", VertexLogLevel.INFO, getClass());

		return signOnPage.loginAsUser(username, password);
	}

	/**
	 * loads the login page of ariba's Supplier site
	 *
	 * @return a representation of this site's login screen
	 */
	protected AribaSupplierSignOnPage loadSupplierSite( )
	{
		AribaSupplierSignOnPage signOnPage = null;
		String url = this.aribaSupplierURL;

		driver.get(url);

		VertexLogger.log(String.format("Ariba Supplier URL - %s", url), VertexLogLevel.DEBUG, getClass());
		signOnPage = new AribaSupplierSignOnPage(driver);
		return signOnPage;
	}

	protected void tryStoreInvoiceId( final String invoiceId, final String testCaseName )
	{
		try
		{
			SQLConnection.storeInvoiceId(invoiceId, testCaseName);
		}
		catch ( SQLException e )
		{
			e.printStackTrace();
		}
	}

	protected void retrieveAndOpenPurchaseOrder( final AribaSupplierInboxPage inboxPage, final String testCaseName )
	{
		String poNumber = null;
		try
		{
			poNumber = SQLConnection.retrieveOrderNumber(testCaseName);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		if ( poNumber != null )
		{
			inboxPage.selectTargetPo(poNumber);
		}
		else
		{
			String message = String.format("unable to find the purchase order created by the test case %s",
				testCaseName);
			VertexLogger.log(message, VertexLogLevel.FATAL);
		}
	}
}

