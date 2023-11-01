package com.vertex.quality.connectors.sitecore.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.sitecore.common.enums.SitecoreVersion;
import com.vertex.quality.connectors.sitecore.pages.base.SitecoreBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Vertex O Series Connector page - contains all re-usable methods related to
 * this page.
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public class SitecoreVertexOSeriesConnectorPage extends SitecoreBasePage
{

	protected By status = By.id("SpanStatus");
	protected By version = By.className("sc-applicationHeader-version");

	public SitecoreVertexOSeriesConnectorPage( WebDriver driver )
	{

		super(driver);
	}

	/**
	 * @return - the current status of the Vertex Connector O Series status.
	 */
	public String getVertexOSeriesStatus( )
	{

		final String statusText = attribute.getElementAttribute(status, "class");

		return statusText;
	}

	/**
	 * @return Core version of Vertex O Series connector.
	 */
	public String getCoreVersion( )
	{
		final String version = this.getVersion(SitecoreVersion.CORE.getText());
		return version;
	}

	/**
	 * @return Adapter version of Vertex O Series connector.
	 */
	public String getAdapterVersion( )
	{
		final String version = this.getVersion(SitecoreVersion.ADAPTER.getText());
		return version;
	}

	/**
	 * This method is used to get the specified version type version number
	 *
	 * @param versionType - takes either "Core version" or "Adapter Version" as value.
	 *
	 * @return the version number of the given version type.
	 */
	private String getVersion( final String versionType )
	{

		List<WebElement> versionsList = element.getWebElements(version);
		String version = null;

		boolean flag = false;

		for ( WebElement versionElement : versionsList )
		{

			String versionText = versionElement.getText();

			if ( versionText != null )
			{
				if ( versionText
					.toUpperCase()
					.contains(versionType.toUpperCase() + ":") )
				{
					version = versionText
						.replaceAll(versionType.toUpperCase() + ":", "")
						.trim();
					flag = true;
				}
			}
		}
		if ( !flag )
		{
			VertexLogger.log(String.format("Given version type: \"%s\" is not found", versionType),
				VertexLogLevel.ERROR, getClass());
		}
		return version;
	}
}
