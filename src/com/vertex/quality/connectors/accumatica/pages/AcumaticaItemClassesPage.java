package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaPostSignOnPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Item Classes page actions/methods
 *
 * @author saidulu kodadala
 */
public class AcumaticaItemClassesPage extends AcumaticaPostSignOnPage
{
	protected By CLASS_ID = By.id("ctl00_phF_form_edItemClassID_text");
	protected By CLASS_DESCRIPTION = By.id("ctl00_phF_form_edDescr");
	protected By STOCK_ITEM_CHECKBOX = By.id("ctl00_phG_tab_t0_chkStkItem");
	protected By ALLOW_NEGATIVE_QUANTITY = By.id("ctl00_phG_tab_t0_chkNegQty");

	protected final By dropdownOptionsTable = By.className("ddTable");
	protected final By dropdownOption = By.className("ddItem");
	//this suffix should be a substring of the css selector for the item type dropdown
	protected final String itemTypeDropdownIdSuffix = "edItemType";
	protected final By typeDropdown = By.cssSelector("div[id$=\"edItemType\"]");
	//this suffix should be a substring of the css selector for the valuation method dropdown
	protected final String valMethodDropdownIdSuffix = "edValMethod";
	protected final By valMethodDropdown = By.cssSelector("div[id$=\"edValMethod\"]");

	//TODO fairly unstable ID's because of the 'tab_t#' part, please replace (perhaps like text fields in aribaold portal catalog's edit item details?)
	protected By TAX_CATEGORY_ID = By.id("ctl00_phG_tab_t0_edTaxCategoryID_text");
	protected By POSTING_CLASS = By.id("ctl00_phG_tab_t0_edPostClassID_text");
	protected By PRICE_CLASS = By.id("ctl00_phG_tab_t0_edPriceClassID_text");
	protected By DEDUCT_QTY_ON_ISSUES = By.id("ctl00_phG_tab_t0_chkInclQtyINIssues");
	protected By DEDUCT_QTY_ON_SALES_PREPARED = By.id("ctl00_phG_tab_t0_chkInclQtySOPrepared");
	protected By DEDUCT_QTY_ON_SALES_ORDERS = By.id("ctl00_phG_tab_t0_chkInclQtySOBooked");
	protected By DEDUCT_QTY_OF_KIT_ASSEMBLY_DEMAND = By.id("ctl00_phG_tab_t0_chkInclQtyINAssemblyDemand");
	protected By DEDUCT_QTY_ON_BACK_ORDERS = By.id("ctl00_phG_tab_t0_chkInclQtySOBackOrdered");
	protected By DEDUCT_QTY_SHIPPED = By.id("ctl00_phG_tab_t0_chkInclQtySOShipped");
	protected By DEDUCT_QTY_ALLOCATED = By.id("ctl00_phG_tab_t0_chkInclQtySOShipped");
	protected By INCLUDE_QTY_ON_RECEIPTS = By.id("ctl00_phG_tab_t0_chkInclQtyINReceipts");
	protected By INCLUDE_QTY_IN_TRANSIT = By.id("ctl00_phG_tab_t0_chkInclQtyInTransit");
	protected By INCLUDE_QTY_ON_PO_RECEIPTS = By.id("ctl00_phG_tab_t0_chkInclQtyPOReceipts");
	protected By INCLUDE_QTY_ON_PURCHANSE_PREPARED = By.id("ctl00_phG_tab_t0_chkInclQtyPOPrepared");
	protected By INCLUDE_QTY_ON_PURCHANSE_ORDERS = By.id("ctl00_phG_tab_t0_chkInclQtyPOOrders");
	protected By INCLUDE_QTY_OF_KIT_ASSEMBLY_SUPPLY = By.id("ctl00_phG_tab_t0_chkInclQtyINAssemblySupply");
	protected By INCLUDE_QTY_ON_RETURNS = By.id("ctl00_phG_tab_t0_chkInclQtySOReverse");
	protected By BASE_UNIT = By.id("ctl00_phG_tab_t0_edBaseUnit_text");
	protected By SALES_UNIT = By.id("ctl00_phG_tab_t0_edSalesUnit_text");
	protected By PURCHASE_UNIT = By.id("ctl00_phG_tab_t0_edPurchaseUnit_text");

