package tpo.lab3;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class AuthorizedTest {
    private static final String BASE_URL = "https://www.answers.com/";
    private static final String TEST_ANSWER_Q_URL = "https://www.answers.com/Q/Who-killed-l-palmer";
    private static final String TEST_LIKES_Q_URL = "https://math.answers.com/algebra/Factors_of_23";

    @Test
    public void createQuestion() {
        DriverConfig driverConfig = new DriverConfig();
        driverConfig.getDrivers().values().parallelStream().forEach(driver -> {
            final String q = "Who killed L. Palmer " + System.currentTimeMillis() + "?";
            driver.get(BASE_URL);
            //login
            login(driver);
            //create question
            getElementByXPath(driver, "//*[@id=\"root\"]/div/div[1]/div/div[4]/div[1]").click();
            getElementByXPath(driver, "//*[@id=\"root\"]/div/div[1]/div/div[4]/div[1]/div/div/div/span[1]/button").click();
            getElementByXPath(driver, "//*[@id=\"root\"]/div/div[4]/div/div[2]/div/div[2]/div[1]/textarea").sendKeys(q);
            getElementByXPath(driver, "//*[@id=\"root\"]/div/div[4]/div/div[2]/div/div[1]/button[2]").click();
            //check answer
            String text = getElementByXPath(driver, "//*[@id=\"root\"]/div/div[2]/div/div[2]/div[1]/h1/a").getText();
            assertEquals(q, text);
        });
        driverConfig.getDrivers().values().forEach(WebDriver::quit);
    }

    @Test
    public void createAnswer() {
        DriverConfig driverConfig = new DriverConfig();
        driverConfig.getDrivers().values().parallelStream().forEach(driver -> {
            final String ans = "L. Palmer killed L. Palmer";
            driver.get(TEST_ANSWER_Q_URL);
            //login
            login(driver);
            //create answer
            getElementByXPath(driver, "//*[@id=\"root\"]/div/div[2]/div/div[5]/div[2]/div[1]").click();
            getElementByXPath(driver, "//*[@id=\"root\"]/div/div[4]/div/div[2]/div/div[2]/div/div[1]/div[2]/div").sendKeys(ans);
            getElementByXPath(driver, "//*[@id=\"root\"]/div/div[4]/div/div[2]/div/div[1]/button[2]").click();
            //check answer
            String text = getElementByXPath(driver, "/html/body/div[1]/div[1]/div[2]/div/div/div[5]/div/span[2]").getText();
            assertEquals("You've already answered this question.", text);
        });
        driverConfig.getDrivers().values().forEach(WebDriver::quit);
    }

    @Test
    public void likeAnswer() {
        DriverConfig driverConfig = new DriverConfig();
        driverConfig.getDrivers().values().parallelStream().forEach(driver -> {
            String spanText;
            long likesOld, likesNew, likesNewNew;
            driver.get(TEST_LIKES_Q_URL);
            //login
            login(driver);
            //check like
            //get old like value
            spanText = getElementByXPath(driver, "//*[@id=\"top-answer\"]/div[2]/div[3]/div/div/button[1]/span[2]").getText();
            likesOld = Integer.parseInt(spanText.substring(9, spanText.length() - 1));
            //fuck shit
            (new WebDriverWait(driver, Duration.ofMillis(3000))).until(ExpectedConditions.invisibilityOfElementLocated
                    (By.xpath("//*[@class='m-auto px-9 py-3 sm:p-16 max-w-750']")));
            //click like
            getElementByXPath(driver, "//*[@id=\"top-answer\"]/div[2]/div[3]/div/div/button[1]").click();
            //get new like value
            spanText = getElementByXPath(driver, "//*[@id=\"top-answer\"]/div[2]/div[3]/div/div/button[1]/span[2]").getText();
            likesNew = Integer.parseInt(spanText.substring(9, spanText.length() - 1));
            //assert it
            assertEquals(likesOld + 1, likesNew);
            //unlike
            getElementByXPath(driver, "//*[@id=\"top-answer\"]/div[2]/div[3]/div/div/button[2]").click();
            //get the newest like value
            spanText = getElementByXPath(driver, "//*[@id=\"top-answer\"]/div[2]/div[3]/div/div/button[1]/span[2]").getText();
            likesNewNew = Integer.parseInt(spanText.substring(9, spanText.length() - 1));
            //assert it
            assertEquals(likesOld, likesNewNew);
        });
        driverConfig.getDrivers().values().forEach(WebDriver::quit);
    }

    @Test
    public void dislikeAnswer() {
        DriverConfig driverConfig = new DriverConfig();
        driverConfig.getDrivers().values().parallelStream().forEach(driver -> {
            String spanText;
            long likesOld, likesNew, likesNewNew;
            driver.get(TEST_LIKES_Q_URL);
            //login
            login(driver);
            //check dislike
            //get old dislike value
            spanText = getElementByXPath(driver, "//*[@id=\"top-answer\"]/div[2]/div[3]/div/div/button[2]/span[2]").getText();
            likesOld = Integer.parseInt(spanText.substring(13, spanText.length() - 1));
            //fuck shit
            (new WebDriverWait(driver, Duration.ofMillis(3000))).until(ExpectedConditions.invisibilityOfElementLocated
                    (By.xpath("//*[@class='m-auto px-9 py-3 sm:p-16 max-w-750']")));
            //click dislike
            getElementByXPath(driver, "//*[@id=\"top-answer\"]/div[2]/div[3]/div/div/button[2]").click();
            //get new dislike value
            spanText = getElementByXPath(driver, "//*[@id=\"top-answer\"]/div[2]/div[3]/div/div/button[2]/span[2]").getText();
            likesNew = Integer.parseInt(spanText.substring(13, spanText.length() - 1));
            //assert it
            assertEquals(likesOld + 1, likesNew);
            //cancel dislike
            getElementByXPath(driver, "//*[@id=\"top-answer\"]/div[2]/div[3]/div/div/button[1]").click();
            //get the newest dislike value
            spanText = getElementByXPath(driver, "//*[@id=\"top-answer\"]/div[2]/div[3]/div/div/button[2]/span[2]").getText();
            likesNewNew = Integer.parseInt(spanText.substring(13, spanText.length() - 1));
            //assert it
            assertEquals(likesOld, likesNewNew);
        });
        driverConfig.getDrivers().values().forEach(WebDriver::quit);
    }

    private void login(WebDriver driver) {
        getElementByXPath(driver, "//*[@id=\"root\"]/div/div[1]/div/div[4]/div[3]/span/button").click();
        getElementByXPath(driver, "//*[@id=\"root\"]/div/div[4]/div/div[2]/div/div[2]/div[2]/div/button[4]").click();
        getElementByXPath(driver, "//*[@id=\"email-input\"]").sendKeys(ConfigFileReader.getPropertyFromConfigFile("login"));
        getElementByXPath(driver, "//*[@id=\"outlined-adornment-password\"]").sendKeys(ConfigFileReader.getPropertyFromConfigFile("password"));
        getElementByXPath(driver, "//*[@id=\"loginUser\"]/button").click();
        //wait login window closed
        (new WebDriverWait(driver, Duration.ofMillis(3000))).until(ExpectedConditions.invisibilityOfElementLocated
                (By.xpath("//*[@class='absolute inset-0 bg-black opacity-75']")));
    }

    private WebElement getElementByXPath(WebDriver driver, String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(20000));
        return wait.until(visibilityOfElementLocated(By.xpath(xpath)));
    }
}
