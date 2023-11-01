package com.vertex.quality.connectors.hybris.tests;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.VertexConfigurations;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.misc.VertexCurrencyUtils;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.connectors.hybris.data.HybrisTestData;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBOBaseStoreTabOptions;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBOCronJobCodes;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBONavTreeOptions;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisProductIds;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOBaseStorePage;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOCronJobsPage;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOHomePage;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOVertexConfigurationPage;
import com.vertex.quality.connectors.hybris.pages.electronics.HybrisEStoreCartPage;
import com.vertex.quality.connectors.hybris.pages.electronics.HybrisEStoreCheckOutPage;
import com.vertex.quality.connectors.hybris.pages.electronics.HybrisEStoreGuestLoginPage;
import com.vertex.quality.connectors.hybris.pages.electronics.HybrisEStorePage;
import com.vertex.quality.connectors.hybris.tests.base.HybrisBaseTest;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Test to validate Hybris Configurations
 *
 * @author Nagaraju Gampa
 */
public class HybrisBasicConfigurationTests extends HybrisBaseTest
{
	/**
	 * Test To Verify Basic Configuration with TrustedID
	 */
	@Test(groups = { "config", "smoke" })
	public void hybrisBasicConfigurationTrustedIDTest( )
	{
		// =================Data declarations=================================
		final String taxGroup = VertexConfigurations.US_TAX_GROUP.gettext();
		final String companyNameOfSeller = VertexConfigurations.COMPANY_NAME_OF_SELLER.gettext();
		final String deliveryCostTaxClass = VertexConfigurations.DELIVERY_COST_TAX_CLASS.gettext();
		final String vertexCronJob = HybrisBOCronJobCodes.VERTEX_CRON_JOB.getCronJobCode();
		final String menuVertex = HybrisBONavTreeOptions.VERTEX.getMenuName();
		final String menuVertexConfiguration = HybrisBONavTreeOptions.VERTEX_CONFIGURATION.getMenuName();
		final String menuBaseCommerce = HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName();
		final String menuBaseStore = HybrisBONavTreeOptions.BASE_STORE.getMenuName();
		final String tabVertexCustomization = HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption();
		final String menuSystem = HybrisBONavTreeOptions.SYSTEM.getMenuName();
		final String menuBackgroundProcess = HybrisBONavTreeOptions.BACKGROUND_PROCESSES.getMenuName();
		final String menuCronJobs = HybrisBONavTreeOptions.CRON_JOBS.getMenuName();

		// =================Script Implementation=================================
		// login as Backoffice user into Hybris-Backoffice Page
		final HybrisBOHomePage boHomePage = loginBOUser();

		// navigate to vertex configuration
		final HybrisBOVertexConfigurationPage vertexConfigPage = boHomePage.navigateToConfigurationPage(menuVertex,
			menuVertexConfiguration);

		// set Vertex Administration Configuration
		vertexConfigPage.selectVertexConfigurationRow(0);
		vertexConfigPage.navigateToAdministrationTab();
		vertexConfigPage.setMessagingLog("True");
		setDefaultVertexAdministrationConfiguration(vertexConfigPage);

		// set Vertex Configuration with TrustedID
		setVertexConfigurationTrustedID(vertexConfigPage);

		// save Vertex Configuration
		vertexConfigPage.saveVertexConfiguration();

		// navigate to BaseStore - Electronic Store Page
		final HybrisBOBaseStorePage electronicsStorePage = boHomePage.navigateToElectronicsStorePage(menuBaseCommerce,
			menuBaseStore);

		// set Electronic Store Default Configuration
		electronicsStorePage.selectElectronicsStore();
		electronicsStorePage.setStorefrontPropertiesNetValue("True");
		electronicsStorePage.setStorefrontExternalTaxCalculation("True");
		electronicsStorePage.selectTaxGroup(taxGroup);
		electronicsStorePage.selectTabFromElectronicsStore(tabVertexCustomization);
		electronicsStorePage.setSellerCompanyName(companyNameOfSeller);
		electronicsStorePage.setDeliveryCostTaxClass(deliveryCostTaxClass);
		electronicsStorePage.setStorefrontEnableTaxInvoicing("True");
		electronicsStorePage.setStorefrontElementProperty("Allow Vertex Cleansed Address", "True");
		electronicsStorePage.setStorefrontElementProperty("Always Accept Cleansed Address", "True");
		electronicsStorePage.saveElectronicsStoreConfiguration();

		// navigate to CronJobs Page
		final HybrisBOCronJobsPage cronJobsPage = boHomePage.navigateToCronJobsPage(menuSystem, menuBackgroundProcess,
			menuCronJobs);

		// search for Vertex Cronjob code
		cronJobsPage.searchVertexCronJob(vertexCronJob);
		cronJobsPage.selectVertexCronJob();

		// run CronJob and validate Status
		cronJobsPage.runVertexCronJob();
		final Map<String, String> cronJobStatusMap = cronJobsPage.getCronJobStatus();
		final String actualCurrentStatus = cronJobStatusMap.get("CURRENTSTATUS");
		final String actualLastResultStatus = cronJobStatusMap.get("LASTRESULTSTATUS");
		final String lastExecutionTime = cronJobStatusMap.get("JOBEXECUTIONLASTENDTIME");
		if ( actualCurrentStatus.equalsIgnoreCase("FINISHED") && actualLastResultStatus.equalsIgnoreCase("SUCCESS") )
		{
			final String cronJobStatusMsg = String.format(
				"Cron Job is Successfully Executed at Last Execution Time - %s . Current Status is: %s and Last Result Status is: %s",
				lastExecutionTime, actualCurrentStatus, actualLastResultStatus);
			VertexLogger.log(cronJobStatusMsg);
		}
		else if ( actualCurrentStatus.equalsIgnoreCase("RUNNING") )
		{
			final String cronJobStatusMsg = String.format(
				"Cron Job is Still Executing. Current Status is: %s and Last Result Status is: %s", actualCurrentStatus,
				actualLastResultStatus);
			VertexLogger.log(cronJobStatusMsg, VertexLogLevel.ERROR);
		}
		else
		{
			final String cronJobStatusMsg = String.format(
				"Cron Job Execution Status is Failed. Current Status is: %s and Last Result Status is: %s",
				actualCurrentStatus, actualLastResultStatus);
			VertexLogger.log(cronJobStatusMsg, VertexLogLevel.ERROR);
		}
	}

