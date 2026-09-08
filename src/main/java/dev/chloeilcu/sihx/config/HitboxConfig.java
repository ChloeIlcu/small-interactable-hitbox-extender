package dev.chloeilcu.sihx.config;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class HitboxConfig {
    private static final HitboxMode DEFAULT_BUTTON_MODE = HitboxMode.VANILLA;
    private static final HitboxMode DEFAULT_LEVER_MODE = HitboxMode.VANILLA;
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("small-interactable-hitbox-extender.properties");

    private static HitboxMode buttonMode = DEFAULT_BUTTON_MODE;
    private static HitboxMode leverMode = DEFAULT_LEVER_MODE;

    static {
        load();
    }

    private HitboxConfig() {}

    public static HitboxMode buttonMode() {
        return buttonMode;
    }

    public static HitboxMode leverMode() {
        return leverMode;
    }

    public static void setButtonMode(HitboxMode mode) {
        buttonMode = mode == null ? DEFAULT_BUTTON_MODE : mode;
        save();
    }

    public static void setLeverMode(HitboxMode mode) {
        leverMode = mode == null ? DEFAULT_LEVER_MODE : mode;
        save();
    }

    public static void reset() {
        buttonMode = DEFAULT_BUTTON_MODE;
        leverMode = DEFAULT_LEVER_MODE;
        save();
    }

    public static void load() {
        Properties props = new Properties();
        if (Files.isRegularFile(CONFIG_PATH)) {
            try (InputStream in = Files.newInputStream(CONFIG_PATH)) {
                props.load(in);
            } catch (IOException ignored) {
                // Keep defaults if the config cannot be read.
            }
        }
        buttonMode = HitboxMode.parse(props.getProperty("button_mode"), DEFAULT_BUTTON_MODE);
        leverMode = HitboxMode.parse(props.getProperty("lever_mode"), DEFAULT_LEVER_MODE);
    }

    public static void save() {
        Properties props = new Properties();
        props.setProperty("button_mode", buttonMode.name());
        props.setProperty("lever_mode", leverMode.name());
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (OutputStream out = Files.newOutputStream(CONFIG_PATH)) {
                props.store(out, "Small Interactable Hitbox Extender");
            }
        } catch (IOException ignored) {
            // The setting still applies for the current session.
        }
    }
}
