package com.vertex.quality.connectors.dynamics365.business.tests;

import com.vertex.quality.connectors.dynamics365.business.pages.BusinessAdminHomePage;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessFlexFieldsPage;
import com.vertex.quality.connectors.dynamics365.business.pages.BusinessVertexAdminPage;
import com.vertex.quality.connectors.dynamics365.business.tests.base.BusinessBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Contains tests for different flex field scenarios
 *
 * @author cgajes
 */
@Listeners(TestRerunListener.class)
public class BusinessFlexFieldsTests extends BusinessBaseTest
{
	BusinessAdminHomePage homePage;
	/**
	 * CDBC-1001
	 * Tests creating new Account (Customer) flex field with the Numeric type
	 * Deletes the flex field after the test
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Smoke" }, retryAnalyzer = TestRerun.class)
	public void createNewAccountNumericTypeTest( )
	{
		String flexSource = "Account (Customer)";
		String flexType = "Numeric";
		String flexId = "5";
		String flexValue = "Last Statement No.";
		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessVertexAdminPage adminPage = homePage.navigateToVertexAdminPage();
		adminPage.changeToWideLayout();
		adminPage.openFlexFieldsCategory();
		adminPage.createNewFlexFieldEntry();
		fillInNewFlexFieldInfo(flexSource, flexType, flexId, flexValue);
		adminPage.toggleReadOnlyMode();
		String row = adminPage.getFlexValue();
		assertTrue(row.equals(flexValue));
		adminPage.deleteField();
		adminPage.exitFocusMode();
	}

	/**
	 * CDBC-1002
	 * Tests creating new Account (Customer) flex field with the Date type
	 * Deletes the flex field after the test
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void createNewAccountDateTypeTest( )
	{
		String flexSource = "Account (Customer)";
		String flexType = "Date";
		String flexId = "2";
		String flexValue = "Date Filter";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessVertexAdminPage adminPage = homePage.navigateToVertexAdminPage();
		adminPage.changeToWideLayout();
		adminPage.openFlexFieldsCategory();
		adminPage.createNewFlexFieldEntry();
		fillInNewFlexFieldInfo(flexSource, flexType, flexId, flexValue);
		adminPage.toggleReadOnlyMode();
		String row = adminPage.getFlexValue();
		assertTrue(row.equals(flexValue));
		adminPage.deleteField();
		}

	/**
	 * CDBC-1003
	 * Tests creating new Account (Customer) flex field with the Text type
	 * Deletes the flex field after the test
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void createNewAccountTextTypeTest( )
	{
		String flexSource = "Account (Customer)";
		String flexType = "Text";
		String flexId = "25";
		String flexValue = "Bill-to Customer No.";
		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessVertexAdminPage adminPage = homePage.navigateToVertexAdminPage();
		adminPage.changeToWideLayout();
		adminPage.openFlexFieldsCategory();
		adminPage.createNewFlexFieldEntry();
		fillInNewFlexFieldInfo(flexSource, flexType, flexId, flexValue);
		adminPage.toggleReadOnlyMode();
		String row = adminPage.getFlexValue();
		assertTrue(row.equals(flexValue));
		adminPage.deleteField();
		}

	/**
	 * CDBC-1004
	 * Tests creating new Constant Value flex field with the Date type
	 * Deletes the flex field after the test
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void createNewConstantValueDateTypeTest( )
	{
		String flexSource = "Constant Value";
		String flexType = "Date";
		String flexId = "2";
		String flexValue = getTransactionDate();

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessVertexAdminPage adminPage = homePage.navigateToVertexAdminPage();
		adminPage.changeToWideLayout();
		adminPage.openFlexFieldsCategory();
		adminPage.createNewFlexFieldEntry();
		fillInNewFlexFieldInfo(flexSource, flexType, flexId, flexValue);
		adminPage.toggleReadOnlyMode();
		String row = adminPage.getFlexValue();
		assertTrue(row.equals(flexValue));
		adminPage.deleteField();
		}

	/**
	 * CDBC-1005
	 * Tests cleansing the date field when an invalid (not M/D/YYYY) format is input
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void flexFieldDateCleansingTest( )
	{
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
		SimpleDateFormat sdf1 = new SimpleDateFormat("M//d//yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("M---d---yyyy");
		SimpleDateFormat sdf3 = new SimpleDateFormat("M/d/yyyy");

		String flexSource = "Constant Value";
		String flexType = "Date";
		String flexId = "2";
		String expectedFlexValue = sdf.format(date);
		String testDate1 = sdf1.format(date);
		String testDate2 = sdf2.format(date);
		String testDate3 = sdf3.format(date);
		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessVertexAdminPage adminPage = homePage.navigateToVertexAdminPage();
		BusinessFlexFieldsPage flexFieldsPage=new BusinessFlexFieldsPage(driver);
		adminPage.changeToWideLayout();
		adminPage.openFlexFieldsCategory();
		adminPage.createNewFlexFieldEntry();
		flexFieldsPage.selectFlexFieldSource(flexSource);
		flexFieldsPage.selectFlexFieldType(flexType);
		flexFieldsPage.inputFlexFieldId(flexId);
		flexFieldsPage.inputFlexFieldValueDate(testDate1);
		adminPage.toggleReadOnlyMode();
		String actualFlexValue1=flexFieldsPage.getDateFieldValue();
		assertEquals(actualFlexValue1, expectedFlexValue);
		adminPage.editMode();
		//test date Format 2
		flexFieldsPage.inputFlexFieldValueDate(testDate2);
		adminPage.toggleReadOnlyMode();
		String actualFlexValue2=flexFieldsPage.getDateFieldValue();
		assertEquals(actualFlexValue2, expectedFlexValue);
		adminPage.editMode();
		//test date Format 3
		flexFieldsPage.inputFlexFieldValueDate(testDate3);
		adminPage.toggleReadOnlyMode();
		String actualFlexValue3=flexFieldsPage.getDateFieldValue();
		assertEquals(actualFlexValue3, expectedFlexValue);
		adminPage.editMode();
		flexFieldsPage.deleteRowWithMoreOptions(1);
		adminPage.dialogBoxClickYes();
		}

	/**
	 * CDBC-1006
	 * Tests the error that should appear when creating a flex field
	 * with an invalid ID
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void flexFieldIdInUseErrorTest( )
	{
		String flexSource = "Quote/Order/Invoice Line";
		String flexType = "Date";
		String flexId = "3";
		String expectedErrorMessage = "Validation Results ID 3 is already in use. Please choose a different ID";
		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessFlexFieldsPage flexFieldsPage=new BusinessFlexFieldsPage(driver);
		BusinessVertexAdminPage adminPage = homePage.navigateToVertexAdminPage();
		adminPage.changeToWideLayout();
		adminPage.openFlexFieldsCategory();
		adminPage.createNewFlexFieldEntry();
		flexFieldsPage.selectFlexFieldSource(flexSource);
		flexFieldsPage.selectFlexFieldType(flexType);
		flexFieldsPage.inputFlexFieldId(flexId);

		String actualErrorMessage = flexFieldsPage.getErrorMessage();
		assertEquals(actualErrorMessage, expectedErrorMessage);
	}

	/**
	 * CDBC-1007
	 * Tests the error that should appear when creating a flex field and
	 * an invalid value is input
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void flexFieldInvalidValueErrorTest( )
	{
		String flexSource = "Quote/Order/Invoice Line";
		String flexType = "Date";
		String flexId = "2";
		String flexValue = "8454369089630";
		String expectedErrorMessage = String.format(
			"Validation Results The field Value (Date) of table Flex Fields contains a value (%1$s) that cannot be found in the related table (Flex Field Values).",
			flexValue);
		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessVertexAdminPage adminPage = homePage.navigateToVertexAdminPage();
		BusinessFlexFieldsPage flexFieldsPage=new BusinessFlexFieldsPage(driver);
		adminPage.changeToWideLayout();
		adminPage.openFlexFieldsCategory();
		adminPage.createNewFlexFieldEntry();
		fillInNewFlexFieldInfo(flexSource, flexType, flexId, flexValue);
		String actualErrorMessage = flexFieldsPage.getErrorMessage();
		assertEquals(actualErrorMessage, expectedErrorMessage);
		flexFieldsPage.deleteRowWithMoreOptions(1);
		adminPage.dialogBoxClickYes();
	}

	/**
	 * CDBC-1008
	 * Tests the error that should appear when creating a flex field with
	 * a value that has a decimal of 3 or more numbers after the decimal point
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void flexFieldThreePointDecimalErrorTest( )
	{
		String flexSource = "Constant Value";
		String flexType = "Numeric";
		String flexId = "3";
		String flexValue = "12.555";
		String expectedErrorMessage = String.format(
			"Validation Results Your entry of '%1$s' is not an acceptable value for 'Value Constant (Numeric)'. The field can have a maximum of 2 decimal places.",
			flexValue);

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessVertexAdminPage adminPage = homePage.navigateToVertexAdminPage();
		BusinessFlexFieldsPage flexFieldsPage=new BusinessFlexFieldsPage(driver);
		adminPage.changeToWideLayout();
		adminPage.openFlexFieldsCategory();
		adminPage.createNewFlexFieldEntry();
		fillInNewFlexFieldInfo(flexSource, flexType, flexId, flexValue);
		String actualErrorMessage = flexFieldsPage.getErrorMessage();
		assertEquals(actualErrorMessage, expectedErrorMessage);
		flexFieldsPage.deleteRowWithMoreOptions(1);
		adminPage.dialogBoxClickYes();
	}

	/**
	 * CDBC-1009
	 * Opens an existing flex field to ensure there is no edit option
	 * and the flex field page is locked into read only mode
	 * Tests is no longer valid
	 */
	@Ignore
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Deprecated" }, retryAnalyzer = TestRerun.class)
	public void ensureCreatedFlexFieldNotEditableTest( )
	{
		String flexSource = "Constant Value";
		String flexType = "Text";
		String flexId = "10";
		String flexValue = "testing";
		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessVertexAdminPage adminPage = homePage.navigateToVertexAdminPage();
		adminPage.openFlexFieldsCategory();
		WebElement row = adminPage.getFlexFieldRow(flexSource, flexType, flexId, flexValue);
		boolean noEditOptionAvailable = adminPage.ensureNoEditOptionOnExistingFlexField(row);
		assertTrue(noEditOptionAvailable);
		adminPage.clickBackAndSaveArrow();
		homePage.refreshPage();
		adminPage = homePage.navigateToVertexAdminPage();
		row = adminPage.getFlexFieldRow(flexSource, flexType, flexId, flexValue);
		BusinessFlexFieldsPage flexFieldPage = adminPage.openFlexFieldRow(row);
		boolean editModeToggleDisabled = flexFieldPage.checkEditToggleDisabled();
		assertTrue(editModeToggleDisabled);
		boolean allInputFieldsDisabled = checkAllFlexFieldInputsDisabled(flexFieldPage);
		assertTrue(allInputFieldsDisabled);

		adminPage = flexFieldPage.deleteFlexField();
		row = adminPage.getFlexFieldRow(flexSource, flexType, flexId, flexValue);
		assertTrue(null == row);

		flexFieldPage = adminPage.flexFieldCreateNew();
		fillInNewFlexFieldInfo(flexSource, flexType, flexId, flexValue);

		flexFieldPage.clickBackAndSaveArrow();
	}
	/**
	 * CDBC-1102
	 * Create flex field for Purchase Quote/Order/Invoice Line with Text type within valid range and delete the entry
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Smoke" }, retryAnalyzer = TestRerun.class)
	public void createAPFlexFieldsTest( )
	{
		String flexSource = "Purchase Quote/Order/Invoice Line";
		String flexType = "Text";
		String flexId = "25";
		String flexValue = "Area";
		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessVertexAdminPage adminPage = homePage.navigateToVertexAdminPage();
		adminPage.changeToWideLayout();
		adminPage.openFlexFieldsCategory();
		adminPage.createNewFlexFieldEntry();
		fillInNewFlexFieldInfo(flexSource, flexType, flexId, flexValue);
		adminPage.toggleReadOnlyMode();
		String row = adminPage.getFlexValue();
		assertTrue(row.equals(flexValue));
		adminPage.deleteField();
		adminPage.exitFocusMode();
		}
	/**
	 * CDBC-1103
	 * Creates flex field for Purchase Quote/Order/Invoice Line with invalid id
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression" }, retryAnalyzer = TestRerun.class)
	public void flexFieldAPInvalidIDErrorTest( )
	{
		String flexSource = "Purchase Quote/Order/Invoice Line";
		String flexType = "Numeric";
		String flexId = "16";
		String expectedErrorMessage = "Validation Results Field ID must be between 1 and 10";
		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessVertexAdminPage adminPage = homePage.navigateToVertexAdminPage();
		BusinessFlexFieldsPage flexFieldsPage=new BusinessFlexFieldsPage(driver);
		adminPage.changeToWideLayout();
		adminPage.openFlexFieldsCategory();
		adminPage.createNewFlexFieldEntry();
		flexFieldsPage.selectFlexFieldSource(flexSource);
		flexFieldsPage.selectFlexFieldType(flexType);
		flexFieldsPage.inputFlexFieldId(flexId);

		String actualErrorMessage = flexFieldsPage.getErrorMessage();
		assertEquals(actualErrorMessage, expectedErrorMessage);
		}
	/**
	 * CDBC-1104
	 * Create flex field for Account (Vendor) with Date type within valid range and delete the entry
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Smoke" }, retryAnalyzer = TestRerun.class)
	public void createAPFlexFieldsForAccountVendorTest( )
	{
		String flexSource = "Account (Vendor)";
		String flexType = "Date";
		String flexId = "2";
		String flexValue = "Date Filter";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessVertexAdminPage adminPage = homePage.navigateToVertexAdminPage();
		adminPage.changeToWideLayout();
		adminPage.openFlexFieldsCategory();
		adminPage.createNewFlexFieldEntry();
		fillInNewFlexFieldInfo(flexSource, flexType, flexId, flexValue);
		adminPage.toggleReadOnlyMode();
		String row = adminPage.getFlexValue();
		assertTrue(row.equals(flexValue));
		adminPage.deleteField();
		}
	/**
	 * CDBC-1105
	 * Create flex field for Purchase Quote/Order/Invoice Header with Numeric type within valid range and delete the entry
	 */
	@Test(alwaysRun = true, groups = { "D365_Business_Central_Smoke" }, retryAnalyzer = TestRerun.class)
	public void createAPFlexFieldsForHeaderTest( )
	{
		String flexSource = "Purchase Quote/Order/Invoice Header";
		String flexType = "Numeric";
		String flexId = "3";
		String flexValue = "No. Printed";
		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessVertexAdminPage adminPage = homePage.navigateToVertexAdminPage();
		adminPage.changeToWideLayout();
		adminPage.openFlexFieldsCategory();
		adminPage.createNewFlexFieldEntry();
		fillInNewFlexFieldInfo(flexSource, flexType, flexId, flexValue);
		adminPage.toggleReadOnlyMode();
		String row = adminPage.getFlexValue();
		assertTrue(row.equals(flexValue));
		adminPage.deleteField();

	}
	@BeforeMethod(alwaysRun = true)
	public void setUpBusinessMgmt(){
		String role="Business Manager";
		homePage = new BusinessAdminHomePage(driver);
		String verifyPage=homePage.verifyHomepageHeader();
		if(!verifyPage.contains(role)){

			//navigate to select role as Business Manager
			homePage.selectSettings();
			homePage.navigateToManagerInSettings(role);
		}
	}
}
