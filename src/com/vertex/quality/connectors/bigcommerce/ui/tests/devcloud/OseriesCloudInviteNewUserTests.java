package com.vertex.quality.connectors.bigcommerce.ui.tests.devcloud;

import com.vertex.quality.connectors.bigcommerce.ui.enums.BigCommerceAdminData;
import com.vertex.quality.connectors.bigcommerce.ui.pages.devcloud.*;
import org.testng.annotations.Test;

/**
 * this class represents the invite new user link and configuration of new company on vertex dev cloud.
 *
 * @author rohit.mogane
 */
public class OseriesCloudInviteNewUserTests extends OseriesCloudLoginTests
{

	/**
	 * CBC-279
	 * this method login to dev cloud and send the invite link to new user.
	 */
	@Test
	public void sendInviteToUserTest( )
	{
		invitePage = new OseriesCloudInviteUserPage(driver);
		newUserPage = new OseriesCloudInviteNewUserPage(driver);
		registerPage = new OseriesCloudCompanyRegisterPage(driver);
		loginToDevCloudTest(true, driver, OSERIES_CLOUD_DEV_ADMIN_USERNAME, OSERIES_CLOUD_DEV_ADMIN_PASSWORD);
		if ( companyPage.getServerUnavailableText() )
		{
			invitePage.clickInviteUserLink();
			invitePage.clickInviteUserButton();
			newUserPage.selectRole(COMPANY_DATA_TEST_ROLE);
			newUserPage.selectAccountType(COMPANY_DATA_TEST_ACCOUNT_TYPE);
			newUserPage.selectFranchiseName(COMPANY_DATA_TEST_FRANCHISE_DEV);
			newUserPage.enterOraclePartyNumber(COMPANY_DATA_TEST_PARTY_NUMBER);
			newUserPage.enterClientName(COMPANY_DATA_TEST_CLIENT_NAME);
			newUserPage.enterEmailAddress(COMPANY_DATA_TEST_EMAIL_ID);
			newUserPage.selectCountry(COMPANY_DATA_TEST_COUNTRY);
			newUserPage.enterStreetAddress(COMPANY_DATA_TEST_ADDRESS1);
			newUserPage.enterStreetAddress2(COMPANY_DATA_TEST_ADDRESS2);
			newUserPage.enterCity(COMPANY_DATA_TEST_CITY);
			newUserPage.selectState(COMPANY_DATA_TEST_STATE);
			newUserPage.enterZipCode(COMPANY_DATA_TEST_ZIPCODE);
			newUserPage.selectSubscription(COMPANY_DATA_TEST_SUBSCRIPTION);
			newUserPage.selectOption(COMPANY_DATA_TEST_OPTION);
			newUserPage.selectReservePodCheckBox();
			newUserPage.selectReservePod(COMPANY_DATA_TEST_POD_DEV);
			newUserPage.clickSendInviteButton();
			newUserPage.clickNewInviteLink();
			registerPage.enterFirstName(COMPANY_DATA_TEST_COMPANY_FIRST_NAME);
			registerPage.enterLastName(COMPANY_DATA_TEST_COMPANY_LAST_NAME);
			registerPage.enterPhone(COMPANY_DATA_TEST_PHONE_NUMBER);
			registerPage.enterCompanyPassword(COMPANY_DATA_TEST_COMPANY_PASSWORD);
			registerPage.enterCompanyConfirmPassword(COMPANY_DATA_TEST_COMPANY_CONFIRM_PASSWORD);
			registerPage.clickOnSaveAndFinishButton();
		}
	}

	/**
	 * CBC-279
	 * this method configure user settings for new company created in dev cloud.
	 */
	@Test(groups = "bigCommerce_create_company")
	public void configureUserSettingTest( )
	{
		companyPage = new OseriesCloudConfigureCompanyPage(driver);
		statePage = new OseriesCloudCompanyRegistrationsPage(driver);
		sendInviteToUserTest();
		loginToDevCloudTest(false, driver, COMPANY_DATA_TEST_EMAIL_ID, COMPANY_DATA_TEST_COMPANY_PASSWORD);
		if ( companyPage.getServerUnavailableText() )
		{
			companyPage.clickConfigureLink();
			companyPage.selectCountryDropDown(COMPANY_DATA_TEST_COUNTRY);
			companyPage.enterCompanyCode(COMPANY_DATA_TEST_COMPANY_CODE);
			companyPage.enterFederalIDNumber(COMPANY_DATA_TEST_COMPANY_FEDERAL_NUMBER);
			companyPage.enterCompanyName(COMPANY_DATA_TEST_COMPANY_NAME);
			companyPage.enterFirstName(COMPANY_DATA_TEST_COMPANY_FIRST_NAME);
			companyPage.enterLastName(COMPANY_DATA_TEST_COMPANY_LAST_NAME);
			companyPage.enterStreetAddress(COMPANY_DATA_TEST_ADDRESS1);
			companyPage.enterCity(COMPANY_DATA_TEST_CITY);
			companyPage.selectState(COMPANY_DATA_TEST_STATE);
			companyPage.enterZipCode(COMPANY_DATA_TEST_ZIPCODE);
			companyPage.clickAddCompanyButton();
			companyPage.clickOnConfirmCompanyAddress();
			statePage.activateStates(BigCommerceAdminData.US_STATES.data);
			statePage.switchCountryTab(BigCommerceAdminData.CA.data);
			statePage.activateStates(BigCommerceAdminData.CA_STATES.data);
			statePage.switchCountryTab(BigCommerceAdminData.IT.data);
			statePage.activateITStates(BigCommerceAdminData.IT_STATES.data);
			statePage.clickReadyToSaveButton();
			statePage.clickSaveConfigurationButton();
			statePage.clickConfirmConfiguration();
			statePage.goLive();
		}
	}
}
