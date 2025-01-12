import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TS5 {
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
    public void testSavjetiPageLoadsCorrectly() throws InterruptedException {
        webDriver.get("https://www.mojposao.ba/poslodavci/profil?id=1&postGroupId=4");
        Thread.sleep(2000);
        List<WebElement> jobListings = webDriver.findElements(By.className("EV5KEN-Vh-h"));
        assertTrue(jobListings.size() > 0, "There should be at least one article displayed.");
        System.out.println("There are " + jobListings.size() + " articles displayed.");
    }

    @Test
    public void testArticleLinksRedirectCorrectly() throws InterruptedException {
        webDriver.get("https://www.mojposao.ba/poslodavci/profil?id=1&postGroupId=4");
        Thread.sleep(2000);
        List<WebElement> jobListings = webDriver.findElements(By.className("EV5KEN-Vh-h"));
        assertTrue(jobListings.size() > 0, "There should be at least one job listing.");

        WebElement jobListing = jobListings.getFirst();
        WebElement link = jobListing.findElement(By.linkText("pročitaj članak"));
        String articleTitle = jobListing.findElement(By.className("EV5KEN-Vh-n")).getText();
        link.click();
        Thread.sleep(2000);
        WebElement openedArticleTitle = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[3]/div[3]/div[2]/div[1]/h1"));
        assertEquals(articleTitle, openedArticleTitle.getText(), "The opened job listing title should match the original article title.");
        webDriver.navigate().back();
        Thread.sleep(2000);
    }

    @Test
    public void testSearchFunctionality() throws InterruptedException {
        String searchStr = "Bruto";
        webDriver.get("https://www.mojposao.ba/poslodavci/profil?id=1&postGroupId=4");
        Thread.sleep(2000);
        WebElement searchField = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div/div[3]/div[2]/div/div/div/div/div[1]/div[1]/div/input"));
        searchField.sendKeys(searchStr);
        Thread.sleep(2000);
        List<WebElement> searchResults = webDriver.findElements(By.className("EV5KEN-Vh-h"));
        assertTrue(searchResults.size() > 0, "There should be at least one search result displayed.");

        WebElement jobListing = searchResults.getFirst();
        WebElement title = jobListing.findElement(By.className("EV5KEN-Vh-n"));
        assertTrue(title.getText().toLowerCase().contains(searchStr.toLowerCase()), "The search results should contain the search string.");

    }
}