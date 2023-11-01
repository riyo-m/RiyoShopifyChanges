package com.vertex.quality.connectors.dynamics365.nav.pages;

import com.vertex.quality.connectors.dynamics365.business.components.BusinessCompanyInfoDialog;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import com.vertex.quality.connectors.dynamics365.nav.components.NavCompanyInfoDialog;
import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavBasepage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * this class represents the Manual setup page and all the necessary methods to interact with it
 *
 * @author bhikshapathi
 */
public class NavManualSetupPage extends NavBasepage
{
    public NavCompanyInfoDialog companyInfoDialog;

    public NavManualSetupPage( WebDriver driver )
    {
        super(driver);
        this.companyInfoDialog = new NavCompanyInfoDialog(driver, this);
    }

    protected By categoryButtonLocs = By.className("thm-cont-u1-color-3--mintint--list-control-hovered");
    protected By categoryTintedButtonLoc = By.className("thm-cont-u1-color-3--medtint--list-control-hovered");
    protected By companyButtonLoc = By.cssSelector("a[aria-label='Name, Company']");

    /**
     * Selects Company manual setup button
     *
     * @return new instance of the Company info dialog.
     */
    public NavCompanyInfoDialog selectCompany( )
    {
        String txt = "Company";
        List<WebElement> buttonList = wait.waitForAllElementsPresent(categoryButtonLocs);

        WebElement companyButton = element.selectElementByText(buttonList, txt);

        if ( null == companyButton )
        {
            buttonList.clear();
            buttonList = wait.waitForAllElementsPresent(categoryTintedButtonLoc);
            companyButton = element.selectElementByText(buttonList, txt);
        }

        click.clickElement(companyButton);

        return companyInfoDialog;
    }
}
