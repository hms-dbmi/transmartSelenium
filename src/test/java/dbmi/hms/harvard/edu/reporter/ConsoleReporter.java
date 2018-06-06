package dbmi.hms.harvard.edu.reporter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class ConsoleReporter extends Reporter {
	int successes = 0;
	int failures = 0;
	private static final Logger LOGGER = Logger.getLogger(ConsoleReporter.class.getName());

	public void doReport() {
		doDelimitedReportToConsole();
	}

	public void doDelimitedReportToConsole() {
		// Summary of results
		Map<String, Integer> summaryResults = doCalcResults(getTestResults());
		LOGGER.info("\n");
		LOGGER.info(
				"=================================================Test Result Summary===========================================");
		LOGGER.info("Total Passed: " + summaryResults.get("successes") + "\t\t" + "Total Failed: "
				+ summaryResults.get("failures"));
		LOGGER.info("\n");
		LOGGER.info(
				"--------------------------------------------------Test Results---------------------------------------------");
		for (Map testResult : getTestResults()) {
			String sTestPlanName = testResult.get("name").toString();
			String sTestPlanResult = testResult.get("TestResult").toString();
			// Object[] args = new Object[] {testResult.get("name").toString() +
			// ":",testResult.get("TestResult").toString()};
			LOGGER.info("TestCaseName:          " + sTestPlanName + "......................"
					+ "Test case Result:            	" + sTestPlanResult);
			LOGGER.info("\n");

			// sTestPlanResult);
			// System.out.printf ("%-20s : %-20s\n", sTestPlanName,
			// sTestPlanResult );
		}
	}

	private Map<String, Integer> doCalcResults(List<Map> testResults) {
		Map<String, Integer> results = new HashMap<String, Integer>();

		for (Map testResult : testResults) {
			String result = testResult.get("TestResult").toString();
			switch (result) {
			case "passed":
				successes++;
				break;
			case "failed":
				failures++;
				break;
			}
		}

		results.put("successes", successes);
		results.put("failures", failures);

		return results;
	}
}
