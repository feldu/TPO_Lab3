package tpo.lab3;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;

import static tpo.lab3.ConfigFileReader.getPropertyFromConfigFile;

@Slf4j
public class DriverConfig {
    private static final String CHROME_SYSTEM_PROPERTY_NAME = "webdriver.chrome.driver";
    private static final String FIREFOX_SYSTEM_PROPERTY_NAME = "webdriver.gecko.driver";
    @Getter
    private final Map<String, WebDriver> drivers = new HashMap<>();

    public DriverConfig() {
        addChromeDriver();
        addFirefoxDriver();
    }

    private void addChromeDriver() {
        if (!System.getProperties().containsKey(CHROME_SYSTEM_PROPERTY_NAME)) {
            try {
                final String propertyValue = getPropertyFromConfigFile(CHROME_SYSTEM_PROPERTY_NAME);
                System.setProperty(CHROME_SYSTEM_PROPERTY_NAME, propertyValue);
            } catch (RuntimeException e) {
                log.warn(e.getMessage());
                log.info("Chrome driver not added to driver list");
                return;
            }
        }
        log.info("Add Chrome driver to drivers list");
        drivers.put("chrome", new ChromeDriver());
    }

    private void addFirefoxDriver() {
        if (!System.getProperties().containsKey(FIREFOX_SYSTEM_PROPERTY_NAME)) {
            try {
                final String propertyValue = getPropertyFromConfigFile(FIREFOX_SYSTEM_PROPERTY_NAME);
                System.setProperty(FIREFOX_SYSTEM_PROPERTY_NAME, propertyValue);
            } catch (RuntimeException e) {
                log.warn(e.getMessage());
                log.info("Firefox driver not added to driver list");
                return;
            }
        }
        log.info("Add Firefox driver to drivers list");
        drivers.put("firefox", new FirefoxDriver());
    }
}
