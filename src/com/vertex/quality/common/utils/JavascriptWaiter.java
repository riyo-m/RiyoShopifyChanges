package com.vertex.quality.common.utils;

import com.vertex.quality.common.enums.VertexLogLevel;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Utility class for handling specialized wait functionality. Currently supports
 * waiting for JQuery and Angular 1.
 *
 * @author dgorecki
 */
public class JavascriptWaiter
{
	protected WebDriver jsWaitDriver;
	protected WebDriverWait jsWait;
	protected JavascriptExecutor jsExec;

	public JavascriptWaiter( final WebDriver driver, final int timeout )
	{
		this.jsWaitDriver = driver;
		jsWait = new WebDriverWait(jsWaitDriver, timeout);
		jsExec = (JavascriptExecutor) jsWaitDriver;
	}

	/**
	 * Wait for JQuery to finish loading on a page
	 */
	public void waitForJQueryLoad( )
	{
		// Wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) jsExec.executeScript("return jQuery.active") == 0);

		// Get JQuery is Ready
		boolean jqueryReady = jQueryLoad.apply(jsWaitDriver);

		// Wait JQuery until it is Ready!
		if ( !jqueryReady )
		{
			// Wait for jQuery to load
			jsWait.until(jQueryLoad);
		}
	}

	/**
	 * Wait for Angular 1 to finish loading on a page
	 */
	public void waitForAngularLoad( )
	{
		String angularReadyScript
			= "return angular.element(document).injector().get('$http').pendingRequests.length === 0";

		// Wait for ANGULAR to load
		ExpectedCondition<Boolean> angularLoad = driver -> Boolean.valueOf(jsExec
			.executeScript(angularReadyScript)
			.toString());

		// Get Angular is Ready
		boolean angularReady = angularLoad.apply(jsWaitDriver);

		// Wait ANGULAR until it is Ready!
		if ( !angularReady )
		{
			// Wait for Angular to load
			jsWait.until(angularLoad);
		}
	}

	/**
	 * Waits for basic javascript to be ready/complete
	 */
	public void waitUntilJSReady( )
	{
		// Pre Wait for stability (Optional)
		sleep(20);

		// Wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = driver -> jsExec
			.executeScript("return document.readyState")
			.toString()
			.equals("complete");

		// Get JS is Ready
		boolean jsReady = jsLoad.apply(jsWaitDriver);

		// Wait Javascript until it is Ready!
		if ( !jsReady )
		{
			// Wait for Javascript to load
			jsWait.until(jsLoad);
		}

		// Post Wait for stability (Optional)
		sleep(20);
	}

	/**
	 * Waits for JQuery to be ready, if present
	 */
	public void waitUntilJQueryReady( )
	{
		// First check that JQuery is defined on the page. If it is, then wait AJAX
		Boolean jQueryDefined = (Boolean) jsExec.executeScript("return typeof jQuery != 'undefined'");
		if ( jQueryDefined == true )
		{
			// Pre Wait for stability (Optional)
			sleep(20);

			// Wait JQuery Load
			waitForJQueryLoad();

			// Post Wait for stability (Optional)
			sleep(20);
		}
		else
		{
			VertexLogger.log("jQuery is not defined on this site!", VertexLogLevel.DEBUG);
		}
	}

	/**
	 * Waits for Angular 1.0 to be ready, if present
	 */
	public void waitUntilAngularReady( )
	{
		// First check that ANGULAR is defined on the page. If it is, then wait ANGULAR
		Boolean angularUnDefined = (Boolean) jsExec.executeScript("return window.angular === undefined");
		if ( !angularUnDefined )
		{
			Boolean angularInjectorUnDefined = (Boolean) jsExec.executeScript(
				"return angular.element(document).injector() === undefined");
			if ( !angularInjectorUnDefined )
			{
				// Pre Wait for stability (Optional)
				sleep(20);

				// Wait Angular Load
				waitForAngularLoad();

				// Post Wait for stability (Optional)
				sleep(20);
			}
			else
			{
				VertexLogger.log("Angular injector is not defined on this site!", VertexLogLevel.DEBUG);
			}
		}
		else
		{
			VertexLogger.log("Angular is not defined on this site!", VertexLogLevel.DEBUG);
		}
	}

	/**
	 * Wait until all potential frameworks have finished loading
	 */
	public void waitForLoadAll( )
	{
		waitUntilJSReady();
		waitUntilJQueryReady();
		waitUntilAngularReady();
		waitUntilJSReady();
	}

	/**
	 * Wrapper around thread.sleep
	 *
	 * @param milliseconds the number of milliseconds to sleep
	 */
	public void sleep( long milliseconds )
	{
		try
		{
			Thread.sleep(milliseconds);
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}
	}
}
