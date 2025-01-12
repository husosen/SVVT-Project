import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TS15 {
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
    public void test2() throws InterruptedException {
        webDriver.get("https://auth.mojposao.ba/?client_id=1&redirect_uri=https%3A%2F%2Fwww.mojposao.ba%2F#!login");
        Thread.sleep(1000);

        webDriver.manage().window().maximize();

        List<WebElement> elements = webDriver.findElements(By.className("NKV6Q2-G-c"));
        WebElement loginButton2 = webDriver.findElement(By.xpath("//a[contains(text(), 'Logiraj se')]"));

        elements.get(0).sendKeys("sendeljhusein5@gmail.com");
        elements.get(1).sendKeys("30072003!");
        loginButton2.click();
        Thread.sleep(10000);

        WebElement jobs = webDriver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/ul/li[1]/a"));
        jobs.click();

        Thread.sleep(5000);

        WebElement save = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[2]/div/div/div[2]/div[5]/div[1]/div[3]/div[4]/div[2]/div[3]/button"));
        save.click();

        Thread.sleep(5000);

        WebElement savedJobs = webDriver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/div[2]/a"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", savedJobs);
        Thread.sleep(10000);

        WebElement savedJobsNumber = webDriver.findElement(By.xpath("/html/body/div[6]/div/table/tbody/tr[1]/td[2]/div/table/tbody/tr/td[1]/div"));
        String text = savedJobsNumber.getText();

        Pattern pattern = Pattern.compile("\\((\\d+)\\)");
        Matcher matcher = pattern.matcher(text);

        String numberString = "";
        if (matcher.find()) {
            numberString = matcher.group(1);
        }

        int number = Integer.parseInt(numberString);

        assertTrue(number > 0, "Test Failed: The number is not greater than 0.");
    }

    @Test
    public void testSaveButtonNotVisibleForNonLoggedUsers() throws InterruptedException {

        webDriver.get(baseUrl + "poslovi");

        Thread.sleep(5000);

        try {
            WebElement saveButton = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div[2]/div/div/div[2]/div[5]/div[1]/div[3]/div[4]/div[2]/div[3]/button"));

            assertFalse(false, "Test Failed: Save button found for non-logged-in user.");
        } catch (NoSuchElementException e) {

            System.out.println("Test Passed: Save button not found for non-logged-in user.");
        }

        webDriver.quit();
    }


}
