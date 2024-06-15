package com.nexia.nexus.api.world.item;

import com.nexia.nexus.api.interfaces.Namespaced;
import com.nexia.nexus.api.interfaces.ObjectMapped;
import com.nexia.nexus.api.interfaces.StringIdentified;

public interface Enchantment extends Namespaced, ObjectMapped {
    boolean isCurse();

    int getMaxLevel();

    boolean canBeAppliedTo(ItemStack itemStack);

    abstract class Other implements Enchantment, StringIdentified {
    }
}
