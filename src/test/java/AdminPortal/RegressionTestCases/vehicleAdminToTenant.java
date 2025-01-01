package AdminPortal.RegressionTestCases;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class vehicleAdminToTenant extends randomGenerator {

	WebDriver driver;
	WebDriverWait wait;

	public String adminWindow;
	public String tenantWindow;
	
	private String baseUrl;
	private String username;
	private String password;
	private String tenantusername;
	private String tenantpassword;
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

	/*
	 * @AfterTest public void tearDown() { if (driver != null) { driver.quit(); } }
	 */

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
			properties.getProperty("tenant");
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
	public void openVehiclePage() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
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
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));

		
		FluentWait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(50)).pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
		
		WebElement tenantList = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("/html[1]/body[1]/div[2]/div[1]/div[1]/div[2]/div[3]/div[1]/div[2]/div[1]/div[2]/form[1]/div[1]/div[1]/div[1]/div[1]"))));
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

		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		WebElement carPlateNumber = driver.findElement(
				By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/div/div[2]/form/div[2]/div[1]/input"));
		storedVehicleNumber = visitor.letters + "-" + visitor.vehiclenumber;
		Thread.sleep(1000);
		js.executeScript("arguments[0].scrollIntoView(true);", carPlateNumber);
		Thread.sleep(1000);
		carPlateNumber.sendKeys(visitor.letters + "-" + visitor.vehiclenumber);
		Thread.sleep(1000);
		
		WebElement carColor = driver.findElement(
				By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/div/div[2]/form/div[2]/div[2]/input"));
		carColor.sendKeys("Black");		

		WebElement carYear = driver.findElement(By.xpath(
				"/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/div/div[2]/form/div[3]/div[2]/span/input"));
		carYear.sendKeys("2024");

		WebElement submit = driver.findElement(
				By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/div/div[2]/form/div[6]/button"));
		
		js.executeScript("arguments[0].scrollIntoView(true);", submit);

		Thread.sleep(500);
		submit.click();
		
		Thread.sleep(5000);
		
		// Step 1: Store the admin window handle
        adminWindow = driver.getWindowHandle();
        
        // Step 2: Open a new tab
        driver.switchTo().newWindow(WindowType.TAB);
		
        // Step 3: Navigate to the new URL in the new tab
        driver.get("https://automation.yarncloud.dev/tenant/auth/login");
        
        // Step 4: Store the tenant window handle
        tenantWindow = driver.getWindowHandle();

	}

	@Test(priority = 1)
	private void tenantLogin() throws InterruptedException { // login code

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		driver.switchTo().window(tenantWindow);

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

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));

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
	
	@Test(priority = 3)
	public void checkDeletedVehicle() {
		
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		driver.switchTo().window(adminWindow);
		
		WebElement actionList = driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[1]/div[2]/div[3]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/button[1]/i[1]"));
		actionList.click();
		
		
		WebElement deleteVehicle = driver.findElement(By.xpath("//div[@class='dropdown-menu text-start show']//button[@class='dropdown-item text-danger py-2 px-3'][normalize-space()='Delete Vehicle']"));
		deleteVehicle.click();
		
		WebElement confirmDelete = driver.findElement(By.xpath("//button[normalize-space()='Delete']"));
		confirmDelete.click();
		
		// Locate the table rows
        List<WebElement> rows = driver.findElements(By.cssSelector(".table tbody tr"));

        boolean vehicleExists = false;

        // Iterate through the rows to check for the vehicle number
        for (WebElement row : rows) {
            // Get the vehicle number from the third column (Plate)
            String vehicleNumber = row.findElements(By.tagName("td")).get(2).getText();
            if (vehicleNumber.equals(storedVehicleNumber)) {
                vehicleExists = true;
                break;
            }
        }

        // Display message based on existence
        if (vehicleExists) {
            System.out.println("The vehicle number " + storedVehicleNumber + " still exists in the table.");
        } else {
            System.out.println("The vehicle number " + storedVehicleNumber + " has been deleted from the table.");
        }
		
	}
	
	@Test(priority = 4)
	public void checkDeletedVehicleTenant() throws InterruptedException {
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		driver.switchTo().window(tenantWindow);
		
		Thread.sleep(500);

		// Refresh the page
		driver.navigate().refresh();
		Thread.sleep(500);
		
		// Locate the vehicle entries in the grid
        List<WebElement> vehicleCards = driver.findElements(By.cssSelector(".grid .block .mb-3 h4"));

        boolean vehicleExists = false;

        // Iterate through the vehicle entries to check for the vehicle number
        for (WebElement vehicleCard : vehicleCards) {
            // Get the vehicle number from the card
            String vehicleNumber = vehicleCard.getText().trim();
            if (vehicleNumber.equals(storedVehicleNumber)) {
                vehicleExists = true;
                break;
            }
        }

        // Display message based on existence
        if (vehicleExists) {
            System.out.println("The vehicle number " + storedVehicleNumber + " still exists in the data.");
        } else {
            System.out.println("The vehicle number " + storedVehicleNumber + " has been deleted from the data.");
        }
		
		
	}
	
	
}
