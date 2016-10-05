package dbmi.hms.harvard.edu.transmartModules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchBySubject extends Module{
	private String searchBySubjectTab = ".//*[@id='ontPanel__ontFilterPanel']/a[2]/em/span/span";
	private String searchBox = ".//*[@id='ontsearchterm']";
	private String typeDropDown = ".//*[@id='tagtype']";
	private String searchButton = ".//*[@id='ontSearchButton']";
	
	public void doSelectNavigationTab(WebDriver driver){
		click(driver,driver.findElement(By.xpath(searchBySubjectTab)));
	}
	
	public void doSearch(WebDriver driver, String term){
		enterText(driver, searchBox, term);
		click(driver, driver.findElement(By.xpath(searchButton)));
	}
}
