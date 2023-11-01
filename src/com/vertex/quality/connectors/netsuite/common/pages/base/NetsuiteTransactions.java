package com.vertex.quality.connectors.netsuite.common.pages.base;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.selenium.VertexAlertUtilities;
import com.vertex.quality.connectors.netsuite.common.components.NetsuiteNavigationPane;
import com.vertex.quality.connectors.netsuite.common.components.NetsuiteTableComponent;
import com.vertex.quality.connectors.netsuite.common.components.NetsuiteTransactionComponent;
import com.vertex.quality.connectors.netsuite.common.enums.*;
import com.vertex.quality.connectors.netsuite.common.interfaces.NetsuiteBasicOrder;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteAddressPopupPage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteVertexCallDetailsPage;
import com.vertex.quality.connectors.netsuite.common.pages.transactions.NetsuiteTransactionsPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteExpense;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCInvoicePage;
import net.minidev.json.JSONUtil;
import org.apache.commons.compress.compressors.zstandard.ZstdCompressorOutputStream;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.pagefactory.ByChained;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Parent of all Netsuite transaction pages
 *
 * @author hho, jyareeda, ravunuri
 */
public abstract class NetsuiteTransactions extends NetsuitePage implements NetsuiteBasicOrder
{
	public NetsuiteNavigationPane navigationPane;
	public VertexAlertUtilities alert;

	protected NetsuiteTransactionComponent transactionComponent;
	protected NetsuiteTableComponent tableComponent;

	protected By editLocator = By.id("edit");
	protected By billLocator = By.id("tdbody_bill");
	protected By paymentLocator = By.id("payment");
	protected By accessBillLocator = By.id("apply_displayval");
	protected By deleteLocator = By.id("delete");
	protected By creditLocator = By.id("credit");
	protected By deleteMemoLocator = By.xpath("//a[@href=\"javascript:NLInvokeButton(getButton('delete'))\"]");
	protected By actionMenuDropdown = By.xpath("//span[@id='spn_ACTIONMENU_d1']");
	protected By deleteAction = By.xpath("//span[text()='Delete']");
	protected By calculateShippingCostLocator = By.xpath("//table[@class='totallingtable']//a[text()='Calculate']");
	protected By enterShippingCostLocator = By.xpath("//*[@id=\"shippingcost_formattedValue\"]");
	protected By taxOverrideCheckboxLocator = By.xpath("//*[@id=\"taxdetailsoverride_fs\"]");
	protected By taxAmtTextboxLocator = By.xpath("//span[@id='taxdetails_taxamount_fs']//input[@id='taxamount_formattedValue']");
	protected By taxRateTextboxLocator = By.xpath("//input[@id='taxrate_formattedValue']");
	protected By taxRateLocator = By.xpath("//*[@id=\"taxdetails_row_1\"]/td[9]");
	protected By taxAmtLocator = By.xpath("//*[@id=\"taxdetails_row_1\"]/td[10]");
	protected By fulfillLocator = By.id("process");
	protected By transactionNumberLocator = By.id("tranid");
	protected By authorizeReturnLocator = By.id("return");
	protected By refundLocator = By.id("refund");

	//Multi-Button Submitter Options
	protected String saveLocatorId = "btn_multibutton_submitter";
	protected By saveLocator = By.id(saveLocatorId);
	protected By multiButtonDropDown = By.xpath("//*[@id='"+saveLocatorId+"']/parent::*/following-sibling::td[1]");
	protected By saveButtonDropDownLocator = By.className("multiBntTri");
	protected By saveNBillLocator = By.xpath("//*[@id='div_multibutton_submitter']//span[contains( text(),'Save & Bill' )]");

	protected By searchFieldLocator = By.id("st");
	protected By searchButtonLocator = By.id("Search");

	protected By itemTable = By.xpath("//table[@id='item_splits']/tbody");
	protected By itemHeaderRow = By.xpath("//table[@id='item_splits']/tbody/tr[@class='uir-machine-headerrow']");
	protected By addEditItemButton = By.xpath("//*[@id=\"item_addedit\"]");
	protected By addExpenseButton = By.xpath("//*[@id=\"expense_addedit\"]");
	protected By displayAddressField = By.id("shipaddress");

	protected By itemDropdownLocator = By.id("parent_actionbuttons_item_item_fs");
	protected By expenseCategoryDropdownLocator = By.xpath("//input[@id='inpt_category5']");
	protected By itemListLocator = By.id("item_popup_list");
	protected By itemAmountLocator = By.id("amount_formattedValue");
	protected By itemQuantityLocator = By.id("quantity_formattedValue");
	protected By itemLocationLocator = By.xpath("//table[@id='item_splits']//input[@name='inpt_location']");
	protected By itemUnrollLabelLocator = By.xpath("//div[@id='item_pane_hd']//a[@class='uir-tab-unroll-label']");
	protected By productSubsidiaryDropdownLocator = By.name("inpt_subsidiary");
	protected By couponCodeDisplayLocator = By.id("couponcode_display");
	protected By transDateLocator = By.id("trandate");
	protected By salesEffDateLocator = By.id("saleseffectivedate");
	protected By exportTypeLocator = By.id("custbody_export_type");
	protected By promotionDropdownLocator = By.name("inpt_promocode");
	protected By taxCodeDropdownLocator = By.id("parent_actionbuttons_item_taxcode_fs");
	protected By taxCodeLocator = By.id("item_taxcode_fs");
	protected By taxCodeListLocator = By.id("taxcode_popup_list");

