package com.vertex.quality.connectors.bigcommerce.ui.pages.storefront;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.bigcommerce.ui.pages.storefront.base.BigCommerceStoreBasePage;
import com.vertex.quality.connectors.bigcommerce.ui.pojos.BigCommerceUiAddressPojo;
import org.openqa.selenium.*;

import java.util.List;

/**
 * a representation of the checkout page on the Big Commerce Storefront website
 *
 * @author ssalisbury
 */
public class BigCommerceStoreCheckoutPage extends BigCommerceStoreBasePage
{
	protected final By loadingPopup = By.className("loadingNotification");

	protected final By customerEmailField = By.id("email");
	protected final By customerEmailSubmitButton = By.id("checkout-customer-continue");

	//these locators are used in both the shipping and billing sections of the checkout page
	protected final By addressFirstNameField = By.id("firstNameInput");
	protected final By addressLastNameField = By.id("lastNameInput");
	protected final By addressLine1Field = By.id("addressLine1Input");
	protected final By addressLine2Field = By.id("addressLine2Input");
	protected final By addressCityField = By.id("cityInput");
	protected final By addressCountryDropdown = By.id("countryCodeInput");
	protected final By addressStateProvinceDropdown = By.id("provinceCodeInput");
	protected final By addressPostalCodeField = By.id("postCodeInput");

	//this shows up in the Shipping address section
	protected final By shippingAddressSectionContainer = By.id("checkoutShippingAddress");

	//the problem here is that the checkbox has an id, but the container element for the checkbox extends over the checkbox,
	// and clicking the container element toggles the checkbox, so this has to fetch the checkbox's container
	protected final By sameBillShipCheckbox = By.id("sameAsBilling");
	protected final By sameBillShipCheckboxContainer = By.className("form-field");

	protected final By shipMethodsContainer = By.id("checkout-shipping-options");
	protected final By shipMethodContainer = By.className("optimizedCheckout-form-checklist-item");

	protected final By shipAddressSubmitButton = By.id("checkout-shipping-continue");

	//this shows up in the Billing address section
	protected final By billingAddressSectionContainer = By.id("checkoutBillingAddress");
	protected final By billAddressSubmitButton = By.id("checkout-billing-continue");

	protected final By cartPriceComponentContainer = By.className("cart-priceItem");
	protected final By cartPriceComponentLabel = By.className("cart-priceItem-label");
	protected final By cartPriceComponentValue = By.className("cart-priceItem-value");
	protected final String taxPriceComponentName = "Tax";

	public BigCommerceStoreCheckoutPage( final WebDriver driver )
	{
		super(driver);
	}

	@Override
	public void waitForPageLoad( )
	{
		super.waitForPageLoad();
		wait.waitForElementNotDisplayed(loadingPopup);
	}

	/**
	 * enters the given customer email address and continues on to the next step of the checkout process
	 *
	 * @param email the customer's email address
	 */
	public void fillAndSubmitCustomerEmail( final String email )
	{
		WebElement emailFieldElem = wait.waitForElementEnabled(customerEmailField);
		text.enterText(emailFieldElem, email);

		click.clickElementCarefully(customerEmailSubmitButton);
	}

	/**
	 * fills in the shipping address section with the given address's values, disables copying that address for
	 * the billing address section (so that specific values can be put into the billing address section), and submits
	 * that address in order to continue on to the next step of the checkout process
	 *
	 * @param shipAddress the address which this order should ship to
	 */
	public void fillAndSubmitShippingAddress( final BigCommerceUiAddressPojo shipAddress )
	{
		wait.waitForElementDisplayed(shippingAddressSectionContainer);

		fillAddressFields(shipAddress);

		uncheckSameBillShipAddressOption();

		clickSubmitButtonInsistently(shipAddressSubmitButton);

		wait.waitForElementNotDisplayed(shippingAddressSectionContainer);
	}

