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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class broadcastAdminToTenant {

	WebDriver driver;
	WebDriverWait wait;

	private String baseUrl;
	private String username;
	private String password;
	private String tenant;
	private String tenantusername;
	private String tenantpassword;
	private String subject;
	private String broadcastcoded;

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
	public void openBroadcastePage() {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		WebElement broadcastMenuItem = driver
				.findElement(By.xpath("/html/body/div[2]/div/nav/div/div[3]/div[2]/div/div/div[2]/div[1]/div"));
		broadcastMenuItem.click();

		WebElement addNewBroadcast = driver
				.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[1]/div/div/div/a"));
		addNewBroadcast.click();

	}

	@Test(priority = 1)
	public void broadcastDataAddition() throws InterruptedException {
		
		randomGenerator.Visitor visitor = randomGenerator.generateRandomContact();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		WebElement propertyList = driver
				.findElement(By.xpath("//div[@class='p-multiselect p-component p-inputwrapper p-multiselect-chip w-full h-10 flex items-center property-dropdown w-fit']//div[@class='p-multiselect-label-container']"));
		propertyList.click();
		
		WebElement searchPropertyList = driver.findElement(By.xpath("//input[@role='searchbox']"));
		searchPropertyList.sendKeys("Property 1");

		Thread.sleep(2000);
		WebElement propertyListOption = driver.findElement(By.xpath("//li[contains(@class, 'p-multiselect-item') and contains(., 'Property 1')]"));
		propertyListOption.click();
		propertyList.click();

		WebElement tenantList = driver
				.findElement(By.xpath("//div[contains(@class, 'p-multiselect') and contains(., 'Tenant Selected')]"));
		tenantList.click();
		
		WebElement searchTenantList = driver.findElement(By.xpath("//input[@role='searchbox']"));
		searchTenantList.sendKeys("yarn.user.tenant");

		WebElement tenantListOption = driver.findElement(By.xpath("//li[contains(@class, 'p-multiselect-item') and contains(., 'yarn.user.tenant - (1)')]"));
		tenantListOption.click();
		tenantList.click();

		WebElement subjectBroadcast = driver
				.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/form/div/div[1]/div[4]/div/input"));
		subject = "New broadcast from admin" + " " + visitor.numbers;
		broadcastcoded = subject;
		subjectBroadcast.sendKeys(subject);

		WebElement broadcastBody = driver.findElement(
				By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/form/div/div[1]/div[7]/div[2]/div[2]/div"));
		broadcastBody.sendKeys(subject);

		WebElement submitButton = driver.findElement(
				By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/form/div/div[2]/div[2]/div[2]/div/button[2]"));
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
	private void checkBroadcast() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));

		WebElement broadcastIcon = driver.findElement(By.xpath("/html/body/div[1]/main/nav[1]/div/div[1]/div[2]/a[2]"));
		broadcastIcon.click();

		WebElement broadcastTenant = driver
				.findElement(By.xpath("/html/body/div[1]/main/div/div/div[3]/div[2]/div[3]/div/div/a"));
		broadcastTenant.click();

		WebElement broadcastBodyTenant = driver
				.findElement(By.xpath("/html/body/div[1]/main/div/div/div[2]/div[3]/div/div/div[2]/p"));
		broadcastBodyTenant.getText();

		Assert.assertEquals(broadcastcoded, broadcastBodyTenant.getText());
	}

}
