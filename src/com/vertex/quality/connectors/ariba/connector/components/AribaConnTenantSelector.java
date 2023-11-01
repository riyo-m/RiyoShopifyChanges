package com.vertex.quality.connectors.ariba.connector.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * handles the choice of which tenant's settings are being configured on the
 * configuration menus of Ariba's Vertex Configuration site
 *
 * @author ssalisbury
 */
public class AribaConnTenantSelector extends VertexComponent
{
	protected final String variantIdAttributeName = "value";
	protected final By tenantSelectorLoc = By.id("tenantSel");

	public AribaConnTenantSelector( final WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * waits for the current configuration menu's tenant selector dropdown is possibly clickable,
	 * then clicks on the page's tenant selector
	 *
	 * @author ssalisbury
	 */
	public void clickTenantSelector( )
	{
		WebElement tenantSelector = wait.waitForElementEnabled(tenantSelectorLoc);
		click.clickElement(tenantSelector);
	}

	/**
	 * checks whether the user can see and potentially click on the tenant selector
	 *
	 * @return whether the user can see and potentially click on the tenant selector
	 *
	 * @author ssalisbury
	 */
	public boolean isTenantSelectorEnabled( )
	{
		boolean isSelectorEnabled = element.isElementEnabled(tenantSelectorLoc);
		return isSelectorEnabled;
	}

	/**
	 * checks whether a tenant with the given variant id currently exists
	 *
	 * @param tenantVariantId the variant id that the tenant should have
	 *
	 * @return whether a tenant with the given variant id currently exists
	 */
	public boolean doesTenantExist( final String tenantVariantId )
	{
		boolean isTenantDefined = false;

		WebElement tenantSelector = wait.waitForElementEnabled(tenantSelectorLoc);
		//waits for the tenant selector to load the data of which tenant is selected and display that instead of displaying no text
		// because it seems that that process also loads the data of what other tenants exist and can be selected in the dropdown
		wait.waitForElementNotHaveObsoleteText(tenantSelector, "");
		List<String> optionIds = dropdown.getDropdownValueOptions(tenantSelector);
		isTenantDefined = optionIds.contains(tenantVariantId);

		return isTenantDefined;
	}

	/**
	 * finds the id of the tenant which is currently selected for this selector
	 *
	 * @return the id of the tenant which is currently selected for this selector
	 */
	public String getSelectedTenantId( )
	{
		String currTenant = null;
		WebElement tenantSelector = wait.waitForElementEnabled(tenantSelectorLoc);
		WebElement selectedTenantOption = dropdown.getDropdownSelectedOption(tenantSelector);
		currTenant = attribute.getElementAttribute(selectedTenantOption, variantIdAttributeName);
		return currTenant;
	}

	/**
	 * finds the name of the tenant which is currently selected for this selector
	 *
	 * @return the name of the tenant which is currently selected for this selector
	 *
	 * @author ssalisbury
	 */
	public String getSelectedTenant( )
	{
		String currTenant = null;
		WebElement tenantSelector = wait.waitForElementEnabled(tenantSelectorLoc);
		//waits for the tenant selector to load the data of which tenant is selected and display that instead of displaying no text
		wait.waitForElementNotHaveObsoleteText(tenantSelector, "");
		WebElement selectedTenantOption = dropdown.getDropdownSelectedOption(tenantSelector);
		currTenant = text.getElementText(selectedTenantOption);
		return currTenant;
	}

	/**
	 * accesses the 'Tenant name' dropdown to change the current tenant based on
	 * that tenant's variant id
	 *
	 * @param newTenantId the variant id of the tenant to be chosen
	 *
	 * @author ssalisbury
	 */
	public void selectTenant( final String newTenantId )
	{
		jsWaiter.sleep(2000);
		WebElement tenantSelector = wait.waitForElementEnabled(tenantSelectorLoc);
		dropdown.selectDropdownByValue(tenantSelector, newTenantId);
		waitForPageLoad();
	}
}