	protected String itemHeader = "Item";
	protected String itemQuantityHeader = "Qty";
	protected String itemPOQuantityHeader = "Quantity";
	protected String itemAmountHeader = "Amount";
	protected String taxCodeHeader = "Tax Code";
	protected String locationHeader = "Location";
	protected String itemClassHeader = "Class";
	protected By itemClassLocator = By.name("inpt_class");
	protected By expandAllTabsLocator = By.xpath("//div[@id='items_pane_hd']/span/a[text()='Expand All']");
	protected By confirmationBannerLocator = By.xpath("//div[@id='div__alert']//div[text()='Confirmation']");

	protected String summarySubtotalHeader = "SUBTOTAL";
	protected String summaryTotalHeader = "TOTAL";
	protected By taxErrorCodeParentContainerLocator = By.className("uir-long-text");
	protected By taxNexusParentContainerLocator = By.xpath("//*[@id=\"taxes_form\"]/table/tbody/tr/td/table/" +
			"tbody/tr/td[1]/table/tbody/tr[1]/td/div");
	protected By taxCodeDropdownLocatorAlt = By.name("inpt_taxcode");
	protected By currencyDropdownLocator = By.xpath("//*[@id=\"inpt_currency38\"]");
	protected By locationDropdownLocator = By.name("inpt_location");
	protected By customerDropdownLocator = By.id("parent_actionbuttons_entity_fs");
	protected By employeeDropdownLocator = By.xpath("//input[@id='inpt_entity1']");
	protected By customerListLocator = By.id("entity_popup_list");
	protected By vendorLocator = By.id("entity_display");
	protected By memoitemTabLocator = By.id("itemslnk");
	protected By itemTabLocator = By.id("items_pane_hd");
	protected By itemPaneLocator = By.xpath("//div[@id='items_pane_hd']/a");
	protected By itemSectionLocator = By.id("itemstxt");
	protected By newShipToAddressButtonLocator = By.id("shipaddresslist_popup_new");
	protected By existingShipToAddressDropDown = By.name("inpt_shipaddresslist");
	protected NetsuiteAddressPopupPage addressPage;
	protected String customAddressText = "- Custom -";
	protected String newAddressText = "- New -";
	protected String itemShipToHeader = "Ship To";
	protected By itemShipToLocator = By.name("inpt_shipaddress");
	protected By itemShipToEditLocator = By.id("shipaddress_popup_link");
	protected By vertexCallDetailsTabLocator = By.id("custom298_pane_hd");
	protected By vertexCallDetailsTableLocator = By.id("recmachcustrecord_trans_vt__tab");
	protected By vertexCallDetailsTableHeaderLocator = By.className("uir-list-headerrow");
	protected By taxPreviewButtonLocator = By.xpath("//input[contains(@id,'preview_tax') and @value='Preview Tax'][1]");
	protected By previewTaxButtonLocator = By.xpath("//input[@id='calculatetax']");
	protected By searchTextBoxLocator = By.xpath("//input[@id='_searchstring']");
	protected By searchResultListLocator = By.xpath("//*[@id=\"/app/common/search/searchredirect.nl?id=734\"]/span");
	protected By expenseMarkAllLocator = By.xpath("//input[@onclick='expcostMarkAll(true);return false; return false;']");
	protected By taxPreviewResultLocator = By.id("custbody_preview_data_vt_val");
	protected By vertexPostToVertex = By.id("custbody_posttax_vt_fs_inp");
	protected By distributeTax = By.id("custbody_distributetax_vt_fs_inp");
	protected By distributeTaxChecked = By.xpath("//span[@id='custbody_distributetax_vt_fs']//img[@alt='Checked']");
	protected By taxPreviewButtonSummaryLocator = By.xpath("//input[contains(@id,'preview_tax_summary') and @value='Preview Tax Summary'][1]");
	protected By taxDetailDropDown = By.id("taxdetails_taxdetailsreference_fs");
	protected By getTaxDetailTaxCode = By.id("parent_actionbuttons_taxdetails_taxcode_fs");
	protected By taxDetailsOverride = By.id("taxdetailsoverride_fs_inp");
	protected By taxAmountLocator = By.xpath("//span[@id='custbody_distributed_tax_vt_fs_lbl_uir_label']/parent::div/span[2]");
	protected By hdrRow = By.xpath("//tr[@id='item_headerrow']/td");
	protected By dropdownLoc = By.xpath("//input[contains(@id,'inpt_location') and @name='inpt_location']");
	protected final By taxDetailAddBtn = By.id("taxdetails_addedit");
	protected String taxErrorCodeLoc = "custbody_taxerrorcode_vt_fs_lbl_uir_label";
	protected String taxResultLocator = "custbody_tax_result_vt_fs_lbl_uir_label";
	protected final By taxDetailsLoc = By.xpath("//table[@id='taxdetails_splits']");
	protected final By vendorBillItemDetailsLoc = By.id("item_splits");
	protected final By vertexCutTaxActionLoc = By.xpath("//td[3]/table/tbody/tr[10]");
	protected final By vertexAssociatedTranstexLoc = By.xpath("//tr[3]//table//tr/td[1]/table/tbody/tr[1]");
	protected final By taxDetailsRowLoc = By.xpath("//table[@id='taxdetails_splits']//tr[2]");
	protected final By totallingSummaryTableLoc = By.xpath("//table[@class='totallingtable']");
	protected final By vertexLogDetailsLoc = By.xpath("//*[@id=\"div__bodytab\"]/tbody");
	protected final By callDetailsRowLoc = By.xpath("//*[@id=\"recmachcustrecord_trans_vt__tab\"]/tbody/tr");
	protected final By taxDetailsTableLoc = By.xpath("//table[@id='taxdetails_splits']");
	protected String taxNexusLabelLoc = "nexus_fs_lbl_uir_label";

	public NetsuiteTransactions( final WebDriver driver )
	{
		super(driver);
		navigationPane = new NetsuiteNavigationPane(driver, this);
		transactionComponent = new NetsuiteTransactionComponent(driver, this);
		tableComponent = new NetsuiteTableComponent(driver, this);
	}

