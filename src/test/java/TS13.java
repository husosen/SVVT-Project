import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TS13 {
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
    public void testPrivacyPolicy() throws InterruptedException {
        webDriver.get(baseUrl);

        Thread.sleep(2000);

        WebElement privacyPolicy = webDriver.findElement(By.xpath("/html/body/div[4]/div[5]/ul[2]/li[3]/a"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", privacyPolicy);

        Thread.sleep(2000);

        WebElement privacyPolicyPageIdentifier = webDriver.findElement(By.xpath("/html/body/div[6]/div/div/div/div[2]/div/div/table/tbody/tr/td[1]/div/div/div[1]"));
        assertTrue(privacyPolicyPageIdentifier.isDisplayed(), "Privacy Policy page was not displayed.");
    }

    @Test
    public void testTermsOfService() throws InterruptedException {
        webDriver.get(baseUrl);

        Thread.sleep(2000);

        WebElement termsOfService = webDriver.findElement(By.xpath("/html/body/div[4]/div[5]/ul[2]/li[1]/a"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", termsOfService);

        Thread.sleep(2000);


        WebElement termsOfServiceIdentifier = webDriver.findElement(By.xpath("/html/body/div[6]/div/div/div/div[2]/div/div/table/tbody/tr/td[1]/div/div/div[1]"));
        assertTrue(termsOfServiceIdentifier.isDisplayed(), "Privacy Policy page was not displayed.");
    }
}
