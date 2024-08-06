package com.sample.test.demo;

import java.lang.reflect.Method;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.sample.test.report.ExtentManager;
import com.sample.test.report.ExtentTestManager;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	private Configuration config;
	protected WebDriver driver;
	protected String url;
	protected ExtentReports extentReports;
	protected ExtentTest extentTest;

	@BeforeClass(alwaysRun = true)
	public void init() throws Throwable {
		config = new Configuration();
		url = config.getUrl();
		initializelDriver();
		navigateToSite();
		ExtentManager.getInstance();
	}

	private void navigateToSite() {
		driver.get(url);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		try {
			driver.quit();
			ExtentManager.getInstance().flush();

		} catch (Exception e) {
			logger.error("Error during driver quit", e);
		}

	}

	/**
	 * Starts an ExtentTest for the current test method before its execution.
	 */
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method method) {
		ExtentTestManager.startTest(method.getName(), "Executing " + method.getName());
	}

	/**
	 * Logs the result of the test method execution in ExtentReports after the
	 * method completes.
	 */
	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			ExtentTestManager.log(Status.FAIL, "Test Failed: " + result.getThrowable());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			ExtentTestManager.log(Status.PASS, "Test Passed");
		} else if (result.getStatus() == ITestResult.SKIP) {
			ExtentTestManager.log(Status.SKIP, "Test Skipped: " + result.getThrowable());
		}
		ExtentTestManager.endTest();
	}

	

	private static final Logger logger = LogManager.getLogger(TestBase.class);

	/**
	 * Initializes the WebDriver based on the browser and platform specified in the
	 * configuration. Sets up the appropriate driver executable and handles
	 * browser-specific setups.
	 */
	private void initializelDriver() {
		String browser = config.getBrowser().toLowerCase();
		String platform = config.getPlatform().toLowerCase();

		logger.info("Initializing driver for browser");

		switch (browser) {
		case "chrome":
			if (platform.contains("mac")) {
				logger.info("Setting up Chrome Driver for macOS");
				System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver/mac/chromedriver");
			} else if (platform.contains("win")) {
				logger.info("Setting up Chrome Driver for Windows");
				WebDriverManager.chromedriver().setup();
			}
			driver = new ChromeDriver();
			break;

		case "firefox":
			if (platform.contains("mac")) {
				logger.info("Setting up Firefox Driver for Mac");
				System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver/mac/geckodriver");
			} else if (platform.contains("win")) {
				logger.info("Setting up Firefox Driver for Windows");
				WebDriverManager.firefoxdriver().setup();
			}
			driver = new FirefoxDriver();
			break;

		case "edge":
			if (platform.equals("mac")) {
				logger.info("Setting up Edge Driver for mac");
				System.setProperty("webdriver.edge.driver", "src/test/resources/edgedriver/mac/msedgedriver");
			} else {
				logger.info("Setting up Edge Driver for Windows");
				WebDriverManager.edgedriver().setup();
			}
			driver = new EdgeDriver();
			break;

		/**
		 * SafariDriver should be enabled to do enter "sudo safaridriver --enable" in
		 * terminal
		 */
		case "safari":
			if (platform.equals("mac")) {
				logger.info("Setting up Safari Driver for Mac");
				driver = new SafariDriver();
			} else {
				logger.error("Safari is only supported on macOS");
				throw new UnsupportedOperationException("Safari is only supported on macOS");
			}
			break;

		default:
			throw new IllegalArgumentException("Unsupported browser " + config.getBrowser());
		}
	}

	/**
	 * Logs Informational message to the ExtentTestManager report.
	 */
	protected void logInfo(String message) {
		ExtentTestManager.log(Status.INFO, message);
	}

	/**
	 * Logs Fail message to the ExtentTestManager report.
	 */
	protected void logError(String message) {
		ExtentTestManager.log(Status.FAIL, message);
	}

}
