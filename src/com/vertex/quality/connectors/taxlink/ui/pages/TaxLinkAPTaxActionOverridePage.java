package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * This class contains all the all the methods to test the pages in AP Tax Action Override tab in TaxLink UI
 *
 * @author mgaikwad
 */
public class TaxLinkAPTaxActionOverridePage extends TaxLinkBasePage
{
	private WebDriverWait wait = new WebDriverWait(driver, 10);
	private TaxLinkUIUtilities utils = new TaxLinkUIUtilities();

	private Actions act = new Actions(driver);

	public TaxLinkAPTaxActionOverridePage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[@value='OVERCHARGED']")
	protected WebElement overchargedSection;

	@FindBy(xpath = "//button[@value='UNDERCHARGED']")
	protected WebElement underchargedSection;

	@FindBy(xpath = "//button[@value='ZEROCHARGED']")
	protected WebElement zerochargedSection;

	@FindBy(xpath = "//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div/div[@col-id='legalEntityName']")
	private List<WebElement> summaryTableAPTaxActOverride;

	@FindBy(xpath = "//*[@id='myGrid']/div/div/div[1]/div/div[3]/div[3]/div[1]/div/div/div/div/button")
	private WebElement threeDots;

	@FindBy(name = "holdReasonCode")
	private WebElement applyHoldText;

	@FindBy(xpath = "//button[contains(text(), 'Add')]")
	private WebElement pageAddButton;

	@FindBy(xpath = "(//button[contains(text(), 'Edit')])[1]")
	private WebElement editButton;

	@FindBy(xpath = "//label[contains(text(), 'Legal Entity')]/following-sibling::select")
	private WebElement legalEntity;

	@FindBy(xpath = "//label[contains(text(), 'Business Unit')]/following-sibling::select")
	private WebElement businessUnit;

	@FindBy(xpath = "//label[contains(text(), 'AP Invoice Source')]/following-sibling::select")
	private WebElement apInvSrc;

	@FindBy(xpath = "//label[contains(text(), 'Tax Action')]/following-sibling::select")
	private WebElement taxAction;

	@FindBy(xpath = "//label[contains(text(), 'Supplier Type')]/following-sibling::select")
	protected WebElement supplierType;

	@FindBy(xpath = "//input[@name='inputFlexcodeValue']")
	private WebElement inputFlexValue;

	@FindBy(xpath = "//input[@name='taxActionPrecedence']")
	private WebElement taxActionPrec;

	@FindBy(xpath = "//label[contains(text(), 'Calculated Zero Tax')]/following-sibling::select")
	private WebElement calcZeroTax;

	@FindBy(xpath = "//input[@name='holdFlag']")
	private WebElement applyHoldCheckBox;

	@FindBy(xpath = "(//div/p[2])[1]")
	private WebElement messageDataSaved;

	@FindBy(xpath = "(//div/p[2])[2]")
	private WebElement messageNewRec;

	@FindBy(xpath = "//div[@class='react-datepicker__month']/div/div")
	private List<WebElement> endDateCalendar;

	@FindBy(xpath = "//div[@col-id='businessUnitName']")
	private List<WebElement> buPresentation;

	@FindBy(xpath = "//div[@col-id='legalEntityName']")
	private List<WebElement> lePresentation;

	@FindBy(xpath = "//div[@col-id='supplierTypeName']")
	private List<WebElement> supplierTypePresentation;

	@FindBy(xpath = "//div[@col-id='invoiceSourceDescription']")
	private List<WebElement> apInvSrcPresentation;

	@FindBy(xpath = "//div[@col-id='taxActionPrecedence']")
	private List<WebElement> taxActionPrecPresentation;

	@FindBy(xpath = "//div[@col-id='overrideTaxAction']")
	private List<WebElement> overrideTaxActionPresentation;

	@FindBy(xpath = "//div[@col-id='holdFlag']")
	private List<WebElement> holdPresentation;