	/**
	 * Test To Verify Basic Configuration with UserCredentials(Username & Password)
	 */
	@Test(groups = { "config", "smoke" })
	public void hybrisBasicConfigurationUserCredentialsTest( )
	{
		// =================Data declarations=================================
		final String taxGroup = VertexConfigurations.US_TAX_GROUP.gettext();
		final String sellerCompanyName = VertexConfigurations.COMPANY_NAME_OF_SELLER.gettext();
		final String deliveryCostTaxClass = VertexConfigurations.DELIVERY_COST_TAX_CLASS.gettext();
		final String vertexCronJob = HybrisBOCronJobCodes.VERTEX_CRON_JOB.getCronJobCode();
		final String menuVertex = HybrisBONavTreeOptions.VERTEX.getMenuName();
		final String menuVertexConfiguration = HybrisBONavTreeOptions.VERTEX_CONFIGURATION.getMenuName();
		final String menuBaseCommerce = HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName();
		final String menuBaseStore = HybrisBONavTreeOptions.BASE_STORE.getMenuName();
		final String tabVertexCustomization = HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption();
		final String menuSystem = HybrisBONavTreeOptions.SYSTEM.getMenuName();
		final String menuBackgroundProcess = HybrisBONavTreeOptions.BACKGROUND_PROCESSES.getMenuName();
		final String menuCronJobs = HybrisBONavTreeOptions.CRON_JOBS.getMenuName();

		// =================Script Implementation=================================
		// login as Backoffice user into Hybris-Backoffice Page
		final HybrisBOHomePage boHomePage = loginBOUser();

		// navigate to vertex configuration
		HybrisBOVertexConfigurationPage vertexConfigPage = boHomePage.navigateToConfigurationPage(menuVertex,
			menuVertexConfiguration);

		// set Vertex Administration Configuration
		vertexConfigPage.selectVertexConfigurationRow(0);
		vertexConfigPage.setMessagingLog("True");
		setDefaultVertexAdministrationConfiguration(vertexConfigPage);

		// set Vertex Configuration with UserCredentials
		setVertexConfigurationUserCredentials(vertexConfigPage);

		// save Vertex Configuration
		vertexConfigPage.saveVertexConfiguration();

		// navigate to BaseStore - Electronic Store Page
		final HybrisBOBaseStorePage electronicsStorePage = boHomePage.navigateToElectronicsStorePage(menuBaseCommerce,
			menuBaseStore);

		// set Electronic Store Default Configuration
		electronicsStorePage.selectElectronicsStore();
		electronicsStorePage.setStorefrontPropertiesNetValue("True");
		electronicsStorePage.setStorefrontExternalTaxCalculation("True");
		electronicsStorePage.selectTaxGroup(taxGroup);
		electronicsStorePage.selectTabFromElectronicsStore(tabVertexCustomization);
		electronicsStorePage.setSellerCompanyName(sellerCompanyName);
		electronicsStorePage.setDeliveryCostTaxClass(deliveryCostTaxClass);
		electronicsStorePage.setStorefrontEnableTaxInvoicing("True");
		electronicsStorePage.setStorefrontElementProperty("Allow Vertex Cleansed Address", "True");
		electronicsStorePage.setStorefrontElementProperty("Always Accept Cleansed Address", "True");
		electronicsStorePage.saveElectronicsStoreConfiguration();

		// navigate to CronJobs Page
		HybrisBOCronJobsPage cronJobsPage = boHomePage.navigateToCronJobsPage(menuSystem, menuBackgroundProcess,
			menuCronJobs);

		// search for Vertex Cronjob code
		cronJobsPage.searchVertexCronJob(vertexCronJob);
		cronJobsPage.selectVertexCronJob();

		// run CronJob and validate Status
		cronJobsPage.runVertexCronJob();
		final Map<String, String> cronJobStatusMap = cronJobsPage.getCronJobStatus();
		String actualCurrentStatus = cronJobStatusMap.get("CURRENTSTATUS");
		String actualLastResultStatus = cronJobStatusMap.get("LASTRESULTSTATUS");
		String lastExecutionTime = cronJobStatusMap.get("JOBEXECUTIONLASTENDTIME");
		if ( actualCurrentStatus.equalsIgnoreCase("FINISHED") && actualLastResultStatus.equalsIgnoreCase("SUCCESS") )
		{
			final String cronJobStatusMsg = String.format(
				"Cron Job is Successfully Executed at Last Execution Time - %s . Current Status is: %s and Last Result Status is: %s",
				lastExecutionTime, actualCurrentStatus, actualLastResultStatus);
			VertexLogger.log(cronJobStatusMsg);
		}
		else if ( actualCurrentStatus.equalsIgnoreCase("RUNNING") )
		{
			final String cronJobStatusMsg = String.format(
				"Cron Job is Still Executing. Current Status is: %s and Last Result Status is: %s", actualCurrentStatus,
				actualLastResultStatus);
			VertexLogger.log(cronJobStatusMsg, VertexLogLevel.ERROR);
		}
		else
		{
			final String cronJobStatusMsg = String.format(
				"Cron Job Execution Status is Failed. Current Status is: %s and Last Result Status is: %s",
				actualCurrentStatus, actualLastResultStatus);
			VertexLogger.log(cronJobStatusMsg, VertexLogLevel.ERROR);
		}

		// navigate to vertex configuration
		vertexConfigPage = boHomePage.navigateToConfigurationPage(menuVertex, menuVertexConfiguration);

		// set Vertex Administration Configuration
		vertexConfigPage.selectVertexConfigurationRow(0);

		vertexConfigPage.navigateToAdministrationTab();
		vertexConfigPage.setMessagingLog("True");

		// set Vertex Configuration with TrustedID
		setVertexConfigurationTrustedID(vertexConfigPage);

		// save Vertex Configuration
		vertexConfigPage.saveVertexConfiguration();

		// navigate to CronJobs Page
		cronJobsPage = boHomePage.navigateToCronJobsPage(menuSystem, menuBackgroundProcess, menuCronJobs);

		// search for Vertex Cronjob code
		cronJobsPage.searchVertexCronJob(vertexCronJob);
		cronJobsPage.selectVertexCronJob();

		// run CronJob and validate Status
		cronJobsPage.runVertexCronJob();
		final Map<String, String> cronJobStatusMap1 = cronJobsPage.getCronJobStatus();
		actualCurrentStatus = cronJobStatusMap1.get("CURRENTSTATUS");
		actualLastResultStatus = cronJobStatusMap1.get("LASTRESULTSTATUS");
		lastExecutionTime = cronJobStatusMap.get("JOBEXECUTIONLASTENDTIME");
		if ( actualCurrentStatus.equalsIgnoreCase("FINISHED") && actualLastResultStatus.equalsIgnoreCase("SUCCESS") )
		{
			final String cronJobStatusMsg = String.format(
				"Cron Job is Successfully Executed at Last Execution Time - %s . Current Status is: %s and Last Result Status is: %s",
				lastExecutionTime, actualCurrentStatus, actualLastResultStatus);
			VertexLogger.log(cronJobStatusMsg);
		}
		else if ( actualCurrentStatus.equalsIgnoreCase("RUNNING") )
		{
			final String cronJobStatusMsg = String.format(
				"Cron Job is Still Executing. Current Status is: %s and Last Result Status is: %s", actualCurrentStatus,
				actualLastResultStatus);
			VertexLogger.log(cronJobStatusMsg, VertexLogLevel.ERROR);
		}
		else
		{
			final String cronJobStatusMsg = String.format(
				"Cron Job Execution Status is Failed. Current Status is: %s and Last Result Status is: %s",
				actualCurrentStatus, actualLastResultStatus);
			VertexLogger.log(cronJobStatusMsg, VertexLogLevel.ERROR);
		}
	}

