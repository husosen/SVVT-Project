import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TS1 {

    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\faris\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe"); // specify the path to chromedriver
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
    public void testMainMenuLinks() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(1000);
        String[] menuLinkXpaths = {
                "/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/ul/li[1]/a", "/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/ul/li[2]/a", "/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/ul/li[3]/a", "/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/ul/li[4]/a", "/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/ul/li[5]/a", "/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/ul/li[6]/a", "/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/a[2]", "/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/div[1]/div/a[1]", "/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/div[1]/div/a[3]/div/a[1]", "/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/div[1]/div/a[3]/div/a[2]"
        };
        String[] expectedUrls = {
                "https://www.mojposao.ba/poslovi", "https://www.mojposao.ba/poslodavci", "https://www.mojposao.ba/poslodavci/profil?id=1&postGroupId=4", "https://www.mojposao.ba/poslodavci/profil?id=1&postId=795", "https://agencija.mojposao.ba/", "https://www.plata.ba/", "https://auth.mojposao.ba/?client_id=1&redirect_uri=https%3A%2F%2Fwww.mojposao.ba%2F%3Baddjob%3Dtrue#!login", "https://auth.mojposao.ba/?client_id=1&redirect_uri=https%3A%2F%2Fwww.mojposao.ba%2F#!login", "https://auth.mojposao.ba/?client_id=1&continue=https%3A%2F%2Fwww.mojposao.ba%3Fregistration%3Dconfirmed%26registration_type%3Dpeople#!register/people", "https://auth.mojposao.ba/?client_id=1&continue=https%3A%2F%2Fwww.mojposao.ba%3Fregistration%3Dconfirmed%26registration_type%3Dcompany#!register/company"
        };

        for (int i = 0; i < menuLinkXpaths.length-2; i++) {
            WebElement menuLink = webDriver.findElement(By.xpath(menuLinkXpaths[i]));
            ((JavascriptExecutor) webDriver).executeScript("arguments[0].removeAttribute('target')", menuLink);
            menuLink.click();
            Thread.sleep(1000);
            assertEquals(expectedUrls[i], webDriver.getCurrentUrl());
            webDriver.navigate().back();
            Thread.sleep(1000);
        }
        for (int i = 8; i < menuLinkXpaths.length; i++) {
            Thread.sleep(1000);
            WebElement menuLink = webDriver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/div[1]/div/a[3]"));
            menuLink.click();
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(menuLinkXpaths[i])));
            WebElement option = webDriver.findElement(By.xpath(menuLinkXpaths[i]));
            option.click();
            Thread.sleep(1000);
            assertEquals(expectedUrls[i], webDriver.getCurrentUrl());
            webDriver.navigate().back();
            Thread.sleep(1000);
        }
    }

    @Test
    public void testLogoRedirection() throws InterruptedException {
        webDriver.get("https://www.mojposao.ba/poslovi");
        Thread.sleep(1000);
        WebElement logo = webDriver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/div/header/a"));
        logo.click();
        Thread.sleep(1000);
        assertTrue(webDriver.getCurrentUrl().equals(baseUrl));
    }

    @Test
    public void testHomePageJobPosting() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(5000);
        List<WebElement> jobPostings = webDriver.findElements(By.className("EV5KEN-bj-f"));
        assertTrue(jobPostings.size() >= 10, "There should be at least 10 job postings displayed.");
        System.out.println("There are currently " + jobPostings.size() + " job postings displayed on the home page.");
    }
}