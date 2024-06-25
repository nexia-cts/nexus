package com.nexia.nexus.builder.implementation.world.structure;

import com.nexia.nexus.api.event.server.ServerTickEvent;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unused")
public class StructurePlace {

    private final WorldGenLevel world;
    private final ResourceLocation templateName;
    private final BlockPos blockPos;
    private final Mirror mirror;
    private final Rotation rotation;
    private final boolean ignoreEntities;
    private final float integrity;
    private final BlockPos offset;
    private Vec3i size = Vec3i.ZERO;

    /**
     * With this you can create placer object which will spawn
     * a new structure from a nbt file located in /data/modid/structures
     *
     * @param world The world in which to place the structure
     * @param templateName The ResourceLocation of the structure to place, like <code>new ResourceLocation(MOD_ID, structure_name)</code>
     * @param blockPos The position of the structure
     * @param mirror Use this to mirror the structure using <code>Mirror.#</code>
     * @param rotation Use this to rotate the structure using <code>Rotation.#</code>
     * @param ignoreEntities Set to true to block the spawning of entities saved in the structure file
     * @param integrity Set this to a value between 0f and 1f to remove some blocks from the placed structure. (All blocks = 1f)
     * @param offset Use this to offset the placing of the structure.
     * */
    public StructurePlace(WorldGenLevel world, ResourceLocation templateName, BlockPos blockPos, Mirror mirror, Rotation rotation, boolean ignoreEntities, float integrity, BlockPos offset){
        this.world = world;
        this.templateName = templateName;
        this.blockPos = blockPos;
        this.mirror = mirror;
        this.rotation = rotation;
        this.ignoreEntities = ignoreEntities;
        this.integrity = integrity;
        this.offset = offset;
    }

    /**
     * With this you can create placer object which will spawn
     * a new structure from a nbt file located in /data/modid/structures
     *
     * @param world The world in which to place the structure
     * @param templateName The ResourceLocation of the structure to place, like <code>new ResourceLocation(MOD_ID, structure_name)</code>
     * @param blockPos The position of the structure
     * */
    public StructurePlace(WorldGenLevel world, ResourceLocation templateName, BlockPos blockPos) {
        this.world = world;
        this.templateName = templateName;
        this.blockPos = blockPos;
        this.mirror = Mirror.NONE;
        this.rotation = Rotation.NONE;
        this.ignoreEntities = true;
        this.integrity = 1.0f;
        this.offset = new BlockPos(0, 0, 0);
    }

    /**
     * With this you can create placer object which will spawn
     * a new structure from a nbt file located in /data/modid/structures
     *
     * @param world The world in which to place the structure
     * @param templateName The ResourceLocation of the structure to place, like <code>new ResourceLocation(MOD_ID, structure_name)</code>
     * @param blockPos The position of the structure
     * @param offset Use this to offset the placing of the structure.
     * */
    public StructurePlace(WorldGenLevel world, ResourceLocation templateName, BlockPos blockPos, BlockPos offset){
        this.world = world;
        this.templateName = templateName;
        this.blockPos = blockPos;
        this.mirror = Mirror.NONE;
        this.rotation = Rotation.NONE;
        this.ignoreEntities = true;
        this.integrity = 1.0f;
        this.offset = offset;
    }

    /**
     * With this you can create placer object which will spawn
     * a new structure from a nbt file located in /data/modid/structures
     *
     * @param world The world in which to place the structure
     * @param templateName The ResourceLocation of the structure to place, like <code>new ResourceLocation(MOD_ID, structure_name)</code>
     * @param blockPos The position of the structure
     * @param mirror Use this to mirror the structure using <code>Mirror.#</code>
     */
    public StructurePlace(WorldGenLevel world, ResourceLocation templateName, BlockPos blockPos, Mirror mirror){
        this.world = world;
        this.templateName = templateName;
        this.blockPos = blockPos;
        this.mirror = mirror;
        this.rotation = Rotation.NONE;
        this.ignoreEntities = true;
        this.integrity = 1.0f;
        this.offset = new BlockPos(0, 0, 0);
    }

