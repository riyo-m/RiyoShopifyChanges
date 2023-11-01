package com.vertex.quality.connectors.netsuite.common.enums;

import lombok.Getter;

/**
 * Item names in Netsuite
 *
 * @author hho
 */
@Getter
public enum NetsuiteItemName
{
	CHLOROFORM_ITEM("Chloroform"),
	STANDARD_TEST_ITEM_1("Standard Test Item 1"),
	STANDARD_TEST_ITEM_2("Standard Test Item 2"),
	STANDARD_TEST_ITEM_3("Standard Test Item 3"),
	ACC00002_ITEM("ACC00002"),
	ACC00003_ITEM("ACC00003"),
	BIC00001_ITEM("BIC00001"),
	OTCDRUG_ITEM("OTCDRUG"),
	SERVICE_FINSVCS("FINSVCS"),
	SERVICE_REPAIRS("REPAIRS"),
	SERVICE_ITEM("Service_item"),
	SERVICE_PROSERV("Pro Serv"),
	SERVICE_NIGHTCLUB_ITEM("NIGHTCLUB"),
	SERVICE_MEDSRCV_ITEM("MEDSRVC"),
	DESIGNER_CHAIR_LEGS("Designer Chair Legs"),
	ERPPRD786NUMBER("ERPPRD786NUMBER"),
	OWPRD786NUMBER("OWPRD786NUMBER"),
	STANDARD_10_FLAT_OFF("Standard $10 discount"),
	STANDARD_25_FLAT_OFF("Standard $25 discount"),
	STANDARD_10_RATE_OFF("Standard 10% discount"),
	STANDARD_25_RATE_OFF("Standard 25% discount"),
	TEST_PRODUCT_CLASS("Test Product Class"),
	//Has 40 characters with a special character towards the end
	ITEM_WITH_LONG_NAME("1234567890123456789012345678901234567&90"),
	BLACKBERRY_PLAYBOOK("BBP00001"),
	SALES_TAX_ONLY("Sales tax only adjustment"),
	TAX_ONLY_ADJUSTMENT_ITEM("Tax Only Adjustment"),
	//This item is covered by a Tax assist rule that charges tax on price BEFORE discounts
	GROSS_TAX_ITEM("Gross Taxed Item"),
	//This item is taxed normaly
	DISCOUNT_TAX_ITEM("Discount Taxed Item"),
	//Item of type Description
	DESCRIPTION_ITEM("Description Item"),
	TESTITEM25OFF("TestItem25Off");

	private String itemName;

	NetsuiteItemName( String itemName )
	{
		this.itemName = itemName;
	}
}
