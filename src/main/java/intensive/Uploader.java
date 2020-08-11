package intensive;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public final class Uploader extends Thread {
    private static final Logger log = Logger.getLogger(Uploader.class.getName());
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final String IMAGE_TYPE = "png";

    private final DbxClientV2 client;
    private final BufferedImage image;
    private final String fileName;

    public Uploader(final DbxClientV2 client, final BufferedImage image) {
        this.client = client;
        this.image = image;
        this.fileName = "/" + LocalDateTime.now().format(FORMATTER) + "." + IMAGE_TYPE;
        log.fine(() -> "Uploading image: " + fileName);
    }

    @Override
    public void run() {
        try {
            final var outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, IMAGE_TYPE, outputStream);
            final var inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            client.files()
                    .uploadBuilder(fileName)
                    .uploadAndFinish(inputStream);
            log.fine(() -> fileName + " was successfully uploaded.");
        } catch (IOException | DbxException e) {
            e.printStackTrace();
        }
    }
}
