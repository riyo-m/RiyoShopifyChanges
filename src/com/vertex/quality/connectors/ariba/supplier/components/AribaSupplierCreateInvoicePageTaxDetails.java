package com.vertex.quality.connectors.ariba.supplier.components;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.ariba.supplier.components.base.AribaSupplierBaseComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * this class represents the tax details section of the Supplier create standard invoice page
 * contains all the methods necessary to interact with this component
 *
 * @author osabha
 */
public class AribaSupplierCreateInvoicePageTaxDetails extends AribaSupplierBaseComponent
{
	public AribaSupplierCreateInvoicePageTaxDetails( final WebDriver driver, final VertexPage parent )
	{
		super(driver, parent);
	}

	protected final By taxCategoriesList = By.id("taxcreator0");
	protected final By categoryOptionsLoc = By.className("w-pmi-item");
	protected final By taxAmountFieldContLoc = By.cssSelector(".base-ncd-label.ANXLabel");
	protected final By taxRadioButtonContLoc = By.cssSelector(".inv-chooser-title.ANXLabel");
	protected final By taxSectionFieldsContLoc = By.cssSelector("table[class='fdml-st-fld-tax-dtl']");

	/**
	 * locates and clicks on the category field and then selects the desired tax category
	 * from the list
	 *
	 * @param category the tax category to select
	 */
	//this method works, but doesn't have the best logic, might do changes on in in the near future.....osabha
	public void setTaxCategory( final String category )
	{
		WebElement parentCont = wait.waitForElementDisplayed(taxSectionFieldsContLoc);
		String expectedText = "Category:";
		WebElement fieldContainer = getFieldCont(expectedText, taxRadioButtonContLoc, parentCont);
		WebElement categoryField = wait.waitForElementEnabled(By.tagName("input"), fieldContainer);
		click.clickElement(categoryField);
		WebElement categoryListCont = wait.waitForElementDisplayed(taxCategoriesList);
		List<WebElement> categoriesList = wait.waitForAllElementsPresent(categoryOptionsLoc, categoryListCont);
		WebElement targetCategory = element.selectElementByText(categoriesList, category);
		click.clickElementCarefully(targetCategory);
	}

	/**
	 * locates the tax amount field and enters the tax amount into it
	 *
	 * @param taxAmount tax amount to enter
	 */
	public void enterTaxAmount( final String taxAmount )
	{
		WebElement parentCont = wait.waitForElementDisplayed(taxSectionFieldsContLoc);
		String expectedText = "Tax Amount:";
		WebElement fieldContainer = getFieldCont(expectedText, taxAmountFieldContLoc, parentCont);
		WebElement taxAmountField = wait.waitForElementEnabled(By.tagName("input"), fieldContainer);
		text.enterText(taxAmountField, taxAmount);
		waitForPageLoad();
	}

	/**
	 * locates the tax rate field and enters the desired rate into it.
	 *
	 * @param taxRate rate of the tax to add to header level.
	 */
	public void enterTaxRate( final String taxRate )
	{
		WebElement parentCont = wait.waitForElementDisplayed(taxSectionFieldsContLoc);
		String expectedText = "Rate(%):";
		WebElement fieldContainer = getFieldCont(expectedText, taxAmountFieldContLoc, parentCont);
		WebElement taxRateField = wait.waitForElementEnabled(By.tagName("input"), fieldContainer);
		text.enterText(taxRateField, taxRate);
		waitForPageLoad();
	}
}

