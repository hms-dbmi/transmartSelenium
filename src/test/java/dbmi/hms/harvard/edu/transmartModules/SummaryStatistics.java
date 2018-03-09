package dbmi.hms.harvard.edu.transmartModules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@SuppressWarnings("unused")
public class SummaryStatistics {
	
	private String genSummaryStatsButton = "//*[contains(text(), 'Generate Summary Statistics')]";
	
	public void runSummaryStatistics(WebDriver driver) {
	
		WebDriverWait wait = new WebDriverWait(driver, 49);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(genSummaryStatsButton)))).click();
		
	}

}
