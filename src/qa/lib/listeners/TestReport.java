package qa.lib.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class TestReport implements ITestListener {

	@Override
	public void onStart(ITestContext itc) {
		Reporter.log("Inicia la ejecución de prueba -> "+itc.getName()+"\n\n");
	}
	
	@Override
	public void onTestStart(ITestResult itr) {
		Reporter.log("Prueba iniciada -> "+itr.getName()+"\n");
	}
	
	@Override
	public void onTestSuccess(ITestResult itr) {
		Reporter.log("Prueba exitosa -> "+itr.getName()+"\n");
	}
	
	
	@Override
	public void onTestFailure(ITestResult itr) {
		Reporter.log("Prueba fallida -> "+itr.getName()+"\n");
	}
	
	
	@Override
	public void onTestSkipped(ITestResult itr) {
		Reporter.log("Prueba saltada -> "+itr.getName()+"\n");
	}
	
	@Override
	public void onFinish(ITestContext itc) {
		Reporter.log("Termina la ejecución de prueba -> "+itc.getName()+"\n");

	}
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult itr) {
		
	}
	
	
	
}
