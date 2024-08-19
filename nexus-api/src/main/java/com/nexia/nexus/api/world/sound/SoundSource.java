package com.nexia.nexus.api.world.sound;

import com.nexia.nexus.api.interfaces.Namespaced;
import com.nexia.nexus.api.interfaces.ObjectMapped;
import com.nexia.nexus.api.interfaces.StringIdentified;

public interface SoundSource extends ObjectMapped, Namespaced {
    abstract class Other implements com.nexia.nexus.api.world.sound.SoundSource, StringIdentified {
        @Override
        public String toString() {
            return this.getId();
        }
    }
}
