package dbmi.hms.harvard.edu.examples;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class TransmartExample1 {
	  private static WebDriver driver;
	  private static String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();
	  private static final String USERNAME = "td85";
	  private static final String PASSWORD = "Maynard12!";



	  
	  public static void main(String[] args) {
		System.setProperty("webdriver.firefox.marionette","/Users/tom/Documents/workspace-ggts-3.6.4.RELEASE/transmartQA/drivers/geckodriver");

	    driver = new FirefoxDriver();
	  // baseUrl = "https://grdr-dev.hms.harvard.edu/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    //driver.get(baseUrl + "/transmart/login/auth");
	    driver.findElement(By.linkText("Harvard Medical School")).click();
	    

	    driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_UsernameTextBox']")).sendKeys(USERNAME);
	    
	    driver.findElement(By.xpath("//*[@id='ctl00_ContentPlaceHolder1_PasswordTextBox']")).sendKeys(PASSWORD);
	    driver.findElement(By.xpath("//*[@id='ctl00_ContentPlaceHolder1_SubmitButton']")).click();
	    //doubleClick(driver.findElement(By.linkText("Clinical Registry Investigating Bardet-Biedl Syndrome")));
	    
	    explorerNavigate("\\Clinical Registry Investigating Bardet-Biedl Syndrome\\1 Socio-demographic data\\Ancestors origin\\What Is The Country Of Your Ancestors Origin?\\");
	    // Perform a drag and drop
	    WebElement source = driver.findElement(By.partialLinkText("1 Socio-demographic data"));
	    
	    WebElement target = driver.findElement(By.xpath(".//*[@id='queryCriteriaDiv1_1']"));
		   
	    dragDrop(source, target);	
	    
	    // Execute Basic Statistics
	    driver.findElement(By.xpath("//*[@id='ext-gen48']")).click();
	    

	  }
	  
	  public static void explorerNavigate(String node){
		  for(String currNode: node.split("\\\\")){
			  if(!currNode.isEmpty() ) {
				  System.out.println("node: " + currNode);
				  doubleClick(driver.findElement(By.partialLinkText(currNode)));
			  }
		  }
	  }
	  
	  public static void doubleClick(WebElement element){
		  Actions action = new Actions(driver);
		  action.doubleClick(element).perform();
	  }
	  
	  public static void dragDrop(WebElement source,WebElement target){
		  Actions action = new Actions(driver);
		  action.dragAndDrop(source, target).perform();
	  }

	}

