package com.nexia.nexus.builder.mixin.world.inventory;

import com.nexia.nexus.builder.extension.world.inventory.MenuSupplierExtension;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(MenuType.MenuSupplier.class)
public interface MenuSupplierMixin<T extends AbstractContainerMenu> extends MenuSupplierExtension<T> {
    @Unique
    @Environment(EnvType.SERVER)
    T create(int i, Inventory inventory);

    @Override
    default T createServer(int i, Inventory inventory) {
        return create(i, inventory);
    }
}
