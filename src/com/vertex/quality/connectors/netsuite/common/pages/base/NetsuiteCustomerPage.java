package com.vertex.quality.connectors.netsuite.common.pages.base;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteAddressComponent;
import com.vertex.quality.connectors.netsuite.common.components.NetsuiteNavigationPane;
import com.vertex.quality.connectors.netsuite.common.components.NetsuiteTableComponent;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuitePageTitles;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteContactPopupPage;
import com.vertex.quality.connectors.netsuite.common.pages.customers.NetsuiteCustomerClassPopupPage;
import com.vertex.quality.connectors.netsuite.common.pages.customers.NetsuiteCustomerListPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.customers.NetsuiteAPICustomerPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.Date;

import static org.testng.Assert.assertTrue;

/**
 * Represents the parent customer page
 *
 * @author hho
 */
public abstract class NetsuiteCustomerPage extends NetsuitePage
{
	public NetsuiteNavigationPane navigationPane;
	protected NetsuiteAddressComponent addressComponent;
	protected NetsuiteTableComponent tableComponent;

	protected By editAddress = By.id("addressbookaddress_helper_popup");
	protected By childAddressForm = By.id("childdrecord_frame");
	protected By addressTable = By.id("addressbook_splits");
	protected By addressHeaderRow = By.id("addressbook_headerrow");
	protected By savedInformationFormContainer = By.id("main_form");
	protected By contactTable = By.id("contact_splits");
	protected By contactHeaderRow = By.id("contact_headerrow");
	protected By editContactTable = By.id("contact__tab");
	protected By editButton = By.id("edit");
	protected By saveButton = By.id("btn_secondarymultibutton_submitter");

	protected String addressHeader = "Address";
	protected String editHeader = "Edit";
	protected By vertexCustomerClassDropdown = By.name("inpt_custentity_customercode_vt");
	protected By newVertexCustomerClassButton = By.id("custentity_customercode_vt_popup_new");
	protected By cancelButton = By.id("_cancel");
	protected By actionsMenu = By.id("spn_ACTIONMENU_d1");
	protected By actionsSubmenu = By.id("div_ACTIONMENU_d1");
	protected By resetButton = By.id("resetter");
	protected By addressTabButton = By.id("addresstxt");
	protected By informationTabButton = By.id("generaltxt");
	protected By addAddressButton = By.xpath("//*[@id=\"addressbook_addedit\"]");
	protected By vertexCustomerIdTextField = By.id("custentity_externalid_vt");
	protected By companyNameTextField = By.id("companyname");
	protected By exemptionCertButton = By.id("newrecrecmachcustrecord_ec_customer_vt");
	protected By certificateNumTextField = By.id("custrecord_ec_number_vt");
	protected By certificateStartDateField = By.id("custrecord_ec_start_date_vt");
	protected By salesRepDropDown = By.name("inpt_salesrep");
	protected By contactsTabButton = By.id("contacttxt");
	protected By addContactButton = By.id("contact_addedit");
	protected By removeAddressButton = By.id("addressbook_remove");
	protected By vertexCustomerIdTextfield = By.id("custentity_externalid_vt");
	protected By autoNameCheckbox = By.id("autoname_fs_inp");
	protected By customerIdTextfield = By.cssSelector("#entityid_fs #entityid");
	protected By cancelButtonLocator = By.xpath("//input[@value='Cancel' and @type='button' and @name='_cancel']");

	public NetsuiteCustomerPage( final WebDriver driver )
	{
		super(driver);
		navigationPane = new NetsuiteNavigationPane(driver, this);
		addressComponent = new NetsuiteAddressComponent(driver, this);
		tableComponent = new NetsuiteTableComponent(driver, this);
	}

	/**
	 * Cancels the editing/creation of the current customer
	 *
	 * @return the customer list page
	 */
	public NetsuiteCustomerListPage cancel( )
	{
		click.clickElement(cancelButton);
		return initializePageObject(NetsuiteCustomerListPage.class);
	}

	/**
	 * Edits the customer
	 *
	 * @return the customer page
	 */
	public abstract <T extends NetsuiteCustomerPage> T edit( );

	/**
	 * Saves the customer
	 *
	 * @return the customer page
	 */
	public abstract <T extends NetsuiteCustomerPage> T save( );

	/**
	 * Deletes the current customer
	 *
	 * @return the customer list page
	 */
	public NetsuiteCustomerListPage delete( )
	{
		String delete = "Delete";

		WebElement deleteButton = getElementInHoverableMenu(actionsMenu, actionsSubmenu, delete);
		click.clickElement(deleteButton);

		alert.acceptAlert(DEFAULT_TIMEOUT);
		waitForPageLoad();
		return initializePageObject(NetsuiteCustomerListPage.class);
	}

