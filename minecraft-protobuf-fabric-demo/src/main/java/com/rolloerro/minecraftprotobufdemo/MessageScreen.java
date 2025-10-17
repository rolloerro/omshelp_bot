package com.rolloerro.minecraftprotobufdemo;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import java.nio.charset.StandardCharsets;

public class MessageScreen extends Screen {

    private TextFieldWidget textField;
    private ButtonWidget sendButton;

    protected MessageScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        textField = new TextFieldWidget(this.textRenderer, centerX - 100, centerY - 20, 200, 20, Text.literal("Enter message"));
        this.addDrawableChild(textField);

        sendButton = ButtonWidget.builder(Text.literal("Send"), button -> {
            String message = textField.getText();
            if (!message.isEmpty()) {
                byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
                ClientPlayNetworking.send(NetworkingConstants.SEND_MESSAGE_PACKET, new net.minecraft.network.PacketByteBuf(io.netty.buffer.Unpooled.wrappedBuffer(bytes)));
                MinecraftClient.getInstance().player.sendMessage(Text.literal("Sent: " + message));
            }
        }).dimensions(centerX - 50, centerY + 20, 100, 20).build();

        this.addDrawableChild(sendButton);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 40, 0xFFFFFF);
        textField.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
