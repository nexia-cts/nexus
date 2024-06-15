package com.nexia.nexus.api.world.item;

import com.nexia.nexus.api.interfaces.Namespaced;
import com.nexia.nexus.api.interfaces.ObjectMapped;
import com.nexia.nexus.api.interfaces.StringIdentified;
import com.nexia.nexus.api.world.block.BlockType;

public interface ItemType extends ObjectMapped, Namespaced {
    boolean isBlockItem();
    BlockType getBlock();
    int getMaxStackSize();
    int getMaxDamage();

    abstract class Other implements ItemType, StringIdentified {}
}
