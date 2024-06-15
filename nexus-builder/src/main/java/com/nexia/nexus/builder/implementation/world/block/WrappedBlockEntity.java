package com.nexia.nexus.builder.implementation.world.block;

import com.nexia.nexus.api.world.block.Block;
import com.nexia.nexus.api.world.nbt.NBTObject;
import com.nexia.nexus.api.world.util.Location;
import com.nexia.nexus.builder.implementation.Wrapped;
import com.nexia.nexus.builder.implementation.world.WrappedWorld;
import com.nexia.nexus.builder.implementation.world.nbt.WrappedNBTObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.TagParser;
import net.minecraft.world.level.block.entity.BlockEntity;

public class WrappedBlockEntity extends Wrapped<BlockEntity> implements com.nexia.nexus.api.world.block.BlockEntity {
    public WrappedBlockEntity(BlockEntity wrapped) {
        super(wrapped);
    }

    @Override
    public Block getBlock() {
        BlockPos pos = wrapped.getBlockPos();
        return new WrappedBlock
                (new Location(pos.getX(), pos.getY(), pos.getZ(), Wrapped.wrap(wrapped.getLevel(), WrappedWorld.class)));
    }

    @Deprecated
    @Override
    public BinaryTagHolder getBlockData() {
        return BinaryTagHolder.of(wrapped.getUpdateTag().getAsString());
    }

    @Deprecated
    @Override
    public void setBlockData(BinaryTagHolder tag) {
        try {
            wrapped.load(wrapped.getBlockState(), TagParser.parseTag(tag.toString()));
        } catch (CommandSyntaxException e) {
            throw new UnsupportedOperationException("Tag is invalid");
        }
    }

    @Override
    public NBTObject getBlockNBT() {
        return Wrapped.wrap(wrapped.getUpdateTag(), WrappedNBTObject.class);
    }

    @Override
    public void setBlockNBT(NBTObject nbt) {
        wrapped.load(wrapped.getBlockState(), ((WrappedNBTObject) nbt).unwrap());
    }
}
