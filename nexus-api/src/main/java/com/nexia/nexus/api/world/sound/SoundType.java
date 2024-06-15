package com.nexia.nexus.api.world.sound;

import com.nexia.nexus.api.interfaces.Namespaced;
import com.nexia.nexus.api.interfaces.ObjectMapped;
import com.nexia.nexus.api.interfaces.StringIdentified;

public interface SoundType extends ObjectMapped, Namespaced {
    abstract class Other implements com.nexia.nexus.api.world.sound.SoundType, StringIdentified {
        @Override
        public String toString() {
            return this.getId();
        }
    }
}
