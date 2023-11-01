package com.vertex.quality.connectors.ariba.portal.pages.requisition;

import com.vertex.quality.common.enums.PageScrollDestination;
import com.vertex.quality.connectors.ariba.portal.components.requisition.AribaPortalRequisitionLineItemsTable;
import com.vertex.quality.connectors.ariba.portal.components.requisition.AribaPortalRequisitionToolbar;
import com.vertex.quality.connectors.ariba.portal.dialogs.requisition.AribaEditChargesDialog;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * the representation of the checkout page for shopping in the catalog
 *
 * @author legacyAribaProgrammer ssalisbury
 */
public class AribaPortalRequisitionCheckoutPage extends AribaPortalPostLoginBasePage {
	protected final By contentBody = By.id("contentAlley");
	protected final By cartSummaryPane = By.className("a-appr-subtot");
	protected final By cartSummaryCostLabel = By.className("approvable_subtotal_supplier");
	protected final By cartSummaryCostValue = By.className("approvable_subtotal_supplier_price");
	protected final By summaryViewContainer = By.className("a-arc-review-frame-tbl");
	protected final By updateLoadingPopupLoc = By.id("awwaitMessage");
	protected final By taxesLoc = By.className("mls");
	protected final By nameField = By.className("mls");

	protected final int shortTimeOut = 5;

	public AribaPortalRequisitionLineItemsTable itemsTable;
	public AribaPortalRequisitionToolbar toolbar;
	public AribaEditChargesDialog chargesDialog;

	protected By alertMessageLoc = By.className("pageSubHead");
	protected By doneButtonLoc = By.className("w-btn-primary");

	public AribaPortalRequisitionCheckoutPage(WebDriver driver) {
		super(driver);

		this.itemsTable = new AribaPortalRequisitionLineItemsTable(driver, this);
		this.toolbar = new AribaPortalRequisitionToolbar(driver, this);
		this.chargesDialog = new AribaEditChargesDialog(driver, this);
	}

	/**
	 * sets the title of the PR
	 *
	 * @param title the title that the PR should have
	 * @author legacyAribaProgrammer ssalisbury
	 */
	public void setRequisitionTitle(final String title) {
		wait.waitForElementPresent(summaryViewContainer);

		WebElement fieldElement = getFieldByLabelText("Title:");
		fieldElement.sendKeys(title);
	}

	/**
	 * sets the company code of the PR
	 *
	 * @param companycode the company code that the PR should have
	 * @author legacyAribaProgrammer ssalisbury
	 */
	public void setRequisitionCompanyCode(final String companycode) {
		wait.waitForElementPresent(summaryViewContainer);

		// Fill up Company Code text box;
		WebElement fieldElement = getFieldByLabelText("Company Code:");
		fieldElement.clear();
		fieldElement.sendKeys(companycode);
	}

	/**
	 * clicks the button that sends a request to the Ariba O-Series connector in order to
	 * update the PR's taxes
	 *
	 * @author legacyAribaProgrammer ssalisbury
	 */
	public void updateTaxes() {
		waitForPageLoad();
		WebElement updateTaxesButton = element.getButtonByText("Update Taxes");

		if (updateTaxesButton == null) {
			updateTaxesButton = element.getButtonByText("Update Taxes");
		}

		click.clickElement(updateTaxesButton, PageScrollDestination.VERT_CENTER);

		try {
			wait.waitForElementDisplayed(updateLoadingPopupLoc, 5);
		} catch (TimeoutException e) {
			// for one reason or other, clicking update taxes didn't result in a long enough
			// delay for the 'updating' popup to appear
		}
		wait.waitForElementNotDisplayed(updateLoadingPopupLoc, DEFAULT_TIMEOUT);
	}

	/**
	 * finds an input field with the given label
	 *
	 * @param labelText the label/name of the desired input field
	 * @return the input field with the given label
	 * @author legacyAribaProgrammer ssalisbury
	 */
	protected WebElement getFieldByLabelText(final String labelText) {
		wait.waitForElementPresent(summaryViewContainer);
		WebElement pageContainer = driver.findElement(summaryViewContainer);

		List<WebElement> labels = pageContainer.findElements(By.tagName("label"));

		WebElement fieldElement = null;
		for (WebElement label : labels) {
			String currLabelText = this.text.getHiddenText(label);
			if (currLabelText != null) {
				String cleanText = this.text.cleanseWhitespace(currLabelText);
				if (labelText.equals(cleanText)) {
					WebElement labelElement = label;
					String fieldId = labelElement.getAttribute("for");
					fieldElement = driver.findElement(By.id(fieldId));
					break;
				}
			}
		}
		return fieldElement;
	}

	/**
	 * retrieves the overall taxes that are displayed for the PR
	 *
	 * @return the overall taxes that are displayed for the PR
	 * @author legacyAribaProgrammer ssalisbury
	 */
	public String getRequisitionTaxes() {
		WebElement contentBodyElement = wait.waitForElementDisplayed(contentBody);
		WebElement summaryPane = wait.waitForElementDisplayed(cartSummaryPane, contentBodyElement);
		List<WebElement> labels = wait.waitForAllElementsDisplayed(cartSummaryCostLabel, summaryPane);
		List<WebElement> values = wait.waitForAllElementsDisplayed(cartSummaryCostValue, summaryPane);

		int taxIndex = 0;

		for (WebElement label : labels) {
			String labelText = text.getElementText(label);

			if ("Taxes".equals(labelText)) {
				break;
			}
			taxIndex++;
		}

		String reqTax = values
				.get(taxIndex)
				.getText();

		return reqTax;
	}

	/**
	 * handles the requisition review alert message about using an item as a reference for mass edit
	 * if message is present, locates and clicks on the done button
	 */
	public void handleRequisitionReviewAlert() {
		String expectedText = "have been used here as a reference for mass edit.";
		try {
			WebElement alertMessage = wait.waitForElementPresent(alertMessageLoc, shortTimeOut);
			if (alertMessage.isDisplayed()) {
				String messageText = alertMessage.getText();
				if (!messageText.isEmpty()) {
					if (messageText.contains(expectedText)) {
						WebElement doneButton = wait.waitForElementEnabled(doneButtonLoc);
						click.clickElement(doneButton);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("No Requisition review alert message");
		}
	}

	public void clickTaxesButton() {
		List<WebElement> taxesButtons = wait.waitForAllElementsPresent(taxesLoc);
		WebElement taxButton = taxesButtons.get(9);
		click.clickElementCarefully(taxButton);

	}

	public String getNameFieldString() {
		waitForPageLoad();

		String nameText = null;

		List<WebElement> names = wait.waitForAllElementsDisplayed(nameField);

		for (WebElement name: names) {
			String message = text.getElementText(name);

					if (message.contains("Invoice Text")) {
						nameText = message;
					}
		}

		return nameText;
	}
}
