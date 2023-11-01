package com.vertex.quality.connectors.ariba.portal.dialogs.requisition;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.ariba.portal.dialogs.base.AribaPortalDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * this class represents the edit charges dialog that appears
 * after selecting an item and taking edit charges action on it
 * @author osabha
 */
public class AribaEditChargesDialog extends AribaPortalDialog
{
	public AribaEditChargesDialog( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	protected final By dialogContLoc = By.className("w-dlg-inner-wrapper");
	protected final By chargeTypesListCont = By.className("w-dropdown-items");
	protected final By applyButtonLoc = By.className("w-btn-primary");
	protected final By chargeTypeFieldLoc = By.cssSelector("span[class='w-dropdown-selected']");
	protected final By amountFieldLoc = By.className("a-arc-charge-edit-currency");
	protected final By actionsButton = By.id("_$ugrg");
	protected final By editActions = By.id("editActions");
	protected final By editButton = By.className("_w-pmi-item");
	/**
	 * @param shippingAmount
	 */
	public void enterShippingAmount( String shippingAmount )
	{
		WebElement dialogContainer = wait.waitForElementDisplayed(dialogContLoc);
		WebElement amountField = wait.waitForElementEnabled(amountFieldLoc, dialogContainer);
		text.enterText(amountField, shippingAmount);
	}

	/**
	 * @param shippingAmount
	 */
	public void editShippingCharges( String shippingAmount )
	{
		WebElement dialogContainer = wait.waitForElementDisplayed(actionsButton);
		//WebElement chargeTypeField = wait.waitForElementDisplayed(chargeTypeFieldLoc, dialogContainer);
		click.clickElement(dialogContainer);
		wait.waitForElementDisplayed(editActions);
		element.selectElementByText(editButton, "Shipping Charges");
		enterShippingAmount(shippingAmount);
		click.clickElement(applyButtonLoc);
	}

	/**
	 * @param shippingAmount
	 *
	 * @author alewis
	 */
	public void editShippingChargesAaron( String shippingAmount )
	{
		WebElement dialogContainer = wait.waitForElementDisplayed(actionsButton);
		click.clickElement(dialogContainer);
		wait.waitForElementDisplayed(chargeTypesListCont);
		element.selectElementByText(chargeTypesListCont, "Shipping Charges");
		enterShippingAmount(shippingAmount);
		click.clickElement(applyButtonLoc);
	}
}
