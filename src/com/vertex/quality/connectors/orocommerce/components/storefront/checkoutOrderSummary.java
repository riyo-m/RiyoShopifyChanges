package com.vertex.quality.connectors.orocommerce.components.storefront;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author alewis
 */
public class checkoutOrderSummary extends VertexComponent
{
	public checkoutOrderSummary( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	protected By couponContainer = By.className("coupon-container");
	protected By couponContainerInput = By.className("coupon-container__input-wrap");
	protected By couponContainerForm = By.className("coupon-container__form");
	protected By btnClass = By.className("btn");
	protected By continueClass = By.className("tooltip-container");
	protected By tableClass = By.className("order-checkout-widget__table");
	protected By subTotalAmount = By.xpath(".//th[text()='Subtotal']/following-sibling::td");
	protected By discountAmount = By.xpath(".//th[text()='Discount']/following-sibling::td");
	protected By shippingAmount = By.xpath(".//th[text()='Shipping']/following-sibling::td");
	protected By taxAmount = By.xpath(".//th[text()='Tax']/following-sibling::td");
	protected By totalAmount = By.xpath(".//th[text()='Total']/following-sibling::td");
	protected By submitOrderButton = By.xpath(".//button[normalize-space(.)='Submit Order']");

	/**
	 * Counts percent based tax
	 *
	 * @param taxPercent need to pass percentage of percentage based tax
	 * @return calculated tax amount based on percentage
	 */
	public String calculatePercentBaseTax(double taxPercent) {
		double shipping = 0;
		double discount = 0;
		waitForPageLoad();
		wait.waitForElementPresent(submitOrderButton);
		if (element.isElementPresent(shippingAmount)) {
			shipping = Double.parseDouble(text.getElementText(shippingAmount).replace("$", ""));
		}
		if (element.isElementDisplayed(discountAmount)) {
			discount = Double.parseDouble(text.getElementText(discountAmount).replace("-", "").replace("$", ""));
		}
		double total = (Double.parseDouble(text.getElementText(subTotalAmount).replace("$", "").replaceAll(",", "")) + shipping - discount)
				* (taxPercent / 100);
		return "$" + String.format("%.2f", total);
	}

    /**
     * Counts percent based tax
     *
     * @param taxPercent need to pass percentage of percentage based tax
     * @param isShip     Pass true if ship amount to be included for tax & false to exclude ship amount
     * @return calculated tax amount based on percentage
     */
	public String calculatePercentBaseTax(double taxPercent, boolean isShip) {
		double shipping = 0;
		double discount = 0;
		waitForPageLoad();
		wait.waitForElementPresent(submitOrderButton);
		if (isShip) {
		    if (element.isElementPresent(shippingAmount)) {
			    shipping = Double.parseDouble(text.getElementText(shippingAmount).replace("$", ""));
		    }
        }
		if (element.isElementDisplayed(discountAmount)) {
			discount = Double.parseDouble(text.getElementText(discountAmount).replace("-", "").replace("$", ""));
		}
		double total = (Double.parseDouble(text.getElementText(subTotalAmount).replace("$", "")) + shipping - discount)
				* (taxPercent / 100);
		return "$" + String.format("%.2f", total);
	}

	/**
	 * Get Tax amount from UI
	 *
	 * @return total applicable tax amount
	 */
	public String getTaxFromUI() {
		waitForPageLoad();
		wait.waitForElementPresent(submitOrderButton);
		wait.waitForElementPresent(taxAmount);
		return text.getElementText(taxAmount);
	}

	/**
	 * get Tax amount calculated while invoice checkout.
	 *
	 * @return actualTaxTotal for the order.
	 */
	public String getTaxAmount( )
	{
		parent.refreshPage();
		String actualTaxTotal;
		WebElement totalFieldsCont = getTotalValuesCont();
		List<WebElement> totalLines = wait.waitForAllElementsDisplayed(By.tagName("tr"), totalFieldsCont);
		jsWaiter.sleep(10000);
		WebElement totalTaxLine = element.selectElementByContainedText(totalLines, "Tax");
		List<WebElement> tds = wait.waitForAllElementsDisplayed(By.tagName("td"), totalTaxLine);
		actualTaxTotal = tds
			.get(0)
			.getText();
		//		String rawText = totalTaxLine.getText();
		//		Pattern p = Pattern.compile("\\d+");
		//		Matcher m = p.matcher(rawText);
		//		actualTaxTotal =m.group();
		return actualTaxTotal;
	}

	/**
	 * get Total value count calculated while invoice checkout.
	 *
	 * @return totalCont for the order.
	 */
	public WebElement getTotalValuesCont( )
	{
		WebElement totalCont;
		totalCont = wait.waitForElementDisplayed(By.cssSelector("tbody[data-totals-container]"));
		return totalCont;
	}

	/**
	 * enter coupon code for applying discount.
	 *
	 * @param couponCode for the discount order.
	 */
	public void enterCouponCode( String couponCode )
	{
		WebElement coupon = wait.waitForElementDisplayed(couponContainer);
		click.clickElementCarefully(coupon);

		WebElement couponField = wait.waitForElementDisplayed(couponContainerInput);
		text.enterText(couponField, couponCode);

		WebElement couponForm = wait.waitForElementDisplayed(couponContainerForm);
		WebElement applyButton = wait.waitForElementDisplayed(btnClass, couponForm);
		click.clickElementCarefully(applyButton);
	}

	/**
	 * click continue button.
	 */
	public void clickContinueButton( )
	{
		WebElement continueButton = wait.waitForElementDisplayed(continueClass);
		click.clickElementCarefully(continueButton);
	}

	/**
	 * get Single Page checkout button.
	 */
	public String getTaxSinglePageCheckout( )
	{
		WebElement table = wait.waitForElementDisplayed(tableClass);
		String tableText = text.getElementText(table);

		return tableText;
	}
}
