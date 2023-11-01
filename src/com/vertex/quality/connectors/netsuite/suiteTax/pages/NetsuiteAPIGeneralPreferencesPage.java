package com.vertex.quality.connectors.netsuite.suiteTax.pages;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteNavigationPane;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteGeneralPreferencesPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * API version of the general preferences page
 *
 * @author mwilliams
 */
public class NetsuiteAPIGeneralPreferencesPage extends NetsuiteGeneralPreferencesPage
{
    public NetsuiteNavigationPane navigationPane;
    protected By defaultNonTaxableCodeTextField = By.id("inpt_custscript_defaultnontax_vt24");
    protected By customerExceptionsCheckbox = By.id("custscript_customer_exmpt_doc_level_vt_fs_inp");
    protected By defaultTaxCodeTextField = By.id("inpt_custscript_taxcode_vt23");

    public NetsuiteAPIGeneralPreferencesPage( WebDriver driver )
    {
        super(driver);
        navigationPane = new NetsuiteNavigationPane(driver, this);
    }

    /**
     * Sets the "VERTEX DEFAULT NONTAXABLE CODE" dropdown
     *
     * @param nontaxableTaxCode the value to set the dropdown to
     */
    public void setDefaultNontaxableTaxCode( String nontaxableTaxCode )
    {
        setDropdownToValue(defaultNonTaxableCodeTextField, nontaxableTaxCode);
    }
}
