package com.vertex.quality.connectors.salesforce;

import com.vertex.quality.common.utils.VertexLogger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Class to rerun failed test cases
 * @author Erik Roeckel
 */
public class TestRerun implements IRetryAnalyzer{

    private int counter = 0;
    // Upon failure test case will be rerun a max of 3 times, totaling 4 runs
    private static final int rerunLimit = 3;

    /**
     * Automatically reruns a test case if it fails
     * @param result
     * @return Rerun test case if true, do not rerun if false
     */
    @Override
    public boolean retry(final ITestResult result) {
        if (!result.isSuccess()){
            if(counter < rerunLimit) {
                VertexLogger.log("Rerun #" + (this.counter + 1) + " for test case: " + result.getName() + " with status " + getResultStatusName(result.getStatus()));
                this.counter++;
                return true;
            }
            else {
                VertexLogger.log("Test Case: " + result.getName() + "was rerun "+ rerunLimit + " times which is the max amount of reruns");
                result.setStatus(ITestResult.FAILURE);
            }
        }
        else{
            result.setStatus(ITestResult.SUCCESS);
        }
        return false;
    }

    /**
     * Set the status name for the test run based on the status code provided
     * @param status status code of test run
     * @return Status name matching status code
     */
    private String getResultStatusName (final int status) {
        String resultName = null;
        if (status == 1) {
            resultName = "SUCCESS";
        }
        if (status == 2) {
            resultName = "FAILURE";
        }
        if (status == 3) {
            resultName = "SKIP";
        }
        return resultName;
    }
}