	public AcumaticaItemClassesPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Enter class id
	 *
	 * @param classId the unique name of the item class
	 */
	public void setClassId( String classId )
	{
		click.clickElementCarefully(CLASS_ID);
		text.setTextFieldCarefully(CLASS_ID, classId, false);
	}

	/**
	 * get class id
	 *
	 * @return the unique name of the item class
	 */
	public String getClassId( )
	{
		click.clickElementCarefully(CLASS_ID);
		String inventoryId = attribute.getElementAttribute(CLASS_ID, "value");
		return inventoryId;
	}

	/**
	 * Enter description of the item class
	 *
	 * @param description the description of the item class
	 */
	public void setDescription( String description )
	{
		click.clickElementCarefully(CLASS_DESCRIPTION);
		text.setTextFieldCarefully(CLASS_DESCRIPTION, description, false);
	}

	/***
	 * Verify 'Stock Item' check box status
	 */
	public boolean setCheckedStockItemCheckbox( boolean expectedState )
	{
		wait.waitForElementDisplayed(STOCK_ITEM_CHECKBOX);
		boolean isChecked = checkbox.isCheckboxChecked(STOCK_ITEM_CHECKBOX);
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(STOCK_ITEM_CHECKBOX);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(STOCK_ITEM_CHECKBOX);
		return resultStatus;
	}

	/***
	 * Verify 'Allow Negative Quantity' check box status
	 */
	public boolean setCheckedAllowNegativeQuantityCheckbox( boolean expectedState )
	{
		wait.waitForElementDisplayed(ALLOW_NEGATIVE_QUANTITY);
		boolean isChecked = checkbox.isCheckboxChecked(ALLOW_NEGATIVE_QUANTITY);
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(ALLOW_NEGATIVE_QUANTITY);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(ALLOW_NEGATIVE_QUANTITY);
		return resultStatus;
	}

	/**
	 * Enters the type of the item class
	 *
	 * @param type the type of the item class
	 *
	 * @author ssalisbury
	 */
	public void setType( String type )
	{
		click.clickElementCarefully(typeDropdown);

		WebElement typeOption = getItemTypeOption(type);
		if ( typeOption != null )
		{
			click.clickElementCarefully(typeOption);
		}
		else
		{
			VertexLogger.log(type);
			throw new RuntimeException("Cannot set possibly invalid item type");
		}
	}

	/**
	 * Enters the valuation method of the item class
	 *
	 * @param valuationMethod the valuation method of the item class
	 *
	 * @author ssalisbury
	 */
	public void setValuationMethod( String valuationMethod )
	{
		click.clickElementCarefully(valMethodDropdown);

		WebElement methodOption = getValuationMethodOption(valuationMethod);
		if ( methodOption != null )
		{
			click.clickElementCarefully(methodOption);
		}
		else
		{
			VertexLogger.log(valuationMethod);
			throw new RuntimeException("Cannot set possibly invalid valuation method");
		}
	}

	/**
	 * choose a tax category
	 *
	 * @param taxCategoryID the unique name of the desired tax category
	 */
	public void setTaxCategory( String taxCategoryID )
	{
		text.setTextFieldCarefully(TAX_CATEGORY_ID, taxCategoryID);
	}

	/**
	 * Get the current tax category's id and description
	 */
	public String getTaxCategory( )
	{
		wait.waitForElementDisplayed(TAX_CATEGORY_ID);
		String taxCategory = attribute.getElementAttribute(TAX_CATEGORY_ID, "value");
		return taxCategory;
	}

	/**
	 * choose a posting class
	 *
	 * @param postingClassId the unique name of the desired posting class
	 */
	public void setPostingClass( String postingClassId )
	{
		text.setTextFieldCarefully(POSTING_CLASS, postingClassId);
	}

	/**
	 * Get the current posting class' id and description
	 */
	public String getPostingClass( )
	{
		wait.waitForElementDisplayed(POSTING_CLASS);
		String postingClass = attribute.getElementAttribute(POSTING_CLASS, "value");
		return postingClass;
	}

	/**
	 * Clear price class
	 */
	public void clearPriceClass( )
	{
		wait.waitForElementEnabled(PRICE_CLASS);
		text.clearText(PRICE_CLASS);
		text.pressTab(PRICE_CLASS);
		waitForPageLoad();
	}

	/**
	 * Enter price class
	 *
	 * @param priceClass
	 */
	public void setPriceClass( String priceClass )
	{
		text.setTextFieldCarefully(PRICE_CLASS, priceClass);
	}

