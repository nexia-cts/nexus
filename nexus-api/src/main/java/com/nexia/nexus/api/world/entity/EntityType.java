package com.nexia.nexus.api.world.entity;

import com.nexia.nexus.api.interfaces.Namespaced;
import com.nexia.nexus.api.interfaces.ObjectMapped;
import com.nexia.nexus.api.interfaces.StringIdentified;

public interface EntityType extends ObjectMapped, Namespaced {
    abstract class Other implements EntityType, StringIdentified {
        @Override
        public String toString() {
            return this.getId();
        }
    }
}
