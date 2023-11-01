package com.vertex.quality.connectors.hybris.pages.admin;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.hybris.pages.HybrisBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the Admin Extensions Page
 * This page is shown Versions of All Vertex Extensions
 *
 * @author Nagaraju Gampa
 */
public class HybrisAdminExtensionsPage extends HybrisBasePage
{
	public HybrisAdminExtensionsPage( WebDriver driver )
	{
		super(driver);
	}

	protected By SHOW_ENTRIES_DROPDOWN = By.cssSelector("select[name='extensions_length']");
	protected By SEARCH_INPUT = By.xpath(".//*[@id='extensions_filter']//input");
	protected By RESULT_EMPTY_TABLE = By.className("dataTables_empty");
	protected By EXTENSIONS_INFO = By.id("extensions_info");
	protected By RESULT_DATA_ROW = By.xpath("//*[@id='extensions']/tbody/tr[@class='odd' or @class='even']");
	protected By EXTENSIONS_TABLE_HEADINGS_ROW = By.cssSelector("#extensions>thead>tr>th");
	protected String EXTENSION_VERSION = ".//tr//td[normalize-space(.)='<<text_replace>>']//following-sibling::td[1]";

	public int searchVertexExtensionsList( final String searchText )
	{
		int rowCount = 0;
		dropdown.selectDropdownByDisplayName(SHOW_ENTRIES_DROPDOWN, "all");
		waitForPageLoad();
		text.enterText(SEARCH_INPUT, searchText);
		waitForPageLoad();
		if ( element.isElementDisplayed(EXTENSIONS_INFO) )
		{
			rowCount = element
				.getWebElements(RESULT_DATA_ROW)
				.size();
			final String elementMessage = String.format("Matching records found for the search text %s is: %s ",
				searchText, rowCount);
			VertexLogger.log(elementMessage);
		}
		else if ( element.isElementDisplayed(RESULT_EMPTY_TABLE) )
		{
			final String elementMessage = String.format("No matching records found for the search text %s", searchText);
			VertexLogger.log(elementMessage);
		}
		return rowCount;
	}

	/**
	 * Get All Headings of Vertex Extensions from Extensions page
	 *
	 * @return - All Heading Names
	 */
	public List<String> getExtensionsTableHeadings( )
	{
		final List<WebElement> heading_elements_list = element.getWebElements(EXTENSIONS_TABLE_HEADINGS_ROW);
		final List<String> elements = new ArrayList<String>();
		for ( final WebElement element : heading_elements_list )
		{
			final String ele = element.getText();
			elements.add(ele);
		}
		return elements;
	}

	/**
	 * Get VertexExtension Details and add to MAP
	 *
	 * @param extensionName - extension or Heading Name
	 *
	 * @return - Version Details of all required Vertex Extensions
	 */
	public Map<String, String> getExtensionVersionDetailsAsDictionary( String extensionName )
	{
		final String row = String.format("//tr[td[contains(text(),'%s')]]", extensionName);
		final Map<String, String> resultDict = new HashMap<String, String>();
		element.isElementDisplayed(By.xpath(row));
		final String name = attribute
			.getElementAttribute(By.xpath(row + "/td[1]"), "textContent")
			.trim();
		resultDict.put("Name", name);
		final String version = attribute
			.getElementAttribute(By.xpath(row + "/td[2]"), "textContent")
			.trim();
		resultDict.put("Version", version);
		final String core = attribute
			.getElementAttribute(By.xpath(row + "/td[3]/img"), "alt")
			.trim();
		resultDict.put("Core", core);
		final String hmc = attribute
			.getElementAttribute(By.xpath(row + "/td[4]/img"), "alt")
			.trim();
		resultDict.put("HMC", hmc);
		final String web = attribute
			.getElementAttribute(By.xpath(row + "/td[5]"), "textContent")
			.trim();
		resultDict.put("Web", web);
		return resultDict;
	}

	/**
	 * Get Extension's version from the UI from Hybris admin console.
	 *
	 * @param extensionName pass the extension name which version to be fetched from UI.
	 * @return version of particular extension
	 */
	public String getExtensionVersionFromUI(String extensionName) {
		hybrisWaitForPageLoad();
		return text.getElementText(By.xpath(EXTENSION_VERSION.replace("<<text_replace>>", extensionName)));
	}
}
