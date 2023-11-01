package com.vertex.quality.connectors.netsuite.scis.pages;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteGeneralPreferencesPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NetsuiteSCISGeneralPreferencesPage extends NetsuiteGeneralPreferencesPage
{

	protected By vertexForSCISCheckbox = By.id("custscript_trigger_vertex_for_scis_fs_inp");

	public NetsuiteSCISGeneralPreferencesPage( final WebDriver driver )
	{
		super(driver);
	}

	public void selectVertexForSCIS( )
	{
		WebElement installCheckbox = wait.waitForElementPresent(vertexForSCISCheckbox);
		if ( !checkbox.isCheckboxChecked(vertexForSCISCheckbox) )
		{
			click.javascriptClick(vertexForSCISCheckbox);
		}
	}

	public void unselectVertexForSCIS( )
	{
		WebElement installCheckbox = wait.waitForElementPresent(vertexForSCISCheckbox);
		if ( checkbox.isCheckboxChecked(vertexForSCISCheckbox) )
		{
			click.javascriptClick(vertexForSCISCheckbox);
		}
	}
}
