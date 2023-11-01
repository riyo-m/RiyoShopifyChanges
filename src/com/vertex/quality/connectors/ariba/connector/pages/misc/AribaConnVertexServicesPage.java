package com.vertex.quality.connectors.ariba.connector.pages.misc;

import com.vertex.quality.common.enums.SpecialCharacter;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * representation of the page of this configuration site which describes the
 * site's purpose of configuring its connector and lists important information
 * for that, like the version number of that connector's WSDL for O Series & the
 * link to that WSDL
 *
 * @author ssalisbury
 */
public class AribaConnVertexServicesPage extends AribaConnBasePage
{
	protected final By connectorVersionLoc = By.id("version-string");
	protected final By connectorWSDLLoc = By.id("wsdl-link");

	public AribaConnVertexServicesPage( WebDriver driver )
	{
		super(driver, "Vertex$trade; Indirect Tax for Procurement with SAP" + SpecialCharacter.TRADEMARK + " Ariba" +
					  SpecialCharacter.TRADEMARK + " Web Services");
	}

	/**
	 * checks whether the Vertex Services page is listing the connector's version
	 *
	 * @return whether the Vertex Services page is listing the connector's version as a bool
	 *
	 * @author ssalisbury
	 */
	public boolean isConnectorVersionDisplayed( )
	{
		WebElement versionElement = wait.waitForElementDisplayed(connectorVersionLoc);
		boolean isVersionDisplayed = element.isElementDisplayed(versionElement);

		return isVersionDisplayed;
	}

	/**
	 * collects the connector's version
	 *
	 * @return the connector's version as a string
	 *
	 * @author ssalisbury
	 */
	public String getConnectorVersion( )
	{
		WebElement connectorVersionElement = wait.waitForElementDisplayed(connectorVersionLoc);
		String connectorVersionText = text.getElementText(connectorVersionElement);

		return connectorVersionText;
	}

	/**
	 * checks whether the Vertex Services page is listing the connector's wsdl
	 *
	 * @return whether the Vertex Services page is listing the connector's wsdl as a bool
	 *
	 * @author dgorecki
	 */
	public boolean isConnectorWSDLDisplayed( )
	{
		WebElement wsdlElement = wait.waitForElementDisplayed(connectorWSDLLoc);
		boolean isWSDLDisplayed = element.isElementDisplayed(wsdlElement);

		return isWSDLDisplayed;
	}

	/**
	 * collects the connector's wsdl link
	 *
	 * @return the connector's wsdl link as a string
	 *
	 * @author dgorecki
	 */
	public String getConnectorWSDLLink( )
	{
		WebElement wsdlElement = wait.waitForElementDisplayed(connectorWSDLLoc);
		String wsdlText = text.getElementText(wsdlElement);

		return wsdlText;
	}
}
