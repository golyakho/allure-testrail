package testng;

import lombok.val;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Listener for additional Allure reporting and updating associated TestRail cases if needed.
 */
public class TestRailListener implements ITestListener {

    @Override
    public void onTestStart(final ITestResult result) {
    }

    @Override
    public void onTestSuccess(final ITestResult result) {
        val testMethod = result.getMethod().getConstructorOrMethod().getMethod();

        AllureTestRail.executeHandlers(testMethod);
    }

    @Override
    public void onTestFailure(final ITestResult result) {
        onTestSuccess(result);
    }

    @Override
    public void onTestSkipped(final ITestResult result) {
        onTestSuccess(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(final ITestResult result) {
        onTestSuccess(result);
    }

    @Override
    public void onStart(final ITestContext context) {
    }

    @Override
    public void onFinish(final ITestContext context) {
    }
}
