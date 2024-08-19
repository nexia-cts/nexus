package com.nexia.nexus.builder.mixin.world.inventory;

import com.nexia.nexus.builder.mixin_interfaces.LevelAccessOwner;
import net.minecraft.world.inventory.CartographyTableMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CartographyTableMenu.class)
public abstract class CartographyMenuMixin implements LevelAccessOwner {
    @Mutable
    @Shadow
    @Final
    private ContainerLevelAccess access;

    @Override
    public void nexus$setContainerLevelAccess(ContainerLevelAccess access) {
        this.access = access;
    }

    @Override
    public ContainerLevelAccess nexus$getContainerLevelAccess() {
        return access;
    }
}