	/**
	 * Get price class
	 */
	public String getPriceClass( )
	{
		wait.waitForElementDisplayed(PRICE_CLASS);
		String priceClass = attribute.getElementAttribute(PRICE_CLASS, "value");
		return priceClass;
	}

	/***
	 * Verify 'Deduct Qty. Shipped' check box status
	 */
	public boolean setCheckedDeductQtyShippedCheckbox( boolean expectedState )
	{
		wait.waitForElementDisplayed(DEDUCT_QTY_SHIPPED);
		boolean isChecked = checkbox.isCheckboxChecked(DEDUCT_QTY_SHIPPED);
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(DEDUCT_QTY_SHIPPED);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(DEDUCT_QTY_SHIPPED);
		return resultStatus;
	}

	/***
	 * Verify 'Deduct Qty. Allocated' check box status
	 */
	public boolean setCheckedDeductQtyAllocatedCheckbox( boolean expectedState )
	{
		wait.waitForElementDisplayed(DEDUCT_QTY_ALLOCATED);
		boolean isChecked = checkbox.isCheckboxChecked(DEDUCT_QTY_ALLOCATED);
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(DEDUCT_QTY_ALLOCATED);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(DEDUCT_QTY_ALLOCATED);
		return resultStatus;
	}

	/***
	 * Verify 'Include Qty. on Returns' check box status
	 */
	public boolean setCheckedIncludeQtyOnReturnsCheckbox( boolean expectedState )
	{
		wait.waitForElementDisplayed(INCLUDE_QTY_ON_RETURNS);
		boolean isChecked = checkbox.isCheckboxChecked(INCLUDE_QTY_ON_RETURNS);
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(INCLUDE_QTY_ON_RETURNS);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(INCLUDE_QTY_ON_RETURNS);
		return resultStatus;
	}

	/***
	 * Verify 'Deduct Qty. on Issues' check box status
	 */
	public boolean setCheckedDeductQtyOnIssuesCheckbox( boolean expectedState )
	{
		wait.waitForElementDisplayed(DEDUCT_QTY_ON_ISSUES);
		boolean isChecked = checkbox.isCheckboxChecked(DEDUCT_QTY_ON_ISSUES);
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(DEDUCT_QTY_ON_ISSUES);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(DEDUCT_QTY_ON_ISSUES);
		return resultStatus;
	}

	/***
	 * Verify 'Deduct Qty. on Sales Prepared' check box status
	 */
	public boolean setCheckedDeductQtyOnSalesPreparedCheckbox( boolean expectedState )
	{
		wait.waitForElementDisplayed(DEDUCT_QTY_ON_SALES_PREPARED);
		boolean isChecked = checkbox.isCheckboxChecked(DEDUCT_QTY_ON_SALES_PREPARED);
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(DEDUCT_QTY_ON_SALES_PREPARED);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(DEDUCT_QTY_ON_SALES_PREPARED);
		return resultStatus;
	}

	/***
	 * Verify 'Deduct Qty. on Sales Orders' check box status
	 */
	public boolean setCheckedDeductQtyOnSalesOrdersCheckbox( boolean expectedState )
	{
		wait.waitForElementDisplayed(DEDUCT_QTY_ON_SALES_ORDERS);
		boolean isChecked = checkbox.isCheckboxChecked(DEDUCT_QTY_ON_SALES_ORDERS);
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(DEDUCT_QTY_ON_SALES_ORDERS);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(DEDUCT_QTY_ON_SALES_ORDERS);
		return resultStatus;
	}

	/***
	 * Verify 'Deduct Qty. of Kit Assembly Demand' check box status
	 */
	public boolean setCheckedDeductQtyOfKitAssemblyDemandCheckbox( boolean expectedState )
	{
		wait.waitForElementDisplayed(DEDUCT_QTY_OF_KIT_ASSEMBLY_DEMAND);
		boolean isChecked = checkbox.isCheckboxChecked(DEDUCT_QTY_OF_KIT_ASSEMBLY_DEMAND);
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(DEDUCT_QTY_OF_KIT_ASSEMBLY_DEMAND);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(DEDUCT_QTY_OF_KIT_ASSEMBLY_DEMAND);
		return resultStatus;
	}

