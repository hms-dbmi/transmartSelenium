package dbmi.hms.harvard.edu.testplans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.xml.xpath.XPath;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.util.Strings;

import com.fasterxml.jackson.databind.ser.SerializerCache;
//import com.gargoylesoftware.htmlunit.BrowserVersion;

import dbmi.hms.harvard.edu.authentication.AuthTypes;
import dbmi.hms.harvard.edu.reporter.Reporter;
import dbmi.hms.harvard.edu.testplans.results.SummaryStatisticsResults;
import dbmi.hms.harvard.edu.transmartModules.DatasetExplorer;
import dbmi.hms.harvard.edu.transmartModules.SearchBySubject;
import dbmi.hms.harvard.edu.transmartModules.SummaryStatistics;

public class BasicStatisticsTestPlan extends Testplan {
	private static final int TIMEOUT = 30;
	// private static final String BROWSER = "webdriver.firefox.marionette";
	// private static final String BROWSER = "webdriver.chrome.driver";
	// private static final String BROWSERDRIVER = "D:\\chromedriver.exe";
	// private static String BROWSERDRIVER =
	// System.getProperty("googlechromepath");

	// private static final String BROWSER = "webdriver.gecko.driver";
	// private static String BROWSERDRIVER =
	// System.getProperty("geckodriverpath");
	private String DragConcept = ".//*[@id='ext-gen157']/div/table/tbody/tr/td";
	private String patientCountSubset2 = "//td[@colspan='2']//table[@width='100%']//tbody//tr//td[@align='center']//table[@class='analysis']//tbody//tr//td[3]";

	private Set<String> subset1;
	private Set<String> subset2;
	// private Set<String> relational;
	private Set<String> subsetmul1;
	private static WebDriver driver;
	private String FractalisType;

	private static final Logger LOGGER = Logger.getLogger(BasicStatisticsTestPlan.class.getName());

	public BasicStatisticsTestPlan() {
	}

	public Set<String> getSubset1() {
		return subset1;
	}

	public void setSubset1(Set<String> subset1) {
		this.subset1 = subset1;
	}

	public Set<String> getSubset2() {
		return subset2;
	}

	public void setSubset2(Set<String> subset2) {
		this.subset2 = subset2;
	}

	public Set<String> getSubsetmul1() {
		return subsetmul1;
	}

	/*
	 * public void setSubset1Multiple(Set<String> subsetmul1) { this.subsetmul1
	 * = subsetmul1; }
	 */

	public void setSubset1Multiple(Set<String> subsetmul1) {
		this.subsetmul1 = subsetmul1;
	}

	/*
	 * public Set<String> getRelational() { return relational; }
	 * 
	 * public void setRelational(Set<String> relational) { this.relational =
	 * relational; }
	 */

	public void loginSite() throws InterruptedException {

		String browserName = (String) testPlan.get("browser");
		System.out.println("The launched browser is " + browserName);
		String browser = browserName.toLowerCase().replaceAll(" ", "");
		switch (browser) {
		case "chrome":

			System.setProperty("webdriver.chrome.driver", System.getProperty("googlechromepath"));

			driver = new ChromeDriver();
			driver.manage().window().maximize();
			break;

		case "safari":

			// TO -DO
		case "firefox":

			System.setProperty("webdriver.gecko.driver", System.getProperty("geckodriverpath"));
			// System.setProperty("webdriver.firebox.bin",
			// System.getProperty("geckodriverpath"));
			// String path=System.getProperty("geckodriverpath");
			// System.out.println("Path of Firefox is "+path);
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			break;

		case "chromeheadless":
			System.setProperty("webdriver.chrome.driver", System.getProperty("googlechromepath"));
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--headless");
			chromeOptions.addArguments("--disable-gpu");
			chromeOptions.addArguments("--window-size=1280,800");
			chromeOptions.addArguments("--allow-insecure-localhost");
			chromeOptions.addArguments("window-size=1980,1080");
			chromeOptions.setCapability("acceptInsecureCerts", true);
			driver = new ChromeDriver(chromeOptions);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			// driver.manage().window().maximize();
			break;
		case "firefoxheadless":

			/*
			 * FirefoxBinary firefoxBinary = new FirefoxBinary(new
			 * File("D://geckodriver.exe")); //FirefoxBinary firefoxBinary = new
			 * FirefoxBinary();
			 * //firefoxBinary.addCommandLineOptions("--headless");
			 * System.setProperty("webdriver.gecko.driver",
			 * System.getProperty("geckodriverpath"));
			 * //System.setProperty("webdriver.gecko.driver",
			 * System.getProperty("geckodriverpath")); FirefoxOptions
			 * firefoxOptions = new FirefoxOptions();
			 * firefoxOptions.setCapability("marionette", false);
			 * firefoxOptions.setBinary(firefoxBinary);
			 * 
			 * // firefoxOptions.setBinary("D://geckodriver.exe"); driver = new
			 * FirefoxDriver(firefoxOptions);
			 */ // FirefoxDriver driver = new FirefoxDriver(firefoxOptions);

			// FirefoxBinary firefoxBinary = new FirefoxBinary();
			FirefoxBinary firefoxBinary = new FirefoxBinary();
			// firefoxBinary.addCommandLineOptions("--headless");

			DesiredCapabilities capabilities = new DesiredCapabilities();
			// capabilities = DesiredCapabilities.firefox();
			capabilities.setBrowserName("firefox");
			// capabilities.setVersion("your firefox version");
			capabilities.setPlatform(Platform.WINDOWS);
			// capabilities.setPlatform(Platform.LINUX);
			capabilities.setCapability("marionette", true);
			capabilities.setCapability("acceptInsecureCerts", true);
			System.setProperty("webdriver.gecko.driver", System.getProperty("geckodriverpath"));
			FirefoxOptions firefoxOptions = new FirefoxOptions(capabilities);
			// firefoxOptions.setCapability("marionette", true);
			// firefoxOptions.setCapability("acceptInsecureCerts", true);
			firefoxOptions.setBinary(firefoxBinary);
			driver = new FirefoxDriver(firefoxOptions);
		}

		LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Launching the Browser>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		// driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		LOGGER.info("");
		LOGGER.info("********************************Loading the site****" + testPlan.get("url").toString()
				+ "********************************");
		driver.get(testPlan.get("url").toString());
		AuthTypes authTypes = new AuthTypes();

		// Set authentication type
		String authLink;
		if (authTypes.getAuthTypes().containsKey(testPlan.get("authmethod").toString())) {
			authLink = authTypes.getAuthTypes().get(testPlan.get("authmethod").toString());
		} else {
			authLink = null;
		}

		// (Old)// driver.findElement(By.linkText(authLink)).click();

		authTypes.doAuth(driver, testPlan);

	}

