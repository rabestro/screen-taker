package intensive;

import java.io.IOException;
import java.util.Properties;

import static java.util.Objects.requireNonNull;

public final class AppConfig {
    private final Properties properties;

    AppConfig() {
        properties = new Properties();
    }

    public AppConfig load(final String fileName) throws IOException {
        final var inputStream = requireNonNull(
                getClass().getClassLoader().getResourceAsStream(fileName),
                "property file '" + fileName + "' not found in the classpath");

        properties.load(inputStream);
        return this;
    }

    public String getToken() {
        return properties.getProperty("token");
    }

    public String getClientIdentifier() {
        return properties.getProperty("identifier") + "/" + properties.getProperty("version");
    }

}
