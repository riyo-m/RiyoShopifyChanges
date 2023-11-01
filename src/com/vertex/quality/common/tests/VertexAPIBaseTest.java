package com.vertex.quality.common.tests;

import org.testng.ITestResult;
import org.testng.annotations.Test;

/**
 * Generic API base test
 *
 * @author hho ssalisbury
 */
@Test(groups = { "api" })
public abstract class VertexAPIBaseTest extends VertexBaseTest
{
	/**
	 * handles any setup that's needed before running a suite of all api tests
	 */
	@Override
	protected void setupTestRun( ) { }

	/**
	 * handles any cleanup that's needed after running a suite of all api tests
	 */
	@Override
	protected void cleanupTestRun( ) {}

	/**
	 * handles any cleanup that's needed after running a suite of all api tests
	 */
	@Override
	protected void cleanupTestCase( ITestResult testResult ) {}
}
