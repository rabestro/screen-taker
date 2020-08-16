package intensive;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import static java.util.Objects.requireNonNull;

public final class AppConfig {
    private final Properties properties;
    private final DbxClientV2 client;

    AppConfig(final String fileName) throws IOException {
        properties = new Properties();
        final var inputStream = requireNonNull(
                getClass().getClassLoader().getResourceAsStream(fileName),
                "property file '" + fileName + "' not found in the classpath");

        properties.load(inputStream);

        final var requestConfig = DbxRequestConfig.newBuilder(getClientIdentifier()).build();
        this.client = new DbxClientV2(requestConfig, getToken());
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

    public DbxClientV2 getClient() {
        return client;
    }
}
