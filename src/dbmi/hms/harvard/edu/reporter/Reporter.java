package dbmi.hms.harvard.edu.reporter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dbmi.hms.harvard.edu.testplans.Testplan;

public abstract class Reporter {
	private List<Map> testResults = new ArrayList<Map>();
	
	public void doReport(){};
	
	public Map appendTestResults(Map testPlan, String result){
		testPlan.put("TestResult", result);
		testResults.add(testPlan);
		return testPlan;
	}

	public List<Map> getTestResults() {
		return testResults;
	}

	public void setTestResults(List<Map> testResults) {
		this.testResults = testResults;
	};
	
}
