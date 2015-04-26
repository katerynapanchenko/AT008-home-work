package webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverFactory {
	private static final String CHROME = "chrome";
	
	private static WebDriver webDriver;
	private static DesiredCapabilities dc;
	
	private WebDriverFactory(){
		
	}
	
	public static WebDriver getInstance(String browser) throws Exception {
		if (webDriver == null) {
			if (CHROME.equals(browser)) {			
				setChromeDriver();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("test-type");
				// set some options
				dc = DesiredCapabilities.chrome();
				dc.setCapability(ChromeOptions.CAPABILITY, options);

				webDriver = new ChromeDriver(dc);
			} else
				throw new Exception("Invalid browser property set in configuration file");
		}
		return webDriver;
	}
	
	private static void setChromeDriver() throws Exception {
		String osName = System.getProperty("os.name").toLowerCase();
		StringBuffer chromeBinaryPath = new StringBuffer(
				"src/main/resources/");

		if (osName.startsWith("win")) {
			chromeBinaryPath.append("chromedriver.exe");
		} else if (osName.startsWith("lin")) {
			chromeBinaryPath.append("chromedriver");
		} else
			throw new Exception("Your OS is invalid for webdriver tests");

		System.setProperty("webdriver.chrome.driver", chromeBinaryPath.toString());
	}

	public static void killDriverInstance() throws Exception {
		webDriver.quit();
		webDriver = null;
	}
}
