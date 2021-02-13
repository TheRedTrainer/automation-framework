package qa.lib.base;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public class BaseTest extends ContextLoader{
	
	public static ThreadLocal<RemoteWebDriver> threadWebDriver = new ThreadLocal<RemoteWebDriver>();

	@BeforeMethod(groups = "config")
	public void setupWebDriver(ITestContext testContext)  {
		loadContext(testContext);
		//System.out.println("XML Test es "+ context.getCurrentXmlTest().toXml(""));
		String testBrowser = ContextLoader.getAttributeString(testContext.getCurrentXmlTest().getName()+".browser");
		//System.out.println("suite browser es "+ suiteBrowser + " y test browser es "+ testBrowser + " en la prueba "+context.getCurrentXmlTest().getName());
		//System.out.println("otro atributo del context es " + firefoxDriverPath + " pero por algun motivo desaparece "+suiteBrowser);
		
		if (remoteDriver) {
			if (testBrowser!=null)  
				setupRemoteDriver(testBrowser);
			else {
				setupRemoteDriver();
				threadWebDriver.get().setFileDetector(new LocalFileDetector());
			}
		} else {
			if (testBrowser!=null)
				setupLocalDriver(testBrowser);
			else
				setupLocalDriver();
		}
		threadWebDriver.get().manage().timeouts().implicitlyWait(findElementTimeout, TimeUnit.SECONDS);
		threadWebDriver.get().manage().timeouts().pageLoadTimeout(pageLoadTimeout, TimeUnit.SECONDS);

	}
	

	private void setupLocalDriver()
	{
		setupLocalDriver(suiteBrowser);
	}
	
	private void setupLocalDriver(String setupBrowser) {
		switch (setupBrowser.toLowerCase()) {
			case "ie":
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, "true");
				threadWebDriver.set(new InternetExplorerDriver());	
				break;
			case "edge":
				threadWebDriver.set(new EdgeDriver());	
				break;
			case "firefox":
				threadWebDriver.set(new FirefoxDriver());
				break;
			case "chrome":
				threadWebDriver.set(new ChromeDriver());
				break;
			case "safari":
				threadWebDriver.set(new SafariDriver());
				break;
			default:
				Assert.fail("Error al configurar webdriver. Navegador no soportado");
				break;
		}
		
	}
	
	private void setupRemoteDriver() {
		setupRemoteDriver(suiteBrowser);
	}
	
	private void setupRemoteDriver(String setupBrowser) {
		try {

			// No olvides levantar el nodo con el siguiente comando
			// java -Dwebdriver.chrome.driver="/home/jperez/webdrivers/chromedriver" -Dwebdriver.gecko.driver="/home/jperez/webdrivers/geckodriver" -jar selenium-sver-standalone-3.141.59.jar -role node -nodeConfig nodeConfig.json
			
			
			DesiredCapabilities capabilities = new DesiredCapabilities();
			switch (setupBrowser.toLowerCase()) {
				case "ie":
					capabilities = DesiredCapabilities.internetExplorer();	
					break;
				case "edge":
					capabilities = DesiredCapabilities.edge();	
					break;
				case "firefox":
					capabilities = DesiredCapabilities.firefox();
					capabilities.setBrowserName("firefox");
					capabilities.setVersion("0.29.0");
					break;
				case "chrome":
					ChromeOptions options = new ChromeOptions();
					options.setCapability("browserVersion","87.0.4280.88");
					options.setCapability("platform","LINUX");
					options.setCapability("browserName","chrome");
					threadWebDriver.set(new RemoteWebDriver(new URL(hub),options));
					return;					
				case "safari":
					capabilities = DesiredCapabilities.safari();
					break;
				default:
					Assert.fail("Error al configurar webdriver. Navegador no soportado");
					break;
			}
			capabilities.setPlatform(Platform.valueOf(platform));
			threadWebDriver.set(new RemoteWebDriver(new URL(hub),capabilities));

			
		} catch (IllegalArgumentException iae) {
			Assert.fail("Error al configurar webdriver. Plataforma no soportada");
		} catch (MalformedURLException mue) {
			Assert.fail("Error con el URL de hub proporcionado.");
		} catch (UnreachableBrowserException ube)
		{
			Assert.fail(ube.getMessage());
			Assert.fail("Error al tratar de conectar con el hub");
		} 
		
		
		
	}
	
	@After
	public void deleteAllCookies() {
		threadWebDriver.get().manage().deleteAllCookies();
	}
	
	@AfterMethod(groups = "config")
	public static void closeBrowser() {
		System.out.println("estoy cerrando los browsers");
		threadWebDriver.get().quit();
	}
	
	public static WebDriver getDriver() {
		return threadWebDriver.get();
	}
	

}
