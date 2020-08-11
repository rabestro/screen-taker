package intensive;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public final class ScreenTaker extends Thread {
    private static final Logger log = Logger.getLogger(ScreenTaker.class.getName());
    private final Rectangle rectangle;
    private final DbxClientV2 client;

    ScreenTaker(final AppConfig appConfig) {
        final var requestConfig = DbxRequestConfig
                .newBuilder(appConfig.getClientIdentifier())
                .build();

        client = new DbxClientV2(requestConfig, appConfig.getToken());
        rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    }

    @Override
    public void run() {
        while (true) {
            try {
                final var image = new Robot().createScreenCapture(rectangle);
                log.fine(()->"Screenshot taken at " + LocalDateTime.now());
                log.finest(() -> image.getWidth() + "x" + image.getHeight());
                new Uploader(client, image).start();

                //noinspection BusyWait
                sleep(5000);
            } catch (InterruptedException | AWTException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
