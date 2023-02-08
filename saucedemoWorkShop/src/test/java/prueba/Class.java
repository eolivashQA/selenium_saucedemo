/*
* @Copyright
* @Enrique Hernandez, Adan Santiago, Antonio Enriquez, Carlos Martinez, Carmelo Moreno, Eduardo Olivas, Eduardo Martinez
* @Simulate user's actions in a website and automate them
* @Inputs: user data
* @Inputs: password data
* @Inputs: user data fields
* @Outputs: Website access
* @Outputs: Add item to cart
* @Outputs: Checkout with cart's data
* @Outputs: Username in the top right of website
* @Outputs: Reset cart data
* @Outputs: Website logged in
* @Outputs: User acceptance after filling form
* */

package prueba;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Class {
	//Pre-conditions
	protected WebDriver driver;
	WebElement element;
	String baseUrl = "https://www.saucedemo.com/";
	@Parameters({"browser","url"})
	@BeforeClass
	public void setUpClass(@Optional("chrome")String browser, @Optional("https://www.saucedemo.com/")String url) {
		System.setProperty("webdriver.chrome.driver", "C:\\SELENIUM\\driver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	public void logIn() {
		//Login procedure
		String username = "standard_user";
		String password = "secret_sauce";
		WebElement inputName = driver.findElement(By.id("user-name"));
		inputName.sendKeys(username);
		WebElement inputpassword = driver.findElement(By.id("password"));
		inputpassword.sendKeys(password);
		WebElement btnLogin = driver.findElement(By.id("login-button"));
		btnLogin.click();
	}
	@Test
	public void suiteTest() {
		//Run checkout suite
		logIn();
		selectFirst();
		goToCart();
		checkCart();	
	}
	@Test
	public void resetApp() {
		logIn();
		//Add to cart
		WebElement cartAdd = driver.findElement(By.id("add-to-cart-sauce-labs-bike-light"));
		cartAdd.click();
		//Open menu
		WebElement openMenu = driver.findElement(By.id("react-burger-menu-btn"));
		openMenu.click();
		WebElement resetSidebar = driver.findElement(By.id("reset_sidebar_link"));
		resetSidebar.click();
	}
	@Test
	public void userCheckout() throws InterruptedException {
		logIn();
		WebElement cartLink = driver.findElement(By.xpath("//a[@class='shopping_cart_link']"));
		cartLink.click();
		//locate the checkout button
		WebElement checkoutButton = driver.findElement(By.id("checkout"));
		//click the checkout button
		checkoutButton.click();
		/**
		 * Block of code for field clearing and filling
		 */
		fillForm();
		//Adding the thread sleep fn
		Thread.sleep(900);
		//locate the continue button
		WebElement continueButton = driver.findElement(By.id("continue"));
		//click on the continue button
		continueButton.click();
		//locate the finish button
		WebElement finishButton = driver.findElement(By.id("finish"));
		//click the finish button
		finishButton.click();
		//locate the back to home button
		WebElement backHomeButton = driver.findElement(By.id("back-to-products"));
		//click on the back to home button
		backHomeButton.click();
	}
	public void fillForm() {
		//find the fist name field
		WebElement nameField = driver.findElement(By.xpath("//input[@id='first-name']"));
		//clears the field
		nameField.clear();
		//writes the field 
		nameField.sendKeys("Evan");
		// locate the last name field
		WebElement lastNameField = driver.findElement(By.xpath("//input[@id='last-name']"));
		//clears the last name field
		lastNameField.clear();
		//write the last name
		lastNameField.sendKeys("Tomoki");
		//locate the zip field
		WebElement zipField = driver.findElement(By.xpath("//input[@id='postal-code']"));
		// clears the zip field
		zipField.clear();
		//write the zip code
		zipField.sendKeys("36570");
	}
	public void selectFirst() {
		//Select cart's first article
		String button_cart ="//*[@id=\"add-to-cart-sauce-labs-backpack\"]";
	    element = new WebDriverWait(driver, Duration.ofSeconds(10))
	            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(button_cart)));
		WebElement item = driver.findElement(By.xpath(button_cart));
		item.click();
	}
	public void goToCart() {
		//Go to shopping cart
		WebElement cart = driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a"));
		cart.click();
	}
	public void checkCart() {	
		//Verify products availability 
		boolean exists = driver.findElements(By.xpath("//*[@id=\"shopping_cart_container\"]/a/span")).size() != 0;
		if (exists == true) {
			System.out.println("Si hay elementos en el carrito");
		}
	}
	@Test
	public void logOut() {
		//Check logout procedure
		WebElement menuBtn = driver.findElement(By.id("react-burger-menu-btn"));
		menuBtn.click();
		WebElement logoutBtn = driver.findElement(By.id("logout_sidebar_link"));
		logoutBtn.click();
	}
	@AfterClass
	public void tearDownClass() {
		//Finish all test cases
		driver.close();
		driver.quit();
	}
}