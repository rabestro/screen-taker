package intensive;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        return properties.getProperty("identifier", "IntensiveSpy") + "/" + properties.getProperty("version");
    }

    public long getInterval() {
        return Long.parseLong(properties.getProperty("interval", "5000"));
    }

    public DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern(properties.getProperty("formatter", "yyyyMMdd_HHmmss"));
    }

    public String getImageType() {
        return properties.getProperty("image_type", "png");
    }

    public String getFileNameNow() {
        return "/" + LocalDateTime.now().format(getFormatter()) + "." + getImageType();
    }
}
