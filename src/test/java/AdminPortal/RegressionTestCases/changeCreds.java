package AdminPortal.RegressionTestCases;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class changeCreds {

	WebDriver driver = new ChromeDriver();
	WebDriverWait wait;

	public String adminWindow;
	public String tenantWindow;

	private String baseUrl;
	private String username;
	private String password;
	private String tenant;
	private String tenantusername;
	private String tenantpassword;
	private String tenantviolation;
	private String newpassword;
	private String newusername;

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
			newpassword = properties.getProperty("newpassword");
			newusername = properties.getProperty("newtenantusername");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void login() throws InterruptedException {

		// login code
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		Thread.sleep(4000);
		WebElement email = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div[1]/input"));
		email.sendKeys(username);

		Thread.sleep(4000);
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
	public void searchForUserAndChangeUsername() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		WebElement peopleMenuItem = driver.findElement(By.xpath(
				"/html[1]/body[1]/div[2]/div[1]/nav[1]/div[1]/div[3]/div[3]/div[1]/div[1]/div[3]/div[1]/div[1]/a[1]"));
		peopleMenuItem.click();

		WebElement peopleOption = driver.findElement(By.xpath("//li[@id='pv_id_3_2_0']//a"));
		peopleOption.click();

		WebElement searchInput = driver.findElement(By.xpath("//div[@class='page-container']//div[2]//input[1]"));
		searchInput.sendKeys("yarn.user.tenant");
		searchInput.sendKeys(Keys.ENTER);

		// Thread.sleep(6000);

		WebElement viewTenant = driver.findElement(By.xpath("//a[@class='btn btn-outline btn-sm btn-primary']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", viewTenant);
		viewTenant.click();

		WebElement userAccountInfo = driver.findElement(By.xpath("//a[normalize-space()='User Account Info']"));
		userAccountInfo.click();

		Thread.sleep(500);
		WebElement userCredsButton = driver.findElement(By.xpath("//button[@class='btn btn-primary']"));
		userCredsButton.click();
		Thread.sleep(500);

		// Thread.sleep(4000);
		WebElement newUsername = driver.findElement(By.xpath("//input[@placeholder='Enter the new username']"));
		newUsername.sendKeys(newusername);

		// Thread.sleep(4000);
		WebElement confirmNewUsername = driver.findElement(By.xpath("//input[@id='confirmUsername']"));
		confirmNewUsername.sendKeys(newusername);

		// Thread.sleep(4000);
		WebElement submitButtonUsername = driver.findElement(By.xpath("//div[4]//button[1]"));
		submitButtonUsername.click();
		
		
		// Step 1: Store the admin window handle
		adminWindow = driver.getWindowHandle();

		// Step 2: Open a new tab
		driver.switchTo().newWindow(WindowType.TAB);

		// Step 3: Navigate to the new URL in the new tab
		driver.get("https://automation.yarncloud.dev/tenant/auth/login");

		// Step 4: Store the tenant window handle
		tenantWindow = driver.getWindowHandle();

	}
	
	@Test(priority = 2)
	private void tenantLogin() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		driver.switchTo().window(tenantWindow);

		WebElement email = driver.findElement(By.xpath("/html/body/div[1]/main/div/div/div[3]/form/div[1]/input"));
		email.sendKeys(newusername);

		WebElement passcode = driver
				.findElement(By.xpath("/html/body/div[1]/main/div/div/div[3]/form/div[2]/div/input"));
		passcode.sendKeys(tenantpassword);

		WebElement loginButton = driver
				.findElement(By.xpath("//*[@id=\"__nuxt\"]/main/div/div/div[3]/form/div[3]/button"));
		loginButton.click();

		WebElement userName = driver.findElement(By.xpath("//*[@id=\"__nuxt\"]/main/nav[1]/div/div[1]/div[2]/span[2]"));
		AssertJUnit.assertEquals(tenantusername, userName.getText());
		
		Thread.sleep(2000);
		WebElement logOutButton = driver.findElement(By.xpath("//div[@class='flex justify-between items-center h-22 border-b']//a[3]//*[name()='svg']"));
		logOutButton.click();
		
		Thread.sleep(4000);

	}
	 
	
	
	@Test(priority = 3)
	public void changePasswordAdmin() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));

		driver.switchTo().window(adminWindow);

		Thread.sleep(500);

		// Refresh the page
		driver.navigate().refresh();
		Thread.sleep(500);

		WebElement userAccountInfo = driver.findElement(By.xpath("//a[normalize-space()='User Account Info']"));
		userAccountInfo.click();

		WebElement userCredsButton = driver.findElement(
				By.cssSelector("#tab-1 > div > div > div > div > div.card.mb-5 > div > div.text-end.mb-3 > button"));
		userCredsButton.click();

		WebElement changePassword = driver.findElement(By.xpath("//a[normalize-space()='Change Password']"));
		changePassword.click();

		// Thread.sleep(4000);
		WebElement passwordInput = driver.findElement(By.xpath("//input[@placeholder='Enter the new password']"));
		passwordInput.sendKeys(newpassword);

		WebElement confirmPassword = driver.findElement(By.xpath("//input[@id='confirm-password']"));
		confirmPassword.sendKeys(newpassword);

		// Thread.sleep(4000);
		WebElement submitButtonPassword = driver
				.findElement(By.xpath("//div[@class='p-dialog-mask p-component-overlay p-component-overlay-enter']//div[3]//button[1]"));

		Thread.sleep(2000);
		submitButtonPassword.click();

	}
	
	  
	
	@Test(priority = 4)
	public void loginTenant() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));

		driver.switchTo().window(tenantWindow);
		
		WebElement email = driver.findElement(By.xpath("/html/body/div[1]/main/div/div/div[3]/form/div[1]/input"));
		email.sendKeys(newusername);

		WebElement passcode = driver
				.findElement(By.xpath("/html/body/div[1]/main/div/div/div[3]/form/div[2]/div/input"));
		passcode.sendKeys(newpassword);

		WebElement loginButton = driver
				.findElement(By.xpath("//*[@id=\"__nuxt\"]/main/div/div/div[3]/form/div[3]/button"));
		loginButton.click();

		WebElement userName = driver.findElement(By.xpath("//*[@id=\"__nuxt\"]/main/nav[1]/div/div[1]/div[2]/span[2]"));
		AssertJUnit.assertEquals(tenantusername, userName.getText());
		
		Thread.sleep(2000);
		WebElement logOutButton = driver.findElement(By.xpath("//div[@class='flex justify-between items-center h-22 border-b']//a[3]//*[name()='svg']"));
		logOutButton.click();

		Thread.sleep(2000);
	}
	 
}
