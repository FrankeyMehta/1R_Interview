package com.sample.test.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.MediaEntityBuilder;

public class ExtentTestManager {
	private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	private static ExtentReports extent = ExtentManager.getInstance();

	/**
	 * Retrieves the 'ExtentTest' instance associated with the current thread.
	 */

	public synchronized static ExtentTest getTest() {
		return extentTest.get();
	}

	/**
	 * Removes the 'ExtentTest' instance associated with the current thread,
	 * indicating the end of the test.
	 */
	public synchronized static void endTest() {
		extentTest.remove();
	}

	/**
	 * Creates and starts a new 'ExtentTest' with the specified name and
	 * description. The test instance is set for the current thread.
	 */
	public synchronized static ExtentTest startTest(String testName, String desc) {
		ExtentTest test = extent.createTest(testName, desc);
		extentTest.set(test);
		return test;
	}

	/**
	 * Logs details to the current 'ExtentTest' instance with the specified status.
	 */

	public static void log(Status status, String details) {
		ExtentTest test = getTest();
		if (test != null) {
			test.log(status, details);
		}
	}

	public static void log(Status status, String details, MediaEntityBuilder mediaEntityBuilder) {
		ExtentTest test = getTest();
		if (test != null) {
			test.log(status, details, mediaEntityBuilder.build());
		}
	}

}