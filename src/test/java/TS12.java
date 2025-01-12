import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TS12 {
    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\sende\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe"); // specify the path to chromedriver
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
    public void testHomePageLoadTime() throws InterruptedException {
        long startTime = System.currentTimeMillis();

        webDriver.get(baseUrl);
        Thread.sleep(1000);

        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime - 1000; //minus the thread.sleep time

        assertTrue(loadTime < 5000, "Home page load time is greater than 5 seconds");
    }

    @Test
    public void tstPageToPageLoadTime() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(5000);

        long startTime = System.currentTimeMillis();

        WebElement jobSearchLink = webDriver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div/div[2]/button"));
        jobSearchLink.click();

        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;

        assertTrue(loadTime < 5000, "Page to page load time is greater than 5 seconds");
    }

    @Test
    public void testFormSubmissionLoadTime() throws InterruptedException {

        webDriver.get("https://auth.mojposao.ba/?client_Sid=1&redirect_uri=https%3A%2F%2Fwww.mojposao.ba%2F#!login");
        Thread.sleep(2000);
        webDriver.manage().window().maximize();

        List<WebElement> elements = webDriver.findElements(By.className("NKV6Q2-G-c"));
        WebElement loginButton2 = webDriver.findElement(By.xpath("//a[contains(text(), 'Logiraj se')]"));

        elements.get(0).sendKeys("email@gmail.com");
        elements.get(1).sendKeys("password");

        long startTime = System.currentTimeMillis();
        loginButton2.click();
        Thread.sleep(2000);


        assertEquals(baseUrl, webDriver.getCurrentUrl(), "User should be redirected to the dashboard after successful login");

        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime - 2000; //thread.sleep time



        assertTrue(loadTime < 5000, "Form submission load time is greater than 5 seconds");
    }

}
