package com.vertex.quality.common.pages;

import com.vertex.quality.common.enums.PageScrollDestination;
import com.vertex.quality.common.utils.JavascriptWaiter;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.selenium.*;
import org.openqa.selenium.*;

import java.util.ArrayList;

/**
 * Represents any "page object" in one of our supported web applications being
 * automated. All pages, components, and dialogs inherit from this object to
 * provide basic common methods for waiting, etc.
 *
 * @author dgorecki, Shiva Mothkula, Nagaraju Gampa
 */
public abstract class VertexAutomationObject
{
	public final PageScrollDestination defaultScrollDestination = PageScrollDestination.VERT_CENTER;

	/**
	 * The default number of seconds that the driver will wait for some condition to
	 * be true before timing out. IF you want a different timeout, specify one explicitly
	 */
	public static final int DEFAULT_TIMEOUT = 60;

	public static final int ONE_SECOND_TIMEOUT = 1;
	public static final int TWO_SECOND_TIMEOUT = 2;
	public static final int THREE_SECOND_TIMEOUT = 3;
	public static final int FOUR_SECOND_TIMEOUT = 4;
	public static final int FIVE_SECOND_TIMEOUT = 5;
	public static final int SIX_SECOND_TIMEOUT = 6;
	public static final int QUARTER_MINUTE_TIMEOUT = 15;
	public static final int HALF_MINUTE_TIMEOUT = 30;

	protected WebDriver driver;

	public JavascriptWaiter jsWaiter;

	public VertexAlertUtilities alert;
	public VertexAttributeUtilities attribute;
	public VertexCheckboxUtilities checkbox;
	public VertexClickUtilities click;
	public VertexDropdownUtilities dropdown;
	public VertexElementUtilities element;

	public VertexHoverUtilities hover;
	public VertexFocusUtilities focus;
	public VertexScrollUtilities scroll;
	public VertexTextUtilities text;
	public VertexWaitUtilities wait;
	public VertexWindowUtilities window;

	public VertexAutomationObject( final WebDriver driver )
	{
		this.driver = driver;

		this.jsWaiter = new JavascriptWaiter(driver, DEFAULT_TIMEOUT);

		this.alert = new VertexAlertUtilities(this, driver);
		this.attribute = new VertexAttributeUtilities(this, driver);
		this.checkbox = new VertexCheckboxUtilities(this, driver);
		this.click = new VertexClickUtilities(this, driver);
		this.dropdown = new VertexDropdownUtilities(this, driver);
		this.element = new VertexElementUtilities(this, driver);
		this.focus = new VertexFocusUtilities(this, driver);
		this.hover = new VertexHoverUtilities(this, driver);
		this.scroll = new VertexScrollUtilities(this, driver);
		this.text = new VertexTextUtilities(this, driver);
		this.wait = new VertexWaitUtilities(this, driver);
		this.window = new VertexWindowUtilities(this, driver);
	}

