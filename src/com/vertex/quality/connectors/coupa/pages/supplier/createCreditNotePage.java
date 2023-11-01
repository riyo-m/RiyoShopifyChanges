package com.vertex.quality.connectors.coupa.pages.supplier;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Representation of the Create Credit Note Page on the supplier
 *
 * @author alewis
 */
public class createCreditNotePage extends VertexPage {

    protected By submitID = By.id("submit_form");
    protected By calculateID = By.id("calculate_button");
    protected By creditNoteID = By.id("invoice_header_invoice_number");
    protected By taxRateID = By.id("tax_rate_1");

    public createCreditNotePage(WebDriver driver) {
        super(driver);
    }
}
