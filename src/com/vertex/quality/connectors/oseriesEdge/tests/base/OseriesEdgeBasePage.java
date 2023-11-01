package com.vertex.quality.connectors.oseriesEdge.tests.base;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.WebDriver;

/**
 * This page launches the browser
 *
 * @author- Brenda
 */
public class OseriesEdgeBasePage extends VertexPage
{
    public OseriesEdgeBasePage( WebDriver driver )
    {
        super(driver);

        waitForPageLoad();
    }
}
