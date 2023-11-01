package com.vertex.quality.connectors.ariba.connector.tests.config;

import com.vertex.quality.connectors.ariba.connector.enums.AribaConnAribaFieldType;
import com.vertex.quality.connectors.ariba.connector.enums.AribaConnConnectionPropertiesTextField;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavConfigurationMenuOption;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavOption;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnBasePage;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnAccountingFieldMappingPage;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnConnectionPropertiesPage;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnCustomFieldMappingPage;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnManageTenantPage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnHomePage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnSignOnPage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnSystemStatusPage;
import com.vertex.quality.connectors.ariba.connector.tests.base.AribaConnMenuBaseTest;
import com.vertex.quality.connectors.ariba.connector.tests.base.AribaUtilities;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * tests the configuration menu for adding, modifying, and deleting the tenants
 * whose configurations can be modified in the current account
 *
 * @author ssalisbury
 */

public class AribaConnMenuManageTenantTests extends AribaConnMenuBaseTest
{
	protected final String testTenant2DisplayName = "AUTOtestTenantDisplay";
	protected final String testTenant2VariantId = "AUTOtestTenantVariant";

	protected final int maxTenantNameLength = 64;

	//some menus have submit buttons that only become enabled once you've changed the text content of a field:
	// if you want to try to submit something while leaving one or more text fields empty, you may have to write this
	// to that text field, then clear that field, and then submit the changes
	protected final String tempGibberish = "sdkf";

	/**
	 * JIRA ticket CARIBA-426
	 * checks that the default tenant can't be deleted
	 */
	//@Test(groups = { "ariba_ui","ariba_regression" })
	public void cantDeleteDefaultTenantTest( )
	{
		AribaConnManageTenantPage tenantPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);
		AribaUtilities utils = new AribaUtilities(driver);

