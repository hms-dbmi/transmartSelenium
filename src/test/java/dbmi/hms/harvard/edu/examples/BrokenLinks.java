package dbmi.hms.harvard.edu.examples;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver.SystemProperty;
import org.testng.annotations.Test;

public class BrokenLinks {

	//private static final String BROWSER = "webdriver.firefox.marionette";
	//private static final String BROWSERDRIVER = "/Users/tom/Documents/workspace-ggts-3.6.4.RELEASE/transmartQA/drivers/geckodriver";
	//private static WebDriver driver;
    //private static WebDriver driver = null;

    @Test
    public static void testBrokenLinks() {
        // TODO Auto-generated method stub
        
        String homePage = "http://www.zlti.com";
        String url = "";
        HttpURLConnection huc = null;  
        int respCode = 200;
      
        System.setProperty("webdriver.chrome.driver","D://chromedriver.exe" );
    	//System.setProperty("webdriver.chrome.driversysy","D://chromedriver.exe" );
        WebDriver driver = new ChromeDriver();
        //System.out("***********");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        
        System.out.println("******************");
        driver.get("http://www.software-testing-tutorials-automation.com/2014/01/element-locators-in-selenium-2-or.html");
        
		//System.setProperty(BROWSER,BROWSERDRIVER);
        
		//driver.get(homePage);
        
        List<WebElement> links = driver.findElements(By.tagName("a"));
        
        Iterator<WebElement> it = links.iterator();
        
        while(it.hasNext()){
            
            url = it.next().getAttribute("href");
            
            System.out.println(url);
        
            if(url == null || url.isEmpty()){
            	System.out.println("URL is either not configured for anchor tag or it is empty");
                continue;
            }
            
            if(!url.startsWith(homePage)){
                System.out.println("URL belongs to another domain, skipping it.");
                continue;
            }
            
            try {
                huc = (HttpURLConnection)(new URL(url).openConnection());
                
                huc.setRequestMethod("HEAD");
                
                huc.connect();
                
                respCode = huc.getResponseCode();
                
                if(respCode >= 400){
                    System.out.println(url+" is a broken link");
                }
                else{
                    System.out.println(url+" is a valid link");
                }
                    
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        //driver.quit();

    }
}