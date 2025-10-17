package com.rolloerro.minecraftprotobufdemo;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MinecraftProtobufDemo implements ModInitializer, ClientModInitializer {

    public static final String MOD_ID = "protobufdemo";

    @Override
    public void onInitialize() {
        System.out.println("[MinecraftProtobufDemo] Server init...");
        ServerNetworking.register();
    }

    @Override
    public void onInitializeClient() {
        System.out.println("[MinecraftProtobufDemo] Client init...");
        // Открываем экран по команде для теста
        MinecraftClient client = MinecraftClient.getInstance();
        client.execute(() -> client.setScreen(new MessageScreen(Text.literal("Message Sender"))));
    }
}
