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
import java.util.logging.Logger;

public final class ScreenTaker extends Thread {
    private static final Logger log = Logger.getLogger(ScreenTaker.class.getName());
    private final AppConfig config;

    ScreenTaker(final AppConfig appConfig) {
        config = appConfig;
    }

    @Override
    public void run() {
        try {
            final var robot = new Robot();
            final var rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            final var requestConfig = DbxRequestConfig.newBuilder(config.getClientIdentifier()).build();
            final var client = new DbxClientV2(requestConfig, config.getToken());

            //noinspection InfiniteLoopStatement
            while (true) {
                final var image = robot.createScreenCapture(rectangle);
                log.fine(() -> "Screenshot taken at " + LocalDateTime.now());
                log.finest(() -> "Screenshot size is " + image.getWidth() + "x" + image.getHeight());

                final var outputStream = new ByteArrayOutputStream();
                ImageIO.write(image, config.getImageType(), outputStream);
                final var inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                final var fileName = config.getFileNameNow();

                new Thread(() -> {
                    try {
                        log.fine(() -> "Uploading image: " + fileName);
                        client.files().uploadBuilder(fileName).uploadAndFinish(inputStream);
                        log.fine(() -> fileName + " was successfully uploaded.");
                    } catch (DbxException | IOException e) {
                        e.printStackTrace();
                    }
                }).start();
                //noinspection BusyWait
                sleep(config.getInterval());
            }
        } catch (InterruptedException | AWTException | IOException e) {
            e.printStackTrace();
        }
    }

}
