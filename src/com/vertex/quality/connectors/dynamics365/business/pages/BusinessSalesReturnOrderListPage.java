package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.connectors.dynamics365.business.components.BusinessPagesNavMenu;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
/**
 * represents the sales return order list page
 *
 * @author bhikshapathi
 */
public class BusinessSalesReturnOrderListPage extends BusinessBasePage
{
       public BusinessPagesNavMenu pageNavMenu;

    public BusinessSalesReturnOrderListPage( WebDriver driver )
    {
        super(driver);
        this.pageNavMenu = new BusinessPagesNavMenu(driver, this);
    }
}
