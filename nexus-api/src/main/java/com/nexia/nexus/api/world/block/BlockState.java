package com.nexia.nexus.api.world.block;

import com.nexia.nexus.api.builder.Builder;
import com.nexia.nexus.api.world.util.Location;

public interface BlockState extends Block {
    static BlockState create(BlockType type, Location location) {
        return Builder.getInstance().createBlockState(type, location);
    }

    static BlockState of(Block block) {
        return Builder.getInstance().blockStateOfBlock(block);
    }
}
