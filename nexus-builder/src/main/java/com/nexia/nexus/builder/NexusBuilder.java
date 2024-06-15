package com.nexia.nexus.builder;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NexusBuilder implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Nexus Builder");

	@Override
	public void onInitialize() {
		LOGGER.info("Building the Nexus API...");
	}
}
