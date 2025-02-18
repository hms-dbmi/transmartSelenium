package dbmi.hms.harvard.edu.authentication;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import dbmi.hms.harvard.edu.testplans.Testplan;

public class AuthTypes {
	Map<String, String> authTypes = new HashMap<String,String>()
			{{
				put("HMS", "Harvard Medical School");
				
			}};
	
	public void doAuth(WebDriver driver, Map testPlan)	{
		switch(testPlan.get("authmethod").toString()){
			case "HMS": doHMSAuth(driver, testPlan); 
						break;
		}
	}
			
	public void doHMSAuth(WebDriver driver, Map testPlan){
	    try {
	    	String usernamebox = ".//*[@id='ctl00_ContentPlaceHolder1_UsernameTextBox']";
	    	String passwordbox = "//*[@id='ctl00_ContentPlaceHolder1_PasswordTextBox']";
	    	String submitbutton = "//*[@id='ctl00_ContentPlaceHolder1_SubmitButton']";
	    	
			driver.findElement(By.xpath(usernamebox)).sendKeys(testPlan.get("username").toString());
			driver.findElement(By.xpath(passwordbox)).sendKeys(testPlan.get("password").toString());
			driver.findElement(By.xpath(submitbutton)).click();
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    //driver.findElement(By.xpath("//*[@id='ctl00_ContentPlaceHolder1_PasswordTextBox']")).sendKeys(PASSWORD);
	}
			
	public Map<String, String> getAuthTypes() {
		return authTypes;
	}

	public void setAuthTypes(Map<String, String> authTypes) {
		this.authTypes = authTypes;
	}
			
			
}
