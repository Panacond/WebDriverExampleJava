import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.xpath;
import static org.testng.Assert.*;

public class AvicTests {
    private WebDriver driver;

    @BeforeTest
    public void profileSetUp(){
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver");
    }

    @BeforeMethod
    public void testsSetUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://avic.ua/");
    }

    @Test(priority = 1)
    public void checkTitle(){
        assertTrue(driver.getTitle()
        .contains("AVIC™ - удобный интернет-магазин бытовой техники и электроники в Украине. | Avic"));
    }

    @Test(priority = 2)
    public void checkChat(){
        String INPUT_TEXT = "Hello";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(xpath("//jdiv [@class='hoverl_bca9']")).click();
        driver.findElement(xpath("//textarea [@class='inputField_0bb0']"))
                .sendKeys( INPUT_TEXT, Keys.ENTER);
        assertTrue(driver.findElement(xpath("//jdiv [contains(@class,'client')]/jdiv [contains(@class,'text')]")).getText().contains( INPUT_TEXT));
    }

    @Test(priority = 3)
    public void checkButtonMi() {
        driver.findElement(xpath("//img [contains(@src,'mi-brand')]")).click();
        new WebDriverWait(driver, 30).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        List<WebElement> elementsList = driver.findElements(xpath("//article [@class='brand__item']"));
        int actualElementsSize = elementsList.size();
        assertEquals(actualElementsSize, 45);

    }

    @Test(priority = 4)
    public void checkAddToCart() {
        driver.findElement(xpath("//span[@class='sidebar-item']")).click();
        driver.findElement(xpath("//ul[contains(@class,'sidebar-list')]//a[contains(@href,'telefonyi-i-aksessuaryi')]")).click();
        driver.findElement(xpath("//div/a[@href='https://avic.ua/smartfonyi']")).click();
        new WebDriverWait(driver, 30).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        driver.findElement(xpath("//a [contains(@data-ecomm-cart, 'Xiaomi Poco X3 Pro 6\\/128GB Frost Blue')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("js_cart")));
        driver.findElement(xpath("//a [text()='Оформить заказ']")).click();
        new WebDriverWait(driver, 30).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        boolean actualProductsCountInCart =
                driver.findElement(xpath("//span [contains(text(),'Xiaomi Poco X3 Pro 6/128GB Frost Blue')]"))
                        .getText().isEmpty();
        assertFalse(actualProductsCountInCart);
    }

    @Test(priority = 5)
    public void checkColorSmartphone(){
        driver.findElement(xpath("//span[@class='sidebar-item']")).click();
        driver.findElement(xpath("//ul[contains(@class,'sidebar-list')]//a[contains(@href,'telefonyi-i-aksessuaryi')]")).click();
        driver.findElement(xpath("//div/a[@href='https://avic.ua/smartfonyi']")).click();
        new WebDriverWait(driver, 30).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        driver.findElement(xpath("//a [@title='Смартфон Xiaomi Poco X3 Pro 6/128GB Metal Bronze']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 30);//ждем пока не отобразится попап с товаром добавленным в корзину
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img [contains(@src, 'smartfon-xiaomi-poco-x3-pro-6128gb-metal-bronze-2-prod_sm')]")));
        boolean actualValue = driver.findElement(By.xpath("//img [contains(@src, 'smartfon-xiaomi-poco-x3-pro-6128gb-metal-bronze-2-prod_sm')]")).isEnabled();
        assertTrue(actualValue);
    }

    @AfterMethod
    public void tearDown(){
        driver.close();
    }
}
