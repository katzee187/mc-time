package dev.krispykatze.gametime;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class GameTimeScreen extends Screen {
    private static final int PANEL_WIDTH = 300;
    private static final int PANEL_HEIGHT = 250;
    private int left;
    private int top;
    private Button speedButton;

    public GameTimeScreen() {
        super(Component.literal("GameTime"));
    }

    @Override
    protected void init() {
        left = (width - PANEL_WIDTH) / 2;
        top = (height - PANEL_HEIGHT) / 2;

        addRenderableWidget(Button.builder(Component.literal("Sunrise"), b -> GameTimeClient.setTime(0L))
                .bounds(left + 18, top + 48, 82, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Noon"), b -> GameTimeClient.setTime(6000L))
                .bounds(left + 109, top + 48, 82, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Sunset"), b -> GameTimeClient.setTime(12000L))
                .bounds(left + 200, top + 48, 82, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Midnight"), b -> GameTimeClient.setTime(18000L))
                .bounds(left + 18, top + 74, 82, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Freeze time"), b -> GameTimeClient.setDaylightCycle(false))
                .bounds(left + 109, top + 74, 82, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Resume time"), b -> GameTimeClient.setDaylightCycle(true))
                .bounds(left + 200, top + 74, 82, 20).build());

        speedButton = Button.builder(speedLabel(), b -> {
            GameTimeClient.cycleDayMultiplier();
            b.setMessage(speedLabel());
        }).bounds(left + 18, top + 108, 264, 20).build();
        addRenderableWidget(speedButton);

        addRenderableWidget(Button.builder(Component.literal("Clear"), b -> GameTimeClient.setWeather("clear"))
                .bounds(left + 18, top + 145, 82, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Rain"), b -> GameTimeClient.setWeather("rain"))
                .bounds(left + 109, top + 145, 82, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Thunder"), b -> GameTimeClient.setWeather("thunder"))
                .bounds(left + 200, top + 145, 82, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Reset to normal"), b -> GameTimeClient.reset())
                .bounds(left + 18, top + 181, 264, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Done"), b -> onClose())
                .bounds(left + 100, top + 215, 100, 20).build());
    }

    private Component speedLabel() {
        return Component.literal("Daytime speed: " + GameTimeClient.dayMultiplier() + "x");
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        graphics.fill(left, top, left + PANEL_WIDTH, top + PANEL_HEIGHT, 0xF0181B20);
        graphics.fill(left, top, left + PANEL_WIDTH, top + 2, 0xFF8FFFE0);
        super.extractRenderState(graphics, mouseX, mouseY, delta);
        graphics.text(font, title, width / 2 - font.width(title) / 2, top + 14, 0xFFFFFFFF, false);
        Component subtitle = Component.literal("Singleplayer world controls");
        graphics.text(font, subtitle, width / 2 - font.width(subtitle) / 2, top + 31, 0xFFB8C4CC, false);
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(null);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
