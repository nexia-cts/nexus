package com.nexia.nexus.api.world.block.container;

import com.nexia.nexus.api.world.entity.player.Player;
import com.nexia.nexus.api.world.item.container.Container;
import com.nexia.nexus.api.world.item.container.menu.ContainerMenu;
import net.kyori.adventure.text.Component;

public interface BlockEntityContainer extends Container {
    Component getName();
    void setName(Component component);

    ContainerMenu openToPlayer(Player player);
}
