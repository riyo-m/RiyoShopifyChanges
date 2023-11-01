package com.vertex.quality.connectors.oseriesEdge.tests.base;

import com.vertex.quality.connectors.oseriesEdge.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * These tests are set to verify users are able to click and upload license file on settings page
 *
 * @author Laxmi Lama-Palladino
 */
public class OseriesEdgeSettingsTests extends OseriesEdgeBaseTest {
    OseriesEdgeSettingsPage enableNotification, disableNotification, displayEmail, buildRadio, pushRadio, datUploaded,
            locateButton, defaultStorage;

    /**
     * XRAYOSE-11
     * This test returns true if the notification is clicked on enable button if not it clicks enable
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void ensureNotificationIsEnabledTest() {
        enableNotification = new OseriesEdgeSettingsPage(driver);
        enableNotifications();
        if (!enableNotification.isNotificationsEnabled()) {
            enableNotification.enableNotifications();
        }
        assertTrue(enableNotification.isNotificationsEnabled(), "Oseries Edge notification is enabled");
    }

    /**
     * This test will return false if the disable button is not disabled
     * XRAYOSE-12
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void clickDisableButtonTest() {
        disableNotification = new OseriesEdgeSettingsPage(driver);
        goToDisableNotification();
        if (!disableNotification.isNotificationsEnabled()) {
            disableNotification.enableNotifications();
        }
        assertFalse(disableNotification.verifyNotificationDisabled(), "Oseries Edge notification is not disabled");
    }

    /**
     * This test enters the user email account on the settings page
     * XRAYOSE-13
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void checkIfEmailIsDisplayedTest() {
        displayEmail = new OseriesEdgeSettingsPage(driver);
        getEmailAdd();
        assertFalse(Boolean.parseBoolean(displayEmail.enterEmailAddress()), "User can enter email address.");
    }
    /**
     * This test verifies the radio buttons for build notification is enabled (all)
     * XRAYOSE-14
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void verifyBuildNotificationTest() {
        buildRadio = new OseriesEdgeSettingsPage(driver);
        checkBuildNotification();
        assertTrue(buildRadio.verifyBuildNotification(), "Content Manager Radio Buttons are visible.");
    }
    /**
     * This test verifies the radio buttons for push notification is enabled (none)
     * XRAYOSE-15
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void verifyPushNotificationTest(){
        pushRadio = new OseriesEdgeSettingsPage(driver);
        goToHomepageSettingsTab();
        assertTrue(pushRadio.pushNotification(), "Push notification buttons work.");
    }
    /**
     * This test verifies that the user can upload license file.
     * XRAYOSE-16
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void oseriesSettingsLicenseUploadTest( )
    {
        datUploaded = new OseriesEdgeSettingsPage(driver);
        goToHomepageSettingsTab();
        uploadLicenseFile();
        assertTrue(datUploaded.verifyOseriesUploadLicense(), "license.dat file has not been uploaded.");
    }
    /**
     * This test verifies user can click the storage location button
     * XRAYOSE-17
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void settingsLocationButtonTest()  {
        locateButton = new OseriesEdgeSettingsPage(driver);
        clickStoreLocationButton();
        assertTrue(locateButton.clickStorageLocationButton(), "Did not click storage location button.");


    }
    /**
     * This test verifies user can set a storage location as default
     * XRAYOSE-65
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void clickDefaultStorageTest(){
        defaultStorage = new OseriesEdgeSettingsPage(driver);
        makeStorageLocationDefault();
        assertTrue(defaultStorage.clickDefaultStorage(),  "Clicked on default storage button." );
    }
}
