package com.vertex.quality.connectors.ariba.portal.pages.requisition;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.ariba.portal.pages.common.AribaPortalPostLoginBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * The representation of the page that describes a completed/submitted PR
 *
 * @author legacyAribaProgrammer ssalisbury
 */
public class AribaPortalRequisitionSummaryPage extends AribaPortalPostLoginBasePage
{
	protected final By cartSummaryClass = By.className("a-appr-subtot");
	protected final By cartSummaryLabelClass = By.className("approvable_subtotal_title");

	protected final By cartSummarySubtotalContentClass = By.className("approvable_subtotal_content");
	protected final By cartSummarySubtotalSupplierPriceClass = By.className("approvable_subtotal_supplier_price");
	protected final By lineItemsTableContLoc = By.className("awtWrapperTable");
	protected final String cartSummaryLabelText = "Cart Summary";

	public AribaPortalRequisitionSummaryPage( WebDriver driver )
	{
		super(driver);
	}

	public String getRequisitionTaxes( )
	{
		String cartSummaryTax = null;

		WebElement cartSummary = null;

		List<WebElement> subtotalSections = wait.waitForAllElementsPresent(cartSummaryClass);
		summaryContainerSearch:
		for ( WebElement subtotalSection : subtotalSections )
		{
			List<WebElement> subtotalLabels = element.getWebElements(cartSummaryLabelClass, subtotalSection);
			WebElement subtotalLabel = null;
			int numLabels = subtotalLabels.size();
			if ( numLabels == 1 )
			{
				subtotalLabel = subtotalLabels.get(0);
			}
			else if ( numLabels != 0 )
			{
				String wrongNumberSubtotalLabels = String.format(
					"Irregular number (%d) of " + "label elements in a subtotal section of the page", numLabels);
				VertexLogger.log(wrongNumberSubtotalLabels, VertexLogLevel.WARN, getClass());
			}

			if ( subtotalLabel != null )
			{
				String subtotalLabelText = attribute.getElementAttribute(subtotalLabel, "innerText");
				boolean isTargetElement = cartSummaryLabelText.equals(subtotalLabelText);
				if ( isTargetElement )
				{
					cartSummary = subtotalSection;
					break summaryContainerSearch;
				}
			}
		}

		List<WebElement> subtotalContentContainers = wait.waitForAllElementsPresent(cartSummarySubtotalContentClass);
		wait.waitForElementDisplayed(cartSummarySubtotalSupplierPriceClass);

		priceSearch:
		for ( WebElement subtotalContentContainer : subtotalContentContainers )
		{
			List<WebElement> subtotalPrices = element.getWebElements(cartSummarySubtotalSupplierPriceClass,
				subtotalContentContainer);
			for ( WebElement subtotalPrice : subtotalPrices )
			{
				String subtotalPriceText = attribute.getElementAttribute(subtotalPrice, "innerText");

				if ( subtotalPriceText != null )
				{
					cartSummaryTax = subtotalPriceText;
					break priceSearch;
				}
			}
		}

		return cartSummaryTax;
	}

	/**
	 * gets the submitted order number
	 *
	 * @return order number as a string
	 */
	public String getOrderNumber( )
	{
		WebElement purchaseOrderNumber = null;
		WebElement tableParentCont = wait.waitForElementDisplayed(lineItemsTableContLoc);
		List<WebElement> aTags = wait.waitForAllElementsEnabled(By.tagName("a"), tableParentCont);

		String orderNumber;

		for ( WebElement element : aTags )
		{
			{
				String rawText = text.getElementText(element);
				if ( rawText != null )
				{
					String cleanText = text.cleanseWhitespace(rawText);
					if ( cleanText.contains("PO") )
					{
						purchaseOrderNumber = element;
						break;
					}
				}
			}
		}
		scroll.scrollElementIntoView(purchaseOrderNumber);
		waitForPageLoad();
		orderNumber = text.getElementText(purchaseOrderNumber);
		return orderNumber;
	}
}
