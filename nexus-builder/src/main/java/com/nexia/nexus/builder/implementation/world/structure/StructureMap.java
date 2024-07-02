package com.nexia.nexus.builder.implementation.world.structure;

import com.nexia.nexus.api.NexusAPI;
import com.nexia.nexus.api.NexusServer;
import com.nexia.nexus.api.util.Identifier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

/**
 * Uses Structure Blocks to paste a map using /setblock.
 * @deprecated
 * @see StructurePlace
 */

@Deprecated
public class StructureMap {

    // Default
    public Identifier id;
    public Rotation rotation;
    public boolean cleanUp;

    // Parameters pre-set (without map)
    public BlockPos placePos;
    public BlockPos pastePos;
    public boolean forceLoad;

    public StructureMap(@NotNull Identifier structureId, @NotNull Rotation rotation, boolean cleanUp) {
        this.id = structureId;
        this.rotation = rotation;
        this.cleanUp = cleanUp;
    }

    public StructureMap(@NotNull Identifier structureId, @NotNull Rotation rotation, boolean cleanUp, @NotNull BlockPos placePos, @NotNull BlockPos pastePos, boolean forceLoad) {
        this.id = structureId;
        this.rotation = rotation;
        this.cleanUp = cleanUp;

        this.placePos = placePos;
        this.pastePos = pastePos;
        this.forceLoad = forceLoad;
    }


    /**
     * Pastes a map.
     * @param level The level where is going to get pasted in.
     * @param placePos Where the structure block is going to get placed in.
     * @param pastePos Where the map is going to get pasted in.
     * @param forceLoad If the chunk should get force loaded.
     */
    public void pasteMap(@NotNull ServerLevel level, @NotNull BlockPos placePos, @NotNull BlockPos pastePos, boolean forceLoad) {
        NexusServer server = NexusAPI.getInstance().getServer();
        String start = "execute in " + level.dimension().location().getNamespace() + ":" + level.dimension().location().getPath();

        if(forceLoad) server.runCommand(start + " run forceload add 0 0", 4, false);
        server.runCommand(this.returnCommand(level, placePos, pastePos), 4, false);

        if(this.cleanUp) {
            server.runCommand(String.format("%s run setblock %s %s %s minecraft:redstone_block", start, placePos.getX() + 1, placePos.getY(), placePos.getZ()), 4, false);

            server.runCommand(String.format("%s if block %s %s %s minecraft:structure_block run setblock %s %s %s air", start, placePos.getX(), placePos.getY(), placePos.getZ(), placePos.getX(), placePos.getY(), placePos.getZ()), 4,false);
            server.runCommand(String.format("%s if block %s %s %s minecraft:redstone_block run setblock %s %s %s air", start, placePos.getX() + 1, placePos.getY(), placePos.getZ(), placePos.getX() + 1, placePos.getY(), placePos.getZ()), 4, false);
        }
    }

    /**
     * Pastes a map with some pre-set parameters.
     * @param level The level where is going to get pasted in.
     */
    public void pasteMap(@NotNull ServerLevel level) {
        if(this.placePos == null || this.pastePos == null) return;
        this.pasteMap(level, this.placePos, this.pastePos, this.forceLoad);
    }

    /**
     * Returns a command which you can use in the console.
     * @param level The level where is going to get pasted in.
     * @param placePos Where the structure block is going to get placed in.
     * @param pastePos Where the map is going to get pasted in.
     * @return The command (/execute in dimension:name run setblock ...).
     */
    public String returnCommand(@NotNull ServerLevel level, @NotNull BlockPos placePos, @NotNull BlockPos pastePos) {
        String start = "execute in " + level.dimension().location().getNamespace() + ":" + level.dimension().location().getPath();

        if(this.rotation != Rotation.NO_ROTATION) {
            return String.format("%s run setblock %s %s %s minecraft:structure_block{mode:'LOAD',name:'%s:%s',posX:%s,posY:%s,posZ:%s,rotation:\"%s\"}", start, placePos.getX(), placePos.getY(), placePos.getZ(), this.id.getNamespace(), this.id.getId(), pastePos.getX(), pastePos.getY(), pastePos.getZ(), this.rotation.id);
        } else {
            return String.format("%s run setblock %s %s %s minecraft:structure_block{mode:'LOAD',name:'%s:%s',posX:%s,posY:%s,posZ:%s}", start, placePos.getX(), placePos.getY(), placePos.getZ(), this.id.getNamespace(), this.id.getId(), pastePos.getX(), pastePos.getY(), pastePos.getZ());
        }
    }

    /**
     * Returns a command which you can use in the console.
     * @param level The level where is going to get pasted in.
     * @return The command (/execute in dimension:name run setblock ...).
     */
    public String returnCommand(@NotNull ServerLevel level) {
        if(this.placePos == null || this.pastePos == null) return null;
        return this.returnCommand(level, this.placePos, this.pastePos);
    }

    public enum Rotation {
        NO_ROTATION("0"),
        CLOCKWISE_90("CLOCKWISE_90"),
        CLOCKWISE_180("CLOCKWISE_180"),
        COUNTERCLOCKWISE_90("COUNTERCLOCKWISE_90");

        public final String id;

        Rotation(String id) {
            this.id = id;
        }
    }
}