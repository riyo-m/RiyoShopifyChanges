package com.vertex.quality.connectors.dummyconnector;

import com.vertex.quality.common.tests.VertexAPIBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class dummyApiTests extends VertexAPIBaseTest
{

	@Override
	protected void setupTestCase( )
	{
		//do nothing
	}
	
	// test with minimal dependencies
	@Test(groups = { "dummyApiTest" })
	public void dummyApiTestGitOne( )
	{		
		assertTrue(true);
	}
}