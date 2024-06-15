package com.nexia.nexus.api.world.block;

import com.nexia.nexus.api.interfaces.Namespaced;
import com.nexia.nexus.api.interfaces.ObjectMapped;
import com.nexia.nexus.api.interfaces.StringIdentified;
import com.google.errorprone.annotations.Immutable;

@Immutable
public interface BlockType extends ObjectMapped, Namespaced {
    @Immutable
    abstract class Other implements BlockType, StringIdentified {
    }
}
