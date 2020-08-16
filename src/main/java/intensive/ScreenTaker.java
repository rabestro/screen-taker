package intensive;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public final class ScreenTaker extends Thread {
    private static final Logger log = Logger.getLogger(ScreenTaker.class.getName());
    private final AppConfig config;
    private final Robot robot;
    private final ExecutorService uploader;
    private final Rectangle rectangle;
    private final DbxClientV2 client;

    ScreenTaker(final Robot robot, final AppConfig appConfig) {
        config = appConfig;
        this.robot = robot;

        this.rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        final var requestConfig = DbxRequestConfig.newBuilder(config.getClientIdentifier()).build();
        this.client = new DbxClientV2(requestConfig, config.getToken());

        final var poolSize = Runtime.getRuntime().availableProcessors();
        this.uploader = Executors.newFixedThreadPool(poolSize);
    }

    @Override
    public void run() {
        Executors
                .newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(this::takeScreenshot, 1, config.getInterval(), TimeUnit.MILLISECONDS);
    }

    private void takeScreenshot() {
        final var image = robot.createScreenCapture(rectangle);
        log.fine(() -> "Screenshot taken at " + LocalDateTime.now());
        log.finest(() -> "Screenshot size is " + image.getWidth() + "x" + image.getHeight());

        uploader.submit(() -> {
            try {
                final var outputStream = new ByteArrayOutputStream();
                ImageIO.write(image, config.getImageType(), outputStream);
                final var inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                final var fileName = config.getFileNameNow();
                log.fine(() -> "Uploading image: " + fileName);
                client.files().uploadBuilder(fileName).uploadAndFinish(inputStream);
                log.fine(() -> fileName + " was successfully uploaded.");
            } catch (DbxException | IOException e) {
                e.printStackTrace();
            }
        });
    }
}

