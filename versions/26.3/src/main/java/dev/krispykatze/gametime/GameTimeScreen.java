package dev.krispykatze.gametime;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class GameTimeScreen extends Screen {
    private static final int PANEL_WIDTH = 300;
    private static final int PANEL_HEIGHT = 230;
    private int left;
    private int top;

    public GameTimeScreen() {
        super(Component.literal("GameTime"));
    }

    @Override
    protected void init() {
        left = (width - PANEL_WIDTH) / 2;
        top = (height - PANEL_HEIGHT) / 2;

        addRenderableWidget(Button.builder(Component.literal("Sunrise"), b -> GameTimeClient.command("time set 0"))
                .bounds(left + 18, top + 45, 82, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Noon"), b -> GameTimeClient.command("time set 6000"))
                .bounds(left + 109, top + 45, 82, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Sunset"), b -> GameTimeClient.command("time set 12000"))
                .bounds(left + 200, top + 45, 82, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Midnight"), b -> GameTimeClient.command("time set 18000"))
                .bounds(left + 18, top + 71, 82, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Freeze time"), b -> GameTimeClient.command("gamerule doDaylightCycle false"))
                .bounds(left + 109, top + 71, 82, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Resume time"), b -> GameTimeClient.command("gamerule doDaylightCycle true"))
                .bounds(left + 200, top + 71, 82, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Clear"), b -> GameTimeClient.command("weather clear"))
                .bounds(left + 18, top + 112, 82, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Rain"), b -> GameTimeClient.command("weather rain"))
                .bounds(left + 109, top + 112, 82, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Thunder"), b -> GameTimeClient.command("weather thunder"))
                .bounds(left + 200, top + 112, 82, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Reset daylight cycle"), b -> {
            GameTimeClient.command("gamerule doDaylightCycle true");
            GameTimeClient.command("time set day");
            GameTimeClient.feedback("Daylight cycle reset.");
        }).bounds(left + 18, top + 153, 264, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Done"), b -> onClose())
                .bounds(left + 100, top + 194, 100, 20).build());
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractRenderState(graphics, mouseX, mouseY, delta);
        graphics.fill(left, top, left + PANEL_WIDTH, top + 2, 0xFF8FFFE0);
        graphics.text(font, title, width / 2 - font.width(title) / 2, top + 14, 0xFFFFFFFF, false);
        Component subtitle = Component.literal("Time and weather (requires permission)");
        graphics.text(font, subtitle, width / 2 - font.width(subtitle) / 2, top + 31, 0xFFB8C4CC, false);
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().gui.setScreen(null);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
