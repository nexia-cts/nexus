package com.nexia.nexus.builder.command;

import com.nexia.nexus.api.command.CommandSourceInfo;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.List;
import java.util.stream.Collectors;

import static com.nexia.nexus.api.command.CommandUtils.literal;

public final class NexusCommand {
    private NexusCommand() {}

    public static void register(CommandDispatcher<CommandSourceInfo> dispatcher) {
        dispatcher.register(
                literal("nexus")
                        .requires(info -> info.getSender().getPermissionLevel() >= 4)
                        .then(literal("version")
                                .executes(context -> {
                                    FabricLoader loader = FabricLoader.getInstance();
                                    assert loader.getModContainer("nexus-api").isPresent() && loader.getModContainer("nexus-builder").isPresent();
                                    String apiVersion = loader.getModContainer("nexus-api").get().getMetadata().getVersion().toString();
                                    String builderVersion = loader.getModContainer("nexus-builder").get().getMetadata().getVersion().toString();
                                    context.getSource().sendMessage(Component.text("Nexus version: \n ")
                                            .append(Component.text("- API: [nexus-api@" + apiVersion + "]\n - Implementation: [nexus-builder@" + builderVersion + "]")
                                                    .color(NamedTextColor.DARK_GREEN)));
                                    return 0;
                                }))
                        .then(literal("plugins")
                                .executes(context -> {
                                    List<ModMetadata> plugins = FabricLoader.getInstance().getAllMods()
                                            .stream()
                                            .map(ModContainer::getMetadata)
                                            .filter(metadata -> metadata.getDependencies().stream()
                                                    .anyMatch(dep -> dep.getModId().equals("nexus-api")))
                                            .toList();
                                    Component result = Component.text("Found " + plugins.size() + " plugin(s): ");
                                    for (ModMetadata plugin : plugins) {
                                        result = result.append(Component.text("[" + plugin.getId() + "@" + plugin.getVersion().toString() + "]"));
                                        if (plugins.indexOf(plugin) != plugins.size() - 1) {
                                            result = result.append(Component.text(", "));
                                        }
                                    }
                                    context.getSource().sendMessage(result);
                                    return 0;
                                }))
        );
    }
}
