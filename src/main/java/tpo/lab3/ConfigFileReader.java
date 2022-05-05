package tpo.lab3;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class ConfigFileReader {
    @Getter
    @Setter
    private static String PROPERTIES_PATH = "src/main/resources/lab3.properties";

    public static String getPropertyFromConfigFile(final String PROP_NAME) {
        String propertyValue;
        try {
            List<String> properties = Files.readAllLines(Paths.get(PROPERTIES_PATH));
            for (String property : properties) {
                if (property.startsWith(PROP_NAME)) {
                    propertyValue = property.toLowerCase().split("=")[1].trim();
                    return propertyValue;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        throw new RuntimeException("Property with name " + PROP_NAME + " not found in config " + PROPERTIES_PATH);
    }
}