	/***
	 * Verify 'Deduct Qty. on Back Orders' check box status
	 */
	public boolean setCheckedDeductQtyOnBackOrdersCheckbox( boolean expectedState )
	{
		wait.waitForElementDisplayed(DEDUCT_QTY_ON_BACK_ORDERS);
		boolean isChecked = checkbox.isCheckboxChecked(DEDUCT_QTY_ON_BACK_ORDERS);
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(DEDUCT_QTY_ON_BACK_ORDERS);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(DEDUCT_QTY_ON_BACK_ORDERS);
		return resultStatus;
	}

	/***
	 * Verify 'Include Qty. on Receipts' check box status
	 */
	public boolean setCheckedIncludeQtyOnReceiptsCheckbox( boolean expectedState )
	{
		wait.waitForElementDisplayed(INCLUDE_QTY_ON_RECEIPTS);
		boolean isChecked = checkbox.isCheckboxChecked(INCLUDE_QTY_ON_RECEIPTS);
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(INCLUDE_QTY_ON_RECEIPTS);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(INCLUDE_QTY_ON_RECEIPTS);
		return resultStatus;
	}

	/***
	 * Verify 'Include Qty. in Transit' check box status
	 */
	public boolean setCheckedIncludeQtyInTransitCheckbox( boolean expectedState )
	{
		wait.waitForElementDisplayed(INCLUDE_QTY_IN_TRANSIT);
		boolean isChecked = checkbox.isCheckboxChecked(INCLUDE_QTY_IN_TRANSIT);
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(INCLUDE_QTY_IN_TRANSIT);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(INCLUDE_QTY_IN_TRANSIT);
		return resultStatus;
	}

	/***
	 * Verify 'Include Qty. on PO Receipts' check box status
	 */
	public boolean setCheckedIncludeQtyOnPOReceiptsCheckbox( boolean expectedState )
	{
		wait.waitForElementDisplayed(INCLUDE_QTY_ON_PO_RECEIPTS);
		boolean isChecked = checkbox.isCheckboxChecked(INCLUDE_QTY_ON_PO_RECEIPTS);
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(INCLUDE_QTY_ON_PO_RECEIPTS);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(INCLUDE_QTY_ON_PO_RECEIPTS);
		return resultStatus;
	}

	/***
	 * Verify 'Include Qty. on Purchase Prepared' check box status
	 */
	public boolean setCheckedIncludeQtyOnPurchasePreparedCheckbox( boolean expectedState )
	{
		wait.waitForElementDisplayed(INCLUDE_QTY_ON_PURCHANSE_PREPARED);
		boolean isChecked = checkbox.isCheckboxChecked(INCLUDE_QTY_ON_PURCHANSE_PREPARED);
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(INCLUDE_QTY_ON_PURCHANSE_PREPARED);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(INCLUDE_QTY_ON_PURCHANSE_PREPARED);
		return resultStatus;
	}

	/***
	 * Verify 'Include Qty. on Purchase Orders' check box status
	 */
	public boolean setCheckedIncludeQtyOnPurchaseOrdersCheckbox( boolean expectedState )
	{
		wait.waitForElementDisplayed(INCLUDE_QTY_ON_PURCHANSE_ORDERS);
		boolean isChecked = checkbox.isCheckboxChecked(INCLUDE_QTY_ON_PURCHANSE_ORDERS);
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(INCLUDE_QTY_ON_PURCHANSE_ORDERS);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(INCLUDE_QTY_ON_PURCHANSE_ORDERS);
		return resultStatus;
	}

	/***
	 * Verify 'Include Qty. of Kit Assembly Supply' check box status
	 */
	public boolean setCheckedIncludeQtyOfKitAssemblySupplyCheckbox( boolean expectedState )
	{
		wait.waitForElementDisplayed(INCLUDE_QTY_OF_KIT_ASSEMBLY_SUPPLY);
		boolean isChecked = checkbox.isCheckboxChecked(INCLUDE_QTY_OF_KIT_ASSEMBLY_SUPPLY);
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(INCLUDE_QTY_OF_KIT_ASSEMBLY_SUPPLY);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(INCLUDE_QTY_OF_KIT_ASSEMBLY_SUPPLY);
		return resultStatus;
	}

	/**
	 * Set Base Unit
	 *
	 * @param baseUnit
	 */
	public void setBaseUnit( String baseUnit )
	{
		text.setTextFieldCarefully(BASE_UNIT, baseUnit, false);
	}

