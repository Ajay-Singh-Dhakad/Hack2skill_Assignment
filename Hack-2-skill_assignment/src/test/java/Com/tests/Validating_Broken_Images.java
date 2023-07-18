package Com.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Validating_Broken_Images{
	static ExtentTest logger;
	static ExtentReports report;
	@BeforeClass
	public void startTest() {
		report = new ExtentReports(System.getProperty("user.dir")+"/test-output/ExtentReportResult.html",true);	
	}
	@Test
	public void ValidatingBrokenImages() {
		logger=report.startTest("ValidatingBrokenImages");
		WebDriverManager.edgedriver().setup();
		WebDriver driver=new EdgeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://the-internet.herokuapp.com/broken_images");
     	driver.manage().window().maximize();
		List<WebElement> imglist=driver.findElements(By.tagName("img"));
		for(int i=0;i<imglist.size();i++) {
			Response res=RestAssured
					           .given()
					           .contentType("application/json")
					           .get(imglist.get(i).getAttribute("src"));
			                     
			if(res.getStatusCode()==200) {
				assertEquals(res.getStatusCode(),200);
				System.out.println(imglist.get(i).getAttribute("src") + " is not broken Image");
			}else {
				assertNotEquals(res.getStatusCode(),200);
				System.out.println(imglist.get(i).getAttribute("src") + " is broken Image");
				}
			  }
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
	


