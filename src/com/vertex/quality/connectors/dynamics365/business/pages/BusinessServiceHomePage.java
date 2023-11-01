package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.connectors.dynamics365.business.components.BusinessHomePageMainNavMenu;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import org.openqa.selenium.WebDriver;


public class BusinessServiceHomePage extends BusinessBasePage {
    public BusinessHomePageMainNavMenu mainNavMenu;

    public BusinessServiceHomePage(WebDriver driver) {
        super(driver);
        this.mainNavMenu=new BusinessHomePageMainNavMenu(driver, this);
    }

}
