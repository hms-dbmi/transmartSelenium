package org.hobsoft.docker.mavenchrome;



	import org.junit.After;
	import org.junit.Before;
	import org.junit.Test;
	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.chrome.ChromeOptions;

	import static org.hamcrest.CoreMatchers.containsString;
	import static org.junit.Assert.assertThat;

	/**
	 * Tests that Selenium can run on Chrome.
	 */
	public class BrowserTest
	{
		// ----------------------------------------------------------------------------------------------------------------
		// fields
		// ----------------------------------------------------------------------------------------------------------------
		
		private WebDriver driver;
		
		// ----------------------------------------------------------------------------------------------------------------
		// JUnit methods
		// ----------------------------------------------------------------------------------------------------------------
		
		@Before
		public void setUp()
		{
			ChromeOptions options = new ChromeOptions();
			options.addArguments("headless");
			
			driver = new ChromeDriver(options);
		}
		
		@After
		public void tearDown()
		{
			driver.close();
		}
		
		// ----------------------------------------------------------------------------------------------------------------
		// tests
		// ----------------------------------------------------------------------------------------------------------------
		
		@Test
		public void canGoogle()
		{
			driver.get("https://www.google.com/ncr");
			driver.findElement(By.name("q")).sendKeys("fish\n");
			
			assertThat(driver.getTitle(), containsString("fish"));
		}
	}

