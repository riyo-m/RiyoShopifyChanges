package com.vertex.quality.connectors.ariba.connector.pages.configuration;

import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnMenuBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the Create or Modify Tenants page on the Ariba Connector's UI
 *
 * @author ssalisbury
 */
public class AribaConnManageTenantPage extends AribaConnMenuBasePage
{
	protected final By variantIdField = By.id("vid");
	protected final By displayNameField = By.id("displayName");

	protected final By createTenantButton = By.id("create");
	protected final By deleteTenantButton = By.id("delete");

	public AribaConnManageTenantPage( WebDriver driver )
	{
		super(driver, "Ariba Connector Tenant Maintenance");
	}

	/**
	 * checks whether the ariba variant id text field can be seen and interacted with
	 *
	 * @return whether the ariba variant id text field can be seen and interacted with
	 *
	 * @author ssalisbury
	 */
	public boolean isVariantIdEnabled( )
	{
		boolean isEnabled = element.isElementEnabled(variantIdField);
		return isEnabled;
	}

	/**
	 * checks whether the connector's display name text field can be seen and interacted with
	 *
	 * @return whether the connector's display name text field can be seen and interacted with
	 *
	 * @author ssalisbury
	 */
	public boolean isDisplayNameEnabled( )
	{
		boolean isEnabled = element.isElementEnabled(displayNameField);
		return isEnabled;
	}

	/**
	 * retrieves the variant Id string currently held by the Ariba Variant ID text field
	 *
	 * @return the variant Id string currently held by the Ariba Variant ID text field
	 */
	public String getVariantId( )
	{
		String variant = attribute.getElementAttribute(variantIdField, "value");
		return variant;
	}

	/**
	 * retrieves the displayed name string currently held by the Display Name text field
	 *
	 * @return the displayed name string currently held by the Display Name text field
	 */
	public String getDisplayName( )
	{
		String displayName = attribute.getElementAttribute(displayNameField, "value");
		return displayName;
	}

	/**
	 * sets the variant id of the currently selected tenant to a new value
	 *
	 * @param newVariantId the new ariba variant id value
	 *
	 * @author ssalisbury
	 */
	public void setVariantId( final String newVariantId )
	{
		text.enterText(variantIdField, newVariantId);
	}

	/**
	 * sets the display name of the currently selected tenant to a new value
	 *
	 * @param newDisplayName the new display name value
	 *
	 * @author ssalisbury
	 */
	public void setDisplayName( final String newDisplayName )
	{
		text.enterText(displayNameField, newDisplayName);
	}

	/**
	 * checks whether the create tenant button can be seen and clicked
	 *
	 * @return whether the create tenant button can be seen and clicked
	 *
	 * @author ssalisbury
	 */
	public boolean isCreateTenantButtonEnabled( )
	{
		boolean isEnabled = element.isElementEnabled(createTenantButton);
		return isEnabled;
	}

	/**
	 * creates a new tenant in the connector for the current account
	 *
	 * @author ssalisbury
	 */
	public void createNewTenant( )
	{
		click.clickElementCarefully(createTenantButton);
	}

	/**
	 * checks whether the delete tenant button can be seen and clicked
	 *
	 * @return whether the delete tenant button can be seen and clicked
	 *
	 * @author ssalisbury
	 */
	public boolean isDeleteTenantButtonEnabled( )
	{
		boolean isEnabled = element.isElementEnabled(deleteTenantButton);
		return isEnabled;
	}

	/**
	 * deletes the currently selected tenant
	 * and also removes that tenant from the database
	 * without having to press the submit button
	 *
	 * @author ssalisbury
	 */
	public void deleteSelectedTenant( )
	{
		click.clickElementCarefully(deleteTenantButton);
	}

	public void checkDeleteDisabled(){
		wait.waitForElementNotEnabled(deleteTenantButton);
	}
}
