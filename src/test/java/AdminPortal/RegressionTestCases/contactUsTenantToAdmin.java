package AdminPortal.RegressionTestCases;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class contactUsTenantToAdmin {

	WebDriver driver;
	WebDriverWait wait;

	private String baseUrl;
	private String username;
	private String password;
	private String tenant;
	private String tenanturl;
	private String tenantusername;
	private String tenantpassword;
	private String contactrequestsubject;

	@BeforeTest
	public void setup() throws InterruptedException {
		loadProperties();
		initializeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.navigate().to(tenanturl);
		login();
	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	private void loadProperties() {
		Properties properties = new Properties();
		try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
			if (input == null) {
				System.out.println("Sorry, unable to find config.properties");
				return;
			}
			properties.load(input);
			tenanturl = properties.getProperty("tenant.url");
			username = properties.getProperty("username");
			password = properties.getProperty("password");
			tenant = properties.getProperty("tenant");
			tenantusername = properties.getProperty("tenantusername");
			tenantpassword = properties.getProperty("tenantpassword");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initializeDriver() {
		Properties properties = new Properties();
		try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
			properties.load(input);
			String browser = properties.getProperty("browser");

			if ("chrome".equalsIgnoreCase(browser)) {

				driver = new ChromeDriver();
			} else if ("firefox".equalsIgnoreCase(browser)) {

				driver = new FirefoxDriver();
			} else {
				System.out.println("Unsupported browser: " + browser);
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void login() throws InterruptedException { // login code

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebElement email = driver.findElement(By.xpath("/html/body/div[1]/main/div/div/div[3]/form/div[1]/input"));
		email.sendKeys(tenantusername);

		WebElement passcode = driver
				.findElement(By.xpath("/html/body/div[1]/main/div/div/div[3]/form/div[2]/div/input"));
		passcode.sendKeys(tenantpassword);

		WebElement loginButton = driver
				.findElement(By.xpath("//*[@id=\"__nuxt\"]/main/div/div/div[3]/form/div[3]/button"));
		loginButton.click();

		WebElement userName = driver.findElement(By.xpath("//*[@id=\"__nuxt\"]/main/nav[1]/div/div[1]/div[2]/span[2]"));
		AssertJUnit.assertEquals(tenantusername, userName.getText());

	}

	@Test(priority = 0)
	public void testRedirectToContactUsPage() {

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		WebElement contactUsTab = driver.findElement(By.linkText("Contact us"));
		contactUsTab.click();

		WebElement formTitle = driver.findElement(By.xpath("//*[@id=\"__nuxt\"]/main/div/div/div[2]/h4"));
		formTitle.getText();

		assertEquals("Contact us", formTitle.getText());

	}

	@Test(priority = 1)
	public void enterDataInContactUsForm() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);

		Thread.sleep(6000);

		WebElement contactUsMessageCategory = driver
				.findElement(By.xpath("/html/body/div[1]/main/div/div/div[2]/form/div[1]/div/span"));
		contactUsMessageCategory.click();

		Thread.sleep(2000);

		WebElement contactUsCategory = driver.findElement(By.xpath("//*[@id=\"pv_id_2_0\"]"));
		contactUsCategory.click();

		Thread.sleep(2000);
		WebElement messageSubject = driver
				.findElement(By.xpath("//*[@id=\"__nuxt\"]/main/div/div/div[2]/form/div[2]/input"));
		messageSubject.sendKeys("Solve a problem for me?");

		Thread.sleep(2000);
		WebElement messageBody = driver
				.findElement(By.xpath("//*[@id=\"__nuxt\"]/main/div/div/div[2]/form/div[3]/textarea"));
		contactrequestsubject = "Hi, I need to contact with the system admin to solve a problem for me?";
		messageBody.sendKeys(contactrequestsubject);

		Thread.sleep(4000);
		driver.findElement(By.xpath("/html/body/div[1]/main/div/div/div[2]/form/div[4]/div/div/input"))
				.sendKeys("C:\\Users\\eng_m\\eclipse-workspace\\AutomationTestCases\\logo-white.png");

		Thread.sleep(2000);
		WebElement submitButton = driver
				.findElement(By.xpath("//*[@id=\"__nuxt\"]/main/div/div/div[2]/form/div[5]/button"));
		submitButton.click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement successMessage = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast > div:nth-child(1)")));

		String alertMessageText = successMessage.getText();
		System.out.println("Alert message: " + alertMessageText);

		assertEquals(alertMessageText, alertMessageText);
	}

	@Test(priority = 2)
	private void loginToAdmin() throws InterruptedException { // login code

		// login code
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.navigate().to("https://automation.yarncloud.dev/auth/login");
		Thread.sleep(4000);
		WebElement email = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div[1]/input"));
		email.sendKeys(username);

		Thread.sleep(4000);
		WebElement passcode = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div[2]/div/input"));
		passcode.sendKeys(password);

		WebElement loginButton = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/button"));
		loginButton.click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement userName = driver.findElement(By.xpath("/html/body/div[2]/div/nav/div/div[2]/div[1]/a/span[1]"));
		AssertJUnit.assertEquals(userName.getText(), userName.getText());

		Thread.sleep(2000);
	}
	
	@Test (priority = 3)
	private void contactRequestCheck() {
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		WebElement tenantRequests = driver.findElement(By.xpath("//span[contains(text(),'Tenant Requests')]"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", tenantRequests);
		tenantRequests.click();
		
		
		WebElement contactRequests = driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/nav[1]/div[1]/div[3]/div[5]/div[1]/div[1]/div[3]/div[2]/div[1]/ul[1]/li[3]/div[1]/a[1]"));
		contactRequests.click();
		
		WebElement contactRequestSubject = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div/div[1]/table/tbody/tr[2]/td[3]/div"));
		contactRequestSubject.getText();
		
		Assert.assertEquals(contactrequestsubject, contactRequestSubject.getText());
	}
	

}
