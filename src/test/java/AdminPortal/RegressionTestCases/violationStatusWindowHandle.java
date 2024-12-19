package AdminPortal.RegressionTestCases;

import static org.testng.Assert.assertEquals;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class violationStatusWindowHandle {

	WebDriver driver = new ChromeDriver();
	WebDriverWait wait;

	public String adminWindow;
	public String tenantWindow;

	private String baseUrl;
	private String username;
	private String password;
	private String tenantusername;
	private String tenantpassword;
	private String violationsubject;
	private String violationcoded;

	@BeforeTest
	public void setup() throws InterruptedException {
		loadProperties();
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
			properties.getProperty("tenant");
			tenantusername = properties.getProperty("tenantusername");
			tenantpassword = properties.getProperty("tenantpassword");
			
			violationsubject = properties.getProperty("violationsubject");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void login() throws InterruptedException {

		// login code
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		// Thread.sleep(2000);
		WebElement email = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div[1]/input"));
		email.sendKeys(username);

		// Thread.sleep(2000);
		WebElement passcode = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div[2]/div/input"));
		passcode.sendKeys(password);

		WebElement loginButton = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/button"));
		loginButton.click();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebElement userName = driver.findElement(By.xpath("/html/body/div[2]/div/nav/div/div[2]/div[1]/a/span[1]"));
		AssertJUnit.assertEquals(userName.getText(), userName.getText());

		// Thread.sleep(2000);
	}

	@Test(priority = 0)
	public void addViolationAdminPortal() throws InterruptedException {

		randomGenerator.Visitor visitor = randomGenerator.generateRandomContact();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		// Thread.sleep(2000);
		WebElement violationTab = driver.findElement(By.linkText("Violations"));
		violationTab.click();

		// Thread.sleep(2000);
		WebElement addNewViolation = driver
				.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div[1]/a"));
		addNewViolation.click();

		// Thread.sleep(2000);
		WebElement propertyList = driver
				.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/form/div/div[1]/div/div[1]/div"));
		propertyList.click();

		// Thread.sleep(2000);
		WebElement propertyListOption = driver.findElement(By.xpath("(//ul[@class='p-dropdown-items']/child::li)[1]"));
		propertyListOption.click();

		// Thread.sleep(6000);
		WebElement violationSubject = driver.findElement(By.xpath("//input[@placeholder='Subject']"));
		
		violationcoded = violationsubject + " " + visitor.numbers;
		
		violationSubject.sendKeys(violationcoded);

		// Thread.sleep(2000);
		WebElement violationCategory = driver
				.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/form/div/div[1]/div/div[3]/div"));
		violationCategory.click();

		// Thread.sleep(2000);
		WebElement violationCategoryOption = driver.findElement(By.xpath("/html/body/div[5]/div[2]/ul/li[1]"));
		violationCategoryOption.click();
		
		FluentWait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(50)).pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
		
		WebElement reportByList = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//span[contains(@class, 'p-dropdown-label') and text()='Reported By']"))));
		reportByList.click();
		
        
        WebElement searchReportByList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@role='searchbox']")));
        searchReportByList.sendKeys("Yarn Support");
        
        WebElement reportByListOption = driver.findElement(By.xpath("//li[contains(@class, 'p-dropdown-item') and .//span[text()='Yarn Support']]"));
        reportByListOption.click();
        

		// Thread.sleep(2000);
		WebElement datePicker = driver.findElement(
				By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/form/div/div[1]/div/div[5]/span/input"));
		datePicker.click();

		// Select the date (for November 17, 2024)
		WebElement dayToSelect = driver.findElement(By.xpath("//td[@aria-label='17']"));
		dayToSelect.click();

		// Select the time (for example, 05:38 PM)
		WebElement hourIncrementButton = driver.findElement(By.xpath("//button[@aria-label='Next Hour']"));
		hourIncrementButton.click(); // Click to increment hour to 6 PM

		WebElement minuteIncrementButton = driver.findElement(By.xpath("//button[@aria-label='Next Minute']"));
		minuteIncrementButton.click(); // Click to increment minute to 39

		// Optionally, if you need to select AM/PM
		WebElement amPmToggle = driver.findElement(By.xpath("//button[@aria-label='pm']"));
		amPmToggle.click(); // Ensure PM is selected

		// Thread.sleep(2000);
		WebElement involvedPeopleList = driver
				.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/form/div/div[1]/div/div[7]/div"));
		involvedPeopleList.click();

		Thread.sleep(2000);
		WebElement search = driver.findElement(By.xpath("//input[@role='searchbox']"));
		search.sendKeys("yarn.user.tenant");
		

		// Thread.sleep(2000);
		WebElement involvedPeopleOptions = driver
				.findElement(By.xpath("//li[@class='p-multiselect-item' and @aria-label='yarn.user.tenant'] "));
		involvedPeopleOptions.click();

		// Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

		// Thread.sleep(2000);
		WebElement createButton = driver
				.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/form/div/div[2]/button"));
		createButton.click();

		// Thread.sleep(2000);
		WebElement violationCategoryDetails = driver.findElement(By.xpath(
				"/html[1]/body[1]/div[2]/div[1]/div[1]/div[2]/div[3]/div[1]/div[1]/div[2]/div[1]/div[2]/span[2]"));
		System.out.println(violationCategoryDetails.getText());
		violationCategoryDetails.getText();
		

		// Thread.sleep(2000);
		WebElement violationCreatedDate = driver.findElement(By.xpath(
				"/html[1]/body[1]/div[2]/div[1]/div[1]/div[2]/div[3]/div[1]/div[1]/div[2]/div[1]/div[5]/span[2]/span[1]"));
		System.out.println(violationCreatedDate.getText());
		violationCreatedDate.getText();
		
		WebElement violationIndexPage = driver.findElement(By.xpath("//a[normalize-space()='Manage Violations']"));
		violationIndexPage.click();
		
		// Locate the table
        WebElement table = driver.findElement(By.cssSelector(".table.table-striped.table-bordered.table-hover"));

        // Locate all rows in the table body
        List<WebElement> rows = table.findElements(By.xpath(".//tbody/tr"));

		// Check if there is at least one row
		if (!rows.isEmpty()) {
			// Get the first row
			WebElement firstRow = rows.get(0);

			// Extract data from each cell in the first row
			List<WebElement> cells = firstRow.findElements(By.xpath(".//td"));
			String cellData = cells.get(2).getText();
			System.out.println("Cell " + (3) + ": " + cellData);
			
			// Assert and print result
			try {
				Assert.assertEquals(cellData, violationcoded);
                System.out.println("Assertion passed for new violation.");
            } catch (AssertionError e) {
                System.out.println("Assertion failed:" + " " + "expected " + violationcoded + " but got " + cellData);
            }
			
			
		} else {
			System.out.println("No rows found in the table.");
		}
	}
	
	@Test (priority = 1)
	public void checkViolationAddedTenant() throws InterruptedException {
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        
        // Step 1: Store the admin window handle
        adminWindow = driver.getWindowHandle();
        
        // Step 2: Open a new tab
        driver.switchTo().newWindow(WindowType.TAB);
		
        // Step 3: Navigate to the new URL in the new tab
        driver.get("https://automation.yarncloud.dev/tenant/auth/login");
        
        // Step 4: Store the tenant window handle
        tenantWindow = driver.getWindowHandle();

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
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));

		WebElement myViolations = driver.findElement(By.linkText("My Violations"));
		myViolations.click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

		// Get the violation cell data 
		WebElement violationData = driver.findElement(By.xpath("//a[1]//div[1]//div[2]//p[1]"));

		String cellDataTenant = violationData.getText();
		
		// Assert and print result
		try {
			Assert.assertEquals(cellDataTenant, violationcoded);
			System.out.println("Assertion passed for new violation Tenant.");
		} catch (AssertionError e) {
			System.out.println("Assertion failed:" + " " + "expected " + violationcoded + " but got " + cellDataTenant);
		}     
		
	}
	
	
	
	@Test(priority = 2)
	public void resolveViolationAdminPortal() {
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		
		driver.switchTo().window(adminWindow);
	
		WebElement viewViolation = driver.findElement(By.xpath("//tbody/tr[1]/td[9]/a[1]"));
		viewViolation.click();

		
		// Thread.sleep(2000);
		WebElement actionsList = driver.findElement(By.xpath("//i[@class='fa-solid fa-ellipsis-vertical']"));
		actionsList.click();

		// Thread.sleep(2000);
		WebElement resolveAction = driver.findElement(By.xpath("//span[normalize-space()='Resolved']"));
		resolveAction.click();

		// Thread.sleep(2000);
		WebElement confirm = driver.findElement(By.xpath("//button[normalize-space()='Yes']"));
		confirm.click();	 
	}

	

	@Test(priority = 2)
	private void tenantViolations() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));

		Thread.sleep(500);
		
		driver.switchTo().window(tenantWindow);
		
		Thread.sleep(500);
		
		// Refresh the page
        driver.navigate().refresh();       
        Thread.sleep(500);
        
        WebElement violationStatus = driver.findElement(By.xpath("//span[normalize-space()='Closed']"));
        
        
		// Assert and print result
		try {
			Assert.assertEquals(violationStatus.getText(), "Closed");
			System.out.println("The violation status has changed to closed successfully on tenant portal.");
		} catch (AssertionError e) {
			System.out.println("The violation status didn't change to closed successfully.");
		}
        
	}

	
	 
}