	public void checkWinodwTitle(Reporter reporter) throws InterruptedException {

		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		String winodwTitle = driver.getTitle();
		LOGGER.info("-------------------Logged in successfully: Title of winodow is -------------------------"
				+ winodwTitle);

		if (winodwTitle.equals("Dataset Explorer")) {
			try {
				SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);

			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		else {

			try {
				SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void doPlan(Reporter reporter) throws InterruptedException {
		try {

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

				}

				driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
				SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
				SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
				DatasetExplorer.class.newInstance().doClearAnalysis(driver);
				// DatasetExplrer.class.newInstance().doSelectComparison(driver);

			}

		} catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}

		Thread.sleep(4000);
		// driver.navigate().refresh();
		// Actions actionObject = new Actions(driver);

		// actionObject.keyDown(Keys.CONTROL).sendKeys(Keys.F5).keyUp(Keys.CONTROL).perform();

		// System.out.println("page gets refreshed.....");
	}

	public void verifyExcludeFeature(Reporter reporter) throws InterruptedException {

		try {

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

				}

				if (testPlan.get("subsetmul1") != null && testPlan.get("subsetmul1") != "") {
					for (String path : java.util.Arrays.asList(testPlan.get("subsetmul1").toString().split(","))) {
						DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
						DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subsetmul1");
						DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
					}

				}

				DatasetExplorer.class.newInstance().doSelectExclude(driver);
				driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
				SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
				SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
				DatasetExplorer.class.newInstance().doClearAnalysis(driver);
				DatasetExplorer.class.newInstance().doSelectComparison(driver);

			}

		} catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}

	}

	public void verifyMessageExclude(Reporter reporter) throws InterruptedException {

		try {

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

				}

				DatasetExplorer.class.newInstance().doSelectExclude(driver);
				SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
				if (driver.findElements(By.xpath(".//*[@class='x-window x-window-plain x-window-dlg']")).size() != 0) {
					LOGGER.info(
							"***************************It will NOT generate the Summary Stats - Exlude Message displays ****************************");
					SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
				} else {
					LOGGER.info(
							"-------------------It is  generating the Summary Stats - Issue with Exlude Message display-------------------------");
					SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
				}
			}

		} catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}

	}

	public void doPlanMultipleSubset1OR(Reporter reporter)
			throws InterruptedException, InstantiationException, IllegalAccessException {
		try {

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

			if (testPlan.get("subsetmul1") != null && testPlan.get("subsetmul1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subsetmul1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

		} catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
		SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
		SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
		DatasetExplorer.class.newInstance().doClearAnalysis(driver);
		// DatasetExplorer.class.newInstance().doSelectComparison(driver);

	}

	/*
	 * Method to check Summary Stats for Multiple Subset 1 and Subset 2 with OR
	 * condition
	 */

	public void doPlanMultipleSubset1Subset2And(Reporter reporter)
			throws InterruptedException, InstantiationException, IllegalAccessException {

		try {

			for (String path : java.util.Arrays.asList(testPlan.get("subset2").toString().split(","))) {
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset2");
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			}

			Thread.sleep(5000);

		}

		catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}

		Thread.sleep(4000);
		try {

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

			Thread.sleep(5000);
			if (testPlan.get("subsetmul2") != null && testPlan.get("subsetmul2") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subsetmul2").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					Thread.sleep(7000);

					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subsetmul2");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

			if (testPlan.get("subsetmul1") != null && testPlan.get("subsetmul1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subsetmul1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subsetmul1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

		} catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}

		driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
		SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
		SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
		DatasetExplorer.class.newInstance().doClearAnalysis(driver);
		DatasetExplorer.class.newInstance().doSelectComparison(driver);

	}

	public void verifyMultipleSubset1and2OR(Reporter reporter)
			throws InterruptedException, InstantiationException, IllegalAccessException {

		try {

			for (String path : java.util.Arrays.asList(testPlan.get("subset2").toString().split(","))) {
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset2";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			}

			Thread.sleep(5000);

		}

		catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}

		Thread.sleep(10000);
		try {

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

			Thread.sleep(10000);
			if (testPlan.get("subsetmul2") != null && testPlan.get("subsetmul2") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subsetmul2").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					Thread.sleep(15000);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset2");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

			if (testPlan.get("subsetmul1") != null && testPlan.get("subsetmul1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subsetmul1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

		} catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}

		driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
		SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
		SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
		DatasetExplorer.class.newInstance().doClearAnalysis(driver);
		DatasetExplorer.class.newInstance().doSelectComparison(driver);

	}

	public void doPlanMultipleSubsetAnd(Reporter reporter)
			throws InterruptedException, InstantiationException, IllegalAccessException {
		try {

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

			if (testPlan.get("subsetmul1") != null && testPlan.get("subsetmul1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subsetmul1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subsetmul1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

		} catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
		SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
		SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
		DatasetExplorer.class.newInstance().doClearAnalysis(driver);
		DatasetExplorer.class.newInstance().doSelectComparison(driver);

	}

	public void verifyClear(Reporter reporter) throws Exception {

		try {

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

				}

				driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
				SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
				SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
				DatasetExplorer.class.newInstance().doClearAnalysis(driver);

			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		WebElement clearButton = driver.findElement(By.xpath(DragConcept));
		String testClearButton = clearButton.getText();
		assertThat(testClearButton).contains("Drag concepts to this");

	}

	public void doPlanSubset2(Reporter reporter) throws InterruptedException {
		try {

			for (String path : java.util.Arrays.asList(testPlan.get("subset2").toString().split(","))) {
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset2";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			}

			driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
			String resultBox = driver.findElement(By.xpath(patientCountSubset2)).getText();
			System.out.println("The value of Subset 2 is " + resultBox);
			DatasetExplorer.class.newInstance().doClearAnalysis(driver);
			DatasetExplorer.class.newInstance().doSelectComparison(driver);

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doPlanSubsetOneTwo(Reporter reporter) throws InterruptedException {
		try {

			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {

				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset1";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			}

			for (String path : java.util.Arrays.asList(testPlan.get("subset2").toString().split(","))) {

				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset2";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			}

			driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
			DatasetExplorer.class.newInstance().doClearAnalysis(driver);
			DatasetExplorer.class.newInstance().doSelectComparison(driver);
			// Thread.sleep(50000);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPlanSetLowFlagRange(Reporter reporter) throws InterruptedException {

		try {

			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset1";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);

			}

			driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
			DatasetExplorer.class.newInstance().doSelectByLowHighFlag(driver);
			DatasetExplorer.class.newInstance().doSelectFlagRange(driver, 0);
			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
			DatasetExplorer.class.newInstance().doClearAnalysis(driver);
			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			}

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPlanSetNoValue(Reporter reporter) throws InterruptedException {

		try {

			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset1";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
				// DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver,
				// path);

			}

			driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
			DatasetExplorer.class.newInstance().doSelectNoValue(driver);
			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
			DatasetExplorer.class.newInstance().doClearAnalysis(driver);
			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			}

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPlanSetValueLessThan(Reporter reporter) throws InterruptedException {

		String subsetValue = testPlan.get("subsetValue").toString();
		try {

			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset1";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

			}

			driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
			DatasetExplorer.class.newInstance().doSelectByNumericValue(driver);
			DatasetExplorer.class.newInstance().doSelectOpeartor(driver, 0);
			DatasetExplorer.class.newInstance().enterValue(driver, subsetValue);

			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
			DatasetExplorer.class.newInstance().doClearAnalysis(driver);
			// DatasetExplorer.class.newInstance().doSelectComparison(driver);
			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			}

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPlanSetValueLessThanEqual(Reporter reporter) throws InterruptedException {
		String subsetValue = testPlan.get("subsetValue").toString();
		try {

			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset1";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			}

			driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
			DatasetExplorer.class.newInstance().doSelectByNumericValue(driver);
			DatasetExplorer.class.newInstance().doSelectOpeartor(driver, 1);
			DatasetExplorer.class.newInstance().enterValue(driver, subsetValue);
			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
			DatasetExplorer.class.newInstance().doClearAnalysis(driver);
			// DatasetExplorer.class.newInstance().doSelectComparison(driver);

			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			}

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPlanSetValueEqual(Reporter reporter) throws InterruptedException {
		String subsetValue = testPlan.get("subsetValue").toString();
		try {

			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset1";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
			}

			driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
			DatasetExplorer.class.newInstance().doSelectByNumericValue(driver);
			DatasetExplorer.class.newInstance().doSelectOpeartor(driver, 2);
			DatasetExplorer.class.newInstance().enterValue(driver, subsetValue);
			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
			DatasetExplorer.class.newInstance().doClearAnalysis(driver);
			// DatasetExplorer.class.newInstance().doSelectComparison(driver);
			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			}

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPlanSetValueBetween(Reporter reporter) throws InterruptedException {

		String subsetLowValue = testPlan.get("subsetValue").toString();
		String subsetHighValue = testPlan.get("subsetValueHigh").toString();
		try {

			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset1";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

			}

			driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
			DatasetExplorer.class.newInstance().doSelectByNumericValue(driver);
			DatasetExplorer.class.newInstance().doSelectOpeartor(driver, 3);
			DatasetExplorer.class.newInstance().enterBetweenValue(driver, subsetLowValue, subsetHighValue);
			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
			DatasetExplorer.class.newInstance().doClearAnalysis(driver);

			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			}

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPlanSetValueGreaterThan(Reporter reporter) throws InterruptedException {

		String subsetValue = testPlan.get("subsetValue").toString();
		try {

			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset1";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

			}

			driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
			DatasetExplorer.class.newInstance().doSelectByNumericValue(driver);
			DatasetExplorer.class.newInstance().doSelectOpeartor(driver, 4);
			DatasetExplorer.class.newInstance().enterValue(driver, subsetValue);
			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
			DatasetExplorer.class.newInstance().doClearAnalysis(driver);

			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			}

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPlanSetValueGreaterThanEqual(Reporter reporter) throws InterruptedException {

		String subsetValue = testPlan.get("subsetValue").toString();
		try {

			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset1";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

			}

			driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
			DatasetExplorer.class.newInstance().doSelectByNumericValue(driver);
			DatasetExplorer.class.newInstance().doSelectOpeartor(driver, 5);
			DatasetExplorer.class.newInstance().enterValue(driver, subsetValue);
			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
			DatasetExplorer.class.newInstance().doClearAnalysis(driver);

			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			}

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPlanSaveSubset(Reporter reporter) throws InterruptedException {
		String subsetName = testPlan.get("subsetName").toString();
		long TimeStamp = new Date().getTime();
		String FinalSubset = subsetName + TimeStamp;

		System.out.println("%%%%%%Final subsetName is" + FinalSubset);
		try {

			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset1";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);

			}

			driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
			DatasetExplorer.class.newInstance().doSaveComparison(driver);
			DatasetExplorer.class.newInstance().doEnterSubsetName(driver, FinalSubset);
			DatasetExplorer.class.newInstance().saveSubsets(driver);

			WebElement workSpaceSubsetHeading = driver.findElement(By.xpath(".//*[@id='subset_manager_name']/label"));

			System.out.println("Workspace Subset heading is " + workSpaceSubsetHeading.getText());

			if (workSpaceSubsetHeading.getText().equals("Subset Manager")) {
				try {
					SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);

				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}

			else {

				try {
					SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* This method checks Summary Stats for Laboratory Terms */
	public void verifySummaryStatsLab(Reporter reporter) throws InterruptedException {

		try {

			String subsetvalue = testPlan.get("subsetValue1").toString();
			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

				}
			}
			driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
			DatasetExplorer.class.newInstance().doSelectByNumericValue(driver);
			DatasetExplorer.class.newInstance().enterValue(driver, subsetvalue);

			if (testPlan.get("subset2") != null && testPlan.get("subset2") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset2").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset2");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
			DatasetExplorer.class.newInstance().doClearAnalysis(driver);
			DatasetExplorer.class.newInstance().doSelectComparison(driver);

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* This method checks Summary Stats for Questionnaire Terms */
	public void verifySumStasQue(Reporter reporter) throws InterruptedException {

		try {

			String subsetvalue = testPlan.get("subsetValue1").toString();
			// double SubsetvalueLab=Double.parseDouble(subsetvalue);

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

				}
			}
			driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
			DatasetExplorer.class.newInstance().doSelectByNumericValue(driver);
			DatasetExplorer.class.newInstance().enterValue(driver, subsetvalue);

			if (testPlan.get("subset2") != null && testPlan.get("subset2") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset2").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset2");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
			DatasetExplorer.class.newInstance().doClearAnalysis(driver);
			DatasetExplorer.class.newInstance().doSelectComparison(driver);

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doSearch(Reporter reporter) throws InterruptedException, Exception {

		String searchTerm = testPlan.get("searchTerm").toString();

		try {
			// SearchBySubject.class.newInstance().doSelectNavigationTab(driver);
			SearchBySubject.class.newInstance().doSearch(driver, searchTerm);
			List<WebElement> optionsToSelect = driver.findElements(By.xpath(".//li[@class='ui-menu-item']"));
			int serachResul = optionsToSelect.size();
			String serachResultcount = new Integer(serachResul).toString();
			// System.out.println("NO.dfdfdf"+count);
			// driver.findElement(By.id("your searchBox")).sendKeys("your
			// partial keyword");
			// List <WebElement> listItems = driver.findElements(By.xpath("your
			// list item locator"));
			Thread.sleep(5000);
			// SearchBySubject.class.newInstance().getSearchResult(driver);
			// String SearchResult1 = SearchBySubject.result;
			// int expected = (int) testPlan.get("expectedSearchResult");
			// System.out.println("Expected result is " +expected);
			if (serachResultcount.equals(testPlan.get("expectedSearchResult").toString())) {
				try {

					SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			}

			else {
				SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		SearchBySubject.class.newInstance().doClearSearchBox(driver);
	}

	public void doSearchCaseSensitivity(Reporter reporter) throws InterruptedException, Exception {

		String searchTerm = testPlan.get("searchTermCapital").toString();

		try {

			SearchBySubject.class.newInstance().doSearch(driver, searchTerm);
			List<WebElement> optionsToSelect = driver.findElements(By.xpath(".//li[@class='ui-menu-item']"));
			int serachResul = optionsToSelect.size();
			String serachResultcount = new Integer(serachResul).toString();
			Thread.sleep(5000);
			if (serachResultcount.equals(testPlan.get("expectedSearchResult").toString())) {
				try {

					SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			}

			else {
				SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		SearchBySubject.class.newInstance().doClearSearchBox(driver);
	}

	public void doSearchLength(Reporter reporter) throws InterruptedException, Exception {

		String searchTerm = testPlan.get("searchTerm1").toString();
		SearchBySubject.class.newInstance().doSelectNavigationTab(driver);
		SearchBySubject.class.newInstance().doSearch(driver, searchTerm);
		try {
			Alert alert = driver.switchTo().alert();
			if (alert != null && !"".equals(alert)) {
				String searchTermCharLength = alert.getText();
				System.out.println("The Text Message is " + searchTermCharLength);
				alert.accept();
				SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			}
		} catch (NoAlertPresentException e) {
			// e.printStackTrace();
			System.out.println("no alert");
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
		}

		SearchBySubject.class.newInstance().doClearSearchBox(driver);
	}

	public void doSearchSpecialChar(Reporter reporter) throws InterruptedException, Exception {

		String searchTermSpecialCharacter = testPlan.get("searchTermSpecialChar").toString();
		try {
			SearchBySubject.class.newInstance().doSelectNavigationTab(driver);
			SearchBySubject.class.newInstance().doSearch(driver, searchTermSpecialCharacter);
			Thread.sleep(20000);
			SearchBySubject.class.newInstance().getSearchResult(driver);
			String SearchResult1 = SearchBySubject.result;
			String expected = testPlan.get("expectedSearchResult").toString();
			if (SearchResult1.equals(testPlan.get("expectedSearchResult").toString())) {
				try {

					SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			}

			else {
				SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		SearchBySubject.class.newInstance().doClearSearchBox(driver);
	}

	public void doPlanSummaryStatSearch(Reporter reporter) throws InterruptedException {

		String searchStats = testPlan.get("searchTermsum").toString();
		try {
			SearchBySubject.class.newInstance().doSelectNavigationTab(driver);
			SearchBySubject.class.newInstance().doSearch(driver, searchStats);
			Thread.sleep(10000);
			// SearchBySubject.class.newInstance().getSearchResult(driver);
			// String searchResult1 = SearchBySubject.result;

			/*
			 * if (searchResult1.substring(6, 7).equals("1")) {
			 * 
			 * DatasetExplorer.class.newInstance().doSearchDoDragAndDrop(driver,
			 * searchResult1, "subset1");
			 * SummaryStatistics.class.newInstance().runSummaryStatistics(driver
			 * );
			 * SummaryStatisticsResults.class.newInstance().doAssertResultTrue(
			 * driver, testPlan, reporter);
			 * DatasetExplorer.class.newInstance().doClearAnalysis(driver);
			 * DatasetExplorer.class.newInstance().doSelectComparison(driver); }
			 * else {
			 * SummaryStatisticsResults.class.newInstance().doAssertResultFalse(
			 * driver, testPlan, reporter); }
			 */
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * try { SearchBySubject.class.newInstance().getSearchResult(driver); }
		 * catch (InstantiationException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (IllegalAccessException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */ }

	public void verifyExpandCollpase(Reporter reporter) {
		for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
			try {
				System.out.println(subset1);
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String subset = "subset1";
			try {
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void verifyGraphs(Reporter reporter) throws Exception {

		try {

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

				}

				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
				// WebElement
				// graph=driver.findElement(By.xpath(".//*[@id='ext-gen62']/div/table[1]/tbody/tr[4]/td/table/tbody/tr/td[1]"));

				if (driver
						.findElements(
								By.xpath(".//*[@id='ext-gen62']/div/table[1]/tbody/tr[4]/td/table/tbody/tr/td[1]"))
						.size() != 0
						&& driver.findElement(By.xpath(
								".//*[@id='ext-gen62']/div/table[1]/tbody/tr[4]/td/table/tbody/tr/td[3]")) != null) {
					LOGGER.info(
							"***************************Graph is present on the Summary reports ****************************");

					SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);

				} else {

					LOGGER.info(
							"-------------------Graph has issue it is not generated on the Summary reports -------------------------");
					SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
				}
			}
		}

		catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}

	}

	public void FractalisDragVariables(String FractalisType)
			throws InterruptedException, InstantiationException, IllegalAccessException {
		try {

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

				}
			}

		} catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}

		DatasetExplorer.class.newInstance().doSelectFractlis(driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// DatasetExplorer.class.newInstance().doSelectFractalisAnalysis(driver,
		// 2);
		DatasetExplorer.class.newInstance().doSelectFractalisAnalysis(driver, FractalisType);
		// DatasetExplorer.class.newInstance().addAnalysis(driver);
		Thread.sleep(7000);

		try {

			if (testPlan.get("fractConNum2") != null && testPlan.get("fractConNum2") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("fractConNum2").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "fractConNum2");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

				}
			}

		} catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}

		Thread.sleep(12000);

		try {

			if (testPlan.get("fractConNum1") != null && testPlan.get("fractConNum1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("fractConNum1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "fractConNum1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

				}
			}

		} catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}

		Thread.sleep(13000);
		try {

			if (testPlan.get("fractConCate2") != null && testPlan.get("fractConCate2") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("fractConCate2").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "fractConCate2");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

				}
			}

		} catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}

		Thread.sleep(10000);
		try {

			if (testPlan.get("fractConCate1") != null && testPlan.get("fractConCate1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("fractConCate1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "fractConCate1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

				}
			}

		} catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}

		Thread.sleep(70000);

		/*List<WebElement> els = driver.findElements(By.xpath("//input[@data-v-074d016a='' and @type='checkbox']"));
		for (WebElement el : els) {
			if (!el.isSelected()) {
				el.click();
			}
		}*/

	}

	public void verifyFractlisIntergrationScatterPlotCorrelationPeasron(Reporter reporter) throws Exception

	{

		// DatasetExplorer.class.newInstance().checkState(driver);
		/*
		 * The method FractalisDragsVariables drags the concepts/variables for
		 * the given Fractalis Analysis type
		 */

		FractalisDragVariables("ScatterPlot");

		List<WebElement> els = driver.findElements(By.xpath("//input[@data-v-074d016a='' and @type='checkbox']"));
		for (WebElement el : els) {
			if (!el.isSelected()) {
				el.click();
			}
		}

		Thread.sleep(60000);

		if (driver
				.findElements(
						By.xpath(".//div[@id='fjs-tm-chart-0']/div/*[name()='svg']/*[name()='g']/*[name()='rect'][1]"))
				.size() != 0) {

			LOGGER.info(
					"***************************Correlation Analysis Graph is generated through Fractalis ****************************");

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);

		} else {

			LOGGER.info(
					"***************************Correlation Analysis Graph is *NOT* generated through Fractalis***************************");
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
		}

		DatasetExplorer.class.newInstance().resetReview(driver);
		// DatasetExplorer.class.newInstance().clearAnalysisCache(driver);
	}

	public void verifyFractlisIntergrationScatterPlotCorrelationSpearman(Reporter reporter) throws Exception

	{

		// DatasetExplorer.class.newInstance().checkState(driver);
		/*
		 * The method FractalisDragsVariables drags the concepts/variables for
		 * the given Fractalis Analysis type
		 */

		DatasetExplorer.class.newInstance().doSelectFractalisAnalysis(driver, "ScatterPlot");

		// Selecting the radio button second which separman correaltion method
		// by passing 1s

		DatasetExplorer.class.newInstance().doSelectSubMethod(driver, 1);
		List<WebElement> els = driver.findElements(By.xpath("//input[@data-v-074d016a='' and @type='checkbox']"));
		for (WebElement el : els) {
			if (!el.isSelected()) {
				el.click();
			}
		}

		Thread.sleep(60000);

		if (driver
				.findElements(
						By.xpath(".//div[@id='fjs-tm-chart-0']/div/*[name()='svg']/*[name()='g']/*[name()='rect'][1]"))
				.size() != 0) {

			LOGGER.info(
					"***************************Correlation Analysis Graph is generated through Fractalis ****************************");

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);

		} else {

			LOGGER.info(
					"***************************Correlation Analysis Graph is *NOT* generated through Fractalis***************************");
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
		}

		DatasetExplorer.class.newInstance().resetReview(driver);
		// DatasetExplorer.class.newInstance().clearAnalysisCache(drive r);
	}

	public void verifyFractlisIntergrationScatterPlotCorrelationKendall(Reporter reporter) throws Exception

	{

		// DatasetExplorer.class.newInstance().checkState(driver);
		/*
		 * The method FractalisDragsVariables drags the concepts/variables for
		 * the given Fractalis Analysis type
		 */

		DatasetExplorer.class.newInstance().doSelectFractalisAnalysis(driver, "ScatterPlot");

		// Selecting the radio button second which separman correaltion method
		// by passing 2

		DatasetExplorer.class.newInstance().doSelectSubMethod(driver, 2);

		List<WebElement> els = driver.findElements(By.xpath("//input[@data-v-074d016a='' and @type='checkbox']"));
		for (WebElement el : els) {
			if (!el.isSelected()) {
				el.click();
			}	
		}

		Thread.sleep(60000);

		if (driver
				.findElements(
						By.xpath(".//div[@id='fjs-tm-chart-0']/div/*[name()='svg']/*[name()='g']/*[name()='rect'][1]"))
				.size() != 0) {

			LOGGER.info(
					"***************************Correlation Analysis Graph is generated through Fractalis ****************************");

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);

		} else {

			LOGGER.info(
					"***************************Correlation Analysis Graph is *NOT* generated through Fractalis***************************");
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
		}

		DatasetExplorer.class.newInstance().resetReview(driver);
		// DatasetExplorer.class.newInstance().clearAnalysisCache(driver);
	}

	public void verifyFractlisIntergrationBoxPlot(Reporter reporter) throws Exception {

		FractalisDragVariables("BoxPlot");
		// DatasetExplorer.class.newInstance().doSelectBoxPlotDataTransformation(driver,
		// 1);

		List<WebElement> els = driver.findElements(By.xpath("//input[@data-v-074d016a='' and @type='checkbox']"));
		for (WebElement el : els) {
			if (!el.isSelected()) {
				el.click();
			}
		}

		Thread.sleep(60000);

		if (driver
				.findElements(
						By.xpath(".//div[@id='fjs-tm-chart-0']/div/*[name()='svg']/*[name()='g']/*[name()='g'][2]/*[name()='rect'][1]"))
				.size() != 0) {

			// By.xpath(".//div[@id='fjs-tm-chart-0']/div/*[name()='svg']/*[name()='g']/*[name()='g'][1])")).size()
			// != 0) {

			LOGGER.info(
					"***************************BoxPlot Anlaysis  Graph is generated through Fractalis ****************************");

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);

		} else {

			LOGGER.info(
					"***************************BoxPlot Anlaysis Graph is *NOT* generated through Fractalis***************************");
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
		}

		DatasetExplorer.class.newInstance().resetReview(driver);
		// DatasetExplorer.class.newInstance().clearAnalysisCache(driver);
	}

	public void verifyFractalisBoxPlotDataTransformation(Reporter reporter) throws Exception {

		String log2xCheck = null;
		String log10xCheck = null;
		String identityCheck = null;
		String twoPowerXCheck = null;
		String tenPowerXCheck = null;

		DatasetExplorer.class.newInstance().doSelectFractalisAnalysis(driver, "BoxPlot");
		DatasetExplorer.class.newInstance().doSelectBoxPlotDataTransformation(driver, 1);

		List<WebElement> els = driver.findElements(By.xpath("//input[@data-v-074d016a='' and @type='checkbox']"));
		for (WebElement el : els) {
			if (!el.isSelected()) {
				el.click();
			}
		}

		Thread.sleep(60000);

		// Verify that data trasformation generates tranformed data in Log2x

		if (driver
				.findElements(
						By.xpath(".//div[@id='fjs-tm-chart-0']/div/*[name()='svg']/*[name()='g']/*[name()='g'][2]/*[name()='rect'][1]"))
				.size() != 0) {

			log2xCheck = "true";
			System.out.println("DataTransformtion with Log2x works fine" + log2xCheck);

			LOGGER.info(
					"***************************BoxPlot Anlaysis  DataTransformtion with Log2x works fine ****************************");
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);

		} else {

			LOGGER.info(
					"***************************BoxPlot Anlaysis DataTransformtion with Log2x is having some issue***************************");
			log2xCheck = "false";
			System.out.println("DataTransformtion with Log2x is having some issue****" + log2xCheck);
		}

		System.out.println("DataTransformtion with Log2x works fine" + log2xCheck);

		DatasetExplorer.class.newInstance().resetReview(driver);
		DatasetExplorer.class.newInstance().doSelectFractalisAnalysis(driver, "BoxPlot");
		DatasetExplorer.class.newInstance().doSelectBoxPlotDataTransformation(driver, 2);

		// Verify that data trasformation generates tranformed data in Log10x

		if (driver
				.findElements(
						By.xpath(".//div[@id='fjs-tm-chart-0']/div/*[name()='svg']/*[name()='g']/*[name()='g'][2]/*[name()='rect'][1]"))
				.size() != 0) {

			log10xCheck = "true";
			System.out.println("DataTransformtion with Log10x works fine" + log10xCheck);

			LOGGER.info(
					"***************************BoxPlot Anlaysis  DataTransformtion with Log10x works fine ****************************");
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);

		} else {

			LOGGER.info(
					"***************************BoxPlot Anlaysis DataTransformtion with Log10x is having some issue***************************");
			log10xCheck = "false";
			System.out.println("DataTransformtion with Log2x is having some issue****" + log10xCheck);
		}

		// Verify that data trasformation generates tranformed data for Identity

		DatasetExplorer.class.newInstance().resetReview(driver);
		DatasetExplorer.class.newInstance().doSelectFractalisAnalysis(driver, "BoxPlot");
		DatasetExplorer.class.newInstance().doSelectBoxPlotDataTransformation(driver, 3);

		if (driver
				.findElements(
						By.xpath(".//div[@id='fjs-tm-chart-0']/div/*[name()='svg']/*[name()='g']/*[name()='g'][2]/*[name()='rect'][1]"))
				.size() != 0) {

			identityCheck = "true";
			System.out.println("DataTransformtion with Log10x works fine" + identityCheck);

			LOGGER.info(
					"***************************BoxPlot Anlaysis  DataTransformtion with identityCheck works fine ****************************");
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);

		} else {

			LOGGER.info(
					"***************************BoxPlot Anlaysis DataTransformtion with identityCheck is having some issue***************************");
			identityCheck = "false";
			System.out.println("DataTransformtion with Log2x is having some issue****" + identityCheck);
		}

		if (log2xCheck == "true" && log10xCheck == "true" && identityCheck == "true") {
			LOGGER.info(
					"<<<<***************************BoxPlot Anlaysis DataTransformtion feature is working fine***************************");

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
		}

		else {
			LOGGER.info(
					">>>>>>.***************************BoxPlot Anlaysis DataTransformtion Feature is having some issue***************************");
			System.out.println();
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
		}
		DatasetExplorer.class.newInstance().resetReview(driver);

	}


	public void verifyFractlisSelection()
	{
		
		//to Do
	}
	
	
	public void verifyFractlisIntergrationPrincipleComponentAnalysis(Reporter reporter) throws Exception {

		FractalisDragVariables("PCA");

		List<WebElement> els = driver.findElements(By.xpath("//input[@data-v-074d016a='' and @type='checkbox']"));
		for (WebElement el : els) {
			if (!el.isSelected()) {
				el.click();
			}
		}

		Thread.sleep(60000);

		if (driver
				.findElements(
						By.xpath(".//div[@id='fjs-tm-chart-0']/div/*[name()='svg']/*[name()='g']/*[name()='g'][8]/*[name()='text']"))
				.size() != 0) {

			LOGGER.info(
					"***************************Principle ComponentAnalysis  Graph is generated through Fractalis ****************************");

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);

		} else {

			LOGGER.info(
					"***************************Principle ComponentAnalysis Graph is *NOT* generated through Fractalis***************************");
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
		}

		DatasetExplorer.class.newInstance().resetReview(driver);
		DatasetExplorer.class.newInstance().clearAnalysisCache(driver);
	}

	public void verifyFractlisIntergrationHistogram(Reporter reporter) throws Exception {

		FractalisDragVariables("Histogram");

		List<WebElement> els = driver.findElements(By.xpath("//input[@data-v-074d016a='' and @type='checkbox']"));
		for (WebElement el : els) {
			if (!el.isSelected()) {
				el.click();
			}
		}

		Thread.sleep(60000);

		if (driver
				.findElements(
						By.xpath(" .//div[@id='fjs-tm-chart-0']/div/div[3]/div/div/div/*[name()='svg']/*[name()='rect'][1]"))
				.size() != 0) {

			LOGGER.info(
					"***************************Histogram Graph is generated through Fractalis ****************************");

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);

		} else {

			LOGGER.info(
					"***************************Histogram Analysis Graph is *NOT* generated through Fractalis***************************");
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
		}

		DatasetExplorer.class.newInstance().resetReview(driver);
		DatasetExplorer.class.newInstance().clearAnalysisCache(driver);
	}

	public void verifyFractalisSurvivalPlot(Reporter reporter) throws Exception {

		FractalisDragVariables("SurvivalPlot");
		

		if (driver
				.findElements(
						By.xpath(".//*[@id='fjs-tm-chart-0']/div/div[3]/div/div/div[1]/span"))
				.size() != 0) {

			LOGGER.info(
					"***************************Survival Analysis Graph is generated through Fractalis ****************************");

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);

		} else {

			LOGGER.info(
					"***************************Survival Analysis Graph is *NOT* generated through Fractalis***************************");
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
		}

		DatasetExplorer.class.newInstance().resetReview(driver);
		//DatasetExplorer.class.newInstance().clearAnalysisCache(driver);
	}

	public void verifyFractalisSurvivalPlotEstimatorKaplanMeier(Reporter reporter) throws Exception {

		DatasetExplorer.class.newInstance().doSelectFractalisAnalysis(driver, "SurvivalPlot");
		
		DatasetExplorer.class.newInstance().doSelectSubMethodSurvival(driver, 0);
		if (driver
				.findElements(
						By.xpath(".//*[@id='fjs-tm-chart-0']/div/div[3]/div/div/div[1]/span"))
				.size() != 0) {

			LOGGER.info(
					"***************************Survival Analysis Graph for kaplan Meier is generated through Fractalis ****************************");

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);

		} else {

			LOGGER.info(
					"***************************Survival Analysis Graph for kaplan Meier is *NOT* generated through Fractalis***************************");
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
		}

		DatasetExplorer.class.newInstance().resetReview(driver);
		DatasetExplorer.class.newInstance().clearAnalysisCache(driver);
	}

	public void verifyFractalisSurvivalPlotEstimatorNelsonAalen(Reporter reporter) throws Exception {

		DatasetExplorer.class.newInstance().doSelectFractalisAnalysis(driver, "SurvivalPlot");
		DatasetExplorer.class.newInstance().doSelectSubMethodSurvival(driver, 1);
		if (driver
				.findElements(
						By.xpath(".//*[@id='fjs-tm-chart-0']/div/div[3]/div/div/div[1]/span"))
				.size() != 0) {

			LOGGER.info(
					"***************************Survival Analysis Graph for NelsonAalen is generated through Fractalis ****************************");

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);

		} else {

			LOGGER.info(
					"***************************Survival Analysis Graph for NelsonAalen is *NOT* generated through Fractalis***************************");
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
		}

		DatasetExplorer.class.newInstance().resetReview(driver);
		//DatasetExplorer.class.newInstance().clearAnalysisCache(driver)
	}

	public void verifyFractlisSelection(Reporter reporter) throws InstantiationException, IllegalAccessException, InterruptedException {

		//Cliking mulltiple times create multi select chart area
		
		FractalisDragVariables("ScatterPlot");
		DatasetExplorer.class.newInstance().doSelectFractalisAnalysis(driver, "ScatterPlot");
		DatasetExplorer.class.newInstance().doClickSelect(driver);
		
		List<WebElement> els = driver.findElements(By.xpath("//input[@data-v-074d016a='' and @type='checkbox']"));
		for (WebElement el : els) {
			if (!el.isSelected()) {
				el.click();
			}	
		}

		
		if (driver
				.findElements(
						By.xpath(".//div[@id='fjs-tm-chart-1']/div/*[name()='svg']/*[name()='g']/*[name()='rect'][1]"))
				.size() != 0) {

			LOGGER.info(
					"***************************Multiple Select button functionality is woring fine ****************************");

			SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);

		} else {
			

			LOGGER.info(
					"***************************Multiple Select button functionality is woring fine***************************");
			SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
		}

		DatasetExplorer.class.newInstance().resetReview(driver);
		//DatasetExplorer.class.newInstance().clearAnalysisCache(driver)
	}
	
	
	public void verifyDelete(Reporter reporter) throws InterruptedException {

		try {

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

			if (testPlan.get("subsetmul1") != null && testPlan.get("subsetmul1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subsetmul1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}
				

			}
			DatasetExplorer.class.newInstance().doCheckSubsetBox(driver);
			DatasetExplorer.class.newInstance().doDelete(driver);
			DatasetExplorer.class.newInstance().doCheckSubsetBox(driver);
			if (Strings.isNullOrEmpty(DatasetExplorer.textSubsetBoxValue)) {
				SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			} else {

				SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			}

		} catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}

	}

	public void closeDriver() {
		driver.quit();
	}

	// Test Expanding/collapsing the concept tree

}
