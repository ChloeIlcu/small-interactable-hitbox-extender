package dev.chloeilcu.sihx.gui;

import dev.chloeilcu.sihx.config.HitboxConfig;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class HitboxConfigScreen extends Screen {
    private final Screen parent;

    public HitboxConfigScreen(Screen parent) {
        super(Component.nullToEmpty("Button & Lever Hitboxes"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = this.height / 2 - 45;

        this.addRenderableWidget(Button.builder(
                Component.nullToEmpty("Button hitbox: " + HitboxConfig.buttonMode().displayName()),
                button -> {
                    HitboxConfig.setButtonMode(HitboxConfig.buttonMode().next());
                    this.rebuildWidgets();
                }
        ).bounds(centerX - 100, startY, 200, 20).build());

        this.addRenderableWidget(Button.builder(
                Component.nullToEmpty("Lever hitbox: " + HitboxConfig.leverMode().displayName()),
                button -> {
                    HitboxConfig.setLeverMode(HitboxConfig.leverMode().next());
                    this.rebuildWidgets();
                }
        ).bounds(centerX - 100, startY + 24, 200, 20).build());

        this.addRenderableWidget(Button.builder(
                Component.nullToEmpty("Reset defaults"),
                button -> {
                    HitboxConfig.reset();
                    this.rebuildWidgets();
                }
        ).bounds(centerX - 100, startY + 56, 98, 20).build());

        this.addRenderableWidget(Button.builder(
                Component.nullToEmpty("Done"),
                button -> this.onClose()
        ).bounds(centerX + 2, startY + 56, 98, 20).build());
    }

    @Override
    public void onClose() {
        HitboxConfig.save();
        this.minecraft.setScreen(this.parent);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