	/**
	 * fills in the billing address section with the given address's values and submits that address in order to
	 * continue on to the next step of the checkout process
	 *
	 * @param billAddress the address which this order should be billed to
	 */
	public void fillAndSubmitBillingAddress( final BigCommerceUiAddressPojo billAddress )
	{
		wait.waitForElementDisplayed(billingAddressSectionContainer);

		fillAddressFields(billAddress);

		clickSubmitButtonInsistently(billAddressSubmitButton);

		wait.waitForElementNotDisplayed(billingAddressSectionContainer);
	}

	/**
	 * fetches the estimated tax on the whole order
	 *
	 * @return the estimated tax on the whole order
	 */
	public String retrieveOrderTax( )
	{
		String taxString = null;
		List<WebElement> priceComponentContainerElems = wait.waitForAnyElementsDisplayed(cartPriceComponentContainer);
		WebElement taxPriceComponentContainerElem = element.selectElementByNestedLabel(priceComponentContainerElems,
			cartPriceComponentLabel, taxPriceComponentName);
		if ( taxPriceComponentContainerElem != null )
		{
			WebElement taxDisplayElem = wait.waitForElementDisplayed(cartPriceComponentValue,
				taxPriceComponentContainerElem);
			taxString = text.getElementText(taxDisplayElem);
		}
		return taxString;
	}

	/**
	 * fills in the fields of either the billing or shipping section of the checkout page
	 *
	 * @param address the address whose values should fill that section of the checkout page
	 */
	protected void fillAddressFields( final BigCommerceUiAddressPojo address )
	{
		WebElement firstNameFieldElem = wait.waitForElementEnabled(addressFirstNameField);
		text.enterText(firstNameFieldElem, address.getFirstName());

		WebElement lastNameFieldElem = wait.waitForElementEnabled(addressLastNameField);
		text.enterText(lastNameFieldElem, address.getLastName());

		WebElement addressLine1FieldElem = wait.waitForElementEnabled(addressLine1Field);
		text.enterText(addressLine1FieldElem, address.getAddressLine1());

		WebElement addressLine2FieldElem = wait.waitForElementEnabled(addressLine2Field);
		text.enterText(addressLine2FieldElem, address.getAddressLine2());

		WebElement cityFieldElem = wait.waitForElementEnabled(addressCityField);
		text.enterText(cityFieldElem, address.getCity());

		WebElement countryDropdownElem = wait.waitForElementEnabled(addressCountryDropdown);
		dropdown.selectDropdownByDisplayName(countryDropdownElem, address.getCountry());

		WebElement stateProvinceDropdownElem = wait.waitForElementEnabled(addressStateProvinceDropdown);
		dropdown.selectDropdownByDisplayName(stateProvinceDropdownElem, address.getStateProvince());

		WebElement postalCodeFieldElem = wait.waitForElementEnabled(addressPostalCodeField);
		text.enterText(postalCodeFieldElem, address.getPostalCode());

		//this is to shift focus away from the last required field in the billing address, because then the site's
		// javascript notices that all mandatory fields for billing address are filled and it reloads some things,
		// for example it updates the tax estimate
		text.pressTab(postalCodeFieldElem);

		waitForPageLoad();
	}

