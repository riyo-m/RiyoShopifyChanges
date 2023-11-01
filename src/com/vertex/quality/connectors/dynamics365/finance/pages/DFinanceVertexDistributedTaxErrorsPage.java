package com.vertex.quality.connectors.dynamics365.finance.pages;

import org.openqa.selenium.WebDriver;
import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Vertex Distributed Error page methods
 *
 * @author Mario Saint-Fleur
 */
public class DFinanceVertexDistributedTaxErrorsPage extends DFinanceBasePage {
    public DFinanceVertexDistributedTaxErrorsPage( WebDriver driver )
    {
        super(driver);
    }

    protected By DISTRIBUTED_TAX_ERROR_HEADER = By.xpath("//span[text()='Vertex distributed tax errors']");

    /**
     * Gets the Vertex Distributed Tax Errors page header
     * @return header - the text value of the header
     */
    public String verifyVTXDistributedTaxErrorsHeader(){
        wait.waitForElementDisplayed(DISTRIBUTED_TAX_ERROR_HEADER);
        String header = text.getElementText(DISTRIBUTED_TAX_ERROR_HEADER);

        return header;

    }
}
