package com.vertex.quality.connectors.dynamics365.nav.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents the vertex extension dialog
 *
 * @author bhikshapathi
 */
public class NavVertexExtensionDialog extends VertexComponent
{
    public NavVertexExtensionDialog(WebDriver driver, VertexPage parent )
    {
        super(driver, parent);
    }
    protected By dialogBoxLoc = By.className("ms-nav-content-box");
    protected By dialogFieldsLoc = By.className("ms-nav-edit-control-container");
    protected By versionFieldLoc = By.cssSelector("span[aria-label*='Version, ']");

    /**
     * checks if the version field is present
     * (actually checks if field displayed; when using versionFieldLoc, it is present
     * but does not return true as displayed)
     *
     * @return boolean indicating if the version field is present
     */
    public boolean isVersionFieldPresent( )
    {
        boolean isPresent;
        WebElement version = null;
        WebElement dialogBox = wait.waitForElementDisplayed(dialogBoxLoc);

        List<WebElement> fieldsList = wait.waitForAllElementsPresent(dialogFieldsLoc, dialogBox);
        for ( WebElement field : fieldsList )
        {
            String txt = field.getText();
            if ( txt.contains("Version") )
            {
                version = field;
                break;
            }
        }
        isPresent = element.isElementDisplayed(version);

        return isPresent;
    }
}
