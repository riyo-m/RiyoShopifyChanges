package com.vertex.quality.connectors.ariba.portal.pages.config;

import com.vertex.quality.connectors.ariba.portal.enums.AribaConnectorIntegrationEndpoints;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents the final page on which the user can edit the configurations task,
 * contains all the methods necessary to edit the tax task endpoints.
 *
 * @author osabha
 */
public class AribaPortalEditConfigTaskPage extends AribaPortalPostLoginBasePage
{
	public AribaPortalEditConfigTaskPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * locates the URL field
	 *
	 * @return url field WebElement
	 */
	public WebElement getFieldContainer( String fieldLabel )

	{
		WebElement fieldCont = null;
		WebElement parentCont = wait.waitForElementPresent(By.className("w-page-wrapper-content"));
		List<WebElement> fieldsConts = wait.waitForAllElementsPresent(By.cssSelector("tr[valign='middle']"),
			parentCont);
		fieldCont = element.selectElementByNestedLabel(fieldsConts, By.tagName("label"), fieldLabel);

		return fieldCont;
	}

	/**
	 * enters url into url field
	 *
	 * @param url string of the url for the endpoint
	 */
	public void enterEndpointUrl( String url )
	{
		String fieldLabel = "URL:";
		WebElement urlFieldContainer = getFieldContainer(fieldLabel);
		WebElement urlField = wait.waitForElementEnabled(By.tagName("input"), urlFieldContainer);
		text.enterText(urlField, url);
		waitForPageLoad();
	}

	/**
	 * locates and clicks on the save button
	 *
	 * @return new instance of the integration configuration page
	 */
	public AribaPortalIntegrationConfigurationPage saveChanges( )
	{
		WebElement saveButton = wait.waitForElementPresent(By.className("w-btn-primary"));
		click.clickElementCarefully(saveButton);
		return initializePageObject(AribaPortalIntegrationConfigurationPage.class);
	}

	/**
	 * locates and clicks on the endpoints field and then
	 *
	 * @param endpoint
	 */
	public void selectEndpoint( String endpoint )
	{
		String fieldLabel = "End point:";
		WebElement endpointFieldContainer = getFieldContainer(fieldLabel);
		WebElement endpointField = wait.waitForElementPresent(By.className("w-dropdown"), endpointFieldContainer);
		click.clickElement(endpointField);
		WebElement dropdownContainer = wait.waitForElementPresent(By.className("w-dropdown-items"),
			endpointFieldContainer);
		List<WebElement> dropDownOptions = wait.waitForAllElementsDisplayed(By.tagName("div"), dropdownContainer);
		WebElement desiredEndpoint = element.selectElementByText(dropDownOptions, endpoint);
		click.clickElement(desiredEndpoint);
		waitForPageLoad();
	}

	/**
	 * sets the endpoint and it's url
	 *
	 * @param endpoint an enum contains the endpoint name and url
	 */
	public void setEndpointTo( AribaConnectorIntegrationEndpoints endpoint )
	{
		waitForUpdate();
		selectEndpoint(endpoint.getEndpoint());
		enterEndpointUrl(endpoint.getUrl());
		waitForPageLoad();
	}

	/**
	 * locates the endpoint field and retrieves the current selected endpoint
	 *
	 * @return selected endpoint
	 */
	public String getSelectedEndpoint( )
	{
		waitForUpdate();
		String fieldLabel = "End point:";
		WebElement endpointFieldContainer = getFieldContainer(fieldLabel);
		WebElement endpointField = wait.waitForElementPresent(By.className("w-dropdown-selected"),
			endpointFieldContainer);
		String currentEndpoint = endpointField.getText();
		String cleansedCurrentEndpoint = currentEndpoint.trim();
		return cleansedCurrentEndpoint;
	}

	/**
	 * locates the url field and retrieves the current value of it
	 *
	 * @return current url the connector is pointing at
	 */
	public String getEndpointUrl( )
	{
		String fieldLabel = "URL:";
		WebElement urlFieldContainer = getFieldContainer(fieldLabel);
		WebElement urlField = wait.waitForElementEnabled(By.tagName("input"), urlFieldContainer);
		String url = urlField.getAttribute("value");
		return url;
	}
}
