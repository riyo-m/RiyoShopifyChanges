package com.vertex.quality.common.dialogs;

import com.vertex.quality.common.pages.VertexAutomationObject;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.WebDriver;

/**
 * A dialog/modal/lightbox in one of our supported applications
 *
 * @author dgorecki
 */
public abstract class VertexDialog extends VertexAutomationObject
{
	protected VertexPage parent;

	public VertexDialog( WebDriver driver, VertexPage parent )
	{
		super(driver);
		this.parent = parent;
		this.window.setPage(parent);
	}
}
