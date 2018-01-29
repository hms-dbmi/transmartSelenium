package dbmi.hms.harvard.edu.testplans;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.Test;



public class TestNG1 {

	@Test
	
	public void testsafari()
	{
		//System.setProperty ("webdriver.safari.driver",".\\resource\\chromedriver.exe");
		//System.setProperty ("webdriver.safari.driver",".\\resources\\SafariDriver.safariextz");
		//WebDriver driver=new SafariDriver();
	 	System.setProperty("webdriver.chrome.driver", ".\\resources\\chromedriver.exe");
		//System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");	
        WebDriver driver = new ChromeDriver();
        //driver.get("http://timesofindia.indiatimes.com/");
		driver.get("https://nhanes.hms.harvard.edu/transmart/datasetExplorer/index");
	    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	//    String element = driver.getTitle();
	  //  System.out.println(element);
		
		
	}}
