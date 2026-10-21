package dev.krispykatze.gametime;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommands.literal;

public class GameTimeClient implements ClientModInitializer {
    private static KeyMapping openKey;

    @Override
    public void onInitializeClient() {
        KeyMapping.Category category = KeyMapping.Category.register(
                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("gametime", "main"));
        openKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.gametime.open",
                InputConstants.Type.KEYSYM,
                InputConstants.KEY_G,
                category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openKey.consumeClick()) {
                if (client.player != null) client.setScreen(new GameTimeScreen());
            }
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(literal("gametime").executes(context -> {
                    Minecraft client = Minecraft.getInstance();
                    client.execute(() -> {
                        if (client.player != null) client.setScreen(new GameTimeScreen());
                    });
                    return 1;
                }))
        );
    }

    static void command(String command) {
        Minecraft client = Minecraft.getInstance();
        if (client.player != null && client.getConnection() != null) {
            client.getConnection().sendCommand(command);
        }
    }

    static void feedback(String message) {
        Minecraft client = Minecraft.getInstance();
        if (client.player != null) client.player.displayClientMessage(Component.literal(message), true);
    }
}
