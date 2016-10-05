package dbmi.hms.harvard.edu.testplans;

import java.util.Map;

import dbmi.hms.harvard.edu.testplans.results.SuccessTypes;

public abstract class CopyOfTestplan {
	protected String name;
	protected String url;
	protected String authMethod;
	protected String username;
	protected String password;
	protected String type;
	protected String successType;
	protected String successVal;	
	
	public CopyOfTestplan(Map map){};

	public CopyOfTestplan() {}

	public void setTestPlan(Map map){}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getAuthMethod() {
		return authMethod;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getType() {
		return type;
	}

	public String getSuccessType() {
		return successType;
	}

	public String getSuccessVal() {
		return successVal;
	}

	public void doPlan(){};

	public void validateTestPlan() throws Exception{
		if(name == null){
			throw new Exception("Invalid Test Plan - Name field is null");
		} else if (url == null){
			throw new Exception("Invalid Test Plan - url field is null");
		} else if (authMethod == null){
			throw new Exception("Invalid Test Plan - authMethod field is null");			
		} else if (successType != null && successVal == null){
			throw new Exception("Invalid Test Plan - if success is expected a success value must be given");			
		} else if (validateSuccessType(successType) != true){
			throw new Exception("Invalid Test Plan - Success Type: " + successType + "is not valid");
		};
	}
	
	protected Boolean validateSuccessType(String success){
		for(SuccessTypes successType : SuccessTypes.values()){
			if(successType.equals(success)){
				return true;
			}
		}
		return false;
	}
}
