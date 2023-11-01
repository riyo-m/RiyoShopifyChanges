package com.vertex.quality.connectors.netsuite.suiteTax.pages.customers;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteCustomerPage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteTransactions;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPIInvoicePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.testng.Assert.assertTrue;

/**
 * Represents the customer page for Netsuite SuiteTax API
 *
 * @author RAVUNURI
 */
public class NetsuiteAPICustomerPage extends NetsuiteCustomerPage
{
	protected By salesRepDropdownLoc = By.name("inpt_salesrep");
	protected By subsidiaryDropdownLoc = By.id("inpt_subsidiary5");
	protected By subsidiaryInContactsDropdownLoc = By.id("inpt_subsidiary14");
	protected By searchTextBoxLocator = By.xpath("//input[@id='_searchstring']");
	protected By searchResultListLocator = By.xpath("//span[normalize-space()='Exemption Customer']");
	protected By exemptionCertStatuslocator = By.xpath("//*[@id=\"recmachcustrecord_ec_customer_vtrow0\"]/td[7]");
	protected By exemptionCertIdLocator = By.xpath("//*[@id=\"recmachcustrecord_ec_customer_vtrow0\"]/./..//tr[last()]//td[2]");

	protected String subsidiaryHeader = "Subsidiary";

	public NetsuiteAPICustomerPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Edits the current customer
	 *
	 * @return the customer page
	 */
	public NetsuiteAPICustomerPage edit( )
	{
		click.clickElement(editButton);
		return initializePageObject(NetsuiteAPICustomerPage.class);
	}

	/**
	 * Saves the current customer
	 *
	 * @return the customer page
	 */
	public NetsuiteAPICustomerPage save( )
	{
		click.clickElement(saveButton);
		return initializePageObject(NetsuiteAPICustomerPage.class);
	}

	/**
	 * Sets the sales rep dropdown to the value
	 *
	 * @param salesRep the sales rep to select
	 */
	public void selectSalesRep( String salesRep )
	{
		selectInformationTab();
		setDropdownToValue(salesRepDropdownLoc, salesRep);
	}

	/**
	 * Set the subsidiary dropdown to the value
	 *
	 * @param subsidiary the subsidiary to select
	 */
	public void selectSubsidiary( String subsidiary )
	{
		setDropdownToValue(subsidiaryDropdownLoc, subsidiary);
	}

	/**
	 * Selects the subsidiary in the contacts table in the contacts tab
	 *
	 * @param subsidiary the subsidiary to select
	 */
	public void selectSubsidiaryInContactsTable( String subsidiary )
	{
		selectInformationContactsTab();
		WebElement subsidiaryTableCell = tableComponent.getFocusedTableCell(contactTable, contactHeaderRow,
			subsidiaryHeader);
		tableComponent.focusOnInteractableTableCell(subsidiaryTableCell);
		setDropdownToValue(subsidiaryInContactsDropdownLoc, subsidiary);
		waitForPageLoad();
	}

	public <T extends NetsuiteTransactions> T searchCustomer(String searchText)
	{
		click.clickElement(searchTextBoxLocator);
		text.enterText(searchTextBoxLocator, searchText);
		click.clickElement(searchResultListLocator);
		waitForPageLoad();
		return initializePageObject(NetsuiteAPIInvoicePage.class);
	}

	public <T extends NetsuiteTransactions> T searchForRecord(String searchText)
	{
		//Element you want to select in Search Bar drop down
		By searchLocator = By.xpath("//span[normalize-space()='"+searchText+"']" );
		//Search for Record using Search Bar
		click.clickElement(searchTextBoxLocator);
		text.enterText(searchTextBoxLocator, searchText);

		click.clickElement(searchLocator);
		waitForPageLoad();
		return initializePageObject(NetsuiteAPIInvoicePage.class);
	}

	/**
	 * Create a New Netsuite Customer
	 *
	 */
	public void createNewCustomer( String customerId, String subsidiary, NetsuiteAddress validAddress )
	{
		enterCompanyName(customerId);
		selectSubsidiary(subsidiary);
		createNewAddress(validAddress);
		assertTrue(isAddressVerified());
		addAddress();
		assertTrue(isAddressDisplayed(validAddress));
		save();
		edit();
		//Validate that the Vertex customer ID auto-populates, if left blank
		assertTrue(isVertexCustomerIdAutoPopulated());
	}

	/**
	 * Gets the vertex exemption Certificate result
	 *
	 * @return exemption Certificate webservice call result
	 */
	public String getExemptionCertificateResult( )
	{
		WebElement certificate_result = element.getWebElement(exemptionCertStatuslocator);
		return certificate_result.getText();
	}

	/**
	 * Gets the vertex exemption Certificate Id
	 * @return
	 */
	public String getExemptionCertId( )
	{
		WebElement IdElement = element.getWebElement(exemptionCertIdLocator);
		return IdElement.getText();
	}

	/**
	 * Delete Exemption Certificate
	 * @param Id
	 */
	public void deleteCert(String Id)
	{
		String baseUrl = ".app.netsuite.com";
		String endOfUrl = "/app/common/custom/custrecordentry.nl?rectype=514&id=" + Id;
		String userUrl = driver.getCurrentUrl();
		userUrl = userUrl.split(baseUrl)[0]+ baseUrl + endOfUrl;
		driver.get(userUrl);

		editRecord();
		deleteRecord();
	}

}
