package dbmi.hms.harvard.edu.testplans.results;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dbmi.hms.harvard.edu.reporter.Reporter;
import dbmi.hms.harvard.edu.testplans.Testplan;

public class SummaryStatisticsResults extends Results {
	//private String patientCountSubset1 = ".//*[@class='analysis']/table[1]/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[2]/td[1]";
	public String patientCountSubset1 = ".//*[@id='ext-gen157']/div/table/tbody/tr[4]/td/table/tbody/tr/td/table/tbody/tr[2]/td[1]";
	private String patientCountSubset2 = ".//*[@class='analysis']/table[1]/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[2]/td[3]";
	
	
	/**
	 *  Simple console output
	 */
	public void doResultsCheck(WebDriver driver, String successType, String successVal){
		
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Subject Totals')]")));
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Subject Totals')]")));
		switch(successType){
			case "patientcountsubset1": doVerifyPatientCountSubset1(driver, successVal);
										break;
			case "patientcountsubset2": doVerifyPatientCountSubset2(driver, successVal);
										break;
		}
	}

	private void doVerifyPatientCountSubset1(WebDriver driver,  String successVal){
		String resultBox = driver.findElement(By.xpath(patientCountSubset1)).getText();

		if(resultBox.equals(successVal)){
			System.out.println("Success!");
		} else {
			System.out.println("Fail");
		};
	}
	
	private void doVerifyPatientCountSubset2(WebDriver driver,  String successVal){
		String resultBox = driver.findElement(By.xpath(patientCountSubset2)).getText();

		if(resultBox.equals(successVal)){
			System.out.println("Success!");
		} else {
			System.out.println("Fail");
		};
	}	

	/**
	 *  Use reporting object to output to console
	 */
	public void doResultsCheck(WebDriver driver, Map testPlan, Reporter reporter) {
		String successType = testPlan.get("success").toString();
		System.out.println("test");
		//WebDriverWait wait = new WebDriverWait(driver, 0);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Subject Totals')]")));
	//	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Subject Totals')]")));
		switch(successType){
			case "patientcountsubset1": doVerifyPatientCountSubset1(driver, testPlan, reporter);
										break;
			case "patientcountsubset2": doVerifyPatientCountSubset2(driver, testPlan, reporter);
										break;
		}
	}	
	public void doFirstResultCheck(WebDriver driver, Map testPlan, Reporter reporter) {
		String successType = testPlan.get("success").toString();
		System.out.println("test");
		//WebDriverWait wait = new WebDriverWait(driver, 0);
		reporter.appendTestResults(testPlan, "passed");
	}	

	private void doVerifyPatientCountSubset1(WebDriver driver, Map testPlan, Reporter reporter){
		String successVal = testPlan.get("successvalue").toString();
		String resultBox = driver.findElement(By.xpath(patientCountSubset1)).getText();

		if(resultBox.equals(successVal)){
			reporter.appendTestResults(testPlan, "passed");
		} else {
			reporter.appendTestResults(testPlan, "failed");
		};
	}
	
	private void doVerifyPatientCountSubset2(WebDriver driver, Map testPlan, Reporter reporter){
		String successVal = testPlan.get("successvalue").toString();
		String resultBox = driver.findElement(By.xpath(patientCountSubset2)).getText();

		if(resultBox.equals(successVal)){
			if(testPlan.containsKey("expected")){
				if(testPlan.get("expected").toString().equalsIgnoreCase("pass")){
					reporter.appendTestResults(testPlan, "passed");
				} else {
					reporter.appendTestResults(testPlan, "failed");
				}
			} else {
				reporter.appendTestResults(testPlan, "passed");
			}
		} else {
			if(testPlan.containsKey("expected")){
				if(testPlan.get("expected").toString().equalsIgnoreCase("fail")){
					reporter.appendTestResults(testPlan, "passed");
				} else {
					reporter.appendTestResults(testPlan, "failed");
				}
			} else {
				reporter.appendTestResults(testPlan, "failed");
			}
		};
	}
	
	/**
	 *  to-do use reporting object to output to log4j
	 */	
	
	/**
	 *  to-do use reporting object to output to html report?
	 */		
}
