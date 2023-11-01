package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import com.vertex.quality.connectors.taxlink.common.utils.TaxLinkUIUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Optional;

/**
 * This class is for scheduling the PTDE job and searching the file name in OracleERP
 *
 * @author Shilpi.Verma
 */
public class OracleCloudOverviewPage extends OracleCloudBasePage
{
	TaxLinkUIUtilities utils = new TaxLinkUIUtilities();

	public OracleCloudOverviewPage( final WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//span[contains(text(), 'Schedule New Process')]")
	private WebElement schedulePrcButton;

	@FindBy(xpath = "//div[contains(text(), 'Schedule New Process')]")
	private WebElement schedulePrcText_PopUp;

	@FindBy(xpath = "//label[contains(text(), 'Name')]/following::td/.//following::input")
	private WebElement searchNameField;

	@FindBy(xpath = "//a[@title = 'Search: Name']")
	private WebElement searchNameDropdown;

	@FindBy(xpath = "//a[text()='Search...']")
	private WebElement searchPopUp;

	@FindBy(xpath = "//label[text() = ' Name']/preceding-sibling::input")
	private WebElement searchAndSelect;

	@FindBy(xpath = "//button[text() = 'Search']")
	private WebElement searchButton;

	@FindBy(xpath = "//td[contains(@id, 'lovDialogId')]/button[text() = 'OK']")
	private WebElement okButtonPrcSearch;

	@FindBy(xpath = "//label[contains(text(), 'Description')]/following::td/div/div[contains(text(), 'invoices') or contains(text(), 'transaction')]")
	private WebElement descrText;

	@FindBy(xpath = "//button[contains(text(), 'OK')][not(@_afrpdo)]")
	private WebElement okButton;

	@FindBy(xpath = "//div[contains(text(), 'Process Details')]")
	private WebElement prcDetailsText_PopUp;

	@FindBy(xpath = "(//label[contains(text(), 'From Invoice Date')]/following::td/input)[1]")
	private WebElement fromInvDate;

	@FindBy(xpath = "(//label[contains(text(), 'To Invoice Date')]/following::td/input)[1]")
	private WebElement toInvDate;

	@FindBy(xpath = "//span[contains(text(), 'Sub')]/parent::a")
	private WebElement process_submitBtn;

	@FindBy(xpath = "//span[contains(@id, 'confirmationPopup')]/label")
	private WebElement process_confirmPopUp;

	@FindBy(xpath = "//button[contains(text(), 'OK')][contains(@id, 'confirmationPopup')]")
	private WebElement process_okBtn_popUp;

	@FindBy(xpath = "//div[@title = 'Refresh']")
	private WebElement refreshButton;

	@FindBy(xpath = "//select[contains(@id, 'Attribute1')]")
	private WebElement applicationName;

	@FindBy(xpath = "//a[@title = 'Search: Business Unit']")
	private WebElement buSearch;

	@FindBy(xpath = "//div[contains(@id, 'BU_NAME::dropdownPopup')][@data-afr-popupid]")
	private WebElement buSearchPopUp;

	@FindBy(xpath = "//div[contains(@id, 'dropDownContent::sm')]/preceding-sibling::div/following::table/tbody/tr/td/span")
	private List<WebElement> buSearchList;

	@FindBy(xpath = "//input[contains(@id, 'Attribute3')][@placeholder='m/d/yy']")
	private WebElement fromTransDate;

	@FindBy(xpath = "//input[contains(@id, 'Attribute4')][@placeholder='m/d/yy']")
	private WebElement toTransDate;

	@FindBy(xpath = "//a[contains(@id, 'file_import_and_export')]")
	WebElement fileImportExportLink;

	@FindBy(xpath = "//a[contains(text(), 'Vertex_TransactionExtract')]")
	WebElement fileNameColumn;

	@FindBy(xpath = "//a[@title = 'Navigator']/node()")
	WebElement navigator;

	@FindBy(xpath = "//div[@title='Tools']")
	WebElement toolsMenu;

	@FindBy(xpath = "//a[contains(@id, 'scheduled_processes')]")
	WebElement scheduledProcessLink;

	@FindBy(xpath = "//table[contains(@summary, 'Processes')]/tbody/tr/td[2]/div/table/tbody/tr/td[1]/span/span")
	List<WebElement> column;

	By name = By.xpath(".//span");
	By childProcessStatus = By.xpath(".//following::td[2]/span");

	/**
	 * Method to pass index values in Xpath of the columns of process summary table
	 *
	 * @param index1
	 * @param index2
	 *
	 * @return
	 */
	public WebElement tableColumns( int index1, int index2 )
	{
		By ele = By.xpath(
			"(//table[contains(@summary, 'Processes')]/tbody/tr/td[2]/div/table/tbody/tr/td[" + index1 + "]/span)[" +
			index2 + "]");

		WebElement column = wait.waitForElementDisplayed(ele);

		return column;
	}

	/**
	 * Navigate to link
	 *
	 * @param ele
	 */
	public void navigate( WebElement ele )
	{
		click.clickElement(navigator);
		wait.waitForElementDisplayed(ele);
		click.clickElement(ele);
	}

	/**
	 * Method to search schedule
	 *
	 * @param scheduleName
	 */
	public void startSchedule( String scheduleName )
	{
		wait.waitForElementEnabled(schedulePrcButton);
		click.clickElement(schedulePrcButton);

		wait.waitForElementDisplayed(schedulePrcText_PopUp);
		click.clickElement(searchNameDropdown);
		jsWaiter.sleep(2000);
		executeJs("window.scrollTo(0, document.body.scrollHeight)");

		wait.waitForElementDisplayed(searchPopUp);
		click.clickElement(searchPopUp);
		wait.waitForElementDisplayed(searchAndSelect);
		text.enterText(searchAndSelect, scheduleName);
		click.clickElement(searchButton);

		By searchRes = By.xpath("//tr[not(@_afrrk)]/td/span[text() = '" + scheduleName + "']");
		WebElement searchResult = wait.waitForElementDisplayed(searchRes, 3000);

		if ( searchResult
			.getText()
			.equals(scheduleName) )
		{
			click.clickElement(searchResult);
			wait.waitForElementDisplayed(okButtonPrcSearch);
			click.clickElement(okButtonPrcSearch);
		}

		wait.waitForElementDisplayed(descrText);
		wait.waitForElementEnabled(okButton);
		jsWaiter.sleep(2000);
		click.clickElementCarefully(okButton);
	}

	/**
	 * Recursive function-----
	 * Method to get Transaction Data Extract
	 *
	 * @return
	 */
	public Optional<WebElement> getDataTransPrc( String scheduleName )
	{
		Optional<WebElement> ele = column
			.stream()
			.filter(mainPrc -> mainPrc
				.getText()
				.equals(scheduleName))
			.findFirst();

		if ( ele.isPresent() && ele
			.get()
			.findElement(childProcessStatus)
			.getText()
			.equals("Succeeded") )
		{
			VertexLogger.log(scheduleName + "is 'Succeeded'");
			return ele;
		}

		click.clickElement(refreshButton);
		jsWaiter.sleep(5000);

		return getDataTransPrc(scheduleName);
	}

	/**
	 * Method to create schedules
	 */
	public void scheduleProcess( )
	{
		navigate(toolsMenu);
		wait.waitForElementDisplayed(scheduledProcessLink);
		click.clickElement(scheduledProcessLink);
		wait.waitForElementDisplayed(schedulePrcButton);

		startSchedule("Validate Payables Invoices");
		wait.waitForElementDisplayed(prcDetailsText_PopUp);

		text.enterText(fromInvDate, utils.getCurrDate("M/dd/yy"));
		text.enterText(toInvDate, utils.getCurrDate("M/dd/yy"));
		click.clickElement(process_submitBtn);

		wait.waitForElementDisplayed(process_confirmPopUp);
		VertexLogger.log(process_confirmPopUp.getText());

		click.clickElement(process_okBtn_popUp);
		jsWaiter.sleep(3000);

		click.clickElement(refreshButton);
		jsWaiter.sleep(5000);

		if ( tableColumns(1, 1)
			.findElement(name)
			.getText()
			.equals("Validate Payables Invoices") )
		{
			VertexLogger.log(tableColumns(3, 1).getText());
		}

		jsWaiter.sleep(5000);

		getDataTransPrc("Validate Payables Invoices");

		startSchedule("Partner Transaction Data Extract");
		jsWaiter.sleep(2000);
		wait.waitForElementDisplayed(prcDetailsText_PopUp);
		jsWaiter.sleep(2000);
		dropdown.selectDropdownByDisplayName(applicationName, "Payables");

		click.clickElement(buSearch);
		wait.waitForElementDisplayed(buSearchPopUp);

		buSearchList
			.stream()
			.filter(bu -> bu
				.getText()
				.equals("VTX_US_BU"))
			.findFirst()
			.get()
			.click();

		jsWaiter.sleep(3000);

		text.enterText(fromTransDate, utils.getCurrDate("M/dd/yy"));
		text.enterText(toTransDate, utils.getCurrDate("M/dd/yy"));

		click.clickElement(process_submitBtn);
		wait.waitForElementDisplayed(process_confirmPopUp);
		VertexLogger.log(process_confirmPopUp.getText());

		click.clickElement(process_okBtn_popUp);
		jsWaiter.sleep(3000);

		click.clickElement(refreshButton);
		jsWaiter.sleep(5000);

		if ( tableColumns(1, 1)
			.findElement(name)
			.getText()
			.equals("Partner Transaction Data Extract") )
		{
			VertexLogger.log(tableColumns(3, 1).getText());
		}

		click.clickElement(refreshButton);
		jsWaiter.sleep(5000);

		getDataTransPrc("Partner Transaction Data Extract");

		boolean childPrc1 = column
			.stream()
			.anyMatch(cp1 -> cp1
								 .getText()
								 .equals("Partner Transaction Extract") && cp1
								 .findElement(childProcessStatus)
								 .getText()
								 .equals("Succeeded"));

		boolean childPrc2 = column
			.stream()
			.anyMatch(cp2 -> cp2
								 .getText()
								 .equals("Partner Manual Tax Extract") && cp2
								 .findElement(childProcessStatus)
								 .getText()
								 .equals("Succeeded"));

		if ( childPrc1 && childPrc2 )
		{
			VertexLogger.log("Partner Transaction Extract process is 'Succeeded'");
			VertexLogger.log("Partner Manual Tax Extract process is 'Succeeded'");
		}
		else
		{
			VertexLogger.log("Partner Transaction Extract process is not 'Succeeded'");
			VertexLogger.log("Partner Manual Tax Extract process is not 'Succeeded'");
		}
	}

	/**
	 * Method to search file name
	 *
	 * @return
	 */
	public String getFileName( )
	{
		navigate(fileImportExportLink);
		wait.waitForElementDisplayed(searchButton);

		click.clickElement(searchButton);
		wait.waitForElementDisplayed(fileNameColumn);

		String fileName = null;
		if ( fileNameColumn.isDisplayed() )
		{
			fileName = fileNameColumn.getText();
			VertexLogger.log(fileName);
		}
		else
		{
			VertexLogger.log("File is not present");
		}

		return fileName;
	}
}