	/**
	 * Resets the customer page
	 */
	public void reset( )
	{
		click.clickElement(resetButton);
	}

	/**
	 * Opens the address tab
	 */
	public void selectAddressTab( )
	{
		click.clickElement(addressTabButton);
	}

	/**
	 * Opens the information tab
	 */
	public void selectInformationTab( )
	{
		click.clickElement(informationTabButton);
	}

	/**
	 * Creates a new address
	 *
	 * @param address the address
	 */
	public void createNewAddress( NetsuiteAddress address )
	{
		WebElement focusedTableRow = tableComponent.getFocusedTableRow(addressTable);
		WebElement editAddressButton = wait.waitForElementDisplayed(editAddress, focusedTableRow);

		click.clickElement(editAddressButton);
		window.waitForAndSwitchToFrame(childAddressForm);

		addressComponent.saveAddress(address);
	}

	/**
	 * Edits a saved address
	 *
	 * @param oldAddressLabel the old address label
	 * @param newAddress      the new address
	 */
	public void editSavedAddress( String oldAddressLabel, NetsuiteAddress newAddress )
	{
		WebElement tableCell = tableComponent.getTableCellByIdentifier(addressTable, addressHeaderRow, oldAddressLabel,
			editHeader);
		WebElement editAddressButton = wait.waitForElementDisplayed(editAddress, tableCell);

		click.clickElement(editAddressButton);
		window.waitForAndSwitchToFrame(childAddressForm);

		addressComponent.saveAddress(newAddress);
	}

	/**
	 * Checks if the address can be verified
	 *
	 * @return if the address can be verified
	 */
	public boolean isAddressVerified( )
	{
		return addressComponent.isAddressVerified();
	}

	/**
	 * Cancels address edit
	 */
	public void cancelAddressEdit( )
	{
		Actions actions = new Actions(driver);
		hover.hoverOverElement(cancelButtonLocator);
		actions.moveToElement(element.getWebElement(cancelButtonLocator)).click().build().perform();
	}

	/**
	 * Fills out the currently open address form
	 *
	 * @param address the address to use
	 */
	public void editOpenAddressForm( NetsuiteAddress address )
	{
		addressComponent.saveAddress(address);
	}

	/**
	 * Adds the address
	 */
	public void addAddress( )
	{
		jsWaiter.sleep(1000);
		click.clickElement(addAddressButton);
	}

	/**
	 * Removes an address from the customer
	 *
	 * @param addressLabel the address to remove
	 */
	public void removeAddress( String addressLabel )
	{
		WebElement tableCell = tableComponent.getTableCellByIdentifier(addressTable, addressHeaderRow, addressLabel,
			editHeader);
		tableComponent.focusOnInteractableTableCell(tableCell);
		click.clickElement(removeAddressButton);
	}

	/**
	 * Checks if the address was saved correctly and is displayed
	 *
	 * @param address the address to check
	 *
	 * @return if the address was saved correctly and is displayed
	 */
	public boolean isAddressDisplayed( NetsuiteAddress address )
	{
		String cleansedAddress = address.getCleansedAddress();

		WebElement tableCell = null;

		tableCell = tableComponent.getTableCellByIdentifier(addressTable, addressHeaderRow, cleansedAddress,
			editHeader);

		return tableCell != null;
	}

	/**
	 * Inputs a customer id into the customer id text field
	 *
	 * @param vertexCustomerId the customer id to input
	 */
	public void enterVertexCustomerId( String vertexCustomerId )
	{
		text.enterText(vertexCustomerIdTextField, vertexCustomerId);
	}

	/**
	 * Checks if the Vertex customer id field is auto populated
	 */
	public Boolean isVertexCustomerIdAutoPopulated( )
	{
		String elementText = text.getElementText(vertexCustomerIdTextField);
		if(elementText != null)
			return true;
		else
			return false;
	}

	/**
	 * Inputs the company name into the text field
	 *
	 * @param companyName the company name
	 */
	public void enterCompanyName( String companyName )
	{
		text.enterText(companyNameTextField, companyName);
	}

	/**
	 * Create new Exemption Certificate
	 *
	 * @param certNum the Certificate number
	 * @param startDate the Start date for the certificate
	 */
	public void createExemptionCertificate( String certNum, String startDate )
	{
		click.clickElement(exemptionCertButton);
		text.enterText(certificateNumTextField, certNum);
		text.enterText(certificateStartDateField, startDate);
		click.clickElement(saveButton);
	}

