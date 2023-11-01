package com.vertex.quality.connectors.hybris.tests;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.CreditCard;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.connectors.hybris.enums.admin.HybrisAdminMenuNames;
import com.vertex.quality.connectors.hybris.enums.admin.HybrisAdminSubMenuNames;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBOCronJobCodes;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBONavTreeOptions;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisProductIds;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisShipmentMethods;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOCronJobsPage;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOHomePage;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOVertexConfigurationPage;
import com.vertex.quality.connectors.hybris.pages.electronics.HybrisEStoreCheckOutPage;
import com.vertex.quality.connectors.hybris.pages.electronics.HybrisEStoreGuestLoginPage;
import com.vertex.quality.connectors.hybris.pages.electronics.HybrisEStoreOrderConfirmationPage;
import com.vertex.quality.connectors.hybris.pages.electronics.HybrisEStorePage;
import com.vertex.quality.connectors.hybris.tests.base.HybrisBaseTest;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertTrue;

/**
 * Test to validate Hybris Cronjob Status and Hybris Logs
 *
 * @author Nagaraju Gampa
 */
public class HybrisLoggingTests extends HybrisBaseTest
{
	/**
	 * Test To Verify Hybris Logging ON-OFF
	 * Note: Validation of logs is put on hold as these logs are moving to AWS
	 *
	 * @author E004012
	 */
	@Test(groups = { "logging", "smoke" })
	public void hybrisLoggingEnableDisableTest( )
	{
		// =================Data declarations=================================
		final String menuVertex = HybrisBONavTreeOptions.VERTEX.getMenuName();
		final String menuVertexConfiguration = HybrisBONavTreeOptions.VERTEX_CONFIGURATION.getMenuName();
		final String menuSystem = HybrisBONavTreeOptions.SYSTEM.getMenuName();
		final String menuBackgroundProcess = HybrisBONavTreeOptions.BACKGROUND_PROCESSES.getMenuName();
		final String menuCronJobs = HybrisBONavTreeOptions.CRON_JOBS.getMenuName();
		final String vertexCronJobCode = HybrisBOCronJobCodes.VERTEX_CRON_JOB.getCronJobCode();
		final String menuPlatform = HybrisAdminMenuNames.PLATFORM.getMenuName();
		final String subMenuSupport = HybrisAdminSubMenuNames.SUPPORT.getSubMenuName();
		final String powershotProductId = HybrisProductIds.POWERSHOTID.getproductID();
		final String usCountry = Address.Berwyn.country.fullName;
		final String titleMr = CommonDataProperties.TITLE;
		final String fName = CommonDataProperties.FIRST_NAME;
		final String lName = CommonDataProperties.LAST_NAME;
		final String addressLine1 = Address.Berwyn.addressLine1;
		final String city = Address.Berwyn.city;
		final String state = Address.Berwyn.state.fullName;
		final String zip = Address.Berwyn.zip5;
		final String expShippingMethod = HybrisShipmentMethods.STANDARD.name;
		final String cardType = CreditCard.TYPE.text;
		final String cardName = CreditCard.NAME.text;
		final String cardNumber = CreditCard.NUMBER.text;
		final String expMonth = CreditCard.EXPIRY_MONTH.text;
		final String expYear = CreditCard.EXPIRY_YEAR.text;
		final String verificationNumber = CreditCard.CODE.text;

		// =================Script Implementation=================================
		// login as Backoffice user into Hybris-Backoffice Page
		HybrisBOHomePage boHomePage = loginBOUser();

		// navigate to vertex configuration
		HybrisBOVertexConfigurationPage vertexConfigPage = boHomePage.navigateToConfigurationPage(menuVertex,
			menuVertexConfiguration);

		// Disable Messaging log in Vertex Administration Configuration
		vertexConfigPage.selectVertexConfigurationRow(0);
		vertexConfigPage.navigateToAdministrationTab();
		vertexConfigPage.setMessagingLog("False");

		// save Vertex Configuration
		vertexConfigPage.saveVertexConfiguration();

		// navigate to CronJobs Page
		HybrisBOCronJobsPage cronJobsPage = boHomePage.navigateToCronJobsPage(menuSystem, menuBackgroundProcess,
			menuCronJobs);

		// search for Vertex Cronjob code
		cronJobsPage.searchVertexCronJob(vertexCronJobCode);
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

		// Logout from Back office
		boHomePage.logoutFromBackOffice();

		/*
		 * //TODO - Place Holder to Implement Logging Functionality after moving all logs to AWS
		 * //login as Admin user into Hybris-Admin Page
		 * HybrisAdminHomePage adminhomepage = logInAsAdminUser();
		 * //navigate to Support Page
		 * boHomePage.navigateToSupportPage(menuPlatform, subMenuSupport);
		 */

		// launch Electronics store front page
		HybrisEStorePage storeFront = launchB2CPage();

		// Add Product - PowerShot to Cart
		storeFront.searchAndAddProductToCart(powershotProductId);
		HybrisEStoreGuestLoginPage eStoreGuestLoginPage = storeFront.proceedToCheckout();

		// Set Electronics Store Guest Credentials and checkout as Guest
		setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
		HybrisEStoreCheckOutPage checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

		// Fill Shipping Address Details and Proceed to Checkout
		// checkoutPage.fillShippingAddressDeatils(usCountry, "Mr.", firstName, lastName,
		// addressLine1, city, state, zip);
		checkoutPage.fillShippingAddressDetails(usCountry, titleMr, fName, lName, addressLine1, city, state, zip);
		checkoutPage.clickDeliveryAddressNext();

		// Get and Validate selected Shipping Method
		final String actualShippingMethod = checkoutPage.getShippingMethod();
		assertTrue(actualShippingMethod.contains(expShippingMethod));
		checkoutPage.clickDeliveryMethodNext();

		// Fill Payment Details and Proceed to Checkout
		checkoutPage.fillPaymentDetails(cardType, cardName, cardNumber, expMonth, expYear, verificationNumber);
		checkoutPage.enableUseDeliveryAddress();
		checkoutPage.clickpaymentBillingAddressNext();

		// Enable Terms And Conditions and Place Order
		checkoutPage.enableTermsConditions();
		HybrisEStoreOrderConfirmationPage orderConfirmPage = checkoutPage.placeOrder();

		// Get order Number
		String orderNumber = orderConfirmPage.getOrderNumber();
		assertTrue(orderNumber != null, "Order Number not available '/' Order Number is Blank");
		String orderNumberMsg = String.format("Order Number is: %s", orderNumber);
		VertexLogger.log(orderNumberMsg);

		/*
		 * //TODO - Place Holder to Implement Logging Functionality after moving all logs to AWS
		 * //login as Admin user into Hybris-Admin Page
		 * adminhomepage = logInAsAdminUser();
		 * //navigate to Support Page
		 * boHomePage.navigateToSupportPage(menuPlatform, subMenuSupport);
		 */

		// login as Backoffice user into Hybris-Backoffice Page
		boHomePage = loginBOUser();

		// navigate to vertex configuration
		vertexConfigPage = boHomePage.navigateToConfigurationPage(menuVertex, menuVertexConfiguration);

		// Disable Messaging log in Vertex Administration Configuration
		vertexConfigPage.selectVertexConfigurationRow(0);
		vertexConfigPage.navigateToAdministrationTab();
		vertexConfigPage.setMessagingLog("True");

		// save Vertex Configuration
		vertexConfigPage.saveVertexConfiguration();

		// navigate to CronJobs Page
		cronJobsPage = boHomePage.navigateToCronJobsPage(menuSystem, menuBackgroundProcess, menuCronJobs);

		// search for Vertex Cronjob code
		cronJobsPage.searchVertexCronJob(vertexCronJobCode);
		cronJobsPage.selectVertexCronJob();

		// run CronJob and validate Status
		cronJobsPage.runVertexCronJob();
		Map<String, String> cronJobStatusMap1 = cronJobsPage.getCronJobStatus();
		actualCurrentStatus = cronJobStatusMap1.get("CURRENTSTATUS");
		actualLastResultStatus = cronJobStatusMap1.get("LASTRESULTSTATUS");
		lastExecutionTime = cronJobStatusMap1.get("JOBEXECUTIONLASTENDTIME");
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

		// Logout from Back office
		boHomePage.logoutFromBackOffice();

		// launch Electronics store front page
		storeFront = launchB2CPage();

		// Add Product - PowerShot to Cart
		storeFront.searchAndAddProductToCart(powershotProductId);
		eStoreGuestLoginPage = storeFront.proceedToCheckout();

		// Set Electronics Store Guest Credentials and checkout as Guest
		setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
		checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

		// Fill Shipping Address Details and Proceed to Checkout
		checkoutPage.fillShippingAddressDetails(usCountry, titleMr, fName, lName, addressLine1, city, state, zip);
		checkoutPage.clickDeliveryAddressNext();

		// Get and Validate selected Shipping Method
		final String actualShippingMethod1 = checkoutPage.getShippingMethod();
		assertTrue(actualShippingMethod.contains(expShippingMethod));
		checkoutPage.clickDeliveryMethodNext();

		// Fill Payment Details and Proceed to Checkout
		checkoutPage.fillPaymentDetails(cardType, cardName, cardNumber, expMonth, expYear, verificationNumber);
		checkoutPage.enableUseDeliveryAddress();
		checkoutPage.clickpaymentBillingAddressNext();

		// Enable Terms And Conditions and Place Order
		checkoutPage.enableTermsConditions();
		orderConfirmPage = checkoutPage.placeOrder();

		// Get order Number
		orderNumber = orderConfirmPage.getOrderNumber();
		assertTrue(orderNumber != null, "Order Number not available '/' Order Number is Blank");
		orderNumberMsg = String.format("Order Number is: %s", orderNumber);
		VertexLogger.log(orderNumberMsg);

		/*
		 * //TODO - Place Holder to Implement Logging Functionality after moving all logs to AWS
		 * //login as Admin user into Hybris-Admin Page
		 * adminhomepage = logInAsAdminUser();
		 * //navigate to Support Page
		 * boHomePage.navigateToSupportPage(menuPlatform, subMenuSupport);
		 */

	}
}
