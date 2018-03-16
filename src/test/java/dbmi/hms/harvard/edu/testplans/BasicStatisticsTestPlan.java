package dbmi.hms.harvard.edu.testplans;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.util.Strings;

import com.fasterxml.jackson.databind.ser.SerializerCache;

import dbmi.hms.harvard.edu.authentication.AuthTypes;
import dbmi.hms.harvard.edu.reporter.Reporter;
import dbmi.hms.harvard.edu.testplans.results.SummaryStatisticsResults;
import dbmi.hms.harvard.edu.transmartModules.DatasetExplorer;
import dbmi.hms.harvard.edu.transmartModules.SearchBySubject;
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

	public void loginSite() {

		System.setProperty(BROWSER, BROWSERDRIVER);
		LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Launching the Browser>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		LOGGER.info("");
		LOGGER.info("********************************Loading the Harvard Website********************************");
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
	}

	public void checkWinodwTitle(Reporter reporter) throws InterruptedException {
		String winodwTitle = driver.getTitle();
		LOGGER.info("-------------------Logged in successfully: Title of winodow is -------------------------"
				+ winodwTitle);

		if (winodwTitle.equals("Dataset Explorer")) {
			try {
				SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		else {

			try {
				SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void doPlan(Reporter reporter) throws InterruptedException {

		try {

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

				}

				driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
				SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
				SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
				DatasetExplorer.class.newInstance().doClearAnalysis(driver);
				DatasetExplorer.class.newInstance().doSelectComparison(driver);

			}

		} catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}

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
				driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
				SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
				SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
				DatasetExplorer.class.newInstance().doClearAnalysis(driver);
				DatasetExplorer.class.newInstance().doSelectComparison(driver);

			}

		} catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}

	}

	public void doPlanMultipleSubset1OR(Reporter reporter)
			throws InterruptedException, InstantiationException, IllegalAccessException {
		try {

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

			if (testPlan.get("subsetmul1") != null && testPlan.get("subsetmul1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subsetmul1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

		} catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
		SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
		SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
		DatasetExplorer.class.newInstance().doClearAnalysis(driver);
		DatasetExplorer.class.newInstance().doSelectComparison(driver);

	}

	/*
	 * Method to check Summary Stats for Multiple Subset 1 and Subset 2 with OR
	 * condition
	 */

	public void verifyMultipleSubset1and2OR(Reporter reporter)
			throws InterruptedException, InstantiationException, IllegalAccessException {
		try {

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

			if (testPlan.get("subsetmul1") != null && testPlan.get("subsetmul1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subsetmul1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

				if (testPlan.get("subset2") != null && testPlan.get("subset2") != "") {
					for (String path : java.util.Arrays.asList(testPlan.get("subset2").toString().split(","))) {

						DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
						DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset2");
						DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
					}
				}

				if (testPlan.get("subsetmul2") != null && testPlan.get("subsetmul2") != "") {
					for (String path : java.util.Arrays.asList(testPlan.get("subsetmul2").toString().split(","))) {
						DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
						DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset2");
						DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
					}
				}

			}

		} catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
		SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
		SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
		DatasetExplorer.class.newInstance().doClearAnalysis(driver);
		DatasetExplorer.class.newInstance().doSelectComparison(driver);

	}

	public void doPlanMultipleSubsetAnd(Reporter reporter)
			throws InterruptedException, InstantiationException, IllegalAccessException {
		try {

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

			if (testPlan.get("subsetmul1") != null && testPlan.get("subsetmul1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subsetmul1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1mul");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

		} catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
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

				driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
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
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset2";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			}

			driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
			DatasetExplorer.class.newInstance().doClearAnalysis(driver);
			DatasetExplorer.class.newInstance().doSelectComparison(driver);

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doPlanSubsetOneTwo(Reporter reporter) throws InterruptedException {
		try {

			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {

				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset1";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			}

			for (String path : java.util.Arrays.asList(testPlan.get("subset2").toString().split(","))) {

				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset2";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
				DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
			}

			driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
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
		String subsetValue = testPlan.get("subsetValue").toString();
		try {

			for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
				DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
				String subset = "subset1";
				DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, subset);
			}

			driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
			DatasetExplorer.class.newInstance().doclickSubsetValue(driver);
			DatasetExplorer.class.newInstance().enterValue(driver, subsetValue);

			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
			DatasetExplorer.class.newInstance().doClearAnalysis(driver);
			DatasetExplorer.class.newInstance().doSelectComparison(driver);

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* This method checks Summary Stats for Laboratory Terms */
	public void verifySummaryStatsLab(Reporter reporter) throws InterruptedException {

		try {

			String subsetvalue = testPlan.get("subsetValue1").toString();
			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

				}
			}
			driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
			DatasetExplorer.class.newInstance().doclickSubsetValue(driver);
			DatasetExplorer.class.newInstance().enterValue(driver, subsetvalue);

			if (testPlan.get("subset2") != null && testPlan.get("subset2") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset2").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset2");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
			DatasetExplorer.class.newInstance().doClearAnalysis(driver);
			DatasetExplorer.class.newInstance().doSelectComparison(driver);

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* This method checks Summary Stats for Questionnaire Terms */
	public void verifySumStasQue(Reporter reporter) throws InterruptedException {

		try {

			String subsetvalue = testPlan.get("subsetValue1").toString();
			// double SubsetvalueLab=Double.parseDouble(subsetvalue);

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

				}
			}
			driver.manage().timeouts().implicitlyWait(49, TimeUnit.SECONDS);
			DatasetExplorer.class.newInstance().doclickSubsetValue(driver);
			DatasetExplorer.class.newInstance().enterValue(driver, subsetvalue);

			if (testPlan.get("subset2") != null && testPlan.get("subset2") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset2").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset2");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

			SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
			SummaryStatisticsResults.class.newInstance().doResults(driver, testPlan, reporter);
			DatasetExplorer.class.newInstance().doClearAnalysis(driver);
			DatasetExplorer.class.newInstance().doSelectComparison(driver);

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doSearch(Reporter reporter) throws InterruptedException, Exception {

		String searchTerm = testPlan.get("searchTerm").toString();
		try {
			SearchBySubject.class.newInstance().doSelectNavigationTab(driver);
			SearchBySubject.class.newInstance().doSearch(driver, searchTerm);
			Thread.sleep(20000);
			SearchBySubject.class.newInstance().getSearchResult(driver);
			String SearchResult1 = SearchBySubject.result;
			String expected = testPlan.get("expectedSearchResult").toString();
			if (SearchResult1.equals(testPlan.get("expectedSearchResult").toString())) {
				try {

					SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			}

			else {
				SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		SearchBySubject.class.newInstance().doClearSearchBox(driver);
	}

	public void doSearchSpecialChar(Reporter reporter) throws InterruptedException, Exception {

		String searchTermSpecialCharacter = testPlan.get("searchTermSpecialChar").toString();
		try {
			SearchBySubject.class.newInstance().doSelectNavigationTab(driver);
			SearchBySubject.class.newInstance().doSearch(driver, searchTermSpecialCharacter);
			Thread.sleep(20000);
			SearchBySubject.class.newInstance().getSearchResult(driver);
			String SearchResult1 = SearchBySubject.result;
			String expected = testPlan.get("expectedSearchResult").toString();
			if (SearchResult1.equals(testPlan.get("expectedSearchResult").toString())) {
				try {

					SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			}

			else {
				SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		SearchBySubject.class.newInstance().doClearSearchBox(driver);
	}

	
	public void doPlanSummaryStatSearch(Reporter reporter) throws InterruptedException {

		String searchStats = testPlan.get("searchTermsum").toString();
		try {
			SearchBySubject.class.newInstance().doSelectNavigationTab(driver);
			SearchBySubject.class.newInstance().doSearch(driver, searchStats);
			Thread.sleep(10000);
			SearchBySubject.class.newInstance().getSearchResult(driver);
			String searchResult1 = SearchBySubject.result;

			if (searchResult1.substring(6, 7).equals("1")) {

				DatasetExplorer.class.newInstance().doSearchDoDragAndDrop(driver, searchResult1, "subset1");
				SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
				SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
				DatasetExplorer.class.newInstance().doClearAnalysis(driver);
				DatasetExplorer.class.newInstance().doSelectComparison(driver);
			} else {
				SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			}

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			SearchBySubject.class.newInstance().getSearchResult(driver);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
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

	public void verifyGraphs(Reporter reporter) throws Exception {

		try {

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);

				}

				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				SummaryStatistics.class.newInstance().runSummaryStatistics(driver);
				List<WebElement> graphs = driver
						.findElements(By.xpath(".//*[@id='ext-gen157']/div/table/tbody/tr/td[1]/img"));
				if (graphs.size() != 0)

				{

					Iterator<WebElement> itr = graphs.iterator();
					while (itr.hasNext()) {
						String graph = itr.next().getAttribute("src");
						assertThat(graph).contains("jfreechart");
						LOGGER.info(
								"-------------------Sex and Race Graph are present on the reports -------------------------");
					}

				}

			}

			try {
				SummaryStatisticsResults.class.newInstance().doResultCheckGraph(driver, testPlan, reporter);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
		}

	}

	public void verifyDelete(Reporter reporter) throws InterruptedException {

		try {

			if (testPlan.get("subset1") != null && testPlan.get("subset1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subset1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}

			if (testPlan.get("subsetmul1") != null && testPlan.get("subsetmul1") != "") {
				for (String path : java.util.Arrays.asList(testPlan.get("subsetmul1").toString().split(","))) {
					DatasetExplorer.class.newInstance().doNavigateByPath(driver, path);
					DatasetExplorer.class.newInstance().doDragAndDrop(driver, path, "subset1");
					DatasetExplorer.class.newInstance().doReverseNavigateByPath(driver, path);
				}

			}
			DatasetExplorer.class.newInstance().doCheckSubsetBox(driver);
			DatasetExplorer.class.newInstance().doDelete(driver);
			DatasetExplorer.class.newInstance().doCheckSubsetBox(driver);
			if (Strings.isNullOrEmpty(DatasetExplorer.textSubsetBoxValue)) {
				SummaryStatisticsResults.class.newInstance().doAssertResultTrue(driver, testPlan, reporter);
			} else {
 
				SummaryStatisticsResults.class.newInstance().doAssertResultFalse(driver, testPlan, reporter);
			}

		} catch (InstantiationException | IllegalAccessException e) {

			e.printStackTrace();
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
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
