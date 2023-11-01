package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaPostSignOnPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author saidulu kodadala
 * Enable Disable Features page actions/methods
 */
public class AcumaticaEnableDisableFeaturesPage extends AcumaticaPostSignOnPage
{
	protected final By featureContainer = By.className("checkBox");

	protected final String labelPointerAttribute = "for";
	//this attribute on some label elements provides the id attribute of the element that the label identifies

	protected By MODIFY_BUTTON = By.xpath("//div[contains(text(),'Modify')]");
	protected By DISABLED_MODIFY_BUTTON = By.xpath("//div[div[contains(text(),'Modify')]][@enabled='false']");
	protected By ENABLE_BUTTON = By.cssSelector("[data-cmd='requestValidation'] div");

	public AcumaticaEnableDisableFeaturesPage( WebDriver driver )
	{
		super(driver);
	}

	/***
	 * Click on modify button
	 */
	public void clickModifyButton( )
	{
		click.clickElementCarefully(MODIFY_BUTTON);
		wait.waitForElementDisplayed(DISABLED_MODIFY_BUTTON);
	}

	/***
	 * Select vertex tax integration
	 * @param expectedState
	 */
	public boolean setVertexTaxIntegrationCheckbox( final boolean expectedState )
	{
		WebElement integrationCheckbox = getFeatureCheckbox(Feature.VERTEX_TAX_INTEGRATION);
		boolean isChecked = getVertexTaxIntegrationState();
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(integrationCheckbox);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(integrationCheckbox);
		return resultStatus;
	}

	/**
	 * checks whether Vertex tax integration is enabled
	 *
	 * @return whether Vertex tax integration is enabled
	 */
	public boolean getVertexTaxIntegrationState( )
	{
		WebElement integrationCheckbox = getFeatureCheckbox(Feature.VERTEX_TAX_INTEGRATION);
		wait.waitForElementDisplayed(integrationCheckbox);
		boolean isChecked = checkbox.isCheckboxChecked(integrationCheckbox);
		return isChecked;
	}

	/***
	 * Select address cleansing
	 * @param expectedState
	 */
	public boolean setAddressCleansingCheckbox( final boolean expectedState )
	{
		WebElement addressCleansingCheckbox = getFeatureCheckbox(Feature.ADDRESS_CLEANSING);
		boolean isChecked = getAddressCleansingState();
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(addressCleansingCheckbox);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(addressCleansingCheckbox);
		return resultStatus;
	}

	/**
	 * checks whether Address Cleansing is currently enabled
	 *
	 * @return whether Address Cleansing is currently enabled
	 */
	public boolean getAddressCleansingState( )
	{
		WebElement addressCleansingCheckbox = getFeatureCheckbox(Feature.ADDRESS_CLEANSING);
		wait.waitForElementDisplayed(addressCleansingCheckbox);
		boolean isChecked = checkbox.isCheckboxChecked(addressCleansingCheckbox);
		return isChecked;
	}

	/***
	 * Click on enabled button
	 */
	public void clickEnabledButton( )
	{
		click.clickElementCarefully(ENABLE_BUTTON);
		wait.waitForElementDisplayed(MODIFY_BUTTON);
	}

	/***
	 * Common method for basic page setup.
	 */
	public void predefinedSettingsFromEnableDisableFeaturesPage( boolean isSelectVertexTaxIntegrationTrue,
		boolean isSelectAddressCleansingTrue )
	{
		boolean areFeaturesMisconfigured = false;
		switchToMainFrame();

		boolean vertexTaxIntegrationState = getVertexTaxIntegrationState();
		if ( vertexTaxIntegrationState != isSelectVertexTaxIntegrationTrue )
		{
			areFeaturesMisconfigured = true;
		}

		boolean addressCleansingState = getAddressCleansingState();
		if ( addressCleansingState != isSelectAddressCleansingTrue )
		{
			areFeaturesMisconfigured = true;
		}

		if ( areFeaturesMisconfigured )
		{
			this.clickModifyButton();
			this.setVertexTaxIntegrationCheckbox(isSelectVertexTaxIntegrationTrue);
			this.setAddressCleansingCheckbox(isSelectAddressCleansingTrue);
			this.clickEnabledButton();
		}

		window.switchToDefaultContent();
	}

	/**
	 * retrieves the checkbox for turning some feature on or off for this user on acumatica
	 *
	 * @param feature which feature's checkbox to get
	 *
	 * @return the checkbox for turning some feature on or off for this user on acumatica
	 */
	protected WebElement getFeatureCheckbox( final Feature feature )
	{
		WebElement featureCheckbox = null;

		List<WebElement> checkboxContainers = wait.waitForAllElementsDisplayed(featureContainer);

		for ( WebElement container : checkboxContainers )
		{
			final WebElement featureLabel = wait.waitForElementDisplayed(By.tagName("label"), container);
			final String currentFeatureText = text.getElementText(featureLabel);
			final String targetFeatureText = feature.getLabel();

			if ( targetFeatureText.equals(currentFeatureText) )
			{
				final String targetFeatureCheckboxId = attribute.getElementAttribute(featureLabel,
					labelPointerAttribute);
				if ( targetFeatureCheckboxId != null )
				{
					featureCheckbox = wait.waitForElementPresent(By.id(targetFeatureCheckboxId));
				}
			}
		}

		return featureCheckbox;
	}

	/**
	 * this lists the options on this page which tests will enable or disable
	 * and stores the identifying information for each option
	 */
	protected enum Feature
	{
		VERTEX_TAX_INTEGRATION("Vertex Tax Integration"),
		ADDRESS_CLEANSING("Address Cleansing");

		private String labelText;

		Feature( final String text )
		{
			this.labelText = text;
		}

		public String getLabel( )
		{
			return labelText;
		}
	}
}
