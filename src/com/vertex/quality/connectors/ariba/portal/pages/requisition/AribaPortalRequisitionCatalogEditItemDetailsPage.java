package com.vertex.quality.connectors.ariba.portal.pages.requisition;

import com.vertex.quality.connectors.ariba.portal.interfaces.AribaPortalTextField;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * generic representation of a page on Ariba Portal that edits an item in a product requisition (PR)
 *
 * @author ssalisbury
 */
public abstract class AribaPortalRequisitionCatalogEditItemDetailsPage extends AribaPortalPostLoginBasePage
{
	protected final By sectionTitleLocator = By.className("w-sh-title");
	protected final By selectButtonParentListContLoc = By.cssSelector("table[role='presentation']");
	protected final By selectButtonListContLoc = By.cssSelector("tr[valign='middle']");

	public AribaPortalRequisitionCatalogEditItemDetailsPage( WebDriver driver )
	{
		super(driver);

		waitForUniqueElement();
	}

	protected void waitForUniqueElement( )
	{
		wait.waitForElementDisplayed(sectionTitleLocator);
	}

	/**
	 * retrieves the string that's currently stored & displayed in a text input field
	 *
	 * @param field the field whose contents are being retrieved
	 *
	 * @return the string that's currently stored & displayed in a text input field
	 *
	 * @author ssalisbury
	 */
	public String getTextFieldContents( AribaPortalTextField field )
	{
		String contents = null;

		WebElement fieldElem = findTextField(field);
		if ( fieldElem != null )
		{
			contents = attribute.getElementAttribute(fieldElem, "value");
		}

		return contents;
	}

	/**
	 * locates and clicks on the contact select button
	 * to select ship from ( Supplier)
	 *
	 * @author osabha
	 */
	public void clickContactSelectButton( )
	{
		String expectedConText = "Contact:";
		String expectedButtonText = "select";

		WebElement selectButtonCont = null;
		WebElement selectButton = null;
		WebElement listCon = wait.waitForElementPresent(selectButtonParentListContLoc);
		List<WebElement> selectButtonConts = wait.waitForAllElementsPresent(selectButtonListContLoc, listCon);
		selectButtonCont = element.selectElementByNestedLabel(selectButtonConts, By.tagName("label"), expectedConText);
		List<WebElement> selectButtons = wait.waitForAnyElementsDisplayed(By.tagName("a"), selectButtonCont);
		selectButton = element.selectElementByText(selectButtons, expectedButtonText);

		if ( selectButton != null )
		{
			click.clickElement(selectButton);
		}
		waitForUpdate();
	}
}
