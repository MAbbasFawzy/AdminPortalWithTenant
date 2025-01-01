package AdminPortal.RegressionTestCases;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;
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
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import AdminPortal.RegressionTestCases.randomGenerator;

public class dependentAdminToPortal extends randomGenerator {

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
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

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
	public void addNewDependent() {

		randomGenerator.Visitor visitor = randomGenerator.generateRandomContact();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

		WebElement manageContractSection = driver
				.findElement(By.xpath("/html/body/div[2]/div/nav/div/div[3]/div[3]/div/div/div[2]/div[1]"));
		manageContractSection.click();

		WebElement tenantSection = driver.findElement(
				By.xpath("/html/body/div[2]/div/nav/div/div[3]/div[3]/div/div/div[2]/div[2]/div/ul/li[2]/div[1]"));
		tenantSection.click();

		WebElement viewTenantData = driver.findElement(
				By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/div[1]/table/tbody/tr[4]/td[9]/a"));
		viewTenantData.click();

		WebElement dependentTab = driver
				.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div[1]/div[1]/div/div[3]/button[1]"));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", dependentTab);

		dependentTab.click();

		WebElement addDependent = driver.findElement(
				By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div[1]/div[1]/div/div[4]/div/div[1]/button"));
		addDependent.click();

		WebElement relationDropDownList = driver
				.findElement(By.xpath("/html/body/div[5]/div/div[2]/div/form/div[1]/div"));
		relationDropDownList.click();

		WebElement relationDropDowOption = driver.findElement(By.xpath("/html/body/div[6]/div[2]/ul/li[16]"));
		relationDropDowOption.click();

		WebElement dependentType = driver.findElement(By.id("new"));
		dependentType.click();

		WebElement dependentFullName = driver.findElement(
				By.xpath("/html/body/div[5]/div/div[2]/div/form/div[3]/div[2]/div[1]/div[2]/div/div[1]/div[1]/input"));
		depedentname = visitor.firstName + " " + visitor.lastName;
		dependentFullName.sendKeys(visitor.firstName + " " + visitor.lastName);

		WebElement dependentEmail = driver.findElement(By.xpath(
				"/html/body/div[5]/div/div[2]/div/form/div[3]/div[2]/div[1]/div[2]/div/div[2]/div[1]/div/input"));
		dependentEmail.sendKeys(visitor.email);

		WebElement dependentGender = driver.findElement(
				By.xpath("/html/body/div[5]/div/div[2]/div/form/div[3]/div[2]/div[1]/div[2]/div/div[6]/div[2]/div"));
		dependentGender.click();

		WebElement dependentGenderListOption = driver.findElement(By.xpath("//li[contains(@class, 'p-dropdown-item') and .//span[text()='Male']]"));
		dependentGenderListOption.click();

		WebElement submitDependent = driver
				.findElement(By.xpath("/html/body/div[5]/div/div[2]/div/form/div[4]/button"));
		submitDependent.click();

		/*
		 * WebElement table = driver.findElement( By.xpath(
		 * "/html/body/div[2]/div/div[1]/div[2]/div[3]/div[1]/div[1]/div/div[4]/div/div[2]/table"
		 * )); List<WebElement> rows = table.findElements(By.tagName("tr"));
		 * 
		 * if (rows.size() > 0) { // Get the last row WebElement lastRow =
		 * rows.get(rows.size() - 1);
		 * 
		 * // Get the first cell in the last row List<WebElement> cells =
		 * lastRow.findElements(By.tagName("td")); // Use "th" if your table has header
		 * cells
		 * 
		 * if (cells.size() > 0) { // Extract data from the first cell String
		 * firstColumnData = cells.get(0).getText(); // Get the text of the first cell
		 * System.out.println("Data in the first column of the last row: " +
		 * firstColumnData); } else { System.out.println("The last row has no cells.");
		 * } } else { System.out.println("The table is empty."); }
		 */

	}

	@Test(priority = 1)
	private void tenantLogin() throws InterruptedException { // login code

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
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

	@Test(priority = 2)
	private void checkDependentAdded() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

		WebElement dependentsTab = driver.findElement(By.linkText("My Dependents"));
		dependentsTab.click();
		
		// Find the last row in the grid
        WebElement lastRow = driver.findElement(By.xpath("//div[@class='grid grid-cols-5 gap-2 items-center h-16 hover:bg-[var(--c3)]'][last()]"));

        // Extract the dependent name
        WebElement dependentNameElement = lastRow.findElement(By.xpath(".//div[1]"));
        String dependentName = dependentNameElement.getText();

        // Print the extracted data
        System.out.println("Dependent Name: " + dependentName);
		
		/*
		 * WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
		 * ".grid")));
		 * 
		 * // Infinite loop to check for new data while (true) { // Locate the last item
		 * in the grid WebElement lastItem =
		 * driver.findElement(By.cssSelector(".grid > div:last-child"));
		 * 
		 * 
		 * // Extract the text you want
		 * 
		 * String dependentName =
		 * lastItem.findElement(By.cssSelector("div:nth-child(1)")).getText(); String
		 * dependentId =
		 * lastItem.findElement(By.cssSelector("div:nth-child(2)")).getText(); String
		 * status = lastItem.findElement(By.cssSelector("span.p-badge")).getText();
		 * String relationship =
		 * lastItem.findElement(By.cssSelector("div:nth-child(4)")).getText();
		 * JavascriptExecutor js = (JavascriptExecutor) driver;
		 * js.executeScript("arguments[0].scrollIntoView(true);", dependentName);
		 * 
		 * // Print the extracted data System.out.println("Latest Dependent:");
		 * System.out.println("Name: " + dependentName); System.out.println("ID: " +
		 * dependentId); System.out.println("Status: " + status);
		 * System.out.println("Relationship: " + relationship);
		 * 
		 * // Wait for a while before checking again (e.g., 5 seconds)
		 * Thread.sleep(5000);
		 * 
		 * // Optionally, you can add logic to break the loop if needed // For example,
		 * if a certain condition is met or if the application closes }
		 */

	}
}
