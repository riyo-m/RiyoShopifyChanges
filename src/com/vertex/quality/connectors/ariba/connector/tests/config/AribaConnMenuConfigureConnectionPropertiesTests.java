package com.vertex.quality.connectors.ariba.connector.tests.config;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.ariba.connector.enums.AribaConnConnectionPropertiesTextField;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavConfigurationMenuOption;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnBasePage;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnConnectionPropertiesPage;
import com.vertex.quality.connectors.ariba.connector.tests.base.AribaConnBaseTest;
import com.vertex.quality.connectors.ariba.connector.tests.base.AribaConnMenuBaseTest;
import com.vertex.quality.connectors.ariba.connector.tests.base.AribaUtilities;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * tests of interacting with the Configure Connection Properties configuration
 * menu to set up how the connector transfers a tenant's requests to an O Series
 * instance
 *
 * @author ssalisbury
 */

public class AribaConnMenuConfigureConnectionPropertiesTests extends AribaConnMenuBaseTest
{

	/**
	 * tests whether this page's url text field displays to the user, can be clicked on, can have
	 * text entered into it, and stores/returns the same text that was put into it
	 * This URL field configures the instance of O Series that the connector is using for this tenant
	 *
	 * TODO JIRA lacks a story specifically for the main part of the UI of the Configure Connection Properties page
	 * maybe CARIBA-14?
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void urlFieldAccessibleTest( )
	{
		testTextFieldAccessible(AribaConnConnectionPropertiesTextField.CONNECTION_URL);
	}

	/**
	 * tests whether this page's vendor code text field displays to the user, can be clicked on, can have
	 * text entered into it, and stores/returns the same text that was put into it
	 * The Vendor tax code field  TODO elaborate
	 *
	 * TODO JIRA lacks a story specifically for the main part of the UI of the Configure Connection Properties page
	 * maybe CARIBA-14?
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void vendorCodeFieldAccessibleTest( )
	{
		testTextFieldAccessible(AribaConnConnectionPropertiesTextField.VENDOR_TAX_CODE);
	}

	/**
	 * tests whether this page's consumer code text field displays to the user, can be clicked on, can have
	 * text entered into it, and stores/returns the same text that was put into it
	 * The Self-assessed tax code field  TODO elaborate
	 *
	 * TODO JIRA lacks a story specifically for the main part of the UI of the Configure Connection Properties page
	 * maybe CARIBA-14?
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void consumerCodeFieldAccessibleTest( )
	{
		testTextFieldAccessible(AribaConnConnectionPropertiesTextField.CONSUMER_TAX_CODE);
	}

	/**
	 * tests whether the page's XML Logging checkbox can be interacted with
	 *
	 * TODO JIRA lacks a story specifically for the main part of the UI of the Configure Connection Properties page
	 * maybe CARIBA-14?
	 *
	 * @author ssalisbury
	 */
	@Test(groups = {"ariba_ui","ariba_smoke","ariba_regression"})
	public void xmlLoggingCheckboxTest( )
	{
		final boolean defaultXMLLogCheckBoxState = true;

		AribaConnConnectionPropertiesPage menuPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.CONFIGURE_CONNECTION_PROPERTIES);

		//test that it's displayed and enabled
		assertTrue(menuPage.isXMLLogCheckboxAccessible());

		//test that its default state is as expected
		assertEquals(menuPage.isXMLLogCheckboxChecked(), defaultXMLLogCheckBoxState);

		//test that its state can be changed
		menuPage.setXMLLogCheckbox(!defaultXMLLogCheckBoxState);
		assertTrue(menuPage.isXMLLogCheckboxChecked() != defaultXMLLogCheckBoxState);

