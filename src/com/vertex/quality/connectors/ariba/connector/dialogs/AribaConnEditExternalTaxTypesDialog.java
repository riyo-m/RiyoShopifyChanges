package com.vertex.quality.connectors.ariba.connector.dialogs;

import com.vertex.quality.common.dialogs.VertexDialog;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnComponentTaxTypesPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the dialog for editing external tax types in the {@link AribaConnComponentTaxTypesPage}
 *
 * @author ssalisbury
 */
public class AribaConnEditExternalTaxTypesDialog extends VertexDialog
{
	protected final By dialogContainer = By.id("edit-ext-tax-type-drawer");
	protected final By closeButton = By.id("close-ext-tax-type-edit-drawer");

	protected final By applyButton = By.className("btn--primary");
	protected final By cancelButton = By.className("btn--tertiary");

	protected final By availableTypesListContainer = By.id("masterSummaryTaxTypes");
	protected final By selectedTypesListContainer = By.id("componentSummaryTaxTypes");

	public AribaConnEditExternalTaxTypesDialog( final WebDriver driver, final VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * closes the dialog
	 */
	public void closeExternalTypesEditor( )
	{
		click.clickElement(closeButton);
	}

	/**
	 * saves the edits that have been made to the lists of external tax types
	 */
	public void submitChanges( )
	{
		WebElement dialogElem = wait.waitForElementPresent(dialogContainer);
		WebElement applyButtonElem = wait.waitForElementEnabled(applyButton, dialogElem);
		click.clickElement(applyButtonElem);
	}

	/**
	 * cancels the edits that have been made to the lists of external tax types
	 */
	public void abortChanges( )
	{
		WebElement dialogElem = wait.waitForElementPresent(dialogContainer);
		WebElement cancelButtonElem = wait.waitForElementEnabled(cancelButton, dialogElem);
		click.clickElement(cancelButtonElem);
	}

	/**
	 * Alters {@link #getExternalTypes(By)} to get the external tax types that are listed as available
	 */
	public List<String> getAvailableExternalTypes( )
	{
		List<String> availableTypes = getExternalTypes(availableTypesListContainer);
		return availableTypes;
	}

	/**
	 * Alters {@link #getExternalTypes(By)} to get the external tax types that are listed as selected
	 */
	public List<String> getSelectedExternalTypes( )
	{
		List<String> selectedTypes = getExternalTypes(selectedTypesListContainer);
		return selectedTypes;
	}

	/**
	 * gets the names of each of a list of external tax types in a container
	 *
	 * @param typesListContainer an element which contains a list of external tax types
	 *
	 * @return the external tax types in the container
	 */
	protected List<String> getExternalTypes( final By typesListContainer )
	{
		List<String> externalTypes = new ArrayList<>();

		WebElement externalTypesContainerElem = wait.waitForElementPresent(typesListContainer);
		List<WebElement> typeElems = wait.waitForAnyElementsDisplayed(By.tagName("li"), externalTypesContainerElem);
		typeElems.removeIf(elem -> !element.isElementDisplayed(elem));
		for ( WebElement typeElem : typeElems )
		{
			final String externalType = text.getElementText(typeElem);
			externalTypes.add(externalType);
		}

		return externalTypes;
	}

	//TODO can get the tax type elements in one of the lists and select by visible text- click and drag?
}
