package intensive;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public final class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    static {
        try (FileInputStream ins = new FileInputStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(ins);
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }
    }

    public static void main(String[] args) throws IOException {

        new ScreenTaker(
                new AppConfig()
                        .load("app.properties")
        ).start();

    }
}
