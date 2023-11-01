package com.vertex.quality.connectors.hybris.pages.backoffice;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.hybris.pages.HybrisBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents to Set configurations on BaseStore page of Electronics Site
 *
 * @author Nagaraju Gampa
 */
public class HybrisBOBaseStorePage extends HybrisBasePage
{
	public HybrisBOBaseStorePage( WebDriver driver )
	{
		super(driver);
	}

	protected By ELECTRONIC_STORE = By.xpath(
		"//td[contains(@class,'listview')]//span[contains(text(),'Electronics Store')]");
	protected By ELECTRONIC_STORE_HEADING = By.xpath(
		"//div[contains(@class,'z-caption-content')]//span[contains(text(),'Electronics Store')]");
	protected By VERTEX_CUSTOMIZATION_TAB = By.cssSelector("[title='Vertex Customization']");
	protected By DEFAULT_DELIVERY_ORIGIN = By.xpath(
		"//div[span[@title='defaultDeliveryOrigin']]//following-sibling::div");
	protected By DELIVERY_COST_TAX_CLASS = By.xpath(
		"//span[contains(@title,'vertexDeliveryCostTaxClass')]/../following-sibling::div//input");
	protected By ELECTRONICS_STORE_SAVE_BUTTON = By.cssSelector(
		"div[class*='toolbar']>div[class*='save-container']>button[type='button']");
	protected By SAVE_CONFIRMATION = By.cssSelector("div[class*='notification-message success']>span[class='z-label']");
	protected By TAXGROUP = By.xpath(
		"//span[contains(@title,'taxGroup')]/../following-sibling::div//a[contains(@class,'combobox-button')]");
	protected By COMPANYNAMEOFSELLER = By.xpath(
		"//span[contains(@title,'vertexSellerCompany')]/../following-sibling::div//input");
	protected By DELIVERYCOSTTAXCLASS = By.xpath(
		"//span[contains(@title,'vertexDeliveryCostTaxClass')]/../following-sibling::div//input");

	/***
	 * Method to select Electronic Store from Base Store
	 */
	public void selectElectronicsStore( )
	{
		hybrisWaitForPageLoad();
		wait.waitForElementDisplayed(ELECTRONIC_STORE);
		click.clickElement(ELECTRONIC_STORE);
		hybrisWaitForPageLoad();
		wait.waitForElementDisplayed(ELECTRONIC_STORE_HEADING);
	}

	/***
	 * Method to enable/disable StoreFront Net Value
	 *
	 * @param value - Set boolean value as either true/false
	 *
	 */
	public void setStorefrontPropertiesNetValue( String value )
	{
		final String propertiesNetValue = String.format(
			"//div[contains(@title,'catalogs')]/../../preceding-sibling::div[contains(@class,'tabpanel')]//td[*[*[text()='Net']]]//label[text()='%s']",
			value);
		click.clickElement(By.xpath(propertiesNetValue));
		waitForPageLoad();
	}

	/***
	 * Method to enable/disable external tax calculation
	 *
	 * @param value - Set boolean value as either true/false
	 *
	 */
	public void setStorefrontExternalTaxCalculation( String value )
	{
		final String externalTaxCalculation = String.format(
			"//td[*[*[text()='External Tax Calculation']]]//label[text()='%s']", value);
		click.clickElement(By.xpath(externalTaxCalculation));
		waitForPageLoad();
	}

	/***
	 * Method to select Tax Group
	 *
	 * @param value -  Value to be selected from TaxGroup dropdown
	 */
	public void selectTaxGroup( String value )
	{
		click.clickElement(TAXGROUP);
		waitForPageLoad();
		final String taxGroupSelection = String.format(
			"//div[contains(@class,'combobox-popup')]//span[contains(text(),'%s')]", value);
		click.clickElement(By.xpath(taxGroupSelection));
		hybrisWaitForPageLoad();
	}

	/**
	 * Method to set Company Name of the Seller
	 *
	 * @param sellerValue - Name of the Company Seller
	 */
	public void setSellerCompanyName( String sellerValue )
	{
		wait.waitForElementDisplayed(COMPANYNAMEOFSELLER);
		text.enterText(COMPANYNAMEOFSELLER, sellerValue);
	}

	/**
	 * Method to set Delivery Cost Tax Class
	 *
	 * @param deliveryCostValue - Set Delivery Cost Tax Value
	 */
	public void setDeliveryCostTaxClass( String deliveryCostValue )
	{
		wait.waitForElementDisplayed(DELIVERYCOSTTAXCLASS);
		text.enterText(DELIVERYCOSTTAXCLASS, deliveryCostValue);
	}

