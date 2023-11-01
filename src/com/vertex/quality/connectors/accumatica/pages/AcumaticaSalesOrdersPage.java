package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.connectors.accumatica.enums.AcumaticaMainPanelTab;
import com.vertex.quality.connectors.accumatica.interfaces.AcumaticaTableColumn;
import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaPostSignOnPage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * @author saidulu kodadala
 * Sales Orders page actions/methods
 */
public class AcumaticaSalesOrdersPage extends AcumaticaPostSignOnPage
{
	protected final By ORDER_TYPE = By.id("ctl00_phF_form_edOrderType_text");
	protected final By CUSTOMER_ID = By.id("ctl00_phF_form_edCustomerID_text");

	protected final By NEW_ROW_PLUS_ICON = By.cssSelector("[id*='phG_tab_t0_grid_at_tlb'] [icon='RecordAdd']");
	protected final By QUANTITY = By.cssSelector("[id$='_grid_lv0_edOrderQty']");
	protected static final String SUBMENU_LOCATOR = "//td[contains(@id,'phG_tab_tab') and contains(text(),'%s')]";
	protected final By FINANCIAL_INFORMATION_BRANCH = By.cssSelector("[id$='_formE_edBranchID_text']");
	protected final By CUSTOMER_TAX_ZONE = By.cssSelector("[id$='_formE_edTaxZoneID_text']");
	protected final By OVERRIDE_ADDRESS_CHECK_BOX = By.cssSelector("[id$='_formA_chkOverrideAddress']");
	protected final By ACS_CHECK_BOX = By.cssSelector("[id$='_formA_chkIsValidated']");
	protected final By PENCIL_ICON_AT_CUSTOMER = By.cssSelector("div#ctl00_phF_form_edCustomerID div[icon='EditN']");

	protected final By tableContainerClass = By.className("GridMainT");

	public AcumaticaSalesOrdersPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * has to find the table of the current page, which is displayed, while filtering out the hidden tables of other
	 * loaded pages
	 *
	 * @return the table on the current page
	 */
	@Override
	protected WebElement getTableContainer( )
	{
		WebElement table = null;

		List<WebElement> containers = wait.waitForAllElementsPresent(tableContainerClass);
		for ( WebElement container : containers )
		{
			if ( container.isDisplayed() )
			{
				table = container;
				break;
			}
		}

		return table;
	}

	/**
	 * waits a short period for the field to not be empty*
	 * Does NOT throw an exception if the wait times out
	 *
	 * *if there are just whitespace characters, it's still counted as empty
	 *
	 * @param field which text field should eventually have text in it
	 */
	public void tryWaitForFieldNotEmpty( By field )
	{
		WebElement fieldElem = wait.waitForElementDisplayed(field);

		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isFieldNotEmpty;
			String currentText = attribute.getElementAttribute(fieldElem, "value");
			currentText = text.cleanseWhitespace(currentText);
			isFieldNotEmpty = !currentText.isEmpty();
			return isFieldNotEmpty;
		};

