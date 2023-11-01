package com.vertex.quality.connectors.oseriesEdge.tests.base;

import com.vertex.quality.connectors.oseriesEdge.pages.OseriesEdgeHomePage;
import com.vertex.quality.connectors.oseriesEdge.pages.OseriesEdgeLoginPage;
import com.vertex.quality.connectors.oseriesEdge.pages.OseriesEdgePostLoginPage;
import com.vertex.quality.connectors.oseriesEdge.tests.base.OseriesEdgeBaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import com.vertex.quality.connectors.oseriesEdge.pages.*;

import org.testng.annotations.Test;

import static org.testng.Assert.*;
/**
 * This test is set to verify users are able to go to template page and search the template name
 *
 * @author Laxmi Lama-Palladino
 */
public class OseriesTemplateTests extends OseriesEdgeBaseTest {
    OseriesEdgeTemplatePage addTemplate, searchTemplateName, tempName, enterTempName, imposition, clickImp, juris, loc,
    latest, storeTee, extractFile, uploadTee, manageFile, journalButton, repoType, dockerInfo, autopush, disableTelemetry, saveButton;

    /**
     * This test verifies a user is able to click on the Add Template button
     * XRAYOSE-8
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void oseriesAddTemplateButtonTest() {
        addTemplate = new OseriesEdgeTemplatePage(driver);
        goToTemplateButton();
        assertFalse(addTemplate.getOseriesEdgeAddTemplateIsVisible(), "Oseries Edge Add Template Button is not Visible");
    }

    /**
     * XRAYOSE-10
     * This test verifies a user is able to search the template 'TrusharTest'
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void oseriesAddTemplateBoxTest() {
        searchTemplateName = new OseriesEdgeTemplatePage(driver);
        typeTemplateName();

        assertTrue(searchTemplateName.getOseriesEdgeSearchTemplateNameBoxIsVisible(), "Oseries Edge Add Template Button is not Visible");
    }
    /**
     * Add template name after entering Add Template
     * XRAYOSE-18
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void usersTempNameIsEnteredTest()
    {
        enterTempName = new OseriesEdgeTemplatePage(driver);
        goToAddTempName();

        assertFalse(Boolean.parseBoolean(enterTempName.templateName()), "Template Name added.");
    }
    /**
     * Verify User can click Imposition
     * XRAYOSE-19
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void impositionTest()
    {
        imposition = new OseriesEdgeTemplatePage(driver);
        verifyImposition();
        assertTrue(imposition.imposition(), "Imposition type is not clicked.");
    }

    /**
     * Verify a user can select imposition type
     * XRAYOSE-20
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void impTypeTest()
    {
        clickImp = new OseriesEdgeTemplatePage(driver);
        impositionTypes();
        assertTrue(clickImp.impType(), "Imposition type is not clicked.");
    }
    /**
     * Click on USA
     * XRAYOSE-21
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void clickUSAjurisdictionTest()
    {
        juris = new OseriesEdgeTemplatePage(driver);
        jurisdictionClickUSA();
        assertTrue(juris.jurisdiction(), "USA is clicked.");
    }
    /**
     * Click tax payer location is clicked and gis miles is entered
     * XRAYROSE-22
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void clickTaxPayerLocTest()
    {
        loc = new OseriesEdgeTemplatePage(driver);
        taxLocation();
        assertTrue(loc.taxLocation(), "Tax location is entered.");
    }
    /**
     * CLick engine version and select the latest version
     * XRAYROSE-68
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void engineVersionTest()
    {
        latest = new OseriesEdgeTemplatePage(driver);
        clickLatestVersion();
        assertTrue(latest.engineVersion(), "Latest version of engine is not selected.");
    }
    /**
     * This test clicks the storage location for TEE and store trainingco-main as a locator
     *XRAYOSE-24
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void storageForTeeFileTest()  {
        storeTee = new OseriesEdgeTemplatePage(driver);
        teeStorage();
        assertTrue(storeTee.storageForTeeFile(), "Tee storage location is not entered.");
    }
    /**
     * select zip tee file
     * XRAYOSE-25
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void teeExtractDropDownTest(){
        extractFile = new OseriesEdgeTemplatePage(driver);
        extractTee();
        assertTrue(extractFile.teeExtractDropDown(), "Tee zip file is not stored.");
    }
    /**
     * Upload the tee zip file
     * XRAYOSE-26
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void uploadTeeTest() {
        uploadTee = new OseriesEdgeTemplatePage(driver);
        uploadingTEE();
        assertTrue(uploadTee.uploadTeeFile(), "File uploaded is a zip file.");
    }
    /**
     * Click on manage file button
     * XRAYOSE-64
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void manageFileButtonTest(){
        manageFile = new OseriesEdgeTemplatePage(driver);
        manageFIleButton();
        assertTrue(manageFile.manageFile(), "Manage file button was clicked" );
    }
    /**
     * Enable Journal automation
     * XRAYOSE-27
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void journalAutomationEnableTest(){
        journalButton = new OseriesEdgeTemplatePage(driver);
        enableButtonJournalAutomation();
        assertTrue(journalButton.journalAutomationEnable(), "Journal button is not enabled.");
    }
    /**
     * This test selects the repository type
     * XRAYOSE-28
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void selectRepositoryTest(){
        repoType = new OseriesEdgeTemplatePage(driver);
        repositoryType();
        assertTrue(repoType.selectRepository(), "Repository type is selected.");

    }
    /**
     * fill up information for docker after it is enabled
     * XRAYOSE-29
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void dockerInformationTest(){
        dockerInfo = new OseriesEdgeTemplatePage(driver);
        infoForDocker();
        assertTrue(dockerInfo.dockerInformation(), "Information for docker inserted.");
    }
    /**
     * This test clicks yes for auto repository push
     * XRAYOSE-30
     */
    public void autoPushRepositoryTest(){
        autopush = new OseriesEdgeTemplatePage(driver);
        clickYesAutoRepository();
        assertTrue(autopush.autoPushRepository(), "Auto push for Repository is enabled.");
    }
    /**
     * This test lets user click no for telemetry data return
     * XRAYOSE-32
     */
    public void telemetryDataTest(){
        disableTelemetry = new OseriesEdgeTemplatePage(driver);
        telemetry();
        assertTrue(disableTelemetry.telemetryData(), "Telemetry data is disabled.");
    }
    /**
     * This test lets the user to click on save template button
     * XRAYOSE-31
     */
    public void saveTemplateButtonTest(){
        saveButton = new OseriesEdgeTemplatePage(driver);
        saveTemplate();
    }
    /**
     * user can find template name on the search template name and edit field
     * XRAYOSE-47
     */
    @Test(groups = {"oseriesEdge-regression"})
    public void templateFoundTest() {
        tempName = new OseriesEdgeTemplatePage(driver);
        searchTemplateName();

    }

}
