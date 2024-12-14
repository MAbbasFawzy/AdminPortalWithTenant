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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class invoicesPayment {

	WebDriver driver = new ChromeDriver();
	WebDriverWait wait;

	private String baseUrl;
	private String username;
	private String password;
	private String tenant;
	private String tenantusername;
	private String tenantpassword;
	private String storedInvoiceNumber;

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
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		
		WebElement email = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div[1]/input"));
		email.sendKeys(username);

		
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
	public void addNewInvoice() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		WebElement invoicesMenuItem = driver
				.findElement(By.xpath("/html/body/div[2]/div/nav/div/div[3]/div[4]/div/div/div[1]/div[1]/div/a"));
		invoicesMenuItem.click();

		
		WebElement addNewInvoiceButton = driver
				.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/ul/div/a/button"));
		addNewInvoiceButton.click();

		
		WebElement propertyDropDownList = driver
				.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/form/div[1]/div[2]/div"));
		propertyDropDownList.click();

		
		WebElement propertyOptionSelection = driver.findElement(By.xpath("/html/body/div[5]/div[2]/ul/li[1]"));
		propertyOptionSelection.click();

		Thread.sleep(2000);
		WebElement unitDropDownList = driver
				.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/form/div[2]/div[2]/div"));
		unitDropDownList.click();

		
		WebElement unitOptionSelection = driver.findElement(By.xpath("/html/body/div[5]/div[2]/ul/li[1]"));
		unitOptionSelection.click();
		
		Thread.sleep(2000);
		WebElement tenantList = driver.findElement(By.xpath(
				"/html[1]/body[1]/div[2]/div[1]/div[1]/div[2]/div[3]/div[1]/div[2]/form[1]/div[3]/div[2]/div[1]"));
		tenantList.click();

		WebElement search = driver.findElement(By.xpath(
				"//input[@type='text' and @class='p-dropdown-filter p-inputtext p-component' and @role='searchbox' and @data-pc-section='filterinput']"));
		search.sendKeys("yarn.user.tenant");

		Thread.sleep(2000);
		WebElement tenantListOption = driver.findElement(By.xpath("//li[span[text()='yarn.user.tenant']]"));
		tenantListOption.click();

		
		WebElement serviceDropDownList = driver
				.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/form/div[5]/div[2]/div"));
		serviceDropDownList.click();

		
		WebElement serviceOptionSelection = driver.findElement(By.xpath("/html/body/div[5]/div[2]/ul/li[1]"));
		serviceOptionSelection.click();

		
		WebElement dateInput = driver.findElement(
				By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/form/div[7]/div[2]/div/span/input"));
		dateInput.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pv_id_20_panel")));

		Thread.sleep(1000);
		WebElement dateToSelect = driver.findElement(By.xpath("//td[@aria-label='12']"));
		dateToSelect.click();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

		
		WebElement dueDateInput = driver.findElement(
				By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/form/div[8]/div[2]/div/span/input"));
		dueDateInput.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//div[@class='p-datepicker p-component p-ripple-disabled' and @role='dialog' and @aria-label='Choose Date']")));

		Thread.sleep(1000);
		WebElement dueDateToSelect = driver.findElement(By.xpath("//td[@aria-label='12']"));
		dueDateToSelect.click();

		
		WebElement invoiceValueInput = driver.findElement(By
				.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/form/div[11]/div[2]/div/div/span/input"));
		invoiceValueInput.sendKeys("5000");

		
		WebElement invoiceNotes = driver.findElement(
				By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/form/div[13]/div[2]/textarea"));
		String notes = "Testing invoice from admin panel to be added for the tenant portal.";
		invoiceNotes.sendKeys(notes);

		
		WebElement submitInvoice = driver
				.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div/div[2]/form/div[15]/button"));
		submitInvoice.click();
		Thread.sleep(8000);

	}

	@Test(priority = 1)
	public void erpSystem() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.navigate().to(
				"https://automation.erp.yarncloud.dev/web#menu_id=184&action=295&model=account.move&view_type=list");

		Thread.sleep(2000);
		WebElement tenantUser = driver
				.findElement(By.xpath("/html/body/div[1]/div/div[4]/div/div[1]/table/tbody/tr[1]/td[3]"));
		tenantUser.getText();
		tenantUser.click();

		Thread.sleep(2000);
		WebElement confirmInvoiceButton = driver
				.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[1]/div[1]/div[1]/button[2]"));
		confirmInvoiceButton.click();

		Thread.sleep(2000);
		WebElement adminPortalInvoice = driver
				.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[1]/div[15]/div[8]/h1[2]/span"));

		storedInvoiceNumber = adminPortalInvoice.getText();

		Thread.sleep(2000);
		WebElement registerPayment = driver
				.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[1]/div[1]/div[1]/button[5]"));
		registerPayment.click();

		Thread.sleep(4000);
		WebElement confirmPayment = driver
				.findElement(By.xpath("/html/body/div[2]/div[5]/div/div/div/div/footer/div/footer/button[1]"));
		confirmPayment.click();
		
		Thread.sleep(4000);

	}

	@Test(priority = 2)
	private void tenantLogin() throws InterruptedException { // login code

		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.navigate().to("https://automation.yarncloud.dev/tenant/auth/login");

		//Thread.sleep(6000);

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

	@Test(priority = 3)
	public void checkInvoiceNumberStatusAndDescription() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		

		WebElement invoicesTab = driver.findElement(By.linkText("My Invoices"));
		invoicesTab.click();

		driver.getCurrentUrl();
		WebElement paidInvoices = driver
				.findElement(By.xpath("/html/body/div[1]/main/div/div/div[1]/div[2]/button[2]"));
		paidInvoices.click();
		/*
		 * Check the invoice in due WebElement tenantPortalInvoice = driver
		 * .findElement(By.xpath(
		 * "/html/body/div[1]/main/div/div/div[1]/div[3]/div[1]/a/div/span[1]"));
		 * tenantPortalInvoice.getText();
		 */
		
		WebElement tenantPortalInvoice = driver.findElement(By.xpath("/html/body/div[1]/main/div/div/div[1]/div[3]/div/a/div/span[1]"));

		Assert.assertEquals(tenantPortalInvoice.getText(), storedInvoiceNumber);

	}

	/*
	 * @Test (priority = 4) public void payInvoice() throws InterruptedException {
	 * 
	 * driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	 * 
	 * 
	 * Code for payment from tenant portal WebElement payButton =
	 * driver.findElement(By.xpath(
	 * "/html/body/div[1]/main/div/div/div[1]/div[3]/div[1]/div/button[2]"));
	 * payButton.click();
	 * 
	 * Thread.sleep(6000); driver.switchTo().frame(0);
	 * 
	 * Thread.sleep(6000); WebElement cardOwner = driver.findElement(By.xpath(
	 * "/html/body/main/div[2]/form/div[1]/div/div[2]/input"));
	 * cardOwner.sendKeys("Mahmoud Abbas Fawzy");
	 * 
	 *  WebElement cardNumber = driver.findElement(By.xpath(
	 * "/html/body/main/div[2]/form/div[1]/div/div[3]/div/input"));
	 * cardNumber.sendKeys("5123450000000008");
	 * 
	 *  WebElement cardMonth = driver.findElement(By.xpath(
	 * "/html/body/main/div[2]/form/div[1]/div/div[4]/div[1]/input"));
	 * cardMonth.sendKeys("12");
	 * 
	 *  WebElement cardYear = driver.findElement(By.xpath(
	 * "/html/body/main/div[2]/form/div[1]/div/div[4]/div[2]/input"));
	 * cardYear.sendKeys("24");
	 * 
	 *  WebElement cvv = driver.findElement(By.xpath(
	 * "/html/body/main/div[2]/form/div[1]/div/div[4]/div[3]/input"));
	 * cvv.sendKeys("100");
	 * 
	 * 
	 * 
	 * 
	 * }
	 */
}
