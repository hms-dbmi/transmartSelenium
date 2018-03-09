package dbmi.hms.harvard.edu.transmartModules;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DatasetExplorer extends Module {
	private String subset1box = ".//*[@id='queryCriteriaDiv1_1']";
	private String subset2box = ".//*[@id='queryCriteriaDiv2_1']";
	private String subset1boxtwo = ".//*[@id='queryCriteriaDiv1_2']";
	private String relationbox = ".//*[@id='queryCriteriaDiv3_1']";
	private String navigationTab = ".//*[@id='ontPanel__navigateTermsPanel']/a[2]/em/span/span";
	private String exclude = ".//*[@id='btnExcludeGroup1_1']";
	private String clear = ".//*[@id='ext-gen89']";
	private String comparisonTab = ".//*[@id='resultsTabPanel__queryPanel']/a[2]/em/span/span";
	private String subsetValue = ".//*[@id='setValueLowValue']";
	//private String subset = "10";
	private String subsetOKbutton = ".//*[@id='ext-gen189']";
	private String searchedSubject =".//span[contains(text(),'mexican')]";
	
	public void doSelectNavigationTab(WebDriver driver) {
		click(driver, driver.findElement(By.xpath(navigationTab)));
	}

	public void doSelectExclude(WebDriver driver) {
		click(driver, driver.findElement(By.xpath(exclude)));
	}

	public void doSelectComparison(WebDriver driver) {
		click(driver, driver.findElement(By.xpath(comparisonTab)));
	}

	public void doclickSubsetValue(WebDriver driver) {
		click(driver, driver.findElement(By.xpath(subsetValue)));
	}

	public void enterValue(WebDriver driver, String SubsetValue) {
		driver.findElement(By.xpath(subsetValue)).sendKeys(SubsetValue);
		driver.findElement(By.xpath(subsetOKbutton)).click();
	}

	public void doNavigateByPath(WebDriver driver, String path) {

		List<String> nodes = getNodes(path);
		for (String node : nodes) {
			navigateByNode(driver, node);

		}
	}

	public void doReverseNavigateByPath(WebDriver driver, String path) {
		List<String> nodes = getReverseNodes(path);
		for (String node : nodes) {
			navigateByNode(driver, node);
		}
	}

	public void doDragAndDrop(WebDriver driver, String path, String subset) {
		List<String> nodes = getNodes(path);
		try {
			WebElement source = driver.findElement(By.partialLinkText(nodes.get(nodes.size() - 1)));
			String targetStr = null;
			if (subset.equalsIgnoreCase("subset1"))
				targetStr = subset1box;
			if (subset.equalsIgnoreCase("subset2"))
				targetStr = subset2box;
			if (subset.equalsIgnoreCase("subset1mul"))
				targetStr = subset1boxtwo;
			if (subset.equalsIgnoreCase("relation"))
				targetStr = relationbox;
			WebElement target = driver.findElement(By.xpath(targetStr));
			dragDrop(driver, source, target);
		} catch (Exception e) {
			System.out.println("Unable to find the element" + e.getMessage());
		}
	}

	public void doClearAnalysis(WebDriver driver) {
		click(driver, driver.findElement(By.xpath(clear)));
		Alert alert = driver.switchTo().alert();
		alert.accept();

	}
	
	public void doSearchDoDragAndDrop(WebDriver driver, String path, String subset) 
	{
		
		WebElement source = driver.findElement(By.xpath(searchedSubject));
			String targetStr = null;
			if (subset.equalsIgnoreCase("subset1"))
				targetStr = subset1box;
			if (subset.equalsIgnoreCase("subset2"))
				targetStr = subset2box;
			if (subset.equalsIgnoreCase("subset1mul"))
				targetStr = subset1boxtwo;
			if (subset.equalsIgnoreCase("relation"))
				targetStr = relationbox;
			WebElement target = driver.findElement(By.xpath(targetStr));
			dragDrop(driver, source, target);
		} 
	

}
