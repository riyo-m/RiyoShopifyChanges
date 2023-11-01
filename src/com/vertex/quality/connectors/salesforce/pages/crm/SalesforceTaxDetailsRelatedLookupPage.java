package com.vertex.quality.connectors.salesforce.pages.crm;

import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Common functions for anything related to Salesforce Basic Field Mapping Page.
 *
 * @author
 */
public class SalesforceTaxDetailsRelatedLookupPage extends SalesForceBasePage {

    public SalesforceTaxDetailsRelatedLookupPage( WebDriver driver )
    {
        super(driver);
    }
    protected By PRODUCT_TAX_DETAIL_TABLE = By.cssSelector("div[id*='_body'] table");
    protected By PRODUCT_TAX_DETAIL_TABLE_HEADER_ROW = By.className("headerRow");
    protected By PRODUCT_TAX_DETAIL_TABLE_ROW = By.cssSelector("tbody>tr[class*='dataRow']");
    protected By ITEM_TAX_DETAILS_COUNT = By.xpath(".//span[@class='listTitle' and text()= 'Vertex Item Tax Details']/span[@class='count']");

    protected By LIGHTING_TAX_DETAILS_LINK = By.xpath(".//span[starts-with(@title,'Vertex Item Tax Details')]");
    protected By LIGHTING_PRODUCT_TAX_DETAIL_TABLE = By.xpath(".//h1[text()= 'Vertex Item Tax Details']/../../../../../../../following-sibling::div//table[@aria-label='Vertex Item Tax Details']");
    protected By LIGHTING_PRODUCT_TAX_DETAIL_TABLE_HEADER_ROW = By.xpath("./thead/tr");
    protected By LIGHTING_PRODUCT_TAX_DETAIL_TABLE_ROW = By.xpath("./tbody/tr");
    protected By LIGHTING_ITEM_TAX_DETAILS_COUNT = By.xpath(".//h1[text()= 'Vertex Item Tax Details']/../../../../..//span[contains(@class,'count')]");

    /**
     * Method to get product details by passing column name and Row index
     */
    public String getProductTaxDetails( int rowNumber, String ColumnName )
    {
        int columnIndex = getTaxDetailsColumnIndex(ColumnName);
        String cellValue = getProductTaxDetailsTableCellValue(rowNumber, columnIndex);
        return cellValue;
    }

    /**
     * Get Tax Details Column Index
     * @param ColumnName
     * @return
     */
    private int getTaxDetailsColumnIndex( String ColumnName )
    {
        waitForPageLoad();
        int columnIndex = -1;
        wait.waitForElementDisplayed(getProductTaxDetailsTable());
        WebElement tableContainer = getProductTaxDetailsTable();
        WebElement headerRow = tableContainer.findElement(PRODUCT_TAX_DETAIL_TABLE_HEADER_ROW);
        wait.waitForElementDisplayed(headerRow);
        List<WebElement> headerCells = headerRow.findElements(By.tagName("th"));
        for ( int x = 0 ; x < headerCells.size() ; x++ )
        {
            WebElement column = headerCells.get(x);
            String current_column_label = column.getAttribute("innerText");
            if ( current_column_label.equals(ColumnName) )
            {
                columnIndex = x;
                break;
            }
        }
        return columnIndex;
    }

    /**
     * Get Product Tax Details Table
     * @return
     */
    private WebElement getProductTaxDetailsTable( )
    {
        WebElement tableContainer = element.getWebElement(PRODUCT_TAX_DETAIL_TABLE);
        return tableContainer;
    }

    /**
     * Get Product Tax Details Table Cell Value
     * @param rowNumber
     * @param ColumnIndex
     * @return
     */
    private String getProductTaxDetailsTableCellValue( int rowNumber, int ColumnIndex )
    {
        String cellValue = null;
        WebElement row = getProductTaxDetailsTableRowByIndex(rowNumber);
        List<WebElement> cells = row.findElements(By.tagName("td"));
        if ( ColumnIndex == 1 )
        {
            ColumnIndex = ColumnIndex;
        }
        else
        {
            ColumnIndex = ColumnIndex - 1;
        }
        WebElement cell = cells.get(ColumnIndex);
        cellValue = cell.getText();
        return cellValue;
    }

    /**
     * Lookup By Row Index
     * @param rowIndex
     * @return
     */
    private WebElement getProductTaxDetailsTableRowByIndex( int rowIndex )
    {
        WebElement tableContainer = getProductTaxDetailsTable();
        List<WebElement> rows = tableContainer.findElements(PRODUCT_TAX_DETAIL_TABLE_ROW);
        return rows.get(rowIndex);
    }

