package dbmi.hms.harvard.edu.testdrivers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.assertj.core.configuration.ConfigurationProvider;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.yaml.snakeyaml.error.YAMLException;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import dbmi.hms.harvard.edu.reporter.Reporter;
import dbmi.hms.harvard.edu.testplans.Testplan;

public class TestDriver {

	public static final String TESTPLANS = "dbmi.hms.harvard.edu.testplans.";
	public static final String REPORTS = "dbmi.hms.harvard.edu.reporter.";
	private static Properties configProperties = new Properties();

	//Read data from Properties file using Java Selenium
	
	static {
		
		try {
			configProperties.load(new FileInputStream(new File("src/test/java/Config.properties")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static final Logger LOGGER = Logger.getLogger(TestDriver.class.getName());
	WebDriver driver;
	// public static void main(String[] args) throws YamlException {
	// @Atul
	public static Testplan testPlan = null;
	static Reporter reporter = null;

	/*
	 * public TestDriver() { try { YamlReader reader1 = new YamlReader( new
	 * FileReader("resources/testConfigs/projects.yaml.template")); //
	 * YamlReader reader2 = new YamlReader(new //
	 * FileReader("resources/testConfigs/projects.yaml.template"));
	 * 
	 * @SuppressWarnings("rawtypes") Map testConfig = (Map) reader1.read(); if
	 * (testConfig != null) {
	 * 
	 * testPlan1 = initTestPlan(testConfig.get("type").toString(), testConfig);
	 * testPlan1.setTestPlan(testConfig); reporter =
	 * initReporter(testConfig.get("reporter").toString()); } } catch
	 * (FileNotFoundException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (YamlException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); }
	 * 
	 * try { YamlReader reader2 = new YamlReader(new
	 * FileReader("resources/testConfigs/projects.yaml.subset2.template")); //
	 * YamlReader reader2 = new YamlReader(new //
	 * FileReader("resources/testConfigs/projects.yaml.template"));
	 * 
	 * @SuppressWarnings("rawtypes") Map testConfig = (Map) reader2.read(); if
	 * (testConfig != null) {
	 * 
	 * testPlan2 = initTestPlan(testConfig.get("type").toString(), testConfig);
	 * testPlan2.setTestPlan(testConfig); } } catch (FileNotFoundException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); } catch
	 * (YamlException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * try { YamlReader reader3 = new YamlReader( new
	 * FileReader("resources/testConfigs/projects.yaml.subset12.template")); //
	 * YamlReader reader2 = new YamlReader(new //
	 * FileReader("resources/testConfigs/projects.yaml.template"));
	 * 
	 * @SuppressWarnings("rawtypes") Map testConfig = (Map) reader3.read(); if
	 * (testConfig != null) {
	 * 
	 * testPlan3 = initTestPlan(testConfig.get("type").toString(), testConfig);
	 * testPlan3.setTestPlan(testConfig); } } catch (FileNotFoundException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); } catch
	 * (YamlException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * }
	 */

	public static void readFile(String fileName) {
		try {
			YamlReader reader1 = new YamlReader(new FileReader(fileName));
			@SuppressWarnings("rawtypes")
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

	@Test(priority = 1)
	public static void verifyWinowTitle() throws YamlException, InterruptedException {

		
	    
		LOGGER.info(
				"---------------------------------The test case verifyWinowTitle is running-------------------------");
		//readFile("resources/testConfigs/projects.yaml.onlysubset1.template");
		readFile(configProperties.getProperty("verify.window.title"));
		testPlan.checkWinodwTitle(reporter);
		LOGGER.info(
				"---------------------------------The test case verifyWinowTitle is Finshed-------------------------");

	}

	@Test(priority = 2)

	public static void verifySummaryStats() throws YamlException, InterruptedException {

		LOGGER.info(
				"-------------------------------The test case verifySummaryStats is running-------------------------");
		
		readFile("resources/testConfigs/projects.yaml.onlysubset1.template");
		testPlan.doPlan(reporter);
		LOGGER.info(
				"--------------------------------The test case verifySummaryStats is Finshed-------------------------");

	}
//@Test(priority = 3)

	public static void verifySummaryStatsSubset2() throws YamlException, InterruptedException {

		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSubset2 is running-------------------------");
		readFile("resources/testConfigs/projects.yaml.onlysubset2.template");
		testPlan.doPlanSubset2(reporter);

		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSubset2 is Finshed-------------------------");

	}

//	@Test(priority = 4)

	public static void verifySummaryStatsSetValue() throws YamlException, InterruptedException {

		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSetValue is running-------------------------");
		readFile("resources/testConfigs/projects.yaml.subset1.setvalue.template");
		testPlan.doPlanSetValue(reporter);
		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSetValue is Finshed-------------------------");

	}

	//@Test(priority = 5)

	public static void verifySummaryStatsSubsetOneTwo() throws YamlException, InterruptedException {

		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSubset3 is running-------------------------");
		readFile("resources/testConfigs/projects.yaml.subset12.template");
		testPlan.doPlanSubset3(reporter);
		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSubset3 is Finshed-------------------------");

	}

	@AfterClass
	public void closeApplication() {

 		reporter.doReport();
		testPlan.closeDriver();
		System.out.println(
				"==================================Test Ends : Browser session closed =====================================");

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

	// @Test (priority=1)

	public static void testLogin() throws YamlException, InterruptedException {

		LOGGER.info("---------------------------The test case testLogin is running-------------------------");

		Testplan testPlan = null;
		Reporter reporter = null;
		try {
			YamlReader reader = new YamlReader(new FileReader("resources/testConfigs/projects.yaml.template"));
			Map testConfig = (Map) reader.read();
			while (testConfig != null) {
				testPlan = initTestPlan(testConfig.get("type").toString(), testConfig);
				reporter = initReporter(testConfig.get("reporter").toString());
				testPlan.checkWinodwTitle(reporter);
				testConfig = (Map) reader.read();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			LOGGER.info(
					"********************************* File Not Found Exception***************************************");
			e.printStackTrace();

		} catch (YAMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// return testplan;
		}
	}

	// @Test

	public void testExpandCollapse() throws YamlException, InterruptedException {

		{

			LOGGER.info(
					"---------------------------The test case verify Expand and collpasing of concept teeeis running-------------------------");

			Testplan testPlan = null;
			Reporter reporter = null;

			try {
				YamlReader reader = new YamlReader(new FileReader("resources/testConfigs/projects.yaml.template"));
				@SuppressWarnings("rawtypes")
				Map testConfig = (Map) reader.read();
				while (testConfig != null) {

					testPlan = initTestPlan(testConfig.get("type").toString(), testConfig);
					reporter = initReporter(testConfig.get("reporter").toString());
					testPlan.verifyExpandCollpase(reporter);
					System.out.println("Test Collapse");

				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				LOGGER.info(
						"********************************* File Not Found Exception***************************************");
				e.printStackTrace();

			} catch (YAMLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				// return testplan;
			}
		}
	}

}