	/***
	 * Method to set store front invoice tax enable
	 * @param value - Set boolean value either true or false
	 */
	public void setStorefrontEnableTaxInvoicing( String value )
	{
		final By taxInvoicing = By.xpath(
			String.format("//td[*[*[text()='Enable Tax Invoicing']]]//label[text()='%s']", value));
		click.clickElement(taxInvoicing);
		waitForPageLoad();
	}

	/***
	 * Method to set store front boolean property
	 *
	 * @param propertyName - Name of the Element/Attribure
	 * @param booleanValueStr - Boolean Value to set as True or False
	 */
	public void setStorefrontElementProperty( String propertyName, String booleanValueStr )
	{
		wait.waitForElementDisplayed(VERTEX_CUSTOMIZATION_TAB);
		final By ELEMENT_TUPLE = By.xpath(
			String.format("//td[*[*[text()='%s']]]//label[text()='%s']", propertyName, booleanValueStr));
		click.clickElement(ELEMENT_TUPLE);
		waitForPageLoad();
	}

	/***
	 * Method to select required Tab from BaseStore of Electronics Page
	 */
	public void selectTabFromElectronicsStore( String tabName )
	{
		By tabNameBy = By.cssSelector(String.format("li[title='%s']", tabName));
		wait.waitForElementDisplayed(tabNameBy);
		click.clickElement(tabNameBy);
		hybrisWaitForPageLoad();
	}

	/**
	 * Method to Double Click on Default Delivery Origin
	 */
	public HybrisBODefaultDeliveryOriginPage doubleClickOnDefaultDeliveryOrigin( )
	{
		hybrisWaitForPageLoad();
		WebElement element = wait.waitForElementPresent(DEFAULT_DELIVERY_ORIGIN);
		click.performDoubleClick(element);
		hybrisWaitForPageLoad();
		final HybrisBODefaultDeliveryOriginPage deliveryOriginPage = initializePageObject(
			HybrisBODefaultDeliveryOriginPage.class);
		return deliveryOriginPage;
	}

	/***
	 * Method to save electronics store configuration
	 */
	public void saveElectronicsStoreConfiguration( )
	{
		if ( isSaveButtonEnabled() )
		{
			click.clickElement(ELECTRONICS_STORE_SAVE_BUTTON);
			wait.waitForElementDisplayed(SAVE_CONFIRMATION);
			this.validateStoreConfigConfirmationMsg();
		}
		else
		{
			VertexLogger.log("No changes made, Save button is disabled");
		}
	}

	/***
	 * Method to check whether Save button enabled or disabled
	 * @return
	 *        True if SaveButton Enabled
	 * 		False if SaveButton Disabled 
	 */
	public boolean isSaveButtonEnabled( )
	{
		// Intentional timeout because save button takes certain time to be enabled after the change in settings
		jsWaiter.sleep(1000);
		boolean elemState = true;
		String disabledState = null;
		disabledState = attribute.getElementAttribute(ELECTRONICS_STORE_SAVE_BUTTON, "disabled");
		if ( disabledState == null )
		{
			elemState = true;
		}
		else if ( disabledState.equalsIgnoreCase("true") )
		{
			elemState = false;
		}
		return elemState;
	}

	/***
	 * Validate Electronics Store confirmation after saving the Store Config Changes
	 */
	public void validateStoreConfigConfirmationMsg( )
	{
		final String ExpStoreConfigConfirmMsg = "Saved items: Electronics Store";
		final String ActualStoreConfigConfirmMsg = text.getElementText(SAVE_CONFIRMATION);
		String elementMsg = String.format("ActualStoreConfigConfirmMsg is: %s", ActualStoreConfigConfirmMsg);
		VertexLogger.log(elementMsg);
		if ( ExpStoreConfigConfirmMsg.contains(ActualStoreConfigConfirmMsg) )
		{
			VertexLogger.log("Expected Message is matching with Actual Message");
		}
		else
		{
			elementMsg = String.format("Expected Message %s is Not matching with Actual Message %s",
				ExpStoreConfigConfirmMsg, ActualStoreConfigConfirmMsg);
			VertexLogger.log(elementMsg);
		}
		wait.waitForElementDisplayed(SAVE_CONFIRMATION);
	}
}