	/**
	 * Gets the transaction number
	 *
	 * @return the transaction number
	 */
	public String getTransactionNumber( )
	{
		return attribute.getElementAttribute(transactionNumberLocator, "value");
	}

	/**
	 * Selects a customer in the customer dropdown
	 *
	 * @param customer the customer
	 */
	public void selectCustomer( NetsuiteCustomer customer )
	{
		transactionComponent.selectCustomer(customerDropdownLocator, customerListLocator, searchFieldLocator,
			searchButtonLocator, customer.getCustomerName());
	}

	/**
	 * Selects an employee in the employee dropdown
	 *
	 * @param employee the employee
	 */
	public void selectEmployee(NetsuiteEmployee employee )
	{
		transactionComponent.selectEmployee(employeeDropdownLocator, employee.getEmployeeName());
	}

	/**
	 * Expands all tabs on the current transaction page
	 */
	public void expandTabs( )
	{
		// If Items panel isn't already expanded...
		if(element.getWebElement(itemPaneLocator).getAttribute("aria-expanded").equals("false")) {
			// Get Expandable Item Section Header
			Actions actions = new Actions(driver);
			hover.hoverOverElement(expandAllTabsLocator);
			actions.moveToElement(element.getWebElement(expandAllTabsLocator)).click().build().perform();
		}
	}

	/**
	 * Opens the item tab on the current transaction page
	 */
	public void selectItemsTab(By... locator)
	{
		By itemLocator = itemTabLocator;
		if(locator.length != 0){
			itemLocator = locator[0];
		}
		click.clickElement(itemLocator);
	}

	public void selectMemoItemsTab(By... locator)
	{
		By itemLocator = memoitemTabLocator;
		if(locator.length != 0){
			itemLocator = locator[0];
		}
		click.clickElement(itemLocator);
	}

	public void getItemSection()
	{
		if(element.isElementPresent(expandAllTabsLocator))
		{
			expandTabs();
			return;
		}
			selectItemsTab(itemSectionLocator);
	}

	/**
	 * Adds the item to the current transaction
	 *
	 * @param item the Netsuite item
	 */
	public void addItemToTransaction( NetsuiteItem item )
	{
		transactionComponent.addItemToTransaction(itemDropdownLocator, itemListLocator, searchFieldLocator,
			searchButtonLocator, item);
		this.alert = new VertexAlertUtilities(this, driver);

		enterItemQuantity(item);
		enterItemAmount(item);
		selectTaxCode(item, item.getTaxCode());
		if(item.getLocation() != null)
		{
			setItemLocation(item);
		}
		jsWaiter.sleep(150);
		click.clickElement(addEditItemButton);
	}

	/**
	 * Adds Multiple items to the current transaction
	 *
	 * @param item the Netsuite item
	 * @param index the index of the item in its item list
	 */
	public void addItemToTransaction( NetsuiteItem item, int index )
	{
		transactionComponent.addItemToTransaction(itemDropdownLocator, itemListLocator, searchFieldLocator,
				searchButtonLocator, item, index);

		this.alert = new VertexAlertUtilities(this, driver);

		enterItemQuantity(item);
		enterItemAmount(item);
		selectTaxCode(item, item.getTaxCode());
		enterItemClass(item);

		if(!item.getLocation().equals(""))
		{
			setItemLocation(item);
		}
		jsWaiter.sleep(250);
		click.clickElement(addEditItemButton);
	}

	/**
	 * Adds Multiple items to the current transaction from item drop down list
	 *
	 * @param item the Netsuite item
	 * @param index the index of the item in its item list
	 */
	public void addDropDownItemToTransaction( NetsuiteItem item, int index )
	{
		transactionComponent.addItemToTransaction(itemDropdownLocator, itemListLocator, searchFieldLocator,
				searchButtonLocator, item, index);

		this.alert = new VertexAlertUtilities(this, driver);

		enterItemQuantity(item);
		enterItemAmount(item);
		selectTaxCodeByDropdown(item, item.getTaxCode());
		if(item.getLocation() != null)
		{
			setItemLocation(item);
		}
		jsWaiter.sleep(250);
		click.clickElement(addEditItemButton);
	}

	/**
	 * Adds Multiple expenses to the current transaction from expenseCategory drop down list
	 *
	 * @param expense the Netsuite expense
	 * @param index the index of the category in its category list
	 */
	public void addDropDownExpenseCategory(NetsuiteExpense expense, int index, String customer )
	{
		transactionComponent.addExpensesToTransaction(expenseCategoryDropdownLocator, expense, index, customer);

		this.alert = new VertexAlertUtilities(this, driver);

		jsWaiter.sleep(500);
		click.clickElement(addExpenseButton);
	}

	/**
	 * Adds Multiple items to the Purchase Order transaction
	 *
	 * @param item the Netsuite item
	 * @param index the index of the item in its item list
	 */
	public void addItemToPurchaseOrder( NetsuiteItem item, int index )
	{
		// Click on "Unrolled item label" to change the display (un-hide) Item drop down section
		boolean isItemDropDownSection_Visible = element.isElementDisplayed(itemDropdownLocator);
		if (!isItemDropDownSection_Visible)
		{
			click.clickElement(itemUnrollLabelLocator);
		}

		transactionComponent.addItemToTransaction(itemDropdownLocator, itemListLocator, searchFieldLocator,
				searchButtonLocator, item, index);

		this.alert = new VertexAlertUtilities(this, driver);

		enterPurchaseOrderItemQuantity(item);
		enterItemAmount(item);
		selectTaxCode(item, item.getTaxCode());
		if(item.getLocation() != null)
		{
			setItemLocation(item);
		}
		jsWaiter.sleep(250);
		click.clickElement(addEditItemButton);
	}

