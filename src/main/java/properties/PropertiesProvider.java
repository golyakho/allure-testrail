package properties;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.Properties;

public class PropertiesProvider {

    private static final Properties PROPERTIES = new Properties();
    private static PropertiesProvider instance;

    static {
        load();
    }

    private static void load() {
        instance = new PropertiesProvider();
        try {
            PROPERTIES.load(requireNonNull(instance.getClass()
                                                   .getClassLoader()
                                                   .getResourceAsStream("allure.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(PropertyNames propertyName) {
        return PROPERTIES.getProperty(propertyName.getProperty());
    }
}
