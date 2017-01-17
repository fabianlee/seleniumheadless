/**
 * Simple program for running a headless Selenium test
 * Headless Chrome only supported on Linux using Xvfb
 */
package org.lee.fabian.seleniumheadless;

import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;


public class HeadlessTest {
	
	public static void main(String args[]) throws Exception {
		
		// user can choose which browser to use at command line: htmlunit, phantomjs, chrome
		String whichBrowser = "htmlunit";
		if(args.length>0) {
			whichBrowser = args[0];
		}
		System.out.println("User chose driver: " + whichBrowser);
		
		HeadlessTest test = new HeadlessTest(whichBrowser);
		
	} // main
	
	public HeadlessTest(String whichBrowser) throws Exception {
		
		WebDriver webDriver = getWebDriver(whichBrowser);
		
		// run test
		webDriver.get("http://www.google.com");
		// identify search textbox
		WebElement element = webDriver.findElement(By.name("q"));
		// do search
		element.sendKeys("fabianlee.org blog");
		element.submit();
		
		// wait and then check the page title of the results page
        Thread.sleep(2000);
        System.out.println("******************************************");
		System.out.println("Page title of results using " + whichBrowser + ": " + webDriver.getTitle());
		System.out.println("******************************************");

		// close browser
		webDriver.quit();
		
	}
	
	/**
	 * Cheap factory for retrieving WebDriver: HtmlUnit, PhantomJS, or Chrome 
	 * @param whichBrowser
	 * @return
	 */
	public WebDriver getWebDriver(String whichBrowser) throws Exception {
		
		// load file paths to phantomjs and chrome drivers
		Properties props = new Properties();
		props.load(this.getClass().getResourceAsStream("/path.properties"));

		// choose the driver
		WebDriver driver = null;
		if("chrome".toLowerCase().equals(whichBrowser)) {
			System.setProperty("webdriver.chrome.driver", props.getProperty("webdriver.chrome.driver"));
			driver = new ChromeDriver();
		}else if ("phantomjs".toLowerCase().equals(whichBrowser)) {
            System.setProperty("phantomjs.binary.path", props.getProperty("phantomjs.binary.path"));		
            driver = new PhantomJSDriver();	
		}else if ("htmlunit".toLowerCase().equals(whichBrowser)) {
			driver = new HtmlUnitDriver();
		}else {
			throw new Exception("Only valid webdrivers are: htmlunit|phantomjs|chrome");
		}
		
		return driver;
	}
	
	

} // class
