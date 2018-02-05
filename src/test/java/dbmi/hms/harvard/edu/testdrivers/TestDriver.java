package dbmi.hms.harvard.edu.testdrivers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.logging.Logger;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.yaml.snakeyaml.error.YAMLException;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import dbmi.hms.harvard.edu.reporter.Reporter;
import dbmi.hms.harvard.edu.testplans.Testplan;

public class TestDriver {

	public static final String TESTPLANS = "dbmi.hms.harvard.edu.testplans.";
	public static final String REPORTS = "dbmi.hms.harvard.edu.reporter.";
	private static final Logger LOGGER = Logger.getLogger(TestDriver.class.getName());

	// public static void main(String[] args) throws YamlException {
	// @Atul
	public static Testplan testPlan = null;
	static Reporter reporter = null;

	public TestDriver() {
		try {
			YamlReader reader = new YamlReader(new FileReader("resources/testConfigs/projects.yaml.template"));

			@SuppressWarnings("rawtypes")
			Map testConfig = (Map) reader.read();
			if (testConfig != null) {

				testPlan = initTestPlan(testConfig.get("type").toString(), testConfig);
				reporter = initReporter(testConfig.get("reporter").toString());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (YamlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(priority=1)
	public static void verifyWinowTitle() throws YamlException, InterruptedException {

		LOGGER.info("---------------------------The test case verifyWinowTitle is running-------------------------");
		testPlan.checkWinodwTitle(reporter);
		LOGGER.info("---------------------------The test case verifyWinowTitle is Finshed-------------------------");

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

	@Test(priority = 2)

	public static void	 verifySummaryStats() throws YamlException, InterruptedException {

		testPlan.doPlan(reporter);

	}

	@SuppressWarnings("finally")
	private static Testplan initTestPlan(String testType, @SuppressWarnings("rawtypes") Map map) {
		Testplan newInstance = null;
		try {
			Class<?> resourceInterfaceClass = Class.forName(TESTPLANS + testType);

			newInstance = (Testplan) resourceInterfaceClass.newInstance();
			newInstance.setTestPlan(map);
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
