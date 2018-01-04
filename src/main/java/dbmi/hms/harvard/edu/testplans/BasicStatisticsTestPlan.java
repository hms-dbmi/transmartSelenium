package dbmi.hms.harvard.edu.testplans;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import dbmi.hms.harvard.edu.authentication.AuthTypes;
import dbmi.hms.harvard.edu.reporter.Reporter;
import dbmi.hms.harvard.edu.testplans.results.SummaryStatisticsResults;
import dbmi.hms.harvard.edu.transmartModules.DatasetExplorer;
import dbmi.hms.harvard.edu.transmartModules.SummaryStatistics;

public class BasicStatisticsTestPlan extends Testplan{
	private static final int TIMEOUT = 30;
	private static final String BROWSER = "webdriver.firefox.marionette";
	private static final String BROWSERDRIVER = "/Users/tom/Documents/workspace-ggts-3.6.4.RELEASE/transmartQA/drivers/geckodriver";
	
	
	private Set<String> subset1;
	private Set<String> subset2;
	private Set<String> relational;


	private static WebDriver driver;
	
	public BasicStatisticsTestPlan() {
	}	
	
	public Set<String> getSubset1() {
		return subset1;
	}
	public void setSubset1(Set<String> subset1) {
		this.subset1 = subset1;
	}
	public Set<String> getSubset2() {
		return subset2;
	}
	public void setSubset2(Set<String> subset2) {
		this.subset2 = subset2;
	}
	public Set<String> getRelational() {
		return relational;
	}
	public void setRelational(Set<String> relational) {
		this.relational = relational;
	}

	public void doPlan(){
    	try {
			
			System.setProperty(BROWSER,BROWSERDRIVER);
    		//System.setProperty("webdriver.chrome.driver","D://chromedriver.exe" );
    		//WebDriver driver=new ChromeDriver();
    		
    		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    		driver.manage().window().maximize();
    		//driver.get("http://timesofindia.indiatimes.com/");
    		
		    //driver = new FirefoxDriver();
    		//WebDriver driver = new SafariDriver();
    		
    	//	driver.get("http://store.demoqa.com");
    		
		    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		    
		    driver.get(testPlan.get("url").toString());
		    
		    AuthTypes authTypes = new AuthTypes();
		    
		    // Set authentication type
			String authLink;
		    
		    if(authTypes.getAuthTypes().containsKey(testPlan.get("authmethod").toString())){
		    	authLink = authTypes.getAuthTypes().get(testPlan.get("authmethod").toString());
		    } else {
		    	authLink = null;
		    }    

		    driver.findElement(By.linkText(authLink)).click();
		    
		    authTypes.doAuth(driver,testPlan);

		    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		    
		    for(String path: java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))){
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
	    		String subset = "subset1";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);				
		    }
			
		    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		    
			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			    
			SummaryStatisticsResults.class.newInstance().doResults(driver,testPlan);
			
		    //driver.close();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void doPlan(Reporter reporter){
    	try {
			
			System.setProperty(BROWSER,BROWSERDRIVER);
	
		    driver = new FirefoxDriver();
		    
    		//System.setProperty("webdriver.chrome.driver","D://chromedriver.exe" );
    		//WebDriver driver=new ChromeDriver();
    		
    		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    		driver.manage().window().maximize();
    		
		    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		    
		    driver.get(testPlan.get("url").toString());
		    
		    AuthTypes authTypes = new AuthTypes();
		    
		    // Set authentication type
			String authLink;
		    
		    if(authTypes.getAuthTypes().containsKey(testPlan.get("authmethod").toString())){
		    	authLink = authTypes.getAuthTypes().get(testPlan.get("authmethod").toString());
		    } else {
		    	authLink = null;
		    }    

		    //driver.findElement(By.linkText(authLink)).click();
		    
		    authTypes.doAuth(driver,testPlan);

		    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		    
		    for(String path: java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))){
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
	    		String subset = "subset1";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);				
		    }
			
		    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		    
			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			
			SummaryStatisticsResults.class.newInstance().doResults(driver,testPlan,reporter);

			reporter.doReport();
						
		    driver.close();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
