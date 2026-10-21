package dev.krispykatze.gametime;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommands.literal;

public class GameTimeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
            dispatcher.register(literal("gametime").executes(context -> {
                Minecraft client = Minecraft.getInstance();
                client.execute(() -> client.gui.setScreen(new GameTimeScreen()));
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
        if (client.player != null) client.player.sendSystemMessage(Component.literal(message));
    }
}
