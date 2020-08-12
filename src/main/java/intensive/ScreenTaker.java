package intensive;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public final class ScreenTaker extends Thread {
    private static final Logger log = Logger.getLogger(ScreenTaker.class.getName());
    private final DbxClientV2 client;
    private final long interval;

    ScreenTaker(final AppConfig appConfig) {
        interval = appConfig.getInterval();
        final var requestConfig = DbxRequestConfig
                .newBuilder(appConfig.getClientIdentifier())
                .build();
        client = new DbxClientV2(requestConfig, appConfig.getToken());
    }

    @Override
    public void run() {
        try {
            final var robot = new Robot();
            final var rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

            //noinspection InfiniteLoopStatement
            while (true) {
                final var image = robot.createScreenCapture(rectangle);
                log.fine(() -> "Screenshot taken at " + LocalDateTime.now());
                log.finest(() -> "Screenshot size is " + image.getWidth() + "x" + image.getHeight());

                new Uploader(client, image).start();

                //noinspection BusyWait
                sleep(interval);
            }
        } catch (InterruptedException | AWTException e) {
            e.printStackTrace();
        }
    }

}