    /**
     * Get number of tax details for opportunity product
     * @return tax detail record count
     */
    public String getItemTaxDetailCount()
    {
        String taxDetailCount;
        wait.waitForElementDisplayed(ITEM_TAX_DETAILS_COUNT);
        taxDetailCount = text.getElementText(ITEM_TAX_DETAILS_COUNT);
        taxDetailCount = taxDetailCount.replaceAll("[\\[\\]]", "");
        return taxDetailCount;
    }

    /**
     * Method to Validate Opportunity Product Detail Page
     * 	 * Brenda Johnson
     *
     * @param rowNumber
     * @param JurisdictionLevel
     * @param Jurisdiction
     * @param Taxable
     * @param EffectiveRate
     * @param CalculatedTax
     * @param TaxType
     * @param TaxResult
     */
    public void getTaxDetailsRelatedList( int rowNumber, String JurisdictionLevel, String Jurisdiction,  String Taxable,
                                          String EffectiveRate,String CalculatedTax,String TaxType,String TaxResult )
    {
        int taxDetailCount = Integer.parseInt(getItemTaxDetailCount());
        int i = 0;
        while(taxDetailCount == 0 && i < 5) {
            waitForSalesForceLoaded();
            refreshPage();
            taxDetailCount = Integer.parseInt(getItemTaxDetailCount());
            i++;
        }

        String actualJurisdictionLevel = getProductTaxDetails(rowNumber, "JurisdictionLevel");
        assertEquals(actualJurisdictionLevel, JurisdictionLevel, "Fail. Actual Value: " + actualJurisdictionLevel);

        String actualJurisdiction = getProductTaxDetails(rowNumber, "Jurisdiction");
        assertEquals(actualJurisdiction, Jurisdiction, "Fail. Actual Value: " + actualJurisdiction);

        String actualTaxable = trimCurrencySubstring(getProductTaxDetails(rowNumber, "Taxable"));
        assertEquals(actualTaxable, Taxable, "Fail. Actual Value: " + actualTaxable);

        String actualEffectiveRate = getProductTaxDetails(rowNumber, "EffectiveRate");
        assertEquals(actualEffectiveRate, EffectiveRate, "Fail. Actual Value: " + actualEffectiveRate);

        String actualCalculatedTax = trimCurrencySubstring(getProductTaxDetails(rowNumber, "CalculatedTax"));
        assertEquals(actualCalculatedTax, CalculatedTax, "Fail. Actual Value: " + actualCalculatedTax);

        String actualTaxType = getProductTaxDetails(rowNumber, "TaxType");
        assertEquals(actualTaxType, TaxType, "Fail. Actual Value: " + actualTaxType);

        String actualTaxResult = getProductTaxDetails(rowNumber, "TaxResult");
        assertEquals(actualTaxResult, TaxResult, "Fail. Actual Value: " + actualTaxResult);
    }

    /**
     * Method to Validate Opportunity Product Detail Page
     * @Tirzah Prabhakaran
     *
     * @param rowNumber
     * @param Taxable
     * @param CalculatedTax
     */
    public void getTaxDetailsRelatedList( int rowNumber,  String Taxable, String CalculatedTax )
    {
        int taxDetailCount = Integer.parseInt(getItemTaxDetailCount());
        int i = 0;
        while(taxDetailCount == 0 && i < 5) {
            waitForSalesForceLoaded();
            refreshPage();
            taxDetailCount = Integer.parseInt(getItemTaxDetailCount());
            i++;
        }


        String actualTaxable = getProductTaxDetails(rowNumber, "Taxable");
        assertEquals(actualTaxable, Taxable, "Fail. Actual Value: " + actualTaxable);

        String actualCalculatedTax = getProductTaxDetails(rowNumber, "CalculatedTax");
        assertEquals(actualCalculatedTax, CalculatedTax, "Fail. Actual Value: " + actualCalculatedTax);

    }

    /**
     * Method to get product details by passing column name and Row index
     */
    public String getLightingProductTaxDetails( int rowNumber, String ColumnName )
    {
        int columnIndex = getLightingTaxDetailsColumnIndex(ColumnName);
        String cellValue = getLightingProductTaxDetailsTableCellValue(rowNumber, columnIndex);
        return cellValue;
    }

    /**
     * Get Tax Details Column Index
     * @param ColumnName
     * @return
     */
    private int getLightingTaxDetailsColumnIndex( String ColumnName )
    {
        waitForPageLoad();
        int columnIndex = -1;
        wait.waitForElementDisplayed(getLightingProductTaxDetailsTable());
        WebElement tableContainer = getLightingProductTaxDetailsTable();
        WebElement headerRow = tableContainer.findElement(LIGHTING_PRODUCT_TAX_DETAIL_TABLE_HEADER_ROW);
        List<WebElement> headerCells = headerRow.findElements(By.xpath("th[@aria-sort='none']"));
        for ( int x = 0 ; x < headerCells.size() ; x++ )
        {
            WebElement column = headerCells.get(x);
            String current_column_label = column.getAttribute("aria-label");
            if ( current_column_label.equals(ColumnName) )
            {
                columnIndex = x;
                break;
            }
        }
        return columnIndex;
    }