		WebDriverWait wait = new WebDriverWait(driver, FIVE_SECOND_TIMEOUT);
		try
		{
			wait.until(condition);
		}
		catch ( TimeoutException e )
		{
		}
	}

	/***
	 * waits for the order type value to load then returns it
	 * @return the type of the current sales order
	 */
	public String getOrderType( )
	{
		tryWaitForFieldNotEmpty(ORDER_TYPE);
		String orderType = attribute.getElementAttribute(ORDER_TYPE, "value");
		orderType = text.cleanseWhitespace(orderType);
		return orderType;
	}

	/***
	 * sets the customer for the first line item
	 * @param customer
	 */
	public void setCustomer( final String customer )
	{
		text.setTextFieldCarefully(CUSTOMER_ID, customer);
	}

	/***
	 * Click plus icon for add new record
	 */
	public void clickPlusIconForAddNewRecord( )
	{
		click.clickElementCarefully(NEW_ROW_PLUS_ICON);
	}

	/***
	 * sets the inventory id for the first line item
	 * @param inventoryId
	 */
	public void setInventoryId( final String inventoryId )
	{
		WebElement inventoryIdElem = getTableCell(TableColumn.INVENTORY_ID, 0);
		click.clickElementCarefully(inventoryIdElem);
		inventoryIdElem = getTableCell(TableColumn.INVENTORY_ID, 0);
		text.setTextFieldCarefully(inventoryIdElem, inventoryId, false);
	}

	/**
	 * sets the branch for the first line item
	 *
	 * @param branch which branch the first line item should have
	 */
	public void setBranch( final String branch )
	{
		WebElement branchElem = getTableCell(TableColumn.BRANCH, 0);
		click.clickElementCarefully(branchElem);
		branchElem = getTableCell(TableColumn.BRANCH, 0);
		text.setTextFieldCarefully(branchElem, branch, false);
	}

	/**
	 * sets the warehouse for the first line item
	 *
	 * @param warehouse which warehouse the first line item should have
	 */
	public void setWarehouse( final String warehouse )
	{
		WebElement warehouseElem = getTableCell(TableColumn.WAREHOUSE, 0);
		click.clickElementCarefully(warehouseElem);
		warehouseElem = getTableCell(TableColumn.WAREHOUSE, 0);
		text.setTextFieldCarefully(warehouseElem, warehouse, false);
	}

	/**
	 * sets the description for the first line item
	 *
	 * @param lineDescription what the first line item's description should be
	 */
	public void setLineDescription( final String lineDescription )
	{
		WebElement descriptionElem = getTableCell(TableColumn.LINE_DESCRIPTION, 0);
		click.clickElementCarefully(descriptionElem);
		descriptionElem = getTableCell(TableColumn.LINE_DESCRIPTION, 0);
		text.setTextFieldCarefully(descriptionElem, lineDescription, false);
	}

	/***
	 * sets the inventory id, warehouse, and description for the first line item
	 * @param inventoryId
	 * @param warehouse
	 * @param lineDescription
	 */
	public void setInventoryIdAndWareHouse( final String inventoryId, final String warehouse,
		final String lineDescription )
	{
		setInventoryId(inventoryId);
		setBranch("MAIN");
		setWarehouse(warehouse);
		setLineDescription(lineDescription);
	}

	/***
	 * sets the quantity for the first line item
	 * @param quantity
	 */
	public void setQuantity( final String quantity )
	{
		WebElement quantityElem = getTableCell(TableColumn.QUANTITY, 0);
		click.clickElementCarefully(quantityElem);
		quantityElem = getTableCell(TableColumn.QUANTITY, 0);
		text.setTextFieldCarefully(quantityElem, quantity, false);
	}

	/***
	 * sets the unit price for the first line item
	 * @param unitPrice
	 */
	public void setUnitPrice( final String unitPrice )
	{
		WebElement unitPriceElem = getTableCell(TableColumn.UNIT_PRICE, 0);
		click.clickElementCarefully(unitPriceElem);
		unitPriceElem = getTableCell(TableColumn.UNIT_PRICE, 0);
		text.setTextFieldCarefully(unitPriceElem, unitPrice, false);
	}

	/***
	 * sets the Discount Amount for the first line item
	 * @param discountAmount
	 */
	public void setDiscountAmount( final String discountAmount )
	{
		WebElement discountAmtElem = getTableCell(TableColumn.DISCOUNT_AMOUNT, 0);
		click.clickElementCarefully(discountAmtElem);
		discountAmtElem = getTableCell(TableColumn.DISCOUNT_AMOUNT, 0);
		text.setTextFieldCarefully(discountAmtElem, discountAmount);
	}

	/***
	 * Get branch
	 * @return
	 */
	public String getFinancialInformationBranch( )
	{
		String branch = attribute.getElementAttribute(FINANCIAL_INFORMATION_BRANCH, "value");
		branch = text.cleanseWhitespace(branch);
		return branch;
	}

	/***
	 * Get customer tax zone
	 * @return
	 */
	public String getCustomerTaxZoneFromFinancialInformationSection( )
	{
		String taxZone = attribute.getElementAttribute(CUSTOMER_TAX_ZONE, "value");
		taxZone = text.cleanseWhitespace(taxZone);
		return taxZone;
	}

	/***
	 * Verify 'orride address check box'
	 * @param flag
	 */
	public boolean verifyOrrideAddressFromBillToInfoSection( final boolean flag )
	{
		wait.waitForElementDisplayed(OVERRIDE_ADDRESS_CHECK_BOX);
		boolean status = checkbox.isCheckboxChecked(OVERRIDE_ADDRESS_CHECK_BOX);
		if ( status != flag )
		{
			click.clickElementCarefully(OVERRIDE_ADDRESS_CHECK_BOX);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(OVERRIDE_ADDRESS_CHECK_BOX);
		return resultStatus;
	}

	/***
	 * Verify 'ACS' check box
	 * @param flag
	 */
	public boolean verifyAcsFromBillToInfoSection( final boolean flag )
	{
		wait.waitForElementDisplayed(ACS_CHECK_BOX);
		boolean status = checkbox.isCheckboxChecked(ACS_CHECK_BOX);
		if ( status != flag )
		{
			click.clickElementCarefully(ACS_CHECK_BOX);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(ACS_CHECK_BOX);
		return resultStatus;
	}

	/***
	 * Click on Financial setting tab
	 * @param tab
	 */
	public void clickMainPanelTab( final AcumaticaMainPanelTab tab )
	{
		By FINANCIAL_TAB = By.xpath(String.format(SUBMENU_LOCATOR, tab));
		click.clickElementCarefully(FINANCIAL_TAB);
	}

	/**
	 * Click on pencil icon at customer for search customer name
	 */
	public void clickPencilIconAtCustomer( )
	{
		click.clickElementCarefully(PENCIL_ICON_AT_CUSTOMER);
		window.switchToWindow();
	}

	/**
	 * Get tax details table values
	 */
	public void getTaxDetailsTableValues( )
	{
		List<WebElement> allHeadersOfTable = driver.findElements(
			By.xpath("//table[@id='ctl00_phG_tab_t1_grid1']/tbody/tr[2]//table//td"));
		System.out.println("Headers in table are below:");
		System.out.println("Total headers found: " + allHeadersOfTable.size());
		for ( WebElement header : allHeadersOfTable )
		{
			System.out.println(header.getText());
		}
	}

	/**
	 * this collects the columns in the table of line items in the sales order
	 * The columns contain different attributes of the line items
	 */
	protected enum TableColumn implements AcumaticaTableColumn
	{
		INVENTORY_ID("Inventory ID", "_grid_lv0_edInventoryID_text"),
		BRANCH("Branch", "_grid_lv0_edBranchID_text"),
		WAREHOUSE("Warehouse", "_grid_lv0_edSiteID_text"),
		LINE_DESCRIPTION("Line Description", "_grid_lv0_edTranDesc"),
		QUANTITY("Quantity", "_grid_lv0_edOrderQty"),
		UNIT_PRICE("Unit Price", "_grid_lv0_edUnitPrice"),
		EXT_PRICE("Ext. Price", "_grid_lv0_edCuryLineAmt"),
		DISCOUNT_AMOUNT("Discount Amount", "_grid_lv0_edCuryDiscAmt");

		TableColumn( final String labelText, final String endOfIdAttribute )
		{
			this.label = labelText;
			String overlayLocatorString = String.format("[id$='%s']", endOfIdAttribute);
			this.editingOverlay = By.cssSelector(overlayLocatorString);
		}

		private String label;
		private By editingOverlay;

		public String getLabel( )
		{
			return label;
		}

		public By getEditingOverlay( )
		{
			return editingOverlay;
		}
	}
}
