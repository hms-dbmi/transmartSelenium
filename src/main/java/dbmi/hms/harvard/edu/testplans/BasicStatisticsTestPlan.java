package dbmi.hms.harvard.edu.testplans;

import static org.testng.Assert.assertTrue;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

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
	
	public void doPlan(Reporter reporter) throws InterruptedException{
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
		    
		    //Checking the title of loaded window after login
		    
		    String winodwTitle = driver.getTitle();
		    System.out.println(winodwTitle);
		    assertTrue(winodwTitle.contains("Dataset Explorer"));
		
		    System.out.println("*************************************************");
		 
		    //Check if text present on the home page to confirm page is loaded
		    
		    WebElement bodyText = driver.findElement(By.xpath(".//*[@id='resultsTabPanel__queryPanel']"));
		    String DatasetString =bodyText.getText();
		    System.out.println(DatasetString);
		    Assert.assertEquals("Comparison", DatasetString, "Home page is loaded");
		    
		    //Test Expanding/collapsing the concept tree
		    
		  //  WebElement conceptTreeDemo = driver.findElement(By.xpath(".//*[@id='extdd-3']/img[1]"));
		    String ExamCollapse="blood"; 
		    WebElement conceptTreeDemo = driver.findElement(By.xpath(".//*[@id='extdd-4']/img[1]"));
		    String conceptTreeDemoValue =conceptTreeDemo.getText();
		    System.out.println(conceptTreeDemoValue);
		    try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    conceptTreeDemo.click();
		    
		    //Check the value of expanded concept Tree
		    WebElement examination=driver.findElement(By.xpath(".//*[@id='extdd-15']"));
		    String examcollpaseText =examination.getText();
		    System.out.println(examcollpaseText);
		  //  Assert.assertEquals("blood pressure (37971)", examcollpaseText, "Concept tree gets expanded");
		      assertTrue(examcollpaseText.contains(ExamCollapse));
		    //assertThat("FooBarBaz", matchesPattern("^Foo"));
		    
		      
		    // Generate summary statstics  with No subsets
		      driver.findElement(By.xpath(".//*[@id='ext-gen49']")).click();
		      Thread.sleep(3000);
		      WebElement noSubset=driver.findElement(By.xpath(".//*[@class='ext-mb-text']"));
		      System.out.println(noSubset.toString());  
		      
		    //  driver.findElement(By.xpath("//button[[@type, 'submit'] and [text()='New']]")).click();
		      
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
