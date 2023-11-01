package com.vertex.quality.connectors.oseriesEdge.tests.base;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oseriesEdge.pages.*;
import org.testng.annotations.BeforeClass;
/**
 * This class represents all the common methods used in the test cases
 *
 * @author Laxmi Lama-Palladino
 */
public class OseriesEdgeBaseTest extends VertexUIBaseTest
{
    public String OSERIES_EDGE_USERNAME = "";
    public String OSERIES_EDGE_PASSWORD = "";

    public String OSERIES_EDGE_URL = "";
    public String OSERIES_EDGE_ADD_TEMPLATE_NAME_URL = "";
    public String OSERIES_TYPE_TEMPLATE_NAME = "";

    @BeforeClass(alwaysRun = true)
    public void initSetup( )
    {
        try
        {
            OSERIES_EDGE_URL = "https://qa-app.vtxdev.net/ui";
            OSERIES_EDGE_USERNAME ="laxmi.lamapalladino@vertexinc.com";
            OSERIES_EDGE_PASSWORD = "Password#1";
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    @BeforeClass(alwaysRun = true)
    public void templateSetup( )
    {
        try
        {
            OSERIES_TYPE_TEMPLATE_NAME = "TrusharTest";

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    /**
     * This navigates to the oseries edge login page
     *
     * @author Brenda Johnson
     */
    protected void NavigateToLoginPage( )
    {
        VertexLogger.log(String.format("Launching OSERIES_EDGE URL - %s", OSERIES_EDGE_URL), VertexLogLevel.DEBUG,
                OseriesEdgeBaseTest.class);
        driver.get(OSERIES_EDGE_URL);
    }

    /**
     * This method logs in to Oseries Edge
     *
     * @author Brenda Johnson
     */
    protected OseriesEdgeLoginPage logInAsOseriesEdgeUser( )
    {
        OseriesEdgeLoginPage logInPage = new OseriesEdgeLoginPage(driver);
        // open oseries edge login page
        NavigateToLoginPage();
        // login as user
        OseriesEdgeLoginPage postLogInPage = logInPage.logInAsEdgeUser(OSERIES_EDGE_USERNAME,
                OSERIES_EDGE_PASSWORD);
        return postLogInPage;
    }

    /**
     * This method navigates to the Oseries Edge page
     *
     * @author Joe Ciccone
     */
    protected void navigateToOseriesEdgePage(){
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
    }
    /**
     *This method clicks on the Template tab
     *
     * @author Laxmi Lama-Palladino
     */
    protected void goToHomepageTemplateTab(){
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToTemplateTab();
    }
    /**
     *This method clicks on the Settings tab
     *
     * @author Laxmi Lama-Palladino
     */
    protected void goToHomepageSettingsTab(){
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToSettingsTab();
    }
    /**
     *This method clicks on the Image tab
     *
     * @author Laxmi Lama-Palladino
     */
    protected void clickImageTab() {
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToSettingsTab();
        homePage.goToOseriesImageTab();

    }
    /**
     *This method clicks on the Image tab
     *
     * @author Laxmi Lama-Palladino
     */
    protected void clickGenerateImage() throws InterruptedException {
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToOseriesImageTab();
        homePage.generateImage();

    }
    /**
     *This method clicks on the action button on the searched template name
     *
     * @author Laxmi Lama-Palladino
     */
    protected void actionButton() throws InterruptedException {
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.generateImage();
        homePage.clickAction();

    }
    /**
     * This method lets user generate container image
     * @author Laxmi Lama.Palladino
     */
    protected void selectTemplateName(){
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
    }
    /**
     * This method uploads the license file
     * @author Laxmi Lama-Palladino
     */
    protected void uploadLicenseFile() {

        OseriesEdgeSettingsPage settingsPage = new OseriesEdgeSettingsPage(driver);
        settingsPage.uploadLicenseFile();
    }

    /**
     *This method clicks on the Instances tab
     * @author Laxmi Lama-Palladino
     */
    protected void goToHomepageInstancesTab(){
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToInstancesTab();
    }
    /**
     *This method clicks on the Template button
     * @author Laxmi Lama-Palladino
     */
    protected void goToTemplateButton(){
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToTemplateTab();
        OseriesEdgeTemplatePage templatePage = new OseriesEdgeTemplatePage(driver);
        templatePage.goToTemplateButton();
    }
    /**
     * enter template name after Add Template button is clicked
     *  @author Laxmi Lama-Palladino
     */
    protected void goToAddTempName()
    {
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToTemplateTab();
        OseriesEdgeTemplatePage templatePage = new OseriesEdgeTemplatePage(driver);
        templatePage.goToTemplateButton();
        OseriesEdgeTemplatePage addedTempName = new OseriesEdgeTemplatePage(driver);
        addedTempName.templateName();
    }
    /**
     * This method types the name of the template in the template box
     * @author Laxmi Lama-Palladino
     */
    protected void typeTemplateName( )
    {
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToTemplateTab();
        OseriesEdgeTemplatePage templatePage = new OseriesEdgeTemplatePage(driver);
        templatePage.goToTemplateButton();
        templatePage.templateName();
    }

    /**
     * This method enables user to click on imposition
     *  @author Laxmi Lama-Palladino
     */
    protected void verifyImposition()
    {
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToTemplateTab();
        OseriesEdgeTemplatePage templatePage = new OseriesEdgeTemplatePage(driver);
        templatePage.goToTemplateButton();
        templatePage.templateName();
        templatePage.imposition();
    }
    /**
     * This method lets user click types of imposition
     * @author Laxmi Lama-Palladino
     */
    protected void impositionTypes()
    {
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToTemplateTab();
        OseriesEdgeTemplatePage templatePage = new OseriesEdgeTemplatePage(driver);
        templatePage.goToTemplateButton();
        templatePage.templateName();
        templatePage.imposition();
        templatePage.impType();
    }
    /**
     * This method lets user click USA JUrisdiction
     * @author Laxmi Lama-Palladino
     */
    protected void jurisdictionClickUSA()
    {
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToTemplateTab();
        OseriesEdgeTemplatePage templatePage = new OseriesEdgeTemplatePage(driver);
        templatePage.goToTemplateButton();
        templatePage.templateName();
        templatePage.imposition();
        templatePage.impType();
        templatePage.jurisdiction();
    }
    /**
     * This method clicks on the latest engine version on the add template page
     * @author Laxmi Lama-Palladino
     */
    protected void clickLatestVersion(){

        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToTemplateTab();
        OseriesEdgeTemplatePage templatePage = new OseriesEdgeTemplatePage(driver);
        templatePage.goToTemplateButton();
        templatePage.templateName();
        templatePage.imposition();
        templatePage.impType();
        templatePage.jurisdiction();
        templatePage.taxLocation();
        templatePage.engineVersion();
    }
    /**
     * This method lets the user click the taxpayer location and enter gis miles
     * @author Laxmi Lama-Palladino
     */
    protected void taxLocation()
    {
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToTemplateTab();
        OseriesEdgeTemplatePage templatePage = new OseriesEdgeTemplatePage(driver);
        templatePage.goToTemplateButton();
        templatePage.templateName();
        templatePage.imposition();
        templatePage.impType();
        templatePage.jurisdiction();
        templatePage.taxLocation();
    }
    /**
     * This method stores the tax engine extract file
     * @author Laxmi Lama-Palladino
     */
    protected void teeStorage() {
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToTemplateTab();
        OseriesEdgeTemplatePage templatePage = new OseriesEdgeTemplatePage(driver);
        templatePage.goToTemplateButton();
        templatePage.templateName();
        templatePage.imposition();
        templatePage.impType();
        templatePage.jurisdiction();
        templatePage.taxLocation();
        templatePage.engineVersion();
        templatePage.storageForTeeFile();
    }
    /**
     * This method lets user upload tee file
     * @author Laxmi Lama-Palladino
     */
    protected void extractTee(){
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToTemplateTab();
        OseriesEdgeTemplatePage templatePage = new OseriesEdgeTemplatePage(driver);
        templatePage.goToTemplateButton();
        templatePage.templateName();
        templatePage.imposition();
        templatePage.impType();
        templatePage.jurisdiction();
        templatePage.taxLocation();
        templatePage.engineVersion();
        templatePage.storageForTeeFile();
        templatePage.teeExtractDropDown();
    }
    /**
     * This method lets user upload tee file
     * @author Laxmi Lama-Palladino
     */
    protected void uploadingTEE() {
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToTemplateTab();
        OseriesEdgeTemplatePage templatePage = new OseriesEdgeTemplatePage(driver);
        templatePage.goToTemplateButton();
        templatePage.templateName();
        templatePage.imposition();
        templatePage.impType();
        templatePage.jurisdiction();
        templatePage.taxLocation();
        templatePage.engineVersion();
        templatePage.storageForTeeFile();
        templatePage.teeExtractDropDown();
        templatePage.uploadTeeFile();

    }
    /**
     * CLick on Manage File
     * @author Laxmi.Lamapalladino
     */
    protected void manageFIleButton(){
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToTemplateTab();
        OseriesEdgeTemplatePage templatePage = new OseriesEdgeTemplatePage(driver);
        templatePage.goToTemplateButton();
        templatePage.templateName();
        templatePage.imposition();
        templatePage.impType();
        templatePage.jurisdiction();
        templatePage.taxLocation();
        templatePage.engineVersion();
        templatePage.storageForTeeFile();
        templatePage.teeExtractDropDown();
        templatePage.uploadTeeFile();
        templatePage.manageFile();
    }
    /**
     * Click enable for journal automation
     * @author Laxmi.Lamapalladino
     */
    protected void enableButtonJournalAutomation(){
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToTemplateTab();
        OseriesEdgeTemplatePage templatePage = new OseriesEdgeTemplatePage(driver);
        templatePage.goToTemplateButton();
        templatePage.templateName();
        templatePage.imposition();
        templatePage.impType();
        templatePage.jurisdiction();
        templatePage.taxLocation();
        templatePage.engineVersion();
        templatePage.storageForTeeFile();
        templatePage.teeExtractDropDown();
        templatePage.uploadTeeFile();
        templatePage.journalAutomationEnable();
    }
    /**
     * This method selects Docker for repository type
     * @author Laxmi.Lamapalladino
     */
    protected void repositoryType()
    {
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToTemplateTab();
        OseriesEdgeTemplatePage templatePage = new OseriesEdgeTemplatePage(driver);
        templatePage.goToTemplateButton();
        templatePage.templateName();
        templatePage.imposition();
        templatePage.impType();
        templatePage.jurisdiction();
        templatePage.taxLocation();
        templatePage.engineVersion();
        templatePage.storageForTeeFile();
        templatePage.uploadTeeFile();
        templatePage.teeExtractDropDown();
        templatePage.journalAutomationEnable();
        templatePage.selectRepository();
    }

    /**
     * This method fills up users docker credential when docker is saved as repository type
     * @author Laxmi LamaPalladino
     */
    protected void infoForDocker(){
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToTemplateTab();
        OseriesEdgeTemplatePage templatePage = new OseriesEdgeTemplatePage(driver);
        templatePage.goToTemplateButton();
        templatePage.templateName();
        templatePage.imposition();
        templatePage.impType();
        templatePage.jurisdiction();
        templatePage.taxLocation();
        templatePage.engineVersion();
        templatePage.storageForTeeFile();
        templatePage.teeExtractDropDown();
        templatePage.uploadTeeFile();
        templatePage.journalAutomationEnable();
        templatePage.selectRepository();
        templatePage.dockerInformation();
    }
    /**
     * This method clicks on yes for auto repository push
     * @author Laxmi Lama-Palladino
     */
    protected void clickYesAutoRepository(){
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToTemplateTab();
        OseriesEdgeTemplatePage templatePage = new OseriesEdgeTemplatePage(driver);
        templatePage.goToTemplateButton();
        templatePage.templateName();
        templatePage.imposition();
        templatePage.impType();
        templatePage.jurisdiction();
        templatePage.taxLocation();
        templatePage.engineVersion();
        templatePage.storageForTeeFile();
        templatePage.teeExtractDropDown();
        templatePage.uploadTeeFile();
        templatePage.journalAutomationEnable();
        templatePage.selectRepository();
        templatePage.dockerInformation();
        templatePage.autoPushRepository();
    }
    /**
     * This method clicks on the save template button
     * @author Laxmi Lama-Palladino
     */
    protected void saveTemplate(){
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToTemplateTab();
        OseriesEdgeTemplatePage templatePage = new OseriesEdgeTemplatePage(driver);
        templatePage.goToTemplateButton();
        templatePage.templateName();
        templatePage.imposition();
        templatePage.impType();
        templatePage.jurisdiction();
        templatePage.taxLocation();
        templatePage.engineVersion();
        templatePage.storageForTeeFile();
        templatePage.teeExtractDropDown();
        templatePage.uploadTeeFile();
        templatePage.journalAutomationEnable();
        templatePage.selectRepository();
        templatePage.dockerInformation();
        templatePage.autoPushRepository();
        templatePage.saveTemplateButton();

    }
    /**
     * Verify a user can click no for returning telemetry data
     * @author Laxmi.Lamapalladino
     */
    protected void telemetry(){
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToTemplateTab();
        OseriesEdgeTemplatePage templatePage = new OseriesEdgeTemplatePage(driver);
        templatePage.goToTemplateButton();
        templatePage.templateName();
        templatePage.imposition();
        templatePage.impType();
        templatePage.jurisdiction();
        templatePage.taxLocation();
        templatePage.engineVersion();
        templatePage.storageForTeeFile();
        templatePage.teeExtractDropDown();
        templatePage.uploadTeeFile();
        templatePage.journalAutomationEnable();
        templatePage.selectRepository();
        templatePage.dockerInformation();
        templatePage.autoPushRepository();
        templatePage.telemetryData();
        templatePage.saveTemplateButton();
    }

    /**
     * Verify user can find a template name on the search template name box on settings page
     *  @author Laxmi Lama-Palladino
     */
    protected void searchTemplateName( )
    {
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToTemplateTab();
        OseriesEdgeTemplatePage addedTemplateNamePage = new OseriesEdgeTemplatePage(driver);
        addedTemplateNamePage.typeTemplateNameBox();
        OseriesEdgeTemplatePage searchingTemplate = new OseriesEdgeTemplatePage(driver);
        searchingTemplate.templateFound();

    }
    /**
     * This method enables the user to click on the notification enable on settings page
     * @author Laxmi Lama-Palladino
     */
    protected void enableNotifications() {
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToSettingsTab();
        OseriesEdgeSettingsPage notificationButton = new OseriesEdgeSettingsPage(driver);
        notificationButton.enableNotifications();
    }
    /**
     * This method enables the user to click on the notification disable on settings page
     *
     * @author Laxmi Lama-Palladino
     */
    protected void goToDisableNotification()
    {
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToSettingsTab();
        OseriesEdgeSettingsPage notificationButton = new OseriesEdgeSettingsPage(driver);
        notificationButton.notificationDisabled();

    }
    /**
     * This method enables the user to enter email address on Settings Page
     *
     * @author Laxmi Lama-Palladino
     */
    protected void getEmailAdd()
    {
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToSettingsTab();
        OseriesEdgeSettingsPage getEmailAdd = new OseriesEdgeSettingsPage(driver);
        getEmailAdd.enterEmailAddress();

    }
    /**
     * This method enables the user to click on the radio button on content manager notification on settings page.
     *
     * @author Laxmi Lama-Palladino
     */
    protected void checkBuildNotification()
    {
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToSettingsTab();
        OseriesEdgeSettingsPage getEmailAdd = new OseriesEdgeSettingsPage(driver);
        getEmailAdd.enterEmailAddress();
        OseriesEdgeSettingsPage contentButton = new OseriesEdgeSettingsPage(driver);
        contentButton.buildNotification();
    }
    /**
     * This method clicks on the Storage Location Button at the settings page
     * @author Laxmi Lama-Palladino
     */
    protected void clickStoreLocationButton()  {
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToSettingsTab();
        OseriesEdgeSettingsPage getEmailAdd = new OseriesEdgeSettingsPage(driver);
        getEmailAdd.enterEmailAddress();
        OseriesEdgeSettingsPage contentButton = new OseriesEdgeSettingsPage(driver);
        contentButton.buildNotification();
        OseriesEdgeSettingsPage locateButton = new OseriesEdgeSettingsPage(driver);
        locateButton.clickStorageLocationButton();
    }
    /**
     * This method clicks on the storage location and makes the storage location default
     * @author Laxmi Lama.Palladino
     */
    protected void makeStorageLocationDefault()  {
        logInAsOseriesEdgeUser();
        OseriesEdgePostLoginPage landingPage = new OseriesEdgePostLoginPage(driver);
        landingPage.goToOSeriesEdgePage();
        OseriesEdgeHomePage homePage = new OseriesEdgeHomePage(driver);
        homePage.goToSettingsTab();
        OseriesEdgeSettingsPage settingsPage = new OseriesEdgeSettingsPage(driver);
        settingsPage.enterEmailAddress();
        settingsPage.buildNotification();
        settingsPage.clickStorageLocationButton();
        settingsPage.clickDefaultStorage();

    }
}

