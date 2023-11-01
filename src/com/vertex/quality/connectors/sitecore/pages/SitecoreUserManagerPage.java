package com.vertex.quality.connectors.sitecore.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.sitecore.pages.base.SitecoreBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Sitecore User Manager page - deals with all re-usable methods specific to
 * this page.
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public class SitecoreUserManagerPage extends SitecoreBasePage
{

	protected By newButton = By.className("scRibbonToolbarLargeButton");

	protected By createUserIframe = By.className("ui-dialog-normal");
	protected By selectRolesIframe = By.cssSelector("[id*='ContentIframeId'][src*='SelectRoles']");
	protected By confirmMessageIframe = By.className("ui-dialog-normal");

	protected String commonIdPart = "CreateUserWizard_CreateUserStepContainer_";

	protected By usernameInput = By.id(String.format("%sUserName", commonIdPart));
	protected By domainDoprdown = By.id(String.format("%sDomain", commonIdPart));
	protected By fullNameInput = By.id(String.format("%sFullName", commonIdPart));
	protected By emailInput = By.id(String.format("%sEmail", commonIdPart));
	protected By commentInput = By.id(String.format("%sDescription", commonIdPart));
	protected By passwordInput = By.id(String.format("%sPassword", commonIdPart));
	protected By confirmPasswordInput = By.id(String.format("%sConfirmPassword", commonIdPart));
	protected By editRolesButton = By.id(String.format("%sAddRoles", commonIdPart));
	protected By rolesSearchInput = By.className("SearchBox");
	protected By okButton = By.id("OK");
	protected By searchResultRow = By.className("Row");

	protected By nextButton = By.className("scButtonPrimary");
	protected By closeButton = By.id("Finish");

	protected By successMessage = By.className("scDialogContentContainer");
	protected By errorMessage = By.cssSelector("[class*='CreateNewUserFormValidationSummary']");

	protected By customerSearchInput = By.id("Users_searchBox");
	protected By customerSearchResultRow = By.className("Row");
	protected By deleteButton = By.className("header");
	protected By confirmMessage = By.id("ConfirmMessage");
	protected By deleteOkButton = By.id("OK");
	protected By selectRow = By.className("Row");
	protected By headingCell = By.className("HeadingCell");
	protected By dataCell = By.className("DataCell");

	public SitecoreUserManagerPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * This method is used to get the specific user row element By object based on
	 * the given username & domain
	 *
	 * @param username of user
	 * @param domain   of user
	 *
	 * @return - row of user
	 */
	private WebElement getUserRow( final String username, final String domain )
	{
		//TODO get rid of xpath
		int usernameIndex = -1;
		int domainIndex = -1;
		List<WebElement> headings = element.getWebElements(headingCell);
		int index = 0;
		for ( WebElement heading : headings )
		{
			if ( text
				.getElementText(heading)
				.equals("User name") )
			{
				usernameIndex = index;
			}
			else if ( text
				.getElementText(heading)
				.equals("Domain") )
			{
				domainIndex = index;
			}
			index++;
		}

		List<WebElement> rows = element.getWebElements(selectRow);
		for ( WebElement row : rows )
		{
			List<WebElement> rowElements = element.getWebElements(dataCell, row);
			String usernameText = text.getElementText(rowElements.get(usernameIndex));
			String domainText = text.getElementText(rowElements.get(domainIndex));
			if ( usernameText.contains(username) && domainText.contains(domainText) )
			{
				return row;
			}
		}
		return null;
	}

	/**
	 * This method is used to click the "new" button to create a new user.
	 */
	public void clickCreateNewUserButton( )
	{
		List<WebElement> newUserLocators = wait.waitForAllElementsEnabled(newButton);
		String newButtonText = "New";
		WebElement newButtonElement = element.selectElementByText(newUserLocators, newButtonText);
		click.clickElement(newButtonElement);
		waitForPageLoad();
	}

	/**
	 * switch frame to create user frame
	 */
	private void switchToCreateUserFrame( )
	{
		this.switchToOuterFrame();
		window.waitForAndSwitchToFrame(createUserIframe);
	}

	/**
	 * switch frame to select roles frame
	 */
	private void switchToSelectRolesFrame( )
	{
		this.switchToOuterFrame();
		window.waitForAndSwitchToFrame(selectRolesIframe);
	}

	/**
	 * switch frame to confirm message frame
	 */
	private void switchToConfirmMessageFrame( )
	{
		this.switchToOuterFrame();
		window.waitForAndSwitchToFrame(confirmMessageIframe);
	}

	/**
	 * This method is used to set the user name for new user which we are creating.
	 *
	 * @param username to set
	 */
	public void setUsername( final String username )
	{
		this.switchToCreateUserFrame();
		wait.waitForElementPresent(usernameInput);
		text.enterText(usernameInput, username);
	}

	/**
	 * This method is used to select the domain for new user which we are creating.
	 *
	 * @param domain to select
	 */
	public void selectDomain( final String domain )
	{
		wait.waitForElementPresent(domainDoprdown);
		dropdown.selectDropdownByDisplayName(domainDoprdown, domain);
	}

	/**
	 * This method is used to set the full name for new user which we are creating.
	 *
	 * @param name full name to set
	 */
	public void setFullName( final String name )
	{
		wait.waitForElementPresent(fullNameInput);
		text.enterText(fullNameInput, name);
	}

	/**
	 * This method is used to set the Email for new user which we are creating.
	 *
	 * @param email to set
	 */
	public void setEmail( final String email )
	{
		wait.waitForElementPresent(emailInput);
		text.enterText(emailInput, email);
	}

	/**
	 * This method is used to set the comment for new user which we are creating.
	 *
	 * @param comment to set
	 */
	public void setComment( final String comment )
	{
		wait.waitForElementPresent(commentInput);
		text.enterText(commentInput, comment);
	}

	/**
	 * This method is used to set the user password for new user which we are
	 * creating.
	 *
	 * @param password to set
	 */
	public void setPassword( final String password )
	{
		wait.waitForElementPresent(passwordInput);
		text.enterText(passwordInput, password);
	}

	/**
	 * This method is used to confirm the user password for new user which we are
	 * creating.
	 *
	 * @param password to enter
	 */
	public void confirmPassword( final String password )
	{
		wait.waitForElementPresent(confirmPasswordInput);
		text.enterText(confirmPasswordInput, password);
	}

	/**
	 * This method is used to set the user role for new user which we are creating.
	 *
	 * @param roleName to add
	 */
	public void setRole( final String roleName )
	{
		editRoles(roleName, "Add");
	}

	/**
	 * This method is used to remove the user role
	 *
	 * @param roleName to remove
	 */
	public void removeRole( final String roleName )
	{
		editRoles(roleName, "Remove");
	}

	/**
	 * This method is used to edit the user roles (i.e either add or remove).
	 *
	 * @param roleName    - Name of the role to edit
	 * @param addOrRemove - takes either "Add" or "Remove" only
	 */
	private void editRoles( final String roleName, final String addOrRemove )
	{

		wait.waitForElementPresent(editRolesButton);
		click.clickElement(editRolesButton);
		waitForPageLoad();
		switchToSelectRolesFrame();

		By selectRoleBy = By.xpath(
			String.format("//select[contains(@id, 'SelectedRoles')]/option[contains(text(), '%s')]", roleName));

		if ( addOrRemove.equalsIgnoreCase("Remove") )
		{

			if ( element.isElementDisplayed(selectRoleBy) )
			{

				click.clickElement(selectRoleBy);

				final String removeButtonText = "Remove";
				WebElement removeButton = element.getButtonByText(removeButtonText);
				click.clickElement(removeButton);
				waitForPageLoad();
			}
			else
			{
				String alreadySelectedMessage = String.format("Role: \"%s\" is already selected", roleName);
				VertexLogger.log(alreadySelectedMessage, getClass());
			}
		}
		else if ( addOrRemove.equalsIgnoreCase("Add") )
		{

			if ( element.isElementDisplayed(selectRoleBy) )
			{
				String alreadySelectedMessage = String.format("Role: \"%s\" is already selected", roleName);
				VertexLogger.log(alreadySelectedMessage, getClass());
			}
			else
			{
				// search role (positive case (i.e. result should found))
				searchRole(roleName, true);

				//TODO get rid of xpath
				By requiredRoleBy = By.xpath(
					String.format("//*[contains(@class, 'Row') and contains(@id, 'Roles_row')]//*[text()='%s']",
						roleName));

				wait.waitForElementDisplayed(requiredRoleBy);
				String roleMessage = String.format("Role: %s", roleName);
				click.clickElement(requiredRoleBy);
				String addButtonText = "Add";
				WebElement addButton = element.getButtonByText(addButtonText);
				click.clickElement(addButton);
				wait.waitForElementDisplayed(selectRoleBy);
			}
		}
		else
		{
			VertexLogger.log("Please provide a valid edit option to edit the Roles (i.e. either 'Add' or 'Remove')",
				getClass());
		}

		// click OK Button
		clickOkButton();
	}

	/**
	 * This method is used to search the given role
	 *
	 * @param roleName       role name to search for
	 * @param isPositiveCase boolean is result found or not
	 */
	public void searchRole( final String roleName, final boolean isPositiveCase )
	{

		wait.waitForElementDisplayed(rolesSearchInput);
		text.enterText(rolesSearchInput, roleName + Keys.ENTER);
		waitForPageLoad();

		if ( isPositiveCase )
		{
			if ( element.isElementDisplayed(searchResultRow) )
			{
				VertexLogger.log(String.format("Search successful, Result found for the role/text: \"%s\"", roleName),
					getClass());
			}
			else
			{
				VertexLogger.log(String.format("No result found for the role/text: \"%s\"", roleName),
					VertexLogLevel.ERROR, getClass());
			}
		}
		else
		{
			if ( element.isElementDisplayed(searchResultRow) )
			{
				VertexLogger.log(String.format("No result found for the role/text: \"%s\"", roleName), getClass());
			}
			else
			{
				VertexLogger.log(String.format("Search failed, Result found for the role/text: \"%s\"", roleName),
					VertexLogLevel.ERROR, getClass());
			}
		}
	}

	/**
	 * this method is used to click the OK button
	 */
	public void clickOkButton( )
	{
		wait.waitForElementDisplayed(okButton);
		click.clickElement(okButton);
		waitForPageLoad();
	}

	/**
	 * This method is used to click the Next button
	 */
	public void clickNextButton( )
	{

		switchToCreateUserFrame();
		wait.waitForElementDisplayed(nextButton);
		click.clickElement(nextButton);
		waitForPageLoad();
	}

	/**
	 * This method is used to get the validation message while creating the new
	 * user.
	 *
	 * @return - displayed validation message.
	 */
	public String getValidationMessage( )
	{

		String msg = null;

		if ( element.isElementDisplayed(errorMessage) )
		{
			msg = text.getElementText(errorMessage);
			VertexLogger.log(msg, VertexLogLevel.ERROR, getClass());
		}
		else if ( element.isElementDisplayed(successMessage) )
		{
			msg = text.getElementText(successMessage);
			VertexLogger.log(msg, getClass());
		}

		if ( msg != null )
		{
			msg = msg.trim();
		}
		else
		{
			VertexLogger.log("No Message displayed", getClass());
		}
		return msg;
	}

	/**
	 * This method is used to click the Close button
	 */
	public void clickCloseButton( )
	{

		switchToCreateUserFrame();
		wait.waitForElementDisplayed(closeButton);
		click.clickElement(closeButton);
		waitForPageLoad();
	}

	/**
	 * This method is used to search user/customer based on the given details.
	 *
	 * @param customerName customer name to search
	 * @param domain       to search for
	 *
	 * @return - true - if user found else returns false.
	 */
	public boolean searchCustomer( final String customerName, final String domain )
	{

		boolean resultFlag = false;
		waitForPageLoad();
		wait.waitForElementDisplayed(customerSearchInput, 1);
		text.enterText(customerSearchInput, customerName + Keys.ENTER);
		waitForPageLoad();
		WebElement userRowBy = getUserRow(customerName, domain);
		if ( userRowBy == null )
		{
			return false;
		}
		if ( element.isElementDisplayed(userRowBy) )
		{
			waitForPageLoad();
			VertexLogger.log(
				String.format("Search successful, Result found for the given customer name: \"%s\"", customerName),
				getClass());
			resultFlag = true;
		}
		return resultFlag;
	}

	/**
	 * This method is used to click the Delete button
	 */
	public void clickDeleteButton( )
	{
		List<WebElement> btns = wait.waitForAllElementsEnabled(deleteButton);
		final String expectedBtnTxt = "Delete";
		WebElement deleteBtn = element.selectElementByText(btns, expectedBtnTxt);
		wait.waitForElementEnabled(deleteBtn, 1);
		click.clickElement(deleteBtn);
		waitForPageLoad();
	}

	/**
	 * This method is used to get the confirmation message while deleting a user.
	 *
	 * @return confirm message that user was deleted
	 */
	public String getConfirmMessage( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(confirmMessage);
		String msg = text.getElementText(confirmMessage);
		return msg;
	}

	/**
	 * This method is used to click the confirm delete button.
	 *
	 * @param username to search for and delete
	 */
	public void confirmDelete( final String username )
	{

		switchToConfirmMessageFrame();
		String msg = getConfirmMessage();

		if ( msg.contains(username) )
		{
			wait.waitForElementDisplayed(deleteOkButton);
			click.clickElement(deleteOkButton);
			wait.waitForElementNotPresent(customerSearchResultRow);
			waitForPageLoad();
		}
		else
		{
			String unconfirmedUsernameDeletionMessage = String.format(
				"Username: \"%s\" is not found in the delete confirmation message", username);
			VertexLogger.log(unconfirmedUsernameDeletionMessage, VertexLogLevel.ERROR, getClass());
		}
	}

	/**
	 * This method is used to create a new user by setting the all mandatory values.
	 *
	 * @param username of user
	 * @param domain   of user
	 * @param fullName of user
	 * @param email    of user
	 * @param comment  of user
	 * @param password of user
	 * @param roleName of user
	 *
	 * @return - validation message (either success or failure message).
	 */
	public String createNewUser( final String username, final String domain, final String fullName, final String email,
		final String comment, final String password, final String roleName )
	{

		clickCreateNewUserButton();
		setUsername(username);
		selectDomain(domain);
		setFullName(fullName);
		setEmail(email);
		setComment(comment);
		setPassword(password);
		confirmPassword(password);
		setRole(roleName);
		clickNextButton();
		String msg = getValidationMessage();
		clickCloseButton();

		return msg;
	}

	/**
	 * This method is used to delete if the given user is available
	 *
	 * @param username of user to delete
	 * @param domain   of user to delete
	 */
	public void deleteUserIfExist( final String username, final String domain )
	{

		if ( searchCustomer(username, domain) )
		{
			deleteUser(username, domain);
		}
	}

	/**
	 * This method is used to delete the given user and validates whether the user
	 * is deleted or not
	 *
	 * @param username of user to delete
	 * @param domain   of user to delete
	 */
	public void deleteUser( final String username, final String domain )
	{

		boolean isUserAvailable = searchCustomer(username, domain);
		final String unavailableUserMessage = String.format(
			"Required user: \"%s\" in \"%s\" domain is not found in the search result to delete.", username, domain);
		assertTrue(isUserAvailable, unavailableUserMessage);

		waitForPageLoad();
		wait.waitForElementDisplayed(deleteButton, 1);

		// selecting the required user row
		WebElement userRowBy = getUserRow(username, domain);
		wait.waitForElementDisplayed(userRowBy, 1);
		wait.waitForElementEnabled(userRowBy, 1);
		click.javascriptClick(userRowBy);
		waitForPageLoad();

		// click delete button and confirm
		clickDeleteButton();
		confirmDelete(username);

		// validating whether the user is deleted or not
		isUserAvailable = searchCustomer(username, domain);

		String failedUserDeleteMessage = String.format("User: \"%s\" in \"%s\" domain is not deleted.", username,
			domain);
		assertFalse(isUserAvailable, failedUserDeleteMessage);
	}
}
