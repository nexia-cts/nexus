package com.nexia.nexus.api.world.effect;

import com.nexia.nexus.api.interfaces.Namespaced;
import com.nexia.nexus.api.interfaces.ObjectMapped;
import com.nexia.nexus.api.interfaces.StringIdentified;

public interface StatusEffect extends ObjectMapped, Namespaced {
    Type getType();

    enum Type {
        BENEFICIAL, HARMFUL, NEUTRAL
    }

    abstract class Other implements StatusEffect, StringIdentified {
    }
}
