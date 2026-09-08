package dev.chloeilcu.sihx;

import com.mojang.blaze3d.platform.InputConstants;
import dev.chloeilcu.sihx.gui.HitboxConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public final class SmallInteractableHitboxExtenderClient implements ClientModInitializer {
    public static final String MOD_ID = "small_interactable_hitbox_extender";

    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath(MOD_ID, "settings")
    );

    private final KeyMapping openConfig = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "key.small_interactable_hitbox_extender.open_config",
            InputConstants.Type.KEYSYM,
            InputConstants.UNKNOWN.getValue(),
            CATEGORY
    ));

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openConfig.consumeClick()) {
                if (client.screen == null) {
                    client.setScreen(new HitboxConfigScreen(null));
                }
            }
        });
    }
}
