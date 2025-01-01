package AdminPortal.RegressionTestCases;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Set;
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

public class subscriptionActivation {

	WebDriver driver;
	WebDriverWait wait;

	private String baseUrl;
	private String username;
	private String password;
	private String tenanturl;
	private String tenantusername;
	private String tenantpassword;
	private String contactrequestsubject;
	private String subscriptionid;
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
	public void chooseServicesSearchAndSubscribe() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));

		WebElement subscribeButton = driver
				.findElement(By.xpath("//*[@id=\"__nuxt\"]/main/div/div/div[2]/div/div[1]/div/button[2]"));
		subscribeButton.click();

		Thread.sleep(4000);
		WebElement servicesList = driver.findElement(By.xpath("/html/body/div[4]/div/div[2]/form/div[1]/div/div"));
		servicesList.click();

		Thread.sleep(2000);
		WebElement serviceOption = driver.findElement(By.xpath("//li[@aria-label='Internet Subscription - 12 Months']"));
		serviceOption.click();

		Thread.sleep(4000);
		WebElement serviceCategoryList = driver.findElement(By.xpath("/html[1]/body[1]/div[4]/div[1]/div[2]/form[1]/div[3]/div[1]"));
		serviceCategoryList.click();

		Thread.sleep(4000);
		WebElement subCategoryList = driver.findElement(By.xpath("/html[1]/body[1]/div[5]/div[2]/ul[1]/li[1]"));
		subCategoryList.click();

		Thread.sleep(1000);
		WebElement serviceDescription = driver
				.findElement(By.xpath("/html/body/div[4]/div/div[2]/form/div[5]/textarea"));
		serviceDescription.sendKeys("I want to subscribe to a higher network connection.");

		Thread.sleep(2000);
		WebElement submitSubscriptionButton = driver
				.findElement(By.xpath("/html/body/div[4]/div/div[2]/form/div[8]/button"));
		submitSubscriptionButton.click();

		Thread.sleep(4000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		WebElement successMessage = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector(".p-toast[data-pc-name='toast'] .p-toast-message")));

		String alertMessageText = successMessage.getText();
		System.out.println("Alert message: " + alertMessageText);

	}
	
	@Test (priority = 2)
	private void checkSubscriptionDataTenantPortal() {
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		
		WebElement requestsPage = driver.findElement(By.linkText("My Requests"));
		requestsPage.click();
		
		WebElement subscriptions = driver.findElement(By.xpath("//a[@class='text-xs']"));
		subscriptions.click();
		
		// Locate the <a> element containing the subscription ID
        WebElement subscriptionLink = driver.findElement(By.xpath("//a[contains(@href, 'subscription/view?id=')]"));

        // Extract the subscription ID from the text
        String subscriptionText = subscriptionLink.getText();
        String subscriptionID = subscriptionText.split(" ")[0].replace("#", ""); // Extracting the ID part
        
        System.out.println(subscriptionID);
        
        subscriptionid = subscriptionID;
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
	public void openSubscriptionsPageAdmin() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));

		WebElement tenantRequests = driver.findElement(By.xpath(
				"/html[1]/body[1]/div[2]/div[1]/nav[1]/div[1]/div[3]/div[5]/div[1]/div[1]/div[3]/div[1]/div[1]/a[1]/span[1]/span[1]"));
		tenantRequests.click();

		WebElement subscriptions = driver.findElement(By.xpath(
				"/html[1]/body[1]/div[2]/div[1]/nav[1]/div[1]/div[3]/div[5]/div[1]/div[1]/div[3]/div[2]/div[1]/ul[1]/li[2]/div[1]/a[1]/span[1]"));
		subscriptions.click();

		WebElement viewSubscription = driver.findElement(
				By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div[2]/div/div[2]/table/tbody/tr[1]/td[13]/a"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", viewSubscription);
		viewSubscription.click();

		WebElement startButton = driver.findElement(
				By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[1]/div/div/div/div[2]/div[1]/button"));
		startButton.click();
		
		Thread.sleep(2000);

		// 2. Set start date (assuming it's the first calendar)
		WebElement startDateInput = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("/html[1]/body[1]/div[5]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/span[1]/input[1]")));
		startDateInput.click();

		WebElement startDayToSelect = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@aria-label='10']"))); // Replace with your desired date
		startDayToSelect.click();

		// 3. Set end date (assuming it's the second calendar)
		WebElement endDateInput = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("/html[1]/body[1]/div[5]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/span[1]/input[1]")));
		endDateInput.click();

		WebElement endDayToSelect = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@aria-label='15']"))); // Replace with your desired date
		endDayToSelect.click();

		// 4. Select billing recurrence (assuming it's the dropdown)
		WebElement billingRecurrenceDropdown = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("/html[1]/body[1]/div[5]/div[1]/div[2]/div[2]/div[3]/div[1]/div[1]/div[1]")));
		billingRecurrenceDropdown.click();

		WebElement billingRecurrenceOption = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//li[@role='option' and @aria-label='Monthly']"))); // Replace with your desired option
																														
		billingRecurrenceOption.click();
		WebElement activateButton = driver.findElement(By.xpath("//button[contains(@class, 'btn btn-primary')]"));
		activateButton.click();
		
		WebElement successMessage = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast > div:nth-child(1)")));

		String alertMessageText = successMessage.getText();
		System.out.println("Alert message: " + alertMessageText);
		
		WebElement subscriptionStatus = driver.findElement(By.xpath("//span[@class='badge badge-success']"));
		subscriptionStatus.getText();
		
		Assert.assertEquals(subscriptionStatus.getText(), "Active");

		Assert.assertEquals(alertMessageText, alertMessageText);
	}
	
	@Test (priority = 4)
	public void checkActivatedSubscriptionTenantPortal() throws InterruptedException {
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		driver.navigate().to("https://automation.yarncloud.dev/tenant/auth/login");

		WebElement requestsPage = driver.findElement(By.linkText("My Requests"));
		requestsPage.click();

		WebElement subscriptions = driver.findElement(By.xpath("//a[@class='text-xs']"));
		subscriptions.click();
		
		WebElement activeSubscriptions = driver.findElement(By.xpath("//button[normalize-space()='Active']"));
		activeSubscriptions.click();
		
		// Locate the <a> element containing the subscription ID
        WebElement subscriptionLink = driver.findElement(By.xpath("//a[contains(@href, 'subscription/view?id=')]"));

        // Extract the subscription ID from the text
        String subscriptionText = subscriptionLink.getText();
        String subscriptionID = subscriptionText.split(" ")[0].replace("#", ""); // Extracting the ID part

        // Locate the status element
        WebElement statusElement = driver.findElement(By.xpath("//span[contains(@class, 'text-sm') and contains(text(), 'Active')]"));

        // Extract the status text
        String status = statusElement.getText();

        // Print the subscription ID and status
        System.out.println("Subscription ID: " + subscriptionID);
        Assert.assertEquals(subscriptionID, subscriptionid);
        
        System.out.println("Status: " + status);
        Assert.assertEquals(status, "Active");
	}
}
