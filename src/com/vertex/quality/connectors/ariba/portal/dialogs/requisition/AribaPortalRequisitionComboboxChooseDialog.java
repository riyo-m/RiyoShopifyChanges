package com.vertex.quality.connectors.ariba.portal.dialogs.requisition;

import com.vertex.quality.common.enums.PageScrollDestination;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.ariba.portal.dialogs.base.AribaPortalDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * dialog popup for choosing the value in a combobox-type text input field out of a set of
 * valid values for that field
 *
 * @author ssalisbury dgorecki
 */
public class AribaPortalRequisitionComboboxChooseDialog extends AribaPortalDialog
{
	protected final String doneButtonText = "Done";
	protected By DIALOG_CLASS = By.className("w-dlg-dialog");
	protected By DIALOG_BUTTONS_CONTAINER_CLASS = By.className("w-dlg-buttons");
	protected By DIALOG_BUTTON_TAG = By.tagName("button");

	public AribaPortalRequisitionComboboxChooseDialog( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * Clicks the Done button on the dialog
	 */
	public void clickDoneButton( )
	{
		WebElement doneButton = findDoneButton();

		wait.waitForElementDisplayed(doneButton);

		click.clickElement(doneButton, PageScrollDestination.VERT_CENTER);
	}

	/**
	 * Helper method for finding the Done button on the dialog
	 *
	 * @return the Done button if found, otherwise null
	 *
	 * @author ssalisbury dgorecki
	 */
	protected WebElement findDoneButton( )
	{
		WebElement doneButton = null;

		WebElement dialog = wait.waitForElementEnabled(DIALOG_CLASS);
		WebElement buttonContainer = wait.waitForElementEnabled(DIALOG_BUTTONS_CONTAINER_CLASS, dialog);
		List<WebElement> buttons = wait.waitForAllElementsEnabled(DIALOG_BUTTON_TAG, buttonContainer);

		doneButton = element.selectElementByText(buttons, doneButtonText);
		return doneButton;
	}
}