    /**
     * With this you can create placer object which will spawn
     * a new structure from a nbt file located in /data/modid/structures
     *
     * @param world The world in which to place the structure
     * @param templateName The ResourceLocation of the structure to place, like <code>new ResourceLocation(MOD_ID, structure_name)</code>
     * @param blockPos The position of the structure
     * @param rotation Use this to rotate the structure using <code>Rotation.#</code>
     * */
    public StructurePlace(WorldGenLevel world, ResourceLocation templateName, BlockPos blockPos, Rotation rotation){
        this.world = world;
        this.templateName = templateName;
        this.blockPos = blockPos;
        this.mirror = Mirror.NONE;
        this.rotation = rotation;
        this.ignoreEntities = true;
        this.integrity = 1.0f;
        this.offset = new BlockPos(0, 0, 0);
    }

    /**
     * With this you can create placer object which will spawn
     * a new structure from a nbt file located in /data/modid/structures
     *
     * @param world The world in which to place the structure
     * @param templateName The ResourceLocation of the structure to place, like <code>new ResourceLocation(MOD_ID, structure_name)</code>
     * @param blockPos The position of the structure
     * @param mirror Use this to mirror the structure using <code>Mirror.#</code>
     * @param rotation Use this to rotate the structure using <code>Rotation.#</code>
     * */
    public StructurePlace(WorldGenLevel world, ResourceLocation templateName, BlockPos blockPos, Mirror mirror, Rotation rotation){
        this.world = world;
        this.templateName = templateName;
        this.blockPos = blockPos;
        this.mirror = mirror;
        this.rotation = rotation;
        this.ignoreEntities = true;
        this.integrity = 1.0f;
        this.offset = new BlockPos(0, 0, 0);
    }

    /**
     * With this you can create placer object which will spawn
     * a new structure from a nbt file located in /data/modid/structures
     *
     * @param world The world in which to place the structure
     * @param templateName The ResourceLocation of the structure to place, like <code>new ResourceLocation(MOD_ID, structure_name)</code>
     * @param blockPos The position of the structure
     * @param integrity Set this to a value between 0f and 1f to remove some blocks from the placed structure. (All blocks = 1f)
     * */
    public StructurePlace(WorldGenLevel world, ResourceLocation templateName, BlockPos blockPos, float integrity){
        this.world = world;
        this.templateName = templateName;
        this.blockPos = blockPos;
        this.mirror = Mirror.NONE;
        this.rotation = Rotation.NONE;
        this.ignoreEntities = true;
        this.integrity = integrity;
        this.offset = new BlockPos(0, 0, 0);
    }

    /**Use this method to load the structure into the world and
     * place it. You can check to see if the placing was successful.
     * <p>
     * This method DOES NOT support regenerating the old terrain, use <code>loadAndRestore()</code> instead
     * if you want to do it. This method however consumes less resources.
     */
    public boolean loadStructure() {
        if (this.templateName != null && world.getLevel() != null) {
            StructureManager structureManager = world.getLevel().getStructureManager();
            StructureTemplate template;
            try {
                template = structureManager.get(this.templateName);
            } catch (ResourceLocationException e) {
                return false;
            }

            return template != null && this.place(template);
        } else {
            return false;
        }
    }

