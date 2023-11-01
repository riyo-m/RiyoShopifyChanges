package com.vertex.quality.connectors.magentoTap.storefront.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoStorefrontPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Representation of the My Account page
 *
 * @author alewis
 */
public class M2StorefrontMyAccountPage extends MagentoStorefrontPage {
    protected By editLinksTag = By.tagName("span");

    protected By navItemsClass = By.className("nav");

    By maskClass = By.className("loading-mask");

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver
     */
    public M2StorefrontMyAccountPage(WebDriver driver) {
        super(driver);
    }

    /**
     * clicks the Manage Addresses link
     *
     * @return add or edit addresses page
     */
    public M2StorefrontAddOrEditAddressPage clickManageAddressesLink() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        List<WebElement> editLinksList = wait.waitForAllElementsDisplayed(editLinksTag);

        WebElement link = element.selectElementByText(editLinksList, "Manage Addresses");

        click.clickElement(link);

        M2StorefrontAddOrEditAddressPage addAddressPage = initializePageObject(M2StorefrontAddOrEditAddressPage.class);

        return addAddressPage;
    }

    /**
     * click Order By SKU button
     *
     * @return order by SKU page
     */
    public M2StorefrontOrderBySKUPage clickOrderBySkuButton() {
        wait.waitForElementNotDisplayed(maskClass);
        waitForPageLoad();
        WebElement navigtationLink = element.selectElementByText(navItemsClass, "Order by SKU");

        navigtationLink.click();

        M2StorefrontOrderBySKUPage orderBySKUPage = initializePageObject(M2StorefrontOrderBySKUPage.class);

        return orderBySKUPage;
    }
}
