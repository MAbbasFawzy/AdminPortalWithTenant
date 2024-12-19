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
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class documentAdminToTenant {

	WebDriver driver;
	WebDriverWait wait;

	public String adminWindow;
	public String tenantWindow;

	private String baseUrl;
	private String username;
	private String password;
	private String tenant;
	private String tenantusername;
	private String tenantpassword;
	

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
	
	// Method to highlight an element
    public static void highlightElement(WebDriver driver, WebElement element) {
        // Use JavaScript to change the style of the element
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String originalStyle = element.getAttribute("style");
        js.executeScript("arguments[0].style.border='3px solid red';", element);

        // Optionally, you can reset the style after a delay
        try {
            Thread.sleep(500); // Highlight duration
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Reset the style back to original
        js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, originalStyle);
    }

	private void login() throws InterruptedException {

		// login code
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		Thread.sleep(500);
		WebElement email = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div[1]/input"));
		email.sendKeys(username);

		Thread.sleep(500);
		WebElement passcode = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div[2]/div/input"));
		passcode.sendKeys(password);

		WebElement loginButton = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/button"));
		loginButton.click();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		WebElement userName = driver.findElement(By.xpath("/html/body/div[2]/div/nav/div/div[2]/div[1]/a/span[1]"));
		AssertJUnit.assertEquals(userName.getText(), userName.getText());

		Thread.sleep(500);
	}

	@Test(priority = 0)
	public void addNewDocument() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		// Thread.sleep(4000);

		WebElement manageContractSection = driver
				.findElement(By.xpath("/html/body/div[2]/div/nav/div/div[3]/div[3]/div/div/div[2]/div[1]"));
		manageContractSection.click();

		// Thread.sleep(4000);
		WebElement tenantSection = driver.findElement(
				By.xpath("/html/body/div[2]/div/nav/div/div[3]/div[3]/div/div/div[2]/div[2]/div/ul/li[2]/div[1]"));
		tenantSection.click();

		// Thread.sleep(4000);
		WebElement viewTenantData = driver.findElement(
				By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/div[1]/table/tbody/tr[4]/td[9]/a"));
		viewTenantData.click();

		Thread.sleep(500);
		driver.findElement(By.xpath(
				"/html/body/div[2]/div/div[1]/div[2]/div[3]/div[1]/div[1]/div/div[2]/div[2]/div[1]/div/div[1]/input"))
				.sendKeys("C:\\Users\\eng_m\\eclipse-workspace\\RegressionTestCases\\logo-white.png");

		Thread.sleep(500);
		driver.findElement(By.xpath(
				"/html/body/div[2]/div/div[1]/div[2]/div[3]/div[1]/div[1]/div/div[2]/div[2]/div[1]/div/div[1]/input"))
				.sendKeys("C:\\Users\\eng_m\\eclipse-workspace\\RegressionTestCases\\New Microsoft Word Document.pdf");

		WebElement successMessage = driver.findElement(By.xpath("/html/body/div[4]"));
		successMessage.getText();
		System.out.println(successMessage.getText());

	}

	@Test(priority = 1)
	private void tenantLogin() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));

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

		Thread.sleep(2000);
	}

	@Test(priority = 2)
	private void checkDocumentAdded() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		
		WebElement documentsTab = driver.findElement(By.linkText("My Documents"));
		documentsTab.click();

		// Create a WebDriverWait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

		// Wait for the element containing "New Microsoft Word Document.pdf" to be visible
		WebElement yarnPDFFile = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'New Microsoft Word Document.pdf')]")));

		WebElement yarnImageFile = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'logo-white.png')]")));

		// Print the text of the found element
		System.out.println("Found file: " + yarnPDFFile.getText());

		// Print the text of the found element
		System.out.println("Found file: " + yarnImageFile.getText());

	}

	@Test(priority = 3)
	public void deleteDocs() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		
		Thread.sleep(2000);

		driver.switchTo().window(adminWindow);
		
		// Wait for the delete icons to be present
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("delete-icon")));

        // Locate all delete icons
        List<WebElement> deleteIcons = driver.findElements(By.className("delete-icon"));

        // Click on each delete icon and confirm deletion
        for (WebElement deleteIcon : deleteIcons) {
            // Highlight the element (optional)
        	
            highlightElement(driver, deleteIcon);

            // Click on the delete icon
            deleteIcon.click();

            // Wait for the confirmation popup to appear
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("swal2-title")));

            // Click the confirm delete button
            WebElement confirmDeleteButton = driver.findElement(By.className("swal2-confirm"));
            confirmDeleteButton.click();

            // Optionally, you may want to add a small delay to observe the action
            Thread.sleep(1000); // Adjust the sleep time as needed
        }
	}
}
