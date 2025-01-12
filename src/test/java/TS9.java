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

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TS9 {

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
    public void testUserProfilePage() throws InterruptedException {
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/div[1]/div/a[2]/div/div/a[2]")));
        WebElement profileButton = webDriver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/div[1]/div/a[2]/div/div/a[2]"));
        profileButton.click();
        Thread.sleep(1000);

        WebElement userNameField = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div/div/div[1]/div[2]/div/div[1]"));
        assertTrue(userNameField.isDisplayed(), "User name field is not displayed.");

        WebElement userEmailField = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div/div/div[1]/div[2]/div/div[3]/span[5]"));
        assertTrue(userEmailField.isDisplayed(), "User email field is not displayed.");

        WebElement profilePicture = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div/div/div[1]/div[1]/img"));
        assertTrue(profilePicture.isDisplayed(), "Profile picture is not displayed.");
    }

    @Test
    public void testEditUserName() throws InterruptedException {

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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/div[1]/div/a[2]/div/div/a[2]")));
        WebElement option = webDriver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/div[1]/div/a[2]/div/div/a[2]"));
        option.click();
        Thread.sleep(1000);

        WebElement editButton = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div/div/div[1]/div[2]/div/button"));
        editButton.click();

        WebElement userNameField = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div/div/div[1]/div[2]/div/div[1]/div[1]/div/div[1]/div[2]/input"));
        userNameField.clear();
        userNameField.sendKeys("DrugoIme");

        try {
            WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[4]/div[3]/div/div/div/div/div[1]/div[2]/div/div[1]/div[2]/button[1]")));
            saveButton.click();

            WebElement successMessage = webDriver.findElement(By.id("success_message")); //we don't know what happens after so we just left it like this
            assertTrue(successMessage.isDisplayed(), "User name change was not successful.");
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            fail("Failed to click on the Save button.");
        }


    }

    @Test
    public void testEditDate() throws InterruptedException {

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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/div[1]/div/a[2]/div/div/a[2]")));
        WebElement option = webDriver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/div/header/div[1]/div/div/div/div/div[1]/div/a[2]/div/div/a[2]"));
        option.click();
        Thread.sleep(1000);

        WebElement editButton = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div/div/div[1]/div[2]/div/button"));
        editButton.click();

        WebElement dayButton = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div/div/div[1]/div[2]/div/div[1]/div[1]/div/div[3]/table/tbody/tr/td[1]/div/div[1]/button"));
        dayButton.click();
        WebElement day = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div/div/div[1]/div[2]/div/div[1]/div[1]/div/div[3]/table/tbody/tr/td[1]/div/div[1]/div/div/div/div[30]/div")); // Replace '30' with the actual day you need
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", day);

        WebElement monthButton = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div/div/div[1]/div[2]/div/div[1]/div[1]/div/div[3]/table/tbody/tr/td[2]/div/div[1]/button"));
        monthButton.click();
        WebElement month = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div/div/div[1]/div[2]/div/div[1]/div[1]/div/div[3]/table/tbody/tr/td[2]/div/div[1]/div/div/div/div[7]/div")); // Replace '7' with the actual month you need
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", month);

        WebElement yearButton = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div/div/div[1]/div[2]/div/div[1]/div[1]/div/div[3]/table/tbody/tr/td[3]/div/div[1]/button"));
        yearButton.click();
        WebElement year = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div/div/div[1]/div[2]/div/div[1]/div[1]/div/div[3]/table/tbody/tr/td[3]/div/div[1]/div/div/div/div[104]/div")); // Replace '1990' with the actual year you need
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", year);


        try {
            WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[4]/div[3]/div/div/div/div/div[1]/div[2]/div/div[1]/div[2]/button[1]")));
            saveButton.click();

            WebElement successMessage = webDriver.findElement(By.id("success_message")); //we don't know what happens after so we just left it like this
            assertTrue(successMessage.isDisplayed(), "User name change was not successful.");
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            fail("Failed to click on the Save button.");
        }
    }

}
