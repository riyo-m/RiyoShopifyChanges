package com.vertex.quality.connectors.netsuite.suiteTax.tests.base;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteSalesOrderPage;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPISalesOrderPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import com.vertex.quality.connectors.netsuite.common.enums.netsuiteCountry;

import java.util.*;

public class NetsuiteTestConfig extends NetsuiteBaseVatTest
{
	@Test
	protected void createNexuses( ) {
		NetsuitePage page;
		WebElement textBox;
		ExpectedCondition<Boolean> condition;
		Actions actions = new Actions(driver);
		netsuiteCountry[] coun = netsuiteCountry.values();
		Set<String> countrySet = new HashSet<String>();
		WebDriverWait waiter = new WebDriverWait(driver, 8);

		for ( netsuiteCountry country: coun )
		{
			countrySet.add(country.getName());
		}

		page = signintoHomepageAsNetsuiteAPI();

		Set<String> existing = getExistingNexus(page);

		for (String country: countrySet)
		{
			if(existing.contains(country) )
			{
				System.out.println("A subsidiary for " + country + " already exist. Skipping it...");
				continue;
			}

			//goto Subsidiary list
			driver.navigate().to("https://tstdrv1847016.app.netsuite.com/app/setup/nexus.nl");

			System.out.println("setting up " + country);
			//name subsidiary [countryName]
			enterText(By.name("inpt_country"), country);
			WebElement Box = driver.findElement(By.id("description") );
			wait.waitForElementEnabled(Box);
			page.jsWaiter.sleep(200);
			if(Box.getAttribute("value").equals(""))
			{
				moveToAndClick(By.id("_cancel"));
				waiter.until(ExpectedConditions.urlContains("https://tstdrv1847016.app.netsuite.com/app/setup/nexuses"));
				continue;

			}
			//select Country using [countryName]
			enterText(By.name("inpt_taxagency"), "Vertex Tax Agency");

			//save new Subsidiary
			NetsuiteSalesOrderPage page2 = page.initializePageObject(NetsuiteAPISalesOrderPage.class);
			page2.saveOrder();
		}

	}

	/**
	 * Cycle through a list of all the countries and create a Subsidiary
	 *
	 */
	@Test
	protected void createSubsidiaries( ) {
		NetsuitePage page;
		WebElement textBox;
		ExpectedCondition<Boolean> condition;
		Actions actions = new Actions(driver);
		page = signintoHomepageAsNetsuiteAPI();
		netsuiteCountry[] coun = netsuiteCountry.values();
		Set<String> countrySet = new HashSet<String>();
		for ( netsuiteCountry country: coun )
		{
			countrySet.add(country.getName());
		}

		Set<String> existing = getExistingSubsidiaries(page);

		for (String country: countrySet)
		{
			//existing.add("US Minor Outlying Islands");
			if(existing.contains(country.trim()) )
			{
				System.out.println("A subsidiary for " + country + " already exist. Skipping it...");
				continue;
			}

			//goto Subsidiary list
			driver.navigate().to("https://tstdrv1847016.app.netsuite.com/app/common/otherlists/subsidiarytype.nl");

			System.out.println("setting up " + country);
			//name subsidiary [countryName]
			enterText(By.id("name"), country);

			driver.findElement(By.name("inpt_country") ).clear();
			//select Country using [countryName]
			enterText(By.name("inpt_country"), country);


			//fill in default endpoints, companyCode, and trustedId
			enterText(By.id("custrecord_companycode_vt"), "TSTtaxpayer");
			enterText(By.id("custrecord_taxserviceurl_vt"),
				"https://oseries9-final.vertexconnectors.com/vertex-ws/services/CalculateTax70");
			enterText(By.id("custrecord_addressserviceurl_vt"),
				"https://oseries9-final.vertexconnectors.com/vertex-ws/services/LookupTaxAreas70");
			enterText(By.id("custrecord_trustedid_vt"), "VTXTST123");
			//check 'SUBSIDIARY IS INACTIVE' checkbox
			actions
				.moveToElement(driver.findElement(By.id("isinactive_fs_inp")))
				.click()
				.build()
				.perform();
			//save new Subsidiary
			NetsuiteSalesOrderPage page2 = page.initializePageObject(NetsuiteAPISalesOrderPage.class);
			page2.saveOrder();

			//edit subsidiary
			page2.editOrder();
			//tax registration tab
			moveToAndClick(By.id("nexustabtxt"));
			//enter country
			enterText(By.name("inpt_nexuscountry"), country);
			//enter tax registration
			tabOver(2, page);
			enterText(By.id("taxregistrationnumber"), "123456789");
			//enter tax engine
			tabOver(1, page);
			enterText(By.name("inpt_taxengine"), "VertexTaxEngine");
			//enter effective date
			tabOver(1, page);
			enterText(By.id("effectivefrom"), "4/1/2022");

			page2.saveOrder();
		}

	}

