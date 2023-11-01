package com.vertex.quality.connectors.kibo.dialog;

import com.vertex.quality.common.dialogs.VertexDialog;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * This class represents the edit contacts dialog that pops up after clicking on change address
 * for the customer account
 * contains all the methods necessary to interact with the dialog
 *
 * @author osabha
 */
public class KiboMaxineEditContactsDialog extends VertexDialog
{
	public KiboMaxineEditContactsDialog( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	protected By loadMaskLoc = By.className("x-mask");
	protected By editAddressButtonSupConLoc = By.className("taco-customer-contacts");
	protected By editAddressButtonConLoc = By.className("address");
	protected By editAddressCheckBox = By.className("x-form-cb-wrap-inner");
	protected By editAddressButtonLoc = By.className("x-btn-button");
	protected By saveButtonConLoc = By.className("taco-window-action-toolbar");
	protected By saveButtonLoc = By.className("x-btn-action-primary-toolbar-medium");

	/**
	 * getter method to locate the edit button for the shipping address
	 *
	 * @return returns edit shipping address button as WebElement
	 */
	protected WebElement getEditShippingAddressButton( )
	{
		WebElement editButton;
		String expectedButtonText = "Edit";
		String expectedText = "DEFAULT SHIPPING";
		WebElement editAddressButtonSupContainer = wait.waitForElementPresent(editAddressButtonSupConLoc);
		List<WebElement> editAddressButtonContainers = wait.waitForAllElementsPresent(editAddressButtonConLoc,
			editAddressButtonSupContainer);
		WebElement billingAddressEditCont = element.selectElementByText(editAddressButtonContainers, expectedText);
		List<WebElement> editButtonClasses = wait.waitForAllElementsPresent(editAddressButtonLoc,
			billingAddressEditCont);
		editButton = element.selectElementByText(editButtonClasses, expectedButtonText);

		return editButton;
	}

	/**
	 * getter method to locate the edit button for the billing address
	 *
	 * @return returns edit billing address button as WebElement
	 */
	protected WebElement getEditBillingAddressButton( )
	{
		WebElement editButton;
		String expectedButtonText = "Edit";
		String expectedText = "DEFAULT BILLING";
		WebElement editAddressButtonSupContainer = wait.waitForElementPresent(editAddressButtonSupConLoc);
		List<WebElement> editAddressButtonContainers = wait.waitForAllElementsPresent(editAddressButtonConLoc,
			editAddressButtonSupContainer);
		WebElement billingAddressEditCont = element.selectElementByText(editAddressButtonContainers, expectedText);
		List<WebElement> editButtonClasses = wait.waitForAllElementsPresent(editAddressButtonLoc,
			billingAddressEditCont);
		editButton = element.selectElementByText(editButtonClasses, expectedButtonText);

		return editButton;
	}

	/**
	 * uses the getter method to locate the edit billing address button and then clicks on it
	 */
	public void clickEditBillingAddress( )
	{
		WebElement editButton = getEditBillingAddressButton();
		editButton.click();
	}

	/**
	 * uses the getter method to locate the edit shipping address button and then clicks on it
	 */
	public void clickEditShippingAddress( )
	{
		WebElement editButton = getEditShippingAddressButton();
		editButton.click();
	}

	/**
	 * located and clicks the save button on the edit contacts dialog
	 */
	public void clickSaveButton( )
	{
		WebElement saveButtonContainer = wait.waitForElementPresent(saveButtonConLoc);
		WebElement saveButton = wait.waitForElementPresent(saveButtonLoc, saveButtonContainer);
		saveButton.click();
		List<WebElement> masks = wait.waitForAllElementsPresent(loadMaskLoc);
		for ( WebElement mask : masks )
		{
			try
			{
				wait.waitForElementNotDisplayed(mask);
			}
			catch ( Exception e )
			{
				System.out.println("Load mask isn't present");
			}
		}
	}

	/**
	 * to select the check box ( ship to this address for the shipping address)
	 */
	public void clickShipToThisAddress( )
	{
		WebElement checkBox;
		String expectedCheckBoxText = "Ship to this address";
		String expectedText = "DEFAULT SHIPPING";
		WebElement editAddressButtonSupContainer = wait.waitForElementPresent(editAddressButtonSupConLoc);
		List<WebElement> editAddressButtonContainers = wait.waitForAllElementsPresent(editAddressButtonConLoc,
			editAddressButtonSupContainer);
		WebElement checkBoxCont = element.selectElementByText(editAddressButtonContainers, expectedText);
		List<WebElement> checkBoxClasses = wait.waitForAllElementsPresent(editAddressCheckBox, checkBoxCont);
		checkBox = element.selectElementByText(checkBoxClasses, expectedCheckBoxText);
		click.clickElement(checkBox);
	}
}

