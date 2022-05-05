package tpo.lab3;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class AuthorizationTest {
    private static final String BASE_URL = "https://www.answers.com/";

    @Test
    public void EmailLoginTest() {
        DriverConfig driverConfig = new DriverConfig();
        driverConfig.getDrivers().values().parallelStream().forEach(driver -> {
            driver.get(BASE_URL);
            //click Log in btn
            getElementByXPath(driver, "//*[@id=\"root\"]/div/div[1]/div/div[4]/div[3]/span/button").click();
            //click Log in with Email
            getElementByXPath(driver, "//*[@id=\"root\"]/div/div[4]/div/div[2]/div/div[2]/div[2]/div/button[4]").click();
            //filling inputs and click submit btn
            getElementByXPath(driver, "//*[@id=\"email-input\"]").sendKeys(ConfigFileReader.getPropertyFromConfigFile("login"));
            getElementByXPath(driver, "//*[@id=\"outlined-adornment-password\"]").sendKeys(ConfigFileReader.getPropertyFromConfigFile("password"));
            getElementByXPath(driver, "//*[@id=\"loginUser\"]/button").click();
            //check logged in
            getElementByXPath(driver, "//*[@id=\"profile-menu\"]").click();
            String text = getElementByXPath(driver, "//*[@id=\"root\"]/div/div[1]/div/div[4]/div[3]/span/div/div/div/a[3]/button/a/span").getText();
            assertEquals("Logout", text);

        });
        driverConfig.getDrivers().values().forEach(WebDriver::quit);
    }

    @Test
    public void FailLoginTest() {
        DriverConfig driverConfig = new DriverConfig();
        driverConfig.getDrivers().values().parallelStream().forEach(driver -> {
            driver.get(BASE_URL);
            //Login
            getElementByXPath(driver, "//*[@id=\"root\"]/div/div[1]/div/div[4]/div[3]/span/button").click();
            getElementByXPath(driver, "//*[@id=\"root\"]/div/div[4]/div/div[2]/div/div[2]/div[2]/div/button[4]").click();
            getElementByXPath(driver, "//*[@id=\"email-input\"]").sendKeys(ConfigFileReader.getPropertyFromConfigFile("login"));
            getElementByXPath(driver, "//*[@id=\"outlined-adornment-password\"]").sendKeys("wrong_password");
            getElementByXPath(driver, "//*[@id=\"loginUser\"]/button").click();
            //Logout
            String text = getElementByXPath(driver, "//*[@id=\"loginUser\"]/div[2]/span").getText();
            assertEquals("Incorrect username or password", text);
        });
        driverConfig.getDrivers().values().forEach(WebDriver::quit);
    }

    @Test
    public void LogoutTest() {
        DriverConfig driverConfig = new DriverConfig();
        driverConfig.getDrivers().values().parallelStream().forEach(driver -> {
            driver.get(BASE_URL);
            //Login
            getElementByXPath(driver, "//*[@id=\"root\"]/div/div[1]/div/div[4]/div[3]/span/button").click();
            getElementByXPath(driver, "//*[@id=\"root\"]/div/div[4]/div/div[2]/div/div[2]/div[2]/div/button[4]").click();
            getElementByXPath(driver, "//*[@id=\"email-input\"]").sendKeys(ConfigFileReader.getPropertyFromConfigFile("login"));
            getElementByXPath(driver, "//*[@id=\"outlined-adornment-password\"]").sendKeys(ConfigFileReader.getPropertyFromConfigFile("password"));
            getElementByXPath(driver, "//*[@id=\"loginUser\"]/button").click();
            //Logout
            getElementByXPath(driver, "//*[@id=\"profile-menu\"]").click();
            getElementByXPath(driver, "//*[@id=\"root\"]/div/div[1]/div/div[4]/div[3]/span/div/div/div/a[3]/button").click();
            String text = getElementByXPath(driver, "//*[@id=\"root\"]/div/div[1]/div/div[4]/div[3]/span/button").getText();
            assertEquals("Log in", text);
        });
        driverConfig.getDrivers().values().forEach(WebDriver::quit);
    }

    private WebElement getElementByXPath(WebDriver driver, String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(20000));
        return wait.until(visibilityOfElementLocated(By.xpath(xpath)));
    }
}