		tenantPage.tenantSelector.selectTenant(defaultTenantDisplayName);
		tenantPage.checkDeleteDisabled();

	}

	/**
	 * JIRA ticket CARIBA-426
	 * checks that a newly created tenant without any data can be deleted
	 */
	//@Test(groups = { "ariba_ui","ariba_regression" })
	public void deleteTempTenantTest( )
	{
		AribaConnManageTenantPage tenantPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);

		//wiping the tax types pages is a workaround- currently the new component tax type maintenance page auto-populates with a bunch of rows
		// for a new tenant, and then you can't delete a tenant while it has data stored in other tables
		AribaConnBasePage lastWipedMenuPage = connUtil.wipeTenantDataRows(tempTenantVariantId, tenantPage);

		AribaConnManageTenantPage postDataCleaningTenantPage = connUtil.openConfigMenu(lastWipedMenuPage,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);
		postDataCleaningTenantPage.deleteSelectedTenant();

		boolean isDeletionSuccessful = postDataCleaningTenantPage.isSuccessMessageDisplayed();
		assertTrue(isDeletionSuccessful);
	}

	/**
	 * JIRA ticket CARIBA-426
	 * adds some data to a newly created tenant, deletes all of the tenant's data rows,
	 * and then checks that the new tenant can still be deleted
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void deleteModifiedAndClearedTenant( )
	{
		final AribaConnAribaFieldType defaultMapping1AribaType = AribaConnAribaFieldType.STRING;
		final String defaultMapping1AribaName = "AUTOdoomedMapping1";
		final String defaultMapping1VertexField = "Flex Code Field 8";

		final AribaConnAribaFieldType defaultMapping2AribaType = AribaConnAribaFieldType.DATE;
		final String defaultMapping2AribaName = "AUTOdoomedMapping2";
		final String defaultMapping2VertexField = "Flex Date Field 2";

		AribaConnManageTenantPage tenantPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);
		tenantPage.tenantSelector.selectTenant(tempTenantDisplayName);

		AribaConnBasePage lastWipedMenuPage = connUtil.wipeTenantDataRows(tempTenantVariantId, tenantPage);

		AribaConnCustomFieldMappingPage mappingPage = connUtil.openConfigMenu(lastWipedMenuPage,
			AribaConnNavConfigurationMenuOption.MODIFY_CUSTOM_FIELD_MAPPING);

		mappingPage.addFilledConfigRow(defaultMapping1AribaType, defaultMapping1AribaName, defaultMapping1VertexField);
		mappingPage.addFilledConfigRow(defaultMapping2AribaType, defaultMapping2AribaName, defaultMapping2VertexField);
		mappingPage.deleteAllConfigRows();
		AribaConnManageTenantPage postDataCleaningTenantPage = connUtil.openConfigMenu(mappingPage,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);
		postDataCleaningTenantPage.deleteSelectedTenant();

		boolean isDeletionSuccessful = postDataCleaningTenantPage.isSuccessMessageDisplayed();
		assertTrue(isDeletionSuccessful);
		//workaround- at present, deleting the current tenant leaves you with no tenant selected, and the site
		// currently logs you out if you try to navigate out of one configuration menu and don't have a tenant selected
		postDataCleaningTenantPage.tenantSelector.selectTenant(defaultTenantVariantId);

		AribaConnCustomFieldMappingPage postDeleteMappingPage = connUtil.openConfigMenu(postDataCleaningTenantPage,
			AribaConnNavConfigurationMenuOption.MODIFY_CUSTOM_FIELD_MAPPING);
		boolean isTenantEliminated = !postDeleteMappingPage.tenantSelector.doesTenantExist(tempTenantVariantId);
		assertTrue(isTenantEliminated);
	}

	//TODO for CARIBA-426, make test case that adds data to every table-based config menu then deletes all of it then deletes the new tenant

	/**
	 * JIRA ticket CARIBA-427
	 * checks that a tenant can be created
	 */
	@Test(groups = {"ariba_ui", "ariba_smoke","ariba_regression" })
	public void tenantCreationTest( )
	{
		AribaConnManageTenantPage tenantPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);

		tenantPage.createNewTenant();
		tenantPage.setVariantId(testTenant2VariantId);
		tenantPage.setDisplayName(testTenant2DisplayName);
		tenantPage.submitChanges();

		boolean isTenantCreated = tenantPage.isSuccessMessageDisplayed();
		assertTrue(isTenantCreated);

		eliminateTenant(tenantPage, testTenant2VariantId);
	}

	/**
	 * JIRA ticket CARIBA-427
	 * checks that a user can't enter an illegally long name or id
	 * when creating a new tenant or when editing an existing one
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void tenantNameMaxLengthTest( )
	{
		final String tooLongTenantName
			= "sdf0324n204nl8xe0e.2lal=sdf0324n204nl8xesdf0324n204nl8xesdf0324n0e.2l3al]204nl8xe";
		final String maxLengthTenantName = tooLongTenantName.substring(0, maxTenantNameLength);

		AribaConnManageTenantPage tenantPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);

		//check that length limits hold while creating a tenant
		tenantPage.createNewTenant();
		tenantPage.setVariantId(tooLongTenantName);
		final String actualNewVariantId = tenantPage.getVariantId();
		assertEquals(actualNewVariantId, maxLengthTenantName);

		tenantPage.setDisplayName(tooLongTenantName);
		final String actualNewDisplayName = tenantPage.getDisplayName();
		assertEquals(actualNewDisplayName, maxLengthTenantName);

		//resets the tenant configuration page to get rid of the partially-created tenant
		AribaConnAccountingFieldMappingPage accountingMappingPage = connUtil.openConfigMenu(tenantPage,
			AribaConnNavConfigurationMenuOption.MODIFY_ACCOUNT_FIELD_MAPPING);
		AribaConnManageTenantPage editTenantPage = connUtil.openConfigMenu(accountingMappingPage,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);

		//check that length limits hold while editing an existing tenant
		editTenantPage.tenantSelector.selectTenant(tempTenantDisplayName);
		editTenantPage.setVariantId(tooLongTenantName);
		final String actualEditedVariantId = editTenantPage.getVariantId();
		assertEquals(actualEditedVariantId, maxLengthTenantName);

		editTenantPage.setDisplayName(tooLongTenantName);
		final String actualEditedDisplayName = editTenantPage.getDisplayName();
		assertEquals(actualEditedDisplayName, maxLengthTenantName);
	}

	/**
	 * JIRA ticket CARIBA-427
	 * checks that a new tenant can't be created with only display name or only variant id
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void createTenantOneValueTest( )
	{
		AribaConnManageTenantPage tenantPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);

		//check that you can't make a tenant which only has a display name
		tenantPage.createNewTenant();
		tenantPage.setDisplayName(testTenant2DisplayName);

		//checks that you have to put something into the variant field to even try to submit the tenant
		boolean isCreation1Impossible = !tenantPage.isSubmitButtonEnabled();
		assertTrue(isCreation1Impossible);

		//checks that you can't submit a tenant when you've edited both fields but ultimately left variant id blank
		tenantPage.setVariantId(tempGibberish);
		tenantPage.setVariantId("");

		verifyIncompleteTenantRejected(tenantPage);

		//resets the tenant configuration page to get rid of the partially-created tenant
		AribaConnAccountingFieldMappingPage accountingMappingPage = connUtil.openConfigMenu(tenantPage,
			AribaConnNavConfigurationMenuOption.MODIFY_ACCOUNT_FIELD_MAPPING);
		AribaConnManageTenantPage editTenantPage = connUtil.openConfigMenu(accountingMappingPage,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);

		//check that you can't make a tenant which only has a variant id
		editTenantPage.createNewTenant();
		editTenantPage.setVariantId(testTenant2VariantId);

		//checks that you have to put something into the display name field to even try to submit the tenant
		boolean isCreation2Impossible = !editTenantPage.isSubmitButtonEnabled();
		assertTrue(isCreation2Impossible);

		//checks that you can't submit a tenant when you've edited both fields but ultimately left display name blank
		tenantPage.setDisplayName(tempGibberish);
		tenantPage.setDisplayName("");

		verifyIncompleteTenantRejected(tenantPage);
	}

	/**
	 * JIRA ticket CARIBA-423
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void namelessTenantTest( )
	{
		AribaConnManageTenantPage tenantPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);

		//try to edit the temporary tenant to have an empty variant id and display name
		tenantPage.tenantSelector.selectTenant(tempTenantVariantId);
		tenantPage.setVariantId("");
		tenantPage.setDisplayName("");

		verifyIncompleteTenantRejected(tenantPage);
	}

	/**
	 * JIRA ticket CARIBA-424
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void duplicateTenantTest( )
	{
		final String duplicateTenantAlertText = "Variant ID already exists";

		AribaConnManageTenantPage tenantPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);

		//checks that the default tenant can't be duplicated
		tenantPage.createNewTenant();
		tenantPage.setDisplayName(defaultTenantDisplayName);
		tenantPage.setVariantId(defaultTenantVariantId);

		verifyTenantRejected(tenantPage, duplicateTenantAlertText);

		//resets the tenant configuration page to get rid of the partially-created tenant
		AribaConnAccountingFieldMappingPage accountingMappingPage = connUtil.openConfigMenu(tenantPage,
			AribaConnNavConfigurationMenuOption.MODIFY_ACCOUNT_FIELD_MAPPING);
		//calling this tempTenantPage because this visit to the page is trying to duplicate the temporary tenant
		AribaConnManageTenantPage tempTenantPage = connUtil.openConfigMenu(accountingMappingPage,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);

		//checks that another tenant also can't be duplicated
		tempTenantPage.createNewTenant();
		tempTenantPage.setDisplayName(tempTenantDisplayName);
		tempTenantPage.setVariantId(tempTenantVariantId);

		verifyTenantRejected(tempTenantPage, duplicateTenantAlertText);
	}

	/**
	 * JIRA ticket CARIBA-428
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void renamedTenantNotDuplicatedTest( )
	{
		AribaConnManageTenantPage tenantPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.CREATE_OR_MODIFY_TENANTS);

		tenantPage.tenantSelector.selectTenant(tempTenantVariantId);
		tenantPage.setVariantId(testTenant2VariantId);
		tenantPage.setDisplayName(testTenant2DisplayName);
		tenantPage.submitChanges();

		//checks whether the old version of the temporary tenant persists in the tenant selector dropdown alongside the new version of it
		boolean oldTenantVersionExists = tenantPage.tenantSelector.doesTenantExist(tempTenantVariantId);
		boolean newTenantVersionExists = tenantPage.tenantSelector.doesTenantExist(testTenant2VariantId);
		boolean wasTenantEditedCorrectly = newTenantVersionExists && !oldTenantVersionExists;
		assertTrue(wasTenantEditedCorrectly);

		eliminateTenant(tenantPage, testTenant2VariantId);
	}

	//TEST HELPER METHODS

	/**
	 * Alters {@link #verifyTenantRejected(AribaConnManageTenantPage, String)}
	 * for cases where one or both tenant fields are empty
	 */
	protected void verifyIncompleteTenantRejected( final AribaConnManageTenantPage tenantPage )
	{
		final String partlyBlankTenantAlertText = "Variant ID and display name cannot be blank";

		//verifies that the submission was set up correctly
		final String currDisplayName = tenantPage.getDisplayName();
		final boolean isDisplayNameEmpty = currDisplayName.isEmpty();
		final String currVariantId = tenantPage.getVariantId();
		final boolean isVariantIdEmpty = currVariantId.isEmpty();
		assertTrue(isDisplayNameEmpty || isVariantIdEmpty);

		assertTrue(tenantPage.isSubmitButtonEnabled());

		verifyTenantRejected(tenantPage, partlyBlankTenantAlertText);
	}

	/**
	 * tries to submit the current changes, which should be flawed in a way that will trigger an alert,
	 * and then checks that that submission triggers an alert which contains the expected message
	 *
	 * @param tenantPage        the tenant configuration menu page which the test is being run on
	 * @param expectedAlertText the error message that the alert should display
	 */
	protected void verifyTenantRejected( final AribaConnManageTenantPage tenantPage, final String expectedAlertText )
	{
		tenantPage.submitUnstableChanges();
		String alertText = tenantPage.alert.getAlertText();
		if ( alertText != null )
		{
			tenantPage.alert.acceptAlert();
		}
		assertEquals(expectedAlertText, alertText);
	}
}
