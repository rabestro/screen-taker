package intensive;

import com.dropbox.core.DbxException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class ImageUploader implements Runnable {
    private static final Logger log = Logger.getLogger(ScreenTaker.class.getName());

    private final AppConfig config;
    private final BufferedImage image;
    private final String fileName;

    public ImageUploader(AppConfig config, BufferedImage image) {
        this.config = config;
        this.image = image;
        this.fileName = config.getFileNameNow();
    }

    @Override
    public void run() {
        try {
            log.fine(() -> "Uploading image: " + fileName);
            config.getClient()
                    .files()
                    .uploadBuilder(fileName)
                    .uploadAndFinish(imageToInputStream(image));
            log.fine(() -> fileName + " was successfully uploaded.");
        } catch (DbxException | IOException e) {
            e.printStackTrace();
        }
    }

    private InputStream imageToInputStream(BufferedImage image) throws IOException {
        final var outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, config.getImageType(), outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

}
