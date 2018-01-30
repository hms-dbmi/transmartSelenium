package dbmi.hms.harvard.edu.transmartModules;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@SuppressWarnings("unused")
public class SummaryStatistics {
//	private String genSummaryStatsButton = "//*[@id='ext-gen48']";
	//private String genSummaryStatsButton = "//*[@id='ext-gen49']";
	private String genSummaryStatsButton ="//*[contains(text(), 'Generate Summary Statistics')]";
	
	
	
	public void runSummaryStatistics(WebDriver driver){
		
		driver.findElement(By.xpath(genSummaryStatsButton)).click();
	}

}
