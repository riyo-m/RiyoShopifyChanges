package com.vertex.quality.connectors.oseriesEdge.pages;

import com.vertex.quality.connectors.oseriesEdge.tests.base.OseriesEdgeBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 * Common functions for anything related to Oseries Edge Settings Page.
 *
 * @author Laxmi Lama-Palladino
 */
public class OseriesEdgeSettingsPage extends OseriesEdgeBasePage{

    protected By NOTIFICATION_ENABLE_BUTTON = By.xpath("//input[@value='1']");
    protected By NOTIFICATION_DISABLE_BUTTON = By.xpath("//input[@value='0']");
    protected By ENTER_EMAIL_ADDRESS = By.xpath("(//input[@type='text'])[1]");
    protected By BUILD_NOTIFICATION = By.xpath("(//input[@name='radioGroup'])[1]");
    protected By PUSH_NOTIFICATION = By.xpath("(//input[@name='radioGroup'])[4]");
    protected By UPLOAD_NOW_BUTTON = By.xpath("//span[contains(text(),'Upload Now')]");
    protected By DAT_LICENSE_FILE = By.xpath("//input[@type='file']");
    protected By UPLOAD_LICENSE_FILE = By.xpath("//span[contains(text(),'Upload License File')]");
    protected By STORAGE_LOCATION_BUTTON = By.xpath("//span[contains(text(),'Add Storage Location')]");
    protected By STORAGE_POP_BOX = By.id("rcDialogTitle0");
    protected By LOCATION_NAME = By.id("storageName");
    protected By CLICK_STORAGE_NAME = By.xpath("(//span[contains(text(),'Add Storage Location')])[2]");
    protected By DEFAULT_STORAGE = By.xpath("//*[contains(text(),'Make Default')]");


    public String userEmail = "Pete@truffleshop.com";


    public OseriesEdgeSettingsPage(WebDriver driver)
    {
        super(driver);
    }

    /**
     * Verify enable radio button is visible
     */
    public boolean isNotificationsEnabled()
    {
        WebElement notificationElement = wait.waitForElementPresent(NOTIFICATION_ENABLE_BUTTON);
        return notificationElement.isEnabled();
    }
    /**
     * Clicks on enable button is visible
     */
    public void enableNotifications() {
        WebElement notificationElement = wait.waitForElementPresent(NOTIFICATION_ENABLE_BUTTON);
        notificationElement.click();

    }
    /**
     * Verify disable button is visible
     */
    public boolean verifyNotificationDisabled()
    {
        WebElement verifyNotificationDisabled = wait.waitForElementPresent(NOTIFICATION_DISABLE_BUTTON);
        return verifyNotificationDisabled.isDisplayed();
    }
    /**
     * Click on disable button
     */
    public void notificationDisabled()
    {
        wait.waitForElementPresent(NOTIFICATION_DISABLE_BUTTON);
        click.moveToElementAndClick(NOTIFICATION_DISABLE_BUTTON);
    }
    /**
     * Verify a user can click on email address
     * @return
     */
    public String enterEmailAddress()
    {
        WebElement emailAddress = wait.waitForElementDisplayed(ENTER_EMAIL_ADDRESS);
        emailAddress.sendKeys(userEmail);
        return String.valueOf(emailAddress);
    }
    /**
     * Verify build Notification buttons work (all)
     */
    public boolean verifyBuildNotification()
    {
        WebElement build = wait.waitForElementPresent(BUILD_NOTIFICATION);
        return build.isEnabled();
    }
    /**
     * Verify content build notification buttons click
     */
    public void buildNotification()
    {
        WebElement build = wait.waitForElementPresent(BUILD_NOTIFICATION);
        build.click();
    }
    /**
     * click on push notification (all)
     */
    public boolean pushNotification(){
        wait.waitForElementPresent(PUSH_NOTIFICATION);
        click.clickElement(PUSH_NOTIFICATION);
        return true;
    }
    /**
     * Verify Content Upload Now work
     */
    public boolean verifyUploadNowIsVisible()
    {
        wait.waitForElementPresent(UPLOAD_NOW_BUTTON);
        return true;
    }
    /**
     * Verify user can click Upload now
     */
    public void uploadNowButton()
    {
        WebElement uploadNow = wait.waitForElementPresent(UPLOAD_NOW_BUTTON);
        uploadNow.click();
    }
    /**
     * Verify OseriesEdge Add Settings button is visible
     */
    public boolean verifyOseriesUploadLicense( )
    {
        waitForPageLoad();
        return element.isElementDisplayed(UPLOAD_LICENSE_FILE);
    }

    /**
     * Enables user to upload license.dat
     * local filepath and remote filepath is different .
     * @return
     */
    public boolean uploadLicenseFile() {

        wait.waitForElementPresent(UPLOAD_NOW_BUTTON);
        click.clickElementCarefully(UPLOAD_NOW_BUTTON);

        WebElement file = wait.waitForElementPresent(DAT_LICENSE_FILE);
        //file.sendKeys("C:\\Users\\laxmi.lamapalladino\\IdeaProjects\\connector-qa\\ConnectorQuality\\resources\\xmlfiles\\oseriesedge\\license.dat");
        file.sendKeys("C:\\ConnectorDev\\ConnectorQuality\\resources\\xmlfiles\\oseriesEdge\\license.dat");
        /**
         *For remote fle upload
         *"/home/jenkins/agent/workspace/OseriesEdgeSmoke/connector-quality-java/ConnectorQuality/resources/xmlfiles/oseriesedge/license.dat"
         */

        WebElement fileUploadButton = wait.waitForElementEnabled(UPLOAD_LICENSE_FILE);
        fileUploadButton.click();
        return true;
    }
    /**
     * Click Storage location button on settings page
     */
    public boolean clickStorageLocationButton()  {
        WebElement locateButton = wait.waitForElementPresent(STORAGE_LOCATION_BUTTON);
        locateButton.click();

        wait.waitForElementPresent(STORAGE_POP_BOX);
        WebElement enterStorageName = wait.waitForElementPresent(LOCATION_NAME);
        enterStorageName.sendKeys("laxmi");
        wait.waitForElementPresent(CLICK_STORAGE_NAME);
        click.clickElementCarefully(CLICK_STORAGE_NAME);
        return true;
    }
    /**
     * Click on Make Default for storage location
     */
    public boolean clickDefaultStorage(){
        wait.waitForElementDisplayed(DEFAULT_STORAGE);
        click.clickElement(DEFAULT_STORAGE);

        return true;
    }
}
