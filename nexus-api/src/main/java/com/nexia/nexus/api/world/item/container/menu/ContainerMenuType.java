package com.nexia.nexus.api.world.item.container.menu;

import com.nexia.nexus.api.interfaces.ObjectMapped;
import net.kyori.adventure.text.Component;

public interface ContainerMenuType extends ObjectMapped {
    MenuHolder createMenu(Component title);
}