	/**
	 * attempts to instantiate any subclass of VertexAutomationObject
	 *
	 * Warning Note- this assumes that every subclass of VertexAutomationObject has a constructor
	 * which
	 * takes only one argument, a WebDriver object
	 *
	 * @param resultingPageObject which subclass of VertexAutomationObject should be instantiated
	 *
	 * @return an instance of some subclass of VertexAutomationObject
	 *
	 * @author dgorecki ssalisbury
	 */
	@SuppressWarnings("unchecked")
	public <T extends VertexAutomationObject> T initializePageObject( final Class<?> resultingPageObject,
		VertexPage parent )
	{
		T result = null;
		try
		{
			result = (T) resultingPageObject
				.getConstructor(WebDriver.class, VertexPage.class)
				.newInstance(driver, parent);
		}
		catch ( final Exception e )
		{
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * attempts to instantiate any subclass of VertexAutomationObject
	 *
	 * Warning Note- this assumes that every subclass of VertexAutomationObject has a constructor
	 * which
	 * takes only one argument, a WebDriver object
	 *
	 * @param resultingPageObject which subclass of VertexAutomationObject should be instantiated
	 *
	 * @return an instance of some subclass of VertexAutomationObject
	 *
	 * @author dgorecki ssalisbury
	 */
	@SuppressWarnings("unchecked")
	public <T extends VertexAutomationObject> T initializePageObject( final Class<?> resultingPageObject )
	{
		T result = null;
		try
		{
			result = (T) resultingPageObject
				.getConstructor(WebDriver.class)
				.newInstance(driver);
		}
		catch ( final Exception e )
		{
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * stores the type of any argument object along with the argument object itself
	 *
	 * @author ssalisbury
	 */
	protected class ConstructorParamArgPair
	{
		public Class<?> param;
		public Object arg;

		public ConstructorParamArgPair( final Class<?> parameter, final Object argument )
		{
			this.param = parameter;
			this.arg = argument;
		}
	}

	/**
	 * attempts to instantiate any subclass of VertexAutomationObject
	 *
	 * @param resultingPageObject    which subclass of VertexAutomationObject should be instantiated
	 * @param nonDriverParamArgPairs all of the arguments that the new PageObject's
	 *                               constructor will need beyond the Webdriver, wrapped in
	 *                               instances of a class that conveys
	 *                               both each argument's type (as an object of Class<?>) and the
	 *                               argument itself
	 *                               (cast to Object so that it's generic enough for this) to the
	 *                               weird functions that
	 *                               enable all of this esoteric reflection-based functionality
	 *
	 * @return an instance of some subclass of VertexAutomationObject
	 *
	 * @author dgorecki, ssalisbury
	 */
	@SuppressWarnings("unchecked")
	protected <T extends VertexAutomationObject> T initializePageObject( final Class<?> resultingPageObject,
		final ConstructorParamArgPair... nonDriverParamArgPairs )
	{
		T result = null;

		ArrayList<Class<?>> constructorParams = new ArrayList<Class<?>>();
		ArrayList<Object> constructorArgs = new ArrayList<Object>();

		for ( ConstructorParamArgPair pair : nonDriverParamArgPairs )
		{
			constructorParams.add(pair.param);
			constructorArgs.add(pair.arg);
		}

		constructorParams.add(0, WebDriver.class);
		constructorArgs.add(0, driver);

		Class<?>[] constructorParamsArray = constructorParams.toArray(new Class<?>[0]);
		Object[] constructorArgsArray = constructorArgs.toArray(new Object[0]);

		try
		{
			result = (T) resultingPageObject
				.getConstructor(constructorParamsArray)
				.newInstance(constructorArgsArray);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Utility method designed to improve test stability by looking for known JS frameworks and
	 * attempting to wait for any present to be in a "ready" state.
	 * Note - this method in general is not meant to 100% guarantee that the page has finished
	 * loading, additional waits may be necessary in certain circumstances/scenarios.
	 * This needs to be public so the utility objects can use it
	 */
	public void waitForPageLoad( )
	{
		jsWaiter.waitForLoadAll();
	}

	/**
	 * runs a piece of javascript using the webdriver
	 *
	 * @param script the javascript code which should be executed
	 * @param args   any arguments to the piece of javascript
	 *
	 * @return the result of the javascript code's execution
	 *
	 * @author ssalisbury
	 */
	public Object executeJs( final String script, final Object... args )
	{
		JavascriptExecutor jsExec = (JavascriptExecutor) driver;
		Object returnValue = jsExec.executeScript(script, args);

		return returnValue;
	}

	/**
	 * handles the pop up message by clicking the ok button.
	 *
	 * @param buttonLoc        the locator of the button to be clicked
	 * @param timeoutInSeconds time to wait for the element to be displayed
	 */
	public void handlePopUpMessage( final By buttonLoc, final int timeoutInSeconds )
	{
		WebElement button = null;

		try
		{
			button = wait.waitForElementDisplayed(buttonLoc, timeoutInSeconds);
		}
		catch ( TimeoutException e )
		{
		}

		if ( button != null )
		{
			click.clickElement(button);
		}
		else
		{
			VertexLogger.log("no pop up message found");
		}
	}
}
