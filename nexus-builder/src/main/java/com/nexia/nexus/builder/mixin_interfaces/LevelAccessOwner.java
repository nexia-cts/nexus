package com.nexia.nexus.builder.mixin_interfaces;

import net.minecraft.world.inventory.ContainerLevelAccess;

public interface LevelAccessOwner {
    void nexus$setContainerLevelAccess(ContainerLevelAccess access);
    ContainerLevelAccess nexus$getContainerLevelAccess();
}
