package com.vertex.quality.connectors.ariba.portal.pages.requisition;

import com.vertex.quality.common.enums.PageScrollDestination;
import com.vertex.quality.connectors.ariba.portal.enums.AribaPortalDashboardTab;
import com.vertex.quality.connectors.ariba.portal.interfaces.AribaPortalTextField;
import com.vertex.quality.connectors.ariba.portal.pages.catalog.AribaPortalCatalogHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * the representation of the page where a product that isn't contained in any current catalog is
 * created so that it can be added to the current order
 *
 * @author legacyAribaProgrammer ssalisbury
 */
public class AribaPortalRequisitionNonCatalogItemCreationPage extends AribaPortalRequisitionCatalogEditItemDetailsPage
{
	protected final String addToCartButtonText = "Add to Cart";

	protected final By contentHeaderLoc = By.id("catContentId");
	protected final By contentHeaderButtonsContainerClass = By.className("a-non-cat-itm-act-btn");
	protected final By addToCartButtonClass = By.className("w-btn-primary");

	public AribaPortalRequisitionNonCatalogItemCreationPage( WebDriver driver )
	{
		super(driver);
	}

	@Override
	protected void waitForUniqueElement( )
	{
	}

	/**
	 * finalizes the creation of the non-catalog product & adds it to the cart
	 *
	 * @return the catalog home page
	 *
	 * @author legacyAribaProgrammer ssalisbury
	 */
	public AribaPortalCatalogHomePage clickAddToCart( )
	{
		AribaPortalCatalogHomePage catalogHome = null;

		WebElement addToCartButton = findAddToCartButton();

		click.clickElement(addToCartButton, PageScrollDestination.VERT_CENTER);
		waitForPageLoad();
		wait.waitForElementDisplayed(AribaPortalDashboardTab.CATALOG.tabLoadedIndicatorLoc);
		catalogHome = initializePageObject(AribaPortalCatalogHomePage.class);

		return catalogHome;
	}

	/**
	 * finds the button that finalizes the creation of the non-catalog product & adds it to the cart
	 *
	 * @return the button that finalizes the creation of the non-catalog product & adds it to
	 * the cart
	 *
	 * @author legacyAribaProgrammer ssalisbury
	 */
	protected WebElement findAddToCartButton( )
	{
		WebElement addToCartButton = null;

		WebElement header = wait.waitForElementDisplayed(contentHeaderLoc);
		WebElement buttonContainer = wait.waitForElementDisplayed(contentHeaderButtonsContainerClass, header);
		List<WebElement> buttons = wait.waitForAllElementsDisplayed(addToCartButtonClass, buttonContainer);

		addToCartButton = element.selectElementByAttribute(buttons, addToCartButtonText, "innerText");

		return addToCartButton;
	}

	public enum NonCatalogTextField implements AribaPortalTextField
	{
		// @formatter off;
		PURCHASING_ORGANIZATION("Purch Org:", true),
		COMMODITY_CODE("Commodity Code:", true),
		MATERIAL_GROUP("Material Group:", true),
		QUANTITY("Quantity:", false),
		UNIT_OF_MEASURE("Unit of Measure:", true),
		PRICE("Price:", false),
		TAX_JURISDICTION_CODE("Tax Jurisdiction code:", false),
		VENDOR("Vendor:", true),
		CONTACT("Contact",false),
		SUPPLIER_PART_NUMBER("Supplier Part Number:", false),
		SUPPLIER_AUXILIARY_PART_ID("Supplier Auxiliary Part ID:", false);
		// @formatter on;

		public String labelText;
		public boolean isCombobox;

		NonCatalogTextField( final String text, final boolean isCombo )
		{
			this.labelText = text;
			this.isCombobox = isCombo;
		}

		public String getLabel( )
		{
			return this.labelText;
		}

		public boolean isCombobox( )
		{
			return this.isCombobox;
		}
	}
}