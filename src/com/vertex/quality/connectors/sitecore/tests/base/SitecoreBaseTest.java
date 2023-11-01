package com.vertex.quality.connectors.sitecore.tests.base;

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
import com.vertex.quality.connectors.sitecore.common.enums.SitecoreItem;
import com.vertex.quality.connectors.sitecore.pages.SitecoreAdminHomePage;
import com.vertex.quality.connectors.sitecore.pages.SitecoreLogInPage;
import com.vertex.quality.connectors.sitecore.pages.store.SitecoreStorefrontHomePage;
import com.vertex.quality.connectors.sitecore.pojos.SitecoreCheckoutAmount;
import com.vertex.quality.connectors.sitecore.pojos.SitecoreItemValues;
import org.apache.commons.lang3.tuple.Pair;

import static org.testng.Assert.assertTrue;

/**
 * sitecore base test
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public abstract class SitecoreBaseTest extends VertexUIBaseTest
{

	private static ReadProperties readEnvUrls;
	private static ReadProperties readCredentials;

	private static String TEST_CREDENTIALS_FILE_PATH = CommonDataProperties.TEST_CREDENTIALS_FILE_PATH;
	private static String ENV_PROP_FILE_PATH = CommonDataProperties.ENV_PROP_FILE_PATH;

	public static String SITECORE_ADMIN_USERNAME = null;
	public static String SITECORE_ADMIN_PASSWORD = null;

	public static String SITECORE_STORE_USERNAME = null;
	public static String SITECORE_STORE_PASSWORD = null;

	public static String SITECORE_ADMIN_URL = null;
	public static String SITECORE_STORE_URL = null;

	public String USERNAME = "SampleTestUser";
	public String PASSWORD = "Welcome20!8";
	public String USER_ROLE = "User Role";

	public String COMPANY_NAME = "Valtech";
	public String ADDRESS_1 = "1041 old cassatt rd";
	public String ADDRESS_2 = "";
	public String COMPANY_CITY = "Berwyn";
	public String COMPANY_REGION = "PA";
	public String COMPANY_ZIP = "19312";
	public String COUNTRY = "United States";

	private DBEnvironmentDescriptors getEnvironmentDescriptor( )
	{
		return DBEnvironmentDescriptors.SITECORE;
	}

	@Override
	public SitecoreLogInPage loadInitialTestPage( )
	{

		try
		{
			EnvironmentInformation environmentInformation = SQLConnection.getEnvironmentInformation(
				DBConnectorNames.SITECORE, DBEnvironmentNames.QA, getEnvironmentDescriptor());
			EnvironmentCredentials environmentCredentials = SQLConnection.getEnvironmentCredentials(
				environmentInformation);
			SITECORE_ADMIN_URL = environmentInformation.getEnvironmentUrl();
			SITECORE_ADMIN_USERNAME = environmentCredentials.getUsername();
			SITECORE_ADMIN_PASSWORD = environmentCredentials.getPassword();
			//SITECORE_STORE_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.SITECORE.STORE.USERNAME");
			//SITECORE_STORE_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.SITECORE.STORE.PASSWORD");
			//SITECORE_STORE_URL = readEnvUrls.getProperty("TEST.ENV.SITECORE.STOREFRONT.URL");
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		SitecoreLogInPage signOnPage = launchStorefrontLoginPage();
		return signOnPage;
	}

	protected SitecoreLogInPage launchStorefrontLoginPage( )
	{

		SitecoreLogInPage signOnPage = null;
		String url = SITECORE_STORE_URL;

		VertexLogger.log(String.format("Launching Sitecore Storefront URL - %s", url),
			SitecoreBaseTest.class);
		signOnPage = new SitecoreLogInPage(driver);
		return signOnPage;
	}

	/**
	 * check if actual checkout values equal expected values
	 *
	 * @param sitecoreCheckoutAmount pair of expected and actual checkout values
	 */
	public void confirmCheckOutValues(
		final Pair<SitecoreCheckoutAmount, SitecoreCheckoutAmount> sitecoreCheckoutAmount )
	{
		SitecoreCheckoutAmount actualSitecoreCheckoutAmount = sitecoreCheckoutAmount.getLeft();
		SitecoreCheckoutAmount expectedSitecoreCheckoutAmount = sitecoreCheckoutAmount.getRight();

		double actualSubtotal = actualSitecoreCheckoutAmount.getSubtotal();
		double updatedItemTotal = expectedSitecoreCheckoutAmount.getSubtotal();
		double actualTaxAmount = actualSitecoreCheckoutAmount.getTax();
		double expectedTaxAmount = expectedSitecoreCheckoutAmount.getTax();
		double actualTotalAmount = actualSitecoreCheckoutAmount.getTotal();
		double expectedTotalAmount = expectedSitecoreCheckoutAmount.getTotal();

		// verify order summary
		assertTrue(Double.compare(actualSubtotal, updatedItemTotal) == 0,
			String.format("Expected Subtotal: %s, actual Subtotal: %s", updatedItemTotal, actualSubtotal));
		VertexLogger.log(String.format("Expected and actual Subtotal both are same (i.e. %s)", actualSubtotal),
			getClass());

		assertTrue(Double.compare(actualTaxAmount, expectedTaxAmount) == 0,
			String.format("Expected Tax amount: %s, actual Tax amount: %s", expectedTaxAmount, actualTaxAmount));
		VertexLogger.log(String.format("Expected and actual Tax amount both are same (i.e. %s)", actualTaxAmount),
			getClass());

		assertTrue(Double.compare(actualTotalAmount, expectedTotalAmount) == 0,
			String.format("Expected Subtotal: %s, actual Subtotal: %s", expectedTotalAmount, actualTotalAmount));
		VertexLogger.log(String.format("Expected and actual Subtotal both are same (i.e. %s)", actualTotalAmount),
			getClass());
	}

	/**
	 * check if actual item values equal expected values
	 *
	 * @param actualExpectedValues pair of expected and actual item values
	 * @param item                 name of item
	 */
	public void confirmValues( final Pair<SitecoreItemValues, SitecoreItemValues> actualExpectedValues,
		final SitecoreItem item )
	{
		SitecoreItemValues actualSitecoreItemValues = actualExpectedValues.getLeft();
		SitecoreItemValues expectedSitecoreItemValues = actualExpectedValues.getRight();

		double actualPrice = actualSitecoreItemValues.getPrice();
		double expectedPrice = expectedSitecoreItemValues.getPrice();
		int expectedQuantity = (int) Math.round(actualSitecoreItemValues.getQuantity());
		int actualQuantity = (int) Math.round(expectedSitecoreItemValues.getQuantity());
		double actualSubtotal = actualSitecoreItemValues.getSubtotal();
		double expectedSubtotal = expectedSitecoreItemValues.getSubtotal();

		assertTrue(Double.compare(actualPrice, expectedPrice) == 0,
			String.format("Expected Unit Price: %s, but actual Unit Price: %s", expectedPrice, actualPrice));
		VertexLogger.log(
			String.format("For Item: %s, expected and actual Unit Price are same (i.e. %s)", item.getName(),
				item.getPrice()), getClass());

		assertTrue(expectedQuantity == actualQuantity,
			String.format("Expected Quantity: %s, but actual Quantity: %s", expectedQuantity, actualQuantity));
		VertexLogger.log(String.format("For Item: %s, expected and actual Quantity are same (i.e. %s)", item.getName(),
			actualQuantity), getClass());

		assertTrue(Double.compare(actualSubtotal, expectedSubtotal) == 0,
			String.format("Expected Total: %s, but actual Total: %s", expectedSubtotal, actualSubtotal));
		VertexLogger.log(
			String.format("For Item: %s, expected and actual Total are same (i.e. %s)", item.getName(), actualSubtotal),
			getClass());
	}

	/**
	 * log in to store front
	 *
	 * @param username to set
	 * @param password to set
	 *
	 * @return sitecore storefront homepage
	 */
	public SitecoreStorefrontHomePage logInAsStorefrontUser( final String username, final String password )
	{

		VertexLogger.log("Signing in to Sitecore Storefront...", SitecoreBaseTest.class);
		// open Sitecore administration login page
		this.launchStorefrontLoginPage();

		// logout from store if any user is already logged in
		SitecoreStorefrontHomePage storeHome = new SitecoreStorefrontHomePage(driver);
		storeHome.logoutFromStoreIfAlreadyLoggedIn();

		// login as Sitecore administration user
		this.logInAsStoreUser(username, password);

		if ( !storeHome.isHomePageDisplayed() )
		{
			storeHome = null;
			VertexLogger.log("Sitecore Store home page logo not displayed", VertexLogLevel.ERROR,
				SitecoreBaseTest.class);
		}

		return storeHome;
	}

	/**
	 * login to store and delete items in cart
	 *
	 * @param username to login
	 * @param password to login
	 *
	 * @return homepage after login
	 */
	public SitecoreStorefrontHomePage loginAndEmptyShoppingCart( final String username, final String password )
	{
		SitecoreStorefrontHomePage homePage = logInAsStorefrontUser(username, password);

		int cartQuantity = homePage.getCartQuantity();

		if ( cartQuantity > 0 )
		{
			homePage.clickShoppingCartButton();
			homePage.removeAllItemsFromShoppingCart();
			homePage.clickBackToShoppingButton();
		}
		return homePage;
	}

	/**
	 * launch admin login page
	 */
	protected void launchAdminLoginPage( )
	{

		VertexLogger.log(String.format("Launching Sitecore Back Office/Administration URL - %s", SITECORE_ADMIN_URL),
			SitecoreBaseTest.class);
		driver.get(SITECORE_ADMIN_URL);
	}

	/**
	 * log in as admin user
	 *
	 * @return sitecore admin homepage instance
	 */
	protected SitecoreAdminHomePage logInAsAdminUser( )
	{

		VertexLogger.log("Signing in to Sitecore Administration/BackOffcie...", SitecoreBaseTest.class);
		// open Sitecore administration login page
		this.launchAdminLoginPage();

		SitecoreAdminHomePage homePage = new SitecoreAdminHomePage(driver);
		// logout from admin site if any user is already logged in
		homePage.logoutFromAdminIfAlreadyLoggedIn();

		// login as Sitecore administration user
		this.logInAsUser(SITECORE_ADMIN_USERNAME, SITECORE_ADMIN_PASSWORD);

		return homePage;
	}

	/***
	 * This is a common re-usable method for both Sitecore Admin and Storefront
	 * applications login
	 *
	 * @param username to logIn as
	 * @param password to login as
	 *
	 */
	protected void logInAsUser( final String username, final String password )
	{

		SitecoreLogInPage login = new SitecoreLogInPage(driver);

		login.enterUsername(username);
		login.enterPassword(password);
		login.clickLoginButton();
	}

	/***
	 * This is a common re-usable method for both Sitecore Admin and Storefront
	 * applications login
	 *
	 * @param username to logIn as
	 * @param password to login as
	 *
	 */
	protected void logInAsStoreUser( final String username, final String password )
	{

		SitecoreLogInPage login = new SitecoreLogInPage(driver);

		login.enterUsername(username);
		login.enterPassword(password);
		login.clickLoginButtonStorefront();
	}
}
