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
        driver.findElement(xpath("//*[@id='jvlabelWrap']/jdiv[@class='hoverl_72cd']")).click();
        driver.findElement(xpath("//textarea[@class='inputField_adc3']"))
                .sendKeys( INPUT_TEXT, Keys.ENTER);
        assertTrue(driver.findElement(xpath("//jdiv[contains(@class,'message')]/jdiv[contains(@class,'text')]")).getText().contains( INPUT_TEXT));
    }

    @Test(priority = 3)
    public void checkButtonMi() {
        driver.findElement(xpath("//img [contains(@src,'mi-brand')]")).click();
        new WebDriverWait(driver, 2).until(
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
        new WebDriverWait(driver, 2).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        driver.findElement(xpath("//a [contains(@data-ecomm-cart, 'Смартфон Xiaomi Poco X3 Pro 8\\/256GB Frost Blue')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='js_cart']")));//*
        driver.findElement(xpath("//a [text()='Оформить заказ']")).click();
        new WebDriverWait(driver, 2).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        boolean actualProductsCountInCart =
                driver.findElement(xpath("//span [contains(text(),'Смартфон Xiaomi Poco X3 Pro 8/256GB Frost Blue')]"))
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
        driver.findElement(xpath("//a[contains(@href,'xiaomi-poco-x3-pro-8256gb-metal-bronze')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[contains(@src,'xiaomi-poco-x3-pro-6128gb-metal-bronze')]")));
        boolean actualValue = driver.findElement(By.xpath("//img[contains(@src,'xiaomi-poco-x3-pro-6128gb-metal-bronze')]")).isEnabled();
        assertTrue(actualValue);
    }

    @AfterMethod
    public void tearDown(){
        driver.close();
    }
}
