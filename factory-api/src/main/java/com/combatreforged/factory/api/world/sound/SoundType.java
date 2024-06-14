package com.combatreforged.factory.api.world.sound;

import com.combatreforged.factory.api.interfaces.Namespaced;
import com.combatreforged.factory.api.interfaces.ObjectMapped;
import com.combatreforged.factory.api.interfaces.StringIdentified;

public interface SoundType extends ObjectMapped, Namespaced {
    abstract class Other implements com.combatreforged.factory.api.world.sound.SoundType, StringIdentified {
        @Override
        public String toString() {
            return this.getId();
        }
    }
}
