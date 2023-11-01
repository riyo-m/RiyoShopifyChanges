package com.vertex.quality.connectors.ariba.connector.tests.base;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.ariba.api.pojos.AribaAPIDateTimes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class AribaUtilities extends VertexPage {
    protected By tenantSelect = By.id("tenantSel");

    public AribaUtilities( WebDriver driver )
    {
        super(driver);
    }

    /**
     * Helper function to switch tenant on any page where it is possible
     * since not every page object has the ability
     *
     * @param tenantName
     * */
    public void switchTenant(String tenantName){
        wait.waitForElementEnabled(tenantSelect);
        click.clickElementCarefully(tenantSelect);
        String tenantValue = String.format("//*/option[@value='%s']",tenantName);
        click.clickElement(By.xpath(tenantValue));
    }

    /**
     * Generates an invoice number based on the date to be added to invoices on creation
     * since invoice numbers must be unique
     **/
    public String generateInvoiceId()
    {
        String invoiceId;
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        String currentDateTime = df.format(new Date());

        invoiceId = "AUTO_" + currentDateTime;
        invoiceId = invoiceId.replace(':', '_');
        invoiceId = invoiceId.replace('.', '_');
        invoiceId = invoiceId.replace('-', '_');
        return invoiceId;
    }
}
