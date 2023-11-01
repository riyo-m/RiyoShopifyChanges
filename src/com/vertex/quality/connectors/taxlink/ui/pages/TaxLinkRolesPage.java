package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.Roles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.List;

/**
 * this class represents all the locators and methods for Roles Tab contained
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkRolesPage extends TaxLinkBasePage
{
	public TaxLinkRolesPage( final WebDriver driver ) throws IOException
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@col-id='roleName']")
	private List<WebElement> roleNamePresentation;

	@FindBy(xpath = "//div[@col-id='roleDiscription']")
	private List<WebElement> roleDescPresentation;

	private By roleDescByRoleName = By.xpath(".//following-sibling::div");

	/**
	 * Method to verify Title of Roles Page
	 *
	 * @return boolean
	 */
	public boolean verifyTitleRolesPage( )
	{
		boolean flagTitleRolesPage = false;
		String viewRolesTitle = wait
			.waitForElementDisplayed(summaryPageTitle)
			.getText();
		if ( viewRolesTitle.equalsIgnoreCase(Roles.ROLES.pageHeaderTextRoles) )
		{
			flagTitleRolesPage = true;
		}
		return flagTitleRolesPage;
	}

	/**
	 * Method to verify View Roles Page
	 *
	 * @return boolean
	 */
	public boolean viewRoles( )
	{
		boolean flagViewRoles = false, flagFIRole = false, flagOnboardingRole = false, flagReadOnlyUserRole = false,
			flagUserRole = false, flagUserMaintenanceRole = false;
		String roleDescText, roleNameInUI;

		navigateToRoles();
		if ( verifyTitleRolesPage() )
		{
			int count = roleNamePresentation.size();
			for ( int i = 1 ; i < count ; i++ )
			{
				if ( !roleNamePresentation
					.get(i)
					.getText()
					.equals("Role Name") )
				{
					roleNameInUI = roleNamePresentation
						.get(i)
						.getText();
					jsWaiter.sleep(1500);
					if ( roleNameInUI.equals(Roles.ROLES.onboardingRoleNameText) )
					{
						roleDescText = roleNamePresentation
							.get(i)
							.findElement(roleDescByRoleName)
							.getText();
						if ( roleDescText.equals(Roles.ROLES.onboardingRoleDescText) )
						{
							jsWaiter.sleep(100);
							flagOnboardingRole = true;
						}
					}
					if ( roleNameInUI.equals(Roles.ROLES.FIRoleNameText) )
					{
						roleDescText = roleNamePresentation
							.get(i)
							.findElement(roleDescByRoleName)
							.getText();
						if ( roleDescText.equals(Roles.ROLES.FIRoleDescText) )
						{
							jsWaiter.sleep(100);
							flagFIRole = true;
						}
					}
					if ( roleNameInUI.equals(Roles.ROLES.userRoleNameText) )
					{
						roleDescText = roleNamePresentation
							.get(i)
							.findElement(roleDescByRoleName)
							.getText();
						if ( roleDescText.equals(Roles.ROLES.userRoleDescText) )
						{
							jsWaiter.sleep(100);
							flagUserRole = true;
						}
					}
					if ( roleNameInUI.equals(Roles.ROLES.readOnlyUserRoleNameText) )
					{
						roleDescText = roleNamePresentation
							.get(i)
							.findElement(roleDescByRoleName)
							.getText();
						if ( roleDescText.equals(Roles.ROLES.readOnlyUserRoleDescText) )
						{
							jsWaiter.sleep(100);
							flagReadOnlyUserRole = true;
						}
					}
					if ( roleNameInUI.equals(Roles.ROLES.userMaintenanceRoleNameText) )
					{
						roleDescText = roleNamePresentation
							.get(i)
							.findElement(roleDescByRoleName)
							.getText();
						if ( roleDescText.equals(Roles.ROLES.userMaintenanceRoleDescText) )
						{
							jsWaiter.sleep(100);
							flagUserMaintenanceRole = true;
						}
					}
				}
			}
			if ( flagOnboardingRole && flagFIRole && flagUserRole && flagReadOnlyUserRole && flagUserMaintenanceRole )
			{
				VertexLogger.log("Role names viewed against Role Description.. Passed!");
				flagViewRoles = true;
			}
		}
		return flagViewRoles;
	}
}
