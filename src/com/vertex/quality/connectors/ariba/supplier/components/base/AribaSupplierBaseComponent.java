package com.vertex.quality.connectors.ariba.supplier.components.base;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * base component class to contain all the common methods in all the component classes
 *
 * @author osabha
 */
public class AribaSupplierBaseComponent extends VertexComponent
{
	public AribaSupplierBaseComponent( final WebDriver driver, final VertexPage parent )
	{
		super(driver, parent);
	}

	protected final By clickableCheckBoxLoc = By.className("w-rdo-dsize");
	protected final By visibleCheckBoxLoc = By.className("w-rdo-native");
	protected final By radioButtonLabelLoc = By.className("radio-label-spacing");

	/**
	 * locates all the  radio buttons and makes sure its enabled.
	 *
	 * @param displayText the displayed text of the target checkbox.
	 */
	public void enableCheckBoxByDisplayText( final String displayText )
	{
		WebElement targetVisibleCheckBox;
		WebElement targetClickableCheckBox;
		int buttonIndex = 0;
		WebElement radioButtonContainer = null;

		List<WebElement> listOfPossibleRadioButtonContainers = wait.waitForAllElementsPresent(radioButtonLabelLoc);

		for ( int i = 0 ; i < listOfPossibleRadioButtonContainers.size() ; i++ )
		{
			WebElement container = listOfPossibleRadioButtonContainers.get(i);
			String rawText = text.getElementText(container);
			String cleanText = rawText.trim();
			if ( displayText.equals(cleanText) )
			{
				radioButtonContainer = container.findElement(By.xpath(".."));
				buttonIndex = i;
				break;
			}
		}
		List<WebElement> visibleCheckBoxes = wait.waitForAllElementsPresent(visibleCheckBoxLoc, radioButtonContainer);
		targetVisibleCheckBox = visibleCheckBoxes.get(buttonIndex);
		List<WebElement> clickableCheckBoxes = wait.waitForAllElementsPresent(clickableCheckBoxLoc, radioButtonContainer);
		targetClickableCheckBox = clickableCheckBoxes.get(buttonIndex);
		if ( !checkbox.isCheckboxChecked(targetVisibleCheckBox) )
		{

			checkbox.setCheckbox(targetClickableCheckBox, true);
		}

		waitForPageLoad();
	}

	/**
	 * locates a specific field or button container
	 *
	 * @param expectedText label of the field or button
	 * @param parentCont   locator of element container
	 *
	 * @return webElement of the field container
	 */
	protected WebElement getFieldCont( final String expectedText, final By contLoc, final WebElement parentCont )
	{
		WebElement fieldContainer = null;
		List<WebElement> potentialFieldConts = wait.waitForAnyElementsDisplayed(contLoc, parentCont);

		for ( WebElement container : potentialFieldConts )
		{
			String rawText = text.getElementText(container);
			String cleanText = rawText.trim();
			if ( expectedText.equals(cleanText) )
			{
				fieldContainer = container.findElement(By.xpath(".."));
				break;
			}
		}
		return fieldContainer;
	}
}
