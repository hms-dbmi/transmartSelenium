package dbmi.hms.harvard.edu.transmartModules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SummaryStatistics {
	private String genSummaryStatsButton = "//*[@id='ext-gen48']";
	
	public void runSummaryStatistics(WebDriver driver){
		driver.findElement(By.xpath(genSummaryStatsButton)).click();
	}

}