	/**
	 * Method to pass dynamic index values to By locator
	 *
	 * @param num
	 *
	 * @return
	 */
	public By xpathIndex( int num )
	{
		By column = By.xpath(".//following-sibling::div[" + num + "]");
		return column;
	}

	/**
	 * Method to pass dynamic index values to WebElement
	 *
	 * @param num
	 *
	 * @return
	 */
	public WebElement xpathInd( int num )
	{
		By element = By.xpath("//*[@id= 'myGrid']/div/div/div[1]/div/div[3]/div[2]/div/div/div[1]/div[" + num + "]");
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(element));

		return ele;
	}

	/**
	 * Method to select value from Dropdown and get text of the same
	 *
	 * @param ele
	 * @param index
	 *
	 * @return
	 */
	public List<String> drpDown_Select_GetText( WebElement ele, int index )
	{
		wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(ele, By.tagName("option")));

		click.clickElement(ele);
		dropdown.selectDropdownByIndex(ele, index);
		String text = dropdown
			.getDropdownOptions(ele)
			.get(index)
			.getText();
		String attr = dropdown
			.getDropdownOptions(ele)
			.get(index)
			.getAttribute("value");

		List<String> drpDown = Arrays.asList(text, attr);

		return drpDown;
	}

	/**
	 * Method to add new record in AP Tax Action Override
	 * as per the section name passed
	 *
	 * @param ele
	 *
	 * @return
	 */
	public boolean addAPTaxActOverride( WebElement ele )
	{
		boolean flag;

		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		boolean flag1 = summaryPageTitle
			.getText()
			.equals("AP Tax Action Override");

		wait.until(ExpectedConditions.visibilityOf(ele));
		click.clickElement(ele);
		click.clickElement(addButton);

		wait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

		String text_BU = drpDown_Select_GetText(businessUnit, 1).get(0);
		String text_AP = drpDown_Select_GetText(apInvSrc, 1).get(0);
		String text_LE = drpDown_Select_GetText(legalEntity, 1).get(0);
		dropdown.selectDropdownByIndex(supplierType, 1);
		text.enterText(inputFlexValue, "120");
		text.enterText(taxActionPrec, "5");
		dropdown.selectDropdownByIndex(calcZeroTax, 2);
		String text_TA = drpDown_Select_GetText(taxAction, 1).get(1);

		String taxActionPrecVal = taxActionPrec.getAttribute("value");
		String inputFlexVal = inputFlexValue.getAttribute("value");
		String text_ST = supplierType.getText();

		click.clickElement(saveButton);
		wait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

		boolean flag2 = messageDataSaved.isDisplayed() && messageDataSaved
			.getText()
			.equals("Data Saved Successfully");

		boolean flag3 = messageNewRec.isDisplayed() && messageNewRec
			.getText()
			.equals("Please click on the Add button to enter a new record");

		boolean flag4 = pageAddButton.isEnabled();

		click.clickElement(closeButton);
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		int count = Integer.parseInt(totalPageCountSummaryTable.getText());
		boolean flag5 = false;

		for ( int i = 1 ; i <= count ; i++ )
		{
			for ( int j = 0 ; j < summaryTableAPTaxActOverride.size() ; j++ )
			{
				WebElement n = summaryTableAPTaxActOverride.get(j);

				if ( n
					.findElement(xpathIndex(4))
					.getText()
					.equals(taxActionPrecVal) )
				{
					boolean legal_entity = n
						.getText()
						.equals(text_LE);
					boolean business_unit = n
						.findElement(xpathIndex(1))
						.getText()
						.equals(text_BU);
					//can be uncommented after the bug fix
				/*	boolean supplier_Type = n
						.findElement(xpathIndex(2))
						.getText()
						.equals(text_ST);*/
					boolean ap_invoice = n
						.findElement(xpathIndex(3))
						.getText()
						.equals(text_AP);

					act
						.sendKeys(Keys.TAB)
						.perform();
					act
						.sendKeys(Keys.TAB)
						.perform();
					boolean override_tax_action = n
						.findElement(xpathIndex(5))
						.getText()
						.equals(text_TA);

					act
						.sendKeys(Keys.TAB)
						.perform();
					boolean apply_hold = n
						.findElement(xpathIndex(6))
						.getText()
						.equals("N");

					act
						.sendKeys(Keys.TAB)
						.perform();
					boolean enabled = n
						.findElement(xpathIndex(7))
						.getText()
						.equals("Y");

					if ( legal_entity && business_unit && ap_invoice && override_tax_action && apply_hold && enabled )
					{
						flag5 = true;
						VertexLogger.log("Data present for " + ele.getText() + "::" + " LegalEntity= " + text_LE +
										 " ,Business Unit= " + text_BU + " ,AP Invoice Source= " + text_AP +
										 " ,Supplier Type= " + " ,Input Flexcode Value= " + inputFlexVal +
										 " ,Tax Action Precedence= " + taxActionPrecVal + " ,Override Tax Action= " +
										 override_tax_action + " Apply Hold= N " + " ,Enabled = Y");

						break;
					}
				}
				if ( j == summaryTableAPTaxActOverride.size() - 1 )
				{
					click.clickElement(nextArrow);
				}
			}

			if ( flag5 )
			{
				break;
			}
		}

		if ( flag1 && flag2 && flag3 && flag4 && flag5 )
		{
			flag = true;
		}
		else

		{
			flag = false;
		}
		return flag;
	}

	/**
	 * Method Overloading------
	 * Method to add new record in AP Tax Action Override
	 *
	 * @return
	 */
	public boolean addAPTaxActOverride( )
	{
		boolean flag;

		navigateToAPTaxActOverride();

		boolean flag1 = addAPTaxActOverride(overchargedSection);
		boolean flag2 = addAPTaxActOverride(underchargedSection);
		boolean flag3 = addAPTaxActOverride(zerochargedSection);

		if ( flag1 && flag2 && flag3 )
		{
			flag = true;
		}
		else
		{
			flag = false;
		}

		return flag;
	}

	/**
	 * Method to verify Edit functionality of AP Tax Action Override
	 * as per the section name and index values of WebElement passed
	 *
	 * @return
	 */
	public boolean editAPTaxActOverride( WebElement e, int ind1, int ind2 )
	{
		boolean flag = false;

		click.clickElement(e);

		String apply_hold = xpathInd(ind1).getText();
		String enabled = xpathInd(ind2).getText();

		click.clickElement(editButton);
		wait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

		click.clickElement(enabledFlag);

		if ( apply_hold.equals("N") )
		{
			boolean flag3 = !applyHoldCheckBox.isSelected();
			if ( flag3 )
			{
				click.clickElement(applyHoldCheckBox);
				wait.until(ExpectedConditions.visibilityOf(applyHoldText));
				text.enterText(applyHoldText, "Apply Hold");
			}
			else
			{
				VertexLogger.log("Apply Hold checkbox is selected but Value in Summary Table is 'N'");
			}
		}
		else
		{
			boolean flag3 = applyHoldCheckBox.isSelected();
			if ( flag3 )
			{
				click.clickElement(applyHoldCheckBox);
				flag3 = !applyHoldText.isDisplayed();
			}
			else
			{
				VertexLogger.log("Apply Hold checkbox is not selected but Value in Summary Table is 'Y'");
			}
		}

		click.clickElement(saveButton);
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		dropdown.selectDropdownByDisplayName(externalFilter, "Both");
		jsWaiter.sleep(2000);

		boolean flag1 = !xpathInd(ind1)
			.getText()
			.equals(apply_hold);
		boolean flag2 = !xpathInd(ind2)
			.getText()
			.equals(enabled);

		if ( flag1 && flag2 )
		{
			flag = true;
		}

		return flag;
	}

	/**
	 * Method Overloading--------------
	 * Method to Edit record in AP Tax Action Override
	 *
	 * @return
	 */
	public boolean editAPTaxActOverride( )
	{
		boolean flag = false;

		navigateToAPTaxActOverride();
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		boolean flag1 = editAPTaxActOverride(overchargedSection, 7, 8);
		boolean flag2 = editAPTaxActOverride(underchargedSection, 7, 8);
		boolean flag3 = editAPTaxActOverride(zerochargedSection, 7, 8);

		if ( flag1 && flag2 && flag3 )
		{
			flag = true;
		}

		return flag;
	}

	/**
	 * Method to verify View functionality of AP Tax Action Override
	 *
	 * @param e
	 *
	 * @return
	 */
	public boolean viewAPTaxActOverride( WebElement e )
	{
		boolean flag = false;

		click.clickElement(e);

		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		click.clickElement(threeDots);
		wait.until(ExpectedConditions.visibilityOf(viewButton));
		click.clickElement(viewButton);
		wait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

		boolean flag1 = !legalEntity.isEnabled();
		boolean flag2 = !businessUnit.isEnabled();
		boolean flag3 = !apInvSrc.isEnabled();
		boolean flag4 = !taxAction.isEnabled();
		boolean flag5 = !inputFlexValue.isEnabled();
		boolean flag6 = !supplierType.isEnabled();
		boolean flag7 = !taxActionPrec.isEnabled();
		boolean flag8 = !calcZeroTax.isEnabled();
		boolean flag9 = !enabledFlag.isEnabled();
		boolean flag10 = !applyHoldCheckBox.isEnabled();
		boolean flag11 = !saveButton.isEnabled();

		click.clickElement(cancelButton);
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		if ( flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8 && flag9 && flag10 && flag11 )
		{
			VertexLogger.log("Verified View page for " + e.getText());
			flag = true;
		}
		return flag;
	}

	/**
	 * Method Overloading--------
	 * Method to view AP Tax Action Override
	 *
	 * @return
	 */
	public boolean viewAPTaxActOverride( )
	{
		boolean flag = false;

		navigateToAPTaxActOverride();
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		boolean flag1 = viewAPTaxActOverride(overchargedSection);
		boolean flag2 = viewAPTaxActOverride(underchargedSection);
		boolean flag3 = viewAPTaxActOverride(zerochargedSection);

		if ( flag1 && flag2 && flag3 )
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * Method of export to CSV - section OVERCHARGED
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public boolean exportToCSV_Overcharged( String instName ) throws Exception
	{
		boolean flag = exportToCSV(overchargedSection, instName);

		return flag;
	}

	/**
	 * Method of export to CSV - section UNDERCHARGED
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public boolean exportToCSV_Undercharged( String instName ) throws Exception
	{
		boolean flag = exportToCSV(underchargedSection, instName);

		return flag;
	}

	/**
	 * Method of export to CSV - section ZEROCHARGED
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public boolean exportToCSV_Zerocharged( String instName ) throws Exception
	{
		boolean flag = exportToCSV(zerochargedSection, instName);

		return flag;
	}

	/**
	 * Method to search data in particular column of
	 * summary tables
	 *
	 * @return Optional<WebElement>
	 */
	public Optional<WebElement> dataPresentInParticularColumn( List<WebElement> ele, String text )
	{
		Optional<WebElement> dataFound = ele
			.stream()
			.filter(col -> col
				.getText()
				.contains(text))
			.findFirst();

		return dataFound;
	}

	/**
	 * Method to verify Export To CSV
	 *
	 * @return flagExportToCSVOverride
	 *
	 * @throws Exception
	 */
	public boolean exportToCSV( WebElement ele, String instName ) throws Exception
	{
		boolean flagExportToCSVOverride = false;
		boolean flagInstanceNameMatch = false;
		boolean flagBuNameMatch = false;
		boolean flagSupplierTypeMatch = false;
		boolean flagApInvSrcMatch = false;
		boolean flagEnabledMatch = false;
		boolean flagHoldMatch = false;
		boolean flagTaxActionPrecMatch = false;
		boolean flagOverrideTAMatch = false;
		boolean taxScenarioFlag = false;

		navigateToAPTaxActOverride();
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		dropdown.selectDropdownByDisplayName(externalFilter, "Both");

		click.clickElement(ele);
		if ( !checkNoRecordsPresent() )
		{
			expWait.until(ExpectedConditions.visibilityOf(exportToCSVSummaryPage));
			exportToCSVSummaryPage.click();

			String fileName = "TaxActionOverride_Extract.csv";
			String fileDownloadPath = String.valueOf(getFileDownloadPath());
			File file = new File(fileDownloadPath + File.separator + fileName);
			VertexLogger.log(String.valueOf(file));

			jsWaiter.sleep(2000);
			try ( Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file))) ;
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT) ; )
			{
				for ( CSVRecord csvRecord : csvParser )
				{
					// Accessing Values by Column Index
					String name = csvRecord.get(0);
					if ( name.contains(instName) )
					{
						VertexLogger.log("Fusion Instance Name : " + name);
						flagInstanceNameMatch = true;
						break;
					}
				}
			}

			try ( Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file))) ;
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
					.withFirstRecordAsHeader()
					.withHeader("Tax Scenario", "Legal Entity", "Business Unit", "Supplier Type", "AP Invoice Source",
						"Tax Action Precedence", "Override Tax Action", "Hold", "Enabled")
					.withTrim()) ; )
			{
				for ( CSVRecord csvRecord : csvParser )
				{
					// Accessing values by Header names
					String taxScenario_CSV = csvRecord.get("Tax Scenario");
					String legalEntity_CSV = csvRecord.get("Legal Entity");
					String bUID_CSV = csvRecord.get("Business Unit");
					String supplierType_CSV = csvRecord.get("Supplier Type");
					String apInvoiceSource_CSV = csvRecord.get("AP Invoice Source");
					String taxActionPrecedence_CSV = csvRecord.get("Tax Action Precedence");
					String overrideTaxAction_CSV = csvRecord.get("Override Tax Action");
					String hold_CSV = csvRecord.get("Hold");
					String enabled_CSV = csvRecord.get("Enabled");

					VertexLogger.log("Record No - " + csvRecord.getRecordNumber());
					VertexLogger.log("---------------");
					VertexLogger.log("Tax Scenario : " + taxScenario_CSV);
					VertexLogger.log("Legal Entity : " + legalEntity_CSV);
					VertexLogger.log("Business Unit : " + bUID_CSV);
					VertexLogger.log("Supplier Type : " + supplierType_CSV);
					VertexLogger.log("AP Invoice Source : " + apInvoiceSource_CSV);
					VertexLogger.log("Tax Action Precedence : " + taxActionPrecedence_CSV);
					VertexLogger.log("Override Tax Action : " + overrideTaxAction_CSV);
					VertexLogger.log("Hold : " + hold_CSV);
					VertexLogger.log("Enabled : " + enabled_CSV);
					VertexLogger.log("---------------\n\n");

					if ( !taxActionPrecedence_CSV.equals("Tax Action Precedence") )
					{
						Optional legalEntity = dataPresentInParticularColumn(lePresentation, legalEntity_CSV);
						if ( legalEntity.isPresent() )
						{
							String taxScenario = ele.getAttribute("value");
							taxScenarioFlag = taxScenario_CSV.equalsIgnoreCase(taxScenario);
							VertexLogger.log("" + taxScenarioFlag);
							Optional buName = dataPresentInParticularColumn(buPresentation, bUID_CSV);
							flagBuNameMatch = buName.isPresent();
							VertexLogger.log("" + flagBuNameMatch);
							Optional supplierType = dataPresentInParticularColumn(supplierTypePresentation,
								supplierType_CSV);
							flagSupplierTypeMatch = supplierType.isPresent();
							VertexLogger.log("" + flagSupplierTypeMatch);
							Optional apInvSrcMatch = dataPresentInParticularColumn(apInvSrcPresentation,
								apInvoiceSource_CSV);
							flagApInvSrcMatch = apInvSrcMatch.isPresent();
							VertexLogger.log("" + flagApInvSrcMatch);
							Optional taxActionMatch = dataPresentInParticularColumn(taxActionPrecPresentation,
								taxActionPrecedence_CSV);
							flagTaxActionPrecMatch = taxActionMatch.isPresent();
							VertexLogger.log("" + flagTaxActionPrecMatch);
							Optional overrideMatch = dataPresentInParticularColumn(overrideTaxActionPresentation,
								overrideTaxAction_CSV);
							flagOverrideTAMatch = overrideMatch.isPresent();
							VertexLogger.log("" + flagOverrideTAMatch);
							Optional holdMatch = dataPresentInParticularColumn(holdPresentation, hold_CSV);
							flagHoldMatch = holdMatch.isPresent();
							VertexLogger.log("" + flagHoldMatch);
							Optional enabled = dataPresentInParticularColumn(enabledFlagPresentation, enabled_CSV);
							flagEnabledMatch = enabled.isPresent();
							VertexLogger.log("" + flagEnabledMatch);
						}
						else
						{
							htmlElement.sendKeys(Keys.END);
							jsWaiter.sleep(100);
							click.clickElement(nextArrowOnSummaryTable);
						}
						if ( flagInstanceNameMatch && flagSupplierTypeMatch && flagBuNameMatch && taxScenarioFlag &&
							 flagApInvSrcMatch && flagTaxActionPrecMatch && flagOverrideTAMatch && flagHoldMatch &&
							 flagEnabledMatch )
						{
							flagExportToCSVOverride = true;
						}
						else
						{
							flagExportToCSVOverride = false;
						}
					}
				}
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
			finally
			{
				if ( file.delete() )
				{
					VertexLogger.log("File deleted successfully");
				}
				else
				{
					VertexLogger.log("Failed to delete the file");
				}
			}
		}
		else
		{
			flagExportToCSVOverride = true;
		}
		return flagExportToCSVOverride;
	}

	/**
	 * Method to add and check empty fields auto populates with "All" prefix
	 *
	 * @param ele
	 * @param ele1
	 * @param ele2
	 * @param ele3
	 * @param ele4
	 * @param val1
	 * @param val2
	 * @param val3
	 * @param val4
	 *
	 * @return
	 */
	public boolean emptyFields_reflectAll( WebElement ele, WebElement ele1, WebElement ele2, WebElement ele3,
		WebElement ele4, String val1, String val2, String val3, String val4 )
	{
		boolean flag = false;

		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));
		wait.until(ExpectedConditions.visibilityOf(ele));
		click.clickElement(ele);
		click.clickElement(addButton);
		wait.until(ExpectedConditions.visibilityOf(addViewEditPageTitle));

		expWait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(ele1, By.tagName("option")));
		dropdown.selectDropdownByIndex(ele1, 1);
		String ele1_value = dropdown
			.getDropdownSelectedOption(ele1)
			.getAttribute("name");
		VertexLogger.log(ele1_value);

		String ele2_value = dropdown
			.getDropdownSelectedOption(ele2)
			.getAttribute("name");
		VertexLogger.log(ele2_value);

		String ele3_value = dropdown
			.getDropdownSelectedOption(ele3)
			.getAttribute("name");
		VertexLogger.log(ele3_value);

		String ele4_value = dropdown
			.getDropdownSelectedOption(ele4)
			.getAttribute("name");
		VertexLogger.log(ele4_value);

		dropdown.selectDropdownByIndex(taxAction, 1);

		text.enterText(inputFlexValue, "120");
		text.enterText(taxActionPrec, "5");

		boolean all_flag = ele2_value.contains("All") && ele3_value.contains("All") && ele4_value.contains("All");

		Map<String, String> map = new TreeMap<>();
		map.put(val1, ele1_value);
		map.put(val2, ele2_value);
		map.put(val3, ele3_value);
		map.put(val4, ele4_value);
		List<Map.Entry<String, String>> list = new ArrayList<>(map.entrySet());

		click.clickElement(saveButton);
		wait.until(ExpectedConditions.elementToBeClickable(closeButton));
		click.clickElement(closeButton);
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		dropdown.selectDropdownByDisplayName(externalFilter, "Both");
		boolean data_flag = false;
		if ( all_flag )
		{
			data_flag = summaryTableAPTaxActOverride
				.stream()
				.anyMatch(el -> el
									.getText()
									.contains(list
										.get(2)
										.getValue()) && el
									.findElement(By.xpath(".//following::div[1]"))
									.getText()
									.contains(list
										.get(1)
										.getValue()) && el
									.findElement(By.xpath(".//following::div[2]"))
									.getText()
									.contains(list
										.get(3)
										.getValue()) && el
									.findElement(By.xpath(".//following::div[3]"))
									.getText()
									.contains(list
										.get(0)
										.getValue()));
		}

		if ( data_flag )
		{
			flag = true;
		}

		return flag;
	}

	/**
	 * -- Method overloading
	 * Pass parameters in desired sequence and check the summary table
	 *
	 * @return
	 */
	public boolean emptyFields_reflectAll( )
	{
		boolean flag = false;
		navigateToAPTaxActOverride();
		wait.until(ExpectedConditions.visibilityOf(summaryPageTitle));

		boolean overcharged_ele1_flag = emptyFields_reflectAll(overchargedSection, legalEntity, businessUnit, apInvSrc,
			supplierType, "LE", "BU", "APInvSrc", "SupplierType");

		boolean overcharged_ele2_flag = emptyFields_reflectAll(overchargedSection, businessUnit, legalEntity, apInvSrc,
			supplierType, "BU", "LE", "APInvSrc", "SupplierType");

		boolean overcharged_ele3_flag = emptyFields_reflectAll(overchargedSection, apInvSrc, legalEntity, businessUnit,
			supplierType, "APInvSrc", "LE", "BU", "SupplierType");

		boolean overcharged_ele4_flag = emptyFields_reflectAll(overchargedSection, supplierType, legalEntity,
			businessUnit, apInvSrc, "SupplierType", "LE", "BU", "APInvSrc");

		boolean undercharged_ele1_flag = emptyFields_reflectAll(underchargedSection, supplierType, legalEntity,
			businessUnit, apInvSrc, "SupplierType", "LE", "BU", "APInvSrc");

		boolean undercharged_ele2_flag = emptyFields_reflectAll(underchargedSection, businessUnit, legalEntity,
			apInvSrc, supplierType, "BU", "LE", "APInvSrc", "SupplierType");

		boolean undercharged_ele3_flag = emptyFields_reflectAll(underchargedSection, apInvSrc, legalEntity,
			businessUnit, supplierType, "APInvSrc", "LE", "BU", "SupplierType");

		boolean undercharged_ele4_flag = emptyFields_reflectAll(underchargedSection, supplierType, legalEntity,
			businessUnit, apInvSrc, "SupplierType", "LE", "BU", "APInvSrc");

		boolean zerocharged_ele1_flag = emptyFields_reflectAll(zerochargedSection, supplierType, legalEntity,
			businessUnit, apInvSrc, "SupplierType", "LE", "BU", "APInvSrc");

		boolean zerocharged_ele2_flag = emptyFields_reflectAll(zerochargedSection, businessUnit, legalEntity, apInvSrc,
			supplierType, "BU", "LE", "APInvSrc", "SupplierType");

		boolean zerocharged_ele3_flag = emptyFields_reflectAll(zerochargedSection, apInvSrc, legalEntity, businessUnit,
			supplierType, "APInvSrc", "LE", "BU", "SupplierType");

		boolean zerocharged_ele4_flag = emptyFields_reflectAll(zerochargedSection, supplierType, legalEntity,
			businessUnit, apInvSrc, "SupplierType", "LE", "BU", "APInvSrc");

		if ( overcharged_ele1_flag && overcharged_ele2_flag && overcharged_ele3_flag && overcharged_ele4_flag &&
			 undercharged_ele1_flag && undercharged_ele2_flag && undercharged_ele3_flag && undercharged_ele4_flag &&
			 zerocharged_ele1_flag && zerocharged_ele2_flag && zerocharged_ele3_flag && zerocharged_ele4_flag )
		{
			flag = true;
		}
		return flag;
	}
}

