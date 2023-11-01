package com.vertex.quality.connectors.netsuite.suiteTax.tests.base;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.utils.selenium.VertexClickUtilities;
import com.vertex.quality.common.utils.selenium.VertexTextUtilities;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteHomepage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.tests.base.NetsuiteBaseTest;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.NetsuiteAPIGeneralPreferencesPage;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPISalesOrderPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import com.vertex.quality.connectors.netsuite.common.enums.netsuiteCountry;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class NetsuiteBaseVatTest extends NetsuiteBaseTest
{

    protected String defaultTaxCode = "Vertex";
    protected NetsuiteSetupManagerPage setupManagerPage;
    protected NetsuiteItem itemOne;
    protected NetsuiteNavigationMenus salesOrderMenu;
    protected NetsuiteCustomer customer;
	protected String exportType = "";
	protected String transDate = "";
    protected NetsuiteAPISalesOrderPage savedSalesOrderPage;
    protected String itemLocation;
    protected double price = 100; //
    protected String quantity = "1";
    protected String expectedTaxResult = "Success";
    protected HashMap<String, String> xmlTokens = new HashMap<String, String>();
    protected List<String> tokenList = new ArrayList<String>();
    protected WebElement xmlElement;
    protected double taxRate = 0.2; //**Default to 20% //This cant be null
    protected String country = "USA";
    protected String coupon = "";
    protected String currency = "";
    protected NetsuiteAddress shipToAddress;
	protected String itemClass;
	protected String subsidiary;
	public VertexTextUtilities text;
	public VertexClickUtilities click;
	public VertexTextUtilities setDropdownToValue;


    @Override
    protected DBConnectorNames getConnectorName( )
    {
        return DBConnectorNames.NETSUITE_SUITE_TAX;
    }

    /**
     * Configures the settings for Netsuite API
     */
    protected NetsuiteSetupManagerPage configureSettings( )
    {
        NetsuiteHomepage homepage = signintoHomepageAsNetsuiteAPI();

		NetsuiteAPIGeneralPreferencesPage generalPreferencesPage = homepage.navigationPane.navigateTo("PREFERENCES");
		generalPreferencesPage.openCustomPreferencesTab();
        generalPreferencesPage.selectInstallFlag();
        generalPreferencesPage.selectOneWorldFlag();

        return generalPreferencesPage.savePreferences();
    }

	/**
	 * Configures the settings to change CUT THRESHOLD AMOUNT
	 */
	protected NetsuiteSetupManagerPage changeCutThresholdAmount( )
	{
		NetsuiteHomepage homepage = signintoHomepageAsNetsuiteAPI();

		NetsuiteAPIGeneralPreferencesPage generalPreferencesPage = homepage.navigationPane.navigateTo("PREFERENCES");
		generalPreferencesPage.openCustomPreferencesTab();
		generalPreferencesPage.selectInstallFlag();
		generalPreferencesPage.selectOneWorldFlag();
		generalPreferencesPage.enterCutThresholdAmount("3");

		return generalPreferencesPage.savePreferences();
	}

	/**
	 * Configures the settings to change CUT THRESHOLD AMOUNT
	 */
	protected NetsuiteSetupManagerPage resetCutThresholdAmount( )
	{
		NetsuiteHomepage homepage = new NetsuiteHomepage(driver);
		NetsuiteAPIGeneralPreferencesPage generalPrefPage = homepage.navigationPane.navigateTo("PREFERENCES");
		generalPrefPage.openCustomPreferencesTab();
		generalPrefPage.enterCutThresholdAmount("1");

		return generalPrefPage.savePreferences();
	}

	/**
	 * Activate subsidiary using checkbox if its Inactive
	 */
	protected NetsuiteHomepage activateSubsidiary( )
	{
		NetsuiteHomepage subsidiaryPage = new NetsuiteHomepage(driver);
		List<WebElement> columnValues;

		//navigate to sub page
		subsidiaryPage.navigationPane.navigateTo("SUBSIDIARY_LIST");

		//Select Subsidiary row [BY COUNTRY] and get other columns
		columnValues = getColumns();
		subsidiary = columnValues.get(2).getText();

		if(isInactive() && isNotDefault()){
			subsidiaryPage.navigationPane.navigateTo("SUBSIDIARY",columnValues.get(2).getText()+"&e=T");
			subsidiaryPage.toggleInactiveFlag();
		}

		return subsidiaryPage.navigationPane.navigateTo("HOMEPAGE");
	}

	protected boolean isInactive(){
		List<WebElement> columnValues;
		columnValues = getColumns();

		if(columnValues.get(1).getText().toLowerCase(Locale.ROOT).equals("yes")){
			return true;
		}
			return false;
	}

	protected boolean isNotDefault(){
		List<WebElement> columnValues;
		columnValues = getColumns();
		String internalId = columnValues.get(2).getText().toLowerCase(Locale.ROOT);

		if(internalId.equals("1") || internalId.equals("3")){
			return false;
		}

		return true;
	}

	/**
	 * Deactivate current subsidiary
	 */
	protected void deactivateSubsidiary(){
		if(subsidiary == null || subsidiary.equals("1") || subsidiary.equals("3") ){
			return;
		}
		NetsuiteHomepage subsidiaryPage = new NetsuiteHomepage(driver);
		subsidiaryPage.navigationPane.navigateTo("SUBSIDIARY",subsidiary +"&e=T");
		subsidiaryPage.toggleInactiveFlag();
		subsidiary = null;
	}

	/**
	 * Get the Country, Internal ID, and Inactive status from Subsidiary Table
	 * @return set of column values
	 */
	protected List<WebElement> getColumns(){
		String targetCountry = getCountry();
		By countryColumnLocator = By.xpath("//table[@id='div__bodytab']/tbody/tr/td[3]");
		By siblingLocator = By.xpath("./preceding-sibling::td");
		List<WebElement> columnValues = new ArrayList<>();

		//Select Subsidiary from list [BY COUNTRY]
		List<WebElement> Subsidiaries = driver.findElements(countryColumnLocator);
		for ( WebElement row: Subsidiaries )
		{
			if(row.getText().equals(targetCountry)){
				columnValues.add(row);
				break;
			}
		}
		//Get other columns
		columnValues.addAll(columnValues.get(0).findElements(siblingLocator) );
		return columnValues;
	}

	/**
	 * Use the 2 digit country abbreviation to get the countries full name
	 * @return country's full name
	 */
	protected String getCountry(){
		return netsuiteCountry.getLongName(country);
	}

    /**
     * Base Sales Order Test
     */
    protected void baseTest(){
		try {
			setupManagerPage = configureSettings();
			activateSubsidiary();

			// If any variants are pre-set use that value or use some default value...
			customer = customer == null
					? NetsuiteCustomer.CUSTOMER_3M
					: customer; //Already Set in Subclass

			//Item Config
			String amount = String.valueOf(price); //Already Set in Subclass

			itemLocation = itemLocation == null
					? ""
					: itemLocation; //Already Set in Subclass

			// Build default Item Object. This is customized by changing variant values???
			// Or an entire Item Object can be passed???
			itemOne = itemOne == null
					? NetsuiteItem
						.builder(NetsuiteItemName.ACC00002_ITEM).quantity(quantity).amount(amount).location(itemLocation).itemClass(itemClass).build()
					: itemOne; //Already Set in Subclass

			//***********************************************************************
			//Create SALES ORDER and Validate XML Response

			//Navigate to Enter Sales Order Page
			NetsuiteAPISalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateTo("SALES");

			/**
			 * START of sales order pipeline
			 */
			setupBasicOrder(salesOrderPage, customer, itemOne);

			/**
			 * NEXT you can start calling methods that set individual fields
			 * Ex. enter a coupon, change shipping address, set location, etc...
			 * Methods should return if input value isn't set
			 */
			salesOrderPage.enterExportType(exportType);
			salesOrderPage.enterTransDate(transDate);
			salesOrderPage.enterCouponCode(coupon);
			salesOrderPage.selectCurrencyByDropdown(currency);

			// Set shipping address or use the default
			if(shipToAddress != null) {
				salesOrderPage.selectExistingShipToAddress(shipToAddress);
				// clear value
				shipToAddress = null;
			}

			/**
			 * SAVE the Sales Order and send a request to Vertex THEN
			 * VALIDATE the values in the XML response below
			 */
				savedSalesOrderPage = salesOrderPage.saveOrder();

			//Verify the “Vertex Tax Error code” field is showing 'Success' status.
			//String actualMessage = savedSalesOrderPage.getVertexTaxErrorCode();
			//assertEquals(actualMessage, expectedTaxResult);

			//****This section handles all our test "variants".
			setCountry();
			calculateTaxAndTotal();

			//Each test can pass a <K,V> list and we'll look for each pair in the XML
			String vertexCallDetails = getXmlDoc();
			validateXmlTags(vertexCallDetails);
			validateIrregularTags(vertexCallDetails);

		}finally{
			clearValues();
		}
    }

	/**
	 * RESET any shared values
	 * Data will persist between tests when you run an entire test suite
	 */
	protected void clearValues(){
		recordRecyclingBin.empty();
		tokenList.clear();
		itemOne = null;
		itemLocation = null;
		customer = null;
		country = null;
		exportType = "";
		transDate = "";
		price = 100; //
		xmlTokens.clear();
		xmlElement = null;
		taxRate = 0.2; //**Default to 20% **This can't be null
		deactivateSubsidiary();
		return;
	}

    /**
     * Navigate to the Vertex Response Page and get the XML response
     * @return xmlText
     */
    protected String getXmlDoc() {
        String vertexCallDetails;

        // Get XML transaction Id (docId)
        String docId = savedSalesOrderPage.element.getWebElement(By.xpath("//tbody/tr/td[2]/a[1]")).getText();

        String environmentId = savedSalesOrderPage.getCurrentUrl();
        environmentId = environmentId.replaceFirst("https://","");
        environmentId = environmentId.split("/")[0];
        driver.navigate().to("https://"+environmentId+"/app/common/custom/custrecordentry.nl?rectype=490&id=" + docId + "&whence=");

        // Get XML text
        By parentXMLContainerLocator = By.className("uir-long-text");
        xmlElement = driver.findElements(parentXMLContainerLocator).get(1);
        String xmlText = xmlElement.getText();
        xmlText = xmlText.replaceFirst(".*\n","");

        return xmlText;
    }

    /**
     * Validate values with standard XML tags
     */
    protected void validateXmlTags(String vertexCallDetails ) {
        xmlTokens.forEach( (tagName,value) -> {
            System.out.println("Checking for value: "+ tagName);
            String xmlToken;
            if(value.equals("") || value.equals(null) ){
                xmlToken = tagName;
            }else{
                xmlToken = creatXmlTag(value,tagName);
            }
            assertTrue(vertexCallDetails.contains(xmlToken),"Response does not contain [ "+ tagName +" ] or it's value is incorrect." );
        });
    }

    /**
     * Check the Xml response for irregular tags
     * You need to pass the entire token, tag and value in the correct format
     * ex. <tagName>value</>
     */
    protected void validateIrregularTags(String vertexCallDetails ) {
        tokenList.forEach( (value) -> {
            System.out.println("Checking for value: "+ value);
            assertTrue(vertexCallDetails.contains(value),"Response does not contain [ "+ value +" ] or it's value is incorrect." );
        });
    }

    /**
     * Delete Sales Order record before exiting
     */
    protected void postTest(){
        //Need to make sure we are going back to the Saved Sales Order Page
        driver.navigate().back();
        //deleteDocument(savedSalesOrderPage);
    };

    /**
     * Use known Price and Tax Rate to calculate Totals
     * Each input parameter should have a default value
     */
    protected void calculateTaxAndTotal(){
        //Calculate Tax amount and Total
        double taxTotal = price * taxRate;
        taxTotal = Math.round(taxTotal * 100.0) / 100.0;
            xmlTokens.put(String.valueOf(taxTotal), "TotalTax" );
        double totalCost = taxTotal + price;
            xmlTokens.put(String.valueOf(totalCost), "Total" );
        //xmlTokens.put(String.valueOf(taxRate),  "EffectiveRate");
    }

    /**
     * Add the 2 character country abbreviation to the token list
     */
    protected void setCountry(){
        //Add Country code to the search list
        xmlTokens.put(country ,"Country");
    }

    /**
     * Add default VAT Xml tags
     */
    protected void setTaxTypeCodeAndResult() {
        tokenList.add("taxType=\"VAT\"");
        tokenList.add("taxCode=\"OGSTC\"");
        tokenList.add("taxResult=\"TAXABLE\"");
    }

    /**
     * Format the input into a valid XML tag
     */
    protected String creatXmlTag(String tagName, String value) {
        return "<"+tagName+">"+value+"</"+tagName+">";
    }



}
