package dbmi.hms.harvard.edu.testplans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dbmi.hms.harvard.edu.reporter.Reporter;
import dbmi.hms.harvard.edu.testplans.results.SuccessTypes;

public abstract class Testplan{
	public static final List<String> REQUIRED_FIELDS = new ArrayList<String>()
		{{
			add("name");
			add("url");
			add("authmethod");
		}};
	
	public static Map testPlan;
	
	public void setTestPlan(Map testPlan){
		try {
			validateTestPlan(testPlan);
		} catch (Exception e) {
			this.testPlan = testPlan;
			e.printStackTrace();
		} finally {
			this.testPlan = testPlan;
		}
	}
	
	public void doPlan(){};

	public void closeDriver() {}

	public void doPlan(Reporter reporter) throws InterruptedException{};
	
	public void doPlanSubset2(Reporter reporter) throws InterruptedException{};
	
	public void doPlanSubset3(Reporter reporter) throws InterruptedException{};
	
	public void checkWinodwTitle(Reporter reporter) throws InterruptedException{};

	public void verifyExpandCollpase(Reporter reporter) throws InterruptedException{};
	
	public void doPlanSetValue(Reporter reporter) throws InterruptedException {	};

	public void verifyClear(Reporter reporter) throws Exception {};
	
	public void doPlanMultipleSubset(Reporter reporter) throws InterruptedException, InstantiationException, IllegalAccessException{};



	public void validateTestPlan(Map testPlan) throws Exception{
		for(String reqField : REQUIRED_FIELDS){
			if(!testPlan.containsKey(reqField)) throw new Exception("Required field " + reqField + " is missing!");
		}
		if(testPlan.get("name") == null){
			throw new Exception("Invalid Test Plan - Name field is null");
		} else if (testPlan.get("url") == null){
			throw new Exception("Invalid Test Plan - url field is null");
		} else if (testPlan.get("authmethod") == null){
			throw new Exception("Invalid Test Plan - authMethod field is null");			
		} else if (testPlan.get("successtype") != null && testPlan.get("successval") == null){
			throw new Exception("Invalid Test Plan - if success is expected a success value must be given");			
		} else if (validateSuccessType(testPlan.get("success").toString()) != true){
			throw new Exception("Invalid Test Plan - Success Type: " + testPlan.get("success") + " is not valid");
		};
	}
	
	protected Boolean validateSuccessType(String success){
		for(SuccessTypes successType : SuccessTypes.values()){
			if(successType.toString().equals(success)){
				return true;
			}
		}
		return false;
	}

	public void doPlanMultipleSubsetAnd(Reporter reporter) throws InterruptedException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		
	}

	public void verifyExcludeFeature(Reporter reporter) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	public void verifyGraphs(Reporter reporter) throws  Exception {
		// TODO Auto-generated method stub
		
	}


	


	
}