	/**
	 * Set Sales Unit
	 *
	 * @param salesUnit
	 */
	public void setSalesUnit( String salesUnit )
	{
		text.setTextFieldCarefully(SALES_UNIT, salesUnit, false);
	}

	/**
	 * Set purchase unit
	 *
	 * @param purchaseUnit
	 */
	public void setPurchaseUnit( String purchaseUnit )
	{
		text.setTextFieldCarefully(PURCHASE_UNIT, purchaseUnit, false);
	}

	/**
	 * Get Base Unit
	 *
	 * @return
	 */
	public String getBaseUnit( )
	{
		wait.waitForElementDisplayed(BASE_UNIT);
		String baseUnit = attribute.getElementAttribute(BASE_UNIT, "value");
		return baseUnit;
	}

	/**
	 * Get Sales Unit
	 *
	 * @return
	 */
	public String getSalesUnit( )
	{
		wait.waitForElementDisplayed(SALES_UNIT);
		String salesUnit = attribute.getElementAttribute(SALES_UNIT, "value");
		return salesUnit;
	}

	/**
	 * Get Purchase Unit
	 */
	public String getPurchaseUnit( )
	{
		wait.waitForElementDisplayed(PURCHASE_UNIT);
		String purchaseUnit = attribute.getElementAttribute(PURCHASE_UNIT, "value");
		return purchaseUnit;
	}

	//element finder helper methods (putting logic in functions rather than locators)

	/**
	 * Overloads {@link #getDropdownOption(String, String)}
	 * to find an option in the item type dropdown
	 */
	protected WebElement getItemTypeOption( final String type )
	{
		WebElement itemTypeOption = getDropdownOption(itemTypeDropdownIdSuffix, type);
		return itemTypeOption;
	}

	/**
	 * Overloads {@link #getDropdownOption(String, String)}
	 * to find an option in the valuation method dropdown
	 */
	protected WebElement getValuationMethodOption( final String method )
	{
		WebElement valMethodOption = getDropdownOption(valMethodDropdownIdSuffix, method);
		return valMethodOption;
	}

	/**
	 * retrieves one of the options for a given dropdown
	 *
	 * @param dropdownIdSuffix which dropdown's options should be retrieved, as identified by
	 *                         the string at the end of that dropdown's 'id' attribute
	 * @param targetOptionText the label/value of the dropdown option that should be retrieved
	 *
	 * @return one of the options for a given dropdown
	 *
	 * @author ssalisbury
	 */
	protected WebElement getDropdownOption( final String dropdownIdSuffix, final String targetOptionText )
	{
		WebElement targetOption = null;

		WebElement dropdownOptionsTable = getDropdownOptionsTable(dropdownIdSuffix);
		if ( dropdownOptionsTable == null )
		{
			VertexLogger.log(targetOptionText);
			throw new RuntimeException("couldn't find the table of options for the item type dropdown");
		}
		List<WebElement> options = wait.waitForAllElementsPresent(dropdownOption, dropdownOptionsTable);

		for ( WebElement option : options )
		{
			String optionText = option.getAttribute("textContent");
			if ( optionText != null )
			{
				optionText = text.cleanseWhitespace(optionText);
				if ( targetOptionText.equals(optionText) )
				{
					targetOption = option;
					break;
				}
			}
		}

		return targetOption;
	}

	/**
	 * retrieves the table containing the options for a given dropdown
	 *
	 * @param dropdownIdSuffix which dropdown's options should be retrieved, as identified by
	 *                         the string at the end of that dropdown's 'id' attribute
	 *
	 * @return the table containing the options for a given dropdown
	 *
	 * @author ssalisbury
	 */
	protected WebElement getDropdownOptionsTable( final String dropdownIdSuffix )
	{
		WebElement optionsTable = null;

		final List<WebElement> dropdownOptionTables = wait.waitForAllElementsPresent(dropdownOptionsTable);
		for ( final WebElement table : dropdownOptionTables )
		{
			//the id of the dropdown that this stores the options of
			String relatedDropdownId = table.getAttribute("controlid");
			if ( relatedDropdownId != null && relatedDropdownId.endsWith(dropdownIdSuffix) )
			{
				optionsTable = table;
				break;
			}
		}

		return optionsTable;
	}
}
