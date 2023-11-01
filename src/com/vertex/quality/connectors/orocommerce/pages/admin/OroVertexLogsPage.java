package com.vertex.quality.connectors.orocommerce.pages.admin;

import com.vertex.quality.connectors.orocommerce.pages.base.OroAdminBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author alewis
 */
public class OroVertexLogsPage extends OroAdminBasePage {
    public OroVertexLogsPage(WebDriver driver) {
        super(driver);
    }

    By idClass = By.className("grid-body-cell-id");
    By summaryClass = By.className("grid-body-cell-summary");
    By cogClass = By.className("");
    By eyeClass = By.xpath("//tbody/tr[1]/td[4]");
    By trashClass = By.className("fa-trash-o");
    By selectAllSelector = By.cssSelector(".checkbox-view__input.input-widget");
    By ellipsisSelector = By.cssSelector(".fa-ellipsis-h.icon");
    By deleteAllLogsButtonSelector = By.cssSelector(".dropdown-menu.dropdown-menu__action-column");


    public void clickLogID(String IDNumber) {
        List<WebElement> ids = wait.waitForAllElementsDisplayed(idClass);

        for (WebElement id : ids) {

            String idString = text.getElementText(id);

            if (idString.equals("ID")) {
                click.clickElementCarefully(id);
            }
        }
    }

    public void clickLog() {
        List<WebElement> eyes = wait.waitForAllElementsPresent(eyeClass);
        WebElement eye = eyes.get(0);
        click.clickElementCarefully(eye);
    }

    public boolean deleteLogsButtonPresent() {
        WebElement deleteButton = wait.waitForElementPresent(trashClass);
        boolean isDeleteButtonDisplayed = deleteButton.isEnabled();

        return isDeleteButtonDisplayed;

    }

    public void deleteAllLogs() {
        WebElement deleteButton = wait.waitForElementPresent(selectAllSelector);
        click.clickElementCarefully(deleteButton);
        List<WebElement> ellipses = wait.waitForAllElementsPresent(ellipsisSelector);
        WebElement ellipsis = ellipses.get(0);
        click.clickElementCarefully(ellipsis);
        List<WebElement> deleteButtons = wait.waitForAllElementsPresent(deleteAllLogsButtonSelector);
        WebElement deleteAllButton = deleteButtons.get(0);
        //click.clickElementCarefully(deleteAllButton);
        System.out.println();
    }
}