	/**
	 * Adds Tax Detail to the current transaction
	 *
	 * @param item the Netsuite item
	 */
	public void addTaxDetailToTransaction( NetsuiteItem item, int index )
	{
		transactionComponent.addTaxDetailToTransaction(item, index);
		this.alert = new VertexAlertUtilities(this, driver);
		click.clickElement(taxDetailAddBtn);
	}

	/**
	 * Selects a vendor in the vendor dropdown
	 *
	 * @param vendor the vendor
	 */
	public void selectVendor( NetsuiteVendor vendor )
	{
		transactionComponent.selectVendor(vendorLocator, vendor.getVendorName());
	}


	/**
	 * Select the location's subsidiary
	 *
	 * @param subsidiary the subsidiary
	 */
	public void selectSubsidiary(String subsidiary )
	{
		setDropdownToValue(productSubsidiaryDropdownLocator, subsidiary);
	}

	/**
	 * Edits a current item
	 *
	 * @param currentItem the current item
	 * @param newItem     the new item
	 */
	public void editItem( NetsuiteItem currentItem, NetsuiteItem newItem )
	{
		WebElement itemCell = tableComponent.getInteractableTableCell(itemTable, itemHeaderRow, currentItem
			.getNetsuiteItemName()
			.getItemName(), itemHeader);
		tableComponent.focusOnInteractableTableCell(itemCell);
		addItemToTransaction(newItem);
	}

	/**
	 * Enters the item quantity for an item
	 *
	 * @param item the Netsuite item
	 */
	public void enterItemQuantity( NetsuiteItem item )
	{
		// Check if item has a quantity before setting
		if(item.getQuantity() != null && !item.getQuantity().equals("1") ) {
			String quantity = item.getQuantity();
			String quantityHeader = getItemQuantityHeader();
			WebElement quantityTextbox = getItemTableCellElementByLocator(item, quantityHeader, itemQuantityLocator);
			forceClear(quantityTextbox);
			text.enterText(quantityTextbox, quantity);
		}
	}

	protected String getItemQuantityHeader( )
	{
		return itemQuantityHeader;
	}

	/**
	 * Enters the item quantity for a Purchase Order item
	 *
	 * @param item the Netsuite item
	 */
	public void enterPurchaseOrderItemQuantity( NetsuiteItem item )
	{
		// Check if item has a quantity before setting
		if(item.getQuantity() != null) {
			String quantity = item.getQuantity();
			String quantityHeader = getItemPurchaseOrderQtyHeader();
			WebElement quantityTextbox = getItemTableCellElementByLocator(item, quantityHeader, itemQuantityLocator);
			forceClear(quantityTextbox);
			text.enterText(quantityTextbox, quantity);
		}
	}

	protected String getItemPurchaseOrderQtyHeader( )
	{
		return itemPOQuantityHeader;
	}

	/**
	 * Enters the item amount for an item
	 *
	 * @param item the Netsuite item
	 */
	public void enterItemAmount( NetsuiteItem item )
	{
		if(item.getAmount() != null) {
			String amount = item.getAmount();
			String amountHeader = getItemAmountHeader();
			WebElement amountTextbox = getItemTableCellElementByLocator(item, amountHeader, itemAmountLocator);
			forceClear(amountTextbox);
			text.enterText(amountTextbox, amount);
		}
	}

	protected String getItemAmountHeader( ) { return itemAmountHeader; }

	/**
	 * Selects Tax Code from dropdown by Box Expansion
	 *
	 * @param taxCode the Netsuite TaxCode By Box Expansion
	 */
	public void selectTaxCode(NetsuiteItem item, String taxCode)
	{
		if(item.getTaxCode() == null && taxCode == null) {
			return;
		}
			Actions actor = new Actions((driver));
			// Expand item search box before attempting to search for item
			WebElement itemNameInput = getItemTableCellElementByLocator(item,taxCodeHeader, taxCodeLocator);

			WebElement altDropDown = element.getWebElement(taxCodeDropdownLocator);


			// Wait for box expansion to finish before proceeding
			if(altDropDown != null) {
				actor.moveToElement(itemNameInput)
						.click()
						.perform();
				wait.waitForAllElementsDisplayed(taxCodeDropdownLocator, 15);
				click.clickElement(taxCodeDropdownLocator);
				click.clickElement(taxCodeListLocator);
				wait.waitForElementDisplayed(searchFieldLocator, 10);
				text.enterText(searchFieldLocator, taxCode);
				click.clickElement(searchButtonLocator);
				clickDropdownListMenuItem(taxCode);
				return;
			}
			setDropdownToValue(taxCodeDropdownLocatorAlt, taxCode);
	}

	/**
	 * Selects Tax Code from dropdown
	 *
	 * @param taxCode the Netsuite TaxCode
	 */
	public void selectTaxCodeByDropdown(NetsuiteItem item, String taxCode)
	{
		if(item.getTaxCode() == null && taxCode == null) {
			return;
		}
		new Actions((driver));
		// Expand item search box before attempting to search for item
		getItemTableCellElementByLocator(item,taxCodeHeader, taxCodeLocator);

		setDropdownToValue(taxCodeDropdownLocatorAlt, taxCode);
	}

	protected String getTaxCodeHeader() { return taxCodeHeader; }

	public void setLocation(String location) {
		transactionComponent.selectLocation(location);
	}

