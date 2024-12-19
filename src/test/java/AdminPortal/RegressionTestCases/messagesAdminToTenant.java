package AdminPortal.RegressionTestCases;

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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class messagesAdminToTenant {

	WebDriver driver;
	WebDriverWait wait;

	private String baseUrl;
	private String username;
	private String password;
	private String tenant;
	private String tenantusername;
	private String tenantpassword;
	private String subject;
	private String messagebody;
	private String messagebodycoded;

	@BeforeTest
	public void setup() throws InterruptedException {
		loadProperties();
		initializeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.navigate().to(baseUrl);
		login();
	}

	@AfterTest
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
			baseUrl = properties.getProperty("base.url");
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

	private void login() throws InterruptedException {

		// login code
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));

		WebElement email = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div[1]/input"));
		email.sendKeys(username);

		WebElement passcode = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div[2]/div/input"));
		passcode.sendKeys(password);

		WebElement loginButton = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/button"));
		loginButton.click();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		WebElement userName = driver.findElement(By.xpath("/html/body/div[2]/div/nav/div/div[2]/div[1]/a/span[1]"));
		AssertJUnit.assertEquals(userName.getText(), userName.getText());

	}

	@Test(priority = 0)
	public void openMessagesPage() {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		WebElement messagesMenuItem = driver
				.findElement(By.xpath("/html/body/div[2]/div/nav/div/div[3]/div[2]/div/div/div[1]/div[1]/div"));
		messagesMenuItem.click();

		WebElement addNewMessage = driver.findElement(By.xpath("//a[@class='btn btn-block btn-primary']"));
		addNewMessage.click();

	}

	@Test(priority = 1)
	public void messageDataAddition() throws InterruptedException {
		
		randomGenerator.Visitor visitor = randomGenerator.generateRandomContact();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));

		Thread.sleep(2000);
		WebElement recipientList = driver.findElement(By.xpath(
				"//div[@class='p-multiselect p-component p-inputwrapper p-multiselect-chip w-full h-10 flex items-center']//div[@class='p-multiselect-label-container']"));
		recipientList.click();

		WebElement search = driver.findElement(By.xpath(
				"//input[@type='text' and @class='p-multiselect-filter p-inputtext p-component' and @role='searchbox']"));
		search.sendKeys(tenantusername);

		WebElement recipient = driver.findElement(By.xpath("//li[@aria-label='yarn.user.tenant - (Tenant) - (1)']"));
		recipient.click();
		recipientList.click();

		WebElement subject = driver.findElement(By.xpath("//input[@id='subject'] "));
		subject.sendKeys("Welcome to Yarn.");

		WebElement messageBody = driver.findElement(By.xpath("//div[@aria-label='Editor editing area: main']"));
		messagebody = "Welcome to Yarn." + " " + visitor.numbers;
		messagebodycoded = messagebody;
		messageBody.sendKeys(messagebody);
		
		
		// Thread.sleep(6000);
		WebElement fileUpload = driver.findElement(By.xpath(
				"//input[@accept='image/*,application/pdf,.docx,.doc']"));
		fileUpload.sendKeys("C:\\Users\\eng_m\\eclipse-workspace\\AutomationTestCases\\logo-white.png");
		
		Thread.sleep(2000);
		WebElement submitButton = driver.findElement(By.xpath("//span[normalize-space()='Send']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", submitButton);
		submitButton.click();
	}

	@Test(priority = 2)
	private void tenantLogin() throws InterruptedException { // login code

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		driver.navigate().to("https://automation.yarncloud.dev/tenant/auth/login");

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

		Thread.sleep(2000);
	}

	@Test(priority = 3)
	public void checkMessageTenantPortal() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		WebElement messageIcon = driver.findElement(By.xpath(
				"//nav[@class='fixed z-10 top-0 right-0 w-full border border-b shadow-xl bg-[var(--c1)] hidden sm:block print:!hidden']//a[1]//p[1]//*[name()='svg']"));
		messageIcon.click();

		WebElement messageInbox = driver.findElement(By.xpath("//div[@class='text-xs mb-1 flex gap-2']"));
		messageInbox.click();
		
		Thread.sleep(2000);
		WebElement messageBodyDetails = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/main[1]/div[1]/div[1]/div[2]/div[3]/div[1]/div[1]/div[2]/p[1]"));
		System.out.println(messageBodyDetails.getText());
		Assert.assertEquals(messageBodyDetails.getText(), messagebodycoded);
	}

}
