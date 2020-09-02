package intensive;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public final class AppConfig {
    private static final String SCREEN_TAKER_PROPERTIES = "screentaker.properties";
    private final Properties properties = new Properties();
    private final DbxClientV2 client;

    public AppConfig() throws IOException {
        final var inputStream = new FileInputStream(SCREEN_TAKER_PROPERTIES);
        properties.load(inputStream);
        final var requestConfig = DbxRequestConfig.newBuilder(getClientIdentifier()).build();
        this.client = new DbxClientV2(requestConfig, properties.getProperty("token"));
        inputStream.close();
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
