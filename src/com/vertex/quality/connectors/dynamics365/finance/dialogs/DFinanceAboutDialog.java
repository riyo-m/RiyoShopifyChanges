package com.vertex.quality.connectors.dynamics365.finance.dialogs;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.dialogs.base.DFinanceBaseDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * contains information about the current setup of the site, eg. the installed version of D365 F&O
 * and the installed versions of various packages/models like Vertex's connector
 * Created by clicking the 'About' button in the dropdown from the 'Settings' (gear icon) button in the universal
 * header bar
 *
 * @author ssalisbury
 */
public class DFinanceAboutDialog extends DFinanceBaseDialog
{
	//SECTION- site platform
	protected final By productVersion = By.cssSelector("[id$='_FinalProductBuildVersion']");
	protected final String productVersionStartText = "Installed product version : ";

	protected final By platformVersion = By.cssSelector("[id$='KernelVersion']");
	protected final String platformVersionStartText = "Installed platform version : ";

	//SECTION- loaded packages/models
	protected final By loadedPackagesHeader = By.cssSelector("[id$='InstalledModulesGroup_text']");
	protected final String loadedPackagesHeaderName = "LOADED PACKAGES AND THEIR MODELS";
	protected final By loadedPackagesHeaderExpandButton = By.cssSelector("[id$='InstalledModulesGroup_hide']");
	protected final String loadedPackagesHeaderExpandButtonClass = "group-frameOptionButtonHide";

	protected final By loadedPackagesListContainer = By.className("treeView");
	protected final String loadedPackagesListContainerAttribute = "data-dyn-controlname";
	protected final String loadedPackagesListContainerControlName = "InstalledModules";

	protected final By loadedPackageContainer = By.className("treeView-treeItem");
	protected final By loadedPackageExpandButton = By.className("treeView-treeExpandCollapse");
	protected final By loadedPackageLabel = By.className("treeView-treeItemLabel");

	protected final By loadedModelsContainer = By.className("treeView-treeItem-children");
	protected final By loadedModelVersion = By.className("treeView-treeItemLabel");
	protected final By tab = By.className("pivot-item");
	protected final By installedModel = By.cssSelector("ul[id*='InstalledModulesList_list'] li");

	public DFinanceAboutDialog( final WebDriver driver, final VertexPage parent )
	{
		super(driver, parent);
	}

	//SECTION- site/platform

	/**
	 * Get Installed Product Version of D365
	 *
	 * @return a String containing the D365 product version under test
	 */
	public String getInstalledProductVersion( )
	{
		String version = null;
		WebElement productVersionElem = getInstalledProductVersionElem();
		if ( productVersionElem != null )
		{
			wait.waitForElementDisplayed(productVersionElem);
			version = attribute.getElementAttribute(productVersionElem, "textContent");
		}
		return version;
	}

	/**
	 * Get Installed Platform Version
	 *
	 * @return a String containing the D365 platform version under test
	 * returns null on error
	 */
	public String getInstalledPlatformVersion( )
	{
		String version = null;
		WebElement platformVersionElem = getInstalledPlatformVersionElem();
		if ( platformVersionElem != null )
		{
			wait.waitForElementDisplayed(platformVersionElem);
			version = attribute.getElementAttribute(platformVersionElem, "textContent");
		}
		return version;
	}

	/**
	 * finds the element containing the installed product version of D365
	 *
	 * @return the element containing the installed product version
	 * returns null on error
	 */
	protected WebElement getInstalledProductVersionElem( )
	{
		WebElement productVersionElem = null;
		List<WebElement> versionElems = wait.waitForAnyElementsDisplayed(productVersion);
		for ( WebElement elem : versionElems )
		{
			String elemText = text.getElementText(elem);
			String cleanText = text.cleanseWhitespace(elemText);
			if ( cleanText != null && cleanText.startsWith(productVersionStartText) )
			{
				productVersionElem = elem;
				break;
			}
		}
		if ( productVersionElem == null )
		{
			VertexLogger.log("failed to find product version", VertexLogLevel.ERROR);
		}

		return productVersionElem;
	}

	/**
	 * finds the element containing the installed version of the platform
	 *
	 * @return the element containing the installed version of the platform
	 * returns null on error
	 */
	protected WebElement getInstalledPlatformVersionElem( )
	{
		WebElement platformVersionElem = null;
		List<WebElement> versionElems = wait.waitForAnyElementsDisplayed(platformVersion);
		for ( WebElement elem : versionElems )
		{
			String elemText = text.getElementText(elem);
			String cleanText = text.cleanseWhitespace(elemText);
			if ( cleanText != null && cleanText.startsWith(platformVersionStartText) )
			{
				platformVersionElem = elem;
				break;
			}
		}
		if ( platformVersionElem == null )
		{
			VertexLogger.log("failed to find platform version", VertexLogLevel.ERROR, getClass());
		}

		return platformVersionElem;
	}

