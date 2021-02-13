package qa.lib.fort.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import qa.lib.abstracts.AbstractPageObject;
import qa.lib.base.ContextLoader;

public class FortMainPage extends AbstractPageObject {

	// Define all web elements
	public WebElement compilationAndInstallationLink ;
	public WebElement loggingLink;


	
	public FortMainPage (WebDriver driver) {			
		super(driver);
		locatorsPropertiesFile = ContextLoader.getAttributeString("locators-properties-path")+"/fortmain.properties";
		loadPageLocatorsFromProperties();
		driver.get(ContextLoader.getAttributeString("home-url"));

		compilationAndInstallationLink=getWebElement(locatorsProperties.getProperty("home.compilationandinstallation.link"));
		loggingLink=getWebElement(locatorsProperties.getProperty("home.logging.link"));
		

	}
	
	
	@Override
	protected By getMainElementBy() {
		return this.menu;
	}
			
	protected By menu = By.className("site-aside");
	
	public void goToLoggingPage() {
		loggingLink.click();
	}
	
	public void goToCompilationAndInstallationPage() {
		compilationAndInstallationLink.click();
	}
	
}
