package dbmi.hms.harvard.edu.testplans;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import dbmi.hms.harvard.edu.authentication.AuthTypes;
import dbmi.hms.harvard.edu.reporter.Reporter;
import dbmi.hms.harvard.edu.testdrivers.TestDriver;
import dbmi.hms.harvard.edu.testplans.results.SummaryStatisticsResults;
import dbmi.hms.harvard.edu.transmartModules.DatasetExplorer;
import dbmi.hms.harvard.edu.transmartModules.SummaryStatistics;

public class BasicStatisticsTestPlan extends Testplan {
	private static final int TIMEOUT = 30;
	private static final String BROWSER = "webdriver.firefox.marionette";
	private static final String BROWSERDRIVER = "/Users/tom/Documents/workspace-ggts-3.6.4.RELEASE/transmartQA/drivers/geckodriver";
	private String DragConcept = ".//*[@id='ext-gen157']/div/table/tbody/tr/td";
	private Set<String> subset1;
	private Set<String> subset2;
	private Set<String> relational;
	private Set<String> subsetmul1;
	private static WebDriver driver;
	private static final Logger LOGGER = Logger.getLogger(BasicStatisticsTestPlan.class.getName());

	public BasicStatisticsTestPlan() {
	}

	public Set<String> getSubset1() {
		return subset1;
	}

	public void setSubset1(Set<String> subset1) {
		this.subset1 = subset1;
	}

	
	public Set<String> getSubset2() {
		return subset2;
	}

	public void setSubset2(Set<String> subset2) {
		this.subset2 = subset2;
	}

	public Set<String> subsetmul1() {
		return subsetmul1;
	}

	public void setSubset1Multiple(Set<String> subsetmul1) {
		this.subsetmul1 = subsetmul1;
	}

	
	
	public Set<String> getRelational() {
		return relational;
	}

	public void setRelational(Set<String> relational) {
		this.relational = relational;
	}

	public void checkWinodwTitle(Reporter reporter) throws InterruptedException {

		System.setProperty(BROWSER, BROWSERDRIVER);
		LOGGER.info(">>>>>>>>>>>>>>>>>>>>>Launching the Browser>>>>>>>>>>>>>>>>>>\n");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		LOGGER.info("");
		LOGGER.info("************************Loading the Harvard Website***************************\4n");
		driver.get(testPlan.get("url").toString());
		AuthTypes authTypes = new AuthTypes();
	
		// Set authentication type
		String authLink;
		if (authTypes.getAuthTypes().containsKey(testPlan.get("authmethod").toString())) 
		{
			authLink = authTypes.getAuthTypes().get(testPlan.get("authmethod").toString());
		} 
		else {
			authLink = null;
		}

		// driver.findElement(By.linkText(authLink)).click();

		authTypes.doAuth(driver, testPlan);
		String winodwTitle = driver.getTitle();
		LOGGER.info("-------------------Logged in successfully: Title of winodow is -------------------------" + winodwTitle);
		assertThat(winodwTitle).contains("Dataset Explorer");

		try {
			SummaryStatisticsResults.class.newInstance().doFirstResultCheck(driver, testPlan, reporter);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void doPlan(Reporter reporter) throws InterruptedException {
		
		try {

			
			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					System.out.println("---------"+path);
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

				}

				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
				SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
				DatasetExplorer.class.newInstance().doClearAnalysis(driver);
				DatasetExplorer.class.newInstance().doSelectComparison(driver);
				
			}
			
		} catch (InstantiationException | IllegalAccessException e) {
			
			e.printStackTrace();
		}
		//reporter.doReport();

	}


	
	