	//TODO Update Location methods to check for null input
	/**
	 * Sets Item Location on Invoice
	 * **Not for Single Company
	 */
	public void setItemLocation(NetsuiteItem item) {
		Actions actor = new Actions((driver) );

		// Expand item search box before attempting to search for item
		List<WebElement> columns = driver.findElements(hdrRow);

		int index = 0;
		for(WebElement col : columns){
			index++;
			if(col.getAttribute("data-label").equals("Location") ){
				break;
			}
		}
		WebElement itemNameInput = driver.findElement(By.xpath("//table[@id='item_splits']/tbody/tr[2]/td["+index+"]") );
		actor.moveToElement(itemNameInput)
				.click()
				.perform();
		wait.waitForElementDisplayed(dropdownLoc);
		transactionComponent.setDropdownToValue(dropdownLoc,item.getLocation());
	}
	/**
	 * Sets Item Location on Invoice
	 * **Not for Single Company
	 * @param location Item location
	 */
	public void setItemLocation(NetsuiteItem item, String location)
	{
		Actions actor = new Actions((driver) );
		String locationHeader = getLocationHeader();

		// Expand item search box before attempting to search for item
		WebElement itemNameInput = driver.findElement(By.xpath("//table[@id='item_splits']/tbody/tr[2]/td[9]") );
		actor.moveToElement(itemNameInput)
				.click()
				.perform();
		transactionComponent.setDropdownToValue(dropdownLoc,location);
	}

	protected String getLocationHeader() { return locationHeader; }

	@Override
	public String getOrderSubtotal( )
	{
		return transactionComponent.getSummaryCategoryAmount(summarySubtotalHeader);
	}

	@Override
	public String getOrderTotal( )
	{
		return transactionComponent.getSummaryCategoryAmount(summaryTotalHeader);
	}

	/**
	 * Selects the shipping tab
	 */
	public abstract void selectShippingDetailsTab( );

	/**
	 * Selects the tab to setup addresses
	 */
	public abstract void selectAddressTab( );

	/**
	 * Adds a new ship to address and saves it to the current customer
	 *
	 * @param address the new ship to address
	 */
	public void createNewShipToAddress( NetsuiteAddress address )
	{
		String currentWindowHandle = driver.getWindowHandle();
		WebElement ShipToAddressButton = element.getWebElement(newShipToAddressButtonLocator);
		hover.hoverOverElement(newShipToAddressButtonLocator);
		ShipToAddressButton.click();
		switchToAddressPage(currentWindowHandle, address);
	}

	/**
	 * Creates a new ship to address selecting the Custom option, which does not save the address to the current
	 * customer
	 *
	 * @param address the new ship to address
	 */
	public void createCustomShipToAddress( NetsuiteAddress address )
	{
		String currentWindowHandle = driver.getWindowHandle();
		setDropdownToValue(existingShipToAddressDropDown, customAddressText);
		switchToAddressPage(currentWindowHandle, address);
	}

	/**
	 * Selects an already existing ship to address to use. If it doesn't exist, create it
	 *
	 * @param address the address to use
	 *
	 * @return if the address was selected successfully
	 */
	public boolean selectExistingShipToAddress( NetsuiteAddress address )
	{
		boolean isSelectionSuccessful = false;
		String fullAddress = address.getFullAddressLine1();
		if ( fullAddress == null || fullAddress.isEmpty() )
		{
			fullAddress = address.getAddressLine1();
		}

		if ( isDropdownValueInList(existingShipToAddressDropDown, fullAddress) )
		{
			setDropdownToValue(existingShipToAddressDropDown, fullAddress);
			attribute.tryWaitForElementAttributeChange(displayAddressField, "value", FOUR_SECOND_TIMEOUT);
			isSelectionSuccessful = true;
		}
		return isSelectionSuccessful;
	}

	/**
	 * Saves the order
	 *
	 * @return the new page
	 */
	public <T extends NetsuiteTransactions> T saveOrder( )
	{
		processMultiButtonSubmitter("Save");
		return initializePageObject(NetsuiteSCInvoicePage.class);
	}

	/**
	 * Checks if sales order was created successfully
	 */
	public boolean wasOrderSaved( )
	{
		WebElement banner = wait.waitForElementDisplayed(confirmationBannerLocator, 30);
		return banner != null;
	}

	/**
	 * Edits the order
	 *
	 * @return the new page
	 */
	public abstract <T extends NetsuiteTransactions> T editOrder( );

	/**
	 * Deletes the order
	 *
	 * @return the transactions page
	 */
	public abstract <T extends NetsuiteTransactionsPage> T deleteOrder();

	protected WebElement getDeleteButton( )
	{
		return wait.waitForElementDisplayed(deleteLocator);
	}

	/**
	 * Checks if the cleansed address is displayed
	 *
	 * @param address the address
	 *
	 * @return if the cleansed address is displayed
	 */
	public boolean isCleansedShippingAddressDisplayed( NetsuiteAddress address )
	{
		attribute.tryWaitForElementAttributeChange(displayAddressField, "value", FOUR_SECOND_TIMEOUT);
		String cleansedAddress = address.getCleansedAddress();
		String actualAddress = attribute.getElementAttribute(displayAddressField, "value");
		boolean isAddressCleansed = actualAddress != null && (cleansedAddress.equals(actualAddress)
															  || actualAddress.contains(cleansedAddress));
		System.out.println("actualAddress -->"+actualAddress);
		System.out.println("cleansedAddress -->"+cleansedAddress);
		return isAddressCleansed;
	}

	/**
	 * Enters the coupon code
	 *
	 * @param couponCode the coupon code
	 */
	public void enterCouponCode( String couponCode )
	{
		text.enterText(couponCodeDisplayLocator, couponCode);
		if ( alert.waitForAlertPresent(FOUR_SECOND_TIMEOUT) )
		{
			alert.acceptAlert();
		}
		attribute.tryWaitForElementAttributeChange(promotionDropdownLocator, "title", TWO_SECOND_TIMEOUT);
	}

	/**
	 * Enters the Transaction Date
	 *
	 * @param transDate the transdate field
	 */
	public void enterTransDate( String transDate )
	{
		//If Transdate not given as user input then don't enter
		if (transDate == "")
		{
			return;
		}
		text.enterText(transDateLocator, transDate);
		jsWaiter.sleep(250);
	}