		//test that it's still displayed and enabled
		assertTrue(menuPage.isXMLLogCheckboxAccessible());
	}

	/**
	 * tests whether the page's o-series post checkbox can be interacted with
	 *
	 * CARIBA-526
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void oseriesPostCheckboxTest( )
	{
		final boolean defaultOSeriesPostCheckBoxState = false;

		AribaConnConnectionPropertiesPage menuPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.CONFIGURE_CONNECTION_PROPERTIES);
		menuPage.tenantSelector.selectTenant("NewTenant");
		//test that it's displayed and enabled
		assertTrue(menuPage.isOSeriesPostCheckboxAccessible());

		//test that its default state is as expected
		assertEquals(menuPage.isOSeriesPostCheckboxChecked(), defaultOSeriesPostCheckBoxState);

		//test that its state can be changed
		menuPage.setOSeriesPostCheckbox(!defaultOSeriesPostCheckBoxState);
		assertTrue(menuPage.isOSeriesPostCheckboxChecked() != defaultOSeriesPostCheckBoxState);

		//test that it's still displayed and enabled
		assertTrue(menuPage.isOSeriesPostCheckboxAccessible());
	}

	/**
	 * tests whether the update button is visible but disabled before
	 * changes have been made to the connection properties
	 *
	 * TODO JIRA lacks a story specifically for the main part of the UI of the Configure Connection Properties page
	 * maybe CARIBA-14?
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void updateButtonInitialAccessTest( )
	{
		AribaConnConnectionPropertiesPage menuPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.CONFIGURE_CONNECTION_PROPERTIES);
		menuPage.tenantSelector.selectTenant("NewTenant");
		//tests that the update button starts out visible but not enabled
		assertTrue(menuPage.isSubmitButtonDisplayed());
		boolean isUpdateButtonNotAccessible = !menuPage.isSubmitButtonEnabled();
		assertTrue(isUpdateButtonNotAccessible);

		/*tests that merely interacting with a connection property's field
		 * (without changing that property's value in the field)
		 * doesn't enable the update button
		 */
		//tests clicking a text field then checking if the update button is still disabled
		AribaConnConnectionPropertiesTextField urlField = AribaConnConnectionPropertiesTextField.CONNECTION_URL;
		menuPage.clickTextField(urlField);
		isUpdateButtonNotAccessible = !menuPage.isSubmitButtonEnabled();
		assertTrue(isUpdateButtonNotAccessible);

		/*tests sending a new line (to emulate pressing enter) or an empty string
		 * then checking if the update button is still disabled
		 */
		AribaConnConnectionPropertiesTextField vendorCodeField = AribaConnConnectionPropertiesTextField.VENDOR_TAX_CODE;
		menuPage.setTextField(vendorCodeField, "\n", false);
		isUpdateButtonNotAccessible = !menuPage.isSubmitButtonEnabled();
		assertTrue(isUpdateButtonNotAccessible);

		AribaConnConnectionPropertiesTextField consumerCodeField
			= AribaConnConnectionPropertiesTextField.CONNECTION_URL;
		menuPage.setTextField(consumerCodeField, "", false);
		isUpdateButtonNotAccessible = !menuPage.isSubmitButtonEnabled();
		assertTrue(isUpdateButtonNotAccessible);
	}

	/**
	 * tests that the update button can be clicked after the connection properties
	 * have been changed, and that after the update button is clicked it becomes
	 * displayed but disabled again
	 *
	 * TODO JIRA lacks a story specifically for the main part of the UI of the Configure Connection Properties page
	 * maybe CARIBA-14?
	 *
	 * @author ssalisbury
	 */
	@Test(groups = {"ariba_ui","ariba_regression" })
	public void updateButtonUseTest( )
	{
		AribaConnConnectionPropertiesPage menuPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.CONFIGURE_CONNECTION_PROPERTIES);

		menuPage.tenantSelector.selectTenant(tempTenantVariantId);

		//tests that the update button starts out visible but not enabled
		assertTrue(menuPage.isSubmitButtonDisplayed());
		boolean isUpdateButtonNotAccessible = !menuPage.isSubmitButtonEnabled();
		assertTrue(isUpdateButtonNotAccessible);

		//makes minor change and verifies that the update button is now enabled as well as visible
		AribaConnConnectionPropertiesTextField urlField = AribaConnConnectionPropertiesTextField.CONNECTION_URL;
		menuPage.setTextField(urlField, "/", false);

		assertTrue(menuPage.isSubmitButtonEnabled());

		//clicks the update button to submit the change, then verifies that the update button has gone back to being displayed but not enabled
		menuPage.submitChanges();
		assertTrue(menuPage.isSubmitButtonDisplayed());
		isUpdateButtonNotAccessible = !menuPage.isSubmitButtonEnabled();
		assertTrue(isUpdateButtonNotAccessible);

		final boolean priorXMLLogSetting = menuPage.isXMLLogCheckboxChecked();
		menuPage.setXMLLogCheckbox(!priorXMLLogSetting);

		assertTrue(menuPage.isSubmitButtonEnabled());

		//clicks the update button to submit the change, then verifies that the update button has gone back to being displayed but not enabled
		menuPage.submitChanges();
		assertTrue(menuPage.isSubmitButtonDisplayed());
		isUpdateButtonNotAccessible = !menuPage.isSubmitButtonEnabled();
		assertTrue(isUpdateButtonNotAccessible);
	}

	/**
	 * tests that the update success message starts out not being displayed but
	 * pops up when changes to the Connection Properties are successfully submitted
	 *
	 * TODO JIRA lacks a story specifically for the main part of the UI of the Configure Connection Properties page
	 * maybe CARIBA-14?
	 *
	 * @author ssalisbury
	 */
	@Test(groups = { "ariba_ui","ariba_regression"})
	public void updateSuccessMessageTest( )
	{
		final String testConsumerCode = "A32";
		final String testVendorCode = "Z89";

		AribaConnConnectionPropertiesPage menuPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.CONFIGURE_CONNECTION_PROPERTIES);

		menuPage.tenantSelector.selectTenant(tempTenantVariantId);

		//test that success message starts out not displayed
		boolean isSuccessMessageNotDisplayed = !menuPage.isSuccessMessageDisplayed();
		assertTrue(isSuccessMessageNotDisplayed);

		//makes minor change & submits it to make the success message appear
		AribaConnConnectionPropertiesTextField vendorField = AribaConnConnectionPropertiesTextField.VENDOR_TAX_CODE;
		menuPage.setTextField(vendorField, testVendorCode);
		menuPage.submitChanges();

		//verifies that the success message displays with the expected message content
		boolean isSuccessMessageDisplayed = menuPage.isSuccessMessageDisplayed();
		assertTrue(isSuccessMessageDisplayed);

		//makes minor change & submits it to make the success message appear
		AribaConnConnectionPropertiesTextField consumerField = AribaConnConnectionPropertiesTextField.CONSUMER_TAX_CODE;

		menuPage.setTextField(consumerField, testConsumerCode);
		menuPage.submitChanges();

		//verifies that the success message displays with the expected message content
		isSuccessMessageDisplayed = menuPage.isSuccessMessageDisplayed();
		assertTrue(isSuccessMessageDisplayed);
	}

	//TODO SMOKE TEST UPDATE FAILURE MESSAGE- check that failure message is loaded but isn't displayed until an update fails, how make it fail? check that it has appropriate content

	/**
	 * tests that clicking update when the URL field is empty results in
	 * the 'missing connection url' error message & the update button remaining accessible
	 *
	 * TODO JIRA lacks a story specifically for the main part of the UI of the Configure Connection Properties page
	 * maybe CARIBA-14?
	 *
	 * @author ssalisbury
	 */
	@Test(groups ={ "ariba_ui","ariba_regression" } )
	public void emptyFieldErrorMessagesTest( )
	{
		final String urlErrorMessageText = "URL is a required field";
		final String vendorCodeErrorMessageText = "Vendor tax code is required";

		AribaConnConnectionPropertiesTextField urlField = AribaConnConnectionPropertiesTextField.CONNECTION_URL;
		AribaConnConnectionPropertiesTextField vendorField = AribaConnConnectionPropertiesTextField.VENDOR_TAX_CODE;
		AribaConnConnectionPropertiesTextField consumerField = AribaConnConnectionPropertiesTextField.CONSUMER_TAX_CODE;

		final String invalidUrl = urlField.getInvalidText();
		final String invalidVendorCde = vendorField.getInvalidText();
		final String invalidConsumerCode = consumerField.getInvalidText();

		AribaConnConnectionPropertiesPage menuPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.CONFIGURE_CONNECTION_PROPERTIES);

		menuPage.tenantSelector.selectTenant(tempTenantVariantId);

		/*test that trying to submit connection properties settings with
		 * an empty connection URL field produces the missing URL error message
		 */
		menuPage.setTextField(urlField, "");

		menuPage.setTextField(vendorField, invalidVendorCde);
		menuPage.setTextField(consumerField, invalidConsumerCode);

		assertTrue(menuPage.isSubmitButtonEnabled());

		menuPage.submitChanges();

		assertTrue(menuPage.isSubmitButtonEnabled());
		assertTrue(menuPage.isErrorMessageDisplayed(urlErrorMessageText));

		/*test that trying to submit connection properties settings with
		 * an empty vendor tax code field produces the missing vendor error message
		 */
		menuPage.setTextField(urlField, invalidUrl);
		menuPage.setTextField(vendorField, "");
		menuPage.setTextField(consumerField, invalidConsumerCode);

		assertTrue(menuPage.isSubmitButtonEnabled());

		menuPage.submitChanges();

		assertTrue(menuPage.isSubmitButtonEnabled());
		assertTrue(menuPage.isErrorMessageDisplayed(vendorCodeErrorMessageText));
	}

	//TODO ?test the mechanism by which pressing 'ENTER' while a text field, in which you've typed something, is in focus results in the submission being made without you actually clicking the update button (or anything outside of the text field at all)

	//TODO test update- change parameters then try to do something, then check xml logs, separate cases for seeing if logs appear/don't appear based on checkbox, that different O Series instance responds to request based on URL, that tax codes are different for vendor & customer

	/**
	 * tests whether a text field on this page displays to the user, can be clicked on, can have
	 * text entered into it, and stores/returns the same text that was put into it
	 *
	 * @param field which of this page's text fields to test
	 */
	protected void testTextFieldAccessible( final AribaConnConnectionPropertiesTextField field )
	{
		AribaConnConnectionPropertiesPage connectionPropertiesPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.CONFIGURE_CONNECTION_PROPERTIES);
		AribaUtilities utils = new AribaUtilities(driver);
		utils.switchTenant("NewTenant");

		final String fieldDescription = field.toString();
		boolean isFieldClickable = connectionPropertiesPage.isFieldClickable(field);
		if ( !isFieldClickable )
		{
			String inaccessibleFieldMessage = String.format(
				"%s should be displayed&enabled but is not displayed and/or not enabled or may even not be present",
				fieldDescription);
			VertexLogger.log(inaccessibleFieldMessage);
		}
		assertTrue(isFieldClickable);

		String expectedText = field.getInvalidText();
		connectionPropertiesPage.setTextField(field, expectedText);

		//TODO actually save before retrieving & validating
		String actualText = connectionPropertiesPage.retrieveTextFieldContents(field);
		boolean textMatches = validateElementTextValue(fieldDescription, expectedText, actualText);
		assertTrue(textMatches);
	}

	//	/**
	//	 * this handles independently accessing the site to record the states
	//	 * of the configurations handled by this configuration menu
	//	 * so that changes made by test cases in this class can be reversed
	//	 */
	//	@BeforeGroups("mayBreakConnectionProperties")
	//	public void prepareForVolatileTests( )
	//	{
	//		try
	//		{
	//			createDriver();
	//			recordConfigMenuValues();
	//		}
	//		catch ( Exception e )
	//		{
	//			String failedTestPrepMessage = String.format("Failure to prepare for volatile tests due to exception: %s",
	//				e.getMessage());
	//			VertexLogger.log(failedTestPrepMessage, getClass());
	//			e.printStackTrace();
	//		}
	//		finally
	//		{
	//			quitDriver();
	//		}
	//	}
	//
	//	/**
	//	 * this records the states of the configurations handled by this configuration menu
	//	 * so that changes made by test cases in this class can be reversed
	//	 */
	//	protected void recordConfigMenuValues( )
	//	{
	//		AribaUiConnectionPropertiesPage menuPage = loadConfigMenu(
	//			AribaUiNavConfigurationMenuOption.CONFIGURE_CONNECTION_PROPERTIES);
	//
	//		AribaUiTenantSelector tenantSelect = menuPage.tenantSelector;
	//
	//		//validateRecordedTenants(tenantSelect);
	//
	//		AribaUiConnectionPropertiesTextField connectionURLField
	//			= AribaUiConnectionPropertiesTextField.CONNECTION_URL;
	//		AribaUiConnectionPropertiesTextField vendorCodeField
	//			= AribaUiConnectionPropertiesTextField.VENDOR_TAX_CODE;
	//		AribaUiConnectionPropertiesTextField consumerCodeField
	//			= AribaUiConnectionPropertiesTextField.CONSUMER_TAX_CODE;
	//
	//		ConfigConnectionPropertiesTenantBaseSettings[] tenantsSettings
	//			= ConfigConnectionPropertiesTenantBaseSettings.values();
	//		for ( ConfigConnectionPropertiesTenantBaseSettings tenantSettings : tenantsSettings )
	//		{
	//			String recordTenantMessage = String.format("Recording settings for tenant %s",
	//				tenantSettings.tenant.toString());
	//			VertexLogger.log(recordTenantMessage, getClass());
	//
	//			tenantSelect.selectTenant(tenantSettings.tenant);
	//
	//			tenantSettings.connectionURL = menuPage.enumElement.retrieveEnumeratedElementTextContents(
	//				connectionURLField);
	//			tenantSettings.vendorTaxCode = menuPage.enumElement.retrieveEnumeratedElementTextContents(vendorCodeField);
	//			tenantSettings.consumerTaxCode = menuPage.enumElement.retrieveEnumeratedElementTextContents(
	//				consumerCodeField);
	//
	//			tenantSettings.isLoggingChecked = menuPage.isXMLLogCheckboxChecked();
	//		}
	//	}
	//
	//	/**
	//	 * this handles independently accessing the site to reset the configurations
	//	 * handled by this configuration menu back to normal values/settings
	//	 * after any changes made by test cases in this class
	//	 */
	//	@AfterGroups("mayBreakConnectionProperties")
	//	public void cleanUpAfterVolatileTests( )
	//	{
	//		try
	//		{
	//			createDriver();
	//			resetConfigMenuValues();
	//		}
	//		catch ( Exception e )
	//		{
	//			String failedTestPrepMessage = String.format(
	//				"Failure to clean up after volatile tests due to exception: %s", e.getMessage());
	//			VertexLogger.log(failedTestPrepMessage, getClass());
	//			e.printStackTrace();
	//		}
	//		finally
	//		{
	//			quitDriver();
	//		}
	//	}
	//
	//	/**
	//	 * this resets the configurations handled by this configuration menu back to normal
	//	 * values/settings
	//	 * after any changes made by test cases in this class
	//	 */
	//	protected void resetConfigMenuValues( )
	//	{
	//
	//		AribaUiConnectionPropertiesPage menuPage = loadConfigMenu(
	//			AribaUiNavConfigurationMenuOption.CONFIGURE_CONNECTION_PROPERTIES);
	//
	//		AribaUiTenantSelector tenantSelect = menuPage.tenantSelector;
	//		AribaUiConnectionPropertiesTextField connectionURLField
	//			= AribaUiConnectionPropertiesTextField.CONNECTION_URL;
	//		AribaUiConnectionPropertiesTextField vendorCodeField
	//			= AribaUiConnectionPropertiesTextField.VENDOR_TAX_CODE;
	//		AribaUiConnectionPropertiesTextField consumerCodeField
	//			= AribaUiConnectionPropertiesTextField.CONSUMER_TAX_CODE;
	//
	//		ConfigConnectionPropertiesTenantBaseSettings[] tenantsSettings
	//			= ConfigConnectionPropertiesTenantBaseSettings.values();
	//		for ( ConfigConnectionPropertiesTenantBaseSettings tenantSettings : tenantsSettings )
	//		{
	//			String recordTenantMessage = String.format("Resetting settings for tenant %s",
	//				tenantSettings.tenant.toString());
	//			VertexLogger.log(recordTenantMessage, getClass());
	//
	//			tenantSelect.selectTenant(tenantSettings.tenant);
	//
	//			boolean anyChanged = false;
	//
	//			String currConnectionURL = menuPage.enumElement.retrieveEnumeratedElementTextContents(connectionURLField);
	//			if ( !currConnectionURL.equals(tenantSettings.connectionURL) )
	//			{
	//				menuPage.enumElement.enterEnumeratedFieldText(connectionURLField, tenantSettings.connectionURL);
	//				anyChanged = true;
	//			}
	//
	//			String currVendorCode = menuPage.enumElement.retrieveEnumeratedElementTextContents(vendorCodeField);
	//			if ( !currVendorCode.equals(tenantSettings.vendorTaxCode) )
	//			{
	//				menuPage.enumElement.enterEnumeratedFieldText(vendorCodeField, tenantSettings.vendorTaxCode);
	//				anyChanged = true;
	//			}
	//
	//			String currConsumerCode = menuPage.enumElement.retrieveEnumeratedElementTextContents(consumerCodeField);
	//			if ( !currConsumerCode.equals(tenantSettings.consumerTaxCode) )
	//			{
	//				menuPage.enumElement.enterEnumeratedFieldText(consumerCodeField, tenantSettings.consumerTaxCode);
	//				anyChanged = true;
	//			}
	//
	//			boolean currLogChecked = menuPage.isXMLLogCheckboxChecked();
	//			if ( currLogChecked != tenantSettings.isLoggingChecked )
	//			{
	//				menuPage.setXMLLogCheckbox(tenantSettings.isLoggingChecked);
	//				anyChanged = true;
	//			}
	//
	//			if ( anyChanged )
	//			{
	//				menuPage.submitChanges();
	//
	//				assertTrue(menuPage.enumElement.isEnumeratedElementDisplayed(
	//					AribaUiConnectionPropertiesSubmissionMessage.SUCCESS));
	//			}
	//
	//			//checks that all fields now match recorded values
	//			currConnectionURL = menuPage.enumElement.retrieveEnumeratedElementTextContents(connectionURLField);
	//			assertTrue(currConnectionURL.equals(tenantSettings.connectionURL));
	//			currVendorCode = menuPage.enumElement.retrieveEnumeratedElementTextContents(vendorCodeField);
	//			assertTrue(currVendorCode.equals(tenantSettings.vendorTaxCode));
	//			currConsumerCode = menuPage.enumElement.retrieveEnumeratedElementTextContents(consumerCodeField);
	//			assertTrue(currConsumerCode.equals(tenantSettings.consumerTaxCode));
	//			currLogChecked = menuPage.isXMLLogCheckboxChecked();
	//			assertTrue(currLogChecked == tenantSettings.isLoggingChecked);
	//		}
	//	}
	//
	//	/**
	//	 * describes the outcomes of submitting changes on the Configure Connection Properties
	//	 * menu of this configuration site
	//	 * TODO eliminate this! it's a really, really fragile way to safeguard the data in this menu
	//	 * because every time a tenant is added or removed it breaks
	//	 * TODO maybe replace this with an array of pojos, where the pojo class stores all of these
	//	 * values for one tenant (including the tenant's 'value' attribute and the enum entry)
	//	 *
	//	 * @author ssalisbury
	//	 */
	//	public enum ConfigConnectionPropertiesTenantBaseSettings
	//	{
	//
	//		// @formatter:off
