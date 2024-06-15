package com.nexia.nexus.builder.extension.world.food;

import net.minecraft.world.entity.player.Player;

public interface FoodDataExtension {
    void setPlayer(Player player);
    Player getPlayer();

    void setExhaustion(float exhaustion);
    float getExhaustion();

    void setSaturationServer(float saturation);
}
