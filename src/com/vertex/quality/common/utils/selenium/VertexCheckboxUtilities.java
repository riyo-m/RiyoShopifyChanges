package com.vertex.quality.common.utils.selenium;

import com.vertex.quality.common.pages.VertexAutomationObject;
import com.vertex.quality.common.utils.selenium.base.VertexSeleniumUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Common functions for interacting with checkboxes. This should be declared as an instance
 * variable in VertexAutomationObject
 *
 * @author hho
 */
public class VertexCheckboxUtilities extends VertexSeleniumUtilities
{
	public VertexCheckboxUtilities( final VertexAutomationObject pageObject, final WebDriver driver )
	{
		super(pageObject, driver);
	}

	/**
	 * determines whether the checkbox is checked (only works on input elements)
	 *
	 * @param loc specifies which element the checkbox is
	 *
	 * @return whether the checkbox is checked
	 *
	 * @author ssalisbury
	 */
	public boolean isCheckboxChecked( final By loc )
	{
		WebElement checkbox = base.wait.waitForElementPresent(loc);
		return isCheckboxChecked(checkbox);
	}

	/**
	 * determines whether the checkbox is checked (only works on input elements)
	 *
	 * @param checkbox the checkbox whose status is being determined
	 *
	 * @return whether the checkbox is checked
	 *
	 * @author ssalisbury
	 */
	public boolean isCheckboxChecked( final WebElement checkbox )
	{
		boolean isChecked = checkbox.isSelected();
		return isChecked;
	}

	/**
	 * sets a checkbox to be checked or to be unchecked (only works on input elements)
	 *
	 * @param loc             specifies which element is the
	 *                        checkbox being checked or unchecked
	 * @param shouldBeChecked whether the checkbox
	 *                        should be checked or whether it should be unchecked
	 *
	 * @author ssalisbury
	 */
	public void setCheckbox( final By loc, final boolean shouldBeChecked )
	{
		WebElement checkbox = base.wait.waitForElementEnabled(loc);

		setCheckbox(checkbox, shouldBeChecked);
	}

	/**
	 * sets a checkbox to be checked or to be unchecked (only works on input elements)
	 *
	 * @param checkbox        the checkbox being checked or unchecked
	 * @param shouldBeChecked whether the checkbox
	 *                        should be checked or whether it should be unchecked
	 *
	 * @author ssalisbury
	 */
	public void setCheckbox( final WebElement checkbox, final boolean shouldBeChecked )
	{
		base.wait.waitForElementEnabled(checkbox);
		boolean isAlreadyChecked = isCheckboxChecked(checkbox);

		if ( isAlreadyChecked != shouldBeChecked )
		{
			base.click.clickElement(checkbox);
		}
	}
}
