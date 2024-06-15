package com.nexia.nexus.builder.implementation.world.item.container.menu;

import com.nexia.nexus.api.world.item.container.menu.MenuHolder;
import com.nexia.nexus.builder.implementation.Wrapped;
import net.minecraft.world.SimpleMenuProvider;

public class WrappedMenuHolder extends Wrapped<SimpleMenuProvider> implements MenuHolder {
    public WrappedMenuHolder(SimpleMenuProvider wrapped) {
        super(wrapped);
    }
}
