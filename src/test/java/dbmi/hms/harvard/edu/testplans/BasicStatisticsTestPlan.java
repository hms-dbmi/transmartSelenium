package dbmi.hms.harvard.edu.testplans;

import static org.testng.Assert.assertTrue;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import  static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.presentation.StandardRepresentation.STANDARD_REPRESENTATION;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.core.util.Lists.newArrayList;


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
		    		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		    		driver.manage().window().maximize();
		    		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				    driver.get(testPlan.get("url").toString());
				    
				    AuthTypes authTypes = new AuthTypes();
				    
				    // Set authentication type
					String authLink;
				    
				    if(authTypes.getAuthTypes().containsKey(testPlan.get("authmethod").toString())){
				    	authLink = authTypes.getAuthTypes().get(testPlan.get("authmethod").toString());
				    } else {
				    	authLink = null;
				    }    
		
//				    driver.findElement(By.linkText(authLink)).click();
				    
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
	
			
	public void checkWinodwTitle(Reporter reporter) throws InterruptedException{

		System.setProperty(BROWSER,BROWSERDRIVER);
		
	    driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    driver.get(testPlan.get("url").toString());
		AuthTypes authTypes = new AuthTypes();
	    // Set authentication type
		String authLink;
	    if(authTypes.getAuthTypes().containsKey(testPlan.get("authmethod").toString())){
	    	authLink = authTypes.getAuthTypes().get(testPlan.get("authmethod").toString());
	    } else {
	    	authLink = null;
	    }    

//		    driver.findElement(By.linkText(authLink)).click();
	    
	    authTypes.doAuth(driver,testPlan);
	    String winodwTitle = driver.getTitle();
	    System.out.println(winodwTitle);
	    assertThat(winodwTitle).contains("Dataset Explorer");
	 
	}			
			
	//Test Expanding/collapsing the concept tree

	public void verifyExpandCollpase(Reporter reporter)
	{
	   for(String path: java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))){
	       try 
	       {
	    	   System.out.println(subset1);
	    	   DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
	       } 
	       catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			   		
			String subset = "subset1";
    		try {
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
			} 
    		catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			} 
			catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	   }
	   
	   driver.close();
	}
	
//@Test
public void doPlan(Reporter reporter) throws InterruptedException{
		    	try {
		    		
		    		//		    driver.findElement(By.linkText(authLink)).click(); This is invalid step 
		
				   // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				    
				    //Checking the title of loaded window after login
				     
				    //Check if text present on the home page to confirm page is loaded
				    
/*				    WebElement bodyText = driver.findElement(By.xpath(".//*[@id='resultsTabPanel__queryPanel']"));
				    String DatasetString =bodyText.getText();
				    System.out.println(DatasetString);
				    Assert.assertEquals("Comparison", DatasetString, "Home page is loaded");
*/				    
				    
				    //Test Expanding/collapsing the concept tree
				    
				  //  WebElement conceptTreeDemo = driver.findElement(By.xpath(".//*[@id='extdd-3']/img[1]"));
				    /*String ExamCollapse="blood"; 
				    WebElement conceptTreeDemo = driver.findElement(By.xpath(".//*[@id='extdd-4']/img[1]"));
				    String conceptTreeDemoValue =conceptTreeDemo.getText();
				    //System.out.println("Examination     :" +conceptTreeDemoValue);
				    try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    conceptTreeDemo.click();
				    */
				    //Check the value of expanded concept Tree
				    /*WebElement examination=driver.findElement(By.xpath(".//*[@id='extdd-15']"));
				    String examcollpaseText =examination.getText();
				    System.out.println("Value after collapsing    "+examcollpaseText);
				  //  Assert.assertEquals("blood pressure (37971)", examcollpaseText, "Concept tree gets expanded");
				    //  assertTrue(examcollpaseText.contains(ExamCollapse));
				    //assertThat("FooBarBaz", matchesPattern("^Foo"));
				      System.out.println("---------------------------------------------------");
				      assertTrue(Pattern.compile(examcollpaseText).matcher("blood pressure").matches());
				      
				    */// Generate summary statstics  with No subsets
				 //     driver.findElement(By.xpath(".//*[@id='ext-gen49']")).click();
				   //   String MainWindow=driver.getWindowHandle();	
				     // System.out.println("Parent Window Title%%%%         :"+MainWindow);
				      // To handle all new opened window.				
			        
				    /*  Set<String> s1=driver.getWindowHandles();
			            System.out.println(s1.size());
				      
			            Iterator<String> i1=s1.iterator();		
		        		
			            while(i1.hasNext())			
			            {		
			                String ChildWindow=i1.next();		
			                		
			                if(!MainWindow.equalsIgnoreCase(ChildWindow))			
			                {    		
			                     
			                        // Switching to Child window
			                        driver.switchTo().window(ChildWindow);	                                                                                                           
			                        
			                 }		
			            }
				      */
				      //Thread.sleep(3000);
				      //WebElement Nosubset=driver.findElement(By.xpath(".//*[@id='ext-gen347']"));
				      //System.out.println("****************** No subset :"+Nosubset);
				      //driver.findElement(By.xpath(".//*[@id='ext-comp-1034']/tbody/tr/td[2]")).click();
				      
				     /* for (String winHandle : driver.getWindowHandles()) {
				    	    driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
				    	}
				      WebElement noSubset=driver.findElement(By.xpath(".//*[@class='ext-mb-text']"));
				    *//*
				      
				        
				      String childWinodwTitle = driver.getTitle();
				      System.out.println("childWinodwTitle is              "+childWinodwTitle);
				      
				      */
				    //  driver.findElement(By.xpath("//button[[@type, 'submit'] and [text()='New']]")).click();
				      
				   //Result/Analysis without selecting any subset
				    
			//	    driver.findElement(By.xpath(".//*[@id='ext-gen49']")).click();
				/*    driver.findElement(By.xpath(".//*[@id='resultsTabPanel__analysisPanel']/a[2]/em/span/span")).click();
				    WebElement NoSubsetResult=driver.findElement(By.xpath(".//*[@id='ext-gen157']/div/table/tbody/tr/td"));
				    String NoSubsetResultText=NoSubsetResult.getText(); 
				    //assertTrue(Pattern.compile(NoSubsetResultText).matches("Drag Concept", input));
				    assertThat(NoSubsetResultText).contains("Drag");*/
				    
		    		  //System.out.println("testing");
				       for(String path: java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))){
				       DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
						   		
						String subset = "subset1";
			    		
			    		
						DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
						DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);	
					
				    }
					
				    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				    SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
				    
					SummaryStatisticsResults.class.newInstance().doResults(driver,testPlan,reporter);
					//System.out.println("testing");
					reporter.doReport();
				    driver.close();
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
}
