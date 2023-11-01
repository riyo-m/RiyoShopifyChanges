package com.vertex.quality.common.utils.selenium.base;

import com.vertex.quality.common.pages.VertexAutomationObject;
import org.openqa.selenium.WebDriver;

/**
 * For all classes that hold utility methods which in turn may be used by all descendants of
 * VertexAutomationObject, this describes the resources of the VertexAutomationObject that the utility methods
 * need access to.
 * Any subclass of this should be instantiated as an instance variable in VertexAutomationObject
 *
 * The subclasses of this class are basically just a way to organize all the different
 * utility functions that VertexAutomationObject instances can use.
 * Meanwhile, the utility functions in those subclasses often need to call utility functions in
 * other subclasses (waiting, scrolling, etc.), and they do that by referencing the instance of
 * that other utility class that their common field contains.
 * Therefore, the utility class fields of VertexAutomationObject must be public.
 *
 * @author {ssalisbury}
 */
public abstract class VertexSeleniumUtilities
{
	//this is the VertexAutomationObject instance that instantiated this this utility class
	protected final VertexAutomationObject base;

	//this is the driver of the VertexAutomationObject instance that instantiated this this utility class
	protected final WebDriver driver;

	public VertexSeleniumUtilities( final VertexAutomationObject base, final WebDriver driver )
	{
		this.base = base;
		this.driver = driver;
	}

	/**
	 * Adding this constructor for convenience in situations where the utility does not
	 * actually need any reference to a page
	 *
	 * @param driver
	 *
	 * @author dgorecki
	 */
	public VertexSeleniumUtilities( final WebDriver driver )
	{
		this.base = null;
		this.driver = driver;
	}
}
