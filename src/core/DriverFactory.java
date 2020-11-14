package core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

	private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<WebDriver>() {
		@Override
		protected synchronized WebDriver initialValue() {
			return initDriver();
		}
	};

	private DriverFactory() {

	}

	public static WebDriver getDriver() {
		return threadDriver.get();
	}

	public static WebDriver initDriver() {
		WebDriver driver = null;

		driver = new FirefoxDriver();

		return driver;
	}

	public static void killDriver() {

		WebDriver driver = getDriver();
		if (driver != null) {
			driver.quit();
			driver = null;
		}
		if (threadDriver != null) {
			threadDriver.remove();
		}

	}
}
