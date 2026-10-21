package dev.krispykatze.gametime;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class GameTimeScreen extends Screen {
    private static final int PANEL_WIDTH = 300;
    private static final int PANEL_HEIGHT = 230;
    private int left;
    private int top;

    public GameTimeScreen() {
        super(Text.literal("GameTime"));
    }

    @Override
    protected void init() {
        left = (width - PANEL_WIDTH) / 2;
        top = (height - PANEL_HEIGHT) / 2;
        clearChildren();

        addDrawableChild(ButtonWidget.builder(Text.literal("Sunrise"), b -> GameTimeClient.command("time set 0"))
                .dimensions(left + 18, top + 45, 82, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Noon"), b -> GameTimeClient.command("time set 6000"))
                .dimensions(left + 109, top + 45, 82, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Sunset"), b -> GameTimeClient.command("time set 12000"))
                .dimensions(left + 200, top + 45, 82, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Midnight"), b -> GameTimeClient.command("time set 18000"))
                .dimensions(left + 18, top + 71, 82, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Freeze time"), b -> GameTimeClient.command("gamerule doDaylightCycle false"))
                .dimensions(left + 109, top + 71, 82, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Resume time"), b -> GameTimeClient.command("gamerule doDaylightCycle true"))
                .dimensions(left + 200, top + 71, 82, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Clear"), b -> GameTimeClient.command("weather clear"))
                .dimensions(left + 18, top + 112, 82, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Rain"), b -> GameTimeClient.command("weather rain"))
                .dimensions(left + 109, top + 112, 82, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Thunder"), b -> GameTimeClient.command("weather thunder"))
                .dimensions(left + 200, top + 112, 82, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Reset daylight cycle"), b -> {
            GameTimeClient.command("gamerule doDaylightCycle true");
            GameTimeClient.command("time set day");
            GameTimeClient.feedback("Daylight cycle reset.");
        }).dimensions(left + 18, top + 153, 264, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Done"), b -> close())
                .dimensions(left + 100, top + 194, 100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        context.fill(left, top, left + PANEL_WIDTH, top + PANEL_HEIGHT, 0xF0181B20);
        context.fill(left, top, left + PANEL_WIDTH, top + 2, 0xFF8FFFE0);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, top + 14, 0xFFFFFFFF);
        context.drawCenteredTextWithShadow(textRenderer,
                Text.literal("Time and weather controls (requires permission)"),
                width / 2, top + 31, 0xFFB8C4CC);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
