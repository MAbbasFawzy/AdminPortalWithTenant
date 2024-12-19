package AdminPortal.RegressionTestCases;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class requestActivation {
	
	WebDriver driver;
	WebDriverWait wait;

	private String baseUrl;
	private String username;
	private String password;
	private String tenanturl;
	private String tenantusername;
	private String tenantpassword;
	private String contactrequestsubject;
	private String requestid;
	private String status;

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

	public void login() throws InterruptedException {
		
		// login code

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
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
	public void chooseServicesSearch() throws InterruptedException {
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));

		WebElement servicesButton = driver
				.findElement(By.xpath("//*[@id=\"__nuxt\"]/main/nav[1]/div/div[2]/div[1]/a[2]"));
		servicesButton.click();

		WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"__nuxt\"]/main/div/div/div[2]/div/div/input"));
		Thread.sleep(2000);
		searchBox.click();
		Thread.sleep(2000);
		searchBox.sendKeys("Internet");

		Thread.sleep(6000);
		WebElement serviceTypeButton = driver.findElement(By.xpath("//*[@id=\"__nuxt\"]/main/div/div/div[3]/div"));
		serviceTypeButton.click();

		Thread.sleep(6000);
		WebElement serviceTitle = driver
				.findElement(By.xpath("//*[@id=\"__nuxt\"]/main/div/div/div[2]/div/div[1]/h4/span[2]"));
		assertEquals(serviceTitle.getText(), serviceTitle.getText());

	}
	
	@Test(priority = 1)
	
	public void requestService() throws InterruptedException {
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		
		Thread.sleep(4000);
		WebElement requestServiceButton = driver
				.findElement(By.xpath("//*[@id=\"__nuxt\"]/main/div/div/div[2]/div/div[1]/div/button[1]"));
		requestServiceButton.click();

		Thread.sleep(4000);
		WebElement servicesDropDown = driver.findElement(By.xpath("/html/body/div[4]/div/div[2]/form/div[1]/div/div"));
		servicesDropDown.click();

		Thread.sleep(4000);
		WebElement serviceOption = driver.findElement(By.xpath("//*[@id=\"pv_id_8_0\"]"));
		serviceOption.click();

		Thread.sleep(4000);
		WebElement serviceCategory = driver.findElement(By.xpath("/html/body/div[4]/div/div[2]/form/div[2]/div/div"));
		serviceCategory.click();

		Thread.sleep(4000);
		WebElement serviceCategoryOption = driver.findElement(By.xpath("/html/body/div[5]/div[2]/ul/li[1]"));
		serviceCategoryOption.click();

		Thread.sleep(4000);
		WebElement description = driver.findElement(By.xpath("//textarea[@class='w-full']"));
		description.sendKeys("Testing description new request is added.");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement dateInput = driver
				.findElement(By.xpath("//input[@role='combobox' and @class='p-inputtext p-component']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateInput);

		// Click on the next button to navigate to November if needed
		WebElement nextButton = driver.findElement(By.className("p-datepicker-next"));
		nextButton.click(); // Click if you need to go to the next month

		// Wait for the date picker to update
		Thread.sleep(4000); // Wait for 1 second

		// Select the date (30)
		WebElement dateToSelect = driver.findElement(By.xpath("//td[@aria-label='30']"));
		dateToSelect.click();

		// Wait for the time picker to be visible (if necessary)
		Thread.sleep(4000); // Wait for 1 second

		// Set the hour (4 PM)
		WebElement hourIncrementButton = driver
				.findElement(By.xpath("//div[@class='p-hour-picker']//button[@aria-label='Next Hour']"));
		for (int i = 0; i < 4; i++) { // Increment to 4 PM
			hourIncrementButton.click();
		}

		// Set the minutes (33)
		WebElement minuteIncrementButton = driver
				.findElement(By.xpath("//div[@class='p-minute-picker']//button[@aria-label='Next Minute']"));
		for (int i = 0; i < 33; i++) { // Increment to 33 minutes
			minuteIncrementButton.click();
		}

		// Set AM/PM to PM
		WebElement ampmButton = driver.findElement(By.xpath("//div[@class='p-ampm-picker']//button[@aria-label='pm']"));
		ampmButton.click();

		Thread.sleep(4000);
		WebElement submitRequest = driver.findElement(By.xpath("/html/body/div[4]/div/div[2]/form/div[7]/button"));
		submitRequest.click();

		
		WebElement successMessage = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast > div:nth-child(1)")));

		String alertMessageText = successMessage.getText();
		System.out.println("Alert message: " + alertMessageText);

		assertEquals(alertMessageText, alertMessageText);
	}
	
	@Test (priority = 2)
	private void checkRequestsDataTenantPortal() {
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		
		WebElement requestsPage = driver.findElement(By.linkText("My Requests"));
		requestsPage.click();
		
		// Locate the <a> element containing the subscription ID
        WebElement requestLink = driver.findElement(By.xpath("//a[contains(@href, 'request/view?id=')]"));

        // Extract the subscription ID from the text
        String requestText = requestLink.getText();
        String requestID = requestText.split(" ")[0].replace("#", ""); // Extracting the ID part
        
        System.out.println(requestID);
        
        requestid = requestID;
	}
	
	@Test(priority = 3)
	private void loginToAdmin() throws InterruptedException { // login code

		// login code

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		
		driver.navigate().to("https://automation.yarncloud.dev/auth/login");
		Thread.sleep(4000);
		WebElement email = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div[1]/input"));
		email.sendKeys(username);

		Thread.sleep(4000);
		WebElement passcode = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div[2]/div/input"));
		passcode.sendKeys(password);

		WebElement loginButton = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/button"));
		loginButton.click();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		WebElement userName = driver.findElement(By.xpath("/html/body/div[2]/div/nav/div/div[2]/div[1]/a/span[1]"));
		AssertJUnit.assertEquals(userName.getText(), userName.getText());

		Thread.sleep(2000);
	}
	
	@Test(priority = 3)
	public void openRequestsPageAdmin() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));

		WebElement tenantRequests = driver.findElement(By.xpath(
				"/html[1]/body[1]/div[2]/div[1]/nav[1]/div[1]/div[3]/div[5]/div[1]/div[1]/div[3]/div[1]/div[1]/a[1]/span[1]/span[1]"));
		tenantRequests.click();

		WebElement requests = driver.findElement(By.xpath(
				"//span[normalize-space()='Requests']"));
		requests.click();

		WebElement viewRequest = driver.findElement(
				By.xpath("//tbody/tr[1]/td[13]/a[1]"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", viewRequest);
		viewRequest.click();

		WebElement startButton = driver.findElement(
				By.xpath("//button[normalize-space()='Start']"));
		startButton.click();
		
		WebElement confirmStart = driver.findElement(By.xpath("//button[@class='swal2-confirm swal2-styled swal2-default-outline']"));
		confirmStart.click();
				
		WebElement successMessage = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast > div:nth-child(1)")));

		String alertMessageText = successMessage.getText();
		System.out.println("Alert message: " + alertMessageText);
		
		WebElement requestStatus = driver.findElement(By.xpath("//span[@class='badge badge-success']"));
		requestStatus.getText();
		
		Assert.assertEquals(requestStatus.getText(), "In Progress");

		Assert.assertEquals(alertMessageText, alertMessageText);
		
		Thread.sleep(4000);
		WebElement actionList = driver.findElement(By.xpath("//div[@class='dropdown']//button[@id='dropdownMenuButton']"));
		actionList.click();
		
		WebElement completeRequest = driver.findElement(By.xpath("//button[normalize-space()='Complete']"));
		completeRequest.click();
		
		WebElement confirmComplete = driver.findElement(By.xpath("//button[@type='button'][normalize-space()='Complete']"));
		confirmComplete.click();
		
		Thread.sleep(4000);
		requestStatus.getText();
		Assert.assertEquals(requestStatus.getText(), "Completed");
	}
	
	@Test(priority = 4)
	public void checkRequestStatusTenantPortal() {
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		driver.navigate().to("https://automation.yarncloud.dev/tenant/auth/login");
		
		WebElement requestsPage = driver.findElement(By.linkText("My Requests"));
		requestsPage.click();
		
		WebElement closedRequests = driver.findElement(By.xpath("//button[normalize-space()='Closed']"));
		closedRequests.click();
		
		// Locate the <a> element containing the request ID
        WebElement requestLink = driver.findElement(By.xpath("//a[contains(@href, 'request/view?id=')]"));

        // Extract the request ID from the text
        String requestText = requestLink.getText();
        String requestID = requestText.split(" ")[0].replace("#", ""); // Extracting the ID part

        // Locate the status element
        WebElement statusElement = driver.findElement(By.xpath("//span[contains(@class, 'text-sm') and contains(text(), 'Completed')]"));

        // Extract the status text
        String status = statusElement.getText();

        // Print the request ID and status
        System.out.println("Request ID: " + requestID);
        
        Assert.assertEquals(requestID, requestid);
        
        System.out.println("Status: " + status);
        
        Assert.assertEquals(status, "Completed");
	}
}
