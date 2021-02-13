package qa.lib.base;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.xml.XmlSuite;

public class ContextLoader {

	public static ITestContext context;
	
	protected String suiteBrowser;
	protected Boolean remoteDriver;
	protected String platform;
	protected String hub ;
	protected String ieDriverPath;
	protected String edgeDriverPath;
	protected String firefoxDriverPath;
	protected String chromeDriverPath;
	protected String safariDriverPath;
	protected Integer findElementTimeout;
	protected Integer pageLoadTimeout;	
	
	
	@BeforeSuite(groups = "config")
	public void loadContext(ITestContext testContext) {
		context = testContext;
		
		loadContextAttributes();
		loadSystemParameters();

	}
	
	private void loadContextAttributes() {
		XmlSuite xmlSuite = context.getSuite().getXmlSuite();
		Map<String, String> allParameters = xmlSuite.getAllParameters();
		
		System.out.println("Numero de atributos: " + xmlSuite.getAllParameters().size());
		for (Map.Entry<String, String> p : allParameters.entrySet()) {
			context.setAttribute(p.getKey(), p.getValue());

			System.out.println(p.getKey() + " : " + p.getValue());
		}
		
		suiteBrowser = ContextLoader.getAttributeString("suite-browser");
		remoteDriver = ContextLoader.getAttributeBoolean("remote-driver");
		platform = ContextLoader.getAttributeString("platform");
		hub = ContextLoader.getAttributeString("hub");
		ieDriverPath = ContextLoader.getAttributeString("ie-driver");
		edgeDriverPath = ContextLoader.getAttributeString("edge-driver");
		firefoxDriverPath = ContextLoader.getAttributeString("firefox-driver");
		chromeDriverPath = ContextLoader.getAttributeString("chrome-driver");
		safariDriverPath = ContextLoader.getAttributeString("safari-driver");
		findElementTimeout = ContextLoader.getAttributeInteger("find-element-timeout");
		pageLoadTimeout = ContextLoader.getAttributeInteger("page-load-timeout");
		
	}
	
	private void loadSystemParameters()
	{
		System.setProperty("webdriver.ie.driver", ieDriverPath);
		System.setProperty("webdriver.edge.driver", edgeDriverPath);
		System.setProperty("webdriver.safari.driver", safariDriverPath);
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
		
	}
	
	
	public static String getAttributeString(String attribute) {
		return (context.getAttribute(attribute) != null) ? context.getAttribute(attribute).toString() : null;
	}

	public static boolean getAttributeBoolean(String attribute) {
		return (context.getAttribute(attribute) != null) ? Boolean.parseBoolean(context.getAttribute(attribute).toString()) : null;
	}
	
	public static int getAttributeInteger(String attribute) {
		return (context.getAttribute(attribute) != null) ? Integer.parseInt(context.getAttribute(attribute).toString()) : null;
	}
	
	public static double getAttributeDouble(String attribute) {
		return (context.getAttribute(attribute) != null) ? Double.parseDouble(context.getAttribute(attribute).toString()) : null;
	}
	
	public static List<String> getAttributeCSV(String attribute){
		return (context.getAttribute(attribute) != null) ? Arrays.asList(context.getAttribute(attribute).toString().split(",")) : null;
	}
	
}
