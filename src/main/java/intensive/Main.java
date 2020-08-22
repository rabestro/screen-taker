package intensive;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;

public final class Main {
    static private final String LOGGING_PROPERTIES = "logging.properties";

    static {
        if (!getExternalProperties() && !getInternalProperties()) {
            System.err.println("Default logger configuration is loaded.");
        }
    }

    private static boolean getExternalProperties() {
        try (final var ins = new FileInputStream(LOGGING_PROPERTIES)) {
            LogManager.getLogManager().readConfiguration(ins);
            System.out.println("External logger configuration is loaded successful.");
            return true;
        } catch (IOException e) {
            System.err.println("Could not load external logger configuration: " + e.toString());
            return false;
        }
    }

    private static boolean getInternalProperties() {
        try (final var ins = Main.class.getClassLoader().getResourceAsStream(LOGGING_PROPERTIES)) {
            LogManager.getLogManager().readConfiguration(ins);
            System.out.println("Internal logger configuration is loaded successful.");
            return true;
        } catch (IOException e) {
            System.err.println("Could not load internal logger configuration: " + e.toString());
            return false;
        }
    }

    public static void main(String[] args) throws IOException, AWTException {

        new ScreenTaker(
                new Robot(),
                new AppConfig("app.properties")
        ).start();

    }
}
