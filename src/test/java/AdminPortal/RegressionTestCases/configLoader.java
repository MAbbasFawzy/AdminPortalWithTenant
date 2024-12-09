package AdminPortal.RegressionTestCases;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class configLoader {

public Properties properties;
	
	public void ConfigLoader() {
        properties = new Properties();
        loadProperties();
    }
	
	public void loadProperties() {
        try (FileInputStream input = new FileInputStream("resources/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public String getUsername() {
        return properties.getProperty("username");
    }

    public String getPassword() {
        return properties.getProperty("password");
    }
	
	
}