	/**
	 * this helper function is run while the shipping address section is open to make sure that the shipping address
	 * isn't automatically used as the billing address as well
	 */
	protected void uncheckSameBillShipAddressOption( )
	{
		//after the shipping address is filled out, the page reloads some stuff; in particular, it reloads the
		// shipping methods selection field from being an empty box that says 'please select a shipping method'
		// to have an actual shipping method option listed in the box (y'know, once there's a place to ship to)
		WebElement shipMethodsContainerElem = wait.waitForElementDisplayed(shipMethodsContainer);
		wait.waitForElementDisplayed(shipMethodContainer, shipMethodsContainerElem);
		waitForPageLoad();

		WebElement sameBillShipAddressCheckboxFieldContainer = getSameBillShipAddressCheckboxContainer();
		if ( sameBillShipAddressCheckboxFieldContainer == null )
		{
			//fixme I'm not sure what the best approach is in this failure case. I'm just ending the test for now
			throw new RuntimeException("Couldn't find the container for the checkbox that governs whether " +
									   "the billing address is the same as the shipping address");
		}

		final int maxCheckAttempts = 5;

		wait.waitForElementDisplayed(sameBillShipCheckbox);
		boolean isSameBillShipChecked = checkbox.isCheckboxChecked(sameBillShipCheckbox);

		//this is necessary because there's weird react js stuff going on behind the scenes (which I'm pretty sure
		// isn't reflected in the browser's DOM):
		// Sometimes the containing element around all of the shipping address's fields acts as an invisible overlay
		// over those fields (it seems like that doesn't last after the first time you try to click something),
		// and sometimes the first click on the checkbox won't affect the checkbox
		for ( int i = 0 ; isSameBillShipChecked && i < maxCheckAttempts ; i++ )
		{
			VertexLogger.log("try to click same bill ship checkbox");//todo delete this trace
			//todo I'm not certain if the focus call is necessary
			focus.focusOnElementJavascript(sameBillShipAddressCheckboxFieldContainer);

			try
			{
				click.clickElementCarefully(sameBillShipAddressCheckboxFieldContainer);
			}
			catch ( ElementClickInterceptedException e )
			{
				continue;
			}

			wait.waitForElementDisplayed(sameBillShipCheckbox);
			isSameBillShipChecked = checkbox.isCheckboxChecked(sameBillShipCheckbox);
		}
	}

	/**
	 * fetches the container around the checkbox for whether the billing address should be the same as the shipping
	 * address
	 * Note- this only works in the shipping section of the page
	 *
	 * @return the container around the checkbox for whether the billing address should be the same as the shipping address
	 */
	protected WebElement getSameBillShipAddressCheckboxContainer( )
	{
		WebElement sameBillShipContainer = null;

		List<WebElement> fieldContainers = wait.waitForAnyElementsDisplayed(sameBillShipCheckboxContainer);
		wait.waitForElementDisplayed(sameBillShipCheckbox);

		for ( WebElement fieldCont : fieldContainers )
		{
			boolean isTargetCheckboxInContainer = element.isElementDisplayed(sameBillShipCheckbox, fieldCont);
			if ( isTargetCheckboxInContainer )
			{
				sameBillShipContainer = fieldCont;
				break;
			}
		}

		return sameBillShipContainer;
	}

	/**
	 * this makes sure that the submit button for the current section of the checkout page is actually clicked
	 * so that the current section's contents is saved and the next section is loaded
	 *
	 * @param submitButtonLoc the locator of the submit button for the current section of the page
	 */
	protected void clickSubmitButtonInsistently( final By submitButtonLoc )
	{
		//this is necessary because there's weird react js stuff going on behind the scenes (which I'm pretty sure
		// isn't reflected in the browser's DOM):
		// Sometimes the containing element around all of the shipping address's fields acts as an invisible overlay
		// over those fields (it seems like that doesn't last after the first time you try to click something),
		// and sometimes the first click on the button won't activate the button and close the current section
		try
		{
			click.clickElementCarefully(submitButtonLoc);
			wait.waitForElementNotDisplayed(submitButtonLoc, FIVE_SECOND_TIMEOUT);
		}
		catch ( TimeoutException | ElementClickInterceptedException e1 )
		{
			try
			{
				click.clickElementCarefully(submitButtonLoc);
				wait.waitForElementNotDisplayed(submitButtonLoc, QUARTER_MINUTE_TIMEOUT);
			}
			catch ( TimeoutException | ElementClickInterceptedException e2 )
			{
				try
				{
					click.clickElementCarefully(submitButtonLoc);
					wait.waitForElementNotDisplayed(submitButtonLoc, HALF_MINUTE_TIMEOUT);
				}
				catch ( TimeoutException | ElementClickInterceptedException e3 )
				{
					try
					{
						click.clickElementCarefully(submitButtonLoc);
						wait.waitForElementNotDisplayed(submitButtonLoc, DEFAULT_TIMEOUT);
					}
					catch ( TimeoutException | ElementClickInterceptedException e4 )
					{
						click.clickElementCarefully(submitButtonLoc);
					}
				}
			}
		}
	}
}
