package com.vertex.quality.common.tests;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.reporters.EmailableReporter;

import java.lang.reflect.Method;
import java.time.ZoneId;

/**
 * Base test for all of our automated tests.
 * Stores information that's shared between selenium-based tests and API-based tests
 *
 * @author dgorecki ssalisbury
 */
@Listeners({ EmailableReporter.class })
public abstract class VertexBaseTest
{
	//Default timezone to use in all test cases
	protected final ZoneId defaultTimeZone = ZoneId.of("America/New_York");

	/**
	 * handles any setup that needs to happen before a test suite of any type (selenium-based, api-based, etc.)
	 *
	 * @author dgorecki ssalisbury
	 */
	@BeforeSuite(alwaysRun = true)
	public void initializeTestRun( )
	{
		try
		{
			VertexLogger.log("Starting Test Run...");
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		try
		{
			setupTestRun();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * handles any setup that needs to happen before each test case
	 *
	 * @param testMethod  a representation of the test case function
	 * @param testContext a representation of the test suite in which the test case is being run
	 *
	 * @author dgorecki ssalisbury
	 */
	@BeforeMethod(alwaysRun = true)
	public void initializeTest( final Method testMethod, final ITestContext testContext )
	{
		try
		{
			String testIdentification = String.format("In test run %s: Test %s -", testContext.getName(),
				testMethod.getName());
			VertexLogger.log(testIdentification, getClass());
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		try
		{
			setupTestCase();
		}
		catch ( Exception e )
		{
			VertexLogger.log("Failed to set up the test case:\n", VertexLogLevel.FATAL);
			e.printStackTrace();
		}
	}

	/**
	 * handles any cleanup that needs to happen after a test suite of any type (selenium-based, api-based, etc.)
	 *
	 * @author dgorecki ssalisbury
	 */
	@AfterSuite(alwaysRun = true)
	public void teardownTestRun( )
	{
		try
		{
			cleanupTestRun();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * handles any cleanup that needs to happen right after each test case
	 *
	 * @param testResult a representation of the result of the test case
	 *
	 * @author dgorecki ssalisbury
	 */
	@AfterMethod(alwaysRun = true)
	public void teardownTest( final ITestResult testResult )
	{
		try
		{
			String testname = testResult.getName();
			int testResultStatus = testResult.getStatus();

			if ( testResultStatus == ITestResult.FAILURE )
			{
				String failedTestMessage = String.format("Test failed - %s", testname);
				VertexLogger.log(failedTestMessage, getClass());
			}
			else if ( testResultStatus == ITestResult.SKIP )
			{
				String skippedTestMessage = String.format("Test was skipped - %s", testname);
				VertexLogger.log(skippedTestMessage, getClass());
			}
			else if ( testResultStatus != ITestResult.SUCCESS )
			{
				String weirdUnsuccessfulTestMessage = String.format(
					"Test terminated unsuccessfully in some unusual way- %s ; test result status code - %d", testname,
					testResultStatus);
				VertexLogger.log(weirdUnsuccessfulTestMessage, getClass());
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		try
		{
			cleanupTestCase(testResult);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * This is a stand-in for setup that needs to be done before any test suite of a certain type (selenium-based,
	 * api-based, etc.)
	 * Base classes for those categories of test classes can override this to do that setup for those tests
	 * Note- it's a good idea to call super.setupTestRun() at the beginning of an override of this function
	 * unless that override is simply implementing this abstract version in VertexBaseTest
	 *
	 * @author ssalisbury
	 */
	protected abstract void setupTestRun( );

	/**
	 * This is a stand-in for setup that needs to be done before all test cases of certain types (selenium-based,
	 * api-based, etc.)
	 * Base classes for those categories of test classes can override this to do that setup for those tests
	 * Note- it's a good idea to call super.setupTestCase() at the beginning of an override of this function
	 * unless that override is simply implementing this abstract version in VertexBaseTest
	 *
	 * @author ssalisbury
	 */
	protected abstract void setupTestCase( );

	/**
	 * this is the stand-in for cleanup that has to be done after any test suite of a certain type (selenium-based,
	 * api-based, etc.)
	 * Base classes for those categories of test classes can override this to do that cleanup for those tests
	 * Note- it's a good idea to call super.cleanupTestRun() at the end of an override of this function
	 * unless that override is simply implementing this abstract version in VertexBaseTest
	 *
	 * @author dgorecki ssalisbury
	 */
	protected abstract void cleanupTestRun( );

	/**
	 * This is a stand-in for cleanup that needs to be done after all test cases of a certain type (selenium-based,
	 * api-based, etc.)
	 * Base classes for those categories of test classes can override this to do that cleanup for those tests
	 * Note- it's a good idea to call super.cleanupTestCase() at the end of an override of this function
	 * unless that override is simply implementing this abstract version in VertexBaseTest
	 *
	 * @param testResult a representation of the result of the test case
	 *
	 * @author ssalisbury
	 */
	protected abstract void cleanupTestCase( final ITestResult testResult );
}
