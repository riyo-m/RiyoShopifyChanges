package com.vertex.quality.connectors.dynamics365.retail.pages;

import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Discount page common methods and object declaration page
 *
 * @author Sgupta
 */

public class DRetailDiscountPage extends DFinanceBasePage
{

	public DRetailDiscountPage( WebDriver driver )
	{
		super(driver);
	}

	protected By DISCOUNT_BUTTON = By.xpath("//button[@data-ax-bubble='tab_discounts']");
	protected By LINE_DISCOUNT_PERCENT = By.xpath("//button[@title='Line discount percent']");
	protected By LINE_DISCOUNT_AMOUNT = By.xpath("//button[@title='Line discount amount']");
	protected By TOTAL_DISCOUNT_PERCENT = By.xpath("(//button[@title='Total discount percent'])[1]");
	protected By TOTAL_DISCOUNT_AMOUNT = By.xpath("//button[@title='Total discount amount']");
	protected By NUMPAD_ONE = By.xpath("//div[@data-ax-bubble='addDiscountDialog_discountNumPad']//div[contains(text( ), '1')]");
	protected By NUMPAD_ZERO = By.xpath("//div[@data-ax-bubble='addDiscountDialog_discountNumPad']//div[contains(text( ), '0')]");
	protected By ENTER_BUTTON = By.xpath("//div[@data-ax-bubble='addDiscountDialog_discountNumPad']//button[@aria-label='Enter']");
	protected By CUSTOMER_SATISFACTION = By.xpath("//div[contains(text(), 'Customer Satisfaction')]");
	protected By DISCOUNT_INPUT = By.xpath("//input[contains(@aria-label,'Enter a discount')]");
	/**
	 * click discounts
	 */

	public void clickDiscount ()
	{
		wait.waitForElementPresent(DISCOUNT_BUTTON);
		click.clickElementCarefully(DISCOUNT_BUTTON);

	}

	/**
	 * select line discount percent
	 */

	public void selectLineDiscountPercent ()
	{
		wait.waitForElementPresent(LINE_DISCOUNT_PERCENT);
		click.clickElementCarefully(LINE_DISCOUNT_PERCENT);

	}

	/**
	 * select line discount amount
	 */

	public void selectLineDiscountAmount ()
	{
		wait.waitForElementPresent(LINE_DISCOUNT_AMOUNT);
		click.clickElementCarefully(LINE_DISCOUNT_AMOUNT);

	}

	/**
	 * select total discount percent
	 */
	public void selectTotalDiscountPercent ()
	{
		wait.waitForElementPresent(TOTAL_DISCOUNT_PERCENT);
		click.clickElementCarefully(TOTAL_DISCOUNT_PERCENT);

	}

	/**
	 * select total discount amount
	 */
	public void selectTotalDiscountAmount ()
	{
		wait.waitForElementPresent(TOTAL_DISCOUNT_AMOUNT);
		click.clickElementCarefully(TOTAL_DISCOUNT_AMOUNT);

	}

	/**
	 * enter discount percent
	 */

	public void enterDiscountPercent ()
	{
		wait.waitForElementDisplayed(NUMPAD_ONE);
		click.clickElementCarefully(NUMPAD_ONE);
		click.clickElementCarefully(NUMPAD_ZERO);
		click.clickElementCarefully(ENTER_BUTTON);

	}

	/**
	 * enter discount amount
	 * @param discountAmount
	 */
	public void enterDiscountAmount (String discountAmount)
	{
		wait.waitForElementDisplayed(NUMPAD_ONE);
		click.clickElementCarefully(By.xpath("//div[@data-ax-bubble='addDiscountDialog_discountNumPad']//div[contains(text( ), '"+discountAmount+"')]"));
		click.clickElementCarefully(ENTER_BUTTON);

	}

	/**
	 * select customer satisfaction
	 */

	public void selectCustomerSatisfaction()
	{
		wait.waitForElementDisplayed(CUSTOMER_SATISFACTION);
		click.clickElementCarefully(CUSTOMER_SATISFACTION);

	}

	/**
	 * enter line discount percent
	 * @param percent
	 */
	public void enterLineDiscountPercent(String percent) {
		selectLineDiscountPercent();
		WebElement discountInput = wait.waitForElementDisplayed(DISCOUNT_INPUT);
		discountInput.sendKeys(percent);
		click.clickElementIgnoreExceptionAndRetry(ENTER_BUTTON);
		selectCustomerSatisfaction();
	}

	/**
	 * enter line discount amount
	 * @param amount
	 */
	public void enterLineDiscountAmount(String amount) {
		selectLineDiscountAmount();
		WebElement discountInput = wait.waitForElementDisplayed(DISCOUNT_INPUT);
		discountInput.sendKeys(amount);
		click.clickElementIgnoreExceptionAndRetry(ENTER_BUTTON);
		selectCustomerSatisfaction();
	}

	/**
	 * enter total discount amount
	 * @param percent
	 */
	public void enterTotalDiscountPercent(String percent) {
		selectTotalDiscountPercent();
		WebElement discountInput = wait.waitForElementDisplayed(DISCOUNT_INPUT);
		discountInput.sendKeys(percent);
		click.clickElementIgnoreExceptionAndRetry(ENTER_BUTTON);
		selectCustomerSatisfaction();
	}

	/**
	 * enter total discount amount
	 * @param amount
	 */
	public void enterTotalDiscountAmount(String amount) {
		selectTotalDiscountAmount();
		WebElement discountInput = wait.waitForElementDisplayed(DISCOUNT_INPUT);
		discountInput.sendKeys(amount);
		click.clickElementIgnoreExceptionAndRetry(ENTER_BUTTON);
		selectCustomerSatisfaction();
	}

}
