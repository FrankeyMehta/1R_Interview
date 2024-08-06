package com.sample.test.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * Retrieves or initializes the singleton 'ExtentReports' instance, configuring
 * it with 'ExtentSparkReporter' and adding custom details to the report.
 */
public class ExtentManager {
	private static ExtentReports extent;

	public synchronized static ExtentReports getInstance() {
		if (extent == null) {
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter("Report/SparkReport.html");
			sparkReporter.config().setTheme(Theme.STANDARD);
			sparkReporter.config().setDocumentTitle("Automation Report");
			sparkReporter.config().setReportName("Automation Results");

			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);

			// Display environment details in the Report
			extent.setSystemInfo("OS", System.getProperty("os.name"));
			extent.setSystemInfo("OS Version", System.getProperty("os.version"));
			extent.setSystemInfo("Java Version", System.getProperty("java.version"));
			extent.setSystemInfo("User Name", System.getProperty("user.name"));
			extent.setSystemInfo("Test Environment", "QA");
		}
		return extent;
	}
}
