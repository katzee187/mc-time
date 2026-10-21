package dev.krispykatze.gametime;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraft.network.chat.Component;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommands.literal;

public class GameTimeClient implements ClientModInitializer {
    private static int dayMultiplier = 1;

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
            dispatcher.register(literal("gametime").executes(context -> {
                Minecraft client = Minecraft.getInstance();
                client.execute(() -> client.setScreen(new GameTimeScreen()));
                return 1;
            }))
        );

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (dayMultiplier <= 1) return;
            Minecraft client = Minecraft.getInstance();
            if (client.getSingleplayerServer() != server) return;
            ServerLevel level = server.overworld();
            long time = level.getDayTime();
            long timeOfDay = Math.floorMod(time, 24000L);
            // Add extra ticks only during daytime; night keeps its normal length.
            if (timeOfDay < 12000L) {
                level.setDayTime(time + (dayMultiplier - 1));
            }
        });
    }

    static boolean withSingleplayerWorld(java.util.function.Consumer<ServerLevel> action) {
        Minecraft client = Minecraft.getInstance();
        MinecraftServer server = client.getSingleplayerServer();
        if (server == null) {
            feedback("GameTime world controls require a singleplayer world.");
            return false;
        }
        server.execute(() -> action.accept(server.overworld()));
        return true;
    }

    static void setTime(long time) {
        withSingleplayerWorld(level -> level.setDayTime(time));
    }

    static void setDaylightCycle(boolean enabled) {
        withSingleplayerWorld(level ->
            level.getGameRules().get(GameRules.RULE_DAYLIGHT).set(enabled, level.getServer())
        );
    }

    static void setWeather(String weather) {
        withSingleplayerWorld(level -> {
            switch (weather) {
                case "clear" -> level.setWeatherParameters(6000, 0, false, false);
                case "rain" -> level.setWeatherParameters(0, 6000, true, false);
                case "thunder" -> level.setWeatherParameters(0, 6000, true, true);
                default -> { }
            }
        });
    }

    static int dayMultiplier() {
        return dayMultiplier;
    }

    static void cycleDayMultiplier() {
        dayMultiplier = dayMultiplier >= 3 ? 1 : dayMultiplier + 1;
    }

    static void reset() {
        dayMultiplier = 1;
        withSingleplayerWorld(level -> {
            level.getGameRules().get(GameRules.RULE_DAYLIGHT).set(true, level.getServer());
            level.setDayTime(0L);
        });
    }

    static void feedback(String message) {
        Minecraft client = Minecraft.getInstance();
        if (client.player != null) client.player.displayClientMessage(Component.literal(message), false);
    }
}
