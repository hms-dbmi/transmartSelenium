	package dbmi.hms.harvard.edu.examples;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

/**
 * 
 * 	@author Tom DeSain
 *	 This is a sample example of how to use selenium webdriver to
 *	drive a firefox driver
 *
 * 
 */


public class SimpleExample1 {

	@Test
	public void testngtest(){
//	public static void main(String[] args) {
		final Logger LOGGER = Logger.getLogger( SimpleExample.class.getName() );
		System.setProperty("webdriver.firefox.marionette","/Users/tom/Documents/workspace-ggts-3.6.4.RELEASE/transmartQA/drivers/geckodriver");

		// New instance of firefox driver
		WebDriver driver = new FirefoxDriver();
		// go to google
		driver.get("http://www.google.com");
		
		// Find the text input element by its name
		WebElement element = driver.findElement(By.name("q"));
		
		// search for something
		element.sendKeys("Cheese!");
		
		element.submit();
		
		System.out.println("Page title is: " + driver.getTitle());
		
		( new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>(){
			public Boolean apply(WebDriver d){
				return d.getTitle().toLowerCase().startsWith("cheese!");
			}
		});
		
		System.out.println("Page title is: " + driver.getTitle());
		LOGGER.info("Page title is: " + driver.getTitle());
		// Close the Browser
		driver.quit();
	}
	}

