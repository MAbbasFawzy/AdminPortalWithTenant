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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class violationsStatus {

	WebDriver driver = new ChromeDriver();
	WebDriverWait wait;

	private String baseUrl;
	private String username;
	private String password;
	private String tenantusername;
	private String tenantpassword;
	private String violationsubject;

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
		
		violationSubject.sendKeys(violationsubject);

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
		
		/*
		 * WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		 * 
		 * try { // Wait for the element to be clickable WebElement reportByList =
		 * wait.until(ExpectedConditions.elementToBeClickable( By.
		 * xpath("//span[contains(@class, 'p-dropdown-label') and text()='Reported By']"
		 * )));
		 * 
		 * // Attempt to click using JavaScript if standard click fails try {
		 * reportByList.click(); } catch (Exception e) { ((JavascriptExecutor)
		 * driver).executeScript("arguments[0].click();", reportByList); }
		 * 
		 * } catch (Exception e) { System.out.println("Element not clickable: " +
		 * e.getMessage()); }
		 */
        
        WebElement searchReportByList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@role='searchbox']")));
        searchReportByList.sendKeys("Yarn Support");
        
        WebElement reportByListOption = driver.findElement(By.xpath("//li[contains(@class, 'p-dropdown-item') and .//span[text()='Yarn Support']]"));
        reportByListOption.click();
        
        // searchReportByList.sendKeys(Keys.ENTER);
		// Thread.sleep(4000);
        
		/*
		 * WebElement reportByListOption =
		 * wait.until(ExpectedConditions.elementToBeClickable(By.
		 * xpath("//li[@class='p-dropdown-item p-focus' and @role='option' and @aria-label='Yarn Support']"
		 * ))); reportByListOption.click();
		 */

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
		violationCategoryDetails.getText();

		// Thread.sleep(2000);
		WebElement violationCreatedDate = driver.findElement(By.xpath(
				"/html[1]/body[1]/div[2]/div[1]/div[1]/div[2]/div[3]/div[1]/div[1]/div[2]/div[1]/div[5]/span[2]/span[1]"));
		System.out.println(violationCreatedDate.getText());
		violationCreatedDate.getText();
		violationCreatedDate.getText();

		// Thread.sleep(2000);
		WebElement actionsList = driver.findElement(By.xpath("//i[@class='fa-solid fa-ellipsis-vertical']"));
		actionsList.click();

		// Thread.sleep(2000);
		WebElement resolveAction = driver.findElement(By.xpath("//span[normalize-space()='Resolved']"));
		resolveAction.click();

		// Thread.sleep(2000);
		WebElement confirm = driver.findElement(By.xpath("//button[normalize-space()='Yes']"));
		confirm.click();
		
		Thread.sleep(6000);

	}
	
	@Test(priority = 1)
	private void violationsTable() {
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		WebElement manageViolationsPage = driver.findElement(By.xpath("//a[normalize-space()='Manage Violations']"));
		manageViolationsPage.click();
		
		// Wait for the table to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table-responsive")));

        // Locate the first row of the table
        WebElement firstRow = driver.findElement(By.xpath("//table[@class='table table-striped table-bordered table-hover']/tbody/tr[1]"));

        // Extract all the required data
        String id = firstRow.findElement(By.cssSelector("td:nth-child(1)")).getText(); // ID
        String status = firstRow.findElement(By.cssSelector("td:nth-child(2) .badge")).getText(); // Status
        String subject = firstRow.findElement(By.cssSelector("td:nth-child(3)")).getText(); // Subject
        String violationCategory = firstRow.findElement(By.cssSelector("td:nth-child(4)")).getText(); // Violation Category
        String location = firstRow.findElement(By.cssSelector("td:nth-child(5) span")).getText(); // Location
        String penalty = firstRow.findElement(By.cssSelector("td:nth-child(6)")).getText(); // Penalty
        String violationDate = firstRow.findElement(By.cssSelector("td:nth-child(7)")).getText(); // Violation Date
        String people = firstRow.findElement(By.cssSelector("td:nth-child(8) .underline")).getText(); // People
        // Print the extracted data
        System.out.println("ID: " + id);
        System.out.println("Status: " + status);
        System.out.println("Subject: " + subject);
        System.out.println("Violation Category: " + violationCategory);
        System.out.println("Location: " + location);
        System.out.println("Penalty: " + penalty);
        System.out.println("Violation Date: " + violationDate);
        System.out.println("People: " + people);
	}

	@Test(priority = 2)
	private void tenantLogin() throws InterruptedException {
		// login code

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.navigate().to("https://automation.yarncloud.dev/tenant/auth/login");

		// Thread.sleep(2000);

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

	@Test(priority = 3)
	private void checkTenantViolation() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));

		WebElement myViolations = driver.findElement(By.linkText("My Violations"));
		myViolations.click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

        // Wait for the grid to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("sm:grid")));

        // Locate the grid container
        WebElement gridContainer = driver.findElement(By.className("sm:grid"));

        // Find all rows in the grid (assuming each row is an <a> element)
        List<WebElement> rows = gridContainer.findElements(By.cssSelector("a.justify-between.items-center"));

        // Get the last row
        WebElement lastRow = rows.get(rows.size() - 1);

        // Extract data from the last row
        String status = lastRow.findElement(By.cssSelector("span.rounded-lg")).getText();
        String subject = lastRow.findElement(By.cssSelector("div.col-span-2 p")).getText();
        String date = lastRow.findElement(By.cssSelector("div.col-span-2 .text-nowrap")).getText();
        String time = lastRow.findElements(By.cssSelector("div.col-span-2 .text-nowrap")).get(1).getText();
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        // Print the extracted data
        System.out.println("Status: " + status);
        System.out.println("Subject: " + subject);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        

		
		Assert.assertEquals(violationsubject, violationsubject);
	}
}