    /**This method is used by the <code>loadStructure()</code> method,
     * which already checks if the structure exists or not, so use that instead*/
    private boolean place(StructureTemplate template) {
        try {
            StructurePlaceSettings structurePlaceSettings = new StructurePlaceSettings().setMirror(this.mirror).setRotation(this.rotation).setIgnoreEntities(this.ignoreEntities);
            if (this.integrity < 1.0F) {
                structurePlaceSettings.clearProcessors().addProcessor(new BlockRotProcessor(Mth.clamp(this.integrity, 0.0F, 1.0F))).setRandom(this.world.getRandom());
            }
            BlockPos pos = blockPos.offset(this.offset);
            template.placeInWorld(world, pos, pos, structurePlaceSettings, this.world.getRandom(), 2);
            unloadStructure();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**This method unloads the structure after it has been placed.
     * No need to use it on your own, included during placement*/
    private void unloadStructure() {
        if (this.templateName != null && world.getLevel() != null) {
            StructureManager structureManager = world.getLevel().getStructureManager();
            structureManager.remove(this.templateName);
        }
    }


    /**Use this method to load the structure into the world and
     * spawn it. You can check to see if the placing was successful.
     * <p>
     * It will also restore the blocks it replaced after <i>restore_ticks</i>
     * Calling this function from the client only, will not regenerate the old terrain.
     * And will probably cause other issues.
     *<p>
     * <b>Notice:</b> Using this could lead to performance issues, especially with very large structures!
     *
     * @param restore_ticks Number of ticks (1 second = 20 ticks) after which the world would be restored.
     */
    public boolean loadAndRestoreStructure(int restore_ticks) {
        if (this.templateName != null && world.getLevel() != null) {
            StructureManager structureManager = world.getLevel().getStructureManager();
            StructureTemplate template;
            try {
                template = structureManager.get(this.templateName);
            } catch (ResourceLocationException var6) {
                return false;
            }

            if(template != null) {
                this.size = template.getSize();
            }

            if(!this.world.isClientSide()){
                scheduleReplacement(restore_ticks, saveFromWorld(this.world, this.blockPos.offset(this.offset), size));
            }
            return template != null && this.place(template);
        } else {
            return false;
        }
    }

    /**Use this method to load the structure into the world and
     * spawn it. You can check to see if the placing was successful.
     *<p>
     * It will also restore the blocks it replaced after <i>restore_ticks</i> with an animation
     *<p>
     * Calling this function from the client only will not regenerate the old terrain.
     * And will probably cause other issues.
     *<p>
     * <b>Notice:</b> Using this could lead to performance issues, especially with very large structures!
     *
     * @param restore_ticks Number of ticks (1 second = 20 ticks) after which the blocks will begin to be restored
     * @param blocks_per_tick How many blocks to restore per tick, the more, the faster the animation will go.
     * @param random Weather or not the blocks will be removed at random. If false, they will be removed from one corner to the other in sequence
     */
    public boolean loadAndRestoreStructureAnimated(int restore_ticks, int blocks_per_tick, boolean random) {
        if (this.templateName != null && world.getLevel() != null) {
            StructureManager structureManager = world.getLevel().getStructureManager();
            StructureTemplate template;
            try {
                template = structureManager.get(this.templateName);
            } catch (ResourceLocationException var6) {
                return false;
            }

            if(template != null) {
                this.size = template.getSize();
            }

            if(!this.world.isClientSide()){
                scheduleReplacementAnimated(restore_ticks, blocks_per_tick, random, saveFromWorld(this.world, this.blockPos.offset(this.offset), size));
            }
            return template != null && this.place(template);
        } else {
            return false;
        }
    }



    /**Schedules the replacement of the older terrain/world and
     * after <i>ticks</i> it executes the replacement.
     *<p>
     * TO CALL SERVER SIDE ONLY*/
    private void scheduleReplacement(int ticks, List<StructureTemplate.StructureBlockInfo> blockInfoList){
        AtomicInteger counter = new AtomicInteger();
        counter.set(0);

        ServerTickEvent.BACKEND.register(server -> {

            if(counter.get() == -1){
                return;
            }
            if(counter.get() == ticks){

                for(StructureTemplate.StructureBlockInfo info : blockInfoList){
                    world.setBlock(info.pos, info.state, 3); // Block.UPDATE_ALL in newer versions

                    if (info.nbt != null) {
                        //The block entities check needs to be done on the main thread
                        server.runAfterwards(() -> {
                            BlockEntity blockEntity = world.getBlockEntity(info.pos);
                            if (blockEntity != null) {
                                if (blockEntity instanceof RandomizableContainerBlockEntity) {
                                    info.nbt.putLong("LootTableSeed", Objects.requireNonNull(blockEntity.getLevel()).getRandom().nextLong());
                                }

                                blockEntity.load(blockEntity.getBlockState(), info.nbt);
                            }
                        });
                    }
                }
                counter.set(-1);

            }else{
                counter.getAndIncrement();
            }

        });

    }


    /**Schedules the replacement of the older terrain/world and
     * after <i>ticks</i> it executes the replacement.
     * It also does this with a basic animation
     *<p>
     * TO CALL SERVER SIDE ONLY
     *
     * @param ticks Number of ticks after which the blocks will begin to be restored
     * @param blocks_per_tick How many blocks to restore per tick, the more, the faster the animation will go
     * @param rand Weather or not the blocks will be removed at random. If false, they will be removed from one corner to the other in sequence
     * */
    private void scheduleReplacementAnimated(int ticks, int blocks_per_tick, boolean rand, List<StructureTemplate.StructureBlockInfo> blockInfoList){

        List<StructureTemplate.StructureBlockInfo> infoList = new ArrayList<>();
        AtomicInteger count = new AtomicInteger();

        ServerTickEvent.BACKEND.register(server -> {

            if(count.get() == -1){
                return;
            }

            if(count.get() >= ticks){
                //If the length of the list is the same as the blocks placed it means it already placed the last one

                if(count.get() == ticks){
                    infoList.addAll(blockInfoList);
                }
                if(infoList.isEmpty()){
                    count.set(-1);
                    return;
                }

                for(int j = 0; j<blocks_per_tick; j++){
                    if(infoList.isEmpty()){
                        count.set(-1);
                        return;
                    }
                    StructureTemplate.StructureBlockInfo info;
                    if(rand){
                        int i;
                        if(infoList.size()-1 == 0){
                            i = 0;
                        } else{
                            i = this.world.getRandom().nextInt(infoList.size()-1);
                        }
                        info = infoList.get(i);
                        infoList.remove(i);
                    }else{
                        info = infoList.get(count.get() - ticks);
                    }

                    world.setBlock(info.pos, info.state, 3); // Block.UPDATE_ALL in newer versions

                    if (info.nbt != null) {
                        //The block entities check needs to be done on the main thread
                        server.runAfterwards(() -> {
                            BlockEntity blockEntity = world.getBlockEntity(info.pos);
                            if (blockEntity != null) {
                                if (blockEntity instanceof RandomizableContainerBlockEntity) {
                                    info.nbt.putLong("LootTableSeed", Objects.requireNonNull(blockEntity.getLevel()).getRandom().nextLong());
                                }
                                blockEntity.load(blockEntity.getBlockState(), info.nbt);
                            }
                        });
                    }
                }


            }
            count.getAndIncrement();
        });
        infoList.clear();
    }


    /**Saves the block infos to <i>blockInfoLists</i>
     *<p>
     * Will also debug log the time it took to save the structure.
     */
    private List<StructureTemplate.StructureBlockInfo> saveFromWorld(WorldGenLevel world, BlockPos start, Vec3i dimensions) {
        List<StructureTemplate.StructureBlockInfo> blockInfoList = new ArrayList<>();
        Instant start_time = Instant.now();
        BlockPos blockPos = start.offset(dimensions).offset(-1, -1, -1);
        BlockPos min_pos = new BlockPos(Math.min(start.getX(), blockPos.getX()), Math.min(start.getY(), blockPos.getY()), Math.min(start.getZ(), blockPos.getZ()));
        BlockPos max_pos = new BlockPos(Math.max(start.getX(), blockPos.getX()), Math.max(start.getY(), blockPos.getY()), Math.max(start.getZ(), blockPos.getZ()));

        //Iterates through all the block positions and adds them to list
        BlockPos.betweenClosed(min_pos, max_pos).iterator().forEachRemaining(pos -> {
            //Copying the pos, so it doesn't mutate and make a mess
            BlockPos save_pos;
            save_pos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());

            BlockEntity blockEntity = world.getBlockEntity(save_pos);
            StructureTemplate.StructureBlockInfo structureBlockInfo;

            structureBlockInfo = new StructureTemplate.StructureBlockInfo(save_pos, world.getBlockState(save_pos), null);

            //This means the block has an inventory, that it has already dropped, so don't copy its nbt

            /*
            if (blockEntity != null) {
                boolean has_inventory = Inventory.class.isAssignableFrom(blockEntity.getClass());
                if (has_inventory) {
                    structureBlockInfo = new StructureTemplate.StructureBlockInfo(save_pos, world.getBlockState(save_pos), null);
                } else {
                    structureBlockInfo = new StructureTemplate.StructureBlockInfo(save_pos, world.getBlockState(save_pos), blockEntity.save(world.getRegistryManager()));
                }
            } else {
                structureBlockInfo = new StructureTemplate.StructureBlockInfo(save_pos, world.getBlockState(save_pos), null);
            }
             */
            blockInfoList.add(structureBlockInfo);
        });

        Instant end_time = Instant.now();
        Duration timeElapsed = Duration.between(start_time, end_time);

        return blockInfoList;
    }
}