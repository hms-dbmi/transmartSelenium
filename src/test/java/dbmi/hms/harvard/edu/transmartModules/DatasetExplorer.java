package dbmi.hms.harvard.edu.transmartModules;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class DatasetExplorer extends Module {
	// private String subset1box = ".//*[@id='queryCriteriaDiv1_1']";
	// private String subset1box = ".//*[@id='panelBoxList_2']";
	// private String subset2box = ".//*[@id='queryCriteriaDiv2_1']";
	// private String subset1boxtwo = ".//*[@id='queryCriteriaDiv1_2']";
	// private String subset1boxtwo =
	// ".//div[@subset='1']//descendant::div[@id='panelBoxList_2']";
	// private String relationbox = ".//*[@id='queryCriteriaDiv3_1']";
	// private String exclude = ".//*[@id='btnExcludeGroup1_1']";
	// private String clear = ".//*[@id='ext-gen89']";
	// private String comparisonTab =
	// ".//*[@id='resultsTabPanel__queryPanel']/a[2]/em/span/span";
	// private String subsetOKbutton = ".//*[@id='ext-gen189']";
	private String subset1box = ".//div[@subset='1']/descendant::div[@class='panelBoxList']";
	private String subset2box = ".//div[@subset='2']/descendant::div[@class='panelBoxList']";
	private String subset1boxtwo = ".//div[@class='panelModel' and @subset='1'] //div[@class='panelBox' and @style='height: 49px;']";
	private String subset2boxtwo = ".//div[@class='panelModel' and @subset='2'] //div[@class='panelBox' and @style='height: 49px;']";
	private String navigationTab = ".//*[@id='ontPanel__navigateTermsPanel']/a[2]/em/span/span";
	private String exclude = ".//*[@id='queryTable']/tbody/tr[2]/td[1]/div[1]/div/div[4]/span[1]/label[2]/span";
	private String clear = "//button[@class='flatbutton clearbutton'][contains(text(),'Clear All Panels and Analysis')]";
	private String comparisonTab = "//span[contains(@class,'x-tab-strip-text')][contains(text(),'Comparison')]";
	private String setNoValue = ".//*[@id='ext-gen151']/div[1]/table/tbody/tr[2]/td[1]/input[1]";
	private String setLowHighFlagValue = ".//*[@id='ext-gen151']/div[1]/table/tbody/tr[2]/td[1]/input[2]";
	private String setValueNumeric = ".//*[@id='ext-gen151']/div[1]/table/tbody/tr[2]/td[1]/input[3]";
	private String setValueTextBox = ".//*[@id='setValueLowValue']";
	private String selectSetOperator = ".//*[@id='setValueOperator']";
	private String selectLowHighRange = ".//*[@id='setValueHighLowSelect']";
	private String subsetOKbutton = ".//*[@id='ext-gen129']";
	private String setValueTextBoxHigh = ".//*[@id='setValueHighValue']";
	private String searchedSubject = ".//span[contains(text(),'mexican')]";
	private String deleteButton = ".//*[@id='clearGroup1_1']";
	private String saveSubSetButton = ".//*[@id='ext-gen59']/div[1]/button[1]";
	private String txtBoxSaveSubset = ".//*[@id='txtSubsetDescription']";
	private String saveSubSets = ".//*[@value='Save Subsets']";
	private String fractalisTab=".//*[@id='resultsTabPanel__fractalisPanel']/a[2]/em";
	String okexlude = "html/body/div[28]/div[2]/div[2]/div/div/div/div/div/table/tbody/tr/td[1]/table/tbody/tr/td[2]";
	public static String textSubsetBoxValue;
	private String selectAnalysisType = ".//*[@id='ext-gen244']/div[2]/div[2]/div[1]/select";
	private String addAnalysisType =".//*[@id='ext-gen244']/div[2]/div[2]/div[1]/input";
	private String fractalisBox  =".//*[@id='ext-gen250']";
	private String resetView  =".//input[@value='Reset View']";
	private String clearAnalysisCache  =".//input[@value='Clear analysis cach']";
	public void doSelectNavigationTab(WebDriver driver) {
		click(driver, driver.findElement(By.xpath(navigationTab)));
	}

	public void doClickOK(WebDriver driver) {
		click(driver, driver.findElement(By.xpath(okexlude)));
	}

	public void doSaveComparison(WebDriver driver) {
		click(driver, driver.findElement(By.xpath(saveSubSetButton)));
	}

	public void doEnterSubsetName(WebDriver driver, String subsetName) {
		// click(driver, driver.findElement(By.xpath(txtBoxSaveSubset)));
		driver.findElement(By.xpath(txtBoxSaveSubset)).sendKeys(subsetName);
	}

	public void saveSubsets(WebDriver driver) {
		click(driver, driver.findElement(By.xpath(saveSubSets)));
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

	public void doSelectByLowHighFlag(WebDriver driver) {
		click(driver, driver.findElement(By.xpath(setLowHighFlagValue)));
	}

	public void doSelectByNumericValue(WebDriver driver) {
		click(driver, driver.findElement(By.xpath(setValueNumeric)));
	}

	public void doSelectNoValue(WebDriver driver) {
		driver.findElement(By.xpath(subsetOKbutton)).click();
	}

	public void doSelectFlagRange(WebDriver driver, Integer i) {
		Select oSelect = new Select(driver.findElement(By.xpath(selectLowHighRange)));
		oSelect.selectByIndex(i);
	}

	public void doSelectOpeartor(WebDriver driver, Integer i) {
		Select oSelect = new Select(driver.findElement(By.xpath(selectSetOperator)));
		oSelect.selectByIndex(i);
	}
	
	public void doSelectFractalisAnalysis(WebDriver driver, Integer i) {
		Select oSelect = new Select(driver.findElement(By.xpath(selectAnalysisType)));
		oSelect.selectByIndex(i);
	}
	public void enterValue(WebDriver driver, String subsetValue) {
		driver.findElement(By.xpath(setValueTextBox)).sendKeys(subsetValue);
		driver.findElement(By.xpath(subsetOKbutton)).click();
	}

	public void enterBetweenValue(WebDriver driver, String subsetLowValue, String subsetHighValue) {
		driver.findElement(By.xpath(setValueTextBox)).sendKeys(subsetLowValue);
		driver.findElement(By.xpath(setValueTextBoxHigh)).sendKeys(subsetHighValue);
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
			if (subset.equalsIgnoreCase("subsetmul1"))
				targetStr = subset1boxtwo;
			if (subset.equalsIgnoreCase("subsetmul2"))
				targetStr = subset2boxtwo;
			if (subset.equalsIgnoreCase("fractConCate1"))
				targetStr = fractalisBox;
			if (subset.equalsIgnoreCase("fractConCate2"))
				targetStr = fractalisBox;
			if (subset.equalsIgnoreCase("fractConNum1"))
				targetStr = fractalisBox;
			if (subset.equalsIgnoreCase("fractConNum2"))
				targetStr = fractalisBox;
			// if (subset.equalsIgnoreCase("relation"))
			// targetStr = relationbox;
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
		/*
		 * if (subset.equalsIgnoreCase("relation")) targetStr = relationbox;
		 */
		WebElement target = driver.findElement(By.xpath(targetStr));
		dragDrop(driver, source, target);
	}

	
	public void doSelectFractlis(WebDriver driver) {
		driver.findElement(By.xpath(fractalisTab)).click();
	}

	public void addAnalysis(WebDriver driver) {
		click(driver, driver.findElement(By.xpath(addAnalysisType)));
	}

	public void resetReview(WebDriver driver) {
		click(driver, driver.findElement(By.xpath(resetView)));
	}
	
	public void clearAnalysisCache(WebDriver driver) {
		click(driver, driver.findElement(By.xpath(clearAnalysisCache)));
	}
}
