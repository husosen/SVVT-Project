import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TS2 {

    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\faris\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
        baseUrl = "https://www.mojposao.ba/";
    }

    @AfterEach
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    public void testRequiredFields() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(1000);
        WebElement loginButton = webDriver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/div[1]/div/a[1]"));
        loginButton.click();
        Thread.sleep(2000);
        WebElement link = webDriver.findElement(By.xpath("//a[contains(text(), 'Logiraj se')]"));
        link.click();
        Thread.sleep(1000);
        List<WebElement> errors = webDriver.findElements(By.xpath("//div[contains(text(), 'Polje ne može biti prazno')]"));
        assertEquals(2, errors.size(), "There should be exactly 2 error messages displayed");

        for (WebElement error : errors) {
            assertTrue(error.isDisplayed(), "Error message should be displayed");
        }
    }

    @Test
    public void testSuccessfulLogin() throws InterruptedException {
        webDriver.get("https://auth.mojposao.ba/?client_id=1&redirect_uri=https%3A%2F%2Fwww.mojposao.ba%2F#!login");
        Thread.sleep(1000);

        List<WebElement> elements = webDriver.findElements(By.className("NKV6Q2-G-c"));
        WebElement loginButton2 = webDriver.findElement(By.xpath("//a[contains(text(), 'Logiraj se')]"));

        elements.get(0).sendKeys("ACCOUNT EMAIL");
        elements.get(1).sendKeys("ACCOUNT PASSWORD");
        loginButton2.click();
        Thread.sleep(2000);
        assertEquals("https://www.mojposao.ba/", webDriver.getCurrentUrl(), "User should be redirected to the dashboard after successful login");
    }

    @Test
    public void testFailedLogin() throws InterruptedException {
        webDriver.get("https://auth.mojposao.ba/?client_id=1&redirect_uri=https%3A%2F%2Fwww.mojposao.ba%2F#!login");
        Thread.sleep(1000);

        List<WebElement> elements = webDriver.findElements(By.className("NKV6Q2-G-c"));
        WebElement loginButton2 = webDriver.findElement(By.xpath("//a[contains(text(), 'Logiraj se')]"));

        elements.get(0).sendKeys("WRONGEMAIL@gmail.com");
        elements.get(1).sendKeys("SUPERDUPERSECRETPASSWORD123");
        loginButton2.click();
        Thread.sleep(2000);
        WebElement error = webDriver.findElement(By.xpath("//div[contains(text(), 'E-mail/korisničko ime ili lozinka je neispravan')]"));

        assertTrue(error.isDisplayed(), "Error message should be displayed");
    }
}
