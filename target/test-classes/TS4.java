import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class TS4 {
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
    public void testJobDetailsDisplayed() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(1000);
        WebElement searchField = webDriver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div/div[1]/div[1]/div[1]/div[2]/div/input"));
        searchField.sendKeys("Kuhar");
        Thread.sleep(1000);

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        WebElement popupPanel = webDriver.findElement(By.className("gwt-PopupPanel"));
        js.executeScript("arguments[0].style.visibility='hidden';", popupPanel);
        js.executeScript("arguments[0].blur();", searchField);

        Thread.sleep(2000);

        List<WebElement> jobListings = webDriver.findElements(By.className("EV5KEN-pg-k"));
        assertTrue(jobListings.size() > 0, "There should be at least one job listing displayed.");

        WebElement firstJob = jobListings.get(0);
        List<WebElement> subChildElements = firstJob.findElements(By.className("EV5KEN-pg-w"));
        boolean found = false;

        for (WebElement subChild : subChildElements) {
            List<WebElement> links = subChild.findElements(By.tagName("a"));
            if (!links.isEmpty()) {
                WebElement link = links.get(0);
                link.click();
                Thread.sleep(2000);
                found = true;
                break;
            }
        }

        assertTrue(found, "There should be a link to the job post.");

        WebElement jobTitle = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[3]/div[3]/div[2]/div[1]/h1"));
        WebElement jobDescription = webDriver.findElement(By.className("EV5KEN-i-g"));
        assertTrue(jobTitle.isDisplayed(), "Job title should be displayed.");
        assertTrue(jobDescription.isDisplayed(), "Job description should be displayed.");
    }

    @Test
    public void testApplyButtonVisibleAndFunctional() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(1000);
        WebElement searchField = webDriver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div/div[1]/div[1]/div[1]/div[2]/div/input"));
        searchField.sendKeys("Kuhar");
        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        WebElement popupPanel = webDriver.findElement(By.className("gwt-PopupPanel"));
        js.executeScript("arguments[0].style.visibility='hidden';", popupPanel);
        js.executeScript("arguments[0].blur();", searchField);
        Thread.sleep(2000);
        List<WebElement> jobListings = webDriver.findElements(By.className("EV5KEN-pg-k"));
        assertTrue(jobListings.size() > 0, "There should be at least one job listing displayed.");
        WebElement firstJob = jobListings.get(0);
        List<WebElement> subChildElements = firstJob.findElements(By.className("EV5KEN-pg-w"));
        boolean found = false;

        for (WebElement subChild : subChildElements) {
            List<WebElement> links = subChild.findElements(By.tagName("a"));
            if (!links.isEmpty()) {
                WebElement link = links.get(0);
                link.click();
                Thread.sleep(2000);
                found = true;
                break;
            }
        }

        assertTrue(found, "There should be a link to the job post.");
        WebElement applyButton = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[3]/div[3]/div[2]/div[2]/div/div[1]/a"));
        WebElement jobTitle = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[3]/div[3]/div[2]/div[1]/h1"));
        String jobTitleText = jobTitle.getText();
        assertTrue(applyButton.isDisplayed(), "Apply button should be visible.");
        applyButton.click();
        Thread.sleep(2000);
        WebElement jobTitleA = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[2]/div[1]/div[1]/h1"));
        WebElement saveButton = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[2]/div[3]/div[6]/button[1]"));
        assertEquals(jobTitleText, jobTitleA.getText());
        assertTrue(saveButton.isDisplayed(), "Save button should be visible.");
    }

    @Test
    public void testJobPostingNotExpired() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(1000);
        WebElement searchField = webDriver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div/div[1]/div[1]/div[1]/div[2]/div/input"));
        searchField.sendKeys("Kuhar");
        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        WebElement popupPanel = webDriver.findElement(By.className("gwt-PopupPanel"));
        js.executeScript("arguments[0].style.visibility='hidden';", popupPanel);
        js.executeScript("arguments[0].blur();", searchField);
        Thread.sleep(2000);
        List<WebElement> jobListings = webDriver.findElements(By.className("EV5KEN-pg-k"));
        assertTrue(jobListings.size() > 0, "There should be at least one job listing displayed.");
        WebElement firstJob = jobListings.getFirst();
        List<WebElement> subChildElements = firstJob.findElements(By.className("EV5KEN-pg-w"));
        boolean found = false;

        for (WebElement subChild : subChildElements) {
            List<WebElement> links = subChild.findElements(By.tagName("a"));
            if (!links.isEmpty()) {
                WebElement link = links.getFirst();
                link.click();
                Thread.sleep(2000);
                found = true;
                break;
            }
        }

        assertTrue(found, "There should be a link to the job post.");
        WebElement expDate = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[3]/div[3]/div[2]/div[1]/div[2]/div[4]/span[2]"));
        Pattern datePattern = Pattern.compile("\\d{2}.\\d{2}.\\d{4}."); // dd.MM.yyyy.
        Matcher matcher = datePattern.matcher(expDate.getText());
        if (matcher.find()) {
            try {
                String dateString = matcher.group();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(dateString, formatter);
                assertTrue(date.isAfter(LocalDate.now()), "Expiration date should be in the future.");
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        } else {
            fail("Expiration date should be displayed.");
        }
    }
}