//		//					tenant							connection URL											vendor	consumer	is logging checked
//		DEFAULT(		AribaConfigTenantOption.DEFAULT,			"http://oseries8-dev.vertexconnectors.com:8095/vertex-ws/",	"I1",	"U1",	true),
//		VREALM_2174(		AribaConfigTenantOption.VREALM_2174,		"http://oseries8-dev.vertexconnectors.com:8095/vertex-ws/",	"I1",	"",		false),
//		VREALM_2175(	AribaConfigTenantOption.VREALM_2175,		"http://oseries8-dev.vertexconnectors.com:8095/vertex-ws/",	"I1",	"",		true);
//		// @formatter:on
	//
	//		public AribaConfigTenantOption tenant;
	//		public String connectionURL;
	//		public String vendorTaxCode;
	//		public String consumerTaxCode;
	//		public boolean isLoggingChecked;
	//
	//		ConfigConnectionPropertiesTenantBaseSettings( final AribaConfigTenantOption tenant, final String url,
	//			final String vendorCode, final String consumerCode, final boolean loggingChecked )
	//		{
	//
	//			this.tenant = tenant;
	//			this.connectionURL = url;
	//			this.vendorTaxCode = vendorCode;
	//			this.consumerTaxCode = consumerCode;
	//			this.isLoggingChecked = loggingChecked;
	//		}
	//
	//	}
}