	/**
	 * Test To verify behavior by Enabling and Disabling connector(External Tax Calculation)
	 */
	@Test(groups = { "config", "smoke" })
	public void hybrisEnableOrDisableConnectorTest( )
	{
		// =================Data declarations=================================
		final String menuBaseCommerce = HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName();
		final String menuBaseStore = HybrisBONavTreeOptions.BASE_STORE.getMenuName();
		final String menuVertex = HybrisBONavTreeOptions.VERTEX.getMenuName();
		final String menuVertexConfiguration = HybrisBONavTreeOptions.VERTEX_CONFIGURATION.getMenuName();
		final String tabVertexCustomization = HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption();
		final String powershotProductId = HybrisProductIds.POWERSHOTID.getproductID();
		final String usCountry = Address.Berwyn.country.fullName;
		final String titleMr = CommonDataProperties.TITLE;
		final String fName = CommonDataProperties.FIRST_NAME;
		final String lName = CommonDataProperties.LAST_NAME;
		final String addressLine1 = Address.Berwyn.addressLine1;
		final String city = Address.Berwyn.city;
		final String state = Address.Berwyn.state.fullName;
		final String zip = Address.Berwyn.zip5;
		final float expectedSubTotalAmount = HybrisTestData.SUBTOTAL_AMOUNT;
		final float expectedDeliveryAmount = HybrisTestData.DELIVERY_AMOUNT;
		final float expectedTaxAmount = HybrisTestData.TAX_AMOUNT;
		float expectedOrderTotalExcludeTax = expectedSubTotalAmount + expectedDeliveryAmount;
		expectedOrderTotalExcludeTax = VertexCurrencyUtils.getDecimalFormatAmount(expectedOrderTotalExcludeTax);
		float expectedOrderTotalIncludeTax = expectedSubTotalAmount + expectedDeliveryAmount + expectedTaxAmount;
		expectedOrderTotalIncludeTax = VertexCurrencyUtils.getDecimalFormatAmount(expectedOrderTotalIncludeTax);

		// =================Script Implementation=================================

		// login as Backoffice user into Hybris-Backoffice Page
		HybrisBOHomePage boHomePage = loginBOUser();

		// Navigate to Electronics Store and Disable External Tax Calculation
		final HybrisBOBaseStorePage electronicsStorePage = boHomePage.navigateToElectronicsStorePage(menuBaseCommerce,
			menuBaseStore);
		electronicsStorePage.selectElectronicsStore();
		electronicsStorePage.setStorefrontExternalTaxCalculation("False");
		electronicsStorePage.selectTabFromElectronicsStore(tabVertexCustomization);
		electronicsStorePage.setStorefrontEnableTaxInvoicing("True");
		electronicsStorePage.setStorefrontElementProperty("Allow Vertex Cleansed Address", "True");
		electronicsStorePage.setStorefrontElementProperty("Always Accept Cleansed Address", "True");
		electronicsStorePage.saveElectronicsStoreConfiguration();

		// navigate to vertex configuration
		final HybrisBOVertexConfigurationPage vertexConfigPage = boHomePage.navigateToConfigurationPage(menuVertex,
			menuVertexConfiguration);

		// set Vertex Administration Configuration
		vertexConfigPage.selectVertexConfigurationRow(0);
		vertexConfigPage.navigateToAdministrationTab();
		vertexConfigPage.setMessagingLog("True");

		// set Vertex Configuration with TrustedID & Save Vertex Configuration
		setVertexConfigurationTrustedID(vertexConfigPage);
		vertexConfigPage.saveVertexConfiguration();

		// Logout from Back office
		boHomePage.logoutFromBackOffice();

		// launch Electronics store front page
		HybrisEStorePage storeFront = launchB2CPage();

		// Add Product - PowerShot to Cart
		storeFront.searchAndAddProductToCart(powershotProductId);
		HybrisEStoreGuestLoginPage eStoreGuestLoginPage = storeFront.proceedToCheckout();

		// Set Electronics Store Guest Credentials and checkout as Guest
		setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
		HybrisEStoreCheckOutPage checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

		// Fill Shipping Address Details
		checkoutPage.fillShippingAddressDetails(usCountry, titleMr, fName, lName, addressLine1, city, state, zip);
		checkoutPage.clickDeliveryAddressNext();

		// Validate Order Total Details on Order Summary Page (Without Tax)
		float actualSubTotalAmount = checkoutPage.getSubtotal();
		assertTrue(actualSubTotalAmount == expectedSubTotalAmount,
			"Actual SubTotal Amount is not matching with Expected SubTotal Amount");
		float actualDeliveryAmount = checkoutPage.getDeliveryAmount();
		assertTrue(actualDeliveryAmount == expectedDeliveryAmount,
			"Actual Delivery Amount is not matching with Expected Delivery Amount");

		final float actualOrderTotalExcludeTax = checkoutPage.getOrderTotalAmount();
		assertTrue(actualOrderTotalExcludeTax == expectedOrderTotalExcludeTax,
			"ActualOrderTotalExcludeTax is not matching with ExpectedOrderTotalExcludeTax");

		boolean taxDisplayed = checkoutPage.isTaxDisplayed();
		assertFalse(taxDisplayed, "Tax should not be displayed here but Tax displayed");

		// login as Backoffice user into Hybris-Backoffice Page
		boHomePage = loginBOUser();

		// Navigate to Electronics Store and Enable External Tax Calculation
		boHomePage.navigateToElectronicsStorePage(menuBaseCommerce, menuBaseStore);
		electronicsStorePage.selectElectronicsStore();
		electronicsStorePage.setStorefrontExternalTaxCalculation("True");
		electronicsStorePage.selectTabFromElectronicsStore(tabVertexCustomization);
		electronicsStorePage.setStorefrontEnableTaxInvoicing("True");
		electronicsStorePage.setStorefrontElementProperty("Allow Vertex Cleansed Address", "True");
		electronicsStorePage.setStorefrontElementProperty("Always Accept Cleansed Address", "True");
		electronicsStorePage.saveElectronicsStoreConfiguration();

		// Navigate to Vertex Configuration and Set Configurations
		boHomePage.navigateToConfigurationPage(menuVertex, menuVertexConfiguration);
		vertexConfigPage.selectVertexConfigurationRow(0);
		vertexConfigPage.setMessagingLog("True");

		// set Vertex Configuration with TrustedID
		setVertexConfigurationTrustedID(vertexConfigPage);

		// save Vertex Configuration
		vertexConfigPage.saveVertexConfiguration();

		// Logout from Back office
		boHomePage.logoutFromBackOffice();

		// launch Electronics store front page
		storeFront = launchB2CPage();

		//Get Cart Quantity and Remove All Items from Cart
		int cartQuantity = storeFront.getCartQuantity();
		VertexLogger.log(String.format("Cart Quantity is: %s", cartQuantity));
		if ( cartQuantity > 0 )
		{
			HybrisEStoreCartPage cartPage = storeFront.navigateToCart();
			cartPage.removeItemsFromCart();
		}

		// Add Product - PowerShot to Cart
		storeFront.searchAndAddProductToCart(powershotProductId);
		eStoreGuestLoginPage = storeFront.proceedToCheckout();

		// Set Electronics Store Guest Credentials and checkout as Guest
		setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
		checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

		// Fill Shipping Address Details and Proceed to Checkout
		checkoutPage.fillShippingAddressDetails(usCountry, titleMr, fName, lName, addressLine1, city, state, zip);
		checkoutPage.clickDeliveryAddressNext();

		// Validate Order Total Details on Order Summary Page (With Tax)
		actualSubTotalAmount = checkoutPage.getSubtotal();
		assertTrue(actualSubTotalAmount == expectedSubTotalAmount,
			"ActualSubTotalAmount is not matching with ExpectedSubTotalAmount");
		actualDeliveryAmount = checkoutPage.getDeliveryAmount();
		assertTrue(actualDeliveryAmount == expectedDeliveryAmount,
			"ActualDeliveryAmount is not matching with ExpectedDeliveryAmount");
		taxDisplayed = checkoutPage.isTaxDisplayed();
		assertTrue(taxDisplayed, "Tax should be displayed here but Tax NOT displayed");

		final float actualTaxAmount = checkoutPage.getTaxAmount();
		assertTrue(actualTaxAmount == expectedTaxAmount, "ActualTaxAmount is not matching with ExpectedTaxAmount");

		final float actualOrderTotalIncludeTax = checkoutPage.getOrderTotalAmount();
		final String msg = String.format("expectedOrderTotalIncludeTax is: %s, actualOrderTotalIncludeTax: %s",
			expectedOrderTotalIncludeTax, actualOrderTotalIncludeTax);
		VertexLogger.log(msg);
		assertTrue(actualOrderTotalIncludeTax == expectedOrderTotalIncludeTax,
			"ActualOrderTotalIncludeTax is not matching with ExpectedOrderTotalIncludeTax");
	}
}
