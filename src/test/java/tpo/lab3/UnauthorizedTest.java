package tpo.lab3;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class UnauthorizedTest {
    private static final String BASE_URL = "https://www.answers.com/";

    @Test
    public void findWithSearchLineTest() {
        final String q = "2 + 2";
        DriverConfig driverConfig = new DriverConfig();
        driverConfig.getDrivers().values().parallelStream().forEach(driver -> {
            driver.get(BASE_URL);
            //type question and send
            getElementByXPath(driver, "//*[@id=\"search-input\"]").sendKeys(q);
            getElementByXPath(driver, "//*[@id=\"root\"]/div/div[2]/main/section[1]/div[2]/button").click();
            //check answer
            String text = getElementByXPath(driver, "//*[@id=\"best-answer\"]/div[3]/div/span").getText();
            assertEquals("This answer is:", text);
        });
        driverConfig.getDrivers().values().forEach(WebDriver::quit);
    }

    @Test
    public void findWithCategories() {
        DriverConfig driverConfig = new DriverConfig();
        driverConfig.getDrivers().values().parallelStream().forEach(driver -> {
            driver.get(BASE_URL);
            //select category and subcategory
            getElementByXPath(driver, "//*[@id=\"root\"]/div/div[2]/main/div[2]/a[6]").click();
            getElementByXPath(driver, "//*[@id=\"root\"]/div/div[2]/div[2]/div[1]").click();
            //select question
            getElementByXPath(driver, "//*[@id=\"root\"]/div/div[2]/div[2]/div[2]").click();
            //check answer
            String text = getElementByXPath(driver, "//*[@id=\"top-answer\"]/div[2]/div[3]/div/span").getText();
            assertEquals("This answer is:", text);
        });
        driverConfig.getDrivers().values().forEach(WebDriver::quit);
    }

    private WebElement getElementByXPath(WebDriver driver, String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(20000));
        return wait.until(visibilityOfElementLocated(By.xpath(xpath)));
    }
}
