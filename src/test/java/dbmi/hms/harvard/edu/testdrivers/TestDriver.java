package dbmi.hms.harvard.edu.testdrivers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.http.impl.cookie.BasicSecureHandler;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import dbmi.hms.harvard.edu.reporter.Reporter;
import dbmi.hms.harvard.edu.testplans.Testplan;

public class TestDriver {

	public static final String TESTPLANS = "dbmi.hms.harvard.edu.testplans.";
	public static final String REPORTS = "dbmi.hms.harvard.edu.reporter.";
	private static Properties configProperties = new Properties();
	private static String baseURI = System.getProperty("pathtoconfigtest");

	// Read data from Properties file using Java Selenium

	static {

		try {
			final Logger LOGGER = Logger.getLogger(TestDriver.class.getName());
			System.out.println("Path of Config.properties file is :   " + baseURI);
			LOGGER.info("TestRun" +baseURI);
			String pathToConfigFile = baseURI + "Config.properties";
			System.out.println("path is "+pathToConfigFile);
			configProperties.load(new FileInputStream(new File(pathToConfigFile)));
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static final Logger LOGGER = Logger.getLogger(TestDriver.class.getName());
	WebDriver driver;

	// @Atul
	public static Testplan testPlan = null;
	static Reporter reporter = null;

	public static void readFile(String fileName) {
		try {
			YamlReader reader1 = new YamlReader(new FileReader(fileName));

			Map testConfig = (Map) reader1.read();
			if (testConfig != null) {

				if (testPlan == null)
					testPlan = initTestPlan(testConfig.get("type").toString(), testConfig);
				testPlan.setTestPlan(testConfig);
				if (reporter == null)
					reporter = initReporter(testConfig.get("reporter").toString());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (YamlException e) {
			e.printStackTrace();
		}
	}

	
//If BeforeTest method fails, all other cases won't execute...	
	
@BeforeTest

	public void setup() throws InterruptedException {

		LOGGER.info(
				"______________________________________Setting up the application______________________________________");
		readFile(configProperties.getProperty("intial.setup.transmart"));
		testPlan.loginSite();
		LOGGER.info(
				"______________________________________Initial Setting up is Done______________________________________");

	}
	

@Test(priority = 2,groups = { "Sanity" })
	public static void verifyLoginWithWinowTitle() throws YamlException, InterruptedException {

		LOGGER.info(
				"---------------------------------The test case verifyWinowTitle is running-------------------------");
		readFile(configProperties.getProperty("verify.window.title"));
		testPlan.checkWinodwTitle(reporter);
		LOGGER.info(
				"---------------------------------The test case verifyWinowTitle is Finished-------------------------");

	}
@Test(priority = 3, groups = { "Sanity" })

public static void verifySummaryStats() throws YamlException, InterruptedException {

	LOGGER.info(
			"-------------------------------The test case verifySummaryStats is running-------------------------");
	readFile(configProperties.getProperty("verify.summaryStats.subset1"));
	testPlan.doPlan(reporter);
	LOGGER.info(
			"--------------------------------The test case verifySummaryStats is Finished-------------------------");

}
//@Test(priority = 4, groups = { "Sanity Regression" })

public static void verifySummaryStatsSetValueByNumericEqual() throws YamlException, InterruptedException {

	LOGGER.info(
			"---------------------------The test case verifySummaryStatsSetValueByNumericEqual is running-------------------------");
	readFile(configProperties.getProperty("verify.summarystats.equal.subset"));
	testPlan.doPlanSetValueEqual(reporter);
	LOGGER.info(
			"---------------------------The test case verifySummaryStatsSetValueByNumericEqual is Finished-------------------------");

}

//@Test(priority = 8, groups = { "Sanity Regression" })

public static void verifySummaryStatsSetValueByNumericGreaterThanEqual() throws YamlException, InterruptedException {

	LOGGER.info(
			"---------------------------The test case verifySummaryStatsSetValueByNumericGreaterThanEqual is running-------------------------");
	readFile(configProperties.getProperty("verify.summarystats.greaterthanequal.subset.value"));
	testPlan.doPlanSetValueGreaterThanEqual(reporter);
	LOGGER.info(
			"---------------------------The test case verifySummaryStatsSetValueByNumericGreaterThanEqual is Finished-------------------------");

}


//@Test(priority = 9, groups = { "Sanity Regression" })

public static void verifySummaryStatsMultipleSubset1OR()
		throws YamlException, InterruptedException, InstantiationException, IllegalAccessException {
	LOGGER.info(
			"-------------------------------The test case verifySummaryStatsMultipleSubset1OR is running-------------------------");
	readFile(configProperties.getProperty("verify.summarystats.multiplesubset1OR"));
	testPlan.doPlanMultipleSubset1OR(reporter);
	LOGGER.info(
			"--------------------------------The test case verifySummaryStatsMultipleSubset1OR is Finished-------------------------");
}

//@Test(priority = 11, groups = { "Sanity Regression" })
public static void verifySummaryStatsMultipleSubset1Subset2AND()
		throws YamlException, InterruptedException, InstantiationException, IllegalAccessException {

	LOGGER.info(
			"-------------------------------The test case verifySummaryStatsMultipleSubset1Subset2AND is running-------------------------");
	readFile(configProperties.getProperty("verify.summarystats.multiplesubset1subset2AND"));
	//readFile(configProperties.getProperty("verify.summarystats.multiplesubset1subset2OR"));
	testPlan.doPlanMultipleSubset1Subset2And(reporter);
	LOGGER.info(
			"--------------------------------The test case verifySummaryStatsMultipleSubset1Subset2AND is Finished-------------------------");

}

//@Test(priority = 10, groups = { "Sanity Regression" })

public static void verifySummaryStatsSubset2() throws YamlException, InterruptedException {

	LOGGER.info(
			"---------------------------The test case verifySummaryStatsSubset2 is running-------------------------");
	readFile(configProperties.getProperty("verify.summaryStats.subset2"));
	testPlan.doPlanSubset2(reporter);
	LOGGER.info(
			"---------------------------The test case verifySummaryStatsSubset2 is Finished-------------------------");

}

		
//@Test(priority = 7, groups = { "Sanity Regression" })

	public static void verifySummaryStatsSetNoValue() throws YamlException, InterruptedException {

		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSetNoValue is running-------------------------");
		readFile(configProperties.getProperty("verify.summarystats.subset.novalue"));
		testPlan.doPlanSetNoValue(reporter);
		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSetNoValue is Finished-------------------------");

	}

	
	
//@Test(priority = 14, groups = { "Sanity Regression" })

		public static void verifySummaryStatsSetLowHighFlagLowRange() throws YamlException, InterruptedException {

			LOGGER.info(
					"---------------------------The test case verifySummaryStatsSetLowHighFlagLowRange is running-------------------------");
			readFile(configProperties.getProperty("verify.summarystats.subset.lowHighflagNormal"));
			testPlan.doPlanSetLowFlagRange(reporter);
			LOGGER.info(
					"---------------------------The test case verifySummaryStatsSetLowHighFlagLowRange is Finished-------------------------");

		}
		/*

		//@Test(priority = 14, groups = { "Sanity Regression" })

		public static void verifySummaryStatsSetLowHighFlagMediumRange() throws YamlException, InterruptedException {

			LOGGER.info(
					"---------------------------The test case verifySummaryStatsSetLowHighFlagMediumRange is running-------------------------");
			readFile(configProperties.getProperty("verify.summarystats.subset.lowHighflagMedium"));
			testPlan.doPlanSetLowFlagRange(reporter);
			LOGGER.info(
					"---------------------------The test case verifySummaryStatsSetLowHighFlagMediumRange is Finished-------------------------");

		}

		//@Test(priority = 14, groups = { "Sanity Regression" })

		public static void verifySummaryStatsSetLowHighFlagHighRange() throws YamlException, InterruptedException {

			LOGGER.info(
					"---------------------------The test case verifySummaryStatsSetLowFlag is running-------------------------");
			readFile(configProperties.getProperty("verify.summarystats.subset.lowhighflagHigh"));
			testPlan.doPlanSetLowFlagRange(reporter);
			LOGGER.info(
					"---------------------------The test case verifySummaryStatsSetLowHighFlagHighRange is Finished-------------------------");

		}

		
@Test(priority = 3, groups = { "Sanity Regression" })

	public static void verifySummaryStatsSetValueByNumericEqual() throws YamlException, InterruptedException {

		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSetValueByNumericEqual is running-------------------------");
		readFile(configProperties.getProperty("verify.summarystats.equal.subset"));
		testPlan.doPlanSetValueEqual(reporter);
		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSetValueByNumericEqual is Finished-------------------------");

	}


@Test(priority = 4, groups = { "Sanity Regression" })

public static void verifySummaryStatsSetValueByNumericLessThan() throws YamlException, InterruptedException {

	LOGGER.info(
			"---------------------------The test case verifySummaryStatsSetValueByNumericLessThan is running-------------------------");
	readFile(configProperties.getProperty("verify.summarystats.lessthan.entersubset.value"));
	testPlan.doPlanSetValueLessThan(reporter);
	LOGGER.info(
			"---------------------------The test case verifySummaryStatsSetValueByNumericLessThan is Finished-------------------------");
	
}



@Test(priority = 5, groups = { "Sanity Regression" })

public static void verifySummaryStatsSetValueByNumericLessThanEqual() throws YamlException, InterruptedException {

	LOGGER.info(
			"---------------------------The test case verifySummaryStatsSetValueByNumericLessThanEqual is running-------------------------");
	readFile(configProperties.getProperty("verify.summarystats.lessthanequal.entersubset.value"));
	testPlan.doPlanSetValueLessThanEqual(reporter);
	LOGGER.info(
			"---------------------------The test case verifySummaryStatsSetValueByNumericLessThanEqual is Finished-------------------------");
	//Thread.sleep(20000);
}

@Test(priority = 6, groups = { "Sanity Regression" })

public static void verifySummaryStatsSetValueByNumericBetween() throws YamlException, InterruptedException {

	LOGGER.info(
			"---------------------------The test case verifySummaryStatsSetValueByNumericBetween is running-------------------------");
	readFile(configProperties.getProperty("verify.summarystats.between.subset"));
	testPlan.doPlanSetValueBetween(reporter);
	LOGGER.info(
			"---------------------------The test case verifySummaryStatsSetValueByNumericBetween is Finished-------------------------");

}
	
@Test(priority = 7, groups = { "Sanity Regression" })

public static void verifySummaryStatsSetValueByNumericGreaterThan() throws YamlException, InterruptedException {

	LOGGER.info(
			"---------------------------The test case verifySummaryStatsSetValueByNumericGreaterThan is running-------------------------");
	readFile(configProperties.getProperty("verify.summarystats.greaterthan.subset.value"));
	testPlan.doPlanSetValueGreaterThan(reporter);
	LOGGER.info(
			"---------------------------The test case verifySummaryStatsSetValueByNumericGreaterThan is Finished-------------------------");

}

@Test(priority = 8, groups = { "Sanity Regression" })

public static void verifySummaryStatsSetValueByNumericGreaterThanEqual() throws YamlException, InterruptedException {

	LOGGER.info(
			"---------------------------The test case verifySummaryStatsSetValueByNumericGreaterThanEqual is running-------------------------");
	readFile(configProperties.getProperty("verify.summarystats.greaterthanequal.subset.value"));
	testPlan.doPlanSetValueGreaterThanEqual(reporter);
	LOGGER.info(
			"---------------------------The test case verifySummaryStatsSetValueByNumericGreaterThanEqual is Finished-------------------------");

}



@Test(priority = 9, groups = { "Sanity Regression" })

public static void verifySummaryStatsMultipleSubset1OR()
		throws YamlException, InterruptedException, InstantiationException, IllegalAccessException {
	LOGGER.info(
			"-------------------------------The test case verifySummaryStatsMultipleSubset1OR is running-------------------------");
	readFile(configProperties.getProperty("verify.summarystats.multiplesubset1OR"));
	testPlan.doPlanMultipleSubset1OR(reporter);
	LOGGER.info(
			"--------------------------------The test case verifySummaryStatsMultipleSubset1OR is Finished-------------------------");
}


@Test(priority = 10, groups = { "Sanity Regression" })

public static void verifySummaryStatsSubset2() throws YamlException, InterruptedException {

	LOGGER.info(
			"---------------------------The test case verifySummaryStatsSubset2 is running-------------------------");
	readFile(configProperties.getProperty("verify.summaryStats.subset2"));
	testPlan.doPlanSubset2(reporter);
	LOGGER.info(
			"---------------------------The test case verifySummaryStatsSubset2 is Finished-------------------------");

}


@Test(priority = 11, groups = { "Sanity Regression" })
	public static void verifySummaryStatsMultipleSubset1Subset2AND()
			throws YamlException, InterruptedException, InstantiationException, IllegalAccessException {

		LOGGER.info(
				"-------------------------------The test case verifySummaryStatsMultipleSubset1Subset2AND is running-------------------------");
		readFile(configProperties.getProperty("verify.summarystats.multiplesubset1subset2AND"));
		//readFile(configProperties.getProperty("verify.summarystats.multiplesubset1subset2OR"));
		testPlan.doPlanMultipleSubset1Subset2And(reporter);
		LOGGER.info(
				"--------------------------------The test case verifySummaryStatsMultipleSubset1Subset2AND is Finished-------------------------");

	}


@Test(priority = 12, groups = { "Sanity Regression" })

public static void verifyMultipleSubset1Subset2OR() throws Exception {

	LOGGER.info(
			"---------------------------The test case verifyMultipleSubset1Subset2OR is running-------------------------");
	readFile(configProperties.getProperty("verify.summarystats.multiplesubset1subset2OR"));
	testPlan.verifyMultipleSubset1and2OR(reporter);
	LOGGER.info(
			"---------------------------The test case verifyMultipleSubset1Subset2OR is Finished-------------------------");
}


@Test(priority = 13, groups = { "Sanity Regression" })
public static void verifySummaryStatsMultipleSubset1AND()
		throws YamlException, InterruptedException, InstantiationException, IllegalAccessException {

	LOGGER.info(
			"-------------------------------The test case verifySummaryStatsMultipleSubset1AND is running-------------------------");
	readFile(configProperties.getProperty("verify.summarystats.multiplesubset1AND"));
	testPlan.doPlanMultipleSubsetAnd(reporter);
	LOGGER.info(
			"--------------------------------The test case verifySummaryStatsMultipleSubset1AND is Finished-------------------------");
	
}


*/	
//@Test(priority = 13, groups = { "Sanity Regression" })

public static void verifySavingSubset() throws YamlException, InterruptedException {

	LOGGER.info(
			"---------------------------The test case verifySavingSubset is running-------------------------");
	readFile(configProperties.getProperty("verify.save.subsetvalues"));
	testPlan.doPlanSaveSubset(reporter);
	LOGGER.info(
			"---------------------------The test case verifySavingSubset is Finished-------------------------");

}




//@Test(priority = 8, groups = { "Sanity Regression" })

	public static void verifySummaryStatsSubsetOneTwo() throws YamlException, InterruptedException {

		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSubsetOneTwo is running-------------------------");
		readFile(configProperties.getProperty("verify.summarystats.subset1subset2"));
		testPlan.doPlanSubsetOneTwo(reporter);
		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSubsetOneTwo is Finished-------------------------");

	}


//@Test(priority = 9, groups = { "Sanity" })
	public static void verifySummaryStatsExcludeFunctionality()
					throws YamlException, InterruptedException, InstantiationException, IllegalAccessException {
		LOGGER.info(
				"-------------------------------The test case verifySummaryStatsExcludeFunctionality is running-------------------------");
		readFile(configProperties.getProperty("verify.summaryStats.exclude"));
		testPlan.verifyExcludeFeature(reporter);
		LOGGER.info(
				"--------------------------------The test case verifySummaryStatsExcludeFunctionality is Finished-------------------------");

	}


//@Test(priority = 30, groups = { "Sanity" })
		public static void verifyMessageExcludeOnlySubset()
						throws YamlException, InterruptedException, InstantiationException, IllegalAccessException {
			LOGGER.info(
					"-------------------------------The test case verify SummaryStatsExcludeMessage is running-------------------------");
			readFile(configProperties.getProperty("verify.summaryStats.exclude.message"));
			testPlan.verifyMessageExclude(reporter);
			LOGGER.info(
					"--------------------------------The test case verify SummaryStatsExcludeMessage is Finshed-------------------------");

		}
/*
@Test(priority = 10, groups = { "Sanity Regression" })

	public static void verifySummaryStatsSetValueOperator() throws YamlException, InterruptedException {

		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSetValue is running------------------------");
		readFile(configProperties.getProperty("verify.summarystats.entersubset.value"));
		testPlan.doPlanSetValue(reporter);
		LOGGER.info(
				"---------------------------The test case verifySummaryStatsSetValue is Finished-------------------------");
	}

*/	
	
// Verify that the Stats reports page displays the graphs 

	
//@Test(priority = 11, groups = { "Sanity Regression" })

	public static void verifySummaryStatsGraphs() throws Exception {
		LOGGER.info(
				"-------------------------------The test case verify graphs  is running-------------------------------");
		readFile(configProperties.getProperty("verify.summaryStats.subset1"));
		testPlan.verifyGraphs(reporter);
		LOGGER.info(
				"--------------------------------The test case verifySummaryStats is Finshed-------------------------");
	}

	/*@Test(priority = 11, groups = { "Sanity Regression" })

	public static void verifyFractalisCorrelationAnalysis() throws Exception {
		LOGGER.info(
				"-------------------------------The test case to verify Correation Analysis  is running-------------------------------");
		readFile(configProperties.getProperty("verify.fractalis.correlation.analysis"));
		testPlan.verifyFractlisIntergrationCorrelationAnalysis(reporter);
		LOGGER.info(
				"--------------------------------The test case to verify Correation Analysis is Finshed-------------------------");
	}
	

	
*/
	
//@Test(priority = 11, groups = { "Sanity Regression" })

	public static void verifyFractalisScatterPlotCorrelationPerason() throws Exception {
		LOGGER.info(
				"-------------------------------The test case to verify Correation Analysis  Pearson is running-------------------------------");
		readFile(configProperties.getProperty("verify.fractalis.correlation.analysis"));
		testPlan.verifyFractlisIntergrationScatterPlotCorrelationPeasron(reporter);
		LOGGER.info(
				"--------------------------------The test case to verify Correation Analysis Pearson is Finshed-------------------------");
	}

//@Test(priority = 12, groups = { "Sanity Regression" })

	public static void verifyFractalisScatterPlotCorrelationSpearman() throws Exception {
		LOGGER.info(
				"-------------------------------The test case to verify Correation Analysis  Spearman running-------------------------------");
		readFile(configProperties.getProperty("verify.fractalis.correlation.analysis"));
		testPlan.verifyFractlisIntergrationScatterPlotCorrelationSpearman(reporter);
		LOGGER.info(
				"--------------------------------The test case to verify Correation Analysis Spearman Finshed-------------------------");
	}

	//@Test(priority = 13, groups = { "Sanity Regression" })

	public static void verifyFractalisScatterPlotCorrelationKendall() throws Exception {
		LOGGER.info(
				"-------------------------------The test case to verify Correation Analysis  Kendall running-------------------------------");
		readFile(configProperties.getProperty("verify.fractalis.correlation.analysis"));
		testPlan.verifyFractlisIntergrationScatterPlotCorrelationKendall(reporter);
		LOGGER.info(
				"--------------------------------The test case to verify Correation Analysis Kendall Finshed-------------------------");
	}

	
//@Test(priority = 12, groups = { "Sanity Regression" })

	public static void verifyFractalisBoxPlotAnalysis() throws Exception {
		LOGGER.info(
				"-------------------------------The test case verify FractalisBoxPlotAnalysis  is running-------------------------------");
		readFile(configProperties.getProperty("verify.fractalis.boxplot.analysis"));
		testPlan.verifyFractlisIntergrationBoxPlot(reporter);
		LOGGER.info(
				"--------------------------------The test case verify FractalisBoxPlotAnalysis is Finshed-------------------------");
}


//@Test(priority = 13, groups = { "Sanity Regression" })

	public static void verifyFractalisBoxPlotDataTransformation() throws Exception {
		LOGGER.info(
				"-------------------------------The test case verify FractalisBoxPlotAnalysis  is running-------------------------------");
		readFile(configProperties.getProperty("verify.fractalis.boxplot.analysis"));
		testPlan.verifyFractalisBoxPlotDataTransformation(reporter);
		LOGGER.info(
				"--------------------------------The test case verify FractalisBoxPlotAnalysis is Finshed-------------------------");
	}


//@Test(priority = 13, groups = { "Sanity Regression" })

	public static void verifyFractalisPrincipleComponentAnalysis() throws Exception {
		LOGGER.info(
				"-------------------------------The test case verify FractalisPrincipleComponentAnalysis  is running-------------------------------");
		readFile(configProperties.getProperty("verify.fractalis.principle.component.analysis"));
		testPlan.verifyFractlisIntergrationPrincipleComponentAnalysis(reporter);
		LOGGER.info(
				"--------------------------------The test case verify FractalisPrincipleComponentAnalysis is Finished-------------------------");
	}

@Test(priority = 14, groups = { "Sanity Regression" })

	public static void verifyFractalisSurvivalPlot() throws Exception {
		LOGGER.info(
				"-------------------------------The test case verify Fractalis SurvivalPlot  is running-------------------------------");
		readFile(configProperties.getProperty("verify.fractalis.survivalplot.analysis"));
		testPlan.verifyFractalisSurvivalPlot(reporter);
		LOGGER.info(
				"--------------------------------The test case verify Fractalis SurvivalPlot is Finished-------------------------");
	}
	
//@Test(priority = 22, groups = { "Sanity Regression" })

		public static void verifyFractalisSurvivalPlotEstimatorKaplanMeier() throws Exception {
			LOGGER.info(
					"-------------------------------The test case verify Fractalis SurvivalPlot Estimator KaplanMeier is running-------------------------------");
			readFile(configProperties.getProperty("verify.fractalis.survivalplot.analysis"));
			testPlan.verifyFractalisSurvivalPlotEstimatorKaplanMeier(reporter);
			LOGGER.info(
					"--------------------------------The test case verify Fractalis SurvivalPlot Estimator KaplanMeier is Finished-------------------------");
		}

@Test(priority = 21, groups = { "Sanity Regression" })
		public static void verifyFractalisSurvivalPlotEstimatorNelsonAalen() throws Exception {
			LOGGER.info(
					"-------------------------------The test case verify Fractalis SurvivalPlot Estimator NelsonAalen is running-------------------------------");
			readFile(configProperties.getProperty("verify.fractalis.survivalplot.analysis"));
			testPlan.verifyFractalisSurvivalPlotEstimatorNelsonAalen(reporter);
			LOGGER.info(
					"--------------------------------The test case verify Fractalis SurvivalPlot Estimator NelsonAalen is Finished-------------------------");
		}
		
//	@Test(priority = 15, groups = { "Sanity Regression" })

	public static void verifyFractalisHistogram() throws Exception {
		LOGGER.info(
				"-------------------------------The test case verify Fractalis Histogram  is running-------------------------------");
		readFile(configProperties.getProperty("verify.fractalis.histogram.analysis"));
		testPlan.verifyFractlisIntergrationHistogram(reporter);
		LOGGER.info(
				"--------------------------------The test case verify Fractalis Histogram is Finished-------------------------");
	}


/*	
//	 Verify that Laboratory terms loads the reports correctly 
@Test(priority = 12, groups = { "Sanity Regression" })

	public static void verifySummaryStatsLaboratory() throws Exception {

		LOGGER.info(
				"-------------------------------The test case verifySummaryStatsMultipleSubset1OR is running-------------------------");
		readFile(configProperties.getProperty("verify.summaryStats.laboratory"));
		testPlan.verifySummaryStatsLab(reporter);
		LOGGER.info(
				"--------------------------------The test case verifySummaryStatsMultipleSubset1OR is Finshed-------------------------");
	}

	
@Test(priority = 13, groups = { "Sanity Regression" })

	public static void verifyQuiestionnaire() throws Exception {

		LOGGER.info(
				"---------------------------The test case verifyQuiestionnaire is running-------------------------------");
		readFile(configProperties.getProperty("verify.summaryStats.questionnaire"));
		testPlan.verifySumStasQue(reporter);
		LOGGER.info(
				"---------------------------The test case verifyQuiestionnaire is Finshed------------------------------");
	}
*/

/*	
@Test(priority = 14, groups = { "Sanity Regression" })

	public static void verifyClearButton() throws Exception {

		LOGGER.info(
				"---------------------------The test case verifyClearButton is running-------------------------------");
		readFile(configProperties.getProperty("verify.window.title"));
		testPlan.verifyClear(reporter);
		LOGGER.info(
				"---------------------------The test case verifyClearButton is Finshed------------------------------");

	}
*/
//@Test(priority = 15, groups = { "Sanity Regression" })

	public static void verifySearch() throws YamlException, Exception {

		LOGGER.info("-------------------------------The test case verifySearch  is running-------------------------");
		readFile(configProperties.getProperty("verify.search.feature"));
		testPlan.doSearch(reporter);
		LOGGER.info("--------------------------------The test case verifySearch is Finshed-------------------------");
	}


//@Test(priority = 16, groups = { "Sanity Regression" })

	public static void verifySearchSpecialCharacters() throws YamlException, Exception {

		LOGGER.info("-------------------------------The test case verifySearch with SpecialCharacter  is running-------------------------");
		readFile(configProperties.getProperty("verify.search.feature.casesensitivity"));
		testPlan.doSearchSpecialChar(reporter);
		LOGGER.info("--------------------------------The test case verifySearch with SpecialCharacter is Finshed-------------------------");
	}
	
	
	
//@Test(priority = 17, groups = { "Sanity Regression" })
	public static void verifySearchCaseSensitivity() throws YamlException, Exception {

		LOGGER.info("-------------------------------The test case  verifySearch Case sensitivity  is running-------------------------");
		readFile(configProperties.getProperty("verify.search.feature.casesensitivity"));
		testPlan.doSearchCaseSensitivity(reporter);
		LOGGER.info("--------------------------------The test case verifySearch CaseSensitivity is Finshed-------------------------");
	}

/*	
@Test(priority = 18, groups = { "Sanity Regression" }, dependsOnMethods = { "verifySearch" })

	public static void verifySummaryStatSearch() throws YamlException, Exception {

		LOGGER.info(
				"-------------------------------The test case verifySummaryStatSearch is running-------------------------");
		readFile(configProperties.getProperty("verify.summaryStats.searchbysubject"));
		testPlan.doPlanSummaryStatSearch(reporter);
		LOGGER.info(
				"--------------------------------The test case verifySummaryStatSearch is Finished-------------------------");
	}

@Test(priority = 19)
	public static void verifyDeleteFunction() throws YamlException, InterruptedException {

		LOGGER.info("---------------------------------The test case verifyDelete is running-------------------------");
		readFile(configProperties.getProperty("verify.summaryStats.deletesubset"));
		testPlan.verifyDelete(reporter);
		LOGGER.info("---------------------------------The test case verifyDelete is Finished-------------------------");

	}

 @Test(priority = 20) 
	public static void verifyToolTips() throws  YamlException,InterruptedException

	{

		LOGGER.info("---------------------------------The test case verifyDelete is running-------------------------");
		readFile(configProperties.getProperty("verify.window.t"
				+ "itle"));
		testPlan.doTooltip(reporter);
		LOGGER.info("---------------------------------The test case verifyDelete is Finshed-------------------------");

	}

@Test(priority = 21) 
	public static void verifySearchTermLengthMessage() throws  Exception

	{

		LOGGER.info("---------------------------------The test case verify SearchTerm LengthMessage is running-------------------------");
		readFile(configProperties.getProperty("verify.summaryStats.searchbysubject.messages"));
		testPlan.doSearchLength(reporter);
		LOGGER.info("---------------------------------The test case verify verify SearchTerm LengthMessage is Finshed-------------------------");

	}

@Test(priority = 22) 
	public static void verifySubsetBoxAutoIncrement() throws  Exception

	{

		LOGGER.info("---------------------------------The test case verify SearchTerm LengthMessage is running-------------------------");
		readFile(configProperties.getProperty("verify.summaryStats.searchbysubject.messages"));
		testPlan.docheckSubsetBoxAutoIncrement(reporter);
		LOGGER.info("---------------------------------The test case verify verify SearchTerm LengthMessage is Finshed-------------------------");

	}

	
@Test(priority = 23) 
	public static void verifyTooltipsSummaryStatsGraphs() throws  Exception

	{

		LOGGER.info("---------------------------------The test case verify TooltipsSummaryStatsGraphs is running-------------------------");
		readFile(configProperties.getProperty("verify.summaryStats.searchbysubject.messages"));
		testPlan.docheckTooltipResultGraph(reporter);
		LOGGER.info("---------------------------------The test case verify TooltipsSummaryStatsGraphsis Finshed-------------------------");

	}

@Test(priority = 24) 
	public static void verifyShowNode() throws  Exception

	{

		LOGGER.info("---------------------------------The test case verify Show Node  is running-------------------------");
		readFile(configProperties.getProperty("verify.summaryStats.searchbysubject.messages"));
		testPlan.docheckShowNode(reporter);
		LOGGER.info("---------------------------------The test case verify Show Node is Finshed-------------------------");

	}
		
@Test(priority = 25) 
	public static void verifyLinkExtenalKnowledge() throws  Exception

	{

		LOGGER.info("---------------------------------The test case verify Show Node  is running-------------------------");
		readFile(configProperties.getProperty("verify.summaryStats.searchbysubject.messages"));
		testPlan.docheckExternalLink(reporter);
		LOGGER.info("---------------------------------The test case verify Show Node is Finshed-------------------------");

	}

@Test(priority = 25) 
	public static void enableDisableVariantPanel() throws  Exception

	{

		LOGGER.info("---------------------------------The test case verify Show Node  is running-------------------------");
		readFile(configProperties.getProperty("verify.summaryStats.searchbysubject.messages"));
		testPlan.docheckenableDisableVarPanel(reporter);
		LOGGER.info("---------------------------------The test case verify Show Node is Finshed-------------------------");

	}*/
		
//@AfterClass
	public void closeApplication() {

		reporter.doReport();
		testPlan.closeDriver();
		//System.out.println("Testing done");
		LOGGER.info(
				"===========================i2b2/TranSMART Test Automation is completed :Closing the Browser ===========================");

	}

	@SuppressWarnings("finally")
	private static Testplan initTestPlan(String testType, @SuppressWarnings("rawtypes") Map map) {
		Testplan newInstance = null;
		try {
			Class<?> resourceInterfaceClass = Class.forName(TESTPLANS + testType);

			newInstance = (Testplan) resourceInterfaceClass.newInstance();
			// newInstance.setTestPlan(map);
		} catch (SecurityException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			System.out.println(e);
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			return null;
		} finally {

			return newInstance;
		}
	}

	@SuppressWarnings("finally")
	private static Reporter initReporter(String reporterType) {
		Reporter newInstance = null;

		try {
			Class<?> resourceInterfaceClass = Class.forName(REPORTS + reporterType);
			newInstance = (Reporter) resourceInterfaceClass.newInstance();
		} catch (SecurityException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			return newInstance;
		}
	}

}
