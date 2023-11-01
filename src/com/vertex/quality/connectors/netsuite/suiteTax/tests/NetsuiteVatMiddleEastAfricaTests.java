package com.vertex.quality.connectors.netsuite.suiteTax.tests;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.suiteTax.tests.base.NetsuiteBaseVatTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class NetsuiteVatMiddleEastAfricaTests extends NetsuiteBaseVatTest {

	/**
	 * Create Sales Order for Domestic: Yemen
	 * Both Taxpayer registered and Customer registered in Yemen. Goods, Tax: 5% VAT
	 * Validate Sales tax details in XML response for Intra-Yemen MiddleEast transaction
	 * @author ravunuri
	 * CNSAPI-1402
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void IntraYemenMiddleEastVatGoodsTest ( )
	{
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_YEMEN;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).location("Yemen_location").build();
		//List of XML response validations
		taxRate = 0.05; country = "YE";
		tokenList.add("<Total>105.0</Total>");
		tokenList.add("<CalculatedTax>5.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("rateClassification=\"Standard Rate\"");
		tokenList.add("YEMEN</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OGSTC\"");
		tokenList.add("taxResult=\"TAXABLE\"");
		tokenList.add("<Imposition impositionId=\"1\">General Sales Tax</Imposition>");
		super.baseTest();
		super.postTest();
	}
}