package com.vertex.quality.common.components;

import com.vertex.quality.common.pages.VertexAutomationObject;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.WebDriver;

/**
 * A component in one of our supported applications
 *
 * @author dgorecki
 */
public abstract class VertexComponent extends VertexAutomationObject
{
	protected VertexPage parent;

	public VertexComponent( WebDriver driver, VertexPage parent )
	{
		super(driver);
		this.parent = parent;
		this.window.setPage(parent);
	}
}
