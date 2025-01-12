import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TS10 {

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
    public void testLogOut() throws InterruptedException {
        webDriver.get("https://auth.mojposao.ba/?client_id=1&redirect_uri=https%3A%2F%2Fwww.mojposao.ba%2F#!login");
        Thread.sleep(1000);
        webDriver.manage().window().maximize();

        List<WebElement> elements = webDriver.findElements(By.className("NKV6Q2-G-c"));
        WebElement loginButton2 = webDriver.findElement(By.xpath("//a[contains(text(), 'Logiraj se')]"));

        elements.get(0).sendKeys("email@gmail.com");
        elements.get(1).sendKeys("password");
        loginButton2.click();
        Thread.sleep(10000);

        WebElement menuLink = webDriver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/div[1]/div/a[2]/img"));
        menuLink.click();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/div[1]/div/a[2]/div/div/a[5]")));
        WebElement logoutButton = webDriver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/div[1]/div/a[2]/div/div/a[5]"));
        logoutButton.click();
        Thread.sleep(1000);

        WebElement loginButtonAfterLogout = webDriver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/div[1]/div/a[1]"));
        assertTrue(loginButtonAfterLogout.isDisplayed(), "Login button is not visible after logout.");
    }

    @Test
    public void testSessionCookie() throws InterruptedException {
        webDriver.get("https://auth.mojposao.ba/?client_id=1&redirect_uri=https%3A%2F%2Fwww.mojposao.ba%2F#!login");
        Thread.sleep(1000);
        webDriver.manage().window().maximize();

        List<WebElement> elements = webDriver.findElements(By.className("NKV6Q2-G-c"));
        WebElement loginButton2 = webDriver.findElement(By.xpath("//a[contains(text(), 'Logiraj se')]"));

        elements.get(0).sendKeys("sendeljhusein5@gmail.com");
        elements.get(1).sendKeys("30072003!");
        loginButton2.click();
        Thread.sleep(10000);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/div[1]/div/a[2]/img")));

        Set<Cookie> cookies = webDriver.manage().getCookies();
        boolean sessionCookieFound = false;
        for (Cookie cookie : cookies) {
            System.out.println(cookie);
            if ("DM_SitId473".equals(cookie.getName())) {
                sessionCookieFound = true;
                break;

            }
        }

        assertTrue(sessionCookieFound, "Session cookie should exist after login.");

        WebElement menuLink = webDriver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/div[1]/div/a[2]/img"));
        menuLink.click();
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/div[1]/div/a[2]/div/div/a[5]")));
        WebElement logoutButton = webDriver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/div[1]/div/a[2]/div/div/a[5]"));
        logoutButton.click();
        Thread.sleep(1000);

        Cookie sessionCookieAfterLogout = webDriver.manage().getCookieNamed("SID");

        assertNull(sessionCookieAfterLogout, "Session cookie was not destroyed after logout.");
    }


}
