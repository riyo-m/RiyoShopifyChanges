package com.vertex.quality.connectors.magento.admin.pages;

import com.vertex.quality.connectors.magento.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * The Magento Platinum Partners page where Vertex is listed as a Partner
 *
 * @author alewis
 */
public class M2AdminPartnersPage extends MagentoAdminPage
{
	protected By partnersClass = By.className("partner-title");
	protected String vertex = "Vertex";

	public M2AdminPartnersPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * This method find locates Vertex listed as one of the platinum partners of Magento
	 *
	 * @return a String of Vertex
	 */
	public String findVertex( )
	{
		String vertexString = null;
		List<WebElement> partners = wait.waitForAllElementsPresent(partnersClass);

		for ( WebElement partner : partners )
		{
			String partnerName = partner.getText();

			if ( vertex.equals(partnerName) )
			{
				vertexString = partnerName;
			}
		}
		return vertexString;
	}
}
