package dbmi.hms.harvard.edu.testdrivers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import dbmi.hms.harvard.edu.reporter.Reporter;
import dbmi.hms.harvard.edu.testplans.Testplan;

public class TestDriver {

	public static final String TESTPLANS = "dbmi.hms.harvard.edu.testplans.";
	public static final String REPORTS = "dbmi.hms.harvard.edu.reporter.";
	private static Properties configProperties = new Properties();

	// Read data from Properties file using Java Selenium

	static {

		try {
			configProperties.load(new FileInputStream(new File("src/test/java/Config.properties")));
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static final Logger LOGGER = Logger.getLogger(TestDriver.class.getName());
	WebDriver driver;

	// @Atul
	public static Testplan testPlan = null;
	static Reporter reporter = null;

	public static void readFile(String fileName) {
		try {
			YamlReader reader1 = new YamlReader(new FileReader(fileName));

			Map testConfig = (Map) reader1.read();
			if (testConfig != null) {

				if (testPlan == null)
					testPlan = initTestPlan(testConfig.get("type").toString(), testConfig);
				testPlan.setTestPlan(testConfig);
				if (reporter == null)
					reporter = initReporter(testConfig.get("reporter").toString());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (YamlException e) {
			e.printStackTrace();
		}
	}

@BeforeTest
		
	public void setup() throws InterruptedException {

		LOGGER.info(
				"______________________________________Setting up the application______________________________________");
		// readFile("resources/testConfigs/projects.yaml.onlysubset1.template");
		readFile(configProperties.getProperty("verify.window.title"));
		testPlan.loginSite();
		LOGGER.info(
				"---------------------------------Initial setup is done------------------------------");
    
	}

@Test(priority = 1)
	public static void verifyLoginWithWinowTitle() throws YamlException, InterruptedException {

		LOGGER.info(
				"---------------------------------The test case verifyWinowTitle is running-------------------------");
		// readFile("resources/testConfigs/projects.yaml.onlysubset1.template");
		readFile(configProperties.getProperty("verify.window.title"));
		testPlan.checkWinodwTitle(reporter);
		LOGGER.info(
				"---------------------------------The test case verifyWinowTitle is Finshed-------------------------");

	}

@Test(priority= 2,groups={"Sanity"})

	public static void verifySummaryStats() throws YamlException, InterruptedException {

		LOGGER.info(
				"-------------------------------The test case verifySummaryStats is running-------------------------");
		readFile(configProperties.getProperty("verify.summaryStats.subset1"));
		testPlan.doPlan(reporter);
		LOGGER.info(
				"--------------------------------The test case verifySummaryStats is Finshed-------------------------");

	}

@Test(priority = 3,groups={"Sanity Regression"})

	public static void verifySummaryStatsSubset2() throws YamlException, InterruptedException {

		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSubset2 is running-------------------------");
		readFile(configProperties.getProperty("verify.summaryStats.subset2"));
		testPlan.doPlanSubset2(reporter);

		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSubset2 is Finshed-------------------------");

	}

@Test(priority = 4,groups={"Sanity Regression"})

	public static void verifySummaryStatsSetValue() throws YamlException, InterruptedException {

		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSetValue is running-------------------------");
		readFile(configProperties.getProperty("verify.summarystats.entersubset.value"));
		// readFile("resources/testConfigs/projects.yaml.subset1.setvalue.template");
		testPlan.doPlanSetValue(reporter);
		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSetValue is Finshed-------------------------");

	}

@Test(priority = 5,groups={"Sanity Regression"})

	public static void verifySummaryStatsSubsetOneTwo() throws YamlException, InterruptedException {

		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSubsetOneTwo is running-------------------------");
		readFile(configProperties.getProperty("verify.summarystats.subset1subset2"));
		// readFile("resources/testConfigs/projects.yaml.subset12.template");
		testPlan.doPlanSubsetOneTwo(reporter);
		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSubsetOneTwo is Finshed-------------------------");

	}


@Test(priority = 6,groups={"Sanity Regression"})

	public static void verifySummaryStatsMultipleSubset1OR()
			throws YamlException, InterruptedException, InstantiationException, IllegalAccessException {

		LOGGER.info(
				"-------------------------------The test case verifySummaryStatsMultipleSubset1OR is running-------------------------");
		readFile(configProperties.getProperty("verify.summarystats.multiplesubset1OR"));
		testPlan.doPlanMultipleSubset(reporter);
		LOGGER.info(
				"--------------------------------The test case verifySummaryStatsMultipleSubset1OR is Finshed-------------------------");

	}

@Test(priority = 7,groups={"Sanity Regression"})
	public static void verifySummaryStatsMultipleSubset1AND()
			throws YamlException, InterruptedException, InstantiationException, IllegalAccessException {

		LOGGER.info(
				"-------------------------------The test case verifySummaryStatsMultipleSubset1AND is running-------------------------");
		readFile(configProperties.getProperty("verify.summarystats.multiplesubset1AND"));
		testPlan.doPlanMultipleSubsetAnd(reporter);
		LOGGER.info(
				"--------------------------------The test case verifySummaryStatsMultipleSubset1AND is Finshed-------------------------");

	}

@Test(priority = 8,groups={"Sanity"})
	public static void verifySummaryStatsExcludeFunctionality()
			throws YamlException, InterruptedException, InstantiationException, IllegalAccessException {

		LOGGER.info(
				"-------------------------------The test case verifySummaryStatsExcludeFunctionality is running-------------------------");
		readFile(configProperties.getProperty("verify.summaryStats.exclude"));
		testPlan.verifyExcludeFeature(reporter);
		LOGGER.info(
				"--------------------------------The test case verifySummaryStatsExcludeFunctionality is Finshed-------------------------");

	}

@Test(priority = 9,groups={"Sanity Regression"})

	public static void verifySummaryStatsSetValueOperator() throws YamlException, InterruptedException {

		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSetValue is running-------------------------");
		readFile(configProperties.getProperty("verify.summarystats.entersubset.value"));
		// readFile("resources/testConfigs/projects.yaml.subset1.setvalue.template");
		testPlan.doPlanSetValue(reporter);
		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSetValue is Finshed-------------------------");

	}

@Test(priority = 10,groups={"Sanity Regression"})

	public static void verifySummaryStatsGraphs() throws Exception {

		LOGGER.info(
				"-------------------------------The test case verify graphs  is running-------------------------");
		readFile(configProperties.getProperty("verify.summaryStats.subset1"));
		testPlan.verifyGraphs(reporter);
		LOGGER.info(
				"--------------------------------The test case verifySummaryStats is Finshed-------------------------");

	}
@Test(priority = 11,groups={"Sanity Regression"})

public static void verifyClearButton() throws Exception {

	LOGGER.info("---------------------------The test case verifyClearButton is running-------------------------");
	readFile(configProperties.getProperty("verify.window.title"));
	testPlan.verifyClear(reporter);
	LOGGER.info("---------------------------The test case verifyClearButton is Finshed-------------------------");
}


@AfterClass
	public void closeApplication() {

		reporter.doReport();
		testPlan.closeDriver();
		System.out.println("Testing done");
		LOGGER.info(
				"=================================i2b2/Transmart Test Automation completed : Browser session closed ================================");

	}
	
	

	@SuppressWarnings("finally")
	private static Testplan initTestPlan(String testType, @SuppressWarnings("rawtypes") Map map) {
		Testplan newInstance = null;
		try {
			Class<?> resourceInterfaceClass = Class.forName(TESTPLANS + testType);

			newInstance = (Testplan) resourceInterfaceClass.newInstance();
			// newInstance.setTestPlan(map);
		} catch (SecurityException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			System.out.println(e);
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			return null;
		} finally {

			return newInstance;
		}
	}

	@SuppressWarnings("finally")
	private static Reporter initReporter(String reporterType) {
		Reporter newInstance = null;

		try {
			Class<?> resourceInterfaceClass = Class.forName(REPORTS + reporterType);
			newInstance = (Reporter) resourceInterfaceClass.newInstance();
		} catch (SecurityException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			return newInstance;
		}
	}

}
