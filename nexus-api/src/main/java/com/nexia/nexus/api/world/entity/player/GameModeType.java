package com.nexia.nexus.api.world.entity.player;

import com.nexia.nexus.api.interfaces.ObjectMapped;
import com.nexia.nexus.api.interfaces.StringIdentified;

public interface GameModeType extends ObjectMapped {
    abstract class Other implements GameModeType, StringIdentified {
        @Override
        public String toString() {
            return this.getId();
        }
    }
}