	/**
	 * Enters the Sales Effective Date
	 *
	 * @param salesEffectiveDate the Sales Effective Date field
	 */
	public void enterSalesEffectiveDate( String salesEffectiveDate )
	{
		//If salesEffectiveDate not given as user input then don't enter
		if (salesEffectiveDate == "")
		{
			return;
		}
		text.enterText(salesEffDateLocator, salesEffectiveDate);
		jsWaiter.sleep(250);
	}

	/**
	 * Enters the Export Type
	 *
	 * @param exportType in the exporttype text field
	 */
	public void enterExportType( String exportType)
	{
		if (exportType == "")
		{
			return;
		}
		text.enterText(exportTypeLocator, exportType);
	}

	/**
	 * Selects the promotion
	 *
	 * @param promotion the promotion
	 */
	public void selectPromotion( String promotion )
	{
		if ( element.isElementEnabled(promotionDropdownLocator) )
		{
			setDropdownToValue(promotionDropdownLocator, promotion);
			if(element.isElementPresent(couponCodeDisplayLocator) ) {
				attribute.tryWaitForElementAttributeChange(couponCodeDisplayLocator, "title", TWO_SECOND_TIMEOUT);
				return;
			}
			jsWaiter.sleep(350);
			click.clickElement(By.id("promotions_addedit"));
		}
		else
		{
			enterCouponCode(promotion);
		}
	}

	/**
	 * Gets the vertex tax error code
	 *
	 * @return the vertex tax error code
	 */
	public String getVertexTaxErrorCode( )
	{
		String taxCallStatusLabelId = taxErrorCodeLoc;
		if(element.isElementPresent(By.id(taxResultLocator) ) ){
			taxCallStatusLabelId = taxResultLocator;
		}
		WebElement textElement = getTextUnderLabel(taxErrorCodeParentContainerLocator, taxCallStatusLabelId);
		return textElement.getText();
	}

	/**
	 * Gets Nexus value
	 *
	 * @return the Tax Nexus
	 */
	public String getNexusText( )
	{
		String taxNexusLabelId = taxNexusLabelLoc;
		WebElement textElement = getTextUnderLabel(taxNexusParentContainerLocator, taxNexusLabelId);
		return textElement.getText();
	}

	/**
	 * Checks if the address can be verified
	 *
	 * @return if the address can be verified
	 */
	public boolean isAddressVerified( )
	{
		boolean isAddressVerified = addressPage.isAddressVerified();
		return isAddressVerified;
	}

	/**
	 * Edits the currently open address form
	 *
	 * @param address the new address to use
	 */
	public void editOpenAddress( NetsuiteAddress address )
	{
		addressPage.saveAddress(address);
	}

	/**
	 * Enables line item shipping if not enabled
	 */
	public void enableLineItemShipping( )
	{
		selectItemsTab();
		transactionComponent.enableLineItemShipping();
	}

	/**
	 * Creates a new ship to address for the line item, which is then saved to the customer record
	 *
	 * @param item    the line item
	 * @param address the new ship to address
	 */
	public void createNewItemShipToAddress( NetsuiteItem item, NetsuiteAddress address )
	{
		String currentWindowHandle = driver.getWindowHandle();
		WebElement shipToDropdown = getItemTableCellElementByLocator(item, itemShipToHeader, itemShipToLocator);
		setDropdownToValue(shipToDropdown, newAddressText);
		switchToAddressPage(currentWindowHandle, address);
	}

	/**
	 * Creates a new ship to address using the custom option, which is not saved to the customer record
	 *
	 * @param item    the line item
	 * @param address the ship to address
	 */
	public void createCustomItemShipToAddress( NetsuiteItem item, NetsuiteAddress address )
	{
		String currentWindowHandle = driver.getWindowHandle();
		WebElement shipToDropdown = getItemTableCellElementByLocator(item, itemShipToHeader, itemShipToLocator);
		setDropdownToValue(shipToDropdown, customAddressText);
		switchToAddressPage(currentWindowHandle, address);
	}

	public void editCustomItemShipToAddress( NetsuiteItem item, NetsuiteAddress address )
	{
		String currentWindowHandle = driver.getWindowHandle();
		WebElement shipToDropdown = getItemTableCellElementByLocator(item, itemShipToHeader, itemShipToLocator);
		setDropdownToValue(shipToDropdown, customAddressText);
		switchToAddressPage(currentWindowHandle, address);
	}

	/**
	 * Checks if the line item's ship to address is cleansed
	 *
	 * @param item            the item to check
	 * @param cleansedAddress the correct cleansed address
	 *
	 * @return if the line item's ship to address is cleansed
	 */
	public boolean isLineItemShipToAddressCleansed( NetsuiteItem item, NetsuiteAddress cleansedAddress )
	{
		String currentWindowHandle = driver.getWindowHandle();
		WebElement editShipToAddressButton = getItemTableCellElementByLocator(item, itemShipToHeader,
			itemShipToEditLocator);
		hover.hoverOverElement(editShipToAddressButton);
		click.clickElement(editShipToAddressButton);
		NetsuitePageTitles addressPageTitle = NetsuitePageTitles.ADDRESS_PAGE;
		addressPage = new NetsuiteAddressPopupPage(driver, currentWindowHandle);
		window.switchToWindowTextInTitle(addressPageTitle.getPageTitle());
		boolean isAddressCleansed = addressPage.isAddressCleansed(cleansedAddress);
		return isAddressCleansed;
	}

