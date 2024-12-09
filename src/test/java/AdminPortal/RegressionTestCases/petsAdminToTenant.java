package AdminPortal.RegressionTestCases;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
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
import org.opentest4j.AssertionFailedError;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class petsAdminToTenant {
	
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
	private String animaltype;
	private String animalname;

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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

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
	public void addNewPet() throws InterruptedException {

		randomGenerator.Visitor visitor = randomGenerator.generateRandomContact();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement manageContractSection = driver
				.findElement(By.xpath("/html/body/div[2]/div/nav/div/div[3]/div[3]/div/div/div[2]/div[1]"));
		manageContractSection.click();

		WebElement tenantSection = driver.findElement(
				By.xpath("/html/body/div[2]/div/nav/div/div[3]/div[3]/div/div/div[2]/div[2]/div/ul/li[2]/div[1]"));
		tenantSection.click();

		WebElement viewTenantData = driver.findElement(
				By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/div[1]/table/tbody/tr[4]/td[9]/a"));
		viewTenantData.click();

		WebElement petsTab = driver
				.findElement(By.xpath("//button[3]//div[1]"));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", petsTab);

		petsTab.click();

		WebElement addPet = driver.findElement(
				By.xpath("//button[normalize-space()='Add pet']"));
		addPet.click();

		WebElement newPet = driver.findElement(By.xpath("//input[@id='newPet']"));
		newPet.click();
		
		Thread.sleep(2000);
		WebElement animal = driver.findElement(By.xpath("/html[1]/body[1]/div[5]/div[1]/div[2]/form[1]/div[2]/form[1]/div[1]/div[2]/div[1]/div[1]"));
		animal.click();
		
		/*
		 * WebElement search =
		 * driver.findElement(By.xpath("//input[@role='searchbox']"));
		 * search.sendKeys("Cat");
		 */
		
		WebElement animalOption = driver.findElement(By.xpath("/html[1]/body[1]/div[6]/div[2]/ul[1]/li[1]"));
		animaltype = animalOption.getText();
		animalOption.click();
		
		WebElement animalName = driver.findElement(By.xpath("//div[@class='p-dialog-mask p-component-overlay p-component-overlay-enter']//div[3]//div[1]//input[1]"));
		animalname = "Zoro";
		animalName.sendKeys(animalname);
		
		WebElement submitPet = driver
				.findElement(By.xpath("//span[normalize-space()='Submit']"));
		submitPet.click();
		

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

		Thread.sleep(2000);
	}
	
	@Test (priority = 2)
	private void checkPetData() {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement myPetsTab = driver.findElement(By.linkText("My Pets"));
		myPetsTab.click();
		
		// Find the last added item in the grid
        WebElement lastPetRow = driver.findElement(By.xpath("//div[@class='grid grid-cols-6 gap-2 items-center h-16 hover:bg-[var(--c3)]'][last()]"));

        // Extract the data from the row
        String petName = lastPetRow.findElement(By.xpath(".//div[1]")).getText();
        String petType = lastPetRow.findElement(By.xpath(".//div[4]")).getText(); 

        // Print the extracted data
        System.out.println("Pet Name: " + petName);
        System.out.println("Pet Type: " + petType);
		
		Assert.assertEquals(animalname, petName);
		Assert.assertEquals(animaltype, petType);
        
        
	}

}
