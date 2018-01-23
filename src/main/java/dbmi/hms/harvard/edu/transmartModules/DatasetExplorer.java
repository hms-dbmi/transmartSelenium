package dbmi.hms.harvard.edu.transmartModules;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;

public class DatasetExplorer extends Module{ 
	private String subset1box = ".//*[@id='queryCriteriaDiv1_1']";
	private String subset2box = ".//*[@id='queryCriteriaDiv2_1'";
	private String relationbox = ".//*[@id='queryCriteriaDiv3_1']";
	private String navigationTab = ".//*[@id='ontPanel__navigateTermsPanel']/a[2]/em/span/span";
	
	/*public void doLoginLaunchBrowser(WebDriver driver)
	{
		
	}*/
	
	public void doSelectNavigationTab(WebDriver driver){
		click(driver,driver.findElement(By.xpath(navigationTab)));
	}
	
	public void doNavigateByPath(WebDriver driver, String path){
		
		List<String> nodes = getNodes(path);
		
		for(String node : nodes){
		
			navigateByNode(driver, node);
			
		}
	}
	
	public void doReverseNavigateByPath(WebDriver driver, String path){
		List<String> nodes = getReverseNodes(path);
		for(String node : nodes){
			navigateByNode(driver, node);
		}
	}
	
	public void doDragAndDrop(WebDriver driver, String path, String subset){
		List<String> nodes = getNodes(path);
		WebElement source = driver.findElement(By.partialLinkText(nodes.get(nodes.size() - 1)));
		String targetStr = null;
		if(subset.equalsIgnoreCase("subset1")) targetStr = subset1box;
		if(subset.equalsIgnoreCase("subset2")) targetStr = subset2box;
		if(subset.equalsIgnoreCase("relation")) targetStr = relationbox;
		WebElement target = driver.findElement(By.xpath(targetStr));
		dragDrop(driver ,source, target);
	}

}
