package qa.lib.abstracts;


import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

import qa.lib.base.ContextLoader;


public abstract class AbstractPageObject {
	protected WebDriver driver;
	protected WebDriverWait wait;
	protected Properties locatorsProperties = new Properties();
	protected String locatorsPropertiesFile = "";
	protected int findElementTimeout;
	
	public AbstractPageObject(WebDriver driver)
	{
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 30);
		findElementTimeout = ContextLoader.getAttributeInteger("find-element-timeout");		
	}
	
	protected abstract By getMainElementBy();

	protected void loadPageLocatorsFromProperties() {
		try {
			locatorsProperties.load(new FileInputStream(new File(locatorsPropertiesFile)));
		} catch (FileNotFoundException fnfe) {
			Assert.fail("Archivo properties " +locatorsPropertiesFile+ " no fue encontrado");
		} catch (IOException ioe) {
			Assert.fail("Archivo properties " + locatorsPropertiesFile + " no pudo ser leído");
		}
	}
	
	protected void isLoaded() throws Error 
	{
		List<WebElement> mainElement = driver.findElements(getMainElementBy());
		assertTrue("El elemento principal \'" + getMainElementBy().toString()+ "\' no fue encontrado " + this.getClass().getSimpleName(), mainElement.size() > 0 );
		wait.until(ExpectedConditions.invisibilityOfElementLocated(getMainElementBy()));
	}
	
	
	protected WebElement getWebElement(By by)
	{
		return ((driver.findElements(by).size() > 0) ? driver.findElement(by) : null);
	}
	
	protected WebElement getWebElementContainingText(String text)
	{
		return ((driver.findElements(By.xpath("//*[contains(text(),'"+text+"')]")).size() > 0) ? driver.findElement(By.xpath("//*[contains(text(),'"+text+"')]")) : null);
	}
	
	protected WebElement getWebElement(String value)
	{
		WebElement webElement = null;
		driver.manage().timeouts().implicitlyWait(ContextLoader.getAttributeInteger("any-by-find-timeout"), TimeUnit.SECONDS);

		
		try {
			if (driver.findElements(By.xpath(value)).size() > 0 ) {
				webElement = driver.findElement(By.xpath(value));
			}
		} catch (InvalidSelectorException ise)
		{ }
		
		if (driver.findElements(By.id(value)).size() > 0 )
			webElement = driver.findElement(By.id(value));
		else if (driver.findElements(By.name(value)).size() > 0 )
			webElement = driver.findElement(By.name(value));
		else if (driver.findElements(By.className(value)).size() > 0 )
			webElement = driver.findElement(By.className(value));
		else if (driver.findElements(By.tagName(value)).size() > 0 )
			webElement = driver.findElement(By.tagName(value));
		else if (driver.findElements(By.linkText(value)).size() > 0 )
			webElement = driver.findElement(By.linkText(value));
		else if (driver.findElements(By.partialLinkText(value)).size() > 0 )
			webElement = driver.findElement(By.partialLinkText(value));
		else if (driver.findElements(By.cssSelector(value)).size() > 0 )
			webElement = driver.findElement(By.cssSelector(value));
		else if (driver.findElements(By.xpath(value)).size() > 0 )
			webElement = driver.findElement(By.xpath(value));
		else 
			webElement = null;
		
		driver.manage().timeouts().implicitlyWait(findElementTimeout, TimeUnit.SECONDS);
		return webElement;
	}

	public boolean isWebElementLoaded(String value) 
	{
		return (getWebElement(value) != null);
	}
	
	public boolean isWebElementContainingTextLoaded(String text) 
	{
		return (getWebElementContainingText(text) != null);
	}
	
	
	public boolean isWebElementLoaded(By by) 
	{
		return (getWebElement(by) != null);
	}
	
	
}
