package com.nexia.nexus.builder.extension.world.inventory;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public interface MenuTypeExtension {
    AbstractContainerMenu createServer(int i, Inventory inventory);
}