	/**
	 * Gets the specified element that is inside any of the item table cells
	 *
	 * @param item            the item, used to designate which row on the item table
	 * @param itemTableHeader the item table header, used to designate which column on the item table
	 * @param elementLocator  the element locator
	 *
	 * @return the specified element in the item table cell
	 */
	private WebElement getItemTableCellElementByLocator( NetsuiteItem item, String itemTableHeader, By elementLocator )
	{
 		WebElement itemTableCellInputElement = tableComponent.getInteractableTableCell(itemTable, itemHeaderRow, item.getNetsuiteItemName().getItemName(), itemTableHeader);
		tableComponent.focusOnInteractableTableCell(itemTableCellInputElement);

		// need to get it twice because of stale element exceptions that occurs after focusing on the cell
		for (int i = 0; i < 3; i++) {
			itemTableCellInputElement = tableComponent.getInteractableTableCell(itemTable, itemHeaderRow, item
					.getNetsuiteItemName()
					.getItemName(), itemTableHeader);
		}

		tableComponent.focusOnInteractableTableCell(itemTableCellInputElement);

		WebElement inputElement = wait.waitForElementPresent(elementLocator, itemTableCellInputElement);
		return inputElement;
	}

	/**
	 * Switches to the address popup page and fills it out with the given address
	 *
	 * @param currentWindowHandle the window handle before the address page was created
	 * @param address             the address
	 */
	private void switchToAddressPage( String currentWindowHandle, NetsuiteAddress address )
	{
		NetsuitePageTitles addressPageTitle = NetsuitePageTitles.ADDRESS_PAGE;
		addressPage = new NetsuiteAddressPopupPage(driver, currentWindowHandle);
		window.switchToWindowTextInTitle(addressPageTitle.getPageTitle());
		addressPage.saveAddress(address);
	}

	/**
	 * Selects the Vertex call details tab
	 */
	public void selectVertexCallDetailsTab( )
	{
		By vertexCallDetailsTabLocator = getVertexCallDetailsTabLocator();
		WebElement vertexCallDetailsTab = element.getWebElement(vertexCallDetailsTabLocator);
		scroll.scrollElementIntoView(vertexCallDetailsTab);
		vertexCallDetailsTab.click();
		vertexCallDetailsTab.click();
	}

	/**
	 * Gets the Vertex call details tab locator
	 *
	 * @return the locator
	 */
	protected By getVertexCallDetailsTabLocator( )
	{
		return vertexCallDetailsTabLocator;
	}

	/**
	 * Shows the XML log page
	 *
	 * @return the Vertex Call Details Page
	 */
	public NetsuiteVertexCallDetailsPage showXMLLog( )
	{
		WebElement idCell = tableComponent.getTableCellByCount(vertexCallDetailsTableLocator,
			vertexCallDetailsTableHeaderLocator, 1, "ID");

		WebElement cellButton = wait.waitForElementDisplayed(By.tagName("a"), idCell);
		click.clickElement(cellButton);
		return initializePageObject(NetsuiteVertexCallDetailsPage.class);
	}

	/**
	 * Shows the Vertex log page
	 *
	 * @return the Vertex Log Details Page
	 */
	public String showVertexLogDetails( )
	{
		WebElement logTableText = element.getWebElement(vertexLogDetailsLoc);
		return logTableText.getText();
	}

	/**
	 * Gets the vertex tax details
	 *
	 * @return the vertex tax details - Test Rate Class, InvoiceTextCode, Tax Structure
	 */
	public String gettaxDetailstext( )
	{
		WebElement taxdetailsrow = element.getWebElement(taxDetailsRowLoc);
		return taxdetailsrow.getText();
	}

	/**
	 * Gets the vendor Bill item details
	 *
	 * @return the vendor bill item details - item, Tax adjustment
	 */
	public String getBillItemDetailstext( )
	{
		WebElement taxdetailsrow = element.getWebElement(vendorBillItemDetailsLoc);
		return taxdetailsrow.getText();
	}

	/**
	 * Gets the totalling table summary details
	 *
	 * @return the totalling table summary details - SUBTOTAL, TAX TOTAL, US SALE, SHIPPING COST, HANDLING COST, Total
	 */
	public String getTotallingTableSummaryText( )
	{
		WebElement totalsummary = element.getWebElement(totallingSummaryTableLoc);
		return totalsummary.getText();
	}

	/**
	 * Gets the vertex CALL details row text
	 *
	 * @return the vertex Call details row information
	 */
	public String getCallDetailstext( )
	{
		WebElement calldetailsrow = element.getWebElement(callDetailsRowLoc);
		return calldetailsrow.getText();
	}

	/**
	 * Gets the VERTEX CUT TAX ACTION text
	 *
	 * @return the VERTEX CUT TAX ACTION text
	 */
	public String getVertexCutTaxActiontext( )
	{
		WebElement cutTaxAction = element.getWebElement(vertexCutTaxActionLoc);
		return cutTaxAction.getText();
	}

	/**
	 * Gets the VERTEX ASSOCIATED TRANSACTION text
	 *
	 * @return the VERTEX ASSOCIATED TRANSACTION text
	 */
	public String getVertexAssociatedTranstext( )
	{
		WebElement vertexAssociatedTranstext = element.getWebElement(vertexAssociatedTranstexLoc);
		return vertexAssociatedTranstext.getText();
	}


	/**
	 * Gets the vertex tax details table text
	 *
	 * @return the vertex entire tax details table text
	 */
	public String gettaxDetailsTabletext( )
	{
		WebElement taxdetailstable = element.getWebElement(taxDetailsTableLoc);
		return taxdetailstable.getText();
	}

	/**
	 * Checks relevant Checkbox
	 */
	public void setCheckBox(By checkBoxLocator)
	{
		WebElement localCheckBox = element.getWebElement(checkBoxLocator);
		if(!checkbox.isCheckboxChecked(localCheckBox) )
		{
			click.javascriptClick(localCheckBox);
		}
	}

