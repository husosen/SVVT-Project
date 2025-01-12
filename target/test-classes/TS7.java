import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TS7 {
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
    public void testCompanyProfileInformation() throws InterruptedException {
        webDriver.get(baseUrl + "poslodavci");
        Thread.sleep(2000);

        WebElement companyName = webDriver.findElement(By.xpath("//div[@class='EV5KEN-Bh-b']"));
        WebElement companyLocation = webDriver.findElement(By.xpath("//div[@class='EV5KEN-Bh-f']"));

        assertTrue(companyName.isDisplayed(), "Company name should be displayed.");
        assertTrue(companyLocation.isDisplayed(), "Company description should be displayed.");
    }

    @Test
    public void testOpenPositionsFromCompany() throws InterruptedException {
        webDriver.get(baseUrl + "poslodavci");
        Thread.sleep(3000);

        List<WebElement> comapnies = webDriver.findElements(By.xpath("//div[@class='EV5KEN-Bh-b']"));
        WebElement company = comapnies.getFirst();
        String companyName = company.getText();

        company.findElements(By.tagName("a")).get(0).click();
        Thread.sleep(2000);
        assertEquals(companyName, webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div/div[3]/div[3]/div[2]/div[2]/a")).getText(), "Company name should be displayed on the company profile page.");

        List<WebElement> elements = webDriver.findElements(By.className("EV5KEN-ij-d"));

        List<String> jobPostings = new ArrayList<>();
        if (elements.size() > 0) {
            for (WebElement element : elements) {
                System.out.println("Job postings: " + element.getText());
                jobPostings.add(element.getText());
            }

            assertTrue(jobPostings.size() > 0, "There should be at least one child element with the specified class.");
            System.out.println("There are " + jobPostings.size() + " job postings.");
        } else {
            System.out.println("The element with the specified XPath does not exist.");
            fail("The company does not have open positions.");
        }
    }

    @Test
    public void testFollowCompanyProfile() throws InterruptedException {
        webDriver.get(baseUrl + "poslodavci");
        Thread.sleep(3000);

        List<WebElement> comapnies = webDriver.findElements(By.xpath("//div[@class='EV5KEN-Bh-b']"));
        WebElement company = comapnies.getFirst();
        String companyName = company.getText();

        company.findElements(By.tagName("a")).get(0).click();
        Thread.sleep(2000);

        WebElement followButton = webDriver.findElement(By.xpath("/html/body/div[4]/div[3]/div/div/div/div[1]/table/tbody/tr[1]/td[3]/div/button"));
        followButton.click();
        Thread.sleep(2000);

        WebElement text = webDriver.findElement(By.className("NKV6Q2-b-d"));
        assertTrue(text.getText().contains("Login"), "The button did not take us to the login page.");
    }
}