	public void verifyExcludeFeature(Reporter reporter) throws InterruptedException {
		
		try {

			
			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
					

				}
				DatasetExplorer.class.newInstance().doSelectExclude(driver);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
				SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
				DatasetExplorer.class.newInstance().doClearAnalysis(driver);
				DatasetExplorer.class.newInstance().doSelectComparison(driver);
				
			}
			
		} catch (InstantiationException | IllegalAccessException e) {
			
			e.printStackTrace();
		}
		//reporter.doReport();

	}


	
	
	public void doPlanMultipleSubset(Reporter reporter) throws InterruptedException, InstantiationException, IllegalAccessException {
		try {

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "")
			{
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) 
				{
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}
			
			if (testPlan.get("subsetmul1") != null && testPlan.get("subsetmul1") != "")
			{
				for (String path : java.util.Arrays.asList(testPlan.get("subsetmul1").toString().split(","))) 
				{
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

			
		} 
			catch (InstantiationException | IllegalAccessException e) {
			
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
		SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
		DatasetExplorer.class.newInstance().doClearAnalysis(driver);
		DatasetExplorer.class.newInstance().doSelectComparison(driver);

	}


	
	public void doPlanMultipleSubsetAnd(Reporter reporter) throws InterruptedException, InstantiationException, IllegalAccessException {
		try {

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "")
			{
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) 
				{
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}
			
			if (testPlan.get("subsetmul1") != null && testPlan.get("subsetmul1") != "")
			{
				for (String path : java.util.Arrays.asList(testPlan.get("subsetmul1").toString().split(","))) 
				{
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1mul");
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

			
		} 
			catch (InstantiationException | IllegalAccessException e) {
			
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
		SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
		DatasetExplorer.class.newInstance().doClearAnalysis(driver);
		DatasetExplorer.class.newInstance().doSelectComparison(driver);

	}

	
	
	
	
	public void verifyClear(Reporter reporter) throws Exception {

		try {

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

				}

				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
				SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
				DatasetExplorer.class.newInstance().doClearAnalysis(driver);

			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		WebElement clearButton = driver.findElement(By.xpath(DragConcept));
		String testClearButton = clearButton.getText();
		assertThat(testClearButton).contains("Drag concepts to this");

	}

	public void doPlanSubset2(Reporter reporter) throws InterruptedException {
		try {

			for (String path : java.util.Arrays.asList(testPlan.get("subset2").toString().split(","))) {
				// Thread.sleep(30000);
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset2";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			}

			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
			//reporter.doReport();
			DatasetExplorer.class.newInstance().doClearAnalysis(driver);
			DatasetExplorer.class.newInstance().doSelectComparison(driver);

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doPlanSubset3(Reporter reporter) throws InterruptedException {
		try {

			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
				// Thread.sleep(30000);
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset1";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			}

			for (String path : java.util.Arrays.asList(testPlan.get("subset2").toString().split(","))) {
				// Thread.sleep(30000);
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset2";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			}

			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
			DatasetExplorer.class.newInstance().doClearAnalysis(driver);
			DatasetExplorer.class.newInstance().doSelectComparison(driver);
			

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPlanSetValue(Reporter reporter) throws InterruptedException {
		try {

			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
				Thread.sleep(40000);
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset1";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
			}

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			DatasetExplorer.class.newInstance().doclickSubsetValue(driver);
			DatasetExplorer.class.newInstance().enterValue(driver);

			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
			DatasetExplorer.class.newInstance().doClearAnalysis(driver);
			DatasetExplorer.class.newInstance().doSelectComparison(driver);
		
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeDriver() {
		driver.quit();
	}

	// Test Expanding/collapsing the concept tree

	public void verifyExpandCollpase(Reporter reporter) {
		for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
			try {
				System.out.println(subset1);
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String subset = "subset1";
			try {
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void doPlan() {
		try {

			System.setProperty(BROWSER, BROWSERDRIVER);

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(testPlan.get("url").toString());

			AuthTypes authTypes = new AuthTypes();

			// Set authentication type
			String authLink;

			if (authTypes.getAuthTypes().containsKey(testPlan.get("authmethod").toString())) {
				authLink = authTypes.getAuthTypes().get(testPlan.get("authmethod").toString());
			} else {
				authLink = null;
			}

			// driver.findElement(By.linkText(authLink)).click();

			authTypes.doAuth(driver, testPlan);

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset1";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			}

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan);

			// assertThat(winodwTitle).contains("Dataset Explorer");
			// driver.close();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
