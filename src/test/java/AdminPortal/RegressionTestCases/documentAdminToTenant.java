package AdminPortal.RegressionTestCases;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class documentAdminToTenant {

	WebDriver driver = new ChromeDriver();
	WebDriverWait wait;

	private String baseUrl;
	private String username;
	private String password;
	private String tenant;
	private String tenantusername;
	private String tenantpassword;
	private String tenantviolation;

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
			tenant = properties.getProperty("tenant");
			tenantusername = properties.getProperty("tenantusername");
			tenantpassword = properties.getProperty("tenantpassword");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void login() throws InterruptedException {

		// login code
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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
	public void addNewDocument() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
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

		// Thread.sleep(4000);
		driver.findElement(By.xpath(
				"/html/body/div[2]/div/div[1]/div[2]/div[3]/div[1]/div[1]/div/div[2]/div[2]/div[1]/div/div[1]/input"))
				.sendKeys("C:\\Users\\eng_m\\eclipse-workspace\\RegressionTestCases\\logo-white.png");

		// Thread.sleep(4000);
		driver.findElement(By.xpath(
				"/html/body/div[2]/div/div[1]/div[2]/div[3]/div[1]/div[1]/div/div[2]/div[2]/div[1]/div/div[1]/input"))
				.sendKeys("C:\\Users\\eng_m\\eclipse-workspace\\RegressionTestCases\\New Microsoft Word Document.pdf");

		WebElement successMessage = driver.findElement(By.xpath("/html/body/div[4]"));
		successMessage.getText();
		System.out.println(successMessage.getText());

	}

	@Test(priority = 1)
	private void tenantLogin() throws InterruptedException { // login code

		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.navigate().to("https://automation.yarncloud.dev/tenant/auth/login");

		// Thread.sleep(6000);

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

		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		// Thread.sleep(4000);

		// Thread.sleep(4000);
		WebElement documentsTab = driver.findElement(By.linkText("My Documents"));
		documentsTab.click();

		// Thread.sleep(4000);
		WebElement document = driver
				.findElement(By.xpath("/html/body/div[1]/main/div/div/div[3]/div/div[2]/div[5]/div[2]"));
		document.hashCode();
		System.out.println(document.hashCode());

		Thread.sleep(6000);
		
		List<WebElement> documentContainers = driver.findElements(By.cssSelector(".image-container"));

        // Check if there are any documents
        if (!documentContainers.isEmpty()) {
            // Get the last document element
            WebElement lastDocument = documentContainers.get(documentContainers.size() - 1);
            
            // Extract details
            String imageUrl = lastDocument.findElement(By.tagName("img")).getAttribute("src");
            String fileName = lastDocument.findElement(By.cssSelector("p.truncate")).getText();
            
            // Print the details
            System.out.println("Last Document Image URL: " + imageUrl);
            System.out.println("Last Document File Name: " + fileName);
        } else {
            System.out.println("No documents found.");
        }
		/*
		 * List<WebElement> documentList =
		 * wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
		 * By.xpath("/html/body/div[1]/main/div/div/div[3]/div/div[2]/div[5]/div[2]")));
		 * // Adjust the XPath for the specific element containing documents boolean
		 * documentFound = false;
		 * 
		 * for (WebElement documentPDF : documentList) { String documentName =
		 * documentPDF.getText(); // Extract document name if
		 * (documentName.equals("New Microsoft Word Document")) { // Replace with the
		 * name of the uploaded document documentFound = true; break; } }
		 * 
		 * if (documentFound) {
		 * System.out.println("Uploaded document is present on tenant portal");
		 * 
		 * // 6. Click on Document Download Link
		 * 
		 * WebElement downloadLink = wait.until(ExpectedConditions.elementToBeClickable(
		 * By.
		 * xpath("//div[@class='document-item' and contains(., 'your_uploaded_document_name')]//a"
		 * ))); // Adjust XPath to target the download link downloadLink.click();
		 * 
		 * 
		 * // 7. Verify Document Download (Optional) // ... (Logic to verify that the
		 * download started or that a file was downloaded)
		 * 
		 * } else { System.out.println("Uploaded document not found on tenant portal");
		 * }
		 */

	}

}