    /**
     * Get Product Tax Details Table
     * @return
     */
    private WebElement getLightingProductTaxDetailsTable( )
    {
        WebElement tableContainer = element.getWebElement(LIGHTING_PRODUCT_TAX_DETAIL_TABLE);
        return tableContainer;
    }

    /**
     * Get Product Tax Details Table Cell Value
     * @param rowNumber
     * @param ColumnIndex
     * @return
     */
    private String getLightingProductTaxDetailsTableCellValue( int rowNumber, int ColumnIndex )
    {
        String cellValue = null;
        WebElement row = getLightingProductTaxDetailsTableRowByIndex(rowNumber);
        List<WebElement> cells = row.findElements(By.tagName("td"));
        if ( ColumnIndex == 0 )
        {
            ColumnIndex = ColumnIndex;
        }
        else
        {
            ColumnIndex = ColumnIndex + 1;
        }

        WebElement cell = cells.get(ColumnIndex);
        cellValue = cell.getText();
        return cellValue;
    }

    /**
     * Lookup By Row Index
     * @param rowIndex
     * @return
     */
    private WebElement getLightingProductTaxDetailsTableRowByIndex( int rowIndex )
    {
        WebElement tableContainer = getLightingProductTaxDetailsTable();
        List<WebElement> rows = tableContainer.findElements(LIGHTING_PRODUCT_TAX_DETAIL_TABLE_ROW);
        int i =0;
        while (rows.isEmpty() && i < 4)
        {
            refreshPage();
            rows = tableContainer.findElements(LIGHTING_PRODUCT_TAX_DETAIL_TABLE_ROW);
            i++;
        }
        return rows.get(rowIndex);
    }

    /**
     * Get number of tax details for opportunity product
     * @return tax detail record count
     */
    public String getLightingItemTaxDetailCount()
    {
        String taxDetailCount;
        wait.waitForElementDisplayed(LIGHTING_ITEM_TAX_DETAILS_COUNT);
        taxDetailCount = text.getElementText(LIGHTING_ITEM_TAX_DETAILS_COUNT);
        return taxDetailCount;
    }

    /**
     * navigate To Lighting Vertex Item Details Related records
     */
    public void navigateToLightingVertexItemDetailsRelated()
    {
        waitForSalesForceLoaded();
        scroll.scrollElementIntoView(LIGHTING_TAX_DETAILS_LINK);
        waitForSalesForceLoaded();
        wait.waitForElementDisplayed(LIGHTING_TAX_DETAILS_LINK);
        waitForSalesForceLoaded();
        click.clickElement(LIGHTING_TAX_DETAILS_LINK);
        waitForSalesForceLoaded();
    }

    /**
     * Method to Validate Opportunity Product Detail Page
     * 	 * Brenda Johnson
     *
     * @param rowNumber
     * @param JurisdictionLevel
     * @param Jurisdiction
     * @param Taxable
     * @param EffectiveRate
     * @param CalculatedTax
     * @param TaxType
     * @param TaxResult
     */
    public void getLightingTaxDetailsRelatedList( int rowNumber, String JurisdictionLevel, String Jurisdiction,  String Taxable,
                                                  String EffectiveRate,String CalculatedTax,String TaxType,String TaxResult )
    {
        String actualJurisdictionLevel = getLightingProductTaxDetails(rowNumber, "JurisdictionLevel");
        assertEquals(actualJurisdictionLevel, JurisdictionLevel, "Fail. Actual Value: " + actualJurisdictionLevel);

        String actualJurisdiction = getLightingProductTaxDetails(rowNumber, "Jurisdiction");
        assertEquals(actualJurisdiction, Jurisdiction, "Fail. Actual Value: " + actualJurisdiction);

        String actualTaxable = getLightingProductTaxDetails(rowNumber, "Taxable");
        assertEquals(actualTaxable, Taxable, "Fail. Actual Value: " + actualTaxable);

        String actualEffectiveRate = getLightingProductTaxDetails(rowNumber, "EffectiveRate");
        assertEquals(actualEffectiveRate, EffectiveRate, "Fail. Actual Value: " + actualEffectiveRate);

        String actualCalculatedTax = getLightingProductTaxDetails(rowNumber, "CalculatedTax");
        assertEquals(actualCalculatedTax, CalculatedTax, "Fail. Actual Value: " + actualCalculatedTax);

        String actualTaxType = getLightingProductTaxDetails(rowNumber, "TaxType");
        assertEquals(actualTaxType, TaxType, "Fail. Actual Value: " + actualTaxType);

        String actualTaxResult = getLightingProductTaxDetails(rowNumber, "TaxResult");
        assertEquals(actualTaxResult, TaxResult, "Fail. Actual Value: " + actualTaxResult);
    }
}
