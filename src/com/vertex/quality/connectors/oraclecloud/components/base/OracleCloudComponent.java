package com.vertex.quality.connectors.oraclecloud.components.base;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.WebDriver;

/**
 * Top-level class representing a component in the Oracle ERP Cloud
 *
 * @author cgajes
 */
public abstract class OracleCloudComponent extends VertexComponent
{
	public OracleCloudComponent( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

}
