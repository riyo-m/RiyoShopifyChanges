package com.vertex.quality.connectors.salesforce;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.selenium.VertexWaitUtilities;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.time.Duration;
import java.util.Iterator;

/**
 * Class to remove any test reruns that result in SKIPPED status
 * @author Erik Roeckel
 */
public class TestRerunListener extends TestListenerAdapter {


    /**
     * @deprecated TODO: temporary solution, fix in some other way
     */
    public static void retryOnError(Runnable action) {
        retryOnError(action, 25);
    }

    /**
     * @deprecated TODO: temporary solution, fix in some other way
     */
    public static void retryOnError(Runnable action, int times) {
        int timesRetried = 0;
        boolean succeeded = false;

        while (!succeeded) {
            try {
                action.run();
                succeeded = true;
            } catch (Exception exception) {
                ++timesRetried;
                if (timesRetried == times) {
                    System.err.println(
                            "retried too many times, it's a failure, sorry!"
                    );
                    throw exception;
                } else {
                    System.err.println(
                            "retried " + timesRetried + " out of " + times + " and failed, retrying again"
                    );
                }
            }
        }
    }

    /**
     * Pairs with TestRerun class to remove any rerun test cases which are marked as Skipped
     * Makes it so that each test case is only reported one time to testng xml report
     * @param context
     */
    @Override
    public void onFinish(ITestContext context){
        Iterator<ITestResult> skippedTestCases = context.getSkippedTests().getAllResults().iterator();
        while (skippedTestCases.hasNext()) {
            ITestResult skippedTestCase = skippedTestCases.next();
            ITestNGMethod method = skippedTestCase.getMethod();
            if (context.getSkippedTests().getResults(method).size() > 0) {
                VertexLogger.log("Removing: " + skippedTestCase.getMethod());
                skippedTestCases.remove();
            }
        }
    }
}
