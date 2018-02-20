package dbmi.hms.harvard.edu.transmartModules;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dbmi.hms.harvard.edu.testdrivers.TestDriver;

public abstract class Module {
	private static final Logger LOGGER = Logger.getLogger(Module.class.getName());	
	protected void enterText(WebDriver driver, String xpath, String text){
		driver.findElement(By.xpath(xpath)).sendKeys(text);
	}
	protected void click(WebDriver driver, WebElement element){
		Actions action = new Actions(driver);
		action.click(element).perform();
	}	
	
	protected void clickEnter(WebDriver driver, WebElement element,int value){
		Actions action = new Actions(driver);
		action.click(element).perform();
		

	}
	protected void doubleClick(WebDriver driver, WebElement element){
		Actions action = new Actions(driver);
		action.doubleClick(element).perform();
	}	
	
	protected void navigateByNode(WebDriver driver, String node){
		if(!node.isEmpty() ) {
			try{
			//doubleClick(driver, driver.findElement(By.partialLinkText(node)));
			doubleClick(driver,new WebDriverWait(driver,30).until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(node))));
			}
			//doubleClick(driver,new WebDriverWait(driver,30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[contains(text(), '"+node+"')]"))));
		
			//doubleClick(driver, driver.findElement(By.xpath(".//*[contains(text(), '"+node+"')]")));

			//doubleClick(driver, driver.findElement(By.name(node)));
				
		 catch (Exception e) 
		    {
		        System.err.println("Element not found: " + e.getMessage());
		        LOGGER.error(e.getMessage());
		   
		    }
		    finally
		    {
		        //driver.findElement(By.xpath(".//table[@id='wishlist-table']/tbody/tr/td[5]/div/button")).click();
		    	//System.out.println("Test");
		    }
	}
	
	}
protected void dragDrop(WebDriver driver, WebElement source,WebElement target){
		Actions action = new Actions(driver);
		action.dragAndDrop(source, target).perform();
	}
	
	protected List<String> getNodes(String path){
		List<String> nodes = new ArrayList<String>();
 		for(String node: path.split("\\\\")){
 			if(!node.isEmpty()) {
	 			nodes.add(node);
 			}
		}
 		return nodes;
	}
	
	protected List<String> getReverseNodes(String path){
		List<String> nodes = new ArrayList<String>();
 		for(String node: path.split("\\\\")){
 			if(!node.isEmpty()) {
	 			nodes.add(0,node);
 			}
		}
 		return nodes;
	}
}
