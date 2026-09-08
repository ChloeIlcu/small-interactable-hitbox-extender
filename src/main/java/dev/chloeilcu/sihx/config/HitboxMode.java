package dev.chloeilcu.sihx.config;

public enum HitboxMode {
    VANILLA("Vanilla"),
    FLAT("Flat"),
    FULL_BLOCK("Full Block");

    private final String displayName;

    HitboxMode(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    public HitboxMode next() {
        HitboxMode[] modes = values();
        return modes[(ordinal() + 1) % modes.length];
    }

    public static HitboxMode parse(String value, HitboxMode fallback) {
        if (value == null) return fallback;
        try {
            return valueOf(value.trim().toUpperCase(java.util.Locale.ROOT));
        } catch (IllegalArgumentException ignored) {
            return fallback;
        }
    }
}
