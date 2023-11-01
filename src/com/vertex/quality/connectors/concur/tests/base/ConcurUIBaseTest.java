package com.vertex.quality.connectors.concur.tests.base;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.connectors.concur.enums.ConcurHeaderTab;
import com.vertex.quality.connectors.concur.pages.base.ConcurBasePage;
import com.vertex.quality.connectors.concur.pages.misc.ConcurHomePage;
import com.vertex.quality.connectors.concur.pages.misc.ConcurSignOnPage;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

/**
 * class contains generic tests methods for other test classes
 *
 * @author alewis
 */
@Test(groups = { "concur" })
public class ConcurUIBaseTest extends VertexUIBaseTest<ConcurSignOnPage> {
	protected String signInUsername;
	protected String signInPassword;
	protected String concurUrl;
	protected String environmentURL;

	private DBEnvironmentDescriptors getEnvironmentDescriptor() {
		return DBEnvironmentDescriptors.CONCUR;
	}

	/**
	 * gets sign on information such as username, password, and url from SQL server
	 */
	@Override
	public ConcurSignOnPage loadInitialTestPage() {
		try {
			EnvironmentInformation environmentInformation = SQLConnection.getEnvironmentInformation(
					DBConnectorNames.CONCUR, DBEnvironmentNames.QA, getEnvironmentDescriptor());
			EnvironmentCredentials environmentCredentials = SQLConnection.getEnvironmentCredentials(
					environmentInformation);
			concurUrl = environmentInformation.getEnvironmentUrl();
			signInUsername = environmentCredentials.getUsername();
			signInPassword = environmentCredentials.getPassword();
			environmentURL = environmentInformation.getEnvironmentUrl();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ConcurSignOnPage signOnPage = loadPortal();
		return signOnPage;
	}

	/**
	 * loads the login page of concur's portal site
	 *
	 * @return a representation of this site's login screen
	 */
	protected ConcurSignOnPage loadPortal() {
		ConcurSignOnPage signOnPage = null;
		String url = this.concurUrl;

		driver.get(url);
		signOnPage = new ConcurSignOnPage(driver);

		return signOnPage;
	}

	/**
	 * enters username and password and signs into page
	 *
	 * @param signOnPage concur sign on page
	 * @return home page
	 */
	protected ConcurHomePage signIntoConcur(final ConcurSignOnPage signOnPage) {
		ConcurHomePage homepage = signOnPage.loginToConcurHomePage(signInUsername, signInPassword);
		return homepage;
	}

	/**
	 * Click panel button
	 *
	 * @param tab possible buttons {Sap Concur, Expense, Invoice, Approvals, or App Center}
	 * @return page loaded after button panel is clicked
	 */
	protected <T extends ConcurBasePage> T navigateToHeaderTabPage(final ConcurHeaderTab tab,
																   final ConcurSignOnPage signOnPage) {
		ConcurHomePage homePage = signIntoConcur(signOnPage);
		boolean isLogin = homePage.checkIfLoggedIn();
		assertTrue(isLogin, "Unable to login to concur homepage");
		T appPage = homePage.navigateToHeaderPage(tab);
		return appPage;
	}
	/**
	 * Runs the batch job to process invoices and make tax call to Vertex
	 */
	public static void runBatchJobUI() {
		Response response = given().contentType(("application/x-www-form-urlencoded")).post("http://connector-dev.vertexconnectors.com:8101/connector-concur/api/jobs");

		//Doing this because it takes 5 seconds for the batch job to process the invoices
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
