import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TS3 {
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
    public void testSearchFieldFunctionality() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(1000);
        WebElement searchField = webDriver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div/div[1]/div[1]/div[1]/div[2]/div/input"));
        searchField.sendKeys("Software Engineer");
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("arguments[0].blur();", searchField);
        Thread.sleep(2000);
        assertEquals("https://www.mojposao.ba/poslovi?keyword=Software+Engineer&page=1", webDriver.getCurrentUrl(), "Search results should be displayed");
        List<WebElement> results = webDriver.findElements(By.className("EV5KEN-pg-k"));
        assertTrue(results.size() > 0, "Search results should be displayed");
        System.out.println("There are " + results.size() + " search results");
    }


    @Test
    public void testFilteringOptions() throws InterruptedException {
        String searchLocation = "Sarajevo";
        webDriver.get(baseUrl);
        Thread.sleep(1000);
        WebElement searchField = webDriver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div/div[1]/div[1]/div[1]/div[2]/div/input"));
        searchField.sendKeys("Menadžer");
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("arguments[0].blur();", searchField);
        Thread.sleep(1000);
        WebElement location = webDriver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div/div[1]/div[1]/div[2]/div[2]/div/input"));
        location.sendKeys(searchLocation);
        js.executeScript("arguments[0].blur();", location);
        Thread.sleep(1000);
        List<WebElement> parentElements = webDriver.findElements(By.className("EV5KEN-pg-k"));
        boolean allContainText = true;
        for (WebElement parent : parentElements) {
            List<WebElement> childElements = parent.findElements(By.className("EV5KEN-pg-l"));
            boolean found = false;
            for (WebElement child : childElements) {
                List<WebElement> subChildElements = child.findElements(By.className("EV5KEN-pg-c"));
                for (WebElement subChild : subChildElements) {
                    List<WebElement> links = subChild.findElements(By.tagName("a"));
                    for (WebElement link : links) {
                        if (link.getText().contains(searchLocation)) {
                            found = true;
                            break;
                        }
                    } if (found) break;
                } if (found) break;
            }
            if (!found) {
                allContainText = false;
                break;
            }
        }
        // Each EV5KEN-pg-k element should contain one EV5KEN-pg-l element with an EV5KEN-pg-c element that has an a tag with the specified text.
        assertTrue(allContainText, "All job listings should be from the specified location");
    }

    @Test
    public void testJobListingDetails() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(1000);
        WebElement searchField = webDriver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div/div[1]/div[1]/div[1]/div[2]/div/input"));
        searchField.sendKeys("Kuhar");
        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        WebElement popupPanel = webDriver.findElement(By.className("gwt-PopupPanel"));
        js.executeScript("arguments[0].style.visibility='hidden';", popupPanel);
        js.executeScript("arguments[0].blur();", searchField);
        Thread.sleep(1000);
        List<WebElement> parentElements = webDriver.findElements(By.className("EV5KEN-pg-k"));

        for (int i = 0; i < Math.min(3, parentElements.size()); i++) {
            WebElement parent = parentElements.get(i);
            List<WebElement> childElements = parent.findElements(By.className("EV5KEN-pg-l"));
            boolean found = false;
            for (WebElement child : childElements) {
                List<WebElement> subChildElements = child.findElements(By.className("EV5KEN-pg-w"));
                for (WebElement subChild : subChildElements) {
                    List<WebElement> links = subChild.findElements(By.tagName("a"));
                    for (WebElement link : links) {
                        String href = link.getAttribute("href");
                        link.click();
                        Thread.sleep(2000);
                        assertEquals(href, webDriver.getCurrentUrl(), "The opened link should match the href attribute.");
                        webDriver.navigate().back();
                        Thread.sleep(2000);
                        found = true;
                        break;
                    } if (found) break;
                } if (found) break;
            }
        }
    }
}