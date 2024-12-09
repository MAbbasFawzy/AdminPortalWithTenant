package AdminPortal.RegressionTestCases;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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

public class vehicleAdminToTenant extends randomGenerator {

	WebDriver driver;
	WebDriverWait wait;

	private String baseUrl;
	private String username;
	private String password;
	private String tenant;
	private String tenantusername;
	private String tenantpassword;
	private String tenantviolation;
	private String depedentname;
	private String storedVehicleNumber;

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
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

		WebElement email = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div[1]/input"));
		email.sendKeys(username);

		WebElement passcode = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div[2]/div/input"));
		passcode.sendKeys(password);

		WebElement loginButton = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/button"));
		loginButton.click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement userName = driver.findElement(By.xpath("/html/body/div[2]/div/nav/div/div[2]/div[1]/a/span[1]"));
		AssertJUnit.assertEquals(userName.getText(), userName.getText());

	}

	@Test(priority = 0)
	public void openVehiclePage() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		WebElement vehicleMenuItem = driver
				.findElement(By.xpath("/html/body/div[2]/div/nav/div/div[3]/div[6]/div/div/div[2]/div[1]/div"));
		vehicleMenuItem.click();

		WebElement createVehicleButton = driver
				.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div[1]/a"));
		createVehicleButton.click();

		Thread.sleep(4000);
		WebElement property = driver.findElement(
				By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/div/div[1]/div/div[1]/div/div/div"));
		property.click();

		WebElement propertyOption = driver.findElement(By.xpath("/html/body/div[5]/div[2]/ul/li[1]"));
		propertyOption.click();

		WebElement ownerList = driver.findElement(
				By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/div/div[1]/div/div[2]/div/div/div[1]"));
		ownerList.click();

		WebElement ownerListOption = driver
				.findElement(By.xpath("//li[@class='p-dropdown-item' and @aria-label='Tenant']"));
		ownerListOption.click();

		WebElement continueButton = driver.findElement(
				By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/div/div[1]/div/div[3]/button"));
		continueButton.click();

	}

	@Test(priority = 1)
	public void enterVehicleData() throws InterruptedException {

		randomGenerator.Visitor visitor = randomGenerator.generateRandomContact();

		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

		WebElement tenantList = wait.until(ExpectedConditions.elementToBeClickable(By
				.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/div/div[2]/form/div[1]/div[1]/div/div")));
		tenantList.click();

		Thread.sleep(2000);
		WebElement searchBox = driver.findElement(By.xpath("//input[@role='searchbox']"));
		Thread.sleep(2000);
		searchBox.click();
		Thread.sleep(2000);
		searchBox.sendKeys("yarn.user.tenant");

		Thread.sleep(2000);
		WebElement tenantListOption = driver.findElement(By.xpath(
				"//li[@class='p-dropdown-item' and contains(@aria-label, 'yarn.user.tenant')]/span[@class='p-dropdown-item-label' and text()='yarn.user.tenant']"));
		tenantListOption.click();
		Thread.sleep(2000);

		WebElement carPlateNumber = driver.findElement(
				By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/div/div[2]/form/div[2]/div[1]/input"));
		storedVehicleNumber = visitor.letters + "-" + visitor.vehiclenumber;
		carPlateNumber.sendKeys(visitor.letters + "-" + visitor.vehiclenumber);

		WebElement carColor = driver.findElement(
				By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/div/div[2]/form/div[2]/div[2]/input"));
		carColor.sendKeys("Black");

		/*
		 * WebElement carModel = wait.until(ExpectedConditions.elementToBeClickable(
		 * By.xpath(
		 * "/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/div/div[2]/form/div[3]/div[1]/div"
		 * ))); carModel.click();
		 * 
		 * WebElement carModelOption = wait
		 * .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
		 * "/html/body/div[4]/div[2]/ul/li[1]"))); carModelOption.click();
		 */

		WebElement carYear = driver.findElement(By.xpath(
				"/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/div/div[2]/form/div[3]/div[2]/span/input"));
		carYear.sendKeys("2024");

		WebElement submit = driver.findElement(
				By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/div/div[2]/form/div[6]/button"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", submit);

		Thread.sleep(500);
		submit.click();

	}

	@Test(priority = 1)
	private void tenantLogin() throws InterruptedException { // login code

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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

	}

	@Test(priority = 2)
	private void checkVehicleAdded() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		WebElement vehiclesTab = driver.findElement(By.linkText("My Vehicles"));
		vehiclesTab.click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".grid")));

		// Locate the last item in the grid
		WebElement lastItem = driver.findElement(By.cssSelector(".grid .block:last-child"));

		// Extract the text you want
		String color = lastItem.findElement(By.cssSelector("h4.font-bold span:last-child")).getText();
		String plate = lastItem.findElement(By.xpath(".//label[text()='Plate']/following-sibling::h4")).getText();

		// Print the extracted data
		System.out.println("Latest Item:");
		System.out.println("Color: " + color);
		System.out.println("Plate: " + plate);

		Assert.assertEquals(plate, storedVehicleNumber);

	}
}
