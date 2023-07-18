package Com.tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class File_Upload {
	static ExtentTest logger;
	static ExtentReports report;
	@BeforeClass
	public void startTest() {
		report = new ExtentReports(System.getProperty("user.dir")+"/test-output/ExtentReportResult.html",true);	
	}
	@Test
	public void Upload_File() {
		logger=report.startTest("Upload_File");
		WebDriverManager.edgedriver().setup();
		WebDriver driver=new EdgeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://the-internet.herokuapp.com/upload");
     	driver.manage().window().maximize();
     	driver.findElement(By.xpath("//*[@id='file-upload']")).sendKeys("C:\\Users\\dzine\\OneDrive\\Desktop\\Selenium Revision.txt");
		driver.findElement(By.xpath("//input[@value='Upload']")).click();
		String msg=driver.findElement(By.xpath("(//h3)[1]")).getText();
		Assert.assertEquals(msg, "File Uploaded!");
		logger.log(LogStatus.PASS,"Test Case Passes");
		}
	@AfterMethod
	public void getResult(ITestResult result) {
		if(result.getStatus()== ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Test case Failed" + result.getName());
			logger.log(LogStatus.FAIL, "Test case Failed" + result.getThrowable());
		}else if(result.getStatus()==ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test case skipped" + result.getName());
			logger.log(LogStatus.SKIP, "Test case skipped" + result.getSkipCausedBy());
		}
		report.endTest(logger);
	}
	@AfterClass
	public void endTest() {
		report.flush();
		report.close();
	}
}
