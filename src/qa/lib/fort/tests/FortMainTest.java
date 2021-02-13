package qa.lib.fort.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.beust.jcommander.Parameter;

import qa.lib.base.BaseTest;
import qa.lib.base.ContextLoader;
import qa.lib.fort.pages.FortMainPage;
import qa.lib.listeners.TestReport;
import qa.lib.utils.CSVDataProvider;

@Listeners(TestReport.class)
public class FortMainTest extends BaseTest {
	public FortMainTest() {
		super();
	}
	
	
	@Test(groups = "test",dataProvider = "CSVDataProvider", dataProviderClass = CSVDataProvider.class)
	public void displayLoggingInfoTest(String expectedText) {		
		FortMainPage mainPage = new FortMainPage(threadWebDriver.get());
		mainPage.goToLoggingPage();
		try
		{
			Assert.assertTrue(mainPage.isWebElementContainingTextLoaded(expectedText));
			Reporter.log("Se mostró la información de Logging exitosamente"+"\n");
		} catch (AssertionError ae)
		{
			Assert.fail("No se encontró el elemento "+expectedText);
		}
	}
	
	
	@Test(groups="test",dataProvider = "CSVDataProvider", dataProviderClass = CSVDataProvider.class)
	public void displayCompilationAndInstallationInfoTest(String expectedText) {
		FortMainPage mainPage = new FortMainPage(threadWebDriver.get());
		mainPage.goToCompilationAndInstallationPage();
		try {
				Assert.assertTrue(mainPage.isWebElementContainingTextLoaded(expectedText));
				Reporter.log("Se mostró la información de Compilación e instalación exitosamente"+"\n");
		} catch (AssertionError ae) {
			Assert.fail("No se encontró el elemento "+expectedText);
		}
	}
}
