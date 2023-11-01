package com.vertex.quality.connectors.ariba.portal.components.requisition;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.ariba.portal.pages.requisition.AribaPortalRequisitionSummaryPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * the popup after a PR has been submitted
 *
 * @author ssalisbury dgorecki
 */
public class AribaPortalRequisitionSubmittedNotification extends VertexComponent
{
	protected final By requisitionSubmittedNotification = By.className("w-md-ct-conf");
	protected final By viewRequisitionButton = By.className("w-btn-small");
	protected final String requisitionButtonText = "View Requisition";

	public AribaPortalRequisitionSubmittedNotification( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * Opens the submitted requisition after submission
	 *
	 * @return a page object representing the requisition summary page
	 *
	 * @author ssalisbury dgorecki
	 */
	public AribaPortalRequisitionSummaryPage openRequisition( )
	{
		AribaPortalRequisitionSummaryPage requisitionSummaryPage;

		WebElement confMsgBox = wait.waitForElementDisplayed(requisitionSubmittedNotification);
		List<WebElement> notificationButtons = wait.waitForAllElementsDisplayed(viewRequisitionButton, confMsgBox);

		for ( WebElement button : notificationButtons )
		{
			String buttonText = text.getElementText(button);
			if ( requisitionButtonText.equals(buttonText) )
			{
				click.clickElement(button);
				break;
			}
		}

		requisitionSummaryPage = initializePageObject(AribaPortalRequisitionSummaryPage.class);

		return requisitionSummaryPage;
	}
}
