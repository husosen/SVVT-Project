import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TS14 {
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
    public void testNextButton() throws InterruptedException {

        webDriver.get(baseUrl + "poslovi");
        Thread.sleep(5000);

        WebElement page2 = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[2]/div/div/div[2]/div[2]/div[3]/div/table/tbody/tr/td[4]/button"));
        page2.click();
        assertTrue(webDriver.getCurrentUrl().contains("page=2"));

        WebElement page3 = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[2]/div/div/div[2]/div[2]/div[3]/div/table/tbody/tr/td[4]/button"));
        page3.click();
        assertTrue(webDriver.getCurrentUrl().contains("page=3"));


    }

    @Test
    public void testBackButton() throws InterruptedException {

        webDriver.get(baseUrl + "poslovi?page=8");
        Thread.sleep(5000);

        WebElement page7 = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[2]/div/div/div[2]/div[2]/div[3]/div/table/tbody/tr/td[2]/button"));
        page7.click();
        assertTrue(webDriver.getCurrentUrl().contains("page=7"));

        WebElement page6 = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[2]/div/div/div[2]/div[2]/div[3]/div/table/tbody/tr/td[2]/button"));
        page6.click();
        assertTrue(webDriver.getCurrentUrl().contains("page=6"));

    }
}
