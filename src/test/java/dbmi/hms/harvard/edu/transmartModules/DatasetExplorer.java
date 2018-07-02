package dbmi.hms.harvard.edu.transmartModules;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DatasetExplorer extends Module {
	// private String subset1box = ".//*[@id='queryCriteriaDiv1_1']";
	// private String subset1box = ".//*[@id='panelBoxList_2']";

	//private String subset2box = ".//*[@id='queryCriteriaDiv2_1']";
	//private String subset1boxtwo = ".//*[@id='queryCriteriaDiv1_2']";
	//private String subset1boxtwo = ".//div[@subset='1']//descendant::div[@id='panelBoxList_2']";
	//private String relationbox = ".//*[@id='queryCriteriaDiv3_1']";
	//private String exclude = ".//*[@id='btnExcludeGroup1_1']";
	//private String clear = ".//*[@id='ext-gen89']";
	//private String comparisonTab = ".//*[@id='resultsTabPanel__queryPanel']/a[2]/em/span/span";
	//private String subsetOKbutton = ".//*[@id='ext-gen189']";
	private String subset1box = ".//div[@subset='1']/descendant::div[@class='panelBoxList']";
	private String subset2box = ".//div[@subset='2']/descendant::div[@class='panelBoxList']";
	private String subset1boxtwo =".//div[@class='panelModel' and @subset='1'] //div[@class='panelBox' and @style='height: 49px;']";
	private String navigationTab = ".//*[@id='ontPanel__navigateTermsPanel']/a[2]/em/span/span";
	private String exclude =".//*[@id='queryTable']/tbody/tr[2]/td[1]/div[1]/div/div[4]/span[1]/label[2]/span";
	private String clear ="//button[@class='flatbutton clearbutton'][contains(text(),'Clear All Panels and Analysis')]";
	private String comparisonTab =	"//span[contains(@class,'x-tab-strip-text')][contains(text(),'Comparison')]";
	private String setValueNumeric = ".//*[@id='ext-gen151']/div[1]/table/tbody/tr[2]/td[1]/input[3]";
	private String setValueTextBox = ".//*[@id='setValueLowValue']";

	private String subsetOKbutton = ".//*[@id='ext-gen129']";
	private String searchedSubject = ".//span[contains(text(),'mexican')]";
	private String deleteButton = ".//*[@id='clearGroup1_1']";
	String okexlude="html/body/div[28]/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td[1]/table/tbody/tr/td[2]";
	public static String textSubsetBoxValue;

	public void doSelectNavigationTab(WebDriver driver) {
		click(driver, driver.findElement(By.xpath(navigationTab)));
	}

	public void doClickOK(WebDriver driver) {
		click(driver, driver.findElement(By.xpath(okexlude)));
	}

	public void doDelete(WebDriver driver) {
		click(driver, driver.findElement(By.xpath(deleteButton)));
	}

	public void doCheckSubsetBox(WebDriver driver) {
		WebElement subsetBoxValue = driver.findElement(By.xpath(subset1box));
		textSubsetBoxValue = subsetBoxValue.getText();

	}

	public void doSelectExclude(WebDriver driver) {
		click(driver, driver.findElement(By.xpath(exclude)));
	}

	public void doSelectComparison(WebDriver driver) {
		click(driver, driver.findElement(By.xpath(comparisonTab)));
	}

	public void doSelectByNumericValue(WebDriver driver) {
		click(driver, driver.findElement(By.xpath(setValueNumeric)));
	}

	public void enterValue(WebDriver driver, String subsetValue) {
		driver.findElement(By.xpath(setValueTextBox)).sendKeys(subsetValue);
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
			// WebElement source =
			// driver.findElement(By.linkText(nodes.get(nodes.size() - 1)));
			String targetStr = null;
			if (subset.equalsIgnoreCase("subset1"))
				targetStr = subset1box;
			if (subset.equalsIgnoreCase("subset2"))
				targetStr = subset2box;
			if (subset.equalsIgnoreCase("subset1mul"))
				targetStr = subset1boxtwo;
			//if (subset.equalsIgnoreCase("relation"))
				//targetStr = relationbox;
			WebElement target = driver.findElement(By.xpath(targetStr));
			dragDrop(driver, source, target);
		} catch (Exception e) {
			System.out.println("Unable to find the element" + e.getMessage());
		}
	}

	public void doClearAnalysis(WebDriver driver) {
		click(driver, driver.findElement(By.xpath(comparisonTab)));
		click(driver, driver.findElement(By.xpath(clear)));
		
		Alert alert = driver.switchTo().alert();
		alert.accept();

	}

	public void doSearchDoDragAndDrop(WebDriver driver, String path, String subset) {
		WebElement source = driver.findElement(By.xpath(searchedSubject));
		String targetStr = null;
		if (subset.equalsIgnoreCase("subset1"))
			targetStr = subset1box;
		if (subset.equalsIgnoreCase("subset2"))
			targetStr = subset2box;
		if (subset.equalsIgnoreCase("subset1mul"))
			targetStr = subset1boxtwo;
		/*if (subset.equalsIgnoreCase("relation"))
			targetStr = relationbox;*/
		WebElement target = driver.findElement(By.xpath(targetStr));
		dragDrop(driver, source, target);
	}

}