	/**
	 * Verify checked VERTEX DISTRIBUTE TAX checkbox is displayed
	 */
	public void getDistributeTaxCheckboxStatus()
	{
		WebElement localCheckBox = wait.waitForElementPresent(distributeTaxChecked);
		if(localCheckBox.isDisplayed())
		{
			VertexLogger.log("Success!! Distribute Tax Checkbox is Checked!");
		}
		else
		{
			VertexLogger.log("FAIL!! Distribute Tax Checkbox is NOT Checked!");
		}
	}

	/*** Checks Post to Vertex checkbox if it isn't checked*/
	public void setVertexPostToVertex() { setCheckBox(vertexPostToVertex); }

	/*** Checks the Distribute tax checkbox*/
	public void setDistributeTax() { setCheckBox(distributeTax); }

	/**
	 * Checks the Tax Details Override checkbox
	 */
	public void setTaxDetailsOverride(){ setCheckBox(taxDetailsOverride); }

	/**
	 * Calculates Shipping cost for order
	 */
	public void calculateShippingCost()
	{
		WebElement calculateButton = element.getWebElement(calculateShippingCostLocator);
		if(calculateButton != null){
			calculateButton.click();
		}
	}

	/**
	 * Enter Shipping cost for order
	 */
	public void enterShippingCost(String shippingCost)
	{
		jsWaiter.sleep(60);
		WebElement enterShippingCost = element.getWebElement(enterShippingCostLocator);
		enterShippingCost.click();
		enterShippingCost.click();
		enterShippingCost.clear();
		text.enterText(enterShippingCost, shippingCost);
	}

	/**
	 * Enter Tax details - new Tax Amount for testing Tax Override feature
	 */
	public void enterTaxDetails(String taxAmount)
	{
		WebElement taxAmountElement = element.getWebElement(taxAmtLocator);
		WebElement taxAmtTextbox = element.getWebElement(taxAmtTextboxLocator);

		if(taxAmountElement != null){
			wait.waitForElementEnabled(taxAmountElement);
			taxAmountElement.click();
			jsWaiter.sleep(40);
			text.enterText(taxAmtTextbox, taxAmount);
		}
	}

	/**
	 * Executes Preview Tax function
	 *
	 * @return the Total Tax and Total Cost
	 */
	public String[] previewTax()
	{
		WebElement previewTaxButton = element.getWebElement(taxPreviewButtonLocator);
		String taxValue;
		String totalValue;
		String dataString;

		click.clickElement(previewTaxButton);
		alert.acceptAlert(30);
		wait.waitForElementDisplayed(taxPreviewResultLocator);
		//Get Tax and Total
		dataString = element.getWebElement(new ByChained(taxPreviewResultLocator,By.xpath("//p"))).getText()
				.replaceAll("\\D*[^\\d]+[^\\d\\.][^\\d]+","!")
				.substring(1).trim();
		//Extract values from string
		System.out.println("dataString value===> "+dataString);
		taxValue = dataString.substring(0, dataString.indexOf('!'));
		System.out.println("taxValue====="+taxValue);
		totalValue = dataString.substring(dataString.indexOf('!')+1).trim();
		System.out.println("totalValue=====>"+totalValue);

		return new String[]{taxValue, totalValue};

	}

	/**
	 * Executes Preview Tax Summary function
	 *
	 * @return the Total Tax and Total Cost
	 */
	public String[] previewTaxSummary()
	{
		WebElement previewTaxButton = element.getWebElement(taxPreviewButtonSummaryLocator);
		String taxValue;
		String totalValue;
		String dataString;

		click.clickElement(previewTaxButton);
		// wait for alert
		dataString = alert.getAlertText(20)
				.replaceAll("\\D*[^\\d]+[^\\d\\.][^\\d]+","!")
				.substring(1).trim();
		alert.acceptAlert();
		//Get Tax and Total & Extract values from string
		taxValue = dataString.substring(0, dataString.indexOf('!'));
		totalValue = dataString.substring(dataString.indexOf('!')+1).trim();

		return new String[]{taxValue, totalValue};
	}

	/**
	 * Executes Preview Tax Summary function
	 *
	 * @return the Total Tax and Total Cost
	 */
	public String getDistributedTax()
	{
		WebElement taxAmount = element.getWebElement(taxAmountLocator );
		String totalValue = taxAmount.getText();
		return totalValue;
	}

	/**
	 * Handles Multi-submit button
	 * @param desiredAction Text of the action you're looking for i.e. "Save", "Save & Fulfill", etc
	 */
	public void processMultiButtonSubmitter(String desiredAction)
	{

		//get multi button
		WebElement multiButton = element.getWebElement(saveLocator);
		//check displayed action
			if( multiButton.getAttribute("value").compareToIgnoreCase(desiredAction) == 0 ) {
				clickThenWaitForConformation(saveLocator);
				return;
			}
		//Expand button dropdown
		hover.hoverOverElement(multiButtonDropDown);
		clickThenWaitForConformation( By.xpath("//*[@id='div_multibutton_submitter']//span[contains( text(),'"+desiredAction+"' )]") );
			return;
	}

	/**
	 * Wait for the action to finish before proceeding
	 * @param button
	 */
	private void clickThenWaitForConformation(By button)
	{
		driver.findElement(button).sendKeys(Keys.ENTER);
		wait.waitForElementDisplayed(confirmationBannerLocator, 20);
		return;
	}

	/**
	 * Enters the item class
	 *
	 * @param item the Netsuite item
	 */
	public void enterItemClass( NetsuiteItem item )
	{
		// Check if item has a Class before setting
		if(item.getItemClass() != null) {
			String itemClass = item.getItemClass();
			String classHeader = itemClassHeader;
			WebElement classTextBox = getItemTableCellElementByLocator(item, classHeader, itemClassLocator);
			text.enterText(classTextBox, itemClass);
		}
	}

}
