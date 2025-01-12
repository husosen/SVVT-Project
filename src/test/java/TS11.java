import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TS11 {

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
    public void testMobileLayout() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(2000);

        webDriver.manage().window().setSize(new Dimension(375, 667));
        Thread.sleep(4000);
        WebElement homepageContent = webDriver.findElement(By.xpath("/html/body/div[4]"));
        assertTrue(homepageContent.isDisplayed(), "Homepage content is not responsive.");
    }

    @Test
    public void testTouchEvents() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(2000);

        webDriver.manage().window().setSize(new Dimension(375, 667));

        WebElement searchButton = webDriver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div/div[2]/button"));
        searchButton.click();
        Thread.sleep(2000);

        WebElement jobsDiv = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[2]/div/div/div[2]"));
        assertTrue(jobsDiv.isDisplayed(), "Jobs section is not displayed");
    }

    @Test
    public void testFormFieldsOnMobile() throws InterruptedException {
        webDriver.get("https://auth.mojposao.ba/?client_id=1&redirect_uri=https%3A%2F%2Fwww.mojposao.ba%2F#!login");
        Thread.sleep(1000);

        webDriver.manage().window().setSize(new Dimension(375, 667));

        List<WebElement> elements = webDriver.findElements(By.className("NKV6Q2-G-c"));
        WebElement loginButton2 = webDriver.findElement(By.xpath("//a[contains(text(), 'Logiraj se')]"));

        elements.get(0).sendKeys("email@gmail.com");
        elements.get(1).sendKeys("password");
        loginButton2.click();
        Thread.sleep(10000);


        assertEquals("https://www.mojposao.ba/", webDriver.getCurrentUrl(), "User should be redirected to the dashboard after successful login");
    }

}
