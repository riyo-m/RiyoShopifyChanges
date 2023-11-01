package com.vertex.quality.connectors.kibo.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * This class represents the payment section of the order details dialog
 * contains all the methods necessary to interact with the different elements it has.
 *
 * @author osabha
 */
public class KiboBackOfficeStoreOrderDetailsPayment extends VertexComponent
{
	public KiboBackOfficeStoreOrderDetailsPayment( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	protected By captureButtonContainerLoc = By.className("orderform-payment-statusRow");
	protected By checkNumberFieldLoc = By.cssSelector("input[name='checkNumber']");
	protected By firstNameFieldLoc = By.cssSelector("input[name='firstName']");
	protected By getSaveButtonSubContainerLoc = By.className("x-btn-inner-center");
	protected By saveButtonContainerLoc = By.className("x-toolbar-default-docked-bottom");
	protected By captureButtonLoc = By.cssSelector(".x-btn-inner.x-btn-inner-center");
	protected By paymentButtonContainerLoc = By.className("taco-form-nav");
	protected By paymentButtonLoc = By.className("taco-link-button");
	protected By fulfilledLoc = By.className("x-column-content-pill");
	protected By fulfilledContainersLoc = By.className("x-panel-header-text-container-subform");
	protected By checkButtonContainerLoc = By.className("x-menu-item-text");
	protected By saveCollectedCheckConLoc = By.className("taco-window-action-toolbar");
	protected By saveCollectedCheckLoc = By.className("x-btn-action-primary-toolbar-medium");
	protected By paymentMethodButtonLoc = By.cssSelector(".x-btn-wrap.x-btn-split.x-btn-split-right");
	String payerName = "Tester firstName";

	/**
	 * this method gets the payment button element, it uses a local expected condition wait function
	 * to make the code more stable
	 *
	 * @return the WebElement of the button
	 */
	protected WebElement getPaymentButton( )
	{
		WebElement paymentButton = null;
		String expectedText = "Payments";
		WebElement paymentButtonContainer = wait.waitForElementPresent(paymentButtonContainerLoc);
		List<WebElement> menuClasses = wait.waitForAllElementsPresent(paymentButtonLoc, paymentButtonContainer);
		paymentButton = element.selectElementByText(menuClasses, expectedText);

		return paymentButton;
	}

	/**
	 * uses the getter method to locate the payment button WebElement and then clicks on it
	 */
	public void clickPaymentButton( )
	{
		WebElement paymentButton = getPaymentButton();
		if ( paymentButton == null )
		{
			paymentButton = getPaymentButton();
			if ( paymentButton == null )
			{
				paymentButton = getPaymentButton();
			}
		}
		click.clickElement(paymentButton);
	}

	/**
	 * getter method to locate the add payment button WebElement
	 *
	 * @return add payment button WebElement
	 */
	protected WebElement getAddPaymentMethodButton( )
	{
		WebElement paymentTypeButton = wait.waitForElementEnabled(paymentMethodButtonLoc);
		return paymentTypeButton;
	}

	/**
	 * uses the getter method to locate the add payment button WebElement and then clicks on the far
	 * right end of the button
	 * Note= this MoveToElement is Offsetting from the middle of element, unlike what it's javadoc says.
	 * to click on the drop list arrow
	 */
	public void clickAddPaymentTypeButton( )
	{
		WebElement addPaymentTypeButton = getAddPaymentMethodButton();

		Actions builder = new Actions(driver);
		Dimension dimensions = addPaymentTypeButton.getSize();
		int buttonWidth = dimensions.width;
		int buttonHeight = dimensions.height;
		//offsetting the click slightly below the top of the element.
		double doubleYOffSet = ((buttonHeight) / 4) * -1;
		//offsetting the click to the far right of the element starting from the middle.
		double doubletXOffSet = ((buttonWidth) / 2) * 0.8;
		int xOffSet = (int) Math.round(doubletXOffSet);
		int yOffSet = (int) Math.round(doubleYOffSet);
		builder.moveToElement(addPaymentTypeButton, xOffSet, yOffSet);

		builder
			.click()
			.build()
			.perform();
	}

	/**
	 * contains nested search using for loops to locate the exact check Button WebElement
	 *
	 * @return the check button WebElement
	 */
	protected WebElement getCheckButton( )
	{
		String expectedText = "Check";
		WebElement checkButton = element.selectElementByText(checkButtonContainerLoc, expectedText);
		return checkButton;
	}

	/**
	 * uses the getter method to locate the check button WebElement and then clicks on it from the
	 * drop down list.
	 */
	public void selectCheck( )
	{
		WebElement checkButton = getCheckButton();
		checkButton.click();
	}

	/**
	 * this is a getter method to locate the WebElement of the first name field for the customer
	 * that will pay with their check
	 *
	 * @return the WebElement of the first name field
	 */
	protected WebElement getFirstNameField( )
	{
		WebElement firstNameField = wait.waitForElementDisplayed(firstNameFieldLoc);

		return firstNameField;
	}

	/**
	 * uses the getter method to locate the firstName Field WebElement and then clears it and sends
	 * the payers first name
	 */
	public void enterPayerFirstName( )
	{
		WebElement firstNameField = getFirstNameField();
		text.enterText(firstNameField, payerName);
	}

	/**
	 * getter method to locate the save  button when taking the check payment
	 * uses nested search with for loops to make the code more stable
	 *
	 * @return WebElement of the save Button
	 */
	protected WebElement getCheckSaveButton( )
	{
		WebElement saveButton = null;
		String expectedText = "Save";
		List<WebElement> saveButtonContainers = wait.waitForAllElementsPresent(saveButtonContainerLoc);

		for ( WebElement container : saveButtonContainers )
		{
			List<WebElement> allSaveButtons = element.getWebElements(getSaveButtonSubContainerLoc, container);
			saveButton = element.selectElementByText(allSaveButtons, expectedText);
			if ( saveButton != null )
			{
				break;
			}
		}

		return saveButton;
	}

	/**
	 * uses the getter method to locate the WebElement of the save Button for the check, and then
	 * clicks on it
	 */
	public void clickCheckSaveButton( )
	{
		WebElement saveButton = getCheckSaveButton();
		click.javascriptClick(saveButton);
	}

	/**
	 * getter method to locate the capture Button WebElement
	 * uses nested search with for loops to make the code more stable
	 *
	 * @return capture button WebElement
	 */
	protected WebElement getCaptureButton( )
	{
		WebElement captureButton = null;
		String expectedText = "Capture";

		List<WebElement> captureButtonContainers = wait.waitForAllElementsPresent(captureButtonContainerLoc);

		for ( WebElement thisCaptureButtonCon : captureButtonContainers )
		{
			List<WebElement> allCaptureButtons = wait.waitForAllElementsPresent(captureButtonLoc, thisCaptureButtonCon);
			captureButton = element.selectElementByText(allCaptureButtons, expectedText);
			if ( captureButton != null )
			{
				break;
			}
		}

		return captureButton;
	}

	/**
	 * uses the getter method to locate the WebElement of the capture button and then clicks on it
	 */
	public void clickCaptureButton( )
	{
		WebElement captureButton = getCaptureButton();
		click.javascriptClick(captureButton);
	}

	/**
	 * getter method that locates the WebElement of the check number field
	 *
	 * @return check number field WebElement
	 */
	protected WebElement getCheckNumberField( )
	{
		WebElement checkNumberField = wait.waitForElementPresent(checkNumberFieldLoc);
		return checkNumberField;
	}

	/**
	 * uses the getter method to locate the check number field WebElement and then clears it and
	 * then types in a random check number
	 */
	public void enterCheckNumber( String checkNumber )
	{
		WebElement checkNumberField = getCheckNumberField();

		text.enterText(checkNumberField, checkNumber);
	}

	/**
	 * this method locates the WebElement of the save button for the collected check dialog( after
	 * the check number is entered )
	 *
	 * @return the save button WebElement
	 */
	protected WebElement getSaveCollectedCheck( )
	{
		WebElement saveCollectedCheckContainer = wait.waitForElementPresent(saveCollectedCheckConLoc);
		WebElement saveCollectedCheck = saveCollectedCheckContainer.findElement(saveCollectedCheckLoc);

		return saveCollectedCheck;
	}

	/**
	 * uses the getter method to get the save button WebElement and then clicks on it
	 */
	public void clickSaveCollectedCheck( )
	{
		WebElement saveCollectedCheck = getSaveCollectedCheck();
		saveCollectedCheck.click();
		waitForPageLoad();
	}

	/**
	 * boolean method to verify if the order has been marked as payed after making the payment
	 *
	 * @return true if paid
	 */
	public boolean checkIfPaid( )
	{
		boolean isPayed = false;
		String expectedPayStatus = "Paid";
		String expectedContainerText = "Payments";
		WebElement fulfilled = null;
		String attribute = "innerHTML";

		List<WebElement> fulfilledContainers = wait.waitForAllElementsPresent(fulfilledContainersLoc);
		for ( WebElement container : fulfilledContainers )
		{
			String containerText = container.getText();
			if ( containerText.contains(expectedContainerText) )
			{
				this.attribute.waitForElementAttributeChange(container, attribute);

				fulfilled = container.findElement(fulfilledLoc);
				break;
			}
		}
		String currentPaymentStatusText = text.getElementText(fulfilled);
		if ( expectedPayStatus.equals(currentPaymentStatusText) )
		{
			isPayed = true;
		}

		return isPayed;
	}
}