	protected void moveToAndClick(By elementLocator){
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(elementLocator)).moveByOffset(0, 0).click().build().perform();
	}

	protected void enterText(By elementLocator, String textInput){
		WebDriverWait waiter = new WebDriverWait(driver, 8);
		WebElement textBox = driver.findElement(elementLocator);
		wait.waitForElementEnabled(textBox);
		textBox.clear();
		moveToAndClick(elementLocator);
		textBox.sendKeys(textInput);
	}

	protected void tabOver(int nTimes, NetsuitePage page){
		Actions actions = new Actions(driver);
		int tabCount = 0;
		while(tabCount < nTimes)
		{
			driver.switchTo().activeElement().sendKeys(Keys.TAB);
			tabCount++;
		}
		page.jsWaiter.sleep(1000);
	}

	protected Set<String> getExistingSubsidiaries(NetsuitePage page){
		driver.navigate().to("https://tstdrv1847016.app.netsuite.com/app/common/otherlists/subsidiarylist.nl?whence=T&scrollid=51&cmid=1650366960586_1107%2C1650366960664_1109");
		page.jsWaiter.sleep(500);
		List<WebElement> existingSubsidiaries = driver.findElements(By.xpath("//table[@id='div__bodytab']/tbody/tr/td[3]"));
		Set<String> existingSubsidiaryNames = new HashSet<String>();
		int count = Integer.parseInt(driver.findElement(By.id("uir_totalcount")).getText().replace("TOTAL:", "").trim() )-50;

		for ( WebElement element: existingSubsidiaries )
		{
			existingSubsidiaryNames.add(element.getText());
		}
		moveToAndClick(By.className("navig-next"));

		while(count > 0)
		{
			count -= 50;
			page.jsWaiter.sleep(500);
			existingSubsidiaries = driver.findElements(By.xpath("//table[@id='div__bodytab']/tbody/tr/td[3]"));

			for ( WebElement element : existingSubsidiaries )
			{
				existingSubsidiaryNames.add(element.getText());
			}
			moveToAndClick(By.className("navig-next"));
		}

		return existingSubsidiaryNames;
	}

	protected Set<String> getExistingNexus(NetsuitePage page){
		driver.navigate().to("https://tstdrv1847016.app.netsuite.com/app/setup/nexuses.nl?whence=");
		page.jsWaiter.sleep(500);
		List<WebElement> existingSubsidiaries = driver.findElements(By.xpath("//table[@id='div__bodytab']/tbody/tr/td[3]"));
		Set<String> existingSubsidiaryNames = new HashSet<String>();
		int count = Integer.parseInt(driver.findElement(By.id("uir_totalcount")).getText().replace("TOTAL:", "").trim() )-50;
		for ( WebElement element: existingSubsidiaries )
		{
			existingSubsidiaryNames.add(element.getText());
		}
		moveToAndClick(By.className("navig-next"));

		while(count > 0)
		{
			count -= 50;
			page.jsWaiter.sleep(500);
			existingSubsidiaries = driver.findElements(By.xpath("//table[@id='div__bodytab']/tbody/tr/td[3]"));

			for ( WebElement element : existingSubsidiaries )
			{
				existingSubsidiaryNames.add(element.getText());
			}
			moveToAndClick(By.className("navig-next"));
		}

		return existingSubsidiaryNames;
	}

}
