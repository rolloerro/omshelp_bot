package com.rolloerro.minecraftprotobufdemo;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.network.PacketByteBuf;

import java.nio.charset.StandardCharsets;

public class ServerNetworking {

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(NetworkingConstants.SEND_MESSAGE_PACKET, (server, player, handler, buf, responseSender) -> {
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            String message = new String(bytes, StandardCharsets.UTF_8);

            System.out.println("[Server] Message from " + player.getUuidAsString() + ": " + message);

            server.execute(() -> {
                player.sendMessage(Text.literal("Server received: " + message));
            });
        });
    }
}
