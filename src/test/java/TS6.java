import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TS6 {
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
    public void testAgencyButtonOpensSubWebsite() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(1000);

        WebElement agencyButton = webDriver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/ul/li[5]/a"));
        agencyButton.click();
        Thread.sleep(1000);

        for (String handle : webDriver.getWindowHandles()) {
            webDriver.switchTo().window(handle);
        }

        String currentUrl = webDriver.getCurrentUrl();
        assertTrue(currentUrl.contains("agencija"), "The URL should contain 'agency' indicating the subdomain.");
    }

    @Test
    public void testSubWebsiteLoadsCorrectly() throws InterruptedException {
        webDriver.get("https://agencija.mojposao.ba/");
        Thread.sleep(2000);
        WebElement element = webDriver.findElement(By.xpath("//*[contains(text(), '387 33 846 941')]"));
        assertTrue(element.isDisplayed(), "The element with the specified text should be displayed.");
    }

    @Test
    public void testAnchorLinkNavigation() throws InterruptedException {
        webDriver.get("https://agencija.mojposao.ba/");
        Thread.sleep(2000);

        WebElement parentElement = webDriver.findElement(By.xpath("/html/body/div[1]/div/ul"));
        List<WebElement> links = parentElement.findElements(By.tagName("a"));


        for (WebElement link : links) {

            System.out.println("Link text: " + link.getText());
            System.out.println("Link href: " + link.getAttribute("href"));
            String href = link.getAttribute("href");
            webDriver.get(href);
            Thread.sleep(2000);
            assertEquals(href, webDriver.getCurrentUrl(), "The URL should match the link's href attribute.");
        }

    }
}

