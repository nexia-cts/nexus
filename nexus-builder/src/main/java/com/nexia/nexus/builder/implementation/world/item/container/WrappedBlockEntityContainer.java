package com.nexia.nexus.builder.implementation.world.item.container;

import com.nexia.nexus.api.world.block.container.BlockEntityContainer;
import com.nexia.nexus.api.world.entity.player.Player;
import com.nexia.nexus.api.world.item.container.menu.ContainerMenu;
import com.nexia.nexus.builder.implementation.Wrapped;
import com.nexia.nexus.builder.implementation.util.ObjectMappings;
import com.nexia.nexus.builder.implementation.world.block.WrappedBlockEntity;
import com.nexia.nexus.builder.implementation.world.item.container.menu.WrappedContainerMenu;
import com.google.common.collect.ImmutableList;
import net.kyori.adventure.text.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;

import java.util.List;

public class WrappedBlockEntityContainer extends WrappedBlockEntity implements BlockEntityContainer, WrappedContainer {
    private final BaseContainerBlockEntity wrappedBEC;
    public WrappedBlockEntityContainer(BaseContainerBlockEntity wrappedBEC) {
        super(wrappedBEC);
        this.wrappedBEC = wrappedBEC;
    }

    @Override
    public Component getName() {
        return ObjectMappings.convertComponent(wrappedBEC.getName());
    }

    @Override
    public void setName(Component component) {
        wrappedBEC.setCustomName(ObjectMappings.convertComponent(component));
    }

    @Override
    public ContainerMenu openToPlayer(Player player) {
        ServerPlayer mcPlayer = (ServerPlayer) player;
        mcPlayer.openMenu(wrappedBEC);
        return Wrapped.wrap(mcPlayer.containerMenu, WrappedContainerMenu.class);
    }

    @Override
    public List<Integer> getAvailableSlots() {
        ImmutableList.Builder<Integer> slots = ImmutableList.builder();
        for (int i = 0; i < wrappedBEC.getContainerSize(); i++) {
            slots.add(i);
        }

        return slots.build();
    }

    @Override
    public Container container() {
        return wrappedBEC;
    }

    @Override
    public BaseContainerBlockEntity unwrap() {
        return wrappedBEC;
    }
}
