package dbmi.hms.harvard.edu.testplans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dbmi.hms.harvard.edu.reporter.Reporter;
import dbmi.hms.harvard.edu.testplans.results.SuccessTypes;

public abstract class Testplan {
	public static final List<String> REQUIRED_FIELDS = new ArrayList<String>() {
		{
			add("name");
			add("url");
			add("authmethod");
		}
	};

	public static Map testPlan;

	public void setTestPlan(Map testPlan) {
		try {
			validateTestPlan(testPlan);
		} catch (Exception e) {
			this.testPlan = testPlan;
			e.printStackTrace();
		} finally {
			this.testPlan = testPlan;
		}
	}

	public void doPlan() throws InterruptedException {
	};

	public void closeDriver() {
	}

	public void doPlan(Reporter reporter) throws InterruptedException {
	};

	public void doPlanSubset2(Reporter reporter) throws InterruptedException {
	};

	public void doPlanSubsetOneTwo(Reporter reporter) throws InterruptedException {
	};

	public void checkWinodwTitle(Reporter reporter) throws InterruptedException {
	};

	public void verifyExpandCollpase(Reporter reporter) throws InterruptedException {
	};

	public void doPlanSetValueEqual(Reporter reporter) throws InterruptedException {
	};

	public void verifyClear(Reporter reporter) throws Exception {
	};

	public void doPlanMultipleSubset1OR(Reporter reporter)
			throws InterruptedException, InstantiationException, IllegalAccessException {
	};

	public void doPlanMultipleSubsetAnd(Reporter reporter)
			throws InterruptedException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub

	}

	public void verifyExcludeFeature(Reporter reporter) throws InterruptedException {
		// TODO Auto-generated method stub

	}

	public void verifyGraphs(Reporter reporter) throws Exception {
		// TODO Auto-generated method stub

	}

	public void doSearchSpecialChar(Reporter reporter) throws InterruptedException, Exception {
		// TODO Auto-generated method stub

	}

	public void doSearchCaseSensitivity(Reporter reporter) throws InterruptedException, Exception {
		// TODO Auto-generated method stub

	}

	public void doSearchLength(Reporter reporter) throws InterruptedException, Exception {
		// TODO Auto-generated method stub

	}

	public void loginSite() throws InterruptedException {
		// TODO Auto-generated method stub

	}

	public void verifyMultipleSubset1and2OR(Reporter reporter)
			throws InterruptedException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub

	}

	public void verifySummaryStatsLab(Reporter reporter) throws Exception {
		// TODO Auto-generated method stub

	}

	public void verifySumStasQue(Reporter reporter) throws InterruptedException {
		// TODO Auto-generated method stub

	}

	public void doSearch(Reporter reporter) throws InterruptedException, Exception {
		// TODO Auto-generated method stub

	}

	public void doPlanSummaryStatSearch(Reporter reporter) throws InterruptedException {
		// TODO Auto-generated method stub

	}

	public void verifyDelete(Reporter reporter) throws InterruptedException {
		// TODO Auto-generated method stub

	}

	public void doTooltip(Reporter reporter) {
		// TODO Auto-generated method stub

	}

	public void docheckSubsetBoxAutoIncrement(Reporter reporter) {
		// TODO Auto-generated method stub
		
	}

	public void docheckTooltipResultGraph(Reporter reporter) {
		// TODO Auto-generated method stub
		
	}

	public void validateTestPlan(Map testPlan) throws Exception {
		for (String reqField : REQUIRED_FIELDS) {
			if (!testPlan.containsKey(reqField))
				throw new Exception("Required field " + reqField + " is missing!");
		}
		if (testPlan.get("name") == null) {
			throw new Exception("Invalid Test Plan - Name field is null");
		} else if (testPlan.get("url") == null) {
			throw new Exception("Invalid Test Plan - url field is null");
		} else if (testPlan.get("authmethod") == null) {
			throw new Exception("Invalid Test Plan - authMethod field is null");
		} else if (testPlan.get("successtype") != null && testPlan.get("successval") == null) {
			throw new Exception("Invalid Test Plan - if success is expected a success value must be given");
		} else if (validateSuccessType(testPlan.get("success").toString()) != true) {
			throw new Exception("Invalid Test Plan - Success Type: " + testPlan.get("success") + " is not valid");
		}
		;
	}

	protected Boolean validateSuccessType(String success) {
		for (SuccessTypes successType : SuccessTypes.values()) {
			if (successType.toString().equals(success)) {
				return true;
			}
		}
		return false;
	}

	public void docheckShowNode(Reporter reporter) {
		// TODO Auto-generated method stub
		
	}

	public void docheckExternalLink(Reporter reporter) {
		// TODO Auto-generated method stub
		
	}

	public void docheckenableDisableVarPanel(Reporter reporter) {
		// TODO Auto-generated method stub
		
	}

	public void verifyMessageExclude(Reporter reporter) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	public void doPlanMultipleSubset1Subset2And(Reporter reporter) throws InterruptedException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		
	}

	public void doPlanSetValueLessThan(Reporter reporter) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	public void doPlanSetValueLessThanEqual(Reporter reporter) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	public void doPlanSetValueBetween(Reporter reporter) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}



	public void doPlanSetValueGreaterThan(Reporter reporter) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	public void doPlanSetValueGreaterThanEqual(Reporter reporter) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	public void doPlanSetNoValue(Reporter reporter) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	public void doPlanSetLowFlagRange(Reporter reporter) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	public void doPlanSaveSubset(Reporter reporter) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}


	/**
	 * @param reporter
	 * @throws Exception 
	 */

	/**
	 * @param reporter
	 * @throws Exception 
	 */
	
	/**
	 * @param reporter
	 * @throws Exception 
	 */
	public void verifyFractlisIntergrationPrincipleComponentAnalysis(Reporter reporter) throws Exception {
		// TODO Auto-generated method stub
		
	}

	

	public void verifyFractalisSurvivalPlot(Reporter reporter) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void verifyFractlisIntergrationHistogram(Reporter reporter) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void verifyFractlisIntergrationScatterPlotCorrelationPeasron(Reporter reporter) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void verifyFractlisIntergrationScatterPlotCorrelationSpearman(Reporter reporter) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void verifyFractlisIntergrationScatterPlotCorrelationKendall(Reporter reporter) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void verifyFractlisIntergrationBoxPlotAnalysisIdentity(Reporter reporter) throws Exception {
		// TODO Auto-generated method stub
		
	}


	

	
	
}
