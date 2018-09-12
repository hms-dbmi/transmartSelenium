package dbmi.hms.harvard.edu.authentication;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthTypes {

	Map<String, String> authTypes = new HashMap<String, String>() {
		{
			put("HMS", "Harvard Medical School");

		}
	};

	public void doAuth(WebDriver driver, Map testPlan) throws InterruptedException {
		switch (testPlan.get("authmethod").toString()) {
		case "HMS":
			doHMSAuth(driver, testPlan);
		case "PUB":
			dopublicauth(driver, testPlan);
			break;
		case "GOOGLELOGIN":
			doGoogleLogin(driver, testPlan);
			break;
		case "ADMINLOGIN":
			doAdminLogin(driver, testPlan);
			break;

		}
	}

	private void doAdminLogin(WebDriver driver, Map testPlan) throws InterruptedException {
		try {

			String usernamebox = ".//*[@id='j_username']";
			String passwordbox = ".//*[@id='j_password']";
			String submitbutton = ".//*[@id='loginButton']";
			//String analyzeMenu = ".//*[@id='menuLinks']/tbody/tr/th[3]";
			String analyzeMenu =".//*[@class='menuVisited']";
			driver.findElement(By.xpath(usernamebox)).sendKeys(testPlan.get("username").toString());
			driver.findElement(By.xpath(passwordbox)).sendKeys(testPlan.get("password").toString());
			//Thread.sleep(10000);
			driver.findElement(By.xpath(submitbutton)).click();
			Thread.sleep(20000);
			if (driver.findElements(By.xpath(".//span[text()='Comparison']")).size() != 0) {
				System.out.println("The default page is Dataset Explorer");
							}
			else{
			driver.findElement(By.xpath(analyzeMenu)).click();
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doGoogleLogin(WebDriver driver, Map testPlan) throws InterruptedException {

		try {
			String googleLogin = ".//*[@id='a0-onestep']/div[2]/div/form/div[2]/div[2]/div[1]/a[2]/span";
			driver.findElement(By.xpath(googleLogin)).click();
			String usernamebox = ".//input[@type='email']";
			String nextButtonUser = ".//*[@id='identifierNext']";
			String passwordbox = ".//input[@type='password']";
			String nextButtonPass = ".//*[@id='passwordNext']";
			String submitbutton = "//*[@id='ctl00_ContentPlaceHolder1_SubmitButton']";

			driver.findElement(By.xpath(usernamebox)).sendKeys(testPlan.get("username").toString());
			driver.findElement(By.xpath(nextButtonUser)).click();
			Thread.sleep(20000);
			driver.findElement(By.xpath(passwordbox)).sendKeys(testPlan.get("password").toString());
			Thread.sleep(10000);
			driver.findElement(By.xpath(nextButtonPass)).click();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doHMSAuth(WebDriver driver, Map testPlan) {

		try {

			String usernamebox = ".//*[@id='ctl00_ContentPlaceHolder1_UsernameTextBox']";
			String passwordbox = "//*[@id='ctl00_ContentPlaceHolder1_PasswordTextBox']";
			String submitbutton = "//*[@id='ctl00_ContentPlaceHolder1_SubmitButton']";

			driver.findElement(By.xpath(usernamebox)).sendKeys(testPlan.get("username").toString());
			driver.findElement(By.xpath(passwordbox)).sendKeys(testPlan.get("password").toString());

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// driver.findElement(By.xpath("//*[@id='ctl00_ContentPlaceHolder1_PasswordTextBox']")).sendKeys(PASSWORD);
	}

	public void dopublicauth(WebDriver driver, Map testPlan) {
		try {

			String publicLogin = ".//*[@id='public']";
			driver.findElement(By.xpath(publicLogin)).click();

		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// driver.findElement(By.xpath("//*[@id='ctl00_ContentPlaceHolder1_PasswordTextBox']")).sendKeys(PASSWORD);
	}

	public Map<String, String> getAuthTypes() {
		return authTypes;
	}

	public void setAuthTypes(Map<String, String> authTypes) {
		this.authTypes = authTypes;
	}

}
