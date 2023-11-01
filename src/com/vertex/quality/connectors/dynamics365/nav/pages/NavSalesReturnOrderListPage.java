package com.vertex.quality.connectors.dynamics365.nav.pages;

import com.vertex.quality.connectors.dynamics365.nav.components.NavPagesNavMenu;
import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavBasepage;
import org.openqa.selenium.WebDriver;

/**
 * represents the sales return order list page
 * @author bhikshapathi
 */
public class NavSalesReturnOrderListPage extends NavBasepage
{
    public NavPagesNavMenu pageNavMenu;
    public NavSalesReturnOrderListPage(WebDriver driver) {
        super(driver);
        this.pageNavMenu = new NavPagesNavMenu(driver, this);

    }
}
