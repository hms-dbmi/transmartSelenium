package dbmi.hms.harvard.edu.transmartModules;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public abstract class Module {
	
	protected void enterText(WebDriver driver, String xpath, String text){
		driver.findElement(By.xpath(xpath)).sendKeys(text);
	}
	protected void click(WebDriver driver, WebElement element){
		Actions action = new Actions(driver);
		action.click(element).perform();
	}	
	
	protected void doubleClick(WebDriver driver, WebElement element){
		Actions action = new Actions(driver);
		action.doubleClick(element).perform();
	}	
	
	protected void navigateByNode(WebDriver driver, String node){
		if(!node.isEmpty() ) {
			doubleClick(driver, driver.findElement(By.partialLinkText(node)));
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