	/**
	 * Selects sales rep in the drop down
	 *
	 * @param salesRep the sales rep to select
	 */
	public void selectSalesRep( String salesRep )
	{
		setDropdownToValue(salesRepDropDown, salesRep);
	}

	/**
	 * Opens the contacts tab inside the Information tab
	 */
	public void selectInformationContactsTab( )
	{
		selectInformationTab();
		click.clickElement(contactsTabButton);
	}

	/**
	 * Enters the contact name in the contacts table
	 *
	 * @param contactName the contact name
	 */
	public void enterContactName( String contactName )
	{
		String contactHeader = "Contact";
		selectInformationContactsTab();
		WebElement contactTableCell = tableComponent.getFocusedTableCell(contactTable, contactHeaderRow, contactHeader);
		tableComponent.focusOnInteractableTableCell(contactTableCell);
		WebElement contactTextField = contactTableCell.findElement(By.tagName("input"));
		text.enterText(contactTextField, contactName);
	}

	/**
	 * Adds the currently edited contact
	 */
	public void addContact( )
	{
		click.clickElement(addContactButton);
	}

	/**
	 * Edits the given contact name
	 *
	 * @param contactName the contact name
	 *
	 * @return the contact page
	 */
	public NetsuiteContactPopupPage editContact( String contactName )
	{
		String currentWindowHandle = driver.getWindowHandle();
		WebElement editedContactTableRow = tableComponent.getTableRowByIdentifier(editContactTable, contactName);
		WebElement editContactTableCell = tableComponent.getTableCellByHeaderIndex(editedContactTableRow, 0);
		WebElement editButton = tableComponent.findCellButton(editContactTableCell, editHeader);
		click.clickElement(editButton);
		NetsuitePageTitles contactPageTitle = NetsuitePageTitles.CONTACT_PAGE;
		NetsuiteContactPopupPage contactPopupPage = new NetsuiteContactPopupPage(driver, currentWindowHandle);
		window.switchToWindowTextInTitle(contactPageTitle.getPageTitle());
		return contactPopupPage;
	}

	/**
	 * Checks if a Vertex customer class is available
	 *
	 * @param customerClassName the customer class name
	 *
	 * @return if a Vertex customer class is available
	 */
	public boolean isVertexCustomerClassAvailable( String customerClassName )
	{
		return isDropdownValueInList(vertexCustomerClassDropdown, customerClassName);
	}

	/**
	 * Creates a new Vertex customer class and selects it
	 *
	 * @param className the class name
	 */
	public void createNewVertexCustomerClass( String className )
	{
		String currentWindowHandle = driver.getWindowHandle();
		hover.hoverOverElement(newVertexCustomerClassButton);
		click.clickElement(newVertexCustomerClassButton);
		NetsuitePageTitles vertexCustomerClassPageTitle = NetsuitePageTitles.VERTEX_CUSTOMER_CLASS_PAGE;
		NetsuiteCustomerClassPopupPage classPopupPage = new NetsuiteCustomerClassPopupPage(driver, currentWindowHandle);
		window.switchToWindowTextInTitle(vertexCustomerClassPageTitle.getPageTitle());
		classPopupPage.enterClassName(className);
		classPopupPage.save();
	}

	/**
	 * Checks if the Vertex Customer Class field is displayed
	 *
	 * @return if the Vertex Customer Class field is displayed
	 */
	public boolean isVertexCustomerClassFieldAvailable( )
	{
		return element.isElementDisplayed(vertexCustomerClassDropdown);
	}

	/**
	 * Checks if the Vertex Customer Class field is enabled
	 *
	 * @return if the Vertex Customer Class field is enabled
	 */
	public boolean isVertexCustomerClassFieldEnabled( )
	{
		return element.isElementEnabled(vertexCustomerClassDropdown);
	}

	/**
	 * Checks if the Vertex Customer Id field is displayed
	 *
	 * @return if the Vertex Customer Id field is displayed
	 */
	public boolean isVertexCustomerIdFieldAvailable( )
	{
		return element.isElementDisplayed(vertexCustomerIdTextfield);
	}

	/**
	 * Uncheck the "AUTO" checkbox so that the Customer ID field isn't auto-populated
	 */
	public void uncheckAutoNameCheckbox( )
	{
		if ( checkbox.isCheckboxChecked(autoNameCheckbox) )
		{
			click.javascriptClick(autoNameCheckbox);
			waitForPageLoad();
		}
	}

	/**
	 * Enters the customer id
	 *
	 * @param customerId the customer id
	 */
	public void enterCustomerId( String customerId )
	{
		text.enterText(customerIdTextfield, customerId);
	}
}
