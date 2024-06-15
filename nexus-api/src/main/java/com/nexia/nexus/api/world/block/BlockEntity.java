package com.nexia.nexus.api.world.block;

import com.nexia.nexus.api.world.nbt.NBTObject;
import net.kyori.adventure.nbt.api.BinaryTagHolder;

public interface BlockEntity {
    Block getBlock();
    @Deprecated BinaryTagHolder getBlockData();
    @Deprecated void setBlockData(BinaryTagHolder tag);

    NBTObject getBlockNBT();
    void setBlockNBT(NBTObject nbt);
}