	//SECTION- loaded packages/models

	/**
	 * expand the list of Loaded Packages at the bottom of the 'About' dialog on right
	 */
	public void expandLoadedPackages( )
	{
		WebElement packagesExpandButton = getPackagesHeaderExpandButton();
		if ( packagesExpandButton != null )
		{
			final String expandedState = attribute.getElementAttribute(packagesExpandButton, "aria-expanded");
			boolean isExpanded = expandedState != null && Boolean.parseBoolean(expandedState);

			if ( !isExpanded )
			{
				List<WebElement> listHeaders = wait.waitForAnyElementsDisplayed(loadedPackagesHeader);
				WebElement loadedPackagesListHeaderElem = element.selectElementByText(listHeaders,
					loadedPackagesHeaderName);

				if ( loadedPackagesListHeaderElem != null )
				{
					click.clickElementCarefully(loadedPackagesListHeaderElem);
				}
			}
		}
	}

	/**
	 * gets the button for expanding the list of loaded packages in the About dialog
	 *
	 * @return the button for expanding the list of loaded packages in the About dialog
	 * returns null on error
	 */
	protected WebElement getPackagesHeaderExpandButton( )
	{
		WebElement packagesExpandButton = null;
		List<WebElement> expandButtons = wait.waitForAnyElementsDisplayed(loadedPackagesHeaderExpandButton);
		for ( WebElement button : expandButtons )
		{
			String buttonClasses = attribute.getElementAttribute(button, "class");
			if ( buttonClasses != null && buttonClasses.contains(loadedPackagesHeaderExpandButtonClass) )
			{
				packagesExpandButton = button;
				break;
			}
		}
		return packagesExpandButton;
	}

	/**
	 * finds the listed version for the first model inside the given package
	 *
	 * @param packageName the package's name
	 *
	 * @return the listed version is for the first model inside the given package
	 * returns null on error
	 */
	public String getPackageModelVersion( final String packageName )
	{
		String modelVersion = null;

		WebElement packageElem = getLoadedPackage(packageName);
		if ( packageElem != null )
		{
			WebElement packageModelsContainer = wait.waitForElementPresent(loadedModelsContainer, packageElem);

			WebElement firstModelLabel = wait.waitForElementDisplayed(loadedModelVersion, packageModelsContainer);
			modelVersion = text.getElementText(firstModelLabel);
		}

		return modelVersion;
	}

	/**
	 * finds a loaded package and makes sure that the list/dropdown of its contents is expanded
	 *
	 * @param packageName the name of the loaded package which should be expanded
	 */
	public void expandLoadedPackage( final String packageName )
	{
		WebElement packageElem = getLoadedPackage(packageName);
		if ( packageElem != null )
		{
			String packageExpandedState = attribute.getElementAttribute(packageElem, "aria-expanded");
			boolean isPackageExpanded = packageExpandedState != null && Boolean.parseBoolean(packageExpandedState);
			if ( !isPackageExpanded )
			{
				click.clickElementCarefully(loadedPackageExpandButton, packageElem);
			}
		}
	}

	/**
	 * finds the loaded package with the given name in the list of loaded packages
	 *
	 * This logs an error if the package could not be found
	 *
	 * @param packageName the name of the desired package
	 *
	 * @return the loaded package with the given name
	 * returns null on error
	 */
	protected WebElement getLoadedPackage( final String packageName )
	{
		WebElement packageElem = null;

		List<WebElement> packages = new ArrayList<>();

		List<WebElement> containers = wait.waitForAllElementsDisplayed(loadedPackagesListContainer);
		WebElement packagesContainer = element.selectElementByAttribute(containers,
			loadedPackagesListContainerControlName, loadedPackagesListContainerAttribute, true);
		if ( packagesContainer != null )
		{
			packages = wait.waitForAnyElementsDisplayed(loadedPackageContainer, packagesContainer);
		}

		packageElem = element.selectElementByNestedLabel(packages, loadedPackageLabel, packageName);

		return packageElem;
	}

	/**
	 * It will switch to Version Tab
	 */
	public void moveToVersionTab()
	{
		List<WebElement> containers = wait.waitForAllElementsPresent(tab);
		WebElement versionEle = element.selectElementByAttribute(containers,"Version","title");
		click.clickElementCarefully(versionEle);
	}

	/**
	 * Get Vertex Version
	 * @return Version
	 */
	public String getVertexVersion()
	{
		List<WebElement> containers = wait.waitForAllElementsPresent(installedModel);
		WebElement versionEle = element.selectElementByContainedText(containers,"Vertex");
		return versionEle.getText();
	}
}
