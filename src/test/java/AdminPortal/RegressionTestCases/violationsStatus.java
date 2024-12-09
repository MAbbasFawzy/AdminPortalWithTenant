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
import org.openqa.selenium.interactions.Actions;
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
	private String tenant;
	private String tenantusername;
	private String tenantpassword;
	private String tenantviolation;
	private String violationcategory;
	private String violationdate;

	@BeforeTest
	public void setup() throws InterruptedException {
		loadProperties();
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
			tenant = properties.getProperty("tenant");
			tenantusername = properties.getProperty("tenantusername");
			tenantpassword = properties.getProperty("tenantpassword");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void login() throws InterruptedException {

		// login code
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		// Thread.sleep(2000);
		WebElement email = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div[1]/input"));
		email.sendKeys(username);

		// Thread.sleep(2000);
		WebElement passcode = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div[2]/div/input"));
		passcode.sendKeys(password);

		WebElement loginButton = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/button"));
		loginButton.click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement userName = driver.findElement(By.xpath("/html/body/div[2]/div/nav/div/div[2]/div[1]/a/span[1]"));
		AssertJUnit.assertEquals(userName.getText(), userName.getText());

		Thread.sleep(2000);
	}

	@Test(priority = 0)
	public void addViolationAdminPortal() throws InterruptedException {

		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(2000);
		WebElement violationTab = driver.findElement(By.linkText("Violations"));
		violationTab.click();

		Thread.sleep(2000);
		WebElement addNewViolation = driver
				.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div[1]/a"));
		addNewViolation.click();

		Thread.sleep(2000);
		WebElement propertyList = driver
				.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/form/div/div[1]/div/div[1]/div"));
		propertyList.click();

		Thread.sleep(2000);
		WebElement propertyListOption = driver.findElement(By.xpath("(//ul[@class='p-dropdown-items']/child::li)[1]"));
		propertyListOption.click();

		Thread.sleep(6000);
		WebElement violationSubject = driver.findElement(By.xpath("//input[@placeholder='Subject']"));
		violationSubject.sendKeys("New violation.");

		Thread.sleep(2000);
		WebElement violationCategory = driver
				.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/form/div/div[1]/div/div[3]/div"));
		violationCategory.click();

		Thread.sleep(2000);
		WebElement violationCategoryOption = driver.findElement(By.xpath("/html/body/div[5]/div[2]/ul/li[1]"));
		violationCategoryOption.click();

		Thread.sleep(2000);
		WebElement reportByList = driver
				.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/form/div/div[1]/div/div[4]/div"));
		reportByList.click();

		Thread.sleep(2000);
		WebElement reportByListOption = driver.findElement(By.xpath("/html/body/div[5]/div[2]/ul/li[1]"));
		reportByListOption.click();

		Thread.sleep(2000);
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

		Thread.sleep(2000);
		WebElement involvedPeopleList = driver
				.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/form/div/div[1]/div/div[7]/div"));
		involvedPeopleList.click();

		Thread.sleep(4000);
		WebElement search = driver.findElement(By.xpath("//input[@role='searchbox']"));
		search.sendKeys("yarn.user.tenant");

		Thread.sleep(2000);
		WebElement involvedPeopleOptions = driver
				.findElement(By.xpath("//li[@class='p-multiselect-item' and @aria-label='yarn.user.tenant'] "));
		involvedPeopleOptions.click();

		Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

		Thread.sleep(2000);
		WebElement createButton = driver
				.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/form/div/div[2]/button"));
		createButton.click();

		Thread.sleep(4000);
		WebElement violationCategoryDetails = driver.findElement(By.xpath(
				"/html[1]/body[1]/div[2]/div[1]/div[1]/div[2]/div[3]/div[1]/div[1]/div[2]/div[1]/div[2]/span[2]"));
		System.out.println(violationCategoryDetails.getText());
		violationCategoryDetails.getText();
		violationcategory = violationCategoryDetails.getText();

		Thread.sleep(2000);
		WebElement violationCreatedDate = driver.findElement(By.xpath(
				"/html[1]/body[1]/div[2]/div[1]/div[1]/div[2]/div[3]/div[1]/div[1]/div[2]/div[1]/div[5]/span[2]/span[1]"));
		System.out.println(violationCreatedDate.getText());
		violationCreatedDate.getText();
		violationdate = violationCreatedDate.getText();
		
		Thread.sleep(4000);
		WebElement actionsList = driver.findElement(By.xpath("//i[@class='fa-solid fa-ellipsis-vertical']"));
		actionsList.click();
		
		Thread.sleep(4000);
		WebElement resolveAction = driver.findElement(By.xpath("//span[normalize-space()='Resolved']"));
		resolveAction.click();
		
		Thread.sleep(4000);
		WebElement confirm = driver.findElement(By.xpath("//button[normalize-space()='Yes']"));
		confirm.click();

		/*
		 * Thread.sleep(2000); WebElement violation = driver .findElement(By.xpath(
		 * "/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[1]/div[1]/div/h3"));
		 * tenantviolation = violation.getText();
		 * 
		 * Thread.sleep(2000); violationTab.click();
		 * 
		 * Thread.sleep(2000); WebElement violationSubjectTableView =
		 * driver.findElement(By .xpath(
		 * "/html/body/div[2]/div/div[1]/div[2]/div[3]/div[2]/div/div/div/div[1]/table/tbody/tr[1]/td[3]"
		 * )); violationSubjectTableView.getText(); tenantviolation =
		 * violationSubjectTableView.getText();
		 */

	}

	@Test(priority = 1)
	private void tenantLogin() throws InterruptedException {
		// login code

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to("https://automation.yarncloud.dev/tenant/auth/login");

		Thread.sleep(2000);

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

	@Test(priority = 2)
	private void checkTenantViolation() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		

		WebElement myViolations = driver.findElement(By.linkText("My Violations"));
		myViolations.click();
		
		
		
		
		// Find the last row in the grid
        WebElement lastRow = driver.findElement(By.xpath("//div[@class='sm:grid bg-[var(--c1)] p-4 sm:p-8']//a[last()]"));

        // Extract the subject
        WebElement subjectElement = lastRow.findElement(By.xpath(".//div[@class='col-span-2']//p"));
        String subject = subjectElement.getText();

        // Extract the date (assuming date and time are in separate spans)
        WebElement dateElement = lastRow.findElement(By.xpath(".//span[@class='text-nowrap'][1]"));
        String date = dateElement.getText();

        // Print the extracted data
        System.out.println("Subject: " + subject);
        System.out.println("Date: " + date);
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", dateElement);
        
        Assert.assertEquals(violationcategory, subject);
        Assert.assertEquals(violationdate, date);

		/*
		 * Thread.sleep(2000); WebElement violationSubject = driver
		 * .findElement(By.xpath(
		 * "/html/body/div[1]/main/div/div/div[3]/div/div/div/a/div/div[2]/p"));
		 * violationSubject.getText();
		 * 
		 * Assert.assertEquals(violationSubject.getText(), tenantviolation);
		 */

	}

}
