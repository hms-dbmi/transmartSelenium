package dbmi.hms.harvard.edu.reporter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleReporter extends Reporter{
	public void doReport(){
		doDelimitedReportToConsole();
	}
	
	public void doDelimitedReportToConsole(){
		// Summary of results
		Map<String, Integer> summaryResults = doCalcResults(getTestResults());
		
		System.out.println("Total Passed: " + summaryResults.get("successes") + "\t\t" + "Total Failed: " + summaryResults.get("failures"));
		System.out.println("Test Results:");
		for(Map testResult:getTestResults()){
			Object[] args = new Object[] {testResult.get("name").toString() + ":",testResult.get("TestResult").toString()};
			System.out.printf ("%-20s%-20s\n",args);
		}
	}
	
	private Map<String, Integer> doCalcResults(List<Map> testResults){
		Map<String, Integer> results = new HashMap<String, Integer>();
		int successes = 0;
		int failures = 0;
		
		for(Map testResult : testResults){
			String result = testResult.get("TestResult").toString();
			switch(result){
				case "passed" :successes++;
								break;
				case "failed" :failures++;
							break;
			}
		}		
		
		results.put("successes", successes);
		results.put("failures", failures);
		
		return results;
	}
}
