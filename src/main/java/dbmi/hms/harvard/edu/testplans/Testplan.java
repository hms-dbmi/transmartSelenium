package dbmi.hms.harvard.edu.testplans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import dbmi.hms.harvard.edu.reporter.Reporter;
import dbmi.hms.harvard.edu.testplans.results.SuccessTypes;

public abstract class Testplan {
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

	public void doPlan(Reporter reporter){};
	
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
}
