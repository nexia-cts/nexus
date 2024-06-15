package com.nexia.nexus.api.util;

import com.nexia.nexus.api.NexusAPI;
import com.nexia.nexus.api.interfaces.Namespaced;
import com.nexia.nexus.api.world.block.BlockType;
import com.nexia.nexus.api.world.effect.StatusEffect;
import com.nexia.nexus.api.world.item.Enchantment;
import com.nexia.nexus.api.world.item.ItemStack;
import com.nexia.nexus.api.world.item.ItemType;
import com.nexia.nexus.api.world.item.container.menu.ContainerMenuType;
import com.nexia.nexus.api.world.item.container.menu.MenuHolder;
import net.kyori.adventure.text.Component;

public interface ImplementationUtils {
    int getMaxLevel(Enchantment enchantment);
    boolean isCurse(Enchantment enchantment);
    boolean canApply(Enchantment enchantment, ItemStack itemStack);

    StatusEffect.Type getType(StatusEffect effect);

    boolean isBlockItem(ItemType item);
    BlockType getBlock(ItemType item);
    int getMaxStackSize(ItemType item);
    int getMaxDamage(ItemType item);

    Identifier getIdentifier(Namespaced namespaced);

    <T extends Namespaced> T getByIdentifier(Identifier identifier, Class<T> type);

    MenuHolder createMenu(ContainerMenuType type, Component title);

    static ImplementationUtils getInstance() {
        return NexusAPI.getInstance().getImplementationUtils();
    }
}
