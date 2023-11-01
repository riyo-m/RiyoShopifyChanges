package com.vertex.quality.connectors.sitecore.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.sitecore.pages.base.SitecoreBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Site-core Administration Content Editor page
 *
 * @author Shiva Mothkula, Daniel Bondi
 */

public class SitecoreContentEditorPage extends SitecoreBasePage
{

	By nodeLoc = By.className("scContentTreeNode");
	By outerFrame = By.id("jqueryModalDialogsFrame");
	By yesNoDialogInnerFrame = By.className("ui-dialog-content");

	public SitecoreContentEditorPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * This method is used to expand the given tree node if not expanded.
	 *
	 * @param nodeName name of node to expand
	 */
	private void expandNode( final String nodeName )
	{

		List<WebElement> nodes = wait.waitForAllElementsEnabled(nodeLoc);
		WebElement targetNode = null;
		for ( WebElement node : nodes )
		{
			WebElement nodeFirstContainer = element.getWebElement(By.tagName("a"), node);
			WebElement nodeSecondContainer = element.getWebElement(By.tagName("span"), nodeFirstContainer);

			final String nodeText = text.getElementText(nodeSecondContainer);
			if ( nodeName.equals(nodeText) )
			{
				targetNode = node;
			}
		}
		WebElement nodeClickableElement = wait.waitForElementPresent(By.tagName("img"), targetNode);

		String nodeSrc = attribute.getElementAttribute(nodeClickableElement, "src");

		if ( nodeSrc.contains("expanded") )
		{
			VertexLogger.log(String.format("Node: %s is already expanded", nodeName), getClass());
		}
		else
		{
			wait.waitForElementEnabled(nodeClickableElement);
			click.clickElement(nodeClickableElement);
		}
	}

	/**
	 * get node by expanding other nodes and return webelement
	 *
	 * @param nodeName name of node
	 *
	 * @return node webelement
	 */
	protected WebElement getNode( final String nodeName )
	{
		List<WebElement> nodes = wait.waitForAllElementsEnabled(nodeLoc);

		WebElement targetNode = null;
		for ( WebElement node : nodes )
		{
			WebElement nodeFirstContainer = element.getWebElement(By.tagName("a"), node);
			WebElement nodeSecondContainer = element.getWebElement(By.tagName("span"), nodeFirstContainer);

			final String nodeText = text.getElementText(nodeSecondContainer);

			if ( nodeName.equals(nodeText) )
			{
				targetNode = nodeSecondContainer;
			}
		}
		targetNode = wait.waitForElementPresent(By.tagName("img"), targetNode);
		return targetNode;
	}

	/**
	 * This method is used to navigate to Sitecore > Content > Product Repository >
	 * Vertex Settings page
	 */
	public SitecoreVertexSettingsContentPage navigateToVertexSettingsContentPage( )
	{

		// expand sitecore
		expandNode("sitecore");
		expandNode("Content");
		expandNode("Product Repository");

		waitForPageLoad();

		WebElement vertexSettingsNode = getNode("Vertex Settings");

		wait.waitForElementDisplayed(vertexSettingsNode);
		click.clickElement(vertexSettingsNode);
		waitForPageLoad();

		SitecoreVertexSettingsContentPage vertexSettingsPage = initializePageObject(
			SitecoreVertexSettingsContentPage.class);

		return vertexSettingsPage;
	}

	/**
	 * switch frame to yes no dialog popup frame
	 */
	private void switchToYesOrNoDialogFrame( )
	{
		this.switchToOuterFrame();
		window.waitForAndSwitchToFrame(yesNoDialogInnerFrame);
	}

	/**
	 * click yes button in yes/no dialog popup
	 */
	public void clickYesButton( )
	{
		String buttonText = "Yes";
		switchToYesOrNoDialogFrame();
		List<WebElement> buttons = wait.waitForAnyElementsDisplayed(By.tagName("button"));

		WebElement buttonElement = null;

		for ( WebElement button : buttons )
		{
			String rawText = text.getHiddenText(button);
			if ( rawText != null )
			{
				String cleanText = rawText.trim();
				if ( buttonText.equals(cleanText) )
				{
					buttonElement = button;
					break;
				}
			}
		}

		click.clickElement(buttonElement);
		waitForPageLoad();
	}

	public void saveCompanyDetails( )
	{

		navigateToVertexSettingsContentPage();

		clickYesButton();
	}
}
