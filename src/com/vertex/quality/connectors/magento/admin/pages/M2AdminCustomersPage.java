package com.vertex.quality.connectors.magento.admin.pages;

import com.vertex.quality.connectors.magento.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Representation of the Customers Page
 *
 * @author alewis
 */
public class M2AdminCustomersPage extends MagentoAdminPage
{
	protected By customerRowClass = By.className("data-row");
	protected By customerCellsTag = By.tagName("td");
	protected By editCustomerButtonClass = By.className("action-menu-item");
	protected By customerCheckboxClass = By.className("admin__control-checkbox");

	protected By headerClass = By.className("sticky-header");
	protected By actionsMenuClass = By.className("action-select-wrap");
	protected By actionMenuItemsClass = By.className("action-menu");
	protected By actionItemTag = By.tagName("li");

	protected By popupButtonsClass = By.className("modal-footer");
	protected By okButtonClass = By.className("action-accept");

	protected By messageSuccessClass = By.className("message-success");
	By maskClass = By.className("loading-mask");

	public M2AdminCustomersPage( WebDriver driver )
	{
		super(driver);
	}

	private WebElement findCustomerByName( String customerName )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		WebElement customerRow = null;
		List<WebElement> rowList = wait.waitForAllElementsPresent(customerRowClass);

		for ( WebElement row : rowList )
		{
			List<WebElement> cellList = wait.waitForAllElementsPresent(customerCellsTag, row);
			WebElement nameCell = cellList.get(2);
			String nameFound = nameCell.getText();

			if ( customerName.equals(nameFound) )
			{
				customerRow = row;
				break;
			}
		}
		return customerRow;
	}

	/**
	 * checks to see if the messages on top of page match the correct string
	 *
	 * @param correctMessage
	 *
	 * @return a boolean if the string matches
	 */
	public boolean checkMessage( String correctMessage )
	{
		boolean match = false;
		WebElement messageSuccess = element.selectElementByText(messageSuccessClass, correctMessage);
		if ( messageSuccess != null )
		{
			match = true;
		}
		return match;
	}

	public M2AdminCustomerInformationPage editCustomerByName( String firstName, String lastName )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		String customerName = String.format("%s %s", firstName, lastName);

		WebElement customer = findCustomerByName(customerName);

		WebElement editButton = wait.waitForElementEnabled(editCustomerButtonClass, customer);

		click.clickElementCarefully(editButton);

		M2AdminCustomerInformationPage informationPage = initializePageObject(M2AdminCustomerInformationPage.class);

		return informationPage;
	}

	public boolean selectCustomerCheckboxByName( String firstName, String lastName )
	{
		boolean verifyCustomer;
		waitForPageLoad();
		wait.waitForElementNotDisplayed(maskClass);
		String customerName = String.format("%s %s", firstName, lastName);

		WebElement customer = findCustomerByName(customerName);
		if (!(customer == null)){
			WebElement checkbox = wait.waitForElementEnabled(customerCheckboxClass, customer);
			click.clickElementCarefully(checkbox);
			verifyCustomer = true;
		}
		else {
			verifyCustomer = false;
		}
		
		return verifyCustomer;
	}

	public void selectDeleteAction( )
	{
		WebElement container = wait.waitForElementDisplayed(headerClass);

		WebElement actionMenu = wait.waitForElementEnabled(actionsMenuClass, container);

		actionMenu.click();

		WebElement actionItems = wait.waitForElementDisplayed(actionMenuItemsClass, container);

		List<WebElement> itemsList = wait.waitForAllElementsEnabled(actionItemTag, actionItems);

		WebElement delete = element.selectElementByText(itemsList, "Delete");

		delete.click();

		WebElement popup = wait.waitForElementDisplayed(popupButtonsClass);
		WebElement okButton = wait.waitForElementEnabled(okButtonClass, popup);

		click.clickElementCarefully(okButton);
	}
}
