package com.nexia.nexus.api.builder;

import com.nexia.nexus.api.NexusAPI;
import com.nexia.nexus.api.NexusServer;
import com.nexia.nexus.api.command.CommandSender;
import com.nexia.nexus.api.command.CommandSourceInfo;
import com.nexia.nexus.api.util.ImplementationUtils;
import com.nexia.nexus.api.world.World;
import com.nexia.nexus.api.world.block.Block;
import com.nexia.nexus.api.world.block.BlockState;
import com.nexia.nexus.api.world.block.BlockType;
import com.nexia.nexus.api.world.damage.DamageData;
import com.nexia.nexus.api.world.effect.StatusEffect;
import com.nexia.nexus.api.world.effect.StatusEffectInstance;
import com.nexia.nexus.api.world.entity.Entity;
import com.nexia.nexus.api.world.entity.EntityType;
import com.nexia.nexus.api.world.entity.player.Player;
import com.nexia.nexus.api.world.item.ItemStack;
import com.nexia.nexus.api.world.item.ItemType;
import com.nexia.nexus.api.world.nbt.NBTList;
import com.nexia.nexus.api.world.nbt.NBTObject;
import com.nexia.nexus.api.world.nbt.NBTValue;
import com.nexia.nexus.api.world.scoreboard.Scoreboard;
import com.nexia.nexus.api.world.util.Location;
import com.mojang.brigadier.CommandDispatcher;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface Builder {
    ImplementationUtils createImplementationUtils();

    Entity createEntity(EntityType type, World world);

    Player createNPCPlayer(World world, UUID uuid, String name);

    NBTObject createNBTObject(@Nullable Map<String, NBTValue> values);
    NBTObject createNBTObjectFromString(String string);

    NBTList createNBTList(List<NBTValue> values);

    NBTValue createNBTValue(short s);
    NBTValue createNBTValue(double d);
    NBTValue createNBTValue(float f);
    NBTValue createNBTValue(byte b);
    NBTValue createNBTValue(int i);
    NBTValue createNBTValue(long l);
    NBTValue createNBTValue(long[] arr);
    NBTValue createNBTValue(byte[] arr);
    NBTValue createNBTValue(int[] arr);
    NBTValue createNBTValue(String string);

    StatusEffectInstance createStatusEffectInstance(StatusEffect statusEffect, int duration,
                                                    int amplifier, boolean ambient);

    DamageData createDamageData(DamageData.Type type, Entity entityCause, boolean isIndirect);

    @Deprecated ItemStack createItemStack(ItemType itemType, int count, int damage, BinaryTagHolder tag);
    ItemStack createItemStack(ItemType itemType, int count, int damage, NBTObject nbt);

    Scoreboard createScoreboard();

    BlockState createBlockState(BlockType type, Location location);
    BlockState blockStateOfBlock(Block block);

    CommandSourceInfo createCommandSourceInfo(CommandSender sender, @Nullable Entity executingEntity, Location location, NexusServer server);

    CommandDispatcher<CommandSourceInfo> getCommandDispatcher();

    static Builder getInstance() {
        return NexusAPI.getInstance().getBuilder();
    }
}
